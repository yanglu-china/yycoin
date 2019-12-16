/**
 * File Name: ParentOutAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-3-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vo.StafferVSCustomerVO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.client.wrap.NotPayWrap;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.customervssail.dao.OutQueryDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.finance.dao.InsVSOutDAO;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PaymentApplyDAO;
import com.china.center.oa.finance.dao.PaymentDAO;
import com.china.center.oa.finance.dao.PreInvoiceVSOutDAO;
import com.china.center.oa.finance.facade.FinanceFacade;
import com.china.center.oa.finance.vs.InsVSOutBean;
import com.china.center.oa.product.bean.ComposeItemBean;
import com.china.center.oa.product.bean.DecomposeProductBean;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.facade.ProductFacade;
import com.china.center.oa.product.helper.StorageRelationHelper;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.NumberUtils;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.DepartmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.InvoiceCreditBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.InvoiceConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.AreaDAO;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.DutyVSInvoiceDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceCreditDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.ShowDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.StafferVSPriDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.helper.StafferHelper;
import com.china.center.oa.publics.manager.AuthManager;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.publics.manager.FatalNotify;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.manager.StafferManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vo.DutyVO;
import com.china.center.oa.publics.vs.DutyVSInvoiceBean;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.oa.publics.vs.StafferVSPriBean;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.BatchReturnLog;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.ExpressBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.PresentFlagBean;
import com.china.center.oa.sail.bean.PromotionBean;
import com.china.center.oa.sail.bean.PromotionItemBean;
import com.china.center.oa.sail.bean.TransportBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.PromotionConstant;
import com.china.center.oa.sail.dao.AppOutDAO;
import com.china.center.oa.sail.dao.AppOutVSOutDAO;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.BaseRepaireDAO;
import com.china.center.oa.sail.dao.BatchReturnLogDAO;
import com.china.center.oa.sail.dao.ConsignDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.ExpressDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutRepaireDAO;
import com.china.center.oa.sail.dao.PackageDAO;
import com.china.center.oa.sail.dao.PresentFlagDAO;
import com.china.center.oa.sail.dao.ProductExchangeConfigDAO;
import com.china.center.oa.sail.dao.PromotionDAO;
import com.china.center.oa.sail.dao.PromotionItemDAO;
import com.china.center.oa.sail.dao.SailConfigDAO;
import com.china.center.oa.sail.dao.StatsDeliveryRankDAO;
import com.china.center.oa.sail.dao.TwBaseDAO;
import com.china.center.oa.sail.dao.TwDistributionDAO;
import com.china.center.oa.sail.dao.TwOutDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.helper.YYTools;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.sail.manager.SailManager;
import com.china.center.oa.sail.vo.BaseVO;
import com.china.center.oa.sail.vo.DistributionVO;
import com.china.center.oa.sail.vo.OutBalanceVO;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.oa.sail.vo.SailConfigVO;
import com.china.center.oa.sail.wrap.BatchBackWrap;
import com.china.center.oa.sail.wrap.PromotionWrap;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.ParamterMap;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;
import com.china.center.tools.WriteFileBuffer;

// import edu.emory.mathcs.backport.java.util.Collections;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * ParentOutAction
 * 
 * @author ZHUZHU
 * @version 2011-3-21
 * @see com.china.center.oa.sail.action.ParentOutAction
 * @since 3.0
 */
public class ParentOutAction extends DispatchAction
{
	protected final Log _logger = LogFactory.getLog(getClass());

	protected final Log importLog = LogFactory.getLog("sec");

	protected UserDAO userDAO = null;

	protected SailManager sailManager = null;

	protected OutQueryDAO outQueryDAO = null;

	protected FatalNotify fatalNotify = null;

	protected OutManager outManager = null;

	protected FinanceFacade financeFacade = null;

	protected ProductDAO productDAO = null;

	protected InvoiceCreditDAO invoiceCreditDAO = null;

	protected CustomerMainDAO customerMainDAO = null;

	protected ProviderDAO providerDAO = null;

	protected PrincipalshipDAO principalshipDAO = null;

	protected StafferDAO stafferDAO = null;

	protected ParameterDAO parameterDAO = null;

	protected LocationDAO locationDAO = null;

	protected CommonDAO commonDAO = null;

	protected DepartmentDAO departmentDAO = null;

	protected StorageDAO storageDAO = null;

	/**
	 * 发票
	 */
	protected InsVSOutDAO insVSOutDAO = null;

	protected DepotDAO depotDAO = null;

	protected InBillDAO inBillDAO = null;

	protected OutBillDAO outBillDAO = null;

	protected ShowDAO showDAO = null;

	protected OrgManager orgManager = null;

	protected UserManager userManager = null;

	protected StafferManager stafferManager = null;

	protected FlowLogDAO flowLogDAO = null;

	protected OutDAO outDAO = null;

    protected TwOutDAO twOutDAO = null;

	protected FinanceDAO financeDAO = null;

	protected DutyDAO dutyDAO = null;

	protected AuthManager authManager = null;

	protected InvoiceDAO invoiceDAO = null;

	protected BaseDAO baseDAO = null;

    protected TwBaseDAO twBaseDAO = null;

	protected TwDistributionDAO twDistributionDAO = null;

	protected ConsignDAO consignDAO = null;

	protected DepotpartDAO depotpartDAO = null;

	protected StorageRelationManager storageRelationManager = null;

	protected BaseBalanceDAO baseBalanceDAO = null;

	protected OutBalanceDAO outBalanceDAO = null;

	protected DutyVSInvoiceDAO dutyVSInvoiceDAO = null;

	protected SailConfigDAO sailConfigDAO = null;

	protected ProvinceDAO provinceDAO = null;

	protected CityDAO cityDAO = null;

	protected AreaDAO areaDAO = null;

	protected StafferVSCustomerDAO stafferVSCustomerDAO = null;

	protected PromotionDAO promotionDAO = null;

	protected PromotionItemDAO promotionItemDAO = null;

	protected PaymentDAO paymentDAO = null;

	protected PaymentApplyDAO paymentApplyDAO = null;

	private StafferVSPriDAO stafferVSPriDAO = null;

	protected CommonMailManager commonMailManager = null;

	protected InvoiceinsItemDAO invoiceinsItemDAO = null;

	protected InvoiceinsDAO invoiceinsDAO = null;

	protected OutRepaireDAO outRepaireDAO = null;

	protected BaseRepaireDAO baseRepaireDAO = null;

	protected DistributionDAO distributionDAO = null;

	protected ExpressDAO expressDAO = null;

	protected AttachmentDAO attachmentDAO = null;
	
	protected AppOutDAO appOutDAO = null;
	
	protected AppOutVSOutDAO appOutVSOutDAO = null;
	
	protected StatsDeliveryRankDAO statsDeliveryRankDAO = null;
	
	protected PreInvoiceVSOutDAO preInvoiceVSOutDAO = null;
	
	protected BatchReturnLogDAO batchReturnLogDAO = null;

    protected PriceConfigDAO priceConfigDAO = null;

    protected ProductExchangeConfigDAO productExchangeConfigDAO = null;

    protected PriceConfigManager priceConfigManager = null;

    protected SailConfigManager sailConfigManager = null;

    protected ProductFacade productFacade = null;

	protected PackageDAO packageDAO = null;

	protected PresentFlagDAO presentFlagDAO = null;

	protected static String QUERYSELFOUT = "querySelfOut";

	protected static String QUERYSELFOUTBALANCE = "querySelfOutBalance";

	protected static String QUERYOUT = "queryOut";

	protected static String QUERYSELFBUY = "querySelfBuy";

	protected static String QUERYBUY = "queryBuy";

	protected static String RPTQUERYOUT = "rptQueryOut";

	protected static String RPTQUERYOUTBALANCE = "rptQueryOutBalance";

	protected static String RPTQUERYHISOUT = "rptQueryHisOut";

	protected static String RPTQUERYOUTFORINVOICE = "rptQueryOutForInvoice";

	protected static final String QUERYSELFOUTREPAIREAPPLY = "querySelfOutRepaireApply";

	protected static final String QUERYAPPROVEOUTREPAIREAPPLY = "queryApproveOutRepaireApply";

	protected static final String QUERYALLOUTREPAIREAPPLY = "queryAllOutRepaireApply";
	
	protected static final String QUERYAPPOUT = "queryAppOut";
	
	protected static String QUERYSTATSRANK = "queryStatsRank";
	
	protected static String QUERYSELFCONFIRMOUT = "querySelfConfirmOut";

    protected static final String QUERYPRODUCTEXCHANGE = "queryProductExchange";
    
    private StorageRelationDAO storageRelationDAO;

	/**
	 * 入库单操作锁
	 */
	protected static Object S_LOCK = new Object();

