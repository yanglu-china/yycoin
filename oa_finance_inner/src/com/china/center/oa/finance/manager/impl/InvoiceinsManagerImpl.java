/**
 * File Name: InvoiceinsManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.InsImportLogBean;
import com.china.center.oa.finance.bean.InsVSInvoiceNumBean;
import com.china.center.oa.finance.bean.InvoiceBindOutBean;
import com.china.center.oa.finance.bean.InvoiceStorageBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.bean.InvoiceinsImportBean;
import com.china.center.oa.finance.bean.InvoiceinsItemBean;
import com.china.center.oa.finance.bean.InvoiceinsTagBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.constant.InvoiceinsConstants;
import com.china.center.oa.finance.constant.StockPayApplyConstant;
import com.china.center.oa.finance.dao.InsImportLogDAO;
import com.china.center.oa.finance.dao.InsVSInvoiceNumDAO;
import com.china.center.oa.finance.dao.InsVSOutDAO;
import com.china.center.oa.finance.dao.InvoiceBindOutDAO;
import com.china.center.oa.finance.dao.InvoiceStorageDAO;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.dao.InvoiceinsDetailDAO;
import com.china.center.oa.finance.dao.InvoiceinsImportDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.dao.InvoiceinsTagDAO;
import com.china.center.oa.finance.dao.StockPayApplyDAO;
import com.china.center.oa.finance.listener.InvoiceinsListener;
import com.china.center.oa.finance.manager.InvoiceinsManager;
import com.china.center.oa.finance.manager.PackageManager;
import com.china.center.oa.finance.vo.InvoiceinsVO;
import com.china.center.oa.finance.vs.InsVSOutBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.NumberUtils;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.InvoiceConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutTransferBean;
import com.china.center.oa.sail.bean.PackageBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.oa.sail.bean.PreConsignBean;
import com.china.center.oa.sail.bean.TempConsignBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.constanst.ShipConstant;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.PackageDAO;
import com.china.center.oa.sail.dao.PackageItemDAO;
import com.china.center.oa.sail.dao.PreConsignDAO;
import com.china.center.oa.sail.dao.TempConsignDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * InvoiceinsManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InvoiceinsManagerImpl
 * @since 3.0
 */
