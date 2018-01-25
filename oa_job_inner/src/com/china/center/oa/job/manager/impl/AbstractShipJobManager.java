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
import com.china.center.oa.sail.dao.*;
import com.china.center.oa.sail.manager.ShipManager;
import com.china.center.oa.sail.vo.BranchRelationVO;
import com.china.center.oa.sail.vo.PackageVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import jxl.Workbook;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class AbstractShipJobManager implements JobManager {
    private final Log _logger = LogFactory.getLog(getClass());

    private PackageDAO packageDAO = null;

    private PackageItemDAO packageItemDAO = null;

    private StafferDAO stafferDAO = null;

    private BranchRelationDAO branchRelationDAO = null;

    private CommonMailManager commonMailManager = null;

    private OutDAO outDAO = null;

    private OutImportDAO outImportDAO = null;

    private ProductImportDAO productImportDAO = null;

    private ProductDAO productDAO = null;

    private CustomerMainDAO customerMainDAO = null;

    private InvoiceinsDAO invoiceinsDAO = null;

    private ShipManager shipManager = null;

    abstract protected String getKey(String customerId);

    abstract protected void createMailAttachment(String customerName, List<PackageItemBean> beans,
                                                 String branchName, String fileName, boolean ignoreLyOrders);

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void run() throws MYException {
        _logger.info("****ShipJobManagerImpl running****");
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
//        con.addCondition("PackageBean.id", "=", "CK201801161697089589");

        //根据customerId+channel合并CK表
//        Map<String,List<PackageVO>> customer2Packages = new HashMap<String,List<PackageVO>>();
        //#245
        Map<String,List<PackageItemBean>> customer2Packages = new HashMap<String,List<PackageItemBean>>();
        //<支行customerId_channel,BranchRelationBean>
        Map<String,BranchRelationBean> customer2Relation = new HashMap<String,BranchRelationBean>();

        //step1: 根据支行customerId+channel对CK单合并
        List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);
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
                    String key = this.getKey(customerId);
//                    String channel = this.getChannel(itemBean);
//                    String key = customerId+"_"+channel;
                    //查询分支行对应关系表
                    if (!customer2Relation.containsKey(key)){
                        //TODO
                        BranchRelationBean bean = this.getRelationByCustomerId(customerId, this.getChannel(itemBean));
                        if(bean == null){
                            _logger.warn(vo.getId()+"***no relation found***"+customerId);
                            continue;
                        } else{
                            _logger.info(vo.getId()+"***relation is found****"+bean);
                            customer2Relation.put(key, bean);
                        }
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
            _logger.info("***mail count to be sent to bank***" + customer2Packages.keySet().size());
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
                String fileName = getShippingAttachmentPath() + "/" + subBranch
                        + "_" + TimeTools.now("yyyyMMddHHmmss") + ".xls";
                _logger.info("***fileName***"+fileName);
//                //浦发上海分行
//                if (subBranch.indexOf("浦发银行") != -1 && "上海分行".equals(branchName)){
//                    createPfMailAttachment(packages,bean.getBranchName(), fileName, true);
//                } else if (subBranch.indexOf("浦发银行") != -1 && subBranch.indexOf("小浦金店-银行")!= -1){
//                    createPfMailAttachmentForXiaoPu(packages,bean.getBranchName(), fileName, true);
//                } else if (subBranch.indexOf("浦发银行") != -1){
//                    this.shipManager.createMailAttachment(ShipConstant.BANK_TYPE_PF, packages,bean.getBranchName(),fileName,true);
//                }
                //TODO
                this.createMailAttachment(subBranch, packages,branchName, fileName, true);
                //#219 浦发改为只有两种模板
//                if (subBranch.indexOf("浦发银行") != -1 && "小浦金店".equals(bean.getChannel())){
//                    createPfMailAttachmentForXiaoPu(packages,branchName, fileName, true);
//                } else if (subBranch.indexOf("浦发银行") != -1){
//                    createPfMailAttachment(subBranch, packages,branchName, fileName, true);
//                }

                // check file either exists
                File file = new File(fileName);
                if (!file.exists()) {
                    _logger.error("fail to create mail attachment:" + fileName);
                    continue;
                    // throw new MYException("邮件附件未成功生成");
                }

                String title = String.format("永银文化%s发货信息", this.getYesterday());
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
                        _logger.info("***update mail flag for bank***"+packageId);
                    }
                }
            }
        } else {
            _logger.warn("***no VO found to send mail****");
        }
        _logger.info("***finish send mail to bank***");
    }

    private String getChannel(PackageItemBean item){
        String outId = item.getOutId();
        //忽略发票
        if (outId.startsWith("A")){
            return "";
        } else{
            OutBean outBean = outDAO.find(outId);
            if (outBean!= null && !StringTools.isNullOrNone(outBean.getChannel())){
                return outBean.getChannel();
            }
        }

        return "";
    }

    private void createPfMailAttachment(String customerName, List<PackageItemBean> beans,
                                        String branchName, String fileName, boolean ignoreLyOrders)
    {
        _logger.info("***createPfMailAttachment with package "+beans+"***branch***"+branchName+"***file name***"+fileName);
        WritableWorkbook wwb = null;

        WritableSheet ws = null;

        OutputStream out = null;

        try
        {
            out = new FileOutputStream(fileName);

            // create a excel
            wwb = Workbook.createWorkbook(out);

            ws = wwb.createSheet("发货信息", 0);

            // 横向
            ws.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4,0.5d,0.5d);

            // 标题字体
            WritableFont font = new WritableFont(WritableFont.ARIAL, 11,
                    WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);

            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 9,
                    WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);

            WritableFont font3 = new WritableFont(WritableFont.ARIAL, 9,
                    WritableFont.NO_BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);

            WritableFont font4 = new WritableFont(WritableFont.ARIAL, 9,
                    WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLUE);

            WritableCellFormat format = new WritableCellFormat(font);

            format.setAlignment(jxl.format.Alignment.CENTRE);
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

            WritableCellFormat format2 = new WritableCellFormat(font2);

            format2.setAlignment(jxl.format.Alignment.LEFT);
            format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            format2.setWrap(true);

            WritableCellFormat format21 = new WritableCellFormat(font2);
            format21.setAlignment(jxl.format.Alignment.RIGHT);

            WritableCellFormat format3 = new WritableCellFormat(font3);
            format3.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);

            WritableCellFormat format31 = new WritableCellFormat(font3);
            format31.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);
            format31.setAlignment(jxl.format.Alignment.RIGHT);

            WritableCellFormat format4 = new WritableCellFormat(font4);
            format4.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);

            WritableCellFormat format41 = new WritableCellFormat(font4);
            format41.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);
            format41.setAlignment(jxl.format.Alignment.CENTRE);
            format41.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

            int i = 0, j = 0, i1 = 1;
            String title = String.format("永银文化创意产业发展有限责任公司%s 产品发货清单", this.getYesterday());

            // 完成标题
            ws.addCell(new Label(1, i, title, format));

            //set column width
            ws.setColumnView(0, 15);
            ws.setColumnView(1, 40);
            ws.setColumnView(2, 40);
            ws.setColumnView(3, 20);
            ws.setColumnView(4, 40);
            ws.setColumnView(5, 10);
            ws.setColumnView(6, 5);
            ws.setColumnView(7, 10);
            ws.setColumnView(8, 20);
            ws.setColumnView(9, 20);
            ws.setColumnView(10, 10);

            i++;
            // 正文表格
            ws.addCell(new Label(0, i, "交易日期", format3));
            ws.addCell(new Label(1, i, "交易机构", format3));
            ws.addCell(new Label(2, i, "配货机构", format3));
            ws.addCell(new Label(3, i, "产品代码", format3));
            ws.addCell(new Label(4, i, "产品名称", format3));
            ws.addCell(new Label(5, i, "零售价", format3));
            ws.addCell(new Label(6, i, "数量", format3));
            ws.addCell(new Label(7, i, "快递公司", format3));
            ws.addCell(new Label(8, i, "快递单号", format3));
            ws.addCell(new Label(9, i, "发票号", format3));
            ws.addCell(new Label(10, i, "证书号", format3));


            _logger.info("***itemList size***"+beans.size());
