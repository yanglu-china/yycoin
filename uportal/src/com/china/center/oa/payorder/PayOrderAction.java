package com.china.center.oa.payorder;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.PayOrderDAO;
import com.china.center.oa.finance.manager.payorder.NbBankPayImpl;
import com.china.center.oa.finance.vo.PayOrderListLogVO;
import com.china.center.oa.finance.vo.PayOrderVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.OpeningBankBean;
import com.china.center.oa.publics.dao.OpeningBankDAO;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;

public class PayOrderAction extends DispatchAction {

	private final Log payLog = LogFactory.getLog("taobao");

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
	 * 待付款
	 */
	private final String CONSTANTS_PAYORDERSTATUS_1 = "1";

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private PayOrderDAO payOrderDao;

	private BankDAO bankDAO;

	private TravelApplyPayDAO travelApplyPayDAO;

	private OpeningBankDAO openingBankDAO;
	
	private TcpApproveDAO tcpApproveDAO;

	public ActionForward queryPayOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		payLog.info("-------------------------------------");
		Map<String, String> queryMap = new HashMap<String, String>();
		String payOrderType = request.getParameter("payOrderType");
		String payOrderStatus = request.getParameter("payOrderStatus");
		String payOrderNo = request.getParameter("payOrderNo");
		String payeeBank = request.getParameter("payeeBank");
		String payeeAccName = request.getParameter("payeeAccName");
		String payeeAcc = request.getParameter("payeeAcc");
		String payeeAmount = request.getParameter("payeeAmount");
		String billTime = request.getParameter("billTime");
		String billEndTime = request.getParameter("billEndTime");
		queryMap.put("payOrderNo", payOrderNo);
		queryMap.put("payeeBank", payeeBank);
		queryMap.put("payeeAccName", payeeAccName);
		queryMap.put("payeeAcc", payeeAcc);
		queryMap.put("payeeAmount", payeeAmount);
		queryMap.put("billEndTime", billEndTime);
		queryMap.put("payOrderType", payOrderType);
		queryMap.put("payOrderStatus", payOrderStatus);
		// 起始日期大于2019-08-01
		if (StringUtils.isEmpty(billTime)) {
			billTime = "2019-08-01";
		} else {
			try {
				Date date = new SimpleDateFormat("yyyy-MM-dd").parse(billTime);
				Date date81 = new SimpleDateFormat("yyyy-MM-dd").parse("2019-08-01");
				if (date.compareTo(date81) < 0) {
					billTime = "2019-08-01";
				}
			} catch (ParseException e) {
			}
		}
		queryMap.put("billTime", billTime);
		if (StringUtils.isEmpty(payOrderStatus)) {
			payOrderStatus = CONSTANTS_PAYORDERSTATUS_1;
		}