@Exceptional
public class InvoiceinsManagerImpl extends AbstractListenerManager<InvoiceinsListener> implements InvoiceinsManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log operationLog = LogFactory.getLog("opr");

    private CommonDAO commonDAO = null;

    private InvoiceDAO invoiceDAO = null;

    private InvoiceinsDAO invoiceinsDAO = null;

    private InvoiceinsItemDAO invoiceinsItemDAO = null;

    private OutBalanceDAO outBalanceDAO = null;

    private InsVSOutDAO insVSOutDAO = null;

    private OutDAO outDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private BaseDAO baseDAO = null;

    private DutyDAO dutyDAO = null;

    private BaseBalanceDAO baseBalanceDAO = null;
    
    private InvoiceStorageDAO invoiceStorageDAO = null;
    
    private InvoiceBindOutDAO invoiceBindOutDAO = null;
    
    private StockPayApplyDAO stockPayApplyDAO = null;
    
    private InvoiceinsDetailDAO invoiceinsDetailDAO = null;
    
    private InsVSInvoiceNumDAO insVSInvoiceNumDAO = null;
    
    private InvoiceinsTagDAO invoiceinsTagDAO = null;
    
    private InvoiceinsImportDAO invoiceinsImportDAO = null;
    
    private InsImportLogDAO insImportLogDAO = null;
    
    private AttachmentDAO attachmentDAO = null;
    
    private ProductDAO productDAO = null;
    
    private DistributionDAO distributionDAO = null;
    
    private PreConsignDAO preConsignDAO = null;

	private TempConsignDAO tempConsignDAO = null;
    
    private PackageManager packageManager = null;
    
    private PlatformTransactionManager transactionManager = null;

    private ParameterDAO parameterDAO = null;

    private OutManager outManager = null;

    private PackageDAO packageDAO = null;

    private PackageItemDAO packageItemDAO = null;

    private StafferDAO stafferDAO = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.InvoiceinsManager#addInvoiceinsBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.finance.bean.InvoiceinsBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addInvoiceinsBean(User user, InvoiceinsBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        boolean update = true;
        
        String id = bean.getId();
        
        if (StringTools.isNullOrNone(id))
        {
        	update = false;
        	
        	id = commonDAO.getSquenceString20();
        }
        
        bean.setId(id);

        bean.setLogTime(TimeTools.now());
        bean.setStafferId(user.getStafferId());

        DutyBean duty = dutyDAO.find(bean.getDutyId());

        if (duty == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 再检查一次商品的管理属性与开票类型是否匹配
        checkProductAndInvoiceAttr(bean);
        
        // check 未发货状态销售单是否一次性开完发票
        for (InsVSOutBean insVSOutBean : bean.getVsList())
        {
            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
            {
            	// 针对未发货状态的开票申请，要一次性开完，不能部分开票
            	OutBean outBean = outDAO.find(insVSOutBean.getOutId());
            	
            	if (null != outBean) {
            		if (outBean.getStatus() != OutConstant.STATUS_PASS
            				&& outBean.getStatus() != OutConstant.STATUS_SEC_PASS) {
            			double ret = outDAO.sumOutBackValue(outBean.getFullId());
            			
            			if (!MathTools.equal(outBean.getTotal() - ret, outBean.getInvoiceMoney() + insVSOutBean.getMoneys())){
            				throw new MYException("销售单[%s]是未发货状态，须一次性开完发票.");
            			}
            		}
            	}
            }
        }
        
        // 对分公司的直接OK
        if (bean.getType() == FinanceConstant.INVOICEINS_TYPE_DUTY)
        {
            bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);

            if ( !bean.getInvoiceId().equals(InvoiceConstant.INVOICE_INSTACE_DK_17))
            {
                throw new MYException("发票只能是:增值专用发票(一般纳税人)[可抵扣](17.00%)");
            }
        }
        else
        {
            // 设置状态
            fillStatus(bean);
        }

        bean.setMtype(duty.getMtype());
        
        setDistFromDist(bean);

        if (update)
        {
        	invoiceinsDAO.updateEntityBean(bean);
        }else
        {
        	invoiceinsDAO.saveEntityBean(bean);
            //#328 2016/12/27
            InsVSInvoiceNumBean num = new InsVSInvoiceNumBean();

            num.setInsId(bean.getId());
            num.setMoneys(bean.getMoneys());
            //2016/3/4 导入时虚拟发票号不能再写入发票表了
//			num.setInvoiceNum(first.getInvoiceNum());

            //#328 生成临时发票号写入发票表，供CK单打印用
            String tempInvoiceNum = commonDAO.getSquenceString20("XN");;
            num.setInvoiceNum(tempInvoiceNum);

            _logger.info(bean.getId()+"生成临时发票号:"+tempInvoiceNum);

            insVSInvoiceNumDAO.saveEntityBean(num);
        }

        invoiceinsItemDAO.deleteEntityBeansByFK(id);
        _logger.info("***delete invoice ins item***"+id);
        insVSOutDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        List<InvoiceinsItemBean> itemList = bean.getItemList();

        for (InvoiceinsItemBean invoiceinsItemBean : itemList)
        {
            invoiceinsItemBean.setId(commonDAO.getSquenceString20());

            invoiceinsItemBean.setParentId(bean.getId());

			//#169 2016/3/31 手工开票，提交申请后直接设置开票状态
			handlerEachInAdd3(invoiceinsItemBean);
        }

        invoiceinsItemDAO.saveAllEntityBeans(itemList);

        List<InsVSOutBean> vsList = bean.getVsList();

        if ( !ListTools.isEmptyOrNull(vsList))
        {
            for (InsVSOutBean insVSOutBean : vsList)
            {
                insVSOutBean.setId(commonDAO.getSquenceString20());
                
                insVSOutBean.setInsId(bean.getId());
                
                if (insVSOutBean.getType() == 1)
                {
                	OutBalanceBean balanceBean = outBalanceDAO.find(insVSOutBean.getOutId());
                	
                	insVSOutBean.setOutId(balanceBean.getOutId());
                	insVSOutBean.setOutBalanceId(balanceBean.getId());
                }
            }

            insVSOutDAO.saveAllEntityBeans(vsList);
            
            processInvoicePay(duty, vsList, 0);
        }
        
        List<AttachmentBean> attachmentList = bean.getAttachmentList();

        for (AttachmentBean attachmentBean : attachmentList) {
            attachmentBean.setId(commonDAO.getSquenceString20());
            attachmentBean.setRefId(bean.getId());
        }

        attachmentDAO.saveAllEntityBeans(attachmentList);
        
        // 这里仅仅是提交,审核通过后才能修改单据的状态
        return true;
    }

    /**
     * fillType 1 地址同原销售单地址
     * @param bean
     */
	private void setDistFromDist(InvoiceinsBean bean) {
		DistributionBean dist = null;
		
		if (bean.getFillType() == 1) {
        	InvoiceinsItemBean item = bean.getItemList().get(0);
        	
        	String outId = item.getOutId();
        	
        	if (item.getType() == FinanceConstant.INSVSOUT_TYPE_BALANCE) {
        		OutBalanceBean balance = outBalanceDAO.find(item.getOutId());
        		
        		outId = balance.getOutId();
        	}
        	
        	List<DistributionBean> dList = distributionDAO.queryEntityBeansByFK(outId);
        	
        	dist = dList.get(0);
        	
        	bean.setShipping(dist.getShipping());
        	bean.setTransport1(dist.getTransport1());
        	bean.setTransport2(dist.getTransport2());
        	bean.setProvinceId(dist.getProvinceId());
        	bean.setCityId(dist.getCityId());
        	bean.setAreaId(dist.getAreaId());
        	bean.setAddress(dist.getAddress());
        	bean.setReceiver(dist.getReceiver());
        	bean.setMobile(dist.getMobile());
        	bean.setTelephone(dist.getTelephone());
        	bean.setExpressPay(dist.getExpressPay());
        	bean.setTransportPay(dist.getTransportPay());
        } else {
        	dist = new DistributionBean();
        	
        	BeanUtil.copyProperties(dist, bean);
        }
		
		dist.setId(commonDAO.getSquenceString20("PS"));
		dist.setOutId(bean.getId());
		dist.setDescription("发票配送");
		
		distributionDAO.deleteEntityBeansByFK(bean.getId());
		
		distributionDAO.saveEntityBean(dist);
	}

    /**
     *  // 普通+旧货=增值税普通发票（旧货）90000000000000000007
     *  // 普通+非旧货=增值税专用发票17 90000000000000000003
     *  // 普通+零税率=增值普通发票(0.00%) 90000000000000000004
     * @param bean
     * @throws MYException
     */
    private void checkProductAndInvoiceAttr(InvoiceinsBean bean) throws MYException {
    	String invoiceId = bean.getInvoiceId();
    	
    	List<InvoiceinsItemBean> itemList = bean.getItemList();
    	
    	for (InvoiceinsItemBean each : itemList) {
    	    String outId = each.getOutId();
    	    OutBean outBean = null;
    	    if (!StringTools.isNullOrNone(outId) && !outId.startsWith("A")){
    	        outBean = this.outDAO.find(outId);
            }
			checkProductAttrInner(invoiceId, each.getProductId(), outBean);
    	}
    }

    /**
     * 普通且是旧货的商品
     * @param productId
     * @return
     */
    private boolean isOldGoods(String productId){
        ProductBean product = productDAO.find(productId);
        if(product!=  null){
            String mtype = product.getReserve4();
            int oldgoods = product.getConsumeInDay();

            if ("1".equals(mtype) && oldgoods == ProductConstant.PRODUCT_OLDGOOD) {
                return true;
            }
        }
        return false;
    }

    private void checkTaxRate(OutBean outBean, double taxRate) throws MYException{
        String date = outBean.getChangeTime();
        if (StringTools.isNullOrNone(date)){
            date = outBean.getPodate();
        }
        _logger.info("***date***"+date+"****tax***"+taxRate);
        if (outBean.getStatus() == OutConstant.STATUS_PASS
                || outBean.getStatus() == OutConstant.STATUS_SEC_PASS){
            //2019年4月1号之后产生的订单，系统只允许开13税点的票
            if (date.compareTo("2019-04-01") >= 0 && !NumberUtils.equals(13,taxRate, 0.001)){
                throw new MYException("2019年4月1号之后产生的订单，系统只允许开13税点的票:"+outBean.getFullId());
            } else if (date.compareTo("2018-05-01") >= 0 && date.compareTo("2019-04-01") < 0
                    && !NumberUtils.equals(16,taxRate, 0.001)){
                //2018年5月1号之后2019年4月1号之前出库的订单，仅能开16的税率的票
                throw new MYException("2018年5月1号之后2019年4月1号之前出库的订单，仅能开16的税率的票:"+outBean.getFullId());
            } else if (date.compareTo("2018-05-01") <0  && !NumberUtils.equals(17,taxRate, 0.001)){
                //2018年5月1号之前出库的订单，仅能开17的税率的票
                throw new MYException("2018年5月1号之前出库的订单，仅能开17的税率的票:"+outBean.getFullId());
            }
        }
        //2019-12-05去掉
//        else{
//            if (!NumberUtils.equals(13,taxRate, 0.001)){
//                //#616 未出库的订单，都按13开
//                throw new MYException("未出库的订单，仅能开13的税率的票:"+outBean.getFullId());
//            }
//        }

    }

	private void checkProductAttrInner(String invoiceId, String productId, OutBean outBean)
			throws MYException {
		ProductBean product = productDAO.find(productId);
		
		if (null == product) {
			throw new MYException("产品不存在:"+productId);
		} else {
            //#863 混合产品不检查税率
            if(product.getName().indexOf("+") != -1){
                return;
            }
			String mtype = product.getReserve4();
			int oldgoods = product.getConsumeInDay();

			boolean isOldGoods = "1".equals(mtype) && oldgoods == ProductConstant.PRODUCT_OLDGOOD;

			if ( isOldGoods) {
			    //旧货
				if (!invoiceId.equals("90000000000000000007")) {
					throw new MYException("普通且是旧货的商品只能开具增值税普通发票（旧货）类型发票");
				}
			}

			//#614
/*			if ("1".equals(mtype) && oldgoods == ProductConstant.PRODUCT_OLDGOOD_YES) {
				if (!(invoiceId.equals("90000000000000000003") || "90000000000000000034".equals(invoiceId)
                        || "90000000000000000035".equals(invoiceId)
                        || "90000000000000000036".equals(invoiceId)
                        || "90000000000000000037".equals(invoiceId)
                        || "90000000000000000038".equals(invoiceId)
                        || "90000000000000000039".equals(invoiceId)
                        || "90000000000000000040".equals(invoiceId))) {
					throw new MYException("普通且是非旧货的商品 只能开具 增值税专用发票17 类型发票");
				}
			}*/
			
			else if ("1".equals(mtype) && oldgoods == ProductConstant.PRODUCT_OLDGOOD_ZERO) {
			    //普0
				if (!invoiceId.equals("90000000000000000004")) {
					throw new MYException("普通且是零税率的商品只能开具增值普通发票(0.00%) 类型发票");
				}
			}
//            else if ("1".equals(mtype) && oldgoods == ProductConstant.PRODUCT_OLDGOOD_ZERO) {
//			    //#616 TODO 普6
//                if (!invoiceId.equals("90000000000000000006")) {
//                    throw new MYException("普通且是零税率的商品只能开具增值普通发票(0.00%) 类型发票");
//                }
//            }
			else if (!isOldGoods && outBean!= null){
                InvoiceBean invoiceBean = this.invoiceDAO.find(invoiceId);
                if(invoiceBean!= null){
                    this.checkTaxRate(outBean, invoiceBean.getVal());
                }
            }
		}
	}
    
    /**
     * 
     * @param duty
     * @param vsList
     * @param type 0:打标记，1：取消标记
     */
	private void processInvoicePay(DutyBean duty, List<InsVSOutBean> vsList, int type)
	{
		Set<String> oset = new HashSet<String>();
		
		for (InsVSOutBean insVSOutBean : vsList)
		{
		//  原销售单打上开票标记
		    if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
		    {
		    	if (!oset.contains(insVSOutBean.getOutId()))
		    	{
		    		OutBean out = outDAO.find(insVSOutBean.getOutId());
		        	
		        	if (null != out)
		        	{
		        		if (type == 0) // 申请
		        		{
		        			if (StringTools.isNullOrNone(out.getPiDutyId()))
			        		{
			        			outDAO.updatePayInvoiceData(out.getFullId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, duty.getMtype(), duty.getId(), 0);
			        		}
		        		}
                        //#169 驳回时清除销售单开票状态及金额
                        else if (type == FinanceConstant.INVOICEINS_STATUS_REJECT){
                            if (out.getPiType() == OutConstant.OUT_PAYINS_TYPE_INVOICE && out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
                                outDAO.initPayInvoiceData(out.getFullId());
                            outDAO.updateInvoiceStatus(out.getFullId(), 0,OutConstant.INVOICESTATUS_INIT);
							//TODO 清除base表开票金额
							baseDAO.clearInvoice(out.getFullId());
                        }
		        		else if (type == 1) // 退票
		        		{
		        			if (out.getPiType() == OutConstant.OUT_PAYINS_TYPE_INVOICE && out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		        				outDAO.initPayInvoiceData(out.getFullId());
							//TODO 扣掉退票金额
		        		}else // 审批通过
		        		{
		        			if (out.getPiType() == OutConstant.OUT_PAYINS_TYPE_INVOICE && out.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		        				outDAO.updatePayInvoiceStatus(out.getFullId(), OutConstant.OUT_PAYINS_STATUS_FINISH);
		        		}
		        	}
		        	
		        	oset.add(insVSOutBean.getOutId());
		    	}
		    	
		    }else{
		    	if (!oset.contains(insVSOutBean.getOutBalanceId()))
		    	{
		    		OutBalanceBean outb = outBalanceDAO.find(insVSOutBean.getOutBalanceId());
		        	
		        	if (null != outb)
		        	{
		        		if (type == 0)
		        		{
		        			if (StringTools.isNullOrNone(outb.getPiDutyId()))
			        		{
			        			outBalanceDAO.updatePayInvoiceData(outb.getId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, duty.getMtype(), duty.getId(), 0);
			        		}
		        		}
		        		else if (type == 1)
		        		{
		        			if (outb.getPiType() == OutConstant.OUT_PAYINS_TYPE_INVOICE && outb.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		        				outBalanceDAO.initPayInvoiceData(outb.getId());
		        		}
		        		else
		        		{
		        			if (outb.getPiType() == OutConstant.OUT_PAYINS_TYPE_INVOICE && outb.getPiStatus() == OutConstant.OUT_PAYINS_STATUS_APPROVE)
		        				outBalanceDAO.updatePayInvoiceStatus(outb.getId(), OutConstant.OUT_PAYINS_STATUS_FINISH);
		        		}
		        	}
		        	
		        	oset.add(insVSOutBean.getOutBalanceId());
		    	}
		    }
		}
	}

    /**
     * fillStatus
     * 
     * @param bean
     */
    private void fillStatus(InvoiceinsBean bean)
    {
        // 全部到稽核
		//#169 流程变更，全部到待财务开票
        bean.setStatus(FinanceConstant.INVOICEINS_STATUS_SUBMIT);

        List<InsVSOutBean> vsList = bean.getVsList();

        if (ListTools.isEmptyOrNull(vsList))
        {
            return;
        }

        // 是否相同纳税属性
        boolean isEqualsMtype = true;

        // 是否都是A1
        boolean isAllCommon = (bean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON);

        // 这里需要判断是否是关注单据
        for (InsVSOutBean insVSOutBean : vsList)
        {
            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
            {
                OutBean out = outDAO.find(insVSOutBean.getOutId());

                if ( !out.getDutyId().equals(bean.getDutyId()))
                {
                    bean.setVtype(PublicConstant.VTYPE_SPECIAL);

                    bean.setStatus(FinanceConstant.INVOICEINS_STATUS_SUBMIT);

                    break;
                }
            }

            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_BALANCE)
            {
                OutBalanceBean ob = outBalanceDAO.find(insVSOutBean.getOutId());

                OutBean out = outDAO.find(ob.getOutId());

                if ( !out.getDutyId().equals(bean.getDutyId()))
                {
                    bean.setVtype(PublicConstant.VTYPE_SPECIAL);

                    bean.setStatus(FinanceConstant.INVOICEINS_STATUS_SUBMIT);

                    break;
                }
            }
        }

        Map<String, InvoiceinsItemBean> tmpMap = new HashMap<String, InvoiceinsItemBean>();

        for (InsVSOutBean insVSOutBean : vsList)
        {
            OutBean out = null;

            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
            {
                out = outDAO.find(insVSOutBean.getOutId());

            }
            else
            {
                OutBalanceBean ob = outBalanceDAO.find(insVSOutBean.getOutId());

                out = outDAO.find(ob.getOutId());
            }

            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(insVSOutBean.getOutId());

            for (BaseBean baseBean : baseList)
            {
                if ( !tmpMap.containsKey(baseBean.getShowId()))
                {
                    InvoiceinsItemBean item = new InvoiceinsItemBean();
                    item.setShowId(baseBean.getShowId());

                    tmpMap.put(baseBean.getShowId(), item);
                }

                InvoiceinsItemBean subItem = tmpMap.get(baseBean.getShowId());

                subItem.setAmount(subItem.getAmount() + baseBean.getAmount());
                subItem.setMoneys(subItem.getMoneys() + baseBean.getValue());
            }

            // 是否同一个纳税实体
            if (isEqualsMtype && out.getMtype() != bean.getMtype())
            {
                isEqualsMtype = false;
            }

            // 是否都是A1
            if (isAllCommon && (out.getMtype() != PublicConstant.MANAGER_TYPE_COMMON))
            {
                isAllCommon = false;
            }

            // 是否同一个纳税实体
            if (isEqualsMtype && out.getMtype() != bean.getMtype())
            {
                isEqualsMtype = false;
            }
        }

        // 设置特殊类型
        if (isAllCommon)
        {
            // 数量相同,但是价格不同
            List<InvoiceinsItemBean> itemList = bean.getItemList();

            for (InvoiceinsItemBean invoiceinsItemBean : itemList)
            {
                InvoiceinsItemBean compareItem = tmpMap.get(invoiceinsItemBean.getShowId());

                if (compareItem == null)
                {
                    bean.setStype(FinanceConstant.INVOICEINS_STYPE_A1A1_APD);

                    return;
                }

                if (compareItem.getAmount() != invoiceinsItemBean.getAmount()
                    && !MathTools.equal2(compareItem.getMoneys(), invoiceinsItemBean.getMoneys()))
                {
                    bean.setStype(FinanceConstant.INVOICEINS_STYPE_A1A1_APD);

                    return;
                }

                if (compareItem.getAmount() == invoiceinsItemBean.getAmount()
                    && !MathTools.equal2(compareItem.getMoneys(), invoiceinsItemBean.getMoneys()))
                {
                    bean.setStype(FinanceConstant.INVOICEINS_STYPE_A1A1_PD);

                    return;
                }
            }
        }
        else
        {
            if (bean.getMtype() != PublicConstant.MANAGER_TYPE_COMMON)
            {
                bean.setStype(FinanceConstant.INVOICEINS_STYPE_A1A2);
            }
            else
            {
                bean.setStype(FinanceConstant.INVOICEINS_STYPE_A2A1);
            }
        }
    }

    /**
     * 处理单据和发票实例
     * 
     * @param insVSOutBean
     * @throws MYException
     */
    private void handlerEachInAdd(InsVSOutBean insVSOutBean)
        throws MYException
    {
        if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
        {
            // 销售单
            OutBean out = outDAO.find(insVSOutBean.getOutId());

            if (out == null)
            {
                throw new MYException("数据错误,请确认操作");
            }
            
            if (MathTools.compare(insVSOutBean.getMoneys() + out.getInvoiceMoney(), out.getTotal()) > 0)
            {
                // TEMPLATE 数字格式化显示
                throw new MYException("单据[%s]开票溢出,开票金额[%.2f],销售金额[%.2f]", out.getFullId(),
                    (insVSOutBean.getMoneys() + out.getInvoiceMoney()), out.getTotal());
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + out.getInvoiceMoney(), out.getTotal()) == 0)            	
            {
                // 更新开票状态-结束
                outDAO.updateInvoiceStatus(out.getFullId(), out.getTotal(),
						OutConstant.INVOICESTATUS_END);
            }

            else if (MathTools.compare(insVSOutBean.getMoneys() + out.getInvoiceMoney(), out.getTotal()) < 0)
            {
                // 更新开票状态-过程
//                outDAO.updateInvoiceStatus(out.getFullId(), (insVSOutBean.getMoneys() + out
//                    .getInvoiceMoney()), OutConstant.INVOICESTATUS_INIT);
                outDAO.updateInvoiceStatus(out.getFullId(), (insVSOutBean.getMoneys() + out
                        .getInvoiceMoney()), OutConstant.INVOICESTATUS_PART);
            }
        }
        else
        {
            // 结算清单
            OutBalanceBean balance = outBalanceDAO.find(insVSOutBean.getOutBalanceId());

            if (balance == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + balance.getInvoiceMoney(), balance.getTotal()) > 0)
            {
                // TEMPLATE 数字格式化显示
                throw new MYException("委托结算单[%s]开票溢出,开票金额[%.2f],销售金额[%.2f]", balance.getId(),
                    (insVSOutBean.getMoneys() + balance.getInvoiceMoney()), balance.getTotal());
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + balance.getInvoiceMoney(), balance.getTotal()) == 0)
            {
                // 更新开票状态-结束
                outBalanceDAO.updateInvoiceStatus(balance.getId(), balance.getTotal(),
                    OutConstant.INVOICESTATUS_END);
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + balance.getInvoiceMoney(), balance.getTotal()) < 0)
            {
                // 更新开票状态-过程
//                outBalanceDAO.updateInvoiceStatus(balance.getId(),
//						(insVSOutBean.getMoneys() + balance.getInvoiceMoney()),
//						OutConstant.INVOICESTATUS_INIT);
                outBalanceDAO.updateInvoiceStatus(balance.getId(),
                        (insVSOutBean.getMoneys() + balance.getInvoiceMoney()),
                        OutConstant.INVOICESTATUS_PART);
            }
        }
    }

    private void handlerEachInAdd2(InsVSOutBean insVSOutBean)
        throws MYException
    {
        if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
        {
            // 销售单
            OutBean out = outDAO.find(insVSOutBean.getOutId());

            if (out == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            BaseBean base = baseDAO.find(insVSOutBean.getBaseId());

            // 溢出的
            if (MathTools.compare(insVSOutBean.getMoneys() + base.getInvoiceMoney(), base
                .getValue()) > 0)
            {
                throw new MYException("单据[%s]开票溢出,开票金额[%.2f],销售项金额[%.2f]", out.getFullId(),
                    (insVSOutBean.getMoneys() + base.getInvoiceMoney()), base.getValue());
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + base.getInvoiceMoney(), base
                .getValue()) <= 0)
            {
                baseDAO.updateInvoice(base.getId(), (insVSOutBean.getMoneys() + base
						.getInvoiceMoney()));
            }

            // 更新主单据
            updateOut(out);
        }
        else
        {
            // 结算清单
            OutBalanceBean balance = outBalanceDAO.find(insVSOutBean.getOutId());

            if (balance == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            BaseBalanceBean bbb = baseBalanceDAO.find(insVSOutBean.getBaseId());

            double baseTotal = bbb.getAmount() * bbb.getSailPrice();

            if (MathTools.compare(insVSOutBean.getMoneys() + bbb.getInvoiceMoney(), baseTotal) > 0)
            {
                throw new MYException("委托结算单项[%s]开票溢出,开票金额[%.2f],销售金额[%.2f]", balance.getId(),
                    (insVSOutBean.getMoneys() + bbb.getInvoiceMoney()), baseTotal);
            }

            if (MathTools.compare(insVSOutBean.getMoneys() + bbb.getInvoiceMoney(), baseTotal) <= 0)
            {
                baseBalanceDAO.updateInvoice(bbb.getId(), (insVSOutBean.getMoneys() + bbb
						.getInvoiceMoney()));
            }

            updateOutBalance(balance);
        }
    }

	/**
	 * 更新单据开票状态和金额
	 * @param item
	 * @throws MYException
	 */
    private void handlerEachInAdd3(InvoiceinsItemBean item)
    throws MYException
	{
        _logger.info("***handlerEachInAdd3***"+item);
	    if (item.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
	    {
	        // 销售单
	        OutBean out = outDAO.find(item.getOutId());
	
	        if (out == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }

	        String[] baseIds = item.getBaseId().split(",");
//	        double totalInvoiceMoney = 0;
//	        double totalValue = 0;
//	        for (String baseId: baseIds){
//                BaseBean base = baseDAO.find(baseId);
//                _logger.info("***find base***"+base);
//                totalInvoiceMoney += base.getInvoiceMoney();
//                totalValue += base.getValue();
//            }

//            if (MathTools.compare(item.getMoneys() + totalInvoiceMoney, totalValue) > 0)
//            {
//                throw new MYException("单据[%s]开票溢出,开票金额[%.2f],销售项金额[%.2f]", out.getFullId(),
//                        (item.getMoneys() + totalInvoiceMoney), totalValue);
//            }

            // #732 把本次开票金额分摊到base表中
            for (String baseId: baseIds){
                BaseBean base = baseDAO.find(baseId);
                _logger.info("***find base***"+base);
                //本次开票金额+已开票金额(累计开票金额)
                double value = item.getMoneys() + base.getInvoiceMoney();
                // 累计开票金额 >= base表中金额,直接取base表金额,否则取累计开票金额(因为有部分开票的情况!)
                double invoiceMoney = (value >= base.getValue() ? base.getValue(): value);
                baseDAO.updateInvoice(base.getId(), invoiceMoney);
            }
//            if (MathTools.compare(item.getMoneys() + base.getInvoiceMoney(), base
//                    .getValue()) <= 0)
//            {
//                baseDAO.updateInvoice(base.getId(), (item.getMoneys() + base
//                        .getInvoiceMoney()));
//            }

	        // TODO 溢出的
//	        if (MathTools.compare(item.getMoneys() + base.getInvoiceMoney(), base
//	            .getValue()) > 0)
//	        {
//	            throw new MYException("单据[%s]开票溢出,开票金额[%.2f],销售项金额[%.2f]", out.getFullId(),
//	                (item.getMoneys() + base.getInvoiceMoney()), base.getValue());
//	        }
	
//	        if (MathTools.compare(item.getMoneys() + base.getInvoiceMoney(), base
//	            .getValue()) <= 0)
//	        {
//	            baseDAO.updateInvoice(base.getId(), (item.getMoneys() + base
//						.getInvoiceMoney()));
//	        }
	
	        // 更新主单据
	        updateOut(out);
	    }
	    else
	    {
	        // 结算清单
	        OutBalanceBean balance = outBalanceDAO.find(item.getOutId());
	
	        if (balance == null)
	        {
	            throw new MYException("数据错误,请确认操作");
	        }
	
	        BaseBalanceBean bbb = baseBalanceDAO.find(item.getBaseId());
	
	        double baseTotal = bbb.getAmount() * bbb.getSailPrice();
	
	        if (MathTools.compare(item.getMoneys() + bbb.getInvoiceMoney(), baseTotal) > 0)
	        {
	            throw new MYException("委托结算单项[%s]开票溢出,开票金额[%.2f],销售金额[%.2f]", balance.getId(),
	                (item.getMoneys() + bbb.getInvoiceMoney()), baseTotal);
	        }
	
	        if (MathTools.compare(item.getMoneys() + bbb.getInvoiceMoney(), baseTotal) <= 0)
	        {
	            baseBalanceDAO.updateInvoice(bbb.getId(), (item.getMoneys() + bbb
						.getInvoiceMoney()));
	        }
	
	        updateOutBalance(balance);
	    }
	}
    
    /**
     * 更新销售单的开票状态
     * 
     * @param out
     */
    private void updateOut(OutBean out)
    {
		String outId = out.getFullId();
        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);

        double total = 0.0d;

        for (BaseBean baseBean : baseList)
        {
            total += baseBean.getInvoiceMoney();
        }

		_logger.info(outId+"**total**"+total+"***getTotal()***"+out.getTotal());
        // 全部开票
        if (MathTools.compare(total, out.getTotal()) >= 0)
        {
            // 更新开票状态-结束
            outDAO.updateInvoiceStatus(out.getFullId(), out.getTotal(),
					OutConstant.INVOICESTATUS_END);
        }
        else
        {
            // 更新开票状态-过程
            if (total == 0.0d){
                outDAO.updateInvoiceStatus(out.getFullId(), total, OutConstant.INVOICESTATUS_INIT);
            } else{
                outDAO.updateInvoiceStatus(out.getFullId(), total, OutConstant.INVOICESTATUS_PART);
            }
        }
    }

    /**
     * updateOutBalance
     * 
     * @param balance
     */
    private void updateOutBalance(OutBalanceBean balance)
    {
        List<OutBalanceBean> baseList = outBalanceDAO.queryEntityBeansByFK(balance.getId());

        double total = 0.0d;

        for (OutBalanceBean baseBean : baseList)
        {
            total += baseBean.getInvoiceMoney();
        }

        // 全部开票
        if (MathTools.compare(total, balance.getTotal()) >= 0)
        {
            // 更新开票状态-结束
            outBalanceDAO.updateInvoiceStatus(balance.getId(), balance.getTotal(),
					OutConstant.INVOICESTATUS_END);
        }
        else
        {
            // 更新开票状态-过程
            outBalanceDAO.updateInvoiceStatus(balance.getId(), total,
					OutConstant.INVOICESTATUS_INIT);
        }
    }

    @Transactional(rollbackFor = MYException.class)
    public void clearRejectInvoiceinsBean()
        throws MYException
    {
        ConditionParse conditionParse = new ConditionParse();

        conditionParse.addWhereStr();

        // 驳回
        conditionParse.addIntCondition("status", "=", FinanceConstant.INVOICEINS_STATUS_REJECT);

        conditionParse.addCondition("logTime", "<=", TimeTools.now(-60));

        List<InvoiceinsBean> beanList = invoiceinsDAO.queryEntityBeansByCondition(conditionParse
            .toString());

        for (InvoiceinsBean invoiceinsBean : beanList)
        {
            realDelete(invoiceinsBean.getId());

            operationLog.info("clearRejectInvoiceinsBean:" + invoiceinsBean);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.finance.manager.InvoiceinsManager#deleteInvoiceinsBean(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteInvoiceinsBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InvoiceinsBean bean = invoiceinsDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getStafferId().equals(user.getStafferId())
            && !bean.getProcesser().equals(user.getStafferId()))
        {
            throw new MYException("只能删除自己的发票或者是自己审批的,请确认操作");
        }

        List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(id, AnoConstant.FK_FIRST);

        realDelete(id);

        if (bean.getStatus() != FinanceConstant.INVOICEINS_STATUS_END)
        {
            return true;
        }

        if (ListTools.isEmptyOrNull(vsList))
        {
            return true;
        }

        // 倒回开票状态
        for (InsVSOutBean insVSOutBean : vsList)
        {
            if (insVSOutBean.getType() == FinanceConstant.INSVSOUT_TYPE_OUT)
            {
                // 销售单
                OutBean out = outDAO.find(insVSOutBean.getOutId());

                if (out == null)
                {
                    continue;
                }

                double im = Math.max(0.0, out.getInvoiceMoney() - insVSOutBean.getMoneys());

                // 更新单据的开票金额
                outDAO.updateInvoiceStatus(out.getFullId(), im, OutConstant.INVOICESTATUS_INIT);
            }
            else
            {
                // 结算清单
                OutBalanceBean balance = outBalanceDAO.find(insVSOutBean.getOutId());

                if (balance == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                double im = Math.max(0.0, balance.getInvoiceMoney() - insVSOutBean.getMoneys());

                // 更新开票状态-过程
                outBalanceDAO.updateInvoiceStatus(balance.getId(), im,
                    OutConstant.INVOICESTATUS_INIT);
            }
        }

        return true;
    }

    /**
     * 清除发票
     * 
     * @param id
     */
    private void realDelete(String id)
    {
        invoiceinsDAO.deleteEntityBean(id);

        invoiceinsItemDAO.deleteEntityBeansByFK(id);
        _logger.info("***realDelete invoice ins item***"+id);

        insVSOutDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        invoiceinsDetailDAO.deleteEntityBeansByFK(id);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean passInvoiceinsBean(User user, InvoiceinsBean bean, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getId());

        InvoiceinsBean obean = invoiceinsDAO.find(bean.getId());

        if (obean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (obean.getStatus() != FinanceConstant.INVOICEINS_STATUS_SUBMIT)
        {
            throw new MYException("数据错误,请确认操作");
        }

        int status = obean.getStatus();

        // 开票成功
        //bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);
        if (obean.getOtype() == FinanceConstant.INVOICEINS_TYPE_OUT){
	        // 开票成功 - 待确认
        	obean.setStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        	
        	saveForFinanceTag(bean.getId());
        }
        else
        	obean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);

        invoiceinsDAO.updateEntityBean(obean);

        List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(obean.getId(), AnoConstant.FK_FIRST);
        
        List<InvoiceinsItemBean> itemList = invoiceinsItemDAO.queryEntityBeansByFK(obean.getId());

        // 单据的开票状态需要更新
        if ( !ListTools.isEmptyOrNull(vsList))
        {
        	if (StringTools.isNullOrNone(vsList.get(0).getBaseId()))
        	{
        		for (InsVSOutBean insVSOutBean : vsList)
                {
        			handlerEachInAdd(insVSOutBean);
                }
        	}
        	// 0新模式标记 @@see InvoiceinsAction.createInsInNavigation1
        	else if (vsList.get(0).getBaseId().equals("0")){
        		
        		for (InvoiceinsItemBean item : itemList)
                {
        			 // 新的开单规则
                    //如果是退票，通过时需要更新开票状态.对于开票，根据新流程，已在导入时就更新过开票状态
					if (obean.getOtype() == FinanceConstant.INVOICEINS_TYPE_IN) {
                    	handlerEachInAdd3(item);
					}
                }
        	}else{
        		for (InsVSOutBean insVSOutBean : vsList)
                {
        			handlerEachInAdd2(insVSOutBean);
                }
        	}
        }
        
        if (bean.getOtype() == FinanceConstant.INVOICEINS_TYPE_IN)
        {
        	// 删除原单关联的销售单数据
    		List<InsVSOutBean> vsrList = insVSOutDAO.queryEntityVOsByFK(bean.getRefId(), AnoConstant.FK_FIRST);
    		
    		// 处理票款纳税实体一致  取消开票时打的标记
    		processInvoicePay(null, vsrList , 1);
    		
    		for (InsVSOutBean each : vsrList)
    		{
    			each.setOutId("");
    			each.setOutBalanceId("");
    			each.setBaseId("");
    			
    			insVSOutDAO.updateEntityBean(each);
    		}
    		
    		List<InvoiceinsItemBean> ritemList = invoiceinsItemDAO.queryEntityBeansByFK(bean.getRefId());
    		
    		for (InvoiceinsItemBean each : ritemList)
    		{
    			each.setOutId("");
    			each.setBaseId("");
    			
    			invoiceinsItemDAO.updateEntityBean(each);
    		}
    			
        	// 产生凭证
//        	for (InvoiceinsListener each : this.listenerMapValues())
//        	{
//        		each.onConfirmPay(user, bean);
//        	}
        }else{
        	// 处理票款纳税实体一致  结束开票时打的标记
        	processInvoicePay(null, vsList, 2);
        }

        List<InsVSInvoiceNumBean> numList = bean.getNumList();
        
        for (InsVSInvoiceNumBean each : numList)
        {
        	each.setInsId(obean.getId());
        }
        
        insVSInvoiceNumDAO.deleteEntityBeansByFK(obean.getId());
        
        insVSInvoiceNumDAO.saveAllEntityBeans(numList);
        
        // 准备发票打包数据
        createPackage(obean);

		if (obean.getOtype() == FinanceConstant.INVOICEINS_TYPE_IN) {
			FlowLogBean log = new FlowLogBean();

			log.setActor(user.getStafferName());
			log.setActorId(user.getStafferId());
			log.setFullId(obean.getId());
			log.setDescription(reason);
			log.setLogTime(TimeTools.now());
			log.setPreStatus(status);
			log.setAfterStatus(obean.getStatus());
			log.setOprMode(PublicConstant.OPRMODE_PASS);

			flowLogDAO.saveEntityBean(log);
		} else{
			FlowLogBean log = new FlowLogBean();

			log.setActor(user.getStafferName());
			log.setActorId(user.getStafferId());
			log.setFullId(obean.getId());
			log.setDescription(reason);
			log.setLogTime(TimeTools.now());
			log.setPreStatus(status);
			log.setAfterStatus(bean.getStatus());
			log.setOprMode(PublicConstant.OPRMODE_PASS);

			flowLogDAO.saveEntityBean(log);
		}


        return true;
    }
    
    private void createPackage(final InvoiceinsBean bean) throws MYException
	{
        _logger.info(bean.getId() + "*****createPackage******" + bean.getInvoiceFollowOut());
    	if (bean.getOtype() == FinanceConstant.INVOICEINS_TYPE_IN) {
    		return;
    	} else if (bean.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
    		return;
    	}

    	if (bean.getShipping() == OutConstant.OUT_SHIPPING_SELFSERVICE)
		{
			 PreConsignBean preConsign = new PreConsignBean();
             
             preConsign.setOutId(bean.getId());
             
             packageManager.createInsPackage(preConsign, bean.getId());
		}else
		{
            PreConsignBean preConsign = new PreConsignBean();
            
            preConsign.setOutId(bean.getId());
            
            preConsignDAO.saveEntityBean(preConsign);
            this.logPreconsign(preConsign);
		}
	}

    private void logPreconsign(PreConsignBean preConsignBean){
        String message = String.format("生成preconsign表:%s", preConsignBean.getOutId());
        _logger.info(message);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean checkInvoiceinsBean(User user, String id, String reason)
        throws MYException
    {
		_logger.info("****begin checkInvoiceinsBean***");
        JudgeTools.judgeParameterIsNull(user, id);

        InvoiceinsBean obean = invoiceinsDAO.find(id);

        if (obean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

//        if (obean.getStatus() != FinanceConstant.INVOICEINS_STATUS_CHECK)
//        {
//            throw new MYException("数据错误,请确认操作");
//        }

        int status = obean.getStatus();

        // 财务审核
        obean.setStatus(FinanceConstant.INVOICEINS_STATUS_SUBMIT);

        invoiceinsDAO.updateEntityBean(obean);
        
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());
        log.setActorId(user.getStafferId());
        log.setFullId(obean.getId());
        log.setDescription(reason);
        log.setLogTime(TimeTools.now());
        log.setPreStatus(status);
        log.setAfterStatus(obean.getStatus());
        log.setOprMode(PublicConstant.OPRMODE_PASS);

        flowLogDAO.saveEntityBean(log);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rejectInvoiceinsBean(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InvoiceinsBean bean = invoiceinsDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() == FinanceConstant.INVOICEINS_STATUS_END)
        {
            throw new MYException("数据错误,请确认操作");
        }

        int status = bean.getStatus();

        // 驳回
        bean.setStatus(FinanceConstant.INVOICEINS_STATUS_REJECT);

        invoiceinsDAO.updateEntityBean(bean);

        List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        // 删除对应关系
        //insVSOutDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
        
        // 开票打的标记清除
        processInvoicePay(null, vsList, FinanceConstant.INVOICEINS_STATUS_REJECT);

        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());
        log.setActorId(user.getStafferId());
        log.setFullId(id);
        log.setDescription(reason);
        log.setLogTime(TimeTools.now());
        log.setPreStatus(status);
        log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_REJECT);
        log.setOprMode(PublicConstant.OPRMODE_REJECT);

        flowLogDAO.saveEntityBean(log);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean checkInvoiceinsBean2(User user, String id, String checks, String refId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        InvoiceinsBean bean = invoiceinsDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setCheckStatus(PublicConstant.CHECK_STATUS_END);
        bean.setChecks(checks + " [" + TimeTools.now() + ']');
        bean.setCheckrefId(refId);

        invoiceinsDAO.updateEntityBean(bean);

        return true;
    }

    public InvoiceinsVO findVO(String id)
    {
        InvoiceinsVO vo = invoiceinsDAO.findVO(id);

        if (vo == null)
        {
            return null;
        }

        vo.setItemList(invoiceinsItemDAO.queryEntityBeansByFK(id));

        vo.setVsList(insVSOutDAO.queryEntityBeansByFK(id, AnoConstant.FK_FIRST));
        
        vo.setDetailList(invoiceinsDetailDAO.queryEntityBeansByFK(id));
        
        vo.setNumList(insVSInvoiceNumDAO.queryEntityBeansByFK(id));
        
        List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(id);

        vo.setAttachmentList(attachmentList);

        return vo;
    }
    
    /**
     * 确认开票
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = MYException.class)
	public boolean confirmInvoice(User user, String id) throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, id);
    	
    	InvoiceinsBean bean = invoiceinsDAO.find(id);
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	if (bean.getStatus() != FinanceConstant.INVOICEINS_STATUS_CONFIRM)
    	{
    		throw new MYException("数据错误，状态不是待确认状态");
    	}
    	
    	bean.setInvoiceConfirmStatus(FinanceConstant.INVOICEINS_CONFIRM_STATUS_YES);
    	
    	if (bean.getPayConfirmStatus() == FinanceConstant.INVOICEINS_PAY_CONFIRM_STATUS_YES)
    	{
    		bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);
    	}
		
		invoiceinsDAO.updateEntityBean(bean);
		
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());
        log.setActorId(user.getStafferId());
        log.setFullId(id);
        log.setDescription("OK");
        log.setLogTime(TimeTools.now());
        log.setPreStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        if (bean.getStatus() == FinanceConstant.INVOICEINS_STATUS_END)
        {
        	log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_END);
        }
        else
        {
        	log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        }
        
        log.setOprMode(PublicConstant.OPRMODE_PASS);

        flowLogDAO.saveEntityBean(log);
		
		return true;
	}
    
    private void saveForFinanceTag(String id)
    {
    	InvoiceinsTagBean tagBean = new InvoiceinsTagBean();
    	
    	tagBean.setInsId(id);
    	
    	invoiceinsTagDAO.saveEntityBean(tagBean);
    }

	/**
	 * 确认付款
	 */
    @Transactional(rollbackFor = MYException.class)
	public boolean confirmPay(User user, String id) throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, id);
    	
    	InvoiceinsBean bean = invoiceinsDAO.find(id);
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	if (bean.getStatus() != FinanceConstant.INVOICEINS_STATUS_CONFIRM)
    	{
    		throw new MYException("数据错误，状态不是待确认状态");
    	}
    	
    	bean.setPayConfirmStatus(FinanceConstant.INVOICEINS_PAY_CONFIRM_STATUS_YES);
    	
    	if (bean.getInvoiceConfirmStatus() == FinanceConstant.INVOICEINS_CONFIRM_STATUS_YES)
    	{
    		bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);
    	}
		
		invoiceinsDAO.updateEntityBean(bean);
		
		List<InvoiceinsItemBean> itemList = invoiceinsItemDAO.queryEntityBeansByFK(id);
		
		bean.setItemList(itemList);
		
		// 产生凭证 借:销售费用_业务员开票税金   贷:主营业务税金及附加
