package com.china.center.oa.client.action;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.china.center.oa.client.bean.*;
import com.china.center.oa.client.dao.*;
import com.china.center.oa.client.vs.DestStafferVSCustomerBean;
import com.china.center.oa.publics.bean.*;
import com.china.center.oa.publics.dao.*;
import com.china.center.oa.sail.bean.ExpressBean;
import com.china.center.oa.sail.dao.ExpressDAO;
import com.china.center.tools.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.jfree.data.category.CategoryDataset;

import com.center.china.osgi.publics.User;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.jsonimpl.OprMap;
import com.china.center.actionhelper.query.CommonQuery;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.facade.ClientFacade;
import com.china.center.oa.client.manager.AppUserManager;
import com.china.center.oa.client.manager.ClientManager;
import com.china.center.oa.client.vo.AppUserVO;
import com.china.center.oa.client.vo.CustomerCorporationApplyVO;
import com.china.center.oa.client.vo.CustomerCorporationVO;
import com.china.center.oa.client.vo.CustomerDepartApplyVO;
import com.china.center.oa.client.vo.CustomerDepartVO;
import com.china.center.oa.client.vo.CustomerDistAddrApplyVO;
import com.china.center.oa.client.vo.CustomerDistAddrVO;
import com.china.center.oa.client.vo.CustomerIndividualApplyVO;
import com.china.center.oa.client.vo.CustomerIndividualVO;
import com.china.center.oa.client.vo.CustomerVO;
import com.china.center.oa.client.vo.DestStafferVSCustomerVO;
import com.china.center.oa.client.vo.StafferVSCustomerVO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.credit.dao.CreditLevelDAO;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.facade.PublicFacade;
import com.china.center.oa.publics.manager.CommonManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vs.RoleAuthBean;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;

import static com.china.center.oa.customer.constant.CustomerConstant.NATURE_INDIVIDUAL;

/**
 * 
 * 升级后的客户管理
 *
 * @author fangliwen 2013-11-22
 */
