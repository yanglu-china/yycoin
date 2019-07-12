package com.china.center.oa.finance.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.china.center.jdbc.inter.impl.IbatisDaoSupportImpl;
import com.china.center.oa.finance.dao.PayOrderDAO;
import com.china.center.oa.finance.vo.PayOrderListLogVO;
import com.china.center.oa.finance.vo.PayOrderVO;

public class PayOrderDAOImpl extends IbatisDaoSupportImpl implements PayOrderDAO  {

	@SuppressWarnings("unchecked")
	public List<PayOrderVO> queryPayOrderList(Map<String,String> paramMap) {
		
		List<PayOrderVO> list = this.queryForList("PayOrderDaoImpl.queryPayOrderList", paramMap);
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayOrderVO> queryPayOrderList41(Map<String, String> paramMap) {
		List<PayOrderVO> list = this.queryForList("PayOrderDaoImpl.queryPayOrderList41", paramMap);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayOrderVO> queryPayOrderList42(Map<String, String> paramMap) {
		List<PayOrderVO> list = this.queryForList("PayOrderDaoImpl.queryPayOrderList42", paramMap);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayOrderVO> queryPayOrderList43(Map<String, String> paramMap) {
		List<PayOrderVO> list = this.queryForList("PayOrderDaoImpl.queryPayOrderList43", paramMap);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayOrderVO> queryPayOrderList44(Map<String, String> paramMap) {
		List<PayOrderVO> list = this.queryForList("PayOrderDaoImpl.queryPayOrderList44", paramMap);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayOrderVO> queryPayOrderList45(Map<String, String> paramMap) {
		List<PayOrderVO> list = this.queryForList("PayOrderDaoImpl.queryPayOrderList45", paramMap);
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PayOrderListLogVO> queryPayOrderLogList(Map<String, String> paramMap) {
		List<PayOrderListLogVO> list = this.queryForList("PayOrderDaoImpl.queryPayOrderLogList", paramMap);
		return list;
	}

	@Override
	@Transactional
	public void createPayListLog(PayOrderListLogVO logvo) {
		this.insert("PayOrderDaoImpl.createPayListLog",logvo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PayOrderListLogVO> queryPayOrderLogStatusList() {
		List<PayOrderListLogVO> list = this.queryForList("PayOrderDaoImpl.queryPayOrderLogStatusList");
		return list;
	}

	@Override
	@Transactional
	public void updatePayOrderLog(Map<String, String> map) {
		// TODO Auto-generated method stub
		this.update("PayOrderDaoImpl.updatePayOrderLog", map);
	}

}
