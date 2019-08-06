package com.china.center.oa.job.manager.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.PayOrderDAO;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.manager.payorder.NbBankPayImpl;
import com.china.center.oa.finance.vo.PayOrderListLogVO;
import com.china.center.oa.job.manager.JobManager;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.tcp.dao.TravelApplyItemDAO;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;
import com.china.center.oa.tcp.manager.BackPrePayManager;
import com.china.center.oa.tcp.manager.ExpenseManager;
import com.china.center.oa.tcp.manager.TravelApplyManager;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.tools.MathTools;

public class NbBankPayOrderQueryImpl implements JobManager {
	
	private final Log _logger = LogFactory.getLog("taobao");
	
	private PayOrderDAO payOrderDao;
	
	private BackPrePayManager backPrePayManager;

	private ExpenseManager expenseManager;

	private TravelApplyItemDAO travelApplyItemDAO;

	private TravelApplyPayDAO travelApplyPayDAO;

	private TravelApplyManager travelApplyManager;
	
	private FinanceFacade financeFacade;
	
	private StafferDAO stafferDao;
	
	private UserDAO userDAO;
	
	private BankDAO bankDAO;
	
	/**
	 * 采购付款
	 */
	private final String CONSTANTS_PAYORDERTYPE_1 = "1";

	/**
	 * 采购预付款
	 */
	private final String CONSTANTS_PAYORDERTYPE_2 = "2";

	/**
	 * 借款申请付款
	 */
	private final String CONSTANTS_PAYORDERTYPE_3 = "3";

	/**
	 * 报销申请付款
	 */
	private final String CONSTANTS_PAYORDERTYPE_4 = "4";

	/**
	 * 预收退款
	 */
	private final String CONSTANTS_PAYORDERTYPE_5 = "5";

