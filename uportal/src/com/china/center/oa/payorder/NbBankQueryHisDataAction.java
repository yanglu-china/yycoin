package com.china.center.oa.payorder;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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

import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.NbBankHisBalanceBean;
import com.china.center.oa.finance.bean.NbBankHisDataBean;
import com.china.center.oa.finance.dao.NbBankHisBalanceDAO;
import com.china.center.oa.finance.dao.NbBankHisDataDAO;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;

public class NbBankQueryHisDataAction extends DispatchAction {
	
	 private final Log _logger = LogFactory.getLog(getClass());

	private final String QUERYNBBANKHISDATA = "queryNbBankHisData";
	
	private final String QUERYNBBANKHISBALANCE = "queryNbBankHisBalance";

	private NbBankHisDataDAO nbBankHisDataDao;
	
	private NbBankHisBalanceDAO nbBankHisBalanceDao;

	public ActionForward queryHisData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYNBBANKHISDATA, request, condtion);

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYNBBANKHISDATA, request, condtion,
				this.nbBankHisDataDao);

		return JSONTools.writeResponse(response, jsonstr);

	}
	
	public ActionForward queryHisBalance(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYNBBANKHISBALANCE, request, condtion);

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYNBBANKHISBALANCE, request, condtion,
				this.nbBankHisBalanceDao);

		return JSONTools.writeResponse(response, jsonstr);

	}

	public ActionForward exportHisData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		OutputStream out = null;

		String filenName = "exportHisData_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename=" + filenName);

		WriteFile write = null;
		
		ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYNBBANKHISDATA);
		
		condtion.addWhereStr();
		
		String transDate = request.getParameter("transDate");
		String bankAcc = request.getParameter("bankAcc");
		String cdSign = request.getParameter("cdSign");
		String amt = request.getParameter("amt");
		if(StringUtils.isNotEmpty(transDate))
		{
			condtion.addCondition("transdate", "=", transDate);
		}
		if(StringUtils.isNotEmpty(bankAcc))
		{
			condtion.addCondition("bankAcc", "=", bankAcc);
		}

		if(StringUtils.isNotEmpty(cdSign))
		{
			condtion.addCondition("cdSign", "=", cdSign);
		}

		if(StringUtils.isNotEmpty(amt))
		{
			condtion.addCondition("amt", "=", amt);
		}

		int count = nbBankHisDataDao.countVOByCondition(condtion.toString());

		if (count > 150000) {
			return ActionTools.toError("导出数量大于150000,请重新选择查询条件", mapping, request);
		}

		try {
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("账号,开户银行,户名,对方账号,对方户名,对方开户行,收支方向,交易金额,明细余额,交易日期,用途");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			while (page.nextPage()) {
				List<NbBankHisDataBean> dataList = nbBankHisDataDao.queryEntityVOsByCondition(condtion, page);

				for (NbBankHisDataBean each : dataList) {
						line.writeColumn("[" + each.getBankAcc() + "]");
						line.writeColumn(each.getBankName());
						line.writeColumn(each.getAccName());
						if(StringUtils.isNotEmpty(each.getOppAccNo()))
						{
							line.writeColumn("[" + each.getOppAccNo()+ "]");
						}
						else
						{
							line.writeColumn("");
						}
						line.writeColumn(each.getOppAccName());
						line.writeColumn(each.getOppAccBank());
						line.writeColumn(DefinedCommon.getValue("cdSign", each.getCdSign()));
						line.writeColumn(each.getAmt().toString());
						line.writeColumn(each.getBal().toString());
						line.writeColumn(each.getTransDate().substring(0,10));
						line.writeColumn(each.getUses());

						line.writeLine();

				}
			}

			write.close();
		} catch (Throwable e) {
			_logger.error(e, e);

			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
				}
			}

			if (write != null) {

				try {
					write.close();
				} catch (IOException e1) {
				}
			}
		}

		return null;
	}
	
	
	public ActionForward exportHisBalanceData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		OutputStream out = null;

		String filenName = "exportHisBalanceData_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename=" + filenName);

		WriteFile write = null;
		
		ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYNBBANKHISBALANCE);
		
		condtion.addWhereStr();
		
		int count = nbBankHisBalanceDao.countVOByCondition(condtion.toString());

		if (count > 150000) {
			return ActionTools.toError("导出数量大于150000,请重新选择查询条件", mapping, request);
		}

		try {
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("日期,账号,银行,公司,户名,账户属性,余额");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			while (page.nextPage()) {
				List<NbBankHisBalanceBean> dataList = nbBankHisBalanceDao.queryEntityVOsByCondition(condtion, page);

				for (NbBankHisBalanceBean each : dataList) {
						line.writeColumn(each.getBusiness_date());
						line.writeColumn("[" +each.getBankAcc() + "]");
						line.writeColumn(each.getBankName());
						line.writeColumn(each.getCorpName());
						line.writeColumn(each.getAccName());
						line.writeColumn(each.getAccAttribute());
						line.writeColumn(each.getBalance().toString());
						line.writeLine();

				}
			}

			write.close();
		} catch (Throwable e) {
			_logger.error(e, e);

			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) {
				}
			}

			if (write != null) {

				try {
					write.close();
				} catch (IOException e1) {
				}
			}
		}

		return null;
	}

	public NbBankHisDataDAO getNbBankHisDataDao() {
		return nbBankHisDataDao;
	}

	public void setNbBankHisDataDao(NbBankHisDataDAO nbBankHisDataDao) {
		this.nbBankHisDataDao = nbBankHisDataDao;
	}

	public NbBankHisBalanceDAO getNbBankHisBalanceDao() {
		return nbBankHisBalanceDao;
	}

	public void setNbBankHisBalanceDao(NbBankHisBalanceDAO nbBankHisBalanceDao) {
		this.nbBankHisBalanceDao = nbBankHisBalanceDao;
	}

}
