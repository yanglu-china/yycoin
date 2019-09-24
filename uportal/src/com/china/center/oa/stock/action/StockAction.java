/**
 *
 */
package com.china.center.oa.stock.action;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.china.center.actionhelper.jsonimpl.JSONStringer;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.publics.bean.*;
import com.china.center.oa.publics.dao.*;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.ExpressBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.ExpressDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.tools.*;
import net.sf.json.JSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.common.OldPageSeparateTools;
import com.china.center.actionhelper.common.PageSeparateTools;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.jsonimpl.JSONArray;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProviderBean;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.ProductBOMDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.vo.DepotpartVO;
import com.china.center.oa.product.vo.ProductBOMVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.InvoiceConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.PublicLock;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.publics.manager.UserManager;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.publics.vs.RoleAuthBean;
import com.china.center.oa.sail.action.AppResult;
import com.china.center.oa.sail.action.JsonMapper;
import com.china.center.oa.stock.action.helper.PriceAskHelper;
import com.china.center.oa.stock.action.helper.StockHelper;
import com.china.center.oa.stock.bean.PriceAskBean;
import com.china.center.oa.stock.bean.PriceAskProviderBean;
import com.china.center.oa.stock.bean.PurchaseBjBean;
import com.china.center.oa.stock.bean.PurchaseXqqrBean;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.bean.StockItemArrivalBean;
import com.china.center.oa.stock.bean.StockItemBean;
import com.china.center.oa.stock.bean.StockWorkBean;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.constant.StockConstant;
import com.china.center.oa.stock.dao.PriceAskDAO;
import com.china.center.oa.stock.dao.PriceAskProviderDAO;
import com.china.center.oa.stock.dao.PurchaseBjDAO;
import com.china.center.oa.stock.dao.PurchaseXqqrDAO;
import com.china.center.oa.stock.dao.StockDAO;
import com.china.center.oa.stock.dao.StockItemArrivalDAO;
import com.china.center.oa.stock.dao.StockItemDAO;
import com.china.center.oa.stock.dao.StockWorkDAO;
import com.china.center.oa.stock.manager.PriceAskManager;
import com.china.center.oa.stock.manager.StockManager;
import com.china.center.oa.stock.vo.PriceAskProviderBeanVO;
import com.china.center.oa.stock.vo.StockItemArrivalVO;
import com.china.center.oa.stock.vo.StockItemVO;
import com.china.center.oa.stock.vo.StockVO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.osgi.jsp.ElTools;

import com.china.center.oa.sail.bean.DistributionBean;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


/**
 * 采购的的action
 * 
 * @author Administrator
 */