		if (CONSTANTS_PAYORDERSTATUS_1.contentEquals(payOrderStatus)) {
			List<PayOrderVO> list = new ArrayList<PayOrderVO>();
			// 查询所有单据的待付款
			if (StringUtils.isEmpty(payOrderType)) {
				list = payOrderDao.queryPayOrderList(queryMap);
			} else {
				if (CONSTANTS_PAYORDERTYPE_1.equals(payOrderType)) {
					list = payOrderDao.queryPayOrderList41(queryMap);
				}
				if (CONSTANTS_PAYORDERTYPE_2.equals(payOrderType)) {
					list = payOrderDao.queryPayOrderList42(queryMap);
				}
				if (CONSTANTS_PAYORDERTYPE_3.equals(payOrderType)) {
					list = payOrderDao.queryPayOrderList43(queryMap);
				}
				if (CONSTANTS_PAYORDERTYPE_4.equals(payOrderType)) {
					list = payOrderDao.queryPayOrderList44(queryMap);
				}
				if (CONSTANTS_PAYORDERTYPE_5.equals(payOrderType)) {
					list = payOrderDao.queryPayOrderList45(queryMap);
				}
			}
			request.setAttribute("payOrderList", list);
		} else {
			List<PayOrderListLogVO> list = payOrderDao.queryPayOrderLogList(queryMap);
			request.setAttribute("payOrderLogList", list);
		}
		request.setAttribute("queryMap", queryMap);
		request.setAttribute("payOrderStatus", payOrderStatus);
		return mapping.findForward("queryPayOrder");

	}

	/**
	 * 根据选择的单号，展示付款银行选择页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward toPayOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String billNoString = request.getParameter("billNoString");
		if (StringUtils.isEmpty(billNoString)) {
			return mapping.findForward("queryPayOrder");
		}
		String[] billNoArray = StringUtils.split(billNoString, ",");
		if (billNoArray == null || billNoArray.length == 0) {
			return mapping.findForward("queryPayOrder");
		}

		List<PayOrderVO> payOrderList = new ArrayList<PayOrderVO>();

		Map<String, String> checkMap = new HashMap<String, String>();

		for (int i = 0; i < billNoArray.length; i++) {
			String billNoAndType = billNoArray[i];
			String[] array = StringUtils.split(billNoAndType, "_");
			String billNo = array[0];
			String billType = array[1];
			if (checkMap.containsKey(billNo)) {
				continue;
			}
			checkMap.put(billNo, billNo);
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("payOrderNo", billNo);
			if (CONSTANTS_PAYORDERTYPE_1.contentEquals(billType)) {
				payOrderList.addAll(payOrderDao.queryPayOrderList41(paramMap));
			}
			if (CONSTANTS_PAYORDERTYPE_2.contentEquals(billType)) {
				payOrderList.addAll(payOrderDao.queryPayOrderList42(paramMap));
			}
			if (CONSTANTS_PAYORDERTYPE_3.contentEquals(billType)) {
				payOrderList.addAll(payOrderDao.queryPayOrderList43(paramMap));
			}
			if (CONSTANTS_PAYORDERTYPE_4.contentEquals(billType)) {
				payOrderList.addAll(payOrderDao.queryPayOrderList44(paramMap));
			}
			if (CONSTANTS_PAYORDERTYPE_5.contentEquals(billType)) {
				payOrderList.addAll(payOrderDao.queryPayOrderList45(paramMap));
			}
		}
		BigDecimal totalAmount = new BigDecimal(0);
		// 总金额
		for (PayOrderVO vo : payOrderList) {
			BigDecimal amount = new BigDecimal(vo.getPayeeAmount());
			totalAmount = totalAmount.add(amount);
		}

		// 返回初始页面的所有参数都带上
		Map<String, String> queryMap = new HashMap<String, String>();
		String payOrderType = request.getParameter("payOrderType");
		String payOrderStatus = request.getParameter("payOrderStatus");
		String payOrderNo = request.getParameter("payOrderNo");
		String payeeBank = request.getParameter("payeeBank");
		String payeeAccName = request.getParameter("payeeAccName");
		String payeeAcc = request.getParameter("payeeAcc");
		String payeeAmount = request.getParameter("payeeAmount");
		String billTime = request.getParameter("billTime");
		queryMap.put("payOrderNo", payOrderNo);
		queryMap.put("payeeBank", payeeBank);
		queryMap.put("payeeAccName", payeeAccName);
		queryMap.put("payeeAcc", payeeAcc);
		queryMap.put("payeeAmount", payeeAmount);
		queryMap.put("billTime", billTime);
		queryMap.put("payOrderType", payOrderType);
		queryMap.put("payOrderStatus", payOrderStatus);
		request.setAttribute("queryMap", queryMap);
		request.setAttribute("billNoString", billNoString);
		request.setAttribute("payOrderList", payOrderList);
		request.setAttribute("totalAmount", totalAmount);
		return mapping.findForward("toPayOrder");
	}

	/**
	 * 发起付款
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward doPayOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String billNoString = request.getParameter("billNoString");
		if (StringUtils.isEmpty(billNoString)) {
			return JSONTools.writeResponse(response, "单据异常，请返回上一次页面重新选择付款单");
		}
		String[] billNoArray = StringUtils.split(billNoString, ",");
		if (billNoArray == null || billNoArray.length == 0) {
			return JSONTools.writeResponse(response, "单据异常，请返回上一次页面重新选择付款单");
		}
		String bankId = request.getParameter("bankId");
		if (StringUtils.isEmpty(bankId)) {
			return JSONTools.writeResponse(response, "单据异常，请返回上一次页面重新选择付款单");
		}
		// 查询银行账号信息
		BankBean bankBean = bankDAO.find(bankId);
		if (bankBean == null) {
			return JSONTools.writeResponse(response, "银行账号信息异常,请重新选择付款银行!");
		}
		User user = Helper.getUser(request);

		String payBankCity = bankBean.getBankcity();
		if (StringUtils.isEmpty(payBankCity)) {
			return JSONTools.writeResponse(response, "付款银行账号的开户省/市为空,银行账号名称:" + bankBean.getName() + ",请完善信息!");
		}
		payBankCity = payBankCity.trim();
		String payBankNo = bankBean.getBankNo();
		if (StringUtils.isEmpty(payBankNo)) {
			return JSONTools.writeResponse(response, "付款银行的付款账号为空,银行账号名称:" + bankBean.getName() + ",请完善信息!");
		}
		payBankNo = payBankNo.trim();
		String payBankName = bankBean.getBankName();
		if (StringUtils.isEmpty(payBankName)) {
			return JSONTools.writeResponse(response, "付款银行的银行名称为空,银行账号名称:" + bankBean.getName() + ",请完善信息!");
		}
		// 付款银行信息
		NbBankPayImpl nbBankPay = new NbBankPayImpl();
		StringBuffer errmsg = new StringBuffer();
		try {
			boolean tempFlag = true;
			for (int i = 0; i < billNoArray.length; i++) {
				Map<String, String> payInfoMap = new HashMap<String, String>();
				String billNoAndType = billNoArray[i];
				String[] array = StringUtils.split(billNoAndType, "_");
				// 单号
				String billNo = array[0];
				// 单据类型
				String billType = array[1];
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("payOrderNo", billNo);
				payInfoMap.put("payerAccNo", bankBean.getBankNo());
				payInfoMap.put("payeeAccNo", "");
				payInfoMap.put("payeeAccName", "");
				payInfoMap.put("payMoney", "");
				payInfoMap.put("payPurpose", "");
				payInfoMap.put("erpReqNo", billNo);

				if (CONSTANTS_PAYORDERTYPE_1.equals(billType)) {
					List<PayOrderVO> payOrderVOList = payOrderDao.queryPayOrderList41(paramMap);
					if (payOrderVOList.size() == 0 || payOrderVOList.size() > 1) {
						errmsg.append("查询单据编号:" + billNo + "出错!");
						continue;
					}

					PayOrderVO vo = payOrderVOList.get(0);
					// 同城
					String peeCity = vo.getCityName();

					if (payBankCity.equals(peeCity)) {
						// 同城
						payInfoMap.put("areaSign", "0");
					} else {
						// 异地
						payInfoMap.put("areaSign", "1");
					}
					// 查询收款账户的联行号
					String payeeBank = vo.getPayeeBank();
					ConditionParse cond = new ConditionParse();
					cond.addWhereStr();
					cond.addCondition("bankname", "=", payeeBank);
					List<OpeningBankBean> openBankList = openingBankDAO.queryEntityBeansByCondition(cond);
					if (openBankList.size() == 0) {
						errmsg.append("单据编号:" + billNo + "的收款账户联行号查询失败");
						continue;
					}
					OpeningBankBean openingBank = openBankList.get(0);
					if (openingBank.getBankTypeName().indexOf(payBankName) != -1) {
						// 同行标识
						payInfoMap.put("difSign", "0");
					} else {
						// 跨行标识
						payInfoMap.put("difSign", "1");
					}
					payInfoMap.put("payPurpose", "采购付款");
					// bank支持电子支付
					if (tempFlag) {
						payInfoMap.put("payeeAccNo", vo.getPayeeBankAcc());
						payInfoMap.put("payeeAccName", vo.getPayeeBankAccName());
						payInfoMap.put("payeeBankCode", openingBank.getUnionBankCode());
						payInfoMap.put("payMoney", vo.getPayeeAmount());
						Map<String, String> retMap = nbBankPay.erpTransfer(payInfoMap);
						String retCode = retMap.get("retCode");
						String retMsg = retMap.get("retMsg");
						// success
						if (!"0".equals(retCode)) {
							errmsg.append("单据号:" + billNo + "付款出错:" + retMsg);
						}
						// 先生成付款日志数据
						PayOrderListLogVO logvo = new PayOrderListLogVO();
						logvo.setId(String.valueOf(System.currentTimeMillis()));
						logvo.setOutid(billNo);
						logvo.setType(CONSTANTS_PAYORDERTYPE_1);
						logvo.setBankName(vo.getPayeeBank());
						logvo.setUserName(vo.getPayeeBankAccName());
						logvo.setBankNo(vo.getPayeeBankAcc());
						logvo.setMoney(vo.getPayeeAmount());
						logvo.setDescription(vo.getDescription());
						logvo.setOutidtime(vo.getLogTime());
						logvo.setCity(peeCity);
						logvo.setOutbillid(billNo);
						// 付款中
						logvo.setStatus("2");
						logvo.setOperator(user.getStafferId());
						logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
						logvo.setPayaccount(bankBean.getBankNo());
						logvo.setPaybank(bankBean.getName());
						// 待确认
						logvo.setBankstatus("0");
						logvo.setPayBankId(bankId);
						logvo.setOperatorId(user.getId());
						logvo.setMessage(retMsg);
						// 先生成付款日志数据
						payOrderDao.createPayListLog(logvo);

					}

				}
				if (CONSTANTS_PAYORDERTYPE_2.equals(billType)) {
					List<PayOrderVO> payOrderVOList = payOrderDao.queryPayOrderList42(paramMap);
					if (payOrderVOList.size() == 0 || payOrderVOList.size() > 1) {
						errmsg.append("查询单据编号:" + billNo + "出错!");
						continue;
					}
					PayOrderVO vo = payOrderVOList.get(0);
					// 同城
					String peeCity = vo.getCityName();

					if (payBankCity.equals(peeCity)) {
						// 同城
						payInfoMap.put("areaSign", "0");
					} else {
						// 异地
						payInfoMap.put("areaSign", "1");
					}
					// 查询收款账户的联行号
					String payeeBank = vo.getPayeeBank();
					ConditionParse cond = new ConditionParse();
					cond.addWhereStr();
					cond.addCondition("bankname", "=", payeeBank);
					List<OpeningBankBean> openBankList = openingBankDAO.queryEntityBeansByCondition(cond);
					if (openBankList.size() == 0) {
						errmsg.append("单据编号:" + billNo + "的收款账户联行号查询失败");
						continue;
					}
					OpeningBankBean openingBank = openBankList.get(0);
					if (openingBank.getBankTypeName().indexOf(payBankName) != -1) {
						// 同行标识
						payInfoMap.put("difSign", "0");
					} else {
						// 跨行标识
						payInfoMap.put("difSign", "1");
					}
					payInfoMap.put("payMoney", vo.getPayeeAmount());
					payInfoMap.put("payPurpose", "采购预付款");
					// bank支持电子支付
					if (tempFlag) {
						payInfoMap.put("payeeBankCode", openingBank.getUnionBankCode());
						Map<String, String> retMap = nbBankPay.erpTransfer(payInfoMap);
						String retCode = retMap.get("retCode");
						String retMsg = retMap.get("retMsg");
						// success
						if (!"0".equals(retCode)) {
							errmsg.append("单据号:" + billNo + "付款出错:" + retMsg);
						}
						// 先生成付款日志数据
						PayOrderListLogVO logvo = new PayOrderListLogVO();
						logvo.setId(String.valueOf(System.currentTimeMillis()));
						logvo.setOutid(billNo);
						logvo.setType(CONSTANTS_PAYORDERTYPE_2);
						logvo.setBankName(vo.getPayeeBank());
						logvo.setUserName(vo.getPayeeBankAccName());
						logvo.setBankNo(vo.getPayeeBankAcc());
						logvo.setMoney(vo.getPayeeAmount());
						logvo.setCity(peeCity);
						logvo.setDescription(vo.getDescription());
						logvo.setOutidtime(vo.getLogTime());
						logvo.setOutbillid(billNo);
						// 付款中
						logvo.setStatus("2");
						logvo.setOperator(user.getStafferId());
						logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
						logvo.setPayaccount(bankBean.getBankNo());
						logvo.setPaybank(bankBean.getName());
						logvo.setPayBankId(bankId);
						logvo.setOperatorId(user.getId());
						logvo.setMessage(retMsg);
						// 待确认
						logvo.setBankstatus("0");
						payOrderDao.createPayListLog(logvo);
					}

				}
				if (CONSTANTS_PAYORDERTYPE_3.equals(billType)) {
					List<PayOrderVO> payOrderVOList = payOrderDao.queryPayOrderList43(paramMap);
					if (payOrderVOList.size() == 0) {
						errmsg.append("查询单据编号:" + billNo + "出错!");

						continue;
					}
					PayOrderVO vo = payOrderVOList.get(0);
					// 根据报销单，查找报销收款明细表
					ConditionParse parse = new ConditionParse();
					parse.addWhereStr();
					parse.addCondition("parentid", "=", vo.getBillNo());
					List<TravelApplyPayBean> applyPayList = travelApplyPayDAO.queryEntityBeansByCondition(parse);
					if (applyPayList.size() == 0) {
						errmsg.append("查询单据明细出错，编号:" + billNo);
						continue;
					}
					// 按收款明细支付
					for (TravelApplyPayBean payBean : applyPayList) {
						// 查询log表是否已经支付
						Map<String, String> queryMap = new HashMap<String, String>();
						queryMap.put("outBillId", payBean.getId());
						List<PayOrderListLogVO> existsList = payOrderDao.queryPayOrderLogList(queryMap);
						if (existsList.size() > 0) {
							continue;
						}
						// bank支持电子支付
						if (tempFlag) {
							// 同城
							String peeCity = payBean.getBankcity();
							if (payBankCity.equals(peeCity)) {
								// 同城
								payInfoMap.put("areaSign", "0");
							} else {
								// 异地
								payInfoMap.put("areaSign", "1");
							}
							// 查询收款账户的联行号
							String payeeBank = vo.getPayeeBank();
							ConditionParse cond = new ConditionParse();
							cond.addWhereStr();
							cond.addCondition("bankname", "=", payeeBank);
							List<OpeningBankBean> openBankList = openingBankDAO.queryEntityBeansByCondition(cond);
							if (openBankList.size() == 0) {
								errmsg.append("单据编号:" + billNo + "的收款账户联行号查询失败");
								continue;
							}
							OpeningBankBean openingBank = openBankList.get(0);
							if (openingBank.getBankTypeName().indexOf(payBankName) != -1) {
								// 同行标识
								payInfoMap.put("difSign", "0");
							} else {
								// 跨行标识
								payInfoMap.put("difSign", "1");
							}

							payInfoMap.put("payeeBankCode", openingBank.getUnionBankCode());
							payInfoMap.put("payeeAccNo", payBean.getBankNo());
							payInfoMap.put("payeeAccName", payBean.getUserName());
							payInfoMap.put("payMoney", vo.getPayeeAmount());
							payInfoMap.put("payPurpose", "借款申请付款");
							// 一笔款多条收款明细,用明细id作为erpno
							payInfoMap.put("erpReqNo", payBean.getId());
							Map<String, String> retMap = nbBankPay.erpTransfer(payInfoMap);
							String retCode = retMap.get("retCode");
							String retMsg = retMap.get("retMsg");
							// success
							if (!"0".equals(retCode)) {
								errmsg.append("单据号:" + billNo + "付款出错:" + retMsg);
							}
							// 先生成付款日志数据
							PayOrderListLogVO logvo = new PayOrderListLogVO();
							logvo.setId(String.valueOf(System.currentTimeMillis()));
							logvo.setOutid(billNo);
							logvo.setType(CONSTANTS_PAYORDERTYPE_3);
							logvo.setBankName(payBean.getBankName());
							logvo.setUserName(payBean.getUserName());
							logvo.setBankNo(payBean.getBankNo());
							logvo.setMoney(vo.getPayeeAmount());
							logvo.setProvince(payBean.getBankprovince());
							logvo.setCity(payBean.getBankcity());
							logvo.setDescription(vo.getDescription());
							logvo.setOutidtime(vo.getLogTime());
							logvo.setOutbillid(payBean.getId());
							// 付款中
							logvo.setStatus("2");
							//审批人id
							List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(billNo);
							if(approveList != null && approveList.size() > 0)
							{
								logvo.setOperator(approveList.get(0).getApproverId());
							}
							logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
							logvo.setPayaccount(bankBean.getBankNo());
							logvo.setPaybank(bankBean.getName());
							// 待确认
							logvo.setBankstatus("0");
							logvo.setPayBankId(bankId);
							logvo.setOperatorId(user.getId());
							logvo.setMessage(retMsg);
							payOrderDao.createPayListLog(logvo);
						}
					}

				}
				if (CONSTANTS_PAYORDERTYPE_4.equals(billType)) {

					List<PayOrderVO> payOrderVOList = payOrderDao.queryPayOrderList44(paramMap);
					if (payOrderVOList.size() == 0) {
						errmsg.append("查询单据编号:" + billNo + "出错!");
						continue;
					}
					PayOrderVO vo = payOrderVOList.get(0);
					// 根据报销单，查找报销收款明细表
					ConditionParse parse = new ConditionParse();
					parse.addWhereStr();
					parse.addCondition("parentid", "=", vo.getBillNo());
					List<TravelApplyPayBean> applyPayList = travelApplyPayDAO.queryEntityBeansByCondition(parse);
					if (applyPayList.size() == 0) {
						errmsg.append("查询单据明细出错，编号:" + billNo);
						continue;
					}
					// 按收款明细支付
					for (TravelApplyPayBean payBean : applyPayList) {
						// 查询log表是否已经支付
						Map<String, String> queryMap = new HashMap<String, String>();
						queryMap.put("outBillId", payBean.getId());
						List<PayOrderListLogVO> existsList = payOrderDao.queryPayOrderLogList(queryMap);
						if (existsList.size() > 0) {
							continue;
						}
						// bank支持电子支付
						if (tempFlag) {
							String peeCity = payBean.getBankcity();
							if (payBankCity.equals(peeCity)) {
								// 同城
								payInfoMap.put("areaSign", "0");
							} else {
								// 异地
								payInfoMap.put("areaSign", "1");
							}
							// 查询收款账户的联行号
							String payeeBank = vo.getPayeeBank();
							ConditionParse cond = new ConditionParse();
							cond.addWhereStr();
							cond.addCondition("bankname", "=", payeeBank);
							List<OpeningBankBean> openBankList = openingBankDAO.queryEntityBeansByCondition(cond);
							if (openBankList.size() == 0) {
								errmsg.append("单据编号:" + billNo + "的收款账户联行号查询失败");
								continue;
							}
							OpeningBankBean openingBank = openBankList.get(0);
							if (openingBank.getBankTypeName().indexOf(payBankName) != -1) {
								// 同行标识
								payInfoMap.put("difSign", "0");
							} else {
								// 跨行标识
								payInfoMap.put("difSign", "1");
							}

							payInfoMap.put("payeeBankCode", openingBank.getUnionBankCode());
							payInfoMap.put("payeeAccNo", payBean.getBankNo());
							payInfoMap.put("payeeAccName", payBean.getUserName());
							payInfoMap.put("payMoney", vo.getPayeeAmount());
							payInfoMap.put("payPurpose", "报销申请付款");
							// 一笔款多条收款明细,用明细id作为erpno
							payInfoMap.put("erpReqNo", payBean.getId());
							Map<String, String> retMap = nbBankPay.erpTransfer(payInfoMap);
							String retCode = retMap.get("retCode");
							String retMsg = retMap.get("retMsg");
							// success
							if (!"0".equals(retCode)) {
								errmsg.append("单据号:" + billNo + "付款出错:" + retMsg);
							}
							// 先生成付款日志数据
							PayOrderListLogVO logvo = new PayOrderListLogVO();
							logvo.setId(String.valueOf(System.currentTimeMillis()));
							logvo.setOutid(billNo);
							logvo.setType(CONSTANTS_PAYORDERTYPE_4);
							logvo.setBankName(payBean.getBankName());
							logvo.setUserName(payBean.getUserName());
							logvo.setBankNo(payBean.getBankNo());
							logvo.setMoney(vo.getPayeeAmount());
							logvo.setProvince(payBean.getBankprovince());
							logvo.setCity(payBean.getBankcity());
							logvo.setDescription(vo.getDescription());
							logvo.setOutidtime(vo.getLogTime());
							logvo.setOutbillid(payBean.getId());
							// 付款中
							logvo.setStatus("2");
							//审批人id
							List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(billNo);
							if(approveList != null && approveList.size() > 0)
							{
								logvo.setOperator(approveList.get(0).getApproverId());
							}
							logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
							logvo.setPayaccount(bankBean.getBankNo());
							logvo.setPaybank(bankBean.getName());
							// 待确认
							logvo.setBankstatus("0");
							logvo.setPayBankId(bankId);
							logvo.setOperatorId(user.getId());
							logvo.setMessage(retMsg);
							// 先生成付款日志数据
							payOrderDao.createPayListLog(logvo);
						}
					}
				}
				if (CONSTANTS_PAYORDERTYPE_5.equals(billType)) {
					List<PayOrderVO> payOrderVOList = payOrderDao.queryPayOrderList45(paramMap);
					if (payOrderVOList.size() == 0 || payOrderVOList.size() > 1) {
						errmsg.append("查询单据编号:" + billNo + "出错!");
						continue;
					}
					PayOrderVO vo = payOrderVOList.get(0);
					// bank支持电子支付
					if (tempFlag) {
						String peeCity = vo.getCityName();
						if (payBankCity.equals(peeCity)) {
							// 同城
							payInfoMap.put("areaSign", "0");
						} else {
							// 异地
							payInfoMap.put("areaSign", "1");
						}
						// 查询收款账户的联行号
						String payeeBank = vo.getPayeeBank();
						ConditionParse cond = new ConditionParse();
						cond.addWhereStr();
						cond.addCondition("bankname", "=", payeeBank);
						List<OpeningBankBean> openBankList = openingBankDAO.queryEntityBeansByCondition(cond);
						if (openBankList.size() == 0) {
							errmsg.append("单据编号:" + billNo + "的收款账户联行号查询失败");
							continue;
						}
						OpeningBankBean openingBank = openBankList.get(0);
						if (openingBank.getBankTypeName().indexOf(payBankName) != -1) {
							// 同行标识
							payInfoMap.put("difSign", "0");
						} else {
							// 跨行标识
							payInfoMap.put("difSign", "1");
						}

						payInfoMap.put("payeeBankCode", openingBank.getUnionBankCode());
						payInfoMap.put("payeeAccNo", vo.getPayeeBankAcc());
						payInfoMap.put("payeeAccName", vo.getPayeeBankAccName());
						payInfoMap.put("payeeBankName", vo.getPayeeBank());
						payInfoMap.put("payMoney", vo.getPayeeAmount());
						payInfoMap.put("payPurpose", "预收退款");
						Map<String, String> retMap = nbBankPay.erpTransfer(payInfoMap);
						String retCode = retMap.get("retCode");
						String retMsg = retMap.get("retMsg");
						// success
						if (!"0".equals(retCode)) {
							errmsg.append("单据号:" + billNo + "付款出错:" + retMsg);
						}
						// 先生成付款日志数据
						PayOrderListLogVO logvo = new PayOrderListLogVO();
						logvo.setId(String.valueOf(System.currentTimeMillis()));
						logvo.setOutid(billNo);
						logvo.setType(CONSTANTS_PAYORDERTYPE_5);
						logvo.setBankName(vo.getPayeeBank());
						logvo.setUserName(vo.getPayeeBankAccName());
						logvo.setBankNo(vo.getPayeeBankAcc());
						logvo.setMoney(vo.getPayeeAmount());
//					logvo.setProvince("");
						logvo.setCity(peeCity);
						logvo.setDescription(vo.getDescription());
						logvo.setOutidtime(vo.getLogTime());
						logvo.setOutbillid(billNo);
						// 付款中
						logvo.setStatus("2");
						logvo.setOperator(user.getStafferId());
						logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
						logvo.setPayaccount(bankBean.getBankNo());
						logvo.setPaybank(bankBean.getName());
						// 待确认
						logvo.setBankstatus("0");
						logvo.setPayBankId(bankId);
						logvo.setOperatorId(user.getId());
						logvo.setMessage(retMsg);
						// 先生成付款日志数据
						payOrderDao.createPayListLog(logvo);
					}
				}
			}

			if (errmsg.length() == 0) {
				errmsg.append("付款成功");
			}
		} catch (Exception e) {
			payLog.error("pay error", e);
		}
		return JSONTools.writeResponse(response, errmsg.toString());
	}

	public PayOrderDAO getPayOrderDao() {
		return payOrderDao;
	}

	public void setPayOrderDao(PayOrderDAO payOrderDao) {
		this.payOrderDao = payOrderDao;
	}

	public BankDAO getBankDAO() {
		return bankDAO;
	}

	public void setBankDAO(BankDAO bankDAO) {
		this.bankDAO = bankDAO;
	}

	public TravelApplyPayDAO getTravelApplyPayDAO() {
		return travelApplyPayDAO;
	}

	public void setTravelApplyPayDAO(TravelApplyPayDAO travelApplyPayDAO) {
		this.travelApplyPayDAO = travelApplyPayDAO;
	}

	public OpeningBankDAO getOpeningBankDAO() {
		return openingBankDAO;
	}

	public void setOpeningBankDAO(OpeningBankDAO openingBankDAO) {
		this.openingBankDAO = openingBankDAO;
	}

	public TcpApproveDAO getTcpApproveDAO() {
		return tcpApproveDAO;
	}

	public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO) {
		this.tcpApproveDAO = tcpApproveDAO;
	}

}
