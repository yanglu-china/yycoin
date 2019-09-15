package com.china.center.oa.finance.dao;

import java.util.List;
import java.util.Map;

import com.china.center.oa.finance.vo.PayOrderListLogVO;
import com.china.center.oa.finance.vo.PayOrderVO;

public interface PayOrderDAO {
	
	List<PayOrderVO> queryPayOrderList(Map<String,String> paramMap);
	
	List<PayOrderVO> queryPayOrderList41(Map<String,String> paramMap);
	
	List<PayOrderVO> queryPayOrderList42(Map<String,String> paramMap);
	
	List<PayOrderVO> queryPayOrderList43(Map<String,String> paramMap);
	
	List<PayOrderVO> queryPayOrderList44(Map<String,String> paramMap);
	
	List<PayOrderVO> queryPayOrderList45(Map<String,String> paramMap);
	
	void createPayListLog(PayOrderListLogVO logvo);
	
	public List<PayOrderListLogVO> queryPayOrderLogList(Map<String, String> paramMap);
	
	public List<PayOrderListLogVO> queryPayOrderListHasNoAttachment(Map<String, String> paramMap);
	
	public List<PayOrderListLogVO> queryPayOrderLogStatusList();
	
	public void updatePayOrderLog(Map<String,String> map);
	
	public void deletePayListVo(PayOrderListLogVO vo);
	
	public void updateStockPayApply(String billNo,String payFlag);
	
	public void updateStockPrePayApply(String billNo,String payFlag);
	
	public void updateTravelPayApply(String billNo,String payFlag);
	
	public void updateBackPrePayApply(String billNo,String payFlag);

}