	/**
	 * queryForAdd
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryForAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		// 增加黑名单-停止销售类型判断
		User user = Helper.getUser(request);

		String stafferId = user.getStafferId();

		StafferBean stafferBean = stafferDAO.find(stafferId);

		List<StafferVSPriBean> priList = stafferVSPriDAO
				.queryEntityBeansByFK(stafferId);

		int temp = 0;

		if (!ListTools.isEmptyOrNull(priList))
		{
			for (int i = 0; i < priList.size(); i++)
			{
				StafferVSPriBean svp = priList.get(i);

				if (!StafferHelper
						.isVirtualDepartment(svp.getPrincipalshipId()))
				{
					temp = temp + 1;

					if (temp > 1)
					{
						request.setAttribute(KeyConstant.ERROR_MESSAGE,
								"所属组织只能为末级组织且只挂靠在一个组织下,请修改你的组织结构1");

						return mapping.findForward("error");
					}
				}

				List<PrincipalshipBean> prinList;

				try
				{
					prinList = orgManager.querySubPrincipalship(svp
							.getPrincipalshipId());
				}
				catch (MYException e)
				{
					request.setAttribute(KeyConstant.ERROR_MESSAGE,
							e.getErrorContent());

					return mapping.findForward("error");
				}

				if (!ListTools.isEmptyOrNull(prinList))
				{
					for (Iterator<PrincipalshipBean> iterator = prinList
							.iterator(); iterator.hasNext();)
					{
						PrincipalshipBean pBean = iterator.next();

						if (pBean.getStatus() == 1) iterator.remove();
					}

					if (prinList.size() > 1)
					{
						request.setAttribute(KeyConstant.ERROR_MESSAGE,
								"所属组织只能为末级组织且只挂靠在一个组织下,请修改你的组织结构2");

						return mapping.findForward("error");
					}
				}
			}
		}

		// 黑名单处理
		if (stafferBean.getBlack() == StafferConstant.BLACK_FORBID)
		{
			String blackName = DefinedCommon.getValue("stafferBlack",
					stafferBean.getBlack());

			String msg = "您现在是【" + blackName + "】类型用户，无法开单，请尽快回款";

			request.setAttribute("black", msg);
		}
		else
		{
			request.setAttribute("black", "");
		}

		// 商务模式下权限检查
		ActionForward error = checkAuthForEcommerce(request, user, mapping);

		if (null != error)
		{
			return error;
		}

		// 是否锁定库存
		if (storageRelationManager.isStorageRelationLock())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库存被锁定,不能开单");

			return mapping.findForward("error");
		}

		String flag = request.getParameter("flag");

		// 入库单
		if ("1".equals(flag))
		{
			try
			{
				innerForPrepare(request, true, true);
			}
			catch (MYException e)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						e.getErrorContent());

				return mapping.findForward("error");
			}

			List<DutyBean> dutyList = dutyDAO.listEntityBeans();

			for (Iterator iterator = dutyList.iterator(); iterator.hasNext();)
			{
				DutyBean dutyBean = (DutyBean) iterator.next();

				if (!(dutyBean.getId().equals(PublicConstant.DEFAULR_DUTY_ID) || dutyBean
						.getId().equals(PublicConstant.MANAGER_DUTY_ID)))
				{
					iterator.remove();
				}
			}

			request.setAttribute("dutyList", dutyList);

			//2015/8/5 针对“调拨”类型增加地址栏位
			//运输方式
			List<ExpressBean> expressList = this.expressDAO.listEntityBeans();
			request.setAttribute("expressList", expressList);

			//省市
			List<ProvinceBean> provinceList = this.provinceDAO.listEntityBeans();
			request.setAttribute("provinceList", provinceList);
			List<CityBean> cityList = this.cityDAO.listEntityBeans();
			request.setAttribute("cityList", cityList);

			return mapping.findForward("addBuy");
		}

		try
		{
			innerForPrepare(request, true, true);
		}
		catch (MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

		}

		if (OATools.isChangeToV5())
		{
			// 销售单
            System.out.println("*******************find you********");
			return mapping.findForward("addOut501");
		}
		else
		{
            System.out.println("*******************aha********");
			return mapping.findForward("addOut4_bak");
		}
	}

	/** 批量退货
	 * addBatchBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addBatchBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		// 增加黑名单-停止销售类型判断
		User user = Helper.getUser(request);

		request.getSession().removeAttribute("backWrapList");
		
		String stafferId = user.getStafferId();

		StafferBean stafferBean = stafferDAO.find(stafferId);

		prepareBack(request, stafferBean);

		request.setAttribute("stafferBean", stafferBean);

		return mapping.findForward("addBatchBack");
	}

	private void prepareBack(HttpServletRequest request, StafferBean stafferBean)
	{
		List<DepotBean> locationList = depotDAO.queryCommonDepotBean();

		// 销售单对仓库的过滤
		for (Iterator iterator = locationList.iterator(); iterator.hasNext();)
		{
			DepotBean depotBean = (DepotBean) iterator.next();

			if (depotBean.getId().equals(DepotConstant.MAKE_DEPOT_ID))
			{
				iterator.remove();

				continue;
			}

			if (depotBean.getId().equals(DepotConstant.STOCK_DEPOT_ID))
			{
				iterator.remove();

				continue;
			}

			// 人为规定
			if (depotBean.getName().indexOf("不可发") != -1)
			{
				iterator.remove();

				continue;
			}

			if (OATools.isChangeToV5())
			{
				// 兼容没有配置事业部的
				if (StringTools.isNullOrNone(depotBean.getIndustryId()))
				{
					continue;
				}

				// 如果事业部不匹配删除
				/*if (!depotBean.getIndustryId().equals(
						stafferBean.getIndustryId()))
				{
					iterator.remove();
					continue;
				}*/
			}
		}

		request.setAttribute("locationList", locationList);
	}

	/**
	 * batchBack
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward batchBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException, MYException
	{
		// 增加黑名单-停止销售类型判断
		User user = Helper.getUser(request);

		request.getSession().removeAttribute("backWrapList");
		
		String stafferId = user.getStafferId();

		StafferBean stafferBean = stafferDAO.find(stafferId);

		prepareBack(request, stafferBean);
		
		int timeLimit = MathTools.parseInt(request.getParameter("timeLimit"));
		request.setAttribute("timeLimit", request.getParameter("timeLimit"));
		
		String type = request.getParameter("outType");
		request.setAttribute("outType", type);
		
		String cusid = request.getParameter("customerId");

		request.setAttribute("customerId", cusid);
		request.setAttribute("cname", request.getParameter("cname"));
		
		String staff = request.getParameter("stafferId");

		request.setAttribute("stafferId", staff);
		request.setAttribute("stafferName", request.getParameter("stafferName"));
		
		
		String description = request.getParameter("description");
		request.setAttribute("description", description);

		String[] pronames = request.getParameterValues("projectpro");
		
		String[] proids = request.getParameterValues("s_projectproId");
		
		String[] amounts = request.getParameterValues("procount");

		if (proids == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "请选择要退的产品");

			return mapping.findForward("error");
		}
		
		// 将页面产品明细缓存 在session
		List<BatchBackWrap> wrapList = new ArrayList<BatchBackWrap>();
		
		for (int i = 0 ; i < proids.length; i++)
		{
			BatchBackWrap wrap = new BatchBackWrap();
			
			wrap.setProductId(proids[i]);
			wrap.setProductName(pronames[i]);
			wrap.setAmount(MathTools.parseInt(amounts[i]));
			
			wrapList.add(wrap);
		}
		
		request.getSession().setAttribute("backWrapList", wrapList);

		StringBuilder builder = new StringBuilder();
		
		boolean error = false;
		
		// 目的仓库
		String dirDeport = request.getParameter("dirDeport");
		request.setAttribute("dirDeport", dirDeport);

		if (StringTools.isNullOrNone(dirDeport))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "请选择目的仓库");

			error = true;
			
			request.setAttribute("backError", error);

			return mapping.findForward("addBatchBack");
		}

		DepotpartBean okDepotpart = depotpartDAO
				.findDefaultOKDepotpart(dirDeport);

		if (okDepotpart == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "仓库下没有良品仓");

			error = true;
			
			request.setAttribute("backError", error);

			return mapping.findForward("addBatchBack");
		}

		// 销售出库
		if (type.equals("0"))
		{
			List<BatchBackWrap> bbbList = new ArrayList<BatchBackWrap>();
			
			StringBuilder sb = new StringBuilder();
			
			try
			{
				for (int i = 0; i < proids.length; i++)
				{
					String productId = proids[i];
					
					// 先查近6月内 timeLimit = 0, and 1 means contain six months ago
					List<BatchBackWrap> list = outManager.queryOutCanBack(cusid, productId, MathTools.parseInt(amounts[i]), timeLimit, 0, builder);
					
					if (!StringTools.isNullOrNone(builder.toString()) && timeLimit == 0)
					{
						outManager.queryOutCanBack(cusid, productId, MathTools.parseInt(amounts[i]), 1, 1, sb);
					}
					
					bbbList.addAll(list);
				}
			}
			catch(MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						e.getErrorContent());
				
				error = true;
			}

			// 近6个月时，查询6个月前的也没有找到
			if (timeLimit == 0 && !StringTools.isNullOrNone(sb.toString()))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, sb.toString());
				
				error = true;
				
				request.setAttribute("backError", error);
				
				return mapping.findForward("addBatchBack");
			}
			
			if (!StringTools.isNullOrNone(builder.toString()))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, builder.toString());
				
				error = true;
				
				request.setAttribute("backError", error);
				
				return mapping.findForward("addBatchBack");
			}
			
			Map<String, List<BatchBackWrap>> map = new HashMap<String, List<BatchBackWrap>>(); 
			// 根据原单及类型进行数据重组
			for (BatchBackWrap each : bbbList)
			{
				String key = each.getRefOutFullId();
				
				if (map.containsKey(key))
				{
					List<BatchBackWrap> mapList = map.get(key);
					
					mapList.add(each);
				}
				else
				{
					List<BatchBackWrap> mapList = new ArrayList<BatchBackWrap>();
					
					mapList.add(each);
					
					map.put(key, mapList);
				}
			}
			
			// 正式生成退货申请单
			List<OutBean> obbList = new ArrayList<OutBean>();
			
			try
			{
				StringBuilder csb = new StringBuilder();
				
				for (Map.Entry<String, List<BatchBackWrap>> entry : map.entrySet())
				{
					List<BatchBackWrap> mList = entry.getValue();
					
					OutBean bean = new OutBean();
					
					// 委托退货单
					setOutBean(mList, request, bean);
					
					obbList.add(bean);
					
					// 批量检查 退货金额是否大于可开票金额
					OutBean oriOut = outDAO.find(bean.getRefOutFullId());
					
					double backTotal = outDAO.sumOutBackValue(bean.getRefOutFullId());
					
					if (MathTools.compare(bean.getTotal(), (oriOut.getTotal() - backTotal - oriOut.getInvoiceMoney())) > 0) {
						csb.append(String.format("原库单[%s]退货金额[%.2f]须小于可开票金额[%.2f],请先退票。 ", bean.getRefOutFullId(), bean.getTotal(), oriOut.getTotal() - oriOut.getInvoiceMoney()));
	    				csb.append("<br>");
					}
				}
				
				if (!StringTools.isNullOrNone(csb.toString())) {
					throw new MYException(csb.toString());
				}
				
				// 批量保存
				outManager.addBatchOut(user, obbList, StorageConstant.OPR_STORAGE_OUTBACK);
				
				request.setAttribute(KeyConstant.MESSAGE,
						"销售出库批量退货申请成功,共生成" + map.size() +"张退货单");
			}
			catch(MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						e.getErrorContent());
				
				error = true;
			}
		}
		else if (type.equals("1")) // 委托代销
		{
			List<BatchBackWrap> bbbList = new ArrayList<BatchBackWrap>();
			
			try
			{
				for (int i = 0; i < proids.length; i++)
				{
					String productId = proids[i];
					
					List<BatchBackWrap> list = outManager.queryConsignCanBack(cusid, productId, MathTools.parseInt(amounts[i]), builder);
					
					bbbList.addAll(list);
				}
			}
			catch(MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						e.getErrorContent());
				
				error = true;
			}

			if (!StringTools.isNullOrNone(builder.toString()))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, builder.toString());
				
				error = true;
				
				request.setAttribute("backError", error);
				
				return mapping.findForward("addBatchBack");
			}
			
			Map<String, List<BatchBackWrap>> map = new HashMap<String, List<BatchBackWrap>>(); 
			// 根据原单及类型进行数据重组
			for (BatchBackWrap each : bbbList)
			{
				String key = each.getRefOutFullId() + "-" + each.getType() + "-" + each.getRefId();
				
				if (map.containsKey(key))
				{
					List<BatchBackWrap> mapList = map.get(key);
					
					mapList.add(each);
				}
				else
				{
					List<BatchBackWrap> mapList = new ArrayList<BatchBackWrap>();
					
					mapList.add(each);
					
					map.put(key, mapList);
				}
			}
			
			// 正式生成退货申请单
			List<OutBalanceBean> obbList = new ArrayList<OutBalanceBean>();
			
			try
			{
				StringBuilder csb = new StringBuilder();
				
				for (Map.Entry<String, List<BatchBackWrap>> entry : map.entrySet())
				{
					String key = entry.getKey();
					
					List<BatchBackWrap> mList = entry.getValue();
					
					OutBalanceBean bean = new OutBalanceBean();
					
					// 委托退货单
					if (key.split("-")[1].equals("0"))
					{
						setOutBalanceBean(mList, OutConstant.OUTBALANCE_TYPE_BACK, request, bean);
					}
					else
					{
						setOutBalanceBean(mList, OutConstant.OUTBALANCE_TYPE_SAILBACK, request, bean);
						
						// 批量检查 退货金额是否大于可开票金额
						OutBalanceBean old = outBalanceDAO.find(bean.getRefOutBalanceId());
			        	
			        	if (null != old) {
			        		double backTotal = outBalanceDAO.sumByOutBalanceId(old.getId());
			        		
			        		if (MathTools.compare(bean.getTotal(), old.getTotal() - backTotal - old.getInvoiceMoney()) > 0) {
			    				csb.append(String.format("原结算单[%s]退货金额[%.2f]须小于可开票金额[%.2f]，请先退票。 ", bean.getRefOutBalanceId(), bean.getTotal(), old.getTotal() - old.getInvoiceMoney()));
			    				csb.append("<br>");
			        		}
			        	}
					}
					
					obbList.add(bean);
				}
				
				if (!StringTools.isNullOrNone(csb.toString())) {
					throw new MYException(csb.toString());
				}
				
				// 批量保存
				outManager.addBatchOutBalance(user, obbList);
				
				request.setAttribute(KeyConstant.MESSAGE,
						"委托代销批量退货申请成功,共生成" + map.size() +"张退货单");
			}
			catch(MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						e.getErrorContent());
				
				error = true;
			}
		}
		else if (type.equals("2")) // 领样
		{
			List<BatchBackWrap> bbbList = new ArrayList<BatchBackWrap>();
			
			try
			{
				for (int i = 0; i < proids.length; i++)
				{
					String productId = proids[i];
					
					// 
					List<BatchBackWrap> list = outManager.queryOutSwatchCanBack(staff, "99", productId, MathTools.parseInt(amounts[i]), builder);
					
					bbbList.addAll(list);
				}
			}
			catch(MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,	e.getErrorContent());
				
				error = true;
			}

			if (!StringTools.isNullOrNone(builder.toString()))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, builder.toString());
				
				error = true;
				
				request.setAttribute("backError", error);
				
				return mapping.findForward("addBatchBack");
			}
			
			Map<String, List<BatchBackWrap>> map = new HashMap<String, List<BatchBackWrap>>(); 
			// 根据原单及类型进行数据重组
			for (BatchBackWrap each : bbbList)
			{
				String key = each.getRefOutFullId();
				
				if (map.containsKey(key))
				{
					List<BatchBackWrap> mapList = map.get(key);
					
					mapList.add(each);
				}
				else
				{
					List<BatchBackWrap> mapList = new ArrayList<BatchBackWrap>();
					
					mapList.add(each);
					
					map.put(key, mapList);
				}
			}
			
			// 正式生成退货申请单
			List<OutBean> obbList = new ArrayList<OutBean>();
			
			try
			{
				for (Map.Entry<String, List<BatchBackWrap>> entry : map.entrySet())
				{
					List<BatchBackWrap> mList = entry.getValue();
					
					OutBean bean = new OutBean();
					
					// 领样
					setOutSwatchBean(mList, request, bean);
					
					obbList.add(bean);
				}
				
				// 批量保存
				outManager.addBatchOut(user, obbList, StorageConstant.OPR_STORAGE_SWATCH);
				
				request.setAttribute(KeyConstant.MESSAGE, "销售领样批量退货申请成功,共生成" + map.size() +"张退货单");
			}
			catch(MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,	e.getErrorContent());
				
				error = true;
			}
		}else // 巡展
		{
			List<BatchBackWrap> bbbList = new ArrayList<BatchBackWrap>();
			
			try
			{
				for (int i = 0; i < proids.length; i++)
				{
					String productId = proids[i];
					
					// 
					List<BatchBackWrap> list = outManager.queryOutSwatchCanBack("", cusid, productId, MathTools.parseInt(amounts[i]), builder);
					
					bbbList.addAll(list);
				}
			}
			catch(MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,	e.getErrorContent());
				
				error = true;
			}

			if (!StringTools.isNullOrNone(builder.toString()))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, builder.toString());
				
				error = true;
				
				request.setAttribute("backError", error);
				
				return mapping.findForward("addBatchBack");
			}
			
			Map<String, List<BatchBackWrap>> map = new HashMap<String, List<BatchBackWrap>>(); 
			// 根据原单及类型进行数据重组
			for (BatchBackWrap each : bbbList)
			{
				String key = each.getRefOutFullId();
				
				if (map.containsKey(key))
				{
					List<BatchBackWrap> mapList = map.get(key);
					
					mapList.add(each);
				}
				else
				{
					List<BatchBackWrap> mapList = new ArrayList<BatchBackWrap>();
					
					mapList.add(each);
					
					map.put(key, mapList);
				}
			}
			
			// 正式生成退货申请单
			List<OutBean> obbList = new ArrayList<OutBean>();
			
			try
			{
				for (Map.Entry<String, List<BatchBackWrap>> entry : map.entrySet())
				{
					List<BatchBackWrap> mList = entry.getValue();
					
					OutBean bean = new OutBean();
					
					// 领样
					setOutSwatchBean(mList, request, bean);
					
					obbList.add(bean);
				}
				
				// 批量保存
				outManager.addBatchOut(user, obbList, StorageConstant.OPR_STORAGE_OUTBACK);
				
				request.setAttribute(KeyConstant.MESSAGE, "销售客户铺货批量退货申请成功,共生成" + map.size() +"张退货单");
			}
			catch(MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,	e.getErrorContent());
				
				error = true;
			}
		}
		
		if (!error)
		{
			request.getSession().removeAttribute("backWrapList");
		}
		
		request.setAttribute("backError", error);

		return mapping.findForward("addBatchBack");
	}

	/**
	 * #441 批量退货
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward batchBack2(ActionMapping mapping, ActionForm form,
											  HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		RequestDataStream rds = new RequestDataStream(request);

		User user = (User) request.getSession().getAttribute("user");

		boolean importError = false;

		List<BatchBackWrap> importItemList = new ArrayList<BatchBackWrap>();

		StringBuilder builder = new StringBuilder();
		try
		{
			rds.parser();
		}
		catch (Exception e1)
		{
			_logger.error(e1, e1);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward("batchBack2");
		}

		if ( !rds.haveStream())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward("batchBack2");
		}

		ReaderFile reader = ReadeFileFactory.getXLSReader();

		try
		{
			reader.readFile(rds.getUniqueInputStream());

			while (reader.hasNext())
			{
				String[] obj = StringUtils.fillObj((String[])reader.next(), 9);

				// 第一行忽略
				if (reader.getCurrentLineNumber() == 1)
				{
					continue;
				}

				if (StringTools.isNullOrNone(obj[0]))
				{
					continue;
				}

				int currentNumber = reader.getCurrentLineNumber();

				if (obj.length >= 2 )
				{
                    BatchBackWrap bean = new BatchBackWrap();

					// 类型
					if ( !StringTools.isNullOrNone(obj[0]))
					{
						String type = obj[0].trim();
                        //TODO
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("类型不能为空")
								.append("<br>");

						importError = true;
					}

					//单号
					if ( !StringTools.isNullOrNone(obj[1]))
					{
						String outId = obj[1].trim();
                        bean.setRefOutFullId(outId);
						OutBean out = this.outDAO.find(outId);
						if (out == null){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("单号不存在:"+outId)
									.append("<br>");

							importError = true;
						} else{
						    int status = out.getStatus();
						    if(status == OutConstant.STATUS_PASS
                                    || status == OutConstant.STATUS_SEC_PASS){

                                if (out.getType() == OutConstant.OUT_TYPE_OUTBILL) {
                                    if (out.getInvoiceMoney() > 0) {
                                        builder
                                                .append("第[" + currentNumber + "]错误:")
                                                .append("销售单已开票，请先退票:"+outId)
                                                .append("<br>");

                                        importError = true;
                                    }
                                }
						    }else{
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("只有已出库或已发货的订单可以退货:"+outId)
                                        .append("<br>");

                                importError = true;
                            }
					    }
					}else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("单号不能为空")
								.append("<br>");

						importError = true;
					}

					//客户
					if ( !StringTools.isNullOrNone(obj[2]))
					{
						String customerName = obj[2].trim();
						List<CustomerBean> customerBeans = this.customerMainDAO.queryByName(customerName);
						if (ListTools.isEmptyOrNull(customerBeans)){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("客户不存在:"+customerName)
									.append("<br>");

							importError = true;
						}
					}  else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("客户不能为空")
								.append("<br>");

						importError = true;
					}

					//商品名
					if ( !StringTools.isNullOrNone(obj[3]))
					{
						String productName = obj[3].trim();
						ProductBean productBean = this.productDAO.findByName(productName);
						if (productBean == null){
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("商品名不存在:"+productName)
                                    .append("<br>");

                            importError = true;
                        } else{
						    bean.setProductId(productBean.getId());
                        }
					} else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("商品名不能为空")
								.append("<br>");

						importError = true;
					}

					try {
					    //自动获取成本
                        String costPriceKey = this.outManager.getCostPriceKey(bean.getRefOutFullId(), bean.getProductId());
                        bean.setCostPriceKey(costPriceKey);
                    }catch(MYException e){
                        //获取不到成本则必填
                        if ( StringTools.isNullOrNone(obj[4]))
                        {
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("订单存在多个成本,必须填写退单成本:"+bean.getRefOutFullId())
                                    .append("<br>");

                            importError = true;
                        } else{
                            String cost = obj[4].trim();
                            bean.setCostPriceKey(StorageRelationHelper.getPriceKey(Double.valueOf(cost)));
                        }
                    }

					//退货数量
					if ( !StringTools.isNullOrNone(obj[5]))
					{
						String amount = obj[5].trim();
                        bean.setAmount(Integer.valueOf(amount));
					} else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("退货数量不能为空")
								.append("<br>");

						importError = true;
					}


					//退货数量必须小于可退数量
					boolean result = this.outManager.checkOutBack(bean.getRefOutFullId(), bean.getProductId(), bean.getCostPriceKey(),
							bean.getAmount());
					if (result){
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("退货数量大于可退数量:"+bean.getRefOutFullId())
								.append("<br>");

						importError = true;
					}

					//目的仓库
					if ( !StringTools.isNullOrNone(obj[6]))
					{
						String depot = obj[6].trim();
						DepotBean depotBean = this.depotDAO.findByUnique(depot);
						if (depotBean == null){
                            importError = true;
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("目的仓库不存在:"+depot)
                                    .append("<br>");
                        } else{
                            DepotpartBean depotpartBean = depotpartDAO.findDefaultOKDepotpart(depotBean.getId());

                            if (depotpartBean == null)
                            {
                                importError = true;
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("仓库下没有良品仓:"+depot)
                                        .append("<br>");
                            } else{
                                bean.setDepotId(depotpartBean.getLocationId());
                                bean.setDepotpartId(depotpartBean.getId());
                                bean.setDepotpartName(depotpartBean.getName());
                            }
                        }
					} else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("目的仓库不能为空")
								.append("<br>");

						importError = true;
					}

					//快递单号
					if ( !StringTools.isNullOrNone(obj[7]))
					{
						String transportNo = obj[7].trim();
                        bean.setTransportNo(transportNo);
					} else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("快递单号不能为空")
								.append("<br>");

						importError = true;
					}

					//退货备注
					if ( !StringTools.isNullOrNone(obj[8]))
					{
						String description = obj[8].trim();
                        bean.setDescription(description);
					} else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("退货备注不能为空")
								.append("<br>");

						importError = true;
					}

					importItemList.add(bean);
				}
				else
				{
					builder
							.append("第[" + currentNumber + "]错误:")
							.append("数据长度不足11格错误")
							.append("<br>");

					importError = true;
				}
			}
		}catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

			return mapping.findForward("batchBack2");
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (IOException e)
			{
				_logger.error(e, e);
			}
		}

		rds.close();
		if (importError){

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

			return mapping.findForward("batchBack2");
		}

		try
		{
            List<OutBean> outBeans = this.batchBack(user, importItemList);
            outManager.addBatchOut(user, outBeans, StorageConstant.OPR_STORAGE_OUTBACK);

            request.setAttribute(KeyConstant.MESSAGE,
                    "批量退货申请成功,共生成" + importItemList.size() +"张退货单");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getMessage());

			return mapping.findForward("batchBack2");
		}
		return mapping.findForward("batchBack2");
	}

	private List<OutBean> batchBack(User user, List<BatchBackWrap> beans) throws MYException{
	    _logger.info("***batchBack beans***"+beans);
	    List<OutBean> outBeans = new ArrayList<OutBean>();
	    for (BatchBackWrap wrap: beans){
	        OutBean bean = new OutBean();

            String outId = wrap.getRefOutFullId();

            OutBean oriOut = outDAO.find(outId);

            if (oriOut == null)
            {
                throw new MYException("单据不存在："+outId);
            }

            bean.setStatus(OutConstant.STATUS_SAVE);

            bean.setType(OutConstant.OUT_TYPE_INBILL);

            bean.setOutTime(TimeTools.now_short());

            bean.setDepartment(oriOut.getDepartment());

            bean.setCustomerId(oriOut.getCustomerId());

            bean.setCustomerName(oriOut.getCustomerName());

            bean.setStafferName(oriOut.getStafferName());

            bean.setStafferId(oriOut.getStafferId());

            bean.setTransportNo(wrap.getTransportNo());

            StafferVSCustomerVO vsCus = stafferVSCustomerDAO.findVOByUnique(oriOut.getCustomerId());

            if (null != vsCus){

                if (!vsCus.getStafferId().equals(oriOut.getStafferId()))
                {
                    bean.setStafferId(vsCus.getStafferId());
                    bean.setStafferName(vsCus.getStafferName());
                }
            }

            bean.setDutyId(oriOut.getDutyId());

            bean.setInvoiceId(oriOut.getInvoiceId());

            // 所在区域
            bean.setLocationId(user.getLocationId());

            // 目的仓库
            bean.setLocation(wrap.getDepotId());

            bean.setInway(OutConstant.IN_WAY_NO);

            if (outId.startsWith("ZS")){
				bean.setOutType(OutConstant.OUTTYPE_IN_PRESENT);
			}  else if (oriOut.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH
					|| oriOut.getOutType() == OutConstant.OUTTYPE_OUT_BANK_SWATCH
					|| oriOut.getOutType() == OutConstant.OUTTYPE_OUT_SHOW){
				// #403 “个人领样、银行领样、客户铺货”这3种类型的订单入库类型为“领样退库”
				bean.setOutType(OutConstant.OUTTYPE_IN_SWATCH);
			} else{
				bean.setOutType(OutConstant.OUTTYPE_IN_OUTBACK);
			}

            bean.setRefOutFullId(outId);
            bean.setDescription("批量退库,销售单号:" + outId + ". " + wrap.getDescription());

            bean.setOperator(user.getStafferId());
            bean.setOperatorName(user.getStafferName());

            //#629
            bean.setVirtualStatus(oriOut.getVirtualStatus());

            double total = 0.0d;

            List<BaseBean> newBaseList = new ArrayList<>();

            BaseBean oribase = this.outManager.getBaseBean(outId,wrap.getProductId(), wrap.getCostPriceKey());

            // 增加base
            BaseBean baseBean = new BaseBean();

            // 卖出价 * 数量
            baseBean.setLocationId(bean.getLocation());
            baseBean.setAmount(wrap.getAmount());
            baseBean.setProductName(oribase.getProductName());
            baseBean.setUnit("套");
            baseBean.setShowId(oribase.getShowId());
            baseBean.setProductId(oribase.getProductId());

            baseBean.setPrice(oribase.getPrice());
            baseBean.setCostPrice(oribase.getCostPrice());
            baseBean.setPprice(oribase.getPprice());
            baseBean.setIprice(oribase.getIprice());
            baseBean.setInputPrice(oribase.getInputPrice());
            baseBean.setCostPriceKey(StorageRelationHelper
                    .getPriceKey(oribase.getCostPrice()));

            //#545
            baseBean.setVirtualPrice(oribase.getVirtualPrice());
            baseBean.setVirtualPriceKey(StorageRelationHelper
                    .getPriceKey(oribase.getVirtualPrice()));

            baseBean.setOwner(oribase.getOwner());
            baseBean.setOwnerName(oribase.getOwnerName());

            if (oriOut.getLocation().equals(bean.getLocation()))
            {
                baseBean.setDepotpartId(oribase.getDepotpartId());
            }
            else
            {
                baseBean.setDepotpartId(wrap.getDepotpartId());
            }

            if (oriOut.getLocation().equals(bean.getLocation()))
            {
                baseBean.setDepotpartName(oribase.getDepotpartName());
            }
            else
            {
                baseBean.setDepotpartName(wrap.getDepotpartName());
            }

            baseBean.setValue(oribase.getPrice() * wrap.getAmount());

            // 成本
            baseBean.setDescription(String.valueOf(oribase
                    .getCostPrice()));

            newBaseList.add(baseBean);

            total += baseBean.getValue();
            bean.setTotal(total);

            bean.setBaseList(newBaseList);

	        outBeans.add(bean);
        }

	    return outBeans;
    }

	/**
	 * addDiaoBo
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addDiaoBo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{

		try
		{
			innerForPrepare(request, true, true);
		}
		catch (MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

			return mapping.findForward("error");
		}

		List<DutyBean> dutyList = dutyDAO.listEntityBeans();

		for (Iterator iterator = dutyList.iterator(); iterator.hasNext();)
		{
			DutyBean dutyBean = (DutyBean) iterator.next();

			if (!(dutyBean.getId().equals(PublicConstant.DEFAULR_DUTY_ID) || dutyBean
					.getId().equals(PublicConstant.MANAGER_DUTY_ID)))
			{
				iterator.remove();
			}
		}

		request.setAttribute("dutyList", dutyList);

		return mapping.findForward("addDiaoBo");

	}

	/**
	 * navigationAddOut2
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward navigationAddOut2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse reponse) throws ServletException
	{
		// 是否锁定库存
		if (storageRelationManager.isStorageRelationLock())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库存被锁定,不能开单");

			return mapping.findForward("error");
		}

		try
		{
			innerForPrepare(request, true, true);
		}
		catch (MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

			return mapping.findForward("error");
		}

		Map ssmap = (Map) request.getSession().getAttribute("ssmap");

		List<String> showIdList = (List<String>) request.getSession()
				.getAttribute("showIds");

		// 查询开单品名(是过滤出来的)
		List<ShowBean> showList = new ArrayList();

		for (String each : showIdList)
		{
			ShowBean show = showDAO.find(each);

			show.setDutyId(ssmap.get("duty").toString());

			showList.add(show);
		}

		DutyBean sailDuty = dutyDAO.find(ssmap.get("duty").toString());

		request.setAttribute("sailDuty", sailDuty);

		String ratio = request.getParameter("sailId");

		ssmap.put("ratio", ratio);

		request.setAttribute("invoiceDes", "销货发票,税点:" + ratio + "‰(千分值)");

		// 替换之前的
		JSONArray shows = new JSONArray(showList, true);

		// 替换过滤的show
		request.setAttribute("showJSON", shows.toString());

		List<DutyBean> dutyList = dutyDAO.listEntityBeans();

		request.setAttribute("dutyList2", dutyList);

		// 销售单
		return mapping.findForward("addOut2");
	}

	/**
	 * 准备增加委托结算清单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward preForAddOutBalance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse reponse) throws ServletException
	{
		CommonTools.saveParamers(request);

		// 0:结算 1:退货
		String type = request.getParameter("type");
		
		User user = Helper.getUser(request);

		// 商务 - begin
		ActionForward error = checkAuthForEcommerce(request, user, mapping);

		if (null != error)
		{
			return error;
		}
		// 商务 - end

		String outId = request.getParameter("outId");

		OutBean out = outDAO.find(outId);
		
    	// 正在对账
    	if (out.getFeedBackCheck() == 1 && type.equals("1"))
    	{
    		request.setAttribute(KeyConstant.ERROR_MESSAGE, "此销售单正在对账，不允许生成结算单或退货");

            return mapping.findForward("error");
    	}

		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);

		int totalLast = 0;

		for (BaseBean baseBean : baseList)
		{
			int hasPass = baseBalanceDAO.sumPassBaseBalance(baseBean.getId());

			baseBean.setInway(hasPass);

			int last = baseBean.getAmount() - baseBean.getInway();

			baseBean.setUnit(String.valueOf(last));

			totalLast += last;
		}

		if (totalLast <= 0)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "销售单委托代销已经结束");

			return mapping.findForward("error");

		}

		request.setAttribute("baseList", baseList);

		request.setAttribute("outId", outId);

		// 选择仓库
		createDepotList(request);

		request.setAttribute("out", out);

		// 销售单
		return mapping.findForward("addOutBalance");
	}

	/**
	 * 销售单移交
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward tranOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		CommonTools.saveParamers(request);

		User user = Helper.getUser(request);

		// 商务 - begin
		ActionForward error = checkAuthForEcommerce(request, user, mapping);

		if (null != error)
		{
			return error;
		}

		User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

		String elogin = (String) request.getSession().getAttribute("g_elogin");

		String g_loginType = (String) request.getSession().getAttribute(
				"g_loginType");

		if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

			return mapping.findForward("error");
		}

		OutBean out = new OutBean();

		// 当前切换用户登陆的且为商务登陆的，记录经办人
		if (!StringTools.isNullOrNone(elogin) && null != g_srcUser
				&& g_loginType.equals("1"))
		{
			out.setOperator(g_srcUser.getStafferId());
			out.setOperatorName(g_srcUser.getStafferName());
		}
		else
		{
			out.setOperator(user.getStafferId());
			out.setOperatorName(user.getStafferName());
		}
		// 商务 - end

		String outId = request.getParameter("outId");

		try
		{
			outManager.tranOut(user, outId, out);
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"处理错误:" + e.getErrorContent());

			return mapping.findForward("error");
		}

		CommonTools.saveParamers(request);

		RequestTools.menuInitQuery(request);

		request.setAttribute(KeyConstant.MESSAGE, "成功申请移交单据:" + outId);

		return querySelfOut(mapping, form, request, reponse);
	}

	/**
	 * 查询单体
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findOutBalance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		CommonTools.saveParamers(request);

		String id = request.getParameter("id");

		String fow = request.getParameter("fow");

		OutBalanceBean bean = outBalanceDAO.findVO(id);

		if (StringTools.isNullOrNone(fow))
		{
			List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(bean
					.getOutId());

			OutBean outBean = outDAO.find(bean.getOutId());

			for (BaseBean baseBean : baseList)
			{
				BaseBalanceBean bbb = baseBalanceDAO.findByUnique(
						baseBean.getId(), id);

				int hasPass = baseBalanceDAO.sumPassBaseBalance(baseBean
						.getId());

				baseBean.setInway(hasPass);

				if (null == bbb)
				{
					baseBean.setUnit("0");

					baseBean.setShowName(MathTools.formatNum(baseBean
							.getPrice()));
				}
				else
				{
					baseBean.setUnit(String.valueOf(bbb.getAmount()));

					baseBean.setShowName(MathTools.formatNum(bbb.getSailPrice()));
				}
			}

			List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);

			request.setAttribute("logList", logs);

			request.setAttribute("outBean", outBean);

			request.setAttribute("baseList", baseList);

			request.setAttribute("bean", bean);

			// 销售单
			return mapping.findForward("detailOutBalance");
		}
		else
		{
			OutBean outBean = outDAO.find(bean.getOutId());
			
        	// 正在对账
        	if (outBean.getFeedBackCheck() == 1)
        	{
        		request.setAttribute(KeyConstant.ERROR_MESSAGE, "此销售单正在对账，不允许退货");

                return mapping.findForward("error");
        	}
			
			List<BaseBalanceBean> refList = new ArrayList<BaseBalanceBean>();

			List<BaseBalanceBean> baseBalanceList = baseBalanceDAO
					.queryEntityBeansByFK(id);

			bean.setBaseBalanceList(baseBalanceList);

			List<OutBalanceBean> refOutBalanceList = outBalanceDAO
					.queryEntityBeansByFK(id, AnoConstant.FK_FIRST);

			for (OutBalanceBean each : refOutBalanceList)
			{
				List<BaseBalanceBean> refBBList = baseBalanceDAO
						.queryEntityBeansByFK(each.getId());

				refList.addAll(refBBList);
			}

			int totalLast = 0;

			for (BaseBalanceBean each : baseBalanceList)
			{
				int hasBack = 0;

				for (BaseBalanceBean eachInner : refList)
				{
					if (each.getBaseId().equals(eachInner.getBaseId()))
					{
						hasBack += eachInner.getAmount();
					}
				}

				each.setInway(hasBack);

				BaseBean baseBean = baseDAO.find(each.getBaseId());

				each.setProductName(baseBean.getProductName());

				totalLast += (each.getAmount() - hasBack);
			}

			if (totalLast == 0)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "委托代销结算单已经全部退货");

				return mapping.findForward("error");
			}

			request.setAttribute("bean", bean);

			request.setAttribute("outBean", outBean);

			// 选择仓库
			createDepotList(request);

			request.setAttribute("type", "2");

			return mapping.findForward("handerOutBalance");
		}

	}

	/**
	 * innerForPrepare(准备库单的维护)
	 * 
	 * @param request
	 * @param check
	 *            是否检查事业部
	 * @param needDeepQuery
	 * 
	 * @throws MYException
	 */
	protected void innerForPrepare(HttpServletRequest request, boolean check,
			boolean needDeepQuery) throws MYException
	{
		String flag = RequestTools.getValueFromRequest(request, "flag");
		
		String outId = RequestTools.getValueFromRequest(request, "outId");

		OutBean out = null;
		
		if (!StringTools.isNullOrNone(outId))
		{
			out = outDAO.find(outId);
		}
		
		// 得到部门
		List<DepartmentBean> list2 = departmentDAO.listEntityBeans();

		User user = Helper.getUser(request);
		String username = user.getName();
		// 仓库是自己选择的
		request.setAttribute("departementList", list2);

		request.setAttribute("current", TimeTools.now("yyyy-MM-dd"));

		boolean isStop = false;
		
		DepotBean depot = null;
		
		if (null != out)
		{
			depot = depotDAO.find(out.getLocation());
			
			if (null != depot)
			{
				// 停用
				if (depot.getStatus() == 1)
				{
					isStop = true;
				}
			}
		}
		
		List<DepotBean> locationList = depotDAO.queryCommonDepotBean();

		// 销售单对仓库的过滤
		if ("0".equals(flag))
		{
			StafferBean staffer = Helper.getStaffer(request);

			for (Iterator iterator = locationList.iterator(); iterator
					.hasNext();)
			{
				DepotBean depotBean = (DepotBean) iterator.next();
				
				if (depotBean.getId().equals(DepotConstant.MAKE_DEPOT_ID))
				{
					iterator.remove();

					continue;
				}

				if (depotBean.getId().equals(DepotConstant.STOCK_DEPOT_ID))
				{
					iterator.remove();

					continue;
				}

				// 人为规定
				if (depotBean.getName().indexOf("不可发") != -1)
				{
					iterator.remove();

					continue;
				}

				if (OATools.isChangeToV5())
				{
					// 兼容没有配置事业部的
					if (StringTools.isNullOrNone(depotBean.getIndustryId()))
					{
						continue;
					}

					// 如果事业部不匹配删除
					if (!depotBean.getIndustryId().equals(
							staffer.getIndustryId()))
					{
						iterator.remove();
						continue;
					}
				}
			}
			
			if (isStop)
			{
				locationList.add(depot);
			}

			setHasProm(request, staffer);
		}
		//2018/12/13 rollback
/*		else if ("1".equals(flag)){
			// # 486 只处理体内系统
			String appName = ConfigLoader.getProperty("appName");
			if ("永银ERP".equals(appName) && !"金加波".equals(username)){
			//只有这个人给特殊权限能看到
				for (Iterator iterator = locationList.iterator(); iterator
						.hasNext();) {
					DepotBean depotBean = (DepotBean) iterator.next();
					// #486
					if ("生产作业库".equals(depotBean.getName()))
					{
						iterator.remove();
						break;
					}
				}
			}
		}*/

		request.setAttribute("locationList", locationList);

		// 只能看到自己的仓库
		if ("1".equals(flag))
		{
			List<AuthBean> depotAuthList = userManager.queryExpandAuthById(
					user.getId(), AuthConstant.EXPAND_AUTH_DEPOT);

			for (Iterator iterator = locationList.iterator(); iterator
					.hasNext();)
			{
				DepotBean depotBean = (DepotBean) iterator.next();

				boolean had = false;

				for (AuthBean authBean : depotAuthList)
				{
					if (authBean.getId().equals(depotBean.getId()))
					{
						had = true;

						break;
					}
				}

				if (!had)
				{
					iterator.remove();
				}

			}

			List<DepotBean> dirLocationList = depotDAO.queryCommonDepotBean();

			request.setAttribute("dirLocationList", dirLocationList);
		}

		int goDays = parameterDAO.getInt(SysConfigConstant.OUT_PERSONAL_REDAY);

		request.setAttribute("goDays", goDays);

		ConditionParse condition = new ConditionParse();

		User oprUser = Helper.getUser(request);

		condition.addCondition("locationId", "=", oprUser.getLocationId());

		if (needDeepQuery)
		{
			showLastCredit(request, user, flag);
		}

		List<InvoiceBean> invoiceList = invoiceDAO.queryEntityBeansByCondition(
				"where forward = ?", InvoiceConstant.INVOICE_FORWARD_OUT);

		request.setAttribute("invoiceList", invoiceList);

		List<InvoiceBean> inInvoiceList = invoiceDAO
				.queryEntityBeansByCondition("where forward = ?",
						InvoiceConstant.INVOICE_FORWARD_IN);

		request.setAttribute("inInvoiceList", inInvoiceList);

		List<DutyVSInvoiceBean> vsList = dutyVSInvoiceDAO.listEntityBeans();

		// 过滤
		fiterVS(invoiceList, vsList);

		JSONArray vsJSON = new JSONArray(vsList, true);

		request.setAttribute("vsJSON", vsJSON.toString());

		JSONArray invoices = new JSONArray(invoiceList, true);

		request.setAttribute("invoicesJSON", invoices.toString());

		List<DutyBean> dutyList = dutyDAO.listEntityBeans();

		request.setAttribute("dutyList", dutyList);

		// 查询开单品名
		List<ShowBean> showList = showDAO.listEntityBeans();

		JSONArray shows = new JSONArray(showList, true);

		request.setAttribute("showJSON", shows.toString());

		StafferBean staffer = stafferDAO.find(user.getStafferId());

		if (check)
		{
			if (StringTools.isNullOrNone(staffer.getIndustryId()))
			{
				throw new MYException("您没有事业部属性,不能开单");
			}
		}

		double swatchMoney = outManager.getSwatchMoney(staffer.getId());
		
		request.setAttribute("swatchMoney", "个人领样应收合计:" + MathTools.formatNum2(swatchMoney));
		
		request.setAttribute("staffer", staffer);
	}

	/**
	 * 是否有自己事业部的促销活动
	 * 
	 * @param request
	 * @param staffer
	 */
	protected void setHasProm(HttpServletRequest request, StafferBean staffer)
	{
		ConditionParse condition = new ConditionParse();

		condition.addWhereStr();

		condition.addCondition("PromotionBean.beginDate", "<=",
				TimeTools.now("yyyy-MM-dd"));

		condition.addCondition("PromotionBean.endDate", ">=",
				TimeTools.now("yyyy-MM-dd"));

		condition.addCondition("PromotionBean.industryId", "like",
				staffer.getIndustryId());

		condition.addIntCondition("PromotionBean.inValid", "=", 0);

		int total = promotionDAO.countByCondition(condition.toString());

		if (total > 0) request.setAttribute("hasProm", "0");
		else
			request.setAttribute("hasProm", "1");
	}

	/**
	 * showLastCredit(剩余的信用)
	 * 
	 * @param request
	 * @param user
	 */
	protected void showLastCredit(HttpServletRequest request, User user,
			String flag)
	{
		StafferBean sb2 = stafferDAO.find(user.getStafferId());

		double noPayBusiness = outDAO.sumAllNoPayAndAvouchBusinessByStafferId(
				user.getStafferId(), sb2.getIndustryId(),
				YYTools.getStatBeginDate(), YYTools.getStatEndDate());

		if (sb2 != null && sb2.getBlack() == StafferConstant.BLACK_NO)
		{
			// 设置其剩余的信用额度
			request.setAttribute(
					"credit",
					ElTools.formatNum(sb2.getCredit() * sb2.getLever()
							- noPayBusiness));
		}
		else
		{
			request.setAttribute("credit", "您是黑名单");
		}

		if (!"1".equals(flag))
		{
			// 事业部经理的额度
			List<StafferBean> managerList = stafferManager
					.queryStafferByAuthIdAndIndustryId(
							AuthConstant.SAIL_LOCATION_MANAGER,
							sb2.getIndustryId());

			PrincipalshipBean pri = principalshipDAO.find(sb2.getIndustryId());

			// List<String> mList = new ArrayList<String>();

			for (StafferBean stafferBean : managerList)
			{
				// 查询经理担保的金额
				double noPayBusinessInM = outDAO
						.sumNoPayAndAvouchBusinessByManagerId(
								stafferBean.getId(), sb2.getIndustryId(),
								YYTools.getStatBeginDate(),
								YYTools.getStatEndDate());

				stafferBean.setDescription(stafferBean.getName()
						+ "的信用额度("
						+ pri.getName()
						+ ")剩余:"
						+ ElTools.formatNum(getIndustryIdCredit(
								sb2.getIndustryId(), stafferBean.getId())
								* stafferBean.getLever() - noPayBusinessInM));

				/*
				 * mList.add(stafferBean.getName() + "的信用额度(" + pri.getName() +
				 * ")剩余:" +
				 * ElTools.formatNum(getIndustryIdCredit(sb2.getIndustryId(),
				 * stafferBean .getId()) stafferBean.getLever() -
				 * noPayBusinessInM));
				 */

			}

			request.setAttribute("mList", managerList);
		}
	}

	protected void fiterVS(List<InvoiceBean> invoiceList,
			List<DutyVSInvoiceBean> vsList)
	{
		for (Iterator iterator = vsList.iterator(); iterator.hasNext();)
		{
			DutyVSInvoiceBean dutyVSInvoiceBean = (DutyVSInvoiceBean) iterator
					.next();

			boolean delete = true;

			for (InvoiceBean invoiceBean : invoiceList)
			{
				if (dutyVSInvoiceBean.getInvoiceId()
						.equals(invoiceBean.getId()))
				{
					delete = false;
					break;
				}
			}

			if (delete)
			{
				iterator.remove();
			}
		}
	}

	protected boolean hasOver(String stafferName)
	{
		ConditionParse condtion = new ConditionParse();

		// 获得条件
		getCondition(condtion, stafferName);

		List<OutBean> list = outDAO.queryEntityBeansByCondition(condtion);

		long current = new Date().getTime();

		for (OutBean outBean : list)
		{
			Date temp = TimeTools.getDateByFormat(outBean.getRedate(),
					"yyyy-MM-dd");

			if (temp != null)
			{
				if (temp.getTime() < current)
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 增加库单时查询供应商
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryProvider(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		List<ProviderBean> list = null;

		ConditionParse condition = new ConditionParse();

		String flag = request.getParameter("flagg");

		String name = request.getParameter("name");

		if (!StringTools.isNullOrNone(name))
		{
			condition.addCondition("name", "like", name);
			request.setAttribute("name", name);
		}

		String code = request.getParameter("code");

		if (!StringTools.isNullOrNone(code))
		{
			condition.addCondition("code", "like", code);
			request.setAttribute("code", code);
		}

		list = providerDAO.queryEntityBeansByLimit(condition, 100);

		request.setAttribute("customerList", list);

		request.setAttribute("flagg", flag);

		return mapping.findForward("rptProvider");
	}

	protected boolean containAuth(User user, String authId)
	{
		if (StringTools.isNullOrNone(authId))
		{
			return true;
		}

		if (authId.equals(AuthConstant.PUNLIC_AUTH))
		{
			return true;
		}

		List<RoleAuthBean> authList = user.getAuth();

		for (RoleAuthBean roleAuthBean : authList)
		{
			if (roleAuthBean.getAuthId().equals(authId))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * export销售单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward export2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		OutputStream out = null;

		String exportKey = (String) request.getSession().getAttribute(
				"exportKey");

		List<OutVO> outList = null;

		String filenName = null;

		User user = (User) request.getSession().getAttribute("user");

		if (OldPageSeparateTools.getPageSeparate(request, exportKey)
				.getRowCount() > 1500)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导出的记录数不能超过1500");

			return mapping.findForward("error");
		}

		outList = outDAO.queryEntityVOsByCondition(OldPageSeparateTools
				.getCondition(request, exportKey));

		filenName = "Export_" + TimeTools.now("MMddHHmmss") + ".xls";

		if (outList.size() == 0)
		{
			return null;
		}

		OutVO outVO = outList.get(0);

		if (outVO.getType() == 0)
		{
			if (!containAuth(user, AuthConstant.SAIL_QUERY_EXPORT))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

				return mapping.findForward("error");
			}

		}
		else
		{
			if (!containAuth(user, AuthConstant.BUY_EXPORT))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

				return mapping.findForward("error");
			}
		}

		reponse.setContentType("application/x-dbf");

		reponse.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WritableWorkbook wwb = null;

		WritableSheet ws = null;

		// 是否可以看到真实的成本
		boolean containAuth = userManager.containAuth(user.getId(),
				AuthConstant.SAIL_QUERY_COST);

		try
		{
			out = reponse.getOutputStream();

			// create a excel
			wwb = Workbook.createWorkbook(out);
			ws = wwb.createSheet("sheel1", 0);
			int i = 0, j = 0;

			OutVO element = null;

			WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLUE);

			WritableFont font2 = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.RED);

			WritableCellFormat format = new WritableCellFormat(font);

			WritableCellFormat format2 = new WritableCellFormat(font2);

			element = (OutVO) outList.get(0);

			String ffs = null;

			if (element.getType() == 0)
			{
				ffs = "出";
			}
			else
			{
				ffs = "入";
			}

			ws.addCell(new Label(j++, i, ffs + "库日期", format));
			ws.addCell(new Label(j++, i, "调" + ffs + "部门", format));
			ws.addCell(new Label(j++, i, element.getType() == 0 ? "客户"
					: "供应商(调出部门)", format));
			ws.addCell(new Label(j++, i, "事业部", format));
			ws.addCell(new Label(j++, i, "联系人", format));
			ws.addCell(new Label(j++, i, "联系电话", format));
			ws.addCell(new Label(j++, i, "单据号码", format));
			ws.addCell(new Label(j++, i, "类型", format));
			ws.addCell(new Label(j++, i, "回款日期", format));
			ws.addCell(new Label(j++, i, "库管通过日期", format));
			ws.addCell(new Label(j++, i, "状态", format));
			ws.addCell(new Label(j++, i, "是否回款", format));
			ws.addCell(new Label(j++, i, "申请人", format));
			ws.addCell(new Label(j++, i, "经办人", format));
			ws.addCell(new Label(j++, i, "仓库", format));
			ws.addCell(new Label(j++, i, "目的库", format));
			ws.addCell(new Label(j++, i, "关联单据", format));
			ws.addCell(new Label(j++, i, "描述", format));

			ws.addCell(new Label(j++, i, "品名", format));
			ws.addCell(new Label(j++, i, "单位", format));
			ws.addCell(new Label(j++, i, "数量", format));
			ws.addCell(new Label(j++, i, "单价", format));
			ws.addCell(new Label(j++, i, "金额", format));
			ws.addCell(new Label(j++, i, "成本", format));

			if (containAuth)
			{
				ws.addCell(new Label(j++, i, "事业部结算价", format));
				ws.addCell(new Label(j++, i, "总部结算价", format));
			}

			ws.addCell(new Label(j++, i, "发货单号", format));
			ws.addCell(new Label(j++, i, "发货方式", format));
			ws.addCell(new Label(j++, i, "总金额", format));

			// 写outbean
			for (Iterator iter = outList.iterator(); iter.hasNext();)
			{
				element = (OutVO) iter.next();

				// 写baseBean
				List<BaseBean> baseList = null;
				BaseBean base = null;

				ConsignBean consignBean = null;

				TransportBean transportBean = null;

				try
				{
					baseList = baseDAO
							.queryEntityBeansByFK(element.getFullId());

					consignBean = consignDAO.findDefaultConsignByFullId(element
							.getFullId());

					if (consignBean != null)
					{
						transportBean = consignDAO
								.findTransportById(consignBean.getTransport());
					}
				}
				catch (Exception e)
				{
					_logger.error(e, e);
				}

				for (Iterator iterator = baseList.iterator(); iterator
						.hasNext();)
				{
					j = 0;
					i++;

					ws.addCell(new Label(j++, i, element.getOutTime()));

					ws.addCell(new Label(j++, i, element.getDepartment()));

					ws.addCell(new Label(j++, i, element.getCustomerName()));

					ws.addCell(new Label(j++, i, element.getIndustryName()));

					ws.addCell(new Label(j++, i, element.getConnector()));

					ws.addCell(new Label(j++, i, element.getPhone()));

					ws.addCell(new Label(j++, i, element.getFullId()));

					ws.addCell(new Label(j++, i, OutHelper.getOutType(element)));

					ws.addCell(new Label(j++, i, element.getRedate()));

					String changeTime = element.getChangeTime();

					if (changeTime.length() > 10)
					{
						changeTime = changeTime.substring(0, 10);
					}

					ws.addCell(new Label(j++, i, changeTime));

					ws.addCell(new Label(j++, i, OutHelper.getStatus(
							element.getStatus(), false)));

					ws.addCell(new Label(j++, i, DefinedCommon.getValue(
							"outPay", element.getPay())));

					ws.addCell(new Label(j++, i, element.getStafferName()));

					ws.addCell(new Label(j++, i, element.getOperatorName()));
					
					ws.addCell(new Label(j++, i, element.getDepotName()));

					ws.addCell(new Label(j++, i, element.getDestinationName()));

					ws.addCell(new Label(j++, i, element.getRefOutFullId()));

					ws.addCell(new Label(j++, i, element.getDescription()));

					// 下面是base里面的数据
					base = (BaseBean) iterator.next();

					ws.addCell(new Label(j++, i, base.getProductName()));
					ws.addCell(new Label(j++, i, base.getUnit()));
					ws.addCell(new Label(j++, i, String.valueOf(base
							.getAmount())));
					ws.addCell(new Label(j++, i,
							String.valueOf(base.getPrice())));
					ws.addCell(new Label(j++, i,
							String.valueOf(base.getValue())));
					ws.addCell(new Label(j++, i, MathTools.formatNum(base
							.getCostPrice())));

					if (containAuth)
					{
						ws.addCell(new Label(j++, i, MathTools.formatNum(base
								.getIprice())));
						ws.addCell(new Label(j++, i, MathTools.formatNum(base
								.getPprice())));
					}

					if (!iterator.hasNext())
					{
						// 到出发货单和发货方式
						if (consignBean != null)
						{
							ws.addCell(new Label(j++, i, consignBean
									.getTransportNo()));

							if (transportBean != null)
							{
								ws.addCell(new Label(j++, i, transportBean
										.getName()));
							}
							else
							{
								ws.addCell(new Label(j++, i, ""));
							}
						}
						else
						{
							ws.addCell(new Label(j++, i, ""));
							ws.addCell(new Label(j++, i, ""));
						}

						ws.addCell(new Label(j++, i, MathTools
								.formatNum(element.getTotal()), format2));
					}
				}

			}
		}
		catch (Exception e)
		{
			_logger.error(e, e);
			return null;
		}
		finally
		{
			if (wwb != null)
			{
				try
				{
					wwb.write();
					wwb.close();
				}
				catch (Exception e1)
				{
				}
			}
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
		}

		return null;
	}

	/**
	 * export销售单(CSV导出)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward export(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		OutputStream out = null;

		String exportKey = (String) request.getSession().getAttribute(
				"exportKey");

		List<OutVO> outList = null;

		String flag = request.getParameter("flag");

		String filenName = null;

		User user = (User) request.getSession().getAttribute("user");

		if (OldPageSeparateTools.getPageSeparate(request, exportKey)
				.getRowCount() > 25000)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导出的记录数不能超过25000");

			return mapping.findForward("error");
		}

		outList = outDAO.queryEntityVOsByCondition(OldPageSeparateTools
				.getCondition(request, exportKey));

		filenName = "Export_" + TimeTools.now("MMddHHmmss") + ".csv";

		if (outList.size() == 0)
		{
			return null;
		}

		OutVO outVO = outList.get(0);

		if (null == flag || (!flag.equals("1")))
		{

			if (outVO.getType() == 0)
			{
				if (!containAuth(user, AuthConstant.SAIL_QUERY_EXPORT))
				{
					request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

					return mapping.findForward("error");
				}

			}
			else
			{
				if (!containAuth(user, AuthConstant.BUY_EXPORT))
				{
					request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

					return mapping.findForward("error");
				}
			}
		}

		reponse.setContentType("application/x-dbf");

		reponse.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WriteFile write = null;

		// 是否可以看到真实的成本
		boolean containAuth = userManager.containAuth(user.getId(),
				AuthConstant.SAIL_QUERY_COST);

		try
		{
			out = reponse.getOutputStream();

			OutVO element = null;

			element = (OutVO) outList.get(0);

			write = WriteFileFactory.getMyTXTWriter();

			write.openFile(out);

			WriteFileBuffer line = new WriteFileBuffer(write);

			line.writeColumn("单据日期");

//			line.writeColumn("客户编码");
			line.writeColumn("客户名称");
            line.writeColumn("客户ID");
			line.writeColumn("客户分类1");

			line.writeColumn("事业部");
			line.writeColumn("大区");
			line.writeColumn("部门");

			line.writeColumn("单据标识");
			line.writeColumn("类型");

			//#171 2016/2/14
			line.writeColumn("未审批原因");

			line.writeColumn("促销活动");
			line.writeColumn("绑定单号");
			line.writeColumn("折扣金额");
			line.writeColumn("回款日期");
			line.writeColumn("回款天数");

			line.writeColumn("退库原销售日期");
			line.writeColumn("退库原销售金额");
			line.writeColumn("退库原销售回款");
			line.writeColumn("原销售是否回款");
			line.writeColumn("是否退库");
			line.writeColumn("退货数量");
			line.writeColumn("相关退库");

			line.writeColumn("库管通过日期");
			line.writeColumn("单据状态");
			line.writeColumn("开票状态");
			line.writeColumn("是否回款");
			line.writeColumn("回款金额");
			line.writeColumn("总金额");
			//#775
			line.writeColumn("退货付款类型");

			line.writeColumn("申请人");
			line.writeColumn("经办人");
			line.writeColumn("仓库");
			line.writeColumn("目的库");
			line.writeColumn("关联单据");
			line.writeColumn("发票类型"); // only for 采购入库
			line.writeColumn("二级类型");

			line.writeColumn("商品编码");
			line.writeColumn("销项税率");
			line.writeColumn("品名");
			line.writeColumn("管理类型");
			line.writeColumn("单位");
			line.writeColumn("数量");
			line.writeColumn("单价");
			line.writeColumn("金额");
            //2015/6/17 导出中收激励金额
            line.writeColumn("中收金额");
            line.writeColumn("激励金额");
			line.writeColumn("中收2金额");
			line.writeColumn("其它费用金额");
			line.writeColumn("平台手续费金额");
            line.writeColumn("银行销售日期");

			line.writeColumn("产品类型");
			line.writeColumn("销售类型");

			if (containAuth)
			{
				line.writeColumn("成本");
			}
//			line.writeColumn("成本");
			line.writeColumn("业务员结算价");
			if (containAuth)
			{
				line.writeColumn("总部结算价");
				line.writeColumn("事业部结算价");
			}
//			line.writeColumn("总部结算价");
//			line.writeColumn("事业部结算价");

			line.writeColumn("发货单号");
			line.writeColumn("发货方式");
			line.writeColumn("描述");

			line.writeColumn("纳税实体");
			line.writeColumn("开票金额");

			line.writeColumn("省");
			line.writeColumn("市");
			line.writeColumn("区县");

			//#39
			line.writeColumn("地址");
			line.writeColumn("收件人");
			line.writeColumn("收件人电话");

			line.writeColumn("付款时间");
			line.writeColumn("旧货");
			
			line.writeColumn("批量操作人");
			line.writeColumn("批量操作批次号");
			line.writeColumn("批量操作时间");
			line.writeColumn("退货快递单号");
			line.writeColumn("快递单状态");

			line.writeLine();
			// 写outbean
			for (Iterator<OutVO> iter = outList.iterator(); iter.hasNext();)
			{
				element = (OutVO) iter.next();
				if (null != flag && flag.equals("1"))
				{
					if (element.getOutType() != 1 && element.getOutType() != 25)
					{
						continue;
					}
				}

				CustomerBean customerBean = customerMainDAO.find(element
						.getCustomerId());

				ProvinceBean provinceBean = null;

				CityBean cityBean = null;

				AreaBean areaBean = null;

				String protype = "";

				if (customerBean != null)
				{
					protype = DefinedCommon.getValue("102",
							customerBean.getProtype());
				}

				// 写baseBean
				List<BaseBean> baseList = null;

				BaseBean base = null;

				ConsignBean consignBean = null;

				TransportBean transportBean = null;

				OutBean refOut = null;

                DistributionVO distributionVO = null;

				try
				{
					baseList = baseDAO
							.queryEntityBeansByFK(element.getFullId());

					consignBean = consignDAO.findDefaultConsignByFullId(element
							.getFullId());

					if (element.getType() == 1
							&& !StringTools.isNullOrNone(element.getRefOutFullId()))
					{
						refOut = outDAO.find(element.getRefOutFullId());
					}

					if (consignBean != null)
					{
						transportBean = consignDAO
								.findTransportById(consignBean.getTransport());
					}

                    List<DistributionBean> distributionBeanList = distributionDAO.queryEntityBeansByFK(element.getFullId());
                    if (!ListTools.isEmptyOrNull(distributionBeanList)){
                        distributionVO = this.distributionDAO.findVO(distributionBeanList.get(0).getId());

                        provinceBean = provinceDAO.find(distributionVO.getProvinceId());

                        cityBean = cityDAO.find(distributionVO.getCityId());

                        areaBean = areaDAO.find(distributionVO.getAreaId());
                    }
				}
				catch (Exception e)
				{
					_logger.error(e, e);
				}

				for (Iterator iterator = baseList.iterator(); iterator
						.hasNext();)
				{
					line.reset();

					// 下面是base里面的数据
					base = (BaseBean) iterator.next();

					line.writeColumn(element.getOutTime());

//					line.writeColumn(element.getCustomerCode());
					line.writeColumn(element.getCustomerName());
					line.writeColumn(element.getCustomerId());
					line.writeColumn(protype);

					line.writeColumn(element.getIndustryName());
					line.writeColumn(element.getIndustryName2());
					line.writeColumn(element.getIndustryName3());

					line.writeColumn(element.getFullId());

					line.writeColumn(OutHelper.getOutType(element));

					line.writeColumn(element.getReason());

					line.writeColumn(element.getEventName());
					if (StringTools.isNullOrNone(element.getEventName()))
					{
						line.writeColumn("");
					}
					else
					{
						line.writeColumn(element.getRefBindOutId());
					}

					line.writeColumn(element.getPromValue());

					line.writeColumn(element.getRedate());
					line.writeColumn(element.getReday());


					// 退库原销售日期
					if (element.getType() == 1)
					{
						if (refOut == null)
						{
							line.writeColumn(element.getOutTime());
							line.writeColumn("");
							line.writeColumn("");
							line.writeColumn("");
						}
						else
						{
							line.writeColumn(refOut.getOutTime());
							line.writeColumn(MathTools.formatNum(refOut
									.getTotal()));
							line.writeColumn(MathTools.formatNum(refOut
									.getHadPay()));
//							line.writeColumn(DefinedCommon.getValue("outPay",
//									refOut.getPay()));
							boolean payFlag = this.getPayFlag(refOut);
							if (payFlag){
								line.writeColumn("已付款");
							} else{
								line.writeColumn("");
							}
						}

						line.writeColumn("");
						line.writeColumn("");
						line.writeColumn("");
					}
					else
					{
						line.writeColumn("");
						line.writeColumn("");
						line.writeColumn("");

						// 查询当前已经有多退货
						ConditionParse con = new ConditionParse();

						con.addWhereStr();

						con.addCondition("OutBean.refOutFullId", "=",
								element.getFullId());

						con.addCondition(" and OutBean.status in (3, 4)");

						con.addIntCondition("OutBean.type", "=",
								OutConstant.OUT_TYPE_INBILL);

						// 排除其他入库(对冲单据)
						con.addIntCondition("OutBean.outType", "<>",
								OutConstant.OUTTYPE_IN_OTHER);

						List<OutBean> refBuyList = outDAO
								.queryEntityBeansByCondition(con);

						if (refBuyList.size() > 0)
						{
							StringBuffer sb = new StringBuffer();

							int backAmount = 0;
							for (OutBean outBean : refBuyList)
							{
								sb.append(outBean.getFullId()).append(';');
								//#376 退货数量,找reffullid为此销售单号+商品+成本价 与原销售单相同的的记录的数量合计
								List<BaseBean> refBaseBeans = this.baseDAO.queryEntityBeansByFK(outBean.getFullId());
								for(BaseBean baseBean: refBaseBeans){
									if (!StringTools.isNullOrNone(baseBean.getProductId()) && baseBean.getProductId().equals(base.getProductId()) &&
											//!StringTools.isNullOrNone(baseBean.getCostPriceKey()) && baseBean.getCostPriceKey().equals(base.getCostPriceKey())
											//#764 base表中存在costPriceKey为空的情况,改为根据成本价相等来判断
											NumberUtils.equals(baseBean.getCostPrice(),base.getCostPrice(), 0.001)){
										backAmount += baseBean.getAmount();
									}
								}
							}

							boolean payFlag = this.getPayFlag(element);
							if (payFlag){
								line.writeColumn("已付款");
							} else{
								line.writeColumn("");
							}
							line.writeColumn("存在退货");
							//#376 退货数量
							line.writeColumn(backAmount);
							line.writeColumn(sb.toString());
						}
						else
						{
							//原销售是否回款
							line.writeColumn("");
							line.writeColumn("未退货");
							line.writeColumn("0");
							line.writeColumn("");
						}
					}

					String changeTime = element.getChangeTime();

					if (changeTime.length() > 10)
					{
						changeTime = changeTime.substring(0, 10);
					} else if( StringTools.isNullOrNone(changeTime) &&
							element.getType() == OutConstant.OUT_TYPE_OUTBILL && element.getStatus() == OutConstant.STATUS_SEC_PASS){
						//#830 部分已发货销售单flowTime为空
						changeTime = element.getChangeTime();
					}
					if(element.getStatus() == 3 || element.getStatus() == 4 || element.getStatus() == 99)
					{
						line.writeColumn(changeTime);
					}
					else
					{
						line.writeColumn("");
					}
					
					line.writeColumn(OutHelper.getOutStatus(element));
					line.writeColumn(DefinedCommon.getValue("invoiceStatus",
							element.getInvoiceStatus()));
					line.writeColumn(DefinedCommon.getValue("outPay",
							element.getPay()));
					line.writeColumn(MathTools.formatNum(element.getHadPay()));
					line.writeColumn(MathTools.formatNum(element.getTotal()));
					line.writeColumn(DefinedCommon.getValue("backPay",
							element.getBackPay()));

					line.writeColumn(element.getStafferName());
					line.writeColumn(element.getOperatorName());
					line.writeColumn(element.getDepotName());
					line.writeColumn(element.getDestinationName());
					line.writeColumn(element.getRefOutFullId());
					line.writeColumn(element.getInvoiceName());
//					line.writeColumn(DefinedCommon.getValue("presentFlag",
//							element.getPresentFlag()));
					ConditionParse conditionParse = new ConditionParse();
					conditionParse.addWhereStr();
					conditionParse.addCondition("type","=",element.getPresentFlag());
					List<PresentFlagBean> presentFlagBeans = this.presentFlagDAO.queryEntityBeansByCondition(conditionParse);
					if (ListTools.isEmptyOrNull(presentFlagBeans)){
						line.writeColumn("");
					} else{
						line.writeColumn(presentFlagBeans.get(0).getName());
					}

					ProductBean pb = productDAO.find(base.getProductId());
					// 产品编码
					if (null != pb){ 
						line.writeColumn(pb.getCode());
						String sailInvoice = pb.getSailInvoice();
						
						if (!StringTools.isNullOrNone(sailInvoice)) {
							InvoiceBean invoice = invoiceDAO.find(sailInvoice);
							
							if (null != invoice) {
								line.writeColumn(invoice.getName() + "(" + MathTools.formatNum(invoice.getVal()) + ")");
							} else {
								line.writeColumn("");
							}
						} else {
							line.writeColumn("");
						}
					}
					else {
						line.writeColumn("");
						line.writeColumn("");
					}

					line.writeColumn(base.getProductName());

					String managerTypeName = "";
					if (null != pb)
					{
						managerTypeName = DefinedCommon.getValue(
								"pubManagerType", pb.getReserve4());
					}
					line.writeColumn(managerTypeName);
					line.writeColumn(base.getUnit());
					line.writeColumn(String.valueOf(base.getAmount()));
					line.writeColumn(String.valueOf(base.getPrice()));
					line.writeColumn(String.valueOf(base.getValue()));

                    //2015/6/17 导出中收激励金额
                    line.writeColumn(MathTools.formatNum(base.getIbMoney()));
                    line.writeColumn(MathTools.formatNum(base.getMotivationMoney()));
					line.writeColumn(MathTools.formatNum(base.getIbMoney2()));
					line.writeColumn(MathTools.formatNum(base.getMotivationMoney2()));
					line.writeColumn(MathTools.formatNum(base.getPlatformFee()));
                    line.writeColumn(element.getPodate());

					String productType = "";
					String productSailType = "";

					if (null != pb)
					{
						productType = DefinedCommon.getValue("productType",
								pb.getType());

						productSailType = DefinedCommon.getValue(
								"productSailType", pb.getSailType());
					}

					line.writeColumn(productType);
					line.writeColumn(productSailType);

					if (containAuth)
					{
						
						line.writeColumn(MathTools.formatNum(base
								.getCostPrice()));
						line.writeColumn(MathTools.formatNum(base
								.getInputPrice()));
						line.writeColumn(MathTools.formatNum(base.getPprice()));
						line.writeColumn(MathTools.formatNum(base.getIprice()));
					}
					else
					{
//						line.writeColumn("");
						line.writeColumn(MathTools.formatNum(base
								.getInputPrice()));
//						line.writeColumn("");
//						line.writeColumn("");
					}

					// 到出发货单和发货方式
					if (consignBean != null)
					{
						line.writeColumn(consignBean.getTransportNo());

						if (transportBean != null)
						{
							line.writeColumn(transportBean.getName());
						}
						else
						{
                            if (distributionVO == null){
                                line.writeColumn("");
                            } else{
                                if (!StringTools.isNullOrNone(distributionVO.getTransportName1())){
                                    line.writeColumn(distributionVO.getTransportName1());
                                } else if (!StringTools.isNullOrNone(distributionVO.getTransportName2())){
                                    line.writeColumn(distributionVO.getTransportName2());
                                } else{
                                    line.writeColumn(OutHelper.getShippingName(distributionVO.getShipping()));
                                }
                            }
						}
					}
					else
					{
						line.writeColumn("");
                        if (distributionVO == null){
                            line.writeColumn("");
                        } else{
                            if (!StringTools.isNullOrNone(distributionVO.getTransportName1())){
                                line.writeColumn(distributionVO.getTransportName1());
                            } else if (!StringTools.isNullOrNone(distributionVO.getTransportName2())){
                                line.writeColumn(distributionVO.getTransportName2());
                            } else{
								line.writeColumn(OutHelper.getShippingName(distributionVO.getShipping()));
                            }
                        }
                    }

					line.writeColumn(element.getDescription());

					// line.writeColumn("管理类型"); add by wulin
					// line.writeColumn("纳税实体");
					// line.writeColumn("开票金额");
					// line.writeColumn("商品编码");
					// line.writeColumn("客户名称");
					// insVSOutDAO.queryEntityBeansByFK(element.getFullId());

					double sumMoney = 0.0d;
					List<InsVSOutBean> insList = insVSOutDAO
							.queryEntityBeansByFK(element.getFullId());
					if (null != insList && insList.size() > 0)
					{
						for (InsVSOutBean ivoBean : insList)
						{
							sumMoney += ivoBean.getMoneys();
						}
					}

					request.setAttribute("insList", insList);

					line.writeColumn(element.getDutyName());
					line.writeColumn(sumMoney);

					if (provinceBean != null) line.writeColumn(provinceBean
							.getName());
					else
						line.writeColumn("");

					if (cityBean != null) line.writeColumn(cityBean.getName());
					else
						line.writeColumn("");

					if (areaBean != null) line.writeColumn(areaBean.getName());
					else
						line.writeColumn("");

					if(distributionVO == null){
						line.writeColumn("");
						line.writeColumn("");
						line.writeColumn("");
					} else{
						line.writeColumn(distributionVO.getAddress());
						line.writeColumn(distributionVO.getReceiver());
						line.writeColumn(distributionVO.getMobile());
					}

					line.writeColumn(element.getPayTime());
					
					if (pb != null)
						line.writeColumn(pb.getConsumeInDay() == ProductConstant.PRODUCT_OLDGOOD ? "旧货" : "非旧货");
					else
						line.writeColumn("");
					
					// 
					BatchReturnLog log = batchReturnLogDAO.findByUnique(element.getFullId());
					
					if (null != log)
					{
						line.writeColumn(log.getOperatorName());
						line.writeColumn(log.getBatchId());
						line.writeColumn(log.getLogTime());
					}else{
						line.writeColumn("");
						line.writeColumn("");
						line.writeColumn("");
					}

					line.writeColumn(element.getTransportNo());
					line.writeColumn(element.getOutbackStatus());
					line.writeLine();
				}

			}
		}
		catch (Exception e)
		{
			_logger.error(e, e);
			return null;
		}
		finally
		{
			if (write != null)
			{
				try
				{
					write.close();
				}
				catch (Exception e1)
				{
				}
			}
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
		}

		return null;
	}

	private boolean getPayFlag(OutBean out){
		//原销售是否回款
		//取日志里的记录来标记原销售单回款情况
		List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(out.getFullId());
		boolean payFlag = false;
		if (!ListTools.isEmptyOrNull(logs)){
			for (FlowLogBean log: logs){
				String description = log.getDescription();
				if (!StringTools.isNullOrNone(description) && description.contains("付款申请通过")
						&& description.contains(String.valueOf(out.getTotal()))){
					payFlag = true;
					break;
				}
			}
		}
		return payFlag;
	}

	/**
	 * exportOutBalance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportOutBalance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse reponse) throws ServletException
	{
		OutputStream out = null;

		String exportKey = QUERYSELFOUTBALANCE;

		List<OutBalanceVO> outList = null;

		String filenName = null;

		if (OldPageSeparateTools.getPageSeparate(request, exportKey)
				.getRowCount() > 1500)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导出的记录数不能超过1500");

			return mapping.findForward("error");
		}

		outList = outBalanceDAO.queryEntityVOsByCondition(OldPageSeparateTools
				.getCondition(request, exportKey));

		filenName = "Balance_Export_" + TimeTools.now("MMddHHmmss") + ".xls";

		if (outList.size() == 0)
		{
			return null;
		}

		reponse.setContentType("application/x-dbf");

		reponse.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WritableWorkbook wwb = null;

		WritableSheet ws = null;

		try
		{
			out = reponse.getOutputStream();

			// create a excel
			wwb = Workbook.createWorkbook(out);
			ws = wwb.createSheet("sheel1", 0);
			int i = 0, j = 0;

			OutBalanceVO element = null;

			WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLUE);

			WritableCellFormat format = new WritableCellFormat(font);

			element = (OutBalanceVO) outList.get(0);

			ws.addCell(new Label(j++, i, "日期", format));
			ws.addCell(new Label(j++, i, "客户", format));
			ws.addCell(new Label(j++, i, "标识", format));
			ws.addCell(new Label(j++, i, "销售单号", format));
			ws.addCell(new Label(j++, i, "总金额", format));
			ws.addCell(new Label(j++, i, "已付金额", format));
			ws.addCell(new Label(j++, i, "类型", format));
			ws.addCell(new Label(j++, i, "职员", format));
			ws.addCell(new Label(j++, i, "状态", format));
			ws.addCell(new Label(j++, i, "仓库", format));
			ws.addCell(new Label(j++, i, "开票状态", format));
			ws.addCell(new Label(j++, i, "描述", format));

			ws.addCell(new Label(j++, i, "商品编码", format));
			ws.addCell(new Label(j++, i, "品名", format));
			ws.addCell(new Label(j++, i, "结算数量", format));
			ws.addCell(new Label(j++, i, "销售价", format));
			ws.addCell(new Label(j++, i, "成本", format));
			ws.addCell(new Label(j++, i, "合计", format));

			// 写outbean
			for (Iterator iter = outList.iterator(); iter.hasNext();)
			{
				element = (OutBalanceVO) iter.next();

				// 写baseBean
				List<BaseBalanceBean> baseList = null;

				BaseBalanceBean base = null;

				try
				{
					baseList = baseBalanceDAO.queryEntityBeansByFK(element
							.getId());
				}
				catch (Exception e)
				{
					_logger.error(e, e);
				}

				for (Iterator iterator = baseList.iterator(); iterator
						.hasNext();)
				{
					j = 0;
					i++;

					ws.addCell(new Label(j++, i, element.getLogTime()));

					ws.addCell(new Label(j++, i, element.getCustomerName()));

					ws.addCell(new Label(j++, i, element.getId()));

					ws.addCell(new Label(j++, i, element.getOutId()));

					ws.addCell(new Label(j++, i, MathTools.formatNum(element
							.getTotal())));

					ws.addCell(new Label(j++, i, MathTools.formatNum(element
							.getPayMoney())));

					ws.addCell(new Label(j++, i, ElTools.get("outBalanceType",
							element.getType())));

					ws.addCell(new Label(j++, i, element.getStafferName()));

					ws.addCell(new Label(j++, i, ElTools.get(
							"outBalanceStatus", element.getStatus())));

					ws.addCell(new Label(j++, i, element.getDirDepotName()));

					ws.addCell(new Label(j++, i, DefinedCommon.getValue("invoiceStatus",
							element.getInvoiceStatus())));
					
					ws.addCell(new Label(j++, i, element.getDescription()));

					// 下面是base里面的数据
					base = (BaseBalanceBean) iterator.next();

					BaseBean baseBean = baseDAO.find(base.getBaseId());

					ProductBean pb = productDAO.find(baseBean.getProductId());
					// 产品编码
					if (null != pb) 
						ws.addCell(new Label(j++, i, pb.getCode()));
					else
						ws.addCell(new Label(j++, i, ""));
					
					ws.addCell(new Label(j++, i, baseBean.getProductName()));
					ws.addCell(new Label(j++, i, String.valueOf(base
							.getAmount())));
					ws.addCell(new Label(j++, i, MathTools.formatNum(baseBean
							.getPrice())));
					ws.addCell(new Label(j++, i, MathTools.formatNum(baseBean
							.getIprice())));
					ws.addCell(new Label(j++, i, MathTools.formatNum(base
							.getAmount() * baseBean.getPrice())));
				}

			}
		}
		catch (Exception e)
		{
			_logger.error(e, e);
			return null;
		}
		finally
		{
			if (wwb != null)
			{
				try
				{
					wwb.write();
					wwb.close();
				}
				catch (Exception e1)
				{
				}
			}
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
		}

		return null;
	}

	/**
	 * TEMPLATE 导出XLS文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward exportNotPay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		// 应收客户导出
		List<NotPayWrap> beanList = outQueryDAO.listNotPayWrap();

		OutputStream out = null;

		String filenName = null;

		filenName = "NotPay_" + TimeTools.now("MMddHHmmss") + ".xls";

		if (beanList.size() == 0)
		{
			return null;
		}

		reponse.setContentType("application/x-dbf");

		reponse.setHeader("Content-Disposition", "attachment; filename="
				+ filenName);

		WritableWorkbook wwb = null;

		WritableSheet ws = null;

		try
		{
			out = reponse.getOutputStream();

			// create a excel
			wwb = Workbook.createWorkbook(out);

			ws = wwb.createSheet("NOTPAY", 0);

			int i = 0, j = 0;

			NotPayWrap element = null;

			WritableFont font = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLUE);

			WritableFont font2 = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false,
					jxl.format.UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);

			WritableCellFormat format = new WritableCellFormat(font);

			WritableCellFormat format2 = new WritableCellFormat(font2);

			ws.addCell(new Label(j++, i, "客户名称", format));
			ws.addCell(new Label(j++, i, "客户编码", format));
			ws.addCell(new Label(j++, i, "信用等级", format));
			ws.addCell(new Label(j++, i, "信用分数", format));
			ws.addCell(new Label(j++, i, "应收账款", format));

			for (Iterator iter = beanList.iterator(); iter.hasNext();)
			{
				element = (NotPayWrap) iter.next();

				j = 0;
				i++;

				ws.addCell(new Label(j++, i, element.getCname()));
				ws.addCell(new Label(j++, i, element.getCcode()));

				ws.addCell(new Label(j++, i, element.getCreditName()));

				ws.addCell(new jxl.write.Number(j++, i, element.getCreditVal()));

				ws.addCell(new jxl.write.Number(j++, i, element.getNotPay(),
						format2));

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return null;
		}
		finally
		{
			if (wwb != null)
			{
				try
				{
					wwb.write();
					wwb.close();
				}
				catch (Exception e1)
				{
				}
			}
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
		}

		return null;
	}

	/**
	 * addOutBalance
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addOutBalance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		OutBalanceBean bean = new OutBalanceBean();

		// 商务 - begin
		ActionForward error = checkAuthForEcommerce(request, user, mapping);

		if (null != error)
		{
			return error;
		}

		User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

		String elogin = (String) request.getSession().getAttribute("g_elogin");

		String g_loginType = (String) request.getSession().getAttribute(
				"g_loginType");

		if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

			return mapping.findForward("error");
		}

		// 当前切换用户登陆的且为商务登陆的，记录经办人
		if (!StringTools.isNullOrNone(elogin) && null != g_srcUser
				&& g_loginType.equals("1"))
		{
			bean.setOperator(g_srcUser.getStafferId());
			bean.setOperatorName(g_srcUser.getStafferName());
		}
		else
		{
			bean.setOperator(user.getStafferId());
			bean.setOperatorName(user.getStafferName());
		}
		// 商务 - end

		try
		{
			setOutBalanceBean(bean, request);

			RequestTools.actionInitQuery(request);

			outManager.addOutBalance(user, bean);

			request.setAttribute(KeyConstant.MESSAGE, "成功增加结算清单");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e);
		}

		CommonTools.removeParamers(request);

		request.setAttribute("queryType", "1");

		return queryOutBalance(mapping, form, request, reponse);
	}

	/**
	 * 委托代销结算单退货
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward outBalanceBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		OutBalanceBean bean = new OutBalanceBean();

		// 商务 - begin
		ActionForward error = checkAuthForEcommerce(request, user, mapping);

		if (null != error)
		{
			return error;
		}

		User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

		String elogin = (String) request.getSession().getAttribute("g_elogin");

		String g_loginType = (String) request.getSession().getAttribute(
				"g_loginType");

		if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

			return mapping.findForward("error");
		}

		// 当前切换用户登陆的且为商务登陆的，记录经办人
		if (!StringTools.isNullOrNone(elogin) && null != g_srcUser
				&& g_loginType.equals("1"))
		{
			bean.setOperator(g_srcUser.getStafferId());
			bean.setOperatorName(g_srcUser.getStafferName());
		}
		else
		{
			bean.setOperator(user.getStafferId());
			bean.setOperatorName(user.getStafferName());
		}
		// 商务 - end

		try
		{
			setOutBalanceBean(bean, request);

			RequestTools.actionInitQuery(request);

			outManager.addOutBalance(user, bean);

			request.setAttribute(KeyConstant.MESSAGE, "成功增加结算清单");
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());
		}

		CommonTools.removeParamers(request);

		request.setAttribute("queryType", "1");

		return queryOutBalance(mapping, form, request, reponse);
	}

	/**
	 * 个人领样退库--申请
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward outBack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		User user = Helper.getUser(request);

		// 商务 - begin
		ActionForward error = checkAuthForEcommerce(request, user, mapping);

		if (null != error)
		{
			return error;
		}
		// 商务 - end

		String outId = request.getParameter("outId");

		// 目的仓库
		String dirDeport = request.getParameter("dirDeport");

		String adescription = request.getParameter("adescription");

		String transportNo = request.getParameter("transportNo");

		if (false)
		{
			// 查询是否被关联
			ConditionParse con = new ConditionParse();

			con.addWhereStr();

			con.addCondition("OutBean.refOutFullId", "=", outId);

			con.addIntCondition("OutBean.type", "=",
					OutConstant.OUT_TYPE_INBILL);

			con.addIntCondition("OutBean.status", "=", OutConstant.STATUS_SAVE);

			con.addIntCondition("OutBean.outType", "=",
					OutConstant.OUTTYPE_IN_SWATCH);

			int count = outDAO.countByCondition(con.toString());

			if (count > 0)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						"此领样已经申请退货请处理结束后再申请");

				return mapping.findForward("error");
			}
		}

		String[] baseItems = request.getParameterValues("baseItem");

		String[] backUnms = request.getParameterValues("backUnm");

		OutBean oldOut = outDAO.find(outId);

		if (oldOut == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

			return mapping.findForward("error");
		}

		if (StringTools.isNullOrNone(dirDeport))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "请选择目的仓库");

			return mapping.findForward("error");
		}

		if (oldOut.getType() != OutConstant.OUT_TYPE_OUTBILL
				&& (oldOut.getOutType() != OutConstant.OUTTYPE_OUT_SWATCH
						|| oldOut.getOutType() != OutConstant.OUTTYPE_OUT_SHOW
                        //2015/3/17 新增银行领样 （与银行铺货类拟）
                        || oldOut.getOutType() != OutConstant.OUTTYPE_OUT_BANK_SWATCH
						|| oldOut.getOutType() != OutConstant.OUTTYPE_OUT_SHOWSWATCH))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

			return mapping.findForward("error");
		}

		OutBean out = new OutBean();

		out.setStatus(OutConstant.STATUS_SAVE);

		out.setStafferName(user.getStafferName());

		out.setStafferId(user.getStafferId());

		out.setType(OutConstant.OUT_TYPE_INBILL);

		out.setOutTime(TimeTools.now_short());

		out.setDepartment("采购部");

		if (oldOut.getOutType() == OutConstant.OUTTYPE_OUT_SHOW
            //2015/3/17 新增银行领样 （与银行铺货类拟）
            || oldOut.getOutType() == OutConstant.OUTTYPE_OUT_BANK_SWATCH)
		{
			out.setCustomerId(oldOut.getCustomerId());

			out.setCustomerName(oldOut.getCustomerName());
		}
		else
		{
			out.setCustomerId("99");

			out.setCustomerName("系统内置供应商");
		}

		// 所在区域
		out.setLocationId(user.getLocationId());

		// 目的仓库
		// out.setLocation(oldOut.getLocation());
		// 目的仓库
		out.setLocation(dirDeport);

		out.setDestinationId(oldOut.getLocation());

		out.setInway(OutConstant.IN_WAY_NO);

		out.setOutType(OutConstant.OUTTYPE_IN_SWATCH);

		out.setRefOutFullId(outId);
		out.setTransportNo(transportNo);

		out.setDutyId(oldOut.getDutyId());

		out.setInvoiceId(oldOut.getInvoiceId());

		out.setDescription("个人领样退库,领样单号:" + outId + ". " + adescription);

		//#629
		out.setVirtualStatus(oldOut.getVirtualStatus());

		// 商务
		User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

		String elogin = (String) request.getSession().getAttribute("g_elogin");

		String g_loginType = (String) request.getSession().getAttribute(
				"g_loginType");

		if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

			return mapping.findForward("error");
		}

		// 当前切换用户登陆的且为商务登陆的，记录经办人
		if (!StringTools.isNullOrNone(elogin) && null != g_srcUser
				&& g_loginType.equals("1"))
		{
			out.setOperator(g_srcUser.getStafferId());
			out.setOperatorName(g_srcUser.getStafferName());
		}
		else
		{
			out.setOperator(user.getStafferId());
			out.setOperatorName(user.getStafferName());
		}
		// 商务 - end

		DepotpartBean okDepotpart = depotpartDAO
				.findDefaultOKDepotpart(dirDeport);

		if (okDepotpart == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "仓库下没有良品仓");

			return mapping.findForward("error");
		}

		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);

		// 校验库存
		List<OutBean> makeLingYang = makeLingYang(outId, request, baseList);

		if (!ListTools.isEmptyOrNull(makeLingYang) && false)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"此单存在转销售单据,所以只能全部转销售,不能退库处理");

			return mapping.findForward("error");
		}

		double total = 0.0d;

		List<BaseBean> newBaseList = new ArrayList<BaseBean>();

		for (int i = 0; i < baseItems.length; i++)
		{
			for (BaseBean each : baseList)
			{
				if (each.getId().equals(baseItems[i]))
				{
					int back = CommonTools.parseInt(backUnms[i]);

					if (each.getInway() + back > each.getAmount())
					{
						request.setAttribute(
								KeyConstant.ERROR_MESSAGE,
								each.getProductName() + "的退货数量超过:"
										+ each.getAmount());
						_logger.error(each+"***inway***"+each.getInway()+"****back***"+back+"***amount***"+each.getAmount());
						return mapping.findForward("error");

					}

					if (back > 0)
					{
						// 增加base
						BaseBean baseBean = new BaseBean();

						baseBean.setLocationId(out.getLocation());
						baseBean.setAmount(back);
						baseBean.setProductName(each.getProductName());
						baseBean.setUnit("套");
						baseBean.setShowId(each.getShowId());
						baseBean.setProductId(each.getProductId());

						// 领样退库的金额是销售的金额,否则无法回款
						baseBean.setPrice(each.getPrice());
						baseBean.setCostPrice(each.getCostPrice());
						baseBean.setIprice(each.getIprice());
						baseBean.setPprice(each.getPprice());
						baseBean.setInputPrice(each.getInputPrice());
						baseBean.setCostPriceKey(StorageRelationHelper
								.getPriceKey(each.getCostPrice()));

						//#545
						baseBean.setVirtualPrice(each.getVirtualPrice());
						baseBean.setVirtualPriceKey(StorageRelationHelper
								.getPriceKey(each.getVirtualPrice()));

						baseBean.setOwner(each.getOwner());
						baseBean.setOwnerName(each.getOwnerName());

						if (oldOut.getLocation().equals(out.getLocation()))
						{
							baseBean.setDepotpartId(each.getDepotpartId());
						}
						else
						{
							baseBean.setDepotpartId(okDepotpart.getId());
						}

						if (oldOut.getLocation().equals(out.getLocation()))
						{
							baseBean.setDepotpartName(each.getDepotpartName());
						}
						else
						{
							baseBean.setDepotpartName(okDepotpart.getName());
						}

						baseBean.setDescription(String.valueOf(each
								.getCostPrice()));

						baseBean.setValue(each.getPrice() * back);

						newBaseList.add(baseBean);

						total += baseBean.getValue();
					}
				}
			}
		}

		out.setTotal(total);

		out.setBaseList(newBaseList);

		try
		{
			if (newBaseList.size() > 0)
			{
				// CORE 个人领样退库
				String fullId = outManager.coloneOutWithAffair(out, user,
						StorageConstant.OPR_STORAGE_SWATH);

				request.setAttribute(KeyConstant.MESSAGE, "成功申请退库:" + fullId);
			}
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e);
		}

		CommonTools.removeParamers(request);

		request.setAttribute("forward", "1");

		request.setAttribute("queryType", "9");

		return queryOut(mapping, form, request, reponse);
	}

	/**
	 * 个人领样退库的预处理
	 * 
	 * @param outId
	 * @param request
	 * @param baseList
	 */
	private List<OutBean> makeLingYang(String outId,
			HttpServletRequest request, List<BaseBean> baseList)
	{
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

		// 领样转销售
		List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

		// 领样退库未审批的
		con.clear();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		con.addIntCondition("OutBean.status", "=", OutConstant.STATUS_SAVE);

		con.addIntCondition("OutBean.outType", "=",
				OutConstant.OUTTYPE_IN_SWATCH);

		List<OutBean> refBuyList = queryRefOut(request, outId);

		// 计算出已经退货的数量
		for (BaseBean baseBean : baseList)
		{
			int hasBack = 0;

			// 退库
			Set<String> backSet = new HashSet<String>();
			for (OutBean ref : refBuyList)
			{
				List<BaseBean> refBaseList = ref.getBaseList();

				for (BaseBean refBase : refBaseList)
				{
					if (refBase.equals(baseBean))
					{
						hasBack += refBase.getAmount();

						break;
					}
				}
				backSet.add(ref.getFullId());
			}

			// 转销售的
			//#73 #66 转销售的需要先核对是否已退库
			for (OutBean ref : refList)
			{
				String description = ref.getDescription();
				if(StringTools.isNullOrNone(description)){
					List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

					for (BaseBean refBase : refBaseList)
					{
						if (refBase.equals(baseBean))
						{
							hasBack += refBase.getAmount();

							break;
						}
					}
				} else{
					boolean flag = true;
					for (String backOutId : backSet){
						//已退库
						if(description.contains(backOutId)){
							flag = false;
							break;
						}
					}

					if (flag){
						List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

						for (BaseBean refBase : refBaseList)
						{
							if (refBase.equals(baseBean))
							{
								hasBack += refBase.getAmount();

								break;
							}
						}
					}
				}

//				List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref
//						.getFullId());
//
//				for (BaseBean refBase : refBaseList)
//				{
//					if (refBase.equals(baseBean))
//					{
//						hasBack += refBase.getAmount();
//
//						break;
//					}
//				}
			}

			baseBean.setInway(hasBack);
		}

		return refList;
	}

	/**
	 * 销售退单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward outBack2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		User user = Helper.getUser(request);

		// 商务 - begin
		ActionForward error1 = checkAuthForEcommerce(request, user, mapping);

		if (null != error1)
		{
			return error1;
		}
		// 商务 - end

		String outId = request.getParameter("outId");

		// 目的仓库
		String dirDeport = request.getParameter("dirDeport");

		String adescription = request.getParameter("adescription");

		String transportNo = request.getParameter("transportNo");

		ActionForward error = checkAddOutBack(mapping, request, outId);

		if (error != null)
		{
			return error;
		}

		String[] baseItems = request.getParameterValues("baseItem");

		String[] backUnms = request.getParameterValues("backUnm");

		OutBean oldOut = outDAO.find(outId);

		if (oldOut == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

			return mapping.findForward("error");
		}

		if (StringTools.isNullOrNone(dirDeport))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "请选择目的仓库");

			return mapping.findForward("error");
		}

		if (oldOut.getType() != OutConstant.OUT_TYPE_OUTBILL
				&& !(oldOut.getOutType() == OutConstant.OUTTYPE_OUT_COMMON
						|| oldOut.getOutType() == OutConstant.OUTTYPE_OUT_RETAIL || oldOut
						.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

			return mapping.findForward("error");
		}

		OutBean out = new OutBean();

		out.setStatus(OutConstant.STATUS_SAVE);

		out.setStafferName(user.getStafferName());

		out.setStafferId(user.getStafferId());

		out.setType(OutConstant.OUT_TYPE_INBILL);

		out.setOutTime(TimeTools.now_short());

		out.setDepartment(oldOut.getDepartment());

		out.setCustomerId(oldOut.getCustomerId());

		out.setCustomerName(oldOut.getCustomerName());

		out.setDutyId(oldOut.getDutyId());

		out.setInvoiceId(oldOut.getInvoiceId());

		// 所在区域
		out.setLocationId(user.getLocationId());

		// 目的仓库
		out.setLocation(dirDeport);

		out.setInway(OutConstant.IN_WAY_NO);

		out.setOutType(OutConstant.OUTTYPE_IN_OUTBACK);
		
		out.setRefOutFullId(outId);

		out.setTransportNo(transportNo);

		if (oldOut.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT) {
			out.setOutType(OutConstant.OUTTYPE_IN_PRESENT);
			out.setDescription("赠品退库,销售单号:" + outId + ". " + adescription);
		} else {
			out.setPay(1);
			out.setDescription("销售退库,销售单号:" + outId + ". " + adescription);

			//#775
			if (oldOut.getPay() == OutConstant.PAY_NOT){
				out.setBackPay(OutConstant.WHKTH);
			} else if (oldOut.getPay() == OutConstant.PAY_YES){
				out.setBackPay(OutConstant.YHKTH);
			}
		}

		//#629
		out.setVirtualStatus(oldOut.getVirtualStatus());
        //2015/5/12 拷贝原销售单中收激励情况
		//#87 XT退单生成的时候，中收激励标志都空。也是在提交中收激励时设置标志位
//        out.setIbFlag(oldOut.getIbFlag());
//		out.setIbApplyId(oldOut.getIbApplyId());
//        out.setMotivationFlag(oldOut.getMotivationFlag());
//		out.setMotivationApplyId(oldOut.getMotivationApplyId());

		// 商务
		User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

		String elogin = (String) request.getSession().getAttribute("g_elogin");

		String g_loginType = (String) request.getSession().getAttribute(
				"g_loginType");

		if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

			return mapping.findForward("error");
		}

		// 当前切换用户登陆的且为商务登陆的，记录经办人
		if (!StringTools.isNullOrNone(elogin) && null != g_srcUser
				&& g_loginType.equals("1"))
		{
			out.setOperator(g_srcUser.getStafferId());
			out.setOperatorName(g_srcUser.getStafferName());
		}
		else
		{
			out.setOperator(user.getStafferId());
			out.setOperatorName(user.getStafferName());
		}
		// 商务 - end

		DepotpartBean okDepotpart = depotpartDAO
				.findDefaultOKDepotpart(dirDeport);

		if (okDepotpart == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "仓库下没有良品仓");

			return mapping.findForward("error");
		}

		List<BaseBean> baseList = OutHelper.trimBaseList2(baseDAO
				.queryEntityBeansByFK(outId));

		List<OutBean> refBuyList = queryRefOut(request, outId);

		// 计算出已经退货的数量(这里是根据产品的总量进行统计的哦)
		for (BaseBean baseBean : baseList)
		{
//			int hasBack = 0;
			//#797
			int hasBack = this.outDAO.sumHasBack2(baseBean.getOutId(), baseBean.getProductName());

			for (OutBean ref : refBuyList)
			{
				List<BaseBean> refBaseList = OutHelper.trimBaseList2(ref
						.getBaseList());

				for (BaseBean refBase : refBaseList)
				{
					if (refBase.equals2(baseBean))
					{
						hasBack += refBase.getAmount();

						break;
					}
				}
			}

			baseBean.setInway(hasBack);

			baseBean.setId(OutHelper.getKey2(baseBean));
		}

		double total = 0.0d;

		List<BaseBean> newBaseList = new ArrayList<BaseBean>();

		for (int i = 0; i < baseItems.length; i++)
		{
			for (BaseBean each : baseList)
			{
				if (each.getId().equals(baseItems[i]))
				{
					int back = CommonTools.parseInt(backUnms[i]);

					if (each.getInway() + back > each.getAmount())
					{
						request.setAttribute(
								KeyConstant.ERROR_MESSAGE,
								each.getProductName() + "的退货数量超过:"
										+ each.getAmount());

						return mapping.findForward("error");

					}

					if (back > 0)
					{
						// 增加base
						BaseBean baseBean = new BaseBean();

						// 卖出价 * 数量
						baseBean.setLocationId(out.getLocation());
						baseBean.setAmount(back);
						baseBean.setProductName(each.getProductName());
						baseBean.setUnit("套");
						baseBean.setShowId(each.getShowId());
						baseBean.setProductId(each.getProductId());

						baseBean.setPrice(each.getPrice());
						baseBean.setCostPrice(each.getCostPrice());
						baseBean.setPprice(each.getPprice());
						baseBean.setIprice(each.getIprice());
						baseBean.setInputPrice(each.getInputPrice());
						baseBean.setCostPriceKey(StorageRelationHelper
								.getPriceKey(each.getCostPrice()));

						//#545
						baseBean.setVirtualPrice(each.getVirtualPrice());
						baseBean.setVirtualPriceKey(StorageRelationHelper
								.getPriceKey(each.getVirtualPrice()));

						baseBean.setOwner(each.getOwner());
						baseBean.setOwnerName(each.getOwnerName());

						if (oldOut.getLocation().equals(out.getLocation()))
						{
							baseBean.setDepotpartId(each.getDepotpartId());
						}
						else
						{
							baseBean.setDepotpartId(okDepotpart.getId());
						}

						if (oldOut.getLocation().equals(out.getLocation()))
						{
							baseBean.setDepotpartName(each.getDepotpartName());
						}
						else
						{
							baseBean.setDepotpartName(okDepotpart.getName());
						}

						baseBean.setValue(each.getPrice() * back);

						if (StringTools.isNullOrNone(each.getDepotpartId()))
						{
							request.setAttribute(KeyConstant.ERROR_MESSAGE,
									"可能是四月之前的单据,没有仓区所以不能退库");

							return mapping.findForward("error");
						}

						// 成本
						baseBean.setDescription(String.valueOf(each
								.getCostPrice()));


                        //2015/5/12 销售退库需要记录中收激励金额
                        baseBean.setIbMoney(each.getIbMoney());
                        baseBean.setMotivationMoney(each.getMotivationMoney());
                        baseBean.setPlatformFee(each.getPlatformFee());

						newBaseList.add(baseBean);

						total += baseBean.getValue();
					}
				}
			}
		}

		// 此次总金额
		out.setTotal(total);

		out.setBaseList(newBaseList);

		// 销售退库- 增加对折扣的检查
		try
		{
			checkPromValueOnReturn(oldOut, out, newBaseList);
			
			double backTotal = outDAO.sumOutBackValue(oldOut.getFullId());
			
			// 检查退货金额是否小于可开票金额
			double kkpje = oldOut.getTotal() - backTotal - oldOut.getInvoiceMoney();
			if (MathTools.compare(out.getTotal(), kkpje) > 0) {
				throw new MYException("退货金额[%.2f]须小于可开票金额[%.2f]，请先退票。 ", out.getTotal(), kkpje);
			}
		}
		catch (MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

			return mapping.findForward("error");
		}

		try
		{
			if (newBaseList.size() > 0)
			{
				// CORE 退库
				String fullId = outManager.coloneOutWithAffair(out, user,
						StorageConstant.OPR_STORAGE_OUTBACK);

				request.setAttribute(KeyConstant.MESSAGE, "成功申请退库:" + fullId);
			}
			else
			{

				request.setAttribute(KeyConstant.ERROR_MESSAGE, "退库数量为0");

				return mapping.findForward("error");

			}
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e);
		}

		CommonTools.removeParamers(request);

		request.setAttribute("forward", "1");

		request.setAttribute("queryType", "8");

		return queryOut(mapping, form, request, reponse);
	}

	private void checkPromValueOnReturn(OutBean oldOut, OutBean out,
			List<BaseBean> newBaseList) throws MYException
	{

		// 1)执行促销活动
		if (oldOut.getPromStatus() == OutConstant.OUT_PROMSTATUS_EXEC)
		{
			checkPromValueOnReturnInner(oldOut, out, newBaseList);
		}

		// 2)绑定促销活动
		if (oldOut.getPromStatus() == OutConstant.OUT_PROMSTATUS_BIND)
		{
			String refBindOutId = oldOut.getRefBindOutId();

			if (StringTools.isNullOrNone(refBindOutId))
			{
				throw new MYException("数据错误，请确认");
			}

			OutBean refBindOutBean = outDAO.find(refBindOutId);

			if (null == refBindOutBean)
			{
				throw new MYException("数据错误，请确认");
			}

			checkPromValueOnReturnInner(refBindOutBean, out, newBaseList);

		}
	}

	/**
	 * 
	 * @param oldOut
	 * @param out
	 * @param newBaseList
	 */
	private void checkPromValueOnReturnInner(OutBean oldOut, OutBean out,
			List<BaseBean> newBaseList) throws MYException
	{

		List<BaseBean> baseList1 = new ArrayList<BaseBean>();
		for (BaseBean bean1 : newBaseList)
		{
			BaseBean bean = new BaseBean();

			BeanUtil.copyProperties(bean, bean1);

			baseList1.add(bean);
		}

		String eventId = oldOut.getEventId();

		if (StringTools.isNullOrNone(eventId))
		{
			throw new MYException("数据错误，请确认");
		}

		PromotionBean promBean = promotionDAO.find(eventId);

		if (null == promBean)
		{
			throw new MYException("数据错误，请确认");
		}

		List<PromotionItemBean> promItemList = promotionItemDAO
				.queryEntityBeansByFK(eventId);

		if (null == promItemList)
		{
			throw new MYException("数据错误，请确认");
		}

		// 参与促销活动的所有销售单 refBindOutId及所有这些销售单的退库单
		String refBindOutId = oldOut.getRefBindOutId();

		if (StringTools.isNullOrNone(refBindOutId))
		{
			refBindOutId = "";
		}

		refBindOutId = oldOut.getFullId() + "~" + refBindOutId;

		String[] refs = refBindOutId.split("~");

		List<BaseBean> baseOutList = new ArrayList<BaseBean>();

		// 所有销售单
		for (String ref : refs)
		{
			if (!StringTools.isNullOrNone(ref))
			{
				List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(ref);

				baseOutList.addAll(baseList);
			}
		}

		List<BaseBean> baseBuyList = new ArrayList<BaseBean>();

		ConditionParse condition = new ConditionParse();

		// 所有退库单
		for (String ref : refs)
		{

			if (!StringTools.isNullOrNone(ref))
			{
				condition.clear();

				condition.addWhereStr();

				condition.addCondition("OutBean.refOutFullId", "=", ref);

				// condition.addCondition(" and OutBean.status in (3, 4)");

				condition.addIntCondition("OutBean.type", "=",
						OutConstant.OUT_TYPE_INBILL);

				condition.addIntCondition("OutBean.outType", "=",
						OutConstant.OUTTYPE_IN_OUTBACK);

				List<OutBean> refBuyList = outDAO
						.queryEntityBeansByCondition(condition);

				for (OutBean outBean : refBuyList)
				{
					// 增加判断其他库单是否存在退库未审批结束的，status not in (3,4);
					if (outBean.getStatus() != 3 && outBean.getStatus() != 4)
					{
						throw new MYException(
								"参与促销活动的销售退库时,单号：%s 存在退库单未审批结束的数据，请确认", ref);
					}

					List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean
							.getFullId());

					baseBuyList.addAll(list);
				}
			}
		}

		baseBuyList.addAll(baseList1);

		for (BaseBean baseBean : baseBuyList)
		{
			baseBean.setAmount(-baseBean.getAmount());
			baseBean.setValue(-baseBean.getValue());
		}

		baseOutList.addAll(baseBuyList);

		BaseBean bean = computeValidProduct(promBean, promItemList, baseOutList);

		PromotionWrap promWrap = new PromotionWrap();

		computePromValue(promBean, bean, promWrap);

		// 未退货部分满足促销活动
		if (promWrap.getRet() == 0)
		{
			if (promWrap.getPromValue() > 0)
			{
				if (promWrap.getPromValue() == oldOut.getPromValue())
				{
					// do noting, direct pass
					_logger.info("do noting,direct pass,原单:"
							+ oldOut.getFullId());

				}
				else if (promWrap.getPromValue() < oldOut.getPromValue())
				{
					out.setPromValue(oldOut.getPromValue()
							- promWrap.getPromValue());

					out.setPromStatus(OutConstant.OUT_PROMSTATUS_PARTBACKBIND);

					out.setRefBindOutId(oldOut.getFullId() + "~"
							+ oldOut.getRefBindOutId());

					_logger.info("退部分折扣,原单:" + oldOut.getFullId());
				}
				else
				{
					_logger.warn("余下商品（包含本次退货商品）:" + promWrap.getPromValue()
							+ ", 销售中折扣：" + oldOut.getPromValue());

					throw new MYException("销售退库时,剩余的商品不满足当时叁与的促销活动规则，请确认");
				}
			}
			else
			{
				if (out.getTotal() >= oldOut.getPromValue())
				{
					out.setPromValue(oldOut.getPromValue());

					out.setPromStatus(OutConstant.OUT_PROMSTATUS_BACKBIND);

					out.setRefBindOutId(oldOut.getFullId() + "~"
							+ oldOut.getRefBindOutId());

					_logger.info("退余下折扣,原单:" + oldOut.getFullId());
				}
				else
				{
					throw new MYException("销售退库时,退库总金额小于折扣金额，请确认");
				}

			}
		}

		if (promWrap.getRet() > 0)
		{
			if (out.getTotal() > oldOut.getPromValue())
			{
				out.setPromValue(oldOut.getPromValue());

				out.setPromStatus(OutConstant.OUT_PROMSTATUS_BACKBIND);

				out.setRefBindOutId(oldOut.getFullId() + "~"
						+ oldOut.getRefBindOutId());

				_logger.info("退余下折扣,原单:" + oldOut.getFullId());
			}
			else
			{
				_logger.info("销售退库时,剩余的商品不满足当时叁与的促销活动规则,且本次退库金额不能小于原单折扣金额,原单:"
						+ oldOut.getFullId());

				throw new MYException(
						"销售退库时,剩余的商品不满足当时叁与的促销活动规则,且本次退库金额不能小于原单折扣金额");
			}

		}

	}

	/**
	 * 检查是否可以增加销售退单
	 * 
	 * @param mapping
	 * @param request
	 * @param outId
	 * @return
	 */
	protected ActionForward checkAddOutBack(ActionMapping mapping,
			HttpServletRequest request, String outId)
	{
		// 查询是否被关联
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		con.addCondition("and OutBean.status in (0, 1)");

		con.addIntCondition("OutBean.outType", "=",
				OutConstant.OUTTYPE_IN_OUTBACK);

		int count = outDAO.countByCondition(con.toString());

		if (count > 0)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"此销售单存在申请退货,请处理结束后再申请");

			return mapping.findForward("error");
		}

		return null;
	}

	/**
	 * 其它入库关联原销售单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward outBack3(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		User user = Helper.getUser(request);

		// 商务 - begin
		ActionForward error1 = checkAuthForEcommerce(request, user, mapping);

		if (null != error1)
		{
			return error1;
		}
		// 商务 - end

		String outId = request.getParameter("outId");

		// 目的仓库
		String dirDeport = request.getParameter("dirDeport");

		String adescription = request.getParameter("adescription");

		String forceBuyType = request.getParameter("forceBuyType");

		String addOutDutyId = request.getParameter("addOutDutyId");

		ActionForward error = checkAddOutOtherBack(mapping, request, outId);

		if (error != null)
		{
			return error;
		}

		String[] baseItems = request.getParameterValues("baseItem");

		String[] backUnms = request.getParameterValues("backUnm");

		String[] costPrice = request.getParameterValues("desciprt");

		OutBean oldOut = outDAO.find(outId);

		if (oldOut == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

			return mapping.findForward("error");
		}

		if (StringTools.isNullOrNone(dirDeport))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "请选择目的仓库");

			return mapping.findForward("error");
		}

		if (oldOut.getType() != OutConstant.OUT_TYPE_OUTBILL
				&& !(oldOut.getOutType() == OutConstant.OUTTYPE_OUT_COMMON
						|| oldOut.getOutType() == OutConstant.OUTTYPE_OUT_RETAIL
						|| oldOut.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT || oldOut
						.getOutType() == OutConstant.OUTTYPE_OUT_CONSIGN))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

			return mapping.findForward("error");
		}

		OutBean out = new OutBean();

		out.setStatus(OutConstant.STATUS_SAVE);

		out.setStafferName(user.getStafferName());

		// 经手人（操作人）
		out.setStafferId(user.getStafferId());

		// 申请人(原单)
		out.setReserve9(oldOut.getStafferId());

		out.setType(OutConstant.OUT_TYPE_INBILL);

		out.setOutTime(TimeTools.now_short());

		out.setDepartment(oldOut.getDepartment());

		out.setCustomerId(oldOut.getCustomerId());

		out.setCustomerName(oldOut.getCustomerName());

		// 用入库单界面输入的纳税实体
		out.setDutyId(addOutDutyId);

		out.setInvoiceId(oldOut.getInvoiceId());

		// 所在区域
		out.setLocationId(user.getLocationId());

		// 目的仓库
		out.setLocation(dirDeport);

		out.setInway(OutConstant.IN_WAY_NO);

		// 其它入库关联单据 入库
		out.setOutType(OutConstant.OUTTYPE_IN_OTHER);

		out.setRefOutFullId(outId);

		out.setDescription("其它入库,原销售单号:" + outId + ". " + adescription);

		out.setForceBuyType(CommonTools.parseInt(forceBuyType));
		//#629
		out.setVirtualStatus(oldOut.getVirtualStatus());

		// 商务
		User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

		String elogin = (String) request.getSession().getAttribute("g_elogin");

		String g_loginType = (String) request.getSession().getAttribute(
				"g_loginType");

		if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

			return mapping.findForward("error");
		}

		// 当前切换用户登陆的且为商务登陆的，记录经办人
		if (!StringTools.isNullOrNone(elogin) && null != g_srcUser
				&& g_loginType.equals("1"))
		{
			out.setOperator(g_srcUser.getStafferId());
			out.setOperatorName(g_srcUser.getStafferName());
		}
		else
		{
			out.setOperator(user.getStafferId());
			out.setOperatorName(user.getStafferName());
		}
		// 商务 - end

		// 根据客户所属的业务员，如果有对应的业务员，则取对应的业务员，同时获取industryId, industryId2,
		// industryId3,如果没有则取原单中的业务员
		StafferVSCustomerBean stafferVSCustomerBean = stafferVSCustomerDAO
				.findByUnique(oldOut.getCustomerId());

		if (null != stafferVSCustomerBean)
		{
			if (!stafferVSCustomerBean.getStafferId().equals(
					oldOut.getStafferId()))
			// 申请人 - 凭证辅助核算项
			out.setReserve9(stafferVSCustomerBean.getStafferId());
		}

		// 获取 industryId, industryId2, industryId3 - 在manager中setInvoiceId()处理

		DepotpartBean okDepotpart = depotpartDAO
				.findDefaultOKDepotpart(dirDeport);

		if (okDepotpart == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "仓库下没有良品仓");

			return mapping.findForward("error");
		}

		List<BaseBean> obaseList = baseDAO.queryEntityBeansByFK(outId);

		for (BaseBean obean : obaseList)
		{
			obean.setDescription(obean.getId());
		}

		List<BaseBean> baseList = OutHelper.trimBaseList3(obaseList);

		List<OutBean> refBuyList = queryRefOut(request, outId);

		// 计算出已经退货的数量(这里是根据产品的总量进行统计的哦)
		for (BaseBean baseBean : baseList)
		{
			int hasBack = 0;

			for (OutBean ref : refBuyList)
			{
				List<BaseBean> refBaseList = OutHelper.trimBaseList3(ref
						.getBaseList());

				for (BaseBean refBase : refBaseList)
				{
					if (refBase.getDescription().equals(
							baseBean.getDescription()))
					{
						hasBack += refBase.getAmount();

						break;
					}
				}
			}

			baseBean.setInway(hasBack);

			baseBean.setId(OutHelper.getKey3(baseBean));
		}

		double total = 0.0d;

		List<BaseBean> newBaseList = new ArrayList<BaseBean>();

		for (int i = 0; i < baseItems.length; i++)
		{
			for (BaseBean each : baseList)
			{
				if (each.getId().equals(baseItems[i]))
				{
					int back = CommonTools.parseInt(backUnms[i]);

					if (each.getInway() + back > each.getAmount())
					{
						request.setAttribute(
								KeyConstant.ERROR_MESSAGE,
								each.getProductName() + "的退货数量超过:"
										+ each.getAmount());

						return mapping.findForward("error");

					}

					if (back > 0)
					{
						// 增加base
						BaseBean baseBean = new BaseBean();

						// 卖出价 * 数量
						baseBean.setLocationId(out.getLocation());
						baseBean.setAmount(back);
						baseBean.setProductName(each.getProductName());
						baseBean.setUnit("套");
						baseBean.setShowId(each.getShowId());
						baseBean.setProductId(each.getProductId());

						baseBean.setPrice(each.getPrice());

						if (each.getCostPrice() > 0)
						{
							baseBean.setCostPrice(each.getCostPrice());
							baseBean.setPprice(each.getPprice());
							baseBean.setIprice(each.getIprice());
							baseBean.setInputPrice(each.getInputPrice());
							baseBean.setCostPriceKey(StorageRelationHelper
									.getPriceKey(each.getCostPrice()));

							//#545
							baseBean.setVirtualPrice(each.getVirtualPrice());
							baseBean.setVirtualPriceKey(StorageRelationHelper
									.getPriceKey(each.getVirtualPrice()));
						}
						else
						{
							// 如果是2011.04.01 之前的单据，没有成本（0），其它入库时手工输入成本。
							baseBean.setCostPrice(MathTools
									.parseDouble(costPrice[i]));
							baseBean.setPprice(MathTools
									.parseDouble(costPrice[i]));
							baseBean.setIprice(MathTools
									.parseDouble(costPrice[i]));
							baseBean.setInputPrice(MathTools
									.parseDouble(costPrice[i]));
							baseBean.setCostPriceKey(StorageRelationHelper
									.getPriceKey(MathTools
											.parseDouble(costPrice[i])));
						}

						baseBean.setOwner(each.getOwner());
						baseBean.setOwnerName(each.getOwnerName());

						if (oldOut.getLocation().equals(out.getLocation()))
						{
							baseBean.setDepotpartId(each.getDepotpartId());
						}
						else
						{
							baseBean.setDepotpartId(okDepotpart.getId());
						}

						if (oldOut.getLocation().equals(out.getLocation()))
						{
							baseBean.setDepotpartName(each.getDepotpartName());
						}
						else
						{
							baseBean.setDepotpartName(okDepotpart.getName());
						}

						baseBean.setValue(each.getPrice() * back);

						// if (StringTools.isNullOrNone(each.getDepotpartId()))
						// {
						// request
						// .setAttribute(KeyConstant.ERROR_MESSAGE,
						// "可能是四月之前的单据,没有仓区所以不能退库");
						//
						// return mapping.findForward("error");
						// }

						// 成本
						// baseBean.setDescription(String.valueOf(baseBean.getCostPrice()));
						// 关联原单其它入库时，可能存在一单多退的情况。标识1:n
						baseBean.setDescription(each.getId());

						newBaseList.add(baseBean);

						total += baseBean.getValue();
					}
				}
			}
		}

		// 此次总金额
		out.setTotal(total);

		out.setBaseList(newBaseList);

		try
		{
			if (newBaseList.size() > 0)
			{
				// CORE 退库
				String fullId = outManager.coloneOutWithAffair(out, user,
						StorageConstant.OPR_STORAGE_INOTHER);

				outManager.submit(fullId, user,
						StorageConstant.OPR_STORAGE_INOTHER);

				request.setAttribute(KeyConstant.MESSAGE, "成功入库:" + fullId);
			}
			else
			{

				request.setAttribute(KeyConstant.ERROR_MESSAGE, "入库数量为0");

				return mapping.findForward("error");

			}
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e);
		}

		CommonTools.removeParamers(request);

		RequestTools.actionInitQuery(request);

		return querySelfBuy(mapping, form, request, reponse);
	}

	/**
	 * 
	 * @param mapping
	 * @param request
	 * @param outId
	 * @return
	 */
	protected ActionForward checkAddOutOtherBack(ActionMapping mapping,
			HttpServletRequest request, String outId)
	{
		// 查询是否被关联
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		con.addCondition("and OutBean.status not in (3, 4)");

		con.addIntCondition("OutBean.outType", "=",
				OutConstant.OUTTYPE_IN_OTHER);

		// 领样转销售自动生成的其它入库(对冲单)且记录标识 1
		con.addCondition("OutBean.reserve8", "<>", "1");

		int count = outDAO.countByCondition(con.toString());

		if (count > 0)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"此销售单存在申请其它入库,请处理结束后再申请");

			return mapping.findForward("error");
		}

		return null;
	}

	/**
	 * 收集数据
	 * 
	 * @param bean
	 * @param request
	 * @throws MYException
	 */
	protected void setOutBalanceBean(OutBalanceBean bean,
			HttpServletRequest request) throws MYException
	{
		User user = (User) request.getSession().getAttribute("user");

		String description = request.getParameter("description");

		String type = request.getParameter("type");

		String outId = request.getParameter("outId");

		OutBean out = outDAO.find(outId);

		if (out == null)
		{
			throw new MYException("数据错误,请确认操作");
		}

		bean.setCustomerId(out.getCustomerId());

		String dirDepot = request.getParameter("dirDepot");

		bean.setDescription(description);

		bean.setLogTime(TimeTools.now());

		bean.setStafferId(user.getStafferId());

		bean.setOutId(outId);

		bean.setDirDepot(dirDepot);

		bean.setStatus(OutConstant.OUTBALANCE_STATUS_SUBMIT);

		bean.setType(MathTools.parseInt(type));
		
		bean.setIndustryId(out.getIndustryId());

		String[] baseIds = request.getParameterValues("baseId");

		String[] amounts = request.getParameterValues("amount");

		String[] prices = request.getParameterValues("price");

		List<BaseBalanceBean> baseBalanceList = new ArrayList<BaseBalanceBean>();

		double total = 0.0d;
		for (int i = 0; i < baseIds.length; i++)
		{
			if (MathTools.parseInt(amounts[i]) > 0)
			{
				String baseId = baseIds[i];

				BaseBalanceBean each = new BaseBalanceBean();

				each.setBaseId(baseId);
				each.setAmount(MathTools.parseInt(amounts[i]));
				each.setOutId(outId);
				each.setSailPrice(MathTools.parseDouble(prices[i]));

				total += each.getAmount() * each.getSailPrice();

				baseBalanceList.add(each);
			}
		}

		bean.setTotal(total);

		if (bean.getType() == OutConstant.OUTBALANCE_TYPE_SAILBACK)
		{
			String id = request.getParameter("id");

			OutBalanceBean balanceBean = outBalanceDAO.find(id);

			if (null == balanceBean)
			{
				throw new MYException("数据错误,请确认操作");
			}

			bean.setRefOutBalanceId(id);
		}

		bean.setBaseBalanceList(baseBalanceList);
	}

	/**
	 * 委托批量退货
	 * @param list
	 * @param type
	 * @throws MYException
	 */
	protected void setOutBalanceBean(List<BatchBackWrap> list,
			int type, HttpServletRequest request, OutBalanceBean bean) throws MYException
	{
		User user = (User) request.getSession().getAttribute("user");

		String description = request.getParameter("description");

		String outId = list.get(0).getRefOutFullId();

		OutBean out = outDAO.find(outId);

		if (out == null)
		{
			throw new MYException("数据错误,请确认操作");
		}

		bean.setCustomerId(out.getCustomerId());

		String dirDepot = request.getParameter("dirDeport");

		bean.setDescription(description);

		bean.setLogTime(TimeTools.now());

		bean.setStafferId(out.getStafferId());

		bean.setOutId(outId);

		bean.setDirDepot(dirDepot);

		bean.setStatus(OutConstant.OUTBALANCE_STATUS_SUBMIT);

		bean.setType(type);

		List<BaseBalanceBean> baseBalanceList = new ArrayList<BaseBalanceBean>();

		double total = 0.0d;

		for (BatchBackWrap eachbb : list)
		{
			String baseId = eachbb.getBaseId();			

			BaseBean baseBean = baseDAO.find(baseId);
			
			BaseBalanceBean each = new BaseBalanceBean();

			each.setBaseId(baseId);
			each.setAmount(eachbb.getAmount());
			each.setOutId(outId);
			
			if (type == OutConstant.OUTBALANCE_TYPE_BACK)
				each.setSailPrice(baseBean.getPrice());
			else
			{
				BaseBalanceBean oribbb = baseBalanceDAO.findByUnique(baseId, eachbb.getRefId());
				
				if (oribbb != null){
					each.setSailPrice(oribbb.getSailPrice());
				}else{
					throw new MYException("数据错误,委托结算退货原结算[%s]单不存", eachbb.getRefId());
				}
				
			}

			total += each.getAmount() * each.getSailPrice();

			baseBalanceList.add(each);
		}

		bean.setTotal(total);

		if (bean.getType() == OutConstant.OUTBALANCE_TYPE_SAILBACK)
		{
			bean.setRefOutBalanceId(list.get(0).getRefId());
		}

		bean.setBaseBalanceList(baseBalanceList);
	}
	
	/**
	 * 
	 * @param list
	 * @param request
	 * @param bean
	 * @throws MYException
	 */
	protected void setOutBean(List<BatchBackWrap> list,
			HttpServletRequest request, OutBean bean) throws MYException
	{
		User user = (User) request.getSession().getAttribute("user");

		String description = request.getParameter("description");

		String dirDepot = request.getParameter("dirDeport");

        String transportNo = request.getParameter("transportNo");
		
		DepotpartBean okDepotpart = depotpartDAO
		.findDefaultOKDepotpart(dirDepot);

		if (okDepotpart == null)
		{
			throw new MYException("仓库下没有良品仓");
		}
		
		String outId = list.get(0).getRefOutFullId();

		OutBean oriOut = outDAO.find(outId);

		if (oriOut == null)
		{
			throw new MYException("数据错误,请确认操作");
		}
		
		bean.setStatus(OutConstant.STATUS_SAVE);

		bean.setType(OutConstant.OUT_TYPE_INBILL);

		bean.setOutTime(TimeTools.now_short());

		bean.setDepartment(oriOut.getDepartment());

		bean.setCustomerId(oriOut.getCustomerId());

		bean.setCustomerName(oriOut.getCustomerName());
		
		bean.setStafferName(oriOut.getStafferName());

		bean.setStafferId(oriOut.getStafferId());

        bean.setTransportNo(transportNo);

		StafferVSCustomerVO vsCus = stafferVSCustomerDAO.findVOByUnique(oriOut.getCustomerId());
		
		if (null != vsCus){
			
			if (!vsCus.getStafferId().equals(oriOut.getStafferId()))
			{
				bean.setStafferId(vsCus.getStafferId());
				bean.setStafferName(vsCus.getStafferName());
			}
		}
		
		bean.setDutyId(oriOut.getDutyId());

		bean.setInvoiceId(oriOut.getInvoiceId());

		// 所在区域
		bean.setLocationId(user.getLocationId());

		// 目的仓库
		bean.setLocation(dirDepot);

		bean.setInway(OutConstant.IN_WAY_NO);

		bean.setOutType(OutConstant.OUTTYPE_IN_OUTBACK);

		bean.setRefOutFullId(outId);

		bean.setDescription("销售退库,销售单号:" + outId + ". " + description);

		//#629
		bean.setVirtualStatus(oriOut.getVirtualStatus());

		bean.setOperator(user.getStafferId());
		bean.setOperatorName(user.getStafferName());
		
		double total = 0.0d;
		
		List<BaseBean> newBaseList = new ArrayList<BaseBean>();
		
		// 行项目
		for (BatchBackWrap each : list)
		{
			BaseBean oribase = baseDAO.find(each.getBaseId());
			
			// 增加base
			BaseBean baseBean = new BaseBean();

			// 卖出价 * 数量
			baseBean.setLocationId(bean.getLocation());
			baseBean.setAmount(each.getAmount());
			baseBean.setProductName(oribase.getProductName());
			baseBean.setUnit("套");
			baseBean.setShowId(oribase.getShowId());
			baseBean.setProductId(oribase.getProductId());

			baseBean.setPrice(oribase.getPrice());
			baseBean.setCostPrice(oribase.getCostPrice());
			baseBean.setPprice(oribase.getPprice());
			baseBean.setIprice(oribase.getIprice());
			baseBean.setInputPrice(oribase.getInputPrice());
			baseBean.setCostPriceKey(StorageRelationHelper
					.getPriceKey(oribase.getCostPrice()));
			//#545
			baseBean.setVirtualPrice(oribase.getVirtualPrice());
			baseBean.setVirtualPriceKey(StorageRelationHelper
					.getPriceKey(oribase.getVirtualPrice()));

			baseBean.setOwner(oribase.getOwner());
			baseBean.setOwnerName(oribase.getOwnerName());

			if (oriOut.getLocation().equals(bean.getLocation()))
			{
				baseBean.setDepotpartId(oribase.getDepotpartId());
			}
			else
			{
				baseBean.setDepotpartId(okDepotpart.getId());
			}

			if (oriOut.getLocation().equals(bean.getLocation()))
			{
				baseBean.setDepotpartName(oribase.getDepotpartName());
			}
			else
			{
				baseBean.setDepotpartName(okDepotpart.getName());
			}

			baseBean.setValue(oribase.getPrice() * each.getAmount());

			if (StringTools.isNullOrNone(oribase.getDepotpartId()))
			{
				throw new MYException("可能是四月之前的单据,没有仓区所以不能退库");
			}

			// 成本
			baseBean.setDescription(String.valueOf(oribase
					.getCostPrice()));

			newBaseList.add(baseBean);

			total += baseBean.getValue();
		}
		
		bean.setTotal(total);
		
		bean.setBaseList(newBaseList);
	}
	
	/**
	 * 
	 * @param list
	 * @param request
	 * @param bean
	 * @throws MYException
	 */
	protected void setOutSwatchBean(List<BatchBackWrap> list,
			HttpServletRequest request, OutBean bean) throws MYException
	{
		User user = (User) request.getSession().getAttribute("user");

		String description = request.getParameter("description");

		String dirDepot = request.getParameter("dirDeport");
		
		DepotpartBean okDepotpart = depotpartDAO.findDefaultOKDepotpart(dirDepot);

		if (okDepotpart == null)
		{
			throw new MYException("仓库下没有良品仓");
		}
		
		String outId = list.get(0).getRefOutFullId();

		OutBean oriOut = outDAO.find(outId);

		if (oriOut == null)
		{
			throw new MYException("数据错误,请确认操作");
		}
		
		bean.setStatus(OutConstant.STATUS_SAVE);

		bean.setStafferName(oriOut.getStafferName());

		bean.setStafferId(oriOut.getStafferId());

		bean.setType(OutConstant.OUT_TYPE_INBILL);

		bean.setOutTime(TimeTools.now_short());

		bean.setDepartment("采购部");

		bean.setCustomerId(oriOut.getCustomerId());

		bean.setCustomerName(oriOut.getCustomerName());

		bean.setDutyId(oriOut.getDutyId());

		bean.setInvoiceId(oriOut.getInvoiceId());

		// 所在区域
		bean.setLocationId(user.getLocationId());

		// 目的仓库
		bean.setLocation(dirDepot);
		
		bean.setDestinationId(oriOut.getLocation());

		bean.setInway(OutConstant.IN_WAY_NO);

		bean.setOutType(OutConstant.OUTTYPE_IN_SWATCH);

		bean.setRefOutFullId(outId);
		
		bean.setDescription("(批量)个人领样退库,领样单号:" + outId + ". " + description);

		//#629
		bean.setVirtualStatus(oriOut.getVirtualStatus());

		bean.setOperator(user.getStafferId());
		bean.setOperatorName(user.getStafferName());
		
		double total = 0.0d;
		
		List<BaseBean> newBaseList = new ArrayList<BaseBean>();
		
		// 行项目
		for (BatchBackWrap each : list)
		{
			BaseBean oribase = baseDAO.find(each.getBaseId());
			
			// 增加base
			BaseBean baseBean = new BaseBean();

			// 卖出价 * 数量
			baseBean.setLocationId(bean.getLocation());
			baseBean.setAmount(each.getAmount());
			baseBean.setProductName(oribase.getProductName());
			baseBean.setUnit("套");
			baseBean.setShowId(oribase.getShowId());
			baseBean.setProductId(oribase.getProductId());

			baseBean.setPrice(oribase.getPrice());
			baseBean.setCostPrice(oribase.getCostPrice());
			baseBean.setPprice(oribase.getPprice());
			baseBean.setIprice(oribase.getIprice());
			baseBean.setInputPrice(oribase.getInputPrice());
			baseBean.setCostPriceKey(StorageRelationHelper
					.getPriceKey(oribase.getCostPrice()));

			//#545
			baseBean.setVirtualPrice(oribase.getVirtualPrice());
			baseBean.setVirtualPriceKey(StorageRelationHelper
					.getPriceKey(oribase.getVirtualPrice()));

			baseBean.setOwner(oribase.getOwner());
			baseBean.setOwnerName(oribase.getOwnerName());

			if (oriOut.getLocation().equals(bean.getLocation()))
			{
				baseBean.setDepotpartId(oribase.getDepotpartId());
			}
			else
			{
				baseBean.setDepotpartId(okDepotpart.getId());
			}

			if (oriOut.getLocation().equals(bean.getLocation()))
			{
				baseBean.setDepotpartName(oribase.getDepotpartName());
			}
			else
			{
				baseBean.setDepotpartName(okDepotpart.getName());
			}

			baseBean.setValue(oribase.getPrice() * each.getAmount());

			if (StringTools.isNullOrNone(oribase.getDepotpartId()))
			{
				throw new MYException("可能是四月之前的单据,没有仓区所以不能退库");
			}

			// 成本
			baseBean.setDescription(String.valueOf(oribase
					.getCostPrice()));

			newBaseList.add(baseBean);

			total += baseBean.getValue();
		}
		
		bean.setTotal(total);
		
		bean.setBaseList(newBaseList);
	}
	
	/**
	 * 增加(保存修改)修改库单(包括销售单和入库单)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		// 是否锁定库存
		if (storageRelationManager.isStorageRelationLock())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库存被锁定,不能开单");

			return mapping.findForward("error");
		}

        _logger.info("addOut 111111111111111");
		CommonTools.saveParamers(request);

		User user = (User) request.getSession().getAttribute("user");

		String locationId = Helper.getCurrentLocationId(request);

		String location = request.getParameter("location");

		String locationShadow = request.getParameter("locationShadow");

		String saves = request.getParameter("saves");

		String step = request.getParameter("step");

		String hasProm = request.getParameter("hasProm");

		String oprType = request.getParameter("oprType");

		if (StringTools.isNullOrNone(oprType)) oprType = "";
		
		int buyReturnFlag = 0;
		if("1".equals(request.getParameter("buyReturnFlag"))){
			buyReturnFlag = 1;
		}

		// 客户信用级别
		String customercreditlevel = request
				.getParameter("customercreditlevel");

		String fullId = request.getParameter("fullId");

		String update = request.getParameter("update");

		if ("save".equals(saves))
		{
			saves = "保存";
		}
		else
		{
			saves = "提交";
		}
        _logger.info("addOut 22222222222222222222222");
		ParamterMap map = new ParamterMap(request);

		ActionForward action = null;

		OutBean outBean = null;

		// 第一销售页面
		outBean = new OutBean();

		outBean.setLocationId(locationId);

		// 增加职员的ID
		outBean.setStafferId(user.getStafferId());
		outBean.setStafferName(user.getStafferName());

		BeanUtil.getBean(outBean, request);

		outBean.setLocation(location);
		
		outBean.setBuyReturnFlag(buyReturnFlag);

		if (StringTools.isNullOrNone(outBean.getLocation())
				&& !oprType.equals("0"))
		{
			outBean.setLocation(locationShadow);

			// for monitor
			_logger.info("Debug Location ...." + location + ", fullid="
					+ fullId);
			_logger.info("Debug LocationShadow ...." + locationShadow);
		}
        _logger.info("addOut 33333333333333333333333");
		outBean.setLogTime(TimeTools.now());

		if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
				&& outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT)
		{
			if (StringTools.isNullOrNone(outBean.getDestinationId()))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						"调拨没有目的仓库属性,请重新操作");

				return mapping.findForward("error");
			}

			outBean.setReserve1(OutConstant.MOVEOUT_OUT);
		}

		if (StringTools.isNullOrNone(outBean.getLocation())
				&& !oprType.equals("0"))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有库存属性,请重新操作");

			return mapping.findForward("error");
		}

		// 商务 - begin
		ActionForward error = checkAuthForEcommerce(request, user, mapping);

		if (null != error)
		{
			return error;
		}

		User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

		String elogin = (String) request.getSession().getAttribute("g_elogin");

		String g_loginType = (String) request.getSession().getAttribute(
				"g_loginType");

		if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

			return mapping.findForward("error");
		}
        _logger.info("addOut 44444444444444444444444");
		// 当前切换用户登陆的且为商务登陆的，记录经办人
		if (!StringTools.isNullOrNone(elogin) && null != g_srcUser
				&& g_loginType.equals("1"))
		{
			outBean.setOperator(g_srcUser.getStafferId());
			outBean.setOperatorName(g_srcUser.getStafferName());
		}
		else
		{
			outBean.setOperator(user.getStafferId());
			outBean.setOperatorName(user.getStafferName());
		}
		// 商务 - end
        _logger.info("addOut 5555555555555555555");
		// 销售单
		if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
		{
			// 只在销售的第一个页面时用到

			// 强制设置成OUT_SAIL_TYPE_MONEY
			if (CustomerConstant.BLACK_LEVEL.equals(customercreditlevel))
			{
				outBean.setReserve3(OutConstant.OUT_SAIL_TYPE_MONEY);
			}

			// 业务员黑名单
			StafferBean sb = stafferDAO.find(user.getStafferId());

			if (sb != null && sb.getBlack() == StafferConstant.BLACK_YES)
			{
				outBean.setReserve3(OutConstant.OUT_SAIL_TYPE_MONEY);
			}
			
			if (outBean.getReserve3() != OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER)
			{
				outBean.setGuarantor("");
			}

			// 销售属性的设置
			if (OATools.getManagerFlag())
			{

				InvoiceBean invoiceBean = invoiceDAO.find(outBean
						.getInvoiceId());

				if (invoiceBean == null)

				outBean.setRatio("0");

				else
				{

					int ratio = (int) (invoiceBean.getVal() * 10);

					outBean.setRatio(String.valueOf(ratio));
				}
			}
            _logger.info("addOut 6666666666666666666666666666");
			action = processCommonOut(mapping, form, request, reponse, user,
					saves, fullId, outBean, map, "1");

		}
		else
		{
			if (outBean.getOutType() != OutConstant.OUTTYPE_IN_OTHER
					&& outBean.getOutType() != OutConstant.OUTTYPE_IN_OUTBACK)
			{
				outBean.setRefOutFullId("");

				if (outBean.getOutType() != OutConstant.OUTTYPE_IN_DROP)
				{
					outBean.setForceBuyType(-1);
					outBean.setReserve9("");
				}
			}

			// 其它入库，领样对冲单不经过这儿
			if (outBean.getOutType() == OutConstant.OUTTYPE_IN_OTHER)
			{
				if (outBean.getForceBuyType() == -1)
				{
					request.setAttribute(KeyConstant.ERROR_MESSAGE,
							"其它入库没有选择事由");

					return mapping.findForward("error");
				}

				String customerName1 = request.getParameter("customerName1");
				if (!StringTools.isNullOrNone(customerName1))
				{
					outBean.setCustomerName(customerName1);
				}
			}

			// 默认很多属性
			outBean.setStafferId(user.getStafferId());
			outBean.setStafferName(user.getStafferName());

			if (StringTools.isNullOrNone(outBean.getCustomerId()))
			{
				outBean.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);
				outBean.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);
			}

			outBean.setDepartment("公共部门");
			outBean.setArriveDate(TimeTools.now_short(10));
            _logger.info("addOut 77777777777777777777");
			// 入库单的处理
			try
			{
                if (outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT)
                {
                    this.fillDistributionForRemoteAllocate(request, outBean);
                }
                
                //mod by zhangxian 2019-10-11
                //创建报废单和采购退货单时，目的库 置为空
                if (outBean.getOutType() == OutConstant.OUTTYPE_IN_DROP || outBean.getOutType() == OutConstant.OUTTYPE_IN_STOCK)
                {
                	outBean.setDestinationId(null);
                }
                //end mod 2019-10-11
                
                //mod by zhangxian 2019-06-18
                //增加预占库存的扣减
				//#718 回滚
                /*String productIdString = request.getParameter("idsList");
                String amountString = request.getParameter("amontList");
                String[] idsList = new String[] {};
                String[] amountList = new String[] {};
                if(org.apache.commons.lang.StringUtils.isNotEmpty(productIdString))
                {
                	idsList = org.apache.commons.lang.StringUtils.split(productIdString, "~");
                }
                if(org.apache.commons.lang.StringUtils.isNotEmpty(amountString))
                {
                	amountList = org.apache.commons.lang.StringUtils.split(amountString, "~");
                }
                String locationid = request.getParameter("location");
                for(int i=0;i<idsList.length;i++){
                    //TODO 该产品全部库存-在途库存-预占>=合成数量，就可以正常合成
                	String productId = idsList[i];
                	String amount = amountList[i];
                    ConditionParse conditionParseRelation = new ConditionParse();
                    conditionParseRelation.addWhereStr();
                    conditionParseRelation.addCondition("productid","=", productId);
                    conditionParseRelation.addCondition("locationid","=", locationid);
                    List<StorageRelationBean> relationList = storageRelationDAO.queryEntityBeansByCondition(conditionParseRelation);
                    for(StorageRelationBean relation:relationList) {
                        int preassign = storageRelationManager.sumPreassignByStorageRelation(relation);
                        //在途数量
                        ConditionParse conditionOut = new ConditionParse();
                        conditionOut.addWhereStr();
                        conditionOut.addCondition("OutBean.type", "=", "1");
                        conditionOut.addCondition("and OutBean.status in (7,8)");
                        conditionOut.addCondition("OutBean.inway", "=", "1");
                        conditionOut.addCondition("OutBean.location","=",locationid);
                        int zt = outDAO.countVOByCondition(conditionOut.toString());
                        _logger.info("relation.getAmount()***"+relation.getAmount()+"***zt**"+zt + "***preassign**:" + preassign);
                        if((relation.getAmount() - zt - preassign) < Math.abs(Integer.valueOf(amount))){
                        	ProductBean productBean = this.productDAO.find(productId);
                            throw new MYException(String.format("入库出错,库存不足,,产品:%s",productBean.getName()));
                        }
                    }
                }*/
                //end mod
				String id = outManager.addOut(outBean, map.getParameterMap(), user);
                _logger.info("addOut 88888888888888888888*********"+id);
				if ("提交".equals(saves))
				{
					int ttype = StorageConstant.OPR_STORAGE_INOTHER;

					if (outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT)
					{
						ttype = StorageConstant.OPR_STORAGE_REDEPLOY;
					}
					
					// if id start with 'TM', then split SO, and then submit each
					if (id.startsWith("TM"))
					{
						// split out, then delete original out in the same
						// transaction
						String[] ids = outManager.splitOut(id);

						for (String eachId : ids)
						{
							_logger.info("入库拆单(共拆成" + ids.length + "张)：原单" + id
									+ ", 新单：" + eachId);
                            _logger.info("addOut 999999999999999999999999*********"+eachId);
							outManager.submit(eachId, user, ttype);
						}
					}else {
                        _logger.info("addOut aaaaaaaaaaaaaaaa*********");
						outManager.submit(id, user, ttype);
					}
				}
			}
			catch (MYException e)
			{
                e.printStackTrace();
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						"处理错误:" + e.getErrorContent());

				return mapping.findForward("error");
			}
			catch (Exception e)
			{
                e.printStackTrace();
				_logger.error(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

				return mapping.findForward("error");
			}
		}

		if (action != null)
		{
			return action;
		}

		CommonTools.removeParamers(request);

		// 第一步做完转到第二步页面
		if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
				&& step.equals("1"))
		{
			DistributionBean distributionBean = null;

			List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(outBean.getFullId());
			
			if (!ListTools.isEmptyOrNull(distList))
			{
				distributionBean = distList.get(0);
			}
			
			List<BaseVO> baseList = baseDAO.queryEntityVOsByFK(outBean.getFullId());

			// 按仓库排序
			Collections.sort(baseList, new Comparator<BaseVO>()
			{
				public int compare(BaseVO o1, BaseVO o2)
				{
					return Integer.parseInt(o1.getLocationId().substring(11))
							- Integer
									.parseInt(o2.getLocationId().substring(11));
				}
			});

			judgeCanPromotion(request, baseList);

			request.setAttribute("distributionBean", distributionBean);

			List<AttachmentBean> attachmentList = attachmentDAO
					.queryEntityBeansByFK(outBean.getFullId());

			String attacmentIds = "";

			for (AttachmentBean attachmentBean : attachmentList)
			{
				attacmentIds = attacmentIds + attachmentBean.getId() + ";";
			}

			request.setAttribute("attacmentIds", attacmentIds);

			request.setAttribute("hasProm", hasProm);

			OutVO outVO = outDAO.findVO(outBean.getFullId());

			if (outVO == null)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

				return mapping.findForward("error");
			}

			outVO.setAttachmentList(attachmentList);

			request.setAttribute("outBean", outVO);

			request.setAttribute("baseList", baseList);

			String desc = outVO.getDescription();

			int idx = desc.indexOf("&&");

			String logDesc = "";
			
			if (idx != -1)
			{
				logDesc = desc.substring(idx + 2, desc.length());
			}

			String swatchLog = outManager.processSwatchCheck(outVO);
			
			request.setAttribute("logDesc", swatchLog);
			
			List<ExpressBean> expressList = expressDAO
					.listEntityBeansByOrder("order by id");

			request.setAttribute("expressList", expressList);

			// 地址区
			
			
			request.setAttribute("update", update);

			//TODO#52
			String depotList = request.getParameter("depotList");
			if (StringTools.isNullOrNone(depotList)){
				request.setAttribute("kf","0");
			}else if (depotList.contains("A1201205251506100751")
					|| depotList.contains("A1201301221008971864")
					|| depotList.contains("A1201310151011526376")){
				request.setAttribute("kf","1");
			} else{
				request.setAttribute("kf","0");
			}
			_logger.info("***depotList***"+depotList);

			return mapping.findForward("addOut51");
		}

		//OutBean checkOut = outDAO.find(outBean.getFullId());

