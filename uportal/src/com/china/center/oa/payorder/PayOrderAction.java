package com.china.center.oa.payorder;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.BackPrePayApplyBean;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.StockPayApplyBean;
import com.china.center.oa.finance.bean.StockPrePayApplyBean;
import com.china.center.oa.finance.dao.BackPrePayApplyDAO;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.PayOrderDAO;
import com.china.center.oa.finance.dao.PayOrderModifyDAO;
import com.china.center.oa.finance.dao.StockPayApplyDAO;
import com.china.center.oa.finance.dao.StockPrePayApplyDAO;
import com.china.center.oa.finance.manager.payorder.NbBankPayImpl;
import com.china.center.oa.finance.vo.PayOrderListLogVO;
import com.china.center.oa.finance.vo.PayOrderModifyListLogVO;
import com.china.center.oa.finance.vo.PayOrderVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.OpeningBankBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.OpeningBankDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TravelApplyDAO;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;
import com.china.center.oa.tcp.manager.PayOrderManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.FileTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;

import edu.emory.mathcs.backport.java.util.Arrays;

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
	 * 	已付款
	 */
	private final String CONSTANTS_PAYORDERSTATUS_3 = "3";

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private PayOrderDAO payOrderDao;
	
	private PayOrderModifyDAO payOrderModifyDAO;

	private BankDAO bankDAO;

	private TravelApplyPayDAO travelApplyPayDAO;

	private OpeningBankDAO openingBankDAO;
	
	private TcpApproveDAO tcpApproveDAO;
	
	private TravelApplyDAO travelApplyDAO;
	
	private StockPayApplyDAO stockPayApplyDAO;
	
	private StockPrePayApplyDAO stockPrePayApplyDAO;
	
	private BackPrePayApplyDAO backPrePayApplyDAO;
	
	private PayOrderManager payOrderManager;
	
	private ProvinceDAO provinceDAO;
	
	private CityDAO cityDAO;
	
	private AttachmentDAO attachmentDAO;
	
	public ActionForward queryPayOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
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
		
		User user = Helper.getUser(request);
		List<RoleAuthBean> authList =  user.getAuth();
		for(RoleAuthBean authBean:authList)
		{
			if("1660".equals(authBean.getAuthId()))
			{
				request.setAttribute("hasAuth", 1);
				break;
			}
		}
		
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
			Map<String, String> checkMap = new HashMap<String, String>();
			for (int i = 0; i < billNoArray.length; i++) {
				Map<String, String> payInfoMap = new HashMap<String, String>();
				String billNoAndType = billNoArray[i];
				String[] array = StringUtils.split(billNoAndType, "_");
				// 单号
				String billNo = array[0];
				if (checkMap.containsKey(billNo)) {
					continue;
				}
				checkMap.put(billNo, billNo);
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
					// 先生成付款日志数据
					payOrderDao.createPayListLog(logvo);
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
						else
						{
							//更新付款状态为待确认
							payOrderDao.updateStockPayApply(billNo, "1");
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
					// 待确认
					logvo.setBankstatus("0");
					payOrderDao.createPayListLog(logvo);
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
						else
						{
							//更新付款状态为待确认
							payOrderDao.updateStockPrePayApply(billNo, "1");
						}
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
							BigDecimal payAmt = new BigDecimal(payBean.getCmoneys());
							payAmt = payAmt.divide(new BigDecimal(100));
							payInfoMap.put("payMoney", payAmt.toString());
							payInfoMap.put("payPurpose", "借款申请付款");
							// 一笔款多条收款明细,用明细id作为erpno
							payInfoMap.put("erpReqNo", payBean.getId());
							
							// 先生成付款日志数据
							PayOrderListLogVO logvo = new PayOrderListLogVO();
							logvo.setId(String.valueOf(System.currentTimeMillis()));
							logvo.setOutid(billNo);
							logvo.setType(CONSTANTS_PAYORDERTYPE_3);
							logvo.setBankName(payBean.getBankName());
							logvo.setUserName(payBean.getUserName());
							logvo.setBankNo(payBean.getBankNo());
							logvo.setMoney(payAmt.toString());
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
							payOrderDao.createPayListLog(logvo);
							
							Map<String, String> retMap = nbBankPay.erpTransfer(payInfoMap);
							String retCode = retMap.get("retCode");
							String retMsg = retMap.get("retMsg");
							// success
							if (!"0".equals(retCode)) {
								errmsg.append("单据号:" + billNo + "付款出错:" + retMsg);
							}
							else
							{
								//更新付款状态为待确认
								payOrderDao.updateTravelPayApply(payBean.getId(), "1");
							}
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
							BigDecimal payAmt = new BigDecimal(payBean.getCmoneys());
							payAmt = payAmt.divide(new BigDecimal(100));
							payInfoMap.put("payeeBankCode", openingBank.getUnionBankCode());
							payInfoMap.put("payeeAccNo", payBean.getBankNo());
							payInfoMap.put("payeeAccName", payBean.getUserName());
							payInfoMap.put("payMoney", payAmt.toString());
							payInfoMap.put("payPurpose", "报销申请付款");
							// 一笔款多条收款明细,用明细id作为erpno
							payInfoMap.put("erpReqNo", payBean.getId());
							
							// 先生成付款日志数据
							PayOrderListLogVO logvo = new PayOrderListLogVO();
							logvo.setId(String.valueOf(System.currentTimeMillis()));
							logvo.setOutid(billNo);
							logvo.setType(CONSTANTS_PAYORDERTYPE_4);
							logvo.setBankName(payBean.getBankName());
							logvo.setUserName(payBean.getUserName());
							logvo.setBankNo(payBean.getBankNo());
							logvo.setMoney(payAmt.toString());
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
							// 先生成付款日志数据
							payOrderDao.createPayListLog(logvo);
							
							Map<String, String> retMap = nbBankPay.erpTransfer(payInfoMap);
							String retCode = retMap.get("retCode");
							String retMsg = retMap.get("retMsg");
							// success
							if (!"0".equals(retCode)) {
								errmsg.append("单据号:" + billNo + "付款出错:" + retMsg);
							}
							else
							{
								//更新付款状态为待确认
								payOrderDao.updateTravelPayApply(payBean.getId(), "1");
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
						
						// 先生成付款日志数据
						PayOrderListLogVO logvo = new PayOrderListLogVO();
						logvo.setId(String.valueOf(System.currentTimeMillis()));
						logvo.setOutid(billNo);
						logvo.setType(CONSTANTS_PAYORDERTYPE_5);
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
						// 待确认
						logvo.setBankstatus("0");
						logvo.setPayBankId(bankId);
						logvo.setOperatorId(user.getId());
						// 先生成付款日志数据
						payOrderDao.createPayListLog(logvo);
						
						Map<String, String> retMap = nbBankPay.erpTransfer(payInfoMap);
						String retCode = retMap.get("retCode");
						String retMsg = retMap.get("retMsg");
						// success
						if (!"0".equals(retCode)) {
							errmsg.append("单据号:" + billNo + "付款出错:" + retMsg);
						}
						else
						{
							//更新付款状态为待确认
							payOrderDao.updateBackPrePayApply(billNo, "1");
						}
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
	
	
	public ActionForward modifyPayOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		payLog.info("modifyPayOrder");
		String outId = request.getParameter("outid");
		String outBillId = request.getParameter("outbillid");
	 	Map<String,String> paramMap = new HashMap<String, String>();
	 	paramMap.put("payOrderNo", outId);
	 	paramMap.put("outBillId", outBillId);
	 	
	 	List<PayOrderListLogVO> payOrderLogVoList = payOrderDao.queryPayOrderLogList(paramMap);
	 	
	 	ConditionParse cond = new ConditionParse();
	 	cond.addWhereStr();
	 	cond.addString(" and id='" + outBillId + "'");
	 	
	 	List<TravelApplyPayBean> payBeanList = travelApplyPayDAO.queryEntityBeansByCondition(cond);
	 	TravelApplyPayBean bean = payBeanList.get(0);
	 	String bankProvince = bean.getBankprovince();
	 	String bankCity = bean.getBankcity();
	 	
	 	request.setAttribute("bankProvince", bankProvince);
	 	request.setAttribute("bankCity", bankCity);
		request.setAttribute("payOrderLogVoList", payOrderLogVoList);
		return mapping.findForward("toModifyPayOrder");

	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public ActionForward saveModifyPayOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try
		{
			payLog.info("modifyPayOrder");
			User user = Helper.getUser(request);
			String outId = request.getParameter("outId");
			String outBillId = request.getParameter("outBillId");
		 	Map<String,String> paramMap = new HashMap<String, String>();
		 	paramMap.put("payOrderNo", outId);
		 	paramMap.put("outBillId", outBillId);
		 	
		 	List<PayOrderListLogVO> payOrderLogVoList = payOrderDao.queryPayOrderLogList(paramMap);
		 	if(payOrderLogVoList.size() == 0)
		 	{
		 		return JSONTools.writeResponse(response, "单据号:" + outId + ";单据子编号:" + outBillId + "查询不到数据");
		 	}
		 	String bankName = request.getParameter("bankName");
		 	String bankNo = request.getParameter("bankNo");
		 	String userName = request.getParameter("userName");
		 	String bankProvince = request.getParameter("bankprovince");
		 	String bankCity = request.getParameter("bankcity");
		 	if(StringUtils.isEmpty(bankName))
		 	{
		 		return JSONTools.writeResponse(response, "收款银行不能为空");
		 	}
		 	if(StringUtils.isEmpty(bankNo))
		 	{
		 		return JSONTools.writeResponse(response, "收款账号不能为空");
		 	}
		 	if(StringUtils.isEmpty(userName))
		 	{
		 		return JSONTools.writeResponse(response, "收款户名不能为空");
		 	}
		 	if(StringUtils.isEmpty(bankProvince))
		 	{
		 		return JSONTools.writeResponse(response, "开户省份不能为空");
		 	}
		 	if(StringUtils.isEmpty(bankCity))
		 	{
		 		return JSONTools.writeResponse(response, "开户城市不能为空");
		 	}
		 	PayOrderListLogVO vo = payOrderLogVoList.get(0);
		 	
		 	//新增修改历史记录
		 	PayOrderModifyListLogVO modify = new PayOrderModifyListLogVO();
		 	modify.setOriginalBankName(vo.getBankName());
		 	modify.setOriginalBankNo(vo.getBankNo());
		 	modify.setBankpaytime(vo.getBankpaytime());
		 	modify.setBankstatus(vo.getBankstatus());
		 	modify.setOriginalCity(vo.getCity());
		 	modify.setDescription(vo.getDescription());
		 	modify.setId(vo.getId());
		 	modify.setMessage(vo.getMessage());
		 	modify.setMoney(vo.getMoney());
		 	modify.setOperator(vo.getOperator());
		 	modify.setOperatorId(vo.getOperatorId());
		 	modify.setOutbillid(vo.getOutbillid());
		 	modify.setOutid(vo.getOutid());
		 	modify.setOutidtime(vo.getOutidtime());
		 	modify.setPayaccount(vo.getPayaccount());
		 	modify.setPaybank(vo.getPaybank());
		 	modify.setPayBankId(vo.getPayBankId());
		 	modify.setPaytime(vo.getPaytime());
		 	modify.setOriginalProvince(vo.getProvince());
		 	modify.setStatus(vo.getStatus());
		 	modify.setType(vo.getType());
		 	modify.setUpdateTime(format.format(Calendar.getInstance().getTime()));
		 	modify.setOriginalUserName(vo.getUserName());
		 	modify.setNewBankName(bankName);
		 	modify.setNewBankNo(bankNo);
		 	modify.setNewUserName(userName);
		 	modify.setNewCity(bankCity);
		 	modify.setNewProvince(bankProvince);
		 	modify.setModifyStafferId(user.getStafferId());

		 	String type = vo.getType();
		 	if(CONSTANTS_PAYORDERTYPE_1.equals(type))
		 	{
		 		ConditionParse cond = new ConditionParse();
			 	cond.addCondition("id", "=", outBillId);
			 	List<StockPayApplyBean> payBeanList = stockPayApplyDAO.queryEntityBeansByCondition(cond);
			 	if(payBeanList.size() == 0 || payBeanList.size() > 1)
			 	{
			 		return JSONTools.writeResponse(response, "单据号:" + outId + ";单据子编号:" + outBillId + "查询采购付款信息失败");
			 	}
			 	StockPayApplyBean stockBean = payBeanList.get(0);
			 	stockBean.setCustAccountBank(bankName);
			 	stockBean.setCustAccountName(userName);
			 	stockBean.setCustAccount(bankNo);
			 	stockBean.setPayFlag("0");
			 	List<CityBean> cityBeanList = cityDAO.queryEntityBeansByCondition(" where name=?", bankCity);
			 	if(cityBeanList.size() == 0)
			 	{
			 		return JSONTools.writeResponse(response, "单据号:" + outId + ";单据子编号:" + outBillId + "查询城市信息出错");
			 	}
			 	stockBean.setCityId(cityBeanList.get(0).getId());
			 	
			 	List<ProvinceBean> provinceBeanList = provinceDAO.queryEntityBeansByCondition(" where name=?", bankProvince);
			 	if(provinceBeanList.size() == 0)
			 	{
			 		return JSONTools.writeResponse(response, "单据号:" + outId + ";单据子编号:" + outBillId + "查询省份信息出错");
			 	}
			 	
			 	stockBean.setProvinceId(provinceBeanList.get(0).getId());
			 	
			 	payOrderManager.updateStockPayApply(stockBean, modify, vo);
			 	
		 	}
		 	if(CONSTANTS_PAYORDERTYPE_2.equals(type))
		 	{
		 		ConditionParse cond = new ConditionParse();
			 	cond.addCondition("id", "=", outBillId);
			 	List<StockPrePayApplyBean> payBeanList = stockPrePayApplyDAO.queryEntityBeansByCondition(cond);
			 	if(payBeanList.size() == 0 || payBeanList.size() > 1)
			 	{
			 		return JSONTools.writeResponse(response, "单据号:" + outId + ";单据子编号:" + outBillId + "查询采购预付款信息失败");
			 	}
			 	
			 	StockPrePayApplyBean stockPreBean = payBeanList.get(0);
			 	stockPreBean.setCustAccountBank(bankName);
			 	stockPreBean.setCustAccountName(userName);
			 	stockPreBean.setCustAccount(bankNo);
			 	stockPreBean.setPayFlag("0");
			 	List<CityBean> cityBeanList = cityDAO.queryEntityBeansByCondition(" where name=?", bankCity);
			 	if(cityBeanList.size() == 0)
			 	{
			 		return JSONTools.writeResponse(response, "单据号:" + outId + ";单据子编号:" + outBillId + "查询城市信息出错");
			 	}
			 	stockPreBean.setCityId(cityBeanList.get(0).getId());
			 	
			 	List<ProvinceBean> provinceBeanList = provinceDAO.queryEntityBeansByCondition(" where name=?", bankProvince);
			 	if(provinceBeanList.size() == 0)
			 	{
			 		return JSONTools.writeResponse(response, "单据号:" + outId + ";单据子编号:" + outBillId + "查询省份信息出错");
			 	}
			 	
			 	stockPreBean.setProvinceId(provinceBeanList.get(0).getId());
			 	
			 	payOrderManager.updateStockPrePayApply(stockPreBean, modify, vo);
		 	}
		 	if(CONSTANTS_PAYORDERTYPE_3.equals(type) || CONSTANTS_PAYORDERTYPE_4.equals(type))
		 	{
		 		ConditionParse cond = new ConditionParse();
			 	cond.addCondition("id", "=", outBillId);
			 	
			 	List<TravelApplyPayBean> payBeanList = travelApplyPayDAO.queryEntityBeansByCondition(cond);
			 	if(payBeanList.size() == 0 || payBeanList.size() > 1)
			 	{
			 		return JSONTools.writeResponse(response, "单据号:" + outId + ";单据子编号:" + outBillId + "查询借款/报销信息失败");
			 	}
			 	
			 	//修改付款信息
			 	TravelApplyPayBean travelBean = payBeanList.get(0);
			 	travelBean.setBankName(bankName);
			 	travelBean.setUserName(userName);
			 	travelBean.setBankNo(bankNo);
			 	travelBean.setBankcity(bankCity);
			 	travelBean.setBankprovince(bankProvince);
			 	travelBean.setPayFlag("0");
			 	
			 	payOrderManager.updateTravelPayApply(travelBean, modify, vo);
		 	}
		 	if(CONSTANTS_PAYORDERTYPE_5.equals(type))
		 	{
		 		List<BackPrePayApplyBean> backPreBeanList = backPrePayApplyDAO.queryEntityBeansByCondition(" where id=?", outBillId);
		 		if(backPreBeanList.size() == 0 || backPreBeanList.size() > 1)
			 	{
			 		return JSONTools.writeResponse(response, "单据号:" + outId + ";单据子编号:" + outBillId + "查询预收退款信息失败");
			 	}
		 		BackPrePayApplyBean backPreBean = backPreBeanList.get(0);
		 		//修改付款信息
		 		backPreBean.setReceiveBank(bankName);
		 		backPreBean.setReceiver(userName);
		 		backPreBean.setReceiveAccount(bankNo);
		 		backPreBean.setBankcity(bankCity);
		 		backPreBean.setBankprovince(bankProvince);
		 		backPreBean.setPayFlag("0");
		 		payOrderManager.updateBackPrePayApply(backPreBean, modify, vo);
		 		
		 	}
		}
		catch(Exception e)
		{
			payLog.error("修改付款信息出错",e);
			JSONTools.writeResponse(response, e.getMessage());
			throw e;
		}
		return JSONTools.writeResponse(response, "修改成功");
		
	}
	
	/**
	 * 	查询付款成功的单据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryAttachement(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		Map<String, String> queryMap = new HashMap<String, String>();
		String payOrderType = request.getParameter("payOrderType");
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
		queryMap.put("payOrderStatus", CONSTANTS_PAYORDERSTATUS_3);
		
		User user = Helper.getUser(request);
		List<RoleAuthBean> authList =  user.getAuth();
		for(RoleAuthBean authBean:authList)
		{
			if("1660".equals(authBean.getAuthId()))
			{
				request.setAttribute("hasAuth", 1);
				break;
			}
		}
		
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

		List<PayOrderListLogVO> list = payOrderDao.queryPayOrderLogList(queryMap);
		request.setAttribute("payOrderLogList", list);
		request.setAttribute("queryMap", queryMap);
		return mapping.findForward("queryAttachement");

	}
	
	/**
	 * 	查询付款成功的单据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward toUploadAttachement(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String outId = request.getParameter("outid");
		String outBillId = request.getParameter("outbillid");
	 	Map<String,String> paramMap = new HashMap<String, String>();
	 	paramMap.put("payOrderNo", outId);
	 	paramMap.put("outBillId", outBillId);
	 	paramMap.put("payOrderStatus", CONSTANTS_PAYORDERSTATUS_3);
	 	List<PayOrderListLogVO> payOrderLogVoList = payOrderDao.queryPayOrderLogList(paramMap);
	 	for(PayOrderListLogVO vo : payOrderLogVoList)
	 	{
	 		String id = vo.getId();
	 		ConditionParse cond = new ConditionParse();
	 		cond.addCondition("refid", "=", id);
	 		List<AttachmentBean> attachmentList = attachmentDAO.queryEntityBeansByCondition(cond);
	 		vo.setAttachmentList(attachmentList);
	 	}
	 	request.setAttribute("outbillid", outBillId);
	 	request.setAttribute("outid", outId);
		request.setAttribute("payOrderLogVoList", payOrderLogVoList);
		return mapping.findForward("toUploadAttachement");

	}
	
	/**
	 * 	上传附件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward uploadAttachement(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
	 	// 模板最多20M
        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 20L);
        
        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            payLog.error("上传附件失败，超过20M", e);
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件不能超过20M");
			return mapping.findForward("toUploadAttachement");
        }
        catch (Exception e)
        {
        	payLog.error("上传附件失败", e);
        	request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
			return mapping.findForward("toUploadAttachement");
        }
        
        String outId = rds.getParameter("outid");
		String outBillId = rds.getParameter("outbillid");
		String delAttaId = rds.getParameter("delAttaId");
	 	Map<String,String> paramMap = new HashMap<String, String>();
	 	paramMap.put("payOrderNo", outId);
	 	paramMap.put("outBillId", outBillId);
	 	paramMap.put("payOrderStatus", CONSTANTS_PAYORDERSTATUS_3);
	 	List<PayOrderListLogVO> payOrderLogVoList = payOrderDao.queryPayOrderLogList(paramMap);
	 	if(payOrderLogVoList.size() == 0 || payOrderLogVoList.size() >1)
	 	{
	 		request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据出错，单据号:" + outBillId);

			return mapping.findForward("toUploadAttachement");
	 	}
        
        
        
        PayOrderListLogVO payVo = payOrderLogVoList.get(0);
        
        BeanUtil.getBean(payVo, rds.getParmterMap());
        
        if ( !rds.haveStream())
        {
            return null;
        }
        
        Map<String, InputStream> streamMap = rds.getStreamMap();
        
        List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

        for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
        {
            AttachmentBean bean = new AttachmentBean();

            BufferedOutputStream out = null;

            UtilStream ustream = null;

            try
            {
            	String attachePath = ConfigLoader.getProperty("financeAttachmentPath");
                String savePath = mkdir(attachePath);

                String fileAlais = SequenceTools.getSequence();

                String fileName = FileTools.getFileName(rds.getFileName(entry.getKey()));

                String rabsPath = '/' + savePath + '/' + fileAlais + "."
                                  + FileTools.getFilePostfix(fileName).toLowerCase();

                String filePath = attachePath + '/' + rabsPath;

                bean.setName(fileName);

                bean.setPath(rabsPath);

                bean.setLogTime(TimeTools.now());

                out = new BufferedOutputStream(new FileOutputStream(filePath));

                ustream = new UtilStream(entry.getValue(), out);

                ustream.copyStream();

                attachmentList.add(bean);
            }
            catch (IOException e)
            {
                payLog.error("上传附件失败", e);
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "上传附件失败:" + e.getMessage());
    			return mapping.findForward("toUploadAttachement");
            }
            finally
            {
            	IOUtils.closeQuietly(out);
                if (ustream != null)
                {
                    try
                    {
                        ustream.close();
                    }
                    catch (IOException e)
                    {
                        payLog.error("close stream error", e);
                    }
                }
            }
        }
        
        try
        {
        	String[] delArray = new String[] {};
        	if(StringUtils.isNotEmpty(delAttaId))
        	{
        		delArray = StringUtils.split(delAttaId,",");
        	}
        	List<String> deleteIdList = Arrays.asList(delArray);
        	payVo.setAttachmentList(attachmentList);
        	payOrderManager.uploadPayOrderAttachement(payVo,deleteIdList);
        }
        catch(Exception e)
        {
        	payLog.error("保存附件失败", e);
        	request.setAttribute(KeyConstant.ERROR_MESSAGE,"保存附件失败:" + e.getMessage());
			return mapping.findForward("toUploadAttachement");
        }
        
        request.setAttribute(KeyConstant.MESSAGE, "上传成功");

		return mapping.findForward("toUploadAttachement");
	}
	
	
	/**
     * downAttachmentFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downAttachmentFile(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String path = ConfigLoader.getProperty("financeAttachmentPath");

        String id = request.getParameter("id");

        AttachmentBean bean = attachmentDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError(mapping, request);
        }

        path += bean.getPath();

        File file = new File(path);
        
        InputStream fis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        // 清空response
        response.reset();
        // 设置response的Header
        response.addHeader("Content-Disposition", "attachment;filename=" + StringTools.getStringBySet(bean.getName(),
                "GBK", "ISO8859-1"));
        response.addHeader("Content-Length", "" + file.length());
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        response.setContentType("application/octet-stream");
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
        IOUtils.closeQuietly(toClient);
        return null;
    }
	
	private String mkdir(String root)
    {
        String path = TimeTools.now("yyyy/MM/dd/HH") + "/"
                      + SequenceTools.getSequence(String.valueOf(new Random().nextInt(1000)));

        FileTools.mkdirs(root + '/' + path);

        return path;
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

	public TravelApplyDAO getTravelApplyDAO() {
		return travelApplyDAO;
	}

	public void setTravelApplyDAO(TravelApplyDAO travelApplyDAO) {
		this.travelApplyDAO = travelApplyDAO;
	}

	public PayOrderModifyDAO getPayOrderModifyDAO() {
		return payOrderModifyDAO;
	}

	public void setPayOrderModifyDAO(PayOrderModifyDAO payOrderModifyDAO) {
		this.payOrderModifyDAO = payOrderModifyDAO;
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

	public BackPrePayApplyDAO getBackPrePayApplyDAO() {
		return backPrePayApplyDAO;
	}

	public void setBackPrePayApplyDAO(BackPrePayApplyDAO backPrePayApplyDAO) {
		this.backPrePayApplyDAO = backPrePayApplyDAO;
	}

	public ProvinceDAO getProvinceDAO() {
		return provinceDAO;
	}

	public void setProvinceDAO(ProvinceDAO provinceDAO) {
		this.provinceDAO = provinceDAO;
	}

	public CityDAO getCityDAO() {
		return cityDAO;
	}

	public void setCityDAO(CityDAO cityDAO) {
		this.cityDAO = cityDAO;
	}

	public PayOrderManager getPayOrderManager() {
		return payOrderManager;
	}

	public void setPayOrderManager(PayOrderManager payOrderManager) {
		this.payOrderManager = payOrderManager;
	}

	public AttachmentDAO getAttachmentDAO() {
		return attachmentDAO;
	}

	public void setAttachmentDAO(AttachmentDAO attachmentDAO) {
		this.attachmentDAO = attachmentDAO;
	}

}
