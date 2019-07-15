package com.china.center.oa.payorder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.PayOrderDAO;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.manager.payorder.NbBankPayImpl;
import com.china.center.oa.finance.vo.PayOrderListLogVO;
import com.china.center.oa.finance.vo.PayOrderVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.dao.TravelApplyItemDAO;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;
import com.china.center.oa.tcp.manager.BackPrePayManager;
import com.china.center.oa.tcp.manager.ExpenseManager;
import com.china.center.oa.tcp.manager.TravelApplyManager;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.tools.MathTools;

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

	/**
	 * 待确认
	 */
	private final String CONSTANTS_PAYORDERSTATUS_2 = "2";

	/**
	 * 已付款
	 */
	private final String CONSTANTS_PAYORDERSTATUS_3 = "3";

	/**
	 * 未成功待付款
	 */
	private final String CONSTANTS_PAYORDERSTATUS_4 = "4";
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private PayOrderDAO payOrderDao;

	private BankDAO bankDAO;

	private FinanceFacade financeFacade;

	private BackPrePayManager backPrePayManager;

	private ExpenseManager expenseManager;

	private TravelApplyItemDAO travelApplyItemDAO;

	private TravelApplyPayDAO travelApplyPayDAO;

	private TravelApplyManager travelApplyManager;

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
		queryMap.put("payOrderNo", payOrderNo);
		queryMap.put("payeeBank", payeeBank);
		queryMap.put("payeeAccName", payeeAccName);
		queryMap.put("payeeAcc", payeeAcc);
		queryMap.put("payeeAmount", payeeAmount);
		queryMap.put("billTime", billTime);
		queryMap.put("payOrderType", payOrderType);
		queryMap.put("payOrderStatus", payOrderStatus);
		if (StringUtils.isEmpty(payOrderStatus)) {
			payOrderStatus = CONSTANTS_PAYORDERSTATUS_1;
		}

		if (CONSTANTS_PAYORDERSTATUS_1.contentEquals(payOrderStatus)) {
			List<PayOrderVO> list = new ArrayList<PayOrderVO>();
			// 查询所有单据的待付款
			if (StringUtils.isEmpty(payOrderType)) {
				list = payOrderDao.queryPayOrderList(queryMap);
			} 
			else 
			{
				if (CONSTANTS_PAYORDERTYPE_1.equals(payOrderType)) 
				{
					list = payOrderDao.queryPayOrderList41(queryMap);
				}
				if (CONSTANTS_PAYORDERTYPE_2.equals(payOrderType)) 
				{
					list = payOrderDao.queryPayOrderList42(queryMap);
				}
				if (CONSTANTS_PAYORDERTYPE_3.equals(payOrderType)) 
				{
					list = payOrderDao.queryPayOrderList43(queryMap);
				}
				if (CONSTANTS_PAYORDERTYPE_4.equals(payOrderType)) 
				{
					list = payOrderDao.queryPayOrderList44(queryMap);
				}
				if (CONSTANTS_PAYORDERTYPE_5.equals(payOrderType)) 
				{
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

		for (int i = 0; i < billNoArray.length; i++) {
			String billNoAndType = billNoArray[i];
			String[] array = StringUtils.split(billNoAndType, "_");
			String billNo = array[0];
			String billType = array[1];
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
		
		//返回初始页面的所有参数都带上
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
		if(StringUtils.isEmpty(payBankCity))
		{
			return JSONTools.writeResponse(response, "付款银行账号的开户省/市为空,银行账号名称:"+ bankBean.getName() +",请完善信息!");
		}
		payBankCity = payBankCity.trim();
		String payBankNo = bankBean.getBankNo();
		if(StringUtils.isEmpty(payBankNo))
		{
			return JSONTools.writeResponse(response, "付款银行的付款账号为空,银行账号名称:"+ bankBean.getName() +",请完善信息!");
		}
		payBankNo = payBankNo.trim();
		String payBankName = bankBean.getName();
		if(StringUtils.isEmpty(payBankName))
		{
			return JSONTools.writeResponse(response, "付款银行的银行名称为空,银行账号名称:"+ bankBean.getName() +",请完善信息!");
		}
		String[] payBankNameArray = StringUtils.split(payBankName,"-");
		if(payBankNameArray == null || payBankNameArray.length < 4)
		{
			return JSONTools.writeResponse(response, "付款银行的银行名称不合法,银行账号名称:"+ bankBean.getName() +",请完善信息!");
		}
		String payBankNameSub = payBankNameArray[3];
		// 付款银行信息
		NbBankPayImpl nbBankPay = new NbBankPayImpl();

		boolean tempFlag = true;
		StringBuffer errmsg = new StringBuffer();
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
				//同城
				String peeCity = vo.getCityName();
				
				if(payBankCity.equals(peeCity))
				{
					//同城
					payInfoMap.put("areaSign", "0");
				}
				else
				{
					//异地
					payInfoMap.put("areaSign", "1");
				}
				String peeBankName = vo.getPayeeBank();
				if(payBankNameSub.indexOf(peeBankName) != -1)
				{
					//同行标识
					payInfoMap.put("difSign","0");
				}
				else
				{
					//跨行标识
					payInfoMap.put("difSign","1");
				}
				//先生成付款日志数据
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
				//付款中
				logvo.setStatus("2");
				logvo.setOperator(user.getStafferId());
				logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
				logvo.setPayaccount(bankBean.getBankNo());
				logvo.setPaybank(bankBean.getName());
				//待确认
				logvo.setBankstatus("0");
				//先生成付款日志数据
				payOrderDao.createPayListLog(logvo);
				// bank支持电子支付
				if (tempFlag) {
					payInfoMap.put("payeeAccNo", vo.getPayeeBankAcc());
					payInfoMap.put("payeeAccName", vo.getPayeeBankAccName());
					Map<String,String> retMap = nbBankPay.erpTransfer(payInfoMap);
					String retCode = retMap.get("retCode");
					String retMsg = retMap.get("retMsg");
					//success
					if("0".equals(retCode))
					{
						retMsg = endPayOrder1ByCash(user.getId(), billNo, bankId, vo.getPayeeAmount());
						errmsg.append(retMsg);
					}
					else
					{
						errmsg.append(retMsg);
					}
					
				}
				
			}
			if (CONSTANTS_PAYORDERTYPE_2.equals(billType)) {
				List<PayOrderVO> payOrderVOList = payOrderDao.queryPayOrderList42(paramMap);
				if (payOrderVOList.size() == 0 || payOrderVOList.size() > 1) {
					errmsg.append("查询单据编号:" + billNo + "出错!");
					continue;
				}
				PayOrderVO vo = payOrderVOList.get(0);
				//同城
				String peeCity = vo.getCityName();
				
				if(payBankCity.equals(peeCity))
				{
					//同城
					payInfoMap.put("areaSign", "0");
				}
				else
				{
					//异地
					payInfoMap.put("areaSign", "1");
				}
				String peeBankName = vo.getPayeeBank();
				if(payBankNameSub.indexOf(peeBankName) != -1)
				{
					//同行标识
					payInfoMap.put("difSign","0");
				}
				else
				{
					//跨行标识
					payInfoMap.put("difSign","1");
				}
				//先生成付款日志数据
				PayOrderListLogVO logvo = new PayOrderListLogVO();
				logvo.setId(String.valueOf(System.currentTimeMillis()));
				logvo.setOutid(billNo);
				logvo.setType(CONSTANTS_PAYORDERTYPE_2);
				logvo.setBankName(vo.getPayeeBank());
				logvo.setUserName(vo.getPayeeBankAccName());
				logvo.setBankNo(vo.getPayeeBankAcc());
				logvo.setMoney(vo.getPayeeAmount());
//				logvo.setProvince("");
//				logvo.setCity("");
				logvo.setDescription(vo.getDescription());
				logvo.setOutidtime(vo.getLogTime());
				//付款中
				logvo.setStatus("2");
				logvo.setOperator(user.getStafferId());
				logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
				logvo.setPayaccount(bankBean.getBankNo());
				logvo.setPaybank(bankBean.getName());
				//待确认
				logvo.setBankstatus("0");
				//先生成付款日志数据
				payOrderDao.createPayListLog(logvo);
				// bank支持电子支付
				if (tempFlag) {
					Map<String,String> retMap = nbBankPay.erpTransfer(payInfoMap);
					String retCode = retMap.get("retCode");
					String retMsg = retMap.get("retMsg");
					//success
					if("0".equals(retCode))
					{
						retMsg = endPayOrder2ByCash(user.getId(), billNo, bankId, vo.getPayeeAmount());
						errmsg.append(retMsg);
					}
					else
					{
						errmsg.append(retMsg);
					}
				}

			}
			if (CONSTANTS_PAYORDERTYPE_3.equals(billType)) {
				List<PayOrderVO> payOrderVOList = payOrderDao.queryPayOrderList43(paramMap);
				if (payOrderVOList.size() == 0 || payOrderVOList.size() > 1) {
					errmsg.append("查询单据编号:" + billNo + "出错!");
					continue;
				}
				PayOrderVO vo = payOrderVOList.get(0);
				//根据报销单，查找报销收款明细表
				ConditionParse parse = new ConditionParse();
				parse.addWhereStr();
				parse.addCondition("parentid", "=", vo.getBillNo());
				List<TravelApplyPayBean> applyPayList = travelApplyPayDAO.queryEntityBeansByCondition(parse);
				if(applyPayList.size() == 0)
				{
					errmsg.append("查询单据明细出错，编号:" + billNo);
					continue;
				}
				//按收款明细支付
				for(TravelApplyPayBean payBean:applyPayList)
				{
					//先生成付款日志数据
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
					//付款中
					logvo.setStatus("2");
					logvo.setOperator(user.getStafferId());
					logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
					logvo.setPayaccount(bankBean.getBankNo());
					logvo.setPaybank(bankBean.getName());
					//待确认
					logvo.setBankstatus("0");
					//先生成付款日志数据
					payOrderDao.createPayListLog(logvo);
					
					// bank支持电子支付
					if (tempFlag) {
						//同城
						String peeCity = payBean.getBankcity();
						if(payBankCity.equals(peeCity))
						{
							//同城
							payInfoMap.put("areaSign", "0");
						}
						else
						{
							//异地
							payInfoMap.put("areaSign", "1");
						}
						String peeBankName = payBean.getBankName();
						if(payBankNameSub.indexOf(peeBankName) != -1)
						{
							//同行标识
							payInfoMap.put("difSign","0");
						}
						else
						{
							//跨行标识
							payInfoMap.put("difSign","1");
						}
						payInfoMap.put("payeeAccNo", payBean.getBankNo());
						payInfoMap.put("payeeAccName", payBean.getUserName());
						Map<String,String> retMap = nbBankPay.erpTransfer(payInfoMap);
						String retCode = retMap.get("retCode");
						String retMsg = retMap.get("retMsg");
						//success
						if("0".equals(retCode))
						{
							retMsg = endPayOrder3ByCash(user, user.getId(), billNo, bankId, vo.getPayeeAmount(),bankBean);
							errmsg.append(retMsg);
						}
						else
						{
							errmsg.append(retMsg);
						}
					}
				}
				
			}
			if (CONSTANTS_PAYORDERTYPE_4.equals(billType)) {

				List<PayOrderVO> payOrderVOList = payOrderDao.queryPayOrderList44(paramMap);
				if (payOrderVOList.size() == 0 || payOrderVOList.size() > 1) {
					errmsg.append("查询单据编号:" + billNo + "出错!");
					continue;
				}
				PayOrderVO vo = payOrderVOList.get(0);
				//根据报销单，查找报销收款明细表
				ConditionParse parse = new ConditionParse();
				parse.addWhereStr();
				parse.addCondition("parentid", "=", vo.getBillNo());
				List<TravelApplyPayBean> applyPayList = travelApplyPayDAO.queryEntityBeansByCondition(parse);
				if(applyPayList.size() == 0)
				{
					errmsg.append("查询单据明细出错，编号:" + billNo);
					continue;
				}
				//按收款明细支付
				for(TravelApplyPayBean payBean:applyPayList)
				{
					//先生成付款日志数据
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
					//付款中
					logvo.setStatus("2");
					logvo.setOperator(user.getStafferId());
					logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
					logvo.setPayaccount(bankBean.getBankNo());
					logvo.setPaybank(bankBean.getName());
					//待确认
					logvo.setBankstatus("0");
					//先生成付款日志数据
					payOrderDao.createPayListLog(logvo);
					// bank支持电子支付
					if (tempFlag) {
						String peeCity = payBean.getBankcity();
						if(payBankCity.equals(peeCity))
						{
							//同城
							payInfoMap.put("areaSign", "0");
						}
						else
						{
							//异地
							payInfoMap.put("areaSign", "1");
						}
						String peeBankName = payBean.getBankName();
						if(payBankNameSub.indexOf(peeBankName) != -1)
						{
							//同行标识
							payInfoMap.put("difSign","0");
						}
						else
						{
							//跨行标识
							payInfoMap.put("difSign","1");
						}
						payInfoMap.put("payeeAccNo", payBean.getBankNo());
						payInfoMap.put("payeeAccName", payBean.getUserName());
						Map<String,String> retMap = nbBankPay.erpTransfer(payInfoMap);
						String retCode = retMap.get("retCode");
						String retMsg = retMap.get("retMsg");
						//success
						if("0".equals(retCode))
						{
							retMsg = endPayOrder4ByCash(user, billNo, bankId, vo.getPayeeAmount());
							errmsg.append(retMsg);
						}
						else
						{
							errmsg.append(retMsg);
						}
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
				//先生成付款日志数据
				PayOrderListLogVO logvo = new PayOrderListLogVO();
				logvo.setId(String.valueOf(System.currentTimeMillis()));
				logvo.setOutid(billNo);
				logvo.setType(CONSTANTS_PAYORDERTYPE_5);
				logvo.setBankName(vo.getPayeeBank());
				logvo.setUserName(vo.getPayeeBankAccName());
				logvo.setBankNo(vo.getPayeeBankAcc());
				logvo.setMoney(vo.getPayeeAmount());
//				logvo.setProvince("");
//				logvo.setCity("");
				logvo.setDescription(vo.getDescription());
				logvo.setOutidtime(vo.getLogTime());
				//付款中
				logvo.setStatus("2");
				logvo.setOperator(user.getStafferId());
				logvo.setPaytime(format.format(Calendar.getInstance().getTime()));
				logvo.setPayaccount(bankBean.getBankNo());
				logvo.setPaybank(bankBean.getName());
				//待确认
				logvo.setBankstatus("0");
				//先生成付款日志数据
				payOrderDao.createPayListLog(logvo);
				// bank支持电子支付
				if (tempFlag) {
					String peeCity = vo.getCityName();
					if(payBankCity.equals(peeCity))
					{
						//同城
						payInfoMap.put("areaSign", "0");
					}
					else
					{
						//异地
						payInfoMap.put("areaSign", "1");
					}
					String peeBankName = vo.getPayeeBank();
					if(payBankNameSub.indexOf(peeBankName) != -1)
					{
						//同行标识
						payInfoMap.put("difSign","0");
					}
					else
					{
						//跨行标识
						payInfoMap.put("difSign","1");
					}
					Map<String,String> retMap = nbBankPay.erpTransfer(payInfoMap);
					String retCode = retMap.get("retCode");
					String retMsg = retMap.get("retMsg");
					//success
					if("0".equals(retCode))
					{
						retMsg = endPayOrder5ByCash(user, billNo, bankId, vo.getPayeeAmount());
						errmsg.append(retMsg);
					}
					else
					{
						errmsg.append(retMsg);
					}
				}
			}
		}

		if (errmsg.length() == 0) {
			errmsg.append("付款成功");
		}

		return JSONTools.writeResponse(response, errmsg.toString());
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
			payLog.error("endPayOrder1ByCash error", e);
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
			payLog.error("endPayOrder2ByCash error", e);
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
			payLog.error("endPayOrder3ByCash error", e);
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
			payLog.error("endPayOrder4ByCash error", e);
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
			payLog.error("endPayOrder5ByCash error", e);
		}
		return retMsg;
	}
	
	public FinanceFacade getFinanceFacade() {
		return financeFacade;
	}

	public void setFinanceFacade(FinanceFacade financeFacade) {
		this.financeFacade = financeFacade;
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

}