//		for (InvoiceinsListener each : this.listenerMapValues())
//		{
//			each.onConfirmPay(user, bean);
//		}
		
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());
        log.setActorId(user.getStafferId());
        log.setFullId(id);
        log.setDescription("OK");
        log.setLogTime(TimeTools.now());
        log.setPreStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        if (bean.getStatus() == FinanceConstant.INVOICEINS_STATUS_END)
        {
        	log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_END);
        }
        else
        {
        	log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
        }
        
        log.setOprMode(PublicConstant.OPRMODE_PASS);

        flowLogDAO.saveEntityBean(log);
		
		return true;
	}
    
    @Transactional(rollbackFor = MYException.class)
	public boolean backInvoiceins(User user, String id) throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, id);
    	
    	InvoiceinsBean bean = invoiceinsDAO.find(id);
    	
    	this.backInvoiceins(user, bean, false);
    	
    	return true;
	}
    
    @Transactional(rollbackFor = MYException.class)
    public int backInvoiceins(User user, List<String> ids) throws MYException{
    	int effects = 0;
    	for(String id : ids){
    		InvoiceinsBean bean = invoiceinsDAO.find(id);
    		
    		boolean flag = this.backInvoiceins(user, bean, true);
    		if(flag){
    			effects++;
    		}
    	}
    	return effects;
    }
    
    private boolean backInvoiceins(User user, InvoiceinsBean bean, boolean fromBatch) throws MYException{
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	String id = bean.getId();
    	
    	if (bean.getStatus() != FinanceConstant.INVOICEINS_STATUS_END)
    	{
    		throw new MYException("数据错误，状态不是待确认状态");
    	}
    	
    	if (bean.getOtype() != FinanceConstant.INVOICEINS_TYPE_OUT)
    	{
    		throw new MYException("数据错误，不是开票数据");
    	}
    	
    	List<InvoiceinsBean> beanList = invoiceinsDAO.queryEntityBeansByFK(id);
    	
    	if (beanList.size() > 0)
    	{
    		throw new MYException("数据错误，开票已发生退票");
    	}
    	
    	// 退票时检查开票日期必须在当前日期前三个月内
    	/*String logDate = TimeTools.changeTimeToDate(bean.getLogTime());
    	
    	if (TimeTools.cdate(TimeTools.now_short(-90), logDate) > 0) {
    		throw new MYException("开票日期必须在当前日期前三个月内才能退票");
    	}*/
    	
    	InvoiceinsBean newBean = new InvoiceinsBean();
    	
    	BeanUtil.copyProperties(newBean, bean);
    	
    	newBean.setOtype(FinanceConstant.INVOICEINS_TYPE_IN);
    	
    	newBean.setMoneys(-bean.getMoneys());
    	
    	newBean.setStatus(FinanceConstant.INVOICEINS_STATUS_SUBMIT);
    	
    	String newId = commonDAO.getSquenceString20();
    	
    	newBean.setId(newId);

    	newBean.setLogTime(TimeTools.now());
    	
    	if(fromBatch){
    		newBean.setDescription("批量导入退票申请");
    	}else{
    		newBean.setDescription("退票，原票：" + bean.getId());
    	}
    	
    	newBean.setRefId(bean.getId());
    	
    	newBean.setStafferId(user.getStafferId());
    	
    	boolean flag = invoiceinsDAO.saveEntityBean(newBean);
    	
    	_logger.debug("save invoiceins: "+ flag);
    	
    	List<InvoiceinsItemBean> itemList = invoiceinsItemDAO.queryEntityBeansByFK(id);
    	
    	for (InvoiceinsItemBean each : itemList)
    	{
    		InvoiceinsItemBean newItem = new InvoiceinsItemBean();
    		
    		BeanUtil.copyProperties(newItem, each);
    		
    		// 检查baseId
    		checkBaseId(each);
    		
    		newItem.setId(commonDAO.getSquenceString20());
    		
    		newItem.setParentId(newId);
    		
    		newItem.setAmount(-each.getAmount());
    		
    		newItem.setMoneys(newItem.getAmount() * newItem.getPrice());
    		
    		flag = invoiceinsItemDAO.saveEntityBean(newItem);
    		
    		_logger.debug("save InvoiceinsItemBean: "+ flag);
    	}
    	
    	List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(id, AnoConstant.FK_FIRST);
    	
    	for (InsVSOutBean each : vsList)
    	{
    		InsVSOutBean newItem = new InsVSOutBean();
    		
    		BeanUtil.copyProperties(newItem, each);
    		
    		newItem.setId(commonDAO.getSquenceString20());
    		
    		newItem.setInsId(newId);
    		
    		newItem.setMoneys(-each.getMoneys());
    		
    		flag = insVSOutDAO.saveEntityBean(newItem);
    		
    		_logger.debug("save InsVSOutBean: "+ flag);
    	}
    	
    	return true;
    }
    
    private void checkBaseId(InvoiceinsItemBean item) throws MYException
    {
    	if (item.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
    		if (!StringTools.isNullOrNone(item.getBaseId())) {
        		BaseBean base = baseDAO.find(item.getBaseId());
        		
        		if (null == base) {
        			throw new MYException("销售单[%s]中的行项目在开票后发生修改过，不能退票.", item.getOutId());
        		}
        	}
    	}
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
	public boolean importInvoice(User user, List<InvoiceStorageBean> list)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user,list);
    	
    	for (InvoiceStorageBean each : list)
    	{
    		each.setOprDate(TimeTools.now_short());
    		each.setOprName(user.getStafferName());
    		each.setLogTime(TimeTools.now());
    		
    		each.setId(commonDAO.getSquenceString20());
    	}
    	
    	invoiceStorageDAO.saveAllEntityBeans(list);
    	
		return true;
	}
    
    @Override
    @Transactional(rollbackFor = MYException.class)
	public boolean refConfirmInvoice(User user, List<InvoiceBindOutBean> vsList)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, vsList);
    	
    	String invoiceId = vsList.get(0).getInvoiceStorageId();
    	
    	String providerId = vsList.get(0).getProviderId();
    	
    	ConditionParse con = new ConditionParse();
    	
    	con.addWhereStr();
    	
    	con.addCondition("InvoiceStorageBean.stafferId", "=", user.getStafferId());
    	
    	con.addCondition("InvoiceStorageBean.invoiceId", "=", invoiceId);
    	
    	con.addCondition("InvoiceStorageBean.providerId", "=", providerId);
    	
    	con.addCondition(" and InvoiceStorageBean.moneys > InvoiceStorageBean.hasConfirmMoneys");

    	List<InvoiceStorageBean> list = invoiceStorageDAO.queryEntityBeansByCondition(con);
    	
    	List<InvoiceBindOutBean> bindList = new ArrayList<InvoiceBindOutBean>();
    	
    	for (InvoiceStorageBean each : list)
    	{
    		double canUse = each.getMoneys() - each.getHasConfirmMoneys();
    		
    		for (InvoiceBindOutBean eachb : vsList)
        	{
    			if (eachb.getTemp() == 1)
    				continue;

    			double needConfirm = eachb.getConfirmMoney() - eachb.getTempMoney();
    			
    			if (canUse >= needConfirm)
    			{
    				canUse -= needConfirm;
    				
    				eachb.setTempMoney(needConfirm);
    				
    				eachb.setTemp(1); // 表示单据完且确认
    				
    				InvoiceBindOutBean bind = new InvoiceBindOutBean();
    				
    				bind.setInvoiceStorageId(each.getId());
    				bind.setConfirmMoney(needConfirm);
    				bind.setFullId(eachb.getFullId());
    				bind.setOuttype(eachb.getOuttype());
    				bind.setProviderId(eachb.getProviderId());
    				bind.setLogTime(eachb.getLogTime());
    				
    				bindList.add(bind);
    				
    				if (canUse == 0)
    					break;
    				
    			}else{
    				eachb.setTempMoney(eachb.getTempMoney() + canUse);
    				
    				eachb.setTemp(0);
    				
    				InvoiceBindOutBean bind = new InvoiceBindOutBean();
    				
    				bind.setInvoiceStorageId(each.getId());
    				bind.setConfirmMoney(canUse);
    				bind.setFullId(eachb.getFullId());
    				bind.setOuttype(eachb.getOuttype());
    				bind.setProviderId(eachb.getProviderId());
    				bind.setLogTime(eachb.getLogTime());
    				
    				bindList.add(bind);
    				
    				canUse = 0;
    				
    				break;
    			}
        	}
    		
    		each.setHasConfirmMoneys(each.getMoneys() - canUse);
    		
    		invoiceStorageDAO.updateEntityBean(each);
    	}
    	
    	Map<String,InvoiceBindOutBean> map = new HashMap<String,InvoiceBindOutBean>();
    	
    	//  按fullId + outtype 合并金额
    	for (InvoiceBindOutBean each : bindList)
    	{
    		String key = each.getFullId() + "-" + each.getOuttype();
    		
    		if (!map.containsKey(key))
    		{
    			map.put(key, each);
    		}else{
    			InvoiceBindOutBean bo = map.get(key);
    			
    			bo.setConfirmMoney(bo.getConfirmMoney() + each.getConfirmMoney());
    		}
    	}
    	
    	for (InvoiceBindOutBean each : map.values())
    	{
    		// 采购付款申请认票
    		if (each.getOuttype() == 999)
    		{
    			StockPayApplyBean spaBean = stockPayApplyDAO.find(each.getFullId());
    			
    			if (null == spaBean)
    			{
    				throw new MYException("数据错误");
    			}
    			
    			if (MathTools.compare(spaBean.getRealMoneys() - spaBean.getHasConfirmInsMoney(), each.getConfirmMoney()) < 0)
    			{
    				throw new MYException("数据溢出，请重新操作");
    			}
    			
    			stockPayApplyDAO.updateHasConfirmMoney(each.getFullId(), spaBean.getHasConfirmInsMoney() + each.getConfirmMoney());
    			
    			if (MathTools.equal(spaBean.getRealMoneys() - spaBean.getHasConfirmInsMoney(), each.getConfirmMoney()))
    			{
    				stockPayApplyDAO.updateHasConfirm(each.getFullId(), 1);
    			}
    			
    		}else if (each.getOuttype() == 98)  //委托退货认票
    		{
    			OutBalanceBean balanceBean = outBalanceDAO.find(each.getFullId());
    			
    			if (null == balanceBean)
    			{
    				throw new MYException("数据错误");
    			}
    			
    			if (MathTools.compare(balanceBean.getTotal() - balanceBean.getHasConfirmInsMoney(), each.getConfirmMoney()) < 0)
    			{
    				throw new MYException("数据溢出，请重新操作");
    			}
    			
    			outBalanceDAO.updateHasConfirmMoney(each.getFullId(), balanceBean.getHasConfirmInsMoney() + each.getConfirmMoney());
    			
    			if (MathTools.equal(balanceBean.getTotal() - balanceBean.getHasConfirmInsMoney(), each.getConfirmMoney()))
    			{
    				outBalanceDAO.updateHasConfirm(each.getFullId(), 1);
    			}
    			
    		}else{
    			OutBean out = outDAO.find(each.getFullId());
    			
    			if (null == out)
    			{
    				throw new MYException("数据错误");
    			}

    			if (MathTools.compare(out.getTotal() - out.getHasConfirmInsMoney(), each.getConfirmMoney()) < 0)
    			{
    				throw new MYException("数据溢出，请重新操作");
    			}
    			
    			outDAO.updateHasConfirmMoney(each.getFullId(), out.getHasConfirmInsMoney() + each.getConfirmMoney());
    			
    			if (MathTools.equal(out.getTotal() - out.getHasConfirmInsMoney(), each.getConfirmMoney()))
    			{
    				outDAO.updateHasConfirm(each.getFullId(), 1);
    			}
    		}
    	}
    	
    	invoiceBindOutDAO.saveAllEntityBeans(bindList);
    	
		return true;
	}
    
    @Transactional(rollbackFor = MYException.class)
	public boolean refInvoice(User user, List<InvoiceBindOutBean> vsList)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, vsList);
    	
    	String stockPayApplyId = vsList.get(0).getFullId();
    	
    	StockPayApplyBean bean = stockPayApplyDAO.find(stockPayApplyId);
    	
    	if (null == bean)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	if (bean.getStatus() != StockPayApplyConstant.APPLY_STATUS_END)
    	{
    		throw new MYException("采购付款申请单须是结束状态");
    	}
    	
    	double needConfirm = bean.getRealMoneys() - bean.getHasConfirmInsMoney();
    	
    	if (needConfirm <= 0)
    	{
    		throw new MYException("发票金额已全部确认");
    	}
    	
    	List<InvoiceBindOutBean> bindList = new ArrayList<InvoiceBindOutBean>();
    	
    	for (InvoiceBindOutBean each : vsList)
    	{
    		InvoiceStorageBean isbean = invoiceStorageDAO.find(each.getInvoiceStorageId());
    		
    		double canUseMoney = isbean.getMoneys() - isbean.getHasConfirmMoneys();
    		
    		if (canUseMoney >= needConfirm)
    		{
    			canUseMoney -= needConfirm;
    			
    			stockPayApplyDAO.updateHasConfirmMoney(stockPayApplyId, bean.getRealMoneys());
    			
    			stockPayApplyDAO.updateHasConfirm(stockPayApplyId, 1);
    			
    			isbean.setHasConfirmMoneys(isbean.getHasConfirmMoneys() + needConfirm);
    			
    			invoiceStorageDAO.updateEntityBean(isbean);
    			
    			bindList.add(each);
    			
    			break;
    		}else{
    			needConfirm -= canUseMoney;
    			
    			stockPayApplyDAO.updateHasConfirmMoney(stockPayApplyId, bean.getHasConfirmInsMoney() + canUseMoney);
    			
    			isbean.setHasConfirmMoneys(isbean.getHasConfirmMoneys() + canUseMoney);
    			
    			bindList.add(each);
    			
    			invoiceStorageDAO.updateEntityBean(isbean);
    		}
    	}
    	
    	invoiceBindOutDAO.saveAllEntityBeans(bindList);
    	
    	return true;
	}
    
    @Transactional(rollbackFor = MYException.class)
	public String importInvoiceins(User user, List<InvoiceinsImportBean> list)
			throws MYException
	{
		_logger.info("***begin importInvoiceins***");
    	JudgeTools.judgeParameterIsNull(user, list);
    	
    	String batchId = commonDAO.getSquenceString20();
    	
    	for (InvoiceinsImportBean each : list) {
    		each.setBatchId(batchId);
    	}

    	try {
            invoiceinsImportDAO.saveAllEntityBeans(list);
        }catch (DataIntegrityViolationException e){
    	    _logger.error(e,e);
    	    throw new MYException("开票信息重复,请联系管理员!");
        }

		_logger.info("importInvoiceins for batchId:" + batchId);
    	
		return batchId;
	}
    
    @Transactional(rollbackFor = MYException.class)
	public boolean batchUpdateInsNum(User user, List<InvoiceinsImportBean> list)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, list);
    	Map<String, List<InvoiceinsImportBean>> map = new HashMap<String, List<InvoiceinsImportBean>>();

    	_logger.info("***batchUpdateInsNum size***"+list.size());
    	for (InvoiceinsImportBean each : list) {
			String id = each.getId();
    		if (!map.containsKey(id)) {
    			List<InvoiceinsImportBean> ilist = new ArrayList<InvoiceinsImportBean>();
    			ilist.add(each);
    			map.put(id, ilist);
    		} else {
    			List<InvoiceinsImportBean> ilist = map.get(id);
    			ilist.add(each);
    		}
    	}

		_logger.info("***batchUpdateInsNum map key size***" + map.keySet().size());
    	for (Map.Entry<String, List<InvoiceinsImportBean>> each : map.entrySet()) {
    		List<InvoiceinsImportBean> ilist = each.getValue();

			String insId = each.getKey();
    		List<InsVSInvoiceNumBean> numList = insVSInvoiceNumDAO.queryEntityBeansByFK(insId);
    		
    		if (ilist.size() != numList.size()) {
    			throw new MYException("开票标识[%s]发票号码条数是[%s],导入的发票号码条数[%s],两者条数须一致。", insId, numList.size(), ilist.size());
    		}
    		
    		// 不得有与现有的重复的发票号
    		for (int i = 0; i < ilist.size(); i++) {
				try{
					InvoiceinsImportBean newnum = ilist.get(i);
					String id = numList.get(i).getId();
					InsVSInvoiceNumBean insNum = insVSInvoiceNumDAO.find(id);

                    //2016/10/11 #328 以真实发票号码替换package_item中对应的产品名中的临时发票号码
                    if (!StringTools.isNullOrNone(insNum.getInvoiceNum())){
                        String outId = insNum.getInsId();
                        this.packageItemDAO.replaceInvoiceNum(outId, insNum.getInvoiceNum(), newnum.getInvoiceNum());

                        //#328 如果是XN发票号,更新CK单为已捡配
                        if (insNum.getInvoiceNum().contains("XN")){
                            ConditionParse conditionParse = new ConditionParse();
                            List<PackageItemBean> packageItemBeanList = this.packageItemDAO.queryEntityBeansByFK(outId,
                                    AnoConstant.FK_FIRST);
                            if (!ListTools.isEmptyOrNull(packageItemBeanList)){
                                String packageId = packageItemBeanList.get(0).getPackageId();
                                _logger.info(packageId+"****update XN***"+ShipConstant.SHIP_STATUS_PICKUP);

                                this.packageDAO.updateStatus(packageId, ShipConstant.SHIP_STATUS_PICKUP);
                            }
                        }
                    }

					insNum.setInvoiceNum(newnum.getInvoiceNum());
                    insNum.setInvoiceCode(newnum.getInvoiceCode());
                    insNum.setInvoiceType(newnum.getInvoiceType());
					insVSInvoiceNumDAO.updateEntityBean(insNum);
					_logger.info(String.format("%s更新发票号码:%s",id, insNum));
					//2016/2/17 #169 生成CK单
					InvoiceinsBean bean = this.invoiceinsDAO.find(insId);
					_logger.info("***find invoiceins bean***"+bean);
					if (bean!= null && bean.getStatus()!= FinanceConstant.INVOICEINS_STATUS_END) {
                        //如果票随货发，就不写入preconsign表，写入临时表
                        if (InvoiceinsImportBean.INVOICE_FOLLOW_OUT.equals(bean.getInvoiceFollowOut()) ){
                            //如果是票随货发，发票对应所有订单已经库管审批通过，那发票也直接写入preconsign
                            String refIds = bean.getRefIds();
                            if (!StringTools.isNullOrNone(refIds)){
                                String[] fullIds = refIds.split(";");
                                boolean flag = false;
                                for (String fullId : fullIds){
                                    OutBean outBean = outDAO.find(fullId);
                                    //如果存在销售单“待商务审批”或"待库管审批"，就写入临时表
                                    if(outBean == null){
                                        continue;
                                    }else if (outBean.getStatus() == OutConstant.STATUS_SUBMIT||
                                            outBean.getStatus() == OutConstant.STATUS_FLOW_PASS){
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag){
                                    //写入临时表
                                    TempConsignBean tempConsignBean = new TempConsignBean();
                                    tempConsignBean.setOutId(refIds);
                                    tempConsignBean.setInsId(bean.getId());
                                    _logger.info(bean.getId()+"此发票存在销售单为待商务审批或待库管审批,写入T_CENTER_TEMPCONSIGN表:" + tempConsignBean);
                                    this.tempConsignDAO.saveEntityBean(tempConsignBean);
                                } else{
                                    this.createPackage(bean);
                                }
                            }
                        } else{
                            this.createPackage(bean);
                        }

						//状态变成结束
						bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);
						this.invoiceinsDAO.updateEntityBean(bean);

						FlowLogBean log = new FlowLogBean();

						log.setActor(user.getStafferName());
						log.setActorId(user.getStafferId());
						log.setFullId(bean.getId());
						log.setDescription("导入发票自动结束");
						log.setLogTime(TimeTools.now());
						log.setPreStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
						log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_END);
						log.setOprMode(PublicConstant.OPRMODE_PASS);

						flowLogDAO.saveEntityBean(log);
					}
				}catch(Exception e){
					e.printStackTrace();
					throw new MYException("导入发票号码异常[%s]", e.getMessage());
				}
    		}
    	}
    	_logger.info("***finish batchUpdateInsNum***");
		return true;
	}

	private String getInCondition(String value){
		StringBuilder sb = new StringBuilder();
		String[] values = value.split(";");
		sb.append("(");
		for(String str: values){
			sb.append("'").append(str).append("',");
		}

		String str = sb.toString();
		return str.substring(0,str.length()-1)+")";
	}

    public void checkImportIns(List<InvoiceinsImportBean> list, StringBuilder sb){
		_logger.info("***begin checkImportIns***"+list);
		//同一个SO可对应多个开票申请
		Map<String ,List<InvoiceinsImportBean>> outToInvoicesMap = new HashMap<String, List<InvoiceinsImportBean>>();

        //销售单中产品数量汇总
        Map<String, Integer> productToAmountMap2 = new HashMap<String, Integer>();
    	//1.没有发生过开票，可开票部分 = 原单 - 退货
    	//2.须符合票、款一致原则
    	for (InvoiceinsImportBean each : list) {
    		String fullId = "";
            // 特殊类型,该类型不生成开票申请
			if ("9999999999".equals(each.getInvoiceId())){
				continue;
			}

			String productName = each.getProductName();
            if(!StringTools.isNullOrNone(productName)) {
                ProductBean product = this.productDAO.findByName(productName);
                if (product == null){
                    sb.append("产品不存在:")
                            .append(productName)
                            .append("<br>");
                }
                else{
                    each.setProductId(product.getId());
                }
            }
    		
    		OutBean out = outDAO.find(each.getOutId());
    		
    		if (out == null) {
				_logger.error("***not found outId***" +each.getOutId());
    			OutBalanceBean balance = outBalanceDAO.find(each.getOutId());

    			if (null == balance) {
    				sb.append("库单");
    				sb.append(each.getOutId());
    				sb.append("不存在");
    				sb.append("<br>");
    			} else {
                    List<BaseBalanceBean> balanceList = baseBalanceDAO.queryEntityBeansByFK(balance.getId());

                    if (!ListTools.isEmptyOrNull(balanceList)) {
                        for (BaseBalanceBean eachbb : balanceList) {
                            BaseBean base = baseDAO.find(eachbb.getBaseId());

                            if (null != base) {
                                try {
                                    checkProductAttrInner(each.getInvoiceId(), base.getProductId(), null);
                                } catch (MYException e) {
                                    sb.append("结算单");
                                    sb.append(each.getOutId());
                                    sb.append(e.getErrorContent());
                                    sb.append("<br>");
                                }
                            }
                        }
                    }

                    if (StringTools.isNullOrNone(balance.getPiDutyId()) || (balance.getPiMtype() == 1 && balance.getPiStatus() == 1)) {
                        //TODO each.getId()应该为空，这段代码有问题！
                        double refMoneys = outBalanceDAO.sumByOutBalanceId(each.getId());
                        double mayMoneys = balance.getTotal() - refMoneys - balance.getInvoiceMoney();
                        //TODO
//        					if (MathTools.compare(each.getInvoiceMoney(), mayMoneys) != 0) {
//	    						sb.append("销售单");
//	            				sb.append(each.getOutId());
//	            				sb.append("导入的开票金额"+each.getInvoiceMoney()+"须等于可开票金额"+mayMoneys);
//	            				sb.append("<br>");
//	    					}

                        if (MathTools.compare(each.getInvoiceMoney(),mayMoneys ) == 1) {
                            sb.append("销售单");
                            sb.append(each.getOutId());
                            sb.append("导入的开票金额"+each.getInvoiceMoney()+"须小于等于可开票金额"+mayMoneys);
                            sb.append("<br>");
                        }
                    }

    				fullId = balance.getOutId();
    			}
    		} else {
                List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(out.getFullId());

                if (!ListTools.isEmptyOrNull(baseList)) {
                    for (BaseBean eachb : baseList) {
                        try {
                            checkProductAttrInner(each.getInvoiceId(), eachb.getProductId(), out);
                        } catch (MYException e) {
                            sb.append("销售单");
                            sb.append(each.getOutId());
                            sb.append(e.getErrorContent());
                            sb.append("<br>");
                        }
                    }
                }

//    				if (StringTools.isNullOrNone(out.getPiDutyId()) || (out.getPiMtype() == 1 && out.getPiStatus() == 1)) {
//    					// 检查 导入的开票金额是全部的可开票金额
//						String key = each.getOutId();
//						if (outToInvoicesMap.containsKey(key)){
//							outToInvoicesMap.get(key).add(each);
//						} else{
//							List<InvoiceinsImportBean> beans = new ArrayList<InvoiceinsImportBean>();
//							beans.add(each);
//							outToInvoicesMap.put(key, beans);
//						}
//    				}

                //#84 2017/6/28 拆单开票和非拆单开票一起导入时，有时开票有问题
                String key = each.getOutId();
                if (outToInvoicesMap.containsKey(key)){
                    outToInvoicesMap.get(key).add(each);
                } else{
                    List<InvoiceinsImportBean> beans = new ArrayList<InvoiceinsImportBean>();
                    beans.add(each);
                    outToInvoicesMap.put(key, beans);
                }
                fullId = out.getFullId();
    		}
    		
    		if (!StringTools.isNullOrNone(fullId) && each.getAddrType() == InvoiceinsConstants.INVOICEINS_DIST_SAME) {
    			List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(fullId);

				if (!ListTools.isEmptyOrNull(distList)) {
    				if (distList.get(0).getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
    					sb.append("销售单");
        				sb.append(fullId);
        				sb.append("配送地址选择 同原销售单，但原销售单配送方式是 空发 ");
        				sb.append("<br>");
    				}
    			}
    		}
    	}

        //#214 同一商品数量必须等于SO单中实际数量
		for (String key : outToInvoicesMap.keySet()){
			_logger.info("****check key***"+key);
			List<InvoiceinsImportBean> beans = outToInvoicesMap.get(key);
			OutBean out = outDAO.find(key);
			if (out!= null){
                String outId = out.getFullId();
				double invoiceMoneyTotal = 0;
				for (InvoiceinsImportBean bean: beans){
					invoiceMoneyTotal += bean.getInvoiceMoney();
				}
				double retTotal = outDAO.sumOutBackValueIgnoreStatus(outId);
                double mayMoneys = out.getTotal() - retTotal - out.getInvoiceMoney();
//				if (MathTools.compare(invoiceMoneyTotal,mayMoneys ) != 0) {
////					sb.append("销售单");
////					sb.append(key);
////					sb.append("导入的开票金额"+invoiceMoneyTotal+"须等于可开票金额"+mayMoneys);
////					sb.append("<br>");
////				}

                if (MathTools.compare(invoiceMoneyTotal,mayMoneys ) == 1) {
                    sb.append("销售单");
                    sb.append(key);
                    sb.append("导入的开票金额"+invoiceMoneyTotal+"须小于等于可开票金额"+mayMoneys);
                    sb.append("<br>");
                }

                List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);

                //开票信息根据productId汇总 <outId_productId,amount>
                Map<String, Integer> productToAmountMap = new HashMap<String, Integer>();
                Map<String, String> productMap = new HashMap<String ,String>();
                for (InvoiceinsImportBean bean : beans){
                    String productId = bean.getProductId();
                    String key2 = bean.getOutId()+"_"+productId;
                    productMap.put(productId, bean.getProductName());
                    if (productToAmountMap.containsKey(key2)){
                        int amount = bean.getAmount()+productToAmountMap.get(key2);
                        productToAmountMap.put(key2, amount);
                    } else{
                        productToAmountMap.put(key2, bean.getAmount());
                    }
                }

                _logger.info("***productToAmountMap***"+productToAmountMap);
                for (String key2 : productToAmountMap.keySet()){
                    int amount = productToAmountMap.get(key2);
                    String[] array = key2.split("_");
                    if (array.length == 2){
                        String productId = array[1];
                        int amount2 = this.getProductAmount(baseList, productId);
                        productToAmountMap2.put(key2, amount2);

                        String productName = productMap.get(productId);
                        //#863 商品名是XX+XX，不控制数量检查
                        if ( productName.indexOf("+") == -1 && amount > amount2){
                            sb.append("商品").append(productName)
                                    .append("数量").append(amount).append("必须小于等于销售单").append(outId)
                                    .append("中对应数量").append(amount2).append("<br>");
                        }
                    }
                }
			}
		}

        //#393 设置开票申请拆分标记
        _logger.info(productToAmountMap2+"***outToInvoicesMap****"+outToInvoicesMap);
        for (InvoiceinsImportBean each : list) {
            String outId = each.getOutId();
            List<InvoiceinsImportBean> beans = outToInvoicesMap.get(outId);
            //case1 导入模板中同一单分拆
            if (!ListTools.isEmptyOrNull(beans) && beans.size()>=2){
                _logger.info("***set split flag***"+each);
                each.setSplitFlag(true);
            }
            //case2 导入模板只有一单,但部分开票
            else{
                String key = outId+"_"+each.getProductId();
                if (productToAmountMap2.get(key)!= null){
                    int amount = productToAmountMap2.get(key);
                    if (each.getAmount() < amount){
                        _logger.info("***set split flag***"+each);
                        each.setSplitFlag(true);
                    }
                }
            }
        }
    }

    @Override
    public boolean process2(final List<InvoiceinsImportBean> list) throws MYException {
        _logger.info("***begin process2***");
        JudgeTools.judgeParameterIsNull(list);

        StringBuilder sb = new StringBuilder();

        // check again
        checkImportIns(list, sb);

        _logger.info("***process step2***" + sb.toString());

        if (!StringTools.isNullOrNone(sb.toString())) {
            throw new MYException(sb.toString());
        }

        final String batchId = list.get(0).getBatchId();

        try
        {
            TransactionTemplate tran = new TransactionTemplate(transactionManager);
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus status)
                {
                    try
                    {
                        processInner2(list);
                    }
                    catch (MYException e)
                    {
						_logger.error("Exception:",e);
						throw new RuntimeException(e);
                    }

                    //saveLog
                    saveLogInnerWithoutTransaction(batchId, OutImportConstant.LOGSTATUS_SUCCESSFULL, "成功");

                    return Boolean.TRUE;
                }
            }
            );
        }
        catch (TransactionException e)
        {
            saveLogInner(batchId, OutImportConstant.LOGSTATUS_FAIL, "处理失败,数据库内部错误");

            operationLog.error("批量开票数据处理错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            saveLogInner(batchId, OutImportConstant.LOGSTATUS_FAIL, "处理失败,数据访问异常");

            operationLog.error("批量开票数据处理错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            String msg = "处理失败,系统错误:"+e.toString();
            saveLogInner(batchId, OutImportConstant.LOGSTATUS_FAIL, msg.substring(0,300));

            operationLog.error("批量开票数据处理错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }

        return true;
    }

    @Deprecated
    public boolean process(final List<InvoiceinsImportBean> list)
	throws MYException
	{
		_logger.info("***enter process ***"+list);
    	JudgeTools.judgeParameterIsNull(list);
    	
    	StringBuilder sb = new StringBuilder();
    	
    	// check again
    	checkImportIns(list, sb);

		_logger.info("***process step2***" + sb.toString());
    	
    	if (!StringTools.isNullOrNone(sb.toString())) {
    		throw new MYException(sb.toString());
    	}
    	
    	final String batchId = list.get(0).getBatchId();
		
		try
		{
			TransactionTemplate tran = new TransactionTemplate(transactionManager);
			
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus status)
				{
					try
					{
						processInner(list);
					}
					catch (MYException e)
					{
						throw new RuntimeException(e);
					}
					
					//saveLog
					saveLogInnerWithoutTransaction(batchId, OutImportConstant.LOGSTATUS_SUCCESSFULL, "成功");

					return Boolean.TRUE;
				}
			}
			);
		}
		catch (TransactionException e)
        {
			saveLogInner(batchId, OutImportConstant.LOGSTATUS_FAIL, "处理失败,数据库内部错误");
			
			operationLog.error("批量开票数据处理错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
        	saveLogInner(batchId, OutImportConstant.LOGSTATUS_FAIL, "处理失败,数据访问异常");
        	
        	operationLog.error("批量开票数据处理错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
        	saveLogInner(batchId, OutImportConstant.LOGSTATUS_FAIL, "处理失败,系统错误，请联系管理员");
        	
        	operationLog.error("批量开票数据处理错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }
        
		return true;
	}
    
    private boolean saveLogInner(final String batchId, final int status, final String message)
	{
		try
		{
			TransactionTemplate tran = new TransactionTemplate(transactionManager);
			
			tran.execute(new TransactionCallback()
			{
				public Object doInTransaction(TransactionStatus tstatus)
				{
					insImportLogDAO.deleteEntityBeansByFK(batchId);
					
					InsImportLogBean logBean = new InsImportLogBean();
					
					logBean.setId(commonDAO.getSquenceString20());
					logBean.setBatchId(batchId);
					logBean.setLogTime(TimeTools.now());
					logBean.setMessage(message);
					logBean.setStatus(status);
					
					insImportLogDAO.saveEntityBean(logBean);
					
					return Boolean.TRUE;
				}
			}
			);
		}
        catch (Exception e)
        {
            throw new RuntimeException("系统错误，请联系管理员saveLogInner:" + e);
        }
		
		return true;
	}
    
    private boolean saveLogInnerWithoutTransaction(String batchId, int status, String message)
	{
		insImportLogDAO.deleteEntityBeansByFK(batchId);
		
		InsImportLogBean logBean = new InsImportLogBean();
		
		logBean.setId(commonDAO.getSquenceString20());
		logBean.setBatchId(batchId);
		logBean.setLogTime(TimeTools.now());
		logBean.setMessage(message);
		logBean.setStatus(status);
		
		insImportLogDAO.saveEntityBean(logBean);
		
		return true;
	}

	private boolean processInner2(List<InvoiceinsImportBean> list) throws MYException
	{
		try {
			// 同一个发票号(虚拟发票) + 客户 组合生成一张开票申请，此规则优先于拆单
            // #829改为仅以虚拟发票号分组
			// 实际待开票的map
			Map<String, List<InvoiceinsImportBean>> map = new HashMap<>();

			// 临时Map, 同一个SO单可拆分多个发票<out,List<InvoiceinsImportBean>>
			// 待检查开票商品数量
			Map<String, List<InvoiceinsImportBean>> outToInsMap = new HashMap<>();


			for (InvoiceinsImportBean each : list) {
				OutBean out = outDAO.find(each.getOutId());

				if (out == null) {
					OutBalanceBean balance = outBalanceDAO.find(each.getOutId());

					if (null == balance) {
						throw new MYException("库单[%s]不存在", each.getOutId());
					} else {
						if (each.getInvoiceId().equals("9999999999")) {
							outBalanceDAO.updateInvoiceStatus(balance.getId(), each.getInvoiceMoney(), OutConstant.INVOICESTATUS_END);

							continue;
						} else {
							each.setCustomerId(balance.getCustomerId());
							each.setType(FinanceConstant.INSVSOUT_TYPE_BALANCE);
							each.setStafferId(balance.getStafferId());
						}
					}

				} else {
					if (each.getInvoiceId().equals("9999999999")) {
						outDAO.updateInvoiceStatus(out.getFullId(), each.getInvoiceMoney(), OutConstant.INVOICESTATUS_END);

						continue;
					} else {
						each.setCustomerId(out.getCustomerId());
						each.setType(FinanceConstant.INSVSOUT_TYPE_OUT);
						each.setStafferId(out.getStafferId());
					}
				}

				String key = each.getOutId();

				if (!outToInsMap.containsKey(key)) {
					List<InvoiceinsImportBean> mlist = new ArrayList<InvoiceinsImportBean>();

					mlist.add(each);

					outToInsMap.put(key, mlist);
				} else {
					List<InvoiceinsImportBean> mlist = outToInsMap.get(key);

					mlist.add(each);
				}
			}

			_logger.info("***outToInsMap size***"+outToInsMap.keySet().size());

			for (InvoiceinsImportBean each : list) {
				//TODO 同一个SO单已拆单的并且不带虚拟发票号的要去掉
//				if (outToInsMap.containsKey(each.getOutId())){
//					_logger.info("****continue ***"+each.getOutId());
//					continue;
//				}
				OutBean out = outDAO.find(each.getOutId());

				if (out == null) {
					OutBalanceBean balance = outBalanceDAO.find(each.getOutId());

					if (null == balance) {
						throw new MYException("库单[%s]不存在", each.getOutId());
					} else {
						if (each.getInvoiceId().equals("9999999999")) {
							outBalanceDAO.updateInvoiceStatus(balance.getId(), each.getInvoiceMoney(), OutConstant.INVOICESTATUS_END);

							continue;
						} else {
							each.setCustomerId(balance.getCustomerId());
							each.setType(FinanceConstant.INSVSOUT_TYPE_BALANCE);
							each.setStafferId(balance.getStafferId());
						}
					}

				} else {
					if (each.getInvoiceId().equals("9999999999")) {
						outDAO.updateInvoiceStatus(out.getFullId(), each.getInvoiceMoney(), OutConstant.INVOICESTATUS_END);

						continue;
					} else {
						each.setCustomerId(out.getCustomerId());
						each.setType(FinanceConstant.INSVSOUT_TYPE_OUT);
						each.setStafferId(out.getStafferId());
					}
				}

				//#829虚拟发票号作为key
				String key = each.getInvoiceNum();
				_logger.info("***invoiceins key2***"+key);
				if (!map.containsKey(key)) {
					List<InvoiceinsImportBean> mlist = new ArrayList<InvoiceinsImportBean>();
					mlist.add(each);
					map.put(key, mlist);
				} else {
					List<InvoiceinsImportBean> mlist = map.get(key);
					mlist.add(each);
				}
			}

			_logger.info("***map size3***"+map.keySet().size());

			List<InvoiceinsBean> invoiceinsList = new ArrayList<InvoiceinsBean>();

			saveInner2(map, invoiceinsList);

            _logger.info("***invoiceinsList***"+invoiceinsList.size());
            
            List<InvoiceinsVO> invoiceinsVOList = new ArrayList<InvoiceinsVO>();
			// 调用审批通过
			for (InvoiceinsBean bean : invoiceinsList) {

				InvoiceinsBean obean = invoiceinsDAO.find(bean.getId());
				
				InvoiceinsVO insVO = new InvoiceinsVO();
				insVO.setId(bean.getId());
				insVO.setOtype(0);
				invoiceinsVOList.add(insVO);

				if (obean == null) {
					throw new MYException("数据错误,请确认操作");
				}

				List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(obean.getId(), AnoConstant.FK_FIRST);

				List<InvoiceinsItemBean> itemList = invoiceinsItemDAO.queryEntityBeansByFK(obean.getId());

				// 单据的开票状态需要更新
				if (!ListTools.isEmptyOrNull(vsList)) {
					String baseId = vsList.get(0).getBaseId();
					_logger.info("*********baseId*****"+baseId);
					if (StringTools.isNullOrNone(baseId)) {
						for (InsVSOutBean insVSOutBean : vsList) {
							handlerEachInAdd(insVSOutBean);
						}
					}
					// 0新模式标记 @@see InvoiceinsAction.createInsInNavigation1
					else if ("0".equals(baseId)) {
						for (InvoiceinsItemBean item : itemList) {
							// 新的开单规则
							handlerEachInAdd3(item);
						}
					} else {
						for (InsVSOutBean insVSOutBean : vsList) {
							handlerEachInAdd2(insVSOutBean);
						}
					}
				}else{
                    _logger.info("****no VS List found***");
                }

				// 处理票款纳税实体一致  结束开票时打的标记
				for (InsVSOutBean vs : vsList) {
					if (vs.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
						outDAO.updatePayInvoiceData(vs.getOutId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, PublicConstant.MANAGER_TYPE_COMMON, PublicConstant.DEFAULR_DUTY_ID, 1);
					} else {
						outBalanceDAO.updatePayInvoiceData(vs.getOutBalanceId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, PublicConstant.MANAGER_TYPE_COMMON, PublicConstant.DEFAULR_DUTY_ID, 1);
					}
				}
			}
			UserVO user = new UserVO();
			user.setStafferName("邢君君");
			user.setStafferId("239358493");
			batchConfirmAndCreatePackage(user, invoiceinsVOList);
		}catch(Exception e){
			_logger.error(e,e);
			throw new RuntimeException(e);
		}

		return true;
	}

	private int getProductAmount(List<BaseBean> baseList, String productId){
		int amount = 0;
		for (BaseBean baseBean: baseList){
			if (baseBean.getProductId().equals(productId)){
				amount += baseBean.getAmount();
			}
		}
		return amount;
	}

    @Deprecated
	private boolean processInner(List<InvoiceinsImportBean> list) throws MYException
	{
		try {
			//  同一个发票号 + 客户 组合生成一张开票申请
			Map<String, List<InvoiceinsImportBean>> map = new HashMap<String, List<InvoiceinsImportBean>>();

			for (InvoiceinsImportBean each : list) {
				OutBean out = outDAO.find(each.getOutId());

				if (out == null) {
					OutBalanceBean balance = outBalanceDAO.find(each.getOutId());

					if (null == balance) {
						throw new MYException("库单[%s]不存在", each.getOutId());
					} else {
						if (each.getInvoiceId().equals("9999999999")) {
							outBalanceDAO.updateInvoiceStatus(balance.getId(), each.getInvoiceMoney(), OutConstant.INVOICESTATUS_END);

							continue;
						} else {
							each.setCustomerId(balance.getCustomerId());
							each.setType(FinanceConstant.INSVSOUT_TYPE_BALANCE);
							each.setStafferId(balance.getStafferId());
						}
					}

				} else {
					if (each.getInvoiceId().equals("9999999999")) {
						outDAO.updateInvoiceStatus(out.getFullId(), each.getInvoiceMoney(), OutConstant.INVOICESTATUS_END);

						continue;
					} else {
						each.setCustomerId(out.getCustomerId());
						each.setType(FinanceConstant.INSVSOUT_TYPE_OUT);
						each.setStafferId(out.getStafferId());
					}
				}

				String key = each.getCustomerId() + "-" + each.getInvoiceNum();

				if (!map.containsKey(key)) {
					List<InvoiceinsImportBean> mlist = new ArrayList<InvoiceinsImportBean>();

					mlist.add(each);

					map.put(key, mlist);
				} else {
					List<InvoiceinsImportBean> mlist = map.get(key);

					mlist.add(each);
				}
			}

			List<InvoiceinsBean> invoiceinsList = new ArrayList<InvoiceinsBean>();

			saveInner(map, invoiceinsList);

			// 调用审批通过
			for (InvoiceinsBean bean : invoiceinsList) {

				InvoiceinsBean obean = invoiceinsDAO.find(bean.getId());

				if (obean == null) {
					throw new MYException("数据错误,请确认操作");
				}

				List<InsVSOutBean> vsList = insVSOutDAO.queryEntityBeansByFK(obean.getId(), AnoConstant.FK_FIRST);

				List<InvoiceinsItemBean> itemList = invoiceinsItemDAO.queryEntityBeansByFK(obean.getId());

				// 单据的开票状态需要更新
				if (!ListTools.isEmptyOrNull(vsList)) {
					if (StringTools.isNullOrNone(vsList.get(0).getBaseId())) {
						for (InsVSOutBean insVSOutBean : vsList) {
							handlerEachInAdd(insVSOutBean);
						}
					}
					// 0新模式标记 @@see InvoiceinsAction.createInsInNavigation1
					else if (vsList.get(0).getBaseId().equals("0")) {

						for (InvoiceinsItemBean item : itemList) {
							// 新的开单规则
							handlerEachInAdd3(item);
						}
					} else {
						for (InsVSOutBean insVSOutBean : vsList) {
							handlerEachInAdd2(insVSOutBean);
						}
					}
				}

				// 处理票款纳税实体一致  结束开票时打的标记
				for (InsVSOutBean vs : vsList) {
					if (vs.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
						outDAO.updatePayInvoiceData(vs.getOutId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, PublicConstant.MANAGER_TYPE_COMMON, PublicConstant.DEFAULR_DUTY_ID, 1);
					} else {
						outBalanceDAO.updatePayInvoiceData(vs.getOutBalanceId(), OutConstant.OUT_PAYINS_TYPE_INVOICE, PublicConstant.MANAGER_TYPE_COMMON, PublicConstant.DEFAULR_DUTY_ID, 1);
					}
				}

				FlowLogBean log = new FlowLogBean();

				log.setActor("系统");
				log.setActorId(StafferConstant.SUPER_STAFFER);
				log.setFullId(obean.getId());
				log.setDescription("批量生成,待审批");
				log.setLogTime(TimeTools.now());
				log.setPreStatus(FinanceConstant.INVOICEINS_STATUS_SAVE);
				log.setAfterStatus(bean.getStatus());
				log.setOprMode(PublicConstant.OPRMODE_PASS);

				flowLogDAO.saveEntityBean(log);
			}
		}catch(Exception e){
			_logger.error(e);
			return false;
		}

        return true;
	}


    @Deprecated
	private void saveInner(Map<String, List<InvoiceinsImportBean>> map,
			List<InvoiceinsBean> invoiceinsList) throws MYException
	{
		DutyBean duty = dutyDAO.find(PublicConstant.DEFAULR_DUTY_ID);
    	
    	for (Map.Entry<String, List<InvoiceinsImportBean>> each : map.entrySet()) {
    		List<InvoiceinsImportBean> elist = each.getValue();
    		
    		InvoiceinsImportBean first = elist.get(0);
    		
    		// Assemble invoiceinsBean/invoiceinsItemBean/insVSOutBean/InsVSInvoiceNumBean
    		InvoiceinsBean bean = new InvoiceinsBean();
    		
    		bean.setId(commonDAO.getSquenceString20());
    		bean.setInvoiceDate(first.getInvoiceDate());
    		bean.setHeadType(0);
    		bean.setHeadContent(first.getInvoiceHead());
    		bean.setInvoiceId(first.getInvoiceId());
    		bean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
    		bean.setCustomerId(first.getCustomerId());
    		bean.setDescription("批量导入生成开票申请");
    		bean.setInsAmount(1);
    		bean.setLogTime(TimeTools.now());
    		bean.setLocationId("999");
            //2015/2/1 票随货发
            bean.setInvoiceFollowOut(first.getInvoiceFollowOut());
    		//
    		bean.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
    		bean.setOperator(StafferConstant.SUPER_STAFFER);
    		bean.setOperatorName("系统");
    		bean.setProcesser(duty.getInvoicer());
    		//
    		bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END); // 直接结束
    		bean.setStafferId(first.getStafferId());
    		bean.setType(0);
    		bean.setOtype(0);
    		
    		List<InvoiceinsItemBean> itemList = new ArrayList<InvoiceinsItemBean>();
    		
    		List<InsVSOutBean> vsList = new ArrayList<InsVSOutBean>();
    		
    		List<InsVSInvoiceNumBean> numList = new ArrayList<InsVSInvoiceNumBean>();
    		
    		bean.setItemList(itemList);
    		bean.setVsList(vsList);
    		bean.setNumList(numList);
    		
    		double invoicemoney = 0.0d;
    		StringBuilder sb = new StringBuilder();
    		
    		for (InvoiceinsImportBean eachb : elist) {
    			
    			invoicemoney += eachb.getInvoiceMoney();
    			sb.append(eachb.getOutId());
    			sb.append(";");
    			
    			if (eachb.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
    				List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(eachb.getOutId());
    				
    				// eliminate return
                	ConditionParse con = new ConditionParse();

                    con.addWhereStr();

                    con.addCondition("OutBean.refOutFullId", "=", eachb.getOutId());

                    con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

                    con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OUTBACK);

                    List<OutBean> refOutList = outDAO.queryEntityBeansByCondition(con);
                    
                    for (OutBean eacho : refOutList)
                    {
                    	List<BaseBean> blist = baseDAO.queryEntityBeansByFK(eacho.getFullId());
                    	
                    	eacho.setBaseList(blist);
                    }
                    
                    // 计算出已经退货的数量                
                    for (BaseBean baseBean : baseList)
                    {
                        int hasBack = 0;

                        // 退库
                        for (OutBean ref : refOutList)
                        {
                            for (BaseBean refBase : ref.getBaseList())
                            {
                                if (refBase.equals2(baseBean))
                                {
                                    hasBack += refBase.getAmount();
                                }
                            }
                        }
                        
                        baseBean.setAmount(baseBean.getAmount() - hasBack);
                    }
                    
                    double vsMoney = 0.0d;
    				for (BaseBean eachitem : baseList) {
    					if (eachitem.getAmount() > 0) {
    						InvoiceinsItemBean item = new InvoiceinsItemBean();
        					
    						item.setId(commonDAO.getSquenceString20());
    						item.setParentId(bean.getId());
    						item.setShowId("10201103130001000189");
    						item.setShowName("纪念品");
    						item.setUnit("8874797");
    						item.setAmount(eachitem.getAmount());
    						item.setPrice(eachitem.getPrice());
    						item.setMoneys(item.getAmount() * item.getPrice());
    						item.setOutId(eachitem.getOutId());
    						item.setBaseId(eachitem.getId());
    						item.setProductId(eachitem.getProductId());
    						item.setType(eachb.getType());
    						item.setCostPrice(eachitem.getCostPrice());
    						
        					itemList.add(item);
        					
        					vsMoney += item.getMoneys();
    					}
    				}
    				
    				InsVSOutBean vsBean = new InsVSOutBean();
    				
    				vsBean.setId(commonDAO.getSquenceString20());
    				vsBean.setInsId(bean.getId());
    				vsBean.setOutId(eachb.getOutId());
    	        	vsBean.setType(eachb.getType());
    	        	vsBean.setMoneys(vsMoney);
    	        	vsBean.setBaseId("0"); // 标记
    				
    				vsList.add(vsBean);
    				
    			} else {
                	// 结算单
    				OutBalanceBean balance = outBalanceDAO.find(eachb.getOutId());
    				
                	List<BaseBalanceBean> baseBalanceList = baseBalanceDAO.queryEntityBeansByFK(balance.getId());
                	
                	List<BaseBean> baseList = new ArrayList<BaseBean>();
                	
                	for (BaseBalanceBean eachba : baseBalanceList)
                	{
                		String baseId = eachba.getBaseId();
                		
                		BaseBean baseBean = baseDAO.find(baseId);
                		
                		if (null != baseBean)
                		{
                			baseBean.setId(eachba.getId());
                			baseBean.setOutId(eachba.getParentId());
                			baseBean.setAmount(eachba.getAmount());
                			baseBean.setPrice(eachba.getSailPrice());
                			baseBean.setMtype(1);
                			baseBean.setDescription("");
                			baseBean.setLocationId(eachba.getBaseId());
                			
                			baseList.add(baseBean);
                		}
                	}
                	
                	// 结算单退货明细
                	List<BaseBalanceBean> refList = new ArrayList<BaseBalanceBean>();
                	
                	List<OutBalanceBean> balanceList = outBalanceDAO.queryEntityBeansByFK(eachb.getId(), AnoConstant.FK_FIRST);
                	
                	for (OutBalanceBean eachob : balanceList)
                	{
                		List<BaseBalanceBean> bbList = baseBalanceDAO.queryEntityBeansByFK(eachob.getId());
                		
                		refList.addAll(bbList);
                	}
                	
            		for (BaseBean baseBean : baseList)
                    {
                        int hasBack = 0;

                        // 申请开票除外
                        for (BaseBalanceBean eachbb : refList)
                        {
                            if (eachbb.getBaseId().equals(baseBean.getLocationId()))
                            {
                                hasBack += eachbb.getAmount();
                            }
                        }
                        
                        baseBean.setAmount(baseBean.getAmount() - hasBack);
                    }
            		
            		double vsMoney = 0.0d;
    				for (BaseBean eachitem : baseList) {
    					if (eachitem.getAmount() > 0) {
    						InvoiceinsItemBean item = new InvoiceinsItemBean();
        					
    						item.setId(commonDAO.getSquenceString20());
    						item.setParentId(bean.getId());
    						item.setShowId("10201103130001000189");
    						item.setShowName("纪念品");
    						item.setUnit("8874797");
    						item.setAmount(eachitem.getAmount());
    						item.setPrice(eachitem.getPrice());
    						item.setMoneys(item.getAmount() * item.getPrice());
    						item.setOutId(eachitem.getOutId());
    						item.setBaseId(eachitem.getId());
    						item.setProductId(eachitem.getProductId());
    						item.setType(eachb.getType());
    						item.setCostPrice(eachitem.getCostPrice());
    						
        					itemList.add(item);
        					
        					vsMoney += item.getMoneys();
    					}
    				}
    				
    				InsVSOutBean vsBean = new InsVSOutBean();
    				
    				vsBean.setId(commonDAO.getSquenceString20());
    				vsBean.setInsId(bean.getId());
    				vsBean.setOutId(balance.getOutId());
    				vsBean.setOutBalanceId(balance.getId());
    	        	vsBean.setType(eachb.getType());
    	        	vsBean.setMoneys(vsMoney);
    	        	vsBean.setBaseId("0"); // 标记
    				
    				vsList.add(vsBean);
    			}
    			
    			eachb.setRefInsId(bean.getId());
    			
    			invoiceinsImportDAO.updateEntityBean(eachb);
    		}
    		
    		bean.setMoneys(invoicemoney);
    		bean.setRefIds(sb.toString());
    		bean.setFillType(0);
    		
    		bean.setDescription(first.getDescription());
    		// fill distribution
    		if (first.getAddrType() == InvoiceinsConstants.INVOICEINS_DIST_NEW) {
    			bean.setShipping(first.getShipping());
            	bean.setTransport1(first.getTransport1());
            	bean.setTransport2(first.getTransport2());
            	bean.setProvinceId(first.getProvinceId());
            	bean.setCityId(first.getCityId());
            	bean.setAreaId(first.getAreaId());
            	bean.setAddress(first.getAddress());
            	bean.setReceiver(first.getReceiver());
            	bean.setMobile(first.getMobile());
            	bean.setTelephone(first.getTelephone());
            	bean.setExpressPay(first.getExpressPay());
            	bean.setTransportPay(first.getTransportPay());
    		} else {
    			bean.setFillType(InvoiceinsConstants.INVOICEINS_DIST_SAME);
    		}
        	
        	setDistFromDist(bean);
    		
    		// numList
    		InsVSInvoiceNumBean num = new InsVSInvoiceNumBean();
    		
    		num.setInsId(bean.getId());
    		num.setMoneys(bean.getMoneys());
            //TODO 导入开票申请时，如果输入虚拟发票号，还需要当作是发票号码吗？
    		num.setInvoiceNum(first.getInvoiceNum());
    		
    		numList.add(num);
    		
    		invoiceinsDAO.saveEntityBean(bean);
    		
    		invoiceinsItemDAO.saveAllEntityBeans(itemList);
    		
    		insVSOutDAO.saveAllEntityBeans(vsList);
    		
    		insVSInvoiceNumDAO.saveAllEntityBeans(numList);
    		
    		// package
    		createPackage(bean);
    		
    		invoiceinsList.add(bean);
    	}
	}

	private void saveInner2(Map<String, List<InvoiceinsImportBean>> map,
						   List<InvoiceinsBean> invoiceinsList) throws MYException
	{
		DutyBean duty = dutyDAO.find(PublicConstant.DEFAULR_DUTY_ID);
        //BaseBean cache
        Map<String, List<BaseBean>> baseMap = new HashMap<String, List<BaseBean>>();

		for (Map.Entry<String, List<InvoiceinsImportBean>> each : map.entrySet()) {
			List<InvoiceinsImportBean> elist = each.getValue();

			InvoiceinsImportBean first = elist.get(0);
			_logger.info("***key***"+each.getKey()+"***value size***"+elist);

			// Assemble invoiceinsBean/invoiceinsItemBean/insVSOutBean/InsVSInvoiceNumBean
			InvoiceinsBean bean = new InvoiceinsBean();

			bean.setId(commonDAO.getSquenceString20());
			bean.setInvoiceDate(first.getInvoiceDate());
			bean.setHeadType(0);
			bean.setHeadContent(first.getInvoiceHead());
			bean.setInvoiceId(first.getInvoiceId());
			bean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
			bean.setCustomerId(first.getCustomerId());
			bean.setDescription("批量导入生成开票申请");
			bean.setInsAmount(1);
			bean.setLogTime(TimeTools.now());
			bean.setLocationId("999");

			bean.setGfmc(first.getGfmc());
			bean.setGfsh(first.getGfsh());
			bean.setGfyh(first.getGfyh());
			bean.setGfdz(first.getGfdz());
			bean.setFpgg(first.getFpgg());
			bean.setFpdw(first.getFpdw());
			bean.setZzsInfo(first.getZzsInfo());

			//2015/2/1 票随货发
			bean.setInvoiceFollowOut(first.getInvoiceFollowOut());
			bean.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
			bean.setOperator(StafferConstant.SUPER_STAFFER);
			bean.setOperatorName("系统");

            //处理人员默认为”杨静“
            StafferBean staffer = this.stafferDAO.findyStafferByName("杨静");
            if (staffer == null){
			    bean.setProcesser(duty.getInvoicer());
            } else{
                bean.setProcesser(staffer.getId());
            }
            //mod by zhangxian 导入后状态置为结束 2019-10-11
			bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END); // 待财务开票
			//end mod
			bean.setStafferId(first.getStafferId());
			bean.setType(0);
			bean.setOtype(0);

			List<InvoiceinsItemBean> itemList = new ArrayList<InvoiceinsItemBean>();

			List<InsVSOutBean> vsList = new ArrayList<InsVSOutBean>();

			List<InsVSInvoiceNumBean> numList = new ArrayList<InsVSInvoiceNumBean>();

			bean.setItemList(itemList);
			bean.setVsList(vsList);
			bean.setNumList(numList);

			double invoicemoney = 0.0d;
			StringBuilder sb = new StringBuilder();

            StringBuilder sb2 = new StringBuilder();
            StringBuilder sb3 = new StringBuilder();
			for (InvoiceinsImportBean eachb : elist) {

				invoicemoney += eachb.getInvoiceMoney();
				sb.append(eachb.getOutId());
				sb.append(";");

				//#493 多单开一张票时,去除重复描述
				if(sb2.indexOf(eachb.getDescription()) == -1){
                    sb2.append(eachb.getDescription());
                    sb2.append(";");
                }

                if(sb3.indexOf(eachb.getOtherDescription()) == -1){
                    sb3.append(eachb.getOtherDescription());
                    sb3.append(";");
                }

				if (eachb.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
					String outId = eachb.getOutId();
					List<BaseBean> baseList = null;
					if (baseMap.containsKey(outId)){
						baseList = baseMap.get(outId);
					} else{
						baseList = baseDAO.queryEntityBeansByFK(eachb.getOutId());
						baseMap.put(outId, baseList);
					}

					// eliminate return
					ConditionParse con = new ConditionParse();

					con.addWhereStr();

					con.addCondition("OutBean.refOutFullId", "=", eachb.getOutId());

					con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

					con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OUTBACK);

					List<OutBean> refOutList = outDAO.queryEntityBeansByCondition(con);

					for (OutBean eacho : refOutList)
					{
						List<BaseBean> blist = baseDAO.queryEntityBeansByFK(eacho.getFullId());

						eacho.setBaseList(blist);
					}

					// 计算出已经退货的数量
					for (BaseBean baseBean : baseList)
					{
						int hasBack = 0;

						// 退库
						for (OutBean ref : refOutList)
						{
							for (BaseBean refBase : ref.getBaseList())
							{
								if (refBase.equals2(baseBean))
								{
									hasBack += refBase.getAmount();
								}
							}
						}

						baseBean.setAmount(baseBean.getAmount() - hasBack);
					}

					double vsMoney = 0.0d;
                    //#169 2016/3/2 对于拆分开票，根据导入模板中商品+数量来生成item表
                    if (eachb.isSplitFlag()){
                        List<BaseBean> baseBeans = this.getBaseBeanByProduct(baseList,eachb);
                        if (ListTools.isEmptyOrNull(baseBeans)){
                            _logger.error("DO NOT find baseId for***"+eachb);
                            throw new MYException("导入开票信息和商品行不一致，无法开票："+eachb);
                        } else{
                            _logger.info(eachb+"***begin to create InvoiceinsItemBean size***"+baseBeans.size());
                            for (BaseBean baseBean:baseBeans){
                                InvoiceinsItemBean item = new InvoiceinsItemBean();

                                item.setId(commonDAO.getSquenceString20());
                                item.setParentId(bean.getId());
                                item.setShowId("10201103130001000189");
                                item.setShowName("纪念品");
                                item.setUnit("8874797");
                                //TODO
                                if (baseBean.getAmount() == 0){
                                    item.setAmount(eachb.getAmount());
                                } else{
                                    item.setAmount(baseBean.getAmount());
                                }

                                item.setPrice(baseBean.getPrice());
                                item.setBaseId(baseBean.getId());
                                item.setCostPrice(baseBean.getCostPrice());

                                if(eachb.getProductName().indexOf("+") == -1){
                                    item.setMoneys(item.getAmount() * item.getPrice());
                                } else{
                                    //#863混合商品直接取开票金额
                                    item.setMoneys(eachb.getInvoiceMoney());
                                }

                                item.setOutId(eachb.getOutId());
                                item.setProductId(eachb.getProductId());
                                item.setType(eachb.getType());
                                //#753
                                item.setSpmc(eachb.getSpmc());

                                _logger.info("生成发票项:"+item);
                                itemList.add(item);

                                vsMoney += item.getMoneys();
                            }
                        }

                    } else{
                        //其他根据base表获取
                        for (BaseBean eachitem : baseList) {
//                            _logger.info("***baseBean***"+eachitem);
                            if (eachitem.getAmount() > 0) {
                                InvoiceinsItemBean item = new InvoiceinsItemBean();

                                item.setId(commonDAO.getSquenceString20());
                                item.setParentId(bean.getId());
                                item.setShowId("10201103130001000189");
                                item.setShowName("纪念品");
                                item.setUnit("8874797");
                                item.setAmount(eachitem.getAmount());
                                item.setPrice(eachitem.getPrice());
                                item.setMoneys(item.getAmount() * item.getPrice());
                                item.setOutId(eachitem.getOutId());
                                item.setBaseId(eachitem.getId());
                                item.setProductId(eachitem.getProductId());
                                item.setType(eachb.getType());
                                item.setCostPrice(eachitem.getCostPrice());
                                //#753
                                item.setSpmc(eachb.getSpmc());
                                _logger.info("生成发票项:"+item);
                                itemList.add(item);

                                vsMoney += item.getMoneys();
                            }
                        }
                    }


					InsVSOutBean vsBean = new InsVSOutBean();

					vsBean.setId(commonDAO.getSquenceString20());
					vsBean.setInsId(bean.getId());
					vsBean.setOutId(eachb.getOutId());
					vsBean.setType(eachb.getType());
					vsBean.setMoneys(vsMoney);
					vsBean.setBaseId("0"); // 标记

					vsList.add(vsBean);

				} else {
					// 结算单
					OutBalanceBean balance = outBalanceDAO.find(eachb.getOutId());

					List<BaseBalanceBean> baseBalanceList = baseBalanceDAO.queryEntityBeansByFK(balance.getId());

					List<BaseBean> baseList = new ArrayList<BaseBean>();

					for (BaseBalanceBean eachba : baseBalanceList)
					{
						String baseId = eachba.getBaseId();

						BaseBean baseBean = baseDAO.find(baseId);

						if (null != baseBean)
						{
							baseBean.setId(eachba.getId());
							baseBean.setOutId(eachba.getParentId());
							baseBean.setAmount(eachba.getAmount());
							baseBean.setPrice(eachba.getSailPrice());
							baseBean.setMtype(1);
							baseBean.setDescription("");
							baseBean.setLocationId(eachba.getBaseId());

							baseList.add(baseBean);
						}
					}

					// 结算单退货明细
					List<BaseBalanceBean> refList = new ArrayList<BaseBalanceBean>();

					List<OutBalanceBean> balanceList = outBalanceDAO.queryEntityBeansByFK(eachb.getId(), AnoConstant.FK_FIRST);

					for (OutBalanceBean eachob : balanceList)
					{
						List<BaseBalanceBean> bbList = baseBalanceDAO.queryEntityBeansByFK(eachob.getId());

						refList.addAll(bbList);
					}

					for (BaseBean baseBean : baseList)
					{
						int hasBack = 0;

						// 申请开票除外
						for (BaseBalanceBean eachbb : refList)
						{
							if (eachbb.getBaseId().equals(baseBean.getLocationId()))
							{
								hasBack += eachbb.getAmount();
							}
						}

						baseBean.setAmount(baseBean.getAmount() - hasBack);
					}

					double vsMoney = 0.0d;
					for (BaseBean eachitem : baseList) {
						if (eachitem.getAmount() > 0) {
							InvoiceinsItemBean item = new InvoiceinsItemBean();

							item.setId(commonDAO.getSquenceString20());
							item.setParentId(bean.getId());
							item.setShowId("10201103130001000189");
							item.setShowName("纪念品");
							item.setUnit("8874797");
							item.setAmount(eachitem.getAmount());
							item.setPrice(eachitem.getPrice());
							item.setMoneys(item.getAmount() * item.getPrice());
							item.setOutId(eachitem.getOutId());
							item.setBaseId(eachitem.getId());
							item.setProductId(eachitem.getProductId());
							item.setType(eachb.getType());
							item.setCostPrice(eachitem.getCostPrice());

							itemList.add(item);

							vsMoney += item.getMoneys();
						}
					}

					InsVSOutBean vsBean = new InsVSOutBean();

					vsBean.setId(commonDAO.getSquenceString20());
					vsBean.setInsId(bean.getId());
					vsBean.setOutId(balance.getOutId());
					vsBean.setOutBalanceId(balance.getId());
					vsBean.setType(eachb.getType());
					vsBean.setMoneys(vsMoney);
					vsBean.setBaseId("0"); // 标记

					vsList.add(vsBean);
				}

				eachb.setRefInsId(bean.getId());

				invoiceinsImportDAO.updateEntityBean(eachb);
			}

			bean.setMoneys(invoicemoney);
			bean.setRefIds(sb.toString());
			bean.setFillType(0);

//			bean.setDescription(first.getDescription());
            bean.setDescription(sb2.toString());
            bean.setOtherDescription(sb3.toString());
			// fill distribution
			if (first.getAddrType() == InvoiceinsConstants.INVOICEINS_DIST_NEW) {
				bean.setShipping(first.getShipping());
				bean.setTransport1(first.getTransport1());
				bean.setTransport2(first.getTransport2());
				bean.setProvinceId(first.getProvinceId());
				bean.setCityId(first.getCityId());
				bean.setAreaId(first.getAreaId());
				bean.setAddress(first.getAddress());
				bean.setReceiver(first.getReceiver());
				bean.setMobile(first.getMobile());
				bean.setTelephone(first.getTelephone());
				bean.setExpressPay(first.getExpressPay());
				bean.setTransportPay(first.getTransportPay());
			} else {
				bean.setFillType(InvoiceinsConstants.INVOICEINS_DIST_SAME);
			}

			setDistFromDist(bean);

			// numList
			InsVSInvoiceNumBean num = new InsVSInvoiceNumBean();

			num.setInsId(bean.getId());
			num.setMoneys(bean.getMoneys());
			//2016/3/4 导入时虚拟发票号不能再写入发票表了
//			num.setInvoiceNum(first.getInvoiceNum());

            //#328 生成临时发票号写入发票表，供CK单打印用
            String tempInvoiceNum = commonDAO.getSquenceString20("XN");;
            num.setInvoiceNum(tempInvoiceNum);
            _logger.info(bean.getId()+"生成临时发票号:"+num);

			numList.add(num);

			invoiceinsDAO.saveEntityBean(bean);
            _logger.info("生成发票:"+bean);

            if (ListTools.isEmptyOrNull(itemList)){
                _logger.error("发票子项未生成:"+bean.getId());
            } else {
//                invoiceinsItemDAO.saveAllEntityBeans(itemList);
                //#695
                invoiceinsItemDAO.saveAllEntityBeans(this.mergeItems(itemList));
                _logger.info("生成发票子项列表:"+itemList);
            }

			insVSOutDAO.saveAllEntityBeans(vsList);
            _logger.info(bean.getId()+"生成insVSOut表:"+vsList);
			insVSInvoiceNumDAO.saveAllEntityBeans(numList);

			invoiceinsList.add(bean);

		}
	}

    /**
     * #738 多个商品合并开票时把开票品名也合并(并且去重)
     * @param list
     * @return
     */
	private String concatSpmc(List<InvoiceinsImportBean> list){
	    Set<String> set = new HashSet<>();
	    StringBuilder sb = new StringBuilder();
        for (InvoiceinsImportBean importBean: list){
            String spmc = importBean.getSpmc();
            if (!StringUtils.isEmpty(spmc) && !set.contains(spmc)){
                sb.append(spmc).append(",");
                set.add(spmc);
            }
        }
        String spmc = sb.toString();
        return StringUtils.removeEnd(spmc, ",");
    }

	//#695 开票明细invoiceins_item表，针对同一商品不同成本合并为一行
	private List<InvoiceinsItemBean> mergeItems(List<InvoiceinsItemBean> items){
        List<InvoiceinsItemBean> result = new ArrayList<>();
        //<insId_fullId_productId, amount>
        Map<String, InvoiceinsItemBean> map = new HashMap<>();
        for (InvoiceinsItemBean item: items){
            String key = item.getParentId()+item.getOutId()+"_"+item.getProductId();
            if (map.containsKey(key)){
                InvoiceinsItemBean i = map.get(key);
                i.setAmount(i.getAmount()+item.getAmount());
                i.setMoneys(i.getAmount()*i.getPrice());
                //#732 把原商品行的baseId也合并
                i.setBaseId(item.getBaseId()+","+i.getBaseId());
            } else{
                map.put(key, item);
            }
        }

        result.addAll(map.values());
        return result;
    }

    /**
     * 根据开票导入配置找到对应的base表记录
     * @param baseBeans
     * @param bean
     * @return
     */
    private List<BaseBean> getBaseBeanByProduct(List<BaseBean> baseBeans, InvoiceinsImportBean bean){
        List<BaseBean> result = new ArrayList<BaseBean>();
		//首先根据productId+amount相等的优先
        for (BaseBean baseBean : baseBeans){
            if (baseBean.getProductId().equals(bean.getProductId())
					&& baseBean.getAmount() == bean.getAmount()
                    //#732 base表已开票金额必须<base表可开票金额
                    && baseBean.getInvoiceMoney() < baseBean.getValue()
                    //尚未开票的base表
					&& baseBean.getTempInvoiceMoney() <0.01){
				baseBean.setTempInvoiceMoney(bean.getInvoiceMoney());
                result.add(baseBean);
                return result;
            }
        }
        // 开票行和base表商品行不是一一对应的情况
        int total = 0;
        int totalCount = 0;
		for (BaseBean baseBean : baseBeans){
			if (baseBean.getProductId().equals(bean.getProductId())
                    //base表可开票金额大于0
                    && (baseBean.getValue() - baseBean.getTempInvoiceMoney() > 0.01)){
                double needInvoiceMoney = bean.getInvoiceMoney()-total;
                //case1: base表可开票金额大于需开票金额(导入金额-已开金额)
                if (baseBean.getValue() - baseBean.getTempInvoiceMoney() >= needInvoiceMoney ){
                    baseBean.setTempInvoiceMoney(baseBean.getTempInvoiceMoney() + needInvoiceMoney);

                    BaseBean newBase = new BaseBean();
                    BeanUtil.copyProperties(newBase, baseBean);
                    //数量根据导入数量-已开数量
                    newBase.setAmount(bean.getAmount()-totalCount);
                    result.add(newBase);
                    _logger.info("****case1***"+newBase);
                    return result;
                } else{
                    //case2: base表可开票金额小于需开票金额
                    //数量是base表单条记录的可开票数量
                    double invoiceMoney = baseBean.getValue()-baseBean.getTempInvoiceMoney();
                    int amount = Double.valueOf(invoiceMoney/baseBean.getPrice()).intValue();
                    baseBean.setTempInvoiceMoney(baseBean.getValue());
                    BaseBean newBase = new BaseBean();
                    BeanUtil.copyProperties(newBase, baseBean);
                    newBase.setAmount(amount);
                    _logger.info("****case2***"+newBase);
                    result.add(newBase);
                    total += invoiceMoney;
                    totalCount += amount;
                }
//                //case1: 开票金额小于base表可开票金额
//                if(bean.getInvoiceMoney() <=  baseBean.getValue() - baseBean.getTempInvoiceMoney()) {
//                    baseBean.setTempInvoiceMoney(baseBean.getTempInvoiceMoney() + bean.getInvoiceMoney());
//
//                    BaseBean newBase = new BaseBean();
//                    BeanUtil.copyProperties(newBase, baseBean);
//                    //数量根据导入数量
//                    newBase.setAmount(bean.getAmount());
//                    result.add(newBase);
//                    _logger.info("****case1***"+newBase);
//                    return result;
//                } else{
//                    //case2: 开票金额大于base表单条记录可开票金额,则需要拆分
//                    //数量是base表单条记录的可开票数量
//                    _logger.info("****baseBean***"+baseBean);
//                    int amount = Double.valueOf((baseBean.getValue()-baseBean.getTempInvoiceMoney())/baseBean.getPrice()).intValue();
//                    baseBean.setTempInvoiceMoney(baseBean.getValue());
//                    BaseBean newBase = new BaseBean();
//                    BeanUtil.copyProperties(newBase, baseBean);
//                    newBase.setAmount(amount);
//                    _logger.info("****case2***"+newBase);
//                    result.add(newBase);
//                }
			}
		}

        return result;
    }


	@Override
    public boolean processAsyn2(final List<InvoiceinsImportBean> invoiceinsImportBeans) {
        Thread ithread = new Thread() {
            public void run() {
                try
                {
                    process2(invoiceinsImportBeans);
                }
                catch (MYException e)
                {
                    operationLog.warn(e, e);
                }
            };
        };

        ithread.start();

        return true;
    }

    @Deprecated
    public boolean processAsyn(final List<InvoiceinsImportBean> list)
	{
		Thread ithread = new Thread() {
			public void run() {
				try
				{
					process(list);
				}
				catch (MYException e)
				{
					operationLog.warn(e, e);
				}
			};
		};
		
		ithread.start();
		
		return true;
	}

    @Override
    public void autoApproveJob() throws MYException {
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addWhereStr();
		conditionParse.addCondition("type", "=", OutConstant.OUT_TYPE_OUTBILL);
        conditionParse.addCondition("status", "=", OutConstant.STATUS_FLOW_PASS);
        List<OutBean> beans = this.outDAO.queryEntityBeansByCondition(conditionParse);
        if (!ListTools.isEmptyOrNull(beans)){
            _logger.info("***autoApproveJob with out size***"+beans.size());
            for (OutBean bean : beans){
                String outId = bean.getFullId();
                boolean result = this.autoApproveOut(true, outId);
                _logger.info(outId+"*****passOut result****"+result);
                if (result){
                    //并检查与上述自动审批的SO单配送信息一致的订单，如有，则一并自动审批通过
                    //根据SO单customerId+PS表上的receiver+mobile一致即可，ZS单不需要已开票
                    List<DistributionBean> distributionBeans = distributionDAO.queryEntityBeansByFK(outId);
                    if (!ListTools.isEmptyOrNull(distributionBeans)) {
                        DistributionBean distributionBean = distributionBeans.get(0);
                        String receiver = distributionBean.getReceiver();
                        String mobile = distributionBean.getMobile();
						ConditionParse con1 = new ConditionParse();
                        con1.addWhereStr();
                        con1.addCondition("OutBean.customerId", "=", bean.getCustomerId());
                        con1.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
                        con1.addCondition(" and OutBean.outtype not in (0,2) ");
                        con1.addIntCondition("OutBean.status", "=", OutConstant.STATUS_FLOW_PASS);
                        con1.addCondition(" and exists (select dis.id from t_center_distribution dis " +
								"where dis.outId=OutBean.fullId and " +
								"dis.receiver='" + receiver + "' and " +
								"dis.mobile='" + mobile + "')");
						List<OutBean> outBeans = this.outDAO.queryEntityBeansByCondition(con1);
                        if (ListTools.isEmptyOrNull(outBeans)){
                            _logger.info("****No same address SO exists****");
                        } else{
                            _logger.info("****same address SO need to auto approve****"+outBeans.size());
                            for (OutBean o: outBeans){
                                String fullId = o.getFullId();
                                boolean pass = this.autoApproveOut(false, fullId);
								_logger.info(fullId+"*****passOut result****"+pass);
                            }
                        }
                    }
                }
            }
        }else{
            _logger.info("***autoApproveJob running without orders***");
        }
    }

    @Override
	@Deprecated
    public void insFollowOutJob() throws MYException {
        //To change body of implemented methods use File | Settings | File Templates.
        String msg = "*****票随货发insFollowOutJob running***************";
        _logger.info(msg);
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addWhereStr();
        conditionParse.addCondition("invoiceFollowOut", "=", InvoiceinsImportBean.INVOICE_FOLLOW_OUT);
        conditionParse.addIntCondition("packaged", "=", 0);
        List<InvoiceinsBean> beans = this.invoiceinsDAO.queryEntityBeansByCondition(conditionParse);
        if (!ListTools.isEmptyOrNull(beans)){
            _logger.info("********票随货发发票数量******"+beans.size());
            for (InvoiceinsBean bean : beans){
                String insId = bean.getId();
                List<String>  outIdList = new ArrayList<String>();
                ConditionParse condition = new ConditionParse();
                condition.addWhereStr();
                condition.addCondition("insId", "=", insId);
                List<InsVSOutBean> insVSOutBeans = insVSOutDAO.queryEntityBeansByCondition(condition);
                if (!ListTools.isEmptyOrNull(insVSOutBeans)){
                    _logger.info(insId+"****with InsVSOutBean size****"+insVSOutBeans.size());
                    for (InsVSOutBean vs : insVSOutBeans){
						String outId = vs.getOutId();
                       boolean result = this.passOut(insId, outId);
                       _logger.info(outId+"*****passOut result****"+result);
                       if (result){
                           _logger.info("****outId to be packaged***"+outId);

                           //2015/2/15 发票单在导入时已进入中间表
                           if (!outIdList.contains(insId)){
                               outIdList.add(insId);
                               _logger.info("****InsID to be packaged***"+insId);
                           }


                           //并检查待库管审批状态的订单地址有无与发票地址一致的订单，如有，则一并自动审批通过
                           String invoiceAddress = bean.getAddress();
                           ConditionParse con1 = new ConditionParse();
                           con1.addWhereStr();
                           con1.addIntCondition("OutBean.status", "=", OutConstant.STATUS_FLOW_PASS);
                           con1.addCondition(" and exists (select dis.* from t_center_distribution dis where dis.outId=OutBean.fullId and dis.address='" + invoiceAddress + "')");
                           List<OutBean> outBeans = this.outDAO.queryEntityBeansByCondition(con1);
                           if (ListTools.isEmptyOrNull(outBeans)){
                               _logger.info("****No same address SO exists****");
                           } else{
                               for (OutBean o: outBeans){
                                   String fullId = o.getFullId();
                                   boolean pass = this.passOut(insId, fullId);
                                   if (pass && !outIdList.contains(fullId)){
//                                       outIdList.add(fullId);
                                       _logger.info("****same address outId to be packaged***"+fullId);
                                   }
                               }
                           }
                       }
                    }
                }

                //与开票申请一并生成CK单，此类订单不再经过中间表过渡生成CK单-->2015/2/15 改为写入中间表
                if (!ListTools.isEmptyOrNull(outIdList)){
                    _logger.info(insId+"****createPackage with OUT size******"+outIdList.size());
                    //2015/2/15 更新InvoiceinsBean.packaged状态
                    this.packageManager.createPackage(outIdList);
                }
            }
        }
    }

	//若销售单状态为“待库管审批”，则将对应的销售单通过库管审批（正常生成凭证及库存扣减）
	private boolean autoApproveOut(boolean checkInvoiceStatus, String outId){
		boolean result = false;
		OutBean out = this.outDAO.find(outId);

		if (out == null){
			_logger.warn("****no SO found****"+outId);
			return false;
		} else{
			_logger.info(outId+"****try to passOut with status****"+out.getStatus());
		}

		if (out!= null && out.getStatus() == OutConstant.STATUS_FLOW_PASS){
			if (checkInvoiceStatus){
				//#169 只把已导入发票号关联的销售单审批过去
//				ConditionParse condition = new ConditionParse();
//				condition.addWhereStr();
//				condition.addCondition(" and exists ( select InsVSOutBean.* from T_CENTER_VS_INSOUT InsVSOutBean " +
//						"where InsVSOutBean.insId=InsVSInvoiceNumBean.insId and InsVSOutBean.outId='" +
//						outId + "')");
//				List<InsVSInvoiceNumBean> insVSInvoiceNumBeans = insVSInvoiceNumDAO.queryEntityBeansByCondition(condition);
                List<InsVSInvoiceNumBean> insVSInvoiceNumBeans = this.getInvoiceNumByOutId(outId);
				if (ListTools.isEmptyOrNull(insVSInvoiceNumBeans)){
					_logger.warn("***No InsVSInvoiceNumBean found***"+outId);
					return false;
				}else{
                    InsVSInvoiceNumBean insVSInvoiceNumBean = insVSInvoiceNumBeans.get(0);
                    String invoiceNum = insVSInvoiceNumBean.getInvoiceNum();
                    if (StringTools.isNullOrNone(invoiceNum)){
                        _logger.warn("***Empty invoiceNum***"+outId);
                        return false;
                    } else if(invoiceNum.startsWith("XN")){
                        //如果是XN号码，需要发票已审批通过才能审批对应销售单
                        InvoiceinsBean invoiceinsBean = this.invoiceinsDAO.find(insVSInvoiceNumBean.getInsId());
                        if (invoiceinsBean.getStatus() != FinanceConstant.INVOICEINS_STATUS_END){
                            _logger.warn("***XN invoiceNum***"+invoiceNum);
                            return false;
                        }
                    }
                }
			}

			final int statuss = 3;
			if (statuss == OutConstant.STATUS_MANAGER_PASS
					|| statuss == OutConstant.STATUS_FLOW_PASS
					|| statuss == OutConstant.STATUS_PASS)
			{
				// 这里需要计算客户的信用金额-是否报送物流中心经理审批
				boolean outCredit = parameterDAO.getBoolean(SysConfigConstant.OUT_CREDIT);

				// 如果是黑名单的客户(且没有付款)
				if (outCredit && out.getReserve3() == OutConstant.OUT_SAIL_TYPE_MONEY
						&& out.getType() == OutConstant.OUT_TYPE_OUTBILL
						&& out.getPay() == OutConstant.PAY_NOT)
				{
					try
					{
						outManager.payOut(null, outId, "结算中心确定已经回款", 0);
					}
					catch (MYException e)
					{
						_logger.error(outId+"****自动库管审批出错****",e);
						return result;
					}
				}

				int resultStatus = -1;
				try
				{
					resultStatus = outManager.pass(outId, null, statuss, "票随货发Job自动审批通过", null,null);
					OutBean newOut = outDAO.find(outId);
					if(resultStatus == OutConstant.STATUS_PASS)
					{
						outManager.updateCusAndBusVal(newOut,"票随货发Job");
					}
					result = true;

				}
				catch (MYException e)
				{
					_logger.warn(e, e);
				}
			}

		}
		return result;
	}

    /**
     * #233 以两个查询代替exists连接查询
     * @param outId
     * @return
     */
	private List<InsVSInvoiceNumBean> getInvoiceNumByOutId(String outId){
        List<InsVSInvoiceNumBean> result = new ArrayList<InsVSInvoiceNumBean>();
	    List<InsVSOutBean> insVSOutBeans = this.insVSOutDAO.queryEntityBeansByFK(outId);
	    if (!ListTools.isEmptyOrNull(insVSOutBeans)){
            for (InsVSOutBean insVSOutBean: insVSOutBeans){
                List<InsVSInvoiceNumBean> insVSInvoiceNumBeans = insVSInvoiceNumDAO.queryEntityBeansByFK(insVSOutBean.getInsId());
                if(!ListTools.isEmptyOrNull(insVSInvoiceNumBeans)){
                    result.addAll(insVSInvoiceNumBeans);
                }
            }
        }

        return result;
    }

    //若销售单状态为“待库管审批”，则将对应的销售单通过库管审批（正常生成凭证及库存扣减）
    private boolean passOut(String insId, String outId){
        boolean result = false;
        OutBean out = this.outDAO.find(outId);

        if (out == null){
            _logger.warn("****no SO found****"+outId);
            return false;
        } else{
            _logger.info(outId+"****try to passOut with status****"+out.getStatus());
        }

        if (out!= null && out.getStatus() == OutConstant.STATUS_FLOW_PASS){
            _logger.info("****自动库管审批通过*****" + outId);

			//#169 只把已导入发票号关联的销售单审批过去
			ConditionParse condition = new ConditionParse();
			condition.addWhereStr();
			condition.addCondition("insId", "=", insId);
			List<InsVSInvoiceNumBean> insVSInvoiceNumBeans = insVSInvoiceNumDAO.queryEntityBeansByCondition(condition);
			if (ListTools.isEmptyOrNull(insVSInvoiceNumBeans)
					|| StringTools.isNullOrNone(insVSInvoiceNumBeans.get(0).getInvoiceNum())){
				_logger.warn(insId+"***No InsVSInvoiceNumBean found***"+outId);
				return false;
			}

            final int statuss = 3;
            if (statuss == OutConstant.STATUS_MANAGER_PASS
                    || statuss == OutConstant.STATUS_FLOW_PASS
                    || statuss == OutConstant.STATUS_PASS)
            {
                // 这里需要计算客户的信用金额-是否报送物流中心经理审批
                boolean outCredit = parameterDAO.getBoolean(SysConfigConstant.OUT_CREDIT);

                // 如果是黑名单的客户(且没有付款)
                if (outCredit && out.getReserve3() == OutConstant.OUT_SAIL_TYPE_MONEY
                        && out.getType() == OutConstant.OUT_TYPE_OUTBILL
                        && out.getPay() == OutConstant.PAY_NOT)
                {
                    try
                    {
                        outManager.payOut(null, outId, "结算中心确定已经回款", 0);
                    }
                    catch (MYException e)
                    {
                        _logger.error(outId+"****自动库管审批出错****",e);
                        return result;
                    }
                }

                int resultStatus = -1;
                try
                {
                    resultStatus = outManager.pass(outId, null, statuss, "票随货发Job自动审批通过", null,null);
                    OutBean newOut = outDAO.find(outId);
                    if(resultStatus == OutConstant.STATUS_PASS)
                    {
                        outManager.updateCusAndBusVal(newOut,"票随货发Job");
                    }
                    result = true;

					// #187 紫金订单zjrcout的状态自动变为3
					if ("ZJRC".equals(out.getFlowId())){
						String zjrcFullId = out.getZjrcFullId();
						if (StringTools.isNullOrNone(zjrcFullId)){
							_logger.warn(out.getFullId() + "***can not get ZJRC ID***");
						} else{
                            _logger.info("更新紫金订单状态:"+zjrcFullId);
							this.outManager.updateZjrcOutStatus(zjrcFullId);
						}
					}
                }
                catch (MYException e)
                {
                    _logger.warn(e, e);
                }
            }

        }
        return result;
    }

    @Transactional(rollbackFor = {MYException.class})
    @Override
    public boolean updateEmergency(User user, String fullId) throws MYException {
        try{
            List<PackageBean> packages = this.packageDAO.queryPackagesByOutId(fullId);
            if (!ListTools.isEmptyOrNull(packages)){
                for (PackageBean pack: packages){
                    pack.setEmergency(1);
                    this.packageDAO.updateEntityBean(pack);
                    _logger.info(String.format("CK单更新为紧急:%s 销售单:%s",pack.getId(), fullId));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            _logger.error("***CK update emergency fail:",e);
            return false;
        }
        return true;
    }


    @Transactional(rollbackFor = MYException.class)
    @Override
    public boolean batchTransferInvoiceins(List<OutTransferBean> outTransferBeans) throws MYException {
        if (!ListTools.isEmptyOrNull(outTransferBeans)){
            for(OutTransferBean bean : outTransferBeans){
                List<InsVSOutBean> insList = insVSOutDAO.queryEntityBeansByFK(bean.getSrcFullId());
                //将原销售单关联的开票单号移到新销售单上
                if (!ListTools.isEmptyOrNull(insList)){
                    for (InsVSOutBean insVSOutBean: insList){
                        insVSOutBean.setOutId(bean.getDestFullId());
                        this.insVSOutDAO.updateEntityBean(insVSOutBean);
                        String template = "transfer InsVSOutBean %s:%s from source %s to destination %s";
                        _logger.info(String.format(template, insVSOutBean.getId(), insVSOutBean.getInsId(),
                                bean.getSrcFullId(), bean.getDestFullId()));

                        //开票单上的关联销售单由原销售单转为新销售单
                        InvoiceinsBean invoiceinsBean = this.invoiceinsDAO.find(insVSOutBean.getInsId());
                        if (invoiceinsBean!= null){
                            invoiceinsBean.setRefIds(invoiceinsBean.getRefIds().replace(bean.getSrcFullId(), bean.getDestFullId()));
                            this.invoiceinsDAO.updateEntityBean(invoiceinsBean);
                            String template2 = "transfer InvoiceinsBean %s from source %s to destination %s";
                            _logger.info(String.format(template2, invoiceinsBean.getId(),
                                    bean.getSrcFullId(), bean.getDestFullId()));
                        }
                    }
                }

                List<InvoiceinsItemBean> insItemList = invoiceinsItemDAO.queryEntityBeansByCondition("where InvoiceinsItemBean.outId = ?", bean.getSrcFullId());

                if (!ListTools.isEmptyOrNull(insItemList)) {
                    for (InvoiceinsItemBean invoiceinsItemBean:insItemList){
                        invoiceinsItemBean.setOutId(bean.getDestFullId());
                        this.invoiceinsItemDAO.updateEntityBean(invoiceinsItemBean);
                        String template = "transfer InvoiceinsItemBean %s:%s from source %s to destination %s";
                        _logger.info(String.format(template, invoiceinsItemBean.getId(), invoiceinsItemBean.getParentId(),
                                bean.getSrcFullId(), bean.getDestFullId()));
                    }
                }

				//原销售单把 out 表里的 invoicestatus置为0，invoicemoneyl置为0
				//base表的invoicemoneyl置为0
				//原数值拷贝到新销售单对应字段上去
				OutBean oldOut = this.outDAO.find(bean.getSrcFullId());
				OutBean newOut = this.outDAO.find(bean.getDestFullId());

				if (oldOut == null || newOut == null){
					throw new MYException("OutBean does not exist:"+bean.getSrcFullId()+":"+bean.getDestFullId());
				} else{
					//copy base value
					List<BaseBean> oldBaseList = baseDAO.queryEntityBeansByFK(bean.getSrcFullId());
                    List<BaseBean> newBaseList = baseDAO.queryEntityBeansByFK(bean.getDestFullId());
                    for (BaseBean baseBean: newBaseList){
                        this.copyInvoiceMoney(baseBean, oldBaseList);
                    }

					newOut.setInvoiceStatus(oldOut.getInvoiceStatus());
					newOut.setInvoiceMoney(oldOut.getInvoiceMoney());
					this.outDAO.updateEntityBean(newOut);
					_logger.info(newOut+"更新开票状态及开票金额:"+newOut.getInvoiceMoney());

                    oldOut.setInvoiceStatus(0);
                    oldOut.setInvoiceMoney(0);
                    this.outDAO.updateEntityBean(oldOut);
                    _logger.info(oldOut+"开票状态及金额重置");
				}
            }

            return true;
        }


        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void copyInvoiceMoney(BaseBean baseBean , List<BaseBean> srcList){
        for (BaseBean base: srcList){
            if (base.equals(baseBean)){
                baseBean.setInvoiceMoney(base.getInvoiceMoney());
                this.baseDAO.updateEntityBean(baseBean);
                _logger.info("Update BaseBean invoice money:"+baseBean);

                base.setInvoiceMoney(0);
                this.baseDAO.updateEntityBean(base);
                _logger.info("Reset BaseBean invoice money:"+base);
                break;
            }
        }
    }

	@Override
	@Transactional(rollbackFor = MYException.class)
	public boolean batchConfirm(User user, List<InvoiceinsVO> list) throws MYException {
		for (InvoiceinsVO vo :list){
			if (vo.getOtype() == FinanceConstant.INVOICEINS_TYPE_OUT) {
				InvoiceinsBean bean = this.invoiceinsDAO.find(vo.getId());
				bean.setStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
				this.invoiceinsDAO.updateEntityBean(bean);

				FlowLogBean log = new FlowLogBean();
				log.setActor(user.getStafferName());
				log.setActorId(user.getStafferId());
				log.setFullId(vo.getId());
				log.setDescription("批量审批通过");
				log.setLogTime(TimeTools.now());
				log.setPreStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
				log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_CONFIRM);
				log.setOprMode(PublicConstant.OPRMODE_PASS);

				flowLogDAO.saveEntityBean(log);
			}
		}
		return true;
	}

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean batchConfirmAndCreatePackage(User user, List<InvoiceinsVO> beanList) throws MYException {
        try {
            for (InvoiceinsVO vo : beanList) {
                if (vo.getOtype() == FinanceConstant.INVOICEINS_TYPE_OUT) {
                    InvoiceinsBean bean = this.invoiceinsDAO.find(vo.getId());
                    bean.setStatus(FinanceConstant.INVOICEINS_STATUS_END);
                    this.invoiceinsDAO.updateEntityBean(bean);

                    FlowLogBean log = new FlowLogBean();
                    log.setActor(user.getStafferName());
                    log.setActorId(user.getStafferId());
                    log.setFullId(vo.getId());
                    log.setDescription("批量审批通过");
                    log.setLogTime(TimeTools.now());
                    log.setPreStatus(FinanceConstant.INVOICEINS_STATUS_SUBMIT);
                    log.setAfterStatus(FinanceConstant.INVOICEINS_STATUS_END);
                    log.setOprMode(PublicConstant.OPRMODE_PASS);

                    flowLogDAO.saveEntityBean(log);

                    if (bean != null ) {
                        //如果票随货发，就不写入preconsign表，写入临时表
                        if (InvoiceinsImportBean.INVOICE_FOLLOW_OUT.equals(bean.getInvoiceFollowOut())) {
                            //如果是票随货发，发票对应所有订单已经库管审批通过，那发票也直接写入preconsign
                            String refIds = bean.getRefIds();
                            if (!StringTools.isNullOrNone(refIds)) {
                                String[] fullIds = refIds.split(";");
                                boolean flag = false;
                                for (String fullId : fullIds) {
                                    OutBean outBean = outDAO.find(fullId);
                                    //如果存在销售单“待商务审批”或"待库管审批"，就写入临时表
                                    if (outBean == null) {
                                        continue;
                                    } else if (outBean.getStatus() == OutConstant.STATUS_SUBMIT ||
                                            outBean.getStatus() == OutConstant.STATUS_FLOW_PASS) {
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag) {
                                    //写入临时表
                                    TempConsignBean tempConsignBean = new TempConsignBean();
                                    tempConsignBean.setOutId(refIds);
                                    tempConsignBean.setInsId(bean.getId());
                                    _logger.info(bean.getId() + "此发票存在销售单为待商务审批或待库管审批,写入T_CENTER_TEMPCONSIGN表:" + tempConsignBean);
                                    this.tempConsignDAO.saveEntityBean(tempConsignBean);
                                } else {
                                    this.createPackage(bean);
                                }
                            }
                        } else {
                            this.createPackage(bean);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            _logger.error(e);
        }
        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public void generateInvoiceins(String packageId, String insId, String fphm) {
        // 取返回的发票号码，写入对应的A单号中替换XN号码
        List<InsVSInvoiceNumBean> numList = insVSInvoiceNumDAO.queryEntityBeansByFK(insId);
        if (!ListTools.isEmptyOrNull(numList)){
            for (InsVSInvoiceNumBean item: numList){
                InsVSInvoiceNumBean insNum = insVSInvoiceNumDAO.find(item.getId());

                //2016/10/11 #328 以真实发票号码替换package_item中对应的产品名中的临时发票号码
                if (!StringTools.isNullOrNone(insNum.getInvoiceNum())){
                    String outId = insNum.getInsId();
                    this.packageItemDAO.replaceInvoiceNum(outId, insNum.getInvoiceNum(), fphm);
                }

                insNum.setInvoiceNum(fphm);
                insVSInvoiceNumDAO.updateEntityBean(insNum);
                _logger.info("***update invoice num***"+insNum);
            }
        }

        if (StringTools.isNullOrNone(packageId)){
            //根据批次打印时先找到对应的CK单
            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addCondition("outId","=",insId);
            conditionParse.addCondition("productName","=","发票号："+fphm);
            List<PackageItemBean> packageItemBeans = this.packageItemDAO.queryEntityBeansByCondition(conditionParse);
            if (!ListTools.isEmptyOrNull(packageItemBeans)){
                packageId = packageItemBeans.get(0).getPackageId();
                _logger.info("packageId***"+packageId);
            }
        }

        //# CK单中的全部虚拟号码替换成真实发票号后，更新CK单状态为已捡配
        List<PackageItemBean> packageItemBeanList = this.packageItemDAO.queryEntityBeansByFK(packageId);
        if (!ListTools.isEmptyOrNull(packageItemBeanList)){
            boolean  flag = true;
            for (PackageItemBean item :packageItemBeanList){
                String productName = item.getProductName();
                if (productName!= null && productName.startsWith("发票号：XN")){
                    flag = false;
                    break;
                }
            }

            if (flag){
                _logger.info(packageId+"****update status***"+ ShipConstant.SHIP_STATUS_PICKUP);
                this.packageDAO.updateStatus(packageId, ShipConstant.SHIP_STATUS_PICKUP);
            }
        }
    }
    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the invoiceinsDAO
     */
    public InvoiceinsDAO getInvoiceinsDAO()
    {
        return invoiceinsDAO;
    }

    /**
     * @param invoiceinsDAO
     *            the invoiceinsDAO to set
     */
    public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO)
    {
        this.invoiceinsDAO = invoiceinsDAO;
    }

    /**
     * @return the invoiceinsItemDAO
     */
    public InvoiceinsItemDAO getInvoiceinsItemDAO()
    {
        return invoiceinsItemDAO;
    }

    /**
     * @param invoiceinsItemDAO
     *            the invoiceinsItemDAO to set
     */
    public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO)
    {
        this.invoiceinsItemDAO = invoiceinsItemDAO;
    }

    /**
     * @return the insVSOutDAO
     */
    public InsVSOutDAO getInsVSOutDAO()
    {
        return insVSOutDAO;
    }

    /**
     * @param insVSOutDAO
     *            the insVSOutDAO to set
     */
    public void setInsVSOutDAO(InsVSOutDAO insVSOutDAO)
    {
        this.insVSOutDAO = insVSOutDAO;
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

    /**
     * @return the outBalanceDAO
     */
    public OutBalanceDAO getOutBalanceDAO()
    {
        return outBalanceDAO;
    }

    /**
     * @param outBalanceDAO
     *            the outBalanceDAO to set
     */
    public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
    {
        this.outBalanceDAO = outBalanceDAO;
    }

    /**
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO()
    {
        return baseDAO;
    }

    /**
     * @param baseDAO
     *            the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO)
    {
        this.baseDAO = baseDAO;
    }

    /**
     * @return the baseBalanceDAO
     */
    public BaseBalanceDAO getBaseBalanceDAO()
    {
        return baseBalanceDAO;
    }

    /**
     * @param baseBalanceDAO
     *            the baseBalanceDAO to set
     */
    public void setBaseBalanceDAO(BaseBalanceDAO baseBalanceDAO)
    {
        this.baseBalanceDAO = baseBalanceDAO;
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }

    /**
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
    }

	/**
	 * @return the invoiceStorageDAO
	 */
	public InvoiceStorageDAO getInvoiceStorageDAO()
	{
		return invoiceStorageDAO;
	}

	/**
	 * @param invoiceStorageDAO the invoiceStorageDAO to set
	 */
	public void setInvoiceStorageDAO(InvoiceStorageDAO invoiceStorageDAO)
	{
		this.invoiceStorageDAO = invoiceStorageDAO;
	}

	/**
	 * @return the invoiceBindOutDAO
	 */
	public InvoiceBindOutDAO getInvoiceBindOutDAO()
	{
		return invoiceBindOutDAO;
	}

	/**
	 * @param invoiceBindOutDAO the invoiceBindOutDAO to set
	 */
	public void setInvoiceBindOutDAO(InvoiceBindOutDAO invoiceBindOutDAO)
	{
		this.invoiceBindOutDAO = invoiceBindOutDAO;
	}

	/**
	 * @return the stockPayApplyDAO
	 */
	public StockPayApplyDAO getStockPayApplyDAO()
	{
		return stockPayApplyDAO;
	}

	/**
	 * @param stockPayApplyDAO the stockPayApplyDAO to set
	 */
	public void setStockPayApplyDAO(StockPayApplyDAO stockPayApplyDAO)
	{
		this.stockPayApplyDAO = stockPayApplyDAO;
	}

	/**
	 * @return the invoiceinsDetailDAO
	 */
	public InvoiceinsDetailDAO getInvoiceinsDetailDAO()
	{
		return invoiceinsDetailDAO;
	}

	/**
	 * @param invoiceinsDetailDAO the invoiceinsDetailDAO to set
	 */
	public void setInvoiceinsDetailDAO(InvoiceinsDetailDAO invoiceinsDetailDAO)
	{
		this.invoiceinsDetailDAO = invoiceinsDetailDAO;
	}

	/**
	 * @return the insVSInvoiceNumDAO
	 */
	public InsVSInvoiceNumDAO getInsVSInvoiceNumDAO()
	{
		return insVSInvoiceNumDAO;
	}

	/**
	 * @param insVSInvoiceNumDAO the insVSInvoiceNumDAO to set
	 */
	public void setInsVSInvoiceNumDAO(InsVSInvoiceNumDAO insVSInvoiceNumDAO)
	{
		this.insVSInvoiceNumDAO = insVSInvoiceNumDAO;
	}

	/**
	 * @return the invoiceinsTagDAO
	 */
	public InvoiceinsTagDAO getInvoiceinsTagDAO()
	{
		return invoiceinsTagDAO;
	}

	/**
	 * @param invoiceinsTagDAO the invoiceinsTagDAO to set
	 */
	public void setInvoiceinsTagDAO(InvoiceinsTagDAO invoiceinsTagDAO)
	{
		this.invoiceinsTagDAO = invoiceinsTagDAO;
	}

	/**
	 * @return the invoiceinsImportDAO
	 */
	public InvoiceinsImportDAO getInvoiceinsImportDAO()
	{
		return invoiceinsImportDAO;
	}

	/**
	 * @param invoiceinsImportDAO the invoiceinsImportDAO to set
	 */
	public void setInvoiceinsImportDAO(InvoiceinsImportDAO invoiceinsImportDAO)
	{
		this.invoiceinsImportDAO = invoiceinsImportDAO;
	}

	/**
	 * @return the insImportLogDAO
	 */
	public InsImportLogDAO getInsImportLogDAO()
	{
		return insImportLogDAO;
	}

	/**
	 * @param insImportLogDAO the insImportLogDAO to set
	 */
	public void setInsImportLogDAO(InsImportLogDAO insImportLogDAO)
	{
		this.insImportLogDAO = insImportLogDAO;
	}

	/**
	 * @return the attachmentDAO
	 */
	public AttachmentDAO getAttachmentDAO()
	{
		return attachmentDAO;
	}

	/**
	 * @param attachmentDAO the attachmentDAO to set
	 */
	public void setAttachmentDAO(AttachmentDAO attachmentDAO)
	{
		this.attachmentDAO = attachmentDAO;
	}

	/**
	 * @return the productDAO
	 */
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	/**
	 * @param productDAO the productDAO to set
	 */
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	/**
	 * @return the transactionManager
	 */
	public PlatformTransactionManager getTransactionManager()
	{
		return transactionManager;
	}

	/**
	 * @param transactionManager the transactionManager to set
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public DistributionDAO getDistributionDAO() {
		return distributionDAO;
	}

	public void setDistributionDAO(DistributionDAO distributionDAO) {
		this.distributionDAO = distributionDAO;
	}

	public PreConsignDAO getPreConsignDAO() {
		return preConsignDAO;
	}

	public void setPreConsignDAO(PreConsignDAO preConsignDAO) {
		this.preConsignDAO = preConsignDAO;
	}

	/**
	 * @return the packageManager
	 */
	public PackageManager getPackageManager() {
		return packageManager;
	}

	/**
	 * @param packageManager the packageManager to set
	 */
	public void setPackageManager(PackageManager packageManager) {
		this.packageManager = packageManager;
	}

    public ParameterDAO getParameterDAO() {
        return parameterDAO;
    }

    public void setParameterDAO(ParameterDAO parameterDAO) {
        this.parameterDAO = parameterDAO;
    }

    public OutManager getOutManager() {
        return outManager;
    }

    public void setOutManager(OutManager outManager) {
        this.outManager = outManager;
    }

    public PackageDAO getPackageDAO() {
        return packageDAO;
    }

    public void setPackageDAO(PackageDAO packageDAO) {
        this.packageDAO = packageDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

	public TempConsignDAO getTempConsignDAO() {
		return tempConsignDAO;
	}

	public void setTempConsignDAO(TempConsignDAO tempConsignDAO) {
		this.tempConsignDAO = tempConsignDAO;
	}

    public PackageItemDAO getPackageItemDAO() {
        return packageItemDAO;
    }

    public void setPackageItemDAO(PackageItemDAO packageItemDAO) {
        this.packageItemDAO = packageItemDAO;
    }

    public InvoiceDAO getInvoiceDAO() {
        return invoiceDAO;
    }

    public void setInvoiceDAO(InvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }
}
