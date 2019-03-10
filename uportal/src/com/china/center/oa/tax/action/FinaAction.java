/**
 * File Name: FinaAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.action;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.*;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.PreInvoiceApplyBean;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.AttachmentUtils;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.publics.wrap.ForwardBean;
import com.china.center.oa.sail.dao.UnitViewDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.tax.bean.*;
import com.china.center.oa.tax.constanst.CheckConstant;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.dao.*;
import com.china.center.oa.tax.facade.TaxFacade;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.oa.tax.vo.*;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.*;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * FinaAction
 * 
 * @author ZHUZHU
 * @version 2011-2-7
 * @see FinaAction
 * @since 1.0
 */
public class FinaAction extends ParentQueryFinaAction
{
	/**
	 * default constructor
	 */
	public FinaAction()
	{
	}

	/**
	 * queryFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

//		Map<String, String> initMap = initLogTime(request, condtion);
//
//		ActionTools.processJSONDataQueryCondition(QUERYFINANCE, request,
//				condtion, initMap);

		ActionTools.processJSONQueryCondition(QUERYFINANCE, request, condtion);

		notQueryFirstTime(condtion);

		condtion.addCondition("order by FinanceBean.financeDate desc, FinanceBean.logTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFINANCE,
				request, condtion, this.financeDAO,
				new HandleResult<FinanceVO>()
				{
					public void handle(FinanceVO obj)
					{
						obj.getShowInmoney();
						obj.getShowOutmoney();
					}
				});

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * 第一次打开时，不做查询，须输入条件才允许查询
	 * @param condtion
	 */
	private void notQueryFirstTime(ConditionParse condtion)
	{
		String conwhere = condtion.toString();

		if (conwhere.indexOf("1 = 2") == -1)
		{
			// 判断1=1 后面是否有And 条件

			String subwhere = conwhere.substring(conwhere.indexOf("1=1") + 3);

			if (StringTools.isNullOrNone(subwhere))
			{
				condtion.addFlaseCondition();
			}
		}
	}
	/**
	 * queryTempFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryTempFinance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		Map<String, String> initMap = initTempLogTime(request, condtion);

		ActionTools.processJSONDataQueryCondition(QUERYTEMPFINANCE, request,
				condtion, initMap);

		condtion.addIntCondition("FinanceTempBean.status", "<>",
				TaxConstanst.FINANCE_STATUS_HIDDEN);

		condtion.addCondition("order by FinanceTempBean.logTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYTEMPFINANCE,
				request, condtion, this.financeTempDAO,
				new HandleResult<FinanceTempVO>()
				{
					public void handle(FinanceTempVO obj)
					{
						obj.getShowInmoney();
						obj.getShowOutmoney();
					}
				});

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * queryFinanceMonth
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryFinanceMonth(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYFINANCEMONTH, request,
				condtion);

		condtion.addCondition("order by FinanceMonthBean.monthKey desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(
				QUERYFINANCEMONTH, request, condtion, this.financeMonthDAO,
				new HandleResult<FinanceMonthVO>()
				{
					public void handle(FinanceMonthVO obj)
					{
						obj.getShowInmoneyAllTotal();
						obj.getShowInmoneyTotal();
						obj.getShowLastAllTotal();
						obj.getShowLastTotal();
						obj.getShowOutmoneyAllTotal();
						obj.getShowOutmoneyTotal();
						obj.getShowMonthTurnTotal();
					}
				});

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * queryFinanceTurn
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryFinanceTurn(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYFINANCETURN, request,
				condtion);

		condtion.addCondition("order by FinanceTurnBean.monthKey desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFINANCETURN,
				request, condtion, this.financeTurnDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * queryFinanceItem
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryFinanceItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		Map<String, String> initMap = initItemLogTime(request, condtion);

		ActionTools.processJSONDataQueryCondition(QUERYFINANCEITEM, request,
				condtion, initMap);

		condtion.addCondition("order by FinanceItemBean.logTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFINANCEITEM,
				request, condtion, this.financeItemDAO,
				new HandleResult<FinanceItemVO>()
				{
					public void handle(FinanceItemVO obj)
					{
						fillItemVO(obj);
					}
				});

		request.getSession().setAttribute("EXPORT_FINANCEITE_KEY",
				QUERYFINANCEITEM);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * exportFinanceItem
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	/*public ActionForward exportFinanceItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		OutputStream out = null;

		String filenName = "FinanceItem_" + TimeTools.now("MMddHHmmss")
				+ ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		Object key = request.getSession().getAttribute("EXPORT_FINANCEITE_KEY");

		if (null == key)
		{
			return null;
		}

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				key.toString());

		int count = financeItemDAO.countVOByCondition(condtion.toString());

		if (count >= 65535)
		{
			return ActionTools.toError("导出数量大于65535,请重新选择时间段导出", mapping,
					request);
		}

		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("日期,凭证,类型,分类,借/贷,余额,事业部,事业部ID,大区,大区ID,部门,部门ID,关联单据,关联库单,关联收付款,关联采购,凭证意见,摘要,科目,借方金额,贷方金额,产品借,产品贷,部门,职员,单位,产品,仓区,纳税实体,事业部");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			int item = 0;

			while (page.nextPage())
			{
				List<FinanceItemVO> voList = financeItemDAO
						.queryEntityVOsByCondition(condtion, page);

				for (FinanceItemVO financeItemVO : voList)
				{
					item++;

					fillItemVO(financeItemVO);

					FinanceBean finance = financeDAO.find(financeItemVO
							.getPid());

					if (finance == null)
					{
						continue;
					}

					line.reset();

					line.writeColumn("[" + financeItemVO.getFinanceDate() + "]");
					line.writeColumn(financeItemVO.getPid());
					line.writeColumn(ElTools.get("financeType",
							finance.getType()));
					line.writeColumn(ElTools.get("financeCreateType",
							finance.getCreateType()));
					line.writeColumn(financeItemVO.getForwardName());
					line.writeColumn(changeString(financeItemVO
							.getShowLastmoney()));

					// 事业部，大区，部门
					StafferVO sv = this.stafferDAO.findVO(financeItemVO
							.getStafferId());
					if (null != sv)
					{
						if (sv.getIndustryName().length()>=5)
						{
							line.writeColumn(sv.getIndustryName().substring(5));
							line.writeColumn(" "
									+ sv.getIndustryName().substring(0, 5) + " ");
						}
						else
						{
							line.writeColumn("");
							line.writeColumn("");
						}
						if (sv.getIndustryName2().length() >= 8)
						{
							line.writeColumn(sv.getIndustryName2().substring(8));
							line.writeColumn(" "
									+ sv.getIndustryName2().substring(0, 8) + " ");
						}
						else
						{
							line.writeColumn("");
							line.writeColumn("");
						}
						
						if (sv.getIndustryName3().length() >= 11)
						{
							line.writeColumn(sv.getIndustryName3().substring(11));
							line.writeColumn(" "
									+ sv.getIndustryName3().substring(0, 11) + " ");
						}
						else
						{
							line.writeColumn("");
							line.writeColumn("");
						}
					}
					else
					{
						line.writeColumn("");
						line.writeColumn("");
						line.writeColumn("");
						// 事业部，大区，部门编码
						line.writeColumn("");
						line.writeColumn("");
						line.writeColumn("");
					}

					line.writeColumn(finance.getRefId());
					line.writeColumn(finance.getRefOut());
					line.writeColumn(finance.getRefBill());
					line.writeColumn(finance.getRefStock());
					line.writeColumn(StringTools.getExportString(finance
							.getRefChecks()));

					line.writeColumn(StringTools.getExportString(financeItemVO
							.getDescription()));
					line.writeColumn(financeItemVO.getTaxId() + " "
							+ financeItemVO.getTaxName());

					line.writeColumn(changeString(financeItemVO
							.getShowInmoney()));
					line.writeColumn(changeString(financeItemVO
							.getShowOutmoney()));
					line.writeColumn(financeItemVO.getProductAmountIn());
					line.writeColumn(financeItemVO.getProductAmountOut());

					line.writeColumn(financeItemVO.getDepartmentName());
					line.writeColumn(financeItemVO.getStafferName());
					line.writeColumn(financeItemVO.getUnitName());
					line.writeColumn(financeItemVO.getProductName());
					line.writeColumn(financeItemVO.getDepotName());
					line.writeColumn(financeItemVO.getDuty2Name());

					TaxBean tax = taxDAO.find(financeItemVO.getTaxId());

					if (tax.getStaffer() == TaxConstanst.TAX_CHECK_YES
							|| !StringTools.isNullOrNone(financeItemVO
									.getStafferId()))
					{
						StafferBean sb = stafferDAO.find(financeItemVO
								.getStafferId());

						if (sb != null)
						{
							PrincipalshipBean prin = principalshipDAO.find(sb
									.getIndustryId());
							line.writeColumn(prin.getName());
						}
					}

					line.writeLine();
				}
			}

			write.writeLine("导出结束,凭证项:" + item);

			write.close();
		}
		catch (Throwable e)
		{
			_logger.error(e, e);

			return null;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}

			if (write != null)
			{

				try
				{
					write.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return null;
	}*/

