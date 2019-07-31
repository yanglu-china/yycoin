package com.china.center.oa.job.manager.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.NbBankHisDataBean;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.NbBankHisDataDAO;
import com.china.center.oa.finance.manager.payorder.NbBankPayImpl;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryHisDtlLoopData;
import com.china.center.oa.job.manager.JobManager;

public class NbBankPayOrderQueryHisDataImpl implements JobManager {
	
	private final Log _logger = LogFactory.getLog("taobao");
	
	private BankDAO bankDAO;
	
	private NbBankHisDataDAO nbBankHisDataDao;

	/**
	 * 定时任务查询银行账户流水明细
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void run() throws MYException {
		_logger.info("start queryHisDtl");
		NbBankPayImpl nbPay = new NbBankPayImpl();
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("paytype","=",1);

		List<BankBean> bankList = bankDAO.queryEntityBeansByCondition(con);
		// 查询昨日明细
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date time = calendar.getTime();
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(time);
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("beginDate", yesterday);
		paramMap.put("endDate", yesterday);
		try
		{
			for (BankBean bankBean : bankList) {
				String bankAcc = bankBean.getBankNo();
				paramMap.put("bankAcc", bankAcc);
				_logger.info("start queryHisDtl bankAcc:" + bankAcc + ";date:" + yesterday);
				List<NbBankQueryHisDtlLoopData> resultDataList = nbPay.queryHisDtl(paramMap);
				if(resultDataList != null)
				{
					List<NbBankHisDataBean> hisDataList = new ArrayList<NbBankHisDataBean>();
					for (NbBankQueryHisDtlLoopData loopData : resultDataList) {
						NbBankHisDataBean hisDataBean = new NbBankHisDataBean();
						hisDataBean.setAccName(loopData.getAccName());
						hisDataBean.setAmt(new BigDecimal(loopData.getAmt()));
						hisDataBean.setBal(new BigDecimal(loopData.getBal()));
						hisDataBean.setBankAcc(loopData.getBankAcc());
						hisDataBean.setBankName(loopData.getBankName());
						hisDataBean.setCdSign(loopData.getCdSign());
						hisDataBean.setCur(loopData.getCur());
						if(StringUtils.isEmpty(loopData.getOppAccBank()) || "null".equals(loopData.getOppAccBank()))
						{
							hisDataBean.setOppAccBank(null);
						}
						else
						{
							hisDataBean.setOppAccBank(loopData.getOppAccBank());
						}
						if(StringUtils.isEmpty(loopData.getOppAccName()) || "null".equals(loopData.getOppAccName()))
						{
							hisDataBean.setOppAccName(null);
						}
						else
						{
							hisDataBean.setOppAccName(loopData.getOppAccName());
						}
						if(StringUtils.isEmpty(loopData.getOppAccNo()) || "null".equals(loopData.getOppAccNo()))
						{
							hisDataBean.setOppAccNo(null);
						}
						else
						{
							hisDataBean.setOppAccNo(loopData.getOppAccNo());
						}
						hisDataBean.setRemark(loopData.getAbs());
						hisDataBean.setSerialId(loopData.getSerialId());
						hisDataBean.setTransDate(loopData.getTransDate().substring(0, 10));
						hisDataBean.setUses(loopData.getUses());
						hisDataBean.setVoucherNo(loopData.getVoucherNo());
						hisDataList.add(hisDataBean);
					}
					nbBankHisDataDao.saveAllEntityBeans(hisDataList);
				}
				
			}
		}
		catch(Exception e)
		{
			_logger.error("queryHisDtl error",e);
		}
		_logger.info("end queryHisDtl");
	}
	
	public BankDAO getBankDAO() {
		return bankDAO;
	}

	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	public NbBankHisDataDAO getNbBankHisDataDao() {
		return nbBankHisDataDao;
	}

	public void setNbBankHisDataDao(NbBankHisDataDAO nbBankHisDataDao) {
		this.nbBankHisDataDao = nbBankHisDataDao;
	}

}
