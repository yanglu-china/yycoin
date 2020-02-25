package com.china.center.oa.product.manager.impl;

import java.util.List;

import com.center.china.osgi.config.ConfigLoader;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.*;
import com.china.center.oa.product.dao.*;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.constant.AppConstant;
import com.china.center.oa.publics.constant.ModuleConstant;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.oa.publics.dao.LogDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.product.constant.ProductApplyConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.listener.ProductApplyListener;
import com.china.center.oa.product.manager.ProductApplyManager;
import com.china.center.oa.product.vs.ProductCombinationBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.tools.JPTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;

public class ProductApplyManagerImpl extends AbstractListenerManager<ProductApplyListener>
        implements ProductApplyManager {

    private final Log             operationLog          = LogFactory.getLog("opr");

    private final Log           _logger             = LogFactory.getLog(getClass());

    private ProductApplyDAO       productApplyDAO       = null;

    private ProductSubApplyDAO    productSubApplyDAO    = null;

    private ProductVSStafferDAO   productVSStafferDAO   = null;

    private FlowLogDAO            flowLogDAO            = null;

    private LogDAO logDAO          = null;

    private CommonDAO             commonDAO             = null;

    private ProductDAO            productDAO            = null;

    private ProductImportDAO productImportDAO = null;
    
    private SequenceDAO sequenceDAO = null;

    private ProductCombinationDAO productCombinationDAO = null;
    
    private ProductBOMDAO productBOMDAO = null;
    
    private static Object lock = new Object();

    public ProductApplyManagerImpl() {

    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean addProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        // 获取ID
        String id = this.createId(bean);

        bean.setId(id);

        int oldStatus = bean.getStatus();

        if (oldStatus == ProductApplyConstant.STATUS_SUBMIT) {
            //2015/8/15 新产品申请去掉产品管理部审批环节
            //2016/6/5 #247
            bean.setStatus(ProductApplyConstant.STATUS_PRODUCT_FINANCE);
        }

        checkUnique(bean);

        // exp.check("#code &unique @productApplyDAO", "编码已经存在");

        _logger.info("***addProductApply***" + bean);
        productApplyDAO.saveEntityBean(bean);

        List<ProductSubApplyBean> subList = bean.getProductSubApplyList();

        if (!ListTools.isEmptyOrNull(subList)) {
            for (ProductSubApplyBean each : subList) {
                each.setRefId(id);

                productSubApplyDAO.saveEntityBean(each);
            }
        }

        List<ProductVSStafferBean> vsList = bean.getVsList();

        if (!ListTools.isEmptyOrNull(vsList)) {
            for (ProductVSStafferBean each : vsList) {
                each.setRefId(id);

                productVSStafferDAO.saveEntityBean(each);
            }
        }

        // 增加数据库日志
        if (oldStatus == ProductApplyConstant.STATUS_SAVE)
            addLog(id, user, bean, "保存", ProductApplyConstant.OPRMODE_SAVE,
                    ProductApplyConstant.STATUS_SUBMIT);
        else {
            bean.setStatus(oldStatus);

            addLog(id, user, bean, "提交", ProductApplyConstant.OPRMODE_SUBMIT,
                    //2015/8/15 新产品申请去掉产品管理部审批环节
                    //2016/6/5 #247
                    ProductApplyConstant.STATUS_PRODUCT_FINANCE);
        }

        return true;
    }

    /**
     * checkUnique
     * 
     * @param bean
     * @throws MYException
     */
    private void checkUnique(ProductApplyBean bean) throws MYException 
    {
        ProductBean productBean = new ProductBean();
        
        productBean.setName(bean.getName());
        
        Expression exp1 = new Expression(productBean, this);

        exp1.check("#name &unique @productDAO", "名称已经存在");
        
        if (bean.getNature() == ProductApplyConstant.NATURE_SINGLE)
	    {
	    	// 根据成品，获取配件的编码
	    	String refProductId = bean.getRefProductId();
	    	
	    	if (StringTools.isNullOrNone(refProductId))
	    	{
	    		throw new MYException("配件产品时要有成品产品关联");
	    	}
	    	
	    	ProductBean product = productDAO.find(refProductId);
	    	
	    	if (null == product)
	    	{
	    		throw new MYException("关联的成品不存在:"+refProductId);
	    	}
	    	
	    	String masterMidName = product.getMidName();
	    	
	    	if (StringTools.isNullOrNone(masterMidName))
	    	{
	    		throw new MYException("新模式产生的成品才能建配件产品");
	    	}
	    }
        
/*        Expression exp = new Expression(bean, this);

        exp.check("#name &unique @productApplyDAO", "名称已经存在");*/
    }


    @Transactional(rollbackFor = MYException.class)
    public boolean updateProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        String id = bean.getId();

        ProductApplyBean old = productApplyDAO.find(id);

        if (null == old) {
            throw new MYException("数据错误");
        }
                
        checkUnique(bean);
        
        operationLog.info("updateProductBean productApplyBean old:" + old);
        
        if (old.getStatus() != ProductApplyConstant.STATUS_SAVE && old.getStatus() != ProductApplyConstant.STATUS_REJECT)
        {
        	throw new MYException("只能修改保存、驳回状态申请");
        }
        
        if (bean.getStatus() == ProductApplyConstant.STATUS_SUBMIT) {
            //2015/8/15 新产品申请去掉产品管理部审批环节
            //2016/6/5 #247
            bean.setStatus(ProductApplyConstant.STATUS_PRODUCT_FINANCE);
        }
        
        productApplyDAO.updateEntityBean(bean);

        List<ProductSubApplyBean> subList = bean.getProductSubApplyList();

        productSubApplyDAO.deleteEntityBeansByFK(id);

        if (!ListTools.isEmptyOrNull(subList)) {
            for (ProductSubApplyBean subBean : subList) {
                subBean.setRefId(id);

                productSubApplyDAO.saveEntityBean(subBean);
            }
        }

        List<ProductVSStafferBean> vsList = bean.getVsList();

        productVSStafferDAO.deleteEntityBeansByFK(id);

        if (!ListTools.isEmptyOrNull(vsList)) {
            for (ProductVSStafferBean each : vsList) {
                each.setRefId(id);

                productVSStafferDAO.saveEntityBean(each);
            }
        }

        // 增加数据库日志
        if (bean.getStatus() == ProductApplyConstant.STATUS_SAVE)
            addLog(id, user, bean, "保存", ProductApplyConstant.OPRMODE_SAVE,
                    ProductApplyConstant.STATUS_SUBMIT);
        else {
            bean.setStatus(bean.getStatus());

            addLog(id, user, bean, "提交", ProductApplyConstant.OPRMODE_SUBMIT,
                    //2015/8/15 新产品申请去掉产品管理部审批环节
                    //2016/6/5 #247
                    ProductApplyConstant.STATUS_PRODUCT_FINANCE);
        }

        operationLog.info("updateProductBean productApplyBean new :" + bean);

        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteProductApply(User user, String id) throws MYException {

        JudgeTools.judgeParameterIsNull(user, id);

        ProductApplyBean pab = productApplyDAO.find(id);

        if (null == pab) {
            throw new MYException("数据错误，ID=" + id + "产品没有找到，请联系管理员");
        }

        int status = pab.getStatus();

        if (status != ProductApplyConstant.STATUS_SAVE
                && status != ProductApplyConstant.STATUS_REJECT) {
            throw new MYException("非保存或驳回状态，不允许删除");
        }

        productApplyDAO.deleteEntityBean(id);

        productSubApplyDAO.deleteEntityBeansByFK(id);

        productVSStafferDAO.deleteEntityBeansByFK(id);

        operationLog.info("Delete ProductApplyBean, id:" + id);

        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean passProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        String id = bean.getId();

        ProductApplyBean pab = productApplyDAO.find(id);

        if (null == pab) {
            throw new MYException("数据错误，ID=" + id + "产品没有找到，请联系管理员");
        }

        int status = bean.getStatus();

        if (status != ProductApplyConstant.STATUS_PRODUCT_FINANCE) {
            throw new MYException("当前状态不是待财务总监审批状态,请联系管理员");
        }

        productApplyDAO.modifyProductApplyStatus(id, ProductApplyConstant.STATUS_PRODUCT_STRATEGY);

        // add log
        addLog(id, user, bean, "通过", ProductApplyConstant.OPRMODE_PASS,
                ProductApplyConstant.STATUS_PRODUCT_STRATEGY);

        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean pass1ProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        synchronized (lock)
		{
        	String id = bean.getId();

            ProductApplyBean applyBean = productApplyDAO.find(id);

            if (null == applyBean) {
                throw new MYException("数据错误，ID=" + id + "产品没有找到，请联系管理员");
            }

            int status = bean.getStatus();

            if (status != ProductApplyConstant.STATUS_PRODUCT_STRATEGY) {
                throw new MYException("当前状态不是待战略审批状态,请联系管理员:"+status);
            }

            createFullName(applyBean);
            
            createCode(applyBean);

            createSpell(applyBean);

            applyBean.setStatus(ProductApplyConstant.STATUS_FINISHED);

            productApplyDAO.updateEntityBean(applyBean);

            // 申请审批结束后产品转至正式商品表
            transferToProductBean(id, applyBean);
            
            addLog(id, user, bean, "通过", ProductApplyConstant.OPRMODE_PASS,
                    ProductApplyConstant.STATUS_FINISHED);
		}
        
        return true;
    }

	private void createFullName(ProductApplyBean bean) throws MYException
	{
		// 组合产品全名
	    String firstName = "";
	    
	    String sailType = "";
	    
	    switch (bean.getChannelType())
	    {
	        case ProductConstant.SAILTYPE_SELF:
	        	sailType = "Z";
	            break;
	        case ProductConstant.SAILTYPE_REPLACE:
	        	sailType = "G";
	            break;
	        case ProductConstant.SAILTYPE_CUSTOMER:
	        	sailType = "D";
	            break;
	        case ProductConstant.SAILTYPE_OTHER:
	        	sailType = "S";
	            break;
	        default:
	        	sailType = "";
	            break;
	    }
	    
	    if (StringTools.isNullOrNone(sailType))
	    {
	    	throw new MYException("渠道数据错误");
	    }
	    
	    firstName = "Y" + sailType;
	    
	    bean.setFirstName(firstName);
	    
	    String midName = "";
	    
	    // midName，需确定是成品还是配件，如果是成品，生成5位的流水， 如果是配件，获取成品midName，并取出后2位，并增1. 
	    // 同步synchronized,且在审批最后产生
	    if (bean.getNature() == ProductApplyConstant.NATURE_COMPOSE)
	    {
	    	midName = sequenceDAO.getSquenceString5(sailType) + "00";
	    }else
	    {
	    	// 根据成品，获取配件的编码
	    	String refProductId = bean.getRefProductId();
	    	
	    	if (StringTools.isNullOrNone(refProductId))
	    	{
	    		throw new MYException("配件产品时要有成品产品关联");
	    	}
	    	
	    	ProductBean product = productDAO.find(refProductId);
	    	
	    	if (null == product)
	    	{
	    		throw new MYException("关联的成品不存在:"+refProductId);
	    	}
	    	
	    	String masterMidName = product.getMidName();
	    	
	    	if (StringTools.isNullOrNone(masterMidName))
	    	{
	    		throw new MYException("新模式产生的成品才能建配件产品");
	    	}
	    	
	    	// 取配件产品midName 最大值 
	    	int maxMidName = productDAO.getmaxMidName(refProductId);
	    	
	    	if (maxMidName == 0)
	    	{
	    		midName = masterMidName.substring(0, 5) + "01";
	    	}else{
	    		midName = StringTools.formatString(String.valueOf(maxMidName + 1), 7);
	    	}
	    }

        String fullName = firstName + midName + " " + bean.getName();

	    //#432
        String appName = ConfigLoader.getProperty("appName");
        if (AppConstant.APP_NAME_TW.equals(appName)){
            fullName = "TW"+fullName;
        } else if(AppConstant.APP_NAME_ZJGH.equals(appName)){
            fullName = AppConstant.PREFIX_GH+fullName;
        }

        bean.setMidName(midName);
	    
	    bean.setFullName(fullName);
	}

    /**
     * transferToProductBean
     * 
     * @param id
     * @param applyBean
     */
    private void transferToProductBean(String id, ProductApplyBean applyBean) throws MYException
    {
        // 申请审批完成后，产品转至正式产品表
        ProductBean productBean = new ProductBean();

        productBean.setId(applyBean.getId());
        productBean.setName(applyBean.getFullName());
        productBean.setFullspell(applyBean.getFullspell());
        productBean.setShortspell(applyBean.getShortspell());
        productBean.setCode(applyBean.getCode());
        productBean.setCtype(applyBean.getNature());
        productBean.setType(applyBean.getType());
        productBean.setStockType(applyBean.getSalePeriod());
        productBean.setCheckDays(applyBean.getMateriaType());
        productBean.setMaxStoreDays(applyBean.getCultrueType());
        productBean.setSafeStoreDays(applyBean.getDiscountRate());
        productBean.setMakeDays(applyBean.getPriceRange());
        productBean.setFlowDays(applyBean.getSaleTarget());
        productBean.setMinAmount(applyBean.getCurrencyType());
        productBean.setConsumeInDay(applyBean.getSecondhandGoods());
        productBean.setOrderAmount(applyBean.getStyle());
        productBean.setAssistantProvider2(applyBean.getCommissionBeginDate());
        productBean.setAssistantProvider3(applyBean.getCommissionEndDate());
        productBean.setAssistantProvider4(applyBean.getProductManagerId());
        productBean.setSailType(applyBean.getChannelType());
        productBean.setAdjustPrice(applyBean.getCountry());
        productBean.setLogTime(TimeTools.now());
        productBean.setCreaterId(applyBean.getOprId());
        productBean.setDescription(applyBean.getDescription());
        productBean.setReserve4(String.valueOf(applyBean.getManagerType()));
        productBean.setReserve6(applyBean.getIndustryId());
        productBean.setStatus(ProductConstant.STATUS_COMMON);
        productBean.setCost(applyBean.getGold());
        productBean.setPlanCost(applyBean.getSilver());
        productBean.setMidName(applyBean.getMidName());
        productBean.setRefProductId(applyBean.getRefProductId()); // 配件产品时关联的成品产品
        productBean.setDutyType(applyBean.getDutyType());
        productBean.setInputInvoice(applyBean.getInputInvoice());
        productBean.setSailInvoice(applyBean.getSailInvoice());

        //2015/11/23 把新产品申请里的销售周期/销售对象/纸币类型/外型栏位，分别改为 实物数量、包装数量、证书数量、产品克重
        productBean.setProductAmount(applyBean.getProductAmount());
        productBean.setPackageAmount(applyBean.getPackageAmount());
        productBean.setCertificateAmount(applyBean.getCertificateAmount());
        productBean.setProductWeight(applyBean.getProductWeight());

        //#156
        productBean.setReserve8("在售常规");
        productBean.setReserve9("常规");

        //#316
        productBean.setStype(applyBean.getStype());

        //#480
        productBean.setVirtualFlag(applyBean.getVirtualFlag());

        Expression exp = new Expression(productBean, this);

        exp.check("#name &unique @productDAO", "名称已经存在");

        exp.check("#code &unique @productDAO", "编码已经存在");
        
        productDAO.saveEntityBean(productBean);
        
        List<ProductSubApplyBean> productSubApplyList = productSubApplyDAO.queryEntityBeansByFK(id);

        if (!ListTools.isEmptyOrNull(productSubApplyList) 
                && applyBean.getNature() == ProductApplyConstant.NATURE_COMPOSE)
        {
            for (ProductSubApplyBean each : productSubApplyList)
            {
                ProductCombinationBean cBean = new ProductCombinationBean();
                
                cBean.setVproductId(id);
                cBean.setSproductId(each.getSubProductId());
                cBean.setAmount(each.getSubProductAmount());
                
                productCombinationDAO.saveEntityBean(cBean);
            }
        }
        
        // 如果有关联产品，则自动增加到BOM表中
        if (!StringTools.isNullOrNone(productBean.getRefProductId())) {
        	ProductBOMBean bom = new ProductBOMBean();
        	
        	bom.setProductId(productBean.getRefProductId());
        	bom.setSubProductId(productBean.getId());
        	
        	productBOMDAO.saveEntityBean(bom);
        }
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectProductApply(User user, ProductApplyBean bean) throws MYException {

        JudgeTools.judgeParameterIsNull(user, bean);

        String id = bean.getId();

        ProductApplyBean pab = productApplyDAO.find(id);

        if (null == pab) {
            throw new MYException("数据错误，ID=" + id + "产品没有找到，请联系管理员");
        }

        int status = bean.getStatus();

        if (status == ProductApplyConstant.STATUS_FINISHED) {
            throw new MYException("结束状态不允许驳回");
        }

        productApplyDAO.modifyProductApplyStatus(id, ProductApplyConstant.STATUS_REJECT);

        // add log
        addLog(id, user, bean, "驳回", ProductApplyConstant.OPRMODE_REJECT,
                ProductApplyConstant.STATUS_REJECT);

        return true;
    }

    private String createId(ProductApplyBean bean){
        String id = commonDAO.getSquenceString();
        String appName = ConfigLoader.getProperty("appName");
        if (AppConstant.APP_NAME_TW.equals(appName)){
            id = "TW"+id;
        } else if(AppConstant.APP_NAME_ZJGH.equals(appName)){
            id = AppConstant.PREFIX_GH+id;
        }
        bean.setId(id);
        return id;
    }
    /**
     * 
     * @param bean
     */
    private void createCode(ProductApplyBean bean) {
    	
        String code = commonDAO.getSquenceString();

        String appName = ConfigLoader.getProperty("appName");
        if (AppConstant.APP_NAME_TW.equals(appName)){
            code = "TW"+code;
        } else if(AppConstant.APP_NAME_ZJGH.equals(appName)){
            code = AppConstant.PREFIX_GH+code;
        }

        bean.setCode(code);
    }

    /**
     * createSpell(更新简拼)
     * 
     * @param bean
     */
    private void createSpell(ProductApplyBean bean) {
        bean.setFullspell(JPTools.createFullSpell(bean.getFullName()));

        bean.setShortspell(JPTools.createShortSpell(bean.getFullName()));
    }

    private void addLog(final String fullId, final User user, final ProductApplyBean bean,
            String des, int mode, int astatus) {
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(des);
        log.setFullId(fullId);
        log.setOprMode(mode);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(bean.getStatus());

        log.setAfterStatus(astatus);

        flowLogDAO.saveEntityBean(log);
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean importProductApply(User user, List<ProductApplyBean> productApplyBeans) throws MYException {
        if (!ListTools.isEmptyOrNull(productApplyBeans)){
            _logger.info("*****importProductApply***"+productApplyBeans.size());
            for (ProductApplyBean bean : productApplyBeans){
                bean.setLogTime(TimeTools.now());
                JudgeTools.judgeParameterIsNull(user, bean);

                // 获取ID
                String id = this.createId(bean);

                checkUnique(bean);

                //直接审核通过
                createFullName(bean);
                createCode(bean);
                createSpell(bean);

                bean.setStatus(ProductApplyConstant.STATUS_FINISHED);
//                bean.setDescription(TimeTools.now_short()+"批量导入新产品申请,产品代码:" + bean.getCode());
                String postfix = TimeTools.now_short()+"批量导入新产品申请,产品代码:" + bean.getCode();
                //#646
                if (StringTools.isNullOrNone(bean.getDescription())){
                    bean.setDescription(postfix);
                } else{
                    bean.setDescription(bean.getDescription()+"."+postfix);
                }

                bean.setOprId(user.getId());

                _logger.info("***addProductApply***" + bean);
                productApplyDAO.saveEntityBean(bean);

                // 申请审批结束后产品转至正式商品表
                transferToProductBean(id, bean);

                // 增加数据库日志
                addLog(id, user, bean, "通过", ProductApplyConstant.OPRMODE_PASS,
                        ProductApplyConstant.STATUS_FINISHED);
            }
        } else{
            _logger.error("*****importProductApply empty****");
            throw new MYException("导入模板无法解析");
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean importProductForMailOut(User user, List<ProductImportBean> productImportBeans) throws MYException {
        _logger.info("***importProductForMailOut with size***"+productImportBeans.size());
        try{
            for(ProductImportBean bean : productImportBeans){
                ConditionParse conditionParse = new ConditionParse();
                conditionParse.addCondition("bank","=",bean.getBank());
                conditionParse.addCondition("branchName","=",bean.getBranchName());
                conditionParse.addCondition("customerName","=",bean.getCustomerName());
                conditionParse.addCondition("channel","=",bean.getChannel());
                conditionParse.addCondition("bankProductCode","=",bean.getBankProductCode());
                conditionParse.addCondition("onMarketDate","=",bean.getOnMarketDate());
                conditionParse.addCondition("offlineDate","=",bean.getOfflineDate());
                List<ProductImportBean> beans = this.productImportDAO.queryEntityBeansByCondition(conditionParse);
                if (ListTools.isEmptyOrNull(beans)){
                    String id = commonDAO.getSquenceString();
                    bean.setId(id);
                    _logger.info("***save product import bean***"+bean);
                    this.productImportDAO.saveEntityBean(bean);

                    StringBuilder sb = new StringBuilder();
                    sb.append("创建人:").append(user.getStafferName());
                    //日志
                    this.log(user, bean.getId(), OperationConstant.OPERATION_NEW,sb.toString());
                } else{
                    ProductImportBean bean2 = beans.get(0);
                    bean.setId(bean2.getId());
                    _logger.info("***update product import**"+bean);
                    this.productImportDAO.updateEntityBean(bean);

                    StringBuilder sb = new StringBuilder();
                    sb.append("修改人:").append(user.getStafferName())
                            .append(".原商品信息:").append(bean2.toString());
                    //日志
                    this.log(user, bean.getId(), OperationConstant.OPERATION_UPDATE,sb.toString());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            _logger.error(e);
            throw new MYException(e.getMessage());
        }

        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void log(User user, String id,String operation, String reason) {
        // 记录审批日志
        LogBean log = new LogBean();

        log.setFkId(id);

        log.setLocationId(user.getLocationId());
        log.setStafferId(user.getStafferId());
        log.setLogTime(TimeTools.now());
        log.setModule(ModuleConstant.MODULE_PRODUCT_IMPORT);
        log.setOperation(operation);
        log.setLog(reason);

        logDAO.saveEntityBean(log);
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void generateCodeJob() {
        _logger.info("***generateCodeJob running***");
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addIntCondition("codeGenerated","=",0);
        List<ProductApplyBean> productApplyBeans = this.productApplyDAO.queryEntityBeansByCondition(conditionParse);
        if (!ListTools.isEmptyOrNull(productApplyBeans)){
            for (ProductApplyBean bean: productApplyBeans){
                try {
                    createFullName(bean);
                    createCode(bean);
                    createSpell(bean);
                    bean.setCodeGenerated(1);
                    this.productApplyDAO.updateEntityBean(bean);
                }catch (MYException e){
                    _logger.error(e);
                }
            }
        }
        _logger.info("***generateCodeJob finished***");
    }

    public ProductApplyDAO getProductApplyDAO() {
        return productApplyDAO;
    }

    public void setProductApplyDAO(ProductApplyDAO productApplyDAO) {
        this.productApplyDAO = productApplyDAO;
    }

    public ProductSubApplyDAO getProductSubApplyDAO() {
        return productSubApplyDAO;
    }

    public void setProductSubApplyDAO(ProductSubApplyDAO productSubApplyDAO) {
        this.productSubApplyDAO = productSubApplyDAO;
    }

    public ProductVSStafferDAO getProductVSStafferDAO() {
        return productVSStafferDAO;
    }

    public void setProductVSStafferDAO(ProductVSStafferDAO productVSStafferDAO) {
        this.productVSStafferDAO = productVSStafferDAO;
    }

    public FlowLogDAO getFlowLogDAO() {
        return flowLogDAO;
    }

    public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
        this.flowLogDAO = flowLogDAO;
    }

    public CommonDAO getCommonDAO() {
        return commonDAO;
    }

    public void setCommonDAO(CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ProductCombinationDAO getProductCombinationDAO() {
        return productCombinationDAO;
    }

    public void setProductCombinationDAO(ProductCombinationDAO productCombinationDAO) {
        this.productCombinationDAO = productCombinationDAO;
    }

	/**
	 * @return the sequenceDAO
	 */
	public SequenceDAO getSequenceDAO()
	{
		return sequenceDAO;
	}

	/**
	 * @param sequenceDAO the sequenceDAO to set
	 */
	public void setSequenceDAO(SequenceDAO sequenceDAO)
	{
		this.sequenceDAO = sequenceDAO;
	}

	/**
	 * @return the productBOMDAO
	 */
	public ProductBOMDAO getProductBOMDAO() {
		return productBOMDAO;
	}

	/**
	 * @param productBOMDAO the productBOMDAO to set
	 */
	public void setProductBOMDAO(ProductBOMDAO productBOMDAO) {
		this.productBOMDAO = productBOMDAO;
	}

    public ProductImportDAO getProductImportDAO() {
        return productImportDAO;
    }

    public void setProductImportDAO(ProductImportDAO productImportDAO) {
        this.productImportDAO = productImportDAO;
    }

    public LogDAO getLogDAO() {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }
}