	public ActionForward exportFinanceItem(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws ServletException
	{
		OutputStream out = null;

		String filenName = "FinanceItem_" + TimeTools.now("MMddHHmmss")
				+ ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		Object key = request.getSession().getAttribute("EXPORT_FINANCEITE_KEY");

		if (null == key)
		{
			return null;
		}

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				key.toString());

		int count = financeItemDAO.countVOByCondition(condtion.toString());

		if (count >= 65535)
		{
			return ActionTools.toError("导出数量大于65535,请重新选择时间段导出", mapping,
					request);
		}

		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("日期,凭证,类型,分类,借/贷,余额,事业部,事业部ID,大区,大区ID,部门,部门ID,关联单据,关联库单,关联收付款,关联采购,凭证意见,摘要,科目,借方金额,贷方金额,产品借,产品贷,部门,职员,单位,产品,仓区,纳税实体,事业部");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			int item = 0;

			while (page.nextPage())
			{
				List<FinanceItemVO> voList = financeItemDAO
						.queryEntityVOsByCondition(condtion, page);

				for (FinanceItemVO financeItemVO : voList)
				{
					FinanceBean finance = financeDAO.find(financeItemVO
							.getPid());

					if (finance == null)
					{
						continue;
					} else{
						List<FinanceItemVO> vos =this.financeItemDAO.queryEntityVOsByFK(finance.getId());
						for (FinanceItemVO vo: vos){
							item++;

							fillItemVO(vo);

							line.reset();

							line.writeColumn("[" + financeItemVO.getFinanceDate() + "]");
							line.writeColumn(financeItemVO.getPid());
							line.writeColumn(ElTools.get("financeType",
									finance.getType()));
							line.writeColumn(ElTools.get("financeCreateType",
									finance.getCreateType()));
							line.writeColumn(financeItemVO.getForwardName());
							line.writeColumn(changeString(financeItemVO
									.getShowLastmoney()));

							// 事业部，大区，部门
							StafferVO sv = this.stafferDAO.findVO(financeItemVO
									.getStafferId());
							if (null != sv)
							{
								if (sv.getIndustryName().length()>=5)
								{
									line.writeColumn(sv.getIndustryName().substring(5));
									line.writeColumn(" "
											+ sv.getIndustryName().substring(0, 5) + " ");
								}
								else
								{
									line.writeColumn("");
									line.writeColumn("");
								}
								if (sv.getIndustryName2().length() >= 8)
								{
									line.writeColumn(sv.getIndustryName2().substring(8));
									line.writeColumn(" "
											+ sv.getIndustryName2().substring(0, 8) + " ");
								}
								else
								{
									line.writeColumn("");
									line.writeColumn("");
								}

								if (sv.getIndustryName3().length() >= 11)
								{
									line.writeColumn(sv.getIndustryName3().substring(11));
									line.writeColumn(" "
											+ sv.getIndustryName3().substring(0, 11) + " ");
								}
								else
								{
									line.writeColumn("");
									line.writeColumn("");
								}
							}
							else
							{
								line.writeColumn("");
								line.writeColumn("");
								line.writeColumn("");
								// 事业部，大区，部门编码
								line.writeColumn("");
								line.writeColumn("");
								line.writeColumn("");
							}

							line.writeColumn(finance.getRefId());
							line.writeColumn(finance.getRefOut());
							line.writeColumn(finance.getRefBill());
							line.writeColumn(finance.getRefStock());
							line.writeColumn(StringTools.getExportString(finance
									.getRefChecks()));

							line.writeColumn(StringTools.getExportString(financeItemVO
									.getDescription()));
							line.writeColumn(financeItemVO.getTaxId() + " "
									+ financeItemVO.getTaxName());

							line.writeColumn(changeString(financeItemVO
									.getShowInmoney()));
							line.writeColumn(changeString(financeItemVO
									.getShowOutmoney()));
							line.writeColumn(financeItemVO.getProductAmountIn());
							line.writeColumn(financeItemVO.getProductAmountOut());

							line.writeColumn(financeItemVO.getDepartmentName());
							line.writeColumn(financeItemVO.getStafferName());
							line.writeColumn(financeItemVO.getUnitName());
							line.writeColumn(financeItemVO.getProductName());
							line.writeColumn(financeItemVO.getDepotName());
							line.writeColumn(financeItemVO.getDuty2Name());

							TaxBean tax = taxDAO.find(financeItemVO.getTaxId());

							if (tax.getStaffer() == TaxConstanst.TAX_CHECK_YES
									|| !StringTools.isNullOrNone(financeItemVO
									.getStafferId()))
							{
								StafferBean sb = stafferDAO.find(financeItemVO
										.getStafferId());

								if (sb != null)
								{
									PrincipalshipBean prin = principalshipDAO.find(sb
											.getIndustryId());
									line.writeColumn(prin.getName());
								}
							}

							line.writeLine();
						}
					}
				}
			}

			write.writeLine("导出结束,凭证项:" + item);

			write.close();
		}
		catch (Throwable e)
		{
			_logger.error(e, e);

			return null;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}

			if (write != null)
			{

				try
				{
					write.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return null;
	}
	
	public ActionForward exportFinanceMonth(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		OutputStream out = null;

		String filenName = "FinanceMonth_" + TimeTools.now("MMddHHmmss")
				+ ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYFINANCEMONTH);

		int count = financeMonthDAO.countVOByCondition(condtion.toString());

		if (count >= 65535)
		{
			return ActionTools.toError("导出数量大于65535,请重新选择时间段导出", mapping,
					request);
		}

		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("月份,科目,本月借,本月贷,本月结余,累计借,累计贷,期末余额,结转金额");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			int item = 0;

			while (page.nextPage())
			{
				List<FinanceMonthVO> voList = financeMonthDAO
						.queryEntityVOsByCondition(condtion, page);

				for (FinanceMonthVO vo : voList)
				{
					item++;

					vo.getShowInmoneyAllTotal();
					vo.getShowInmoneyTotal();
					vo.getShowLastAllTotal();
					vo.getShowLastTotal();
					vo.getShowOutmoneyAllTotal();
					vo.getShowOutmoneyTotal();
					vo.getShowMonthTurnTotal();

					line.reset();

					line.writeColumn(vo.getMonthKey());
					line.writeColumn(vo.getTaxName());
					line.writeColumn(vo.getShowInmoneyTotal());
					line.writeColumn(vo.getShowOutmoneyTotal());
					line.writeColumn(vo.getShowLastTotal());
					line.writeColumn(vo.getShowInmoneyAllTotal());
					line.writeColumn(vo.getShowOutmoneyAllTotal());
					line.writeColumn(vo.getShowLastAllTotal());
					line.writeColumn(vo.getShowMonthTurnTotal());

					line.writeLine();
				}
			}

			write.writeLine("导出结束,凭证项:" + item);

			write.close();
		}
		catch (Throwable e)
		{
			_logger.error(e, e);

			return null;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}