public class StockAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());
    
    private RoleAuthDAO   roleAuthDAO   = null;

    private PriceAskManager priceAskManager = null;
    
    protected CommonMailManager commonMailManager = null;

    private StockManager stockManager = null;

    private StockItemDAO stockItemDAO = null;

    private StockDAO stockDAO = null;
    
    private RoleDAO roleDAO = null;

    private ProductDAO productDAO = null;

    private ProductBOMDAO productBOMDAO = null;

    private UserManager userManager = null;

    private LocationDAO locationDAO = null;

    private StorageRelationDAO storageRelationDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private FinanceDAO financeDAO = null;

    private UserDAO userDAO = null;

    private DutyDAO dutyDAO = null;

    private ShowDAO showDAO = null;

    private CommonDAO commonDAO = null;

    private DepartmentDAO departmentDAO = null;

    private PriceAskProviderDAO priceAskProviderDAO = null;

    private PriceAskDAO priceAskDAO = null;

    private InvoiceDAO invoiceDAO = null;

    private StafferDAO stafferDAO = null;

    private DepotpartDAO depotpartDAO = null;
    
    private StockWorkDAO stockWorkDAO = null;

    private StockItemArrivalDAO stockItemArrivalDAO = null;

    private ProviderDAO providerDAO = null;

    private PurchaseBjDAO purchaseBjDAO = null;

    private PurchaseXqqrDAO purchaseXqqrDAO = null;

    private static String RPTQUERYSTOCKITEM = "rptQueryStockItem";

    protected OutManager outManager = null;

    protected OutDAO outDAO = null;
    protected BaseDAO baseDAO = null;

    protected ExpressDAO expressDAO = null;
    protected ProvinceDAO provinceDAO = null;

    protected CityDAO cityDAO = null;

    /**
     *
     */
    public StockAction()
    {
    }

    /**
     * 增加采购单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward addStock(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
    	User user = Helper.getUser(request);
    	
    	//商务 - begin
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
    	
    	if (null != error)
    	{
    		return error;
    	}
    	
        StockBean bean = new StockBean();

        String oprMode = request.getParameter("oprMode");
        String bjNo = request.getParameter("bjNo");
        String stype = request.getParameter("stype");
        try
        {
            BeanUtil.getBean(bean, request);
            bean.setStype(Integer.valueOf(stype));
            bean.setBjNo(bjNo);
            setStockBean(bean, request);
            setCommerceOperator(request, user, bean);
            bean.setUserId(user.getId());

            bean.setLocationId(user.getLocationId());

            bean.setLogTime(TimeTools.now());

            if (bean.getStockType() == StockConstant.STOCK_SAILTYPE_PUBLIC)
            {
                // 公共
                bean.setOwerId("0");
                bean.setStafferId(user.getStafferId());
            }
            else
            {
                String stafferId = request.getParameter("stafferId");

                if (StringTools.isNullOrNone(stafferId))
                {
                    bean.setOwerId(user.getStafferId());
                    bean.setStafferId(user.getStafferId());
                }
                else
                {
                    // 私人
                    bean.setOwerId(request.getParameter("stafferId"));
                    bean.setStafferId(request.getParameter("stafferId"));
                }
            }

            bean.setExceptStatus(StockConstant.EXCEPTSTATUS_COMMON);

            // 权限
            checkAddTypeAuth(user, String.valueOf(bean.getMode()));
            stockManager.addStockBean(user, bean);

            if ("1".equals(oprMode))
            {
                stockManager.passStock(user, bean.getId());
            }
            _logger.info("***addStock***"+bean);
            request.setAttribute(KeyConstant.MESSAGE, "成功增加采购单:" + bean.getId());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加采购单失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("forward", "1");

        request.getSession().setAttribute("g_ltype", "0");

        return queryStock(mapping, form, request, reponse);
    }

    private void setCommerceOperator(HttpServletRequest request, User user,
    		StockBean bean) throws MYException
	{
		User g_srcUser = (User)request.getSession().getAttribute("g_srcUser");
		
		String elogin = (String)request.getSession().getAttribute("g_elogin");
		
		String g_loginType = (String)request.getSession().getAttribute("g_loginType");
		
		if (!StringTools.isNullOrNone(elogin) && null == g_srcUser)
		{
		    throw new MYException("登陆异常,请重新登陆");
		}
		
		// 当前切换用户登陆的且为商务登陆的，记录经办人
		if (!StringTools.isNullOrNone(elogin) && null != g_srcUser && g_loginType.equals("1"))
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
	}

    private List<StockItemArrivalBean> getStockItemArrialBeanFromRequest(HttpServletRequest request){
        String stockId = request.getParameter("stockId");
        String[] productIds = request.getParameterValues("productId");
        String[] itemIds = request.getParameterValues("itemId");
        String[] amounts = request.getParameterValues("amount");
        String[] deliveryDates = request.getParameterValues("deliveryDate");
        String[] arrivalDates = request.getParameterValues("arrivalDate");

        _logger.info("***addStockArrival***"+stockId+"**productIds**"+productIds.length+
                "**itemIds**"+itemIds+
                "**amounts**"+amounts.length+"**deliveryDates**"+deliveryDates.length+"**arrivalDates**"+arrivalDates.length);

        List<StockItemArrivalBean> stockItemArrivalBeans = new ArrayList<StockItemArrivalBean>();

        for (int i=0;i<productIds.length;i++){
            String productId = productIds[i];
            if (!StringTools.isNullOrNone(productId)){
                StockItemArrivalBean bean = new StockItemArrivalBean();
                ConditionParse conditionParse = new ConditionParse();
                conditionParse.addWhereStr();
                conditionParse.addCondition("stockId", "=", stockId);
                conditionParse.addCondition("productId","=", productId);
                List<StockItemBean> stockItemBeans = this.stockItemDAO.queryEntityBeansByCondition(conditionParse);
                if (!ListTools.isEmptyOrNull(stockItemBeans)){
                    //copy bean from StockItemBean
                    StockItemBean stockItemBean = stockItemBeans.get(0);
                    BeanUtil.copyProperties(bean, stockItemBean);
                }

                bean.setId("");
                if (itemIds!= null && itemIds.length == productIds.length ){
                    String itemId = itemIds[i];
                    if (!StringTools.isNullOrNone(itemId)){
                        bean.setId(itemId);
                    }
                }


                bean.setStockId(stockId);
                bean.setProductId(productId);
                bean.setAmount(Integer.valueOf(amounts[i]));
                bean.setDeliveryDate(deliveryDates[i]);
                bean.setArrivalDate(arrivalDates[i]);


                stockItemArrivalBeans.add(bean);
            }
        }

        return stockItemArrivalBeans;
    }

    public ActionForward queryBjNo(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        User user = Helper.getUser(request);
        String stafferId = user.getStafferId();
        String bjNo = request.getParameter("bjNo");
        _logger.info("***queryBjNo***"+bjNo+"***stafferid***"+stafferId);

        JsonMapper mapper = new JsonMapper();
        AppResult result = new AppResult();

        try
        {
            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addWhereStr();
            conditionParse.addCondition("bjNo","=",bjNo);
            List<PurchaseBjBean> bjBeans = this.purchaseBjDAO.queryEntityBeansByCondition(conditionParse);
            List<PurchaseBjBean> filteredBjBeans = new ArrayList<PurchaseBjBean>();
            for (PurchaseBjBean bjBean: bjBeans){
                // 仅带出“确认供应商”字段等于“供应商”字段的商品信息
                if (bjBean.getSupplier()!= null && bjBean.getSupplier().equals(bjBean.getConfirmSupplier())){
                    //从需求确认表取得数量
                    ConditionParse conditionParse1 = new ConditionParse();
                    conditionParse1.addWhereStr();
                    conditionParse1.addCondition("demandQRId","=", bjBean.getDemandQRId());
                    conditionParse1.addCondition("pjid", "=", bjBean.getPjId());
                    conditionParse1.addCondition("demandId", "=", bjBean.getDemandId());

                    List<PurchaseXqqrBean> xqqrBeans = this.purchaseXqqrDAO.queryEntityBeansByCondition(conditionParse1);

                    if (!ListTools.isEmptyOrNull(xqqrBeans)){
                        // 拆单
                        boolean cdFlag = false;
                        for (PurchaseXqqrBean xqqrBean: xqqrBeans){
                            // 拆单
                            if (xqqrBean.getPurchaseStatus() == 4
                                    && bjBean.getAdviseSupplier()!= null && bjBean.getAdviseSupplier().equals(xqqrBean.getAdviseSupplier())){
                                bjBean.setAmount(xqqrBean.getPurchaseAmount());
                                cdFlag = true;
                                break;
                            }
                        }
                        if (!cdFlag){
                            bjBean.setAmount(xqqrBeans.get(0).getPurchaseAmount());
                        }

                        ProviderBean providerBean = this.providerDAO.findByUnique(bjBean.getConfirmSupplier());
                        if(providerBean!= null){
                            bjBean.setProviderId(providerBean.getId());
                            bjBean.setProviderName(providerBean.getName());
                        }
                        filteredBjBeans.add(bjBean);
                    } 
                }
            }

            //#389 同一供应商+商品+价格合并
            Map<String,PurchaseBjBean> supplierMap = new HashMap<String,PurchaseBjBean>();
            List<PurchaseBjBean> filteredBjBeansIgnoreDuplicate = new ArrayList<PurchaseBjBean>();
            for (PurchaseBjBean bjBean: filteredBjBeans){
                String key = bjBean.getConfirmSupplier()+"_"+bjBean.getPjId()+"_"+String.valueOf(bjBean.getPrice());
                PurchaseBjBean value = supplierMap.get(key);
                if (value == null){
                    supplierMap.put(key, bjBean);
                    filteredBjBeansIgnoreDuplicate.add(bjBean);
                } else{
                    value.setAmount(bjBean.getAmount()+value.getAmount());
                }
            }
            result.setSuccessAndObj("操作成功", filteredBjBeansIgnoreDuplicate);
        }
        catch(Exception e)
        {
            _logger.warn(e, e);

            result.setError("创建失败");
        }

        String jsonstr = mapper.toJson(result);

        _logger.info("***queryBjNo result***" + jsonstr);

        return JSONTools.writeResponse(reponse, jsonstr);
    }

    private boolean belongToStaffer(String stafferId, List<PurchaseXqqrBean> xqqrBeans){
        for (PurchaseXqqrBean bean: xqqrBeans){
            if (stafferId.equals(String.valueOf(bean.getPurchaser()))){
                return true;
            }
        }
        return false;
    }

    /**
     * 2015/12/12 导入采购行信息(成品或配件)
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importStockItem(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        String ptype = null;

        // You must use apache commons upload library to read payload of content type:form-data
        // You can not get field value like this: request.getParameter()
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);

        InputStream inputStream = null;
        try {
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            for (FileItem item : formItems) {
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    String fileName = FilenameUtils.getName(item.getName());
                    inputStream = item.getInputStream();
                    _logger.info("***filename***"+fileName+"***input***"+inputStream);
                    continue;
                } else {
                    //parse POST form data
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();
                    if ("ptype".equals(fieldName)) {
                        ptype = fieldValue;
                        _logger.info("***ptype****"+ptype);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        _logger.info("***import stock item***"+ptype);

        boolean importError = false;

        List<StockItemVO> stockItemBeans = new ArrayList<StockItemVO>();

        StringBuilder builder = new StringBuilder();

        ReaderFile reader = ReadeFileFactory.getXLSReader();

        if ("1".equals(ptype)){
             //成品
            _logger.info("***import***11111111111111");
            try
            {
                reader.readFile(inputStream);

                while (reader.hasNext())
                {
                    _logger.info("***import***222222222222222");
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
                    _logger.info("***import***3333333333333333");
                    if (obj.length >= 2 )
                    {
                        List<StockItemVO> bomList = new ArrayList<StockItemVO>();

                        // 产品名
                        if ( !StringTools.isNullOrNone(obj[0]))
                        {
                            String productName = obj[0].trim();
                            ConditionParse conditionParse = new ConditionParse();
                            conditionParse.addWhereStr();
                            conditionParse.addCondition("name","=",productName);
                            List<ProductBean> productBeans = this.productDAO.queryEntityBeansByCondition(conditionParse);
                            if (ListTools.isEmptyOrNull(productBeans)){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("产品名不存在")
                                        .append("<br>");

                                importError = true;
                            } else{
                                ProductBean product = productBeans.get(0);
                                String productId = product.getId();

                                List<ProductBOMVO> voList = productBOMDAO.queryEntityVOsByFK(productId);
                                if (ListTools.isEmptyOrNull(voList)){
                                    _logger.warn("product is not finished product:"+productName);
                                    StockItemVO bean = new StockItemVO();
                                    bean.setProductId(productId);
                                    bean.setProductName(productName);
                                    bomList.add(bean);
                                } else{
                                    for (ProductBOMVO bom :voList){
                                        StockItemVO bean = new StockItemVO();
                                        bean.setProductId(bom.getSubProductId());
                                        bean.setProductName(bom.getSubProductName());
                                        bomList.add(bean);
                                        _logger.info("***import bom***"+bean);
                                    }
                                }
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("产品名不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 供应商
                        if ( !StringTools.isNullOrNone(obj[1]))
                        {
                            String provider = obj[1].trim();
                            ConditionParse conditionParse = new ConditionParse();
                            conditionParse.addWhereStr();
                            conditionParse.addCondition("name", "=", provider);
                            List<ProviderBean> providerBeans = this.providerDAO.queryEntityBeansByCondition(conditionParse);
                            if (ListTools.isEmptyOrNull(providerBeans)){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("供应商不存在")
                                        .append("<br>");

                                importError = true;
                            } else{
                                ProviderBean providerBean = providerBeans.get(0);
                                for (StockItemVO bean :bomList){
                                    bean.setProviderId(providerBean.getId());
                                    bean.setProviderName(provider);
                                }
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("供应商不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 参考价格
                        if ( !StringTools.isNullOrNone(obj[2]))
                        {
                            String price = obj[2].trim();

                            for (StockItemVO bean :bomList){
                                bean.setPrice(MathTools.parseDouble(price));
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("参考价格不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 数量
                        if ( !StringTools.isNullOrNone(obj[3]))
                        {
                            String amount = obj[3].trim();

                            for (StockItemVO bean :bomList){
                                bean.setAmount(Integer.valueOf(amount));
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("数量不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 发票类型
                        if ( !StringTools.isNullOrNone(obj[4]))
                        {
                            String invoiceType = obj[4].trim();

                            ConditionParse conditionParse = new ConditionParse();
                            conditionParse.addWhereStr();
                            conditionParse.addCondition("name","=", invoiceType);
                            List<InvoiceBean> invoiceBeans = this.invoiceDAO.queryEntityBeansByCondition(conditionParse);
                            if (ListTools.isEmptyOrNull(invoiceBeans)){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("发票类型不存在")
                                        .append("<br>");

                                importError = true;
                            } else{
                                InvoiceBean invoiceBean = invoiceBeans.get(0);
                                for (StockItemVO bean :bomList){
                                    bean.setInvoiceType(invoiceBean.getId());
                                }
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("发票类型不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 纳税实体
                        if ( !StringTools.isNullOrNone(obj[5]))
                        {
                            String dutyName = obj[5].trim();
                            ConditionParse conditionParse = new ConditionParse();
                            conditionParse.addWhereStr();
                            conditionParse.addCondition("name","=",dutyName);
                            List<DutyBean> dutyBeans = this.dutyDAO.queryEntityBeansByCondition(conditionParse);
                            if (ListTools.isEmptyOrNull(dutyBeans)){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("纳税实体不存在")
                                        .append("<br>");

                                importError = true;
                            } else{
                                DutyBean dutyBean = dutyBeans.get(0);
                                for (StockItemVO bean :bomList){
                                    bean.setDutyId(dutyBean.getId());
                                    bean.setDutyName(dutyName);
                                }
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("纳税实体不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 出货日期
                        if ( !StringTools.isNullOrNone(obj[6]))
                        {
                            String deliveryDate = obj[6].trim();
                            for (StockItemVO bean :bomList){
                                bean.setDeliveryDate(deliveryDate);
                            }
                        }

                        // 预计到货日期
                        if ( !StringTools.isNullOrNone(obj[7]))
                        {
                            String arrivalDate = obj[7].trim();
                            for (StockItemVO bean :bomList){
                                bean.setArrivalDate(arrivalDate);
                            }
                        }
                        stockItemBeans.addAll(bomList);
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
                importError = true;
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
            _logger.info("***import stock item***" + stockItemBeans);
        } else if ("0".equals(ptype)){
            //配件
            _logger.info("***import stock item accessory***11111111111111");
            try
            {
                reader.readFile(inputStream);

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
                        StockItemVO bean = new StockItemVO();

                        // 产品名
                        if ( !StringTools.isNullOrNone(obj[0]))
                        {
                            String productName = obj[0].trim();
                            ConditionParse conditionParse = new ConditionParse();
                            conditionParse.addWhereStr();
                            conditionParse.addCondition("name","=",productName);
                            List<ProductBean> productBeans = this.productDAO.queryEntityBeansByCondition(conditionParse);
                            if (ListTools.isEmptyOrNull(productBeans)){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("产品名不存在")
                                        .append("<br>");

                                importError = true;
                            } else{
                                ProductBean product = productBeans.get(0);
                                bean.setProductId(product.getId());
                                bean.setProductName(productName);
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("产品名不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 供应商
                        if ( !StringTools.isNullOrNone(obj[1]))
                        {
                            String provider = obj[1].trim();
                            ConditionParse conditionParse = new ConditionParse();
                            conditionParse.addWhereStr();
                            conditionParse.addCondition("name", "=", provider);
                            List<ProviderBean> providerBeans = this.providerDAO.queryEntityBeansByCondition(conditionParse);
                            if (ListTools.isEmptyOrNull(providerBeans)){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("供应商不存在")
                                        .append("<br>");

                                importError = true;
                            } else{
                                ProviderBean providerBean = providerBeans.get(0);
                                bean.setProviderId(providerBean.getId());
                                bean.setProviderName(provider);
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("供应商不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 参考价格
                        if ( !StringTools.isNullOrNone(obj[2]))
                        {
                            String price = obj[2].trim();

                            bean.setPrice(MathTools.parseDouble(price));
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("参考价格不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 数量
                        if ( !StringTools.isNullOrNone(obj[3]))
                        {
                            String amount = obj[3].trim();

                            bean.setAmount(Integer.valueOf(amount));
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("数量不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 发票类型
                        if ( !StringTools.isNullOrNone(obj[4]))
                        {
                            String invoiceType = obj[4].trim();

                            ConditionParse conditionParse = new ConditionParse();
                            conditionParse.addWhereStr();
                            conditionParse.addCondition("name","=", invoiceType);
                            List<InvoiceBean> invoiceBeans = this.invoiceDAO.queryEntityBeansByCondition(conditionParse);
                            if (ListTools.isEmptyOrNull(invoiceBeans)){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("发票类型不存在")
                                        .append("<br>");

                                importError = true;
                            } else{
                                InvoiceBean invoiceBean = invoiceBeans.get(0);
                                bean.setInvoiceType(invoiceBean.getId());
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("发票类型不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 纳税实体
                        if ( !StringTools.isNullOrNone(obj[5]))
                        {
                            String dutyName = obj[5].trim();
                            ConditionParse conditionParse = new ConditionParse();
                            conditionParse.addWhereStr();
                            conditionParse.addCondition("name","=",dutyName);
                            List<DutyBean> dutyBeans = this.dutyDAO.queryEntityBeansByCondition(conditionParse);
                            if (ListTools.isEmptyOrNull(dutyBeans)){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append("纳税实体不存在")
                                        .append("<br>");

                                importError = true;
                            } else{
                                DutyBean dutyBean = dutyBeans.get(0);
                                bean.setDutyId(dutyBean.getId());
                                bean.setDutyName(dutyName);
                            }
                        } else{
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("纳税实体不能为空")
                                    .append("<br>");

                            importError = true;
                        }

                        // 出货日期
                        if ( !StringTools.isNullOrNone(obj[6]))
                        {
                            String deliveryDate = obj[6].trim();
                            bean.setDeliveryDate(deliveryDate);
                        }

                        // 预计到货日期
                        if ( !StringTools.isNullOrNone(obj[7]))
                        {
                            String arrivalDate = obj[7].trim();
                            bean.setArrivalDate(arrivalDate);
                        }
                        stockItemBeans.add(bean);
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
                importError = true;
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


            _logger.info("***import accessory stock item***" + stockItemBeans);
        } else{
            importError = true;
            builder.append("请选择采购商品类别");
        }

        AjaxResult ajaxResult = new AjaxResult();
        if (importError || ListTools.isEmptyOrNull(stockItemBeans)){
            ajaxResult.setRet(0);
            ajaxResult.setMsg(builder.toString());
        }else {
            ajaxResult.setRet(1);
            ajaxResult.setMsg(stockItemBeans);
        }

        return JSONTools.writeResponse(response, ajaxResult);
    }


    /**
     *
     * @param obj
     * @return
     */
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[50];

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
     * 2015/12/3 采购到货信息
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    @Deprecated
    public ActionForward addStockArrival(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        User user = Helper.getUser(request);

        String stockId = request.getParameter("stockId");
        List<StockItemArrivalBean> stockItemArrivalBeans = this.getStockItemArrialBeanFromRequest(request);
        try
        {
            this.stockManager.addStockArrivalBean(user, stockItemArrivalBeans);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加采购到货信息:" + stockId);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加采购到货信息失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return queryStock(mapping, form, request, reponse);
    }


    public ActionForward updateStockArrival(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        User user = Helper.getUser(request);
        String stockId = request.getParameter("stockId");
        StockBean stockBean = new StockBean();
        stockBean.setId(stockId);
        List<StockItemArrivalBean> stockItemArrivalBeans = this.getStockItemArrialBeanFromRequest(request);
        stockBean.setArrivalBeans(stockItemArrivalBeans);

        try
        {
            this.stockManager.updateStockArrivalBean(user, stockBean);
            request.setAttribute(KeyConstant.MESSAGE, "成功修改采购到货信息:" + stockId);
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改采购到货信息失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return queryStock(mapping, form, request, reponse);
    }
    
    /**
     * 自动生成外网询价单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward createAskBean(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String itemId = request.getParameter("itemId");

        StockItemBean item = stockItemDAO.find(itemId);

        if (item == null)
        {
            return ActionTools.toError("数据错误,请确认操作", mapping, request);
        }

        PriceAskBean old = priceAskDAO.findByDescription(itemId);

        if (old != null)
        {
            return ActionTools.toError("已经生成自动询价单,请确认操作", mapping, request);
        }

        User user = Helper.getUser(request);

        try
        {
            PriceAskBean bean = new PriceAskBean();

            setAutoAskBean(mapping, request, itemId, item, user, bean);

            priceAskManager.addPriceAskBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功增加询价申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加询价申失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("forward", "1");

        return queryStock(mapping, form, request, reponse);
    }

    /**
     * setAutoAskBean
     * 
     * @param mapping
     * @param request
     * @param itemId
     * @param item
     * @param user
     * @param bean
     */
    private void setAutoAskBean(ActionMapping mapping, HttpServletRequest request, String itemId,
                                StockItemBean item, User user, PriceAskBean bean)
    {
        bean.setId(SequenceTools.getSequence("ASK", 5));

        bean.setProductId(item.getProductId());

        bean.setAmount(item.getAmount());

        bean.setSrcamount(item.getAmount());

        bean.setType(PriceConstant.PRICE_ASK_TYPE_NET);

        bean.setUserId(user.getId());

        bean.setLogTime(TimeTools.now());

        bean.setStatus(PriceConstant.PRICE_COMMON);

        bean.setLocationId(user.getLocationId());

        bean.setDescription(itemId);

        ProductBean product = productDAO.find(bean.getProductId());

        if (product != null)
        {
            bean.setProductType(product.getType());
        }

        StockBean stock = stockDAO.find(item.getStockId());

        if (stock != null)
        {
            bean.setUserId(stock.getUserId());

            bean.setLocationId(stock.getLocationId());
        }

        bean.setProcessTime(TimeTools.getDateString(1, TimeTools.LONG_FORMAT));

        bean.setAskDate(TimeTools.getDateString(1, "yyyyMMdd"));
    }

    /**
     * 修改采购单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updateStock(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        StockBean bean = new StockBean();

        try
        {
            BeanUtil.getBean(bean, request);

            setStockBean(bean, request);

            User user = Helper.getUser(request);

            bean.setUserId(user.getId());

            bean.setLocationId(user.getLocationId());

            bean.setLogTime(TimeTools.now());

            bean.setStafferId(user.getStafferId());

            bean.setExceptStatus(StockConstant.EXCEPTSTATUS_COMMON);

            // 权限
            checkAddTypeAuth(user, String.valueOf(bean.getMode()));

            stockManager.updateStockBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改采购单:" + bean.getId());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改采购单失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("forward", "1");

        request.setAttribute("auto", "1");

        return queryStock(mapping, form, request, reponse);
    }

    /**
     * updateStock2
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updateStockDutyConfig(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request,
                                               HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String willDate = request.getParameter("willDate");

        StockBean bean = stockDAO.find(id);

        try
        {
            setStockBean2(bean, request);

            User user = Helper.getUser(request);

            bean.setInvoice(StockConstant.INVOICE_YES);

            bean.setWillDate(MathTools.parseInt(willDate));

            stockManager.updateStockDutyConfig(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功配置采购单:" + bean.getId());
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "配置采购单失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return queryStock(mapping, form, request, reponse);
    }

    /**
     * 修改采购单的状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward updateStockStatus(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        String pass = request.getParameter("pass");

        String nearlyPayDate = TimeTools.now_short(30);

        String reject = request.getParameter("reject");
        
        //StockItemVO vo = stockItemDAO.findVO(id);

        try
        {
            User user = Helper.getUser(request);

            if ( !StringTools.isNullOrNone(nearlyPayDate))
            {
                stockManager.updateStockNearlyPayDate(user, id, nearlyPayDate);
            }

            if ( !StringTools.isNullOrNone(pass))
            {
                stockManager.passStock(user, id);

                request.setAttribute(KeyConstant.MESSAGE, "成功处理采购单:" + id);
            }
            else
            {
                if ("1".equals(reject))
                {
                    stockManager.rejectStock(user, id, reason);
                    
                    //sendOutRejectMail(id, user, reason, vo,"采购单驳回");
                }

                request.setAttribute(KeyConstant.MESSAGE, "成功处理采购单:" + id);

                if ("2".equals(reject))
                {
                    stockManager.rejectStockToAsk(user, id, reason);

                    request.setAttribute(KeyConstant.MESSAGE, "成功驳回采购单到询价员:" + id);
                }
            }
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理采购单失败:" + e.getMessage());
        }

        CommonTools.saveParamers(request);

        request.setAttribute("forward", "1");

        return queryStock(mapping, form, request, reponse);
    }

    /**
     * 结束采购单，并自动生成入库单
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward endStock(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String reason = request.getParameter("reason");

        try
        {
            User user = Helper.getUser(request);

            stockManager.endStock(user, id, reason);

            request.setAttribute(KeyConstant.MESSAGE, "成功结束采购单,并自动生成了入库单");
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "结束采购单失败:" + e.getMessage());
        }

        CommonTools.saveParamers(request);

        request.setAttribute("forward", "1");

        return queryStock(mapping, form, request, reponse);
    }

    /**
     * 修改采购单的状态
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward stockItemAskChange(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String stockId = request.getParameter("stockId");

        String providerId = request.getParameter("providerId");

        try
        {
            stockManager.stockItemAskChange(id, providerId);

            request.setAttribute(KeyConstant.MESSAGE, "成功修改产品的供应商");
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "修改采购单状态失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("forward", "1");

        request.setAttribute("stockId", stockId);

        request.setAttribute("stockAskChange", "1");

        return findStock(mapping, form, request, reponse);
    }

    /**
     * #306
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward batchFetchProduct(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {

        _logger.info("***batchFetchProduct***");
        // LOCK 采购拿货变动库存
        synchronized (PublicLock.PRODUCT_CORE)
        {
            String[] itemIds = request.getParameterValues("itemId");

            String depotpartId = request.getParameter("depotpartId");

            String[] batchWarehouseNums = request.getParameterValues("batchWarehouseNum");
            String[] to_be_warehouses = request.getParameterValues("to_be_warehouse");
            _logger.info(itemIds+"***warehouseNum*********"+batchWarehouseNums+"***to_be_warehouse"+to_be_warehouses);

            boolean result = true;
            String msg = "成功拿货,且自动生成入库单";
            try
            {
                User user = Helper.getUser(request);

                for (int i=0;i<=itemIds.length-1; i++){
                    String itemId = itemIds[i];
                    int warehouseNumber = Integer.valueOf(batchWarehouseNums[i]);
                    int toBeWarehouseNum = Integer.valueOf(to_be_warehouses[i]);
                    if (warehouseNumber!= 0){
                        result = stockManager.fetchProductByArrivalBean(user, itemId, depotpartId, warehouseNumber, toBeWarehouseNum,
                                null);
                    }
                }

                request.setAttribute(KeyConstant.MESSAGE, msg);
            }
            catch(NumberFormatException e){
                _logger.warn(e, e);
                msg = "批量拿货数量每行必填";
                result = false;
            }
            catch (MYException e)
            {
                e.printStackTrace();
                _logger.warn(e, e);
                msg = e.getErrorContent();
                result = false;
            } finally{
                if (!result){
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "拿货失败:"+msg);
                }
            }

            CommonTools.removeParamers(request);

            request.setAttribute("forward", "1");

            return queryStock(mapping, form, request, reponse);
        }
    }

    /**
     * fechProduct
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward fechProduct(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        // LOCK 采购拿货变动库存
        synchronized (PublicLock.PRODUCT_CORE)
        {
            String itemId = request.getParameter("itemId");

            String depotpartId = request.getParameter("depotpartId");

            String warehouseNum = request.getParameter("warehouseNum");
            String to_be_warehouse = request.getParameter("to_be_warehouse");
            String demandQRId = request.getParameter("demandQRId");
           _logger.info("warehouseNum*********"+warehouseNum+"****to_be_warehouse*****"+to_be_warehouse+"**demandQRId***"+demandQRId);
            int  warehouseNumber = 0;
            int toBeWarehouseNum = Integer.valueOf(to_be_warehouse);
            if (StringTools.isNullOrNone(warehouseNum)){
                _logger.info("warehouseNum is empty and will use default to_be_warehouse value");
                warehouseNumber = Integer.valueOf(to_be_warehouse);
            } else{
                warehouseNumber = Integer.valueOf(warehouseNum);
            }

            boolean result = true;
            String msg = "成功拿货,且自动生成入库单";
            try
            {
                User user = Helper.getUser(request);

                result = stockManager.fetchProductByArrivalBean(user, itemId, depotpartId, warehouseNumber, toBeWarehouseNum,
                        demandQRId);

                request.setAttribute(KeyConstant.MESSAGE, msg);
            }
            catch (MYException e)
            {
                e.printStackTrace();
                _logger.warn(e, e);
                msg = e.getErrorContent();
                result = false;
            } finally{
                if (!result){
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "拿货失败:"+msg);
                }
            }

            CommonTools.removeParamers(request);

            request.setAttribute("forward", "1");

            return queryStock(mapping, form, request, reponse);
        }
    }

    /**
     * 收集数据
     * 
     * @param pbean
     * @param request
     * @throws MYException
     */
    private void setStockBean2(StockBean pbean, HttpServletRequest request)
        throws MYException
    {
        List<StockItemBean> item = new ArrayList<StockItemBean>();

        for (int i = 0; i < 1000; i++ )
        {
            String pid = request.getParameter("productId_" + i);

            if ( !StringTools.isNullOrNone(pid))
            {
                StockItemBean bean = new StockItemBean();

                bean.setProductId(request.getParameter(pid));

                bean.setPriceAskProviderId(request.getParameter("netaskId_" + i));

                bean.setId(request.getParameter("itemId_" + i));

                bean.setLogTime(TimeTools.now());

                bean.setPrePrice(Float.parseFloat(request.getParameter("price_" + i)));

                bean.setShowId(request.getParameter("showId_" + i));

                bean.setDutyId(request.getParameter("dutyId_" + i));

                String invoiceType = request.getParameter("invoiceType_" + i);

                bean.setInvoiceType(invoiceType);

                bean.setAmount(CommonTools.parseInt(request.getParameter("amount_" + i)));

                int num = storageRelationDAO.sumAllProductByProductId(bean.getProductId());

                // 当前库存总数量
                bean.setProductNum(num);

                bean.setStatus(StockConstant.STOCK_ITEM_STATUS_INIT);

                item.add(bean);
            }
            else
            {
                break;
            }
        }

        pbean.setItem(item);
    }

    private void setStockBean(StockBean pbean, HttpServletRequest request)
        throws MYException
    {
        String[] indexs = request.getParameterValues("check_init");

        List<StockItemBean> item = new ArrayList<StockItemBean>();

        for (int i = 0; i < indexs.length; i++ )
        {
            if ( !StringTools.isNullOrNone(indexs[i]))
            {
                StockItemBean bean = new StockItemBean();

                bean.setProductId(request.getParameter("productId_" + indexs[i]));

//                bean.setPriceAskProviderId(request.getParameter("netaskId_" + indexs[i]));

                bean.setLogTime(TimeTools.now());

                bean.setPrePrice(Float.parseFloat(request.getParameter("price_" + indexs[i])));

                //2015/10/28 实际价格==参考价格
                bean.setPrice(bean.getPrePrice());

                bean.setShowId(request.getParameter("showId_" + indexs[i]));

                bean.setAmount(CommonTools.parseInt(request.getParameter("amount_" + indexs[i])));
                //2015/10/29 直接设置total
                bean.setTotal(bean.getPrice() * bean.getAmount());
                bean.setNearlyPayDate(TimeTools.now_short());

                int num = storageRelationDAO.sumAllProductByProductId(bean.getProductId());

                bean.setProductNum(num);

                bean.setStatus(StockConstant.STOCK_ITEM_STATUS_INIT);

                //2015/10/24 增加供应商和发票类型
                bean.setProviderId(request.getParameter("providerId_" + indexs[i]));
                bean.setInvoiceType(request.getParameter("invoiceType_" + indexs[i]));
                bean.setDutyId(request.getParameter("dutyId_" + indexs[i]));

                //2015/11/10 导入发货日期和预计到货日期
                String deliveryDate = request.getParameter("deliveryDate_"+indexs[i]);
                String arrivalDate = request.getParameter("arrivalDate_" + indexs[i]);
                if (!StringTools.isNullOrNone(deliveryDate)) {
                    bean.setDeliveryDate(deliveryDate);
                }
                if (!StringTools.isNullOrNone(arrivalDate)){
                    bean.setArrivalDate(arrivalDate);
                }

                item.add(bean);
            }
        }

        String invoiceType = request.getParameter("invoiceType");

        if (StringTools.isNullOrNone(invoiceType))
        {
            pbean.setInvoice(StockConstant.INVOICE_YES);
        }
        else
        {
            pbean.setInvoice(StockConstant.INVOICE_NO);
        }

        pbean.setItem(item);

        pbean.setType(PriceConstant.PRICE_ASK_TYPE_NET);

        User user = Helper.getUser(request);

        StafferBean sb = stafferDAO.find(user.getStafferId());

        if (sb == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (StringTools.isNullOrNone(sb.getIndustryId()))
        {
            throw new MYException("职员[%s]没有事业部属性(事业部下的4级组织),请确认", sb.getName());
        }

        pbean.setIndustryId(sb.getIndustryId());

        String description = request.getParameter("description");
        pbean.setDescription(description);
    }

    /**
     * rptInQueryPriceAskProvider
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptInQueryPriceAskProvider(ActionMapping mapping, ActionForm form,
                                                    HttpServletRequest request,
                                                    HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String productId = request.getParameter("productId");

        String stockId = request.getParameter("stockId");

        // 价格是每天都询价
        List<PriceAskProviderBeanVO> beanList = priceAskProviderDAO.queryByCondition(TimeTools
            .now("yyyyMMdd"), productId, stockId);

        // 获取PID
        for (PriceAskProviderBeanVO vo : beanList)
        {
            PriceAskBean ask = priceAskDAO.find(vo.getAskId());

            if (ask == null)
            {
                continue;
            }

            vo.setPid(vo.getId());

            int sum = stockItemDAO.sumNetProductByPid(vo.getId());

            vo.setRemainmount(vo.getSupportAmount() - sum);

            User fuser = userManager.findUser(vo.getUserId());

            if (fuser != null)
            {
                vo.setStafferName(fuser.getStafferName());

                vo.setStafferId(fuser.getStafferId());
            }
        }

        request.setAttribute("beanList", beanList);

        return mapping.findForward("rptPriceAskProviderList");
    }

    /**
     * rptQueryStockItem
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryStockItem(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        List<StockItemVO> list = null;

        if (PageSeparateTools.isFirstLoad(request))
        {
            ConditionParse condtion = new ConditionParse();

            condtion.addWhereStr();

            setInnerCondition2(request, condtion);

            int total = stockItemDAO.countVOByCondition(condtion.toString());

            PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_COMMON_SIZE);

            PageSeparateTools.initPageSeparate(condtion, page, request, RPTQUERYSTOCKITEM);

            list = stockItemDAO.queryEntityVOsByCondition(condtion, page);
        }
        else
        {
            PageSeparateTools.processSeparate(request, RPTQUERYSTOCKITEM);

            list = stockItemDAO.queryEntityVOsByCondition(PageSeparateTools.getCondition(request,
                RPTQUERYSTOCKITEM), PageSeparateTools.getPageSeparate(request, RPTQUERYSTOCKITEM));
        }

        request.setAttribute("list", list);

        return mapping.findForward("rptQueryStockItem");
    }

    /**
     * setInnerCondition2
     * 
     * @param request
     * @param condtion
     */
    private void setInnerCondition2(HttpServletRequest request, ConditionParse condtion)
    {
        // 条件查询
        String alogTime = request.getParameter("alogTime");

        String blogTime = request.getParameter("blogTime");

        String stockId = request.getParameter("stockId");

        if ( !StringTools.isNullOrNone(alogTime))
        {
            condtion.addCondition("StockBean.logTime", ">=", alogTime);
        }
        else
        {
            condtion.addCondition("StockBean.logTime", ">=", TimeTools.now( -180));

            request.setAttribute("alogTime", TimeTools.now( -180));
        }

        if ( !StringTools.isNullOrNone(blogTime))
        {
            condtion.addCondition("StockBean.logTime", "<=", blogTime);
        }
        else
        {
            condtion.addCondition("StockBean.logTime", "<=", TimeTools.now());

            request.setAttribute("blogTime", TimeTools.now());
        }

        if ( !StringTools.isNullOrNone(stockId))
        {
            condtion.addCondition("StockBean.id", "like", stockId);
        }

        // (url)固定查询
        String providerId = request.getParameter("providerId");

        if ( !StringTools.isNullOrNone(providerId))
        {
            condtion.addCondition("StockItemBean.providerId", "=", providerId);
        }

        condtion.addCondition("StockBean.nearlyPayDate", "<=", TimeTools.now_short());

        condtion.addIntCondition("StockItemBean.pay", "=", StockConstant.STOCK_PAY_NO);

        condtion.addIntCondition("StockBean.status", "=", StockConstant.STOCK_STATUS_LASTEND);

        condtion.addCondition("order by StockBean.logTime desc");
    }

    /**
     * 查询采购单据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward findStock(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String ltype = getLType(request);

        int queryType = CommonTools.parseInt(ltype);

        request.setAttribute("ltype", ltype);

        if (StringTools.isNullOrNone(id))
        {
            id = (String)request.getAttribute("stockId");
        }

        String out = request.getParameter("out");

        String update = request.getParameter("update");

        String stockAskChange = request.getParameter("stockAskChange");

        if (StringTools.isNullOrNone(stockAskChange))
        {
            stockAskChange = (String)request.getAttribute("stockAskChange");
        }

        String process = request.getParameter("process");

        if (StringTools.isNullOrNone(process))
        {
            process = (String)request.getAttribute("process");
        }

        StockVO vo = null;

        vo = stockManager.findStockVO(id);

        request.setAttribute("bean", vo);
//        request.setAttribute("divMap", vo.getDivMap());

        prepare(request);

        if ( !StringTools.isNullOrNone(update))
        {
            if ( !"2".equals(update))
            {
                int last = 20 - vo.getItemVO().size();

                // 补齐
                for (int i = 0; i < last; i++ )
                {
                    vo.getItemVO().add(new StockItemVO());
                }

                request.setAttribute("maxItem", 20 - last);
            }

            if ("1".equals(update))
            {
                return mapping.findForward("updateStock");
            }
            else
            {
                // 如果没有询价不能配置
                List<StockItemVO> itemVO = vo.getItemVO();

                //2015/10/24 取消询价
//                for (StockItemVO stockItemVO : itemVO)
//                {
//                    if (stockItemVO.getStatus() == StockConstant.STOCK_ITEM_STATUS_INIT)
//                    {
//                        return ActionTools.toError("还没有全部询价,不能配置税务属性", mapping, request);
//                    }
//                }

                // 过滤管理
                if (OATools.getManagerFlag())
                {
                    List<DutyBean> dutyList = dutyDAO.listEntityBeans();

/*                    if (vo.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
                    {
                        DutyBean defalit = dutyDAO.find(PublicConstant.DEFAULR_DUTY_ID);

                        dutyList.add(defalit);
                    }
                    else
                    {

                        DutyBean manager = dutyDAO.find(PublicConstant.MANAGER_DUTY_ID);

                        dutyList.add(manager);
                    }*/

                    for (Iterator<DutyBean> iterator = dutyList.iterator(); iterator.hasNext();)
                    {
                    	DutyBean dbean = iterator.next();
                    	
                    	// 去掉管理
                    	if (dbean.getMtype() == 0)
                    	{
                    		iterator.remove();
                    		
                    		continue;
                    	}
                    	
                    	if (dbean.getName().contains("停用"))
                    	{
                    		iterator.remove();
                    	}
                    }

                    if (vo.getMtype() == StockConstant.MANAGER_TYPE_MANAGER)
                    {
                    	dutyList.clear();
                    	
                    	DutyBean manager = dutyDAO.find(PublicConstant.MANAGER_DUTY_ID);

                        dutyList.add(manager);
                    }
                    
                    // 管理默认为 17%
                    /*if (vo.getMtype() == StockConstant.MANAGER_TYPE_COMMON)
                    {
                    	for(StockItemVO eachitem : vo.getItemVO())
                    	{
                    		eachitem.setInvoiceType("90000000000000000001");
                    	}
                    }*/
                    /*else if(vo.getMtype() == StockConstant.MANAGER_TYPE_COMMON3)
                    {
                    	for(StockItemVO eachitem : vo.getItemVO())
                    	{
                    		eachitem.setInvoiceType("90000000000000000002");
                    	}
                    }
                    else // 默认为空
                    {
                    	for(StockItemVO eachitem : vo.getItemVO())
                    	{
                    		eachitem.setInvoiceType("");
                    	}
                    }*/

                    request.setAttribute("dutyList", dutyList);
                }

                return mapping.findForward("updateStockDutyConfig");
            }
        }

        if ("1".equals(process))
        {
            return mapping.findForward("processStock");
        }

        // 询价人拿货
        if ("2".equals(process) || "21".equals(process))
        {
            List<DepotpartBean> depotpartList = new ArrayList<DepotpartBean>();

            //#531 去掉采购中心库
//            if (vo.getMode() == StockConstant.STOCK_MODE_SAIL)
//            {
//                // 查询采购中心的良品仓区
//                depotpartList = depotpartDAO.queryOkDepotpartInDepot(DepotConstant.STOCK_DEPOT_ID);
//            }
            //#107
//            else
//            {
//                // 查询生产库
//                depotpartList = depotpartDAO.queryOkDepotpartInDepot(DepotConstant.MAKE_DEPOT_ID);
//            }

            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addWhereStr();
            // #486 去掉到货预告库_默认仓区
//            conditionParse.addCondition("name","=","在售仓");
//            conditionParse.addCondition(" and name in ('在售仓','到货预告库_默认仓区')");
            //#531 生产作业库-采购仓，生产作业库-深圳百吉仓，生产作业库-东莞铭丰仓，生产作业库-其他生产仓，生产作业库-无锡巨洋仓，生产作业库-南京国声仓
            conditionParse.addCondition(" and name in ('在售仓','采购仓','深圳百吉仓','东莞铭丰仓','其他生产仓','无锡巨洋仓','南京国声仓')");
            List<DepotpartBean> depotpartBeans = this.depotpartDAO.queryEntityBeansByCondition(conditionParse);
            if (!ListTools.isEmptyOrNull(depotpartBeans)){
                depotpartList.addAll(depotpartBeans);
            }
            List<DepotpartVO> voList = new ArrayList<>();
            for (DepotpartBean bean: depotpartList){
                DepotpartVO depotpartVO = new DepotpartVO();
                BeanUtil.copyProperties(depotpartVO, bean);
                DepotpartVO temp = this.depotpartDAO.findVO(bean.getId());
                depotpartVO.setLocationName(temp.getLocationName());
                voList.add(depotpartVO);
            }
            request.setAttribute("depotpartList", voList);
            List<StockItemArrivalVO> stockItemArrivalBeans = this.stockItemArrivalDAO.queryEntityVOsByFK(vo.getId());
            vo.setStockItemArrivalVOs(stockItemArrivalBeans);
            _logger.info("***stockItemArrivalBeans***"+stockItemArrivalBeans.size());
            if ("2".equals(process)) {
                return mapping.findForward("processStock2");
            }
            else {
            	return mapping.findForward("processStock21");
            }
        }

        // 获取审批日志
        List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);

        List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

        for (FlowLogBean flowLogBean : logs)
        {
            FlowLogVO flowLogVO = StockHelper.getStockFlowLogVO(flowLogBean);
            if (!StringTools.isNullOrNone(flowLogBean.getReserved1())){
                ProductBean productBean = this.productDAO.find(flowLogBean.getReserved1());
                if(productBean!= null){
                    flowLogVO.setProductName(productBean.getName());
                }
            }

            logsVO.add(flowLogVO);
        }

        // 获得询价的列表
        Map<String, String> map = new HashMap<String, String>();
        Map<String, List<PriceAskProviderBeanVO>> map1 = new HashMap<String, List<PriceAskProviderBeanVO>>();

        for (StockItemVO StockItemVO : vo.getItemVO())
        {
            if (StockItemVO.getStatus() > StockConstant.STOCK_ITEM_STATUS_INIT)
            {
                List<PriceAskProviderBeanVO> items = priceAskProviderDAO
                    .queryEntityVOsByFK(StockItemVO.getId());

                map1.put(StockItemVO.getId(), items);

                User user = Helper.getUser(request);

                map.put(StockItemVO.getId(), PriceAskHelper.createTable(items, user));

                // 业务员和区域经理不能看到供应商
                if (queryType == 0 || queryType == 1)
                {
                    StockItemVO.setProviderName("");
                }
            }
        }

        request.setAttribute("map", map);
        request.setAttribute("map1", map1);

        request.setAttribute("logs", logsVO);

        if ("1".equals(stockAskChange))
        {
            return mapping.findForward("stockAskChange");
        }

        if ("1".equals(out))
        {
            request.setAttribute("out", 1);
        }

        // 关联的
        List<FinanceBean> financeBeanList = financeDAO.queryRefFinanceItemByStockId(id);

        request.setAttribute("financeBeanList", financeBeanList);
        
        List<StockWorkBean> swList = stockWorkDAO.queryEntityBeansByFK(id);

        request.setAttribute("stockWorkBeanList", swList);

        String addStockArrival = request.getParameter("addStockArrival");
        String updateStockArrival = request.getParameter("updateStockArrival");
        if ("1".equals(addStockArrival)){
            return mapping.findForward("addStockArrival");
        } else if ("1".equals(updateStockArrival)){
            List<StockItemArrivalVO> stockItemArrivalBeans = this.stockItemArrivalDAO.queryEntityVOsByFK(vo.getId());
            vo.setStockItemArrivalVOs(stockItemArrivalBeans);
            return mapping.findForward("updateStockArrival");
        }
        else {
            return mapping.findForward("detailStock");
        }
    }

    public ActionForward backStock(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        String id = request.getParameter("id");

        String ltype = getLType(request);

        int queryType = CommonTools.parseInt(ltype);

        request.setAttribute("ltype", ltype);

        if (StringTools.isNullOrNone(id))
        {
            id = (String)request.getAttribute("stockId");
        }

        String out = request.getParameter("out");

        String update = request.getParameter("update");

        String stockAskChange = request.getParameter("stockAskChange");

        if (StringTools.isNullOrNone(stockAskChange))
        {
            stockAskChange = (String)request.getAttribute("stockAskChange");
        }

        String process = request.getParameter("process");

        if (StringTools.isNullOrNone(process))
        {
            process = (String)request.getAttribute("process");
        }

        StockVO vo = null;

        vo = stockManager.findStockVO(id);

        request.setAttribute("bean", vo);
//        request.setAttribute("divMap", vo.getDivMap());

        prepare(request);

        if ( !StringTools.isNullOrNone(update))
        {
            if ( !"2".equals(update))
            {
                int last = 20 - vo.getItemVO().size();

                // 补齐
                for (int i = 0; i < last; i++ )
                {
                    vo.getItemVO().add(new StockItemVO());
                }

                request.setAttribute("maxItem", 20 - last);
            }

            if ("1".equals(update))
            {
                return mapping.findForward("updateStock");
            }
            else
            {
                // 如果没有询价不能配置
                List<StockItemVO> itemVO = vo.getItemVO();

                //2015/10/24 取消询价
//                for (StockItemVO stockItemVO : itemVO)
//                {
//                    if (stockItemVO.getStatus() == StockConstant.STOCK_ITEM_STATUS_INIT)
//                    {
//                        return ActionTools.toError("还没有全部询价,不能配置税务属性", mapping, request);
//                    }
//                }

                // 过滤管理
                if (OATools.getManagerFlag())
                {
                    List<DutyBean> dutyList = dutyDAO.listEntityBeans();

/*                    if (vo.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
                    {
                        DutyBean defalit = dutyDAO.find(PublicConstant.DEFAULR_DUTY_ID);

                        dutyList.add(defalit);
                    }
                    else
                    {

                        DutyBean manager = dutyDAO.find(PublicConstant.MANAGER_DUTY_ID);

                        dutyList.add(manager);
                    }*/

                    for (Iterator<DutyBean> iterator = dutyList.iterator(); iterator.hasNext();)
                    {
                        DutyBean dbean = iterator.next();

                        // 去掉管理
                        if (dbean.getMtype() == 0)
                        {
                            iterator.remove();

                            continue;
                        }

                        if (dbean.getName().contains("停用"))
                        {
                            iterator.remove();
                        }
                    }

                    if (vo.getMtype() == StockConstant.MANAGER_TYPE_MANAGER)
                    {
                        dutyList.clear();

                        DutyBean manager = dutyDAO.find(PublicConstant.MANAGER_DUTY_ID);

                        dutyList.add(manager);
                    }

                    // 管理默认为 17%
                    /*if (vo.getMtype() == StockConstant.MANAGER_TYPE_COMMON)
                    {
                    	for(StockItemVO eachitem : vo.getItemVO())
                    	{
                    		eachitem.setInvoiceType("90000000000000000001");
                    	}
                    }*/
                    /*else if(vo.getMtype() == StockConstant.MANAGER_TYPE_COMMON3)
                    {
                    	for(StockItemVO eachitem : vo.getItemVO())
                    	{
                    		eachitem.setInvoiceType("90000000000000000002");
                    	}
                    }
                    else // 默认为空
                    {
                    	for(StockItemVO eachitem : vo.getItemVO())
                    	{
                    		eachitem.setInvoiceType("");
                    	}
                    }*/

                    request.setAttribute("dutyList", dutyList);
                }

                return mapping.findForward("updateStockDutyConfig");
            }
        }

        if ("1".equals(process))
        {
            return mapping.findForward("processStock");
        }

        // 询价人拿货
        if ("2".equals(process) || "21".equals(process))
        {
            List<DepotpartBean> depotpartList = new ArrayList<DepotpartBean>();

            //#531 去掉采购中心库
//            if (vo.getMode() == StockConstant.STOCK_MODE_SAIL)
//            {
//                // 查询采购中心的良品仓区
//                depotpartList = depotpartDAO.queryOkDepotpartInDepot(DepotConstant.STOCK_DEPOT_ID);
//            }
            //#107
//            else
//            {
//                // 查询生产库
//                depotpartList = depotpartDAO.queryOkDepotpartInDepot(DepotConstant.MAKE_DEPOT_ID);
//            }

            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addWhereStr();
            // #486 去掉到货预告库_默认仓区
//            conditionParse.addCondition("name","=","在售仓");
//            conditionParse.addCondition(" and name in ('在售仓','到货预告库_默认仓区')");
            //#531 生产作业库-采购仓，生产作业库-深圳百吉仓，生产作业库-东莞铭丰仓，生产作业库-其他生产仓，生产作业库-无锡巨洋仓，生产作业库-南京国声仓
            conditionParse.addCondition(" and name in ('在售仓','采购仓','深圳百吉仓','东莞铭丰仓','其他生产仓','无锡巨洋仓','南京国声仓')");
            List<DepotpartBean> depotpartBeans = this.depotpartDAO.queryEntityBeansByCondition(conditionParse);
            if (!ListTools.isEmptyOrNull(depotpartBeans)){
                depotpartList.addAll(depotpartBeans);
            }
            List<DepotpartVO> voList = new ArrayList<>();
            for (DepotpartBean bean: depotpartList){
                DepotpartVO depotpartVO = new DepotpartVO();
                BeanUtil.copyProperties(depotpartVO, bean);
                DepotpartVO temp = this.depotpartDAO.findVO(bean.getId());
                depotpartVO.setLocationName(temp.getLocationName());
                voList.add(depotpartVO);
            }
            request.setAttribute("depotpartList", voList);
            List<StockItemArrivalVO> stockItemArrivalBeans = this.stockItemArrivalDAO.queryEntityVOsByFK(vo.getId());
            vo.setStockItemArrivalVOs(stockItemArrivalBeans);
            _logger.info("***stockItemArrivalBeans***"+stockItemArrivalBeans.size());
            if ("2".equals(process)) {
                return mapping.findForward("processStock2");
            }
            else {
                return mapping.findForward("processStock21");
            }
        }

        // 获取审批日志
        List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);

        List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

        for (FlowLogBean flowLogBean : logs)
        {
            FlowLogVO flowLogVO = StockHelper.getStockFlowLogVO(flowLogBean);
            if (!StringTools.isNullOrNone(flowLogBean.getReserved1())){
                ProductBean productBean = this.productDAO.find(flowLogBean.getReserved1());
                if(productBean!= null){
                    flowLogVO.setProductName(productBean.getName());
                }
            }

            logsVO.add(flowLogVO);
        }

        // 获得询价的列表
        Map<String, String> map = new HashMap<String, String>();
        Map<String, List<PriceAskProviderBeanVO>> map1 = new HashMap<String, List<PriceAskProviderBeanVO>>();

        for (StockItemVO StockItemVO : vo.getItemVO())
        {
            if (StockItemVO.getStatus() > StockConstant.STOCK_ITEM_STATUS_INIT)
            {
                List<PriceAskProviderBeanVO> items = priceAskProviderDAO
                        .queryEntityVOsByFK(StockItemVO.getId());

                map1.put(StockItemVO.getId(), items);

                User user = Helper.getUser(request);

                map.put(StockItemVO.getId(), PriceAskHelper.createTable(items, user));

                // 业务员和区域经理不能看到供应商
                if (queryType == 0 || queryType == 1)
                {
                    StockItemVO.setProviderName("");
                }
            }
        }

        request.setAttribute("map", map);
        request.setAttribute("map1", map1);

        request.setAttribute("logs", logsVO);

        String beanItemVO = JSONTools.getJSONString(vo.getItemVO());
        request.setAttribute("beanItemVOJson", beanItemVO);

        if ("1".equals(stockAskChange))
        {
            return mapping.findForward("stockAskChange");
        }

        if ("1".equals(out))
        {
            request.setAttribute("out", 1);
        }

        // 关联的
        List<FinanceBean> financeBeanList = financeDAO.queryRefFinanceItemByStockId(id);

        request.setAttribute("financeBeanList", financeBeanList);

        List<StockWorkBean> swList = stockWorkDAO.queryEntityBeansByFK(id);

        request.setAttribute("stockWorkBeanList", swList);

        String addStockArrival = request.getParameter("addStockArrival");
        String updateStockArrival = request.getParameter("updateStockArrival");
        
        //获取已提交的采购退货

        List<Map> baseList = new ArrayList<Map>();
        try{
        	List<Map> tempList = baseDAO.queryBaseByStockId(id);

        	_logger.debug("stockid:"+id+", tempList.size():"+tempList.size());
            //获取相关信息
            for (Map baseItem:tempList){
                String productId = (String)baseItem.get("productId");

                List<StockItemVO> stockItemVOs = vo.getItemVO();

                _logger.debug("productId:"+productId+", stockItemVOs.size():"+stockItemVOs.size());
                for(StockItemVO stockItemVO : stockItemVOs){
                    if(stockItemVO.getProductId().equals(productId)){
                        baseItem.put("provider", stockItemVO.getProviderName());
                        baseItem.put("providerId",stockItemVO.getProviderId());
                        baseItem.put("duty",stockItemVO.getDutyName());
                        baseItem.put("dutyId",stockItemVO.getDutyId());
                        baseItem.put("invoiceType",stockItemVO.getInvoiceTypeName());
                        baseItem.put("invoiceId",stockItemVO.getInvoiceType());
                        break;
                    }
                }
            }
            baseList.addAll(tempList);
        }catch(Exception ex){
            _logger.error(ex);
        }
        
        request.setAttribute("baseList", baseList);
        
        //准备供应商、纳税实体、发票类型列表
        List<Map<String, String>> providerList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> dutyList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> invoiceList = new ArrayList<Map<String, String>>();

        for (StockItemVO itemVO : vo.getItemVO())
        {
            Map<String, String> providerMap = this.getMap(itemVO.getProviderId(), itemVO.getProviderName());
            if(!providerList.contains(providerMap)){
            	_logger.debug("provider "+itemVO.getProviderId()+":"+itemVO.getProviderName());
                providerList.add(providerMap);
            }

            Map<String, String> dutyMap = this.getMap(itemVO.getDutyId(), itemVO.getDutyName());
            if(!dutyList.contains(dutyMap)){
                dutyList.add(dutyMap);
            }

            Map<String, String> invoiceMap = this.getMap(itemVO.getInvoiceType(), itemVO.getInvoiceTypeName());
            if(!invoiceList.contains(invoiceMap)){
                invoiceList.add(invoiceMap);
            }

        }
        request.setAttribute("providerList", providerList);
        request.setAttribute("dutyList", dutyList);
        request.setAttribute("invoiceList", invoiceList);
        
		//运输方式
		List<ExpressBean> expressList = this.expressDAO.listEntityBeans();
		request.setAttribute("expressList", expressList);

		//省市
		List<ProvinceBean> provinceList = this.provinceDAO.listEntityBeans();
		request.setAttribute("provinceList", provinceList);
		List<CityBean> cityList = this.cityDAO.listEntityBeans();
		request.setAttribute("cityList", cityList);
        
        return mapping.findForward("addStockBack");
    }

    private Map<String, String> getMap(String id, String name){
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
        map.put("name", name);
        return map;
    }

    /**
     * 拿货时找到对应的需求确认单
     */
    public ActionForward queryXqqr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                        HttpServletResponse response) throws ServletException{
        String bjNo = request.getParameter("bjNo");
        String productId = request.getParameter("productId");
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addWhereStr();
        conditionParse.addCondition("bjNo","=", bjNo);
        conditionParse.addCondition("pjId","=", productId);
        conditionParse.addCondition(" and supplier=confirmSupplier");
        List<PurchaseBjBean> bjBeans = this.purchaseBjDAO.queryEntityBeansByCondition(conditionParse);
        _logger.info("***bjBeans***"+bjBeans);
        List<PurchaseXqqrBean> result = new ArrayList<PurchaseXqqrBean>();
        for(PurchaseBjBean bjBean: bjBeans){
            ConditionParse conditionParse1 = new ConditionParse();
            conditionParse1.addWhereStr();
            conditionParse1.addCondition("demandQRId","=", bjBean.getDemandQRId());
            conditionParse1.addCondition("pjId","=", bjBean.getPjId());
            List<PurchaseXqqrBean> purchaseXqqrBeans = this.purchaseXqqrDAO.queryEntityBeansByCondition(conditionParse1);
            _logger.info("***xqqrBeans***"+purchaseXqqrBeans);
            if (!ListTools.isEmptyOrNull(purchaseXqqrBeans)){
                result.addAll(purchaseXqqrBeans);
            }
        }

        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setRet(0);
        ajaxResult.setMsg(result);

        return JSONTools.writeResponse(response, ajaxResult);
    }

    /**
     * 增加采购的准备
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddStock(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
    	User user = Helper.getUser(request);
    	
        // 商务模式下权限检查
        ActionForward error = checkAuthForEcommerce(request, user, mapping);
        
        if (null != error)
        {
        	return error;
        }
    	
        prepare(request);

//        // 过滤管理
        if (OATools.getManagerFlag())
        {
            List<DutyBean> dutyList = dutyDAO.listEntityBeans();


            for (Iterator<DutyBean> iterator = dutyList.iterator(); iterator.hasNext();)
            {
                DutyBean dbean = iterator.next();

                // 去掉管理
//                if (dbean.getMtype() == 0)
//                {
//                    iterator.remove();
//
//                    continue;
//                }

                if (dbean.getName().contains("停用"))
                {
                    iterator.remove();
                }
            }

//            if (vo.getMtype() == StockConstant.MANAGER_TYPE_MANAGER)
//            {
//                dutyList.clear();
//
//                DutyBean manager = dutyDAO.find(PublicConstant.MANAGER_DUTY_ID);
//
//                dutyList.add(manager);
//            }


            request.setAttribute("dutyList", dutyList);
        }

        String type = request.getParameter("type");

        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addWhereStr();
        conditionParse.addIntCondition("bjStatus","=", 2);
        conditionParse.addIntCondition("spStatus","=", 1);
        conditionParse.addIntCondition("isUsed","=", 0);
        List<PurchaseBjBean> bjBeans = this.purchaseBjDAO.queryEntityBeansByCondition(conditionParse);

        List<String> bjList = new ArrayList<String>();
        String stafferId = user.getStafferId();
        for (PurchaseBjBean bean : bjBeans){
            ConditionParse conditionParse1 = new ConditionParse();
            conditionParse1.addWhereStr();
            conditionParse1.addCondition("demandQRId","=", bean.getDemandQRId());
            conditionParse1.addCondition("pjid", "=", bean.getPjId());
            conditionParse1.addCondition("demandId", "=", bean.getDemandId());

            List<PurchaseXqqrBean> xqqrBeans = this.purchaseXqqrDAO.queryEntityBeansByCondition(conditionParse1);
            
            if(!bjList.contains(bean.getBjNo())
                // #467 只显示职员的比价单
                && this.belongToStaffer(stafferId, xqqrBeans)){
                bjList.add(bean.getBjNo());
            }
        }
        request.setAttribute("bjList", bjList);
        if ("0".equals(type))
        {
            //销售采购
            return mapping.findForward("addStock");
        }
        else
        {
            //生产采购
            return mapping.findForward("addStock1");
        }
    }

    private void prepare(HttpServletRequest request)
    {
        CommonTools.saveParamers(request);

        List<DepartmentBean> departementList = departmentDAO.listEntityBeans();

        request.setAttribute("departementList", departementList);

        ConditionParse condition = new ConditionParse();

        condition.addIntCondition("forward", "=", InvoiceConstant.INVOICE_FORWARD_IN);

        // 获得所有的发票类型
        List<InvoiceBean> invoiceList = invoiceDAO.queryEntityBeansByCondition(condition);

        request.setAttribute("invoiceList", invoiceList);

        List<DutyBean> dutyList = dutyDAO.listEntityBeans();

        request.setAttribute("dutyList", dutyList);

        // 查询开单品名
        List<ShowBean> showList = showDAO.listEntityBeans();

        JSONArray shows = new JSONArray(showList, true);

        request.setAttribute("showList", showList);

        request.setAttribute("showJSON", shows.toString());
    }

    /**
     * 采购单据询价的准备
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward preForSockAsk(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String itemId = request.getParameter("itemId");

        StockItemVO vo = stockItemDAO.findVO(itemId);

        request.setAttribute("bean", vo);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "采购不存在");

            request.setAttribute("forward", 1);

            return queryStock(mapping, form, request, reponse);
        }

        String stockId = vo.getStockId();

        StockBean stock = stockDAO.find(stockId);

        if (stock == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "采购不存在");

            request.setAttribute("forward", 1);

            return queryStock(mapping, form, request, reponse);
        }

        request.setAttribute("stock", stock);

        ProductBean product = productDAO.find(vo.getProductId());

        if (product == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "产品不存在");

            request.setAttribute("forward", 1);

            return queryStock(mapping, form, request, reponse);
        }

        request.setAttribute("product", product);

        // 规定所有的询价都是一致的
        return mapping.findForward("stockAskPriceForNet");
    }

    /**
     * 处理询价(把界面上询价的结果保存到数据库)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward stockItemAskPrice(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String stockId = request.getParameter("stockId");

        List<PriceAskProviderBean> item = new ArrayList<PriceAskProviderBean>();

        StockItemBean bean = stockItemDAO.find(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "采购不存在");

            request.setAttribute("forward", 1);

            return queryStock(mapping, form, request, reponse);
        }

        setPriceAskProviderBeans(bean, item, request);

        double min = Integer.MAX_VALUE;

        String pid = "";

        for (int i = item.size() - 1; i >= 0; i-- )
        {
            PriceAskProviderBean priceAskProviderBean = item.get(i);

            //if (priceAskProviderBean.getHasAmount() == PriceConstant.HASAMOUNT_OK)
            //{
                if (priceAskProviderBean.getPrice() <= min)
                {
                    min = priceAskProviderBean.getPrice();

                    pid = priceAskProviderBean.getProviderId();
                }
            //}
        }

        if (min == Integer.MAX_VALUE)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "采购询价失败");

            request.setAttribute("forward", 1);

            return queryStock(mapping, form, request, reponse);
        }

        bean.setPrice(min);

        bean.setProviderId(pid);

        try
        {
            stockManager.stockItemAsk(bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功处理采购询价");
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理采购询价失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("process", "1");

        request.setAttribute("stockId", stockId);

        return findStock(mapping, form, request, reponse);
    }

    /**
     * 处理询价(外网询价员替供应商选择产品入库重新生成stockItem)<br>
     * 这里不存在最低价,只有单价超过数额的
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward stockItemAskPriceForNet(ActionMapping mapping, ActionForm form,
                                                 HttpServletRequest request,
                                                 HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        String stockId = request.getParameter("stockId");

        StockItemBean bean = stockItemDAO.find(id);

        if (bean == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "采购不存在");

            request.setAttribute("forward", 1);

            return queryStock(mapping, form, request, reponse);
        }

        List<StockItemBean> newItemList = new ArrayList();

        setNewStockItemList(request, bean, newItemList);

        try
        {
            stockManager.stockItemAskForNet(bean, newItemList);

            request.setAttribute(KeyConstant.MESSAGE, "成功处理采购询价");
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理采购询价失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        request.setAttribute("process", "1");

        request.setAttribute("stockId", stockId);

        return findStock(mapping, form, request, reponse);
    }

    /**
     * CORE 采购确认价格的核心
     * 
     * @param request
     * @param bean
     * @param newItemList
     */
    private void setNewStockItemList(HttpServletRequest request, StockItemBean bean,
                                     List<StockItemBean> newItemList)
    {
        String[] providers = request.getParameterValues("check_init");

        for (int i = 0; i < providers.length; i++ )
        {
            if ( !StringTools.isNullOrNone(providers[i]))
            {
                StockItemBean newBean = new StockItemBean();

                BeanUtil.copyProperties(newBean, bean);

                newBean.setAmount(CommonTools.parseInt(request.getParameter("amount_"
                                                                            + providers[i])));

                newBean.setPrice(Float.parseFloat(request.getParameter("price_" + providers[i])));

                newBean.setLogTime(TimeTools.now());

                newBean.setProviderId(request.getParameter("customerId_" + providers[i]));

                newBean.setPriceAskProviderId(request.getParameter("netaskId_" + providers[i]));

                newBean.setStafferId(request.getParameter("stafferId_" + providers[i]));

                newBean.setDescription(request.getParameter("description_" + providers[i]));

                newBean.setNearlyPayDate(request.getParameter("nearlyPayDate_" + providers[i]));

                newItemList.add(newBean);
            }
        }
    }

    /**
     * 收集数据
     * 
     * @param pbean
     * @param item
     * @param request
     */
    private void setPriceAskProviderBeans(StockItemBean pbean, List<PriceAskProviderBean> item,
                                          HttpServletRequest request)
    {
        String[] providers = request.getParameterValues("check_init");

        for (int i = 0; i < providers.length; i++ )
        {
            if ( !StringTools.isNullOrNone(providers[i]))
            {
                PriceAskProviderBean bean = new PriceAskProviderBean();

                // 询价ID
                bean.setAskId(pbean.getId());

                bean.setLogTime(TimeTools.now());

                bean.setProductId(pbean.getProductId());

                bean.setPrice(Float.parseFloat(request.getParameter("price_" + providers[i])));

                bean.setProviderId(request.getParameter("customerId_" + providers[i]));
                
                bean.setUnitPrice(Float.parseFloat(request.getParameter("unitPrice_" + providers[i])));
                bean.setGoldPrice(Float.parseFloat(request.getParameter("goldPrice_" + providers[i])));
                bean.setSilverPrice(Float.parseFloat(request.getParameter("silverPrice" + providers[i])));
                bean.setAmount(Float.parseFloat(request.getParameter("amount_" + providers[i])));
                bean.setHandleFee(Float.parseFloat(request.getParameter("handleFee_" + providers[i])));
                bean.setIsWrapper(MathTools.parseInt(request.getParameter("isWrapper_" + providers[i])));
                bean.setFlow(request.getParameter("flow_" + providers[i]));
                bean.setGap(MathTools.parseInt(request.getParameter("gap_" + providers[i])));
                
                bean.setProvideConfirmDate(request.getParameter("provideConfirmDate_" + providers[i]));
                bean.setConfirmSendDate(request.getParameter("confirmSendDate_" + providers[i]));

//                bean.setHasAmount(CommonTools.parseInt(request.getParameter("hasAmount_"
//                                                                            + providers[i])));

                bean.setUserId(Helper.getUser(request).getId());

                item.add(bean);
            }
        }

        pbean.setAsks(item);
    }

    /**
     * checkQueryOutAuth
     * 
     * @param user
     * @param queryType
     * @throws MYException
     */
    private void checkQueryAuth(User user, String queryType)
        throws MYException
    {
    	if ("99".equals(queryType))
    		return;
    			
        if ("0".equals(queryType)
            && !userManager.containAuth(user, AuthConstant.STOCK_ADD, AuthConstant.STOCK_MAKE_ADD))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("1".equals(queryType)
            && !userManager.containAuth(user.getId(), AuthConstant.STOCK_MANAGER_PASS))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("2".equals(queryType)
            && !userManager.containAuth(user, AuthConstant.STOCK_NET_STOCK_PASS,
                AuthConstant.STOCK_MAKE_PASS))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("3".equals(queryType)
            && !userManager.containAuth(user.getId(), AuthConstant.STOCK_PRICE_PASS))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("4".equals(queryType)
            && !userManager.containAuth(user.getId(), AuthConstant.STOCK_INNER_STOCK_PASS))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("5".equals(queryType)
            && !userManager.containAuth(user.getId(), AuthConstant.STOCK_STOCK_MANAGER_PASS))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("6".equals(queryType)
            && !userManager.containAuth(user.getId(), AuthConstant.STOCK_NOTICE_CHAIRMA))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("7".equals(queryType)
            && !userManager.containAuth(user.getId(), AuthConstant.STOCK_NOTICE_CEO))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("8".equals(queryType)
            && !userManager.containAuth(user, AuthConstant.PRICE_ASK_PROCESS,
                AuthConstant.PRICE_ASK_MANAGER, AuthConstant.PRICE_ASK_NET_INNER_PROCESS,
                AuthConstant.PRICE_ASK_MAKE))
        {
            throw new MYException("用户没有此操作的权限");
        }
        
        if ("10".equals(queryType)
                && !userManager.containAuth(user.getId(), AuthConstant.STOCK_NOTICE_CHAIRMA))
        {
            throw new MYException("用户没有此操作的权限");
        }

        int type = CommonTools.parseInt(queryType);

        if (type < 0 || type > 10)
        {
            throw new MYException("用户没有此操作的权限");
        }
    }

    private void checkQueryTypeAuth(User user, String type)
        throws MYException
    {
        if ("0".equals(type) && !userManager.containAuth(user, AuthConstant.STOCK_NET_STOCK_PASS))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("1".equals(type)
            && !userManager.containAuth(user.getId(), AuthConstant.STOCK_MAKE_PASS))
        {
            throw new MYException("用户没有此操作的权限");
        }
    }

    /**
     * checkAddTypeAuth
     * 
     * @param user
     * @param type
     * @throws MYException
     */
    private void checkAddTypeAuth(User user, String type)
        throws MYException
    {
        if ("0".equals(type) && !userManager.containAuth(user, AuthConstant.STOCK_ADD))
        {
            throw new MYException("用户没有此操作的权限");
        }

        if ("1".equals(type) && !userManager.containAuth(user.getId(), AuthConstant.STOCK_MAKE_ADD))
        {
            throw new MYException("用户没有此操作的权限");
        }
    }

    /**
     * 查询采购单(自己的)
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryStock(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        System.out.println("1111111111111111111111111111111");
        CommonTools.saveParamers(request);

        ConditionParse condtion = new ConditionParse();

        User user = Helper.getUser(request);

        String ltype = getLType(request);

        String type = getL2Type(request);
        
        String extraStatus = getExtraStatus(request);
        System.out.println("22222222222222222222222222222222");
        
        //add by zhangxian 2019-06-17
        //add staffer query parameter
        String stafferName = request.getParameter("stafferName");
        if(!StringUtils.isEmpty(stafferName))
        {
        	StafferBean sb = stafferDAO.findyStafferByName(StringUtils.trim(stafferName));
        	if(sb != null)
        	{
        		condtion.addCondition("stafferid", "=", sb.getId());
        	}
        }
        
        // 鉴权
        try
        {
            checkQueryAuth(user, ltype);

            // 生产和销售采购的区别
            checkQueryTypeAuth(user, type);
        }
        catch (MYException e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.getErrorContent());

            _logger.error(e, e);

            return mapping.findForward("error");
        }
        System.out.println("333333333333333333");
        List<StockVO> list = null;
        
        List<StockVO> viewList = new ArrayList<StockVO>();
        String roleid = user.getRoleId();
        List<RoleAuthBean> authList = roleAuthDAO.queryEntityBeansByFK(roleid);
        int flag = 0;
        if(authList != null && authList.size() > 0 )
        {
        	for(int i = 0 ; i < authList.size(); i++ )
        	{
        		RoleAuthBean rab = authList.get(i);
        		if(rab.getAuthId().trim().equals("131801"))
        		{
        			flag = flag + 131801;//只查看终端类单据
        			
        		}
        		if(rab.getAuthId().trim().equals("131802"))
        		{
        			flag = flag + 131802;//只查看非终端类单据
        		}
        		
        	}
        }
        String template = "*** queryStock with ltype:%s flag:%d *****";
        System.out.println(String.format(template,ltype,flag));
        try
        {
            if (OldPageSeparateTools.isFirstLoad(request))
            {
                setCondition(request, condtion, CommonTools.parseInt(ltype), extraStatus);
                
                int total = stockDAO.countVOByCondition(condtion.toString());

                PageSeparate page = new PageSeparate(total, PublicConstant.PAGE_SIZE);

                OldPageSeparateTools.initPageSeparate(condtion, page, request, "queryStock");

                list = stockDAO.queryEntityVOsByCondition(condtion, page);
            }
            else
            {
                OldPageSeparateTools.processSeparate(request, "queryStock");

                list = stockDAO.queryEntityVOsByCondition(OldPageSeparateTools.getCondition(
                    request, "queryStock"), OldPageSeparateTools.getPageSeparate(request,
                    "queryStock"));
            }
            System.out.println("555555555555555555555555555");
            // 页面显示div用
            Map<String, String> div = new HashMap<String, String>();

            for (StockVO StockVO : list)
            {
            	StafferBean sb = stafferDAO.find(StockVO.getStafferId());
            	String indusId = sb.getIndustryId();
            	if(indusId.equals("5111658") && flag == 131801)//只展示终端事业部
            	{
            		viewList.add(StockVO);
            	}
            	if((!indusId.equals("5111658")) && flag == 131802)//只展示非终端事业部
            	{
            		viewList.add(StockVO);
            	}
            	
                setStockDisplay(user, StockVO, CommonTools.parseInt(ltype));

                if (StringTools.compare(StockVO.getNeedTime(), TimeTools.now_short()) < 0)
                {
                    StockVO.setOverTime(StockConstant.STOCK_OVERTIME_YES);
                }

                List<StockItemVO> itemVO = stockItemDAO.queryEntityVOsByFK(StockVO.getId());

                div.put(StockVO.getId(), StockHelper.createTable(itemVO, CommonTools
                    .parseInt(ltype)));
            }
            System.out.println("666666666666666666666666666666");
            List<LocationBean> locations = locationDAO.listEntityBeans();
            
            request.setAttribute("locations", locations);

            request.setAttribute("map", div);
            if(flag == 131801 || flag == 131802)
            {
            	request.setAttribute("list", viewList);
            }
            else
            {
            	request.setAttribute("list", list);
            }
            
        }
        catch (Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询采购单价格失败:" + e.getMessage());

            _logger.error(e, e);

            return mapping.findForward("error");
        }

        return mapping.findForward("queryStock");
    }

    /**
     * 获取查询类型
     * 
     * @param request
     * @return
     */
    private String getLType(HttpServletRequest request)
    {
        String ltype = request.getParameter("ltype");

        if (StringTools.isNullOrNone(ltype))
        {
            Object attribute = request.getSession().getAttribute("g_ltype");

            if (attribute != null)
            {
                ltype = attribute.toString();
            }

            request.setAttribute("ltype", ltype);
        }
        else
        {
            // 放到session里面
            request.getSession().setAttribute("g_ltype", ltype);
        }
        
        //
        String extraStatus = request.getParameter("extraStatus");

        if (StringTools.isNullOrNone(extraStatus))
        {
            Object attribute = request.getSession().getAttribute("g_extraStatus");

            if (attribute != null)
            {
            	extraStatus = attribute.toString();
            }

            request.setAttribute("extraStatus", extraStatus);
        }
        else
        {
            // 放到session里面
            request.getSession().setAttribute("g_extraStatus", extraStatus);
        }

        return ltype;
    }

    private String getL2Type(HttpServletRequest request)
    {
        String ltype = request.getParameter("type");

        return ltype;
    }

    /**
     * 获取查询类型
     * 
     * @param request
     * @return
     */
    private String getExtraStatus(HttpServletRequest request)
    {
        String extraStatus = RequestTools.getValueFromRequest(request, "extraStatus");
        
        request.setAttribute("extraStatus", extraStatus);

        return extraStatus;
    }
    
    /**
     * setStockDisplay
     * 
     * @param user
     * @param stockBeanVO
     */
    private void setStockDisplay(User user, StockVO stockBeanVO, int type)
    {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();

        map.put(StockConstant.STOCK_STATUS_END, null);

        map.put(StockConstant.STOCK_STATUS_INIT, 0);

        map.put(StockConstant.STOCK_STATUS_REJECT, 0);

        map.put(StockConstant.STOCK_STATUS_SUBMIT, 1);

        map.put(StockConstant.STOCK_STATUS_MANAGERPASS, 3);

        map.put(StockConstant.STOCK_STATUS_PRICEPASS, 4);

        map.put(StockConstant.STOCK_STATUS_STOCKPASS, 5);

        map.put(StockConstant.STOCK_STATUS_STOCKPASS, 6);

        map.put(StockConstant.STOCK_STATUS_END, 7);

        map.put(StockConstant.STOCK_STATUS_STOCKMANAGERPASS, 8);
        
        map.put(StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO, 9);
        
        map.put(StockConstant.STOCK_STATUS_STOCKPASS_CEO, 10);

        if (map.get(stockBeanVO.getStatus()) != null && map.get(stockBeanVO.getStatus()) == type)
        {
            stockBeanVO.setDisplay(StockConstant.DISPLAY_YES);
        }
        else
        {
            stockBeanVO.setDisplay(StockConstant.DISPLAY_NO);
        }

        if (type == 2)
        {
            if (stockBeanVO.getStatus() == StockConstant.STOCK_STATUS_PRICEPASS)
            {
                stockBeanVO.setDisplay(StockConstant.DISPLAY_YES);
            }
        }

        if (type == 5)
        {
            if (stockBeanVO.getStatus() == StockConstant.STOCK_STATUS_STOCKPASS)
            {
                stockBeanVO.setDisplay(StockConstant.DISPLAY_YES);
            }
        }
        
        if (type == 9)
        {
            if (stockBeanVO.getStatus() == StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO)
            {
                stockBeanVO.setDisplay(StockConstant.DISPLAY_YES);
            }
        }
        
        if (type == 10)
        {
            if (stockBeanVO.getStatus() == StockConstant.STOCK_STATUS_STOCKPASS_CEO)
            {
                stockBeanVO.setDisplay(StockConstant.DISPLAY_YES);
            }
        }
    }

    /**
     * 处理采购的分页
     * 
     * @param request
     * @param condtion
     * @throws MYException
     */
    private void setCondition(HttpServletRequest request, ConditionParse condtion, int type, String extraStatus)
        throws MYException
    {
        condtion.addWhereStr();

        User user = Helper.getUser(request);

        // 只能看到通过的 COMMON
        if (type == 0)
        {
            condtion.addCondition("StockBean.stafferId", "=", user.getStafferId());
        }

        // 事业部经理
        if (type == 1)
        {
            StafferBean sb = stafferDAO.find(user.getStafferId());

            if (sb == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            condtion.addCondition("StockBean.industryId", "=", sb.getIndustryId());
        }

        // 采购主管(区分不同类型的)
        if (type == 2)
        {
            String typeStr = request.getParameter("type");

            condtion.addIntCondition("StockBean.mode", "=", typeStr);

            request.setAttribute("mode", typeStr);
        }

        // 暂时没有
        if (type == 3)
        {
            // 采购里面只有内外询价或者外网询价，其他的没有
            condtion.addIntCondition("StockBean.type", "=", PriceConstant.PRICE_ASK_TYPE_INNER);
        }

        // STOCK(内网采购主管)暂时没有
        if (type == 4)
        {
            condtion.addIntCondition("StockBean.type", "=", PriceConstant.PRICE_ASK_TYPE_INNER);
        }

        // 董事长(只能审核价格不是最小的)
//        if (type == 5)
//        {
//            condtion.addIntCondition("StockBean.exceptStatus", "=",
//                StockConstant.EXCEPTSTATUS_EXCEPTION_MIN);
//        }

        // 董事长(只能审核钱过多的)
//        if (type == 6)
//        {
//            condtion.addIntCondition("StockBean.exceptStatus", "=",
//                StockConstant.EXCEPTSTATUS_EXCEPTION_MONEY);
//        }

        String status = request.getParameter("status");

        // 查询固定状态的
        if ( !StringTools.isNullOrNone(status))
        {
            condtion.addIntCondition("StockBean.status", "=", status);
        }
        else
        {
            if (type == 2)
            {
                if (isMenuLoad(request))
                {
                    condtion.addIntCondition("StockBean.status", "=",
                        StockConstant.STOCK_STATUS_PRICEPASS);

                    request.setAttribute("status", StockConstant.STOCK_STATUS_PRICEPASS);
                }
            }
            else
            {
                // 设置不同角色的默认状态
                Map<Integer, Integer> map = new HashMap<Integer, Integer>();

                // manager
                map.put(1, StockConstant.STOCK_STATUS_SUBMIT);

                // 采购主管审核
                map.put(2, StockConstant.STOCK_STATUS_PRICEPASS);

                // PRICE
                map.put(3, StockConstant.STOCK_STATUS_MANAGERPASS);

                // STOCK
                map.put(4, StockConstant.STOCK_STATUS_PRICEPASS);

                // STOCKMANAGER
                map.put(5, StockConstant.STOCK_STATUS_STOCKPASS);

                // 董事长
                map.put(6, StockConstant.STOCK_STATUS_STOCKPASS);

                // STOCK(结束采购)
                map.put(7, StockConstant.STOCK_STATUS_END);

                // 采购拿货
                map.put(8, StockConstant.STOCK_STATUS_STOCKMANAGERPASS);
                
                map.put(9, StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO);
                
                map.put(10, StockConstant.STOCK_STATUS_STOCKPASS_CEO);

                for (Map.Entry<Integer, Integer> entry : map.entrySet())
                {
                    if (type == entry.getKey())
                    {
                        condtion.addIntCondition("StockBean.status", "=", entry.getValue());

                        request.setAttribute("status", entry.getValue());

                        break;
                    }
                }
            }
        }

        String locationId = request.getParameter("locationId");

        if ( !StringTools.isNullOrNone(locationId))
        {
            condtion.addCondition("StockBean.locationId", "=", locationId);
        }

        String over = request.getParameter("over");

        if ( !StringTools.isNullOrNone(over))
        {
            if ("0".equals(over))
            {
                condtion.addCondition("StockBean.needTime", ">=", TimeTools.now_short());
            }

            if ("1".equals(over))
            {
                condtion.addCondition("StockBean.needTime", "<", TimeTools.now_short());
            }
        }

        String pay = request.getParameter("pay");

        if ( !StringTools.isNullOrNone(pay))
        {
            condtion.addIntCondition("StockBean.pay", "=", pay);
        }

        String stockType = request.getParameter("stype");

        if ( !StringTools.isNullOrNone(stockType))
        {
            condtion.addIntCondition("StockBean.stype", "=", stockType);
        }

        String mode = request.getParameter("mode");

        if ( !StringTools.isNullOrNone(mode))
        {
            condtion.addIntCondition("StockBean.mode", "=", mode);
        }

        String id = request.getParameter("ids");

        if ( !StringTools.isNullOrNone(id))
        {
            condtion.addCondition("StockBean.id", "like", id);
        }

        String alogTime = request.getParameter("alogTime");

        if ( !StringTools.isNullOrNone(alogTime))
        {
            condtion.addCondition("StockBean.logTime", ">=", alogTime + " 00:00:00");
        }
        else
        {
            condtion.addCondition("StockBean.logTime", ">=", TimeTools.getDateShortString( -30)
                                                             + " 00:00:00");

            request.setAttribute("alogTime", TimeTools.getDateShortString( -30));
        }

        String blogTime = request.getParameter("blogTime");

        if ( !StringTools.isNullOrNone(blogTime))
        {
            condtion.addCondition("StockBean.logTime", "<=", blogTime + " 23:59:59");
        }
        else
        {
            condtion.addCondition("StockBean.logTime", "<=", TimeTools.getDateShortString(0)
                                                             + " 23:59:59");

            request.setAttribute("blogTime", TimeTools.getDateShortString(0));
        }

        if (!StringTools.isNullOrNone(extraStatus))
        {
        	if ("0".equals(extraStatus))
        		condtion.addIntCondition("StockBean.extraStatus", "=", 0);
        }

        String productName = request.getParameter("productName");
        if (!StringTools.isNullOrNone(productName)){
             condtion.addCondition(" and exists (select StockItemBean.id from t_center_stockitem StockItemBean " +
                     "left outer join t_center_product ProductBean on ProductBean.id=StockItemBean.productId " +
                     "where StockItemBean.stockId=StockBean.id and ProductBean.name like '%"+productName+"%')");
        }

        String providerName = request.getParameter("providerName");
        if (!StringTools.isNullOrNone(providerName)){
            condtion.addCondition(" and exists (select StockItemBean.id from t_center_stockitem StockItemBean " +
                    "left outer join T_CENTER_PROVIDE ProviderBean on ProviderBean.id=StockItemBean.providerId " +
                    "where StockItemBean.stockId=StockBean.id and ProviderBean.name like '%"+providerName+"%')");
        }
        
        condtion.addCondition("order by StockBean.logTime desc");
    }

    private boolean isMenuLoad(HttpServletRequest request)
    {
        // f_menu
        String menu = request.getParameter("menu");

        if ( !StringTools.isNullOrNone(menu))
        {
            return true;
        }

        Object o1 = request.getAttribute("menu");

        if (o1 != null)
        {
            return true;
        }

        return false;
    }

    /**
     * 删除采购单价格
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward delStock(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        String id = request.getParameter("id");

        User user = Helper.getUser(request);

        try
        {
            stockManager.delStockBean(user, id);

            request.setAttribute(KeyConstant.MESSAGE, "成功删除采购单价格");
        }
        catch (MYException e)
        {
            _logger.warn(e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "删除采购单失败:" + e.getMessage());
        }

        request.setAttribute("forward", 1);

        return queryStock(mapping, form, request, reponse);
    }
    
	/**
	 * 处理多个采购退货
	 * @param mapping
	 * @param form
	 * @param request
	 * @param reponse
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addOuts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException
	{
		
		String saves = request.getParameter("saves");

		String step = request.getParameter("step");

		String hasProm = request.getParameter("hasProm");

		String oprType = request.getParameter("oprType");

		if (StringTools.isNullOrNone(oprType)) oprType = "";
		

		// 客户信用级别
		String customercreditlevel = request
				.getParameter("customercreditlevel");

		String fullId = request.getParameter("fullId");
		
		//String fullId = request.getParameter("stockId");

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
		
		//处理参数
		
		
		final String[] prices = request.getParameterValues("price");
		final String[] depotpartIds = request.getParameterValues("depotpartId");
		final String[] depotIds = request.getParameterValues("depotId");
		final String[] providerIds = request.getParameterValues("providerId");
		//final String[] providerNames = request.getParameterValues("providerName");
		final String[] dutyIds = request.getParameterValues("dutyId");
		final String[] invoiceIds = request.getParameterValues("invoiceId");
		
		//location, locationID 比较乱，要对照关联关系看
		//对应location
		final String[] locationIds = request.getParameterValues("locationId");
		//对应depot
		String[] locations = request.getParameterValues("location");

		final String[] nameList = request.getParameterValues("productName");

        final String[] idsList = request.getParameterValues("productId");
        final String[] amontList = request.getParameterValues("amount");
        
        final String[] desList = request.getParameterValues("description");
        
        final String[] backTypes = request.getParameterValues("backType");
        
        final String[] virtualPriceList = request.getParameterValues("virtualPrice");
        final String[] virtualPriceKeyList = request.getParameterValues("virtualPriceKey");
  
        
        //debug
        
        for(String name: nameList){
        	_logger.debug("parent addOuts, name: "+name);
        }
        
		
		User user = (User) request.getSession().getAttribute("user");
		
		//String locationId = Helper.getCurrentLocationId(request);

		//String location = request.getParameter("location");
		
		for(int i=0; i<nameList.length; i++){
			
			if(StringTools.isNullOrNone(nameList[i])){
				continue;
			}
			
			OutBean outBean = new OutBean();

            BeanUtil.getBean(outBean, request);

			//outBean.setLocationId(locationIds[i]);
			
			outBean.setLocationId("999");
			
			outBean.setLocation(locations[i]);

			String stockId = request.getParameter("stockId");
			outBean.setRefOutFullId(stockId);
			
			outBean.setDepotpartId(depotpartIds[i]);
			
			outBean.setDutyId(dutyIds[i]);
			outBean.setInvoiceId(invoiceIds[i]);

			outBean.setStafferId(user.getStafferId());
			outBean.setStafferName(user.getStafferName());
			
			outBean.setOutType(OutConstant.OUTTYPE_IN_STOCK);
			outBean.setType(OutConstant.OUT_TYPE_INBILL);

			outBean.setCustomerId(providerIds[i]);
			
			String customerId = providerIds[i];
			_logger.debug("customerId:"+customerId);
			if(!StringTools.isNullOrNone(customerId)){
				
                ConditionParse conditionParse = new ConditionParse();
                conditionParse.addWhereStr();
                conditionParse.addCondition("id", "=", customerId);
                List<ProviderBean> providerBeans = this.providerDAO.queryEntityBeansByCondition(conditionParse);		

                _logger.debug("providerBeans.size():"+providerBeans.size());
                if(providerBeans.size()>0) {
                	ProviderBean providerBean = providerBeans.get(0);
                    outBean.setCustomerName(providerBean.getName());
                }
            }
			
			outBean.setBuyReturnFlag(1);

			outBean.setLogTime(TimeTools.now());

			outBean.setOutTime(TimeTools.now_short());
			
			outBean.setDescription(desList[i]);
			
			int backType = 1;
			if(!StringTools.isNullOrNone(backTypes[i])){
				backType = Integer.parseInt(backTypes[i]);
			}
			outBean.setBuyReturnType(backType);
			
			/*

			if (StringTools.isNullOrNone(outBean.getLocation()))
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "没有库存属性,请重新操作");

				return mapping.findForward("error");
			}
			*/

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
            
            //构造参数
            ParamterMap map = new ParamterMap(request);

            map.getParameterMap().put("nameList", nameList[i]);
            map.getParameterMap().put("idsList", idsList[i]);
            map.getParameterMap().put("amontList", amontList[i]);
            map.getParameterMap().put("priceList", prices[i]);
            map.getParameterMap().put("desList", desList[i]);
            
            map.getParameterMap().put("virtualPriceList", virtualPriceList[i]);

            //ele.productid + '-' + ele.price + '-' + ele.stafferid + '-' + ele.depotpartid
            if(StringTools.isNullOrNone(depotpartIds[i])){
            	depotpartIds[i] = "999";//特殊字符串，代表depotpartIds 空缺
            }

            outBean.setDepotpartId(depotpartIds[i]);

            map.getParameterMap().put("otherList", idsList[i]+"-"+prices[i]+"-"+outBean.getStafferId()+"-"+depotpartIds[i]);
            map.getParameterMap().put("depotList", depotpartIds[i]);
            
			// 入库单的处理
			try
			{
				//发货信息
				if(backType == 1){
					this.fillDistributionForRemoteAllocate(request, outBean);
				}
				
				
				String id = outManager.addOut(outBean, map.getParameterMap(), user);
                _logger.info("addOut 88888888888888888888*********"+id);
				if ("提交".equals(saves))
				{
					int ttype = StorageConstant.OPR_STORAGE_INOTHER;
					
                    _logger.info("addOut aaaaaaaaaaaaaaaa*********");
					outManager.submit(id, user, ttype);
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
		
        CommonTools.removeParamers(request);

        request.setAttribute("ltype", "0");
        request.setAttribute("load", "1");
        request.setAttribute("menu", "1");
		
		request.setAttribute(KeyConstant.MESSAGE, "成功提交采购退货单!");
		
		return queryStock(mapping, form, request, reponse);
	}   
	
    private void fillDistributionForRemoteAllocate(HttpServletRequest rds, OutBean out)
    {
        DistributionBean distributionBean = new DistributionBean();

        distributionBean.setOutId(out.getFullId());

        out.setDistributeBean(distributionBean);

        BeanUtil.getBean(distributionBean, rds);
        _logger.info(out+" fillDistributionForRemoteAllocate*****"+distributionBean);

    }


    /**
     * 导出查询中的stock
     * 
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward exportStock(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse reponse)
        throws ServletException
    {
        OutputStream out = null;

        PageSeparate oldPage = OldPageSeparateTools.getPageSeparate(request, "queryStock");

        final ConditionParse condition = OldPageSeparateTools.getCondition(request, "queryStock");

        PageSeparate page = new PageSeparate(oldPage);

        if (page.getRowCount() > 1000)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导出的记录数不能超过1000");

            return mapping.findForward("error");
        }

        page.reset(page.getRowCount(), PublicConstant.PAGE_EXPORT);

        String filenName = "Stock_" + TimeTools.now("MMddHHmmss") + "_ALL.xls";

        reponse.setContentType("application/x-dbf");

        reponse.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WritableWorkbook wwb = null;

        WritableSheet ws = null;

        try
        {
            out = reponse.getOutputStream();

            wwb = Workbook.createWorkbook(out);
            ws = wwb.createSheet(TimeTools.now("yyyyMMdd") + "_" + page.getRowCount(), 0);
            int i = 0, j = 0;

            WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
                jxl.format.UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);

            WritableCellFormat format = new WritableCellFormat(font);

            ws.addCell(new Label(j++ , i, "时间", format));
            ws.addCell(new Label(j++ , i, "采购单号", format));
            ws.addCell(new Label(j++ , i, "入库单号", format));
            ws.addCell(new Label(j++ , i, "状态", format));
            ws.addCell(new Label(j++ , i, "采购人", format));
            ws.addCell(new Label(j++ , i, "采购区域", format));
            ws.addCell(new Label(j++ , i, "采购总金额", format));

            // 子项
            ws.addCell(new Label(j++ , i, "产品名称", format));
            ws.addCell(new Label(j++ , i, "产品编码", format));
            ws.addCell(new Label(j++ , i, "采购数量", format));
            ws.addCell(new Label(j++ , i, "参考价格", format));
            ws.addCell(new Label(j++ , i, "采购价格", format));
            ws.addCell(new Label(j++ , i, "供应商", format));
            ws.addCell(new Label(j++ , i, "纳税实体", format));
            ws.addCell(new Label(j++ , i, "发票", format));
            ws.addCell(new Label(j++ , i, "开票品名", format));
            ws.addCell(new Label(j++ , i, "本品合计", format));

            boolean fr = true;

            while (fr || page.nextPage())
            {
                List<StockVO> list = stockDAO.queryEntityVOsByCondition(condition, page);

                for (StockVO item : list)
                {
                    List<StockItemVO> itemVOs = stockItemDAO.queryEntityVOsByFK(item.getId());

                    for (StockItemVO vo : itemVOs)
                    {
                        j = 0;

                        i++ ;

                        ws.addCell(new Label(j++ , i, item.getLogTime()));
                        ws.addCell(new Label(j++ , i, item.getId()));
                        ws.addCell(new Label(j++ , i, vo.getRefOutId()));
                        ws
                            .addCell(new Label(j++ , i, ElTools
                                .get("stockStatus", item.getStatus())));
                        ws.addCell(new Label(j++ , i, item.getUserName()));
                        ws.addCell(new Label(j++ , i, item.getLocationName()));
                        ws.addCell(new Label(j++ , i, String.valueOf(MathTools.formatNum(item
                            .getTotal()))));

                        // 子项
                        ws.addCell(new Label(j++ , i, vo.getProductName()));
                        ws.addCell(new Label(j++ , i, vo.getProductCode()));
                        ws.addCell(new Label(j++ , i, String.valueOf(vo.getAmount())));
                        ws.addCell(new Label(j++ , i, String.valueOf(MathTools.formatNum(vo
                            .getPrePrice()))));
                        ws.addCell(new Label(j++ , i, String.valueOf(MathTools.formatNum(vo
                            .getPrice()))));

                        ws.addCell(new Label(j++ , i, vo.getProviderName()));

                        ws.addCell(new Label(j++ , i, vo.getDutyName()));

                        String invoiceTypeName = "没有发票";

                        if ( !StringTools.isNullOrNone(vo.getInvoiceType()))
                        {
                            InvoiceBean iBean = invoiceDAO.find(vo.getInvoiceType());

                            if (iBean != null)
                            {
                                invoiceTypeName = iBean.getFullName();
                            }
                        }

                        ws.addCell(new Label(j++ , i, invoiceTypeName));

                        String showName = "";

                        if ( !StringTools.isNullOrNone(vo.getShowId()))
                        {
                            ShowBean show = showDAO.find(vo.getShowId());

                            if (show != null)
                            {
                                showName = show.getName();
                            }
                        }

                        ws.addCell(new Label(j++ , i, showName));

                        ws.addCell(new Label(j++ , i, String.valueOf(MathTools.formatNum(vo
                            .getTotal()))));
                    }

                }

                fr = false;
            }

            wwb.write();

        }
        catch (Exception e)
        {
            return null;
        }
        finally
        {
            if (wwb != null)
            {
                try
                {
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
     * confirmProduct
     * 拿货前预确认
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward confirmProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
    	String itemId = request.getParameter("itemId");

    	try
    	{
        	User user = Helper.getUser(request);

        	stockManager.confirmProduct(user, itemId);

        	request.setAttribute(KeyConstant.MESSAGE, "成功确认");
    	}
    	catch (MYException e)
    	{
        	_logger.warn(e, e);

        	request.setAttribute(KeyConstant.ERROR_MESSAGE, "确认失败:" + e.getMessage());
    	}

    	CommonTools.removeParamers(request);

    	request.setAttribute("forward", "1");
    	
    	request.setAttribute("extraStatus", 0);
    	
    	return queryStock(mapping, form, request, reponse);
	}
    
    /**
     * endProduct
     * 异常结束
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward endProduct(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse reponse)
	throws ServletException
	{
    	String id = request.getParameter("id");
    	
    	String reason = request.getParameter("reason");

    	try
    	{
        	User user = Helper.getUser(request);

        	stockManager.endProduct(user, id, reason);

        	request.setAttribute(KeyConstant.MESSAGE, "成功确认");
    	}
    	catch (MYException e)
    	{
        	_logger.warn(e, e);

        	request.setAttribute(KeyConstant.ERROR_MESSAGE, "确认失败:" + e.getMessage());
    	}

    	CommonTools.removeParamers(request);

    	request.setAttribute("forward", "1");
    	
    	return queryStock(mapping, form, request, reponse);
	}

    private ActionForward checkAuthForEcommerce(HttpServletRequest request, User user, ActionMapping mapping)
    {
    	/*
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
        */
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
     * @return the priceAskManager
     */
    public PriceAskManager getPriceAskManager()
    {
        return priceAskManager;
    }

    /**
     * @param priceAskManager
     *            the priceAskManager to set
     */
    public void setPriceAskManager(PriceAskManager priceAskManager)
    {
        this.priceAskManager = priceAskManager;
    }

    /**
     * @return the stockManager
     */
    public StockManager getStockManager()
    {
        return stockManager;
    }

    /**
     * @param stockManager
     *            the stockManager to set
     */
    public void setStockManager(StockManager stockManager)
    {
        this.stockManager = stockManager;
    }

    /**
     * @return the stockItemDAO
     */
    public StockItemDAO getStockItemDAO()
    {
        return stockItemDAO;
    }

    /**
     * @param stockItemDAO
     *            the stockItemDAO to set
     */
    public void setStockItemDAO(StockItemDAO stockItemDAO)
    {
        this.stockItemDAO = stockItemDAO;
    }

    /**
     * @return the stockDAO
     */
    public StockDAO getStockDAO()
    {
        return stockDAO;
    }

    /**
     * @param stockDAO
     *            the stockDAO to set
     */
    public void setStockDAO(StockDAO stockDAO)
    {
        this.stockDAO = stockDAO;
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
     * @return the locationDAO
     */
    public LocationDAO getLocationDAO()
    {
        return locationDAO;
    }

    /**
     * @param locationDAO
     *            the locationDAO to set
     */
    public void setLocationDAO(LocationDAO locationDAO)
    {
        this.locationDAO = locationDAO;
    }

    /**
     * @return the storageRelationDAO
     */
    public StorageRelationDAO getStorageRelationDAO()
    {
        return storageRelationDAO;
    }

    /**
     * @param storageRelationDAO
     *            the storageRelationDAO to set
     */
    public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO)
    {
        this.storageRelationDAO = storageRelationDAO;
    }

    /**
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
    }

    /**
     * @return the userDAO
     */
    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    /**
     * @param userDAO
     *            the userDAO to set
     */
    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the departmentDAO
     */
    public DepartmentDAO getDepartmentDAO()
    {
        return departmentDAO;
    }

    /**
     * @param departmentDAO
     *            the departmentDAO to set
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO)
    {
        this.departmentDAO = departmentDAO;
    }

    /**
     * @return the priceAskProviderDAO
     */
    public PriceAskProviderDAO getPriceAskProviderDAO()
    {
        return priceAskProviderDAO;
    }

    /**
     * @param priceAskProviderDAO
     *            the priceAskProviderDAO to set
     */
    public void setPriceAskProviderDAO(PriceAskProviderDAO priceAskProviderDAO)
    {
        this.priceAskProviderDAO = priceAskProviderDAO;
    }

    /**
     * @return the priceAskDAO
     */
    public PriceAskDAO getPriceAskDAO()
    {
        return priceAskDAO;
    }

    /**
     * @param priceAskDAO
     *            the priceAskDAO to set
     */
    public void setPriceAskDAO(PriceAskDAO priceAskDAO)
    {
        this.priceAskDAO = priceAskDAO;
    }

    /**
     * @return the invoiceDAO
     */
    public InvoiceDAO getInvoiceDAO()
    {
        return invoiceDAO;
    }

    /**
     * @param invoiceDAO
     *            the invoiceDAO to set
     */
    public void setInvoiceDAO(InvoiceDAO invoiceDAO)
    {
        this.invoiceDAO = invoiceDAO;
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
     * @return the userManager
     */
    public UserManager getUserManager()
    {
        return userManager;
    }

    /**
     * @param userManager
     *            the userManager to set
     */
    public void setUserManager(UserManager userManager)
    {
        this.userManager = userManager;
    }

    /**
     * @return the showDAO
     */
    public ShowDAO getShowDAO()
    {
        return showDAO;
    }

    /**
     * @param showDAO
     *            the showDAO to set
     */
    public void setShowDAO(ShowDAO showDAO)
    {
        this.showDAO = showDAO;
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
     * @return the depotpartDAO
     */
    public DepotpartDAO getDepotpartDAO()
    {
        return depotpartDAO;
    }

    /**
     * @param depotpartDAO
     *            the depotpartDAO to set
     */
    public void setDepotpartDAO(DepotpartDAO depotpartDAO)
    {
        this.depotpartDAO = depotpartDAO;
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
     * 邮件驳回发送
     * @param fullId
     * @param user
     * @param reason
     * @param vo
     * @param subject
     */
    private void sendOutRejectMail(String fullId, User user, String reason, StockItemVO vo,String subject) 
    {
        StafferBean rejectorBean = stafferDAO.find(user.getStafferId());        

        StafferBean approverBean = stafferDAO.find(vo.getStafferId());
        
        if (null != approverBean)
        {
            StringBuffer sb = new StringBuffer();
            
            sb.append("系统发送>>>")
            .append("\r\n").append("单号:"+ fullId).append(",")
            .append("\r\n").append("采购产品:"+ vo.getProductName()).append(",")
            .append("\r\n").append("总金额:"+ vo.getTotal()).append(",")
            .append("\r\n").append("审批人:"+ rejectorBean.getName()).append(",")
            .append("\r\n").append("审批结果:驳回").append(",")
            .append("\r\n").append("审批意见:" + reason).append(",")
            .append("\r\n").append("审批人电话:"+ rejectorBean.getHandphone());
            
            String message = sb.toString();
            
            String to = approverBean.getNation();
            
            _logger.info(message);
            
            commonMailManager.sendMail(to, subject, message);
        }
    }

	public CommonMailManager getCommonMailManager() {
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager) {
		this.commonMailManager = commonMailManager;
	}

	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public RoleAuthDAO getRoleAuthDAO() {
		return roleAuthDAO;
	}

	public void setRoleAuthDAO(RoleAuthDAO roleAuthDAO) {
		this.roleAuthDAO = roleAuthDAO;
	}

	/**
	 * @return the stockWorkDAO
	 */
	public StockWorkDAO getStockWorkDAO()
	{
		return stockWorkDAO;
	}

	/**
	 * @param stockWorkDAO the stockWorkDAO to set
	 */
	public void setStockWorkDAO(StockWorkDAO stockWorkDAO)
	{
		this.stockWorkDAO = stockWorkDAO;
	}

    public StockItemArrivalDAO getStockItemArrivalDAO() {
        return stockItemArrivalDAO;
    }

    public void setStockItemArrivalDAO(StockItemArrivalDAO stockItemArrivalDAO) {
        this.stockItemArrivalDAO = stockItemArrivalDAO;
    }

    public ProviderDAO getProviderDAO() {
        return providerDAO;
    }

    public void setProviderDAO(ProviderDAO providerDAO) {
        this.providerDAO = providerDAO;
    }

    public ProductBOMDAO getProductBOMDAO() {
        return productBOMDAO;
    }

    public void setProductBOMDAO(ProductBOMDAO productBOMDAO) {
        this.productBOMDAO = productBOMDAO;
    }

    public PurchaseBjDAO getPurchaseBjDAO() {
        return purchaseBjDAO;
    }

    public void setPurchaseBjDAO(PurchaseBjDAO purchaseBjDAO) {
        this.purchaseBjDAO = purchaseBjDAO;
    }

    public PurchaseXqqrDAO getPurchaseXqqrDAO() {
        return purchaseXqqrDAO;
    }

    public void setPurchaseXqqrDAO(PurchaseXqqrDAO purchaseXqqrDAO) {
        this.purchaseXqqrDAO = purchaseXqqrDAO;
    }

	public OutManager getOutManager() {
		return outManager;
	}

	public void setOutManager(OutManager outManager) {
		this.outManager = outManager;
	}

    public OutDAO getOutDAO() {
        return outDAO;
    }

    public void setOutDAO(OutDAO outDAO) {
        this.outDAO = outDAO;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public ExpressDAO getExpressDAO() {
        return expressDAO;
    }

    public void setExpressDAO(ExpressDAO expressDAO) {
        this.expressDAO = expressDAO;
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
    
    
}
