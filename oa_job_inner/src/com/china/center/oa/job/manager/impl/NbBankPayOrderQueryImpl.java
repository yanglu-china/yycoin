package com.china.center.oa.job.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.common.MYException;
import com.china.center.oa.finance.dao.PayOrderDAO;
import com.china.center.oa.finance.manager.payorder.NbBankPayImpl;
import com.china.center.oa.finance.vo.PayOrderListLogVO;
import com.china.center.oa.job.manager.JobManager;

public class NbBankPayOrderQueryImpl implements JobManager {
	
	private final Log _logger = LogFactory.getLog(getClass());
	
	private PayOrderDAO payOrderDao;

	/**
	 * 查询宁波银行付款结果
	 * 00付款成功
	 * 01付款待提交
     * 02 付款流程中
	 * 03 等待银行结果
	 * 95 付款已删除
	 * 96 付款已打回
	 * 97付款不存在
	 * 98付款失败
	 * 99 其他状态
	 */
	@Override
	public void run() throws MYException {
		
		_logger.info("start NbBankPayOrderQueryImpl");
		List<PayOrderListLogVO> list = payOrderDao.queryPayOrderLogStatusList();
		NbBankPayImpl nbPay = new NbBankPayImpl();
		for(PayOrderListLogVO vo : list)
		{
			String erpno = vo.getOutid();
			Map<String,String> retMap = nbPay.queryTransfer(erpno);
			String retCode = retMap.get("retCode");
			Map<String,String> map = new HashMap<String, String>();
			if(StringUtils.isNotEmpty(retCode) && "0".equals(retCode))
			{
				String payStatus = map.get("payStatus");
				if(StringUtils.isNotEmpty(payStatus) && "00".equals(payStatus))
				{
					//付款成功，更新log表的状态
					map.put("status", "3");
					map.put("bankStatus", payStatus);
					map.put("outId", erpno);
					payOrderDao.updatePayOrderLog(map);
				}
				else
				{
					//95 付款已删除
					//96 付款已打回
					//97付款不存在
					//98付款失败
					if("95".equals(payStatus) || "96".equals(payStatus) || "97".equals(payStatus) || "98".equals(payStatus))
					{
						//付款失败，更新log表的状态
						map.put("status", "4");
						map.put("bankStatus", payStatus);
						map.put("outId", erpno);
						payOrderDao.updatePayOrderLog(map);
						
					}
				}
			}
		}
		
		
		_logger.info("end NbBankPayOrderQueryImpl");
	}

	public PayOrderDAO getPayOrderDao() {
		return payOrderDao;
	}

	public void setPayOrderDao(PayOrderDAO payOrderDao) {
		this.payOrderDao = payOrderDao;
	}

}