			if (write != null)
			{

				try
				{
					write.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return null;
	}

	/**
	 * exportFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		OutputStream out = null;

		String filenName = "Finance_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				QUERYFINANCE);

		int count = financeDAO.countVOByCondition(condtion.toString());

		if (count > 150000)
		{
			return ActionTools.toError("导出数量大于150000,请重新选择时间段导出", mapping,
					request);
		}

		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("日期,凭证,类型,分类,借/贷,余额,事业部,大区,部门,事业部ID,大区ID,部门ID,关联单据,关联库单,关联收付款,关联采购,凭证意见,描述,摘要,科目,借方金额,贷方金额,产品借,产品贷,部门,职员,单位,产品,仓区,纳税实体");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			int parent = 0;
			int item = 0;

			while (page.nextPage())
			{
				List<FinanceVO> voFList = financeDAO.queryEntityVOsByCondition(
						condtion, page);

				for (FinanceVO financeVO : voFList)
				{
					List<FinanceItemVO> voList = financeItemDAO
							.queryEntityVOsByFK(financeVO.getId());

					parent++;

					for (FinanceItemVO financeItemVO : voList)
					{
						item++;

						fillItemVO(financeItemVO);

						line.reset();

						line.writeColumn("[" + financeItemVO.getFinanceDate()
								+ "]");
						line.writeColumn(financeItemVO.getPid());
						line.writeColumn(ElTools.get("financeType",
								financeVO.getType()));
						line.writeColumn(ElTools.get("financeCreateType",
								financeVO.getCreateType()));
						line.writeColumn(financeItemVO.getForwardName());
						line.writeColumn(changeString(financeItemVO
								.getShowLastmoney()));
						
						if (!StringTools.isNullOrNone(financeItemVO.getDepartmentId())) {
							// 由部门找上级 事业部， 再往上级 大区
							PrincipalshipBean prin = principalshipDAO.find(financeItemVO.getDepartmentId());
							
							if (null == prin) {
								line.writeColumn("");
								line.writeColumn("");
								line.writeColumn("");
								// 事业部，大区，部门编码
								line.writeColumn("");
								line.writeColumn("");
								line.writeColumn("");
							} else {
								PrincipalshipBean syb0 = principalshipDAO.find(prin.getParentId());
								
								if (null == syb0) {
									line.writeColumn("");
									line.writeColumn("");
									line.writeColumn(prin.getName());
									// 事业部，大区，部门编码
									line.writeColumn("");
									line.writeColumn("");
									line.writeColumn("");
								} else {
									PrincipalshipBean syb = principalshipDAO.find(syb0.getParentId());
									
									if (null == syb) {
										line.writeColumn("");
										line.writeColumn("");
										line.writeColumn(prin.getName());
										// 事业部，大区，部门编码
										line.writeColumn("");
										line.writeColumn("");
										line.writeColumn("");
									} else {
										PrincipalshipBean area = principalshipDAO.find(syb.getParentId());
										
										if (null == area) {
											line.writeColumn(syb.getName());
											line.writeColumn("");
											line.writeColumn(prin.getName());
											// 事业部，大区，部门编码
											line.writeColumn("");
											line.writeColumn("");
											line.writeColumn("");
										} else {
											line.writeColumn(syb.getName());
											line.writeColumn(area.getName());
											line.writeColumn(prin.getName());
											// 事业部，大区，部门编码
											line.writeColumn("");
											line.writeColumn("");
											line.writeColumn("");
										}
									}
								}
							}
						} else {
							if (!StringTools.isNullOrNone(financeItemVO.getStafferId())) {
								// 事业部，大区，部门
								StafferVO sv = this.stafferDAO.findVO(financeItemVO.getStafferId());
								if (null != sv)
								{
									line.writeColumn(sv.getIndustryName());
									line.writeColumn(sv.getIndustryName2());
									line.writeColumn(sv.getIndustryName3());
									// 事业部，大区，部门编码
									line.writeColumn(" "
											+ sv.getIndustryName());
									line.writeColumn(" "
											+ sv.getIndustryName2());
									line.writeColumn(" "
											+ sv.getIndustryName3());
								}
								else
								{
									line.writeColumn("");
									line.writeColumn("");
									line.writeColumn("");
									// 事业部，大区，部门编码
									line.writeColumn("");
									line.writeColumn("");
									line.writeColumn("");
								}
							} else {
								line.writeColumn("");
								line.writeColumn("");
								line.writeColumn("");
								// 事业部，大区，部门编码
								line.writeColumn("");
								line.writeColumn("");
								line.writeColumn("");
							}
						}
						
						line.writeColumn(financeVO.getRefId());
						line.writeColumn(financeVO.getRefOut());
						line.writeColumn(financeVO.getRefBill());
						line.writeColumn(financeVO.getRefStock());

						line.writeColumn(financeVO.getRefChecks());
						line.writeColumn(financeVO.getDescription());
						line.writeColumn(financeItemVO.getDescription());
						line.writeColumn(financeItemVO.getTaxId() + " "
								+ financeItemVO.getTaxName());

						line.writeColumn(changeString(financeItemVO
								.getShowInmoney()));
						line.writeColumn(changeString(financeItemVO
								.getShowOutmoney()));
						line.writeColumn(financeItemVO.getProductAmountIn());
						line.writeColumn(financeItemVO.getProductAmountOut());

						line.writeColumn(financeItemVO.getDepartmentName());
						line.writeColumn(financeItemVO.getStafferName());
						line.writeColumn(financeItemVO.getUnitName());
						line.writeColumn(financeItemVO.getProductName());
						line.writeColumn(financeItemVO.getDepotName());
						line.writeColumn(financeItemVO.getDuty2Name());

						line.writeLine();
					}
				}
			}

			write.writeLine("导出结束,凭证:" + parent + ",凭证项:" + item);

			write.close();
		}
		catch (Throwable e)
		{
			_logger.error(e, e);

			return null;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}

			if (write != null)
			{

				try
				{
					write.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return null;
	}

	private String changeString(String str)
	{
		return str.replaceAll(",", "");
	}

	/**
	 * queryCheck
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryCheckView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		Map<String, String> initMap = initCheck(request, condtion);

		ActionTools.processJSONDataQueryCondition(QUERYCHECKVIEW, request,
				condtion, initMap);

		condtion.addCondition("order by CheckViewBean.logTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCHECKVIEW,
				request, condtion, this.checkViewDAO,
				new HandleResult<CheckViewVO>()
				{
					public void handle(CheckViewVO obj)
					{
						StafferBean sb = stafferDAO.find(obj.getStafferId());

						if (sb != null)
						{
							obj.setStafferId(sb.getName());
						}
					}
				});

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * initLogTime
	 * 
	 * @param request
	 * @param condtion
	 * @return
	 */
	private Map<String, String> initLogTime(HttpServletRequest request,
			ConditionParse condtion)
	{
		Map<String, String> changeMap = new HashMap<String, String>();

		String alogTime = request.getParameter("afinanceDate");

		String blogTime = request.getParameter("bfinanceDate");

//		if (StringTools.isNullOrNone(alogTime)
//				&& StringTools.isNullOrNone(blogTime))
//		{
//			changeMap.put("afinanceDate", TimeTools.now_short(-10));
//
//			changeMap.put("bfinanceDate", TimeTools.now_short(1));
//
//			condtion.addCondition("FinanceBean.financeDate", ">=",
//					TimeTools.now_short(-10));
//
//			condtion.addCondition("FinanceBean.financeDate", "<=",
//					TimeTools.now_short(1));
//		}

		return changeMap;
	}

	private Map<String, String> initTempLogTime(HttpServletRequest request,
			ConditionParse condtion)
	{
		Map<String, String> changeMap = new HashMap<String, String>();

		String alogTime = request.getParameter("afinanceDate");

		String blogTime = request.getParameter("bfinanceDate");

		if (StringTools.isNullOrNone(alogTime)
				&& StringTools.isNullOrNone(blogTime))
		{
			changeMap.put("afinanceDate", TimeTools.now_short(-10));

			changeMap.put("bfinanceDate", TimeTools.now_short(1));

			condtion.addCondition("FinanceTempBean.financeDate", ">=",
					TimeTools.now_short(-10));

			condtion.addCondition("FinanceTempBean.financeDate", "<=",
					TimeTools.now_short(1));
		}

		return changeMap;
	}

	private Map<String, String> initItemLogTime(HttpServletRequest request,
			ConditionParse condtion)
	{
		Map<String, String> changeMap = new HashMap<String, String>();

		String alogTime = request.getParameter("afinanceDate");

		String blogTime = request.getParameter("bfinanceDate");

		if (StringTools.isNullOrNone(alogTime)
				&& StringTools.isNullOrNone(blogTime))
		{
			changeMap.put("afinanceDate", TimeTools.now_short(-1));

			changeMap.put("bfinanceDate", TimeTools.now_short(1));

			condtion.addCondition("FinanceItemBean.financeDate", ">=",
					TimeTools.now_short(-1));

			condtion.addCondition("FinanceItemBean.financeDate", "<=",
					TimeTools.now_short(1));
		}

		return changeMap;
	}

	private Map<String, String> initCheck(HttpServletRequest request,
			ConditionParse condtion)
	{
		Map<String, String> changeMap = new HashMap<String, String>();

		String checkStatus = request.getParameter("checkStatus");

		if (StringTools.isNullOrNone(checkStatus))
		{
			changeMap.put("checkStatus",
					String.valueOf(PublicConstant.CHECK_STATUS_INIT));

			condtion.addIntCondition("CheckViewBean.checkStatus", "=",
					PublicConstant.CHECK_STATUS_INIT);
		}

		return changeMap;
	}

	/**
	 * addFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	/*public ActionForward addFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		FinanceBean bean = new FinanceBean();

		String tempFlag = request.getParameter("tempFlag");

		try
		{
			BeanUtil.getBean(bean, request);

			setFinanceBean(bean, request);

			User user = Helper.getUser(request);

			// 是否增加
			if (!"1".equals(tempFlag))
			{
				taxFacade.addFinanceBean(user.getId(), bean);
			}
			else
			{
				financeManager.addTempFinanceBean(user, bean);
			}

			request.setAttribute(KeyConstant.MESSAGE, "成功操作:" + bean.getName());
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作失败:" + e.getMessage());
		}

		return preForAddFinance(mapping, form, request, response);
	}*/

    public ActionForward addFinance(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        FinanceBean bean = new FinanceBean();

        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 10L);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过10M");

            return mapping.findForward("error");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

            return mapping.findForward("error");
        }