//		request.getSession().setAttribute(
//				KeyConstant.MESSAGE,
//				"此库单的单号是:" + outBean.getFullId() + ".下一步是:"
//						+ OutHelper.getStatus(checkOut.getStatus()));
		
		request.getSession().setAttribute(
				KeyConstant.MESSAGE,
				"库单提交成功");

		CommonTools.removeParamers(request);

		RequestTools.actionInitQuery(request);

		if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
		{
			if (!outBean.getStafferId().equals(outBean.getOperator()))
			{
				outManager.sendOutMail(outBean, "商务开单确认.");
			}

			return querySelfOut(mapping, form, request, reponse);
		}
		else
		{
			return querySelfBuy(mapping, form, request, reponse);
		}
	}

	private void judgeCanPromotion(HttpServletRequest request,
			List<BaseVO> baseList)
	{
		String canProm = "0";

		Map<String, String> map = new HashMap<String, String>();

		for (BaseBean eachItem : baseList)
		{
			String key = eachItem.getLocationId() + "-" + eachItem.getMtype()
					+ "-" + eachItem.getOldGoods();

			if (!map.containsKey(key))
			{
				map.put(key, key);
			}
		}

		if (map.size() > 1)
		{
			canProm = "1";
		}

		request.setAttribute("canProm", canProm);
	}


    /**
     * 2015/10/19 入库-换货
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddBuyExchange(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        _logger.info("*****preForAddBuyExchange************");
        // 增加黑名单-停止销售类型判断
        User user = Helper.getUser(request);

        // 商务模式下权限检查
        ActionForward error = checkAuthForEcommerce(request, user, mapping);

        if (null != error)
        {
            return error;
        }

        // 是否锁定库存
        if (storageRelationManager.isStorageRelationLock())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "库存被锁定,不能开单");

            return mapping.findForward("error");
        }

        // 入库单
        try
        {
            innerForPrepare(request, true, true);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE,
                    e.getErrorContent());

            return mapping.findForward("error");
        }

        //运输方式
        List<ExpressBean> expressList = this.expressDAO.listEntityBeans();
        request.setAttribute("expressList", expressList);

        //省市
        List<ProvinceBean> provinceList = this.provinceDAO.listEntityBeans();
        request.setAttribute("provinceList", provinceList);
        List<CityBean> cityList = this.cityDAO.listEntityBeans();
        request.setAttribute("cityList", cityList);

        return mapping.findForward("addBuyExchange");
    }


    /**
     * 2015/10/17入库-商品调换
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward addBuyExchange(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        // 是否锁定库存
        if (storageRelationManager.isStorageRelationLock())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "库存被锁定,不能开单");

            return mapping.findForward("error");
        }

        CommonTools.saveParamers(request);

        User user = (User) request.getSession().getAttribute("user");

        String locationId = Helper.getCurrentLocationId(request);

        String location = request.getParameter("location");

        String locationShadow = request.getParameter("locationShadow");

        String saves = request.getParameter("saves");

        String oprType = request.getParameter("oprType");

        if (StringTools.isNullOrNone(oprType)) oprType = "";

        String fullId = request.getParameter("fullId");

        if ("save".equals(saves))
        {
            saves = "保存";
        }
        else
        {
            saves = "提交";
        }
        ParamterMap map = new ParamterMap(request);

        ActionForward action = null;

        OutBean outBean = new OutBean();

        outBean.setOutTime(TimeTools.now_short());
        outBean.setOutType(OutConstant.OUTTYPE_IN_EXCHANGE);

        outBean.setLocationId(locationId);
		//#188 TH单默认紧急
		outBean.setEmergency(OutConstant.OUT_EMERGENCY_YES);

        // 增加职员的ID
        outBean.setStafferId(user.getStafferId());
        outBean.setStafferName(user.getStafferName());

        BeanUtil.getBean(outBean, request);

        //TODO location设置为与destination一致，否则查询有问题
        outBean.setLocation(outBean.getDestinationId());

        if (StringTools.isNullOrNone(outBean.getLocation())
                && !oprType.equals("0"))
        {
            outBean.setLocation(locationShadow);

            // for monitor
            _logger.info("Debug Location ...." + location + ", fullid="
                    + fullId);
            _logger.info("Debug LocationShadow ...." + locationShadow);
        }
        _logger.info("addBuyExchange 33333333333333333333333");
        outBean.setLogTime(TimeTools.now());

        if (StringTools.isNullOrNone(outBean.getLocation())
                && !oprType.equals("0"))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有库存属性,请重新操作");

            return mapping.findForward("error");
        }

        // 商务 - begin
        ActionForward error = checkAuthForEcommerce(request, user, mapping);

        if (null != error)
        {
            return error;
        }

        User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

        String elogin = (String) request.getSession().getAttribute("g_elogin");

        String g_loginType = (String) request.getSession().getAttribute(
                "g_loginType");

        if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

            return mapping.findForward("error");
        }
        _logger.info("addBuyExchange 44444444444444444444444");
        // 当前切换用户登陆的且为商务登陆的，记录经办人
        if (!StringTools.isNullOrNone(elogin) && null != g_srcUser
                && g_loginType.equals("1"))
        {
            outBean.setOperator(g_srcUser.getStafferId());
            outBean.setOperatorName(g_srcUser.getStafferName());
        }
        else
        {
            outBean.setOperator(user.getStafferId());
            outBean.setOperatorName(user.getStafferName());
        }
        // 商务 - end
        _logger.info("addBuyExchange 5555555555555555555");
        // 入库单

        if (outBean.getOutType() != OutConstant.OUTTYPE_IN_OTHER
                && outBean.getOutType() != OutConstant.OUTTYPE_IN_OUTBACK)
        {
            outBean.setRefOutFullId("");

            if (outBean.getOutType() != OutConstant.OUTTYPE_IN_DROP)
            {
                outBean.setForceBuyType(-1);
                outBean.setReserve9("");
            }
        }

        // 默认很多属性
        outBean.setStafferId(user.getStafferId());
        outBean.setStafferName(user.getStafferName());

        if (StringTools.isNullOrNone(outBean.getCustomerId()))
        {
            outBean.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);
            outBean.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);
        }

        outBean.setDepartment("公共部门");
        outBean.setArriveDate(TimeTools.now_short(10));
        _logger.info("addBuyExchange 77777777777777777777");
        // 入库单的处理
        try
        {
             this.fillDistributionForRemoteAllocate(request, outBean);

            //TODO
            String id = outManager.exchange(outBean, map.getParameterMap(), user, null, null);
            _logger.info("addBuyExchange 88888888888888888888*********"+id);
            if ("提交".equals(saves))
            {
                int ttype = StorageConstant.OPR_STORAGE_INOTHER;

                // if id start with 'TM', then split SO, and then submit each
                if (id.startsWith("TM"))
                {
                    // split out, then delete original out in the same
                    // transaction
                    String[] ids = outManager.splitOut(id);

                    for (String eachId : ids)
                    {
                        _logger.info("入库拆单(共拆成" + ids.length + "张)：原单" + id
                                + ", 新单：" + eachId);
                        _logger.info("addBuyExchange 999999999999999999999999*********"+eachId);
                        outManager.submitExchange(eachId, user, ttype);
                    }
                }else {
                    _logger.info("addBuyExchange aaaaaaaaaaaaaaaa*********");
                    outManager.submitExchange(id, user, ttype);
                }
            }
        }
        catch (MYException e)
        {
            e.printStackTrace();
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE,
                    "处理错误:" + e.getErrorContent());

            return mapping.findForward("error");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

            return mapping.findForward("error");
        }

        if (action != null)
        {
            return action;
        }

        CommonTools.removeParamers(request);

        request.getSession().setAttribute(
                KeyConstant.MESSAGE,
                "库单提交成功");

        CommonTools.removeParamers(request);

        RequestTools.actionInitQuery(request);

        return querySelfBuy(mapping, form, request, reponse);
    }


	/**
	 * 增加(保存修改)修改库单(包括销售单和入库单)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward diaoBo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		// 是否锁定库存
		if (storageRelationManager.isStorageRelationLock())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库存被锁定,不能开单");

			return mapping.findForward("error");
		}

		User user = (User) request.getSession().getAttribute("user");

		StafferBean sb = stafferDAO.find(user.getStafferId());

		String locationId = Helper.getCurrentLocationId(request);

		String destinationId = request.getParameter("destinationId");

		String dutyId = request.getParameter("dutyId");

		String description = request.getParameter("description");

		String saves = request.getParameter("saves");

		String fullId = request.getParameter("fullId");

		ActionForward action = null;

		String s_diaoBoproId[] = request.getParameterValues("s_diaoBoproId");
		String amount[] = request.getParameterValues("amount");

		saves = request.getParameter("saves");

		ParamterMap map = new ParamterMap(request);
		OutBean outBean = null;

		outBean = new OutBean();

		outBean.setLocationId(locationId);
		outBean.setDestinationId(destinationId);
		outBean.setDutyId(dutyId);
		outBean.setDescription(description);
		outBean.setType(OutConstant.OUT_TYPE_INBILL);
		outBean.setOutType(OutConstant.OUTTYPE_OUT_APPLY);
		outBean.setIndustryId(sb.getIndustryId());
		outBean.setOutTime(TimeTools.now());
		outBean.setLogTime(TimeTools.now());
		// 增加职员的ID
		outBean.setStafferId(user.getStafferId());
		outBean.setStafferName(sb.getName());

		BeanUtil.getBean(outBean, request);

		if (StringTools.isNullOrNone(outBean.getDestinationId()))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "调拨没有目的仓库属性,请重新操作");

			return mapping.findForward("error");
		}

		outBean.setReserve1(OutConstant.MOVEOUT_DIAOBO);

		if (StringTools.isNullOrNone(outBean.getLocation()))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有库存属性,请重新操作");

			return mapping.findForward("error");
		}

		// 入库单的处理
		try
		{
			if ("save".equals(saves))
			{
				saves = "保存";
				outBean.setStatus(OutConstant.STATUS_SAVE);
			}
			else
			{
				saves = "提交";
				outBean.setStatus(OutConstant.STATUS_LOCATION_MANAGER_CHECK);
			}
			
			//mod by zhangxian 2019-06-18
            //增加预占库存的扣减
            String productIdString = request.getParameter("idsList");
            String amountString = request.getParameter("amontList");
            String[] idsList = new String[] {};
            String[] amountList = new String[] {};
            if(org.apache.commons.lang.StringUtils.isNotEmpty(productIdString))
            {
            	idsList = org.apache.commons.lang.StringUtils.split(productIdString, "~");
            }
            if(org.apache.commons.lang.StringUtils.isNotEmpty(amountString))
            {
            	amountList = org.apache.commons.lang.StringUtils.split(amountString, "~");
            }
            String locationid = request.getParameter("location");
            for(int i=0;i<idsList.length;i++){
                //TODO 该产品全部库存-在途库存-预占>=合成数量，就可以正常合成
            	String productId = idsList[i];
            	String amountValue = amountList[i];
                ConditionParse conditionParseRelation = new ConditionParse();
                conditionParseRelation.addWhereStr();
                conditionParseRelation.addCondition("productid","=", productId);
                conditionParseRelation.addCondition("locationid","=", locationid);
                List<StorageRelationBean> relationList = storageRelationDAO.queryEntityBeansByCondition(conditionParseRelation);
                for(StorageRelationBean relation:relationList) {
                    int preassign = storageRelationManager.sumPreassignByStorageRelation(relation);
                    //在途数量
                    ConditionParse conditionOut = new ConditionParse();
                    conditionOut.addWhereStr();
                    conditionOut.addCondition("OutBean.type", "=", "1");
                    conditionOut.addCondition("and OutBean.status in (7,8)");
                    conditionOut.addCondition("OutBean.inway", "=", "1");
                    conditionOut.addCondition("OutBean.location","=",locationid);
                    int zt = outDAO.countVOByCondition(conditionOut.toString());
                    _logger.info("relation.getAmount()***"+relation.getAmount()+"***zt**"+zt + "***preassign**:" + preassign);
                    if((relation.getAmount() - zt - preassign) < Math.abs(Integer.valueOf(amountValue))){
                    	ProductBean productBean = this.productDAO.find(productId);
                        throw new MYException(String.format("入库出错,库存不足,产品:%s",productBean.getName()));
                    }
                }
            }
            //end mod
			

			outManager.diaoBo(outBean, map.getParameterMap(), user,
					s_diaoBoproId, amount);
			//
			if ("提交".equals(saves))
			{
				int ttype = StorageConstant.OPR_STORAGE_REDEPLOY;
				outManager.submitDiaoBo(outBean.getFullId(), user, ttype);
			}
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE,
					"处理错误:" + e.getErrorContent());

			return mapping.findForward("error");
		}
		catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

			return mapping.findForward("error");
		}

		CommonTools.removeParamers(request);

		OutBean checkOut = outDAO.find(outBean.getFullId());

		request.getSession().setAttribute(
				KeyConstant.MESSAGE,
				"此库单的单号是:" + outBean.getFullId() + ".下一步是:"
						+ OutHelper.getStatus(checkOut.getStatus()));

		CommonTools.removeParamers(request);

		RequestTools.actionInitQuery(request);

		return querySelfBuy(mapping, form, request, reponse);
	}

	/**
	 * @return the flowAtt
	 */
	public String getAttachmentPath()
	{
		return ConfigLoader.getProperty("outAttachmentPath");
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
	public ActionForward downAttachmentFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
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

		response.setHeader(
				"Content-Disposition",
				"attachment; filename="
						+ StringTools.getStringBySet(bean.getName(), "GBK",
								"ISO8859-1"));

		UtilStream us = new UtilStream(new FileInputStream(file), out);

		us.copyAndCloseStream();

		return null;
	}

	/**
	 * 配送页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addOutStep2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		// 是否锁定库存
		if (storageRelationManager.isStorageRelationLock())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库存被锁定,不能开单");

			return mapping.findForward("error");
		}

		User user = (User) request.getSession().getAttribute("user");

		String fullId = request.getParameter("fullId");

		String saves = request.getParameter("saves");

		if ("save".equals(saves))
		{
			saves = "保存";
		}
		else
		{
			saves = "提交";
		}

		ActionForward action = null;
        OutBean outBean = outDAO.find(fullId);
        if (null == outBean)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在,请重新操作");

			return mapping.findForward("error");
		}

		fillDistribution(request, outBean);
		// 商务 - begin
		ActionForward error = checkAuthForEcommerce(request, user, mapping);
		if (null != error)
		{
			return error;
		}

		User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

		String elogin = (String) request.getSession().getAttribute("g_elogin");

		if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "登陆异常,请重新登陆");

			return mapping.findForward("error");
		}

		// 商务 - end
		// 销售单
		action = processCommonOut(mapping, form, request, reponse, user, saves,
				fullId, outBean, null, "2");
        _logger.info("addOutStep2***"+outBean);
		if (action != null)
		{
			return action;
		}

		if (!outBean.getFullId().startsWith("TM"))
		{
			OutBean checkOut = outDAO.find(outBean.getFullId());

			request.getSession().setAttribute(
					KeyConstant.MESSAGE,
					"此库单的单号是:" + outBean.getFullId() + ".下一步是:"
							+ OutHelper.getStatus(checkOut.getStatus()));
		}

		CommonTools.removeParamers(request);

		RequestTools.actionInitQuery(request);

		if (!outBean.getStafferId().equals(outBean.getOperator()))
		{
            System.out.println("addOutStep2 sendOutMailsendOutMailsendOutMailsendOutMail");
			//outManager.sendOutMail(outBean, "商务开单确认.");
		}

		return querySelfOut(mapping, form, request, reponse);
	}

	/**
	 * 
	 * @param rds
	 * @param out
	 */
	private void fillDistribution(HttpServletRequest rds, OutBean out)
	{
		// 促销
		String eventId = rds.getParameter("eventId");

		String refBindOutId = rds.getParameter("refBindOutId");

		double promValue = MathTools.parseDouble(rds.getParameter("promValue"));

		out.setEventId(eventId);
		out.setRefBindOutId(refBindOutId);
		out.setPromValue(promValue);

		// 计划到货时间
		String arriveDate = TimeTools.now_short(1); // rds.getParameter("arriveDate");

		out.setArriveDate(arriveDate);

		DistributionBean distributionBean = new DistributionBean();

		distributionBean.setOutId(out.getFullId());

		out.setDistributeBean(distributionBean);

		BeanUtil.getBean(distributionBean, rds);

		int rshipping = MathTools.parseInt(rds.getParameter("rshipping"));

		distributionBean.setShipping(rshipping);

		// 获取子产品，nature = 0
		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(out.getFullId());

		out.setBaseList(baseList);

		String[] baseIds = rds.getParameterValues("baseId");

		String[] deliverTypes = rds.getParameterValues("deliverType");

		for (BaseBean eachBase : baseList)
		{
			for (int i = 0; i < baseIds.length; i++)
			{
				if (baseIds[i].equals(eachBase.getId()))
				{
					int deliverType = 0;

					if (StringTools.isNullOrNone(deliverTypes[i]))
					{
						deliverType = -1;
					}
					else
					{
						deliverType = MathTools.parseInt(deliverTypes[i]);
					}

					eachBase.setDeliverType(deliverType);
				}
			}
		}
	}

    /**
     * 2015/8/6 入库调拨生成配送单
	 * 2015/10/20 入库换货也需要生成配送单
     * @param rds
     * @param out
     */
    private void fillDistributionForRemoteAllocate(HttpServletRequest rds, OutBean out)
    {
        DistributionBean distributionBean = new DistributionBean();

        distributionBean.setOutId(out.getFullId());

        out.setDistributeBean(distributionBean);

        BeanUtil.getBean(distributionBean, rds);
        _logger.info(out+" fillDistributionForRemoteAllocate*****"+distributionBean);

    }

	/**
	 * 处理销售单的逻辑
	 * 
	 * @param mapping
	 * @param request
	 * @param user
	 * @param saves
	 * @param fullId
	 * @param outBean
	 * @param map
	 */
	protected ActionForward processCommonOut(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse reponse, User user, String saves,
			String fullId, OutBean outBean, ParamterMap map, String step)
	{
        _logger.info(outBean+"***processCommonOut:****step***" + step+"***fullId***"+fullId);
		// 增加库单
		if (!StringTools.isNullOrNone(fullId))
		{
			// 修改
			OutBean out = outDAO.find(fullId);

			if (out == null)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在");

				return mapping.findForward("error");
			} else{
				//#206
				if (!StringTools.isNullOrNone(out.getPodate())){
					outBean.setPodate(out.getPodate());
				}
			}
		}

		String id = "";

		try
		{
			// 主销售界面
			if (step.equals("1"))
			{
				id = outManager.addOut(outBean, map.getParameterMap(), user);
			}
			else
			// 配送信息界面
			{
				id = outManager.addOutStep2(outBean, user);
			}
            _logger.info("step:"+step+" id******************"+id);

			// 提交
			if (OutConstant.FLOW_DECISION_SUBMIT.equals(saves)
					&& step.equals("2"))
			{
				// if id start with 'TM', then split SO, and then submit each
				if (id.startsWith("TM"))
				{
					// split out, then delete original out in the same
					// transaction
					String[] ids = outManager.splitOut(id);

					for (String eachId : ids)
					{
						_logger.info("销售拆单(共拆成" + ids.length + "张)：原单" + id
								+ ", 新单：" + eachId);

						outManager.submit(eachId, user,
								StorageConstant.OPR_STORAGE_OUTBILL);

						checkSubmit(user, eachId);
					}
				}
				else
				{
					outManager.submit(id, user,
							StorageConstant.OPR_STORAGE_OUTBILL);

					checkSubmit(user, id);
				}
			}
		}
		catch (MYException e)
		{
            e.printStackTrace();
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

			return mapping.findForward("error");
		}

		// 增加提交前就对库存、信用检查
		boolean exception = false;

		String message = "";

		try
		{
			if (step.equals("1"))
			{
				outManager.preCheckBeforeSubmit(id);
			}
		}
		catch (MYException e)
		{
			_logger.warn(e, e);

			exception = true;

			message = e.getErrorContent();
		}

		if (exception)
		{
			CommonTools.removeParamers(request);

			request.setAttribute("fow", "1");

			request.setAttribute("outId", id);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, message);

			try
			{
				return this.findOutForUpdate(mapping, form, request, reponse);
			}
			catch (ServletException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请确认");

				return mapping.findForward("error");
			}
		}

		return null;
	}

	/**
	 * 第一页面提交时，有异常，修改的方式返回主界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findOutForUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse reponse) throws ServletException
	{
		String outId = RequestTools.getValueFromRequest(request, "outId");

		String fow = RequestTools.getValueFromRequest(request, "fow");

		CommonTools.saveParamers(request);

		String goback = request.getParameter("goback");

		if (StringTools.isNullOrNone(goback))
		{
			goback = "1";
		}

		request.setAttribute("goback", goback);

		OutVO bean = null;
		try
		{
			bean = outDAO.findVO(outId);

			if (bean == null)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在,请重新操作");

				return mapping.findForward("error");
			}

			List<BaseBean> list = baseDAO.queryEntityBeansByFK(outId);

			if (ListTools.isEmptyOrNull(list))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在子项,请重新操作");

				return mapping.findForward("error");
			}

			// 行项目中没有产品类型
			if ("1".equals(fow))
			{
				if (bean.getType() == OutConstant.OUT_TYPE_OUTBILL)
				{
					for (BaseBean each : list)
					{
						ProductBean productBean = productDAO.find(each
								.getProductId());

						if (null != productBean)
						{
							each.setProductType(productBean.getType());
						}
					}
				}
			}

			bean.setBaseList(list);

			String desc1 = bean.getDescription();

			int idx = desc1.indexOf("&&");

			if (idx != -1)
			{
				String newDesc = desc1.substring(0, idx);

				bean.setDescription(newDesc);
			}

			request.setAttribute("bean", bean);

			request.setAttribute("fristBase", list.get(0));

			if (list.size() > 1)
			{
				request.setAttribute("lastBaseList",
						list.subList(1, list.size()));
			}

			PrincipalshipBean shiye = principalshipDAO.find(bean
					.getIndustryId());

			request.setAttribute("shiye", shiye);

			List<ShowBean> showList = showDAO.listEntityBeans();

			request.setAttribute("showList", showList);
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询库单失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.setAttribute("flag", "0");

		boolean deepQuery = true;

		try
		{
			innerForPrepare(request, false, deepQuery);
		}
		catch (MYException e)
		{
			_logger.warn(e, e);
		}

		// 修改单据
		if ("1".equals(fow))
		{
			if (!OutHelper.canDelete(bean))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "此状态不能修改单据");

				return mapping.findForward("error");
			}

			setHasProm(request, Helper.getStaffer(request));

			if (OATools.isChangeToV5())
			{
				if (outId.startsWith("TM"))
				{
					return mapping.findForward("updateOut501");
				}
				else
				{
					return mapping.findForward("updateOut50");
				}
			}

			return mapping.findForward("updateOut4_bak");
		}
		else
			return null;

	}

	/**
	 * checkSubmit
	 * 
	 * @param user
	 * @param id
	 */
	protected void checkSubmit(User user, String id)
	{
		OutBean newOut = outDAO.findRealOut(id);

		if (newOut == null)
		{
			loggerError(id + " is errro in checkSubmit");

			return;
		}

		if (newOut.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
		{
			importLog.info(id + ":" + user.getStafferName() + "(after):"
					+ newOut.getStatus() + "(预计:"
					+ OutConstant.STATUS_CEO_CHECK + ")");

			// 赠品改为结算中心审批 - 2012-09-14
			if (newOut.getStatus() != OutConstant.STATUS_CEO_CHECK && false)
			{
				loggerError(id + ":" + user.getStafferName() + "(after):"
						+ newOut.getStatus() + "(预计:"
						+ OutConstant.STATUS_CEO_CHECK + ")");
			}
		}
		else
		{
			importLog.info(id + ":" + user.getStafferName() + "(after):"
					+ newOut.getStatus() + "(预计:" + OutConstant.STATUS_SUBMIT
					+ ")");

			if (newOut.getReserve3() != OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER
					&& newOut.getStatus() != OutConstant.STATUS_SUBMIT)
			{
				loggerError(id + ":" + user.getStafferName() + "(after):"
						+ newOut.getStatus() + "(预计:"
						+ OutConstant.STATUS_SUBMIT + ")");
			}

			if (newOut.getReserve3() == OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER
					&& newOut.getStatus() != OutConstant.STATUS_LOCATION_MANAGER_CHECK)
			{
				loggerError(id + ":" + user.getStafferName() + "(after):"
						+ newOut.getStatus() + "(预计:"
						+ OutConstant.STATUS_LOCATION_MANAGER_CHECK + ")");
			}
		}
	}

	/**
	 * loggerError(严重错误的日志哦)
	 * 
	 * @param msg
	 */
	protected void loggerError(String msg)
	{
		importLog.error(msg);

		fatalNotify.notify(msg);
	}

	/**
	 * 入库单通过
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward submitOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		synchronized (S_LOCK)
		{
			String fullId = request.getParameter("outId");

			OutBean out = outDAO.find(fullId);

			User user = (User) request.getSession().getAttribute("user");

			if (out == null)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

				return mapping.findForward("error");
			}

			// 退库-事业部经理审批
			if (out.getType() == OutConstant.OUT_TYPE_INBILL
					&& (out.getOutType() == OutConstant.OUTTYPE_IN_SWATCH 
					|| out.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
					|| out.getOutType() == OutConstant.OUTTYPE_IN_PRESENT))
			{
				if (out.getStatus() != OutConstant.BUY_STATUS_SUBMIT)
				{
					request.setAttribute(KeyConstant.ERROR_MESSAGE, "状态错误");

					return mapping.findForward("error");
				}
			}
			else
			{
				if (out.getStatus() != OutConstant.STATUS_SAVE)
				{
					request.setAttribute(KeyConstant.ERROR_MESSAGE, "状态错误");

					return mapping.findForward("error");
				}
			}

			try
			{
				int type = OutConstant.OUTTYPE_IN_SWATCH;

				if (out.getOutType() == OutConstant.OUTTYPE_IN_SWATCH)
				{
					type = StorageConstant.OPR_STORAGE_SWATH;
					
					// add check 溢出
					outManager.checkSwithToSail(out.getRefOutFullId());
				}

				if (out.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK)
				{
					type = StorageConstant.OPR_STORAGE_OUTBACK;
					
					// add check 溢出
					outManager.checkOutBack(out.getRefOutFullId());
				}

				outManager.submit(fullId, user, type);
			}
			catch (MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE,
						"处理错误:" + e.getErrorContent());

				return mapping.findForward("error");
			}

			if ((out.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK || out
					.getOutType() == OutConstant.OUTTYPE_IN_SWATCH)
					&& !StringTools.isNullOrNone(out.getRefOutFullId()))
			{
				// 验证(销售单)是否可以全部回款
				try
				{
					outManager.payOut(user, out.getRefOutFullId(), "自动核对付款", 0);
				}
				catch (MYException e)
				{
					_logger.info(e, e);
				}
			}

			CommonTools.saveParamers(request);

			RequestTools.menuInitQuery(request);

			request.setAttribute("queryType", "5");

			request.setAttribute("holdCondition", "1");

			request.setAttribute(KeyConstant.MESSAGE, "成功确认单据:" + fullId);

			return queryBuy(mapping, form, request, reponse);
		}
	}

    /**
     * 2015/10/23 入库换货库管通过
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward submitOutForExchange(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        synchronized (S_LOCK)
        {
            String fullId = request.getParameter("outId");

            OutBean out = outDAO.find(fullId);

            User user = (User) request.getSession().getAttribute("user");

            if (out == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

                return mapping.findForward("error");
            }

            try
            {
                int type = OutConstant.OUTTYPE_IN_EXCHANGE;

                outManager.submit3(fullId, user, type, null);
            }
            catch (MYException e)
            {
                _logger.warn(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE,
                        "处理错误:" + e.getErrorContent());

                return mapping.findForward("error");
            }

            CommonTools.saveParamers(request);

            RequestTools.menuInitQuery(request);

            request.setAttribute("queryType", "5");

            request.setAttribute("holdCondition", "1");

            request.setAttribute(KeyConstant.MESSAGE, "成功确认单据:" + fullId);

            return queryBuy(mapping, form, request, reponse);
        }
    }

    /**2014/12/28
     * 领样销售退库
     *
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward submitOut2(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        synchronized (S_LOCK)
        {
            String fullId = request.getParameter("outId");
            String accessoryList = request.getParameter("accessoryList");
            _logger.info("******************submitOut2*****************"+fullId+"****accessoryList****"+accessoryList);

            User user = (User) request.getSession().getAttribute("user");

            OutVO out = outDAO.findVO(fullId);
            if (out == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

                return mapping.findForward("error");
            }

			int pay = 0;
            // 退库-事业部经理审批
            if (out.getType() == OutConstant.OUT_TYPE_INBILL
                    && (out.getOutType() == OutConstant.OUTTYPE_IN_SWATCH
                    || out.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
                    || out.getOutType() == OutConstant.OUTTYPE_IN_PRESENT))
            {
                if (out.getStatus() != OutConstant.BUY_STATUS_SUBMIT)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "状态错误");

                    return mapping.findForward("error");
                }

                if(!StringTools.isNullOrNone(out.getRefOutFullId())){
					OutBean refOut = this.outDAO.find(out.getRefOutFullId());
					if (refOut!= null){
						pay = refOut.getPay();
					}
				}
            }
            else
            {
                if (out.getStatus() != OutConstant.STATUS_SAVE)
                {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "状态错误");

                    return mapping.findForward("error");
                }
            }

            try
            {
                int type = OutConstant.OUTTYPE_IN_SWATCH;

                if (out.getOutType() == OutConstant.OUTTYPE_IN_SWATCH)
                {
                    type = StorageConstant.OPR_STORAGE_SWATH;

                    // add check 溢出
                    outManager.checkSwithToSail(out.getRefOutFullId());
                }

                if (out.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK)
                {
                    type = StorageConstant.OPR_STORAGE_OUTBACK;

                    // add check 溢出
                    outManager.checkOutBack(out.getRefOutFullId());
                }

                //未入库前对应的BaseBean
                final List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);

                //成品行退货
                List<BaseBean> baseBeans = this.getBaseBeansFromRequest(request);

                //根据数据库中BaseBean进行相应设置
                if (!ListTools.isEmptyOrNull(baseBeans)){
                     for (BaseBean base :baseBeans){
                         String productId = base.getProductId();
                         for(BaseBean b : baseList){
                             if (productId.equals(b.getProductId())){
                                 base.setCostPrice(b.getCostPrice());
                                 base.setOwner(b.getOwner());
                                 base.setInputRate(b.getInputRate());
                                 base.setPrice(b.getPrice());
                                 base.setVirtualPrice(b.getVirtualPrice());
                             }
                         }
                     }
                }

                //配件退货
                List<DecomposeProductBean> beans = this.getDecomposeBeanFromRequest(accessoryList, user);

                this.checkProductNumber(fullId,baseBeans,beans);
                outManager.submit2(fullId, user, type, baseBeans);

                for (DecomposeProductBean bean:beans){
                    _logger.info("**********************bean*****************"+bean);
                    productFacade.addDecomposeProduct(user.getId(), bean);
                }
            }
            catch (MYException e)
            {
                e.printStackTrace();
                _logger.warn(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE,
                        "处理错误:" + e.getErrorContent());
//                request.setAttribute(KeyConstant.ERROR_MESSAGE,
//                        "处理错误:" + e.getMessage());

                return mapping.findForward("error");
            }

            if ((out.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK || out
                    .getOutType() == OutConstant.OUTTYPE_IN_SWATCH)
                    && !StringTools.isNullOrNone(out.getRefOutFullId()))
            {
                // 验证(销售单)是否可以全部回款
                try
                {
                    int backPay = 0;
                    //#775 当销售单退货触发销售单付款状态变更时，判断原销售单的付款状态PAY为1时，付款类型字段设为4，实际付款退货
                    if (pay == OutConstant.PAY_YES){
                        backPay = OutConstant.SJFKTH;
                    } else if (pay == OutConstant.PAY_NOT){
                        //当销售单退货触发销售单付款状态变更时，判断原销售单的付款状态PAY为0时，付款类型字段设为5，未付款退货
                        backPay = OutConstant.WFKTH;
                    }

					_logger.info(pay+"***out pay***"+out.getPay());
                    outManager.payOut(user, out.getRefOutFullId(), "自动核对付款", backPay);
                }
                catch (MYException e)
                {
                    _logger.info(e, e);
                }
            }

            CommonTools.saveParamers(request);

            RequestTools.menuInitQuery(request);

            request.setAttribute("queryType", "5");

            request.setAttribute("holdCondition", "1");

            request.setAttribute(KeyConstant.MESSAGE, "成功确认单据:" + fullId);

            return queryBuy(mapping, form, request, reponse);
        }
    }

    private void checkProductNumber(String fullId, List<BaseBean> finishedProductList, List<DecomposeProductBean> accessoryList) throws MYException{
        _logger.info("****成品行数量:"+finishedProductList+"*****配件行数量:"+accessoryList);
        Map<String, Integer> productNumberIn = new HashMap<String,Integer>();
        //成品数量对应关系
        for (BaseBean base : finishedProductList){
            String productId = base.getProductId();
            _logger.info("put**************"+base.getProductId()+"****"+base.getAmount());
            if (productNumberIn.containsKey(productId)){
                productNumberIn.put(productId, productNumberIn.get(productId)+base.getAmount());
            } else {
                productNumberIn.put(productId, base.getAmount());
            }
        }
        //配件行数量对应关系
        for (DecomposeProductBean cpb: accessoryList){
            String productId = cpb.getProductId();
             if (productNumberIn.containsKey(productId)){
                 productNumberIn.put(productId, productNumberIn.get(productId)+cpb.getAmount());
             } else {
                 productNumberIn.put(productId, cpb.getAmount());
             }
        }
        _logger.info("***productNumberIn***"+productNumberIn);
        List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByFK(fullId);
        Map<String, Integer> productNumberOut = new HashMap<String,Integer>();
        //销售单产品数量对应关系
        for (BaseBean base : baseBeans){
            String productId = base.getProductId();
            if (productNumberOut.containsKey(productId)){
                productNumberOut.put(productId, productNumberOut.get(productId)+base.getAmount());
            } else {
                productNumberOut.put(productId, base.getAmount());
            }
        }

        for (String productId: productNumberOut.keySet()){
            int outAmount = productNumberOut.get(productId);
            int inAmount = productNumberIn.get(productId);
            _logger.info(productId+" vs outAmount***"+outAmount+"***vs inAmount***"+inAmount);
            if(outAmount!= inAmount){
                _logger.error(productId+"*****************************退库商品数量不对******************************");
                throw new MYException("退库商品数量不对:"+productId);
            }
        }

//        for (BaseBean base: baseBeans){
//            String productId = base.getProductId();
//			_logger.info(productId+"***********amount***********"+base.getAmount());
//            if (!productNumber.containsKey(base.getProductId())) {
//                _logger.error("*****************************提交商品信息有误******************************");
//                throw new MYException("退库商品数量不对");
//            } else if (base.getAmount() != productNumber.get(productId)){
//                _logger.error("*****************************退库商品数量不对******************************"+productNumber.get(productId));
//                throw new MYException("退库商品数量不对");
//            }
//        }
    }

    //获取成品行信息
    protected List<BaseBean> getBaseBeansFromRequest(HttpServletRequest request){
        List<BaseBean> baseBeans = new ArrayList<BaseBean>();
        String[] products = request.getParameterValues("productName");
        String[] amounts = request.getParameterValues("amount");
        String[] locations = request.getParameterValues("location");

        if (products!= null && products.length>0){
           for (int i=0;i<products.length;i++){
               String productId = products[i];
               String amount = amounts[i];
               String location = locations[i];
               if (!StringTools.isNullOrNone(productId)){
                   BaseBean bean = new BaseBean();
                   bean.setProductId(productId);
                   bean.setAmount(Integer.valueOf(amount));
                   bean.setLocationId(location);

                   // 默认仓区
                   DepotpartBean defaultOKDepotpart = depotpartDAO.findDefaultOKDepotpart(location);
                   bean.setDepotpartId(defaultOKDepotpart.getId());

                   //#270
//                   if (depotParts!= null && depotParts.length>i){
//                       String depotPartId = depotParts[i];
//                       bean.setDepotpartId(depotPartId);
//                   } else{
//                       // 默认仓区
//                       DepotpartBean defaultOKDepotpart = depotpartDAO.findDefaultOKDepotpart(location);
//                       bean.setDepotpartId(defaultOKDepotpart.getId());
//                   }

                   baseBeans.add(bean);
                   _logger.info("*******add base bean ***"+bean);
               }
           }
        }

        return baseBeans;
    }

    //获取配件行信息
    private List<DecomposeProductBean> getDecomposeBeanFromRequest(String str, User user){
        List<DecomposeProductBean> beans = new ArrayList<DecomposeProductBean>();
        if (StringTools.isNullOrNone(str)){
            return beans;
        }
        String[] arr1 = str.split(";");
        System.out.println(arr1.length);

        for (String s : arr1){
            System.out.println(s);
            String[] arr2 = s.split(":");
            String productId = arr2[0];
            String[] arr3 = arr2[1].split("&");
            System.out.println("productId:"+productId+":"+arr3.length);
            DecomposeProductBean bean = new DecomposeProductBean();
            bean.setProductId(productId);
            //TODO  price

            bean.setStafferId(user.getStafferId());
            bean.setLogTime(TimeTools.now());
            bean.setType(StorageConstant.OPR_STORAGE_DECOMPOSE);


            if (arr3.length>=3){
                List<ComposeItemBean> itemList = new ArrayList<ComposeItemBean>();
                for (int i=3;i<arr3.length;i+=3){
                    System.out.println(arr3[i]);
                    ComposeItemBean each = new ComposeItemBean();
                    for (int j=0;j<3;j++){
                        System.out.println(arr3[i+j]);
                        String value = arr3[i+j].split("=")[1];
                        if (j==0){
                            each.setProductId(value);
                        } else if (j==1){
                            each.setAmount(Integer.valueOf(value));
                            bean.setAmount(Integer.valueOf(value));
                        } else if (j==2){
                            each.setDeportId(value);
                            bean.setDeportId(value);
                            // 默认仓区
                            DepotpartBean defaultOKDepotpart = depotpartDAO.findDefaultOKDepotpart(value);
                            bean.setDepotpartId(defaultOKDepotpart.getId());
                        }
                    }
                    itemList.add(each);
                }
                bean.setItemList(itemList);
            }
            System.out.println("****配件行:"+bean.getProductId()+" amount:"+bean.getAmount());
            beans.add(bean);
        }
        return beans;
    }

	/**
	 * 业务员查询销售单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward querySelfOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
        User user = (User) request.getSession().getAttribute("user");

		request.getSession().setAttribute("exportKey", QUERYSELFOUT);

		List<OutVO> list = null;

		CommonTools.saveParamers(request);

		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
               ConditionParse condtion = getQuerySelfCondition(request, user);

                int tatol = outDAO.countVOByCondition(condtion.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYSELFOUT);

				list = outDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request, QUERYSELFOUT);
				list = outDAO.queryEntityVOsByCondition(OldPageSeparateTools
						.getCondition(request, QUERYSELFOUT),
						OldPageSeparateTools.getPageSeparate(request,
								QUERYSELFOUT));
			}
		}
		catch (Exception e)
		{
            e.printStackTrace();
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}
        request.setAttribute("listOut1", list);


        // 发货单
		ConsignBean temp = null;

		for (OutBean outBean : list)
		{
			temp = consignDAO.findDefaultConsignByFullId(outBean.getFullId());

			if (temp != null)
			{
				outBean.setConsign(temp.getCurrentStatus());
			}
		}
        List<InvoiceBean> invoiceList = invoiceDAO.queryEntityBeansByCondition(
				"where forward = ?", InvoiceConstant.INVOICE_FORWARD_OUT);
        request.setAttribute("invoiceList", invoiceList);

		List<DutyVO> dutyList = dutyDAO.listEntityVOs();
        for (DutyVO vo : dutyList)
		{
			List<InvoiceBean> queryForwardOutByDutyId = invoiceDAO
					.queryForwardOutByDutyId(vo.getId());

			vo.setOutInvoiceBeanList(queryForwardOutByDutyId);
		}

		request.setAttribute("dutyList", dutyList);

		List<DepotBean> depotList = depotDAO.listEntityBeans();

		request.setAttribute("depotList", depotList);

		request.getSession().setAttribute("listOut1", list);

		request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));

        this.getDivs(request, list);
//		List<String> fullIdList = getDivsForSelfOut(request, list);
//        System.out.println("querySelfOut1dutyList*********Finished:"+fullIdList);
        //Filter list with fullId
//        String productName = request.getParameter("product_name");
//        if (!StringTools.isNullOrNone(productName)){
//            for (Iterator<OutVO> iterator = list.iterator(); iterator.hasNext();) {
//                OutVO vo = iterator.next();
//                if (!fullIdList.contains(vo.getFullId())){
//                    iterator.remove();
//                }
//            }
//        }

//        System.out.println("querySelfOut1dutyList*********listOut1:"+list);
//        System.out.println("querySelfOut1dutyList*********listOut1 size:"+list.size());
//        request.getSession().setAttribute("listOut1", list);
//        request.setAttribute("listOut1", list);
		return mapping.findForward("querySelfOut");
	}

	protected void createDepotList(HttpServletRequest request)
	{
		List<DepotBean> depotList = depotDAO.queryCommonDepotBean();

		StafferBean staffer = Helper.getStaffer(request);

		for (Iterator iterator = depotList.iterator(); iterator.hasNext();)
		{
			DepotBean depotBean = (DepotBean) iterator.next();

			if (StringTools.isNullOrNone(depotBean.getIndustryId()))
			{
				continue;
			}

			if (!depotBean.getIndustryId().equals(staffer.getIndustryId()))
			{
				iterator.remove();
				continue;
			}
		}

		request.setAttribute("depotList", depotList);
	}

	/**
	 * 查询自我的入库单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward querySelfBuy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		request.getSession().setAttribute("exportKey", QUERYSELFBUY);

		List<OutVO> list = null;

		CommonTools.saveParamers(request);

		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				ConditionParse condtion = getQuerySelfBuyCondition(request,
						user);

				int tatol = outDAO.countVOByCondition(condtion.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYSELFBUY);
				
				_logger.debug(condtion.getCondition());

				list = outDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request, QUERYSELFBUY);

				list = outDAO.queryEntityVOsByCondition(OldPageSeparateTools
						.getCondition(request, QUERYSELFBUY),
						OldPageSeparateTools.getPageSeparate(request,
								QUERYSELFBUY));
			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.setAttribute("listOut1", list);

		List<DepotBean> depotList = depotDAO.listEntityBeans();

		request.setAttribute("depotList", depotList);

		request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));

		getDivs(request, list);

		String buyReturnFlag = this.getBuyReturnFlag(request);
		if("1".equals(buyReturnFlag)){
			return mapping.findForward("querySelfBuyReturn");
		}else{
			return mapping.findForward("querySelfBuy");
		}
	}

	/**
	 * 查询委托结算清单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryOutBalance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse reponse) throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		try
		{
			checkQueryOutBalanceAuth(request);
		}
		catch (MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		List<OutBalanceVO> list = null;

		CommonTools.saveParamers(request);

		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				ConditionParse condtion = getQueryBalanceCondition(request,
						user);

				int tatol = outBalanceDAO.countVOByCondition(condtion
						.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYSELFOUTBALANCE);

				list = outBalanceDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request,
						QUERYSELFOUTBALANCE);

				list = outBalanceDAO.queryEntityVOsByCondition(
						OldPageSeparateTools.getCondition(request,
								QUERYSELFOUTBALANCE), OldPageSeparateTools
								.getPageSeparate(request, QUERYSELFOUTBALANCE));
			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.setAttribute("resultList", list);

		return mapping.findForward("queryOutBalance");
	}

	/**
	 * checkQueryOutAuth
	 * 
	 * @param request
	 * @throws MYException
	 */
	protected void checkQueryOutAuth(HttpServletRequest request)
			throws MYException
	{
		// 权限校验
		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		User user = (User) request.getSession().getAttribute("user");

		if ("1".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_LOCATION_MANAGER))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("2".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_MONEY_CENTER))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("3".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_FLOW))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("4".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_ADMIN))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("5".equals(queryType)
				&& !userManager
						.containAuth(user.getId(), AuthConstant.SAIL_SEC))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("6".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_CENTER_CHECK))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("7".equals(queryType)
				&& !userManager
						.containAuth(user.getId(), AuthConstant.SAIL_CEO))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("8".equals(queryType)
				&& !userManager.containAuth(user, AuthConstant.BUY_SUBMIT,
						AuthConstant.SAIL_SUBMIT))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("9".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_SUBMIT))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("10".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_QUERY_SUB))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("11".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_QUERY_INDUSTY))
		{
			throw new MYException("用户没有此操作的权限");
		}
	}

	/**
	 * checkQueryBuyAuth
	 * 
	 * @param request
	 * @throws MYException
	 */
	protected void checkQueryBuyAuth(HttpServletRequest request)
			throws MYException
	{
		// 权限校验
		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		User user = (User) request.getSession().getAttribute("user");

		if ("1".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.BUY_LOCATION_MANAGER))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("2".equals(queryType)
				&& !userManager.containAuth(user.getId(), AuthConstant.BUY_CEO))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("3".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.BUY_CHAIRMA))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("4".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.BUY_SUBMIT))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("5".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.BUY_SUBMIT))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("6".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.BUY_QUERYALL))
		{
			throw new MYException("用户没有此操作的权限");
		}

		// 业务员查询自己的退货单
		if ("7".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_SUBMIT))
		{
			throw new MYException("用户没有此操作的权限");
		}

		// 总部会计
		if ("8".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.BILL_QUERY_ALL))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("9".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_LOCATION_MANAGER))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("10".equals(queryType)
				&& !userManager.containAuth(user.getId(), AuthConstant.BUY_SEC))
		{
			throw new MYException("用户没有此操作的权限");
		}
	}

	/**
	 * checkQueryOutAuth
	 * 
	 * @param request
	 * @throws MYException
	 */
	protected void checkQueryOutBalanceAuth(HttpServletRequest request)
			throws MYException
	{
		// 权限校验
		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		User user = (User) request.getSession().getAttribute("user");

		if ("2".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.SAIL_MONEY_CENTER))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("3".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.BUY_SUBMIT))
		{
			throw new MYException("用户没有此操作的权限");
		}

		if ("4".equals(queryType)
				&& !userManager.containAuth(user.getId(),
						AuthConstant.FINANCE_CHECK))
		{
			throw new MYException("用户没有此操作的权限");
		}
	}

	/**
	 * 销售单审批过程的查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		try
		{
			checkQueryOutAuth(request);
		}
		catch (MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.getSession().setAttribute("exportKey", QUERYOUT);

		List<OutVO> list = null;

		CommonTools.saveParamers(request);

		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				Object attribute = request.getAttribute("holdCondition");

				ConditionParse condtion = null;

				if (attribute == null)
				{
					condtion = getQueryCondition(request, user);
				}
				else
				{
					condtion = OldPageSeparateTools.getCondition(request,
							QUERYOUT);
				}

				int tatol = outDAO.countVOByCondition(condtion.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYOUT);

				list = outDAO.queryEntityVOsByCondition(condtion, page);

			}
			else
			{
				OldPageSeparateTools.processSeparate(request, QUERYOUT);

				list = outDAO
						.queryEntityVOsByCondition(OldPageSeparateTools
								.getCondition(request, QUERYOUT),
								OldPageSeparateTools.getPageSeparate(request,
										QUERYOUT));

			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.setAttribute("listOut1", list);

		// 发货单
		ConsignBean temp = null;

		for (OutBean outBean : list)
		{
			temp = consignDAO.findDefaultConsignByFullId(outBean.getFullId());

			if (temp != null)
			{
				outBean.setConsign(temp.getReprotType());
			}
		}

		// 处理仓库
		List<DepotBean> depotList = handerDepot(request, user);

		request.setAttribute("depotList", depotList);

		int radioIndex = CommonTools.parseInt(request
				.getParameter("radioIndex"));

		Map map = (Map) request.getSession().getAttribute("ppmap");

		if (list.size() > 0 && radioIndex >= list.size())
		{
			request.setAttribute("radioIndex", list.size() - 1);

			map.put("radioIndex", list.size() - 1);
		}

		List<InvoiceBean> invoiceList = invoiceDAO.queryEntityBeansByCondition(
				"where forward = ?", InvoiceConstant.INVOICE_FORWARD_OUT);

		request.setAttribute("invoiceList", invoiceList);

		request.getSession().setAttribute("listOut1", list);

		List<DepartmentBean> departementList = departmentDAO.listEntityBeans();

		request.setAttribute("departementList", departementList);

		List<DutyBean> dutyList = dutyDAO.listEntityBeans();

		request.setAttribute("dutyList", dutyList);

		request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));

		handlerFlow(request, list, true);

		// 这里是过滤
		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		if ("1".equals(queryType))
		{
			showLastCredit(request, user, "0");
		}

		getDivs(request, list);

		return mapping.findForward("queryOut");
	}

	/**
	 * 入库单审批过程的查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryBuy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		_logger.debug("status: "+ request.getParameter("status") );
		
		saveQueryType(request);

		_logger.debug("status: "+ request.getParameter("status") );
		
		saveBuyReturnFlag(request);

		try
		{
			checkQueryBuyAuth(request);
		}
		catch (MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

			_logger.error(e, e);

			return mapping.findForward("error");
		}

		request.getSession().setAttribute("exportKey", QUERYBUY);

		List<OutVO> list = null;
		List<OutVO> list1 = new ArrayList<OutVO>();

		CommonTools.saveParamers(request);

		try
		{
			if (OldPageSeparateTools.isFirstLoad(request))
			{
				Object attribute = request.getAttribute("holdCondition");

				ConditionParse condtion = null;

				if (attribute == null)
				{
					condtion = getQueryBuyCondition(request, user);
				}
				else
				{
					condtion = OldPageSeparateTools.getCondition(request,
							QUERYBUY);
				}

				int tatol = outDAO.countVOByCondition(condtion.toString());

				PageSeparate page = new PageSeparate(tatol,
						PublicConstant.PAGE_SIZE - 5);

				OldPageSeparateTools.initPageSeparate(condtion, page, request,
						QUERYBUY);

				list = outDAO.queryEntityVOsByCondition(condtion, page);
			}
			else
			{
				OldPageSeparateTools.processSeparate(request, QUERYBUY);

				list = outDAO
						.queryEntityVOsByCondition(OldPageSeparateTools
								.getCondition(request, QUERYBUY),
								OldPageSeparateTools.getPageSeparate(request,
										QUERYBUY));
			}
		}
		catch (Exception e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询单据失败");

			_logger.error(e, e);

			return mapping.findForward("error");
		}
		ConditionParse condtion = new ConditionParse();
		condtion.addWhereStr();
//		condtion.addIntCondition("OutBean.outType", "=",
//				OutConstant.OUTTYPE_OUT_APPLY);
        //2015/10/23 增加入库换货查询
		String queryType = request.getParameter("queryType");
		//入库-调拨查询
		if ("4".equals(queryType)){
				condtion.addIntCondition("OutBean.outType", "=",
				OutConstant.OUTTYPE_OUT_APPLY);
		}else{
			condtion.addCondition("and OutBean.outType in (8,25)");
		}
		condtion.addIntCondition("OutBean.type", "=",
				OutConstant.OUT_TYPE_INBILL);
//		condtion.addIntCondition("OutBean.status", "=",
//				OutConstant.STATUS_LOCATION_MANAGER_CHECK);
        condtion.addCondition("and OutBean.status in (7,8)");

		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("OutBean.outTime", ">=", outTime);
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("OutBean.outTime", "<=", outTime1);
		}

		String changeTime = request.getParameter("changeTime");

		String changeTime1 = request.getParameter("changeTime1");

		if (!StringTools.isNullOrNone(changeTime))
		{
			condtion.addCondition("OutBean.changeTime", ">=", changeTime
					+ " 00:00:00");
		}

		if (!StringTools.isNullOrNone(changeTime1))
		{
			condtion.addCondition("OutBean.changeTime", "<=", changeTime1
					+ " 23:59:59");
		}

		// add 2012.7.2 当没有选择时间才给outTime默认日期
		if (StringTools.isNullOrNone(changeTime)
				&& StringTools.isNullOrNone(changeTime1)
				&& StringTools.isNullOrNone(outTime)
				&& StringTools.isNullOrNone(outTime1))
		{

			condtion.addCondition("OutBean.outTime", ">=",
					TimeTools.now_short(-7));

			request.setAttribute("outTime", TimeTools.now_short(-7));

			condtion.addCondition("OutBean.outTime", "<=",
					TimeTools.now_short());

			request.setAttribute("outTime1", TimeTools.now_short());

		}

		int tatol = outDAO.countVOByCondition(condtion.toString());

		PageSeparate page = new PageSeparate(tatol,
				PublicConstant.PAGE_SIZE - 5);

		list1 = outDAO.queryEntityVOsByCondition(condtion, page);
        _logger.info(condtion+"**********queryBuy list size:"+list1.size());

		StafferBean sb = this.stafferDAO.find(user.getStafferId());
		String postid = sb.getPostId();
		String industryid = sb.getIndustryId();
		if (null != postid && postid.equals("16") && null != list1)// 16事业部体系负责人
		{
			for (int i = 0; i < list1.size(); i++)
			{
				OutVO vo = list1.get(i);
				String location = vo.getLocation();
				if (null != location)
				{
					DepotBean db = this.depotDAO.find(location);
					if (null != industryid
							&& industryid.equals(db.getIndustryId()))
					{
						list.add(vo);
					}
                    //TODO 2015/10/23
                    else if (vo.getOutType() == OutConstant.OUTTYPE_IN_EXCHANGE){
                        list.add(vo);
                    }
				}else if (vo.getOutType() == OutConstant.OUTTYPE_IN_EXCHANGE){
                    list.add(vo);
                }
			}
		}
        _logger.info("**********queryBuy listOut1 size*********:"+list.size());
		request.setAttribute("listOut1", list);
		getDivs(request, list);

		List<DepotBean> depotList = depotDAO.listEntityBeans();

		request.setAttribute("depotList", depotList);

		List<PrincipalshipBean> locationList = orgManager.listAllIndustry();

		request.setAttribute("locationList", locationList);

		int radioIndex = CommonTools.parseInt(request
				.getParameter("radioIndex"));

		Map map = (Map) request.getSession().getAttribute("ppmap");

		if (list.size() > 0 && radioIndex >= list.size())
		{
			request.setAttribute("radioIndex", list.size() - 1);

			map.put("radioIndex", list.size() - 1);
		}

		request.setAttribute("now", TimeTools.now("yyyy-MM-dd"));

		String buyReturnFlag = this.getBuyReturnFlag(request);
		if("1".equals(buyReturnFlag)){
			return mapping.findForward("queryBuyReturn");
		}else{
			return mapping.findForward("queryBuy");
		}
	}

	/**
	 * saveQueryType
	 * 
	 * @param request
	 */
	private void saveQueryType(HttpServletRequest request)
	{
		String queryType = request.getParameter("queryType");

		if (!StringTools.isNullOrNone(queryType))
		{
			request.getSession().setAttribute("queryType", queryType);

			return;
		}

		Object attribute = request.getAttribute("queryType");

		if (attribute != null)
		{
			request.getSession().setAttribute("queryType", attribute);
		}
	}
	

	/**
	 * saveBuyReturnFlag
	 * 
	 * @param request
	 */
	private void saveBuyReturnFlag(HttpServletRequest request)
	{
		String buyReturnFlag = request.getParameter("buyReturnFlag");

		if (!StringTools.isNullOrNone(buyReturnFlag))
		{
			//request.getSession().setAttribute("buyReturnFlag", buyReturnFlag);

			return;
		}

		Object attribute = request.getAttribute("buyReturnFlag");

		if (attribute != null)
		{
			//request.getSession().setAttribute("buyReturnFlag", attribute);
		}
	}
	
	/**
	 * getBuyReturnFlag
	 * 
	 * @param request
	 */
	private String getBuyReturnFlag(HttpServletRequest request)
	{
		String buyReturnFlag = request.getParameter("buyReturnFlag");

		if (StringTools.isNullOrNone(buyReturnFlag))
		{
			buyReturnFlag = (String)request.getAttribute("buyReturnFlag");
		}
		
		return buyReturnFlag;

	}

	/**
	 * 处理仓库
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	protected List<DepotBean> handerDepot(HttpServletRequest request, User user)
	{
		// 这里是过滤
		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		List<DepotBean> depotList = depotDAO.listEntityBeans();

		if ("3".equals(queryType) || "4".equals(queryType))
		{
			// 只能看到自己的仓库
			List<AuthBean> depotAuthList = userManager.queryExpandAuthById(
					user.getId(), AuthConstant.EXPAND_AUTH_DEPOT);

			for (Iterator iterator = depotList.iterator(); iterator.hasNext();)
			{
				DepotBean depotBean = (DepotBean) iterator.next();

				boolean delete = true;

				for (AuthBean authBean : depotAuthList)
				{
					if (authBean.getId().equals(depotBean.getId()))
					{
						delete = false;

						break;
					}
				}

				if (delete)
				{
					iterator.remove();
				}

			}
		}

		return depotList;
	}

	/**
	 * 处理部分物流的逻辑
	 * 
	 * @param request
	 * @param list
	 * @param alert
	 */
	protected void handlerFlow(HttpServletRequest request, List<OutVO> list,
			boolean alert)
	{
		ConsignBean temp = null;

		// 物流的需要知道是否有发货单
		double total = 0.0d;

		Map<String, String> hasMap = new HashMap<String, String>();
		Map<String, String> overDayMap = new HashMap<String, String>();

		for (OutBean outBean : list)
		{
			temp = consignDAO.findDefaultConsignByFullId(outBean.getFullId());

			if (temp != null)
			{
				outBean.setConsign(temp.getCurrentStatus());
			}

			total += outBean.getTotal();

			// 是否超期 超期几天
			if (!StringTools.isNullOrNone(outBean.getRedate())
					&& outBean.getPay() == OutConstant.PAY_NOT)
			{
				int overDays = TimeTools.cdate(TimeTools.now_short(),
						outBean.getRedate());

				if (overDays < 0)
				{
					overDayMap.put(outBean.getFullId(),
							String.valueOf(overDays));
				}
				else
				{
					overDayMap.put(outBean.getFullId(), "<font color=red><b>"
							+ overDays + "</b></font>");
				}
			}

			// 款到发货
			if (outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_MONEY
					&& outBean.getPay() == OutConstant.PAY_YES)
			{
				overDayMap.put(outBean.getFullId(), String.valueOf(0));
			}

			if (alert)
			{
				if (hasOver(outBean.getStafferName()))
				{
					hasMap.put(outBean.getFullId(), "true");
				}
				else
				{
					hasMap.put(outBean.getFullId(), "false");
				}
			}
		}

		request.setAttribute("hasMap", hasMap);

		request.setAttribute("overDayMap", overDayMap);
	}

	protected ConditionParse getQuerySelfCondition(HttpServletRequest request,
			User user)
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addIntCondition("OutBean.type", "=",
				OutConstant.OUT_TYPE_OUTBILL);

		String vtype = request.getParameter("vtype");

		if (!StringTools.isNullOrNone(vtype))
		{
			condtion.addIntCondition("OutBean.vtype", "=", vtype);
		}

		String customerId = request.getParameter("customerId");

		if (!StringTools.isNullOrNone(customerId))
		{
			// 客户查询的时候可以查看所有的客户历史
			condtion.addCondition("OutBean.customerId", "=", customerId);
		}
		else
		{
			// 如果不是默认查询整个vtype
			if (!String.valueOf(OutConstant.VTYPE_SPECIAL).equals(vtype))
			{
				// 只能查询自己的
				condtion.addCondition("OutBean.STAFFERID", "=",
						user.getStafferId());
			}
		}

		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("OutBean.outTime", ">=", outTime);
		}
		else
		{
			condtion.addCondition("OutBean.outTime", ">=",
					TimeTools.now_short(-7));

			request.setAttribute("outTime", TimeTools.now_short(-7));
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("OutBean.outTime", "<=", outTime1);
		}
		else
		{
			condtion.addCondition("OutBean.outTime", "<=",
					TimeTools.now_short());

			request.setAttribute("outTime1", TimeTools.now_short());
		}

		String redateB = request.getParameter("redateB");

		String redateE = request.getParameter("redateE");

		if (!StringTools.isNullOrNone(redateB))
		{
			condtion.addCondition("OutBean.redate", ">=", redateB);
		}

		if (!StringTools.isNullOrNone(redateE))
		{
			condtion.addCondition("OutBean.redate", "<=", redateE);
		}

		String id = request.getParameter("id");

		if (!StringTools.isNullOrNone(id))
		{
			condtion.addCondition("OutBean.fullid", "like", id);
		}

		String status = request.getParameter("status");

		if (!StringTools.isNullOrNone(status))
		{
			if ("99".equals(status))
			{
				condtion.addCondition(" and OutBean.status in (3, 4)");
			}
			else
			{
				condtion.addIntCondition("OutBean.status", "=", status);
			}
		}
		else
		{
			request.setAttribute("status", null);
		}

		String outType = request.getParameter("outType");

		if (!StringTools.isNullOrNone(outType))
		{
			condtion.addIntCondition("OutBean.outType", "=", outType);
		}

		String location = request.getParameter("location");

		if (!StringTools.isNullOrNone(location))
		{
			condtion.addCondition("OutBean.location", "=", location);
		}

		String redate = request.getParameter("redate");

		String reCom = request.getParameter("reCom");

		if (!StringTools.isNullOrNone(redate)
				&& !StringTools.isNullOrNone(reCom))
		{
			condtion.addCondition("OutBean.redate", reCom, redate);

			condtion.addCondition("and OutBean.redate <> ''");
		}

		String pay = request.getParameter("pay");

		if (!StringTools.isNullOrNone(pay))
		{
			if (!pay.equals(String.valueOf(OutConstant.PAY_OVER)))
			{
				condtion.addIntCondition("OutBean.pay", "=", pay);
			}
			else
			{
				condtion.addCondition(" and OutBean.status in (3, 4)");

				condtion.addCondition(" and OutBean.redate < '"
						+ TimeTools.now_short() + "'");

				condtion.addIntCondition("OutBean.pay", "=", 0);
			}
		}

		String tempType = request.getParameter("tempType");

		if (!StringTools.isNullOrNone(tempType))
		{
			condtion.addIntCondition("OutBean.tempType", "=", tempType);
		}

        String productName = request.getParameter("product_name");
        if (!StringTools.isNullOrNone(productName)){
            condtion.addCondition(" and exists (select b.id from t_center_base b where b.outId=OutBean.fullId and b.productName like '%" + productName + "%')");
        }

		condtion.addCondition("order by OutBean.id desc");

		return condtion;
	}

	/**
	 * getQuerySelfBuyCondition
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	protected ConditionParse getQuerySelfBuyCondition(
			HttpServletRequest request, User user)
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addIntCondition("OutBean.type", "=",
				OutConstant.OUT_TYPE_INBILL);

		String vtype = request.getParameter("vtype");

		if (!StringTools.isNullOrNone(vtype))
		{
			condtion.addIntCondition("OutBean.vtype", "=", vtype);
		}

		// 只能查询自己的
		condtion.addCondition("OutBean.STAFFERID", "=", user.getStafferId());

		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("OutBean.outTime", ">=", outTime);
		}
		else
		{
			condtion.addCondition("OutBean.outTime", ">=",
					TimeTools.now_short(-7));

			request.setAttribute("outTime", TimeTools.now_short(-7));
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("OutBean.outTime", "<=", outTime1);
		}
		else
		{
			condtion.addCondition("OutBean.outTime", "<=",
					TimeTools.now_short());

			request.setAttribute("outTime1", TimeTools.now_short());
		}

		String id = request.getParameter("id");

		if (!StringTools.isNullOrNone(id))
		{
			condtion.addCondition("OutBean.fullid", "like", id);
		}

		String status = request.getParameter("status");

		if (!StringTools.isNullOrNone(status))
		{
			if ("99".equals(status))
			{
				condtion.addCondition(" and OutBean.status in (3, 4)");
			}
			else
			{
				condtion.addIntCondition("OutBean.status", "=", status);
			}
		}
		else
		{
			request.setAttribute("status", null);
		}

		String customerId = request.getParameter("customerId");

		if (!StringTools.isNullOrNone(customerId))
		{
			condtion.addCondition("OutBean.customerId", "=", customerId);
		}

		String customerName = request.getParameter("customerName");

		if (!StringTools.isNullOrNone(customerName))
		{
			condtion.addCondition("OutBean.customerName", "like", customerName);
		}

		String outType = request.getParameter("outType");

		if (!StringTools.isNullOrNone(outType))
		{
			condtion.addIntCondition("OutBean.outType", "=", outType);
		}

		String location = request.getParameter("location");

		if (!StringTools.isNullOrNone(location))
		{
			condtion.addCondition("OutBean.location", "=", location);
		}

		String inway = request.getParameter("inway");

		if (!StringTools.isNullOrNone(inway))
		{
			condtion.addIntCondition("inway", "=", inway);
		}
		

		String buyReturnFlag = request.getParameter("buyReturnFlag");
		if(!"1".equals(buyReturnFlag)){
			buyReturnFlag = "0";
		}

		if (!StringTools.isNullOrNone(buyReturnFlag))
		{
			condtion.addIntCondition("OutBean.buyReturnFlag", "=", buyReturnFlag);
		}

		condtion.addCondition("order by OutBean.id desc");

		return condtion;
	}

	/**
	 * 查询REF的入库单(所有状态的)
	 * 
	 * @param request
	 * @param outId
	 * @return
	 */
	protected List<OutBean> queryRefOut4(HttpServletRequest request,
			String outId)
	{
		// 查询当前已经有多少个人领样
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		// 排除其他入库(对冲单据)
		con.addCondition("OutBean.reserve8", "<>", "1");

		List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

		for (OutBean outBean : refBuyList)
		{
			List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean
					.getFullId());

			outBean.setBaseList(list);
		}

		request.setAttribute("refBuyList", refBuyList);

		return refBuyList;
	}

	/**
	 * getQuerySelfBalanceCondition
	 * 
	 * @param request
	 * @param user
	 * @return
	 */
	protected ConditionParse getQueryBalanceCondition(
			HttpServletRequest request, User user)
	{
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("OutBalanceBean.logTime", ">=", outTime + " 00:00:01");
		}
		else
		{
			condtion.addCondition("OutBalanceBean.logTime", ">=",
					TimeTools.now_short(-30) + " 00:00:01");

			request.setAttribute("outTime", TimeTools.now_short(-30));
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("OutBalanceBean.logTime", "<=", outTime1 + " 23:59:59");
		}
		else
		{
			condtion.addCondition("OutBalanceBean.logTime", "<=",
					TimeTools.now_short(1));

			request.setAttribute("outTime1", TimeTools.now_short(1));
		}

		String changeTime = request.getParameter("changeTime");

		String changeTime1 = request.getParameter("changeTime1");

		if (!StringTools.isNullOrNone(changeTime))
		{
			condtion.addCondition("OutBean.changeTime", ">=", changeTime
					+ " 00:00:00");
		}

		if (!StringTools.isNullOrNone(changeTime1))
		{
			condtion.addCondition("OutBean.changeTime", "<=", changeTime1
					+ " 23:59:59");
		}
		
		String redateB = request.getParameter("redateB");

		String redateE = request.getParameter("redateE");

		if (!StringTools.isNullOrNone(redateB))
		{
			condtion.addCondition("OutBean.redate", ">=", redateB);
		}

		if (!StringTools.isNullOrNone(redateE))
		{
			condtion.addCondition("OutBean.redate", "<=", redateE);
		}

		String outId = request.getParameter("outId");

		if (!StringTools.isNullOrNone(outId))
		{
			condtion.addCondition("OutBalanceBean.outId", "like", outId.trim());
		}

		String id = request.getParameter("qid");

		if (!StringTools.isNullOrNone(id))
		{
			condtion.addCondition("OutBalanceBean.id", "like", id.trim());
		}

		String status = request.getParameter("status");

		if (!StringTools.isNullOrNone(status))
		{
			condtion.addIntCondition("OutBalanceBean.status", "=", status);
		}

		String type = request.getParameter("type");

		if (!StringTools.isNullOrNone(type))
		{
			condtion.addIntCondition("OutBalanceBean.type", "=", type);
		}
		
		String customerName = request.getParameter("customerName");

		if (!StringTools.isNullOrNone(customerName))
		{
			condtion.addCondition("CustomerBean.name", "like", customerName);
		}

		String stafferName = request.getParameter("stafferName");

		if (!StringTools.isNullOrNone(stafferName))
		{
			condtion.addCondition("StafferBean.name", "like", stafferName);
		}
		
		String industryId = request.getParameter("industryId");

		if (!StringTools.isNullOrNone(industryId))
		{
			condtion.addCondition("OutBalanceBean.industryId", "=", industryId);
		}

		String pay = request.getParameter("pay");

		if (!StringTools.isNullOrNone(pay))
		{
			if (pay.equals(String.valueOf(OutConstant.PAY_NOT)))
			{
				condtion.addIntCondition("OutBalanceBean.payMoney", "=", 0);
			}
			else
			{
				condtion.addIntCondition("OutBalanceBean.payMoney", ">", 0);
			}
		}

		// 权限校验
		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		if ("1".equals(queryType))
		{
			// 只能查询自己的
			condtion.addCondition("OutBalanceBean.STAFFERID", "=",
					user.getStafferId());
		}
		// 查询审批的
		else if ("2".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBalanceBean.status", "=",
						OutConstant.OUTBALANCE_STATUS_SUBMIT);

				request.setAttribute("status",
						OutConstant.OUTBALANCE_STATUS_SUBMIT);
			}
		}
		// 查询退货的
		else if ("3".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBalanceBean.status", "=",
						OutConstant.OUTBALANCE_STATUS_PASS);

				request.setAttribute("status",
						OutConstant.OUTBALANCE_STATUS_PASS);
			}

			condtion.addCondition(" and OutBalanceBean.type in (1,2)");

			request.setAttribute("type", OutConstant.OUTBALANCE_TYPE_BACK);

			// 库存所管辖的
			setDepotCondotionInOutBlance(user, condtion);
		}
		// 退货总部核对
		else if ("4".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBalanceBean.status", "=",
						OutConstant.OUTBALANCE_STATUS_END);

				request.setAttribute("status",
						OutConstant.OUTBALANCE_STATUS_END);

				condtion.addIntCondition("OutBalanceBean.checkStatus", "=",
						PublicConstant.CHECK_STATUS_INIT);
			}

			condtion.addCondition(" and OutBalanceBean.type in (1,2)");

			request.setAttribute("type", OutConstant.OUTBALANCE_TYPE_BACK);
		}
		else
		{
			condtion.addFlaseCondition();
		}

		condtion.addCondition("order by OutBalanceBean.logTime desc");

		return condtion;
	}

	/**
	 * 销售单审批过程的查询(条件的设置)
	 * 
	 * @param request
	 * @param user
	 * @return
	 * @throws MYException
	 */
	protected ConditionParse getQueryCondition(HttpServletRequest request,
			User user) throws MYException
	{
		Map<String, String> queryOutCondtionMap = CommonTools
				.saveParamersToMap(request);

		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addIntCondition("OutBean.type", "=",
				OutConstant.OUT_TYPE_OUTBILL);

		String vtype = request.getParameter("vtype");

		if (!StringTools.isNullOrNone(vtype))
		{
			condtion.addIntCondition("OutBean.vtype", "=", vtype);
		}

		// 这里是过滤
		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("OutBean.outTime", ">=", outTime);
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("OutBean.outTime", "<=", outTime1);
		}

        //2015/7/12 新增查询条件“银行销售日期"
        String poDate = request.getParameter("poDate");

        String poDate1 = request.getParameter("poDate1");

        if (!StringTools.isNullOrNone(poDate))
        {
            condtion.addCondition("OutBean.poDate", ">=", poDate);
        }

        if (!StringTools.isNullOrNone(poDate1))
        {
            condtion.addCondition("OutBean.poDate", "<=", poDate1);
        }

		String changeTime = request.getParameter("changeTime");

		String changeTime1 = request.getParameter("changeTime1");

		if (!StringTools.isNullOrNone(changeTime))
		{
			condtion.addCondition("OutBean.changeTime", ">=", changeTime
					+ " 00:00:00");
		}

		if (!StringTools.isNullOrNone(changeTime1))
		{
			condtion.addCondition("OutBean.changeTime", "<=", changeTime1
					+ " 23:59:59");
		}

		String redateB = request.getParameter("redateB");

		String redateE = request.getParameter("redateE");

		if (!StringTools.isNullOrNone(redateB))
		{
//			condtion.addCondition("OutBean.redate", ">=", redateB);
			condtion.addCondition("OutBean.payTime", ">=", redateB+ " 00:00:00");
		}

		if (!StringTools.isNullOrNone(redateE))
		{
//			condtion.addCondition("OutBean.redate", "<=", redateE);
			condtion.addCondition("OutBean.payTime", "<=", redateE+ " 23:59:59");
		}

		// add 2012.7.2 当没有选择时间才给outTime默认日期
		if (StringTools.isNullOrNone(redateB)
				&& StringTools.isNullOrNone(redateE)
				&& StringTools.isNullOrNone(changeTime)
				&& StringTools.isNullOrNone(changeTime1)
				&& StringTools.isNullOrNone(outTime)
				&& StringTools.isNullOrNone(outTime1))
		{

			//#162 商务审批、库管、往来核对、总部核对里边开始时间设置为当天
			String beginTime = TimeTools.now_short();
			condtion.addCondition("OutBean.outTime", ">=", beginTime);

			request.setAttribute("outTime", beginTime);

			queryOutCondtionMap.put("outTime", beginTime);

			String endTime = TimeTools.now_short();
			condtion.addCondition("OutBean.outTime", "<=",endTime);

			request.setAttribute("outTime1", endTime);

			queryOutCondtionMap.put("outTime1", endTime);

		}

		String id = request.getParameter("id");

		if (!StringTools.isNullOrNone(id))
		{
			condtion.addCondition("OutBean.fullid", "like", id.trim());
		}

		String status = request.getParameter("status");

		if (!StringTools.isNullOrNone(status))
		{
			if ("99".equals(status))
			{
				condtion.addCondition(" and OutBean.status in (3, 4)");
			}
			else
			{
				condtion.addIntCondition("OutBean.status", "=", status);
			}
		}

		String customerId = request.getParameter("customerId");

		if (!StringTools.isNullOrNone(customerId))
		{
			condtion.addCondition("OutBean.customerId", "=", customerId);
		}

		String duty = request.getParameter("duty");

		if (!StringTools.isNullOrNone(duty))
		{
			condtion.addCondition("OutBean.dutyId", "=", duty);
		}

		String invoiceStatus = request.getParameter("invoiceStatus");

		if (!StringTools.isNullOrNone(invoiceStatus))
		{
			condtion.addIntCondition("OutBean.invoiceStatus", "=",
					invoiceStatus);
		}

		String customerName = request.getParameter("customerName");

		if (!StringTools.isNullOrNone(customerName))
		{
			condtion.addCondition("OutBean.customerName", "like", customerName);
		}

		String stafferName = request.getParameter("stafferName");

		if (!StringTools.isNullOrNone(stafferName))
		{
			condtion.addCondition("OutBean.stafferName", "like", stafferName);
		}

		String outType = request.getParameter("outType");

		if (!StringTools.isNullOrNone(outType))
		{
			condtion.addIntCondition("OutBean.outType", "=", outType);
		}

		String location = request.getParameter("location");

		if (!StringTools.isNullOrNone(location))
		{
			condtion.addCondition("OutBean.location", "=", location);
		}

		String redate = request.getParameter("redate");

		String reCom = request.getParameter("reCom");

		if (!StringTools.isNullOrNone(redate)
				&& !StringTools.isNullOrNone(reCom))
		{
			condtion.addCondition("OutBean.redate", reCom, redate);

			condtion.addCondition("and OutBean.redate <> ''");
		}

		String operatorName = request.getParameter("operatorName");

		if (!StringTools.isNullOrNone(operatorName))
		{
			condtion.addCondition("OutBean.operatorName", "like", operatorName);
		}

		String industryId = request.getParameter("industryId");

		if (!StringTools.isNullOrNone(industryId))
		{
			condtion.addCondition("OutBean.industryId", "=", industryId);
		}

		String pay = request.getParameter("pay");

		if (!StringTools.isNullOrNone(pay))
		{
			if (!pay.equals(String.valueOf(OutConstant.PAY_OVER)))
			{
				condtion.addIntCondition("OutBean.pay", "=", pay);
			}
			else
			{
				condtion.addCondition(" and OutBean.status in (3, 4)");

				condtion.addCondition(" and OutBean.redate < '"
						+ TimeTools.now_short() + "'");

				condtion.addIntCondition("OutBean.pay", "=", 0);
			}
		}

		String tempType = request.getParameter("tempType");

		if (!StringTools.isNullOrNone(tempType))
		{
			condtion.addIntCondition("OutBean.tempType", "=", tempType);
		}

		String isBank = request.getParameter("isBank");

		if (!StringTools.isNullOrNone(isBank))
		{
			if (isBank.equals("0"))
				condtion.addCondition("OutBean.flowid", "=", "CITIC");
			else
				condtion.addCondition("OutBean.flowid", "<>", "CITIC");
		}
		
		StafferBean staffer = Helper.getStaffer(request);

		// 事业部经理查询
		if ("1".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.STATUS_LOCATION_MANAGER_CHECK);

				request.setAttribute("status",
						OutConstant.STATUS_LOCATION_MANAGER_CHECK);

				queryOutCondtionMap.put("status", String
						.valueOf(OutConstant.STATUS_LOCATION_MANAGER_CHECK));
			}

			condtion.addCondition("and OutBean.industryId in "
					+ getAllIndustryId(staffer));
			
			condtion.addCondition("OutBean.guarantor", "=", staffer.getId());
		}
		// 结算中心查询
		else if ("2".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.STATUS_SUBMIT);

				request.setAttribute("status", OutConstant.STATUS_SUBMIT);

				queryOutCondtionMap.put("status",
						String.valueOf(OutConstant.STATUS_SUBMIT));
			}

            //2015/2/24 Filter by product name
            String productName = request.getParameter("product_name");
            if (!StringTools.isNullOrNone(productName)){
                condtion.addCondition(" and exists (select b.id from t_center_base b where b.outId=OutBean.fullId and b.productName like '%"+productName+"%')");
            }
			condtion.addCondition("order by outTime desc");
		}
		// 处理发货单 物流审批(只有中心仓库才有物流的)
		else if ("3".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.STATUS_MANAGER_PASS);

				request.setAttribute("status", OutConstant.STATUS_MANAGER_PASS);

				queryOutCondtionMap.put("status",
						String.valueOf(OutConstant.STATUS_MANAGER_PASS));
			}

			setDepotCondotionInOut(user, condtion);

			condtion.addCondition("order by managerTime desc");
		}
		// 库管
		else if ("4".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.STATUS_FLOW_PASS);

				request.setAttribute("status", OutConstant.STATUS_FLOW_PASS);

				queryOutCondtionMap.put("status",
						String.valueOf(OutConstant.STATUS_FLOW_PASS));
			}

			setDepotCondotionInOut(user, condtion);

            //2015/2/24 Filter by product name
            String productName = request.getParameter("product_name");
            if (!StringTools.isNullOrNone(productName)){
                condtion.addCondition(" and exists (select b.id from t_center_base b where b.outId=OutBean.fullId and b.productName like '%"+productName+"%')");
            }
			condtion.addCondition("order by managerTime desc");
		}
		// 会计往来核对
		else if ("5".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.pay", "=",
						OutConstant.PAY_NOT);

				condtion.addCondition("and OutBean.status in (3, 4)");

				request.setAttribute("pay", OutConstant.PAY_NOT);

				queryOutCondtionMap.put("pay",
						String.valueOf(OutConstant.PAY_NOT));
			}

			if (!userManager.containAuth(user.getId(),
					AuthConstant.BILL_QUERY_ALL))
			{
				condtion.addCondition("OutBean.locationId", "=",
						user.getLocationId());
			}

			condtion.addCondition("order by changeTime desc");
		}
		// 总部核对(已经付款的销售单)
		else if ("6".equals(queryType))
		{
			//				//#162 总部核对 默认查询状态“已出库” 置空
//			if (OldPageSeparateTools.isMenuLoad(request))
//			{
//				condtion.addIntCondition("OutBean.status", "=",
//						OutConstant.STATUS_PASS);
//
//				request.setAttribute("status", OutConstant.STATUS_PASS);
//
//				queryOutCondtionMap.put("status",
//						String.valueOf(OutConstant.STATUS_PASS));
//			}

            //Filter by product name
            String productName = request.getParameter("product_name");
            if (!StringTools.isNullOrNone(productName)){
                condtion.addCondition(" and exists (select b.id from t_center_base b where b.outId=OutBean.fullId and b.productName like '%"+productName+"%')");
            }
			condtion.addCondition("order by changeTime desc");
		}
		// 总裁审批赠送
		else if ("7".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.STATUS_CEO_CHECK);

				request.setAttribute("status", OutConstant.STATUS_CEO_CHECK);

				queryOutCondtionMap.put("status",
						String.valueOf(OutConstant.STATUS_CEO_CHECK));
			}
		}
		// 查询销售退库
		else if ("8".equals(queryType))
		{
			condtion.addCondition("and OutBean.status in (3, 4)");

			condtion.addCondition("and OutBean.outType not in (1, 5, 6,7, 3)");
			
			/*condtion.addIntCondition("OutBean.outType", "<>",
					OutConstant.OUTTYPE_OUT_SWATCH);
			
			condtion.addIntCondition("OutBean.outType", "<>",
					OutConstant.OUTTYPE_OUT_SHOW);

			condtion.addIntCondition("OutBean.outType", "<>",
					OutConstant.OUTTYPE_OUT_CONSIGN);*/

			// 只能退自己的(移交的通过流程)
			condtion.addCondition("OutBean.stafferId", "=", user.getStafferId());
		}
		// 查询没有结束的个人领样 含巡展
		else if ("9".equals(queryType))
		{
			condtion.addCondition("and OutBean.outType in (1, 5, 6,7)");
			
			condtion.addCondition("and OutBean.status in (3, 4)");

			condtion.addCondition("OutBean.stafferId", "=", user.getStafferId());

			request.setAttribute("outType", OutConstant.OUTTYPE_OUT_SWATCH);

			queryOutCondtionMap.put("outType",
					String.valueOf(OutConstant.OUTTYPE_OUT_SWATCH));

			request.setAttribute("status", OutConstant.STATUS_PASS);

			queryOutCondtionMap.put("status",
					String.valueOf(OutConstant.STATUS_PASS));
		}
		// 查询下属的销售单
		else if ("10".equals(queryType))
		{
			// 查询自己下面的
			String srcId = staffer.getPrincipalshipId();

			if (srcId.equals(staffer.getIndustryId())
					|| staffer.getPostId().equals(
							PublicConstant.POST_SHI_MANAGER))
			{
				condtion.addCondition("OutBean.industryId", "=",
						staffer.getIndustryId());
			}
			else if (srcId.equals(staffer.getIndustryId2())
					|| staffer.getPostId().equals(
							PublicConstant.POST_AREA_MANAGER))
			{
				condtion.addCondition("OutBean.industryId2", "=",
						staffer.getIndustryId2());
			}
			else if (srcId.equals(staffer.getIndustryId3())
					|| staffer.getPostId().equals(
							PublicConstant.POST_DEPART_MANAGER))
			{
				condtion.addCondition("OutBean.industryId3", "=",
						staffer.getIndustryId3());
			}
			else
			{
				condtion.addFlaseCondition();
			}
		}
		// 查询事业部的销售单
		else if ("11".equals(queryType))
		{
			if (StringTools.isNullOrNone(staffer.getIndustryId()))
			{
				condtion.addCondition("OutBean.stafferId", "=",
						user.getStafferId());
			}
			else
			{
				condtion.addCondition("OutBean.industryId", "=",
						staffer.getIndustryId());
			}
		}
		// 未知的则什么都没有
		else
		{
			condtion.addFlaseCondition();
		}


		if (!condtion.containOrder())
		{
			condtion.addCondition("order by OutBean.id desc");
		}

		request.getSession().setAttribute("ppmap", queryOutCondtionMap);

		return condtion;
	}

	/**
	 * getIndustryIdCredit
	 * 
	 * @param industryId
	 * @param managerStafferId
	 * @return double
	 */
	protected double getIndustryIdCredit(String industryId,
			String managerStafferId)
	{
		List<InvoiceCreditBean> inList = invoiceCreditDAO
				.queryEntityBeansByFK(managerStafferId);

		for (InvoiceCreditBean invoiceCreditBean : inList)
		{
			if (invoiceCreditBean.getInvoiceId().equals(industryId))
			{
				return invoiceCreditBean.getCredit();
			}
		}

		return 0.0d;
	}

	protected String getAllIndustryId(StafferBean sb) throws MYException
	{
		List<InvoiceCreditBean> inList = invoiceCreditDAO
				.queryEntityBeansByFK(sb.getId());

		if (inList.size() == 1)
		{
			return "('" + sb.getIndustryId() + "')";
		}

		if (inList.size() == 0)
		{
			throw new MYException("职员[%s]没有事业部属性", sb.getName());
		}

		StringBuffer buffer = new StringBuffer();

		buffer.append("(");

		for (InvoiceCreditBean invoiceCreditBean : inList)
		{
			buffer.append("'").append(invoiceCreditBean.getInvoiceId())
					.append("'").append(",");
		}

		buffer.deleteCharAt(buffer.length() - 1);

		buffer.append(")");

		return buffer.toString();
	}

	/**
	 * 入库单审批过程的查询(条件的设置)
	 * 
	 * @param request
	 * @param user
	 * @return
	 * @throws MYException
	 */
	protected ConditionParse getQueryBuyCondition(HttpServletRequest request,
			User user) throws MYException
	{
		Map<String, String> queryOutCondtionMap = CommonTools
				.saveParamersToMap(request);

		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addIntCondition("OutBean.type", "=",
				OutConstant.OUT_TYPE_INBILL);

		String vtype = request.getParameter("vtype");

		if (!StringTools.isNullOrNone(vtype))
		{
			condtion.addIntCondition("OutBean.vtype", "=", vtype);
		}

		String outTime = request.getParameter("outTime");

		String outTime1 = request.getParameter("outTime1");

		if (!StringTools.isNullOrNone(outTime))
		{
			condtion.addCondition("OutBean.outTime", ">=", outTime);
		}

		if (!StringTools.isNullOrNone(outTime1))
		{
			condtion.addCondition("OutBean.outTime", "<=", outTime1);
		}

		String changeTime = request.getParameter("changeTime");

		String changeTime1 = request.getParameter("changeTime1");

		if (!StringTools.isNullOrNone(changeTime))
		{
			condtion.addCondition("OutBean.changeTime", ">=", changeTime
					+ " 00:00:00");
		}

		if (!StringTools.isNullOrNone(changeTime1))
		{
			condtion.addCondition("OutBean.changeTime", "<=", changeTime1
					+ " 23:59:59");
		}

		// add 2012.7.2 当没有选择时间才给outTime默认日期
		if (StringTools.isNullOrNone(changeTime)
				&& StringTools.isNullOrNone(changeTime1)
				&& StringTools.isNullOrNone(outTime)
				&& StringTools.isNullOrNone(outTime1))
		{

			condtion.addCondition("OutBean.outTime", ">=",
					TimeTools.now_short(-30));

			request.setAttribute("outTime", TimeTools.now_short(-30));

			queryOutCondtionMap.put("outTime", TimeTools.now_short(-30));

			condtion.addCondition("OutBean.outTime", "<=",
					TimeTools.now_short());

			request.setAttribute("outTime1", TimeTools.now_short());

			queryOutCondtionMap.put("outTime1", TimeTools.now_short());

		}

		String id = request.getParameter("id");

		if (!StringTools.isNullOrNone(id))
		{
			condtion.addCondition("OutBean.fullid", "like", id);
		}

		String status = request.getParameter("status");
		
		_logger.debug("status: "+ status );

		if (!StringTools.isNullOrNone(status))
		{
			if ("99".equals(status))
			{
				condtion.addCondition(" and OutBean.status in (3, 4)");
			}
			else
			{
				condtion.addIntCondition("OutBean.status", "=", status);
			}
		}

		String customerId = request.getParameter("customerId");

		if (!StringTools.isNullOrNone(customerId))
		{
			condtion.addCondition("OutBean.customerId", "=", customerId);
		}
		
		String operatorName = request.getParameter("operatorName");

		if (!StringTools.isNullOrNone(operatorName))
		{
			condtion.addCondition("OutBean.operatorName", "like", operatorName);
		}

		String industryId = request.getParameter("industryId");

		if (!StringTools.isNullOrNone(industryId))
		{
			condtion.addCondition("OutBean.industryId", "=", industryId);
		}

		String customerName = request.getParameter("customerName");

		if (!StringTools.isNullOrNone(customerName))
		{
			condtion.addCondition("OutBean.customerName", "like", customerName);
		}

		String stafferName = request.getParameter("stafferName");

		if (!StringTools.isNullOrNone(stafferName))
		{
			condtion.addCondition("OutBean.stafferName", "like", stafferName);
		}

		String outType = request.getParameter("outType");

		if (!StringTools.isNullOrNone(outType))
		{
			condtion.addIntCondition("OutBean.outType", "=", outType);
		}

		String location = request.getParameter("location");

		if (!StringTools.isNullOrNone(location))
		{
			condtion.addCondition("OutBean.location", "=", location);
		}

		String destinationId = request.getParameter("destinationId");

		if (!StringTools.isNullOrNone(destinationId))
		{
			condtion.addCondition("OutBean.destinationId", "=", destinationId);
		}

		String transportNo = request.getParameter("transportNo");
		if (!StringTools.isNullOrNone(transportNo)){
			condtion.addCondition("OutBean.transportNo", "=", transportNo);
		}

		String outbackStatus = request.getParameter("outbackStatus");
		if (!StringTools.isNullOrNone(outbackStatus)){
			condtion.addCondition("OutBean.outbackStatus", "=", outbackStatus);
		}

		String inway = request.getParameter("inway");

		if (!StringTools.isNullOrNone(inway))
		{
			condtion.addIntCondition("OutBean.inway", "=", inway);
		}

		String autoOther = request.getParameter("autoOther");

		if (!StringTools.isNullOrNone(autoOther))
		{
			if ("0".equals(autoOther))
			{
				condtion.addCondition("OutBean.description", "like", "自动生成");
			}
			else
			{
				condtion.addCondition("and OutBean.description not like '%"
						+ "自动生成" + "%'");
			}
		}
		
		String hasConfirm = request.getParameter("hasConfirm");

		if (!StringTools.isNullOrNone(hasConfirm))
		{
			condtion.addIntCondition("OutBean.hasConfirm", "=", hasConfirm);
		}

		StafferBean staffer = Helper.getStaffer(request);

		// 这里是过滤
		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		// 分公司经理查询
		if ("1".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.STATUS_LOCATION_MANAGER_CHECK);

				request.setAttribute("status",
						OutConstant.STATUS_LOCATION_MANAGER_CHECK);

				queryOutCondtionMap.put("status", String
						.valueOf(OutConstant.STATUS_LOCATION_MANAGER_CHECK));
			}

			condtion.addCondition("and OutBean.industryId in "
					+ getAllIndustryId(staffer));
		}
		// 总裁审核
		else if ("2".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.STATUS_CEO_CHECK);

				request.setAttribute("status", OutConstant.STATUS_CEO_CHECK);

				queryOutCondtionMap.put("status",
						String.valueOf(OutConstant.STATUS_CEO_CHECK));
			}
		}
		// 董事长审核
		else if ("3".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.STATUS_CHAIRMA_CHECK);

				request.setAttribute("status", OutConstant.STATUS_CHAIRMA_CHECK);

				queryOutCondtionMap.put("status",
						String.valueOf(OutConstant.STATUS_CHAIRMA_CHECK));
			}
		}
		// 库管调拨
		else if ("4".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addCondition("and OutBean.status in (3, 4)");
				//2015/10/28 过滤掉入库换货
				condtion.addCondition("and OutBean.outType!="+OutConstant.OUTTYPE_IN_EXCHANGE);

				condtion.addIntCondition("OutBean.inway", "=",
						OutConstant.IN_WAY);

				request.setAttribute("inway", OutConstant.IN_WAY);

				queryOutCondtionMap.put("inway",
						String.valueOf(OutConstant.IN_WAY));
			}

			setDepotCondotionInBuy(user, condtion);
		}
		// 领样退库/销售退库
        //2015/10/2 入库-换货也走此流程
		else if ("5".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.BUY_STATUS_SUBMIT);

				request.setAttribute("status", OutConstant.BUY_STATUS_SUBMIT);

				queryOutCondtionMap.put("status",
						String.valueOf(OutConstant.BUY_STATUS_SUBMIT));

				// 领样退库/销售退库
                //2015/10/22 入库换货
				condtion.addCondition("and OutBean.outType in (4, 5, 7, 8)");
			}

			setLocalDepotConditionInBuy(user, condtion);
		}
		else if ("6".equals(queryType))
		{
			// 可以查询所有
            //2015/6/27 Filter by product name
            String productName = request.getParameter("product_name");
            if (!StringTools.isNullOrNone(productName)){
                condtion.addCondition(" and exists (select b.id from t_center_base b where b.outId=OutBean.fullId and b.productName like '%"+productName+"%')");
            }
		}
		// 业务员查询自己的销售退库
		else if ("7".equals(queryType))
		{
			condtion.addCondition("OutBean.stafferId", "=", user.getStafferId());

			condtion.addCondition("and OutBean.outType in (4, 5, 7)");
		}
		// 会计总部核对
		else if ("8".equals(queryType))
		{
			if (OldPageSeparateTools.isMenuLoad(request))
			{
				condtion.addIntCondition("OutBean.status", "=",
						OutConstant.STATUS_PASS);

				request.setAttribute("status", OutConstant.STATUS_PASS);

				queryOutCondtionMap.put("status",
						String.valueOf(OutConstant.STATUS_PASS));
			}
		}
		// 事业部经理领样退库/销售退库
		else if ("9".equals(queryType))
		{
			condtion.addIntCondition("OutBean.status", "=",
					OutConstant.STATUS_SAVE);

			request.setAttribute("status", OutConstant.STATUS_SAVE);

			queryOutCondtionMap.put("status",
					String.valueOf(OutConstant.STATUS_SAVE));

			// 领样退库/销售退库/赠品退货
			condtion.addCondition("and OutBean.outType in (4, 5, 7)");

			condtion.addCondition("and OutBean.industryId in "
					+ getAllIndustryId(staffer));
		}
		// 财务审核
		else if ("10".equals(queryType))
		{
			condtion.addIntCondition("OutBean.status", "=",
					OutConstant.BUY_STATUS_SECOND_PASS);

			request.setAttribute("status", OutConstant.BUY_STATUS_SECOND_PASS);

			queryOutCondtionMap.put("status",
					String.valueOf(OutConstant.BUY_STATUS_SECOND_PASS));
		}
		// 未知的则什么都没有
		else
		{
			condtion.addFlaseCondition();
		}
		
		String buyReturnFlag = RequestTools.getValueFromRequest(request,
				"buyReturnFlag");
		if(!"1".equals(buyReturnFlag)){
			buyReturnFlag = "0";
		}
		condtion.addIntCondition("OutBean.buyReturnFlag", "=", buyReturnFlag);
		request.setAttribute("buyReturnFlag", buyReturnFlag);
		queryOutCondtionMap.put("buyReturnFlag", buyReturnFlag);	
		

		if (!condtion.containOrder())
		{
			condtion.addCondition("order by OutBean.id desc");
		}

		request.getSession().setAttribute("ppmap", queryOutCondtionMap);
        _logger.info("**********condition*********"+condtion.toString());

		return condtion;
	}

	/**
	 * 设置仓库的过滤条件
	 * 
	 * @param user
	 * @param condtion
	 */
	protected void setDepotCondotionInBuy(User user, ConditionParse condtion)
	{
		// 只能看到自己的仓库
		List<AuthBean> depotAuthList = userManager.queryExpandAuthById(
				user.getId(), AuthConstant.EXPAND_AUTH_DEPOT);

		if (ListTools.isEmptyOrNull(depotAuthList))
		{
			// 永远也没有结果
			condtion.addFlaseCondition();
		}
		else
		{
			StringBuffer sb = new StringBuffer();

			sb.append("and (");
			for (Iterator iterator = depotAuthList.iterator(); iterator
					.hasNext();)
			{
				AuthBean authBean = (AuthBean) iterator.next();

				// 接受仓库是自己管辖的
				if (iterator.hasNext())
				{
					sb.append("OutBean.destinationId = '" + authBean.getId()
							+ "' or ");
				}
				else
				{
					sb.append("OutBean.destinationId = '" + authBean.getId()
							+ "'");
				}

			}

			sb.append(") ");

			condtion.addCondition(sb.toString());
		}
	}

	/**
	 * setDepotCondotionInOutBlance
	 * 
	 * @param user
	 * @param condtion
	 */
	protected void setDepotCondotionInOutBlance(User user,
			ConditionParse condtion)
	{
		// 只能看到自己的仓库
		List<AuthBean> depotAuthList = userManager.queryExpandAuthById(
				user.getId(), AuthConstant.EXPAND_AUTH_DEPOT);

		if (ListTools.isEmptyOrNull(depotAuthList))
		{
			// 永远也没有结果
			condtion.addFlaseCondition();
		}
		else
		{
			StringBuffer sb = new StringBuffer();

			sb.append("and (");

			for (Iterator iterator = depotAuthList.iterator(); iterator
					.hasNext();)
			{
				AuthBean authBean = (AuthBean) iterator.next();

				// 接受仓库是自己管辖的
				if (iterator.hasNext())
				{
					sb.append("OutBalanceBean.dirDepot = '" + authBean.getId()
							+ "' or ");
				}
				else
				{
					sb.append("OutBalanceBean.dirDepot = '" + authBean.getId()
							+ "'");
				}
			}

			sb.append(") ");

			condtion.addCondition(sb.toString());
		}
	}

	/**
	 * 设置发货仓库的过滤条件
	 * 
	 * @param user
	 * @param condtion
	 */
	protected void setLocalDepotConditionInBuy(User user,
			ConditionParse condtion)
	{
		// 只能看到自己的仓库
		List<AuthBean> depotAuthList = userManager.queryExpandAuthById(
				user.getId(), AuthConstant.EXPAND_AUTH_DEPOT);

		if (ListTools.isEmptyOrNull(depotAuthList))
		{
			// 永远也没有结果
			condtion.addFlaseCondition();
		}
		else
		{
			StringBuffer sb = new StringBuffer();

			sb.append("and (");
			for (Iterator iterator = depotAuthList.iterator(); iterator
					.hasNext();)
			{
				AuthBean authBean = (AuthBean) iterator.next();

				// 接受仓库是自己管辖的
				if (iterator.hasNext())
				{
					sb.append("OutBean.location = '" + authBean.getId()
							+ "' or ");
				}
				else
				{
					sb.append("OutBean.location = '" + authBean.getId() + "'");
				}

			}

			sb.append(") ");

			condtion.addCondition(sb.toString());
		}
	}

	/**
	 * 设置仓库的过滤条件
	 * 
	 * @param user
	 * @param condtion
	 */
	protected void setDepotCondotionInOut(User user, ConditionParse condtion)
	{
		// 只能看到自己的仓库
		List<AuthBean> depotAuthList = userManager.queryExpandAuthById(
				user.getId(), AuthConstant.EXPAND_AUTH_DEPOT);

		if (ListTools.isEmptyOrNull(depotAuthList))
		{
			// 永远也没有结果
			condtion.addFlaseCondition();
		}
		else
		{
			StringBuffer sb = new StringBuffer();

			sb.append("and (");
			for (Iterator iterator = depotAuthList.iterator(); iterator
					.hasNext();)
			{
				AuthBean authBean = (AuthBean) iterator.next();

				// 接受仓库是自己管辖的
				if (iterator.hasNext())
				{
					sb.append("OutBean.location = '" + authBean.getId()
							+ "' or ");
				}
				else
				{
					sb.append("OutBean.location = '" + authBean.getId() + "'");
				}

			}

			sb.append(") ");

			condtion.addCondition(sb.toString());
		}
	}

	/**
	 * 业务员预警
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryWarnOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		User user = (User) request.getSession().getAttribute("user");

		ConditionParse condtion = new ConditionParse();

		// 获得条件
		getCondition(condtion, user.getStafferName());

		List<OutVO> list = outDAO.queryEntityVOsByCondition(condtion);

		long current = new Date().getTime();

		for (Iterator iterator = list.iterator(); iterator.hasNext();)
		{
			OutVO outBean = (OutVO) iterator.next();

			Date temp = TimeTools.getDateByFormat(outBean.getRedate(),
					"yyyy-MM-dd");

			if (temp != null)
			{
				if (temp.getTime() > current)
				{
					iterator.remove();
				}
			}
		}

		handlerFlow(request, list, false);

		// 提示页面
		getDivs(request, list);

		request.setAttribute("listOut1", list);

		return mapping.findForward("queryWarnOut");
	}

	/**
	 * queryShow
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryShow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		Map<String, String> ssmap = CommonTools.saveParamersToMap(request);

		String[] showIds = request.getParameterValues("showId");

		// showIds
		List<String> showIdList = new ArrayList();

		if (showIds != null && showIds.length > 0)
		{
			for (String string : showIds)
			{
				showIdList.add(string);
			}
		}

		request.getSession().setAttribute("showIds", showIdList);

		request.getSession().setAttribute("ssmap", ssmap);

		List<SailConfigVO> resultList = new ArrayList();

		String dutyId = ssmap.get("duty");

		DutyBean duty = dutyDAO.find(dutyId);

		for (String showId : showIdList)
		{
			ConditionParse condtion = new ConditionParse();

			condtion.addWhereStr();

			condtion.addIntCondition("finType" + duty.getType(), "=",
					ssmap.get("finType"));

			condtion.addIntCondition("ratio" + duty.getType(), ">", 0);

			condtion.addIntCondition("sailType", "=", ssmap.get("sailType"));

			condtion.addIntCondition("productType", "=",
					ssmap.get("productType"));

			condtion.addCondition("showId", "=", showId);

			List<SailConfigVO> eachtList = sailConfigDAO
					.queryEntityVOsByCondition(condtion);

			if (resultList.isEmpty())
			{
				resultList.addAll(eachtList);
			}
			else
			{
				for (Iterator iterator = resultList.iterator(); iterator
						.hasNext();)
				{
					SailConfigVO sailConfigVO = (SailConfigVO) iterator.next();

					boolean hasIn = false;

					for (SailConfigVO each : eachtList)
					{
						String property1 = BeanUtil.getProperty(sailConfigVO,
								"ratio" + duty.getType());
						String property2 = BeanUtil.getProperty(each, "ratio"
								+ duty.getType());

						if (!"".equals(property1)
								&& property1.equals(property2))
						{
							hasIn = true;
							break;
						}
					}

					if (!hasIn)
					{
						iterator.remove();
					}
				}
			}
		}

		for (SailConfigVO sailConfigVO : resultList)
		{
			String property1 = BeanUtil.getProperty(sailConfigVO, "ratio"
					+ duty.getType());

			sailConfigVO.setRatio0(CommonTools.parseInt(property1));
		}

		List<ShowBean> showList = (List<ShowBean>) request.getSession()
				.getAttribute("g_showList");

		if (showIdList != null)
		{
			for (ShowBean showBean : showList)
			{
				if (showIdList.contains(showBean.getId()))
				{
					showBean.setDescription("1");
				}
				else
				{
					showBean.setDescription("0");
				}
			}
		}

		List<DutyBean> dutyList = dutyDAO.listEntityBeans();

		request.setAttribute("dutyList", dutyList);

		request.getSession().setAttribute("navigationList", resultList);

		// 进入导航页面
		return mapping.findForward("navigationAddOut1");
	}

	/**
	 * getCondition
	 * 
	 * @param condtion
	 * @param stafferName
	 */
	protected void getCondition(ConditionParse condtion, String stafferName)
	{
		// 只查询销售单
		condtion.addIntCondition("OutBean.type", "=",
				OutConstant.OUT_TYPE_OUTBILL);

		condtion.addIntCondition("OutBean.status", "=", OutConstant.STATUS_PASS);

		condtion.addCondition("OutBean.STAFFERNAME", "=", stafferName);

		condtion.addIntCondition("OutBean.pay", "=", OutConstant.PAY_NOT);

		condtion.addCondition("OutBean.reday", "<>", "0");
	}

	/**
	 * 删除库单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward delOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		String fullId = request.getParameter("outId");

		User user = (User) request.getSession().getAttribute("user");

		String reason = request.getParameter("reason");

		if (StringTools.isNullOrNone(fullId))
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在，请重新操作");

			return mapping.findForward("error");
		}

		OutBean bean = outDAO.find(fullId);

		if (bean == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "库单不存在，请重新操作");

			return mapping.findForward("error");
		}

		// 商务 - begin
		ActionForward error = checkAuthForEcommerce(request, user, mapping);

		if (null != error)
		{
			return error;
		}

		User g_srcUser = (User) request.getSession().getAttribute("g_srcUser");

		String operatorName = "";

		if (null != g_srcUser) operatorName = g_srcUser.getStafferName();

		// 商务 - end

		if (bean.getStatus() == OutConstant.STATUS_SAVE
				|| bean.getStatus() == OutConstant.STATUS_REJECT)
		{
			try
			{
				//#230 直接删除
				outManager.delOut(user, fullId);
//				outManager.rejectOutBack(user, fullId, reason);

				_logger.info(operatorName + "/" + user.getName() + "删除了库单:"
						+ fullId);

				request.setAttribute(KeyConstant.MESSAGE, "库单删除成功:" + fullId);
			}
			catch (MYException e)
			{
				_logger.warn(e, e);

				request.setAttribute(KeyConstant.ERROR_MESSAGE, "流程异常，请重新操作:"
						+ e.toString());

				return mapping.findForward("error");
			}
		}
		else
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "只有保存态,驳回态的库单才可以删除");

			return mapping.findForward("error");
		}

		CommonTools.removeParamers(request);

		RequestTools.actionInitQuery(request);

		if (bean.getType() == OutConstant.OUT_TYPE_OUTBILL)
		{
			return querySelfOut(mapping, form, request, reponse);
		}
		else
		{
			return querySelfBuy(mapping, form, request, reponse);
		}
	}

	/**
	 * 查询REF的入库单(已经通过的,退库的)
	 * 
	 * @param request
	 * @param outId
	 * @return
	 */
	protected List<OutBean> queryRefOut(HttpServletRequest request, String outId)
	{
		// 查询当前已经有多少个人领样
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addCondition(" and OutBean.status in (3, 4)");

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		// 排除其他入库(对冲单据)
		// con.addIntCondition("OutBean.outType", "<>",
		// OutConstant.OUTTYPE_IN_OTHER);

		con.addCondition("OutBean.reserve8", "<>", "1");

		List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

		for (OutBean outBean : refBuyList)
		{
			List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean
					.getFullId());

			outBean.setBaseList(list);
		}

		request.setAttribute("refBuyList", refBuyList);

		return refBuyList;
	}

	/**
	 * 查询REF的入库单(已经通过的,退库的)
	 * 
	 * @param request
	 * @param outId
	 * @return
	 */
	protected List<OutBean> queryRefOut1(HttpServletRequest request,
			String outId)
	{
		// 查询当前已经有多少个人领样
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		// con.addCondition(" and OutBean.status in (1,3, 4)");

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		// 排除其他入库(对冲单据)
		// con.addIntCondition("OutBean.outType", "<>",
		// OutConstant.OUTTYPE_IN_OTHER);

		con.addCondition("OutBean.reserve8", "<>", "1");

		List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

		for (OutBean outBean : refBuyList)
		{
			List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean
					.getFullId());

			outBean.setBaseList(list);
		}

		request.setAttribute("refBuyList", refBuyList);

		return refBuyList;
	}

	protected void queryRefOut2(HttpServletRequest request, String outId)
	{
		// 验证ref
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

		List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

		request.setAttribute("refOutList", refList);
	}

	protected void queryRefOut3(HttpServletRequest request, String outId)
	{
		// 验证ref
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		con.addIntCondition("OutBean.outType", "=",
				OutConstant.OUTTYPE_IN_OTHER);

		List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

		request.setAttribute("refOutToBuyList", refList);
	}

	/**
	 * getDivs
	 * 
	 * @param request
	 * @param list
	 */
	protected void getDivs(HttpServletRequest request, List list)
	{
		Map divMap = new HashMap();
        List<String> fullIdList = new ArrayList<String>();

		String queryType = RequestTools.getValueFromRequest(request,
				"queryType");

		// 是否可以看到真实的成本
		boolean containAuth = userManager.containAuth(Helper.getUser(request)
				.getId(), AuthConstant.SAIL_QUERY_COST);

		if (list != null)
		{
			for (Object each : list)
			{
				OutBean bean = (OutBean) each;

				try
				{
					List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(bean
							.getFullId());

					for (BaseBean baseBean : baseList)
					{
						// 销售价低于成本
						if ("2".equals(queryType))
						{
							if (bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
							{
								break;
							}

							if (baseBean.getPrice() < baseBean.getCostPrice())
							{
								bean.setReserve9("1");

								break;
							}
						}
					}

					if (OATools.isChangeToV5())
					{
						if (!containAuth)
						{
							for (BaseBean baseBean : baseList)
							{
								// 显示成本
								baseBean.setCostPrice(baseBean.getInputPrice());
							}
						}
					}

					divMap.put(bean.getFullId(),
							OutHelper.createTable(baseList, bean.getTotal()));
				}
				catch (Exception e)
				{
					_logger.error("addOut", e);
				}
			}
		}

		request.setAttribute("divMap", divMap);

	}

    /**
     * Get DIV for querySelfOut
     * @param request
     * @param list
     * @return
     */
    protected List<String> getDivsForSelfOut(HttpServletRequest request, List list)
    {
        String productName = request.getParameter("product_name");
        System.out.println("********product**********"+productName);
        Map divMap = new HashMap();
        List<String> fullIdList = new ArrayList<String>();

        String queryType = RequestTools.getValueFromRequest(request,
                "queryType");

        // 是否可以看到真实的成本
        boolean containAuth = userManager.containAuth(Helper.getUser(request)
                .getId(), AuthConstant.SAIL_QUERY_COST);

        if (list != null)
        {
            for (Object each : list)
            {
                OutBean bean = (OutBean) each;

                try
                {
                    List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(bean
                            .getFullId());

                    for (BaseBean baseBean : baseList)
                    {
                        // 销售价低于成本
                        if ("2".equals(queryType))
                        {
                            if (bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
                            {
                                break;
                            }

                            if (baseBean.getPrice() < baseBean.getCostPrice())
                            {
                                bean.setReserve9("1");

                                break;
                            }
                        }
                    }

                    //check productName
                    if (!StringTools.isNullOrNone(productName)){
                        for (BaseBean baseBean : baseList)
                        {
                            if (baseBean.getProductName().indexOf(productName)!=-1){
                                System.out.println("Product found***********"+baseBean.getProductName());
                                fullIdList.add(bean.getFullId());
                                break;
                            }
                        }
                    }


                    if (OATools.isChangeToV5())
                    {
                        if (!containAuth)
                        {
                            for (BaseBean baseBean : baseList)
                            {
                                // 显示成本
                                baseBean.setCostPrice(baseBean.getInputPrice());
                            }
                        }
                    }

                    divMap.put(bean.getFullId(),
                            OutHelper.createTable(baseList, bean.getTotal()));
                }
                catch (Exception e)
                {
                    _logger.error("addOut", e);
                }
            }
        }

        request.setAttribute("divMap", divMap);
        return fullIdList;

    }

	/**
	 * 校验商品是否满足 促销规则,并根据数量与金额计算折扣金额
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward checkPromotionAndRetPromValue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse reponse) throws ServletException
	{
		AjaxResult ajax = new AjaxResult();

		// 产品-数量~产品-数量~ -- 20121126 thisOrder 为 本单单号
		String thisOrder = request.getParameter("thisOrder");

		String hisOrders = request.getParameter("hisOrders");

		String eventId = request.getParameter("eventId");

		String flag = request.getParameter("flag");

		// 不包含历史销售单
		try
		{
			checkParameter(thisOrder, hisOrders, eventId, flag);

		}
		catch (MYException e1)
		{
			ajax.setError(e1.getErrorContent());

			return JSONTools.writeResponse(reponse, ajax);
		}

		if (ajax.getRet() == 1)
		{
			return JSONTools.writeResponse(reponse, ajax);
		}

		PromotionBean promBean = promotionDAO.find(eventId);

		if (null == promBean)
		{
			ajax.setError("活动号 " + eventId + " 不存在");

			return JSONTools.writeResponse(reponse, ajax);
		}

		List<PromotionItemBean> promItemList = promotionItemDAO
				.queryEntityBeansByFK(eventId);

		// String [] products = thisOrder.split("~");
		String fullId = thisOrder;

		List<BaseBean> lists = new ArrayList<BaseBean>();

		List<BaseBean> tempList = null;

		//OutBean currOut = outDAO.find(fullId);

		tempList = baseDAO.queryEntityBeansByFK(fullId);

		lists.addAll(tempList);

		// 绑定历史单据
		if (flag.equals("1"))
		{
			String[] orders = hisOrders.split("~");

			for (String outId : orders)
			{
				//OutBean hisOut = outDAO.find(outId);

				List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);

				lists.addAll(baseList);
			}

			// 绑定历史单据,要减去退货部分
			List<BaseBean> backBaseList = new ArrayList<BaseBean>();

			ConditionParse condition = new ConditionParse();

			for (String outId : orders)
			{
				condition.clear();

				condition.addWhereStr();

				condition.addCondition("OutBean.refOutFullId", "=", outId);

				// condition.addCondition(" and OutBean.status in (3, 4)");

				condition.addIntCondition("OutBean.type", "=",
						OutConstant.OUT_TYPE_INBILL);

				condition.addIntCondition("OutBean.outType", "=",
						OutConstant.OUTTYPE_IN_OUTBACK);

				List<OutBean> refBuyList = outDAO
						.queryEntityBeansByCondition(condition);

				for (OutBean outBean : refBuyList)
				{
					List<BaseBean> blist = baseDAO.queryEntityBeansByFK(outBean.getFullId());

					backBaseList.addAll(blist);
				}
			}

			for (BaseBean backBaseBean : backBaseList)
			{
				backBaseBean.setAmount(-backBaseBean.getAmount());
				backBaseBean.setValue(-backBaseBean.getValue());
			}

			lists.addAll(backBaseList);
		}

		BaseBean bean = null;
		try
		{
			bean = computeValidProduct(promBean, promItemList, lists);
		}
		catch (MYException e)
		{
			ajax.setError(e.getErrorContent());

			return JSONTools.writeResponse(reponse, ajax);
		}

		PromotionWrap promWrap = new PromotionWrap();

		computePromValue(promBean, bean, promWrap);

		ajax.setSuccess(promWrap);

		return JSONTools.writeResponse(reponse, ajax);

	}

	/**
	 * 计算可折扣的金额
	 * 
	 * @param promBean
	 * @param bean
	 * @param promWrap
	 */
	private void computePromValue(PromotionBean promBean, BaseBean bean,
			PromotionWrap promWrap)
	{

		double promValue = 0.0d;

		promWrap.setRet(0);

		// 检查是否满足礼包规则要求
		if (promBean.getGiftBag() == PromotionConstant.GIFTBAG_LIMIT)
		{
			// 4 表示不满足
			if (bean.getInway() == 4)
			{
				promWrap.setRet(PromotionConstant.ERROR_GIFTBAG);

				return;
			}

		}

		// 校验是否符合折扣规则
		// 1.不限数量，则只检查金额
		if ((promBean.getMinAmount() == 0 && promBean.getMaxAmount() == 0)
				|| (bean.getAmount() >= promBean.getMinAmount() && bean
						.getAmount() <= promBean.getMaxAmount()))
		{
			if (bean.getValue() >= promBean.getMinMoney())
			{
				// a)直降
				if (promBean.getRebateType() == PromotionConstant.REBATETYPE_DOWN)
				{
					promValue = promBean.getRebateMoney();

				}
				else
				{
					if (promBean.getRebateType() == PromotionConstant.REBATETYPE_ZK)
					{
						promValue = bean.getValue()
								* (promBean.getRebateRate() / 1000.0d);

						if (promBean.getMaxRebateMoney() > 0
								&& promValue > promBean.getMaxRebateMoney())
						{
							promValue = promBean.getMaxRebateMoney();
						}

					}
				}

			}
			else
			{
				// 金额不满足- ret = 1
				promWrap.setRet(PromotionConstant.ERROR_MONEY);
			}

		}
		else
		{
			// 数量不满足 - ret = 2
			promWrap.setRet(PromotionConstant.ERROR_AMOUNT);
		}

		// 折扣金额大于参加活动商品的总金额是不行的 ret = 3
		if (bean.getValue() < promValue)
		{
			promWrap.setRet(PromotionConstant.ERROR_LESSPROMVALUE);
		}

		promWrap.setPromValue(promValue);
	}

	/**
	 * 计算符合规则的商品的数量及金额
	 * 
	 * @param promItemList
	 *            选择的活动
	 * @param lists
	 *            所有参加活动的商品
	 * @return
	 * @throws MYException
	 */
	private BaseBean computeValidProduct(PromotionBean bean,
			List<PromotionItemBean> promItemList, List<BaseBean> lists)
			throws MYException
	{

		double allOutValue = 0.0d;

		int allAmount = 0;

		Set<String> productIdSet = new HashSet<String>();

		for (BaseBean basebean : lists)
		{

			String productId = basebean.getProductId();
			int amount = basebean.getAmount();
			double outValue = basebean.getValue();

			// 检查商品属性是否符合活动范围
			ProductBean productBean = productDAO.find(productId);

			if (null == productBean)
			{
				throw new MYException("数据错误");
			}

			for (PromotionItemBean promItemBean : promItemList)
			{

				if (!StringTools.isNullOrNone(promItemBean.getProductId())
						&& !"0".equals(promItemBean.getProductId()))
				{

					if (productId.equals(promItemBean.getProductId()))
					{
						allAmount += amount;

						allOutValue += outValue;

						if (!productIdSet.contains(productId)) productIdSet
								.add(productId);

						continue;
					}

				}
				else
				{

					if (promItemBean.getProductType() != -1)
					{

						if (productBean.getType() == promItemBean
								.getProductType())
						{
							allAmount += amount;

							allOutValue += outValue;

							continue;
						}
					}

					if (promItemBean.getSailType() != -1)
					{
						if (productBean.getSailType() == promItemBean
								.getSailType())
						{
							allAmount += amount;

							allOutValue += outValue;

							continue;
						}

					}

				}

			}

		}

		BaseBean tempBean = new BaseBean();

		// 礼包则要求活动中的商品在销售单都包含
		if (bean.getGiftBag() == PromotionConstant.GIFTBAG_LIMIT)
		{
			int count = 0;
			for (PromotionItemBean promItemBean : promItemList)
			{

				if (!StringTools.isNullOrNone(promItemBean.getProductId())
						&& !"0".equals(promItemBean.getProductId()))
				{
					count++;
				}
			}

			// 相等表示 活动中的商品都参加了
			if (productIdSet.size() != count)
			{
				tempBean.setInway(4);
			}
		}

		tempBean.setAmount(allAmount);
		tempBean.setValue(allOutValue);

		return tempBean;

	}

	/**
	 * 
	 * @param thisOrder
	 * @param hisOrders
	 * @param eventId
	 * @param flag
	 * @throws MYException
	 */
	private void checkParameter(String thisOrder, String hisOrders,
			String eventId, String flag) throws MYException
	{

		if (StringTools.isNullOrNone(thisOrder))
		{
			throw new MYException("数据错误");

		}

		if (StringTools.isNullOrNone(eventId))
		{

			throw new MYException("数据错误,规则为空");

		}

		if (flag.equals("1"))
		{
			if (StringTools.isNullOrNone(hisOrders))
			{
				throw new MYException("数据错误,绑定历史单据为空");

			}
		}
	}

	protected ActionForward checkAuthForEcommerce(HttpServletRequest request,
			User user, ActionMapping mapping)
	{
		// 针对非商务模式下，业务员开单要有权限限制
		String elogin = (String) request.getSession().getAttribute("g_elogin");

		String g_loginType = (String) request.getSession().getAttribute(
				"g_loginType");

		// elogin 为空，则表示非商务模式, elogin 1 表示是商务切换登陆
		if (StringTools.isNullOrNone(elogin)
				|| (elogin.equals("1") && g_loginType.equals("2")))
		{
			// 检查是否有权限
			if (!containAuth(user, AuthConstant.DIRECT_SALE))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "非商务模式下,没有权限操作");

				return mapping.findForward("error");
			}
		}
		return null;
	}

	protected void sendOutRejectMail(String fullId, User user, String reason,
			OutBean out, String subject)
	{
		StafferBean rejectorBean = stafferDAO.find(user.getStafferId());

		StafferBean approverBean = stafferDAO.find(out.getStafferId());

		String customerName = out.getCustomerName();

		if (null != approverBean && null != rejectorBean)
		{
			StringBuffer sb = new StringBuffer();

			sb.append("系统发送>>>").append("<br>").append("单号:" + fullId)
					.append(",").append("<br>").append("客户:" + customerName)
					.append(",").append("<br>").append("总金额:" + out.getTotal())
					.append(",").append("<br>")
					.append("审批人:" + rejectorBean.getName()).append(",")
					.append("<br>").append("审批结果:驳回").append(",")
					.append("<br>").append("审批意见:" + reason).append(",")
					.append("<br>")
					.append("审批人电话:" + rejectorBean.getHandphone());

			String message = sb.toString();

			String to = approverBean.getNation();

			_logger.info(message);

			commonMailManager.sendMail(to, subject, message);
		}
	}

	public StafferVSPriDAO getStafferVSPriDAO()
	{
		return stafferVSPriDAO;
	}

	public void setStafferVSPriDAO(StafferVSPriDAO stafferVSPriDAO)
	{
		this.stafferVSPriDAO = stafferVSPriDAO;
	}

	public FinanceFacade getFinanceFacade()
	{
		return financeFacade;
	}

	public void setFinanceFacade(FinanceFacade financeFacade)
	{
		this.financeFacade = financeFacade;
	}

	/**
	 * @return the batchReturnLogDAO
	 */
	public BatchReturnLogDAO getBatchReturnLogDAO()
	{
		return batchReturnLogDAO;
	}

	/**
	 * @param batchReturnLogDAO the batchReturnLogDAO to set
	 */
	public void setBatchReturnLogDAO(BatchReturnLogDAO batchReturnLogDAO)
	{
		this.batchReturnLogDAO = batchReturnLogDAO;
	}

    public PriceConfigDAO getPriceConfigDAO() {
        return priceConfigDAO;
    }

    public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO) {
        this.priceConfigDAO = priceConfigDAO;
    }

    public PriceConfigManager getPriceConfigManager() {
        return priceConfigManager;
    }

    public void setPriceConfigManager(PriceConfigManager priceConfigManager) {
        this.priceConfigManager = priceConfigManager;
    }

    public SailConfigManager getSailConfigManager() {
        return sailConfigManager;
    }

    public void setSailConfigManager(SailConfigManager sailConfigManager) {
        this.sailConfigManager = sailConfigManager;
    }

    public ProductFacade getProductFacade() {
        return productFacade;
    }

    public void setProductFacade(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    public ProductExchangeConfigDAO getProductExchangeConfigDAO() {
        return productExchangeConfigDAO;
    }

    public void setProductExchangeConfigDAO(ProductExchangeConfigDAO productExchangeConfigDAO) {
        this.productExchangeConfigDAO = productExchangeConfigDAO;
    }

	public PackageDAO getPackageDAO() {
		return packageDAO;
	}

	public void setPackageDAO(PackageDAO packageDAO) {
		this.packageDAO = packageDAO;
	}

	public InvoiceinsDAO getInvoiceinsDAO() {
		return invoiceinsDAO;
	}

	public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO) {
		this.invoiceinsDAO = invoiceinsDAO;
	}

	public InsVSOutDAO getInsVSOutDAO() {
		return insVSOutDAO;
	}

	public void setInsVSOutDAO(InsVSOutDAO insVSOutDAO) {
		this.insVSOutDAO = insVSOutDAO;
	}

	public PresentFlagDAO getPresentFlagDAO() {
		return presentFlagDAO;
	}

	public void setPresentFlagDAO(PresentFlagDAO presentFlagDAO) {
		this.presentFlagDAO = presentFlagDAO;
	}

    public TwOutDAO getTwOutDAO() {
        return twOutDAO;
    }

    public void setTwOutDAO(TwOutDAO twOutDAO) {
        this.twOutDAO = twOutDAO;
    }

    public TwBaseDAO getTwBaseDAO() {
        return twBaseDAO;
    }

    public void setTwBaseDAO(TwBaseDAO twBaseDAO) {
        this.twBaseDAO = twBaseDAO;
    }

    public TwDistributionDAO getTwDistributionDAO() {
        return twDistributionDAO;
    }

    public void setTwDistributionDAO(TwDistributionDAO twDistributionDAO) {
        this.twDistributionDAO = twDistributionDAO;
    }

	public StorageRelationDAO getStorageRelationDAO() {
		return storageRelationDAO;
	}

	public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO) {
		this.storageRelationDAO = storageRelationDAO;
	}
    
}