	/**
	 * 查询宁波银行付款结果
	 * 00付款成功
	 * 01付款待提交
     * 02付款流程中
	 * 03 等待银行结果
	 * 95 付款已删除
	 * 96 付款已打回
	 * 97付款不存在
	 * 98付款失败
	 * 99 其他状态
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public void run() throws MYException {
		_logger.info("start NbBankPayOrderQueryImpl");
		List<PayOrderListLogVO> list = payOrderDao.queryPayOrderLogStatusList();
		NbBankPayImpl nbPay = new NbBankPayImpl();
		try
		{
			for(PayOrderListLogVO vo : list)
			{
				String erpno = vo.getOutbillid();
				_logger.info("start query billno:" + erpno);
				Map<String,String> retMap = nbPay.queryTransfer(erpno);
				String retCode = retMap.get("retCode");
				_logger.info("billno:" + erpno + " retCode:" + retCode);
				Map<String,String> map = new HashMap<String, String>();
				if(StringUtils.isNotEmpty(retCode) && "0".equals(retCode))
				{
					String payStatus = retMap.get("payState");
					String payMsg = retMap.get("payMsg");
					_logger.info("billno:" + erpno + " payStatus:" + payStatus);
					if(StringUtils.isNotEmpty(payStatus) && "00".equals(payStatus))
					{
						//付款成功，更新log表的状态
						map.put("status", "3");
						map.put("bankStatus", payStatus);
						map.put("outBillId", erpno);
						map.put("payMsg",payMsg);
						payOrderDao.updatePayOrderLog(map);
						
						//生成凭证
						if(CONSTANTS_PAYORDERTYPE_1.equals(vo.getType()))
						{
							endPayOrder1ByCash(vo.getOperatorId(), erpno, vo.getPayBankId(), vo.getMoney());
							//更新付款单表的状态为2
							payOrderDao.updateStockPayApply(erpno, "2");
						}
						if(CONSTANTS_PAYORDERTYPE_2.equals(vo.getType()))
						{
							endPayOrder2ByCash(vo.getOperatorId(), erpno, vo.getPayBankId(), vo.getMoney());
							payOrderDao.updateStockPrePayApply(erpno, "2");
						}
						if(CONSTANTS_PAYORDERTYPE_3.equals(vo.getType()))
						{
							ConditionParse cc = new ConditionParse();
							cc.addCondition("stafferid", "=", vo.getOperator());
							List<UserBean> userList = userDAO.queryEntityBeansByCondition(cc);
							UserBean userBean= (UserBean) userList.get(0);
							UserVO user = userDAO.findVO(userBean.getId());
							BankBean bankBean = bankDAO.find(vo.getPayBankId());
							endPayOrder3ByCash(user, user.getId(), vo.getOutid(), vo.getPayBankId(), vo.getMoney(), bankBean);
							payOrderDao.updateTravelPayApply(erpno, "2");
						}
						if(CONSTANTS_PAYORDERTYPE_4.equals(vo.getType()))
						{
							ConditionParse cc = new ConditionParse();
							cc.addCondition("stafferid", "=", vo.getOperator());
							List<UserBean> userList = userDAO.queryEntityBeansByCondition(cc);
							UserBean userBean= (UserBean) userList.get(0);
							UserVO user = userDAO.findVO(userBean.getId());
							endPayOrder4ByCash(user, vo.getOutid(), vo.getPayBankId(), vo.getMoney());
							payOrderDao.updateTravelPayApply(erpno, "2");
						}
						if(CONSTANTS_PAYORDERTYPE_5.equals(vo.getType()))
						{
							UserVO user = userDAO.findVO(vo.getOperatorId());
							endPayOrder5ByCash(user, erpno,vo.getPayBankId(), vo.getMoney());
							payOrderDao.updateBackPrePayApply(erpno, "2");
						}
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
							map.put("outBillId", erpno);
							map.put("payMsg",payMsg);
							payOrderDao.updatePayOrderLog(map);
//							//原付款单改为待付款
//							if(CONSTANTS_PAYORDERTYPE_1.equals(vo.getType()))
//							{
//								payOrderDao.updateStockPayApply(erpno, "0");
//							}
//							if(CONSTANTS_PAYORDERTYPE_2.equals(vo.getType()))
//							{
//								payOrderDao.updateStockPrePayApply(erpno, "0");
//							}
//							if(CONSTANTS_PAYORDERTYPE_3.equals(vo.getType()))
//							{
//								payOrderDao.updateTravelPayApply(erpno, "0");
//							}
//							if(CONSTANTS_PAYORDERTYPE_4.equals(vo.getType()))
//							{
//								payOrderDao.updateTravelPayApply(erpno, "0");
//							}
//							if(CONSTANTS_PAYORDERTYPE_5.equals(vo.getType()))
//							{
//								payOrderDao.updateBackPrePayApply(erpno, "0");
//							}
							
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			_logger.error("NbBankPayOrderQueryImpl error",e);
			throw e;
		}
		
		_logger.info("end NbBankPayOrderQueryImpl");
	}

	/**
	 * 非网银付款--采购付款
	 * 
	 * @param userId
	 * @param billNo
	 * @param bankId
	 * @param amount
	 */
	public String endPayOrder1ByCash(String userId, String billNo, String bankId, String amount) {
		String retMsg = "";
		List<OutBillBean> outBillList = new ArrayList<OutBillBean>();
		OutBillBean outBill = new OutBillBean();
		outBill.setBankId(bankId);
		outBill.setPayType(0);
		outBill.setMoneys(new BigDecimal(amount).doubleValue());
		outBillList.add(outBill);

		try {
			// 结束采购付款
			financeFacade.endStockPayBySEC(userId, billNo, "集中付款-现金-采购付款", outBillList);
		} catch (MYException e) {
			_logger.error("endPayOrder1ByCash error", e);
			retMsg = e.getMessage();
		}
		return retMsg;
	}

	/**
	 * 非网银付款--采购预付款
	 * 
	 * @param userId
	 * @param billNo
	 * @param bankId
	 * @param amount
	 * @return
	 */
	public String endPayOrder2ByCash(String userId, String billNo, String bankId, String amount) {
		String retMsg = "";
		List<OutBillBean> outBillList = new ArrayList<OutBillBean>();
		OutBillBean outBill = new OutBillBean();
		outBill.setBankId(bankId);
		outBill.setPayType(0);
		outBill.setMoneys(new BigDecimal(amount).doubleValue());
		outBillList.add(outBill);

		try {
			// 结束采购预付款
			financeFacade.endStockPrePayBySEC(userId, billNo, "集中付款-现金-采购预付款", outBillList);

		} catch (MYException e) {
			retMsg = e.getMessage();
			_logger.error("endPayOrder2ByCash error", e);
		}

		return retMsg;

	}

	/**
	 * 借款申请
	 * 
	 * @param userId
	 * @param billNo
	 * @param bankId
	 * @param amount
	 * @return
	 */