        String tempFlag = request.getParameter("tempFlag");

        try
        {
            BeanUtil.getBean(bean, rds.getParmterMap());

            ActionForward afor = parserAttachment(mapping, request, rds, bean);

            if (afor != null) {
                return afor;
            }
            rds.close();

            setFinanceBean(bean, rds);

            User user = Helper.getUser(request);

            // 是否增加
            if (!"1".equals(tempFlag))
            {
                taxFacade.addFinanceBean(user.getId(), bean);
            }
            else
            {
                financeManager.addTempFinanceBean(user, bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功操作:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE,
                    "操作失败:" + e.getMessage());
        }

        return preForAddFinance(mapping, form, request, response);
    }

    private ActionForward parserAttachment(ActionMapping mapping, HttpServletRequest request,
                                           RequestDataStream rds, FinanceBean financeBean)
    {
        List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

        financeBean.setAttachmentList(attachmentList);

        String addOrUpdate = rds.getParameter("addOrUpdate");

        // 更新新加入之前
        if ("1".equals(addOrUpdate))
        {
            String attacmentIds = rds.getParameter("attacmentIds");

            String[] split = attacmentIds.split(";");

            for (String each : split)
            {
                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                AttachmentBean att = attachmentDAO.find(each);

                if (att != null)
                {
                    attachmentList.add(att);
                }
            }
        }

        // parser attachment
        if ( !rds.haveStream())
        {
            _logger.info("****no attachment****");
            return null;
        }

        Map<String, InputStream> streamMap = rds.getStreamMap();

        for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
        {
            AttachmentBean bean = new AttachmentBean();

            FileOutputStream out = null;

            UtilStream ustream = null;

            try
            {
                String savePath = AttachmentUtils.mkdir(this.getAttachmentPath());

                String fileAlais = SequenceTools.getSequence();

                String fileName = FileTools.getFileName(rds.getFileName(entry.getKey()));

                String rabsPath = '/' + savePath + '/' + fileAlais + "."
                        + FileTools.getFilePostfix(fileName).toLowerCase();

                String filePath = this.getAttachmentPath() + '/' + rabsPath;

                bean.setName(fileName);

                bean.setPath(rabsPath);

                bean.setLogTime(TimeTools.now());

                out = new FileOutputStream(filePath);

                ustream = new UtilStream(entry.getValue(), out);

                ustream.copyStream();

                attachmentList.add(bean);
            }
            catch (IOException e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

                return mapping.findForward("querySelfTravelApply");
            }
            finally
            {
                if (ustream != null)
                {
                    try
                    {
                        ustream.close();
                    }
                    catch (IOException e)
                    {
                        _logger.error(e, e);
                    }
                }
            }
        }
        return null;
    }

    public String getAttachmentPath()
    {
        return ConfigLoader.getProperty("financeAttachmentPath");
    }

	public ActionForward downAttachmentFile(ActionMapping mapping, ActionForm form,
											HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String path = getAttachmentPath();

		String id = request.getParameter("id");

		AttachmentBean bean = attachmentDAO.find(id);

		if (bean == null)
		{
			return ActionTools.toError(mapping, request);
		}

		path += bean.getPath();

		File file = new File(path);

		OutputStream out = response.getOutputStream();

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ StringTools.getStringBySet(bean.getName(),
				"GBK", "ISO8859-1"));

		UtilStream us = new UtilStream(new FileInputStream(file), out);

		us.copyAndCloseStream();

