package com.china.center.oa.tcp.manager;

import java.util.List;

import com.china.center.oa.finance.bean.BackPrePayApplyBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;
import com.china.center.oa.finance.vo.PayOrderListLogVO;
import com.china.center.oa.finance.vo.PayOrderModifyListLogVO;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;

public interface PayOrderManager {
	
	/**
	 * 更新采购付款
	 * @param stockPayBean
	 * @param modifyVo
	 * @param logVo
	 * @throws Exception
	 */
	void updateStockPayApply(StockPayApplyBean stockPayBean,PayOrderModifyListLogVO modifyVo,PayOrderListLogVO logVo) throws Exception;
	
	/**
	 * 更新采购预付款
	 * @param stockPayBean
	 * @param modifyVo
	 * @param logVo
	 * @throws Exception
	 */
	void updateStockPrePayApply(StockPrePayApplyBean stockPrePayBean,PayOrderModifyListLogVO modifyVo,PayOrderListLogVO logVo) throws Exception;
	
	/**
	 * 更新借款/报销申请
	 * @param travleApplyPayBean
	 * @param modifyVo
	 * @param logVo
	 * @throws Exception
	 */
	void updateTravelPayApply(TravelApplyPayBean travleApplyPayBean,PayOrderModifyListLogVO modifyVo,PayOrderListLogVO logVo) throws Exception;
	
	/**
	 * 更新预收退款
	 * @param backPreApplyBean
	 * @param modifyVo
	 * @param logVo
	 * @throws Exception
	 */
	void updateBackPrePayApply(BackPrePayApplyBean backPreApplyBean,PayOrderModifyListLogVO modifyVo,PayOrderListLogVO logVo) throws Exception;
	
	/**
	 * 	上传付款单的附件
	 * @param logVo
	 * @throws Exception
	 */
	void uploadPayOrderAttachement(PayOrderListLogVO logVo,List<String> deleteIdList) throws Exception;

}