	public String endPayOrder3ByCash(User user, String userId, String billNo, String bankId, String amount,BankBean bankBean) {
		String retMsg = "";
		try {
			TcpParamWrap param = new TcpParamWrap();

			param.setId(billNo);
			param.setType("0");
			param.setReason("审批通过");
			param.setProcessId("");
			param.setCompliance("0");

			List<OutBillBean> outBillList = new ArrayList<OutBillBean>();
			OutBillBean outBill = new OutBillBean();
			outBill.setBankId(bankId);
			outBill.setPayType(0);
			outBill.setMoneys(MathTools.parseDouble(amount));
			outBillList.add(outBill);
			param.setOther(outBillList);
			
			param.setDutyId(bankBean.getDutyId());

			// 提交
			travelApplyManager.passTravelApplyBean(user, param);

		} catch (MYException e) {
			retMsg = e.getMessage();
			_logger.error("endPayOrder3ByCash error", e);
		}
		return retMsg;
	}

	/**
	 * 报销申请
	 * 
	 * @param user
	 * @param billNo
	 * @param bankId
	 * @param amount
	 * @return
	 */
	public String endPayOrder4ByCash(User user, String billNo, String bankId, String amount) {
		String retMsg = "";
		try {
			TcpParamWrap param = new TcpParamWrap();

			param.setId(billNo);
			param.setType("0");
			param.setReason("审批通过");
			param.setProcessId("");
			param.setCompliance("0");

			List<OutBillBean> outBillList = new ArrayList<OutBillBean>();
			OutBillBean outBill = new OutBillBean();

			outBill.setBankId(bankId);

			outBill.setPayType(0);

			outBill.setMoneys(MathTools.parseDouble(amount));

			outBillList.add(outBill);

			param.setOther(outBillList);

			expenseManager.passExpenseBean(user, param);

		} catch (MYException e) {
			_logger.error("endPayOrder4ByCash error", e);
			retMsg = e.getMessage();
		}

		return retMsg;

	}
	
	/**
	 * 非网银付款--预收退款
	 * 
	 * @return
	 */
	public String endPayOrder5ByCash(User user, String billNo, String bankId, String amount) {
		String retMsg = "";
		TcpParamWrap param = new TcpParamWrap();
		param.setId(billNo);
		param.setType("0");
		param.setReason("审批通过");
		param.setProcessId("");

		List<OutBillBean> outBillList = new ArrayList<OutBillBean>();
		OutBillBean outBill = new OutBillBean();
		outBill.setBankId(bankId);
		outBill.setPayType(0);
		outBill.setMoneys(new BigDecimal(amount).doubleValue());
		outBillList.add(outBill);
		param.setOther(outBillList);

		try {
			backPrePayManager.passBackPrePayBean(user, param);
		} catch (MYException e) {
			retMsg = e.getMessage();
			_logger.error("endPayOrder5ByCash error", e);
		}
		return retMsg;
	}

	public PayOrderDAO getPayOrderDao() {
		return payOrderDao;
	}

	public void setPayOrderDao(PayOrderDAO payOrderDao) {
		this.payOrderDao = payOrderDao;
	}
	
	public BackPrePayManager getBackPrePayManager() {
		return backPrePayManager;
	}

	public void setBackPrePayManager(BackPrePayManager backPrePayManager) {
		this.backPrePayManager = backPrePayManager;
	}
	
	public ExpenseManager getExpenseManager() {
		return expenseManager;
	}

	public void setExpenseManager(ExpenseManager expenseManager) {
		this.expenseManager = expenseManager;
	}

	public TravelApplyItemDAO getTravelApplyItemDAO() {
		return travelApplyItemDAO;
	}

	public void setTravelApplyItemDAO(TravelApplyItemDAO travelApplyItemDAO) {
		this.travelApplyItemDAO = travelApplyItemDAO;
	}

	public TravelApplyPayDAO getTravelApplyPayDAO() {
		return travelApplyPayDAO;
	}

	public void setTravelApplyPayDAO(TravelApplyPayDAO travelApplyPayDAO) {
		this.travelApplyPayDAO = travelApplyPayDAO;
	}

	public TravelApplyManager getTravelApplyManager() {
		return travelApplyManager;
	}

	public void setTravelApplyManager(TravelApplyManager travelApplyManager) {
		this.travelApplyManager = travelApplyManager;
	}
	
	public FinanceFacade getFinanceFacade() {
		return financeFacade;
	}

	public void setFinanceFacade(FinanceFacade financeFacade) {
		this.financeFacade = financeFacade;
	}

	public StafferDAO getStafferDao() {
		return stafferDao;
	}

	public void setStafferDao(StafferDAO stafferDao) {
		this.stafferDao = stafferDao;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public BankDAO getBankDAO() {
		return bankDAO;
	}

	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

}
