package com.china.center.oa.job.manager.impl;

import com.center.china.osgi.config.ConfigLoader;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.job.manager.JobManager;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductImportDAO;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.sail.bean.*;
import com.china.center.oa.sail.constanst.ShipConstant;
import com.china.center.oa.sail.dao.*;
import com.china.center.oa.sail.manager.ShipManager;
import com.china.center.oa.sail.vo.BranchRelationVO;
import com.china.center.oa.sail.vo.PackageVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class AbstractShipJobManager implements JobManager {
    private final Log _logger = LogFactory.getLog(getClass());

    protected PackageDAO packageDAO = null;

    private PackageItemDAO packageItemDAO = null;

    private StafferDAO stafferDAO = null;

    private BranchRelationDAO branchRelationDAO = null;

    private CommonMailManager commonMailManager = null;

    protected OutDAO outDAO = null;

    protected OutImportDAO outImportDAO = null;

    private ProductImportDAO productImportDAO = null;

    private ProductDAO productDAO = null;

    private CustomerMainDAO customerMainDAO = null;

    private InvoiceinsDAO invoiceinsDAO = null;

    protected ShipManager shipManager = null;

    /**
     * 分组package的key
     * @param itemBean
     * @return
     */
    abstract protected String getKey(PackageItemBean itemBean);

    abstract protected boolean createMailAttachment(int index,String customerName, String channel, List<PackageItemBean> beans,
                                                 String branchName, String fileName, boolean ignoreLyOrders);

    abstract protected BranchRelationBean getRelation(String customerId,String channel);

    abstract protected boolean needSendMail(String customerName, String channel);

    protected String getAttachmentFileName(BranchRelationBean bean){
        return getShippingAttachmentPath() + "/" + bean.getSubBranchName()
                + TimeTools.now("yyyyMMddHHmm") + ".xls";
    }

    protected String getTitle(){
        return String.format("永银文化%s发货信息", this.getYesterday());
    }

    protected List<PackageVO> getPackageList(){
        ConditionParse con = new ConditionParse();
        con.addWhereStr();
        con.addIntCondition("PackageBean.sendMailFlag", "=", 0);
        con.addCondition("PackageBean.logTime", ">=", "2017-04-27 00:00:00");
        //自提类的也不在发送邮件范围内
        con.addIntCondition("PackageBean.shipping","!=", 0);
        //#174 把有直邮标识的订单过滤掉
        con.addIntCondition("PackageBean.direct","!=", 1);
        //#236 已发货和在途都要发邮件
        con.addCondition(" and PackageBean.status in(2,10)");
        //!!test only
//        con.addCondition("PackageBean.id", "=", this.getTestCk());

        //step1: 根据支行customerId+channel对CK单合并
        List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);
        return packageList;
    }

    /**
     * Test only!
     * @return
     */
    @Deprecated
    abstract protected String getTestCk();

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void run() throws MYException {
        _logger.info(this.getClass()+" JOB running****");

        //根据customerId+channel合并CK表
        //#245
        Map<String,List<PackageItemBean>> customer2Packages = new HashMap<String,List<PackageItemBean>>();
        //<支行customerId_channel,BranchRelationBean>
        Map<String,BranchRelationBean> customer2Relation = new HashMap<String,BranchRelationBean>();

        //step1: 根据支行customerId+channel对CK单合并
        List<PackageVO> packageList = this.getPackageList();
        if (!ListTools.isEmptyOrNull(packageList))
        {
            _logger.info("****packageList to be sent mail to bank***"+packageList.size());
            for (PackageVO vo : packageList){
                //2016/4/12 update
                //如果收货人姓名是在oastaffer表的name字段里的，则此销售单不在发送邮件范围内
                String receiver = vo.getReceiver();
                if(!StringTools.isNullOrNone(receiver)){
                    StafferBean stafferBean = this.stafferDAO.findyStafferByName(receiver);
                    if (stafferBean!= null){
                        _logger.warn(vo.getId()+"***belong to staffer***"+receiver);
                        continue;
                    }
                }

                List<PackageItemBean> itemList = packageItemDAO.queryEntityBeansByFK(vo.getId());
                for(PackageItemBean itemBean: itemList){
                    //#245
                    itemBean.setReceiver(vo.getReceiver());
                    itemBean.setTransportName1(vo.getTransportName1());
                    itemBean.setTransportNo(vo.getTransportNo());

                    String customerId = itemBean.getCustomerId();
                    String key = this.getKey(itemBean);
                    CustomerBean customerBean = this.customerMainDAO.find(customerId);
                    String customerName = "";
                    if (customerBean!= null){
                        customerName = customerBean.getName();
                    }
                    String channel = this.getChannel(itemBean);
                    //查询分支行对应关系表
                    if (!customer2Relation.containsKey(key)){
                        BranchRelationBean bean = this.getRelation(customerId, channel);
                        if(bean == null){
                            String template = "JOB:%s no relation found for package:%s customer:%s channel:%s";
                            _logger.warn(String.format(template, this.getClass(),itemBean.toString(),customerId,channel));
                            continue;
                        } else{
                            String template = "JOB:%s relation is found for package:%s customer:%s channel:%s key:%s customerName:%s bean:%s";
                            _logger.warn(String.format(template, this.getClass(),itemBean.toString(),customerId, channel, key, customerName, bean.toString()));
                            customer2Relation.put(key, bean);
                        }
                    }

                    boolean needSendMail = this.needSendMail(customerName,channel);
                    //只检查特定客户的订单
                    if (!needSendMail){
                        continue;
                    }

                    if (customer2Packages.containsKey(key)){
                        List<PackageItemBean> voList = customer2Packages.get(key);
                        voList.add(itemBean);
                    }else{
                        List<PackageItemBean> voList =  new ArrayList<PackageItemBean>();
                        voList.add(itemBean);
                        customer2Packages.put(key, voList);
                    }
                }
            }

            //step2 send mail for merged packages
            _logger.info(this.getClass()+"***mail count to be sent to bank***" + customer2Packages.keySet().size());
            int index = 0;
            for (String key : customer2Packages.keySet()) {
                List<PackageItemBean> packages = customer2Packages.get(key);
                BranchRelationBean bean = customer2Relation.get(key);
                _logger.info(key+"***send mail to branch***"+bean);
                if (bean == null){
                    continue;
                } else if (bean.getSendMailFlag() ==0 && bean.getCopyToBranchFlag() == 0) {
                    _logger.warn("***flag is not set***"+bean);
                    continue;
                }
                String subBranch = bean.getSubBranchName();
                String branchName = bean.getBranchName();
                String fileName = this.getAttachmentFileName(bean);
                _logger.info("***fileName***"+fileName);
                boolean result = this.createMailAttachment(index, subBranch, bean.getChannel(), packages,branchName, fileName, true);
                if (!result){
                    _logger.warn("No mail attachment created***"+fileName);
                    continue;
                }
                index += 1;

                // check file either exists
                File file = new File(fileName);
                if (!file.exists()) {
                    _logger.error("fail to create mail attachment:" + fileName);
                    continue;
                }

                String title = this.getTitle();
                String content = "永银文化创意产业发展有限责任公司发货信息，请查看附件，谢谢。";
                if(bean.getSendMailFlag() == 1){
                    String subBranchMail = bean.getSubBranchMail().trim();
                    _logger.info("***send mail to subBranchMail:"+subBranchMail+"***package size***:"+packages.size());
                    String[] mails = subBranchMail.split(",");
                    // 发送给支行
                    for (String mail:mails){
                        commonMailManager.sendMail(mail, title,content, fileName);
                    }
                } else{
                    _logger.warn("***send mail flag for sub branch is 0***" + bean);
                }

                if(bean.getCopyToBranchFlag() == 1){
                    String branchMail = bean.getBranchMail().trim();
                    _logger.info("***send mail to branchMail:"+branchMail+"***package size***:"+packages.size());
                    // 抄送分行
                    String[] mails = branchMail.split(",");
                    for (String mail: mails){
                        commonMailManager.sendMail(mail, title,content, fileName);
                    }
                } else{
                    _logger.warn("***relation send mail copy flag is 0***" + bean);
                }

                for (PackageItemBean vo:customer2Packages.get(key)){
                    //Update sendMailFlag to 1
                    String packageId = vo.getPackageId();
                    PackageBean packBean = packageDAO.find(packageId);
                    if (packBean.getSendMailFlag()!= 1){
                        packBean.setSendMailFlag(1);
                        this.packageDAO.updateEntityBean(packBean);
                        this.shipManager.addLog(packageId,packBean.getStatus(), ShipConstant.SHIP_STATUS_CONSIGN,
                                "发货邮件:"+bean.getSubBranchMail().trim(), TimeTools.now());
                        _logger.info("***update mail flag for bank***"+packageId);
                    }
                }
            }
        } else {
            _logger.warn("***no VO found to send mail****");
        }
        _logger.info("***finish send mail to bank***");
    }

    protected String getChannel(PackageItemBean item){
        String outId = item.getOutId();
        //忽略发票
        if (outId.startsWith("A")){
            //#275 发票取原销售单的渠道
            InvoiceinsBean invoiceinsBean = this.invoiceinsDAO.find(outId);
            if (invoiceinsBean!= null && invoiceinsBean.getRefIds()!= null){
                String[] outIds = invoiceinsBean.getRefIds().split(";");
                if (outIds!= null && outIds.length>=1){
                    OutBean outBean = this.outDAO.find(outIds[0]);
                    if(outBean!= null){
                        return outBean.getChannel();
                    }
                }
            }
            return "";
        } else{
            OutBean outBean = outDAO.find(outId);
            if (outBean!= null && !StringTools.isNullOrNone(outBean.getChannel())){
                return outBean.getChannel();
            }
        }

        return "";
    }


    protected String getCustomerName(PackageItemBean item){
        String customerName = "";
        String outId = item.getOutId();
        if (StringTools.isNullOrNone(outId)){
            _logger.warn("****Empty OutId***********"+outId);
        } else if (outId.startsWith("SO")){
            customerName = this.getCustomerNameFromOutImport(outId);
        } else if(outId.startsWith("ZS")){
            OutBean out = outDAO.find(outId);
            if (out!= null){
                String sourceOutId = out.getRefOutFullId();
                if (!StringTools.isNullOrNone(sourceOutId)){
                    customerName = this.getCustomerNameFromOutImport(sourceOutId);
                }

                if (StringTools.isNullOrNone(item.getCustomerName())) {
                    //#181默认就读取out表中客户名
                    customerName = out.getCustomerName();
                }

            }
        } else if(outId.startsWith("A")){
            //TODO refer to ShipAction
            CustomerBean customerBean = this.customerMainDAO.find(item.getCustomerId());
            if (customerBean!= null){
                customerName = customerBean.getName();
            }
        }
        return customerName;
    }

    protected String getCiticNoFromOutImport(PackageItemBean item){
        String outId = item.getOutId();
        if (outId.startsWith("A")){
            //如果是发票，返回合并过的发票号
            return item.getProductName();
        }else{
            List<OutImportBean> importBeans = outImportDAO.queryEntityBeansByFK(outId, AnoConstant.FK_FIRST);

            if (!ListTools.isEmptyOrNull(importBeans))
            {
                return importBeans.get(0).getCiticNo();
            }

            return outId;
        }
    }

    private String getCustomerNameFromOutImport(String outId){
        StringBuilder sb = new StringBuilder();
        List<OutImportBean> importBeans = outImportDAO.queryEntityBeansByFK(outId, AnoConstant.FK_FIRST);

        if (!ListTools.isEmptyOrNull(importBeans))
        {
            for (OutImportBean outImportBean: importBeans){
                String customerName = outImportBean.getCustomerName();
                if (!StringTools.isNullOrNone(customerName)){
                    sb.append(customerName).append(";");
                }
            }
        }
        String customerName = sb.toString();
        if (StringTools.isNullOrNone(customerName)){
            return customerName;
        } else{
            //remove last ";" char
            return customerName.substring(0,customerName.length()-1);
        }
    }

    protected String[] getCommunicationBranchNameAndProductCodeFromOutImport(String outId){
        String[] result = new String[2];
        String productCode = "";
        String branchName = "";
        List<OutImportBean> importBeans = outImportDAO.queryEntityBeansByFK(outId, AnoConstant.FK_FIRST);
        if (!ListTools.isEmptyOrNull(importBeans))
        {
            for (OutImportBean outImportBean: importBeans){
                if (!StringTools.isNullOrNone(outImportBean.getBranchName())){
                    productCode = outImportBean.getProductCode();
                    branchName = outImportBean.getComunicatonBranchName();
                    break;
                }
            }
        }

        result[0] = productCode;
        result[1] = branchName;
        return result;
    }

    public String getShippingAttachmentPath()
    {
        return ConfigLoader.getProperty("shippingAttachmentPath");
    }

    protected String getYesterday(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String specifiedDay = sdf.format(date);
        return this.getSpecifiedDayBefore(specifiedDay);
    }

    private String getSpecifiedDayBefore(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("MM月dd日").format(c
                .getTime());
        return dayBefore;
    }

    protected BranchRelationBean getRelationByCustomerId(String customerId,String channel){
        ConditionParse con2 = new ConditionParse();
        con2.addWhereStr();
        con2.addCondition("BranchRelationBean.id", "=", customerId);
        if (StringTools.isNullOrNone(channel)){
            con2.addCondition(" and (BranchRelationBean.channel is null or BranchRelationBean.channel='')");
        } else{
            con2.addCondition("BranchRelationBean.channel", "=", channel);
        }
        List<BranchRelationVO> relationList = this.branchRelationDAO.queryVOsByCondition(con2);
        if (!ListTools.isEmptyOrNull(relationList)){
            BranchRelationVO relation = relationList.get(0);
            _logger.info("***relation is found****"+relation);
            return relation;
        } else{
            CustomerBean customerBean = this.customerMainDAO.find(customerId);
            if (customerBean == null){
                _logger.warn("***no relation found***"+customerId);
                return null;
            } else{
                ConditionParse conditionParse = new ConditionParse();
                conditionParse.addWhereStr();
                conditionParse.addCondition("BranchRelationBean.subBranchName", "=", customerBean.getName());
                if (StringTools.isNullOrNone(channel)){
                    conditionParse.addCondition(" and (BranchRelationBean.channel is null or BranchRelationBean.channel='')");
                } else{
                    conditionParse.addCondition("BranchRelationBean.channel", "=", channel);
                }
                relationList = this.branchRelationDAO.queryVOsByCondition(conditionParse);
                if (ListTools.isEmptyOrNull(relationList)){
                    _logger.warn(customerId+"***no relation found***"+channel);
                    return null;
                }else{
                    BranchRelationVO relation = relationList.get(0);
                    _logger.info(customerBean.getName()+"***relation is found for customer****"+relation);
                    return relation;
                }
            }
        }
    }

    protected List<PackageItemBean> mergeItems(List<PackageItemBean> items){
        List<PackageItemBean> result = new ArrayList<PackageItemBean>();
        //商品行
        List<PackageItemBean> soList = new ArrayList<PackageItemBean>();
        //发票行
        List<PackageItemBean> aList = new ArrayList<PackageItemBean>();
        for (PackageItemBean item: items){
            if (item.getOutId().startsWith("A")){
                aList.add(item);
            } else{
                soList.add(item);
            }
        }

        _logger.info(soList);
        _logger.info(aList);
        //找到商品行对应的发票号码
        if (!ListTools.isEmptyOrNull(soList)){
            for (PackageItemBean item: soList){
                for (Iterator<PackageItemBean> it=aList.iterator();it.hasNext();){
                    PackageItemBean itemBean = it.next();
                    InvoiceinsBean invoiceinsBean = this.invoiceinsDAO.find(itemBean.getOutId());
                    _logger.info(invoiceinsBean+"***vs***"+itemBean);
                    if (invoiceinsBean!= null && invoiceinsBean.getRefIds()!=null &&
                            invoiceinsBean.getRefIds().contains(item.getOutId())){
                        //把发票号码合并到商品行,此发票行不再单独显示
                        item.setInvoiceNum(StringUtils.concat(item.getInvoiceNum(), itemBean.getProductId(),"/"));
                        it.remove();
                        _logger.info("***find invoice num***"+item.getInvoiceNum());
                        continue;
                    }
                }
            }
        }
        _logger.info(aList);
        //剩下未合并的发票行还需要合并到一行
        this.mergeInvoiceNum(aList);
        _logger.info(aList);
        result.addAll(soList);
        result.addAll(aList);
        return result;
    }

    private void mergeInvoiceNum(List<PackageItemBean> packages){
        StringBuilder sb = new StringBuilder();
        PackageItemBean newItem = new PackageItemBean();
        int invoiceCount = 0;
        double invoiceMoney = 0;
        for (Iterator<PackageItemBean> it=packages.iterator();it.hasNext();){
            PackageItemBean item = it.next();
            if (item.getOutId().startsWith("A")){
                String[] arrays = item.getProductName().split("：");
                String invoiceNum = arrays[1].trim();
                sb.append(invoiceNum).append("/");
                it.remove();
                BeanUtil.copyProperties(newItem, item);
                invoiceCount++;
                invoiceMoney+= item.getPrice();
            }
        }
        //发票号码以/分隔
        String productName = sb.toString();
        _logger.info("***productName for***"+productName);
        if(!StringTools.isNullOrNone(productName)) {
            newItem.setProductName(productName.substring(0, productName.length() - 1));
            newItem.setPrice(invoiceMoney);
            newItem.setAmount(invoiceCount);
            packages.add(newItem);
        }
    }



    public PackageDAO getPackageDAO() {
        return packageDAO;
    }

    public void setPackageDAO(PackageDAO packageDAO) {
        this.packageDAO = packageDAO;
    }

    public PackageItemDAO getPackageItemDAO() {
        return packageItemDAO;
    }

    public void setPackageItemDAO(PackageItemDAO packageItemDAO) {
        this.packageItemDAO = packageItemDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

    public BranchRelationDAO getBranchRelationDAO() {
        return branchRelationDAO;
    }

    public void setBranchRelationDAO(BranchRelationDAO branchRelationDAO) {
        this.branchRelationDAO = branchRelationDAO;
    }

    public CommonMailManager getCommonMailManager() {
        return commonMailManager;
    }

    public void setCommonMailManager(CommonMailManager commonMailManager) {
        this.commonMailManager = commonMailManager;
    }

    public OutDAO getOutDAO() {
        return outDAO;
    }

    public void setOutDAO(OutDAO outDAO) {
        this.outDAO = outDAO;
    }

    public OutImportDAO getOutImportDAO() {
        return outImportDAO;
    }

    public void setOutImportDAO(OutImportDAO outImportDAO) {
        this.outImportDAO = outImportDAO;
    }

    public ProductImportDAO getProductImportDAO() {
        return productImportDAO;
    }

    public void setProductImportDAO(ProductImportDAO productImportDAO) {
        this.productImportDAO = productImportDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public CustomerMainDAO getCustomerMainDAO() {
        return customerMainDAO;
    }

    public void setCustomerMainDAO(CustomerMainDAO customerMainDAO) {
        this.customerMainDAO = customerMainDAO;
    }

    public InvoiceinsDAO getInvoiceinsDAO() {
        return invoiceinsDAO;
    }

    public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO) {
        this.invoiceinsDAO = invoiceinsDAO;
    }

    public ShipManager getShipManager() {
        return shipManager;
    }

    public void setShipManager(ShipManager shipManager) {
        this.shipManager = shipManager;
    }
}
