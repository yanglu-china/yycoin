package com.china.center.oa.tcp.manager.impl;

import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.oa.finance.bean.BackPrePayApplyBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;
import com.china.center.oa.finance.dao.BackPrePayApplyDAO;
import com.china.center.oa.finance.dao.PayOrderDAO;
import com.china.center.oa.finance.dao.PayOrderModifyDAO;
import com.china.center.oa.finance.dao.StockPayApplyDAO;
import com.china.center.oa.finance.dao.StockPrePayApplyDAO;
import com.china.center.oa.finance.vo.PayOrderListLogVO;
import com.china.center.oa.finance.vo.PayOrderModifyListLogVO;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;
import com.china.center.oa.tcp.manager.PayOrderManager;

@Exceptional
public class PayOrderManagerImpl implements PayOrderManager {
	
	private StockPayApplyDAO stockPayApplyDAO;
	
	private StockPrePayApplyDAO stockPrePayApplyDAO;
	
	private TravelApplyPayDAO travelApplyPayDAO;
	
	private BackPrePayApplyDAO backPrePayApplyDAO;

	private PayOrderModifyDAO payOrderModifyDAO;
	
	private PayOrderDAO payOrderDao;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateStockPayApply(StockPayApplyBean stockPayBean, PayOrderModifyListLogVO modifyVo,
			PayOrderListLogVO logVo) throws Exception {
		stockPayApplyDAO.updateEntityBean(stockPayBean);
		payOrderModifyDAO.saveEntityBean(modifyVo);
		payOrderDao.deletePayListVo(logVo);
		
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateStockPrePayApply(StockPrePayApplyBean stockPrePayBean, PayOrderModifyListLogVO modifyVo,
			PayOrderListLogVO logVo) throws Exception {
		
		stockPrePayApplyDAO.updateEntityBean(stockPrePayBean);
		payOrderModifyDAO.saveEntityBean(modifyVo);
		payOrderDao.deletePayListVo(logVo);
		
		
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateTravelPayApply(TravelApplyPayBean travleApplyPayBean, PayOrderModifyListLogVO modifyVo,
			PayOrderListLogVO logVo) throws Exception {

		travelApplyPayDAO.updateEntityBean(travleApplyPayBean);
		payOrderModifyDAO.saveEntityBean(modifyVo);
		payOrderDao.deletePayListVo(logVo);
		
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateBackPrePayApply(BackPrePayApplyBean backPreApplyBean, PayOrderModifyListLogVO modifyVo,
			PayOrderListLogVO logVo) throws Exception {
		backPrePayApplyDAO.updateEntityBean(backPreApplyBean);
		payOrderModifyDAO.saveEntityBean(modifyVo);
		payOrderDao.deletePayListVo(logVo);
	}

	public StockPayApplyDAO getStockPayApplyDAO() {
		return stockPayApplyDAO;
	}

	public void setStockPayApplyDAO(StockPayApplyDAO stockPayApplyDAO) {
		this.stockPayApplyDAO = stockPayApplyDAO;
	}

	public StockPrePayApplyDAO getStockPrePayApplyDAO() {
		return stockPrePayApplyDAO;
	}

	public void setStockPrePayApplyDAO(StockPrePayApplyDAO stockPrePayApplyDAO) {
		this.stockPrePayApplyDAO = stockPrePayApplyDAO;
	}

	public TravelApplyPayDAO getTravelApplyPayDAO() {
		return travelApplyPayDAO;
	}

	public void setTravelApplyPayDAO(TravelApplyPayDAO travelApplyPayDAO) {
		this.travelApplyPayDAO = travelApplyPayDAO;
	}

	public BackPrePayApplyDAO getBackPrePayApplyDAO() {
		return backPrePayApplyDAO;
	}

	public void setBackPrePayApplyDAO(BackPrePayApplyDAO backPrePayApplyDAO) {
		this.backPrePayApplyDAO = backPrePayApplyDAO;
	}

	public PayOrderModifyDAO getPayOrderModifyDAO() {
		return payOrderModifyDAO;
	}

	public void setPayOrderModifyDAO(PayOrderModifyDAO payOrderModifyDAO) {
		this.payOrderModifyDAO = payOrderModifyDAO;
	}

	public PayOrderDAO getPayOrderDao() {
		return payOrderDao;
	}

	public void setPayOrderDao(PayOrderDAO payOrderDao) {
		this.payOrderDao = payOrderDao;
	}

}