		return null;
	}

	/**
	 * 增加结转
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addFinanceTurn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		FinanceTurnBean bean = new FinanceTurnBean();

		try
		{
			BeanUtil.getBean(bean, request);

			User user = Helper.getUser(request);

			financeManager.addFinanceTurnBean(user, bean);

			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作失败:" + e.getMessage());
		}

		CommonTools.removeParamers(request);

		return mapping.findForward(QUERYFINANCETURN);
	}

	/**
	 * deleteFinanceTurn
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deleteFinanceTurn(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		try
		{
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			financeManager.deleteFinanceTurnBean(user, id);

			ajax.setSuccess("成功操作");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * updateFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward updateFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		FinanceBean bean = new FinanceBean();

		String tempFlag = request.getParameter("tempFlag");

		try
		{
			BeanUtil.getBean(bean, request);

			setFinanceBean(bean, request);

			User user = Helper.getUser(request);

			if (!"1".equals(tempFlag))
			{
				taxFacade.updateFinanceBean(user.getId(), bean);
			}
			else
			{
				financeManager.updateTempFinanceBean(user, bean);
			}

			request.setAttribute(KeyConstant.MESSAGE, "成功操作:" + bean.getName());
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作失败:" + e.getMessage());
		}

		CommonTools.removeParamers(request);

		ForwardBean forward = (ForwardBean) request.getSession().getAttribute(
				"g_Forward_tax");

		if (forward == null)
		{
			if (!"1".equals(tempFlag))
			{
				return mapping.findForward(QUERYFINANCE);
			}
			else
			{
				return mapping.findForward(QUERYTEMPFINANCE);
			}
		}
		else
		{
			try
			{
				// 重定向
				response.sendRedirect(forward.getUrl());
			}
			catch (IOException e)
			{
				_logger.error(e, e);
			}

			return null;
		}
	}

	/**
	 * deleteFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deleteFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		try
		{
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			taxFacade.deleteFinanceBean(user.getId(), id);

			ajax.setSuccess("成功操作");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * deleteTempFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward deleteTempFinance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		try
		{
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			financeManager.deleteTempFinanceBean(user, id);

			ajax.setSuccess("成功操作");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * moveTempFinanceBeanToRelease
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward moveTempFinanceBeanToRelease(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		try
		{
			String id = request.getParameter("id");

			User user = Helper.getUser(request);

			financeManager.moveTempFinanceBeanToRelease(user, id);

			ajax.setSuccess("成功操作");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * copyFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward copyFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		String id = request.getParameter("id");

		String financeDate = request.getParameter("financeDate");

		CommonTools.removeParamers(request);

		String newId = "";

		try
		{
			User user = Helper.getUser(request);

			newId = financeManager.copyFinanceBean(user, id, financeDate);

			request.setAttribute("id", newId);

			request.setAttribute(KeyConstant.MESSAGE, "成功复制:" + id);
		}
		catch (MYException e)
		{
			return ActionTools.toError(e.toString(), mapping, request);
		}

		return findFinance(mapping, form, request, response);
	}

	/**
	 * 总部核对
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward checks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		try
		{
			String idStr = request.getParameter("id");

			String[] ids = idStr.split("~");

			for (int i = 0; i < ids.length; i++)
			{
				String id = ids[i];

				int type = MathTools.parseInt(request.getParameter("type"));

				String reason = request.getParameter("reason");

				User user = Helper.getUser(request);

				if (type != 6 && type != 99)
				{
					taxFacade.checks(user.getId(), id,
							"[" + user.getStafferName() + "]" + reason);
				}
				// 凭证核对
				else if (type == 99)
				{
					taxFacade.updateFinanceCheck(user.getId(), id,
							"[" + user.getStafferName() + "]" + reason);
				}
				else
				{
					outManager.check(id, user, reason);

					financeManager.deleteChecks(user, id);
				}
			}

			ajax.setSuccess("成功操作");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("操作失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * checks2
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward checks2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		int type = MathTools.parseInt(request.getParameter("type"));

		try
		{
			String id = request.getParameter("id");

			String reason = request.getParameter("reason");

			User user = Helper.getUser(request);

			if (type != 6)
			{
				taxFacade.checks2(user.getId(), id, type,
						"[" + user.getStafferName() + "]" + reason);
			}
			else
			{
				outManager.check(id, user, reason);

				financeManager.deleteChecks(user, id);
			}

			request.setAttribute(KeyConstant.MESSAGE, "成功操作");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"操作失败:" + e.getMessage());
		}

		String forward = "error";

		if (type == CheckConstant.CHECK_TYPE_COMPOSE)
		{
			forward = "queryCompose";
		}
		else if (type == CheckConstant.CHECK_TYPE_CHANGE)
		{
			forward = "queryPriceChange";
		}
		else if (type == CheckConstant.CHECK_TYPE_INBILL)
		{
			forward = "queryInBill";
		}
		else if (type == CheckConstant.CHECK_TYPE_OUTBILL)
		{
			forward = "queryOutBill";
		}
		else if (type == CheckConstant.CHECK_TYPE_STOCK)
		{
			return mapping.findForward(QUERYCHECKVIEW);
		}
		else if (type == CheckConstant.CHECK_TYPE_BUY)
		{
			return mapping.findForward(QUERYCHECKVIEW);
		}
		else if (type == CheckConstant.CHECK_TYPE_CUSTOMER)
		{
			return mapping.findForward(QUERYCHECKVIEW);
		}

		return mapping.findForward(forward);
	}

	/**
	 * synCheckView
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward synCheckView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		checkViewDAO.syn();

		unitViewDAO.syn();

		ajax.setSuccess("成功操作");

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * findFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		String id = RequestTools.getValueFromRequest(request, "id");

		String update = request.getParameter("update");

		String tempFlag = request.getParameter("tempFlag");

		request.setAttribute("tempFlag", tempFlag);

		CommonTools.saveParamers(request);

		if (!"1".equals(tempFlag))
		{
			FinanceVO bean = financeDAO.findVO(id);

			if (bean == null)
			{
				return ActionTools.toError("数据异常,请重新操作", mapping, request);
			}

			List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(id);

			bean.setAttachmentList(attachmentList);

			List<FinanceItemVO> voList = financeItemDAO.queryEntityVOsByFK(id);

			for (FinanceItemVO item : voList)
			{
				fillItemVO(item);
			}

			bean.setItemVOList(voList);

			request.setAttribute("bean", bean);

			if ("1".equals(update))
			{
				if (bean.getStatus() == TaxConstanst.FINANCE_STATUS_CHECK)
				{
					return ActionTools.toError("已经被核对(锁定)不能修改,请重新操作", mapping,
							request);
				}

				preInner(request);

				// 定制
				cacheBack(request);

				return mapping.findForward("updateFinance");
			}
		}
		else
		{
			FinanceTempVO bean = financeTempDAO.findVO(id);

			if (bean == null)
			{
				return ActionTools.toError("数据异常,请重新操作", mapping, request);
			}

			List<FinanceItemTempVO> voList = financeItemTempDAO
					.queryEntityVOsByFK(id);

			List<FinanceItemVO> itemList = new ArrayList<FinanceItemVO>();

			for (FinanceItemTempVO financeItemTempBean : voList)
			{
				FinanceItemVO item = new FinanceItemVO();

				BeanUtil.copyProperties(item, financeItemTempBean);

				itemList.add(item);
			}

			for (FinanceItemVO item : itemList)
			{
				fillItemVO(item);
			}

			bean.setItemVOList(itemList);

			request.setAttribute("bean", bean);

			if ("1".equals(update))
			{
				preInner(request);

				cacheBack(request);

				return mapping.findForward("updateFinance");
			}
		}

		return mapping.findForward("detailFinance");
	}

	/**
	 * cacheBack
	 * 
	 * @param request
	 */
	private void cacheBack(HttpServletRequest request)
	{
		// 进入修改
		String backType = request.getParameter("backType");
		String backId = request.getParameter("backId");

		request.getSession().removeAttribute("g_Forward_tax");

		if (!StringTools.isNullOrNone(backType)
				&& !StringTools.isNullOrNone(backId))
		{
			ForwardBean forward = new ForwardBean();

			if ("0".equals(backType))
			{
				forward.setUrl("../sail/out.do?method=findOut&radioIndex=0&goback=4&fow=99&outId="
						+ backId);

				forward.setType(backType);

				forward.setId(backId);
			}

			// 付款单
			if ("1".equals(backType))
			{
				forward.setUrl("../finance/bill.do?method=findOutBill&goback=4&id="
						+ backId);

				forward.setType(backType);

				forward.setId(backId);
			}

			// 收款单
			if ("2".equals(backType))
			{
				forward.setUrl("../finance/bill.do?method=findInBill&id="
						+ backId + "&goback=4");

				forward.setType(backType);

				forward.setId(backId);
			}

			request.getSession().setAttribute("g_Forward_tax", forward);
		}
	}

	/**
	 * setFinanceBean
	 * 
	 * @param bean
	 * @param request
	 * @throws MYException
	 */
	private void setFinanceBean(FinanceBean bean, HttpServletRequest request)
			throws MYException
	{
		String[] departmentIds = request.getParameterValues("departmentId2");
		String[] idescriptions = request.getParameterValues("idescription");
		String[] taxIds = request.getParameterValues("taxId2");
		String[] stafferId2s = request.getParameterValues("stafferId2");
		String[] unitId2s = request.getParameterValues("unitId2");
		String[] productId2s = request.getParameterValues("productId2");
		String[] depotIds = request.getParameterValues("depotId");
		String[] duty2Ids = request.getParameterValues("duty2Id");
		String[] inmoneys = request.getParameterValues("inmoney");
		String[] inproducts = request.getParameterValues("inproduct");

		String[] outmoneys = request.getParameterValues("outmoney");
		String[] outproducts = request.getParameterValues("outproduct");

		List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

		long inTotal = 0;

		long outTotal = 0;

		String pareId = SequenceTools.getSequence();

		for (int i = 0; i < taxIds.length; i++)
		{
			if (StringTools.isNullOrNone(taxIds[i]))
			{
				continue;
			}

			FinanceItemBean item = new FinanceItemBean();

			item.setDescription(idescriptions[i]);

			item.setDutyId(bean.getDutyId());

			item.setTaxId(taxIds[i]);

			TaxBean tax = taxDAO.find(item.getTaxId());

			if (tax == null)
			{
				throw new MYException("科目不存在");
			}

			item.setForward(tax.getForward());

			if (tax.getDepartment() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(departmentIds[i]))
				{
					throw new MYException("科目[%s]部门不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setDepartmentId(departmentIds[i]);
			}
			else
			{
				item.setDepartmentId("");
			}

			if (tax.getStaffer() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(stafferId2s[i]))
				{
					throw new MYException("科目[%s]职员不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setStafferId(stafferId2s[i]);
			}
			else
			{
				item.setStafferId("");
			}

			if (tax.getUnit() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(unitId2s[i]))
				{
					throw new MYException("科目[%s]单位不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setUnitId(unitId2s[i]);

				UnitBean unit = unitDAO.find(item.getUnitId());

				if (unit == null)
				{
					throw new MYException("单位不存在,请确认操作");
				}

				item.setUnitType(unit.getType());
			}
			else
			{
				item.setUnitId("");
			}

			if (tax.getProduct() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(productId2s[i]))
				{
					throw new MYException("科目[%s]产品不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setProductId(productId2s[i]);

				ProductBean product = productDAO.find(item.getProductId());

				if (product == null)
				{
					throw new MYException("产品不存在,请确认操作");
				}
			}
			else
			{
				item.setProductId("");
			}

			if (tax.getDepot() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(depotIds[i]))
				{
					throw new MYException("科目[%s]仓库不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setDepotId(depotIds[i]);

				DepotBean depot = depotDAO.find(item.getDepotId());

				if (depot == null)
				{
					throw new MYException("仓库不存在,请确认操作");
				}
			}
			else
			{
				item.setDepotId("");
			}

			if (tax.getDuty() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(duty2Ids[i]))
				{
					throw new MYException("科目[%s]纳税实体不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setDuty2Id(duty2Ids[i]);

				DutyBean duty2 = dutyDAO.find(item.getDuty2Id());

				if (duty2 == null)
				{
					throw new MYException("纳税实体不存在,请确认操作");
				}
			}
			else
			{
				item.setDuty2Id("");
			}

			item.setName(idescriptions[i]);

			item.setType(bean.getType());

			item.setPareId(pareId);

			item.setInmoney(FinanceHelper.doubleToLong(inmoneys[i]));

			item.setOutmoney(FinanceHelper.doubleToLong(outmoneys[i]));

			item.setProductAmountIn(MathTools.parseInt(inproducts[i]));

			item.setProductAmountOut(MathTools.parseInt(outproducts[i]));

			inTotal += item.getInmoney();

			outTotal += item.getOutmoney();

			if (inTotal == outTotal && outTotal != 0)
			{
				inTotal = 0;

				outTotal = 0;

				pareId = SequenceTools.getSequence();
			}

			itemList.add(item);
		}

		if (inTotal != outTotal)
		{
			throw new MYException("借贷不相等,请重新操作");
		}

		bean.setItemList(itemList);
	}
	/**
	 * setFinanceBean
	 *
	 * @param bean
	 * @param rds
	 * @throws MYException
	 */
	private void setFinanceBean(FinanceBean bean, RequestDataStream rds)
			throws MYException
	{
		List<String> departmentIds = rds.getParameters("departmentId2");
		List<String> idescriptions = rds.getParameters("idescription");
		List<String> taxIds = rds.getParameters("taxId2");
		List<String> stafferId2s = rds.getParameters("stafferId2");
		List<String> unitId2s = rds.getParameters("unitId2");
		List<String> productId2s = rds.getParameters("productId2");
		List<String> depotIds = rds.getParameters("depotId");
		List<String> duty2Ids = rds.getParameters("duty2Id");
		List<String> inmoneys = rds.getParameters("inmoney");
		List<String> inproducts = rds.getParameters("inproduct");
		List<String> outmoneys = rds.getParameters("outmoney");
		List<String> outproducts = rds.getParameters("outproduct");

		List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

		long inTotal = 0;

		long outTotal = 0;

		String pareId = SequenceTools.getSequence();

		for (int i = 0; i < taxIds.size(); i++)
		{
			if (StringTools.isNullOrNone(taxIds.get(i)))
			{
				continue;
			}

			FinanceItemBean item = new FinanceItemBean();

			item.setDescription(idescriptions.get(i));

			item.setDutyId(bean.getDutyId());

			item.setTaxId(taxIds.get(i));

			TaxBean tax = taxDAO.find(item.getTaxId());

			if (tax == null)
			{
				throw new MYException("科目不存在");
			}

			item.setForward(tax.getForward());

			if (tax.getDepartment() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(departmentIds.get(i)))
				{
					throw new MYException("科目[%s]部门不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setDepartmentId(departmentIds.get(i));
			}
			else
			{
				item.setDepartmentId("");
			}

			if (tax.getStaffer() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(stafferId2s.get(i)))
				{
					throw new MYException("科目[%s]职员不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setStafferId(stafferId2s.get(i));
			}
			else
			{
				item.setStafferId("");
			}

			if (tax.getUnit() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(unitId2s.get(i)))
				{
					throw new MYException("科目[%s]单位不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setUnitId(unitId2s.get(i));

				UnitBean unit = unitDAO.find(item.getUnitId());

				if (unit == null)
				{
					throw new MYException("单位不存在,请确认操作");
				}

				item.setUnitType(unit.getType());
			}
			else
			{
				item.setUnitId("");
			}

			if (tax.getProduct() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(productId2s.get(i)))
				{
					throw new MYException("科目[%s]产品不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setProductId(productId2s.get(i));

				ProductBean product = productDAO.find(item.getProductId());

				if (product == null)
				{
					throw new MYException("产品不存在,请确认操作");
				}
			}
			else
			{
				item.setProductId("");
			}

			if (tax.getDepot() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(depotIds.get(i)))
				{
					throw new MYException("科目[%s]仓库不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setDepotId(depotIds.get(i));

				DepotBean depot = depotDAO.find(item.getDepotId());

				if (depot == null)
				{
					throw new MYException("仓库不存在,请确认操作");
				}
			}
			else
			{
				item.setDepotId("");
			}

			if (tax.getDuty() == TaxConstanst.TAX_CHECK_YES)
			{
				if (StringTools.isNullOrNone(duty2Ids.get(i)))
				{
					throw new MYException("科目[%s]纳税实体不能为空,请重新操作", tax.getCode()
							+ tax.getName());
				}

				item.setDuty2Id(duty2Ids.get(i));

				DutyBean duty2 = dutyDAO.find(item.getDuty2Id());

				if (duty2 == null)
				{
					throw new MYException("纳税实体不存在,请确认操作");
				}
			}
			else
			{
				item.setDuty2Id("");
			}

			item.setName(idescriptions.get(i));

			item.setType(bean.getType());

			item.setPareId(pareId);

			item.setInmoney(FinanceHelper.doubleToLong(inmoneys.get(i)));

			item.setOutmoney(FinanceHelper.doubleToLong(outmoneys.get(i)));

			item.setProductAmountIn(MathTools.parseInt(inproducts.get(i)));

			item.setProductAmountOut(MathTools.parseInt(outproducts.get(i)));

			inTotal += item.getInmoney();

			outTotal += item.getOutmoney();

			if (inTotal == outTotal && outTotal != 0)
			{
				inTotal = 0;

				outTotal = 0;

				pareId = SequenceTools.getSequence();
			}

			itemList.add(item);
		}

		if (inTotal != outTotal)
		{
			throw new MYException("借贷不相等,请重新操作");
		}

		bean.setItemList(itemList);
	}
	/**
	 * preForAddFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward preForQueryTaxFinance1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		List<DepotBean> listEntityBeans = depotDAO.listEntityBeans();

		request.getSession().setAttribute("g_tax_depotList", listEntityBeans);

		return mapping.findForward("queryTaxFinance1");
	}

	/**
	 * preForAddFinance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward preForAddFinance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		preInner(request);

		// 是否是临时
		String tempFlag = request.getParameter("tempFlag");

		request.setAttribute("tempFlag", tempFlag);

		CommonTools.removeParamers(request);

		return mapping.findForward("addFinance");
	}

	/**
	 * 凭证结转做准备(月结准备)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward preForAddFinanceTurn(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
	{
		// 这里由于有初始化数据必然存在
		FinanceTurnVO findLastVO = financeTurnDAO.findLastVO();

		if (findLastVO == null)
		{
			return ActionTools.toError("没有默认的结转,请联系管理员设置", mapping, request);
		}

		String nextKey = TimeTools.getStringByOrgAndDaysAndFormat(
				findLastVO.getMonthKey(), 32, "yyyyMM");

		request.setAttribute("nextKey", nextKey);

		String changeFormat = TimeTools.changeFormat(nextKey, "yyyyMM",
				"yyyy-MM");

		String monthEnd = TimeTools.getMonthEnd(changeFormat + "-01");

		if (TimeTools.now_short().compareTo(monthEnd) < 0)
		{
			return ActionTools
					.toError("结转只能在月末或者下月发生,不能提前结转", mapping, request);
		}

		// 查询是否都已经核对
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("financeDate", ">=", changeFormat + "-01");
		con.addCondition("financeDate", "<=", changeFormat + "-31");

		con.addIntCondition("createType", "=", TaxConstanst.FINANCE_CREATETYPE_HAND);
		con.addIntCondition("updateFlag", "=", TaxConstanst.FINANCE_UPDATEFLAG_YES);
		con.addIntCondition("status", "=", TaxConstanst.FINANCE_STATUS_NOCHECK);

		int count = financeDAO.countByCondition(con.toString());

//		int count = 0;
		
		if (count > 0)
		{
			return ActionTools.toError("当前[" + changeFormat + "]下存在:" + count
					+ "个类型是手工凭证或是更改的凭证且没有核对,不能月结", mapping, request);
		}

		List<TaxBean> taxList = taxDAO.listEntityBeansByOrder("order by id");
        _logger.info("***taxList***"+taxList.size());

		List<FinanceMonthVO> monthList = new ArrayList<FinanceMonthVO>();

		for (TaxBean taxBean : taxList)
		{
            long begin = System.currentTimeMillis();
			ConditionParse condition = new ConditionParse();

			condition.addWhereStr();

			condition.addCondition("financeDate", ">=", changeFormat + "-01");
			condition.addCondition("financeDate", "<=", changeFormat + "-31");

			condition.addCondition("taxId" + taxBean.getLevel(), "=",
					taxBean.getId());

			long inMonetTotal = financeItemDAO.sumInByCondition(condition);
            long end2 = System.currentTimeMillis();
            _logger.info(taxBean.getId()+"***time inMonetTotal***"+(end2-begin));

			long outMonetTotal = financeItemDAO.sumOutByCondition(condition);
            long end3 = System.currentTimeMillis();
            _logger.info(taxBean.getId()+"***time outMonetTotal***"+(end3-end2));

			FinanceMonthVO fmb = new FinanceMonthVO();

			fmb.setMonthKey(nextKey);

			FinanceHelper.copyTax(taxBean, fmb);

			fmb.setTaxName(taxBean.getName());

			fmb.setForwardName(ElTools.get("taxForward", taxBean.getForward()));

			fmb.setInmoneyTotal(inMonetTotal);

			fmb.setOutmoneyTotal(outMonetTotal);

			if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN)
			{
				fmb.setLastTotal(inMonetTotal - outMonetTotal);
			}
			else
			{
				fmb.setLastTotal(outMonetTotal - inMonetTotal);
			}

			fmb.setLogTime(TimeTools.now());

			monthList.add(fmb);
            long end = System.currentTimeMillis();
            _logger.info(taxBean.getId()+"***time used***"+(end-begin));
		}

		request.setAttribute("monthList", monthList);

		return mapping.findForward("addFinanceTurn");
	}

	private void preInner(HttpServletRequest request)
	{
		CommonTools.saveParamers(request);

		if (request.getSession().getAttribute("stafferListStr") != null)
		{
			return;
		}

		List<TaxBean> taxList = taxDAO
				.listEntityBeansByOrder("order by TaxBean.code asc");

		for (Iterator iterator = taxList.iterator(); iterator.hasNext();)
		{
			TaxBean taxBean = (TaxBean) iterator.next();

			if (taxBean.getBottomFlag() == TaxConstanst.TAX_BOTTOMFLAG_ROOT)
			{
				iterator.remove();
			}
		}

		request.getSession().setAttribute("taxList", taxList);

		List<DutyBean> dutyList = dutyDAO.listEntityBeans();

		request.getSession().setAttribute("dutyList", dutyList);

		List<DepotBean> depotList = depotDAO.listEntityBeans();

		request.getSession().setAttribute("depotList", depotList);

		JSONArray object = new JSONArray(taxList, false);

		request.getSession().setAttribute("taxListStr", object.toString());

		List<StafferBean> tempList = stafferDAO.listCommonEntityBeans();

		List<StafferBean> stafferList = new ArrayList();

		List<PrincipalshipBean> principalshipBeans = this.principalshipDAO.listEntityBeans();

		for (StafferBean bean : tempList)
		{
			StafferBean nbean = new StafferBean();

			bean.setPwkey("");

//			PrincipalshipBean pri = orgManager.findPrincipalshipById(bean
//					.getPrincipalshipId());
			PrincipalshipBean pri = orgManager.findPrincipalshipById(principalshipBeans, bean
					.getPrincipalshipId());

			if (pri != null)
			{
				nbean.setDescription(pri.getName());
			}

			nbean.setPriList(null);
			nbean.setName(bean.getName());

			nbean.setId(bean.getId());
			nbean.setPrincipalshipId(bean.getPrincipalshipId());

			stafferList.add(nbean);
		}

		object = new JSONArray(stafferList, false);

		request.getSession().setAttribute("stafferListStr", object.toString());

		// List<PrincipalshipBean> priList = principalshipDAO.listEntityBeans();

		ConditionParse condition = new ConditionParse();

		condition.addWhereStr();

		condition.addIntCondition("PrincipalshipBean.status", "=",
				PublicConstant.ORG_STATUS_USED);

		List<PrincipalshipBean> priList = principalshipDAO
				.queryEntityBeansByCondition(condition);
		List<PrincipalshipBean> principalshipBeanList = this.principalshipDAO.listEntityBeans();
		for (PrincipalshipBean principalshipBean : priList)
		{
//			PrincipalshipBean fullBean = orgManager
//					.findPrincipalshipById(principalshipBean.getId());
			PrincipalshipBean fullBean = orgManager
					.findPrincipalshipById(principalshipBeanList, principalshipBean.getId());

			BeanUtil.copyProperties(principalshipBean, fullBean);

			principalshipBean.setName("[" + principalshipBean.getLevel() + "]"
					+ principalshipBean.getName() + "("
					+ principalshipBean.getParentName() + ")");
		}

		object = new JSONArray(priList, false);

		request.getSession().setAttribute("priListStr", object.toString());
	}

	/**
	 * rptQueryUnit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward rptQueryUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		CommonTools.saveParamers(request);

		List<UnitBean> list = null;

		if (PageSeparateTools.isFirstLoad(request))
		{
			ConditionParse condtion = new ConditionParse();

			condtion.addWhereStr();

			setInnerCondition(request, condtion);

			int total = unitDAO.countByCondition(condtion.toString());

			PageSeparate page = new PageSeparate(total,
					PublicConstant.PAGE_COMMON_SIZE);

			PageSeparateTools.initPageSeparate(condtion, page, request,
					RPTQUERYUNIT);

			list = unitDAO.queryEntityBeansByCondition(condtion, page);
		}
		else
		{
			PageSeparateTools.processSeparate(request, RPTQUERYUNIT);

			list = unitDAO.queryEntityBeansByCondition(
					PageSeparateTools.getCondition(request, RPTQUERYUNIT),
					PageSeparateTools.getPageSeparate(request, RPTQUERYUNIT));
		}

		request.setAttribute("list", list);

		return mapping.findForward(RPTQUERYUNIT);
	}

	private void setInnerCondition(HttpServletRequest request,
			ConditionParse condtion)
	{
		String name = request.getParameter("name");

		String code = request.getParameter("code");

		String type = request.getParameter("type");

		if (!StringTools.isNullOrNone(name))
		{
			condtion.addCondition("name", "like", name);
		}

		if (!StringTools.isNullOrNone(code))
		{
			condtion.addCondition("code", "like", code);
		}

		if (!StringTools.isNullOrNone(type))
		{
			condtion.addIntCondition("type", "=", type);
		}

		condtion.addCondition("order by id desc");
	}
	
	/**
	 * queryFinanceTag
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryFinanceTag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		Map<String, String> initMap = initTagTime(request, condtion);

		ActionTools.processJSONDataQueryCondition(QUERYFINANCETAG, request,
				condtion, initMap);

		condtion.addCondition("order by FinanceTagBean.statsTime desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYFINANCETAG,
				request, condtion, this.financeTagDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}
	
	private Map<String, String> initTagTime(HttpServletRequest request,
			ConditionParse condtion)
	{
		Map<String, String> changeMap = new HashMap<String, String>();

		String alogTime = request.getParameter("afinanceTagDate");

		String blogTime = request.getParameter("bfinanceTagDate");

		if (StringTools.isNullOrNone(alogTime)
				&& StringTools.isNullOrNone(blogTime))
		{
			changeMap.put("afinanceTagDate", TimeTools.getMonthBegin() + " 00:00:01");

			changeMap.put("bfinanceTagDate", TimeTools.now_short() + " 23:59:59");

			condtion.addCondition("FinanceTagBean.statsTime", ">=",
					TimeTools.getMonthBegin() + " 00:00:01");

			condtion.addCondition("FinanceTagBean.statsTime", "<=",
					TimeTools.now_short(1) + " 23:59:59");
		}
		
		return changeMap;
	}
	
	/**
	 * exportFinanceTag
	 * 导出标记数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportFinanceTag(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		OutputStream out = null;

		String filenName = "FinanceTag_" + TimeTools.now("MMddHHmmss") + ".csv";

		response.setContentType("application/x-dbf");

		response.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
				QUERYFINANCETAG);

		int count = financeTagDAO.countVOByCondition(condtion.toString());

		if (count > 150000)
		{
			return ActionTools.toError("导出数量大于150000,请重新选择时间段导出", mapping,
					request);
		}

		try
		{
			out = response.getOutputStream();

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			write.writeLine("类型,单号,标记,时间");

			PageSeparate page = new PageSeparate();

			page.reset2(count, 2000);

			WriteFileBuffer line = new WriteFileBuffer(write);

			int parent = 0;

			while (page.nextPage())
			{
				List<FinanceTagBean> voFList = financeTagDAO.queryEntityBeansByCondition(
						condtion, page);

				for (FinanceTagBean vo : voFList)
				{
					parent++;
					
					line.reset();

					line.writeColumn(vo.getTypeName());
					line.writeColumn(vo.getFullId());
					line.writeColumn(vo.getTag());
					line.writeColumn(vo.getStatsTime());
					
					line.writeLine();
				}
			}

			write.writeLine("导出结束,标记:" + parent);

			write.close();
		}
		catch (Throwable e)
		{
			_logger.error(e, e);

			return null;
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e1)
				{
				}
			}

			if (write != null)
			{

				try
				{
					write.close();
				}
				catch (IOException e1)
				{
				}
			}
		}

		return null;
	}
	
	/**
     * 后台任务 查询科目余额,同时只能一个task
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward asynQueryTaxFinance2(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
    	String beginDate = request.getParameter("beginDate");

        String endDate = request.getParameter("endDate");
    	
	    try{
	    	financeManager.asynQueryFinanceTax(beginDate, endDate);

	    	request.setAttribute(KeyConstant.MESSAGE, "后台计算中...");
	    } catch (MYException e) {
	    	request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            _logger.error(e, e);

            return mapping.findForward("error");
	    }
	    
	    return mapping.findForward(QUERYTAXFINANCE2);
	}
	
	/**
	 * @return the taxFacade
	 */
	public TaxFacade getTaxFacade()
	{
		return taxFacade;
	}

	/**
	 * @param taxFacade
	 *            the taxFacade to set
	 */
	public void setTaxFacade(TaxFacade taxFacade)
	{
		this.taxFacade = taxFacade;
	}

	/**
	 * @return the financeDAO
	 */
	public FinanceDAO getFinanceDAO()
	{
		return financeDAO;
	}

	/**
	 * @param financeDAO
	 *            the financeDAO to set
	 */
	public void setFinanceDAO(FinanceDAO financeDAO)
	{
		this.financeDAO = financeDAO;
	}

	/**
	 * @return the financeItemDAO
	 */
	public FinanceItemDAO getFinanceItemDAO()
	{
		return financeItemDAO;
	}

	/**
	 * @param financeItemDAO
	 *            the financeItemDAO to set
	 */
	public void setFinanceItemDAO(FinanceItemDAO financeItemDAO)
	{
		this.financeItemDAO = financeItemDAO;
	}

	/**
	 * @return the taxDAO
	 */
	public TaxDAO getTaxDAO()
	{
		return taxDAO;
	}

	/**
	 * @param taxDAO
	 *            the taxDAO to set
	 */
	public void setTaxDAO(TaxDAO taxDAO)
	{
		this.taxDAO = taxDAO;
	}

	/**
	 * @return the dutyDAO
	 */
	public DutyDAO getDutyDAO()
	{
		return dutyDAO;
	}

	/**
	 * @param dutyDAO
	 *            the dutyDAO to set
	 */
	public void setDutyDAO(DutyDAO dutyDAO)
	{
		this.dutyDAO = dutyDAO;
	}

	/**
	 * @return the stafferDAO
	 */
	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	/**
	 * @param stafferDAO
	 *            the stafferDAO to set
	 */
	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the unitDAO
	 */
	public UnitDAO getUnitDAO()
	{
		return unitDAO;
	}

	/**
	 * @param unitDAO
	 *            the unitDAO to set
	 */
	public void setUnitDAO(UnitDAO unitDAO)
	{
		this.unitDAO = unitDAO;
	}

	/**
	 * @return the checkViewDAO
	 */
	public CheckViewDAO getCheckViewDAO()
	{
		return checkViewDAO;
	}

	/**
	 * @param checkViewDAO
	 *            the checkViewDAO to set
	 */
	public void setCheckViewDAO(CheckViewDAO checkViewDAO)
	{
		this.checkViewDAO = checkViewDAO;
	}

	/**
	 * @return the outManager
	 */
	public OutManager getOutManager()
	{
		return outManager;
	}

	/**
	 * @param outManager
	 *            the outManager to set
	 */
	public void setOutManager(OutManager outManager)
	{
		this.outManager = outManager;
	}

	/**
	 * @return the financeManager
	 */
	public FinanceManager getFinanceManager()
	{
		return financeManager;
	}

	/**
	 * @param financeManager
	 *            the financeManager to set
	 */
	public void setFinanceManager(FinanceManager financeManager)
	{
		this.financeManager = financeManager;
	}

	/**
	 * @return the unitViewDAO
	 */
	public UnitViewDAO getUnitViewDAO()
	{
		return unitViewDAO;
	}

	/**
	 * @param unitViewDAO
	 *            the unitViewDAO to set
	 */
	public void setUnitViewDAO(UnitViewDAO unitViewDAO)
	{
		this.unitViewDAO = unitViewDAO;
	}

	/**
	 * @return the depotDAO
	 */
	public DepotDAO getDepotDAO()
	{
		return depotDAO;
	}

	/**
	 * @param depotDAO
	 *            the depotDAO to set
	 */
	public void setDepotDAO(DepotDAO depotDAO)
	{
		this.depotDAO = depotDAO;
	}

	/**
	 * @return the productDAO
	 */
	public ProductDAO getProductDAO()
	{
		return productDAO;
	}

	/**
	 * @param productDAO
	 *            the productDAO to set
	 */
	public void setProductDAO(ProductDAO productDAO)
	{
		this.productDAO = productDAO;
	}

	/**
	 * @return the principalshipDAO
	 */
	public PrincipalshipDAO getPrincipalshipDAO()
	{
		return principalshipDAO;
	}

	/**
	 * @param principalshipDAO
	 *            the principalshipDAO to set
	 */
	public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
	{
		this.principalshipDAO = principalshipDAO;
	}

	/**
	 * @return the financeTurnDAO
	 */
	public FinanceTurnDAO getFinanceTurnDAO()
	{
		return financeTurnDAO;
	}

	/**
	 * @param financeTurnDAO
	 *            the financeTurnDAO to set
	 */
	public void setFinanceTurnDAO(FinanceTurnDAO financeTurnDAO)
	{
		this.financeTurnDAO = financeTurnDAO;
	}

	/**
	 * @return the financeMonthDAO
	 */
	public FinanceMonthDAO getFinanceMonthDAO()
	{
		return financeMonthDAO;
	}

	/**
	 * @param financeMonthDAO
	 *            the financeMonthDAO to set
	 */
	public void setFinanceMonthDAO(FinanceMonthDAO financeMonthDAO)
	{
		this.financeMonthDAO = financeMonthDAO;
	}

	/**
	 * @return the orgManager
	 */
	public OrgManager getOrgManager()
	{
		return orgManager;
	}

	/**
	 * @param orgManager
	 *            the orgManager to set
	 */
	public void setOrgManager(OrgManager orgManager)
	{
		this.orgManager = orgManager;
	}

	/**
	 * @return the parameterDAO
	 */
	public ParameterDAO getParameterDAO()
	{
		return parameterDAO;
	}

	/**
	 * @param parameterDAO
	 *            the parameterDAO to set
	 */
	public void setParameterDAO(ParameterDAO parameterDAO)
	{
		this.parameterDAO = parameterDAO;
	}

	/**
	 * @return the financeRepDAO
	 */
	public FinanceRepDAO getFinanceRepDAO()
	{
		return financeRepDAO;
	}

	/**
	 * @param financeRepDAO
	 *            the financeRepDAO to set
	 */
	public void setFinanceRepDAO(FinanceRepDAO financeRepDAO)
	{
		this.financeRepDAO = financeRepDAO;
	}

	/**
	 * @return the financeTempDAO
	 */
	public FinanceTempDAO getFinanceTempDAO()
	{
		return financeTempDAO;
	}

	/**
	 * @param financeTempDAO
	 *            the financeTempDAO to set
	 */
	public void setFinanceTempDAO(FinanceTempDAO financeTempDAO)
	{
		this.financeTempDAO = financeTempDAO;
	}

	/**
	 * @return the financeItemTempDAO
	 */
	public FinanceItemTempDAO getFinanceItemTempDAO()
	{
		return financeItemTempDAO;
	}

	/**
	 * @param financeItemTempDAO
	 *            the financeItemTempDAO to set
	 */
	public void setFinanceItemTempDAO(FinanceItemTempDAO financeItemTempDAO)
	{
		this.financeItemTempDAO = financeItemTempDAO;
	}

	public FinanceMonthBefDAO getFinanceMonthBefDAO()
	{
		return financeMonthBefDAO;
	}

	public void setFinanceMonthBefDAO(FinanceMonthBefDAO financeMonthBefDAO)
	{
		this.financeMonthBefDAO = financeMonthBefDAO;
	}

	/**
	 * @return the financeTagDAO
	 */
	public FinanceTagDAO getFinanceTagDAO()
	{
		return financeTagDAO;
	}

	/**
	 * @param financeTagDAO the financeTagDAO to set
	 */
	public void setFinanceTagDAO(FinanceTagDAO financeTagDAO)
	{
		this.financeTagDAO = financeTagDAO;
	}

	/**
	 * @return the financeShowDAO
	 */
	public FinanceShowDAO getFinanceShowDAO()
	{
		return financeShowDAO;
	}

	/**
	 * @param financeShowDAO the financeShowDAO to set
	 */
	public void setFinanceShowDAO(FinanceShowDAO financeShowDAO)
	{
		this.financeShowDAO = financeShowDAO;
	}


}
