
package com.china.center.oa.sail.manager.impl;


import java.util.ArrayList;

import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;

import com.china.center.common.MYException;

import com.china.center.oa.product.constant.DepotConstant;

import com.china.center.oa.product.constant.StorageConstant;

import com.china.center.oa.product.helper.StorageRelationHelper;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.sail.bean.BaseBean;

import com.china.center.oa.sail.bean.OutBean;

import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.sail.manager.OutSample2OrderManager;

import com.china.center.oa.sail.wrap.Sample2OrderWrap;

import com.china.center.tools.TimeTools;

import com.center.china.osgi.publics.User; 

/**
 * OutSample2OrderManagerImpl
 * 
 * @author GLQ
 * @version 2020-5-12
 * @see OutSample2OrderManagerImpl
 * @since 1.0
 */
public class OutSample2OrderManagerImpl implements OutSample2OrderManager
{
    private final Log _logger = LogFactory.getLog(getClass());
    
    private OutDAO outDAO = null;
    
    private OutManager outManager = null;
    
    private FlowLogDAO flowLogDAO = null;
    
	public OutDAO getOutDAO() {
		return outDAO;
	}

	public void setOutDAO(OutDAO outDAO) {
		this.outDAO = outDAO;
	}

	/**
	 * @return the outManager
	 */
	public OutManager getOutManager()
	{
		return outManager;
	}

	/**
	 * @param outManager the outManager to set
	 */
	public void setOutManager(OutManager outManager)
	{
		this.outManager = outManager;
	}

	public FlowLogDAO getFlowLogDAO() {
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
		this.flowLogDAO = flowLogDAO;
	}

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
	public boolean batchHandle(List<Sample2OrderWrap> list, User user) throws MYException {
		boolean flag = true;
		List<String> destOrderIds = new ArrayList<String>();
		for(Sample2OrderWrap wrap : list){
			
			//生成退单
			this.handleBack(wrap, user);
			
			_logger.debug("!destOrderIds.contains(wrap.getDestOrderId()):"+(!destOrderIds.contains(wrap.getDestOrderId())));
			
			//处理新订单
			if(!destOrderIds.contains(wrap.getDestOrderId())){
				
				_logger.debug("handleDestOrder...");
				
				this.handleDestOrder(wrap);
				
				destOrderIds.add(wrap.getDestOrderId());
			}

		}

		return flag;
	}
	
	private boolean handleBack(Sample2OrderWrap wrap, User user){
		OutBean out = new OutBean();

		out.setStatus(OutConstant.STATUS_SAVE);

		out.setStafferName(user.getStafferName());

		out.setStafferId(user.getStafferId());

		out.setType(OutConstant.OUT_TYPE_INBILL);

		out.setOutTime(TimeTools.now_short());

		out.setDepartment("采购部");

		out.setCustomerId("99");

		out.setCustomerName("系统内置供应商");

		// 所在区域
		out.setLocationId(user.getLocationId());

		// 目的仓库
		out.setLocation(DepotConstant.KTKK_SW_DEPOT_ID);

		out.setDestinationId("999");

		out.setInway(OutConstant.IN_WAY_NO);

		out.setOutType(OutConstant.OUTTYPE_IN_SWATCH);

		out.setRefOutFullId(wrap.getSampleId());
		out.setTransportNo("转订单");

		out.setDutyId(wrap.getSampleOut().getDutyId());

		out.setInvoiceId(wrap.getSampleOut().getInvoiceId());

		out.setDescription("个人领样退库,领样单号:" + wrap.getSampleId() + ". 空退空开库（仅限商务部操作）" );

		out.setVirtualStatus(0);

		out.setOperator(user.getStafferId());
		out.setOperatorName(user.getStafferName());

		List<BaseBean> newBaseList = new ArrayList<BaseBean>();

		// 增加base
		BaseBean baseBeanSample = wrap.getSampleOut().getBaseList().get(0);
		
		BaseBean baseBean = new BaseBean();

		baseBean.setLocationId(out.getLocation());
		baseBean.setAmount(wrap.getAmount());
		baseBean.setProductName(baseBeanSample.getProductName());
		baseBean.setUnit(baseBeanSample.getUnit());
		baseBean.setShowId(baseBeanSample.getShowId());
		baseBean.setProductId(baseBeanSample.getProductId());

		// 领样退库的金额是销售的金额,否则无法回款
		baseBean.setPrice(baseBeanSample.getPrice());
		baseBean.setCostPrice(baseBeanSample.getCostPrice());
		baseBean.setIprice(baseBeanSample.getIprice());
		baseBean.setPprice(baseBeanSample.getPprice());
		baseBean.setInputPrice(baseBeanSample.getInputPrice());
		baseBean.setCostPriceKey(StorageRelationHelper
				.getPriceKey(baseBeanSample.getCostPrice()));

		baseBean.setVirtualPrice(baseBeanSample.getVirtualPrice());
		baseBean.setVirtualPriceKey(StorageRelationHelper
				.getPriceKey(baseBeanSample.getVirtualPrice()));

		baseBean.setOwner("0");
		baseBean.setOwnerName("公共");
		
		baseBean.setDepotpartId(DepotConstant.KTKK_SW_DEPOTPART_ID);
		
		baseBean.setDepotpartName(DepotConstant.KTKK_SW_DEPOTPART_NAME);

		baseBean.setDescription(String.valueOf(baseBeanSample.getCostPrice()));

		baseBean.setValue(baseBeanSample.getPrice() * wrap.getAmount());

		newBaseList.add(baseBean);

		double total = 0;
		total += baseBean.getValue();

		out.setTotal(total);

		out.setBaseList(newBaseList);
		
		boolean flag = true;

		try
		{
			if (newBaseList.size() > 0)
			{
				// CORE 个人领样退库
				String fullId = outManager.coloneOutWithAffair(out, user,
						StorageConstant.OPR_STORAGE_SWATH);
			}
		}catch (MYException e){
			_logger.warn(e, e);
			flag = false;
		}
		
		return flag;
	}
	

	private boolean handleDestOrder(Sample2OrderWrap wrap){
		boolean flag = true;
		
		this.outDAO.updateForSampleToOrderByOutId(wrap.getDestOrderId());
		
		//auto approve
		this.autoApprove(wrap.getDestOrderId(), OutConstant.STATUS_SUBMIT, OutConstant.STATUS_FLOW_PASS);

		this.autoApprove(wrap.getDestOrderId(), OutConstant.STATUS_FLOW_PASS, OutConstant.STATUS_PASS);
		
		return flag;
	}
	
	private boolean autoApprove(String fullId, int preStatus, int afterStatus ){
		
		this.outDAO.modifyOutStatus(fullId, afterStatus);
		
    	// 记录退货审批日志 操作人系统，自动审批 
    	FlowLogBean log = new FlowLogBean();

        log.setActor("系统");

        log.setDescription("领样转订单导入系统自动审批");
        log.setFullId(fullId);
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(preStatus);

        log.setAfterStatus(afterStatus);

        flowLogDAO.saveEntityBean(log);
        
        return true;
	}
    
}