public class ClientAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());
	
	private ClientFacade clientFacade = null;
	
	private ClientManager clientManager = null;
	
	private CustomerApproveDAO customerApproveDAO = null;
	
	private CustomerMainDAO customerMainDAO = null;

	private CustomerIndividualDAO customerIndividualDAO = null;
	
	private UserManager userManager = null;
	
	private PublicFacade publicFacade = null;
	
	private StafferVSCustomerDAO stafferVSCustomerDAO = null;
	
	private CustomerContactApplyDAO customerContactApplyDAO = null;
	
	private CustomerBusinessApplyDAO customerBusinessApplyDAO = null;
	
	private CustomerContactDAO customerContactDAO = null;
	
	private CustomerBusinessDAO customerBusinessDAO = null;
	
	private CustomerDistAddrApplyDAO customerDistAddrApplyDAO = null;
	
	private CustomerDistAddrDAO customerDistAddrDAO = null;
	
	private AssignApplyDAO assignApplyDAO = null;
	
	private CommonManager commonManager = null;
	
	private StafferDAO stafferDAO = null;
	
	private LocationDAO locationDAO = null;
	
	private DestStafferVSCustomerDAO destStafferVSCustomerDAO = null;
	
	private CreditLevelDAO creditLevelDAO = null;
	
	private DutyDAO dutyDAO = null;
	
    private AppUserDAO appUserDAO = null;
    
    private AppUserApplyDAO appUserApplyDAO = null;
    
    private AppUserManager appUserManager = null;
    
    private AppUserVSCustomerDAO appUserVSCustomerDAO = null;
    
    private ChangeLogDAO changeLogDAO = null;
    
    private CustomerFormerNameDAO customerFormerNameDAO = null;

    private ProvinceDAO provinceDAO = null;

    private CityDAO cityDAO = null;

    private AreaDAO areaDAO = null;

    private EnumDAO enumDAO = null;

	private ExpressDAO expressDAO = null;
    
    private static String QUERYCLIENTBYSUBSTAFFER = "queryClientBySubStaffer";
	
	private static String QUERYAPPLYCLIENT = "queryApplyClient";
	
	private static String QUERYCLIENT = "queryClient";
	
	private static String QUERYCHECKADDAPPLYCLIENT = "queryCheckAddApplyClient";
	
	private static String QUERYCHECKAPPLYCLIENT = "queryCheckApplyClient";
	
	private static String QUERYCANASSIGNCLIENT = "queryCanAssignClient";
	
	private static String QUERYASSIGNAPPLY = "queryAssignApply";
	
	private static String QUERYCLIENTASSIGN = "queryClientAssign";
	
	private static String RPTQUERYCLIENTBATCHTRANS = "rptQueryClientBatchTrans";
	
	private static String RPTQUERYALLCLIENT = "rptQueryAllClient";
	
	private static String QUERYLOCATIONCLIENT = "queryLocationClient";
	
    private static String RPTQUERYSELFCLIENT = "rptQuerySelfClient";
    
    private static String RPTQUERYALLAPPUSER = "rptQueryAllAppUser";
    
    private static String QUERYAPPUSERAPPLY = "queryAppUserApply";
    
    private static String QUERYAPPUSER = "queryAppUser";
    
    private static String QUERYAPPLYCLIENTFORCREDIT = "queryApplyClientForCredit";
    
    private static String QUERYCHANGELOG = "queryChangeLog";
    
    private static String RPTQUERYSELFDEPARTCLIENT = "rptQuerySelfDepartClient";
    
    private static String RPTQUERYNOTUSECLIENT = "rptQueryNotUseClient";

    private static String shipping [] = new String []{"自提","公司","第三方快递","第三方货运","第三方快递+货运","空发"};
    private static int ishipping [] = new int []{0,1,2,3,4,99};
	private static String expressPay [] = new String []{"业务员支付","公司支付","客户支付"};
	private static int iexpressPay [] = new int []{1,2,3};
	private static String customerType [] = new String []{"个人客户","部门客户","组织客户"};
	private static int icustomerType [] = new int []{1,2,3};
    
	public ClientAction()
	{
		
	}
	
	/**
	 * queryApplyClient
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryApplyClient(ActionMapping mapping, ActionForm form,
            								HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		ConditionParse condtion = new ConditionParse();
		
		condtion.addWhereStr();
		
		User user = Helper.getUser(request);
		
		condtion.addIntCondition("CustomerApproveBean.opr", "<>", CustomerConstant.OPR_UPATE_CREDIT);
		
		condtion.addIntCondition("CustomerApproveBean.opr", "<>", CustomerConstant.OPR_UPATE_ASSIGNPER);
		
		// 只能看到自己的客户
		condtion.addCondition("CustomerApproveBean.applyId", "=", user.getStafferId());
		
		ActionTools.processJSONQueryCondition(QUERYAPPLYCLIENT, request, condtion);
		
		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPLYCLIENT, request,
		condtion, this.customerApproveDAO);
		
		return JSONTools.writeResponse(response, jsonstr);
	}
	
	/**
	 * delApplyClient
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward delApplyClient(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);
            
            clientFacade.delApplyClient(user.getId(), id);

            ajax.setSuccess("成功删除申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除申请失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
	
	/**
	 * preForAddClient
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MYException
	 */
	public ActionForward preForAddClient(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
	throws MYException
	{
		User user = Helper.getUser(request);
		
		String stype = request.getParameter("stype");
		
		request.setAttribute("stype", stype);
		
		  //商务 - begin
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
    	
    	if (null != error)
    	{
    		return error;
    	}
		
		return mapping.findForward("addClient");
	}
	
	/**
	 * addApplyClient
	 * 客户申请
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws MYException
	 */
	public ActionForward addOrUpdateApplyClient(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                         HttpServletResponse response)
	throws MYException
	{
		User user = Helper.getUser(request);
		
		String stype = request.getParameter("stype");
		
		String addOrUpdate = request.getParameter("addOrUpdate");
		
		String cid = "";
		
		if (addOrUpdate.equals("1"))
		 {
			cid = request.getParameter("id");
			
			CustomerBean cust = customerMainDAO.find(cid);
			
			if (null == cust)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "客户不存在，无法提交修改申请");
				
				return mapping.findForward("error");
			}
		 }
		
		  //商务 - begin
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
    	
    	if (null != error)
    	{
    		return error;
    	}

		try
		{
			String name = "";
			
			if (stype.equals("1"))
			{
				CustomerIndividualApplyBean individualBean = new CustomerIndividualApplyBean();
				
				BeanUtil.getBean(individualBean, request);
				
				name = individualBean.getName();
				
				individualBean.setCreaterId(user.getStafferId());
				
				individualBean.setUpdateId(user.getStafferId());
				individualBean.setUpdateTime(TimeTools.now());

				List<CustomerContactApplyBean> custContList = fillCustCont(request);

				List<CustomerBusinessApplyBean> custBusiList = fillCustBusi(request);
				
				List<CustomerDistAddrApplyBean> custAddrList = fillCustAddr(request);

				individualBean.setCustContList(custContList);
				individualBean.setCustBusiList(custBusiList);
				individualBean.setCustAddrList(custAddrList);
				
	            clientFacade.applyAddOrUpdateClient(user.getId(), individualBean, addOrUpdate);
	            
			}else if(stype.equals("2")) 
			{
				CustomerDepartApplyBean departBean = new CustomerDepartApplyBean();

				BeanUtil.getBean(departBean, request);

				name = departBean.getName();
				
				departBean.setCreaterId(user.getStafferId());
				departBean.setUpdateId(user.getStafferId());
				departBean.setUpdateTime(TimeTools.now());
				
				List<CustomerContactApplyBean> custContList = fillCustCont(request);

				List<CustomerBusinessApplyBean> custBusiList = fillCustBusi(request);
				
				List<CustomerDistAddrApplyBean> custAddrList = fillCustAddr(request);

				departBean.setCustContList(custContList);
				departBean.setCustBusiList(custBusiList);
				departBean.setCustAddrList(custAddrList);

				clientFacade.applyAddOrUpdateClient(user.getId(), departBean, addOrUpdate);
	            
			}else if(stype.equals("3"))
			{
				CustomerCorporationApplyBean corpBean = new CustomerCorporationApplyBean();
				
				BeanUtil.getBean(corpBean, request);
				
				name = corpBean.getName();
				
				corpBean.setCreaterId(user.getStafferId());
				corpBean.setUpdateId(user.getStafferId());
				corpBean.setUpdateTime(TimeTools.now());
				
				List<CustomerContactApplyBean> custContList = fillCustCont(request);
				List<CustomerBusinessApplyBean> custBusiList = fillCustBusi(request);
				
				List<CustomerDistAddrApplyBean> custAddrList = fillCustAddr(request);

				corpBean.setCustContList(custContList);
				corpBean.setCustBusiList(custBusiList);
				corpBean.setCustAddrList(custAddrList);

				clientFacade.applyAddOrUpdateClient(user.getId(), corpBean, addOrUpdate);
	            
			}else
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "申请失败:不存在的客户类型");
			}
			
			request.setAttribute(KeyConstant.MESSAGE, "成功申请:" + name);
			
		}catch(MYException e)
		{
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "申请失败:" + e.getMessage());
        }
		
		CommonTools.removeParamers(request);

		if (addOrUpdate.equals("1"))
			return mapping.findForward("querySelfClient");
		
        return mapping.findForward("queryApplyClient");
	}
	
	private ActionForward checkAuthForEcommerce(HttpServletRequest request, User user, ActionMapping mapping)
    {
        // 针对非商务模式下，业务员开单要有权限限制
        String elogin = (String)request.getSession().getAttribute("g_elogin");
        
        String g_loginType = (String)request.getSession().getAttribute("g_loginType");
        
        // elogin 为空，则表示非商务模式, elogin 1 表示是商务切换登陆
        if (StringTools.isNullOrNone(elogin) || (elogin.equals("1") && g_loginType.equals("2")))
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
    
    private boolean containAuth(User user, String authId)
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
	 * 
	 * @param request
	 * @return List<CustomerContactBean>
	 */
	private List<CustomerContactApplyBean> fillCustCont(HttpServletRequest request)
	{
		List<CustomerContactApplyBean> custContList = new ArrayList<CustomerContactApplyBean>();
		
		String [] ids = request.getParameterValues("p_id1");
		String [] names = request.getParameterValues("p_name1");
		String [] sexs = request.getParameterValues("p_sex1");
		String [] personals = request.getParameterValues("p_personal1");
		String [] ages = request.getParameterValues("p_age1");
		String [] birthdays = request.getParameterValues("p_birthday1");
		String [] qqs = request.getParameterValues("p_qq1");
		String [] weixins = request.getParameterValues("p_weixin1");
		String [] weibos = request.getParameterValues("p_weibo1");
		String [] descriptions = request.getParameterValues("p_description1");
		String [] handphones = request.getParameterValues("p_handphone1");
		String [] tels = request.getParameterValues("p_tel1");
		String [] emails = request.getParameterValues("p_email1");
		String [] dutys = request.getParameterValues("p_duty1");
		String [] relationships = request.getParameterValues("p_relationship1");
		String [] reportTos = request.getParameterValues("p_reportTo1");
		String [] interests = request.getParameterValues("p_interest1");
		String [] roles = request.getParameterValues("p_role1");
		
		if (names != null)
		{
			for (int i = 0; i < names.length; i++)
			{
				if (StringTools.isNullOrNone(names[i]))
				{
					continue;
				}
				
				CustomerContactApplyBean bean = new CustomerContactApplyBean();
				
				bean.setId(ids[i]);
				bean.setName(names[i]);
				bean.setSex(MathTools.parseInt(sexs[i]));
				bean.setPersonal(MathTools.parseInt(personals[i]));
				bean.setAge(MathTools.parseInt(ages[i]));
				bean.setBirthday(birthdays[i]);
				bean.setQq(qqs[i]);
				bean.setWeibo(weibos[i]);
				bean.setWeixin(weixins[i]);
				bean.setDescription(descriptions[i]);
				bean.setHandphone(handphones[i]);
				bean.setTel(tels[i]);
				bean.setEmail(emails[i]);
				bean.setDuty(MathTools.parseInt(dutys[i]));
				bean.setRelationship(MathTools.parseInt(relationships[i]));
				bean.setReportTo(reportTos[i]);
				bean.setInterest(interests[i]);
				bean.setRole(MathTools.parseInt(roles[i]));
				
				custContList.add(bean);
			}
		}
		
		return custContList;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private List<CustomerBusinessApplyBean> fillCustBusi(HttpServletRequest request)
	{
		List<CustomerBusinessApplyBean> custBusiList = new ArrayList<CustomerBusinessApplyBean>();
		
		String [] ids = request.getParameterValues("p_id3");
		String [] custAccountTypes = request.getParameterValues("p_custAccountType");
		String [] custAccountBanks = request.getParameterValues("p_custAccountBank");
		String [] custAccountNames = request.getParameterValues("p_custAccountName");
		String [] custAccounts = request.getParameterValues("p_custAccount");
		String [] myAccountTypes = request.getParameterValues("p_myAccountType");
		String [] myAccountBanks = request.getParameterValues("p_myAccountBank");
		String [] myAccountNames = request.getParameterValues("p_myAccountName");
		String [] myAccounts = request.getParameterValues("p_myAccount");
		
		if (custAccountBanks != null)
		{
			for (int i = 0; i < custAccountBanks.length; i++)
			{
				if (StringTools.isNullOrNone(custAccountBanks[i]))
				{
					continue;
				}
				
				CustomerBusinessApplyBean bean = new CustomerBusinessApplyBean();
				
				bean.setId(ids[i]);
				bean.setCustAccountType(MathTools.parseInt(custAccountTypes[i]));
				bean.setMyAccountType(MathTools.parseInt(myAccountTypes[i]));
				bean.setCustAccount(custAccounts[i]);
				bean.setMyAccount(myAccounts[i]);
				bean.setCustAccountBank(custAccountBanks[i]);
				bean.setCustAccountName(custAccountNames[i]);
				bean.setMyAccountBank(myAccountBanks[i]);
				bean.setMyAccountName(myAccountNames[i]);
				
				custBusiList.add(bean);
			}
		}
		
		return custBusiList;
	}
	
	private List<CustomerDistAddrApplyBean> fillCustAddr(HttpServletRequest request)
	{
		List<CustomerDistAddrApplyBean> custBusiList = new ArrayList<CustomerDistAddrApplyBean>();
		
		String [] ids = request.getParameterValues("p_id2");
		String [] fullAddress = request.getParameterValues("p_fullAddress");
		String [] provinceIds = request.getParameterValues("p_provinceId2");
		String [] cityIds = request.getParameterValues("p_cityId2");
		String [] areaIds = request.getParameterValues("p_areaId2");
		String [] address = request.getParameterValues("p_address2");
		String [] atypes = request.getParameterValues("p_atype");
		String [] contacts = request.getParameterValues("p_contact");
		String [] telephones = request.getParameterValues("p_telephone");
		
		if (fullAddress != null)
		{
			for (int i = 0; i < fullAddress.length; i++)
			{
				if (StringTools.isNullOrNone(fullAddress[i]))
				{
					continue;
				}
				
				CustomerDistAddrApplyBean bean = new CustomerDistAddrApplyBean();
				
				bean.setId(ids[i]);
				bean.setFullAddress(fullAddress[i]);
				bean.setProvinceId(provinceIds[i]);
				bean.setCityId(cityIds[i]);
				bean.setAreaId(areaIds[i]);
				bean.setAddress(address[i]);
				bean.setAtype(MathTools.parseInt(atypes[i]));
				bean.setContact(contacts[i]);
				bean.setTelephone(telephones[i]);
				
				custBusiList.add(bean);
			}
		}
		
		return custBusiList;
	}


    /**
     * 2015/1/12 #157 导入客户
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
	public ActionForward importCustomer(ActionMapping mapping, ActionForm form,
										HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		RequestDataStream rds = new RequestDataStream(request);

		boolean importError = false;

		//就几个字段:客户名，省，市，地址，对应业务员
		//默认都是组织客户
		//#443 去掉地址

		List<CustomerVO> importItemList = new ArrayList<CustomerVO>();

		StringBuilder builder = new StringBuilder();

		try
		{
			rds.parser();
		}
		catch (Exception e1)
		{
			_logger.error(e1, e1);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward("importCustomer");
		}

		if ( !rds.haveStream())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward("importCustomer");
		}

		ReaderFile reader = ReadeFileFactory.getXLSReader();

		try
		{
			reader.readFile(rds.getUniqueInputStream());

			while (reader.hasNext())
			{
				String[] obj = fillObj((String[])reader.next());

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
					CustomerVO bean = new CustomerVO();

					// 客户类型
					if ( !StringTools.isNullOrNone(obj[0]))
					{
						String type = obj[0].trim();
						if ("个人".equals(type) || "个人客户".equals(type)){
							bean.setType(NATURE_INDIVIDUAL);
						} else if ("部门".equals(type) || "部门客户".equals(type)){
							bean.setType(CustomerConstant.NATURE_DEPART);
						} else if ("组织".equals(type) || "组织客户".equals(type)){
							bean.setType(CustomerConstant.NATURE_CORPORATION);
						} else {
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("客户类型只能为：个人、部门、组织")
									.append("<br>");

							importError = true;
						}
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("客户类型不能为空")
								.append("<br>");

						importError = true;
					}


					// 客户名
					if ( !StringTools.isNullOrNone(obj[1]))
					{
						String name = obj[1].trim();
						bean.setName(name);

						//部门和组织客户名称不能重复
						if(bean.getType() == CustomerConstant.NATURE_DEPART || bean.getType() == CustomerConstant.NATURE_CORPORATION){

							List<CustomerBean> beans = this.customerMainDAO.queryByName(name);
							if (!ListTools.isEmptyOrNull(beans)){
								builder
										.append("第[" + currentNumber + "]错误:")
										.append("客户名已存在")
										.append("<br>");

								importError = true;
							}
						} else if(bean.getType() == NATURE_INDIVIDUAL && !name.startsWith("永银")){
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("个人客户名必须以永银开头:"+name)
                                    .append("<br>");

                            importError = true;
                        }
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("客户名不能为空")
								.append("<br>");

						importError = true;
					}

					// 省
					if ( !StringTools.isNullOrNone(obj[2]))
					{
						String province = obj[2].trim();
						bean.setProvinceName(province);

						ProvinceBean provinceBean = this.provinceDAO.findByUnique(province);
						if (provinceBean == null){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("省份不存在")
									.append("<br>");

							importError = true;
						} else{
							bean.setProvinceId(provinceBean.getId());
						}

					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("省份不能为空")
								.append("<br>");

						importError = true;
					}

					// 市
					if ( !StringTools.isNullOrNone(obj[3]))
					{
						String city = obj[3].trim();
						bean.setCityName(city);

						CityBean cityBean = this.cityDAO.findByUnique(city);
						if (cityBean == null){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("城市不存在")
									.append("<br>");

							importError = true;
						} else{
							bean.setCityId(cityBean.getId());
						}

					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("市不能为空")
								.append("<br>");

						importError = true;
					}

					// 区县
					if ( !StringTools.isNullOrNone(obj[4]))
					{
						String area = obj[4].trim();
						bean.setAreaName(area);

						ConditionParse conditionParse = new ConditionParse();
						conditionParse.addWhereStr();
						conditionParse.addCondition("name","=",area);
						List<AreaBean> areaBeans = this.areaDAO.queryEntityBeansByCondition(conditionParse);
						if (ListTools.isEmptyOrNull(areaBeans)){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("区县不存在")
									.append("<br>");

							importError = true;
						} else{
							bean.setAreaId(areaBeans.get(0).getId());
						}

					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("区县不能为空")
								.append("<br>");

						importError = true;
					}


					// 业务员
					if ( !StringTools.isNullOrNone(obj[5]))
					{
						String stafferName = obj[5].trim();
						bean.setStafferName(stafferName);

						StafferBean stafferBean = this.stafferDAO.findyStafferByName(stafferName);
						if (stafferBean == null){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("业务员不存在")
									.append("<br>");

							importError = true;
						}
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("业务员不能为空")
								.append("<br>");

						importError = true;
					}

					// 一级分行
					if ( !StringTools.isNullOrNone(obj[6]))
					{
						String reserve1 = obj[6].trim();
						bean.setReserve1(reserve1);
					}
					else if(bean.getName().contains("-银行") ||
							bean.getName().contains("-零售"))
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("一级分行不能为空")
								.append("<br>");

						importError = true;
					}

					// 地址
					if ( !StringTools.isNullOrNone(obj[7]))
					{
						String address = obj[7].trim();
						bean.setAddress(address);
					}
					// #175
//					else if(bean.getType() == NATURE_INDIVIDUAL)
//					{
//						builder
//								.append("第[" + currentNumber + "]错误:")
//								.append("个人客户地址不能为空")
//								.append("<br>");
//
//						importError = true;
//					}

					// 手机
					if ( !StringTools.isNullOrNone(obj[8]))
					{
						String mobile = obj[8].trim();
						bean.setHandphone(mobile);

						//#175 个人类型手机号不能重复
						if(bean.getType() == NATURE_INDIVIDUAL){
							ConditionParse conditionParse = new ConditionParse();
							conditionParse.addWhereStr();
							conditionParse.addCondition("handPhone","=",mobile);
							 List<CustomerIndividualBean> customerIndividualBeans = this.customerIndividualDAO.queryEntityBeansByCondition(conditionParse);
							 if (!ListTools.isEmptyOrNull(customerIndividualBeans)){
							 	for (CustomerIndividualBean customerIndividualBean : customerIndividualBeans){
									// #464 检查存在的用户是否在“停用”，“重复”名下，如果在其他用户名下，则不允许重复
									StafferVSCustomerBean vsBean = stafferVSCustomerDAO.findByUnique(customerIndividualBean.getId());
									if (vsBean!= null){
										StafferBean stafferBean = this.stafferDAO.find(vsBean.getStafferId());
										if (stafferBean!= null &&
												!"停用".equals(stafferBean.getName())
												&& !"重复".equals(stafferBean.getName())){
											builder
													.append("第[" + currentNumber + "]错误:")
													.append("个人客户手机号已存在:" + mobile+",客户名:"+customerIndividualBean.getName())
													.append("<br>");

											importError = true;
											break;
										}
									}
								}
							 }
						}
					}
					else if(bean.getType() == NATURE_INDIVIDUAL)
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("个人客户手机不能为空")
								.append("<br>");

						importError = true;
					}

					// 邮箱
					if ( !StringTools.isNullOrNone(obj[9]))
					{
						String email = obj[9].trim();
						bean.setEmail(email);
					}

					importItemList.add(bean);
				}
				else
				{
					builder
							.append("第[" + currentNumber + "]错误:")
							.append("数据长度不足26格错误")
							.append("<br>");

					importError = true;
				}
			}
		}catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

			return mapping.findForward("importCustomer");
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

			return mapping.findForward("importCustomer");
		}

		try
		{
			this.clientManager.importCustomer(importItemList);
			request.setAttribute(KeyConstant.MESSAGE, "批量导入成功");
		}
		catch(MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

			return mapping.findForward("importCustomer");
		}

		return mapping.findForward("importCustomer");
	}


	private int getType(String type){
		int result = 1;
		for (int i=0;i<ClientAction.customerType.length;i++){
			if (ClientAction.customerType[i].equals(type)){
				return i;
			}
		}
		return result;
	}

	/** 2016/5/16 #236
	 * 导入客户地址信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward importCustomerAddress(ActionMapping mapping, ActionForm form,
										HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		RequestDataStream rds = new RequestDataStream(request);

		boolean importError = false;

		List<CustomerVO> importItemList = new ArrayList<CustomerVO>();

		StringBuilder builder = new StringBuilder();

		final String url = "importCustomerAddress";

		try
		{
			rds.parser();
		}
		catch (Exception e1)
		{
			_logger.error(e1, e1);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward(url);
		}

		if ( !rds.haveStream())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward(url);
		}

		ReaderFile reader = ReadeFileFactory.getXLSReader();
        Map<String, List<CustomerDistAddrBean>> map = new HashMap<String ,List<CustomerDistAddrBean>>();
		Map<String, CustomerVO> nameToCustomer = new HashMap<String, CustomerVO>();

		try {
			reader.readFile(rds.getUniqueInputStream());

			while (reader.hasNext())
			{
				String[] obj = fillObj2((String[])reader.next());

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
					CustomerVO bean = new CustomerVO();

					// 客户名
					if ( !StringTools.isNullOrNone(obj[0]))
					{
						String name = obj[0].trim();
						bean.setName(name);

//						List<CustomerBean> voList = this.customerMainDAO.queryByName(name);
//						if (ListTools.isEmptyOrNull(voList){
//							builder
//									.append("第[" + currentNumber + "]错误:")
//									.append("客户名不存在")
//									.append("<br>");
//
//							importError = true;
//						} else{
//                            bean.setId(vo.getId());
//                        }
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("客户名不能为空")
								.append("<br>");

						importError = true;
					}

					// 客户编码
					if ( !StringTools.isNullOrNone(obj[1]))
					{
						String code = obj[1].trim();
						bean.setCode(code);

						ConditionParse conditionParse = new ConditionParse();
						conditionParse.addWhereStr();
						conditionParse.addCondition("name","=",bean.getName());
						conditionParse.addCondition("code","=",code);

						List<CustomerBean> voList = this.customerMainDAO.queryEntityBeansByCondition(conditionParse);
						if (ListTools.isEmptyOrNull(voList)) {
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("客户名和编码组合不存在")
									.append("<br>");

							importError = true;
						} else{
							bean.setId(voList.get(0).getId());
						}
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("客户编码不能为空")
								.append("<br>");

						importError = true;
					}

					// 客户类型
					if ( !StringTools.isNullOrNone(obj[2]))
					{
						String type = obj[2].trim();

						if (!ArrayUtils.contains(ClientAction.customerType, type)){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("客户类型不存在")
									.append("<br>");

							importError = true;
						} else{
							bean.setType(this.getType(type));
							nameToCustomer.put(bean.getName(), bean);
						}
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("客户类型不能为空")
								.append("<br>");

						importError = true;
					}


                    CustomerDistAddrBean address = new CustomerDistAddrBean();
					// 省
					if ( !StringTools.isNullOrNone(obj[3]))
					{
						String province = obj[3].trim();

						ProvinceBean provinceBean = this.provinceDAO.findByUnique(province);
						if (provinceBean == null){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("省份不存在")
									.append("<br>");

							importError = true;
						} else{
                            address.setProvinceId(provinceBean.getId());
						}
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("省份不能为空")
								.append("<br>");

						importError = true;
					}

					// 市
					if ( !StringTools.isNullOrNone(obj[4]))
					{
						String city = obj[4].trim();

						CityBean cityBean = this.cityDAO.findByUnique(city);
						if (cityBean == null){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("城市不存在")
									.append("<br>");

							importError = true;
						} else{
                            address.setCityId(cityBean.getId());
						}

					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("市不能为空")
								.append("<br>");

						importError = true;
					}


                    // 区
                    if ( !StringTools.isNullOrNone(obj[5])) {
                        String area = obj[5].trim();
                        ConditionParse conditionParse = new ConditionParse();
                        conditionParse.addWhereStr();
                        conditionParse.addCondition("name","=",area);
                        List<AreaBean> areaBeans = this.areaDAO.queryEntityBeansByCondition(conditionParse);
                        if (ListTools.isEmptyOrNull(areaBeans)){
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("区不存在")
                                    .append("<br>");

                            importError = true;
                        } else{
                            address.setAreaId(areaBeans.get(0).getId());
                        }
                    }
                    else
                    {
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("区不能为空")
                                .append("<br>");

                        importError = true;
                    }

					// 地址
					if ( !StringTools.isNullOrNone(obj[6]))
					{
						String addr = obj[6].trim();
						address.setAddress(addr);
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("地址不能为空")
								.append("<br>");

						importError = true;
					}

					// 收货人
					if ( !StringTools.isNullOrNone(obj[7]))
					{
						String receiver = obj[7].trim();
                        address.setContact(receiver);
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("收货人不能为空")
								.append("<br>");

						importError = true;
					}

                    // 收货人电话
                    if ( !StringTools.isNullOrNone(obj[8]))
                    {
						String receiverPhone = obj[8].trim();
                        address.setTelephone(receiverPhone);
                    }
                    else
                    {
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("收货人电话不能为空")
                                .append("<br>");

                        importError = true;
                    }

                    // 地址性质
                    if ( !StringTools.isNullOrNone(obj[9])) {
                        String atype = obj[9].trim();

                        ConditionParse conditionParse = new ConditionParse();
                        conditionParse.addWhereStr();
                        conditionParse.addCondition("type","=","303");
                        conditionParse.addCondition("val","=",atype);
                        List<EnumBean> enumBeans = this.enumDAO.queryEntityBeansByCondition(conditionParse);
                        if (ListTools.isEmptyOrNull(enumBeans)){
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("地址性质不存在")
                                    .append("<br>");

                            importError = true;
                        } else{
                            address.setAtype(Integer.valueOf(enumBeans.get(0).getKey()));
                        }
                    }
                    else
                    {
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("地址性质不能为空")
                                .append("<br>");

                        importError = true;
                    }


                    // 发货方式
                    if ( !StringTools.isNullOrNone(obj[10]))
                    {
                        boolean has = false;

						String shipping = obj[10].trim();

                        for (int i = 0 ; i < ClientAction.shipping.length; i++)
                        {
                            if (ClientAction.shipping[i].equals(shipping))
                            {
                                has = true;

                                address.setShipping(ClientAction.ishipping[i]);

                                break;
                            }
                        }
                        if (!has)
                        {
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("发货方式不对,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
                                    .append("<br>");

                            importError = true;
                        }
                    }else
                    {
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("发货方式不能为空,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,空发]中之一")
                                .append("<br>");

                        importError = true;
                    }

                    //快递公司
					if (address.getShipping() == 2 || address.getShipping() == 4)
					{
						// 如果发货方式是快递或快递+货运 ,则快递须为必填
						if ( !StringTools.isNullOrNone(obj[11]))
						{
							String transport1 = obj[11].trim();

							ExpressBean express = expressDAO.findByUnique(transport1);

							if (null == express)
							{
								builder
										.append("第[" + currentNumber + "]错误:")
										.append("快递方式"+ transport1 +"不存在")
										.append("<br>");

								importError = true;
							}else{
								address.setTransport1(MathTools.parseInt(express.getId()));
							}
						}else
						{
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("快递方式不能为空")
									.append("<br>");

							importError = true;
						}

						// 快递支付方式也不能为空
						if ( !StringTools.isNullOrNone(obj[12]))
						{
							String expressPay = obj[12].trim();

							boolean isexists = false;

							for (int i = 0; i < ClientAction.expressPay.length; i++)
							{
								if (ClientAction.expressPay[i].equals(expressPay))
								{
									isexists = true;

									address.setExpressPay(ClientAction.iexpressPay[i]);

									break;
								}
							}

							if (!isexists)
							{
								builder
										.append("第[" + currentNumber + "]错误:")
										.append("快递支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
										.append("<br>");

								importError = true;
							}
						}else
						{
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("快递支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
									.append("<br>");

							importError = true;
						}
					}

                    //货运
					if (address.getShipping() == 3 || address.getShipping() == 4)
					{
						// 如果发货方式是货运或快递+货运 ,则快递须为必填
						if ( !StringTools.isNullOrNone(obj[13]))
						{
							String transport1 = obj[13].trim();

							ExpressBean express = expressDAO.findByUnique(transport1);

							if (null == express)
							{
								builder
										.append("第[" + currentNumber + "]错误:")
										.append("货运方式"+ transport1 +"不存在")
										.append("<br>");

								importError = true;
							}else{
								address.setTransport2(MathTools.parseInt(express.getId()));
							}
						}else
						{
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("货运方式不能为空")
									.append("<br>");

							importError = true;
						}

						// 货运支付方式也不能为空
						if ( !StringTools.isNullOrNone(obj[14]))
						{
							String expressPay = obj[14].trim();

							boolean isexists = false;

							for (int i = 0; i < ClientAction.expressPay.length; i++)
							{
								if (ClientAction.expressPay[i].equals(expressPay))
								{
									isexists = true;

									address.setTransportPay(ClientAction.iexpressPay[i]);

									break;
								}
							}

							if (!isexists)
							{
								builder
										.append("第[" + currentNumber + "]错误:")
										.append("货运支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
										.append("<br>");

								importError = true;
							}
						}else
						{
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("货运支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
									.append("<br>");

							importError = true;
						}
					}

					if (map.containsKey(bean.getName())){
                        List<CustomerDistAddrBean> list = map.get(bean.getName());
                        list.add(address);
					} else{
                        List<CustomerDistAddrBean> list = new ArrayList<CustomerDistAddrBean>();
                        list.add(address);
                        map.put(bean.getName(), list);
					}

				}
				else
				{
					builder
							.append("第[" + currentNumber + "]错误:")
							.append("数据长度不足26格错误")
							.append("<br>");

					importError = true;
				}
			}
		}catch (Exception e)
		{
            e.printStackTrace();
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

			return mapping.findForward(url);
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

			return mapping.findForward(url);
		}

		try
		{
			for (String name : map.keySet()){
				CustomerVO customerVO = nameToCustomer.get(name);
				customerVO.setCustAddrList(map.get(name));
                importItemList.add(customerVO);
			}
			this.clientManager.importCustomerAddress(importItemList);
			request.setAttribute(KeyConstant.MESSAGE, "批量导入成功");
		}
		catch(MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

			return mapping.findForward(url);
		}

		return mapping.findForward(url);
	}

    private String[] fillObj(String[] obj)
    {
        String[] result = new String[10];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }

    private String[] fillObj2(String[] obj)
    {
        String[] result = new String[15];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }

	/**
     * addUpdateApplyCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addDelApplyClient(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        CustomerApproveBean apply = new CustomerApproveBean();

        AjaxResult ajax = new AjaxResult();

        try
        {
            CustomerBean bean = customerMainDAO.find(id);

            if (bean == null)
            {
                ajax.setError("客户不存在");

                return JSONTools.writeResponse(response, ajax);
            }

            BeanUtil.copyProperties(apply, bean);

            User user = Helper.getUser(request);
            
            clientFacade.applyDelClient(user.getId(), apply);

            ajax.setSuccess("成功申请删除客户:" + bean.getName());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("申请删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
	
	/**
	 * findApplyClient
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findApplyClient(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        try
        {
            CustomerApproveBean abean = customerApproveDAO.find(id);

            if (abean == null)
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "客户不存在");

                return mapping.findForward("querySelfClient");
            }

            boolean isSelfApply = abean.getApplyId().equals(user.getStafferId());

            boolean hasAuth = clientManager.hasCustomerAuth(user.getStafferId(), id);

            if ( !isSelfApply && !hasAuth
                && !userManager.containAuth(user, AuthConstant.CUSTOMER_CHECK))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

                return mapping.findForward("queryApplyClient");
            }
            
            if (abean.getType() == NATURE_INDIVIDUAL)
            {
            	CustomerIndividualApplyVO vo = clientManager.findIndividualApplyVO(id);
            	
            	request.setAttribute("bean", vo);
            	
//                ClientHelper.decryptIndividualClient(vo);
//                
//                for (CustomerContactApplyBean each : vo.getCustContList())
//                {
//                	ClientHelper.decryptClientContact(each);	
//                }
            	
            }else if (abean.getType() == CustomerConstant.NATURE_DEPART)
            {
            	CustomerDepartApplyVO vo = clientManager.findDepartApplyVO(id);
            	
            	request.setAttribute("bean", vo);
            	
//                for (CustomerContactApplyBean each : vo.getCustContList())
//                {
//                	ClientHelper.decryptClientContact(each);	
//                }
            	
            }else if (abean.getType() == CustomerConstant.NATURE_CORPORATION)
            {
            	CustomerCorporationApplyVO vo = clientManager.findCorporationApplyVO(id);
            	
            	request.setAttribute("bean", vo);
            	
//                for (CustomerContactApplyBean each : vo.getCustContList())
//                {
//                	ClientHelper.decryptClientContact(each);	
//                }
            	
            }else
            {
            	request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

                return mapping.findForward("querySelfClient");
            }
            
            request.setAttribute("apply", true);
            
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

            return mapping.findForward("queryApplyClient");
        }
        
        CustomerApproveBean bean = customerApproveDAO.find(id);
        
        if (null != bean)
        {
        	request.setAttribute("reson", bean.getReson());
        }

        return mapping.findForward("detailClient");
    }
	
	/**
	 * querySelfClient
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
    public ActionForward querySelfClient(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        String jsonstr = "";

        final String stafferId = user.getStafferId();

        ActionTools.processJSONQueryCondition(QUERYCLIENT, request, condtion);

		condtion.addIntCondition("CustomerBean.ostatus", "=", 0);
        condtion.addCondition("order by CustomerBean.logTime desc");

        jsonstr = ActionTools.querySelfBeanByJSONAndToString(QUERYCLIENT, request, condtion,
            new CommonQuery()
            {
                public int getCount(String key, HttpServletRequest request, ConditionParse condition)
                {
                    return customerMainDAO.countSelfCustomerByConstion(stafferId, condition);
                }

                public String getOrderPfix(String key, HttpServletRequest request)
                {
                    return "CustomerBean";
                }

                @SuppressWarnings("rawtypes")
				public List queryResult(String key, HttpServletRequest request,
                                        ConditionParse queryCondition)
                {
                    return customerMainDAO.querySelfCustomerByConstion(stafferId, PageSeparateTools
                        .getCondition(request, key), PageSeparateTools
                        .getPageSeparate(request, key));
                }

                public String getSortname(HttpServletRequest request)
                {
                    return request.getParameter(ActionTools.SORTNAME);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }
	
    /**
     * queryCheckAddApplyClient
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCheckAddApplyClient(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("CustomerApproveBean.opr", "=", CustomerConstant.OPR_ADD);

        condtion.addIntCondition("CustomerApproveBean.status", "=", CustomerConstant.STATUS_APPLY);

        ActionTools.processJSONQueryCondition(QUERYCHECKADDAPPLYCLIENT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCHECKADDAPPLYCLIENT, request,
            condtion, this.customerApproveDAO);

        return JSONTools.writeResponse(response, jsonstr);
	}
    
    /**
     * queryCheckApplyClient
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCheckApplyClient(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        // User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("CustomerApproveBean.opr", "<>", CustomerConstant.OPR_ADD);

        condtion.addIntCondition("CustomerApproveBean.opr", "<>", CustomerConstant.OPR_UPATE_CREDIT);

        condtion.addIntCondition("CustomerApproveBean.opr", "<>",
            CustomerConstant.OPR_UPATE_ASSIGNPER);

        // condtion.addCondition("CustomerApproveBean.locationId", "=", user.getLocationId());

        condtion.addIntCondition("CustomerApproveBean.status", "=", CustomerConstant.STATUS_APPLY);

        ActionTools.processJSONQueryCondition(QUERYCHECKAPPLYCLIENT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCHECKAPPLYCLIENT, request,
            condtion, this.customerApproveDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * processApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        String operation = request.getParameter("operation");

        String reson = request.getParameter("reson");
        
        String type = request.getParameter("type");
        
        if (StringTools.isNullOrNone(type))
        {
        	type = "";
        }

        String resultMsg = "";

        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);
            
            if ("0".equals(operation))
            {
                clientFacade.passApplyClient(user.getId(), id);
            }

            if ("1".equals(operation))
            {
            	//sendRejectMail(id,user,reson,CustomerApproveBean,"客户变更申请驳回");
                clientFacade.rejectApplyClient(user.getId(), id, reson);
            }

            resultMsg = "成功处理申请";

            if ("2".equals(operation))
            {
                clientFacade.delApplyClient(user.getId(), id);

                resultMsg = "成功删除申请";
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            resultMsg = "处理申请失败:" + e.getMessage();

            ajax.setError();
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, resultMsg);
        }

        CommonTools.removeParamers(request);

        ajax.setMsg(resultMsg);
        
        request.setAttribute(KeyConstant.MESSAGE, resultMsg);

        if (type.equals("1"))
        	return mapping.findForward("queryCheckApplyClient");
        else
        	return JSONTools.writeResponse(response, ajax);
    }
    
    /**
     * findClient
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findClient(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        String linkId = request.getParameter("linkId");

        if (StringTools.isNullOrNone(update))
        	update = "0";
        
        CommonTools.saveParamers(request);

        User user = Helper.getUser(request);

        CustomerVO cvo = customerMainDAO.findVO(id);

        if (cvo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "客户不存在");

            return mapping.findForward("querySelfClient");
        }
        
        try
        {
            if (cvo.getType() == NATURE_INDIVIDUAL)
            {
            	CustomerIndividualVO vo = clientManager.findIndividualVO(cvo.getId(), update);
            	
            	request.setAttribute("bean", vo);
        	
            	ActionForward forward = authSensitiveInfo(mapping, request, id, update, user, vo);
            	
            	if (null != forward)
            	{
            		return forward;
            	}
            	
            	if (update.equals("99"))
            	{
                	CustomerIndividualApplyVO newvo = clientManager.findIndividualApplyVO(cvo.getId());
                	
                	request.setAttribute("newBean", newvo);
                	
//                	ClientHelper.decryptIndividualClient(newvo);
                	
                	preForCompare(vo.getCustContList(), vo.getCustBusiList(), vo.getCustAddrVOList(), 
                			newvo.getCustContList(), newvo.getCustBusiList(), newvo.getCustAddrVOList());
            	}
            	
            }else if (cvo.getType() == CustomerConstant.NATURE_DEPART)
            {
            	CustomerDepartVO vo = clientManager.findDepartVO(cvo.getId(), update);
            	
            	request.setAttribute("bean", vo);
        	
            	ActionForward forward = authSensitiveInfo(mapping, request, id, update, user, vo);
            	
            	if (null != forward)
            	{
            		return forward;
            	}
            	
            	if (update.equals("99"))
            	{
            		CustomerDepartApplyVO newvo = clientManager.findDepartApplyVO(cvo.getId());
                	
                	request.setAttribute("newBean", newvo);
                	
                	preForCompare(vo.getCustContList(), vo.getCustBusiList(), vo.getCustAddrVOList(), 
                			newvo.getCustContList(), newvo.getCustBusiList(), newvo.getCustAddrVOList());
            	}
            	
            }else if (cvo.getType() == CustomerConstant.NATURE_CORPORATION)
            {
            	CustomerCorporationVO vo = clientManager.findCorporationVO(cvo.getId(), update);
            	
            	request.setAttribute("bean", vo);
        	
            	ActionForward forward = authSensitiveInfo(mapping, request, id, update, user, vo);
            	
            	if (null != forward)
            	{
            		return forward;
            	}
            	
            	if (update.equals("99"))
            	{
            		CustomerCorporationApplyVO newvo = clientManager.findCorporationApplyVO(cvo.getId());
                	
                	request.setAttribute("newBean", newvo);
                	
                	preForCompare(vo.getCustContList(), vo.getCustBusiList(), vo.getCustAddrVOList(), 
                			newvo.getCustContList(), newvo.getCustBusiList(), newvo.getCustAddrVOList());
            	}
            	
            }else
            {
            	request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据错误");

                return mapping.findForward("querySelfClient");
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getMessage());

            return mapping.findForward("querySelfClient");
        }

        if ("1".equals(update))
        {
            return mapping.findForward("updateClient");
        }
        else if ("99".equals(update))
        {
        	return mapping.findForward("compareUpdateClient");
        }
        else
        {
            StafferVSCustomerBean vs = stafferVSCustomerDAO.findVOByUnique(id);

            request.setAttribute("vs", vs);

            return mapping.findForward("detailClient");
        }
    }

    private void preForCompare(List<CustomerContactBean> custContList, 
    						   List<CustomerBusinessBean> custBusiList,List<CustomerDistAddrVO> custAddrList,
    						   List<CustomerContactApplyBean> newCustContList, 
    						   List<CustomerBusinessApplyBean> newCustBusiList, List<CustomerDistAddrApplyVO> newCustAddrList)
    {
    	// 解决 修改时删除联系信息，新信息为“空”
    	for (CustomerContactBean each : custContList)
    	{
    		CustomerContactApplyBean newCustCont =  customerContactApplyDAO.find(each.getId());
    		
    		// 被删了
    		if (null == newCustCont)
    		{
    			CustomerContactBean self = new CustomerContactBean(true);
    			
//    			ClientHelper.decryptClientContact(each);
    			
    			each.setSelf(self);
    		}else // 未删 ,直接比较
    		{
    			CustomerContactBean custCont = new CustomerContactBean();
    			
    			BeanUtil.copyProperties(custCont, newCustCont);
    			
//    			ClientHelper.decryptClientContact(custCont);
    			
    			each.setSelf(custCont);
    		}
    	}
    	
    	// 新增了
    	for (CustomerContactApplyBean each : newCustContList)
    	{
    		CustomerContactBean CustCont =  customerContactDAO.find(each.getId());
    		
    		// 新增的
    		if (null == CustCont)
    		{
    			CustomerContactBean self = new CustomerContactBean(true);
    			
    			CustomerContactBean newCustCont = new CustomerContactBean();
    			
    			BeanUtil.copyProperties(newCustCont, each);
    			
//    			ClientHelper.decryptClientContact(newCustCont);
    			
    			self.setSelf(newCustCont);
    			
    			custContList.add(self);
    		}
    	}
    	
    	// 解决 修改时删除联系信息，新信息为“空”
    	for (CustomerBusinessBean each : custBusiList)
    	{
    		CustomerBusinessApplyBean newCustBusi =  customerBusinessApplyDAO.findVO(each.getId());
    		
    		// 被删了
    		if (null == newCustBusi)
    		{
    			CustomerBusinessBean self = new CustomerBusinessBean(true);
    			
    			each.setSelf(self);
    		}else{
    			
    			CustomerBusinessBean custBusi = new CustomerBusinessBean();
    			
    			BeanUtil.copyProperties(custBusi, newCustBusi);
    			
    			each.setSelf(custBusi);
    		}
    	}
    	
    	// 新增了
    	for (CustomerBusinessApplyBean each : newCustBusiList)
    	{
    		CustomerBusinessBean CustBusi =  customerBusinessDAO.findVO(each.getId());
    		
    		// 新增的
    		if (null == CustBusi)
    		{
    			CustomerBusinessBean self = new CustomerBusinessBean(true);
    			
    			CustomerBusinessBean newCustBusi = new CustomerBusinessBean();
    			
    			BeanUtil.copyProperties(newCustBusi, each);
    			
    			self.setSelf(newCustBusi);
    			
    			custBusiList.add(self);
    		}
    	}
    	
    	// 解决 修改时删除联系信息，新信息为“空”  ==  配送地址
    	for (CustomerDistAddrVO each : custAddrList)
    	{
    		CustomerDistAddrApplyVO newCustAddr =  customerDistAddrApplyDAO.findVO(each.getId());
    		
    		// 被删了
    		if (null == newCustAddr)
    		{
    			CustomerDistAddrVO self = new CustomerDistAddrVO(true);
    			
    			each.setSelf(self);
    		}else{
    			
    			CustomerDistAddrVO custAddr = new CustomerDistAddrVO();
    			
    			BeanUtil.copyProperties(custAddr, newCustAddr);
    			
    			each.setSelf(custAddr);
    		}
    	}
    	
    	// 新增了
    	for (CustomerDistAddrApplyVO each : newCustAddrList)
    	{
    		CustomerDistAddrVO CustAddr =  customerDistAddrDAO.findVO(each.getId());
    		
    		// 新增的
    		if (null == CustAddr)
    		{
    			CustomerDistAddrVO self = new CustomerDistAddrVO(true);
    			
    			CustomerDistAddrVO newCustAddr = new CustomerDistAddrVO();
    			
    			BeanUtil.copyProperties(newCustAddr, each);
    			
    			self.setSelf(newCustAddr);
    			
    			custAddrList.add(self);
    		}
    	}
    }
    
    /**
     * authSensitiveInfo
     * 
     * @param mapping
     * @param request
     * @param id
     * @param update
     * @param user
     * @param vo #CustomerIndividualVO
     * @return
     * @throws MYException
     */
	private ActionForward authSensitiveInfo(ActionMapping mapping, HttpServletRequest request, String id,
			String update, User user, CustomerIndividualVO vo) throws MYException
	{
		// 修改，需要验证权限
		if ("1".equals(update))
		{
		    boolean hasAuth = clientManager.hasCustomerAuth(user.getStafferId(), id);

		    if ( !hasAuth)
		    {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有此客户的修改权限");

                    return mapping.findForward("querySelfClient");
		    }

//		    ClientHelper.decryptIndividualClient(vo);
//		    // 解密联系人
//		    for(CustomerContactBean each : vo.getCustContList())
//		    {
//		    	ClientHelper.decryptClientContact(each);
//		    }
		}
		else if ("0".equals(update))
		{
		    if (userManager.containAuth(user, AuthConstant.CUSTOMER_OQUERY)
		        || clientManager.hasCustomerAuth(user.getStafferId(), id))
		    {
//			    ClientHelper.decryptIndividualClient(vo);
//			    // 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.decryptClientContact(each);
//			    }
		    }
		    else
		    {
//		        ClientHelper.hideIndividualClient(vo);
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.hideClientContact(each);
//			    }
		    }
		    
		    clientManager.addAccessLog(user, user.getStafferId(), id);
		}
		else if ("2".equals(update))
		{
		    boolean hasAuth = clientManager.hasCustomerAuth(user.getStafferId(), id);

		    if ( !hasAuth && !userManager.containAuth(user, AuthConstant.CUSTOMER_CHECK))
		    {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

                    return mapping.findForward("queryApplyCustomer");
		    }

//		    ClientHelper.decryptIndividualClient(vo);
//		    // 解密联系人
//		    for(CustomerContactBean each : vo.getCustContList())
//		    {
//		    	ClientHelper.decryptClientContact(each);
//		    }
		    
		    clientManager.addAccessLog(user, user.getStafferId(), id);
		}
		// process linkid
		else if ("3".equals(update))
		{
			// TODO
		}
		else
		{
		    if (userManager.containAuth(user, AuthConstant.CUSTOMER_OQUERY))
		    {
//		    	ClientHelper.decryptIndividualClient(vo);
//			    // 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.decryptClientContact(each);
//			    }
		    }
		    else
		    {
//		    	ClientHelper.hideIndividualClient(vo);
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.hideClientContact(each);
//			    }
		    }
		    
		    clientManager.addAccessLog(user, user.getStafferId(), id);
		}
		
		return null;
	}
	
	/**
	 * authSensitiveInfo
	 * @param mapping
	 * @param request
	 * @param id
	 * @param update
	 * @param user
	 * @param vo
	 * @return
	 * @throws MYException
	 */
	private ActionForward authSensitiveInfo(ActionMapping mapping, HttpServletRequest request, String id,
			String update, User user, CustomerDepartVO vo) throws MYException
	{
		// 修改，需要验证权限
		if ("1".equals(update))
		{
		    boolean hasAuth = clientManager.hasCustomerAuth(user.getStafferId(), id);

		    if ( !hasAuth)
		    {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有此客户的修改权限");

                    return mapping.findForward("querySelfClient");
		    }

		    // 解密联系人
//		    for(CustomerContactBean each : vo.getCustContList())
//		    {
//		    	ClientHelper.decryptClientContact(each);
//		    }
		}
		else if ("0".equals(update))
		{
		    if (userManager.containAuth(user, AuthConstant.CUSTOMER_OQUERY)
		        || clientManager.hasCustomerAuth(user.getStafferId(), id))
		    {
//		    	// 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.decryptClientContact(each);
//			    }
		    }
		    else
		    {
		    	// 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.hideClientContact(each);
//			    }
		    }
		    System.out.println("-----------------------------1");
		    clientManager.addAccessLog(user, user.getStafferId(), id);
			System.out.println("-----------------------------2");

		}
		else if ("2".equals(update))
		{
		    boolean hasAuth = clientManager.hasCustomerAuth(user.getStafferId(), id);

		    if ( !hasAuth && !userManager.containAuth(user, AuthConstant.CUSTOMER_CHECK))
		    {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

                    return mapping.findForward("queryApplyCustomer");
		    }

		    // 解密联系人
//		    for(CustomerContactBean each : vo.getCustContList())
//		    {
//		    	ClientHelper.decryptClientContact(each);
//		    }
		    
		    clientManager.addAccessLog(user, user.getStafferId(), id);
		}
		// process linkid
		else if ("3".equals(update))
		{
			// TODO
		}
		else
		{
		    if (userManager.containAuth(user, AuthConstant.CUSTOMER_OQUERY))
		    {
		    	// 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.decryptClientContact(each);
//			    }
		    }
		    else
		    {
		    	// 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.hideClientContact(each);
//			    }
		    }
		    
		    clientManager.addAccessLog(user, user.getStafferId(), id);
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param request
	 * @param id
	 * @param update
	 * @param user
	 * @param vo
	 * @return
	 * @throws MYException
	 */
	private ActionForward authSensitiveInfo(ActionMapping mapping, HttpServletRequest request, String id,
			String update, User user, CustomerCorporationVO vo) throws MYException
	{
		// 修改，需要验证权限
		if ("1".equals(update))
		{
		    boolean hasAuth = clientManager.hasCustomerAuth(user.getStafferId(), id);

		    if ( !hasAuth)
		    {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有此客户的修改权限");

                    return mapping.findForward("querySelfClient");
		    }

		    // 解密联系人
//		    for(CustomerContactBean each : vo.getCustContList())
//		    {
//		    	ClientHelper.decryptClientContact(each);
//		    }
		}
		else if ("0".equals(update))
		{
		    if (userManager.containAuth(user, AuthConstant.CUSTOMER_OQUERY)
		        || clientManager.hasCustomerAuth(user.getStafferId(), id))
		    {
			    // 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.decryptClientContact(each);
//			    }
		    }
		    else
		    {
		    	// 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.hideClientContact(each);
//			    }
		    }
		    
		    clientManager.addAccessLog(user, user.getStafferId(), id);
		}
		else if ("2".equals(update))
		{
		    boolean hasAuth = clientManager.hasCustomerAuth(user.getStafferId(), id);

		    if ( !hasAuth && !userManager.containAuth(user, AuthConstant.CUSTOMER_CHECK))
		    {
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

                    return mapping.findForward("queryApplyCustomer");
		    }

		    // 解密联系人
//		    for(CustomerContactBean each : vo.getCustContList())
//		    {
//		    	ClientHelper.decryptClientContact(each);
//		    }
		    
		    clientManager.addAccessLog(user, user.getStafferId(), id);
		}
		// process linkid
		else if ("3".equals(update))
		{
			// TODO
		}
		else
		{
		    if (userManager.containAuth(user, AuthConstant.CUSTOMER_OQUERY))
		    {
			    // 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.decryptClientContact(each);
//			    }
		    }
		    else
		    {
		    	// 解密联系人
//			    for(CustomerContactBean each : vo.getCustContList())
//			    {
//			    	ClientHelper.hideClientContact(each);
//			    }
		    }
		    
		    clientManager.addAccessLog(user, user.getStafferId(), id);
		}
		
		return null;
	}

	/**
     * queryCanAssignCustomer(可分配的)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryCanAssignClient(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addIntCondition("CustomerBean.status", "=", CustomerConstant.REAL_STATUS_IDLE);
        // exportAssign(request);

        ActionTools.processJSONQueryCondition(QUERYCANASSIGNCLIENT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYCANASSIGNCLIENT, request,
            condtion, this.customerMainDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
	
    /**
     * queryAssignApply(查询可分配申请)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAssignApply(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        // User user = Helper.getUser(request);

        // condtion.addCondition("AssignApplyBean.locationId", "=", user.getLocationId());

        ActionTools.processJSONQueryCondition(QUERYASSIGNAPPLY, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYASSIGNAPPLY, request, condtion,
            this.assignApplyDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * 查询区域下的客户分布(用来回收客户的)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryClientAssign(ActionMapping mapping, ActionForm form,
                                             final HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        // User user = Helper.getUser(request);

        // condtion.addCondition("t3.locationId", "=", user.getLocationId());

        User user = Helper.getUser(request);
        
        String innerCon = commonManager.getAllSubStafferIds(user.getStafferId());
        
        condtion.addCondition(" and t3.id in " + innerCon);

        ActionTools.processJSONQueryCondition(QUERYCLIENTASSIGN, request, condtion);

        String jsonstr = ActionTools.querySelfBeanByJSONAndToString(QUERYCLIENTASSIGN, request,
            condtion, new CommonQuery()
            {
                public int getCount(String key, HttpServletRequest request, ConditionParse condition)
                {
                    return customerMainDAO.countCustomerAssignByConstion(condition);
                }

                public String getOrderPfix(String key, HttpServletRequest request)
                {
                    return "t1";
                }

                public List queryResult(String key, HttpServletRequest request,
                                        ConditionParse queryCondition)
                {
                    return customerMainDAO.queryCustomerAssignByConstion(PageSeparateTools
                        .getCondition(request, key), PageSeparateTools
                        .getPageSeparate(request, key));
                }

                public String getSortname(HttpServletRequest request)
                {
                    return request.getParameter(ActionTools.SORTNAME);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * addAssignApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addAssignApply(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String cids = request.getParameter("cids");
            
            String[] customerIds = cids.split("~");
            
            AssignApplyBean bean = new AssignApplyBean();

            bean.setUserId(user.getStafferId());

            bean.setStafferId(user.getStafferId());

            bean.setLocationid(user.getLocationId());

            for (String eachItem : customerIds)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    bean.setCustomerId(eachItem.trim());

                    clientFacade.addAssignApply(user.getId(), bean);
                }
            }

            ajax.setSuccess("成功增加申请");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("申请失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    
    /**
     * 批量接受客户移交
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryClientBatchTrans(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException
    {
        CommonTools.saveParamers(request);
        
        List<DestStafferVSCustomerVO> list = null;
        
        if (PageSeparateTools.isFirstLoad(request))
        {
        	User user = Helper.getUser(request);
        	
        	String stafferId = user.getStafferId();
        	
            ConditionParse condition = new ConditionParse();
            
            condition.addWhereStr();
            
            condition.addCondition("DestStafferVSCustomerBean.destStafferId", "=", stafferId);
            
            int total = destStafferVSCustomerDAO.countByCondition(condition.toString());

            PageSeparate page = new PageSeparate(total, 20);

            PageSeparateTools.initPageSeparate(condition, page, request, RPTQUERYCLIENTBATCHTRANS);

            list = destStafferVSCustomerDAO.queryEntityVOsByCondition(condition, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYCLIENTBATCHTRANS);

            list = destStafferVSCustomerDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
            		RPTQUERYCLIENTBATCHTRANS), PageSeparateTools.getPageSeparate(request, RPTQUERYCLIENTBATCHTRANS));
        }
                
        request.setAttribute("beanList", list);
        
        return mapping.findForward("rptQueryClientBatchTrans");
        
    }
    
    /**
     * 处理分配客户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processAssignApply(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String cids = request.getParameter("cids");

            String opr = request.getParameter("opr");

            String[] customerIds = cids.split("~");

            for (String eachItem : customerIds)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    if ("0".equals(opr))
                    {
                        clientFacade.passAssignApply(user.getId(), eachItem);
                    }

                    if ("1".equals(opr))
                    {
                        clientFacade.rejectAssignApply(user.getId(), eachItem);
                    }
                }
            }

            ajax.setSuccess("成功处理申请");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("处理申请失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    
    /**
     * 生成客户分布图
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryClientDistribute(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_DISTRIBUTE))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

            return mapping.findForward("error");
        }

        String path = servlet.getServletContext().getRealPath("/") + "temp/";

        String ttemp = TimeTools.now("yyyy/MM/dd/HH/");

        path = path + ttemp;

        FileTools.mkdirs(path);

        String fileName = SequenceTools.getSequence() + ".png";

        List<LocationBean> locationList = locationDAO.listEntityBeans();

        double[] data = new double[locationList.size()];

        String[] columnKeys = new String[locationList.size()];

        int total = 0;

        for (int i = 0; i < locationList.size(); i++ )
        {
            data[i] = customerMainDAO.countByLocationId(locationList.get(i).getId());

            total += data[i];

            columnKeys[i] = locationList.get(i).getName();
        }

        CreateChartServiceImpl pm = new CreateChartServiceImpl(path);

        double[][] datas = new double[][] {data};

        String[] rowKeys = {"Customers:" + total};

        CategoryDataset dataset = pm.getBarData(datas, rowKeys, columnKeys);

        pm.createBarChart(dataset, "各分公司", "客户数量", "客户分布", fileName);

        List<String> urlList = new ArrayList<String>();

        urlList.add("../temp/" + ttemp + fileName);

        request.setAttribute("urlList", urlList);

        return mapping.findForward("queryClientDistribute");
    }
    
    /**
     * 生成所有区域的职员分布
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllStafferClientDistribute(ActionMapping mapping, ActionForm form,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_DISTRIBUTE))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

            return mapping.findForward("error");
        }

        List<LocationBean> locationlist = locationDAO.listEntityBeans();

        List<String> urlList = new ArrayList<String>();

        for (LocationBean locationBean : locationlist)
        {
            urlList.add(createCustmoerDistribute(locationBean.getId(), locationBean.getName()));
        }

        request.setAttribute("urlList", urlList);

        return mapping.findForward("queryClientDistribute");
    }
    
    /**
     * 生成职员的客户分布图
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryStafferClientDistribute(ActionMapping mapping, ActionForm form,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        if ( !userManager.containAuth(user, AuthConstant.CUSTOMER_RECLAIM))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有权限");

            return mapping.findForward("error");
        }

        LocationBean lbean = locationDAO.find(user.getLocationId());

        if (lbean == null)
        {
            return ActionTools.toError("区域为空", mapping, request);
        }

        String url = createCustmoerDistribute(user.getLocationId(), lbean.getName());

        List<String> urlList = new ArrayList<String>();

        urlList.add(url);

        request.setAttribute("urlList", urlList);

        return mapping.findForward("queryClientDistribute");
    }
    
    /**
     * 构建区域分布
     * 
     * @param locationId
     *            区域
     * @return 构建的路径
     */
    private String createCustmoerDistribute(String locationId, String locationName)
    {
        String path = servlet.getServletContext().getRealPath("/") + "temp/";

        String ttemp = TimeTools.now("yyyy/MM/dd/HH/");

        path = path + ttemp;

        FileTools.mkdirs(path);

        String fileName = SequenceTools.getSequence() + ".png";

        List<StafferBean> stafferList = stafferDAO.queryStafferByLocationId(locationId);

        List<OprMap> lop = new ArrayList<OprMap>();

        int total = 0;
        for (StafferBean stafferBean : stafferList)
        {
            int count = stafferVSCustomerDAO.countByStafferId(stafferBean.getId());

            if (count > 0)
            {
                OprMap mms = new OprMap();

                mms.setKey(stafferBean.getName());

                mms.setValue(count);

                lop.add(mms);
            }

            total += count;
        }

        double[] data = new double[lop.size()];

        String[] columnKeys = new String[lop.size()];

        for (int i = 0; i < lop.size(); i++ )
        {
            data[i] = (Integer)lop.get(i).getValue();

            columnKeys[i] = lop.get(i).getKey().toString();
        }

        CreateChartServiceImpl pm = new CreateChartServiceImpl(path);

        double[][] datas = new double[][] {data};

        String[] rowKeys = {"Customers:" + total};

        CategoryDataset dataset = pm.getBarData(datas, rowKeys, columnKeys);

        // 一个职员75
        int width = lop.size() * 60 > 900 ? lop.size() * 60 : 900;

        pm.createBarChart(dataset, locationName + "公司职员", "客户数量", locationName + "客户分布", fileName,
            width);

        return "../temp/" + ttemp + fileName;
    }
    
    /**
     * 回收分配客户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward reclaimAssignClient(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String cids = request.getParameter("customerIds");

            String type = request.getParameter("type");
            
            String destStafferId = request.getParameter("destStafferId");
            
            String[] customerIds = cids.split("~");

            for (String eachItem : customerIds)
            {
                if ( !StringTools.isNullOrNone(eachItem))
                {
                    clientFacade.reclaimAssignClient(user.getId(), eachItem, type, destStafferId);
                }
            }

            ajax.setSuccess("成功回收客户");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("回收客户失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    
    /**
     * 回收职员的客户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward reclaimStafferClient(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            User user = Helper.getUser(request);

            String stafferId = request.getParameter("stafferId");

            String flag = request.getParameter("flag");
            
            String destStafferId = request.getParameter("destStafferId");

            clientFacade.reclaimStafferAssignClient(user.getId(), stafferId, destStafferId, CommonTools
                .parseInt(flag));

            ajax.setSuccess("成功回收职员客户");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("回收职员客户失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    
    /**
     * queryLocationCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryLocationClient(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        User user = Helper.getUser(request);

        String jsonstr = "";
        
        if (userManager.containAuth(user, AuthConstant.CUSTOMER_QUERY_LOCATION))
        {
            // 看到区域下所有的客户
            // condtion.addCondition("CustomerBean.locationId", "=", user.getLocationId());
        }
        else
        {
            condtion.addFlaseCondition();
        }
        
        ActionTools.processJSONQueryCondition(QUERYLOCATIONCLIENT, request, condtion);
        
        notQueryFirstTime(condtion);

        /*jsonstr = ActionTools.queryVOByJSONAndToString(QUERYLOCATIONCLIENT, request, condtion,
            this.customerMainDAO, new HandleResult<CustomerVO>()
            {
				public void handle(CustomerVO vo)
				{
					List<StafferVSCustomerVO> vsList = stafferVSCustomerDAO.queryEntityVOsByFK(vo.getId(), AnoConstant.FK_FIRST);
					
					if (!ListTools.isEmptyOrNull(vsList))
					{
						StafferVSCustomerVO vs = vsList.get(0);
						
						vo.setStafferName(vs.getStafferName());
					}
				}
            }
        );*/
		final List<StafferVSCustomerVO> vsList = stafferVSCustomerDAO.listEntityVOs();
		_logger.info("*vsList size***"+vsList.size());

        jsonstr = ActionTools.querySelfBeanByJSONAndToString(QUERYLOCATIONCLIENT, request,
                condtion, new CommonQuery()
                {
                    public int getCount(String key, HttpServletRequest request, ConditionParse condition)
                    {
                        return customerMainDAO.countCustomerLocationByCondition(condition);
                    }

                    public String getOrderPfix(String key, HttpServletRequest request)
                    {
                        return "CustomerBean";
                    }

                    public List queryResult(String key, HttpServletRequest request,
                                            ConditionParse queryCondition)
                    {
						List<CustomerVO> customerVOs = customerMainDAO.queryCustomerLocationByCondition(PageSeparateTools
																.getCondition(request, key), PageSeparateTools
														.getPageSeparate(request, key));
						_logger.info("***customerVOs size***"+customerVOs.size());
						setStafferName(customerVOs, vsList);
						return customerVOs;
                    }

                    public String getSortname(HttpServletRequest request)
                    {
                        return request.getParameter(ActionTools.SORTNAME);
                    }
                });
        

        return JSONTools.writeResponse(response, jsonstr);
    }

	private void setStafferName(List<CustomerVO> customerVOs, List<StafferVSCustomerVO> stafferVSCustomerVOs){
		for (CustomerVO customerVO: customerVOs){
				for (StafferVSCustomerVO stafferVSCustomerVO : stafferVSCustomerVOs){
						if (stafferVSCustomerVO.getCustomerId().equals(customerVO.getId())){
								customerVO.setStafferName(stafferVSCustomerVO.getStafferName());
							}
					}
			}
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
     * 查询客户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryAllClient(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);
        
        String first = request.getParameter("first");
        
        if (StringTools.isNullOrNone(first))
        	first = "";
        
        if (first.equals("1"))
        	return mapping.findForward("rptQueryAllClient");
        
        preRptCustomer(request);
        
        return mapping.findForward("rptQueryAllClient");
    }

    /**
     * 查询客户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryMultiCustomer(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);
        
        String first = request.getParameter("first");
        
        if (StringTools.isNullOrNone(first))
        	first = "";
        
        if (first.equals("1"))
        	return mapping.findForward("rptQueryMultiClient");
        
        preRptCustomer(request);
        
        return mapping.findForward("rptQueryMultiClient");
    }

    /**
     * 根据职员，查询其下所有的客户信息
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryMultiClientByStaff(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
        CommonTools.saveParamers(request);
        
        String first = request.getParameter("first");
        
        if (StringTools.isNullOrNone(first))
        	first = "";
        
        if (first.equals("1"))
        	return mapping.findForward("rptQueryMultiClientByStaff");
        
        String stafferId = "";
        
		List<CustomerVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            String name = request.getParameter("name");

            if ( !StringTools.isNullOrNone(name))
            {
            	condtion.addCondition("staffer.name", "=", name);
            }else{
            	condtion.addFlaseCondition();
            }

            int total = customerMainDAO.countCustomerByStaff(condtion);

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYALLCLIENT);

            list = customerMainDAO.queryCustomerByStaff(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYALLCLIENT);

            list = customerMainDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYALLCLIENT), PageSeparateTools.getPageSeparate(request,
                RPTQUERYALLCLIENT));
        }

        request.setAttribute("list", list);
        
        for (CustomerVO each : list)
        {
        	List<StafferVSCustomerVO> vsList = stafferVSCustomerDAO.queryEntityVOsByFK(each.getId(), AnoConstant.FK_FIRST);
			
			if (!ListTools.isEmptyOrNull(vsList))
			{
				StafferVSCustomerVO vs = vsList.get(0);
				
				each.setStafferName(vs.getStafferName());
				
				stafferId = vs.getStafferId();
			}
			
			// 取出手机号并解密
			CustomerContactBean custContBean = customerContactDAO.findFirstValidBean(each.getId());
			
			if (null != custContBean)
				each.setHandphone(custContBean.getHandphone());
        }
        
        request.setAttribute("stafferId", stafferId);
        
        return mapping.findForward("rptQueryMultiClientByStaff");
    }
    
    /**
     * ajax 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryMultiClientByStaff(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
    	AjaxResult ajax = new AjaxResult();
    	
    	String stafferId = request.getParameter("stafferId");

    	ConditionParse condtion = new ConditionParse();

        if ( !StringTools.isNullOrNone(stafferId))
        {
        	condtion.addCondition("staffer.id", "=", stafferId);
        }else{
        	condtion.addFlaseCondition();
        }
    	
        try{
        	List<CustomerVO> list = customerMainDAO.queryCustomerByStaff(condtion, null);
        	
        	// 多个以,间隔
        	String handphones = "";
        	
        	for (int i = 0; i < list.size(); i++)
        	{
        		// 取出手机号并解密  
        		CustomerContactBean custCont = customerContactDAO.findFirstValidBean(list.get(i).getId());
        		
        		String handphone = ""; 
        		
        		if (null != custCont)
        			handphone = custCont.getHandphone();
        		
        		if (StringTools.isNullOrNone(handphone))
        			continue;
        		
        		if (handphone.length() != 11)
        		{
        			continue;
        		}
        		
        		if (!handphone.startsWith("1"))
        		{
        			continue;
        		}
        		
        		if (!RegularExpress.isNumber(handphone))
        		{
        			continue;
        		}
        		
        		if (i == list.size() - 1){
        			handphones += handphone;
        			
        		}
        		else{
        			handphones += handphone + ",";
        		}
        	}

        	ajax.setSuccess(handphones);
        }catch(Exception e){
        	_logger.warn(e, e);
        	
        	ajax.setError();
        }

    	return JSONTools.writeResponse(reponse, ajax);
	}
    
	private void preRptCustomer(HttpServletRequest request)
	{
		List<CustomerVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();
            
            setInnerCondition(request, condtion);

            int total = customerMainDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYALLCLIENT);

            list = customerMainDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYALLCLIENT);

            list = customerMainDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
            		RPTQUERYALLCLIENT), PageSeparateTools.getPageSeparate(request,
            				RPTQUERYALLCLIENT));
        }

        request.setAttribute("list", list);
        
        for (CustomerVO each : list)
        {
        	List<StafferVSCustomerVO> vsList = stafferVSCustomerDAO.queryEntityVOsByFK(each.getId(), AnoConstant.FK_FIRST);
			
			if (!ListTools.isEmptyOrNull(vsList))
			{
				StafferVSCustomerVO vs = vsList.get(0);
				
				each.setStafferName(vs.getStafferName());
			}
			
			// 取出手机号并解密
			CustomerContactBean custCont = customerContactDAO.findFirstValidBean(each.getId());
			
			if (null != custCont)
				each.setHandphone(custCont.getHandphone());
        }
	}
    
	/**
     * @param request
     * @param condtion
     */
    private void setInnerCondition(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        String code = request.getParameter("code");

        String type = request.getParameter("type");
        
        if ( !StringTools.isNullOrNone(name))
        {
        	if (StringTools.isNullOrNone(type))
            {
        		condtion.addCondition("CustomerBean.name", "like", name);
            }
        	else
        	{
        		condtion.addCondition("CustomerBean.name", "=", name);
        		
        		request.setAttribute("CustomerBean.type", type);
        	}            
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("CustomerBean.code", "like", code);
        }

        if ( !StringTools.isNullOrNone(name) || !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("order by CustomerBean.creditVal desc");
        }
    }

    /**
     * @param request
     * @param condtion
     */
    private void setInnerCondition2(HttpServletRequest request, ConditionParse condtion)
    {
        String name = request.getParameter("name");

        String code = request.getParameter("code");

        if ( !StringTools.isNullOrNone(name))
        {
            condtion.addCondition("CustomerBean.name", "like", name);
        }

        if ( !StringTools.isNullOrNone(code))
        {
            condtion.addCondition("CustomerBean.code", "like", code);
        }
        
        condtion.addIntCondition("CustomerBean.ostatus", "=", 0);

        condtion.addCondition("order by CustomerBean.id desc");
    }
	
    /**
     * 查询自己名下的客户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQuerySelfClient(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String stafferId = request.getParameter("stafferId");

        List<CustomerBean> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setInnerCondition2(request, condtion);

            int total = customerMainDAO.countSelfCustomerByConstion(stafferId, condtion);

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYSELFCLIENT);

            list = customerMainDAO.querySelfCustomerByConstion(stafferId, condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYSELFCLIENT);

            list = customerMainDAO.querySelfCustomerByConstion(stafferId, PageSeparateTools
                .getCondition(request, RPTQUERYSELFCLIENT), PageSeparateTools.getPageSeparate(
                request, RPTQUERYSELFCLIENT));
        }

        List<CreditLevelBean> levelList = creditLevelDAO.listEntityBeans();

        // 自动解密
        for (CustomerBean customerBean : list)
        {
        	CustomerContactBean custCont = customerContactDAO.findFirstValidBean(customerBean.getId());
        	
        	if (null != custCont)
        	{
        		customerBean.setHandphone(custCont.getHandphone());
        		customerBean.setConnector(custCont.getName());
        	}

            double totla = 0.0d;

            for (CreditLevelBean creditLevelBean : levelList)
            {
                if (customerBean.getCreditLevelId().equals(creditLevelBean.getId()))
                {
                    customerBean.setReserve1(creditLevelBean.getName());

                    totla = creditLevelBean.getMoney();

                    break;
                }
            }

            double sumNoPayBusiness = clientManager.sumNoPayBusiness(customerBean);

            // 客户所欠金额
            customerBean.setReserve2(MathTools.formatNum(sumNoPayBusiness));

            // 剩余金额
            customerBean.setReserve3(MathTools.formatNum(totla - sumNoPayBusiness));
        }

        request.setAttribute("list", list);

        return mapping.findForward("rptQuerySelfClient");
    }
    
    /**
     * 查询自己名下的客户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQuerySelfClient1(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String stafferId = request.getParameter("stafferId");

        List<CustomerBean> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setInnerCondition2(request, condtion);

            int total = customerMainDAO.countSelfCustomerByConstion(stafferId, condtion);

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYSELFCLIENT);

            list = customerMainDAO.querySelfCustomerByConstion(stafferId, condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYSELFCLIENT);

            list = customerMainDAO.querySelfCustomerByConstion(stafferId, PageSeparateTools
                .getCondition(request, RPTQUERYSELFCLIENT), PageSeparateTools.getPageSeparate(
                request, RPTQUERYSELFCLIENT));
        }

        List<CreditLevelBean> levelList = creditLevelDAO.listEntityBeans();

        // 自动解密
        for (CustomerBean customerBean : list)
        {
        	CustomerContactBean custCont = customerContactDAO.findFirstValidBean(customerBean.getId());
        	
        	if (null != custCont)
        	{
        		customerBean.setHandphone(custCont.getHandphone());
        		customerBean.setConnector(custCont.getName());
        	}

            double totla = 0.0d;

            for (CreditLevelBean creditLevelBean : levelList)
            {
                if (customerBean.getCreditLevelId().equals(creditLevelBean.getId()))
                {
                    customerBean.setReserve1(creditLevelBean.getName());

                    totla = creditLevelBean.getMoney();

                    break;
                }
            }

            double sumNoPayBusiness = clientManager.sumNoPayBusiness(customerBean);

            // 客户所欠金额
            customerBean.setReserve2(MathTools.formatNum(sumNoPayBusiness));

            // 剩余金额
            customerBean.setReserve3(MathTools.formatNum(totla - sumNoPayBusiness));
        }

        request.setAttribute("list", list);

        return mapping.findForward("rptQuerySelfClient1");
    }

    /**
     * rptQueryPublicCustomer
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryPublicClient(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<DutyBean> entryList = dutyDAO.listEntityBeans();

        DutyBean duty = new DutyBean();

        duty.setId("99");

        duty.setName("公共客户");

        entryList.add(0, duty);

        request.setAttribute("list", entryList);

        return mapping.findForward("rptQueryPublicClient");
    }

    /**
     * 纳税公共客户
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQuerySelfPublicClient(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<DutyBean> entryList = dutyDAO.listEntityBeans();

        List<CustomerBean> list = new ArrayList();

        List<CreditLevelBean> levelList = creditLevelDAO.listEntityBeans();

        for (DutyBean dutyBean : entryList)
        {
            CustomerBean bean = new CustomerBean();

            bean.setId(dutyBean.getId());

            bean.setName(dutyBean.getName());

            bean.setCode(dutyBean.getId());

            for (CreditLevelBean creditLevelBean : levelList)
            {
                if (bean.getCreditLevelId().equals(creditLevelBean.getId()))
                {
                    bean.setReserve1(creditLevelBean.getName());

                    break;
                }
            }

            double sumNoPayBusiness = clientManager.sumNoPayBusiness(bean);

            // 客户所欠金额
            bean.setReserve2(MathTools.formatNum(sumNoPayBusiness));

            // 剩余金额
            bean.setReserve3("MAX");

            list.add(bean);
        }

        request.setAttribute("list", list);

        return mapping.findForward("rptQuerySelfPublicClient");
    }
    
    /**
     * App 用户查询
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryAllAppUser(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
    	CommonTools.saveParamers(request);

    	String first = request.getParameter("first");

    	if (StringTools.isNullOrNone(first))
    	first = "";

    	if (first.equals("1"))
    	return mapping.findForward("rptQueryAllAppUser");

		List<AppUserVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();
            
            String name = request.getParameter("name");

            String code = request.getParameter("id");

            condtion.addIntCondition("AppUserBean.status", "=", CustomerConstant.USER_STATUS_AVAILABLE);
            
            if ( !StringTools.isNullOrNone(name))
            {
            	condtion.addCondition("AppUserBean.loginName", "like", name);
            }

            if ( !StringTools.isNullOrNone(code))
            {
                condtion.addCondition("AppUserBean.id", "like", code);
            }

            int total = appUserDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYALLAPPUSER);

            list = appUserDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYALLAPPUSER);

            list = appUserDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
            		RPTQUERYALLAPPUSER), PageSeparateTools.getPageSeparate(request,
            				RPTQUERYALLAPPUSER));
        }

        request.setAttribute("list", list);
        
    	return mapping.findForward("rptQueryAllAppUser");
	}
    
    /**
     * 查询自己名下的客户 - 部门、组织
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQuerySelfRelateClient(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String stafferId = request.getParameter("stafferId");
        
        String stype = request.getParameter("stype");
        
        int type = 0;
        
        if (!StringTools.isNullOrNone(stype))
        {
        	type = MathTools.parseInt(stype);
        }

        List<CustomerBean> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();
            
            // 部门客户
            condtion.addIntCondition("CustomerBean.type", "=", type);

            setInnerCondition2(request, condtion);
            
            int total = customerMainDAO.countSelfCustomerByConstion(stafferId, condtion);

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYSELFDEPARTCLIENT);

            list = customerMainDAO.querySelfCustomerByConstion(stafferId, condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYSELFDEPARTCLIENT);

            list = customerMainDAO.querySelfCustomerByConstion(stafferId, PageSeparateTools
                .getCondition(request, RPTQUERYSELFDEPARTCLIENT), PageSeparateTools.getPageSeparate(
                request, RPTQUERYSELFDEPARTCLIENT));
        }

        List<CustomerVO> newList = new ArrayList<CustomerVO>();
        
        for (CustomerBean each : list)
        {
        	CustomerVO vo = new CustomerVO();
        	
        	BeanUtil.copyProperties(vo, each);
        	
        	CustomerBean cust1 = customerMainDAO.find(each.getRefCorpCustId());
        	
        	if (null != cust1)
        	{
        		vo.setRefCorpCustName(cust1.getName());
        	}
        	
        	cust1 = customerMainDAO.find(each.getRefDepartCustId());
        	
        	if (null != cust1)
        	{
        		vo.setRefDepartCustName(cust1.getName());
        	}
        	
        	newList.add(vo);
        }
        request.setAttribute("list", newList);

        return mapping.findForward("rptQuerySelfRelateClient");
    }
    
    /**
     * rptQueryNotUseClient
     * 查询停用的客户,如果被其它客户占用了，则排除
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryNotUseClient(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String first = request.getParameter("first");
        
        if (StringTools.isNullOrNone(first))
        	first = "";
        
        if (first.equals("1"))
        	return mapping.findForward("rptQueryNotUseClient");
        
        String stype = request.getParameter("stype");
        
        int type = 0;
        
        if (!StringTools.isNullOrNone(stype))
        {
        	type = MathTools.parseInt(stype);
        }
        
        List<CustomerBean> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();
            
            // 部门客户
            condtion.addIntCondition("CustomerBean.type", "=", type);
            
            condtion.addIntCondition("CustomerBean.ostatus", "=", 1);
            
            String name = request.getParameter("name");

            String code = request.getParameter("code");

            if ( !StringTools.isNullOrNone(name))
            {
                condtion.addCondition("CustomerBean.name", "like", name);
            }

            if ( !StringTools.isNullOrNone(code))
            {
                condtion.addCondition("CustomerBean.code", "like", code);
            }

            int total = customerMainDAO.countByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYNOTUSECLIENT);

            list = customerMainDAO.queryEntityBeansByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYNOTUSECLIENT);

            list = customerMainDAO.queryEntityBeansByCondition(PageSeparateTools
                .getCondition(request, RPTQUERYNOTUSECLIENT), PageSeparateTools.getPageSeparate(
                request, RPTQUERYNOTUSECLIENT));
        }

        for (Iterator<CustomerBean> iterator = list.iterator(); iterator.hasNext();)
        {
        	CustomerBean bean = iterator.next();
        	
        	CustomerFormerNameBean formr = customerFormerNameDAO.findByUnique(bean.getId());
        	
        	if (null != formr)
        		iterator.remove();
        }
        
        request.setAttribute("list", list);

        return mapping.findForward("rptQueryNotUseClient");
    }
    
    /**
     * 接受批量移交的客户
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward customerBatchTrans(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException
    {
        try
        {
        	int type = MathTools.parseInt(request.getParameter("type"));
        	
            User user = Helper.getUser(request);

            clientFacade.batchTransCustomer(user.getId(), type);
            
            request.setAttribute(KeyConstant.MESSAGE, "成功回收/驳回职员客户");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "回收/驳回职员客户失败:" + e.getMessage());
        }

        return rptQueryClientBatchTrans(mapping, form, request, response);
    
    }


	/**
	 * #549 客户批量转移
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward batchTransferCustomer(ActionMapping mapping, ActionForm form,
										HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		RequestDataStream rds = new RequestDataStream(request);

		boolean importError = false;

		User user = Helper.getUser(request);

		//就几个字段:客户名，现业务员，新业务员
		List<DestStafferVSCustomerBean> importItemList = new ArrayList<>();

		StringBuilder builder = new StringBuilder();

		try
		{
			rds.parser();
		}
		catch (Exception e1)
		{
			_logger.error(e1, e1);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward("batchTransferCustomer");
		}

		if ( !rds.haveStream())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward("batchTransferCustomer");
		}

		ReaderFile reader = ReadeFileFactory.getXLSReader();

		try
		{
			reader.readFile(rds.getUniqueInputStream());

			while (reader.hasNext())
			{
				String[] obj = fillObj((String[])reader.next());

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
					DestStafferVSCustomerBean bean = new DestStafferVSCustomerBean();

					// 客户名
					if ( !StringTools.isNullOrNone(obj[0]))
					{
						String name = obj[0].trim();
						List<CustomerBean> customerBeans = this.customerMainDAO.queryByName(name);
						if (ListTools.isEmptyOrNull(customerBeans)){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("客户名不存在："+name)
									.append("<br>");

							importError = true;
						} else{
							CustomerBean customerBean = customerBeans.get(0);
							bean.setCustomerId(customerBean.getId());
							bean.setCustomerName(name);
						}
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("客户名不能为空")
								.append("<br>");

						importError = true;
					}


					// 现业务员
					if ( !StringTools.isNullOrNone(obj[1]))
					{
						String stafferName = obj[1].trim();

						StafferBean stafferBean = this.stafferDAO.findyStafferByName(stafferName);
						if (stafferBean == null){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("业务员不存在:"+stafferName)
									.append("<br>");

							importError = true;
						} else{
							bean.setStafferId(stafferBean.getId());

							boolean hasAuth = this.clientManager.hasCustomerAuth(stafferBean.getId(), bean.getCustomerId());
							if (!hasAuth){
								builder
										.append("第[" + currentNumber + "]错误:")
										.append("客户:"+bean.getCustomerName()+"和当前业务员:"+stafferName+"关系不正确")
										.append("<br>");

								importError = true;
							}
						}
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("业务员不能为空")
								.append("<br>");

						importError = true;
					}

					// 新业务员
					if ( !StringTools.isNullOrNone(obj[2]))
					{
						String stafferName = obj[2].trim();

						StafferBean stafferBean = this.stafferDAO.findyStafferByName(stafferName);
						if (stafferBean == null){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("新业务员不存在:"+stafferName)
									.append("<br>");

							importError = true;
						} else{
							bean.setDestStafferId(stafferBean.getId());
						}
					}
					else
					{
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("业务员不能为空")
								.append("<br>");

						importError = true;
					}

					importItemList.add(bean);
				}
				else
				{
					builder
							.append("第[" + currentNumber + "]错误:")
							.append("数据长度不足26格错误")
							.append("<br>");

					importError = true;
				}
			}
		}catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

			return mapping.findForward("batchTransferCustomer");
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

			return mapping.findForward("batchTransferCustomer");
		}

		try
		{
			this.clientManager.batchTransferCustomer(user ,importItemList);
			request.setAttribute(KeyConstant.MESSAGE, "批量导入成功");
		}
		catch(MYException e)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

			return mapping.findForward("batchTransferCustomer");
		}

		return mapping.findForward("batchTransferCustomer");
	}
    
    /**
     * 查询APP 用户审核
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAppUserApply(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException
	{
    	ConditionParse condtion = new ConditionParse();

    	condtion.addWhereStr();

    	String jsonstr = "";

    	ActionTools.processJSONQueryCondition(QUERYAPPUSERAPPLY, request, condtion);

    	jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPUSERAPPLY, request, condtion,
    	this.appUserApplyDAO);

    	return JSONTools.writeResponse(response, jsonstr);
	}
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAppUser(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException
	{
    	ConditionParse condtion = new ConditionParse();

    	condtion.addWhereStr();

    	String jsonstr = "";

    	ActionTools.processJSONQueryCondition(QUERYAPPUSER, request, condtion);

    	jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPUSER, request, condtion,
    	this.appUserDAO, new HandleResult<AppUserVO>()
		{
			public void handle(AppUserVO vo)
			{
				AppUserVSCustomerBean vscust = appUserVSCustomerDAO.findByUnique(vo.getId());
				
				if (null != vscust)
				{
					CustomerBean custBean = customerMainDAO.find(vscust.getCustomerId());
					
					if  (null != custBean){
						vo.setCustomerName(custBean.getName());
					}
				}
			}
		});

    	return JSONTools.writeResponse(response, jsonstr);
	}
    
    /**
     * 总裁配置信用等级
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward interposeLever(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("cid");

        String newLever = request.getParameter("newcval");

        StafferBean bean = stafferDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError("职员不存在", "interposeLever", mapping, request);
        }

        bean.setLever(CommonTools.parseInt(newLever));

        try
        {
            User user = Helper.getUser(request);

            publicFacade.updateStafferLever(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功设置职员的信用杠杆");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改失败:" + e.getMessage());
        }

        return mapping.findForward("interposeLever");
    }
    
    /**
     * queryApplyCustomerForCredit
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryApplyClientForCredit(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        // User user = Helper.getUser(request);

        condtion.addIntCondition("CustomerApproveBean.opr", "=", CustomerConstant.OPR_UPATE_CREDIT);

        // condtion.addCondition("CustomerApplyBean.locationId", "=", user.getLocationId());

        condtion.addIntCondition("CustomerApproveBean.status", "=", CustomerConstant.STATUS_APPLY);

        ActionTools.processJSONQueryCondition(QUERYAPPLYCLIENTFORCREDIT, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYAPPLYCLIENTFORCREDIT, request,
            condtion, this.customerApproveDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * 查询日志
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryChangeLog(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYCHANGELOG, request, condtion);

        condtion.addCondition("order by logTime desc");

        String jsonstr = ActionTools.queryBeanByJSONAndToString(QUERYCHANGELOG, request, condtion,
            this.changeLogDAO);

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryClientBySubStaffer(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
    throws ServletException
    {
        final ConditionParse condtion = new ConditionParse();
        
        condtion.addWhereStr();

        User user = Helper.getUser(request);
        
        String innerCon = commonManager.getAllSubStafferIds(user.getStafferId());
        
        condtion.addCondition(" and t4.id in " + innerCon);
        
        ActionTools.processJSONQueryCondition(QUERYCLIENTBYSUBSTAFFER, request, condtion);
        
        String jsonstr = ActionTools.querySelfBeanByJSONAndToString(QUERYCLIENTBYSUBSTAFFER, request,
            condtion, new CommonQuery()
            {
                public int getCount(String key, HttpServletRequest request, ConditionParse condition)
                {
                    return customerMainDAO.countCustomerBySubStaffer(condition);
                }

                public String getOrderPfix(String key, HttpServletRequest request)
                {
                    return "t1";
                }

                public List queryResult(String key, HttpServletRequest request,
                                        ConditionParse queryCondition)
                {
                    
                    return customerMainDAO.queryCustomerBySubStaffer(PageSeparateTools
                        .getCondition(request, key), PageSeparateTools
                        .getPageSeparate(request, key));

                }

                public String getSortname(HttpServletRequest request)
                {
                    return request.getParameter(ActionTools.SORTNAME);
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }
    
	/**
	 * @return the clientFacade
	 */
	public ClientFacade getClientFacade()
	{
		return clientFacade;
	}

	/**
	 * @param clientFacade the clientFacade to set
	 */
	public void setClientFacade(ClientFacade clientFacade)
	{
		this.clientFacade = clientFacade;
	}

	/**
	 * @return the customerApproveDAO
	 */
	public CustomerApproveDAO getCustomerApproveDAO()
	{
		return customerApproveDAO;
	}

	/**
	 * @param customerApproveDAO the customerApproveDAO to set
	 */
	public void setCustomerApproveDAO(CustomerApproveDAO customerApproveDAO)
	{
		this.customerApproveDAO = customerApproveDAO;
	}

	/**
	 * @return the clientManager
	 */
	public ClientManager getClientManager()
	{
		return clientManager;
	}

	/**
	 * @param clientManager the clientManager to set
	 */
	public void setClientManager(ClientManager clientManager)
	{
		this.clientManager = clientManager;
	}

	/**
	 * @return the userManager
	 */
	public UserManager getUserManager()
	{
		return userManager;
	}

	/**
	 * @param userManager the userManager to set
	 */
	public void setUserManager(UserManager userManager)
	{
		this.userManager = userManager;
	}

	/**
	 * @return the customerMainDAO
	 */
	public CustomerMainDAO getCustomerMainDAO()
	{
		return customerMainDAO;
	}

	/**
	 * @param customerMainDAO the customerMainDAO to set
	 */
	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO)
	{
		this.customerMainDAO = customerMainDAO;
	}

	/**
	 * @return the stafferVSCustomerDAO
	 */
	public StafferVSCustomerDAO getStafferVSCustomerDAO()
	{
		return stafferVSCustomerDAO;
	}

	/**
	 * @param stafferVSCustomerDAO the stafferVSCustomerDAO to set
	 */
	public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
	{
		this.stafferVSCustomerDAO = stafferVSCustomerDAO;
	}

	/**
	 * @return the customerContactApplyDAO
	 */
	public CustomerContactApplyDAO getCustomerContactApplyDAO()
	{
		return customerContactApplyDAO;
	}

	/**
	 * @param customerContactApplyDAO the customerContactApplyDAO to set
	 */
	public void setCustomerContactApplyDAO(
			CustomerContactApplyDAO customerContactApplyDAO)
	{
		this.customerContactApplyDAO = customerContactApplyDAO;
	}

	/**
	 * @return the customerBusinessApplyDAO
	 */
	public CustomerBusinessApplyDAO getCustomerBusinessApplyDAO()
	{
		return customerBusinessApplyDAO;
	}

	/**
	 * @param customerBusinessApplyDAO the customerBusinessApplyDAO to set
	 */
	public void setCustomerBusinessApplyDAO(
			CustomerBusinessApplyDAO customerBusinessApplyDAO)
	{
		this.customerBusinessApplyDAO = customerBusinessApplyDAO;
	}

	/**
	 * @return the customerContactDAO
	 */
	public CustomerContactDAO getCustomerContactDAO()
	{
		return customerContactDAO;
	}

	/**
	 * @param customerContactDAO the customerContactDAO to set
	 */
	public void setCustomerContactDAO(CustomerContactDAO customerContactDAO)
	{
		this.customerContactDAO = customerContactDAO;
	}

	/**
	 * @return the customerBusinessDAO
	 */
	public CustomerBusinessDAO getCustomerBusinessDAO()
	{
		return customerBusinessDAO;
	}

	/**
	 * @param customerBusinessDAO the customerBusinessDAO to set
	 */
	public void setCustomerBusinessDAO(CustomerBusinessDAO customerBusinessDAO)
	{
		this.customerBusinessDAO = customerBusinessDAO;
	}

	/**
	 * @return the assignApplyDAO
	 */
	public AssignApplyDAO getAssignApplyDAO()
	{
		return assignApplyDAO;
	}

	/**
	 * @param assignApplyDAO the assignApplyDAO to set
	 */
	public void setAssignApplyDAO(AssignApplyDAO assignApplyDAO)
	{
		this.assignApplyDAO = assignApplyDAO;
	}

	/**
	 * @return the commonManager
	 */
	public CommonManager getCommonManager()
	{
		return commonManager;
	}

	/**
	 * @param commonManager the commonManager to set
	 */
	public void setCommonManager(CommonManager commonManager)
	{
		this.commonManager = commonManager;
	}

	/**
	 * @return the destStafferVSCustomerDAO
	 */
	public DestStafferVSCustomerDAO getDestStafferVSCustomerDAO()
	{
		return destStafferVSCustomerDAO;
	}

	/**
	 * @param destStafferVSCustomerDAO the destStafferVSCustomerDAO to set
	 */
	public void setDestStafferVSCustomerDAO(
			DestStafferVSCustomerDAO destStafferVSCustomerDAO)
	{
		this.destStafferVSCustomerDAO = destStafferVSCustomerDAO;
	}

	/**
	 * @return the stafferDAO
	 */
	public StafferDAO getStafferDAO()
	{
		return stafferDAO;
	}

	/**
	 * @param stafferDAO the stafferDAO to set
	 */
	public void setStafferDAO(StafferDAO stafferDAO)
	{
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the locationDAO
	 */
	public LocationDAO getLocationDAO()
	{
		return locationDAO;
	}

	/**
	 * @param locationDAO the locationDAO to set
	 */
	public void setLocationDAO(LocationDAO locationDAO)
	{
		this.locationDAO = locationDAO;
	}

	/**
	 * @return the creditLevelDAO
	 */
	public CreditLevelDAO getCreditLevelDAO()
	{
		return creditLevelDAO;
	}

	/**
	 * @param creditLevelDAO the creditLevelDAO to set
	 */
	public void setCreditLevelDAO(CreditLevelDAO creditLevelDAO)
	{
		this.creditLevelDAO = creditLevelDAO;
	}

	/**
	 * @return the dutyDAO
	 */
	public DutyDAO getDutyDAO()
	{
		return dutyDAO;
	}

	/**
	 * @param dutyDAO the dutyDAO to set
	 */
	public void setDutyDAO(DutyDAO dutyDAO)
	{
		this.dutyDAO = dutyDAO;
	}

	/**
	 * @return the appUserDAO
	 */
	public AppUserDAO getAppUserDAO()
	{
		return appUserDAO;
	}

	/**
	 * @param appUserDAO the appUserDAO to set
	 */
	public void setAppUserDAO(AppUserDAO appUserDAO)
	{
		this.appUserDAO = appUserDAO;
	}

	/**
	 * @return the appUserApplyDAO
	 */
	public AppUserApplyDAO getAppUserApplyDAO()
	{
		return appUserApplyDAO;
	}

	/**
	 * @param appUserApplyDAO the appUserApplyDAO to set
	 */
	public void setAppUserApplyDAO(AppUserApplyDAO appUserApplyDAO)
	{
		this.appUserApplyDAO = appUserApplyDAO;
	}

	/**
	 * @return the appUserManager
	 */
	public AppUserManager getAppUserManager()
	{
		return appUserManager;
	}

	/**
	 * @param appUserManager the appUserManager to set
	 */
	public void setAppUserManager(AppUserManager appUserManager)
	{
		this.appUserManager = appUserManager;
	}

	/**
	 * @return the appUserVSCustomerDAO
	 */
	public AppUserVSCustomerDAO getAppUserVSCustomerDAO()
	{
		return appUserVSCustomerDAO;
	}

	/**
	 * @param appUserVSCustomerDAO the appUserVSCustomerDAO to set
	 */
	public void setAppUserVSCustomerDAO(AppUserVSCustomerDAO appUserVSCustomerDAO)
	{
		this.appUserVSCustomerDAO = appUserVSCustomerDAO;
	}

	/**
	 * @return the publicFacade
	 */
	public PublicFacade getPublicFacade()
	{
		return publicFacade;
	}

	/**
	 * @param publicFacade the publicFacade to set
	 */
	public void setPublicFacade(PublicFacade publicFacade)
	{
		this.publicFacade = publicFacade;
	}

	/**
	 * @return the changeLogDAO
	 */
	public ChangeLogDAO getChangeLogDAO()
	{
		return changeLogDAO;
	}

	/**
	 * @param changeLogDAO the changeLogDAO to set
	 */
	public void setChangeLogDAO(ChangeLogDAO changeLogDAO)
	{
		this.changeLogDAO = changeLogDAO;
	}

	/**
	 * @return the customerDistAddrApplyDAO
	 */
	public CustomerDistAddrApplyDAO getCustomerDistAddrApplyDAO()
	{
		return customerDistAddrApplyDAO;
	}

	/**
	 * @param customerDistAddrApplyDAO the customerDistAddrApplyDAO to set
	 */
	public void setCustomerDistAddrApplyDAO(
			CustomerDistAddrApplyDAO customerDistAddrApplyDAO)
	{
		this.customerDistAddrApplyDAO = customerDistAddrApplyDAO;
	}

	/**
	 * @return the customerDistAddrDAO
	 */
	public CustomerDistAddrDAO getCustomerDistAddrDAO()
	{
		return customerDistAddrDAO;
	}

	/**
	 * @param customerDistAddrDAO the customerDistAddrDAO to set
	 */
	public void setCustomerDistAddrDAO(CustomerDistAddrDAO customerDistAddrDAO)
	{
		this.customerDistAddrDAO = customerDistAddrDAO;
	}

	/**
	 * @return the customerFormerNameDAO
	 */
	public CustomerFormerNameDAO getCustomerFormerNameDAO()
	{
		return customerFormerNameDAO;
	}

	/**
	 * @param customerFormerNameDAO the customerFormerNameDAO to set
	 */
	public void setCustomerFormerNameDAO(CustomerFormerNameDAO customerFormerNameDAO)
	{
		this.customerFormerNameDAO = customerFormerNameDAO;
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

    public AreaDAO getAreaDAO() {
        return areaDAO;
    }

    public void setAreaDAO(AreaDAO areaDAO) {
        this.areaDAO = areaDAO;
    }

    public EnumDAO getEnumDAO() {
        return enumDAO;
    }

    public void setEnumDAO(EnumDAO enumDAO) {
        this.enumDAO = enumDAO;
    }

	public ExpressDAO getExpressDAO() {
		return expressDAO;
	}

	public void setExpressDAO(ExpressDAO expressDAO) {
		this.expressDAO = expressDAO;
	}

	public CustomerIndividualDAO getCustomerIndividualDAO() {
		return customerIndividualDAO;
	}

	public void setCustomerIndividualDAO(CustomerIndividualDAO customerIndividualDAO) {
		this.customerIndividualDAO = customerIndividualDAO;
	}
}