//                this.mergeInvoiceNum(itemList);
            //TODO
            List<PackageItemBean> itemList = this.mergeItems(beans);
            _logger.info("***after merge itemList size***"+itemList.size());
            if (!ListTools.isEmptyOrNull(itemList)){
                for (PackageItemBean each : itemList)
                {
                    //#351 filter LY orders
                    if (ignoreLyOrders && each.getOutId().startsWith("LY")){
                        continue;
                    }
                    i++;

                    //交易日期
                    String outId = each.getOutId();
                    OutBean outBean = outDAO.find(outId);
                    if (outBean!= null){
                        each.setPoDate(outBean.getPodate());
                    } else if(out == null && outId.startsWith("A")){
                        each.setPoDate(each.getOutTime());
                    }

                    ws.addCell(new Label(j++, i, each.getPoDate(), format3));

                    //交易机构
                    ws.addCell(new Label(j++, i, this.getCustomerName(each), format3));

                    String[] temp = this.getCommunicationBranchNameAndProductCodeFromOutImport(each.getOutId());
                    //配货机构
                    ws.addCell(new Label(j++, i, temp[1], format3));

                    if(each.getOutId().startsWith("A")){
                        //产品代码
                        ws.addCell(new Label(j++, i, "", format3));

                        //产品名称
                        ws.addCell(new Label(j++, i, "", format3));
                    } else{
                        //产品代码
                        ws.addCell(new Label(j++, i, temp[0], format3));

                        //产品名称
                        ws.addCell(new Label(j++, i, this.shipManager.convertProductName(each,customerName), format3));
                    }


                    //零售价
                    ws.addCell(new Label(j++, i, String.valueOf(each.getPrice()), format3));

                    //数量
                    ws.addCell(new Label(j++, i, String.valueOf(each.getAmount()), format3));

                    //快递公司
                    ws.addCell(new Label(j++, i, each.getTransportName1(), format3));
                    //2016/4/5 #2 快递单号改取package表的transportNo
                    String transportNo = each.getTransportNo();
                    ws.addCell(new Label(j++, i, transportNo, format3));

                    //发票号
                    if(each.getOutId().startsWith("A")){
                        ws.addCell(new Label(j++, i, each.getProductName(), format3));
                    } else{
                        ws.addCell(new Label(j++, i, each.getInvoiceNum(), format3));
                    }

                    //证书号
                    ws.addCell(new Label(j++, i, "", format3));

                    j = 0;
                }
            }

        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (wwb != null)
            {
                try
                {
                    wwb.write();
                    wwb.close();
                }
                catch (Exception e1)
                {
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                }
            }
        }
    }


    private String getCustomerName(PackageItemBean item){
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

    private String getCiticNoFromOutImport(PackageItemBean item){
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

    private String[] getCommunicationBranchNameAndProductCodeFromOutImport(String outId){
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

    private String getYesterday(){
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

    private BranchRelationBean getRelationByCustomerId(String customerId,String channel){
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

    private List<PackageItemBean> mergeItems(List<PackageItemBean> items){
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

    /**
     * #170 浦发银行小浦金店
     * @param beans
     * @param branchName
     * @param fileName
     * @param ignoreLyOrders
     */
    private void createPfMailAttachmentForXiaoPu(List<PackageItemBean> beans, String branchName, String fileName, boolean ignoreLyOrders)
    {
        _logger.info("***createPfMailAttachmentForXiaoPu with package "+beans+"***branch***"+branchName+"***file name***"+fileName);
        WritableWorkbook wwb = null;

        WritableSheet ws = null;

        OutputStream out = null;

        try
        {
            out = new FileOutputStream(fileName);

            // create a excel
            wwb = Workbook.createWorkbook(out);

            ws = wwb.createSheet("发货信息", 0);

            // 横向
            ws.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4,0.5d,0.5d);

            // 标题字体
            WritableFont font = new WritableFont(WritableFont.ARIAL, 11,
                    WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);

            WritableFont font2 = new WritableFont(WritableFont.ARIAL, 9,
                    WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);

            WritableFont font3 = new WritableFont(WritableFont.ARIAL, 9,
                    WritableFont.NO_BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);

            WritableFont font4 = new WritableFont(WritableFont.ARIAL, 9,
                    WritableFont.BOLD, false,
                    jxl.format.UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLUE);

            WritableCellFormat format = new WritableCellFormat(font);

            format.setAlignment(jxl.format.Alignment.CENTRE);
            format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

            WritableCellFormat format2 = new WritableCellFormat(font2);

            format2.setAlignment(jxl.format.Alignment.LEFT);
            format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
            format2.setWrap(true);

            WritableCellFormat format21 = new WritableCellFormat(font2);
            format21.setAlignment(jxl.format.Alignment.RIGHT);

            WritableCellFormat format3 = new WritableCellFormat(font3);
            format3.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);

            WritableCellFormat format31 = new WritableCellFormat(font3);
            format31.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);
            format31.setAlignment(jxl.format.Alignment.RIGHT);

            WritableCellFormat format4 = new WritableCellFormat(font4);
            format4.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);

            WritableCellFormat format41 = new WritableCellFormat(font4);
            format41.setBorder(jxl.format.Border.ALL,
                    jxl.format.BorderLineStyle.THIN);
            format41.setAlignment(jxl.format.Alignment.CENTRE);
            format41.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

            int i = 0, j = 0, i1 = 1;
            String title = String.format("永银文化创意产业发展有限责任公司%s 产品发货清单", this.getYesterday());

            // 完成标题
            ws.addCell(new Label(1, i, title, format));

            //set column width
            ws.setColumnView(0, 40);
            ws.setColumnView(1, 20);
            ws.setColumnView(2, 40);


            i++;
            // 正文表格
            ws.addCell(new Label(0, i, "银行单号", format3));
            ws.addCell(new Label(1, i, "快递公司", format3));
            ws.addCell(new Label(2, i, "快递单号", format3));

            _logger.info("***itemList size***"+beans.size());
            List<PackageItemBean> itemList = this.mergeItems(beans);
            _logger.info("***after merge itemList size***"+itemList);
            if (!ListTools.isEmptyOrNull(itemList)){
                for (PackageItemBean each : itemList)
                {
                    //#351 filter LY orders
                    if (ignoreLyOrders && each.getOutId().startsWith("LY")){
                        continue;
                    }
                    i++;

                    //银行单号
                    String outId = each.getOutId();
                    String citicNo = this.getCiticNoFromOutImport(each);
                    ws.addCell(new Label(j++, i, citicNo, format3));

                    //快递公司
                    ws.addCell(new Label(j++, i, each.getTransportName1(), format3));

                    //2016/4/5 #2 快递单号改取package表的transportNo
                    String transportNo = each.getTransportNo();
                    ws.addCell(new Label(j++, i, transportNo, format3));

                    j = 0;
                }
            }

        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (wwb != null)
            {
                try
                {
                    wwb.write();
                    wwb.close();
                }
                catch (Exception e1)
                {
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                }
            }
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
