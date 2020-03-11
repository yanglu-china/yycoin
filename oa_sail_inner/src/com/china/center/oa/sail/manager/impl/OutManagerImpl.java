/**
 * File Name: OutManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.manager.impl;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.AddressBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.bean.CustomerContactBean;
import com.china.center.oa.client.bean.CustomerDistAddrBean;
import com.china.center.oa.client.bean.CustomerIndividualBean;
import com.china.center.oa.client.dao.AddressDAO;
import com.china.center.oa.client.dao.CustomerContactDAO;
import com.china.center.oa.client.dao.CustomerDistAddrDAO;
import com.china.center.oa.client.dao.CustomerIndividualDAO;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.facade.ClientFacade;
import com.china.center.oa.client.manager.ClientManager;
import com.china.center.oa.client.vo.CustomerVO;
import com.china.center.oa.client.vo.StafferVSCustomerVO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.credit.bean.CreditLevelBean;
import com.china.center.oa.credit.dao.CreditCoreDAO;
import com.china.center.oa.credit.dao.CreditLevelDAO;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.extsail.bean.ZJRCOutBean;
import com.china.center.oa.extsail.dao.ZJRCOutDAO;
import com.china.center.oa.extsail.manager.ZJRCManager;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.note.manager.HandleMessage;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProductImportBean;
import com.china.center.oa.product.bean.StorageLogBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductCombinationDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductImportDAO;
import com.china.center.oa.product.dao.StorageLogDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.helper.StorageRelationHelper;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.product.wrap.ProductChangeWrap;
import com.china.center.oa.publics.DateTimeUtils;
import com.china.center.oa.publics.NumberUtils;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.publics.bean.AreaBean;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.EnumBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.InvoiceCreditBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.NotifyBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.bean.UserBean;
import com.china.center.oa.publics.constant.AppConstant;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.InvoiceConstant;
import com.china.center.oa.publics.constant.PluginNameConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.PublicLock;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.AreaDAO;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.EnumDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.InvoiceCreditDAO;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.manager.CommonMailManager;
import com.china.center.oa.publics.manager.FatalNotify;
import com.china.center.oa.publics.manager.NotifyManager;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.publics.vo.InvoiceCreditVO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.publics.wrap.ResultBean;
import com.china.center.oa.sail.bean.AppOutVSOutBean;
import com.china.center.oa.sail.bean.AuditRuleItemBean;
import com.china.center.oa.sail.bean.AutoApproveBean;
import com.china.center.oa.sail.bean.BaseBalanceBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.BaseRepaireBean;
import com.china.center.oa.sail.bean.BatchDropBean;
import com.china.center.oa.sail.bean.BatchReturnLog;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.DeliveryRankVSOutBean;
import com.china.center.oa.sail.bean.DistributionBaseBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.FrDbBean;
import com.china.center.oa.sail.bean.OutBackBean;
import com.china.center.oa.sail.bean.OutBackItemBean;
import com.china.center.oa.sail.bean.OutBalanceBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.OutPayTagBean;
import com.china.center.oa.sail.bean.OutRepaireBean;
import com.china.center.oa.sail.bean.OutUniqueBean;
import com.china.center.oa.sail.bean.PackageBean;
import com.china.center.oa.sail.bean.PreConsignBean;
import com.china.center.oa.sail.bean.PromotionBean;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.bean.SailTranApplyBean;
import com.china.center.oa.sail.bean.StatsDeliveryRankBean;
import com.china.center.oa.sail.bean.SwatchStatsBean;
import com.china.center.oa.sail.bean.SwatchStatsItemBean;
import com.china.center.oa.sail.bean.TempConsignBean;
import com.china.center.oa.sail.constanst.AuditRuleConstant;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.constanst.SailConstant;
import com.china.center.oa.sail.dao.AppOutVSOutDAO;
import com.china.center.oa.sail.dao.AutoApproveOutDAO;
import com.china.center.oa.sail.dao.BaseBalanceDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.BaseRepaireDAO;
import com.china.center.oa.sail.dao.BatchReturnLogDAO;
import com.china.center.oa.sail.dao.ConsignDAO;
import com.china.center.oa.sail.dao.DeliveryRankVSOutDAO;
import com.china.center.oa.sail.dao.DhZjbDAO;
import com.china.center.oa.sail.dao.DistributionBaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.FrDbDAO;
import com.china.center.oa.sail.dao.OutBackDAO;
import com.china.center.oa.sail.dao.OutBackItemDAO;
import com.china.center.oa.sail.dao.OutBalanceDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutPayTagDAO;
import com.china.center.oa.sail.dao.OutRepaireDAO;
import com.china.center.oa.sail.dao.OutUniqueDAO;
import com.china.center.oa.sail.dao.PackageDAO;
import com.china.center.oa.sail.dao.PreConsignDAO;
import com.china.center.oa.sail.dao.ProductExchangeConfigDAO;
import com.china.center.oa.sail.dao.PromotionDAO;
import com.china.center.oa.sail.dao.SailConfigDAO;
import com.china.center.oa.sail.dao.SailTranApplyDAO;
import com.china.center.oa.sail.dao.StatsDeliveryRankDAO;
import com.china.center.oa.sail.dao.SwatchStatsDAO;
import com.china.center.oa.sail.dao.SwatchStatsItemDAO;
import com.china.center.oa.sail.dao.TempConsignDAO;
import com.china.center.oa.sail.helper.FlowLogHelper;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.helper.YYTools;
import com.china.center.oa.sail.listener.OutListener;
import com.china.center.oa.sail.manager.AuditRuleManager;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.sail.manager.ShipManager;
import com.china.center.oa.sail.vo.BaseBalanceVO;
import com.china.center.oa.sail.vo.OutInterface;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.oa.sail.vo.ProductExchangeConfigVO;
import com.china.center.oa.sail.wrap.BatchBackWrap;
import com.china.center.oa.sail.wrap.CreditWrap;
import com.china.center.osgi.dym.DynamicBundleTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.ParamterMap;
import com.china.center.tools.RandomTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;

/**
 * OutManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see OutManagerImpl
 * @since 1.0
 */
public class OutManagerImpl extends AbstractListenerManager<OutListener> implements OutManager
{
    private final Log _logger = LogFactory.getLog(getClass());
    
    private final static String EmptyDepotpartId = "999";

    private final Log operationLog = LogFactory.getLog("opr");

    private final Log importLog = LogFactory.getLog("sec");

    private final Log triggerLog = LogFactory.getLog("trigger");

    private LocationDAO locationDAO = null;

    private CommonDAO commonDAO = null;

    private ProductDAO productDAO = null;
    
    private UserDAO userDAO = null;

    private StafferDAO stafferDAO = null;
    
    protected PromotionDAO promotionDAO = null;
    
    protected ClientFacade clientFacade = null;

    private InvoiceCreditDAO invoiceCreditDAO = null;

    private OutDAO outDAO = null;

    private DepotDAO depotDAO = null;

    private BaseDAO baseDAO = null;

    private DutyDAO dutyDAO = null;

    private ConsignDAO consignDAO = null;

    private CustomerMainDAO customerMainDAO = null;

    private ClientManager clientManager = null;

    private ParameterDAO parameterDAO = null;

    private CreditLevelDAO creditLevelDAO = null;

    private DepotpartDAO depotpartDAO = null;

    private CreditCoreDAO creditCoreDAO = null;

    private SailTranApplyDAO sailTranApplyDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private SailConfigDAO sailConfigDAO = null;

    private NotifyManager notifyManager = null;

    private InvoiceDAO invoiceDAO = null;

    private FatalNotify fatalNotify = null;

    private BaseBalanceDAO baseBalanceDAO = null;

    private OutBalanceDAO outBalanceDAO = null;

    private SailConfigManager sailConfigManager = null;

    private StorageRelationManager storageRelationManager = null;

    private StorageRelationDAO storageRelationDAO = null;
    
    private StorageLogDAO storageLogDAO = null;
    
    private OutUniqueDAO outUniqueDAO = null;

    private OutRepaireDAO outRepaireDAO = null;
    
    private BaseRepaireDAO baseRepaireDAO = null;
    
    private ProductCombinationDAO productCombinationDAO = null;
    
    private DistributionDAO distributionDAO = null;

    private PriceConfigDAO priceConfigDAO = null;
    
    private PriceConfigManager priceConfigManager = null;
    
    private AuditRuleManager auditRuleManager = null;
    
    private CommonMailManager commonMailManager = null;
    
    private AttachmentDAO attachmentDAO = null;
    
    private AddressDAO addressDAO = null;
    
    private SwatchStatsDAO swatchStatsDAO = null;
    
    private SwatchStatsItemDAO swatchStatsItemDAO = null;
    
    private AppOutVSOutDAO appOutVSOutDAO = null;
    
    private DistributionBaseDAO distributionBaseDAO = null;
    
    private StatsDeliveryRankDAO statsDeliveryRankDAO = null;
    
    private DeliveryRankVSOutDAO deliveryRankVSOutDAO = null;
    
    private PreConsignDAO preConsignDAO = null;

    private TempConsignDAO tempConsignDAO = null;
    
    private ShipManager shipManager = null;
    
    private ProvinceDAO provinceDAO = null;
    
    private CityDAO cityDAO = null;
    
    private AreaDAO areaDAO = null;
    
    private StafferVSCustomerDAO stafferVSCustomerDAO = null;
    
    private BatchReturnLogDAO batchReturnLogDAO = null;
    
    private OutPayTagDAO outPayTagDAO = null;
    
    private ZJRCManager zjrcManager = null;

    private CustomerIndividualDAO  customerIndividualDAO = null;

    private ZJRCOutDAO zjrcOutDAO = null;

    private PackageDAO packageDAO = null;

    private AutoApproveOutDAO autoApproveOutDAO = null;

    private ProductExchangeConfigDAO productExchangeConfigDAO = null;

    private OutBackDAO outBackDAO = null;

    private OutBackItemDAO outBackItemDAO = null;

    private ProductImportDAO productImportDAO = null;

    private EnumDAO enumDAO = null;

    private CustomerDistAddrDAO customerDistAddrDAO = null;

    private CustomerContactDAO customerContactDAO = null;

    private DhZjbDAO dhZjbDAO = null;

    private FrDbDAO frDbDAO = null;

    /**
     * 短信最大停留时间
     */
    private int internal = 300000;

    private PlatformTransactionManager transactionManager = null;

    /**
     * default constructor
     */
    public OutManagerImpl()
    {
    }
    
    public List<BaseBean> queryBaseByConditions(final Map dataMap)
    throws MYException
    {
    	return this.outDAO.queryBaseByConditions(dataMap);
    }

    /**
     * 增加(修改)
     * 
     * @param outBean
     * @return String 销售单的ID
     * @throws Exception
     */
    public String addOut(final OutBean outBean, final Map dataMap, final User user)
        throws MYException
    {
        ParamterMap request = new ParamterMap(dataMap);

        String fullId = request.getParameter("fullId");
        
        String dutyId = request.getParameter("dutyId");
        
        // 0:表示新的批量开单 1:表示单笔（仓库+管理类型+旧货）用于驳回态修改
        final String oprType = request.getParameter("oprType") == null ? "" : request.getParameter("oprType");

        dataMap.put("tmdutyId", dutyId);
        
        _logger.debug("fullId:"+fullId+", type:"+outBean.getType()+", outType:"+outBean.getOutType());

        if (StringTools.isNullOrNone(fullId))
        {
            // 先保存
            String id = getAll(commonDAO.getSquence());

            LocationBean location = locationDAO.find(outBean.getLocationId());

            if (location == null)
            {
                _logger.error("区域不存在:" + outBean.getLocationId());

                throw new MYException("区域不存在:" + outBean.getLocationId());
            }

            //String flag = location.getCode();
            String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());
            
            _logger.debug("type:"+outBean.getType()+", outType:"+outBean.getOutType()+", flag:"+flag);
            
            // 临时单号，待拆分为SO单
            if (oprType.equals("0"))
            {
            	flag = "TM";
            }

            String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

            fullId = flag + time + id;

            outBean.setId(getOutId(id));

            outBean.setFullId(fullId);

            dataMap.put("modify", false);
        }
        else
        {
            dataMap.put("modify", true);
        }

        // 批量产品开单时，为saveOutInner准备
        if (oprType.equals("0") && outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        	outBean.setLocation(oprType);
        
        final String totalss = request.getParameter("totalss");

        outBean.setTotal(MathTools.parseDouble(totalss));
        
        outBean.setStatus(OutConstant.STATUS_SAVE);

        outBean.setPay(OutConstant.PAY_NOT);

        outBean.setInway(OutConstant.IN_WAY_NO);

        // 获得baseList
        final String[] nameList = this.getParam(request, "nameList");
        
        for(String name: nameList){
        	_logger.debug("name: "+ name);
        }
        
        final String[] idsList = this.getParam(request, "idsList");
//        final String[] showIdList = request.getParameter("showIdList").split("~");
//        final String[] showNameList = request.getParameter("showNameList").split("~");
//        final String[] unitList = request.getParameter("unitList").split("~");
        final String[] amontList = this.getParam(request, "amontList");

        // 含税价
        final String[] priceList = this.getParam(request, "priceList");

        // 输入价格
        final String[] inputPriceList = this.getParam(request, "inputPriceList");

        // 显示成本(只有V5有)
        final String[] showCostList = this.getParam(request, "showCostList");

        // 成本
        final String[] desList = this.getParam(request, "desList");

        //#545 采购入库的时候，非虚拟商品的虚料金额是0
        //虚拟商品的虚料金额，等于成本价
        //String vpl = request.getParameter("virtualPriceList");
        //虚料金额
        String[] virtualPriceList1 = this.getParam(request, "virtualPriceList");
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_IN_COMMON){
            //vpl = request.getParameter("desList");
            virtualPriceList1 = this.getParam(request, "desList");
        }
        
        final String[] virtualPriceList = virtualPriceList1;


        final String[] otherList = this.getParam(request, "otherList");
        
        final String [] depotList = this.getParam(request, "depotList");
        
        final String [] mtypeList = this.getParam(request, "mtypeList");
        
        final String [] oldGoodsList = this.getParam(request, "oldGoodsList");
        
        final String [] taxrateList = this.getParam(request, "taxrateList");
        
        final String [] taxList = this.getParam(request, "taxList");
        
        final String [] inputRateList = this.getParam(request, "inputRateList");

//        final String ibMoneyListStr = request.getParameter("ibMoneyList");
//        final String motivationMoneyListStr = request.getParameter("motivationMoneyList");
        
        _logger.info(fullId + "/nameList/" + request.getParameter("nameList"));

        _logger.info(fullId + "/idsList/" + request.getParameter("idsList"));

        _logger.info(fullId + "/totalList/" + request.getParameter("totalList"));

//        _logger.info(fullId + "/price/" + request.getParameter("priceList"));

        _logger.info(fullId + "/inputPriceList/" + request.getParameter("inputPriceList"));

        _logger.info(fullId + "/showCostList/" + request.getParameter("showCostList"));

        _logger.info(fullId + "/desList/" + request.getParameter("desList"));

        _logger.info(fullId + "/otherList/" + request.getParameter("otherList"));
        
        _logger.info(fullId + "/depotList/" + request.getParameter("depotList"));
        
        _logger.info(fullId + "/mtypeList/" + request.getParameter("mtypeList"));
        
        _logger.info(fullId + "/oldGoodsList/" + request.getParameter("oldGoodsList"));
        
        _logger.info(fullId + "/taxrateList/" + request.getParameter("taxrateList"));
        _logger.info(fullId + "/taxList/" + request.getParameter("taxList"));
        _logger.info(fullId + "/inputRateList/" + request.getParameter("inputRateList"));
//        _logger.info(fullId + "/ibMoneyList/" + ibMoneyListStr);
//        _logger.info(fullId + "/motivationMoneyList/" + motivationMoneyListStr);

        // 组织BaseBean
        double ttatol = 0.0d;
        
        for (int i = 0; i < nameList.length; i++ )
        {
            try{
                ttatol += (Double.parseDouble(priceList[i]) * Integer.parseInt(amontList[i]));
            }catch(Exception e){}
        }

        outBean.setTotal(ttatol);

        outBean.setCurcredit(0.0d);

        outBean.setStaffcredit(0.0d);

        if (StringTools.isNullOrNone(outBean.getCustomerId())
            || CustomerConstant.PUBLIC_CUSTOMER_ID.equals(outBean.getCustomerId()))
        {
            outBean.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);

            outBean.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);
        }else{
        	
        	if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL){
        		// 增加客户与职员对应关系的校验 
            	StafferVSCustomerBean stafferVSCust = stafferVSCustomerDAO.findByUnique(outBean.getCustomerId());
            	
            	if (null == stafferVSCust){
            		throw new MYException("客户[%s]没有挂靠职员", outBean.getCustomerName());
            	}else{
            		if (!stafferVSCust.getStafferId().equals(outBean.getStafferId()))
            		{
            			throw new MYException("客户[%s]不在职员[%s]名下", outBean.getCustomerName(), outBean.getStafferName());
            		}
            	}
        	}
        }

        if (StringTools.isNullOrNone(outBean.getInvoiceId()))
        {
            outBean.setHasInvoice(OutConstant.HASINVOICE_NO);
        }
        else
        {
            outBean.setHasInvoice(OutConstant.HASINVOICE_YES);
        }

        // 赠送的价格为0
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
        {
            outBean.setTotal(0.0d);
        }

        // 行业属性
        setInvoiceId(outBean);

        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
        {
        	String eventId = request.getParameter("eventId");
        	
        	if (StringTools.isNullOrNone(eventId))
        		eventId = "";
        	
        	PromotionBean promBean = promotionDAO.find(eventId);
        	
            if (outBean.getPromValue() > 0 || (promBean==null?false:promBean.getRebateType()==2))//此处折扣类型为增加信用分也绑定活动单号
                
                outBean.setPromStatus(0);
            else{
                
                outBean.setEventId("");
                outBean.setRefBindOutId("");
                outBean.setPromStatus(-1);
            }
        }

        final StafferBean stafferBean = stafferDAO.find(user.getStafferId());

        // 防止商务登陆多人在不同的页面，导致脏数据
        if (!user.getStafferName().equals(outBean.getStafferName()))
        {
        	throw new MYException("当前登陆者为[%s],但系统中记录的是[%s],请重新登陆！", user.getStafferName(), outBean.getStafferName());
        }
        
        // 增加管理员操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	String dutyId = "";
                	
                    if ((Boolean)dataMap.get("modify"))
                    {
                        // 在删除前检查单据状态是否为保存或驳回
                        OutBean coutBean = outDAO.find(outBean.getFullId());
                        
                        if (coutBean.getStatus() != OutConstant.STATUS_SAVE && coutBean.getStatus() != OutConstant.STATUS_REJECT)
                        {
                            throw new RuntimeException("销售单"+outBean.getFullId()+"已不是保存或驳回状态，当前状态是："+OutHelper.getStatus(coutBean.getStatus())+",不可修改");
                        }
                        
                        outDAO.deleteEntityBean(outBean.getFullId());

                        baseDAO.deleteEntityBeansByFK(outBean.getFullId());
                        
                        dutyId = coutBean.getDutyId();
                    }
                    
                    _logger.debug("outBean.getFullId():"+outBean.getFullId());

                    // 组织BaseBean
                    boolean addSub = false;

                    boolean hasZero = false;

                    double total = 0.0d;
                    
                    double taxTotal = 0.0d;

                    List<BaseBean> baseList = new ArrayList<BaseBean>();

                    boolean sailJiuBi = false;
                    
                    boolean sailCommon = false;
                    
                    List<ProductBean> tempProductList = new ArrayList<ProductBean>();
                    
                    int accountPeroid = 0;
                    
//                    boolean isManagerPass = false;
                    StringBuffer messsb = new StringBuffer();
                    
                    _logger.debug("nameList.length:"+ nameList.length);
                    
                    // 处理每个base
                    for (int i = 0; i < nameList.length; i++ )
                    {
                        BaseBean base = new BaseBean();

                        base.setId(commonDAO.getSquenceString());

                        // 允许存在
                        base.setAmount(MathTools.parseInt(amontList[i]));

                        if (base.getAmount() == 0)
                        {
                            continue;
                        }

                        base.setOutId(outBean.getFullId());

                        base.setProductId(idsList[i]);

                        if (StringTools.isNullOrNone(base.getProductId()))
                        {
                            throw new RuntimeException("产品ID为空,数据不完备");
                        }

                        ProductBean product = productDAO.find(base.getProductId());

                        if (product == null)
                        {
                            throw new RuntimeException("产品为空,数据不完备");
                        }

                        //#359
                        if ((outBean.getType() == OutConstant.OUT_TYPE_OUTBILL && outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
                                ||(outBean.getType() == OutConstant.OUT_TYPE_INBILL && outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK)){
                            CustomerBean customerBean = customerMainDAO.find(outBean.getCustomerId());
                            try {
                                ProductImportBean productImportBean = getProductImportBean(outBean, customerBean, base.getProductId());
                                if (productImportBean != null){
                                   base.setGrossProfit(productImportBean.getGrossProfit());
                                   base.setCash(productImportBean.getCash());
                                   base.setCash2(productImportBean.getCash2());
                                }
                            }catch (Exception e){
                                _logger.error(e);
                            }
                        }
                        
/*                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                            && !nameList[i].trim().equals(product.getName().trim()))
                        {
                            throw new RuntimeException("产品名不匹配,请重新操作.申请:" + nameList[i].trim()
                                                       + ".实际:" + product.getName());
                        }*/

                        // 旧币的产品必须单独销售，不允许和其他的产品类型一起销售
//                        if (product.getType() == ProductConstant.PRODUCT_TYPE_NUMISMATICS)
//                        {
//                            sailJiuBi = true;
//                        }
                        
                        // 旧货（旧币与邮票均为旧货）
                        if (product.getConsumeInDay() == ProductConstant.PRODUCT_OLDGOOD)
                        {
                            sailJiuBi = true;
                        }
                        
                        if (product.getId().equals(ProductConstant.OUT_COMMON_PRODUCTID)
                        		|| product.getId().equals(ProductConstant.OUT_COMMON_MPRODUCTID))
                        {
                        	sailCommon = true;
                        }

                        tempProductList.add(product);

                        // 产品名称来源于数据库
                        base.setProductName(product.getName());

                        base.setUnit("套");

                        // 赠送的价格为0
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                            && outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
                        {
                            base.setPrice(0.0d);
                        }
                        else
                        {
                            base.setPrice(MathTools.parseDouble(priceList[i]));
                        }

                        if (base.getPrice() == 0)
                        {
                            hasZero = true;
                        }

                        // 销售价格动态获取的
                        base.setValue(base.getAmount() * base.getPrice());

                        total += base.getValue();

                        // 入库单是没有showId的 - 作废
//                        if (showNameList != null && showNameList.length >= (i + 1))
//                        {
//                            base.setShowId(showIdList[i]);
//                            base.setShowName(showNameList[i]);
//                        }

                        // 这里需要处理99的其他入库,因为其他入库是没有完成的otherList
                        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                            && outBean.getOutType() == OutConstant.OUTTYPE_IN_OTHER)
                        {
                            base.setCostPrice(MathTools.parseDouble(desList[i]));
                            base.setCostPriceKey(StorageRelationHelper.getPriceKey(base
                                .getCostPrice()));

                            base.setOwner("0");

                            // 默认仓区
                            DepotpartBean defaultOKDepotpart = depotpartDAO
                                .findDefaultOKDepotpart(outBean.getLocation());

                            if (defaultOKDepotpart == null)
                            {
                                throw new RuntimeException("没有默认的良品仓,请确认操作");
                            }

                            base.setDepotpartId(defaultOKDepotpart.getId());
                        }
                        else
                        {
                            // ele.productid + '-' + ele.price + '-' + ele.stafferid + '-' + ele.depotpartid
                        	_logger.debug("otherList[i]:"+otherList[i]);
                            String[] coreList = otherList[i].split("-");

                            if (coreList.length < 3)
                            {
                                throw new RuntimeException("数据不完备");
                            }

                            // 寻找具体的产品价格位置
                            base.setCostPrice(MathTools.parseDouble(coreList[1]));

                            base.setCostPriceKey(StorageRelationHelper.getPriceKey(base
                                .getCostPrice()));

                            base.setOwner(coreList[2]);
                            
                            if(coreList.length>3){
                            	base.setDepotpartId(coreList[3]);
                            }
                            
                        }

                        // 这里需要核对价格 调拨
                        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                            && (outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT || outBean
                                .getOutType() == OutConstant.OUTTYPE_IN_DROP))
                        {
                            if ( !MathTools.equal(base.getPrice(), base.getCostPrice()))
                            {
                                throw new RuntimeException("调拨/报废的时候价格必须相等");
                            }
                        }

                        if (StringTools.isNullOrNone(base.getOwner()))
                        {
                            base.setOwner("0");
                        } else if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                                && outBean.getOutType() == OutConstant.OUTTYPE_IN_STOCK){
                            base.setOwner("0");
                        }

                        if ("0".equals(base.getOwner()))
                        {
                            base.setOwnerName("公共");
                        }
                        else
                        {
                            StafferBean sb = stafferDAO.find(base.getOwner());

                            if (sb == null)
                            {
                                throw new RuntimeException("所属职员不存在,请确认操作");
                            }

                            base.setOwnerName(sb.getName());
                        }

                        if(!EmptyDepotpartId.equals(base.getDepotpartId())){
                            DepotpartBean deport = depotpartDAO.find(base.getDepotpartId());

                            if (deport == null)
                            {
                                throw new RuntimeException("仓区("+base.getDepotpartId()+")不存在,请确认操作");
                            }

                            base.setDepotpartName(deport.getName());                       	
                        
                            // 销售单的时候仓库必须一致
                            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                                && !deport.getLocationId().equals(outBean.getLocation())
                                && !oprType.equals("0"))
                            {
                                throw new RuntimeException("销售必须在一个仓库下面");
                            }
                        }

                        // 调拨的时候有bug啊
                        base.setLocationId(outBean.getLocation());
                        
                        //base.setMtype(outBean.getMtype());
                        base.setMtype(MathTools.parseInt(product.getReserve4()));

                        // 其实也是成本
                        base.setDescription(desList[i].trim());

                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                        {
                            //#545
                            base.setVirtualPrice(MathTools.parseDouble(virtualPriceList[i]));
                            base.setVirtualPriceKey(StorageRelationHelper.getPriceKey(base
                                    .getVirtualPrice()));
                            // 显示成本(V5新功能)
                            base.setInputPrice(MathTools.parseDouble(showCostList[i]));
                            
                        	base.setTaxrate(MathTools.parseDouble(taxrateList[i]));
                        	
                        	base.setTax(MathTools.parseDouble(taxList[i]));
                        	
                        	base.setInputRate(MathTools.parseDouble(inputRateList[i]));
                        	
                        	taxTotal += base.getTax();
                        	
                        	 // 批量开单 - 拆单标准
                            if (oprType.equals("0"))
                            {
                            	base.setLocationId(depotList[i]);
                            	base.setMtype(MathTools.parseInt(mtypeList[i]));
                            	base.setOldGoods(MathTools.parseInt(oldGoodsList[i]));
                            }
                        }
                        else
                        {
                            // 入库单
                            if (inputPriceList != null && inputPriceList.length > i)
                            {
                                // 兼容
                                base.setInputPrice(MathTools.parseDouble(inputPriceList[i]));
                            }
                            
                            base.setOldGoods(product.getConsumeInDay());

                            //#545
//                            if (isVirtualProduct(base.getProductId())){
//                                base.setVirtualPrice(MathTools.parseDouble(virtualPriceList[i]));
//                                base.setVirtualPriceKey(StorageRelationHelper.getPriceKey(base
//                                        .getVirtualPrice()));
//                            } else{
//                                base.setVirtualPrice(0);
//                                base.setVirtualPriceKey(StorageRelationHelper.getPriceKey(base
//                                        .getVirtualPrice()));
//                            }
                            //#545 采购入库时才会判断是否为虚拟产品，是的话，虚料金额=成本金额
                            //其他入库单在确认库存时不用判断
                            base.setVirtualPrice(MathTools.parseDouble(virtualPriceList[i]));
                            base.setVirtualPriceKey(StorageRelationHelper.getPriceKey(base
                                    .getVirtualPrice()));
                        }

                        double sailPrice = 0.0d;
                        
                        double minPrice = 0.0d;

                        sailPrice = product.getSailPrice();
                        
                        minPrice = sailPrice;
                        
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                        {
                        	// 毛利，毛利率（针对业务员的）
                        	double profit = 0.0d;
                        	
                        	double profitRatio = 0.0d;
                        	
                        	if (base.getValue() != 0)
                        	{
                            	profit = base.getAmount() * (base.getPrice() - base.getInputPrice());
                            	
                            	profitRatio = profit / base.getValue();
                        	}

                        	base.setProfit(profit);
                        	base.setProfitRatio(profitRatio);
                        	
                        	PriceConfigBean priceConfigBean = priceConfigManager
                            						.getPriceConfigBean(product.getId(), outBean.getIndustryId(), outBean.getStafferId());
                        	
                        	//operationLog.info("销售单号： "+outBean.getFullId() + ", priceConfigBean:" + priceConfigBean);
                        	
                        	if (null != priceConfigBean)
                        	{
                        		sailPrice = priceConfigBean.getPrice();
                        		
                        		minPrice = priceConfigBean.getMinPrice();
                        	}
                        	
                        	// 检查到款天数（销售账期）及销售价是否低于规定的售价
                        	AuditRuleItemBean auditRuleItemBean = auditRuleManager
                        					.getAuditRuleItem(product.getId(), outBean.getIndustryId(), 
                        							outBean.getOutType(), outBean.getReserve3());
                        	
                        	//operationLog.info("销售单号： "+outBean.getFullId() + ", auditRuleItemBean:" + auditRuleItemBean);
                        	
                        	if (null != auditRuleItemBean)
                        	{
                        		int peroid1 = auditRuleItemBean.getAccountPeriod();
                        		int peroid2 = auditRuleItemBean.getProductPeriod();
                        		int peroid3 = auditRuleItemBean.getProfitPeriod();
                        		
                        		int minPeroid = OutHelper.compare3IntMin(peroid1, peroid2, peroid3);
                        		
                        		if (accountPeroid == 0)
                        		{
                        			accountPeroid = minPeroid;
                        		}
                        		else
                        		{
                        			accountPeroid = Math.min(accountPeroid, minPeroid);
                        		}
                        		
                        		// 检查价格,成交价小于最低售价
                        		if (base.getPrice()<minPrice)
                        		{
                        			if (outBean.getOutType() != OutConstant.OUTTYPE_OUT_PRESENT)
                        			{
                        				if (auditRuleItemBean.getLtSailPrice() == AuditRuleConstant.LESSTHANSAILPRICE_NO)
                            			{
                            				throw new RuntimeException("售价不能低于最低售价，最低售价为："+ MathTools.round2(minPrice)+" ,不允许销售.");
                            			}
                            			else
                            			{
                            				double cha = minPrice - base.getPrice();
                            				
                            				if (cha > minPrice * auditRuleItemBean.getDiffRatio())
                            				{
                            					operationLog.info("销售单号： "+outBean.getFullId() + ", 售价低于最低售价时，差异值大于差异比例规定的范围");
                            					
                            					messsb.append("售价低于最低售价时，差异值大于差异比例规定的范围;");
                            				}
                            			}
                        			}
                        		}
                        		else // 成交价大于最低售价，则判断毛利率
                        		{
                        			double ratioUp = auditRuleItemBean.getRatioUp()/100d; 
                        			
                        			double ratioDown = auditRuleItemBean.getRatioDown()/100d;
                        			
                        			if (ratioUp > 0)
                        			{
                        				if (ratioDown > profitRatio)
                        				{
                        					operationLog.info("销售单号： "+outBean.getFullId() + ", 商品的毛利率小于规则中规定的毛利率的下限");
                        					
                        					messsb.append("商品的毛利率小于规则中规定的毛利率的下限;");
                        				}
                        			}
                        			
                        			if (auditRuleItemBean.getMinRatio() > 0)
                        			{
                        				if (profitRatio < auditRuleItemBean.getMinRatio())
                        				{
                        					operationLog.info("销售单号： "+outBean.getFullId() + ", 商品的毛利率小于规则中规定的最小毛利率.");
                        					
                        					messsb.append("商品的毛利率小于规则中规定的最小毛利率;");
                        				}
                        			}
                        		}
                        	}
                        }
                        
                        // 公卖
                        if ("0".equals(base.getOwner()))
                        {
				           if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL && 
				        		   (base.getProductId().equals(ProductConstant.OUT_COMMON_PRODUCTID)
				        				   || base.getProductId().equals(ProductConstant.OUT_COMMON_MPRODUCTID)))
                        	{
                        		base.setPprice(base.getCostPrice());
                        		
                        		base.setInputPrice(base.getCostPrice());
                        		
                        		base.setIprice(base.getCostPrice());
                        	}
                        	else
                        	{
                        		// 获取销售配置
                                SailConfBean sailConf = sailConfigManager.findProductConf(stafferBean,
                                    product);
                                
                                // 总部结算价(产品结算价 * (1 + 总部结算率))
                                base.setPprice(sailPrice
                                               * (1 + sailConf.getPratio() / 1000.0d));

                                //#647
                                if (sailConf.getIprice() > 0){
                                    base.setIprice(sailConf.getIprice());
                                } else{
                                    // 事业部结算价(产品结算价 * (1 + 总部结算率 + 事业部结算率))
                                    base.setIprice(sailPrice
                                            * (1 + sailConf.getIratio() / 1000.0d + sailConf
                                            .getPratio() / 1000.0d));
                                }


                                // 业务员结算价就是事业部结算价
                                base.setInputPrice(base.getIprice());
                        	}
                        	
                        	if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                            {
                                // 发现一些异常,这里保护一下
                                if (base.getInputPrice() == 0)
                                {
                                    throw new RuntimeException("业务员结算价不能为0");
                                }
                            }
                        }
                        else
                        {
                            // 私卖
                            base.setPprice(base.getInputPrice());
                            base.setIprice(base.getInputPrice());
                        }

                        //2015/4/15 中收激励金额（对于入库来说没有此数据）
//                        if (!StringTools.isNullOrNone(ibMoneyListStr)){
//                            final String [] ibMoneyList = ibMoneyListStr.split("~");
//                            base.setIbMoney(MathTools.parseDouble(ibMoneyList[i]));
//                        }
//
//                        if (!StringTools.isNullOrNone(motivationMoneyListStr)){
//                            final String [] motivationMoneyList = motivationMoneyListStr.split("~");
//                            base.setMotivationMoney(MathTools.parseDouble(motivationMoneyList[i]));
//                        }


                        baseList.add(base);

                        // 增加单个产品到base表
                        baseDAO.saveEntityBean(base);

                        _logger.info("生成BaseBean:"+base);

                        addSub = true;
                    }

                    // 旧币的产品必须单独销售(或者都是旧币)，不允许和其他的产品类型一起销售
                    if (sailJiuBi && false)
                    {
                        for (ProductBean each : tempProductList)
                        {
                            if (each.getConsumeInDay() != ProductConstant.PRODUCT_OLDGOOD)
                            {
                                throw new RuntimeException("旧货的产品必须单独销售(或者都是旧货)，不允许和其他的产品类型一起销售:"
                                                           + each.getName());
                            }
                        }
                    }

                    if (sailCommon && !oprType.equals("0") && false)
                    {
                        for (ProductBean each : tempProductList)
                        {
                            if (!each.getId().equals(ProductConstant.OUT_COMMON_PRODUCTID)
                            		&& !each.getId().equals(ProductConstant.OUT_COMMON_MPRODUCTID))
                            {
                                throw new RuntimeException("通用商品不允许和其他的产品一起销售:"
                                                           + each.getName());
                            }
                        }
                    }
                    
                    
                    // 自卖的东西必须先卖掉
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                    {
                        for (BaseBean base : baseList)
                        {
                            if ("0".equals(base.getOwner()))
                            {
                            	// 通用商品不判断
                            	if (base.getProductId().equals(ProductConstant.OUT_COMMON_PRODUCTID) 
                            			|| base.getProductId().equals(ProductConstant.OUT_COMMON_MPRODUCTID))
                            	{
                            		continue;
                            	}
                            	
                                ConditionParse con = new ConditionParse();
                                con.addWhereStr();
                                con.addCondition("stafferId", "=", user.getStafferId());
                                con.addCondition("productId", "=", base.getProductId());

                                List<StorageRelationBean> selfRelation = storageRelationDAO
                                    .queryEntityBeansByCondition(con);

                                if (ListTools.isEmptyOrNull(selfRelation))
                                {
                                    continue;
                                }

                                int samont = 0;

                                for (StorageRelationBean seach : selfRelation)
                                {
                                    samont += seach.getAmount();
                                }

                                // 看看是否都在里面出售
                                int amount = 0;

                                for (BaseBean each : baseList)
                                {
                                    if (user.getStafferId().equals(each.getOwner())
                                        && base.getProductId().equals(each.getProductId()))
                                    {
                                        amount += each.getAmount();
                                    }
                                }

                                if (samont != amount)
                                {
                                    throw new RuntimeException("必须先销售自己名下的产品["
                                                               + base.getProductName() + "]");
                                }
                            }
                        }
                    }

                    // 销售单强制设置为赠送
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL && hasZero)
                    {
                        outBean.setOutType(OutConstant.OUTTYPE_OUT_PRESENT);
                    }

                    // 检查账期
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                    {
                    	// 黑名单（款到发货）回款天数为0
                    	if (outBean.getReserve3() == 1)
                    	{
                    		if (outBean.getReday() > 0)
                    		{
                    			//messsb.append("款到发货时，回款天数大于0;");
                    		}
                    	}
                    	else
                    	{
                    		if (outBean.getReday() > accountPeroid)
                    		{
                    			//messsb.append("回款天数大于规则中指定的账期;");
                    		}
                    	}
                    	
                    	// 下一审批流为结算中心（稽核）
//                    	if (isManagerPass)
//                    	{
                    	if (StringTools.isNullOrNone(outBean.getFlowId())){
                    		outBean.setFlowId(OutConstant.FLOW_MANAGER);
                    	}
//                    	}
                    		
                    	outBean.setDescription(outBean.getDescription() + "&&" + messsb.toString());
                    	
                    	if (!StringTools.isNullOrNone(dutyId))
                    	{
                    		outBean.setDutyId(dutyId);
                    	}
                    }
                    
                    // 重新计算价格
                    outBean.setTotal(total);
                    
                    outBean.setTaxTotal(taxTotal);

                    // 促销金额不能大于本单总金额
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                    {
                        if (outBean.getPromValue() > outBean.getTotal())
                        {
                            throw new RuntimeException("促销折扣金额不能大于本单总金额");
                        }
                    }
                    
                    outBean.setBaseList(baseList);

                    // 保存入库单
                    try
                    {
                        saveOutInner(outBean);
                        _logger.debug("save outBean.getFullId():"+outBean.getFullId());
                    }
                    catch (MYException e1)
                    {
                        throw new RuntimeException(e1.toString());
                    }

                    if ( !addSub)
                    {
                        throw new RuntimeException("没有产品数量");
                    }

                    // 防止溢出
                    if (isSwatchToSail(outBean.getFullId()))
                    {
                        try
                        {
                            checkSwithToSail(outBean.getRefOutFullId());
                        }
                        catch (MYException e)
                        {
                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }
                    
                    //#836 handle attachments
                    List<AttachmentBean> attachmentList = outBean.getAttachmentList();
                    
                    _logger.info("null != attachmentList:"+(null != attachmentList));
                    
                    _logger.debug("outBean.getFullId():"+outBean.getFullId());

                    if(null != attachmentList && attachmentList.size() > 0)
                    {
                    	_logger.info("attachmentList.size():"+ attachmentList.size());
            	        for (AttachmentBean attachmentBean : attachmentList)
            	        {
            	            attachmentBean.setId(commonDAO.getSquenceString20());
            	            attachmentBean.setRefId(outBean.getFullId());
            	            _logger.info("attachmentBean:"+ attachmentBean.toString());
            	        }

            	        attachmentDAO.saveAllEntityBeans(attachmentList);
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }

        _logger.info(user.getStafferName() + "/" + user.getName() + "/ADD:" + outBean);

        return fullId;
    }
    
    /**
     * 增加报废单
     * 
     * @param batchDropBean
     * @return String 销售单的ID
     * @throws Exception
     */
    public String addOut(BatchDropBean batchDropBean, final User user)
        throws MYException
    {
    	final String batchId = batchDropBean.getBatchId();
    	final OutBean outBean = batchDropBean.getOutBean();
    	final List<BaseBean> baseBeans = batchDropBean.getBaseBeanList();
        
        // 0:表示新的批量开单 1:表示单笔（仓库+管理类型+旧货）用于驳回态修改
        final String oprType = "0";

        this.genFullId(outBean, oprType);

        // 批量产品开单时，为saveOutInner准备
        if (oprType.equals("0") && outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        	outBean.setLocation(oprType);
        
        this.sumTotal(outBean, baseBeans);
        
        outBean.setStatus(OutConstant.STATUS_SAVE);

        outBean.setPay(OutConstant.PAY_NOT);

        outBean.setInway(OutConstant.IN_WAY_NO);

        outBean.setCurcredit(0.0d);

        outBean.setStaffcredit(0.0d);

        if (StringTools.isNullOrNone(outBean.getCustomerId())
            || CustomerConstant.PUBLIC_CUSTOMER_ID.equals(outBean.getCustomerId()))
        {
            outBean.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);

            outBean.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);
        }else{
        	
        	if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL){
        		// 增加客户与职员对应关系的校验 
            	StafferVSCustomerBean stafferVSCust = stafferVSCustomerDAO.findByUnique(outBean.getCustomerId());
            	
            	if (null == stafferVSCust){
            		throw new MYException("客户[%s]没有挂靠职员", outBean.getCustomerName());
            	}else{
            		if (!stafferVSCust.getStafferId().equals(outBean.getStafferId()))
            		{
            			throw new MYException("客户[%s]不在职员[%s]名下", outBean.getCustomerName(), outBean.getStafferName());
            		}
            	}
        	}
        }

        if (StringTools.isNullOrNone(outBean.getInvoiceId()))
        {
            outBean.setHasInvoice(OutConstant.HASINVOICE_NO);
        }
        else
        {
            outBean.setHasInvoice(OutConstant.HASINVOICE_YES);
        }

        // 赠送的价格为0
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
        {
            outBean.setTotal(0.0d);
        }

        // 行业属性
        setInvoiceId(outBean);

        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
        {
            outBean.setEventId("");
            outBean.setRefBindOutId("");
            outBean.setPromStatus(-1);
        }
        
        // 增加管理员操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	String dutyId = "";

                    // 组织BaseBean
                    boolean addSub = false;

                    boolean hasZero = false;

                    double total = 0.0d;
                    
                    double taxTotal = 0.0d;

                    List<BaseBean> baseList = new ArrayList<BaseBean>();
                    
                    List<ProductBean> tempProductList = new ArrayList<ProductBean>();
                    
//                    boolean isManagerPass = false;
                    StringBuffer messsb = new StringBuffer();
                    
                    // 处理每个base
                    for (BaseBean base : baseBeans )
                    {

                        base.setId(commonDAO.getSquenceString());

                        if (base.getAmount() == 0)
                        {
                            continue;
                        }

                        base.setOutId(outBean.getFullId());

                        ProductBean product = productDAO.find(base.getProductId());

                        if (product == null)
                        {
                            throw new RuntimeException("产品为空,数据不完备");
                        }

                        //#359
                        if ((outBean.getType() == OutConstant.OUT_TYPE_OUTBILL && outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
                                ||(outBean.getType() == OutConstant.OUT_TYPE_INBILL && outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK)){
                            CustomerBean customerBean = customerMainDAO.find(outBean.getCustomerId());
                            try {
                                ProductImportBean productImportBean = getProductImportBean(outBean, customerBean, base.getProductId());
                                if (productImportBean != null){
                                   base.setGrossProfit(productImportBean.getGrossProfit());
                                   base.setCash(productImportBean.getCash());
                                   base.setCash2(productImportBean.getCash2());
                                }
                            }catch (Exception e){
                                _logger.error(e);
                            }
                        }

                        tempProductList.add(product);

                        // 赠送的价格为0
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                            && outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
                        {
                            base.setPrice(0.0d);
                        }

                        if (base.getPrice() == 0)
                        {
                            hasZero = true;
                        }

                        // 这里需要核对价格 调拨
                        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                            && (outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT || outBean
                                .getOutType() == OutConstant.OUTTYPE_IN_DROP))
                        {
                            if ( !MathTools.equal(base.getPrice(), base.getCostPrice()))
                            {
                                throw new RuntimeException("调拨/报废的时候价格必须相等");
                            }
                        }
                        
                        //base.setMtype(outBean.getMtype());
                        base.setMtype(MathTools.parseInt(product.getReserve4()));

                        double sailPrice = 0.0d;
                        
                        double minPrice = 0.0d;

                        sailPrice = product.getSailPrice();
                        
                        minPrice = sailPrice;

                        baseList.add(base);

                        // 增加单个产品到base表
                        baseDAO.saveEntityBean(base);

                        _logger.info("生成BaseBean:"+base);

                        addSub = true;
                    }                  
                    
                    outBean.setTaxTotal(taxTotal);
                    
                    outBean.setBaseList(baseList);

                    // 保存入库单
                    try
                    {
                        saveOutInner(outBean);
                        _logger.debug("save outBean.getFullId():"+outBean.getFullId());
                    }
                    catch (MYException e1)
                    {
                        throw new RuntimeException(e1.toString());
                    }

                    if ( !addSub)
                    {
                        throw new RuntimeException("没有产品数量");
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }

        _logger.info(user.getStafferName() + "/" + user.getName() + "/ADD:" + outBean);

        return outBean.getFullId();
    }
    
    private void genFullId(OutBean outBean, String oprType) throws MYException{
        // 先保存
        String id = getAll(commonDAO.getSquence());

        /*
        LocationBean location = locationDAO.find(outBean.getLocationId());

        if (location == null)
        {
            _logger.error("区域不存在:" + outBean.getLocationId());

            throw new MYException("区域不存在:" + outBean.getLocationId());
        }
        */

        //String flag = location.getCode();
        String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());
        
        _logger.debug("type:"+outBean.getType()+", outType:"+outBean.getOutType()+", flag:"+flag);
        
        // 临时单号，待拆分为SO单
        if (oprType.equals("0"))
        {
        	flag = "TM";
        }

        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

        String fullId = flag + time + id;

        outBean.setId(getOutId(id));

        outBean.setFullId(fullId);
    }
    
    private void sumTotal(OutBean outBean, List<BaseBean> baseBeans){
    	double total = 0;
    	for(BaseBean bean:baseBeans){
    		total += bean.getValue();
    	}
    	outBean.setTotal(total); 
    }

    private boolean isVirtualProduct(String productId){
        ProductBean productBean = this.productDAO.find(productId);
        if (productBean!= null && productBean.getVirtualFlag() == 1){
            return true;
        }

        return false;
    }

    private void setInvoiceId(final OutBean outBean)
    {
    	// 行业
        StafferBean sb = stafferDAO.find(outBean.getStafferId());

        outBean.setIndustryId(sb.getIndustryId());

        if (StringTools.isNullOrNone(sb.getIndustryId2()))
        {
        	outBean.setIndustryId2(sb.getIndustryId());
        }
        else
        {
        	outBean.setIndustryId2(sb.getIndustryId2());
        }
        
        if (StringTools.isNullOrNone(sb.getIndustryId3()))
        {
        	outBean.setIndustryId3(outBean.getIndustryId2());
        }
        else
        {
        	outBean.setIndustryId3(sb.getIndustryId3());
        }
        
    }

    /**
     * 领样转销售
     */
    public String addSwatchToSail(final User user, final OutBean outBean)
        throws MYException
    {
        if (OutHelper.isManagerSail(outBean))
        {
            throw new MYException("销售-个人领样转销售只能在收藏品下,请重新操作");
        }

        // 先保存
        String id = getAll(commonDAO.getSquence());

        LocationBean location = locationDAO.find(outBean.getLocationId());

        if (location == null)
        {
            _logger.error("区域不存在:" + outBean.getLocationId());

            throw new MYException("区域不存在:" + outBean.getLocationId());
        }

        //String flag = location.getCode();
        String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());

        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

        String fullId = flag + time + id;

        outBean.setId(getOutId(id));

        outBean.setFullId(fullId);

        outBean.setStatus(OutConstant.STATUS_SAVE);

        setInvoiceId(outBean);

        // 增加管理员操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    double total = 0.0d;

                    // 组织BaseBean
                    for (BaseBean base : outBean.getBaseList())
                    {
                        base.setId(commonDAO.getSquenceString());

                        base.setOutId(outBean.getFullId());

                        base.setMtype(outBean.getMtype());
                        
                        // 增加单个产品到base表
                        baseDAO.saveEntityBean(base);

                        total += base.getAmount() * base.getPrice();
                    }

                    outBean.setTotal(total);

//                    outDAO.saveEntityBean(outBean);
                    saveOutWithPodate(outBean);
                    
//                    try
//                    {
                        // 保存入库单
                        //saveOutInner(outBean);
//                    }
//                    catch (MYException e)
//                    {
//                        throw new RuntimeException(e.toString());
//                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e.getMessage());
        }

        return fullId;
    }

    public String coloneOutAndSubmitAffair(final OutBean outBean, final User user, final int type)
        throws MYException
    {
        // LOCK 自动生成入库单
        synchronized (PublicLock.PRODUCT_CORE)
        {
            String result = "";

            try
            {
                // 增加管理员操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);

                result = (String)tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        try
                        {
                            return coloneOutAndSubmitWithOutAffair(outBean, user, type);
                        }
                        catch (MYException e)
                        {
                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }
                });
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e.getMessage(), e);
            }

            return result;
        }
    }

    /**
     * 对外开放(锁和事务由调用的方法保证)
     */
    public String coloneOutAndSubmitWithOutAffair(OutBean outBean, User user, int type)
        throws MYException
    {
        String fullId = coloneOutWithoutAffair(outBean, user, type);
        _logger.info(fullId+"***coloneOutAndSubmitWithOutAffair***"+outBean);
        // 提交(上级已经使用全局锁了)
        this.submitWithOutAffair(fullId, user, type);
        _logger.info("***finish coloneOutAndSubmitWithOutAffair***"+outBean);
        return fullId;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public String coloneOutWithAffair(final OutBean outBean, final User user, int type)
        throws MYException
    {
        String fullId = coloneOutWithoutAffair(outBean, user, type);

        return fullId;
    }
    
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public String coloneOutWithAffair1(final OutBean outBean, final User user, int type)
        throws MYException
    {
        String fullId = coloneOutWithoutAffair1(outBean, user, type);

        return fullId;
    }

    public String coloneOutWithoutAffair(final OutBean outBean, final User user, int type)
        throws MYException
    {
        // 先保存
        String id = getAll(commonDAO.getSquence());

        LocationBean location = locationDAO.find(outBean.getLocationId());

        if (location == null)
        {
            _logger.error("区域不存在:" + outBean.getLocationId());

            throw new MYException("区域不存在:" + outBean.getLocationId());
        }

        // 退货入库的逻辑
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH && false)
        {
            // 查询是否被关联
            ConditionParse con = new ConditionParse();

            con.addWhereStr();

            con.addCondition("OutBean.refOutFullId", "=", outBean.getRefOutFullId());

            con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

            con.addIntCondition("OutBean.status", "=", OutConstant.STATUS_SAVE);

            con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_SWATCH);

            int count = outDAO.countByCondition(con.toString());

            if (count > 0)
            {
                throw new MYException("此领样已经申请退货请处理结束后再申请");
            }
        }

        //String flag = location.getCode();
        String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());

        String time = TimeTools.now("yyMMddHHmm");

        final String fullId = flag + time + id;

        outBean.setId(getOutId(id));

        outBean.setFullId(fullId);

        // 保存库单
        //2015/11/26 入库销售退单直接提交为“待库管处理”状态
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                && (outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH
                || outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
                || outBean.getOutType() == OutConstant.OUTTYPE_IN_PRESENT)){
            outBean.setStatus(OutConstant.BUY_STATUS_SUBMIT);
        } else{
            outBean.setStatus(OutConstant.STATUS_SAVE);
        }


        setInvoiceId(outBean);

        // 保存入库单
        saveOutInner(outBean);

        List<BaseBean> list = outBean.getBaseList();

        for (BaseBean baseBean : list)
        {
            baseBean.setId(commonDAO.getSquenceString());

            baseBean.setOutId(fullId);

            baseBean.setMtype(outBean.getMtype());

            //#511 批量退货时需要设置中收激励金额
            if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                    && outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK){
                BaseBean originalBase = this.getBaseBean(outBean.getRefOutFullId(), baseBean.getProductId(), baseBean.getCostPriceKey());
                if (originalBase!= null){
                    baseBean.setIbMoney(originalBase.getIbMoney());
                    baseBean.setIbMoney2(originalBase.getIbMoney2());
                    baseBean.setMotivationMoney(originalBase.getMotivationMoney());
                    baseBean.setMotivationMoney2(originalBase.getMotivationMoney2());
                    baseBean.setPlatformFee(originalBase.getPlatformFee());
                    //#825
                    baseBean.setGrossProfit(originalBase.getGrossProfit());
                    baseBean.setCash(originalBase.getCash());
                    baseBean.setCash2(originalBase.getCash2());
                    baseBean.setYkibMoney(originalBase.getYkibMoney());
                }
            }

            // 增加单个产品到base表
            baseDAO.saveEntityBean(baseBean);
        }

        return fullId;
    }
    
    public String coloneOutWithoutAffair1(final OutBean outBean, final User user, int type)
    throws MYException
		{
		    // 先保存
		    String id = getAll(commonDAO.getSquence());
		
		    LocationBean location = locationDAO.find(outBean.getLocationId());
		
		    if (location == null)
		    {
		        _logger.error("区域不存在:" + outBean.getLocationId());
		
		        throw new MYException("区域不存在:" + outBean.getLocationId());
		    }
		    // 退货入库的逻辑
		    if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
		        && outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH && false)
		    {
		        // 查询是否被关联
		        ConditionParse con = new ConditionParse();
		
		        con.addWhereStr();
		
		        con.addCondition("OutBean.refOutFullId", "=", outBean.getRefOutFullId());
		
		        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);
		
		        con.addIntCondition("OutBean.status", "=", OutConstant.STATUS_SAVE);
		
		        con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_SWATCH);
		        int count = outDAO.countByCondition(con.toString());
		        if (count > 0)
		        {
		            throw new MYException("此领样已经申请退货请处理结束后再申请");
		        }
		    }
		
		    //String flag = location.getCode();
		    String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());
		
		    String time = TimeTools.now("yyMMddHHmm");
		
		    final String fullId = flag + time + id;
		
		    outBean.setId(getOutId(id));
		
		    outBean.setFullId(fullId);
		
		    // 保存库单
		    outBean.setStatus(OutConstant.STATUS_SAVE);
		
		    setInvoiceId(outBean);
		    // 保存入库单
		    saveOutInner(outBean);
		    
		    List<BaseBean> list = outBean.getBaseList();
		    for (BaseBean baseBean : list)
		    {
		        baseBean.setId(commonDAO.getSquenceString());
		
		        baseBean.setOutId(fullId);
		
		        baseBean.setMtype(outBean.getMtype());
		        // 增加单个产品到base表
		        baseDAO.saveEntityBean(baseBean);
		    }
		    return fullId;
		}

    /**
     * 提交(包括领样退库和销售退库)
     * 
     * @param fullId
     * @param user
     * @param storageType
     * @return
     * @throws Exception
     */
    public int submit(final String fullId, final User user, final int storageType)
        throws MYException
    {
        // LOCK 库存提交(当是入库单的时候是变动库存的)
        synchronized (PublicLock.PRODUCT_CORE)
        {
            Integer result = 0;

            try
            {
                // 增加管理员操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);

                result = (Integer)tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        try
                        {
                            return submitWithOutAffair(fullId, user, storageType);
                        }
                        catch (MYException e)
                        {
                            _logger.error(e, e);

                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }
                });
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e.getMessage());
            }

            return result;
        }
    }

    @Override
    public int submit2(final String fullId, final User user, final int storageType, final List<BaseBean> baseBeans) throws MYException {
        // LOCK 库存提交(当是入库单的时候是变动库存的)
        synchronized (PublicLock.PRODUCT_CORE)
        {
            Integer result = 0;

            try
            {
                // 增加管理员操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);

                result = (Integer)tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        try
                        {
                            return submitWithOutAffair2(fullId, user, storageType, baseBeans);
                        }
                        catch (MYException e)
                        {
                            _logger.error(e, e);

                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }
                });
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e.getMessage());
            }

            return result;
        }
    }

    @Override
    public int submit3(final String fullId, final User user, final int storageType, final List<BaseBean> baseBeans) throws MYException {
        synchronized (PublicLock.PRODUCT_CORE)
        {
            Integer result = 0;

            try
            {
                // 增加管理员操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);

                result = (Integer)tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        try
                        {
                            return submitWithOutAffair3(fullId, user, storageType, baseBeans);
                        }
                        catch (MYException e)
                        {
                            _logger.error(e, e);

                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }
                });
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e.getMessage());
            }

            return result;
        }
    }

    /**
     * 暂时没有对外开放
     */
    private int submitWithOutAffair(final String fullId, final User user, int type)
        throws MYException
    {
        _logger.info("***submitWithOutAffair fullId***"+fullId);
        final OutBean outBean = outDAO.find(fullId);

        if (outBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 检查日志核对
        int outStatusInLog = this.findOutStatusInLog(outBean.getFullId());

        if (outStatusInLog != -1 && outStatusInLog != OutConstant.STATUS_REJECT
            && outStatusInLog != outBean.getStatus())
        {
            String msg = "严重错误,当前单据的状态应该是:" + OutHelper.getStatus(outStatusInLog) + ",而不是"
                         + OutHelper.getStatus(outBean.getStatus()) + ".请联系管理员确认此单的正确状态!";

            loggerError(outBean.getFullId() + ":" + msg);

            throw new MYException(msg);
        }

        final List<BaseBean> baseList = checkSubmit(fullId, outBean);

        // 这里是入库单的直接库存变动(部分)
        processBuyBaseList(user, outBean, baseList, type);

        //add 针对促销订单绑定历史订单，更新被绑定订单的相关信息
        processPromBindOutId(user, outBean);

        // CORE 修改库单(销售/入库)的状态(信用额度处理)
        int status = processOutStutus(fullId, user, outBean);

        try
        {
            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
            {
                outDAO.modifyOutStatus(fullId, status);
            }
        }
        catch (Exception e)
        {
            _logger.error(e, e);
            
            throw new MYException(e);
        }

        // 处理APP 订单关联关系
        if (outBean.getFlowId().startsWith("AP"))
        {
        	AppOutVSOutBean appOut = appOutVSOutDAO.findByUnique(outBean.getFullId());
        	
        	if (null == appOut)
        	{
        		AppOutVSOutBean appOutBean = new AppOutVSOutBean();
        		
        		appOutBean.setOutId(outBean.getFullId());
        		appOutBean.setAppOutId(outBean.getFlowId());
        		
        		appOutVSOutDAO.saveEntityBean(appOutBean);
        	}
        }

        // 处理在途(销售无关)/调入接受时
        int result = processBuyOutInWay(user, fullId, outBean);

        // 在途改变状态
        if (result != -1)
        {
            status = result;
        }
        
        String desc = outBean.getDescription();
        
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL 
        		&& outBean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
        {
        	if (desc.indexOf(OutConstant.SWATCH_COMMENT) == -1)
        	{
        		desc = OutConstant.SWATCH_COMMENT + desc;
        	}
        }
        
        int idx = desc.indexOf("&&");
        
        if (idx == -1)
        {
        	if (!desc.equals(outBean.getDescription())){
        		outDAO.updateDescription(fullId, desc);
        	}
        	
        	// 增加数据库日志
            addOutLog(fullId, user, outBean, "提交", SailConstant.OPR_OUT_PASS, status);
        }
        else
        {
        	String newDesc = desc.substring(0, idx);
        	
        	String logDesc = desc.substring(idx + 2, desc.length());
        	
//        	outBean.setDescription(newDesc);
        	
        	outDAO.updateDescription(fullId, newDesc);
        	
        	if (StringTools.isNullOrNone(logDesc))
        	{
        		logDesc = "提交";
        	}
        	// 增加数据库日志
            addOutLog(fullId, user, outBean, logDesc, SailConstant.OPR_OUT_PASS, status);
        }
        
        outBean.setStatus(status);

        notifyOut(outBean, user, 0);

        return status;
    }

    /**
     * 暂时没有对外开放
     */
    private int submitWithOutAffair2(final String fullId, final User user, int type, List<BaseBean> baseList)
            throws MYException
    {
        final OutBean outBean = outDAO.find(fullId);

        if (outBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 检查日志核对
        int outStatusInLog = this.findOutStatusInLog(outBean.getFullId());
        if (outStatusInLog != -1 && outStatusInLog != OutConstant.STATUS_REJECT
                && outStatusInLog != outBean.getStatus())
        {
            String msg = "严重错误,当前单据的状态应该是:" + OutHelper.getStatus(outStatusInLog) + ",而不是"
                    + OutHelper.getStatus(outBean.getStatus()) + ".请联系管理员确认此单的正确状态!";

            loggerError(outBean.getFullId() + ":" + msg);

            throw new MYException(msg);
        }

        //TODO
//        final List<BaseBean> baseList = checkSubmit(fullId, outBean);
        // 这里是入库单的直接库存变动(部分)
        processBuyBaseList(user, outBean, baseList, type);
        //add 针对促销订单绑定历史订单，更新被绑定订单的相关信息
        processPromBindOutId(user, outBean);
        // CORE 修改库单(销售/入库)的状态(信用额度处理)
        int status = processOutStutus(fullId, user, outBean);
        try
        {
            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
            {
                outDAO.modifyOutStatus(fullId, status);
            }
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            throw new MYException(e);
        }

        // 处理APP 订单关联关系
        if (outBean.getFlowId().startsWith("AP"))
        {
            AppOutVSOutBean appOut = appOutVSOutDAO.findByUnique(outBean.getFullId());

            if (null == appOut)
            {
                AppOutVSOutBean appOutBean = new AppOutVSOutBean();

                appOutBean.setOutId(outBean.getFullId());
                appOutBean.setAppOutId(outBean.getFlowId());

                appOutVSOutDAO.saveEntityBean(appOutBean);
            }
        }

        // 处理在途(销售无关)/调入接受时
        int result = processBuyOutInWay(user, fullId, outBean);

        // 在途改变状态
        if (result != -1)
        {
            status = result;
        }

        String desc = outBean.getDescription();

        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
        {
            if (desc.indexOf(OutConstant.SWATCH_COMMENT) == -1)
            {
                desc = OutConstant.SWATCH_COMMENT + desc;
            }
        }

        int idx = desc.indexOf("&&");

        if (idx == -1)
        {
            if (!desc.equals(outBean.getDescription())){
                outDAO.updateDescription(fullId, desc);
            }

            // 增加数据库日志
            addOutLog(fullId, user, outBean, "提交", SailConstant.OPR_OUT_PASS, status);
        }
        else
        {
            String newDesc = desc.substring(0, idx);

            String logDesc = desc.substring(idx + 2, desc.length());

//        	outBean.setDescription(newDesc);

            outDAO.updateDescription(fullId, newDesc);

            if (StringTools.isNullOrNone(logDesc))
            {
                logDesc = "提交";
            }
            // 增加数据库日志
            addOutLog(fullId, user, outBean, logDesc, SailConstant.OPR_OUT_PASS, status);
        }

        outBean.setStatus(status);

        notifyOut(outBean, user, 0);

        return status;
    }


    /**2015/10/23
     * 入库换货
     * @param fullId
     * @param user
     * @param type
     * @param baseList
     * @return
     * @throws MYException
     */
    private int submitWithOutAffair3(final String fullId, final User user, int type, List<BaseBean> baseList)
            throws MYException
    {
        final OutBean outBean = outDAO.find(fullId);

        if (outBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 检查日志核对
        int outStatusInLog = this.findOutStatusInLog(outBean.getFullId());
        if (outStatusInLog != -1 && outStatusInLog != OutConstant.STATUS_REJECT
                && outStatusInLog != outBean.getStatus())
        {
            String msg = "严重错误,当前单据的状态应该是:" + OutHelper.getStatus(outStatusInLog) + ",而不是"
                    + OutHelper.getStatus(outBean.getStatus()) + ".请联系管理员确认此单的正确状态!";

            loggerError(outBean.getFullId() + ":" + msg);

            throw new MYException(msg);
        }

        // CORE 修改库单(销售/入库)的状态(信用额度处理)
        int status = processOutStutus(fullId, user, outBean);
        // 处理APP 订单关联关系
        if (outBean.getFlowId().startsWith("AP"))
        {
            AppOutVSOutBean appOut = appOutVSOutDAO.findByUnique(outBean.getFullId());

            if (null == appOut)
            {
                AppOutVSOutBean appOutBean = new AppOutVSOutBean();

                appOutBean.setOutId(outBean.getFullId());
                appOutBean.setAppOutId(outBean.getFlowId());

                appOutVSOutDAO.saveEntityBean(appOutBean);
            }
        }

        // 处理在途(销售无关)/调入接受时
        int result = processBuyOutInWay(user, fullId, outBean);

        // 在途改变状态
        if (result != -1)
        {
            status = result;
        }

        String desc = outBean.getDescription();


        int idx = desc.indexOf("&&");

        if (idx == -1)
        {
            if (!desc.equals(outBean.getDescription())){
                outDAO.updateDescription(fullId, desc);
            }

            // 增加数据库日志
            addOutLog(fullId, user, outBean, "提交", SailConstant.OPR_OUT_PASS, status);
        }
        else
        {
            String newDesc = desc.substring(0, idx);

            String logDesc = desc.substring(idx + 2, desc.length());

//        	outBean.setDescription(newDesc);

            outDAO.updateDescription(fullId, newDesc);

            if (StringTools.isNullOrNone(logDesc))
            {
                logDesc = "提交";
            }
            // 增加数据库日志
            addOutLog(fullId, user, outBean, logDesc, SailConstant.OPR_OUT_PASS, status);
        }

        outBean.setStatus(status);

        notifyOut(outBean, user, 0);

        return status;
    }

    /**
     * @param fullId
     * @param outBean
     * @throws MYException
     */
    public int processBuyOutInWay(final User user, final String fullId, final OutBean outBean)
        throws MYException
    {
        _logger.info(fullId+"***processBuyOutInWay**"+outBean);
        int result = -1;

        // 调出直接变动库存 /回滚也是直接变动库存(CORE 这里特殊不产生任何凭证)
        if (OutHelper.isMoveOut(outBean) || OutHelper.isMoveRollBack(outBean))
        {
            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);

            String sequence = commonDAO.getSquenceString();

            for (BaseBean element : baseList)
            {
                ProductChangeWrap wrap = new ProductChangeWrap();

                wrap.setDepotpartId(element.getDepotpartId());
                wrap.setPrice(element.getCostPrice());
                wrap.setVirtualPrice(element.getVirtualPrice());
                wrap.setProductId(element.getProductId());
                if (StringTools.isNullOrNone(element.getOwner()))
                {
                    wrap.setStafferId("0");
                }
                else
                {
                    wrap.setStafferId(element.getOwner());
                }
                wrap.setChange(element.getAmount());
                wrap.setDescription("库单[" + outBean.getFullId() + "]操作");
                wrap.setSerializeId(sequence);

                if (OutHelper.isMoveRollBack(outBean))
                {
                    wrap.setType(StorageConstant.OPR_STORAGE_REDEPLOY_ROLLBACK);
                }
                else
                {
                    wrap.setType(StorageConstant.OPR_STORAGE_REDEPLOY);
                }

                wrap.setRefId(outBean.getFullId());

                storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, false);
            }

            saveUnique(user, outBean);
        }

        // 如果是调入提交
        if (OutHelper.isMoveIn(outBean))
        {
            // 调入的库存(正数增加,负数减少)
            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);

            String sequence = commonDAO.getSquenceString();

            // CORE 调入直接变动库存
            for (BaseBean element : baseList)
            {
                ProductChangeWrap wrap = new ProductChangeWrap();

                wrap.setDepotpartId(element.getDepotpartId());
                wrap.setPrice(element.getCostPrice());
                wrap.setVirtualPrice(element.getVirtualPrice());
                wrap.setProductId(element.getProductId());

                if (StringTools.isNullOrNone(element.getOwner()))
                {
                    wrap.setStafferId("0");
                }
                else
                {
                    wrap.setStafferId(element.getOwner());
                }

//                //#270 2016/7/29 调入取绝对值
//                wrap.setChange(Math.abs(element.getAmount()));
                wrap.setChange(element.getAmount());

                wrap.setDescription("库单[" + outBean.getFullId() + "]调入操作");
                wrap.setSerializeId(sequence);
                wrap.setType(StorageConstant.OPR_STORAGE_REDEPLOY);
                wrap.setRefId(outBean.getFullId());

                storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, true);
            }

            // 调入的存入唯一
            saveUnique(user, outBean);

            // 在途结束
            outDAO.updataInWay(fullId, OutConstant.IN_WAY_OVER);

            outDAO.modifyOutStatus(fullId, OutConstant.STATUS_PASS);

            result = OutConstant.STATUS_PASS;

            // -----------------------------------------------------------------//

            // 处理调出变动库存
            String moveOutFullId = outBean.getRefOutFullId();

            OutBean moveOut = outDAO.find(moveOutFullId);

            if (moveOut!= null){
                // 调出检查
                if (moveOut.getInway() == OutConstant.IN_WAY_OVER)
                {
                    throw new MYException("调出单据已在途结束状态，请确认操作");
                }

                // 结束调出的单据
                changeMoveOutToEnd(user, moveOut, "自动接收");
            }


            // TAX_ADD 入库单-调拨（调入接受时）
            Collection<OutListener> listenerMapValues = listenerMapValues();

            for (OutListener listener : listenerMapValues)
            {
                listener.onConfirmOutOrBuy(user, outBean);
            }
        }

        // 如果是调出(调出提交)
        if (OutHelper.isMoveOut(outBean))
        {
            outDAO.updataInWay(fullId, OutConstant.IN_WAY);

            importLog.info(fullId + "的在途状态改变成在途");
        }

        // 回滚的
        if (OutHelper.isMoveRollBack(outBean))
        {
            outDAO.updataInWay(fullId, OutConstant.IN_WAY_OVER);

            OutBean moveOut = outDAO.find(outBean.getRefOutFullId());

            // 调出驳回检查
            if (OutHelper.isMoveOut(moveOut) && moveOut.getInway() == OutConstant.IN_WAY_OVER)
            {
                throw new MYException("调出单据状态错误，请重新操作");
            }

            // 结束调出的单据
            changeMoveOutToEnd(user, moveOut, "调拨驳回");
        }

        return result;
    }

    /**
     * 结束调出的单据
     * 
     * @param user
     * @param moveOut
     */
    private void changeMoveOutToEnd(final User user, OutBean moveOut, String reason) throws MYException
    {
        outDAO.updataInWay(moveOut.getFullId(), OutConstant.IN_WAY_OVER);

        // 操作日志
        addOutLog(moveOut.getFullId(), user, moveOut, reason, SailConstant.OPR_OUT_PASS, moveOut
            .getStatus());

        importLog.info(moveOut.getFullId() + "的在途状态改变成在途结束");
    }

    /**
     * @param fullId
     * @param user
     * @param outBean
     * @throws MYException
     */
    private int processOutStutus(final String fullId, final User user, final OutBean outBean)
        throws MYException
    {
        int result = 0;

        int nextStatus = OutConstant.STATUS_SUBMIT;

        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        {
            importLog.info(fullId + ":" + user.getStafferName() + ":" + 1 + ":redrectFrom:"
                           + outBean.getStatus());

            // 销售单处理
            try
            {
                // 分公司经理担保
                if (outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER)
                {
                    nextStatus = OutConstant.STATUS_LOCATION_MANAGER_CHECK;
                }

                // 赠送流程 - 2012-09-13 停用
                if (outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT && false)
                {
                    nextStatus = OutConstant.STATUS_CEO_CHECK;
                }

//                outDAO.modifyOutStatus(fullId, nextStatus);
                
                result = nextStatus;

                processCredit(fullId, outBean);
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e);
            }
        }
        else
        {
            nextStatus = OutConstant.BUY_STATUS_PASS;

            // 采购入库直接就是库管通过结束
            if (outBean.getOutType() == OutConstant.OUTTYPE_IN_COMMON)
            {
                nextStatus = OutConstant.BUY_STATUS_PASS;
            }
            // 调拨直接通过
            else if (outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT)
            {
                nextStatus = OutConstant.BUY_STATUS_PASS;
            }
            // 领样直接通过
            else if (outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH)
            {
                nextStatus = OutConstant.BUY_STATUS_PASS;
            }
            // 销售退单直接通过
            else if (outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK || outBean.getOutType() == OutConstant.OUTTYPE_IN_PRESENT)
            {
                nextStatus = OutConstant.BUY_STATUS_PASS;
            }
            // 2015/10/23 换货直接通过
            else if (outBean.getOutType() == OutConstant.OUTTYPE_IN_EXCHANGE)
            {
                nextStatus = OutConstant.BUY_STATUS_SEC_PASS;
            }
            // 其他入库
            else if (outBean.getOutType() == OutConstant.OUTTYPE_IN_OTHER)
            {
                nextStatus = OutConstant.BUY_STATUS_SECOND_PASS;
            }
            // #888 报废
            else if (outBean.getOutType() == OutConstant.OUTTYPE_IN_DROP2)
            {
                //nextStatus = OutConstant.BUY_STATUS_SECOND_PASS;
            	nextStatus = OutConstant.BUY_STATUS_CEO_CHECK;
            }            
            // 其他直接是待分公司经理审核
            else
            {
                nextStatus = OutConstant.STATUS_LOCATION_MANAGER_CHECK;
            }

            try
            {
                outDAO.modifyOutStatus(fullId, nextStatus);

                result = nextStatus;

                //2015/10/23 入库换货，库管通过后生成CK单
                if (outBean.getOutType() == OutConstant.OUTTYPE_IN_EXCHANGE){
                    this.createPackage(outBean);
                }

                //#430 2017/3/4 入库库管通过设置时间
                if (nextStatus == OutConstant.BUY_STATUS_PASS){
                    this.outDAO.updateFlowTime(fullId, TimeTools.now());
                }
            }
            catch (Exception e)
            {
                throw new MYException(e.toString());
            }

            // 入库直接通过
            importLog.info(fullId + ":" + user.getStafferName() + ":" + nextStatus
                           + ":redrectFrom:" + outBean.getStatus());
        }

        return result;
    }

    /**
     * CORE 销售信用处理
     * 
     * @param fullId
     * @param outBean
     * @throws MYException
     */
    private void processCredit(final String fullId, final OutBean outBean)
        throws MYException
    {
        // 只有销售单才有信用(但是个人领样没有客户,就是公共客户)
        boolean isCreditOutOf = false;

        // 这里需要计算客户的信用金额-是否报送物流中心经理审批
        boolean outCredit = parameterDAO.getBoolean(SysConfigConstant.OUT_CREDIT);

        // 取消信用处理
        if ( !outCredit)
        {
            return;
        }

        DutyBean duty = dutyDAO.find(outBean.getCustomerId());

        if (duty != null)
        {
            // 纳税实体作为客户的时候,使用客户的信用等级
            outDAO.updateCurcredit(fullId, outBean.getTotal());

            return;
        }

        CustomerBean cbean = customerMainDAO.find(outBean.getCustomerId());

        if (cbean == null)
        {
            throw new MYException("客户不存在,请确认操作");
        }

        // query customer credit
        CreditLevelBean clevel = creditLevelDAO.find(cbean.getCreditLevelId());

        if (clevel == null)
        {
            throw new MYException("客户信用等级不存在");
        }

        // 进行逻辑处理(必须是货到收款才能有此逻辑) 此逻辑已经废除
        if (outCredit && cbean != null && !StringTools.isNullOrNone(cbean.getCreditLevelId())
            && outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_COMMON)
        {
            throw new MYException("不支持此类型,请重新操作");
        }

        // 使用业务员的信用额度(或者是事业部经理的)
        if (outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_CREDIT_AND_CUR
            || outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER)
        {
            StafferBean sb2 = stafferDAO.find(outBean.getStafferId());

            if (sb2 == null)
            {
                throw new MYException("数据不完备,请重新操作");
            }

            // 先清空预占金额,主要是统计的时候方便
            outDAO.updateCurcredit(fullId, 0.0d);

            outDAO.updateStaffcredit(fullId, 0.0d);

            outDAO.updateManagercredit(fullId, "", 0.0d);

            double noPayBusinessInCur = outDAO.sumNoPayBusiness(outBean.getCustomerId(), YYTools
                .getFinanceBeginDate(), YYTools.getFinanceEndDate());

            // 自己担保的+替人担保的(这里应该区分不同的事业部)
            double noPayBusiness = outDAO.sumAllNoPayAndAvouchBusinessByStafferId(outBean
                .getStafferId(), outBean.getIndustryId(), YYTools.getStatBeginDate(), YYTools
                .getStatEndDate());

            double remainInCur = clevel.getMoney() - noPayBusinessInCur;

            // 不是公共客户
            if ( !cbean.getId().equals(CustomerConstant.PUBLIC_CUSTOMER_ID))
            {
                if (remainInCur < 0)
                {
                    remainInCur = 0.0;
                }

                // 先客户信用 然后职员信用(信用*杠杆) 最后分公司经理
                if (remainInCur >= outBean.getTotal())
                {
                    outDAO.updateCurcredit(fullId, outBean.getTotal());

                    outDAO.updateStaffcredit(fullId, 0.0d);

                    outBean.setReserve6("客户信用最大额度是:" + MathTools.formatNum(clevel.getMoney())
                                        + ".当前客户未付款金额(不包括此单):"
                                        + MathTools.formatNum(noPayBusinessInCur) + ".职员信用额度是:"
                                        + MathTools.formatNum(sb2.getCredit() * sb2.getLever())
                                        + ".职员信用已经使用额度是:" + MathTools.formatNum(noPayBusiness)
                                        + ".信用未超支,不需要事业部经理担保");
                }
            }

            // 职员杠杆后的信用
            double staffCredit = sb2.getCredit() * sb2.getLever();

            // 一半使用客户,一半使用职员的(且不是公共客户的)
            if (remainInCur < outBean.getTotal()
                && !cbean.getId().equals(CustomerConstant.PUBLIC_CUSTOMER_ID))
            {
                // 全部使用客户的信用等级
                outDAO.updateCurcredit(fullId, remainInCur);

                // 当前单据需要使用的职员信用额度
                double remainInStaff = outBean.getTotal() - remainInCur;

                // 防止职员信用等级超支
                if ( (noPayBusiness + remainInStaff) > staffCredit)
                {
                    double lastNeed = (noPayBusiness + remainInStaff) - staffCredit;

                    outBean.setReserve6("客户信用最大额度是:" + MathTools.formatNum(clevel.getMoney())
                                        + ".当前客户未付款金额(不包括此单):"
                                        + MathTools.formatNum(noPayBusinessInCur) + ".职员信用额度是:"
                                        + MathTools.formatNum(staffCredit) + ".职员信用已经使用额度是:"
                                        + MathTools.formatNum(noPayBusiness) + ".信用超支(包括此单):"
                                        + (MathTools.formatNum(lastNeed)));

                    // 这里如果不使用分公司经理直接不允许提交此单据
                    if (outBean.getReserve3() != OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER)
                    {
                        throw new MYException(outBean.getReserve6());
                    }

                    isCreditOutOf = true;

                    outDAO.updateOutReserve(fullId, OutConstant.OUT_CREDIT_OVER, outBean
                        .getReserve6());

                    // 把剩余的信用全部给此单据
                    outDAO.updateStaffcredit(fullId, (staffCredit - noPayBusiness));
                }
                else
                {
                    // 这里完全使用职员的信用
                    outDAO.updateStaffcredit(fullId, remainInStaff);
                }
            }

            // 公共客户信用处理
            if (cbean.getId().equals(CustomerConstant.PUBLIC_CUSTOMER_ID))
            {
                // 当前单据需要使用的职员信用额度
                double remainInStaff = outBean.getTotal();

                // 防止职员信用等级超支
                if ( (noPayBusiness + remainInStaff) > staffCredit)
                {
                    double lastNeed = (noPayBusiness + remainInStaff) - staffCredit;

                    outBean.setReserve6("职员信用额度是:" + MathTools.formatNum(staffCredit)
                                        + ".职员信用已经使用额度是:" + MathTools.formatNum(noPayBusiness)
                                        + ".信用超支(包括此单):" + (MathTools.formatNum(lastNeed)));

                    // 这里如果不使用分公司经理直接不允许提交此单据
                    if (outBean.getReserve3() != OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER)
                    {
                        throw new MYException(outBean.getReserve6());
                    }

                    isCreditOutOf = true;

                    outDAO.updateOutReserve(fullId, OutConstant.OUT_CREDIT_OVER, outBean
                        .getReserve6());

                    // 把剩余的信用全部给此单据
                    outDAO.updateStaffcredit(fullId, (staffCredit - noPayBusiness));
                }
                else
                {
                    // 这里完全使用职员的信用
                    outDAO.updateStaffcredit(fullId, remainInStaff);
                }
            }
        }

        // 信用没有受限检查产品价格是否为0
        if ( !isCreditOutOf)
        {
            outDAO.updateOutReserve(fullId, OutConstant.OUT_CREDIT_COMMON, outBean.getReserve6());
        }

        // 修改人工干预,重新置人工干预信用为0
//        String pid = "90000000000000009999";不做人工干预
//        PromotionBean proBean = promotionDAO.find(outBean.getEventId());
//        if(null != proBean && proBean.getRebateType()==2)
//        {
////        	
//        }
//        else
//        {
//        	creditCoreDAO.updateCurCreToInit(pid, outBean.getCustomerId());
//        }
    }

    /**
     * CORE 处理入库单的库存变动 采购入库/调拨(调出/回滚)/领样退货/销售退单/采购退货
     * 
     * @param user
     * @param outBean
     * @param baseList
     * @throws MYException
     */
    private void processBuyBaseList(final User user, final OutBean outBean,
                                    final List<BaseBean> baseList, int type)
        throws MYException
    {
        _logger.info(outBean+"******processBuyBaseList with out "+baseList);
        // 入库单提交后就直接移动库存了,销售需要在库管通过后生成发货单前才会变动库存
        //未入库退货不扣减库存
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
        {
            return;
        }
        
        if (outBean.getBuyReturnFlag() == 1 && outBean.getBuyReturnType() == 2)
        {
        	//未入库退货，修改totalwarehousenum
            
            for (BaseBean element : baseList)
            {
            	String stockId = outBean.getRefOutFullId();
                String productId = element.getProductId();
                String providerId = outBean.getCustomerId();
                
                outDAO.updateTotalWarehouseNum(stockId, productId, providerId, Math.abs(element.getAmount()));
            }
            
            //更新待结束采购状态
            this.checkAndUpdateStatus(outBean);

            return;
        }

        // 处理入库单的库存变动 采购入库/领样退货/销售退单
        if (outBean.getOutType() == OutConstant.OUTTYPE_IN_COMMON
            || outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH
            || outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
            || outBean.getOutType() == OutConstant.OUTTYPE_IN_PRESENT
            || outBean.getBuyReturnFlag() == 1)
        {
            String sequence = commonDAO.getSquenceString();

            for (BaseBean element : baseList)
            {
                ProductChangeWrap wrap = new ProductChangeWrap();

                //TODO
                wrap.setDepotpartId(element.getDepotpartId());
                wrap.setPrice(element.getCostPrice());
                wrap.setVirtualPrice(element.getVirtualPrice());
                wrap.setProductId(element.getProductId());
                if (StringTools.isNullOrNone(element.getOwner()))
                {
                    wrap.setStafferId("0");
                }
                else
                {
                    wrap.setStafferId(element.getOwner());
                }
                wrap.setChange(element.getAmount());
                wrap.setDescription("库单[" + outBean.getFullId() + "]操作");
                wrap.setSerializeId(sequence);
                wrap.setType(type);
                wrap.setRefId(outBean.getFullId());
                wrap.setInputRate(element.getInputRate());
                
                

                storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, false);
            }
            saveUnique(user, outBean);

            outBean.setBaseList(baseList);

            // 领样退货，要检查领样是否已全部转销售或领样退货了
            if (outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH)
            {
                // 检查是否溢出
                boolean ret = checkIfAllSwithToSail(outBean.getRefOutFullId());
                if (ret)
                {
                 // add 原领样单自动变为已付款
                    String srcFullId = outBean.getRefOutFullId();
                    
                    outDAO.updatePay(srcFullId, OutConstant.PAY_YES);
                }
            }
            
            // TAX_ADD 采购入库/领样退货/销售退单的通过
            Collection<OutListener> listenerMapValues = listenerMapValues();

            for (OutListener listener : listenerMapValues)
            {
                listener.onConfirmOutOrBuy(user, outBean);
                
            }
            _logger.info("processBuyBaseList finished:"+outBean.getFullId());
        }
        _logger.info("******processBuyBaseList exit ");
    }
    
    private void checkAndUpdateStatus(final OutBean outBean)
    {
    	String stockId = outBean.getRefOutFullId();
        int count = outDAO.countPartialFetch(stockId);
        if(count == 0){
            outDAO.updateStockStatus(stockId, "7"); //待结束采购
        }
    }

    /**
     * 变动库存的时候插入唯一的值,保证库存只变动一次(领样转销售也要插入,只是不减少库存)
     * 
     * @param user
     * @param outBean
     */
    private void saveUnique(final User user, final OutBean outBean)
    {
        OutUniqueBean unique = new OutUniqueBean();

        unique.setId(outBean.getFullId());

        String stafferName = "";
        if (user == null){
            stafferName = "系统Job";
        } else{
            unique.setRef(user.getStafferName());
        }

        unique.setLogTime(TimeTools.now());

        outUniqueDAO.saveEntityBean(unique);

        // 更新库存异动时间
        outDAO.updateChangeTime(outBean.getFullId(), TimeTools.now());
    }

    /**
     * 检查submit的准备
     * 
     * @param fullId
     * @param outBean
     * @return
     * @throws MYException
     */
    private List<BaseBean> checkSubmit(final String fullId, final OutBean outBean)
        throws MYException
    {
        if (outBean == null)
        {
            throw new MYException(fullId + " 不存在");
        }

        // 退库-事业部经理审批
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && (outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH 
            	|| outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
            	|| outBean.getOutType() == OutConstant.OUTTYPE_IN_PRESENT))
        {
            if (outBean.getStatus() != OutConstant.BUY_STATUS_SUBMIT)
            {
                throw new MYException(fullId + " 状态错误,不能提交");
            }
        }
        else
        {
            if ( !OutHelper.canSubmit(outBean))
            {
                throw new MYException(fullId + " 状态错误,不能提交");
            }
        }

        final List<BaseBean> baseList = checkCoreStorage(outBean, false);

        // 如果是入库的调入，验证是否在途
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT
            && outBean.getReserve1() == OutConstant.MOVEOUT_OUT)
        {
            OutBean out = outDAO.find(fullId);

            if (out == null)
            {
                throw new MYException("选择调出的库单不存在，请重新操作选择调出的库单");
            }
        }

        // 如果是入库的调入，验证是否在途
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT
            && outBean.getReserve1() == OutConstant.MOVEOUT_IN)
        {
            String ofullid = outBean.getRefOutFullId();

            if (StringTools.isNullOrNone(ofullid))
            {
                throw new MYException("由于是调入的库单需要调出的库单对应，请重新操作选择调出的库单");
            }

            OutBean moveOut = outDAO.find(ofullid);

            if (moveOut == null)
            {
                throw new MYException("选择调出的库单不存在，请重新操作选择调出的库单");
            }

            if (moveOut.getInway() != OutConstant.IN_WAY)
            {
                throw new MYException("选择调出的库单不是在途中，请确认");
            }
        }

        // 验证 销售退库
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK)
        {
            List<OutBean> refBuyList = queryRefOut(outBean.getRefOutFullId());

            // 原单据的base
            List<BaseBean> lastList = OutHelper.trimBaseList2(baseDAO.queryEntityBeansByFK(outBean
                .getRefOutFullId()));

            for (BaseBean baseBean : lastList)
            {
                int hasBack = 0;

                for (OutBean ref : refBuyList)
                {
                    List<BaseBean> refBaseList = OutHelper.trimBaseList2(ref.getBaseList());

                    for (BaseBean refBase : refBaseList)
                    {
                        if (refBase.equals2(baseBean))
                        {
                            hasBack += refBase.getAmount();

                            break;
                        }
                    }
                }

                if (hasBack > baseBean.getAmount())
                {
                    throw new MYException("退货数量溢出，可退数量合计:[%d],当前退货数量(含本单):[%d]", baseBean
                        .getAmount(), hasBack);
                }
            }
        }

        return baseList;
    }

    /**
     * 不带事务操作的信用检查
     * 
     * @param fullId
     * @param outBean
     * @throws MYException
     */
    private void checkCredit(String fullId, OutBean outBean)
    throws MYException
    {
        // 这里需要计算客户的信用金额-是否报送物流中心经理审批
        boolean outCredit = parameterDAO.getBoolean(SysConfigConstant.OUT_CREDIT);

        // 取消信用处理
        if ( !outCredit)
        {
            return;
        }

        DutyBean duty = dutyDAO.find(outBean.getCustomerId());

        if (duty != null)
        {
            return;
        }

        CustomerBean cbean = customerMainDAO.find(outBean.getCustomerId());

        if (cbean == null)
        {
            throw new MYException("客户不存在,请确认操作");
        }

        // query customer credit
        CreditLevelBean clevel = creditLevelDAO.find(cbean.getCreditLevelId());

        if (clevel == null)
        {
            throw new MYException("客户信用等级不存在");
        }

        // 进行逻辑处理(必须是货到收款才能有此逻辑) 此逻辑已经废除
        if (outCredit && cbean != null && !StringTools.isNullOrNone(cbean.getCreditLevelId())
            && outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_COMMON)
        {
            throw new MYException("不支持此类型,请重新操作");
        }

        // 使用业务员的信用额度(或者是事业部经理的)
        if (outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_CREDIT_AND_CUR
            || outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER)
        {
            StafferBean sb2 = stafferDAO.find(outBean.getStafferId());

            if (sb2 == null)
            {
                throw new MYException("数据不完备,请重新操作");
            }

            double noPayBusinessInCur = outDAO.sumNoPayBusiness(outBean.getCustomerId(), YYTools
                .getFinanceBeginDate(), YYTools.getFinanceEndDate());

            // 自己担保的+替人担保的(这里应该区分不同的事业部)
            double noPayBusiness = outDAO.sumAllNoPayAndAvouchBusinessByStafferId(outBean
                .getStafferId(), outBean.getIndustryId(), YYTools.getStatBeginDate(), YYTools
                .getStatEndDate());

            double remainInCur = clevel.getMoney() - noPayBusinessInCur;

            // 不是公共客户
            if ( !cbean.getId().equals(CustomerConstant.PUBLIC_CUSTOMER_ID))
            {
                if (remainInCur < 0)
                {
                    remainInCur = 0.0;
                }

                // 先客户信用 然后职员信用(信用*杠杆) 最后分公司经理
                if (remainInCur >= outBean.getTotal())
                {
                    outBean.setReserve6("客户信用最大额度是:" + MathTools.formatNum(clevel.getMoney())
                                        + ".当前客户未付款金额(不包括此单):"
                                        + MathTools.formatNum(noPayBusinessInCur) + ".职员信用额度是:"
                                        + MathTools.formatNum(sb2.getCredit() * sb2.getLever())
                                        + ".职员信用已经使用额度是:" + MathTools.formatNum(noPayBusiness)
                                        + ".信用未超支,不需要事业部经理担保");
                }
            }

            // 职员杠杆后的信用
            double staffCredit = sb2.getCredit() * sb2.getLever();

            // 一半使用客户,一半使用职员的(且不是公共客户的)
            if (remainInCur < outBean.getTotal()
                && !cbean.getId().equals(CustomerConstant.PUBLIC_CUSTOMER_ID))
            {
                // 当前单据需要使用的职员信用额度
                double remainInStaff = outBean.getTotal() - remainInCur;

                // 防止职员信用等级超支
                if ( (noPayBusiness + remainInStaff) > staffCredit)
                {
                    double lastNeed = (noPayBusiness + remainInStaff) - staffCredit;

                    outBean.setReserve6("客户信用最大额度是:" + MathTools.formatNum(clevel.getMoney())
                                        + ".当前客户未付款金额(不包括此单):"
                                        + MathTools.formatNum(noPayBusinessInCur) + ".职员信用额度是:"
                                        + MathTools.formatNum(staffCredit) + ".职员信用已经使用额度是:"
                                        + MathTools.formatNum(noPayBusiness) + ".信用超支(包括此单):"
                                        + (MathTools.formatNum(lastNeed)));

                    // 这里如果不使用分公司经理直接不允许提交此单据
                    if (outBean.getReserve3() != OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER)
                    {
                        throw new MYException(outBean.getReserve6());
                    }
                }
            }

            // 公共客户信用处理
            if (cbean.getId().equals(CustomerConstant.PUBLIC_CUSTOMER_ID))
            {
                // 当前单据需要使用的职员信用额度
                double remainInStaff = outBean.getTotal();

                // 防止职员信用等级超支
                if ( (noPayBusiness + remainInStaff) > staffCredit)
                {
                    double lastNeed = (noPayBusiness + remainInStaff) - staffCredit;

                    outBean.setReserve6("职员信用额度是:" + MathTools.formatNum(staffCredit)
                                        + ".职员信用已经使用额度是:" + MathTools.formatNum(noPayBusiness)
                                        + ".信用超支(包括此单):" + (MathTools.formatNum(lastNeed)));

                    // 这里如果不使用分公司经理直接不允许提交此单据
                    if (outBean.getReserve3() != OutConstant.OUT_SAIL_TYPE_LOCATION_MANAGER)
                    {
                        throw new MYException(outBean.getReserve6());
                    }
                }
            }
        }
    }
    
    /**
     * 查询REF的入库单(已经通过的)
     * 
     * @param outId
     * @return
     */
    protected List<OutBean> queryRefOut(String outId)
    {
        // 查询当前已经有多少个人领样
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", outId);

        con.addCondition(" and OutBean.status in (3, 4)");

        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

        List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

        for (OutBean outBean : refBuyList)
        {
            List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean.getFullId());

            outBean.setBaseList(list);
        }

        return refBuyList;
    }

    /**
     * 查询所有入库单Ignore status and not contain other in
     * @param outId
     * @return
     */
	protected List<OutBean> queryRefOut1(final String outId, boolean checkStatus)
	{
		// 查询当前已经有多少个人领样
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);
		if (checkStatus) {
            con.addCondition(" and OutBean.status in (3, 4)");
        }

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

		return refBuyList;
	}
	
    /**
     * CORE 检查库存,包括在途的都需要检查
     * 
     * @param outBean
     * @param includeSelf
     *            是否包含自身(当没有提交的时候是false,提交后是true)
     * @return
     * @throws MYException
     */
    private List<BaseBean> checkCoreStorage(final OutBean outBean, boolean includeSelf)
        throws MYException
    {
        //2015/9/24 库存检查去掉此限制
    	// 针对中信银行的库单不作处理
//    	if (outBean.getFlowId().equals(OutImportConstant.CITIC))
//    	{
//    		return null;
//    	}
    	
        final List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

        // 先检查入库
        for (BaseBean element : baseList)
        {
            ProductChangeWrap wrap = new ProductChangeWrap();

            wrap.setDepotpartId(element.getDepotpartId());
            wrap.setPrice(element.getCostPrice());
            wrap.setVirtualPrice(element.getVirtualPrice());
            wrap.setProductId(element.getProductId());
            wrap.setStafferId(element.getOwner());
            wrap.setRefId(outBean.getFullId());

            // 销售单
            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
            {
                // 领样转销售
                if (isSwatchToSail(outBean.getFullId()))
                {
                    try
                    {
                        checkSwithToSail(outBean.getRefOutFullId());
                    }
                    catch (MYException e)
                    {
                        throw new RuntimeException(e.getErrorContent(), e);
                    }
                }
                else
                {
                    wrap.setChange( -element.getAmount());

                    if(!EmptyDepotpartId.equals(outBean.getDepotpartId())){
                    	_logger.debug("outBean.getDepotpartId():"+outBean.getDepotpartId()+", check1");
                    	storageRelationManager.checkStorageRelation(wrap, includeSelf);
                    }
                    
                }
            }
            else
            {
                // 入库单
                wrap.setChange(element.getAmount());

                if(!EmptyDepotpartId.equals(outBean.getDepotpartId())){
                	_logger.debug("outBean.getDepotpartId():"+outBean.getDepotpartId()+", check2");
                	storageRelationManager.checkStorageRelation(wrap, includeSelf);
                }
                
            }
        }

        return baseList;
    }

    /**
     * 处理被绑定参与促销的订单的关联关系
     * @param user
     * @param outBean
     * @throws MYException
     */
    private void processPromBindOutId(User user, OutBean outBean) throws MYException
    {
        // 执行促销活动的销售单 - 是否只针对销售出库类型
        if (outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_EXEC && outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
        {
            String refBindOutId = outBean.getRefBindOutId();
            
            // 存在绑定销售单
            if (!StringTools.isNullOrNone(refBindOutId))
            {
                String [] refs = refBindOutId.split("~");
                
                if (null == refs)
                    return ;
                
                for (String refId : refs)
                {
                    OutBean bean = outDAO.find(refId);
                    
                    if (null == bean)
                    {
                        throw new MYException("数据错误");
                    }
                    
                    if (!StringTools.isNullOrNone(bean.getRefBindOutId()) && bean.getPromStatus() == OutConstant.OUT_PROMSTATUS_BIND)
                    {
                        throw new MYException("此单已被[%s]绑定过，请检查", bean.getRefBindOutId());
                    }
                    
                    outDAO.modifyRefBindOutId(refId, outBean.getFullId(), true);
                    
                }
            }
        }
        
        // 是否只针对销售退库 - 退库时将执行促销的销售单中的refBindOutId 带过来了且含执行促销本身的销售单，绑定的单据以~分隔
        if (outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_BACKBIND && outBean.getType() == OutConstant.OUT_TYPE_INBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK)
        {
                // refOutFullId 可能是执行促销活动的单据，也有可能是参与绑定的销售单                     
                String refBindOutId = outBean.getRefBindOutId();
                
                if (!StringTools.isNullOrNone(refBindOutId))
                {
                    String [] refs = refBindOutId.split("~");
                    
                    if (null == refs)
                        return ;
                    
                    for (String refId : refs)
                    {
                        outDAO.modifyRefBindOutId(refId, "", false);
                    }                                        
                    
                }
                
            }
            
           
        }
    
    /**
     * 驳回(只有销售单和入库单)
     * 
     * @param fullId
     * @param user
     * @return
     * @throws Exception
     */
    public int reject(final String fullId, final User user, final String reason)
        throws MYException
    {
        // LOCK 库存驳回(这里存在库存锁的问题)
        synchronized (PublicLock.PRODUCT_CORE)
        {
            final OutBean outBean = outDAO.find(fullId);

            if (outBean == null)
            {
                throw new MYException("销售单不存在，请重新操作");
            }
            
            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL) {
            	if (outBean.getInvoiceMoney() > 0) {
            		throw new MYException("销售单已开票，请先退票再驳回");
            	} else{
            	    //#356 在批量处理和商务审批及库管审批时做下控制，以PODATE为判断
                    String podate = outBean.getPodate();
                    if (!StringTools.isNullOrNone(podate)){
                        int months = DateTimeUtils.monthsBetweenToday(podate);
                        if (months >= 1){
                            throw new MYException("不允许跨月驳回,银行订单日期:"+podate);
                        }
                    }
                }
            }

            //#177 2016/2/14
            if (outBean.getIbFlag()==1 || outBean.getMotivationFlag()==1){
                throw new MYException("销售单已提交中收或激励申请，无法驳回");
            }

            final List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);

            // 仓库
            final String locationId = outBean.getLocation();

            doReject(fullId, user, reason, outBean, baseList, locationId);

            return OutConstant.STATUS_REJECT;
        }
    }

    /**
     * doReject
     * 
     * @param fullId
     * @param user
     * @param reason
     * @param outBean
     * @param baseList
     * @param deportId
     * @throws MYException
     */
    private void doReject(final String fullId, final User user, final String reason,
                          final OutBean outBean, final List<BaseBean> baseList,
                          final String deportId)
        throws MYException
    {
        checkReject(outBean, baseList, deportId);

        // 入库操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    // TAX_ADD 驳回后坏账取消
                    Collection<OutListener> listenerMapValues = listenerMapValues();

                    for (OutListener listener : listenerMapValues)
                    {
                        try
                        {
                            listener.onReject(user, outBean);
                        }
                        catch (MYException e)
                        {
                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }

                    // 如果销售单，需要删除发货单(当库管驳回的时候才触发)
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                    {
                        ConsignBean beans = consignDAO.findDefaultConsignByFullId(fullId);

                        if (beans != null)
                        {
                            consignDAO.delConsign(fullId);
                        }

                        //如果执行了促销活动的销售单驳回，清除所有促销相关信息
                        if (outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
                        {
                            if (outBean.getPromStatus() == OutConstant.OUT_PROMSTATUS_EXEC)
                            {
                                String refBindOutId = outBean.getRefBindOutId();
                                
                                if (!StringTools.isNullOrNone(refBindOutId))
                                {
                                    String [] refs = refBindOutId.split("~");
                                    
                                    if (null != refs)
                                    {
                                        for (String refId : refs)
                                        {
                                            outDAO.modifyRefBindOutId(refId, "", false);
                                        }  
                                    }
                                    
                                }
                                
                                outDAO.modifyRefBindOutId(outBean.getFullId(), "", false);
                            }
                        }
                        
                        // 取消坏账
                        outDAO.modifyBadDebts(fullId, 0.0d);
                    }
                    
                    //采购退货驳回
                    _logger.debug("outBean.getBuyReturnFlag():"+outBean.getBuyReturnFlag()+", outBean.getBuyReturnType():"+outBean.getBuyReturnType());
                    if(outBean.getBuyReturnFlag() == 1){
                    	
                    	String sequence = commonDAO.getSquenceString();
                    	
                        ConditionParse conditionParse = new ConditionParse();
                        conditionParse.addWhereStr();
                        conditionParse.addCondition("outId","=",outBean.getFullId());
                        List<BaseBean> baseList = baseDAO.queryEntityBeansByCondition(conditionParse);
                        
                        BaseBean element = new BaseBean();
                        if(baseList.size() > 0){
                        	element = baseList.get(0);
                        }
                        
                        //已入库退货
                        if(outBean.getBuyReturnType() == 1){
                        	
                        	_logger.debug("恢复库存。。。。");
                        	
                        	//恢复库存
                            ProductChangeWrap wrap = new ProductChangeWrap();

                            //TODO
                            wrap.setDepotpartId(element.getDepotpartId());
                            wrap.setPrice(element.getCostPrice());
                            wrap.setVirtualPrice(element.getVirtualPrice());
                            wrap.setProductId(element.getProductId());
                            if (StringTools.isNullOrNone(element.getOwner()))
                            {
                                wrap.setStafferId("0");
                            }
                            else
                            {
                                wrap.setStafferId(element.getOwner());
                            }
                            wrap.setChange(0-element.getAmount()); //恢复库存
                            wrap.setDescription("库单[" + outBean.getFullId() + "]操作");
                            wrap.setSerializeId(sequence);
                            wrap.setType(StorageConstant.OPR_STORAGE_REDEPLOY_ROLLBACK);
                            wrap.setRefId(outBean.getFullId());
                            wrap.setInputRate(element.getInputRate());

                            try {
                                StorageLogBean storageLogBean = new StorageLogBean();
                                storageLogBean.setProductId(wrap.getProductId());
                                storageLogBean.setRefId(wrap.getRefId());
                                storageLogBean.setDepotpartId(wrap.getDepotpartId());
                                String priceKey = StorageRelationHelper.getPriceKey(wrap.getPrice());
                                storageLogBean.setPriceKey(priceKey);
                                
                            	//clear T_CENTER_STORAGELOG
                                storageLogDAO.deleteStorageLog(storageLogBean);
                                
    							storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, false);
    							//clear T_CENTER_STORAGELOG
                                storageLogDAO.deleteStorageLog(storageLogBean);
                                
                                //clear OutUnique
                                ConditionParse condition = new ConditionParse();
                                condition.addWhereStr();
                                condition.addCondition("id", "=", wrap.getRefId());
                                outUniqueDAO.deleteEntityBeansByCondition(condition);
    						} catch (MYException e) {
    							// TODO Auto-generated catch block
    							_logger.error("change storage relation error", e);
    						}
                        	
                        	//删除凭证
                            outDAO.clearTicket(outBean.getFullId());                       	
                        }else 
                        //未入库退货
                        if(outBean.getBuyReturnType() == 2){ 
                        	_logger.debug("恢复累计入库数量。。。。");
                        	String stockId = outBean.getRefOutFullId();
                            String productId = element.getProductId();
                            String providerId = outBean.getCustomerId();
                            
                            outDAO.updateTotalWarehouseNum(stockId, productId, providerId, (0-Math.abs(element.getAmount())));
                        }
                    	
                    	
                    }

                    // 如果是调出的驳回需要回滚
                    if (OutHelper.isMoveOut(outBean))
                    {
                        // 这个里面修改了在途状态
                        handlerMoveOutBack(fullId, user, outBean);

                        if (outBean.getCheckStatus() == PublicConstant.CHECK_STATUS_INIT)
                        {
                            outDAO.modifyChecks(outBean.getFullId(), "调拨回滚后原单据还未核对,系统自动核对原调拨单");
                        }

                        // 直接结束
                        outDAO.modifyOutStatus(outBean.getFullId(), OutConstant.STATUS_SEC_PASS);

                        // 操作日志
                        addOutLog(fullId, user, outBean, reason, SailConstant.OPR_OUT_REJECT,
                            OutConstant.STATUS_SEC_PASS);
                    }
                    else
                    {
                        importLog.info(fullId + ":" + user.getStafferName() + ":"
                                       + OutConstant.STATUS_REJECT + ":redrectFrom:"
                                       + outBean.getStatus());

                        outDAO.modifyOutStatus(outBean.getFullId(), OutConstant.STATUS_REJECT);

                        // 驳回修改在途方式
                        outDAO.updataInWay(fullId, OutConstant.IN_WAY_NO);

                        // 变成没有付款
                        outDAO.updatePay(fullId, OutConstant.PAY_NOT);

                        // 操作日志
                        addOutLog(fullId, user, outBean, reason, SailConstant.OPR_OUT_REJECT,
                            OutConstant.STATUS_REJECT);
                    }

                    notifyOut(outBean, user, 1);

                    return Boolean.TRUE;
                }

            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);

            throw new MYException(e.toString());
        }
    }

    /**
     * 处理驳回的验证(驳回不涉及库存的移动)
     * 
     * @param outBean
     * @param baseList
     * @param deportId
     * @throws MYException
     */
    private void checkReject(final OutBean outBean, final List<BaseBean> baseList,
                             final String deportId)
        throws MYException
    {
        if (outBean == null)
        {
            throw new MYException("销售单不存在，请重新操作");
        } else if (outBean.getProfigFlag() == 1){
            throw new MYException("可支配毛利已支付，不可驳回");
        }
//        else if (outBean.getCashFlag() == 1){
//            throw new MYException("单品奖励已支付，不可驳回");
//        }

        if ( !OutHelper.canReject(outBean))
        {
            throw new MYException("状态不可以驳回!");
        }

        // 调出驳回检查
        if (OutHelper.isMoveOut(outBean) && outBean.getInway() == OutConstant.IN_WAY_OVER)
        {
            throw new MYException("状态错误，请重新操作");
        }
    }

    @Override
    public int rejectWithZs(String fullId, int status, User user, String reason) throws MYException {
        _logger.info("****fullId***"+fullId+"***status****"+status);
        int result = this.reject(fullId, user,  reason);
        //#593
        List<OutBean> outBeans = this.getZsOrders(1, fullId,status, true);
        if (!ListTools.isEmptyOrNull(outBeans)){
            _logger.info("****ZS orders need to auto reject****"+outBeans.size());
            for (OutBean o: outBeans){
                String zsFullId = o.getFullId();
                result = this.reject(zsFullId, user, reason);
                _logger.info(zsFullId+"*****auto reject ZS****");
            }
        } else{
            _logger.info("****ZS orders need to auto reject****"+outBeans.size());
        }

        return result;
    }

    /**
     * CORE 审核通过(这里只有销售单/入库单才有此操作)分公司经理审核/结算中心/物流审批/库管发货
     * 
     * @param fullId
     * @param user
     * @param depotpartId
     *            废弃
     * @return
     * @throws Exception
     */
    public int pass(final String fullId, final User user, final int nextStatus,
                    final String reason, final String customerDescription, final String depotpartId)
        throws MYException
    {
        final OutBean outBean = outDAO.find(fullId);

        _logger.info(outBean+"***pass to nextStatus"+nextStatus);
        checkPass(outBean);
        final int oldStatus = outBean.getStatus();

        final DepotBean depot = checkDepotInPass(nextStatus, outBean);
        // LOCK 销售单/入库单通过(这里是销售单库存变动的核心)
        synchronized (PublicLock.PRODUCT_CORE)
        {
            // 入库操作在数据库事务中完成
            TransactionTemplate tran = new TransactionTemplate(transactionManager);
            try
            {
                tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        int newNextStatus = nextStatus;

                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                        {
                            // 直接把结算中心通过的设置成物流管理员通过(直接跳过物流)
                            if (newNextStatus == OutConstant.STATUS_MANAGER_PASS)
                            {
                                newNextStatus = OutConstant.STATUS_FLOW_PASS;
                            }
                            
                            // 针对通用产品，结算中心通过后直接为待回款，跳过库管审批
                            if (oldStatus == OutConstant.STATUS_SUBMIT)
                            {
                            	outDAO.modifyManagerTime(outBean.getFullId(), TimeTools.now());
                            	
                            	// 领样/巡展转销售
                            	boolean isSTS = isSwatchToSail(outBean.getFullId());
                            	
                            	if (isSTS){
                            		newNextStatus = OutConstant.STATUS_PASS;
                            	}else{
                            		List<BaseBean> list = baseDAO.queryEntityBeansByFK(fullId);
                                	
                                	if (list.get(0).getProductId().equals(ProductConstant.OUT_COMMON_PRODUCTID)
                                			|| list.get(0).getProductId().equals(ProductConstant.OUT_COMMON_MPRODUCTID))
                                	{
                                		newNextStatus = OutConstant.STATUS_PASS;
                                	}
                                	
                                	// 中信订单 - 商务审批 - 直接到库管审批
                                	if (outBean.getFlowId().equals("CITIC") 
                                			|| outBean.getFlowId().equals("ZJRC"))
                                	{
                                		newNextStatus = OutConstant.STATUS_FLOW_PASS;
                                		
                                		// 针对中信银行接口产生的OA库单要先拆分行项目 。先检查，若足够库存则拆分行项目.拆分后订单将占有库存
                                		// 增加 只对成本为0的数据进行操作
                                		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());
                                		
                                		// 成本为0的表示还未拆分到具体的库存批次
                                		if (baseList.get(0).getCostPrice() == 0)
                                		{
                                			List<BaseBean> newBaseList = null;
    										try
    										{
    											newBaseList = splitBase(baseList);
    										}
    										catch (MYException e)
    										{
    											throw new RuntimeException(e.getErrorContent(), e);
    										}
                                        	
                                        	baseDAO.deleteEntityBeansByFK(outBean.getFullId());
                                        	
                                        	baseDAO.saveAllEntityBeans(newBaseList);
                                        	
                                        	// 同步更新未拆分到具体批次前就开票的明细（规定：这样销售单须一次性开完发票）
                                        	outBean.setForceBuyType(999);
                                		}
                                	}
                            	}
                            }
                        }
                        else
                        {
                            if (outBean.getOutType() == OutConstant.OUTTYPE_OUT_APPLY)
                            	newNextStatus = OutConstant.BUY_STATUS_PASS;
                        }

                        String stafferName = "票随货发Job";
                        if (user != null){
                            stafferName = user.getStafferName();
                        }

                        _logger.info(outBean.getFullId() + ":" + stafferName + ":"
                                + newNextStatus + ":redirectFrom:" + oldStatus);


                        // 修改状态
                        outDAO.modifyOutStatus(outBean.getFullId(), newNextStatus);

                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                        {
                            handerPassOut(fullId, user, outBean, depot, newNextStatus);
                        }
                        else
                        {
                        	if (outBean.getOutType() != OutConstant.OUTTYPE_OUT_APPLY)
                        		handerPassBuy(fullId, user, outBean, newNextStatus);
                        }

                        //#162 2016/2/4 通过库管审批时,空发检查
                        boolean isKf = false;
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                                && oldStatus == OutConstant.STATUS_FLOW_PASS
                                && newNextStatus == OutConstant.STATUS_PASS){
                            List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(outBean.getFullId());
                            if (!ListTools.isEmptyOrNull(distList))
                            {
                                DistributionBean distributionBean = distList.get(0);
                                if (distributionBean!= null && distributionBean.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING){
                                    _logger.info(outBean.getFullId()+"***isKf***");
                                    isKf = true;
                                }
                            }

                            //#169 票随货发的发票需要生成CK单
                            createPackageForInvoice(outBean);
                        }

                        if (isKf){
                            addOutLog(fullId, user, outBean, reason, SailConstant.OPR_OUT_PASS,
                                    OutConstant.STATUS_SEC_PASS);
                        } else {
                            addOutLog(fullId, user, outBean, reason, SailConstant.OPR_OUT_PASS,
                                    newNextStatus);
                        }


                        // 把状态放到最新的out里面
                        outBean.setStatus(newNextStatus);

                        // OSGI 监听实现
                        Collection<OutListener> listenerMapValues = listenerMapValues();

                        for (OutListener listener : listenerMapValues)
                        {
                            try
                            {
                                listener.onPass(user, outBean);
                            }
                            catch (MYException e)
                            {
                                throw new RuntimeException(e.getErrorContent(), e);
                            }
                        }

                        notifyOut(outBean, user, 0);

                        //#171 3.商务审批通过时，原因如为 库存不足、待产品加工的，则将原因清空
                        // 4.库管审批通过时，将原因清空
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                                && !StringTools.isNullOrNone(outBean.getReason())){
                            if ((newNextStatus == OutConstant.STATUS_FLOW_PASS &&
                                    (("库存不足").equals(outBean.getReason()) || "待产品加工".equals(outBean.getReason())))
                            || newNextStatus == OutConstant.STATUS_PASS){
                                outDAO.modifyReason(outBean.getFullId(), "");
                                _logger.info(outBean.getFullId() + "清除reason字段:" + outBean.getReason());
                            }
                        }

                        //#315
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL && newNextStatus == OutConstant.STATUS_FLOW_PASS){
                            _logger.info(outBean.getFullId()+"更新swbz字段:"+reason);
                            outDAO.updateSwbz(outBean.getFullId(), reason);
                        }

                        //#352 中信批量商务审批时更新"客户备注"字段
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL && newNextStatus == OutConstant.STATUS_FLOW_PASS
                                && !StringTools.isNullOrNone(customerDescription)){
                            _logger.info(outBean.getFullId()+"update customer description:"+customerDescription);
                            outDAO.updateCustomerDescription(outBean.getFullId(), customerDescription);
                        }

                        // #162 发货方式为“空发”的订单，在通过库管审批时，状态更新为“已发货”
                        // 原处理逻辑不变，修改状态放到最后进行
                        if (isKf){
                            newNextStatus = OutConstant.STATUS_SEC_PASS;
                            // 修改状态
                            outBean.setStatus(newNextStatus);
                            outDAO.modifyOutStatus(outBean.getFullId(), newNextStatus);
                            _logger.info(outBean.getFullId()+"空发订单库管审批后更新为已发货状态");
                        }

                        //#430 2017/3/4 出库库管审批通过时设置时间
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                                && oldStatus == OutConstant.STATUS_FLOW_PASS
                                && newNextStatus == OutConstant.STATUS_PASS){
                            outDAO.updateFlowTime(outBean.getFullId(), TimeTools.now());
                        }

                        return Boolean.TRUE;
                    }
                });
            }
            catch (TransactionException e)
            {
                _logger.error(e, e);
                throw new MYException("数据库内部错误");
            }
            catch (DataAccessException e)
            {
                _logger.error(e, e);
                throw new MYException("数据库内部错误");
            }
            catch (Exception e)
            {
                _logger.error(e, e);
                throw new MYException("处理异常:" + e.getMessage());
            }
        }

        // 更新后的状态
        return outBean.getStatus();

    }
    
    /**
     * CORE 采购退货审核通过
     * 
     * @param fullId
     * @param user
     * @param depotpartId
     *            废弃
     * @return
     * @throws Exception
     */
    public int passBuyReturn(final String fullId, final User user, final int nextStatus,
                    final String reason, final String customerDescription, final String depotpartId)
        throws MYException
    {
        final OutBean outBean = outDAO.find(fullId);

        _logger.info(outBean+"***passBuyReturn to nextStatus"+nextStatus);
        checkPass(outBean);
        final int oldStatus = outBean.getStatus();

        synchronized (PublicLock.PRODUCT_CORE)
        {
            // 入库操作在数据库事务中完成
            TransactionTemplate tran = new TransactionTemplate(transactionManager);
            try
            {
                tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        int newNextStatus = nextStatus;

                        String stafferName = "票随货发Job";
                        if (user != null){
                            stafferName = user.getStafferName();
                        }

                        _logger.debug("before "+outBean.getFullId() + ":" + stafferName + ":"
                                + newNextStatus + ":redirectFrom:" + oldStatus);


                        // 修改状态
                        outDAO.modifyOutStatus(outBean.getFullId(), newNextStatus);

                        handerPassBuy(fullId, user, outBean, newNextStatus);
                        
                        addOutLog(fullId, user, outBean, reason, SailConstant.OPR_OUT_PASS,
                                newNextStatus);


                        // 把状态放到最新的out里面
                        outBean.setStatus(newNextStatus);
                        
                        _logger.debug("after "+outBean.getFullId() + ":" + stafferName + ", newNextStatus:"
                                + newNextStatus + ", oldStatus:" + oldStatus);

                        // OSGI 监听实现
                        Collection<OutListener> listenerMapValues = listenerMapValues();

                        for (OutListener listener : listenerMapValues)
                        {
                            try
                            {
                                listener.onPass(user, outBean);
                            }
                            catch (MYException e)
                            {
                                throw new RuntimeException(e.getErrorContent(), e);
                            }
                        }

                        notifyOut(outBean, user, 0);

                        return Boolean.TRUE;
                    }
                });
            }
            catch (TransactionException e)
            {
                _logger.error(e, e);
                throw new MYException("数据库内部错误");
            }
            catch (DataAccessException e)
            {
                _logger.error(e, e);
                throw new MYException("数据库内部错误");
            }
            catch (Exception e)
            {
                _logger.error(e, e);
                throw new MYException("处理异常:" + e.getMessage());
            }
        }

        // 更新后的状态
        return outBean.getStatus();

    }

    /**
     * checkDepotInPass
     * 
     * @param nextStatus
     * @param outBean
     * @return
     * @throws MYException
     */
    private DepotBean checkDepotInPass(final int nextStatus, final OutBean outBean)
        throws MYException
    {
        final DepotBean depot = depotDAO.find(outBean.getLocation());

        if (depot == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 需要发货单先通过(只有中心仓库才有物流哦)
        if (nextStatus == OutConstant.STATUS_FLOW_PASS
            && depot.getType() == DepotConstant.DEPOT_TYPE_CENTER && false)
        {
            ConsignBean consignBean = consignDAO.findDefaultConsignByFullId(outBean.getFullId());

            if (consignBean == null)
            {
                throw new MYException("没有发货单,请重新操作!");
            }

            if (consignBean.getCurrentStatus() == SailConstant.CONSIGN_INIT)
            {
            	if (!outBean.getFlowId().equals(OutImportConstant.CITIC))
            		throw new MYException("发货单没有审批通过，请先处理发货单");
            }
        }
        return depot;
    }

    /**
     * checkPass
     * 
     * @param outBean
     * @throws MYException
     */
    private void checkPass(final OutBean outBean)
        throws MYException
    {
        if (outBean == null)
        {
            throw new MYException("销售单不存在，请重新操作");
        }

        final int oldStatus = outBean.getStatus();


        // 检查pass的条件
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && (outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH 
            	|| outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
            	|| outBean.getOutType() == OutConstant.OUTTYPE_IN_PRESENT))
        {
            if (outBean.getStatus() != OutConstant.STATUS_SAVE)
            {
                throw new MYException("状态不可以通过!");
            }
        }
        else
        {
            if (outBean.getStatus() == OutConstant.STATUS_SAVE
                || outBean.getStatus() == OutConstant.STATUS_REJECT)
            {
                throw new MYException("状态不可以通过!");
            }
            
            // 增加对导入订单的控制. 第三方快递，地址为0或空， 收货人为0或空的，不给通过
            List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(outBean.getFullId());
            
            if (!ListTools.isEmptyOrNull(distList))
            {
            	DistributionBean distBean = distList.get(0);
            	
            	if (distBean.getShipping() == OutConstant.OUT_SHIPPING_3PL
            			&& (StringTools.isNullOrNone(distBean.getAddress()) || distBean.getAddress().trim().equals("0")) 
            					&& (StringTools.isNullOrNone(distBean.getReceiver()) || distBean.getReceiver().trim().equals("0")))
            	{
            		throw new MYException("请完善配送信息后再审批!");
            	}
            }
        }

        // 检查日志核对
        int outStatusInLog = this.findOutStatusInLog(outBean.getFullId());

        if (outStatusInLog != -1 && outStatusInLog != oldStatus)
        {
            String msg = "严重错误,当前单据的状态应该是:" + OutHelper.getStatus(outStatusInLog) + ",而不是"
                         + OutHelper.getStatus(oldStatus) + ".请联系管理员确认此单的正确状态!";

            _logger.error(outBean.getFullId()+ ":" + msg);
            loggerError(outBean.getFullId() + ":" + msg);

            throw new MYException(msg);
        }
    }

    /**
     * CORE 销售单/入库单变动库存(这里是普通销售单和普通入库单库存通过的核心操作)
     * 
     * @param user
     * @param outBean
     * @param baseList
     * @param type
     * @throws MYException
     */
    private void processBuyAndSailPass(final User user, final OutBean outBean,
                                       final List<BaseBean> baseList, int type)
        throws MYException
    {
        // 领样转销售时,库存无变动(优先处理)
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
            && isSwatchToSail(outBean.getFullId()))
        {
            // 检查是否溢出
            boolean ret = checkIfAllSwithToSail(outBean.getRefOutFullId());

            if (ret)
            {
             // add 原领样单自动变为已付款
                String srcFullId = outBean.getRefOutFullId();
                
                outDAO.updatePay(srcFullId, OutConstant.PAY_YES);
            }
            
            // CORE 领样转销售通过的时候,生成的对冲单据
            createBuyDuichong(user, outBean, baseList);

            saveUnique(user, outBean);
            
            outBean.setBaseList(baseList);

            // TAX_ADD 领样转销售
            Collection<OutListener> listenerMapValues = listenerMapValues();

            for (OutListener listener : listenerMapValues)
            {
                listener.onConfirmOutOrBuy(user, outBean);
            }

            // 直接返回
            return;
        }

        String sequence = commonDAO.getSquenceString();

        for (BaseBean element : baseList)
        {
            ProductChangeWrap wrap = new ProductChangeWrap();

            wrap.setDepotpartId(element.getDepotpartId());
            wrap.setPrice(element.getCostPrice());
            wrap.setVirtualPrice(element.getVirtualPrice());
            wrap.setProductId(element.getProductId());
            if (StringTools.isNullOrNone(element.getOwner()))
            {
                wrap.setStafferId("0");
            }
            else
            {
                wrap.setStafferId(element.getOwner());
            }
            
            // 通用商品销售设为 私卖
            if ((element.getProductId().equals(ProductConstant.OUT_COMMON_PRODUCTID) || element.getProductId().equals(ProductConstant.OUT_COMMON_MPRODUCTID))
            		&& outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
            {
            	wrap.setStafferId(outBean.getStafferId());
            }
            
            wrap.setRefId(outBean.getFullId());

            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
            {
                // 这里是销售单,所以是负数
                wrap.setChange( -element.getAmount());

                wrap.setDescription("销售单[" + outBean.getFullId() + "]库管通过操作");
            }
            else
            {
                // 这里是入库单
                wrap.setChange(element.getAmount());

                wrap.setDescription("入库单[" + outBean.getFullId() + "]库管通过操作");
            }

            wrap.setSerializeId(sequence);

            wrap.setType(type);

            storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, false);
        }
        
        outBean.setBaseList(baseList);

        // TAX_ADD 销售单和入库单通过(最常见的)
        Collection<OutListener> listenerMapValues = listenerMapValues();

        for (OutListener listener : listenerMapValues)
        {
            listener.onConfirmOutOrBuy(user, outBean);
        }

        saveUnique(user, outBean);
    }

    /**
     * CORE 生成对称的B单据(核心逻辑,当前已经废除)<br>
     * 1.收藏品 销售 经纬公司 P产品 B单据<br>
     * 2.经纬公司 入库 P产品 C单据
     * 
     * @param outBean
     * @param baseList
     * @throws MYException
     */
    protected void createRealDuichong(User user, final OutBean outBean,
                                      final List<BaseBean> baseList)
        throws MYException
    {
        // 1.收藏品 销售 经纬公司 P产品 B单据
        OutBean newOutBean = new OutBean();

        // 拷贝基本属性
        BeanUtil.copyProperties(newOutBean, outBean);

        newOutBean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);

        String productId = baseList.get(0).getProductId();

        ProductBean product = productDAO.find(productId);

        if (product.getType() == ProductConstant.PRODUCT_TYPE_NUMISMATICS)
        {
            // 旧货专用发票
            newOutBean.setInvoiceId(InvoiceConstant.INVOICE_INSTACE_NDK_2);
        }
        else
        {
            // 可抵扣增值票
            newOutBean.setInvoiceId(InvoiceConstant.INVOICE_INSTACE_DK_17);
        }

        newOutBean.setCustomerId(outBean.getDutyId());

        DutyBean duyu = dutyDAO.find(outBean.getDutyId());

        newOutBean.setCustomerName(duyu.getName());

        // 默认都是销售出库
        newOutBean.setOutType(OutConstant.OUTTYPE_OUT_COMMON);

        newOutBean.setStatus(OutConstant.STATUS_PASS);

        newOutBean.setDescription("规则生成的销售单据");

        newOutBean.setChecks("");

        newOutBean.setHadPay(0.0d);

        newOutBean.setPay(OutConstant.PAY_NOT);

        newOutBean.setVtype(OutConstant.VTYPE_SPECIAL);

        // 表明是对冲的单据
        newOutBean.setReserve8("1");

        newOutBean.setCurcredit(0);

        newOutBean.setStaffcredit(0);

        newOutBean.setManagercredit(0);

        newOutBean.setVtypeFullId(outBean.getFullId());

        String newFullId = wrapOutId(outBean, newOutBean);

        List<BaseBean> newBaseList = new ArrayList<BaseBean>();

        double newTotal = 0.0d;

        for (BaseBean baseBean : baseList)
        {
            BaseBean newEach = new BaseBean();

            BeanUtil.copyProperties(newEach, baseBean);

            newEach.setId(commonDAO.getSquenceString());

            newEach.setOutId(newFullId);

            double d1 = newEach.getPrice() * 0.05;

            double d2 = newEach.getPrice() - newEach.getCostPrice();

            // 含税价/1.03
            newEach.setInputPrice(newEach.getPrice() / 1.03d);

            // （含税价*5%） >（含税价-成本价）
            if (MathTools.compare(d1, d2) >= 0)
            {
                // 含税价等于A单含税价*95%
                newEach.setPrice(newEach.getPrice() * 0.95d);

                // 计算总价
                newEach.setValue(newEach.getPrice() * newEach.getAmount());

            }
            else
            {
                // 含税价取原单的含税价的值
                newEach.setPrice(newEach.getPrice());
            }

            // 计算新的总额
            newTotal += newEach.getValue();

            newBaseList.add(newEach);
        }

        baseDAO.saveAllEntityBeans(newBaseList);

        newOutBean.setTotal(newTotal);

        // 信用这里是纳税实体全部承担
        newOutBean.setCurcredit(newTotal);

        newOutBean.setBaseList(newBaseList);

        saveOutInner(newOutBean);

        setInvoiceId(newOutBean);

        // 更新关联
        outDAO.updateVtypeFullId(outBean.getFullId(), newFullId);

        saveUnique(user, newOutBean);

        // 增加日志
        addOutLog(newFullId, user, newOutBean, "提交", SailConstant.OPR_OUT_PASS,
            OutConstant.BUY_STATUS_PASS);

        // TAX_ADD B单对冲生成的单据
        Collection<OutListener> listenerMapValues = listenerMapValues();

        for (OutListener listener : listenerMapValues)
        {
            listener.onConfirmOutOrBuy(user, newOutBean);
        }

        // 2.经纬公司 入库 P产品 C单据
        createBuyDuichong2(user, outBean, baseList);
    }

    /**
     * 2.经纬公司 入库 P产品 C单据
     * 
     * @param user
     * @param outBean
     * @param baseList
     * @throws MYException
     */
    private void createBuyDuichong2(User user, final OutBean outBean, final List<BaseBean> baseList)
        throws MYException
    {
        OutBean newInBean = new OutBean();

        BeanUtil.copyProperties(newInBean, outBean);

        final String fullId = wrapOutId(outBean, newInBean);

        newInBean.setType(OutConstant.OUT_TYPE_INBILL);

        newInBean.setOutType(OutConstant.OUTTYPE_IN_OTHER);

        newInBean.setStatus(OutConstant.BUY_STATUS_PASS);

        newInBean.setCustomerId(outBean.getDutyId());

        DutyBean duyu = dutyDAO.find(outBean.getDutyId());

        newInBean.setCustomerName(duyu.getName());

        newInBean.setVtype(OutConstant.VTYPE_SPECIAL);

        newInBean.setVtypeFullId(outBean.getFullId());

        newInBean.setChecks("");

        newInBean.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);

        newInBean.setDescription("规则生成的入库单据(自动生成的)");

        List<BaseBean> newBaseList = new ArrayList();

        double newTotal = 0.0d;

        // 这里的单据应该和原始单据的一致，因为要相互抵消
        for (BaseBean element : baseList)
        {
            BaseBean newEach = new BaseBean();

            BeanUtil.copyProperties(newEach, element);

            newEach.setOutId(fullId);

            newTotal += newEach.getValue();

            newBaseList.add(newEach);
        }

        newInBean.setTotal(newTotal);

        newInBean.setBaseList(newBaseList);

        setInvoiceId(newInBean);

        // 保存入库单
        saveOutInner(newInBean);

        for (BaseBean baseBean : newBaseList)
        {
            baseBean.setId(commonDAO.getSquenceString());

            baseBean.setOutId(fullId);

            baseBean.setMtype(newInBean.getMtype());

            // 增加单个产品到base表
            baseDAO.saveEntityBean(baseBean);
        }

        // 直接通过
        newInBean.setStatus(3);

        addOutLog(fullId, user, newInBean, "提交", SailConstant.OPR_OUT_PASS,
            OutConstant.BUY_STATUS_PASS);

        saveUnique(user, newInBean);

        // OSGI 监听实现(对冲单据,有入库的过程)
        Collection<OutListener> listenerMapValues = listenerMapValues();

        for (OutListener listener : listenerMapValues)
        {
            listener.onConfirmOutOrBuy(user, newInBean);
        }
    }

    /**
     * 领样转销售通过的时候,生成的对冲单据
     * 
     * @param outBean
     * @param baseList
     * @throws MYException
     */
    private void createBuyDuichong(User user, final OutBean outBean, final List<BaseBean> baseList)
        throws MYException
    {
        OutBean newInBean = new OutBean();

        BeanUtil.copyProperties(newInBean, outBean);

        final String fullId = wrapOutId(outBean, newInBean);

        newInBean.setType(OutConstant.OUT_TYPE_INBILL);

        newInBean.setOutType(OutConstant.OUTTYPE_IN_OTHER);

        newInBean.setStatus(OutConstant.BUY_STATUS_PASS);

        // 表明是对冲的单据
        newInBean.setReserve8("1");

        // 对冲单据
        newInBean.setRefOutFullId(outBean.getRefOutFullId());

        newInBean.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);

        newInBean.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);

        newInBean.setChecks("");

        newInBean.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);

        newInBean.setDescription("领样转销售[" + outBean.getFullId() + "]的库管通过的时候自动产生一张财务做帐的领样退库单,"
                + "此单据系统自动生成，直接到待核对状态(此单不会对库存产生任何异动)");

        String lingyangId = outBean.getRefOutFullId();

        List<BaseBean> lyBaseList = baseDAO.queryEntityBeansByFK(lingyangId);

        List<BaseBean> newBaseList = new ArrayList();

        double newTotal = 0.0d;

        for (BaseBean element : baseList)
        {
            BaseBean newEach = new BaseBean();

            BeanUtil.copyProperties(newEach, element);

            newEach.setOutId(fullId);

            // 价格是领样的价格哦
            BaseBean findBase = OutHelper.findBase(lyBaseList, newEach);

            if (findBase == null)
            {
                throw new MYException("找不到对应的领样原始单据,请确认操作");
            }

            newEach.setPrice(findBase.getPrice());

            newEach.setValue(newEach.getAmount() * findBase.getPrice());

            newTotal += newEach.getValue();

            newBaseList.add(newEach);
        }

        newInBean.setTotal(newTotal);

        newInBean.setBaseList(newBaseList);

        setInvoiceId(newInBean);

        // 保存入库单
        saveOutInner(newInBean);

        for (BaseBean baseBean : newBaseList)
        {
            baseBean.setId(commonDAO.getSquenceString());

            baseBean.setOutId(fullId);

            baseBean.setMtype(newInBean.getMtype());

            // 增加单个产品到base表
            baseDAO.saveEntityBean(baseBean);
        }

        newInBean.setStatus(0);

        addOutLog(fullId, user, newInBean, "提交", SailConstant.OPR_OUT_PASS,
            OutConstant.BUY_STATUS_PASS);

        saveUnique(user, newInBean);

        // OSGI 监听实现(对冲单据,有入库的过程)
        Collection<OutListener> listenerMapValues = listenerMapValues();

        for (OutListener listener : listenerMapValues)
        {
            listener.onConfirmOutOrBuy(user, newInBean);
        }
    }

    /**
     * warpOutId
     * 
     * @param outBean
     * @param newInBean
     * @return
     * @throws MYException
     */
    private String wrapOutId(final OutBean outBean, OutBean newInBean)
        throws MYException
    {
        // 先保存
        String id = getAll(commonDAO.getSquence());

        LocationBean location = locationDAO.find(outBean.getLocationId());

        if (location == null)
        {
            _logger.error("区域不存在:" + outBean.getLocationId());

            throw new MYException("区域不存在:" + outBean.getLocationId());
        }

        //String flag = location.getCode();
        String flag = OutHelper.getSailHead(newInBean.getType(), newInBean.getOutType());

        String time = TimeTools.now("yyMMddHHmm");

        final String fullId = flag + time + id;

        newInBean.setId(getOutId(id));

        newInBean.setFullId(fullId);

        return fullId;
    }

    /**
     * 
     * {@inheritDoc}
     */
    public String processSwatchCheck(OutBean out)
    {
    	if (out.getType() != OutConstant.OUT_TYPE_OUTBILL)
    		return "";
    	
    	if (out.getOutType() != OutConstant.OUTTYPE_OUT_SWATCH)
    		return "";
    	
    	String stafferId = out.getStafferId();

    	List<BaseBean> list = baseDAO.queryEntityBeansByFK(out.getFullId());

    	// 截止昨日
    	SwatchStatsBean ssBean = swatchStatsDAO.findByUnique(stafferId);

    	double totalMoney = 0.0d;
    	
    	List<SwatchStatsItemBean> itemList = null;
    	
    	if (null != ssBean)
    	{
    		totalMoney = ssBean.getTotalMoney();
    		
    		itemList = swatchStatsItemDAO.queryEntityBeansByFK(ssBean.getId());
    	}
    	
    	// 当天
    	SwatchStatsBean curSSBean = this.statsPersonalSwatch(stafferId, TimeTools.now_short(), TimeTools.now_short()); 

    	double curTotalMoney = 0.0d;
    	
    	List<SwatchStatsItemBean> curItemList = null;
    	
    	if (null != curSSBean)
    	{
    		curTotalMoney = curSSBean.getTotalMoney();
    		
    		curItemList = curSSBean.getItemList();
    	}
    	
    	// 总额
		if ((totalMoney + curTotalMoney + out.getTotal()) > 30000)
		{
			return ";当前未转销售或未退领样单总额（含本单）超过3万";
		}
		
		StringBuilder sb = new StringBuilder();
		
		// 产品数量
		for (BaseBean each : list)
		{
			int amount = 0;
			
			if (!ListTools.isEmptyOrNull(itemList))
			{
				for (SwatchStatsItemBean eachss : itemList)
				{
					if (each.getProductId().equals(eachss.getProductId()))
					{
						amount += eachss.getAmount();
						
						break;
					}
				}
			}
			
			if (!ListTools.isEmptyOrNull(curItemList))
			{
				for (SwatchStatsItemBean eachss : curItemList)
    			{
    				if (each.getProductId().equals(eachss.getProductId()))
    				{
    					amount += eachss.getAmount();
    					
    					break;
    				}
    			}
			}
			
			if ((amount + each.getAmount()) > 2)
			{
				sb.append("商品:").append(each.getProductName()).append("（含本单）总数大于2").append("<br>");
			}
		}
    	
		if (!StringTools.isNullOrNone(sb.toString()))
		{
			return ";当前未转销售或未退领样单:" + sb.toString();
		}
		
    	return "";
    }
    
    public double getSwatchMoney(String stafferId)
    {
    	// 截止昨日
    	SwatchStatsBean ssBean = swatchStatsDAO.findByUnique(stafferId);

    	double totalMoney = 0.0d;
    	
    	if (null != ssBean)
    	{
    		totalMoney = ssBean.getTotalMoney();
    	}
    	
    	// 当天
    	SwatchStatsBean curSSBean = this.statsPersonalSwatch(stafferId, TimeTools.now_short(), TimeTools.now_short()); 

    	double curTotalMoney = 0.0d;
    	
    	if (null != curSSBean)
    	{
    		curTotalMoney = curSSBean.getTotalMoney();
    	}
    	
		return (totalMoney + curTotalMoney);
    }
    
    /**
     * CORE (销售单的终结)财务核对
     * 
     * @param fullId
     * @param user
     * @return
     * @throws Exception
     */
    public boolean check(final String fullId, final User user, final String checks)
        throws MYException
    {
        final OutBean outBean = outDAO.find(fullId);

        if (outBean == null)
        {
            throw new MYException("销售单不存在，请重新操作");
        }

        if (outBean.getStatus() != OutConstant.STATUS_PASS
            && outBean.getStatus() != OutConstant.STATUS_SEC_PASS)
        {
            throw new MYException("销售单不在待回款/结束状态，不能核对");
        }

        // 入库操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    outDAO.modifyChecks(fullId, outBean.getChecks() + " / " + checks);

                    // 结束
                    outDAO.modifyOutStatus(fullId, OutConstant.STATUS_SEC_PASS);

                    // OSGI 监听实现
                    Collection<OutListener> listenerMapValues = listenerMapValues();

                    for (OutListener listener : listenerMapValues)
                    {
                        try
                        {
                            listener.onCheck(user, outBean);
                        }
                        catch (MYException e)
                        {
                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }

                    addOutLog(fullId, user, outBean, "核对", SailConstant.OPR_OUT_PASS,
                        OutConstant.STATUS_SEC_PASS);

                    notifyOut(outBean, user, 3);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("核对库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("核对库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("核对库单错误：", e);
            throw new MYException("系统错误,请重新操作");
        }

        return true;

    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean tranOut(User user, String fullId, OutBean outBean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, fullId);

        OutBean out = checkTran(user, fullId);

        SailTranApplyBean apply = new SailTranApplyBean();
        apply.setId(commonDAO.getSquenceString20());
        apply.setCustomerId(out.getCustomerId());
        apply.setLogTime(TimeTools.now());
        apply.setOldStafferId(out.getStafferId());
        apply.setOutId(fullId);
        apply.setStafferId(user.getStafferId());
        apply.setStatus(SailConstant.SAILTRANAPPLY_SUBMIT);
        
        apply.setOperator(outBean.getOperator());
        apply.setOperatorName(outBean.getOperatorName());


        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("提交申请");
        log.setFullId(apply.getId());
        log.setOprMode(PublicConstant.OPRMODE_SUBMIT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(SailConstant.SAILTRANAPPLY_INIT);

        log.setAfterStatus(apply.getStatus());

       // flowLogDAO.saveEntityBean(log);
        
        
        
        if (apply == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (apply.getStatus() != SailConstant.SAILTRANAPPLY_SUBMIT)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !OutHelper.isSailEnd(out))
        {
            throw new MYException("单据不处于结束,请确认操作");
        }

        if (out.getPay() != OutConstant.PAY_YES)
        {
            throw new MYException("单据没有完全付款,请确认操作");
        }

        if ( !clientManager.hasCustomerAuth2(apply.getStafferId(), out.getCustomerId()))
        {
            throw new MYException("您当前和客户[%s]没有关联关系,无法移交,请确认操作", out.getCustomerName());
        }

        StafferBean sb = stafferDAO.find(apply.getStafferId());

        if (sb == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        apply.setStatus(SailConstant.SAILTRANAPPLY_PASS);

        sailTranApplyDAO.saveEntityBean(apply);

        // 凭证变更
        // TAX_ADD 未付款的销售单移交
        Collection<OutListener> listenerMapValues = this.listenerMapValues();

        for (OutListener outListener : listenerMapValues)
        {
            outListener.onTranOut(user, out, sb);
        }
        
        log.setActor(user.getStafferName());

        log.setDescription("通过");
        log.setFullId(fullId);
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(SailConstant.SAILTRANAPPLY_SUBMIT);

        log.setAfterStatus(SailConstant.SAILTRANAPPLY_PASS);

        flowLogDAO.saveEntityBean(log);

        String oldName = out.getStafferName();

        out.setStafferId(sb.getId());
        out.setStafferName(sb.getName());

        // 更新责任人
        outDAO.updateEntityBean(out);

        log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("变更单据提交人,从:" + oldName + "到:" + sb.getName());
        log.setFullId(out.getFullId());
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(out.getStatus());

        log.setAfterStatus(out.getStatus());

        // 销售单日志
        flowLogDAO.saveEntityBean(log);
        
        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean passTranApply(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        SailTranApplyBean bean = sailTranApplyDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() != SailConstant.SAILTRANAPPLY_SUBMIT)
        {
            throw new MYException("数据错误,请确认操作");
        }

        OutBean out = outDAO.find(bean.getOutId());

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !OutHelper.isSailEnd(out))
        {
            throw new MYException("单据不处于结束,请确认操作");
        }

        if (out.getPay() != OutConstant.PAY_YES)
        {
            throw new MYException("单据没有完全付款,请确认操作");
        }

        if ( !clientManager.hasCustomerAuth2(bean.getStafferId(), out.getCustomerId()))
        {
            throw new MYException("您当前和客户[%s]没有关联关系,无法移交,请确认操作", out.getCustomerName());
        }

        StafferBean sb = stafferDAO.find(bean.getStafferId());

        if (sb == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setStatus(SailConstant.SAILTRANAPPLY_PASS);

        sailTranApplyDAO.updateEntityBean(bean);
        
        // 凭证变更
        // TAX_ADD 未付款的销售单移交
        Collection<OutListener> listenerMapValues = this.listenerMapValues();

        for (OutListener outListener : listenerMapValues)
        {
            outListener.onTranOut(user, out, sb);
        }

        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("通过");
        log.setFullId(id);
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(SailConstant.SAILTRANAPPLY_SUBMIT);

        log.setAfterStatus(SailConstant.SAILTRANAPPLY_PASS);

        flowLogDAO.saveEntityBean(log);

        String oldName = out.getStafferName();

        out.setStafferId(sb.getId());
        out.setStafferName(sb.getName());

        // 更新责任人
        outDAO.updateEntityBean(out);
        
        log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("变更单据提交人,从:" + oldName + "到:" + sb.getName());
        log.setFullId(out.getFullId());
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(out.getStatus());

        log.setAfterStatus(out.getStatus());

        // 销售单日志
        flowLogDAO.saveEntityBean(log);

        return true;
    }

    public boolean tranCompleteOutListNT(User user, List<OutBean> outList, StafferBean staffer)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, outList, staffer);

        for (OutBean outBean : outList)
        {
            if (outBean.getOutType() != OutConstant.OUTTYPE_OUT_COMMON)
            {
                throw new MYException("只能销售出库的单据可以移交,请确认操作");
            }

            if (outBean.getPay() == OutConstant.PAY_YES)
            {
                throw new MYException("单据已经完全付款,请确认操作");
            }
        }

        // TAX_ADD 未付款的销售单移交
        Collection<OutListener> listenerMapValues = this.listenerMapValues();

        for (OutListener outListener : listenerMapValues)
        {
            outListener.onTranOutList(user, outList, staffer);
        }

        for (OutBean out : outList)
        {
            String oldName = out.getStafferName();

            out.setStafferId(staffer.getId());
            out.setStafferName(staffer.getName());

            // #347
            out.setIndustryId(staffer.getIndustryId());
            out.setIndustryId2(staffer.getIndustryId2());
            out.setIndustryId3(staffer.getIndustryId3());

            // 更新责任人
            outDAO.updateEntityBean(out);

            FlowLogBean log = new FlowLogBean();

            log.setActor(user.getStafferName());

            log.setDescription("变更单据提交人,从:" + oldName + "到:" + staffer.getName());
            log.setFullId(out.getFullId());
            log.setOprMode(PublicConstant.OPRMODE_PASS);
            log.setLogTime(TimeTools.now());

            log.setPreStatus(out.getStatus());

            log.setAfterStatus(out.getStatus());

            // 销售单日志
            flowLogDAO.saveEntityBean(log);
        }

        return true;
    }

    /**
     * 
     * {@inheritDoc}
     */
	public boolean tranCompleteConsignOutListNT(User user,
			List<OutBean> outList, StafferBean staffer) throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, outList, staffer);

        for (OutBean outBean : outList)
        {
            if (outBean.getOutType() != OutConstant.OUTTYPE_OUT_CONSIGN)
            {
                throw new MYException("处理委托代销时,只能是委托代销的单据可以移交,请确认操作");
            }

            if (outBean.getPay() == OutConstant.PAY_YES)
            {
                throw new MYException("单据已经完全付款,请确认操作");
            }
        }
        
        for (OutBean out : outList)
        {
            String oldName = out.getStafferName();

            out.setStafferId(staffer.getId());
            out.setStafferName(staffer.getName());
            out.setIndustryId(staffer.getIndustryId());
            out.setIndustryId2(staffer.getIndustryId2());
            out.setIndustryId3(staffer.getIndustryId3());

            setInvoiceId(out);

            // 更新责任人
            outDAO.updateEntityBean(out);

            // 同时针对委托结算单也同步更新
            List<OutBalanceBean> balanceList = outBalanceDAO.queryEntityBeansByFK(out.getFullId());
            
            for (OutBalanceBean each : balanceList)
            {
            	each.setStafferId(out.getStafferId());
            	
            	outBalanceDAO.updateEntityBean(each);
            }
            
            FlowLogBean log = new FlowLogBean();

            log.setActor(user.getStafferName());

            log.setDescription("变更单据提交人,从:" + oldName + "到:" + staffer.getName());
            log.setFullId(out.getFullId());
            log.setOprMode(PublicConstant.OPRMODE_PASS);
            log.setLogTime(TimeTools.now());

            log.setPreStatus(out.getStatus());

            log.setAfterStatus(out.getStatus());

            // 销售单日志
            flowLogDAO.saveEntityBean(log);
        }

        return true;
    }
    
    
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectTranApply(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        SailTranApplyBean bean = sailTranApplyDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() != SailConstant.SAILTRANAPPLY_SUBMIT)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setStatus(SailConstant.SAILTRANAPPLY_REJECT);

        sailTranApplyDAO.updateEntityBean(bean);

        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(reason);
        log.setFullId(id);
        log.setOprMode(PublicConstant.OPRMODE_REJECT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(SailConstant.SAILTRANAPPLY_SUBMIT);

        log.setAfterStatus(SailConstant.SAILTRANAPPLY_REJECT);

        flowLogDAO.saveEntityBean(log);

        return true;
    }

    /**
     * checkTran
     * 
     * @param user
     * @param fullId
     * @return
     * @throws MYException
     */
    private OutBean checkTran(User user, String fullId)
        throws MYException
    {
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

    	// 正在对账
    	if (out.getFeedBackCheck() == 1)
    	{
    		throw new MYException("此销售单正在对账，不允许移交");
    	}
        
        if (out.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("自己不能移交给自己,请确认操作");
        }

        if ( !OutHelper.isSailEnd(out))
        {
            throw new MYException("单据不处于结束,请确认操作");
        }

        if (out.getPay() != OutConstant.PAY_YES)
        {
            throw new MYException("单据没有完全付款,请确认操作");
        }

        if (out.getOutType() != OutConstant.OUTTYPE_OUT_COMMON)
        {
            throw new MYException("只能销售出库的单据可以移交,请确认操作");
        }

        if ( !clientManager.hasCustomerAuth2(user.getStafferId(), out.getCustomerId()))
        {
            throw new MYException("您当前和客户[%s]没有关联关系,无法移交,请确认操作", out.getCustomerName());
        }

        SailTranApplyBean tran = sailTranApplyDAO.findByUnique(out.getCustomerId(),
            SailConstant.SAILTRANAPPLY_SUBMIT);

        if (tran != null)
        {
            throw new MYException("申请已经存在,请确认操作");
        }

        return out;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean checkOutBalance(String id, User user, String checks)
        throws MYException
    {
        OutBalanceBean bean = outBalanceDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() != OutConstant.OUTBALANCE_STATUS_END)
        {
            throw new MYException("不在库管通过,请确认操作");
        }

        outBalanceDAO.updateCheck(id, PublicConstant.CHECK_STATUS_END, checks);

        addOutLog2(id, user, OutConstant.OUTBALANCE_STATUS_END, "总部核对", SailConstant.OPR_OUT_PASS,
                OutConstant.OUTBALANCE_STATUS_END);

        return true;
    }

    public OutBean findOutById(final String fullId)
    {
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            return null;
        }

        out.setBaseList(baseDAO.queryEntityBeansByFK(fullId));

        return out;
    }

    public OutVO findOutVOById(final String fullId)
    {
        OutVO out = outDAO.findVO(fullId);

        if (out == null)
        {
            return null;
        }

        out.setBaseList(baseDAO.queryEntityBeansByFK(fullId));

        return out;
    }

    @Override
    public boolean rejectOutBack(final User user, final String fullId, final String reason) throws MYException {

        JudgeTools.judgeParameterIsNull(fullId);

        final OutBean outBean = outDAO.find(fullId);

        if (outBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (outBean.getInvoiceMoney() > 0) {
            throw new MYException("单据不能被驳回,原因是已开过发票.");
        }


        // 入库操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    outBean.setStatus(OutConstant.BUY_STATUS_REJECT);
                    outBean.setReason(reason);
                    outDAO.updateEntityBean(outBean);

                    if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                            && outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH)
                    {
                        notifyManager.notifyMessage(outBean.getStafferId(), outBean
                                .getRefOutFullId()
                                + "的领样退货申请已经被["
                                + user.getStafferName()
                                + "]驳回,申请自动删除");
                    }

                    List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);
                    outBean.setBaseList(baseList);
                    Collection<OutListener> listenerMapValues = listenerMapValues();

                    for (OutListener listener : listenerMapValues)
                    {
                        try
                        {
                            listener.onDelete(user, outBean);
                        }
                        catch (MYException e)
                        {
                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("删除库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("删除库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("删除库单错误：", e);
            throw new MYException("系统错误,请重新操作");
        }

        _logger.info(user.getStafferName() + "/" + user.getName() + "/REJECT:" + outBean);

//        operationLog.info(user.getStafferName() + "/" + user.getName() + "/REJECT OUT:" + outBean);

        return true;
    }

    /**
     * 删除库单
     * 
     * @param fullId
     * @return
     */
    public boolean delOut(final User user, final String fullId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(fullId);

        final OutBean outBean = outDAO.find(fullId);

        if (outBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 退库的逻辑比较特殊
//        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
//            && (outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH
//                || outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
//                || outBean.getOutType() == OutConstant.OUTTYPE_IN_EXCHANGE))
//        {
////            if ( ! (outBean.getStatus() == OutConstant.STATUS_SAVE
////                    || outBean.getStatus() == OutConstant.STATUS_REJECT || outBean.getStatus() == OutConstant.BUY_STATUS_SUBMIT))
////            {
////                throw new MYException("单据不能被删除,请确认操作.状态不能为保存,驳回,待库管处理");
////            }
//            if ( ! (outBean.getStatus() == OutConstant.STATUS_SAVE
//                    || outBean.getStatus() == OutConstant.STATUS_REJECT))
//            {
//                throw new MYException("单据不能被删除,请确认操作.状态不能为保存,驳回");
//            }
//        }
//        else
//        {
//            if ( !OutHelper.canDelete(outBean))
//            {
//                throw new MYException("单据不能被删除,请确认操作.状态不能为保存,驳回");
//            }
//        }
        
        if (outBean.getInvoiceMoney() > 0) {
        	throw new MYException("单据不能被删除,原因是已开过发票.");
        }

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);

        outBean.setBaseList(baseList);

        // 入库操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    outDAO.deleteEntityBean(fullId);

                    baseDAO.deleteEntityBeansByFK(fullId);

                    // 删除审批记录
                    flowLogDAO.deleteEntityBeansByFK(fullId);

                    if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                        && outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH)
                    {
                        notifyManager.notifyMessage(outBean.getStafferId(), outBean
                            .getRefOutFullId()
                                                                            + "的领样退货申请已经被["
                                                                            + user.getStafferName()
                                                                            + "]驳回,申请自动删除");
                    }

                    Collection<OutListener> listenerMapValues = listenerMapValues();

                    for (OutListener listener : listenerMapValues)
                    {
                        try
                        {
                            listener.onDelete(user, outBean);
                        }
                        catch (MYException e)
                        {
                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("删除库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("删除库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("删除库单错误：", e);
            throw new MYException("系统错误,请重新操作");
        }

        _logger.info(user.getStafferName() + "/" + user.getName() + "/DELETE:" + outBean);

//        operationLog.info(user.getStafferName() + "/" + user.getName() + "/DELETE OUT:" + outBean);

        return true;
    }

    @Override
    public boolean updateZjrcOutStatus(final String fullId) throws MYException {
        final ZJRCOutBean out = this.zjrcOutDAO.find(fullId);

        if (out == null){
            String msg = "紫金订单未找到："+fullId;
            _logger.error(msg);
            throw new MYException(msg);
        } else {
            out.setStatus(OutImportConstant.STATUS_SHIPPING);
            // 入库操作在数据库事务中完成
            TransactionTemplate tran = new TransactionTemplate(transactionManager);
            try
            {
                tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        zjrcOutDAO.updateEntityBean(out);
                        _logger.info(fullId+"资金订单状态更新为3(配送中)") ;

                        return Boolean.TRUE;
                    }
                });
            }
            catch (TransactionException e)
            {
                _logger.error("修改紫金订单错误：", e);
                throw new MYException("数据库内部错误");
            }
            catch (DataAccessException e)
            {
                _logger.error("修改紫金订单错误：", e);
                throw new MYException(e.getCause().toString());
            }
            catch (Exception e)
            {
                _logger.error("修改紫金订单错误：", e);
                throw new MYException("系统错误,请重新操作");
            }
            return true;
        }

    }

    /**
     * 更新库单(但是不包括状态)
     * 
     * @param out
     * @return
     */
    public boolean updateOut(final OutBean out)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(out);

        JudgeTools.judgeParameterIsNull(out.getFullId());

        final OutBean oldBean = outDAO.find(out.getFullId());

        out.setStatus(oldBean.getStatus());
        if(oldBean.getStatus()==OutConstant.STATUS_PASS)
        {
        	out.setPay(OutConstant.PAY_YES);
        }
        else
        {
        out.setPay(oldBean.getPay());
        }

        setInvoiceId(out);

        // 入库操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    outDAO.updateEntityBean(out);

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("修改库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("修改库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("修改库单错误：", e);
            throw new MYException("系统错误,请重新操作");
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.manager.OutManager#modifyPay(com.center.china.osgi.publics.User, java.lang.String,
     *      int, java.lang.String)
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean payOut(final User user, String fullId, String reason, int backPay)
        throws MYException
    {
        return payOutWithoutTransactional(user, fullId, reason, backPay);
    }

    /**
     * 强制通过付款(对于4月之前的单据使用)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean fourcePayOut(User user, String fullId, String reason)
        throws MYException
    {
        // 需要增加是否超期 flowId
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ("2011-04-01".compareTo(out.getOutTime()) < 0)
        {
            throw new MYException("销售单必须在(2011-04-01),请确认操作");
        }

        // 如果getRedate为空说明已经超前回款了
        if ( !StringTools.isNullOrNone(out.getRedate()))
        {
            int delay = TimeTools.cdate(TimeTools.now(), out.getRedate());

            if (delay > 0)
            {
                outDAO.modifyTempType(fullId, delay);
            }
            else
            {
                outDAO.modifyTempType(fullId, 0);
            }
        }

        addOutLog(fullId, user, out, reason + "[强制回款]", SailConstant.OPR_OUT_PASS, out.getStatus());

        notifyOut(out, user, 2);

        // 修改付款标识
        return outDAO.updatePay(fullId, OutConstant.PAY_YES);
    }

    public boolean payOutWithoutTransactional(final User user, String fullId, String reason, int backPay)
        throws MYException
    {
        // 需要增加是否超期 flowId
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 如果getRedate为空说明已经超前回款了
        if ( !StringTools.isNullOrNone(out.getRedate()))
        {
            int delay = TimeTools.cdate(TimeTools.now(), out.getRedate());

            if (delay > 0)
            {
                outDAO.modifyTempType(fullId, delay);
            }
            else
            {
                outDAO.modifyTempType(fullId, 0);
            }
        }

        OutListener listener = this.findListener(PluginNameConstant.OUTLISTENER_FINANCEIMPL);

        if (listener == null)
        {
            throw new MYException("资金模块没有加载,请确认操作");
        }

        ResultBean result = listener.onHadPay(user, out);

        // 不能完全回款
        if (result.getResult() != 0)
        {
            throw new MYException(result.getMessage());
        }

        addOutLog(fullId, user, out, reason + ";" + result.getMessage(), SailConstant.OPR_OUT_PASS,
                out.getStatus());

        notifyOut(out, user, 2);

        // 针对销售出库，由未付款到已付款时，记录销售单，为财务 销售单回款标记 做准备
        savePayTagBean(out);

        //#775
        if (backPay!= 0){
            _logger.info("***update back pay***"+backPay);
            outDAO.updateBackPay(fullId,backPay);
        }

        // 修改付款标识
        return outDAO.updatePay(fullId, OutConstant.PAY_YES);
    }

	private void savePayTagBean(OutBean out)
	{
		if (out.getType() == OutConstant.OUT_TYPE_OUTBILL && out.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
        {
        	OutPayTagBean oldtag = outPayTagDAO.findByUnique(out.getFullId());
        	
        	if (null == oldtag)
        	{
        		OutPayTagBean tagBean = new OutPayTagBean();
        		
        		tagBean.setOutId(out.getFullId());
        		
        		outPayTagDAO.saveEntityBean(tagBean);
        	}
        }
	}

    public boolean payOutWithoutTransactional2(final User user, String fullId, String reason)
        throws MYException
    {
        // 需要增加是否超期 flowId
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        OutListener listener = this.findListener(PluginNameConstant.OUTLISTENER_FINANCEIMPL);

        if (listener == null)
        {
            return true;
        }

        ResultBean result = listener.onHadPay(user, out);

        if (result.getResult() != 0)
        {
            addOutLog(fullId, user, out, reason + ",但是系统核算后没有完全付款", SailConstant.OPR_OUT_PASS, out
                .getStatus());

            // 不能完全回款
            return outDAO.updatePay(fullId, OutConstant.PAY_NOT);
        }

        // 如果getRedate为空说明已经超前回款了
        if ( !StringTools.isNullOrNone(out.getRedate()))
        {
            int delay = TimeTools.cdate(TimeTools.now(), out.getRedate());

            if (delay > 0)
            {
                outDAO.modifyTempType(fullId, delay);
            }
            else
            {
                outDAO.modifyTempType(fullId, 0);
            }
        }

        addOutLog(fullId, user, out, reason + ";" + result.getMessage(), SailConstant.OPR_OUT_PASS,
            out.getStatus());

        notifyOut(out, user, 2);

        // 修改付款标识
        return outDAO.updatePay(fullId, OutConstant.PAY_YES);
    }

    public ResultBean checkOutPayStatus(User user, OutInterface out)
    {
        ResultBean result = new ResultBean();

        OutListener listener = this.findListener(PluginNameConstant.OUTLISTENER_FINANCEIMPL);

        if (listener == null)
        {
            return result;
        }

        result = listener.onHadPay(user, out);

        return result;
    }

    public double outNeedPayMoney(User user, String fullId)
    {
        OutListener listener = this.findListener(PluginNameConstant.OUTLISTENER_FINANCEIMPL);

        double total = 0.0d;

        if (listener == null)
        {
            return total;
        }

        // 这里只要一个实现即可
        total = listener.outNeedPayMoney(user, fullId);

        return total;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.sail.manager.OutManager#payBaddebts(com.center.china.osgi.publics.User,
     *      java.lang.String, double)
     */
    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean payBaddebts(final User user, String fullId, double bad)
        throws MYException
    {
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            return false;
        }

        if ( !OutHelper.isSailEnd(out))
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (MathTools.compare(out.getHadPay() + bad, out.getTotal()) > 0)
        {
            throw new MYException("坏账金额过多,请确认操作");
        }

        // 付款的金额
        outDAO.modifyBadDebts(fullId, bad);

        out.setBadDebts(bad);

        // TAX_ADD 坏账的确认
        Collection<OutListener> listenerMapValues = listenerMapValues();

        for (OutListener listener : listenerMapValues)
        {
            listener.onConfirmBadDebts(user, out);
        }

        return true;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateInvoice(User user, String fullId, String invoiceId)
        throws MYException
    {
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (out.getInvoiceMoney() > 0)
        {
            throw new MYException("单据已经开票,不能修改发票类型");
        }

        outDAO.updateInvoice(fullId, invoiceId);

        return true;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean initPayOut(final User user, String fullId)
        throws MYException
    {
        // 需要增加是否超期 flowId
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            return false;
        }

        if ( !OutHelper.isSailEnd(out))
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (out.getPay() != OutConstant.PAY_YES)
        {
            throw new MYException("数据错误,请确认操作");
        }

        outDAO.modifyOutStatus(fullId, OutConstant.STATUS_PASS);

        addOutLog(fullId, user, out, "坏账消除", SailConstant.OPR_OUT_REJECT, OutConstant.STATUS_PASS);

        outDAO.modifyBadDebts(fullId, 0.0d);

        // 关联状态取消
        outDAO.updataBadDebtsCheckStatus(fullId, OutConstant.BADDEBTSCHECKSTATUS_NO);

        Collection<OutListener> listenerMapValues = this.listenerMapValues();

        // 通知其他的模块
        for (OutListener outListener : listenerMapValues)
        {
            outListener.onCancleBadDebts(user, out);
        }

        // 修改付款标识
        return outDAO.updatePay(fullId, OutConstant.PAY_NOT);
    }

    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean mark(String fullId, boolean status)
    {
        return outDAO.mark(fullId, status);
    }

    @Transactional(rollbackFor = {MYException.class})
    @Exceptional
    public boolean modifyReDate(String fullId, String reDate) {
        return outDAO.modifyReDate(fullId, reDate);
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean addOutBalance(final User user, OutBalanceBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getBaseBalanceList());

        addOutBalanceInner(user, bean);

        return true;
    }

    /**
     * 提取-common use
     * @param user
     * @param bean
     * @throws MYException
     */
	private void addOutBalanceInner(final User user, OutBalanceBean bean)
			throws MYException
	{
		OutBean out = outDAO.find(bean.getOutId());

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !user.getStafferId().equals(out.getStafferId()) && false)
        {
            throw new MYException("只能结算自己委托代销单,请确认操作");
        }

        if (out.getType() != OutConstant.OUT_TYPE_OUTBILL
            || out.getOutType() != OutConstant.OUTTYPE_OUT_CONSIGN)
        {
            throw new MYException("不是委托代销的销售单,请确认操作");
        }

        if (out.getStatus() != OutConstant.STATUS_PASS
            && out.getStatus() != OutConstant.STATUS_SEC_PASS)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 增加退货金额须小于 可开票金额
        if (!StringTools.isNullOrNone(bean.getRefOutBalanceId())) {
        	OutBalanceBean old = outBalanceDAO.find(bean.getRefOutBalanceId());
        	
        	if (null != old) {
        		double backTotal = outBalanceDAO.sumByOutBalanceId(old.getId());
        		
        		if (MathTools.compare(bean.getTotal(), old.getTotal() - backTotal - old.getInvoiceMoney()) > 0) {
    				throw new MYException("退货金额[%.2f]须小于可开票金额[%.2f]，请先退票。 ", bean.getTotal(), old.getTotal() - old.getInvoiceMoney());
        		}
        	}
        }
        
        bean.setId(commonDAO.getSquenceString20());

        bean.setMtype(out.getMtype());

        outBalanceDAO.saveEntityBean(bean);

        List<BaseBalanceBean> baseBalanceList = bean.getBaseBalanceList();

        for (BaseBalanceBean baseBalanceBean : baseBalanceList)
        {
            baseBalanceBean.setId(commonDAO.getSquenceString20());

            baseBalanceBean.setParentId(bean.getId());

            baseBalanceBean.setOutId(bean.getOutId());

            baseBalanceBean.setMtype(out.getMtype());
        }

        baseBalanceDAO.saveAllEntityBeans(baseBalanceList);
	}

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean passOutBalance(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBalanceBean bean = checkBalancePass(id);

        bean.setStatus(OutConstant.OUTBALANCE_STATUS_PASS);
        bean.setLogTime(TimeTools.now());

        outBalanceDAO.updateEntityBean(bean);

        addOutLog2(id, user, OutConstant.OUTBALANCE_STATUS_SUBMIT, "结算中心通过",
                SailConstant.OPR_OUT_PASS, OutConstant.OUTBALANCE_STATUS_PASS);

        List<BaseBalanceBean> baseList = baseBalanceDAO.queryEntityBeansByFK(id);

        bean.setBaseBalanceList(baseList);       
        
        // 这里结算单结束
        if (bean.getType() == OutConstant.OUTBALANCE_TYPE_SAIL)
        {
            // TAX_ADD 销售-结算单（审核通过）
            Collection<OutListener> listenerMapValues = listenerMapValues();

            for (OutListener listener : listenerMapValues)
            {
                listener.onOutBalancePass(user, bean);
            }
        }

        notifyManager.notifyMessage(bean.getStafferId(), bean.getOutId() + "的结算清单已经被["
                + user.getStafferName() + "]通过");

        return true;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean passOutBalanceToDepot(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBalanceBean bean = checkBalancePassToDepot(id);

        bean.setStatus(OutConstant.OUTBALANCE_STATUS_END);

        outBalanceDAO.updateEntityBean(bean);

        boolean useDefaultDepotpart = true;

        DepotpartBean defaultOKDepotpart = null;

        if ((bean.getType() == OutConstant.OUTBALANCE_TYPE_BACK || bean.getType() == OutConstant.OUTBALANCE_TYPE_SAILBACK )
            && !StringTools.isNullOrNone(bean.getDirDepot()))
        {
            useDefaultDepotpart = false;

            defaultOKDepotpart = depotpartDAO.findDefaultOKDepotpart(bean.getDirDepot());

            if (defaultOKDepotpart == null)
            {
                throw new MYException("默认仓区不存在,请确认操作");
            }
        }
        else
        {
            throw new MYException("不是退货单或者没有选择退货的的仓库,请确认操作");
        }

        List<BaseBalanceBean> baseList = baseBalanceDAO.queryEntityBeansByFK(id);

        String sequence = commonDAO.getSquenceString();

        // 退货单是要变动库存的
        if (bean.getType() == OutConstant.OUTBALANCE_TYPE_BACK || bean.getType() == OutConstant.OUTBALANCE_TYPE_SAILBACK )
        {
            // 这里需要变动库存(增加库存)
            for (BaseBalanceBean each : baseList)
            {
                ProductChangeWrap wrap = new ProductChangeWrap();

                BaseBean element = baseDAO.find(each.getBaseId());

                if (useDefaultDepotpart)
                {
                    // 使用默认仓区
                    wrap.setDepotpartId(element.getDepotpartId());
                }
                else
                {
                    // 使用用户选择的仓库
                    wrap.setDepotpartId(defaultOKDepotpart.getId());
                }
                wrap.setPrice(element.getCostPrice());
                wrap.setVirtualPrice(element.getVirtualPrice());
                wrap.setProductId(element.getProductId());
                if (StringTools.isNullOrNone(element.getOwner()))
                {
                    wrap.setStafferId("0");
                }
                else
                {
                    wrap.setStafferId(element.getOwner());
                }

                // 增加的数量来自退货的数量
                wrap.setChange(each.getAmount());

                wrap.setDescription("库单[" + bean.getOutId() + "]代销退货操作");
                wrap.setSerializeId(sequence);
                wrap.setType(StorageConstant.OPR_STORAGE_BALANCE);
                wrap.setRefId(id);

                storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, false);
            }
        }

        addOutLog2(id, user, OutConstant.OUTBALANCE_STATUS_PASS, "库管通过", SailConstant.OPR_OUT_PASS,
            OutConstant.OUTBALANCE_STATUS_END);

        bean.setBaseBalanceList(baseList);

        if (bean.getType() == OutConstant.OUTBALANCE_TYPE_BACK || bean.getType() == OutConstant.OUTBALANCE_TYPE_SAILBACK )
        {
            // TAX_ADD 销售-结算退货单（审核通过）
            Collection<OutListener> listenerMapValues = listenerMapValues();

            for (OutListener listener : listenerMapValues)
            {
                listener.onOutBalancePass(user, bean);
            }
        }

        // 未结算退货
        if (bean.getType() == OutConstant.OUTBALANCE_TYPE_BACK)
        {
            OutBean out = outDAO.find(bean.getOutId());
            
            // 看看销售单是否可以结帐
            ResultBean result = this.checkOutPayStatus(user, out);

            // 如果全部支付就自动表示收款
            if (result.getResult() == 0)
            {
            	// 尝试全部付款
                this.payOutWithoutTransactional(user, out.getFullId(), "付款申请通过", 0);
            }
        }
        
        notifyManager.notifyMessage(bean.getStafferId(), bean.getOutId() + "的结算清单已经被["
                                                         + user.getStafferName() + "]退库通过");

        return true;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean deleteOutBalance(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBalanceBean bean = outBalanceDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() != OutConstant.OUTBALANCE_STATUS_REJECT)
        {
            throw new MYException("数据错误,请确认操作");
        }
        
        if (bean.getInvoiceMoney() > 0) {
        	throw new MYException("删除出错，原因是该单据已开过发票.");
        }

        outBalanceDAO.deleteEntityBean(id);

        return true;
    }

    /**
     * 是否是领样转销售
     * 
     * @param fullId
     * @return
     */
    public boolean isSwatchToSail(String fullId)
    {
        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            return false;
        }

        if (StringTools.isNullOrNone(out.getRefOutFullId()))
        {
            return false;
        }

        OutBean src = outDAO.find(out.getRefOutFullId());

        if (src == null)
        {
            return false;
        }

        if (src.getType() == OutConstant.OUT_TYPE_OUTBILL
            && (src.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH 
            		|| src.getOutType() == OutConstant.OUTTYPE_OUT_SHOW
            		|| src.getOutType() == OutConstant.OUTTYPE_OUT_SHOWSWATCH
                //2015/3/17 新增银行领样 （与银行铺货类拟）
                || src.getOutType() == OutConstant.OUTTYPE_OUT_BANK_SWATCH))
        {
            return true;
        }

        return false;
    }

    /**
     * checkBalancePass
     * 
     * @param id
     * @return
     * @throws MYException
     */
    private OutBalanceBean checkBalancePass(String id)
        throws MYException
    {
        OutBalanceBean bean = outBalanceDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() != OutConstant.OUTBALANCE_STATUS_SUBMIT)
        {
            throw new MYException("数据错误,请确认操作");
        }

        checkBalanceOver(id);

        return bean;
    }

    private OutBalanceBean checkBalancePassToDepot(String id)
        throws MYException
    {
        OutBalanceBean bean = outBalanceDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() != OutConstant.OUTBALANCE_STATUS_PASS)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getType() != OutConstant.OUTBALANCE_TYPE_BACK && bean.getType() != OutConstant.OUTBALANCE_TYPE_SAILBACK)
        {
            throw new MYException("只能是退货单入库,请确认操作");
        }

        checkBalanceOver(id);

        return bean;
    }

    private void checkBalanceOver(String id)
        throws MYException
    {
    	OutBalanceBean bean = outBalanceDAO.find(id);
    	
    	if (bean.getType() != OutConstant.OUTBALANCE_TYPE_SAILBACK)
    	{
    		// 检查是否结算溢出
            List<BaseBalanceBean> currentBaseList = baseBalanceDAO.queryEntityBeansByFK(id);

            // 看看是否溢出了
            for (BaseBalanceBean baseBalanceBean : currentBaseList)
            {
                BaseBean baseBean = baseDAO.find(baseBalanceBean.getBaseId());

                int total = baseBalanceBean.getAmount();
                List<BaseBalanceVO> hasPassBaseList = baseBalanceDAO
                    .queryPassBaseBalance(baseBalanceBean.getBaseId());

                for (BaseBalanceBean pss : hasPassBaseList)
                {
                    if ( !pss.getId().equals(baseBalanceBean.getId()))
                    {
                        total += pss.getAmount();
                    }
                }

                if (total > baseBean.getAmount())
                {
                    throw new MYException(baseBean.getProductName() + "的数量溢出");
                }
            }
    	}
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectOutBalance(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        OutBalanceBean bean = outBalanceDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getType() == OutConstant.OUTBALANCE_TYPE_SAIL)
        {
            if (bean.getStatus() != OutConstant.OUTBALANCE_STATUS_SUBMIT)
            {
                throw new MYException("结算中心通过的单子不能被驳回,请确认操作");
            }
        }
        else
        {
            if (bean.getStatus() != OutConstant.OUTBALANCE_STATUS_SUBMIT
                && bean.getStatus() != OutConstant.OUTBALANCE_STATUS_PASS)
            {
                throw new MYException("数据错误,请确认操作");
            }
        }

        int old = bean.getStatus();

        bean.setStatus(OutConstant.OUTBALANCE_STATUS_REJECT);

        bean.setReason(reason);

        outBalanceDAO.updateEntityBean(bean);

        addOutLog2(id, user, old, "驳回", SailConstant.OPR_OUT_REJECT,
            OutConstant.OUTBALANCE_STATUS_REJECT);

        notifyManager.notifyMessage(bean.getStafferId(), bean.getOutId() + "的结算清单已经被["
                                                         + user.getStafferName() + "]驳回");

        return true;
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean updateDistPrintCount(String id) throws MYException
    {
    	JudgeTools.judgeParameterIsNull(id);
    	
    	DistributionBean bean = distributionDAO.find(id);
    	
    	if (null != bean)
    	{
    		bean.setPrintCount(bean.getPrintCount() + 1);
    		
    		distributionDAO.updateEntityBean(bean);
    	}
    	
    	return true;
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
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
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
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
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
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

    private String getAll(int i)
    {
        String s = "00000000" + i;

        return s.substring(s.length() - 9);
    }

    private String getOutId(String idStr)
    {
        while (idStr.length() > 0 && idStr.charAt(0) == '0')
        {
            idStr = idStr.substring(1);
        }

        return idStr;
    }

    /**
     * @return the consignDAO
     */
    public ConsignDAO getConsignDAO()
    {
        return consignDAO;
    }

    /**
     * @param consignDAO
     *            the consignDAO to set
     */
    public void setConsignDAO(ConsignDAO consignDAO)
    {
        this.consignDAO = consignDAO;
    }

    /**
     * TODO 发送短信(只有销售单发送短信)(从短信接收模块来说需要修复回复短信处理结果)
     * 
     * @param out
     * @param user
     */
    private void notifyOut(OutBean out, User user, int type)
    {
        String stafferName = "";
        if (user == null){
            stafferName = "系统后台自动审批Job";
        } else{
            stafferName = user.getStafferName();
        }
        if (out.getType() == 1)
        {
            return;
        }

        NotifyBean notify = new NotifyBean();

        if (type == 0)
        {
            notify.setMessage(out.getFullId() + "已经被[" + stafferName + "]审批通过");
        }
        else if (type == 1)
        {
            notify.setMessage(out.getFullId() + "已经被[" + stafferName + "]驳回");
        }
        else if (type == 2)
        {
            notify.setMessage(out.getFullId() + "已经被[" + stafferName + "]确认付款");
        }
        else if (type == 3)
        {
            notify.setMessage(out.getFullId() + "已经被[" + stafferName + "]总部核对,单据流程结束");
        }
        else
        {
            notify.setMessage(out.getFullId() + "已经被[" + stafferName + "]处理");
        }

        notify.setUrl("../sail/out.do?method=findOut&fow=99&outId=" + out.getFullId());

        notifyManager.notifyWithoutTransaction(out.getStafferId(), notify);

        if (false)
        {
            // 0:保存 1:提交 2:驳回 3:发货 4:会计审核通过 6:总经理审核通过
            if (out.getStatus() == OutConstant.STATUS_SAVE
                || out.getStatus() == OutConstant.STATUS_REJECT
                || out.getStatus() == OutConstant.STATUS_SEC_PASS)
            {
                return;
            }

            // 发送短信给区域总经理审核
            if (out.getStatus() == OutConstant.STATUS_SUBMIT)
            {
                ConditionParse condtition = new ConditionParse();

                condtition.addCondition("locationId", "=", out.getLocationId());

                condtition.addIntCondition("status", "=", 0);

                condtition.addIntCondition("role", "=", 4);

                queryUserToSendSMS(out, user, condtition, "总经理审批");
            }

            // 发送短信给库管审核(非总部)
            if (out.getStatus() == OutConstant.STATUS_MANAGER_PASS
                && !"0".equals(out.getLocation()))
            {
                ConditionParse condtition = new ConditionParse();

                condtition.addCondition("locationId", "=", out.getLocationId());

                condtition.addIntCondition("status", "=", 0);

                condtition.addIntCondition("role", "=", 1);

                queryUserToSendSMS(out, user, condtition, "库管员审批");
            }
        }

    }

    /**
     * queryUserToSendSMS
     * 
     * @param out
     * @param user
     * @param condtition
     */
    private void queryUserToSendSMS(OutBean out, User user, ConditionParse condtition,
                                    String tokenName)
    {
        List<UserBean> userList = ListTools.distinct(userDAO
            .queryEntityBeansByCondition(condtition));

        for (UserBean baseUser : userList)
        {
            StafferBean sb = stafferDAO.find(baseUser.getStafferId());

            if (sb != null && !StringTools.isNullOrNone(sb.getHandphone()))
            {
                sendSMSInner(out, user, sb, tokenName);
            }
        }
    }

    /**
     * sendSMS
     * 
     * @param out
     * @param user
     * @param sb
     */
    private void sendSMSInner(OutBean out, User user, StafferBean sb, String tokenName)
    {
        StafferBean realStaffer = stafferDAO.find(out.getStafferId());

        if (realStaffer == null)
        {
            return;
        }

        // DBT 动态引入的判断
        if ( !DynamicBundleTools.isServiceExist("com.china.center.oa.note.dao.ShortMessageTaskDAO"))
        {
            return;
        }

        ShortMessageTaskDAO shortMessageTaskDAO = DynamicBundleTools
            .getService(ShortMessageTaskDAO.class);

        if (shortMessageTaskDAO == null)
        {
            return;
        }

        // TODO 恢复短信回复的处理 send short message
        ShortMessageTaskBean sms = new ShortMessageTaskBean();

        sms.setId(commonDAO.getSquenceString20());

        sms.setFk(out.getFullId());

        sms.setType(HandleMessage.TYPE_OUT);

        sms.setHandId(RandomTools.getRandomMumber(4));

        sms.setStatus(ShortMessageConstant.STATUS_INIT);

        sms.setMtype(ShortMessageConstant.MTYPE_ONLY_SEND_RECEIVE);

        sms.setFktoken(String.valueOf(out.getStatus()));

        sms.setMessage(realStaffer.getName() + "发起销售单[" + out.getDescription() + "(回款天数:"
                       + out.getReday() + ";总金额:" + MathTools.formatNum(out.getTotal()) + ")]"
                       + "需您审批(" + tokenName + ").0通过,1驳回.回复格式[" + sms.getHandId() + ":0]或["
                       + sms.getHandId() + ":1:理由]");

        sms.setReceiver(sb.getHandphone());

        sms.setStafferId(sb.getId());

        sms.setLogTime(TimeTools.now());

        // 24 hour
        sms.setEndTime(TimeTools.now(1));

        // internal
        sms.setSendTime(TimeTools.getDateTimeString(this.internal));

        // add sms
        shortMessageTaskDAO.saveEntityBean(sms);
    }

    public int findOutStatusInLog(String fullId)
    {
        // 获取日志，正排序
        List<FlowLogBean> logList = flowLogDAO.queryEntityBeansByFK(fullId);

        if (ListTools.isEmptyOrNull(logList))
        {
            return -1;
        }

        return logList.get(logList.size() - 1).getAfterStatus();
    }

    /**
     * 增加日志
     * 
     * @param fullId
     * @param user
     * @param outBean
     */
    private void addOutLog(final String fullId, final User user, final OutBean outBean, String des,
                           int mode, int astatus)
    {
        FlowLogBean log = new FlowLogBean();

        if (user == null){
            log.setActor("系统自动审批Job");
        }else{
            log.setActor(user.getStafferName());
        }

        log.setDescription(des);
        log.setFullId(fullId);
        log.setOprMode(mode);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(outBean.getStatus());

        log.setAfterStatus(astatus);

        flowLogDAO.saveEntityBean(log);
        this.log(log);
    }

    private void addOutLog2(final String fullId, final User user, final int preStatus, String des,
                            int mode, int astatus)
    {
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(des);
        log.setFullId(fullId);
        log.setOprMode(mode);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(preStatus);

        log.setAfterStatus(astatus);

        flowLogDAO.saveEntityBean(log);
        this.log(log);
    }

    private void addLog3(final String id, int preStatus, int afterStatus, int oprMode,String reason)
    {
        FlowLogBean log = new FlowLogBean();

        log.setActor("系统");

        log.setLogTime(TimeTools.now());

        log.setFullId(id);

        // 操作类型
        log.setOprMode(oprMode);

        log.setPreStatus(preStatus);

        log.setAfterStatus(afterStatus);

        log.setDescription(reason);

        flowLogDAO.saveEntityBean(log);
    }

    private void log(FlowLogBean log){
        FlowLogVO flowLogVO = FlowLogHelper.getOutLogVO(log);
        String message = String.format(log.getFullId()+" 审批人:%s 审批动作:%s 前状态:%s 后状态:%s 意见:%s",
                log.getActor(), flowLogVO.getOprModeName(), flowLogVO.getPreStatusName(), flowLogVO.getAfterStatusName(),
                flowLogVO.getDescription());
        _logger.info(message);
    }

    /**
     * loggerError(严重错误的日志哦)
     * 
     * @param msg
     */
    private void loggerError(String msg)
    {
        importLog.error(msg);

        fatalNotify.notify(msg);
    }
    
    /**
     * 
     * {@inheritDoc}
     */
    @Transactional(rollbackFor = MYException.class)
    public String statsAndAddRank(User user, List<OutBean> list, String batchId)
	throws MYException {
    	JudgeTools.judgeParameterIsNull(user, list);
    	
    	if (!StringTools.isNullOrNone(batchId))
    	{
    		List<StatsDeliveryRankBean> oldList = statsDeliveryRankDAO.queryEntityBeansByFK(batchId);
    		
    		statsDeliveryRankDAO.deleteEntityBeansByFK(batchId);
    		
    		for(StatsDeliveryRankBean each : oldList)
    		{
    			deliveryRankVSOutDAO.deleteEntityBeansByFK(each.getId());
    		}
    	}
    	
    	//根据客户 + 订单日期 + 商品名 生成数据
    	Map<String, StatsDeliveryRankBean> map = new HashMap<String, StatsDeliveryRankBean>();
    	
    	Map<String, Set<String>> vsMap = new HashMap<String, Set<String>>();
    	
    	for (OutBean each : list)
    	{
    		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(each.getFullId());
    		
    		for(BaseBean base : baseList)
    		{
    			String key = each.getCustomerId() + "~" + each.getOutTime() + "~" + base.getProductId();
    			
    			if (map.containsKey(key))
    			{
    				StatsDeliveryRankBean bean = map.get(key);
    				
    				bean.setAmount(bean.getAmount() + base.getAmount());
    				
    			}else{
    				StatsDeliveryRankBean bean = new StatsDeliveryRankBean();
    				
    				bean.setCustomerId(each.getCustomerId());
    				bean.setCustomerName(each.getCustomerName());
    				bean.setOutTime(each.getOutTime());
    				bean.setProductId(base.getProductId());
    				bean.setProductName(base.getProductName());
    				
    				bean.setAmount(base.getAmount());
    				
    				map.put(key, bean);
    			}
    			
    			if (vsMap.containsKey(key)){
    				Set<String> set = vsMap.get(key);
    				if (!set.contains(each.getFullId()))
    					set.add(each.getFullId());
    			}else{
    				Set<String> set = new HashSet<String>();
    				
    				set.add(each.getFullId());
    				
    				vsMap.put(key, set);
    			}
    		}
    	}
    	
    	String newBatchId = commonDAO.getSquenceString20();
    	
    	for(Entry<String, StatsDeliveryRankBean> entry : map.entrySet())
    	{
    		String key = entry.getKey();
    		
    		StatsDeliveryRankBean bean = entry.getValue();
    		
    		String id = commonDAO.getSquenceString();
    		
    		bean.setId(id);
    		bean.setBatchId(newBatchId);
    		
    		statsDeliveryRankDAO.saveEntityBean(bean);
    		
    		Set<String> set = vsMap.get(key);
    		
    		for(String outId : set)
    		{
    			DeliveryRankVSOutBean vsBean = new DeliveryRankVSOutBean();
    			
    			vsBean.setRefId(id);
    			
    			vsBean.setOutId(outId);
    			
    			deliveryRankVSOutDAO.saveEntityBean(vsBean);
    		}
    	}
    	
    	return newBatchId;
	}
    
    @Transactional(rollbackFor = MYException.class)
    public boolean updateStatsRank(String id, String type, String value)
	throws MYException
	{
    	JudgeTools.judgeParameterIsNull(id, type, value);
    	
    	StatsDeliveryRankBean bean = statsDeliveryRankDAO.find(id);
    	
    	if (null == bean)
    	{
    		throw  new MYException("数据错误");
    	}
    	
    	if (type.equals("humanSort"))
    	{
    		bean.setHumanSort(MathTools.parseInt(value));
    	}
    	
    	if (type.equals("baseOutTime"))
    	{
    		bean.setBaseOutTime(MathTools.parseInt(value));
    	}
    	
    	statsDeliveryRankDAO.updateEntityBean(bean);
    	
    	return true;
	}
    
    @Transactional(rollbackFor = MYException.class)
    public boolean sortsRank(User user, String batchId)
	throws MYException
	{
    	JudgeTools.judgeParameterIsNull(user, batchId);
    	
    	ConditionParse con = new ConditionParse();
    	
    	con.addWhereStr();
    	
    	con.addCondition("StatsDeliveryRankBean.batchId", "=", batchId);
    	
    	con.addCondition(" order by StatsDeliveryRankBean.outTime ASC");
    	
    	List<StatsDeliveryRankBean> list = statsDeliveryRankDAO.queryEntityBeansByCondition(con);
    	
    	if (ListTools.isEmptyOrNull(list))
    	{
    		throw  new MYException("数据错误");
    	}
    	
    	int rank = 0;
    	
    	int size = list.size();

    	// 1.排序- 第一次排序
    	for(StatsDeliveryRankBean each : list)
    	{
    		if (each.getHumanSort() == 1)
    		{
    			each.setRank(++rank);
    		}else if (each.getHumanSort() == 0 && each.getBaseOutTime() == 1)
    		{
    			// 7 个 9
    			each.setRank(9999999);
    		}
    		else
    		{
    			each.setRank(++size);
    		}
    	}

    	//第二次排序， 针对 按订单日期配送  要求客户当天所有订单须满足库存 - 确定最终是优先还是最后
    	Map<String,Boolean> map = new HashMap<String, Boolean>();
    	
    	for (StatsDeliveryRankBean each : list)
    	{
    		if (each.getRank() == 9999999){
    			String key = each.getCustomerId() + "~" + each.getOutTime();
    			if (map.containsKey(key))
    			{
    				continue;
    			}else{
    				// 同一客户 同一天的订单中所有产品库存检查
    				boolean flag = true;
    				for (StatsDeliveryRankBean eachb : list){
    					String key1 = eachb.getCustomerId() + "~" + eachb.getOutTime();
    					
    					if (key.equals(key1))
    					{
    						String productId = eachb.getProductId();
    						
    						int amount = eachb.getAmount();
    						
    						ProductChangeWrap wrap = new ProductChangeWrap();
    						
    						wrap.setDepotpartId(OutImportConstant.CITIC_DEPOTPART);
    			            wrap.setPrice(0.0d); // 导入订单时没有成本
    			            wrap.setProductId(productId);
    			            wrap.setStafferId("0"); // 公卖

    			            wrap.setChange( -amount);

    			            try{
    			            	storageRelationManager.checkStorageRelation2(wrap, false);	
    			            }catch(MYException e)
    			            {
    			            	// 库存不满足
    			            	flag = false;
    			            }
    			            
    						if (!flag)
    						{
    							break;
    						}
    					}
    				}
    				
    				// 记录库存是否满足，用于排序
    				map.put(key, flag);
    			}
    		}
    	}

    	for (Entry<String,Boolean> entry : map.entrySet())
    	{
    		String key = entry.getKey();
    		
    		Boolean flag = entry.getValue();
    		
    		for (StatsDeliveryRankBean each : list)
    		{
    			String key1 = each.getCustomerId() + "~" + each.getOutTime();
    			if (key.equals(key1))
    			{
    				if (flag)
    				{
    					each.setRank(++rank);
    				}else
    				{
    					each.setRank(++size);
    				}
    			}
    		}
    	}

    	// 综合排序，使序号连贯 从小到大
        Collections.sort(list, new Comparator<StatsDeliveryRankBean>() {
            public int compare(StatsDeliveryRankBean o1, StatsDeliveryRankBean o2) {
                return o1.getRank() - o2.getRank();
            }
        });
        
        int newRank = 0;
        
        for (StatsDeliveryRankBean each : list)
        {
        	each.setRank(++newRank);
        }
        
    	//=====sort end======
    	
    	// 2.检查库存。only for 中信导入订单 - 根据排序,依次检查库存,产品库存是否存在，排序在前则有，否则就没有
        Map<String,Integer> sMap = new HashMap<String,Integer>();
        
        for (StatsDeliveryRankBean each : list)
        {
        	if (sMap.containsKey(each.getProductId()))
        	{
        		int hasAmount = sMap.get(each.getProductId());
        		
        		if (hasAmount >= each.getAmount())
            	{
            		each.setEnoughStock(1); // 1 满足
            		
            		sMap.remove(each.getProductId());
            		
            		sMap.put(each.getProductId(), hasAmount - each.getAmount());
            	}else{
            		each.setEnoughStock(0); // 0 不满足
            	}
        		
        	}else
        	{
        		ProductChangeWrap wrap = new ProductChangeWrap();
    			
    			wrap.setDepotpartId(OutImportConstant.CITIC_DEPOTPART);
                wrap.setPrice(0.0d); // 导入订单时没有成本
                wrap.setProductId(each.getProductId());
                wrap.setStafferId("0"); // 公卖

                wrap.setChange( -each.getAmount());

                List<StorageRelationBean> srList = null;
                try{
                	srList = storageRelationManager.checkStorageRelation2(wrap, false);	
                }catch(MYException e)
                {
                	//
                }
                
                if (ListTools.isEmptyOrNull(srList))
                {
                	each.setEnoughStock(0);
                	
                	sMap.put(each.getProductId(), 0);
                }else{
                	int hasAmount = 0;
                	for(StorageRelationBean eachsr : srList)
                	{
                		hasAmount += eachsr.getAmount();
                	}
                	
                	if (hasAmount >= each.getAmount())
                	{
                		each.setEnoughStock(1); // 1 满足
                		sMap.put(each.getProductId(), hasAmount - each.getAmount());
                	}else{
                		each.setEnoughStock(0); // 0 不满足
                		
                		sMap.put(each.getProductId(), hasAmount);
                	}
                }
        	}
        }

        // 将结果存盘 
        statsDeliveryRankDAO.updateAllEntityBeans(list);
        
        return true;
	}
    
    /**
     * 批量审批通过
     * {@inheritDoc}
     */
    public String passSortRank(User user, String batchId)
		throws MYException
	{
    	JudgeTools.judgeParameterIsNull(batchId);
    	
    	StringBuffer builder = new StringBuffer();
    	
    	List<StatsDeliveryRankBean> list = statsDeliveryRankDAO.queryEntityBeansByFK(batchId);
    	
    	for (StatsDeliveryRankBean each : list)
    	{
    		if (each.getEnoughStock() == 0)
    			continue;
    		
    		List<DeliveryRankVSOutBean> vsList = deliveryRankVSOutDAO.queryEntityBeansByFK(each.getId());
    		
    		for (DeliveryRankVSOutBean eachVS : vsList)
    		{
    			OutBean out = outDAO.find(eachVS.getOutId());
    			
    			if (null == out)
    			{
    				builder.append("单号:" + eachVS.getOutId() + " 不存在").append("<br>");
    				continue;
    			}else{
    				if (out.getStatus() != OutConstant.STATUS_SUBMIT){
    					builder.append("单号:" + eachVS.getOutId() + " 当前不待商务审核态").append("<br>");
    					continue;
    				}
    				else
    				{
    					try{
    						this.pass(out.getFullId(), user, OutConstant.STATUS_FLOW_PASS, "根据系统排名,审批通过", null,"");
    						
    					}catch(MYException e)
    					{
    						builder.append("单号:" + eachVS.getOutId() + " 审批异常，原因：" + e.getErrorContent()).append("<br>");	
    					}
    				}
    			}
    		}
    	}
    	
    	return builder.toString();
	}

    @Transactional(rollbackFor = MYException.class)
    public boolean undoSortRank(String batchId)
		throws MYException
	{
    	JudgeTools.judgeParameterIsNull(batchId);
    	
    	List<StatsDeliveryRankBean> list = statsDeliveryRankDAO.queryEntityBeansByFK(batchId);
    	
    	for (StatsDeliveryRankBean each : list)
    	{
    		statsDeliveryRankDAO.deleteEntityBean(each.getId());
    		
    		deliveryRankVSOutDAO.deleteEntityBeansByFK(each.getId());
    	}
    	
    	return true;
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
     * @return the internal
     */
    public int getInternal()
    {
        return internal;
    }

    /**
     * @param internal
     *            the internal to set
     */
    public void setInternal(int internal)
    {
        this.internal = internal;
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
     * @return the creditLevelDAO
     */
    public CreditLevelDAO getCreditLevelDAO()
    {
        return creditLevelDAO;
    }

    /**
     * @param creditLevelDAO
     *            the creditLevelDAO to set
     */
    public void setCreditLevelDAO(CreditLevelDAO creditLevelDAO)
    {
        this.creditLevelDAO = creditLevelDAO;
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
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO()
    {
        return baseDAO;
    }

    /**
     * @param baseDAO
     *            the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO)
    {
        this.baseDAO = baseDAO;
    }

    /**
     * @return the creditCoreDAO
     */
    public CreditCoreDAO getCreditCoreDAO()
    {
        return creditCoreDAO;
    }

    /**
     * @param creditCoreDAO
     *            the creditCoreDAO to set
     */
    public void setCreditCoreDAO(CreditCoreDAO creditCoreDAO)
    {
        this.creditCoreDAO = creditCoreDAO;
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
     * @return the transactionManager
     */
    public PlatformTransactionManager getTransactionManager()
    {
        return transactionManager;
    }

    /**
     * @param transactionManager
     *            the transactionManager to set
     */
    public void setTransactionManager(PlatformTransactionManager transactionManager)
    {
        this.transactionManager = transactionManager;
    }

    /**
     * @return the storageRelationManager
     */
    public StorageRelationManager getStorageRelationManager()
    {
        return storageRelationManager;
    }

    /**
     * @param storageRelationManager
     *            the storageRelationManager to set
     */
    public void setStorageRelationManager(StorageRelationManager storageRelationManager)
    {
        this.storageRelationManager = storageRelationManager;
    }

    /**
     * @return the outUniqueDAO
     */
    public OutUniqueDAO getOutUniqueDAO()
    {
        return outUniqueDAO;
    }

    /**
     * @param outUniqueDAO
     *            the outUniqueDAO to set
     */
    public void setOutUniqueDAO(OutUniqueDAO outUniqueDAO)
    {
        this.outUniqueDAO = outUniqueDAO;
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
     * @return the notifyManager
     */
    public NotifyManager getNotifyManager()
    {
        return notifyManager;
    }

    /**
     * @param notifyManager
     *            the notifyManager to set
     */
    public void setNotifyManager(NotifyManager notifyManager)
    {
        this.notifyManager = notifyManager;
    }

    /**
     * @return the fatalNotify
     */
    public FatalNotify getFatalNotify()
    {
        return fatalNotify;
    }

    /**
     * @param fatalNotify
     *            the fatalNotify to set
     */
    public void setFatalNotify(FatalNotify fatalNotify)
    {
        this.fatalNotify = fatalNotify;
    }

    /**
     * @return the baseBalanceDAO
     */
    public BaseBalanceDAO getBaseBalanceDAO()
    {
        return baseBalanceDAO;
    }

    /**
     * @param baseBalanceDAO
     *            the baseBalanceDAO to set
     */
    public void setBaseBalanceDAO(BaseBalanceDAO baseBalanceDAO)
    {
        this.baseBalanceDAO = baseBalanceDAO;
    }

    /**
     * @return the outBalanceDAO
     */
    public OutBalanceDAO getOutBalanceDAO()
    {
        return outBalanceDAO;
    }

    /**
     * @param outBalanceDAO
     *            the outBalanceDAO to set
     */
    public void setOutBalanceDAO(OutBalanceDAO outBalanceDAO)
    {
        this.outBalanceDAO = outBalanceDAO;
    }

    /**
     * CORE 处理销售单的通过
     * 
     * @param fullId
     * @param user
     * @param outBean
     * @param depot
     * @param newNextStatus
     */
    private void handerPassOut(final String fullId, final User user, final OutBean outBean,
                               final DepotBean depot, int newNextStatus)
    {
        // 从分公司经理审核通过到提交
        if (newNextStatus == OutConstant.STATUS_SUBMIT)
        {
            // 只有信用已经超支的情况下才启用分事业部经理的信用
            if (outBean.getReserve2() == OutConstant.OUT_CREDIT_OVER)
            {
                if (user == null){
                    _logger.info(fullId+"票随货发Job系统用户handerPassOut*****");
                }

                //TODO
                // 加入审批人的信用(是自己使用的信用+担保的信用)
                double noPayBusinessByManager = outDAO.sumAllNoPayAndAvouchBusinessByStafferId(user
                    .getStafferId(), outBean.getIndustryId(), YYTools.getStatBeginDate(), YYTools
                    .getStatEndDate());


                StafferBean staffer = stafferDAO.find(user.getStafferId());

                // 这里自己不能给自己担保的
                if (outBean.getStafferId().equals(user.getStafferId()))
                {
                    throw new RuntimeException("事业部经理担保中,自己不能给自己担保");
                }

                // 事业部经理的信用
                double industryIdCredit = getIndustryIdCredit(outBean.getIndustryId(), staffer
                    .getId())
                                          * staffer.getLever();

                // 这里分公司总经理的信用已经使用结束了,此时直接抛出异常
                if (noPayBusinessByManager > industryIdCredit)
                {
                    throw new RuntimeException("您的信用额度已经全部占用[使用了"
                                               + MathTools.formatNum(noPayBusinessByManager)
                                               + "],不能再担保业务员的销售");
                }

                // 本次需要担保的信用
                double lastCredit = outBean.getTotal() - outBean.getStaffcredit()
                                    - outBean.getCurcredit();

                if ( (lastCredit + noPayBusinessByManager) > industryIdCredit)
                {
                    throw new RuntimeException("您杠杆后的信用额度是["
                                               + MathTools.formatNum(industryIdCredit) + "],已经使用了["
                                               + MathTools.formatNum(noPayBusinessByManager)
                                               + "],本单需要您担保的额度是[" + MathTools.formatNum(lastCredit)
                                               + "],加上本单已经超出您的最大额度,不能再担保业务员的销售");
                }

                // 这里使用分公司经理信用担保
                outDAO.updateManagercredit(outBean.getFullId(), user.getStafferId(), lastCredit);

                // 此时信用不超支了
                outDAO.updateOutReserve(fullId, OutConstant.OUT_CREDIT_COMMON, outBean
                    .getReserve6());
            }

            try
            {
                checkCoreStorage(outBean, true);
            }
            catch (MYException e)
            {
                throw new RuntimeException(e.getErrorContent(), e);
            }
        }

        // 结算中心通过/总裁通过 修改manager的入库时间
        if (newNextStatus == OutConstant.STATUS_MANAGER_PASS)
        {
            outDAO.modifyManagerTime(outBean.getFullId(), TimeTools.now());

            // 验证是否是款到发货
            if (outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_MONEY
                && outBean.getPay() != OutConstant.PAY_YES)
            {
                throw new RuntimeException("此单据是款到发货,当前此单未付款,不能通过");
            }

            try
            {
                checkCoreStorage(outBean, true);
            }
            catch (MYException e)
            {
                throw new RuntimeException(e.getErrorContent(), e);
            }
        }

        // 结算中心审核通过后/总裁通过，中心仓库的销售单转到物流管理员，同时自动生成发货单
//        if (newNextStatus == OutConstant.STATUS_MANAGER_PASS
//            && depot.getType() == DepotConstant.DEPOT_TYPE_CENTER)
        if (newNextStatus == OutConstant.STATUS_FLOW_PASS)
        {
            // 验证是否是款到发货
            if (outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_MONEY
                && outBean.getPay() != OutConstant.PAY_YES)
            {
                throw new RuntimeException("此单据是款到发货,当前此单未付款,不能通过");
            }
        	
        	List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(outBean.getFullId());
        	
        	if (ListTools.isEmptyOrNull(distList))
        	{
                ConsignBean bean = new ConsignBean();

                bean.setCurrentStatus(SailConstant.CONSIGN_PASS);

                bean.setGid(commonDAO.getSquenceString20());

                bean.setFullId(outBean.getFullId());

                bean.setArriveDate(outBean.getArriveDate());

                consignDAO.addConsign(bean);
        	}else{
        		
        		for(DistributionBean each : distList)
        		{
        			ConsignBean hbean = consignDAO.findDefaultConsignByDistId(each.getId());
        			
        			if (null == hbean)
        			{
        				ConsignBean bean = new ConsignBean();

                        bean.setCurrentStatus(SailConstant.CONSIGN_PASS);

                        bean.setGid(commonDAO.getSquenceString20());
                        
                        bean.setDistId(each.getId());

                        bean.setFullId(outBean.getFullId());

                        bean.setArriveDate(outBean.getArriveDate());
                        
                        if (each.getShipping() == OutConstant.OUT_SHIPPING_SELFSERVICE)
                        {
                        	bean.setReveiver(outBean.getStafferName());
                        }else
                        {
                        	bean.setReveiver(each.getReceiver());
                        }

                        consignDAO.addConsign(bean);
        			}
        		}
        	}

            try
            {
                checkCoreStorage(outBean, true);
            }
            catch (MYException e)
            {
                throw new RuntimeException(e.getErrorContent(), e);
            }
        }

        // CORE 需要把回款日志敲定且变动销售库存(库管通过)
        if (newNextStatus == OutConstant.STATUS_PASS)
        {
            // 只有未付款的时候才有
            if (outBean.getPay() == OutConstant.PAY_NOT)
            {
                long add = outBean.getReday() * 24 * 3600 * 1000L;

                // 这里需要把出库单的回款日期修改
                outDAO.modifyReDate(fullId, TimeTools.getStringByFormat(new Date(new Date()
                    .getTime()
                                                                                 + add),
                    "yyyy-MM-dd"));
            }

            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

            try
            {
                // 变动库存
                processBuyAndSailPass(user, outBean, baseList, StorageConstant.OPR_STORAGE_OUTBILL);
            }
            catch (MYException e)
            {
                throw new RuntimeException(e.getErrorContent(), e);
            }
            
            // 针对赠送单自动核对已付款
            if (outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
            {
            	//savePayTagBean(outBean);
                
                // 修改付款标识
                outDAO.updatePay(fullId, OutConstant.PAY_YES);
            }
            
            // 销售单库管通过后,向发货预处理中写入此单据 -- 领样转销售的除外
            createPackage(outBean);
            
            // 针对紫金农商的订单通过时要更新紫金农商订单的行项目状态(全部库管通过后,将状态置 为 配送中)
            if (outBean.getFlowId().startsWith("ZJRC")) {
            	String refBaseId = baseList.get(0).getRefId();
            	
            	zjrcManager.modifyStatusWithoutTrans(outBean.getRefOutFullId(), refBaseId);
            }
        }
    }

	public void createPackage(final OutBean outBean)
{
    _logger.info("准备生成CK单:"+outBean);
    if (StringTools.isNullOrNone(outBean.getRefOutFullId())
            //ZS单也需要生成CK单
            || outBean.getFullId().startsWith("ZS"))
    {
        List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(outBean.getFullId());
        if (!ListTools.isEmptyOrNull(distList))
        {
            DistributionBean dist = distList.get(0);

            if (dist.getShipping() == OutConstant.OUT_SHIPPING_SELFSERVICE)
            {
                PreConsignBean preConsign = new PreConsignBean();

                preConsign.setOutId(outBean.getFullId());

                try{
                    _logger.info("自提单据生成CK单:"+outBean.getFullId());
                    OutVO vo = outDAO.findVO(outBean.getFullId());

                    shipManager.createPackage(preConsign, vo);
                }
                catch (MYException e)
                {
                    throw new RuntimeException(e.getErrorContent(), e);
                }
            }else
            {
                DepotBean depot =  this.depotDAO.find(outBean.getLocation());
                if (depot != null &&
                        ("99".equals(depot.getIndustryId2()) || outBean.isFrFlag())){
                    PreConsignBean preConsign = new PreConsignBean();

                    preConsign.setOutId(outBean.getFullId());

                    preConsignDAO.saveEntityBean(preConsign);
                    this.logPreconsign(preConsign);
                } else{
                    _logger.warn(outBean.getFullId()+"not create PreConsignBean****");
                }
            }
        } else {
            _logger.info("****distList not found 2222222222222222222222222222****");
        }
    }
}

    private void logPreconsign(PreConsignBean preConsignBean){
        _logger.info("写入preconsign表:"+preConsignBean.getOutId());
    }

    /** #169
     * create package for invoice bean related with OutBean
     * @param outBean
     */
    private void createPackageForInvoice(final OutBean outBean)
    {
        String fullId = outBean.getFullId();

        //SO单库管审核通过时，需要检查临时表，有的话就写入两条preconsign，并且把临时表删除
        if (!StringTools.isNullOrNone(fullId)){
            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addWhereStr();
            conditionParse.addCondition("outId","like","%"+fullId+"%");
            List<TempConsignBean> tempConsignBeans = this.tempConsignDAO.queryEntityBeansByCondition(conditionParse);
            List<String> ids = new ArrayList<String>();
            if(!ListTools.isEmptyOrNull(tempConsignBeans)){
               for(TempConsignBean bean : tempConsignBeans){
                   String insId = bean.getInsId();
                   PreConsignBean preConsign = new PreConsignBean();
                   preConsign.setOutId(insId);
                   preConsignDAO.saveEntityBean(preConsign);
                   _logger.info(fullId+"对应的发票"+insId+"写入preconsign表");
                   this.logPreconsign(preConsign);
                   ids.add(bean.getId());
               }
            }

            if(!ListTools.isEmptyOrNull(ids)){
                _logger.info("***tempConsignDAO.deleteByIds****"+ids);
                this.tempConsignDAO.deleteByIds(ids);
            }
        }
    }

    /**
     * 拆分行项目
     * 
     * @param baseList
     * @return
     * @throws MYException
     */
	public List<BaseBean> splitBase(List<BaseBean> baseList) throws MYException
    {
		List<BaseBean> newBaseList = new ArrayList<BaseBean>();
		
		for  (BaseBean element : baseList)
		{
            ProductChangeWrap wrap = new ProductChangeWrap();

            wrap.setDepotpartId(element.getDepotpartId());
            wrap.setPrice(element.getCostPrice());
            wrap.setProductId(element.getProductId());
            wrap.setStafferId(element.getOwner());

            wrap.setChange( -element.getAmount());

            List<StorageRelationBean> relationList = storageRelationManager.checkStorageRelation2(wrap, false);
            
            // 足够库存，开始拆分
            if (!ListTools.isEmptyOrNull(relationList))
            {
            	int amount = element.getAmount();
            	
            	for (StorageRelationBean each : relationList)
            	{
            		if (each.getAmount() <= 0)
            			continue;
            		
            		if (each.getAmount() < amount)
            		{
            			BaseBean newBase = new BaseBean();
            			
            			BeanUtil.copyProperties(newBase, element);
            			
            			newBase.setId(commonDAO.getSquenceString());
            			
            			newBase.setAmount(each.getAmount());
            			
            			newBase.setValue(each.getAmount() * newBase.getPrice());
            			
            			newBase.setCostPrice(each.getPrice());
            			
            			newBase.setCostPriceKey(each.getPriceKey());

            			//#545
            			newBase.setVirtualPrice(each.getVirtualPrice());
            			newBase.setVirtualPriceKey(each.getVirtualPriceKey());
            			if (element.getInvoiceMoney() > 0) {
            				newBase.setInvoiceMoney(newBase.getValue());
            			}
            			
            			newBaseList.add(newBase);
            			
            			amount -= each.getAmount();
            		}
            		else
            		{
            			BaseBean newBase = new BaseBean();
            			
            			BeanUtil.copyProperties(newBase, element);
            			
            			newBase.setId(commonDAO.getSquenceString());
            			
            			newBase.setAmount(amount);
            			
            			newBase.setValue(amount * newBase.getPrice());
            			
            			newBase.setCostPrice(each.getPrice());
            			
            			newBase.setCostPriceKey(each.getPriceKey());

                        //#545
                        newBase.setVirtualPrice(each.getVirtualPrice());
                        newBase.setVirtualPriceKey(each.getVirtualPriceKey());
            			
            			if (element.getInvoiceMoney() > 0) {
            				newBase.setInvoiceMoney(newBase.getValue());
            			}
            			
            			newBaseList.add(newBase);
            			
            			amount = 0;
            			
            			break;
            		}
            	}
            	
            	if (amount != 0)
            	{
            		throw new MYException("拆分库单行项目出错，库存不足");
            	}
            }
            else
            {
            	throw new MYException("拆分库单行项目出错");
            }
		}
		
		return newBaseList;
    }
    
    /**
     * CORE 处理入库单的流程
     * 
     * @param fullId
     * @param user
     * @param outBean
     * @param newNextStatus
     */
    private void handerPassBuy(final String fullId, final User user, final OutBean outBean,
                               int newNextStatus)
    {
        // 退库-事业部经理审批
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
            && (outBean.getOutType() == OutConstant.OUTTYPE_IN_SWATCH 
            	|| outBean.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK
            	|| outBean.getOutType() == OutConstant.OUTTYPE_IN_PRESENT
            	|| outBean.getBuyReturnFlag() == 1))
        {
            // 不处理
            return;
        }

        // 分公司总经理审批-->待总裁审批
        if (newNextStatus == OutConstant.STATUS_CEO_CHECK)
        {
            try
            {
                checkCoreStorage(outBean, true);
            }
            catch (MYException e)
            {
                throw new RuntimeException(e.getErrorContent(), e);
            }
        }

        // 待总裁审批-->待董事长审批
        if (newNextStatus == OutConstant.STATUS_CHAIRMA_CHECK)
        {
            try
            {
                checkCoreStorage(outBean, true);
            }
            catch (MYException e)
            {
                throw new RuntimeException(e.getErrorContent(), e);
            }
        }

        // CORE 待董事长审批-->发货
        if (newNextStatus == OutConstant.STATUS_PASS)
        {
            long add = outBean.getReday() * 24 * 3600 * 1000L;

            // 这里需要把出库单的回款日期修改
            outDAO.modifyReDate(fullId, TimeTools.getStringByFormat(new Date(new Date().getTime()
                                                                             + add), "yyyy-MM-dd"));

            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

            try
            {
                // 变动库存
                processBuyAndSailPass(user, outBean, baseList, StorageConstant.OPR_STORAGE_INOTHER);
            }
            catch (MYException e)
            {
                throw new RuntimeException(e.getErrorContent(), e);
            }
        }
    }

    /**
     * 检查是否可以转销售(入数据库但是没有提交)
     * 
     * @param outId
     * @return
     * @throws MYException
     */
    public boolean checkSwithToSail(String outId)
        throws MYException
    {
        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);

        // 验证ref
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", outId);

        con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

        // 包括保存的,防止溢出
        List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

        con.clear();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", outId);

        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

        // 排除其他入库(对冲单据)
        con.addIntCondition("OutBean.outType", "<>", OutConstant.OUTTYPE_IN_OTHER);

        // 包括保存的,防止溢出
        List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

        // 计算出已经退货的数量
        for (Iterator iterator = baseList.iterator(); iterator.hasNext();)
        {
            BaseBean baseBean = (BaseBean)iterator.next();

            int hasBack = 0;

            // 退库
            Set<String> backSet = new HashSet<String>();
            for (OutBean ref : refBuyList)
            {
                List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

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

//                List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());
//
//                for (BaseBean refBase : refBaseList)
//                {
//                    if (refBase.equals(baseBean))
//                    {
//                        hasBack += refBase.getAmount();
//
//                        break;
//                    }
//                }
            }

            baseBean.setAmount(baseBean.getAmount() - hasBack);

            if (baseBean.getAmount() < 0)
            {
                _logger.error("***baseBean.getAmount()***"+baseBean.getAmount()+"***hasBack***"+hasBack);
                throw new MYException("领样转销售或者退库数量不够[%s],请重新操作", baseBean.getProductName());
            }
        }

        return true;
    }

    /**
     * 检查领样转销售(含领样退库)是否溢出,同时检查是否全部转销售(或退库) - 转销售与领样退货时均要检查
     * @param outId
     * @return
     * @throws MYException
     */
    public boolean checkIfAllSwithToSail(String outId)
    throws MYException
    {
        boolean ret = true;
        
        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);

        // 验证ref
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", outId);

        con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
        
        con.addCondition(" and OutBean.status in (3, 4)");

        // 包括保存的,防止溢出
        List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);

        con.clear();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", outId);

        con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

        con.addCondition(" and OutBean.status in (3, 4)");
        
        // 排除其他入库(对冲单据)
        con.addIntCondition("OutBean.outType", "<>", OutConstant.OUTTYPE_IN_OTHER);

        // 包括保存的,防止溢出
        List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

        // 计算出已经退货的数量
        for (Iterator iterator = baseList.iterator(); iterator.hasNext();)
        {
            BaseBean baseBean = (BaseBean)iterator.next();

            int hasBack = 0;

            // 退库
            Set<String> backSet = new HashSet<String>();
            for (OutBean ref : refBuyList)
            {
                List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

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
            }

            baseBean.setAmount(baseBean.getAmount() - hasBack);

            if (baseBean.getAmount() < 0)
            {
                _logger.error("***baseBean.getAmount()***"+baseBean.getAmount()+"***hasBack***"+hasBack);
                throw new MYException("领样转销售或者退库数量不够[%s],请重新操作", baseBean.getProductName());
            }
            
            if (baseBean.getAmount() > 0) {
                _logger.info(baseBean+" checkIfAllSwithToSail hasBack "+hasBack);
                ret = false;
            }
        }

        return ret;
    }
    
    /**
     * 
     */
   public boolean checkOutBack(String outId) throws MYException
	{
        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);

        List<OutBean> refBuyList = queryRefOut1(outId, true);

        int amount = 0;
        int hasBack = 0;
        // 计算出已经退货的数量
        for (BaseBean baseBean : baseList)
        {
            // 退库
            for (OutBean ref : refBuyList)
            {
                List<BaseBean> refBaseList = ref.getBaseList();

                for (BaseBean refBase : refBaseList)
                {
                    if (refBase.equals2(baseBean))
                    {
                        hasBack += refBase.getAmount();
                    }
                }
            }

            amount += baseBean.getAmount();
        }

        if (amount - hasBack < 0)
        {
        	throw new MYException("[%s]退库数量溢出,请重新操作", outId);
        }

        return true;
    }

    /**
     * #880 确认退货无法控制可退数量
     * @param outId
     * @return
     * @throws MYException
     */
//    public boolean checkOutBack(String outId) throws MYException
//    {
//        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);
//        for (BaseBean baseBean: baseList){
//            boolean result = this.checkOutBack(outId, baseBean.getProductId(), baseBean.getCostPriceKey(), baseBean.getAmount());
//            if (result){
//                throw new MYException("[%s]退库数量溢出,请重新操作", outId);
//            }
//        }
//
//        return true;
//    }

    @Override
    public boolean checkOutBack(String outId, String productId, String priceKey, int amount) throws MYException{
        _logger.info("***checkOutBack***"+outId+"***"+productId+"**priceKey***"+priceKey+"***amount**"+amount);
        if (StringTools.isNullOrNone(priceKey)) {
            throw new MYException("[%s]成本不能为空", outId);
        } else if (StringTools.isNullOrNone(productId)){
            throw new MYException("[%s]退货商品名错误", outId);
        }
        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outId);

        //先检查原单是否有该成本
        boolean hasPriceKey = false;
        if (!ListTools.isEmptyOrNull(baseList)){
            for (BaseBean baseBean: baseList){
                if (baseBean.getProductId().equals(productId) && baseBean.getCostPriceKey().equals(priceKey)){
                    hasPriceKey = true;
                    break;
                }
            }
        }

        if (!hasPriceKey){
            throw new MYException("[%s]没有成本为[%s]的商品", outId, priceKey);
        }

        List<OutBean> refBuyList = queryRefOut1(outId, true);

        // 计算出已经退货的数量
        for (BaseBean baseBean : baseList)
        {
//            int hasBack = 0;
            int hasBack = this.outDAO.sumHasBack2(baseBean.getOutId(), baseBean.getProductName());
            // 退库
            for (OutBean ref : refBuyList)
            {
                List<BaseBean> refBaseList = ref.getBaseList();

                for (BaseBean refBase : refBaseList)
                {
                    if (refBase.equals2(baseBean))
                    {
                        hasBack += refBase.getAmount();
                    }
                }
            }

            _logger.info(baseBean+"***hasBack***"+hasBack);
            if (baseBean.getProductId().equals(productId) && baseBean.getCostPriceKey().equals(priceKey)
                    && (hasBack + amount > baseBean.getAmount())){
                _logger.error("outId exceed amount:"+outId);
                return true;
            }
        }

        return false;
    }

    private double getIndustryIdCredit(String industryId, String managerStafferId)
    {
        List<InvoiceCreditBean> inList = invoiceCreditDAO.queryEntityBeansByFK(managerStafferId);

        for (InvoiceCreditBean invoiceCreditBean : inList)
        {
            if (invoiceCreditBean.getInvoiceId().equals(industryId))
            {
                return invoiceCreditBean.getCredit();
            }
        }

        return 0.0d;
    }

    /**
     * @return the invoiceCreditDAO
     */
    public InvoiceCreditDAO getInvoiceCreditDAO()
    {
        return invoiceCreditDAO;
    }

    /**
     * @param invoiceCreditDAO
     *            the invoiceCreditDAO to set
     */
    public void setInvoiceCreditDAO(InvoiceCreditDAO invoiceCreditDAO)
    {
        this.invoiceCreditDAO = invoiceCreditDAO;
    }

    /**
     * handlerMoveOutBack
     * 
     * @param fullId
     * @param user
     * @param outBean
     */
    private void handlerMoveOutBack(final String fullId, final User user, final OutBean outBean)
    {
        // 这里的回滚先把入库单结束掉(就是把在途状态修改)
        OutBean newOut = new OutBean(outBean);

        newOut.setStatus(0);

        newOut.setLocationId(user.getLocationId());

        // 自己调入自己
        newOut.setDestinationId(outBean.getLocation());

        newOut.setOutType(OutConstant.OUTTYPE_IN_MOVEOUT);

        newOut.setFullId("");

        newOut.setRefOutFullId(fullId);

        newOut.setDestinationId(outBean.getLocation());

        newOut.setDescription("驳回调拨单自动回滚:" + fullId + ".生成的回滚单据");

        newOut.setInway(OutConstant.IN_WAY_NO);

        if (outBean.getCheckStatus() == PublicConstant.CHECK_STATUS_INIT)
        {
            newOut.setChecks("原调拨单据还未核对,系统自动核对回滚单据");

            newOut.setCheckStatus(PublicConstant.CHECK_STATUS_END);
        }
        else
        {
            newOut.setChecks("");

            newOut.setCheckStatus(PublicConstant.CHECK_STATUS_INIT);
        }

        newOut.setReserve1(OutConstant.MOVEOUT_ROLLBACK);

        newOut.setPay(OutConstant.PAY_NOT);

        newOut.setTotal( -newOut.getTotal());

        List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);

        for (BaseBean baseBean : baseList)
        {
            baseBean.setValue( -baseBean.getValue());
            baseBean.setLocationId(outBean.getDestinationId());
            baseBean.setAmount( -baseBean.getAmount());
        }

        List<BaseBean> lastList = OutHelper.trimBaseList(baseList);

        newOut.setBaseList(lastList);

        // 直接入库
        try
        {
            coloneOutAndSubmitWithOutAffair(newOut, user,
                StorageConstant.OPR_STORAGE_REDEPLOY_ROLLBACK);

            if (outBean.getCheckStatus() == PublicConstant.CHECK_STATUS_INIT)
            {
                outDAO.modifyOutStatus(newOut.getFullId(), OutConstant.STATUS_SEC_PASS);
            }
        }
        catch (MYException e)
        {
            _logger.error(e, e);

            throw new RuntimeException(e.getErrorContent(), e);
        }
    }

    /**
     * 
     * {@inheritDoc}
     */
    public String [] splitOut(final String id) throws MYException
    {
    	JudgeTools.judgeParameterIsNull(id);
    	
    	TransactionTemplate tran = new TransactionTemplate(transactionManager);
    	
    	String [] ids = null;
    	
        try
        {
            ids = (String [])tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	OutBean out = outDAO.find(id);
                	
                	if (null == out)
                	{
                		throw new RuntimeException("数据错误，单据不存");
                	}
                	
                	boolean isOut = (out.getType() == OutConstant.OUT_TYPE_OUTBILL) ? true : false;
                	
                	List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(id);
                	
                	List<DistributionBean> distBeanList = distributionDAO.queryEntityBeansByFK(id);
                	
                	List<AttachmentBean> attachmentBeanList = attachmentDAO.queryEntityBeansByFK(id);
                	
                	DistributionBean distBean = null;
                	
                	if (!ListTools.isEmptyOrNull(distBeanList))
                	{
                		distBean = distBeanList.get(0);
                	}
                	
                	// 根据base中的仓库， 管理类型，旧货/非旧货 进行拆单
                	Map<String, List<BaseBean>> map = new HashMap<String, List<BaseBean>>();
                	
                	for (BaseBean each : baseList)
                	{
                		String key = "";
                		
                		if (isOut) {
                			key = each.getLocationId() + "-" + each.getMtype() + "-" + each.getOldGoods() + "-" + MathTools.formatNum2(each.getTaxrate());	
                		} else {
                			key = each.getMtype() + "-" + each.getOldGoods();
                		}
                		
                		if (map.containsKey(key))
                		{
                			List<BaseBean> mBaseList = map.get(key);
                			
                			mBaseList.add(each);
                		}
                		else
                		{
                			List<BaseBean> mBaseList = new ArrayList<BaseBean>();
                			
                			mBaseList.add(each);
                			
                			map.put(key, mBaseList);
                		}
                	}
                	
                	String [] idss = new String[map.values().size()];
                	
                	int i = 0;
                	
                	// 将要拆成如下张单据
                	for (List<BaseBean> eachList :  map.values())
                	{
                		String newOutId = "";
                		double total = 0.0d;
                		double taxTotal = 0.0d;
                		String location = "";
                		String dutyId = "";
                		int mtype = 0;
                		
                		OutBean newOutBean = new OutBean();
                		
                		BeanUtil.copyProperties(newOutBean, out);
                		
                    	String id = getAll(commonDAO.getSquence());

                        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

                        String flag = OutHelper.getSailHead(out.getType(), out.getOutType());
                        
                        newOutId = flag + time + id;

                        newOutBean.setId(getOutId(id));
                    	
                    	newOutBean.setFullId(newOutId);
                		
                    	List<BaseBean> newBaseList = new ArrayList<BaseBean>();
                    	
                		for (BaseBean each : eachList)
                		{
                			BaseBean newbaseBean = new BaseBean();
                			
                			BeanUtil.copyProperties(newbaseBean, each);
                			
                			newbaseBean.setId(commonDAO.getSquenceString());
                			
                			newbaseBean.setOutId(newOutId);
                			
                			newBaseList.add(newbaseBean);
                			
                			location = each.getLocationId();
                			
                			total += each.getValue();
                			taxTotal += each.getTax();
                			
                			if (isOut) {
                				if (each.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
                    			{
                    				dutyId = PublicConstant.DEFAULR_DUTY_ID;
                    			}
                    			else
                    			{
                    				dutyId = PublicConstant.MANAGER2_DUTY_ID;
                    			}
                			} else {
                				dutyId = out.getDutyId();
                			}
                			
                			mtype = each.getMtype();
                		}
                		
                		//handle attachments #888
                		List<AttachmentBean> newAttachmentBeanList = new ArrayList<AttachmentBean>();
                		for(AttachmentBean attachmentBean : attachmentBeanList){
                			
                			attachmentBean.setId(commonDAO.getSquenceString20());
                			attachmentBean.setRefId(newOutId);
                			
                			newAttachmentBeanList.add(attachmentBean);
                		}
                		                		
                		newOutBean.setTotal(total);
                		
                		newOutBean.setTaxTotal(taxTotal);
                		
                		newOutBean.setLocation(location);
                		
                		newOutBean.setDutyId(dutyId);
                		
                		newOutBean.setMtype(mtype);
                		
//                		outDAO.saveEntityBean(newOutBean);
                        saveOutWithPodate(newOutBean);
                		
                		baseDAO.saveAllEntityBeans(newBaseList);
                		
                		attachmentDAO.saveAllEntityBeans(newAttachmentBeanList);
                		
                		if (null != distBean)
                		{
                			distBean.setOutId(newOutBean.getFullId());
                			
                			saveDistributionInner(distBean, newBaseList);

                            //2015/8/7 入库调拨生成CK单
                            if (newOutBean.getType() == OutConstant.OUT_TYPE_INBILL
                                    && newOutBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT
                                    //2015/8/15 异地调拨才生成CK单
                                    && newOutBean.getRemoteAllocate() == 1)
                            {
                                createPackage(newOutBean);

                            }
                		}
                		
                		idss[i] = newOutId;
                		
                		i++;
                	}
                	
                	outDAO.deleteEntityBean(id);
                	
                	baseDAO.deleteEntityBeansByFK(id);
                	
                	attachmentDAO.deleteEntityBeansByFK(id);
                	
                	distributionDAO.deleteEntityBeansByFK(id);
                	
                	distributionBaseDAO.deleteEntityBeansByFK(id, AnoConstant.FK_FIRST);
                	
                	return idss;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }
    	
    	
		return ids;
    }

    private void saveOutWithPodate(OutBean out){
        if(StringTools.isNullOrNone(out.getPodate())){
           out.setPodate(TimeTools.now_short());
        }
        outDAO.saveEntityBean(out);
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

    @Transactional(rollbackFor = {MYException.class})
    public void initPmtype()
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("outTime", ">=", "2012-01-01");

        con.addIntCondition("mtype", "=", PublicConstant.MANAGER_TYPE_MANAGER);

        List<OutBean> outList = outDAO.queryEntityBeansByCondition(con);

        for (OutBean outBean : outList)
        {
            // 管理的开单类型必须一致(要么全部是普通,要么全部是管理)
            int mtype = 0;

            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

            for (BaseBean baseBean : baseList)
            {
                ProductBean product = productDAO.find(baseBean.getProductId());

                if (product != null)
                {
                    mtype = CommonTools.parseInt(product.getReserve4());

                    break;
                }
            }

            // 更新pmtype
            outDAO.updatePmtype(outBean.getFullId(), mtype);
        }
    }

    @Transactional(rollbackFor = {MYException.class})
    public void initOutBackBasePrice()
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("outTime", ">=", "2012-02-01");

        con.addIntCondition("type", "=", OutConstant.OUT_TYPE_INBILL);

        con.addCondition("and outType in (4, 5)");

        List<OutBean> outList = outDAO.queryEntityBeansByCondition(con);

        for (OutBean outBean : outList)
        {
            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

            OutBean refOut = outDAO.find(outBean.getRefOutFullId());

            if (refOut != null)
            {
                List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(refOut.getFullId());

                // 修改结算价
                for (BaseBean each1 : baseList)
                {
                    for (BaseBean each2 : refBaseList)
                    {
                        if (each1.equals2(each2))
                        {
                            each1.setIprice(each2.getIprice());
                            each1.setPprice(each2.getPprice());
                            each1.setInputPrice(each2.getInputPrice());

                            // 更新
                            baseDAO.updateEntityBean(each1);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Transactional(rollbackFor = {MYException.class})
    public void initOutPrice()
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("outTime", ">=", "2012-04-01");

        con.addIntCondition("type", "=", OutConstant.OUT_TYPE_OUTBILL);

        List<OutBean> outList = outDAO.queryEntityBeansByCondition(con);

        for (OutBean outBean : outList)
        {
            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

            StafferBean sb = stafferDAO.find(outBean.getStafferId());

            if (sb == null)
            {
                continue;
            }

            for (BaseBean baseBean : baseList)
            {
                ProductBean product = productDAO.find(baseBean.getProductId());

                if (product != null && baseBean.getInputPrice() == 0.0)
                {
                    SailConfBean sailConf = sailConfigManager.findProductConf(sb, product);

                    double sailPrice = product.getSailPrice();

                    baseBean.setPprice(sailPrice * (1 + sailConf.getPratio() / 1000.0d));

                    //#647
                    if(sailConf.getIprice() > 0){
                        baseBean.setIprice(sailConf.getIprice());
                    } else{
                        // 就是看到的结算价
                        baseBean
                                .setIprice(sailPrice
                                        * (1 + sailConf.getPratio() / 1000.0d + sailConf.getIratio() / 1000.0d));
                    }


                    baseBean.setInputPrice(baseBean.getIprice());

                    _logger.info("更新[" + product.getName() + "]在:" + outBean.getFullId()
                                       + ".新价格:" + MathTools.formatNum(baseBean.getInputPrice()));

                    baseDAO.updateEntityBean(baseBean);
                }
            }
        }
    }

    public int[] initPriceKey()
    {
        int[] result = new int[2];

        int success = 0;
        int fail = 0;

        String lastTime = "2011-04-01";

        String outTime = TimeTools.now_short( -180);

        if (lastTime.compareTo(outTime) > 0)
        {
            outTime = lastTime;
        }

        int count = baseDAO.countBaseByOutTime(outTime);

        PageSeparate page = new PageSeparate();

        page.reset2(count, 2000);

        while (page.hasNextPage())
        {
            page.nextPage();

            List<BaseBean> baseList = baseDAO.queryBaseByOutTime(outTime, page);

            for (final BaseBean each : baseList)
            {
                // 核对品名和开单不对的单据
                ProductBean product = productDAO.find(each.getProductId());

                if (product == null)
                {
                    _logger.error("BASE:" + each.getId() + ",product is null:"
                                  + each.getProductId());
                }

                if (product != null && !product.getName().equals(each.getProductName()))
                {
                    _logger.error("BASE:" + each.getId() + ",product is name error:"
                                  + each.getProductName() + ", real is:" + product.getName()
                                  + ",fullid is:" + each.getOutId());
                }

                String priceKey = StorageRelationHelper.getPriceKey(each.getCostPrice());

                if ( !priceKey.equals(each.getCostPriceKey()))
                {
                    _logger.info(each + "||old CostPriceKey:" + each.getCostPriceKey()
                                 + ";new CostPriceKey:" + priceKey);

                    each.setCostPriceKey(priceKey);

                    try
                    {
                        // 增加管理员操作在数据库事务中完成
                        TransactionTemplate tran = new TransactionTemplate(transactionManager);

                        tran.execute(new TransactionCallback()
                        {
                            public Object doInTransaction(TransactionStatus arg0)
                            {
                                baseDAO.updateCostPricekey(each.getId(), each.getCostPriceKey());

                                return Boolean.TRUE;
                            }
                        });

                        success++ ;
                    }
                    catch (Exception e)
                    {
                        fail++ ;

                        _logger.error(e, e);
                    }
                }
            }
        }

        result[0] = success;
        result[1] = fail;

        return result;
    }

    public void exportAllStafferCredit() {
        triggerLog.info("begin exportAllStafferCredit...");

        WriteFile write = null;

        OutputStream out = null;

        try
        {
            out = new FileOutputStream(getStafferCreditStorePath() + "/StafferCredit_"
                                       + TimeTools.now("yyyyMMddHHmmss") + ".csv");

            List<StafferBean> stafferList = stafferDAO.listCommonEntityBeans();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            writeStafferCredit(write, stafferList);

            write.close();

        }
        catch (Throwable e)
        {
            triggerLog.error(e, e);
        }
        finally
        {
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

        triggerLog.info("end exportAllStafferCredit...");
    }

    public void writeStafferCredit(WriteFile write, List<StafferBean> stafferList)
            throws IOException {
        write.writeLine("日期,类别,职员,总信用额度,单据,客户,原始信用,信用杠杆,开单使用额度,担保使用额度,被担保额度,剩余额度,其他");

        String now = TimeTools.now("yyyy-MM-dd");

        WriteFileBuffer line = new WriteFileBuffer(write);

        for (StafferBean staffer : stafferList)
        {
            if (staffer.getCredit() <= 0)
            {
                continue;
            }

            // 自己信用
            double st = outDAO.sumNoPayAndAvouchBusinessByStafferId(staffer.getId(), staffer
                .getIndustryId(), YYTools.getStatBeginDate(), YYTools.getStatEndDate());

            // 担保他人
            double mt = outDAO.sumNoPayAndAvouchBusinessByManagerId2(staffer.getId(), YYTools
                .getStatBeginDate(), YYTools.getStatEndDate());

            // 被担保
            double bei = outDAO.sumNoPayAndAvouchBusinessByManagerId3(staffer.getId(), YYTools
                .getStatBeginDate(), YYTools.getStatEndDate());

            // 总信用
            double total = staffer.getCredit() * staffer.getLever();

            StringBuffer buffer = new StringBuffer();

            List<InvoiceCreditVO> vsList = invoiceCreditDAO.queryEntityVOsByFK(staffer.getId());

            for (InvoiceCreditVO invoiceCreditVO : vsList)
            {
                buffer.append(invoiceCreditVO.getInvoiceName()).append("下的信用额度:").append(
                    MathTools.formatNum(invoiceCreditVO.getCredit() * staffer.getLever())).append(
                    ".");
            }

            line.writeColumn(now);
            line.writeColumn("合计");
            line.writeColumn(staffer.getName());
            line.writeColumn(total);
            line.writeColumn("");
            line.writeColumn("");
            line.writeColumn(staffer.getCredit());
            line.writeColumn(staffer.getLever());
            line.writeColumn(st);
            line.writeColumn(mt);
            line.writeColumn(bei);
            line.writeColumn(total - st - mt);
            line.writeColumn(buffer.toString());

            line.writeLine();

            // 自己信用使用明细
            List<CreditWrap> creditList = outDAO.queryNoPayAndAvouchBusinessByStafferId(staffer
                .getId(), staffer.getIndustryId(), YYTools.getStatBeginDate(), YYTools
                .getStatEndDate());

            for (CreditWrap creditWrap : creditList)
            {
                if (creditWrap.getCredit() > 0)
                {
                    line.writeColumn(creditWrap.getOutTime());
                    line.writeColumn("自己使用");
                    line.writeColumn(staffer.getName());
                    line.writeColumn("");
                    line.writeColumn(creditWrap.getFullId());
                    line.writeColumn(creditWrap.getCustomerName());
                    line.writeColumn("");
                    line.writeColumn("");
                    line.writeColumn(creditWrap.getCredit());
                    line.writeLine();
                }
            }

            creditList = outDAO.queryNoPayAndAvouchBusinessByManagerId2(staffer.getId(), YYTools
                .getStatBeginDate(), YYTools.getStatEndDate());

            for (CreditWrap creditWrap : creditList)
            {
                line.writeColumn(creditWrap.getOutTime());
                line.writeColumn("担保他人");
                line.writeColumn(staffer.getName());
                line.writeColumn("");
                line.writeColumn(creditWrap.getFullId());
                line.writeColumn(creditWrap.getCustomerName());
                line.writeColumn("");
                line.writeColumn("");
                line.writeColumn("");
                line.writeColumn(creditWrap.getCredit());
                line.writeLine();
            }

            creditList = outDAO.queryNoPayAndAvouchBusinessByManagerId3(staffer.getId(), YYTools
                .getStatBeginDate(), YYTools.getStatEndDate());

            for (CreditWrap creditWrap : creditList)
            {
                line.writeColumn(creditWrap.getOutTime());
                line.writeColumn("被担保");
                line.writeColumn(staffer.getName());
                line.writeColumn("");
                line.writeColumn(creditWrap.getFullId());
                line.writeColumn(creditWrap.getCustomerName());
                line.writeColumn("");
                line.writeColumn("");
                line.writeColumn("");
                line.writeColumn("");
                line.writeColumn(creditWrap.getCredit());
                line.writeLine();
            }
            line.writeLine();
        }
    }

    /**
     * CORE 内部实现(所有的增加库单都调用这个方法)
     * 
     * @param outBean
     */
    private void saveOutInner(final OutBean outBean)
            throws MYException
    {
        configOutBean(outBean);
        _logger.info("*****saveOutInner*****"+outBean);
        List<BaseBean> baseList = outBean.getBaseList();

        // MANAGER 管理类型的库单处理
        if (OATools.getManagerFlag() && !"0".equals(outBean.getLocation()))
        {
            if (OATools.isCommon(outBean.getMtype()))
            {
                if (ListTools.isEmptyOrNull(baseList)){
                    _logger.error("*****empty baseList****");
                } else {
                    for (BaseBean baseBean : baseList)
                        {
                            ProductBean product = productDAO.find(baseBean.getProductId());

                            if (product == null){
                                throw new MYException("产品[%s]不存在", baseBean.getProductId());
                            } else{
                                if (OATools.isManager(product.getReserve4()) && outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                                {
                                    throw new MYException("库单当前所属的纳税实体是普通类型,当前产品[%s]不是1", product.getName());
                                }
                            }
                        }
                }

                outBean.setPmtype(PublicConstant.MANAGER_TYPE_COMMON);
            }
            else
            {
                // 管理的开单类型必须一致(要么全部是普通,要么全部是管理)
                int mtype = -1;

                if (ListTools.isEmptyOrNull(baseList)){
                    _logger.error("*****empty list****");
                } else{
                    for (BaseBean baseBean : baseList)
                    {
                        ProductBean product = productDAO.find(baseBean.getProductId());

                        if (product == null){
                            throw new MYException("产品[%s]不存在", baseBean.getProductId());
                        } else{
                            if (mtype == -1)
                            {
                                mtype = OATools.getManagerType(product.getReserve4());
                            }
                            else
                            {
                                if (OATools.getManagerType(product.getReserve4()) != mtype && outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                                {
                                    throw new MYException("库单里面的产品管理属性必须完全一致,当前产品[%s]不是", product.getName());
                                }
                            }
                        }


                    }
                }


                // 产品类型
                outBean.setPmtype(mtype);

                // 产品管理类型和单据的管理类型不一样
                if (mtype != outBean.getMtype())
                {
                    outBean.setVtype(OutConstant.VTYPE_SPECIAL);
                }
            }
        }

        if ("0".equals(outBean.getLocation()))
        	outBean.setLocation("");
        
//        outDAO.saveEntityBean(outBean);
        this.saveOutWithPodate(outBean);
        
        _logger.debug("*****outBean.getFullId()*****"+outBean.getFullId());

        this.saveDistributionForRemoteAllocate(outBean);

        _logger.info("*****finish saveOutInner*****"+outBean.getFullId());
    }

    private void saveDistributionForRemoteAllocate(OutBean outBean){
        //2015/8/6 入库调拨需生成配送单及CK单
        //2015/10/22 入库换货单也需要生成CK单
        int outType = outBean.getOutType();
        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                && (outType == OutConstant.OUTTYPE_IN_MOVEOUT 
                || outType == OutConstant.OUTTYPE_IN_EXCHANGE
                || outBean.getBuyReturnFlag() == 1))
        {
            DistributionBean distributionBean = outBean.getDistributeBean();

            if (distributionBean!= null){
                String id = commonDAO.getSquenceString20(IDPrefixConstant.ID_DISTRIBUTION_PRIFIX);
                distributionBean.setId(id);
                distributionBean.setOutId(outBean.getFullId());
                distributionDAO.saveEntityBean(distributionBean);
                _logger.info(outBean.getFullId()+"入库调拨/换货单生成配送单:"+distributionBean);
            }else{
                _logger.warn("distributionBean不存在:" + outBean.getFullId());
            }
        }
    }
    
    /**
     * 更新客户信用分及业务员信用额度 
     * 
     * @param out
     * @param id
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateCusAndBusVal(OutBean out,String id)
        throws MYException {
        PromotionBean proBean = promotionDAO.find(out.getEventId());
	        if(null != proBean)
	        {
		    	StafferBean stafferBean = stafferDAO.find(out.getStafferId());
		    	
		        //stafferBean.setCredit(stafferBean.getCredit()+proBean.getBusiCredit());

		        List<InvoiceCreditBean> inList = invoiceCreditDAO.queryEntityBeansByFK(out.getStafferId());
		        if(null != inList)
		        {
			        for (InvoiceCreditBean invoiceCreditBean : inList)
			        {
			        	invoiceCreditBean.setCredit(invoiceCreditBean.getCredit()+proBean.getBusiCredit());
			            invoiceCreditDAO.updateEntityBean(invoiceCreditBean);
			        }
		        }
		        stafferDAO.updateEntityBean(stafferBean);
		        CustomerBean customer = customerMainDAO.find(out.getCustomerId());
		        if(customer.getCreditVal()+proBean.getCustCredit()>100)
		        {
		        	customer.setCreditVal(100);
		        }
		        else
		        {
		        	customer.setCreditVal(customer.getCreditVal()+proBean.getCustCredit());
		        }
		        boolean b = customerMainDAO.updateEntityBean(customer);
		        String cid = out.getCustomerId();
		        cid = cid+"---"+out.getFullId();
                if (id == null){
                    _logger.info("****票随货发Job***updateCusAndBusVal");
                } else {
                    clientFacade.interposeCredit(id, cid, proBean.getCustCredit());
                }
		        return b;
	        }
	        return true;
        }

    private void configOutBean(final OutBean outBean)
    {
    	String dutyId = outBean.getDutyId();

    	// 领样转销售
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL && !StringTools.isNullOrNone(outBean.getRefOutFullId()))
        {
        	BaseBean base = outBean.getBaseList().get(0);
        	
        	int mtype = base.getMtype();

        	if (mtype == PublicConstant.MANAGER_TYPE_COMMON)
			{
				dutyId = PublicConstant.DEFAULR_DUTY_ID;
			}
			else
			{
				dutyId = PublicConstant.MANAGER2_DUTY_ID;
			}
        }

        DutyBean duty = dutyDAO.find(dutyId);

        if (duty != null)
        {
            outBean.setMtype(duty.getMtype());
        }

        outBean.setDutyId(dutyId);
    }

	@Transactional(rollbackFor = MYException.class)
	public boolean outRepaire(User user, OutRepaireBean outRepaireBean)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, outRepaireBean);
		
		List<BaseRepaireBean> list = outRepaireBean.getList();
		
		String id = commonDAO.getSquenceString20();
		
		outRepaireBean.setId(id);
		
		outRepaireBean.setStatus(OutConstant.OUT_REPAIRE_PASS);
		
		outRepaireDAO.saveEntityBean(outRepaireBean);

		for (BaseRepaireBean each : list)
		{
			each.setId(commonDAO.getSquenceString());
			
			each.setRefId(id);
			
			baseRepaireDAO.saveEntityBean(each);
		}
		
		FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("提交");
        log.setFullId(id);
        log.setOprMode(PublicConstant.OPRMODE_SUBMIT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(OutConstant.OUT_REPAIRE_SUBMIT);

        log.setAfterStatus(OutConstant.OUT_REPAIRE_PASS);

        flowLogDAO.saveEntityBean(log);
		
		return true;
	}
    
	@Transactional(rollbackFor = MYException.class)
	public boolean rejectOutRepaireApply(User user, String id, String reason)
			throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        OutRepaireBean bean = outRepaireDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() != OutConstant.OUT_REPAIRE_PASS)
        {
            throw new MYException("数据错误,请确认操作");
        }

        bean.setStatus(OutConstant.OUT_REPAIRE_REJECT);

        outRepaireDAO.updateEntityBean(bean);

        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(reason);
        log.setFullId(id);
        log.setOprMode(PublicConstant.OPRMODE_REJECT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(OutConstant.OUT_REPAIRE_PASS);

        log.setAfterStatus(OutConstant.OUT_REPAIRE_REJECT);

        flowLogDAO.saveEntityBean(log);

        return true;
    }

	@Transactional(rollbackFor = MYException.class)
	public boolean passOutRepaireApply(User user, String id) throws MYException
	{
        JudgeTools.judgeParameterIsNull(user, id);

        OutRepaireBean bean = outRepaireDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (bean.getStatus() != OutConstant.OUT_REPAIRE_PASS)
        {
            throw new MYException("数据错误,请确认操作");
        }

        OutBean out = outDAO.find(bean.getOutId());

        if (out == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !OutHelper.isSailEnd(out))
        {
            throw new MYException("单据不处于结束,请确认操作");
        }

        List<BaseRepaireBean> repaireList = baseRepaireDAO.queryEntityBeansByFK(bean.getId()); 
        
        bean.setList(repaireList);
        
        // Core 一退一销，脏数据利用，安全性
        processBlankBuyAndOut(bean, out, user, null);
        
        bean.setStatus(OutConstant.OUT_REPAIRE_END);

        outRepaireDAO.updateEntityBean(bean);
        
        // 新旧销售单单号相互关联
        outDAO.updateDescription(bean.getOutId(), out.getDescription() + ",空开空退新单："+ bean.getNewOutId());

        // 验证(销售单)是否可以全部回款
        try
        {
            this.payOut(user, out.getFullId(), "自动核对付款", 0);
        }
        catch (MYException e)
        {
            _logger.info(e, e);
        }
        
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("通过");
        log.setFullId(id);
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(OutConstant.OUT_REPAIRE_PASS);

        log.setAfterStatus(OutConstant.OUT_REPAIRE_END);

        flowLogDAO.saveEntityBean(log);

        return true;
    }
	
	/**
	 * CORE 处理空开空退&空出空进
     * 产生一退货单，新销售单，且涉及到凭证
	 * @param bean
	 * @param out
	 * @throws MYException
	 */
	private void processBlankBuyAndOut(OutRepaireBean bean, OutBean out, User user, String reason) throws MYException
	{
		String newBuyId = createNewBuyBean(bean, out, user, reason);

		OutBean newOutBean = createNewOutBean(bean, out, user, reason);
		
		// 新单的配送地址，复制原销售单
		DistributionBean dist = null;
		
		List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(out.getFullId());
		
		if (newOutBean!= null && !ListTools.isEmptyOrNull(distList))
		{
			dist = distList.get(0);
			
			dist.setOutId(newOutBean.getFullId());
			
			saveDistributionInner(dist, newOutBean.getBaseList());
		}

		if (newOutBean == null){
            _logger.info("空出空进原单:"+ out.getFullId()+", 退单"+ newBuyId );
        } else{
            _logger.info("空开空退原单:"+ out.getFullId()+", 退单"+ newBuyId + ", 新单：" + newOutBean);
        }
		
		if (bean!= null && !bean.getReason().equals(OutConstant.OUT_REPAIREREASON_DONOTAUTOPAY))
		{
			// 已付款 1.处理预收-应收凭让 2.收款单关联 3.如果新单小于原单，部分转预收
	        if (out.getPay() == OutConstant.PAY_YES)
	        {
	        	// 通过listener触发
	            Collection<OutListener> listenerMapValues = listenerMapValues();

	            for (OutListener listener : listenerMapValues)
	            {
	                listener.onOutRepairePass(user, out, newOutBean);
	            }
	            
	            outDAO.updateHadPay(out.getFullId(), 0);
	        }
		}

		if (bean!= null){
            bean.setNewOutId(newOutBean.getFullId());
            bean.setRetOutId(newBuyId);
        }
	}

	private String createNewBuyBean(OutRepaireBean bean, OutBean out, User user, String reason) throws MYException
	{
		String newBuyId;
		OutBean newOutBean = new OutBean();
    	BeanUtil.copyProperties(newOutBean, out);
    	String id = getAll(commonDAO.getSquence());

        //String flag = location.getCode();
        //# 373
        String refOutId = out.getFullId();
        int outType = OutConstant.OUTTYPE_IN_OUTBACK;
        if ( refOutId.startsWith("ZS") ){
            outType = OutConstant.OUTTYPE_IN_PRESENT;
        } else if (out.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH
                || out.getOutType() == OutConstant.OUTTYPE_OUT_BANK_SWATCH
                || out.getOutType() == OutConstant.OUTTYPE_OUT_SHOW){
            // #403 “个人领样、银行领样、客户铺货”这3种类型的订单入库类型为“领样退库”
            outType = OutConstant.OUTTYPE_IN_SWATCH;
        }
        String flag = OutHelper.getSailHead(OutConstant.OUT_TYPE_INBILL, outType);

        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

        newBuyId = flag + time + id;

        newOutBean.setId(getOutId(id));
    	
    	newOutBean.setFullId(newBuyId);
    	
    	newOutBean.setOutTime(TimeTools.now_short());
    	
    	newOutBean.setOutType(outType);

    	if(bean == null){
            newOutBean.setDescription("空出空进销售退库,销售单号:" + out.getFullId() + ". 销售退库");
        }else{
            newOutBean.setDescription("空开空退销售退库,销售单号:" + out.getFullId() + ". 销售退库");
        }

        //#373 退单的中收激励标识也复制了原单的信息，应该是置空的
        newOutBean.setIbApplyId("");
    	newOutBean.setIbFlag(0);
    	newOutBean.setIbApplyId2("");
    	newOutBean.setIbFlag2(0);
    	newOutBean.setMotivationApplyId("");
    	newOutBean.setMotivationFlag(0);
    	newOutBean.setMotivationApplyId2("");
    	newOutBean.setMotivationFlag2(0);
    	newOutBean.setPlatformFlag(0);
    	newOutBean.setPlatformApplyId("");

    	newOutBean.setType(OutConstant.OUT_TYPE_INBILL);
    	newOutBean.setStatus(OutConstant.BUY_STATUS_SAVE);
    	newOutBean.setHasInvoice(0);
    	newOutBean.setPay(0);
    	newOutBean.setTempType(0);
    	newOutBean.setPhone("");
    	newOutBean.setConnector("");
    	newOutBean.setReday(0);
    	newOutBean.setRedate("");
    	newOutBean.setArriveDate("");
    	newOutBean.setRefOutFullId(out.getFullId());
    	
    	newOutBean.setReserve1(0);
    	newOutBean.setReserve2(0);
    	newOutBean.setReserve3(0);
    	newOutBean.setReserve4("");
    	newOutBean.setReserve5("");
    	newOutBean.setReserve6("");
    	newOutBean.setReserve7("");
    	newOutBean.setReserve8("");
    	newOutBean.setReserve9("");
    	
    	newOutBean.setInvoiceMoney(0);
    	
    	newOutBean.setCurcredit(0);
    	newOutBean.setStaffcredit(0);
    	newOutBean.setManagercredit(0);
    	newOutBean.setChangeTime(TimeTools.now());
    	
    	newOutBean.setRatio("");
    	newOutBean.setPayTime("");
    	newOutBean.setLastModified("");

    	if (bean!= null){
            newOutBean.setOperator(bean.getOperator());
            newOutBean.setOperatorName(bean.getOperatorName());
        }
    	
    	newOutBean.setFeedBackVisit(0);
    	newOutBean.setFeedBackCheck(0);
    	
    	List<BaseBean> baseList = new ArrayList<BaseBean>();
    	
    	newOutBean.setBaseList(baseList);
    	
    	List<BaseBean> list = baseDAO.queryEntityBeansByFK(out.getFullId());

    	for (BaseBean each : list)
    	{
    		BaseBean newBaseBean = new BaseBean();
    		
    		BeanUtil.copyProperties(newBaseBean, each);
    		
    		newBaseBean.setId(commonDAO.getSquenceString());
    		
    		newBaseBean.setOutId(newOutBean.getFullId());
    		
    		newBaseBean.setShowName("");
    		
    		baseList.add(newBaseBean);
    	}

    	//#59 空开空退XT单的poDate取当前日期
//    	newOutBean.setPodate(TimeTools.now_short());
        //#882 poDate取原单,flowTime取当前时间
        newOutBean.setPodate(out.getPodate());
    	newOutBean.setFlowTime(TimeTools.now());

        newOutBean.setLogTime(TimeTools.now());
        newOutBean.setManagerTime(TimeTools.now());
    	outDAO.saveEntityBean(newOutBean);
//        this.saveOutWithPodate(newOutBean);

    	baseDAO.saveAllEntityBeans(baseList);

    	// 触发产生退货凭证 TAX_ADD
        Collection<OutListener> listenerMapValues = listenerMapValues();

        for (OutListener listener : listenerMapValues)
        {
            listener.onConfirmOutOrBuy(user, newOutBean);
        }

        if(bean == null){
            outDAO.modifyOutStatus(newOutBean.getFullId(), OutConstant.BUY_STATUS_SEC_PASS);
            outDAO.updatePay(newOutBean.getFullId(), OutConstant.PAY_YES);
        }else{
            outDAO.modifyOutStatus(newOutBean.getFullId(), OutConstant.BUY_STATUS_PASS);
        }
        
        // 记录退货审批日志 操作人系统，自动审批 
    	FlowLogBean log = new FlowLogBean();

        log.setActor("系统");

        if(bean == null){
            StringBuilder sb = new StringBuilder();
            sb.append("空出空进系统自动审批");
            if (!StringTools.isNullOrNone(reason)){
                sb.append(":"+reason);
            }
            log.setDescription(sb.toString());
        }else{
            log.setDescription("空开空退系统自动审批");
        }

        log.setFullId(newOutBean.getFullId());
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(OutConstant.BUY_STATUS_SAVE);

        if(bean == null){
            log.setAfterStatus(OutConstant.BUY_STATUS_SEC_PASS);
        }else{
            log.setAfterStatus(OutConstant.BUY_STATUS_PASS);
        }

        flowLogDAO.saveEntityBean(log);
        
    	return newBuyId;
	}
	
	/**
	 * 空开空退产生新的销售单
	 * @param out
	 * @param user
	 * @return
	 * @throws MYException
	 */
	private OutBean createNewOutBean(OutRepaireBean bean, OutBean out, User user, String reason) throws MYException
	{
        // #226 直接出库,触发产生退货凭证 TAX_ADD,不需要生成新单
        if (bean == null){
            Collection<OutListener> listenerMapValues = listenerMapValues();

            for (OutListener listener : listenerMapValues)
            {
                listener.onConfirmOutOrBuy(user, out);
            }
	        return null;
        }

		String newOutId;
		
		OutBean newOutBean = new OutBean();
    	
    	BeanUtil.copyProperties(newOutBean, out);
    	
    	String id = getAll(commonDAO.getSquence());

        LocationBean location = locationDAO.find(out.getLocationId());

        //String flag = location.getCode();
        String flag = OutHelper.getSailHead(out.getType(), out.getOutType());

        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

        newOutId = flag + time + id;

        newOutBean.setId(getOutId(id));
    	
    	newOutBean.setFullId(newOutId);
    	
    	newOutBean.setOutTime(TimeTools.now_short());

        newOutBean.setChangeTime(TimeTools.now());

        double total = 0.0d;
        List<BaseRepaireBean> repaireList = new ArrayList<BaseRepaireBean>();
        if(bean!= null){
            newOutBean.setReday(bean.getReday());

            newOutBean.setRedate(bean.getRedate());

            newOutBean.setDutyId(bean.getDutyId());

            newOutBean.setInvoiceId(bean.getInvoiceId());

            repaireList = bean.getList();

            for (BaseRepaireBean each : repaireList)
            {
                total += each.getPrice() * each.getAmount();
            }
        }

    	newOutBean.setTotal(total);
    	
    	if (total > out.getTotal())
    	{
    		newOutBean.setPay(0);
    		
    		newOutBean.setPayTime("");
    	}
    	
    	if (newOutBean.getPay() == OutConstant.PAY_YES)
    	{
    		newOutBean.setPayTime(TimeTools.now());
    		
    		newOutBean.setRedate(TimeTools.now_short());
    	}
    	
    	if (out.getPay() == OutConstant.PAY_YES && total < out.getTotal())
    	{
    		newOutBean.setHadPay(total);
    	}
    	
    	// 新单不自动勾款，新单为未付款，已支付0，付款时间为空
    	if (bean!= null && bean.getReason().equals(OutConstant.OUT_REPAIREREASON_DONOTAUTOPAY))
    	{
    		newOutBean.setHadPay(0);
    		
    		newOutBean.setPay(0);
    		
    		newOutBean.setPayTime("");
    	}
    	
		long add = 3 * 24 * 3600 * 1000L;
		
		String arrveDate = TimeTools.getStringByFormat(new Date(new Date().getTime() + add), "yyyy-MM-dd");
    	
    	newOutBean.setArriveDate(arrveDate);

        newOutBean.setManagerTime(TimeTools.now());

        newOutBean.setChangeTime(TimeTools.now());
    	
    	newOutBean.setFeedBackVisit(0);
    	newOutBean.setFeedBackCheck(0);

    	if (bean!= null){
            newOutBean.setOperator(bean.getOperator());

            newOutBean.setOperatorName(bean.getOperatorName());
            newOutBean.setDescription(out.getDescription() + "," + bean.getDescription() + ",空开空退后原单：" + out.getFullId());
        } else{
            newOutBean.setDescription(out.getDescription() +  ",空出空进后原单：" + out.getFullId());
        }

    	
    	//  如果是领样转销售的单子,refOutFullId 有值
    	newOutBean.setRefOutFullId("");
    	
    	List<BaseBean> baseList = new ArrayList<BaseBean>();
    	
    	newOutBean.setBaseList(baseList);
    	
    	List<BaseBean> list = baseDAO.queryEntityBeansByFK(out.getFullId());
    	
    	for (BaseBean each : list)
    	{
    		for (BaseRepaireBean eachre : repaireList)
    		{
    			if (each.getId().equals(eachre.getBaseId()))
    			{
    				BaseBean newBaseBean = new BaseBean();
    	    		
    	    		BeanUtil.copyProperties(newBaseBean, each);
    	    		
    	    		newBaseBean.setId(commonDAO.getSquenceString());
    	    		
    	    		newBaseBean.setOutId(newOutBean.getFullId());
    	    		
    	    		newBaseBean.setPrice(eachre.getPrice());
    	    		
    	    		newBaseBean.setValue(newBaseBean.getPrice() * newBaseBean.getAmount());
    	    		
    	    		newBaseBean.setShowId(eachre.getShowId());
    	    		
    	    		newBaseBean.setShowName(eachre.getShowName());
    	    		
    	    		newBaseBean.setInputPrice(eachre.getInputPrice());
    	    		
    	    		newBaseBean.setIprice(newBaseBean.getInputPrice());
    	    		
    	    		baseList.add(newBaseBean);
    			}
    		}
    		
    	}
    	
//    	outDAO.saveEntityBean(newOutBean);
        //#143 空开的新订单的状态要设为 已发货
        newOutBean.setStatus(OutConstant.STATUS_SEC_PASS);
        this.saveOutWithPodate(newOutBean);
    	
    	baseDAO.saveAllEntityBeans(baseList);
    	
    	// 触发产生退货凭证 TAX_ADD
        Collection<OutListener> listenerMapValues = listenerMapValues();

        for (OutListener listener : listenerMapValues)
        {
            listener.onConfirmOutOrBuy(user, newOutBean);
        }
        
    	// 记录退货审批日志 操作人系统，自动审批 
    	FlowLogBean log = new FlowLogBean();

        log.setActor("系统");

        if (bean  == null){
            StringBuilder sb = new StringBuilder();
            sb.append("空出空进系统自动审批");
            if (!StringTools.isNullOrNone(reason)){
                sb.append(":"+reason);
            }
            log.setDescription(sb.toString());
            log.setFullId(out.getFullId());
            log.setAfterStatus(OutConstant.STATUS_PASS);
        } else{
            log.setDescription("空开空退系统自动审批");
            log.setFullId(newOutBean.getFullId());
            log.setAfterStatus(newOutBean.getStatus());
        }

        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());
        log.setPreStatus(OutConstant.STATUS_SAVE);

        flowLogDAO.saveEntityBean(log);
        
    	return newOutBean;
	}

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean kckj(User user, String fullId, String reason) throws MYException {
        JudgeTools.judgeParameterIsNull(user, fullId, reason);

        OutBean out = outDAO.find(fullId);

        if (out == null)
        {
            throw new MYException("单据不存在,请确认操作");
        } else if (out.getInvoiceStatus() != 0){
            throw new MYException("开票状态必须为未开票");
        } else if (out.getStatus() != OutConstant.STATUS_SUBMIT){
            throw new MYException("销售单状态必须为待商务审批");
        }
/*        else if (out.getStatus() == OutConstant.STATUS_SEC_PASS){
            throw new MYException("销售单状态已发货,不能操作");
        }*/

        // Core 一退一销，脏数据利用，安全性
        processBlankBuyAndOut(null, out, user, reason);

        // 验证(销售单)是否可以全部回款
        this.payOut(user, out.getFullId(), "自动核对付款", 0);
        this.outDAO.modifyOutStatus(fullId, OutConstant.STATUS_SEC_PASS);
        this.outDAO.updateChangeTime(fullId, TimeTools.now());
        //把这单的发货方式改成 空发，物流怕以后对账时会有误解
        this.distributionDAO.updateShipping(fullId, OutConstant.OUT_SHIPPING_NOTSHIPPING);
        return true;
    }

    /**
	 * 增加(修改) -- 销售单第二步（配送信息）
	 * 
	 * {@inheritDoc}
	 */
	public String addOutStep2(final OutBean outBean, final User user)
			throws MYException
	{
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
        {
            if (outBean.getPromValue() > 0)
                
                outBean.setPromStatus(0);
            else{
                
                outBean.setEventId("");
                outBean.setRefBindOutId("");
                outBean.setPromStatus(-1);
            }
        }

//        outBean.setOutTime(TimeTools.now_short());
        if (StringTools.isNullOrNone(outBean.getOutTime())){
            outBean.setOutTime(TimeTools.now_short());
        }

        outBean.setLogTime(TimeTools.now());

        
        // 增加管理员操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    
                	// 在删除前检查单据状态是否为保存或驳回
                    OutBean coutBean = outDAO.find(outBean.getFullId());
                    
                    if (null == coutBean)
                    {
                    	throw new RuntimeException("销售库单不存在，请确认");
                    }
                    
                    if (coutBean.getStatus() != OutConstant.STATUS_SAVE && coutBean.getStatus() != OutConstant.STATUS_REJECT)
                    {
                        throw new RuntimeException("销售单"+outBean.getFullId()+"已不是保存或驳回状态，当前状态是："+OutHelper.getStatus(coutBean.getStatus())+",请确认");
                    }
                	
                	// 促销金额不能大于本单总金额
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                    {
                        if (outBean.getPromValue() > outBean.getTotal())
                        {
                            throw new RuntimeException("促销折扣金额不能大于本单总金额");
                        }
                    }

                    //2014/12/8 add podate,default same as outTime
                    //2016/3/23 #206
                    if (StringTools.isNullOrNone(outBean.getPodate())) {
                        outBean.setPodate(TimeTools.now_short());
                        _logger.info(outBean.getFullId()+"更新podate"+outBean.getPodate());
                    }
                    
                    outDAO.updateEntityBean(outBean);

                    if ((outBean.getType() == OutConstant.OUT_TYPE_OUTBILL && outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)){
                        for (BaseBean  base: outBean.getBaseList()){
                            CustomerBean customerBean = customerMainDAO.find(outBean.getCustomerId());
                            try {
                                ProductImportBean productImportBean = getProductImportBean(outBean, customerBean, base.getProductId());

                                _logger.info("****productImport***" + productImportBean);
                                if (productImportBean != null) {
                                    base.setIbMoney(productImportBean.getIbMoney());
                                    base.setMotivationMoney(productImportBean.getMotivationMoney());
                                    base.setIbMoney2(productImportBean.getIbMoney2());
                                    base.setMotivationMoney2(productImportBean.getMotivationMoney2());
                                    base.setPlatformFee(productImportBean.getPlatformFee());
                                    base.setProductImportId(productImportBean.getId());
                                    base.setYkibMoney(productImportBean.getYkibMoney());

                                    //#625 OA出库单数量等于折算系数*开单数量
                                    double amount = Math.round(base.getAmount()*productImportBean.getRated());
                                    base.setAmount(new BigDecimal(amount).intValueExact());
                                    base.setPrice(base.getPrice()/productImportBean.getRated());
                                    base.setValue(base.getAmount() * base.getPrice());
                                }
                            }catch (MYException e){
                                _logger.error(e);
                            }
                        }
                    }

                    baseDAO.updateAllEntityBeans(outBean.getBaseList());
                    
                    DistributionBean distBean = outBean.getDistributeBean();
                    
                    // 先删后增
                    distributionDAO.deleteEntityBeansByFK(outBean.getFullId());
                	
                	distributionBaseDAO.deleteEntityBeansByFK(outBean.getFullId(), AnoConstant.FK_FIRST);
                    
                    // 根据配送方式生成多个配送单 
                    saveDistributionInner(distBean, outBean.getBaseList());
                    
                    // 判断地址在地址库中是否存在，不存在则自动新增
                    compareAddressAndSave(distBean, outBean);

                    // 更新客户省市信息
                    saveCustomerInfo(distBean, outBean);
                    
                    
                    // 防止溢出
                    if (isSwatchToSail(outBean.getFullId()))
                    {
                        try
                        {
                            checkSwithToSail(outBean.getRefOutFullId());
                        }
                        catch (MYException e)
                        {
                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }

        _logger.info(user.getStafferName() + "/" + user.getName() + "/ADD1:" + outBean);

        return outBean.getFullId();
    
	}
	
	/**
	 * 产生多个配送单
	 */
	private void saveDistributionInner(DistributionBean distBean, List<BaseBean> baseList)
	{
		// 预处理distBean，将详细地址中有与省、市、区一样的信息清除
		String address = distBean.getAddress().trim();
		
		String provinceId = distBean.getProvinceId();
		
		String provinceName = "";
		
		if (!StringTools.isNullOrNone(provinceId))
		{
			ProvinceBean province = provinceDAO.find(provinceId);
			
			if (null != province)
			{
				provinceName = province.getName();
				
				int p = address.indexOf(provinceName);
				
				if (p == 0)
				{
					address = address.substring(province.getName().length());
				}
			}
		}
		
		String cityId = distBean.getCityId();
		
		if (!StringTools.isNullOrNone(cityId))
		{
			CityBean city = cityDAO.find(cityId);
			
			if (null != city)
			{
//				if (provinceName.equals(city.getName()))
//				{
//					distBean.setProvinceId("");
//				}
				
				int p = address.indexOf(city.getName());
				
				if (p == 0)
				{
					address = address.substring(city.getName().length());
				}
			}
		}
		
		String areaId = distBean.getAreaId();
		
		if (!StringTools.isNullOrNone(areaId))
		{
			AreaBean area = areaDAO.find(areaId);
			
			if (null != area)
			{
				int p = address.indexOf(area.getName());
				
				if (p == 0)
				{
					address = address.substring(area.getName().length());
				}
			}
		}
		distBean.setAddress(address);
		
		Map<String,List<BaseBean>> map = new HashMap<String,List<BaseBean>>();
        
        for (BaseBean each : baseList)
        {
        	String key = each.getDeliverType()+"";
        	
        	if (!map.containsKey(key))
        	{
        		List<BaseBean> ebaseList = new ArrayList<BaseBean>();
        		
        		ebaseList.add(each);
        		
        		map.put(key, ebaseList);
        	}else{
        		List<BaseBean> ebaseList = map.get(key);
        		
        		ebaseList.add(each);
        	}
        }
        
        // 
        for (Map.Entry<String, List<BaseBean>> entry : map.entrySet())
        {
        	List<BaseBean> blist = entry.getValue();
        	
        	DistributionBean newDist = new DistributionBean();
        	
        	BeanUtil.copyProperties(newDist, distBean);
        	
        	String id = commonDAO.getSquenceString20(IDPrefixConstant.ID_DISTRIBUTION_PRIFIX);
        	
        	newDist.setId(id);
        	
        	distributionDAO.saveEntityBean(newDist);
            _logger.info("save saveEntityBean***"+newDist);
        	
        	for(BaseBean eachB : blist)
        	{
        		DistributionBaseBean dbb = new DistributionBaseBean();
        		
        		dbb.setRefId(id);
        		
        		dbb.setOutId(eachB.getOutId());
        		
        		dbb.setBaseId(eachB.getId());
        		
        		distributionBaseDAO.saveEntityBean(dbb);
        	}
        }
	}

	private void compareAddressAndSave(DistributionBean distBean, OutBean outBean)
	{
		List<AddressBean> addList = addressDAO.queryByCustomerId(outBean.getCustomerId());
		
		boolean  isExsit = false;
		
		if (StringTools.isNullOrNone(distBean.getProvinceId()))
		{
			return ;
		}
		
		for (AddressBean each : addList)
		{
			if (each.getProvinceId().equals(distBean.getProvinceId())
					&& each.getCityId().equals(distBean.getCityId())
					&& each.getAreaId().equals(distBean.getAreaId())
					&& each.getAddress().equals(distBean.getAddress())
					&& each.getReceiver().equals(distBean.getReceiver())
					&& each.getMobile().equals(distBean.getMobile()))
			{
				isExsit = true;
				
				break;
			}
		}
		
		if (!isExsit)
		{
			AddressBean addBean = new AddressBean();
			
			BeanUtil.copyProperties(addBean, distBean);
			
			addBean.setId(commonDAO.getSquenceString());
			addBean.setCustomerId(outBean.getCustomerId());
	    	addBean.setCustomerName(outBean.getCustomerName());
	    	addBean.setStafferId(outBean.getStafferId());
	    	addBean.setLogTime(TimeTools.now());
			
	    	addressDAO.saveEntityBean(addBean);
		}
	}


    private void saveCustomerInfo(DistributionBean distBean, OutBean outBean)
    {
        String psProvinceId = distBean.getProvinceId();
        String psCityId = distBean.getCityId();
        if (StringTools.isNullOrNone(psProvinceId) || StringTools.isNullOrNone(psCityId)){
            _logger.warn("Empty province ID and City ID");
            return;
        } else {
            String customerId = outBean.getCustomerId();
            CustomerBean customer = this.customerMainDAO.find(customerId);
            String provinceId = customer.getProvinceId();
            String cityId = customer.getCityId();
            _logger.info("Customer current provinceId*****"+provinceId+" cityId:"+cityId);
            //如果原客户地址是虚拟省市，并且配送单地址非虚拟省市，则更新客户地址信息
            if ("000000".equals(provinceId) && "000010".equals(cityId) &&
                    !"000000".equals(distBean.getProvinceId()) && !("000010").equals(distBean.getCityId())){
                _logger.info("Update customer main info*****");
                customer.setProvinceId(distBean.getProvinceId());
                customer.setCityId(distBean.getCityId());
                this.customerMainDAO.updateEntityBean(customer);

                //更新对应的Individual表
                if (customer.getType() == CustomerConstant.NATURE_INDIVIDUAL){
                    _logger.info("Update customer individual info*****");
                    CustomerIndividualBean indiBean = this.customerIndividualDAO.find(customerId);
                    indiBean.setProvinceId(distBean.getProvinceId());
                    indiBean.setCityId(distBean.getCityId());
                    this.customerIndividualDAO.updateEntityBean(indiBean);
                }
            }
        }


    }
	
	/**
	 * 每10分钟运行一次 JOB
	 * 
	 * {@inheritDoc}
	 */
	public void handleCheckPay()
	{
        triggerLog.info("handleCheckPay 开始统计...");
        
        long statsStar = System.currentTimeMillis();
        
        final UserVO user = new UserVO();
        
        user.setStafferId(StafferConstant.SUPER_STAFFER);
        
        try
        {
            TransactionTemplate tran = new TransactionTemplate(transactionManager);

            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                	try
					{
                        triggerLog.info("handleCheckPay 暂停统计，款到发货1小时内未付款，不会自动驳回...");
//                		List<OutBean> list = checkPayInner(user);
//
//                		// 发邮件
//                		for (OutBean each : list)
//                		{
//                			sendOutMail(each, "款到发货未在一小时内勾款,系统自动驳回");
//                		}
					}
//					catch (MYException e)
//					{
//						throw new RuntimeException(e);
//					}
                    catch(Exception e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    
                    return Boolean.TRUE;
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
            triggerLog.error(e, e);
        }
      
        triggerLog.info("handleCheckPay 统计结束... ,共耗时："+ (System.currentTimeMillis() - statsStar));
        
        return;
    }
	
	private List<OutBean> checkPayInner(final UserVO user)
	throws MYException
	{
		List<OutBean> list = new ArrayList<OutBean>();
		
		String now = TimeTools.now();

		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.outTime", "=", TimeTools.now_short());

		con.addIntCondition("OutBean.reserve3", "=", OutConstant.OUT_SAIL_TYPE_MONEY);

		//con.addItemCondition(" and OutBean.status not in (0, 2, 3, 4)");
        con.addIntCondition("OutBean.status", "=", OutConstant.STATUS_SUBMIT);

		con.addIntCondition("OutBean.pay", "=", OutConstant.PAY_NOT);

        con.addCondition("OutBean.flowId", "<>", OutImportConstant.CITIC);

		List<OutBean> outList = outDAO.queryEntityBeansByCondition(con);

		for (OutBean each : outList)
		{
			String logTime = each.getLogTime();
			
			Long nowl =  TimeTools.getDate(now).getTime();
			
			Long logTimel = TimeTools.getDate(logTime).getTime();
			
			// 超过30分钟未付款
			if ((nowl - logTimel) > (1 * 3600 * 1000L))
			{
				reject(each.getFullId(), user, "款到发货未在一小时内勾款,系统自动驳回");
				
				list.add(each);
			}
		}
		
		return list;
	}
	
	public void sendOutMail(OutBean out,String subject)
    {
        String operatorName = out.getOperatorName();

        StafferBean approverBean = stafferDAO.find(out.getStafferId());
        
        String customerName = out.getCustomerName();
        
        List<BaseBean> list = baseDAO.queryEntityBeansByFK(out.getFullId());
        
        StringBuffer sb1 = new StringBuffer();
        
        sb1.append("<br>")
                .append("<br>")
        .append("====销售明细====");
        
        for (BaseBean each : list)
        {
        	sb1.append("<br>").append("商品：").append(each.getProductName())
        					  .append(", 数量：").append(each.getAmount())
        					  .append(", 单价：").append(each.getPrice());
        }
        
        String desc1 = out.getDescription();
        
        String logDesc = "";
        
        int idx = desc1.indexOf("&&");
        
        if (idx != -1)
        {
        	logDesc = desc1.substring(idx + 2, desc1.length());
        }
        
        sb1.append("。<br>")
        	.append("<br>")
        	.append("销售单异常原因：" + logDesc + ", 请尽快确认。");
        
        if (null != approverBean)
        {
            StringBuffer sb = new StringBuffer();
            
            sb.append("系统发送>>>")
            .append("<br>").append("单号:"+ out.getFullId()).append(",")
            .append("<br>").append("客户:"+ customerName).append(",")
            .append("<br>").append("总金额:"+  out.getTotal()).append(",")
            .append("<br>").append("申请人:"+ approverBean.getName()).append(",")
            .append("<br>").append("经办人:"+ operatorName);
            
            String message = sb.toString() + sb1.toString();
            
            String to = approverBean.getNation();
            
            _logger.info(message);
            
            commonMailManager.sendMail(to, subject, message);
        }
    }
	
	/**
	 * 在完成销售的第一个页面时提前检查信用、库存
	 * 
	 * {@inheritDoc}
	 */
	public void preCheckBeforeSubmit(String fullId) throws MYException
	{
        OutBean outBean = outDAO.find(fullId);

        if (outBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        checkSubmit(fullId, outBean);
        
        checkCredit(fullId, outBean);
	}
	
	 /**
     * 提交(包括领样退库和销售退库)
     * 
     * @param fullId
     * @param user
     * @return
     * @throws Exception
     */
    public int submitDiaoBo(final String fullId, final User user, final int storageType)
        throws MYException
    {
        // LOCK 库存提交(当是入库单的时候是变动库存的)
        synchronized (PublicLock.PRODUCT_CORE)
        {
            Integer result = 0;

            try
            {
                // 增加管理员操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);

                result = (Integer)tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        try
                        {
                            return submitWithOutAffair1(fullId, user, storageType);
                        }
                        catch (MYException e)
                        {
                            _logger.error(e, e);

                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }
                });
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e.getMessage());
            }

            return result;
        }
    }

    
    /**
     * 暂时没有对外开放
     */
    
    public int submitWithOutAffair1(final String fullId, final User user, int type)
        throws MYException
    {
    	final OutBean outBean = outDAO.find(fullId);
        if (outBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        // 检查日志核对
        int outStatusInLog = this.findOutStatusInLog(outBean.getFullId());
        if (outStatusInLog != -1 && outStatusInLog != OutConstant.STATUS_REJECT
            && outStatusInLog != outBean.getStatus())
        {
            String msg = "严重错误,当前单据的状态应该是:" + OutHelper.getStatus(outStatusInLog) + ",而不是"
                         + OutHelper.getStatus(outBean.getStatus()) + ".请联系管理员确认此单的正确状态!";

            loggerError(outBean.getFullId() + ":" + msg);

            throw new MYException(msg);
        }

        final List<BaseBean> baseList = checkSubmit(fullId, outBean);

        // 这里是入库单的直接库存变动(部分)
        processBuyBaseList(user, outBean, baseList, type);

        //add 针对促销订单绑定历史订单，更新被绑定订单的相关信息
        processPromBindOutId(user, outBean);

        // CORE 修改库单(销售/入库)的状态(信用额度处理)
        int status = processOutStutus1(fullId, user, outBean);

        try
        {
            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
            {
                outDAO.modifyOutStatus(fullId, status);
            }
            else
            {
            	if (outBean.getType() == OutConstant.OUT_TYPE_INBILL && outBean.getOutType() == OutConstant.OUTTYPE_OUT_APPLY )
            	{
            		outDAO.modifyOutStatus(fullId, OutConstant.BUY_STATUS_LOCATION_MANAGER_CHECK);
            		
            		status = OutConstant.BUY_STATUS_LOCATION_MANAGER_CHECK;
            	}
            }
        }
        catch (Exception e)
        {
            _logger.error(e, e);
            
            throw new MYException(e);
        }


        // 处理在途(销售无关)/调入接受时
        int result = processBuyOutInWay(user, fullId, outBean);

        // 在途改变状态
        if (result != -1)
        {
            status = result;
        }
        
        String desc = outBean.getDescription();
        
        int idx = desc.indexOf("&&");
        
        if (idx == -1)
        {
        	// 增加数据库日志
            addOutLog(fullId, user, outBean, "提交", SailConstant.OPR_OUT_PASS, status);
        }
        else
        {
        	String newDesc = desc.substring(0, idx);
        	
        	String logDesc = desc.substring(idx + 2, desc.length());
        	
//        	outBean.setDescription(newDesc);
        	
        	outDAO.updateDescription(fullId, newDesc);
        	
        	if (StringTools.isNullOrNone(logDesc))
        	{
        		logDesc = "提交";
        	}
        	// 增加数据库日志
            addOutLog(fullId, user, outBean, logDesc, SailConstant.OPR_OUT_PASS, status);
        }
        
        outBean.setStatus(status);

        notifyOut(outBean, user, 0);

        return status;
    }
    
    /**
     * @param fullId
     * @param user
     * @param outBean
     * @throws MYException
     */
    public int processOutStutus1(final String fullId, final User user, final OutBean outBean)
        throws MYException
    {
        int result = 0;

        	int nextStatus = OutConstant.BUY_STATUS_LOCATION_MANAGER_CHECK;
            try
            {
                outDAO.modifyOutStatus(fullId, nextStatus);

                result = nextStatus;
            }
            catch (Exception e)
            {
                throw new MYException(e.toString());
            }

            // 入库直接通过
            importLog.info(fullId + ":" + user.getStafferName() + ":" + nextStatus
                           + ":redrectFrom:" + outBean.getStatus());

        return result;
    }

    private int processOutStutus3(final String fullId, final User user, final OutBean outBean)
            throws MYException
    {
        int result = 0;

        int nextStatus = OutConstant.BUY_STATUS_SUBMIT;
        try
        {
            outDAO.modifyOutStatus(fullId, nextStatus);

            result = nextStatus;
        }
        catch (Exception e)
        {
            throw new MYException(e.toString());
        }

        // 入库直接通过
        importLog.info(fullId + ":" + user.getStafferName() + ":" + nextStatus
                + ":redrectFrom:" + outBean.getStatus());

        return result;
    }

    @Override
    public int passWithZs(String fullId, User user, int nextStatus, String reason, String customerDescription, String depotpartId) throws MYException {
        _logger.info("***fullId***"+fullId+"***nextStatus***"+nextStatus);

        List<OutBean> outBeans = this.getZsOrders(0, fullId, nextStatus, true);

        //待商务审批时检查库存
        if (nextStatus == OutConstant.STATUS_MANAGER_PASS
                || nextStatus == OutConstant.STATUS_FLOW_PASS){
            // 先检查赠品库存
            List<BaseBean> baseList = new ArrayList<>();
            if (!ListTools.isEmptyOrNull(outBeans)){
                _logger.info("****ZS orders need to auto approve****"+outBeans.size());
                for (OutBean o: outBeans){
                    List<BaseBean> baseList0 = this.baseDAO.queryEntityBeansByFK(o.getFullId());
                    baseList.addAll(baseList0);
                }
            } else{
                _logger.info("****ZS orders need to auto approve****"+outBeans.size());
            }

            //如果有赠品再加上原销售单库存,因为可能赠品和销售单包含同一商品，必须合并商品项检查库存才行
            if (!ListTools.isEmptyOrNull(baseList)){
                List<BaseBean> baseList0 = this.baseDAO.queryEntityBeansByFK(fullId);
                baseList.addAll(baseList0);
                List<BaseBean> mergeItems = this.mergeItems(baseList);
                _logger.info("***mergeItems***"+mergeItems);
                //检查库存
                this.splitBase(mergeItems);
            }
        }

        //审核销售单
        int result = this.pass(fullId, user, nextStatus, reason, customerDescription, depotpartId);
        //#593 审核赠品
        if (!ListTools.isEmptyOrNull(outBeans)){
            _logger.info("****ZS orders need to auto approve****"+outBeans.size());
            for (OutBean o: outBeans){
                String zsFullId = o.getFullId();
                result = this.pass(zsFullId, user, nextStatus, reason, customerDescription, depotpartId);
                _logger.info(zsFullId+"*****auto approve ZS****");
            }
        } else{
            _logger.info("****ZS orders need to auto approve****"+outBeans.size());
        }

        return result;
    }

    private List<BaseBean> mergeItems(List<BaseBean> items){
        List<BaseBean> result = new ArrayList<>();
        //<deportpartId_productId_owner, amount>
        Map<String, BaseBean> map = new HashMap<>();
        for (BaseBean item: items){
            String key = item.getDepotpartId()+"_"+item.getProductId()+"_"+item.getOwner();
            if (map.containsKey(key)){
                BaseBean i = map.get(key);
                i.setAmount(i.getAmount()+item.getAmount());
            } else{
                map.put(key, item);
            }
        }

        result.addAll(map.values());
        return result;
    }

    /**
     * CORE 审核通过(这里只有销售单/入库单才有此操作)分公司经理审核/结算中心/物流审批/库管发货
     * 
     * @param fullId
     * @param user
     * @param depotpartId
     *            废弃
     * @return
     * @throws Exception
     */
    public int pass1(final String fullId, final User user, final int nextStatus,
                    final String reason, final String depotpartId)
        throws MYException
    {
        final OutBean outBean = outDAO.find(fullId);
        
//        checkPass(outBean);

        final int oldStatus = outBean.getStatus();
//        final DepotBean depot = checkDepotInPass(nextStatus, outBean);

        // LOCK 销售单/入库单通过(这里是销售单库存变动的核心)
        synchronized (PublicLock.PRODUCT_CORE)
        {
            // 入库操作在数据库事务中完成
            TransactionTemplate tran = new TransactionTemplate(transactionManager);
            try
            {
                tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        int newNextStatus = nextStatus;

                        importLog.info(outBean.getFullId() + ":" + user.getStafferName() + ":"
                                       + newNextStatus + ":redrectFrom:" + oldStatus);
                        
                        // 修改状态
                        outDAO.modifyOutStatus(outBean.getFullId(), newNextStatus);
                        
                        //handerPassBuy(fullId, user, outBean, newNextStatus);

                        addOutLog(fullId, user, outBean, reason, SailConstant.OPR_OUT_PASS,
                            newNextStatus);

                        // 把状态放到最新的out里面
                        outBean.setStatus(newNextStatus);

                        // OSGI 监听实现
                        Collection<OutListener> listenerMapValues = listenerMapValues();

                        for (OutListener listener : listenerMapValues)
                        {
                            try
                            {
                                listener.onPass(user, outBean);
                            }
                            catch (MYException e)
                            {
                                throw new RuntimeException(e.getErrorContent(), e);
                            }
                        }

                        notifyOut(outBean, user, 0);

                        return Boolean.TRUE;
                    }
                });
            }
            catch (TransactionException e)
            {
                _logger.error(e, e);
                throw new MYException("数据库内部错误");
            }
            catch (DataAccessException e)
            {
                _logger.error(e, e);
                throw new MYException("数据库内部错误");
            }
            catch (Exception e)
            {
                _logger.error(e, e);
                throw new MYException("处理异常:" + e.getMessage());
            }
        }

        // 更新后的状态
        return outBean.getStatus();

    }

    @Override
    public String exchange(final OutBean outBean, final Map dataMap, final User user, String[] strings, String[] strings2) throws MYException {
        _logger.info("***********exchange out bean*****"+outBean);

        ParamterMap request = new ParamterMap(dataMap);

        String fullId = request.getParameter("fullId");

        String dutyId = request.getParameter("dutyId");

        dataMap.put("tmdutyId", dutyId);
        if (StringTools.isNullOrNone(fullId))
        {
            // 先保存
            String id = getAll(commonDAO.getSquence());
            LocationBean location = locationDAO.find(outBean.getLocationId());

            if (location == null)
            {
                _logger.error("区域不存在:" + outBean.getLocationId());

                throw new MYException("区域不存在:" + outBean.getLocationId());
            }
            //String flag = location.getCode();
            String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());

            String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

            fullId = flag + time + id;

            outBean.setId(getOutId(id));

            outBean.setFullId(fullId);
            dataMap.put("modify", false);
        }
        else
        {
            dataMap.put("modify", true);
        }
//        final String totalss = request.getParameter("totalss");
//
//        outBean.setTotal(MathTools.parseDouble(totalss));

        outBean.setStatus(OutConstant.STATUS_SAVE);

        outBean.setPay(OutConstant.PAY_NOT);

        outBean.setInway(OutConstant.IN_WAY_NO);
        // 获得baseList
        final String[] nameList = request.getParameter("nameList").split("~");
        final String[] idsList = request.getParameter("idsList").split("~");
//        final String[] showIdList = request.getParameter("showIdList").split("~");
//        final String[] showNameList = request.getParameter("showNameList").split("~");
//        final String[] unitList = request.getParameter("unitList").split("~");
        final String[] amontList = request.getParameter("amontList").split("~");
        // 含税价
        final String[] priceList = request.getParameter("priceList").split("~");

        // 输入价格
        final String[] inputPriceList = request.getParameter("inputPriceList").split("~");

        // 显示成本(只有V5有)
        final String[] showCostList = request.getParameter("showCostList").split("~");
        // 成本
        final String[] desList = request.getParameter("desList").split("~");

        final String[] otherList = request.getParameter("otherList").split("~");

        _logger.info(fullId + "/nameList/" + request.getParameter("nameList"));

        _logger.info(fullId + "/idsList/" + request.getParameter("idsList"));

        _logger.info(fullId + "/totalList/" + request.getParameter("totalList"));

//        _logger.info(fullId + "/price/" + request.getParameter("priceList"));

        _logger.info(fullId + "/inputPriceList/" + request.getParameter("inputPriceList"));

        _logger.info(fullId + "/showCostList/" + request.getParameter("showCostList"));

        _logger.info(fullId + "/desList/" + request.getParameter("desList"));

        _logger.info(fullId + "/otherList/" + request.getParameter("otherList"));

        // 组织BaseBean
//        double ttatol = 0.0d;
//        for (int i = 0; i < nameList.length; i++ )
//        {
//            try{
//            ttatol += (Double.parseDouble(priceList[i]) * Integer.parseInt(amontList[i]));
//
//        }
//        outBean.setTotal(ttatol);

        outBean.setCurcredit(0.0d);

        outBean.setStaffcredit(0.0d);

        if (StringTools.isNullOrNone(outBean.getCustomerId())
                || CustomerConstant.PUBLIC_CUSTOMER_ID.equals(outBean.getCustomerId()))
        {
            outBean.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);

            outBean.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);
        }

        if (StringTools.isNullOrNone(outBean.getInvoiceId()))
        {
            outBean.setHasInvoice(OutConstant.HASINVOICE_NO);
        }
        else
        {
            outBean.setHasInvoice(OutConstant.HASINVOICE_YES);
        }

        // 行业属性
        setInvoiceId(outBean);

        final StafferBean stafferBean = stafferDAO.find(user.getStafferId());

        // 增加管理员操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    if ((Boolean)dataMap.get("modify"))
                    {
                        _logger.info("***********exchange out bean1111111111111111*****");
                        // 在删除前检查单据状态是否为保存或驳回
                        OutBean coutBean = outDAO.find(outBean.getFullId());

                        if (coutBean.getStatus() != OutConstant.STATUS_SAVE && coutBean.getStatus() != OutConstant.STATUS_REJECT)
                        {
                            throw new RuntimeException("销售单"+outBean.getFullId()+"已不是保存或驳回状态，当前状态是："+OutHelper.getStatus(coutBean.getStatus())+",不可修改");
                        }

                        outDAO.deleteEntityBean(outBean.getFullId());

                        baseDAO.deleteEntityBeansByFK(outBean.getFullId());

//                        distributionDAO.deleteEntityBeansByFK(outBean.getFullId());
                    }

                    // 组织BaseBean
                    boolean addSub = false;

                    boolean hasZero = false;

                    double total = 0.0d;

                    List<BaseBean> baseList = new ArrayList();

                    boolean sailJiuBi = false;
                    List<ProductBean> tempProductList = new ArrayList<ProductBean>();

                    int accountPeroid = 0;

//                    boolean isManagerPass = false;
                    // 处理每个base
                    for (int i = 0; i < nameList.length; i++ )
                    {
                        BaseBean base = new BaseBean();

                        base.setId(commonDAO.getSquenceString());

                        // 允许存在
                        base.setAmount(MathTools.parseInt(amontList[i]));

                        if (base.getAmount() == 0)
                        {
                            continue;
                        }

                        base.setOutId(outBean.getFullId());

                        base.setProductId(idsList[i]);

                        if (StringTools.isNullOrNone(base.getProductId()))
                        {
                            throw new RuntimeException("产品ID为空,数据不完备");
                        }

                        ProductBean product = productDAO.find(base.getProductId());

                        if (product == null)
                        {
                            throw new RuntimeException("产品为空,数据不完备");
                        }

                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                                && !nameList[i].trim().equals(product.getName().trim()))
                        {
                            throw new RuntimeException("产品名不匹配,请重新操作.申请:" + nameList[i].trim()
                                    + ".实际:" + product.getName());
                        }

                        // 旧币的产品必须单独销售，不允许和其他的产品类型一起销售
                        if (product.getConsumeInDay() == ProductConstant.PRODUCT_OLDGOOD)
                        {
                            sailJiuBi = true;
                        }

                        tempProductList.add(product);

                        // 产品名称来源于数据库
                        base.setProductName(product.getName());

                        base.setUnit("套");

                        // 赠送的价格为0
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
                        {
                            base.setPrice(0.0d);
                        }
                        else
                        {
                            try {
                                base.setPrice(MathTools.parseDouble(priceList[i]));
                            }catch (Exception e){}
                        }

                        if (base.getPrice() == 0)
                        {
                            hasZero = true;
                        }

                        // 销售价格动态获取的
                        base.setValue(base.getAmount() * base.getPrice());

                        total += base.getValue();

                        // 入库单是没有showId的 - 作废
//                        if (showNameList != null && showNameList.length >= (i + 1))
//                        {
//                            base.setShowId(showIdList[i]);
//                            base.setShowName(showNameList[i]);
//                        }

                        // 这里需要处理99的其他入库,因为其他入库是没有完成的otherList
                        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                                && outBean.getOutType() == OutConstant.OUTTYPE_IN_OTHER)
                        {
                            base.setCostPrice(MathTools.parseDouble(desList[i]));
                            base.setCostPriceKey(StorageRelationHelper.getPriceKey(base
                                    .getCostPrice()));

                            base.setOwner("0");

                            // 默认仓区
                            DepotpartBean defaultOKDepotpart = depotpartDAO
                                    .findDefaultOKDepotpart(outBean.getLocation());

                            if (defaultOKDepotpart == null)
                            {
                                throw new RuntimeException("没有默认的良品仓,请确认操作");
                            }

                            base.setDepotpartId(defaultOKDepotpart.getId());
                        }
                        else
                        {
                            _logger.info("***********exchange out 5555555555555*****");
                            // ele.productid + '-' + ele.price + '-' + ele.stafferid + '-' + ele.depotpartid
//                            String[] coreList = otherList[i].split("-");
//
//                            if (coreList.length != 4)
//                            {
//                                throw new RuntimeException("数据不完备");
//                            }
//
//                            // 寻找具体的产品价格位置
//                            try{
//                                base.setCostPrice(MathTools.parseDouble(coreList[1]));
//                            }catch(Exception e){e.printStackTrace();}
//
//                            base.setCostPriceKey(StorageRelationHelper.getPriceKey(base
//                                    .getCostPrice()));
//
//                            base.setOwner(coreList[2]);
//
//                            base.setDepotpartId(coreList[3]);
                        }


                        if (StringTools.isNullOrNone(base.getOwner()))
                        {
                            base.setOwner("0");
                        }

                        if ("0".equals(base.getOwner()))
                        {
                            base.setOwnerName("公共");
                        }
                        else
                        {
                            StafferBean sb = stafferDAO.find(base.getOwner());

                            if (sb == null)
                            {
                                throw new RuntimeException("所属职员不存在,请确认操作");
                            }

                            base.setOwnerName(sb.getName());
                        }

                        //TODO
//                        DepotpartBean deport = depotpartDAO.find(base.getDepotpartId());
//
//                        if (deport == null)
//                        {
//                            throw new RuntimeException("仓区不存在,请确认操作");
//                        }
//
//                        base.setDepotpartName(deport.getName());

                        // 调拨的时候有bug啊
                        base.setLocationId(outBean.getLocation());

                        // 其实也是成本
//                        base.setDescription(desList[i].trim());

//                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
//                        {
//                            // 显示成本(V5新功能)
//                            base.setInputPrice(MathTools.parseDouble(showCostList[i]));
//                        }
//                        else
//                        {
//                            // 入库单
//                            if (inputPriceList != null && inputPriceList.length > i)
//                            {
//                                // 兼容
//                                base.setInputPrice(MathTools.parseDouble(inputPriceList[i]));
//                            }
//                        }
//
//                        double sailPrice = 0.0d;
//
//                        sailPrice = product.getSailPrice();



                        baseList.add(base);

                        base.setMtype(outBean.getMtype());

                        // 增加单个产品到base表
                        baseDAO.saveEntityBean(base);

                        addSub = true;
                    }


                    // 重新计算价格
                    outBean.setTotal(total);

                    outBean.setBaseList(baseList);

                    // 保存入库单
                    try
                    {
                        outBean.setOutType(OutConstant.OUTTYPE_IN_EXCHANGE);
                        outBean.setInway(OutConstant.IN_WAY);
                        saveOutInner(outBean);
                    }
                    catch (MYException e1)
                    {
                        throw new RuntimeException(e1.toString());
                    }

                    if ( !addSub)
                    {
                        throw new RuntimeException("没有产品数量");
                    }


                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }

        _logger.info(user.getStafferName() + "/" + user.getName() + "/ADD:" + outBean);

        return fullId;
    }

    @Override
    public int submitExchange(final String fullId, final User user, final int storageType) throws MYException {

        // LOCK 库存提交(当是入库单的时候是变动库存的)
        synchronized (PublicLock.PRODUCT_CORE)
        {
            Integer result = 0;

            try
            {
                // 增加管理员操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);

                result = (Integer)tran.execute(new TransactionCallback()
                {
                    public Object doInTransaction(TransactionStatus arg0)
                    {
                        try
                        {
                            return submitWithOutAffair3(fullId, user, storageType);
                        }
                        catch (MYException e)
                        {
                            _logger.error(e, e);

                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }
                });
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                throw new MYException(e.getMessage());
            }

            return result;
        }
    }

    private int submitWithOutAffair3(final String fullId, final User user, int type)
            throws MYException
    {
        final OutBean outBean = outDAO.find(fullId);
        if (outBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        // 检查日志核对
        int outStatusInLog = this.findOutStatusInLog(outBean.getFullId());
        if (outStatusInLog != -1 && outStatusInLog != OutConstant.STATUS_REJECT
                && outStatusInLog != outBean.getStatus())
        {
            String msg = "严重错误,当前单据的状态应该是:" + OutHelper.getStatus(outStatusInLog) + ",而不是"
                    + OutHelper.getStatus(outBean.getStatus()) + ".请联系管理员确认此单的正确状态!";

            loggerError(outBean.getFullId() + ":" + msg);

            throw new MYException(msg);
        }

        // CORE 修改库单(销售/入库)的状态(信用额度处理)
        int status = processOutStutus3(fullId, user, outBean);

//        try
//        {
//            if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
//                    && outBean.getOutType() == OutConstant.OUTTYPE_IN_EXCHANGE )
//            {
//                outDAO.modifyOutStatus(fullId, OutConstant.BUY_STATUS_SUBMIT);
//
//                status = OutConstant.BUY_STATUS_SUBMIT;
//            }
//        }
//        catch (Exception e)
//        {
//            _logger.error(e, e);
//
//            throw new MYException(e);
//        }


        // 处理在途(销售无关)/调入接受时
        int result = processBuyOutInWay(user, fullId, outBean);

        // 在途改变状态
        if (result != -1)
        {
            status = result;
        }

        String desc = outBean.getDescription();

        int idx = desc.indexOf("&&");

        if (idx == -1)
        {
            // 增加数据库日志
            addOutLog(fullId, user, outBean, "提交", SailConstant.OPR_OUT_PASS, status);
        }
        else
        {
            String newDesc = desc.substring(0, idx);

            String logDesc = desc.substring(idx + 2, desc.length());

//        	outBean.setDescription(newDesc);

            outDAO.updateDescription(fullId, newDesc);

            if (StringTools.isNullOrNone(logDesc))
            {
                logDesc = "提交";
            }
            // 增加数据库日志
            addOutLog(fullId, user, outBean, logDesc, SailConstant.OPR_OUT_PASS, status);
        }

        outBean.setStatus(status);

        notifyOut(outBean, user, 0);

        return status;
    }

    @Exceptional
    @Transactional(rollbackFor = {MYException.class})   
    public String diaoBo(final OutBean outBean, final Map dataMap, final User user,String proid[],String amount[])
    throws MYException
    {
    	ParamterMap request = new ParamterMap(dataMap);

        String fullId = request.getParameter("fullId");
        
        String dutyId = request.getParameter("dutyId");
        
        dataMap.put("tmdutyId", dutyId);
        if (StringTools.isNullOrNone(fullId))
        {
            // 先保存
            String id = getAll(commonDAO.getSquence());
            LocationBean location = locationDAO.find(outBean.getLocationId());

            if (location == null)
            {
                _logger.error("区域不存在:" + outBean.getLocationId());

                throw new MYException("区域不存在:" + outBean.getLocationId());
            }
            //String flag = location.getCode();
            String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());

            String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

            fullId = flag + time + id;

            outBean.setId(getOutId(id));

            outBean.setFullId(fullId);
            dataMap.put("modify", false);
        }
        else
        {
            dataMap.put("modify", true);
        }
        final String totalss = request.getParameter("totalss");

        outBean.setTotal(MathTools.parseDouble(totalss));
        
        outBean.setStatus(OutConstant.STATUS_SAVE);

        outBean.setPay(OutConstant.PAY_NOT);

        outBean.setInway(OutConstant.IN_WAY_NO);
        // 获得baseList
        final String[] nameList = request.getParameter("nameList").split("~");
        final String[] idsList = request.getParameter("idsList").split("~");
//        final String[] showIdList = request.getParameter("showIdList").split("~");
//        final String[] showNameList = request.getParameter("showNameList").split("~");
//        final String[] unitList = request.getParameter("unitList").split("~");
        final String[] amontList = request.getParameter("amontList").split("~");
        // 含税价
        final String[] priceList = request.getParameter("priceList").split("~");

        // 输入价格
        final String[] inputPriceList = request.getParameter("inputPriceList").split("~");

        // 显示成本(只有V5有)
        final String[] showCostList = request.getParameter("showCostList").split("~");
        // 成本
        final String[] desList = request.getParameter("desList").split("~");

        final String[] otherList = request.getParameter("otherList").split("~");

        _logger.info(fullId + "/nameList/" + request.getParameter("nameList"));

        _logger.info(fullId + "/idsList/" + request.getParameter("idsList"));

        _logger.info(fullId + "/totalList/" + request.getParameter("totalList"));

//        _logger.info(fullId + "/price/" + request.getParameter("priceList"));

        _logger.info(fullId + "/inputPriceList/" + request.getParameter("inputPriceList"));

        _logger.info(fullId + "/showCostList/" + request.getParameter("showCostList"));

        _logger.info(fullId + "/desList/" + request.getParameter("desList"));

        _logger.info(fullId + "/otherList/" + request.getParameter("otherList"));

        // 组织BaseBean
        double ttatol = 0.0d;
        for (int i = 0; i < nameList.length; i++ )
        {
            ttatol += (Double.parseDouble(priceList[i]) * Integer.parseInt(amontList[i]));
        }
        outBean.setTotal(ttatol);

        outBean.setCurcredit(0.0d);

        outBean.setStaffcredit(0.0d);

        if (StringTools.isNullOrNone(outBean.getCustomerId())
            || CustomerConstant.PUBLIC_CUSTOMER_ID.equals(outBean.getCustomerId()))
        {
            outBean.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);

            outBean.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);
        }

        if (StringTools.isNullOrNone(outBean.getInvoiceId()))
        {
            outBean.setHasInvoice(OutConstant.HASINVOICE_NO);
        }
        else
        {
            outBean.setHasInvoice(OutConstant.HASINVOICE_YES);
        }

        // 赠送的价格为0
        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
            && outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
        {
            outBean.setTotal(0.0d);
        }

        // 行业属性
        setInvoiceId(outBean);

        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON)
        {
        	String eventId = request.getParameter("eventId");
        	
        	if (StringTools.isNullOrNone(eventId))
        		eventId = "";
        	
        	PromotionBean promBean = promotionDAO.find(eventId);
        	
            if (outBean.getPromValue() > 0 || (promBean==null?false:promBean.getRebateType()==2))//此处折扣类型为增加信用分也绑定活动单号
                
                outBean.setPromStatus(0);
            else{
                
                outBean.setEventId("");
                outBean.setRefBindOutId("");
                outBean.setPromStatus(-1);
            }
        }

        final StafferBean stafferBean = stafferDAO.find(user.getStafferId());

        // 增加管理员操作在数据库事务中完成
        TransactionTemplate tran = new TransactionTemplate(transactionManager);
        try
        {
            tran.execute(new TransactionCallback()
            {
                public Object doInTransaction(TransactionStatus arg0)
                {
                    if ((Boolean)dataMap.get("modify"))
                    {
                        // 在删除前检查单据状态是否为保存或驳回
                        OutBean coutBean = outDAO.find(outBean.getFullId());
                        
                        if (coutBean.getStatus() != OutConstant.STATUS_SAVE && coutBean.getStatus() != OutConstant.STATUS_REJECT)
                        {
                            throw new RuntimeException("销售单"+outBean.getFullId()+"已不是保存或驳回状态，当前状态是："+OutHelper.getStatus(coutBean.getStatus())+",不可修改");
                        }
                        
                        outDAO.deleteEntityBean(outBean.getFullId());

                        baseDAO.deleteEntityBeansByFK(outBean.getFullId());
                        
//                        distributionDAO.deleteEntityBeansByFK(outBean.getFullId());
                    }

                    // 组织BaseBean
                    boolean addSub = false;

                    boolean hasZero = false;

                    double total = 0.0d;

                    List<BaseBean> baseList = new ArrayList();

                    boolean sailJiuBi = false;
                    List<ProductBean> tempProductList = new ArrayList<ProductBean>();
                    
                    int accountPeroid = 0;
                    
//                    boolean isManagerPass = false;
                    StringBuffer messsb = new StringBuffer();
                    
                    // 处理每个base
                    for (int i = 0; i < nameList.length; i++ )
                    {
                        BaseBean base = new BaseBean();

                        base.setId(commonDAO.getSquenceString());

                        // 允许存在
                        base.setAmount(MathTools.parseInt(amontList[i]));

                        if (base.getAmount() == 0)
                        {
                            continue;
                        }

                        base.setOutId(outBean.getFullId());

                        base.setProductId(idsList[i]);

                        if (StringTools.isNullOrNone(base.getProductId()))
                        {
                            throw new RuntimeException("产品ID为空,数据不完备");
                        }

                        ProductBean product = productDAO.find(base.getProductId());

                        if (product == null)
                        {
                            throw new RuntimeException("产品为空,数据不完备");
                        }

                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                            && !nameList[i].trim().equals(product.getName().trim()))
                        {
                            throw new RuntimeException("产品名不匹配,请重新操作.申请:" + nameList[i].trim()
                                                       + ".实际:" + product.getName());
                        }

                        // 旧币的产品必须单独销售，不允许和其他的产品类型一起销售
                        if (product.getConsumeInDay() == ProductConstant.PRODUCT_OLDGOOD)
                        {
                            sailJiuBi = true;
                        }

                        tempProductList.add(product);

                        // 产品名称来源于数据库
                        base.setProductName(product.getName());

                        base.setUnit("套");

                        // 赠送的价格为0
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                            && outBean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT)
                        {
                            base.setPrice(0.0d);
                        }
                        else
                        {
                            base.setPrice(MathTools.parseDouble(priceList[i]));
                        }

                        if (base.getPrice() == 0)
                        {
                            hasZero = true;
                        }

                        // 销售价格动态获取的
                        base.setValue(base.getAmount() * base.getPrice());

                        total += base.getValue();

                        // 入库单是没有showId的 - 作废
//                        if (showNameList != null && showNameList.length >= (i + 1))
//                        {
//                            base.setShowId(showIdList[i]);
//                            base.setShowName(showNameList[i]);
//                        }

                        // 这里需要处理99的其他入库,因为其他入库是没有完成的otherList
                        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                            && outBean.getOutType() == OutConstant.OUTTYPE_IN_OTHER)
                        {
                            base.setCostPrice(MathTools.parseDouble(desList[i]));
                            base.setCostPriceKey(StorageRelationHelper.getPriceKey(base
                                .getCostPrice()));

                            base.setOwner("0");

                            // 默认仓区
                            DepotpartBean defaultOKDepotpart = depotpartDAO
                                .findDefaultOKDepotpart(outBean.getLocation());

                            if (defaultOKDepotpart == null)
                            {
                                throw new RuntimeException("没有默认的良品仓,请确认操作");
                            }

                            base.setDepotpartId(defaultOKDepotpart.getId());
                        }
                        else
                        {
                            // ele.productid + '-' + ele.price + '-' + ele.stafferid + '-' + ele.depotpartid
                            String[] coreList = otherList[i].split("-");

                            if (coreList.length != 4)
                            {
                                throw new RuntimeException("数据不完备");
                            }

                            // 寻找具体的产品价格位置
                            base.setCostPrice(MathTools.parseDouble(coreList[1]));

                            base.setCostPriceKey(StorageRelationHelper.getPriceKey(base
                                .getCostPrice()));

                            base.setOwner(coreList[2]);

                            base.setDepotpartId(coreList[3]);
                        }

                        // 这里需要核对价格 调拨
                        if (outBean.getType() == OutConstant.OUT_TYPE_INBILL
                            && (outBean.getOutType() == OutConstant.OUTTYPE_IN_MOVEOUT || outBean
                                .getOutType() == OutConstant.OUTTYPE_IN_DROP))
                        {
                            if ( !MathTools.equal(base.getPrice(), base.getCostPrice()))
                            {
                                throw new RuntimeException("调拨/报废的时候价格必须相等");
                            }
                        }

                        if (StringTools.isNullOrNone(base.getOwner()))
                        {
                            base.setOwner("0");
                        }

                        if ("0".equals(base.getOwner()))
                        {
                            base.setOwnerName("公共");
                        }
                        else
                        {
                            StafferBean sb = stafferDAO.find(base.getOwner());

                            if (sb == null)
                            {
                                throw new RuntimeException("所属职员不存在,请确认操作");
                            }

                            base.setOwnerName(sb.getName());
                        }

                        DepotpartBean deport = depotpartDAO.find(base.getDepotpartId());

                        if (deport == null)
                        {
                            throw new RuntimeException("仓区不存在,请确认操作");
                        }

                        base.setDepotpartName(deport.getName());

                        // 销售单的时候仓库必须一致
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                            && !deport.getLocationId().equals(outBean.getLocation()))
                        {
                            throw new RuntimeException("销售必须在一个仓库下面");
                        }

                        // 调拨的时候有bug啊
                        base.setLocationId(outBean.getLocation());

                        // 其实也是成本
                        base.setDescription(desList[i].trim());

                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                        {
                            // 显示成本(V5新功能)
                            base.setInputPrice(MathTools.parseDouble(showCostList[i]));
                        }
                        else
                        {
                            // 入库单
                            if (inputPriceList != null && inputPriceList.length > i)
                            {
                                // 兼容
                                base.setInputPrice(MathTools.parseDouble(inputPriceList[i]));
                            }
                        }

                        double sailPrice = 0.0d;
                        
                        double minPrice = 0.0d;

                        sailPrice = product.getSailPrice();
                        
                        minPrice = sailPrice;
                        
                        if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                        {
                        	// 毛利，毛利率（针对业务员的）
                        	double profit = 0.0d;
                        	
                        	double profitRatio = 0.0d;
                        	
                        	if (base.getValue() != 0)
                        	{
                            	profit = base.getAmount() * (base.getPrice() - base.getInputPrice());
                            	
                            	profitRatio = profit / base.getValue();
                        	}

                        	base.setProfit(profit);
                        	base.setProfitRatio(profitRatio);
                        	
                        	PriceConfigBean priceConfigBean = priceConfigManager
                            						.getPriceConfigBean(product.getId(), outBean.getIndustryId(), outBean.getStafferId());
                        	
                        	//operationLog.info("销售单号： "+outBean.getFullId() + ", priceConfigBean:" + priceConfigBean);
                        	
                        	if (null != priceConfigBean)
                        	{
                        		sailPrice = priceConfigBean.getPrice();
                        		
                        		minPrice = priceConfigBean.getMinPrice();
                        	}
                        	
                        	// 检查到款天数（销售账期）及销售价是否低于规定的售价
                        	AuditRuleItemBean auditRuleItemBean = auditRuleManager
                        					.getAuditRuleItem(product.getId(), outBean.getIndustryId(), 
                        							outBean.getOutType(), outBean.getReserve3());
                        	
                        	//operationLog.info("销售单号： "+outBean.getFullId() + ", auditRuleItemBean:" + auditRuleItemBean);
                        	
                        	if (null != auditRuleItemBean)
                        	{
                        		int peroid1 = auditRuleItemBean.getAccountPeriod();
                        		int peroid2 = auditRuleItemBean.getProductPeriod();
                        		int peroid3 = auditRuleItemBean.getProfitPeriod();
                        		
                        		int minPeroid = OutHelper.compare3IntMin(peroid1, peroid2, peroid3);
                        		
                        		if (accountPeroid == 0)
                        		{
                        			accountPeroid = minPeroid;
                        		}
                        		else
                        		{
                        			accountPeroid = Math.min(accountPeroid, minPeroid);
                        		}
                        		
                        		// 检查价格,成交价小于最低售价
                        		if (base.getPrice()<minPrice)
                        		{
                        			if (outBean.getOutType() != OutConstant.OUTTYPE_OUT_PRESENT)
                        			{
                        				if (auditRuleItemBean.getLtSailPrice() == AuditRuleConstant.LESSTHANSAILPRICE_NO)
                            			{
                            				throw new RuntimeException("售价不能低于最低售价，最低售价为："+ MathTools.round2(minPrice)+" ,不允许销售.");
                            			}
                            			else
                            			{
                            				double cha = minPrice - base.getPrice();
                            				
                            				if (cha > minPrice * auditRuleItemBean.getDiffRatio())
                            				{
                            					operationLog.info("销售单号： "+outBean.getFullId() + ", 售价低于最低售价时，差异值大于差异比例规定的范围");
                            					
                            					messsb.append("售价低于最低售价时，差异值大于差异比例规定的范围;");
                            				}
                            			}
                        			}
                        		}
                        		else // 成交价大于最低售价，则判断毛利率
                        		{
                        			double ratioUp = auditRuleItemBean.getRatioUp()/100d; 
                        			
                        			double ratioDown = auditRuleItemBean.getRatioDown()/100d;
                        			
                        			if (ratioUp > 0)
                        			{
                        				if (ratioDown > profitRatio)
                        				{
                        					operationLog.info("销售单号： "+outBean.getFullId() + ", 商品的毛利率小于规则中规定的毛利率的下限");
                        					
                        					messsb.append("商品的毛利率小于规则中规定的毛利率的下限;");
                        				}
                        			}
                        			
                        			if (auditRuleItemBean.getMinRatio() > 0)
                        			{
                        				if (profitRatio < auditRuleItemBean.getMinRatio())
                        				{
                        					operationLog.info("销售单号： "+outBean.getFullId() + ", 商品的毛利率小于规则中规定的最小毛利率.");
                        					
                        					messsb.append("商品的毛利率小于规则中规定的最小毛利率;");
                        				}
                        			}
                        		}
                        	}
                        }
                        
                        // 公卖
                        if ("0".equals(base.getOwner()))
                        {
                            // 获取销售配置
                            SailConfBean sailConf = sailConfigManager.findProductConf(stafferBean,
                                product);
                            
                            // 总部结算价(产品结算价 * (1 + 总部结算率))
                            base.setPprice(sailPrice
                                           * (1 + sailConf.getPratio() / 1000.0d));

                            //#647
                            if(sailConf.getIprice() > 0){
                                base.setIprice(sailConf.getIprice());
                            } else{
                                // 事业部结算价(产品结算价 * (1 + 总部结算率 + 事业部结算率))
                                base.setIprice(sailPrice
                                        * (1 + sailConf.getIratio() / 1000.0d + sailConf
                                        .getPratio() / 1000.0d));
                            }

                            // 业务员结算价就是事业部结算价
                            base.setInputPrice(base.getIprice());

                            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                            {
                                // 发现一些异常,这里保护一下
                                if (base.getInputPrice() == 0)
                                {
                                    throw new RuntimeException("业务员结算价不能为0");
                                }
                            }
                        }
                        else
                        {
                            // 私卖
                            base.setPprice(base.getInputPrice());
                            base.setIprice(base.getInputPrice());
                        }

                        baseList.add(base);

                        base.setMtype(outBean.getMtype());                                                       

                        // 增加单个产品到base表
                        baseDAO.saveEntityBean(base);

                        addSub = true;
                    }

                    // 旧币的产品必须单独销售(或者都是旧币)，不允许和其他的产品类型一起销售
                    if (sailJiuBi&&false)
                    {
                        for (ProductBean each : tempProductList)
                        {
                            if (each.getConsumeInDay() != ProductConstant.PRODUCT_OLDGOOD)
                            {
                                throw new RuntimeException("旧货的产品必须单独销售(或者都是旧货)，不允许和其他的产品类型一起销售:"
                                                           + each.getName());
                            }
                        }
                    }

                    // 自卖的东西必须先卖掉
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                    {
                        for (BaseBean base : baseList)
                        {
                            if ("0".equals(base.getOwner()))
                            {
                                ConditionParse con = new ConditionParse();
                                con.addWhereStr();
                                con.addCondition("stafferId", "=", user.getStafferId());
                                con.addCondition("productId", "=", base.getProductId());

                                List<StorageRelationBean> selfRelation = storageRelationDAO
                                    .queryEntityBeansByCondition(con);

                                if (ListTools.isEmptyOrNull(selfRelation))
                                {
                                    continue;
                                }

                                int samont = 0;

                                for (StorageRelationBean seach : selfRelation)
                                {
                                    samont += seach.getAmount();
                                }

                                // 看看是否都在里面出售
                                int amount = 0;

                                for (BaseBean each : baseList)
                                {
                                    if (user.getStafferId().equals(each.getOwner())
                                        && base.getProductId().equals(each.getProductId()))
                                    {
                                        amount += each.getAmount();
                                    }
                                }

                                if (samont != amount)
                                {
                                    throw new RuntimeException("必须先销售自己名下的产品["
                                                               + base.getProductName() + "]");
                                }
                            }
                        }
                    }

                    // 销售单强制设置为赠送
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL && hasZero)
                    {
                        outBean.setOutType(OutConstant.OUTTYPE_OUT_PRESENT);
                    }

                 // 检查账期
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                    {
                    	// 黑名单（款到发货）回款天数为0
                    	if (outBean.getReserve3() == 1)
                    	{
                    		if (outBean.getReday() > 0)
                    		{
                    			//messsb.append("款到发货时，回款天数大于0;");
                    		}
                    	}
                    	else
                    	{
                    		if (outBean.getReday() > accountPeroid)
                    		{
                    			//messsb.append("回款天数大于规则中指定的账期;");
                    		}
                    	}
                    	
                    	// 下一审批流为结算中心（稽核）
//                    	if (isManagerPass)
//                    	{
                    		outBean.setFlowId(OutConstant.FLOW_MANAGER);
//                    	}
                    		
                    	outBean.setDescription(outBean.getDescription() + "&&" + messsb.toString());
                    }
                    
                    // 重新计算价格
                    outBean.setTotal(total);

                    // 促销金额不能大于本单总金额
                    if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                    {
                        if (outBean.getPromValue() > outBean.getTotal())
                        {
                            throw new RuntimeException("促销折扣金额不能大于本单总金额");
                        }
                    }
                    
                    outBean.setBaseList(baseList);

                    // 保存入库单
                    try
                    {
                    	outBean.setOutType(OutConstant.OUTTYPE_OUT_APPLY);
                    	outBean.setInway(OutConstant.IN_WAY);
                        saveOutInner(outBean);
                    }
                    catch (MYException e1)
                    {
                        throw new RuntimeException(e1.toString());
                    }

                    if ( !addSub)
                    {
                        throw new RuntimeException("没有产品数量");
                    }

                    // 防止溢出
                    if (isSwatchToSail(outBean.getFullId()))
                    {
                        try
                        {
                            checkSwithToSail(outBean.getRefOutFullId());
                        }
                        catch (MYException e)
                        {
                            throw new RuntimeException(e.getErrorContent(), e);
                        }
                    }

                    return Boolean.TRUE;
                }
            });
        }
        catch (TransactionException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("数据库内部错误");
        }
        catch (DataAccessException e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException(e.getCause().toString());
        }
        catch (Exception e)
        {
            _logger.error("增加库单错误：", e);
            throw new MYException("系统错误，请联系管理员:" + e);
        }

        _logger.info(user.getStafferName() + "/" + user.getName() + "/ADD:" + outBean);

        return fullId;
    }
    
    /**
     * 查询可退货的委托代销数据
     * 1.找出未生成结算单(含结算单与委托退货单)的数据
     * 2.找出可退的结算单
     * {@inheritDoc}
     */
	public List<BatchBackWrap> queryConsignCanBack(String customerId,
			String productId, int amount, StringBuilder builder) throws MYException
	{
		List<BatchBackWrap> bbbList = new ArrayList<BatchBackWrap>();
		
		ConditionParse con = new ConditionParse();
		
		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
		
		con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_OUT_CONSIGN);
		
		con.addCondition(" and OutBean.status in (3,4)");
		
		con.addCondition("OutBean.customerId", "=", customerId);
		
		con.addCondition("BaseBean.productId", "=", productId);
		
		List<BaseBean> list = baseDAO.queryBaseByOneCondition(con);
		
		for (BaseBean each : list)
		{
			int oriamount = each.getAmount();
			
			int settleAmount = 0;
			// 1.去掉所有生成结算单的数据（不含结算单退货部分）
			List<OutBalanceBean> obList = outBalanceDAO.queryExcludeSettleBack(each.getOutId(), OutConstant.OUTBALANCE_TYPE_SAILBACK);
			
			for (OutBalanceBean eachob : obList)
			{
				List<BaseBalanceBean> bbList =  baseBalanceDAO.queryEntityBeansByFK(eachob.getId());
				
				for (BaseBalanceBean eachb : bbList)
				{
					if (each.getId().equals(eachb.getBaseId()))
					{
						settleAmount += eachb.getAmount();
					}
				}
			}
			
			int canBack = oriamount - settleAmount;
			
			if (canBack > 0)
			{
				addBatchBackBeanToList(each, bbbList, canBack, 0, "");
			}
			
			// 2.去掉结算单中退货部分
			List<OutBalanceBean> obList1 = outBalanceDAO.queryByOutIdAndType(each.getOutId(), OutConstant.OUTBALANCE_TYPE_SAIL);
			
			List<BaseBalanceBean> refbList = new ArrayList<BaseBalanceBean>();
			
			for (OutBalanceBean eachob1 : obList1)
			{
				int hasBack = 0;
				
				refbList.clear();
				
				List<BaseBalanceBean> bbList1 =  baseBalanceDAO.queryEntityBeansByFK(eachob1.getId());
				
				List<OutBalanceBean> refList = outBalanceDAO.queryEntityBeansByFK(eachob1.getId(), AnoConstant.FK_FIRST);
				
				for (OutBalanceBean eachRef : refList)
				{
					refbList.addAll(baseBalanceDAO.queryEntityBeansByFK(eachRef.getId()));
				}
				
				for (BaseBalanceBean eachItem : bbList1)
				{
					if (eachItem.getBaseId().equals(each.getId()))
					{
						for (BaseBalanceBean eachItem1 : refbList)
						{
							if (eachItem1.getBaseId().equals(each.getId()))
							{
								hasBack += eachItem1.getAmount();
							}
						}
						
						// 结算单可退量
						canBack = eachItem.getAmount() - hasBack;
						
						if (canBack > 0)
						{
							addBatchBackBeanToList(each, bbbList, canBack, 1, eachob1.getId());
						}
					}
					else
						continue;
				}
			}
		}
		
		ProductBean pbean = productDAO.find(productId);
		
		// bbbList
		if (ListTools.isEmptyOrNull(bbbList))
		{
			builder.append("退库产品" + pbean.getName() +"数量不足，无量可退")
			.append("<Br>");
			
//			throw new MYException("退库产品[%s]数量不足，无量可退", pbean.getName());
		}
		else
		{
			int allAmount = 0;
			
			int excludeSettle = 0;
			
			// 先看未退的委托单＋　结算单未退货的够不够
			for (BatchBackWrap each : bbbList)
			{
				allAmount += each.getAmount();
				
				if (each.getType() == 0)
				{
					excludeSettle += each.getAmount();
				}
			}
			
			if (amount > allAmount)
			{
				builder.append("退库产品" +  pbean.getName() +"数量不足，委托未结算与已结算未退货之和为:" + allAmount +",当前要退的量为:" + amount)
				.append("<Br>");
//				throw new MYException("退库产品[%s]数量不足，委托未结算与已结算未退货之和为:[%s],当前要退的量为:[%s]", pbean.getName(), allAmount, amount);
			}
			else
			{
				// 按baseId 从小到大 排序
				Collections.sort(bbbList, new Comparator<BatchBackWrap>()
				{
					public int compare(BatchBackWrap o1, BatchBackWrap o2)
					{
						return Integer.parseInt(o1.getBaseId()) - Integer.parseInt(o2.getBaseId());
					}
					
				});
				
				// 如果未退部分不够，则占用已结算部分；如果够就从未退货部分确定退的数量
				if (amount > excludeSettle)
				{
					// 要占用结算单这么多数量
					int settle = amount - excludeSettle;
					
					List<BatchBackWrap> tmpList = new ArrayList<BatchBackWrap>();
					
					for (BatchBackWrap each : bbbList)
					{
						// 委托未退货不处理
						if (each.getType() == 0)
							continue;
						else
						{
							int eachSettle = each.getAmount();
							
							if (settle <= eachSettle)
							{
								each.setAmount(settle);
								
								tmpList.add(each);
								
								settle = 0;
							}
							else
							{
								tmpList.add(each);
								
								settle -= eachSettle;
							}
							
							if (settle == 0)
								break;
						}
					}
					
					// 去掉bbbList 中的type = 1
					for (Iterator<BatchBackWrap> iterator = bbbList.iterator(); iterator.hasNext(); )
					{
						BatchBackWrap bbb = iterator.next();
						
						if (bbb.getType() == 1)
							iterator.remove();
					}
					
					bbbList.addAll(tmpList);
				}
				else
				{
					List<BatchBackWrap> tmpList = new ArrayList<BatchBackWrap>();
					
					for (BatchBackWrap each : bbbList)
					{
						if (each.getType() == 1)
							continue;
						else
						{
							int current = each.getAmount();
							
							if (amount <= current)
							{
								each.setAmount(amount);
								
								tmpList.add(each);
								
								amount = 0;
							}
							else
							{
								tmpList.add(each);
								
								amount -= current;
							}
							
							if (amount == 0)
								break;
						}
					}
					
					// 去掉bbbList 中的type = 0
					bbbList.clear();
					
					bbbList.addAll(tmpList);
				}
			}
			
		}
		
		return bbbList;
	}

	/**
	 * 
	 * @param baseBean
	 * @param bbbList
	 * @param canBack
	 * @param type 委托时 表示已结算/未结算  ；销售出库时 表示付款/未付款
	 * @param refId
	 */
	private void addBatchBackBeanToList(BaseBean baseBean,
			List<BatchBackWrap> bbbList, int canBack, int type, String refId)
	{
		BatchBackWrap bbb = new BatchBackWrap();
		
		bbb.setProductId(baseBean.getProductId());
		bbb.setType(type); // 0 表示未结算产品
		bbb.setAmount(canBack);
		bbb.setRefOutFullId(baseBean.getOutId()); 
		bbb.setRefId(refId);
		bbb.setBaseId(baseBean.getId());
		
		bbbList.add(bbb);
	}
    
	@Transactional(rollbackFor = MYException.class)
	public boolean addBatchOutBalance(User user, List<OutBalanceBean> beanList)
	throws MYException {
        JudgeTools.judgeParameterIsNull(user, beanList);
		
		for (OutBalanceBean bean : beanList)
		{
			addOutBalanceInner(user, bean);
		}
		
		return true;
	}
	
	/**
	 * 根据客户，产品查询销售数据：
	 * 
	 * @type 0：表示未付款单据；1：表示已付款单据。要求时间远的，未付款的优先
	 * @otype 1:表示近6个月不足时，查询6个月前的数据。 用于区分是近6个月的查询还是在近6个月不足的情况下包含6个月前的数据进行查询判断。
	 * 
	 * {@inheritDoc}
	 */
	public List<BatchBackWrap> queryOutCanBack(String customerId,
			String productId, int amount, int type, int otype, StringBuilder builder)
			throws MYException
	{
		List<BatchBackWrap> bbbList = new ArrayList<BatchBackWrap>();
		
		String message = "";
		
		ConditionParse con = new ConditionParse();
		
		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
		
		con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_OUT_COMMON);
		
		con.addCondition(" and OutBean.status in (3,4)");
		
		con.addCondition("OutBean.customerId", "=", customerId);

		// 去掉参加过促销的单子
		con.addCondition(" and OutBean.promStatus not in (0,1)");
		
		con.addCondition("BaseBean.productId", "=", productId);
		
		// 近6个月
		if (type == 0)
		{
			message = "近6个月内";
			
			con.addCondition("OutBean.outTime", ">=", TimeTools.now_short(-180));
		}
		else
		{
			message = "2011年4月1日到现在";
			
			con.addCondition("OutBean.outTime", ">=", "2011-04-01");
		}
		
		String message2 = "";
		
		if (otype == 1)
			message2 = "近6个月内可退不足，但从";
		
		List<BaseBean> list = baseDAO.queryBaseByOneCondition(con);
		
		for (BaseBean each : list)
		{
			int oriamount = each.getAmount();
			
			int backAmount = 0;
			// 1.去掉已退货部分
			List<OutBean> obList = this.queryRefOut1(each.getOutId(), false);
			
			for (OutBean eachob : obList)
			{
				List<BaseBean> bbList = eachob.getBaseList();
				
				for (BaseBean eachb : bbList)
				{
					if (each.equals2(eachb))
					{
						backAmount += eachb.getAmount();
					}
				}
			}
			
			int canBack = oriamount - backAmount;
			
			if (canBack > 0)
			{
				OutBean out = outDAO.find(each.getOutId());
				
				// 销售出库时为是否付款 see @type
				addBatchBackBeanToList(each, bbbList, canBack, out.getPay(), "");
			}
		}
		
		ProductBean pbean = productDAO.find(productId);
		
		// bbbList
		if (ListTools.isEmptyOrNull(bbbList))
		{
			builder.append(message2).append(message).append("退库产品" + pbean.getName() +" 数量不足，无量可退")
			.append("<Br>");
			
//			throw new MYException("退库产品[%s]数量不足，无量可退", pbean.getName());
		}
		else
		{
			int allAmount = 0;
			
			// 未付款的量
			int excludeHasPay = 0;
			
			// 先看未退的委托单＋　结算单未退货的够不够
			for (BatchBackWrap each : bbbList)
			{
				allAmount += each.getAmount();
				
				if (each.getType() == 0)
				{
					excludeHasPay += each.getAmount();
				}
			}
			
			if (amount > allAmount)
			{
				builder.append(message2).append(message).append("退库产品" +  pbean.getName() +"数量不足，未退货之和为:" + allAmount +",当前要退的量为:" + amount)
				.append("<Br>");
//				throw new MYException("退库产品[%s]数量不足，委托未结算与已结算未退货之和为:[%s],当前要退的量为:[%s]", pbean.getName(), allAmount, amount);
			}
			else
			{
				// 按baseId 从小到大 排序
				Collections.sort(bbbList, new Comparator<BatchBackWrap>()
				{
					public int compare(BatchBackWrap o1, BatchBackWrap o2)
					{
						return Integer.parseInt(o1.getBaseId()) - Integer.parseInt(o2.getBaseId());
					}
					
				});
				
				// 如果未付款部分不够，则占用已付款部分；如果够就从未退货部分确定退的数量
				if (amount > excludeHasPay)
				{
					// 要占用已付单这么多数量
					int hasPay = amount - excludeHasPay;
					
					List<BatchBackWrap> tmpList = new ArrayList<BatchBackWrap>();
					
					for (BatchBackWrap each : bbbList)
					{
						// 未付款不处理
						if (each.getType() == 0)
							continue;
						else
						{
							int eachSettle = each.getAmount();
							
							if (hasPay <= eachSettle)
							{
								each.setAmount(hasPay);
								
								tmpList.add(each);
								
								hasPay = 0;
							}
							else
							{
								tmpList.add(each);
								
								hasPay -= eachSettle;
							}
							
							if (hasPay == 0)
								break;
						}
					}
					
					// 去掉bbbList 中的type = 1
					for (Iterator<BatchBackWrap> iterator = bbbList.iterator(); iterator.hasNext(); )
					{
						BatchBackWrap bbb = iterator.next();
						
						if (bbb.getType() == 1)
							iterator.remove();
					}
					
					bbbList.addAll(tmpList);
				}
				else
				{
					List<BatchBackWrap> tmpList = new ArrayList<BatchBackWrap>();
					
					for (BatchBackWrap each : bbbList)
					{
						if (each.getType() == 1)
							continue;
						else
						{
							int current = each.getAmount();
							
							if (amount <= current)
							{
								each.setAmount(amount);
								
								tmpList.add(each);
								
								amount = 0;
							}
							else
							{
								tmpList.add(each);
								
								amount -= current;
							}
							
							if (amount == 0)
								break;
						}
					}
					
					// 清空bbbList，未付款足够，不含付款的
					bbbList.clear();
					
					bbbList.addAll(tmpList);
				}
				
				// 近6个月不够 ，但6个月前的足够，提示 
				if (otype == 1)
				{
					builder.append(message2).append(message).append("退库产品" + pbean.getName() +" 数量不足，但从" + message + "的可退库存为" + allAmount)
					.append("<Br>");
				}
			}
			
		}
		
		return bbbList;
	}

	/**
	 * queryOutSwatchCanBack
	 */
	public List<BatchBackWrap> queryOutSwatchCanBack(String stafferId, String customerId,
			String productId, int amount, StringBuilder builder)
			throws MYException
	{
		List<BatchBackWrap> bbbList = new ArrayList<BatchBackWrap>();
		
		String message = "";
		
		ConditionParse con = new ConditionParse();
		
		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

        con.addCondition(" and OutBean.status in (3,4)");
		
		con.addCondition("OutBean.customerId", "=", customerId);

		if (!StringTools.isNullOrNone(stafferId))
		{
			con.addCondition("OutBean.stafferId", "=", stafferId);
			
//			con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_OUT_SWATCH);
			con.addCondition(" and OutBean.outType in (1, 6)");
		}else
		{
			con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_OUT_SHOW);
		}
		
		con.addCondition("BaseBean.productId", "=", productId);
		
		message = "2011年4月1日到现在";
			
		con.addCondition("OutBean.outTime", ">=", "2011-04-01");
		
		List<BaseBean> list = baseDAO.queryBaseByOneCondition(con);
		
		ConditionParse con1 = new ConditionParse();
		
		for (BaseBean each : list)
		{
			int oriamount = each.getAmount();
			
			int backAmount = 0;
			// 1.去掉已退货部分

    		// 查询已转的销售
            con1.clear();

            con1.addWhereStr();

            con1.addCondition("OutBean.refOutFullId", "=", each.getOutId());

            con1.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);

            List<OutBean> refList = outDAO.queryEntityBeansByCondition(con1);
            
    		// 查询已退的退货
            List<OutBean> refBuyList = queryRefOut1(each.getOutId(), false);
            
    		// 计算出已经退货的数量
            for (OutBean ref : refBuyList)
            {
                List<BaseBean> refBaseList = ref.getBaseList();

                for (BaseBean refBase : refBaseList)
                {
                    if (refBase.equals(each))
                    {
                    	backAmount += refBase.getAmount();
                    }
                }
            }

            // 转销售的
            for (OutBean ref : refList)
            {
                List<BaseBean> refBaseList = baseDAO.queryEntityBeansByFK(ref.getFullId());

                for (BaseBean refBase : refBaseList) {
                    if (refBase.equals(each))
                    {
                    	backAmount += refBase.getAmount();
                    }
                }
            }

			int canBack = oriamount - backAmount;
			
			if (canBack > 0)
			{
				addBatchBackBeanToList(each, bbbList, canBack, 1, "");
			}
		}
		
		ProductBean pbean = productDAO.find(productId);
		
		// bbbList
		if (ListTools.isEmptyOrNull(bbbList))
		{
			builder.append(message).append("退库产品" + pbean.getName() +" 数量不足，无量可退")
			.append("<Br>");
			
//			throw new MYException("退库产品[%s]数量不足，无量可退", pbean.getName());
		}
		else
		{
			int allAmount = 0;
			
			for (BatchBackWrap each : bbbList)
			{
				allAmount += each.getAmount();
			}

            if (amount > allAmount)
			{
				builder.append(message).append("退库产品" +  pbean.getName() +"数量不足，未退货之和为:" + allAmount +",当前要退的量为:" + amount)
				.append("<Br>");
//				throw new MYException("退库产品[%s]数量不足，委托未结算与已结算未退货之和为:[%s],当前要退的量为:[%s]", pbean.getName(), allAmount, amount);
			}
			else
			{
				// 按baseId 从小到大 排序
				Collections.sort(bbbList, new Comparator<BatchBackWrap>()
				{
					public int compare(BatchBackWrap o1, BatchBackWrap o2)
					{
						return Integer.parseInt(o1.getBaseId()) - Integer.parseInt(o2.getBaseId());
					}
				});

				List<BatchBackWrap> tmpList = new ArrayList<BatchBackWrap>();
				
				for (BatchBackWrap each : bbbList)
				{
					int current = each.getAmount();
					
					if (amount <= current)
					{
						each.setAmount(amount);
						
						tmpList.add(each);
						
						amount = 0;
					}
					else
					{
						tmpList.add(each);
						
						amount -= current;
					}
					
					if (amount == 0)
						break;
				}
				
				// 清空bbbList，未付款足够，不含付款的
				bbbList.clear();
				
				bbbList.addAll(tmpList);
			}
		}
		
		return bbbList;
	}
	
	/**
	 * 
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean addBatchOut(User user, List<OutBean> beanList, int type)
			throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, beanList);
		
		List<BatchReturnLog> logList = new ArrayList<BatchReturnLog>();
		
		String batchId = commonDAO.getSquenceString20();
		
		for (OutBean bean : beanList)
		{
			// CORE 退库
			String fullId = this.coloneOutWithoutAffair(bean, user, type);
			
			BatchReturnLog log = new BatchReturnLog();
			
			log.setBatchId(batchId);
			log.setOutId(fullId);
			log.setOperator(bean.getOperator());
			log.setOperatorName(bean.getOperatorName());
			log.setLogTime(TimeTools.now_short());
			
			logList.add(log);
		}
		
		batchReturnLogDAO.saveAllEntityBeans(logList);
		
		return true;
	}
	
	/**
	 * 统计某人下个人领样未退且未转销售的金额及产品数量
	 * {@inheritDoc}
	 */
	public SwatchStatsBean statsPersonalSwatch(String stafferId, String begin, String end)
	{
		SwatchStatsBean ssBean = new SwatchStatsBean();
		
		List<SwatchStatsItemBean> itemList = new ArrayList<SwatchStatsItemBean>();
		
		ssBean.setItemList(itemList);
		
		ConditionParse con = new ConditionParse();
    	
		con.clear();
		
		con.addWhereStr();
		
		con.addCondition("stafferId", "=", stafferId);
		
    	con.addCondition("outTime", ">=", begin);
        con.addCondition("outTime", "<=", end);
        
        con.addIntCondition("type", "=", OutConstant.OUT_TYPE_OUTBILL);
        con.addIntCondition("outtype", "=", OutConstant.OUTTYPE_OUT_SWATCH);
        
        con.addCondition(" and status in (3, 4)");
        
        con.addIntCondition("pay", "=", OutConstant.PAY_NOT);
		
        List<OutBean> list = outDAO.queryEntityBeansByCondition(con);
        
        double totalMoney = 0.0d;
        
        Map<String, SwatchStatsItemBean> map = new HashMap<String, SwatchStatsItemBean>();
        
        // 减去领样退货，领样转销售
        for (OutBean each : list)
        {
        	double refMoney = 0.0d;
        	
        	List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(each.getFullId());
        	
        	// 转销售
        	List<OutBean> refList = queryRef(each);
            
        	// 转退货
        	List<OutBean> refBuyList = queryRefBuy(each);
        	
        	// 计算出已经退货的数量
            for (BaseBean baseBean : baseList)
            {
                int hasBack = 0;

                // 退库
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
                }

                // 转销售的
                for (OutBean ref : refList)
                {
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

                baseBean.setAmount(baseBean.getAmount() - hasBack);
                
                refMoney += hasBack * baseBean.getPrice();
                
                if (!map.containsKey(baseBean.getProductId()))
                {
                	SwatchStatsItemBean ssItemBean = new SwatchStatsItemBean();
                	
                	ssItemBean.setProductId(baseBean.getProductId());
                	ssItemBean.setAmount(baseBean.getAmount());
                	
                	map.put(baseBean.getProductId(), ssItemBean);
                }
                else
                {
                	SwatchStatsItemBean ssItemBean = map.get(baseBean.getProductId());
                	
                	ssItemBean.setAmount(ssItemBean.getAmount() + baseBean.getAmount());
                }
            }
            
            // 本单总金额
            totalMoney += each.getTotal() - refMoney;
        }
        
        ssBean.setId(commonDAO.getSquenceString());
        ssBean.setStafferId(stafferId);
        ssBean.setTotalMoney(totalMoney);
        
        for(SwatchStatsItemBean each : map.values())
        {
        	each.setRefId(ssBean.getId());
        	
        	itemList.add(each);
        }
        
        return ssBean;
	}

	private List<OutBean> queryRefBuy(OutBean each)
	{
		// 查询当前已经有多少个人领样
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", each.getFullId());

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		// 排除其他入库(对冲单据)
		con.addCondition("OutBean.reserve8", "<>", "1");

		List<OutBean> refBuyList = outDAO.queryEntityBeansByCondition(con);

		for (OutBean outBean : refBuyList)
		{
			List<BaseBean> listb = baseDAO.queryEntityBeansByFK(outBean
					.getFullId());

			outBean.setBaseList(listb);
		}
		
		return refBuyList;
	}

	private List<OutBean> queryRef(OutBean each)
	{
		ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("OutBean.refOutFullId", "=", each.getFullId());

		con.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
		
		// 领样转销售
		List<OutBean> refList = outDAO.queryEntityBeansByCondition(con);
		
		return refList;
	}
	
	/**
	 * 批量转结算单
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean batchSettleOut(User user, List<OutBalanceBean> list)
	throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, list);
		
		for (OutBalanceBean each : list)
		{
			addOutBalanceInner(user, each);
		}
		
		return true;
	}

    @Transactional(rollbackFor = MYException.class)
    @Override
    public boolean batchUpdateReason(List<OutBean> list) throws MYException {
        for (OutBean bean : list){
            OutBean out = this.outDAO.find(bean.getFullId());
            if (out!= null){
                out.setReason(bean.getReason());
                this.outDAO.updateEntityBean(out);
            }
        }
        return true;
    }

    /**
	 * 批量领样转销售
	 * {@inheritDoc}
	 */
	@Transactional(rollbackFor = MYException.class)
	public boolean batchSwatchOut(User user, List<OutBean> list)
	throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, list);
		
		for (OutBean each : list)
		{
			addSwatchToSailWithoutTrans(user, each);
		}
		
		return true;
	}
	
    public void addSwatchToSailWithoutTrans(final User user, final OutBean outBean)
    throws MYException
	{
        if (OutHelper.isManagerSail(outBean))
        {
            throw new MYException("销售-个人领样转销售只能在收藏品下,请重新操作");
        }

        // 先保存
        String id = getAll(commonDAO.getSquence());

        LocationBean location = locationDAO.find(outBean.getLocationId());

        if (location == null)
        {
            _logger.error("区域不存在:" + outBean.getLocationId());

            throw new MYException("区域不存在:" + outBean.getLocationId());
        }

        //String flag = location.getCode();
        String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());

        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

        String fullId = flag + time + id;

        outBean.setId(getOutId(id));

        outBean.setFullId(fullId);

        outBean.setStatus(OutConstant.STATUS_SAVE);

        setInvoiceId(outBean);

        double total = 0.0d;

        // 组织BaseBean
        for (BaseBean base : outBean.getBaseList())
        {
            base.setId(commonDAO.getSquenceString());

            base.setOutId(outBean.getFullId());

            base.setMtype(outBean.getMtype());
            
            // 增加单个产品到base表
            baseDAO.saveEntityBean(base);

            total += base.getAmount() * base.getPrice();
        }

        outBean.setTotal(total);

//        outDAO.saveEntityBean(outBean);
        this.saveOutWithPodate(outBean);
	}
	
    /**
     * 单据直接结束 
     * 
     * {@inheritDoc}
     */
	public void directPassWithoutTrans(final User user, final OutBean outBean, final int type) throws MYException
	{
		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());
		
		if (type == OutConstant.OUT_TYPE_OUTBILL)
		{
			// 只有未付款的时候才有
            if (outBean.getPay() == OutConstant.PAY_NOT)
            {
                long add = outBean.getReday() * 24 * 3600 * 1000L;

                // 这里需要把出库单的回款日期修改
                outDAO.modifyReDate(outBean.getFullId(), TimeTools.getStringByFormat(new Date(new Date()
                    .getTime()
                                                                                 + add),
                    "yyyy-MM-dd"));
            }
            
            // 变动库存
            processBuyAndSailPass(user, outBean, baseList, StorageConstant.OPR_STORAGE_INOTHER);
            
		}else{
			processBuyBaseList(user, outBean, baseList, StorageConstant.OPR_STORAGE_SWATH);
		}
	}

    @Transactional(rollbackFor = {MYException.class})
    @Override
    public boolean updateEmergency(User user, String fullId) throws MYException {
        OutBean out = outDAO.find(fullId);
        if (out!= null){
            out.setEmergency(1);
            this.outDAO.updateEntityBean(out);

            try{
                List<PackageBean> packages = this.packageDAO.queryPackagesByOutId(fullId);
                if (!ListTools.isEmptyOrNull(packages)){
                    for (PackageBean pack: packages){
                        pack.setEmergency(1);
                        this.packageDAO.updateEntityBean(pack);
                        _logger.info(fullId+"对应的CK单更新为紧急:"+pack.getId());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                _logger.error("***CK update emergency fail:"+fullId,e);
            }
            return true;
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Transactional(rollbackFor = {MYException.class})
    @Override
    public boolean updateBase(BaseBean baseBean) throws MYException {
        this.baseDAO.updateEntityBean(baseBean);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {MYException.class})
    public boolean importOutAutoApprove(List<AutoApproveBean> autoApproveBeans) throws MYException {
        this.autoApproveOutDAO.saveAllEntityBeans(autoApproveBeans);
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(rollbackFor = {MYException.class})
    public void autoApproveOutJob() {
        //To change body of implemented methods use File | Settings | File Templates.
        triggerLog.info("***autoApproveOutJob is running***");
        _logger.info("***autoApproveOutJob is running***");
        List<AutoApproveBean> beans = this.autoApproveOutDAO.listEntityBeans();
        if (!ListTools.isEmptyOrNull(beans)){
             for(AutoApproveBean bean : beans){
                 String fullId = bean.getFullId();
                 try{
                    this.autoApprove(fullId, null, OutConstant.STATUS_PASS, "库管自动审批JOB通过");
                 }catch(Exception e){
                     e.printStackTrace();
                 }finally{
                     this.autoApproveOutDAO.deleteEntityBean(bean.getId());
                 }
             }
        }
    }

    @Override
    public int autoApprove(final String fullId, final User user, final int nextStatus,
                           final String reason) throws MYException{
            final OutBean outBean = outDAO.find(fullId);
            String template ="***autoApprove fullId:%s nextStatus:%s reason:%s";
            _logger.info(String.format(template, fullId, nextStatus, reason));
            checkPass(outBean);
            final int oldStatus = outBean.getStatus();

            if (oldStatus!= OutConstant.STATUS_FLOW_PASS){
                _logger.warn("fullId is not OutConstant.STATUS_FLOW_PASS:"+fullId);
                return -1;
            }

            // 库管审批通过时，检查行项目商品如在转换表的 商品名 范围内，数量则必须为配置数量的倍数，否则报错提示
            List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByFK(fullId);
            if (!ListTools.isEmptyOrNull(baseBeans)){
                for (BaseBean item : baseBeans){
                    ConditionParse condition = new ConditionParse();
                    condition.addWhereStr();
                    condition.addCondition("ProductExchangeConfigBean.srcProductId", "=", item.getProductId());
                    List<ProductExchangeConfigVO> list = this.productExchangeConfigDAO.queryEntityVOsByCondition(condition);
                    if (!ListTools.isEmptyOrNull(list)){
                        ProductExchangeConfigVO vo = list.get(0);
                        if (item.getAmount()%vo.getSrcAmount() == 0){
                            _logger.info(item+" basebean match for product exchange:"+vo);
                        } else{
                            _logger.warn(item+" does not match product exchange:"+vo);
                            return -1;
                        }
                    }
                }
            }


        // 这里需要计算客户的信用金额-是否报送物流中心经理审批
        boolean outCredit = parameterDAO.getBoolean(SysConfigConstant.OUT_CREDIT);

        // 如果是黑名单的客户(且没有付款)
        if (outCredit && outBean.getReserve3() == OutConstant.OUT_SAIL_TYPE_MONEY
                && outBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && outBean.getPay() == OutConstant.PAY_NOT)
        {
            try
            {
                this.payOut(user, fullId, "结算中心确定已经回款", 0);
            }
            catch (MYException e)
            {
                _logger.error(e);
                return -1;
            }

            OutBean newOut = outDAO.find(fullId);

            if (newOut.getPay() == OutConstant.PAY_NOT)
            {
                _logger.warn("只有结算中心确定已经回款后才可以审批此销售单:"+fullId);
                return -1;
            }
        }

            final DepotBean depot = checkDepotInPass(nextStatus, outBean);
            // LOCK 销售单/入库单通过(这里是销售单库存变动的核心)
            synchronized (PublicLock.PRODUCT_CORE)
            {
                // 入库操作在数据库事务中完成
                TransactionTemplate tran = new TransactionTemplate(transactionManager);
                try
                {
                    tran.execute(new TransactionCallback()
                    {
                        public Object doInTransaction(TransactionStatus arg0)
                        {
                            int newNextStatus = nextStatus;

                            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                            {
                                // 直接把结算中心通过的设置成物流管理员通过(直接跳过物流)
                                if (newNextStatus == OutConstant.STATUS_MANAGER_PASS)
                                {
                                    newNextStatus = OutConstant.STATUS_FLOW_PASS;
                                }

                                // 针对通用产品，结算中心通过后直接为待回款，跳过库管审批
                                if (oldStatus == OutConstant.STATUS_SUBMIT)
                                {
                                    outDAO.modifyManagerTime(outBean.getFullId(), TimeTools.now());

                                    // 领样/巡展转销售
                                    boolean isSTS = isSwatchToSail(outBean.getFullId());

                                    if (isSTS){
                                        newNextStatus = OutConstant.STATUS_PASS;
                                    }else{
                                        List<BaseBean> list = baseDAO.queryEntityBeansByFK(fullId);

                                        if (list.get(0).getProductId().equals(ProductConstant.OUT_COMMON_PRODUCTID)
                                                || list.get(0).getProductId().equals(ProductConstant.OUT_COMMON_MPRODUCTID))
                                        {
                                            newNextStatus = OutConstant.STATUS_PASS;
                                        }

                                        // 中信订单 - 商务审批 - 直接到库管审批
                                        if (outBean.getFlowId().equals("CITIC")
                                                || outBean.getFlowId().equals("ZJRC"))
                                        {
                                            newNextStatus = OutConstant.STATUS_FLOW_PASS;

                                            // 针对中信银行接口产生的OA库单要先拆分行项目 。先检查，若足够库存则拆分行项目.拆分后订单将占有库存
                                            // 增加 只对成本为0的数据进行操作
                                            List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(outBean.getFullId());

                                            // 成本为0的表示还未拆分到具体的库存批次
                                            if (baseList.get(0).getCostPrice() == 0)
                                            {
                                                List<BaseBean> newBaseList = null;
                                                try
                                                {
                                                    newBaseList = splitBase(baseList);
                                                }
                                                catch (MYException e)
                                                {
                                                    throw new RuntimeException(e.getErrorContent(), e);
                                                }

                                                baseDAO.deleteEntityBeansByFK(outBean.getFullId());

                                                baseDAO.saveAllEntityBeans(newBaseList);

                                                // 同步更新未拆分到具体批次前就开票的明细（规定：这样销售单须一次性开完发票）
                                                outBean.setForceBuyType(999);
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                if (outBean.getOutType() == OutConstant.OUTTYPE_OUT_APPLY)
                                    newNextStatus = OutConstant.BUY_STATUS_PASS;
                            }

                            String stafferName = "自动库管通过Job";
                            if (user != null){
                                stafferName = user.getStafferName();
                            }
                            _logger.info(outBean.getFullId() + ":" + stafferName + ":"
                                    + newNextStatus + ":redrectFrom:" + oldStatus);

                            // 修改状态
                            outDAO.modifyOutStatus(outBean.getFullId(), newNextStatus);
                            if (outBean.getType() == OutConstant.OUT_TYPE_OUTBILL)
                            {
                                handerPassOut(fullId, user, outBean, depot, newNextStatus);
                            }
                            else
                            {
                                if (outBean.getOutType() != OutConstant.OUTTYPE_OUT_APPLY)
                                    handerPassBuy(fullId, user, outBean, newNextStatus);
                            }

                            addOutLog(fullId, user, outBean, reason, SailConstant.OPR_OUT_PASS,
                                    newNextStatus);

                            // 把状态放到最新的out里面
                            outBean.setStatus(newNextStatus);

                            // OSGI 监听实现
                            Collection<OutListener> listenerMapValues = listenerMapValues();

                            for (OutListener listener : listenerMapValues)
                            {
                                try
                                {
                                    listener.onPass(user, outBean);
                                }
                                catch (MYException e)
                                {
                                    throw new RuntimeException(e.getErrorContent(), e);
                                }
                            }

                            notifyOut(outBean, user, 0);

                            return Boolean.TRUE;
                        }
                    });
                }
                catch (TransactionException e)
                {
                    _logger.error(e, e);
                    throw new MYException("数据库内部错误");
                }
                catch (DataAccessException e)
                {
                    _logger.error(e, e);
                    throw new MYException("数据库内部错误");
                }
                catch (Exception e)
                {
                    _logger.error(e, e);
                    throw new MYException("处理异常:" + e.getMessage());
                }
            }

            // 更新后的状态
            return outBean.getStatus();

    }

    @Override
    @Transactional(rollbackFor = {MYException.class})
    public void frDbJob() {
        _logger.info("***frDbJob running****");
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addWhereStr();
        conditionParse.addCondition("status","=",1);
        conditionParse.addCondition("newAmount", ">", 0);
        conditionParse.addCondition("yck", "!=", " ");
        conditionParse.addCondition("cb",">", 0);
        String appName = ConfigLoader.getProperty("appName");
        if (AppConstant.APP_NAME.equals(appName)){
            conditionParse.addCondition("sys","=","体内");
        } else if (AppConstant.APP_NAME_TW.equals(appName)){
            conditionParse.addCondition("sys","=","体外");
        }

        List<FrDbBean> frDbBeans = this.frDbDAO.queryEntityBeansByCondition(conditionParse);
        Map<String, List<FrDbBean>> map = new HashMap<>();
        if (!ListTools.isEmptyOrNull(frDbBeans)){
            _logger.info("***frDbBeans size***"+frDbBeans.size());
            //根据流程ID_目的库_源仓库作为联合主键合并开单
            //#735 根据流程ID_目的库_源仓库_productId_priceKey作为联合主键合并开单
            for (FrDbBean frDbBean: frDbBeans){
//                String key = frDbBean.getOutId()+"_"+frDbBean.getMdk()+"_"+frDbBean.getYcq();
                String priceKey = StorageRelationHelper.getPriceKey(NumberUtils.roundDouble(frDbBean.getCb()));
                String key = frDbBean.getOutId()+"_"+frDbBean.getMdk()+"_"+frDbBean.getYcq()+"_"+frDbBean.getProductId()+"_"+priceKey;
                if(map.containsKey(key)){
                    List<FrDbBean> frDbBeanList = map.get(key);
                    frDbBeanList.add(frDbBean);
                } else{
                    List<FrDbBean> frDbBeanList = new ArrayList<>();
                    frDbBeanList.add(frDbBean);
                    map.put(key, frDbBeanList);
                }
            }
        }

        for(String key: map.keySet()){
            String errorMessage = "";
            double total = 0;
            List<FrDbBean> frDbBeanList = map.get(key);

            FrDbBean frDbBean = frDbBeanList.get(0);

            List<BaseBean> baseBeans = new ArrayList<>();
            OutBean outBean =  new OutBean();
            //#691
            StringBuffer sb = new StringBuffer();
            sb.append("异地调拨JOB:").append(frDbBean.getId());
            if (!StringTools.isNullOrNone(frDbBean.getRemark())){
                sb.append(".提交人备注:"+frDbBean.getRemark());
            }
            if (!StringTools.isNullOrNone(frDbBean.getDescription())){
                sb.append(".审批人备注:"+frDbBean.getDescription());
            }
            outBean.setDescription(sb.toString());
            outBean.setType(OutConstant.OUT_TYPE_INBILL);
            outBean.setOutType(OutConstant.OUTTYPE_IN_MOVEOUT);

            String id = OutHelper.getAll(commonDAO.getSquence());
            String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");
            String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());

            String fullId = flag + time + id;
            outBean.setId(OutHelper.getOutId(id));
            outBean.setFullId(fullId);

            // 增加职员的ID
            String stafferId = frDbBean.getChangeUser();
            StafferBean stafferBean = this.stafferDAO.find(stafferId);
            if (stafferBean == null) {
                errorMessage = "职员不存在";
                _logger.error(key+errorMessage);
                this.frDbDAO.updateStatus(frDbBean.getId(),null, errorMessage);
                continue;
            } else {
                outBean.setIndustryId(stafferBean.getIndustryId());
                outBean.setIndustryId2(stafferBean.getIndustryId2());
                outBean.setIndustryId3(stafferBean.getIndustryId3());
                outBean.setStafferId(stafferId);
                outBean.setStafferName(stafferBean.getName());
            }

            String now = TimeTools.now();
            String nowShort = TimeTools.now_short();
            outBean.setOutTime(nowShort);
            outBean.setPodate(nowShort);
            outBean.setLogTime(now);
            outBean.setChangeTime(now);
            outBean.setFlowTime(now);

            outBean.setOperatorName("系统");
            outBean.setReserve1(OutConstant.MOVEOUT_OUT);

            outBean.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);
            outBean.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);

            outBean.setDepartment("公共部门");
            outBean.setArriveDate(TimeTools.now_short(10));
            outBean.setDutyId(frDbBean.getDutyId());

            outBean.setLocationId("999");
            //源仓库
            outBean.setLocation(frDbBean.getYck());
            //目的仓库
            outBean.setDestinationId(frDbBean.getMdk());

            outBean.setCustomerId("99");
            outBean.setCustomerName("公共客户");
            outBean.setDepartment("公共部门");

            outBean.setDutyId("90201008080000000001");
            outBean.setPmtype(PublicConstant.MANAGER_TYPE_COMMON);

            for(FrDbBean bean: frDbBeanList){
                BaseBean baseBean = new BaseBean();
                baseBeans.add(baseBean);
                baseBean.setId(commonDAO.getSquenceString());
                baseBean.setOutId(fullId);

                String ycq = bean.getYcq();
                if(StringTools.isNullOrNone(ycq)){
                    DepotpartBean defaultOKDepotpart = depotpartDAO
                            .findDefaultOKDepotpart(bean.getYck());

                    if (defaultOKDepotpart == null)
                    {
                        errorMessage = "没有默认良品仓";
                        _logger.error(bean.getId()+errorMessage);
                        this.frDbDAO.updateStatus(bean.getId(),null, errorMessage);
                        break;
                    } else{
                        //源仓区
                        baseBean.setDepotpartId(defaultOKDepotpart.getId());
                        baseBean.setDepotpartName(defaultOKDepotpart.getName());
                    }
                } else{
                    DepotpartBean depotpartBean = this.depotpartDAO.find(ycq);
                    if(depotpartBean == null){
                        errorMessage = "仓区不存在";
                        _logger.error(bean.getId()+errorMessage);
                        this.frDbDAO.updateStatus(bean.getId(),null, errorMessage);
                        break;
                    } else{
                        //源仓区
                        baseBean.setDepotpartId(ycq);
                        baseBean.setDepotpartName(depotpartBean.getName());
                    }
                }

                baseBean.setLocationId(bean.getYck());

                String productId = bean.getProductId();
                ProductBean productBean = this.productDAO.find(productId);
                if (productBean == null){
                    errorMessage = "产品不存在";
                    _logger.error(bean.getId()+errorMessage);
                    this.frDbDAO.updateStatus(bean.getId(),null, errorMessage);
                    break;
                } else{
                    baseBean.setProductId(bean.getProductId());
                    baseBean.setProductName(bean.getProductName());
                }
                baseBean.setUnit("套");
                baseBean.setAmount(-bean.getNewAmount());

                baseBean.setPrice(NumberUtils.roundDouble(bean.getCb()));
                baseBean.setValue(NumberUtils.roundDouble(baseBean.getAmount() * baseBean.getPrice()));
                baseBean.setCostPrice(NumberUtils.roundDouble(bean.getCb()));
                baseBean.setCostPriceKey(StorageRelationHelper
                        .getPriceKey(baseBean.getCostPrice()));
                if(bean.getVirtualPrice()>0){
                    baseBean.setVirtualPrice(bean.getVirtualPrice());
                    baseBean.setCostPriceKey(StorageRelationHelper
                            .getPriceKey(baseBean.getVirtualPrice()));
                }
                total += baseBean.getValue();

                baseBean.setOwner("0");
                baseBean.setOwnerName("公共");
                baseBean.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
            }

            if (StringTools.isNullOrNone(errorMessage)){
                try
                {
                    DistributionBean distributionBean = new DistributionBean();
                    distributionBean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_DISTRIBUTION_PRIFIX));
                    distributionBean.setOutId(outBean.getFullId());
                    distributionBean.setAddress(frDbBean.getAddress());
                    distributionBean.setProvinceId(frDbBean.getProvinceId());
                    distributionBean.setCityId(frDbBean.getCityId());
                    distributionBean.setReceiver(frDbBean.getReceiver());
                    distributionBean.setMobile(frDbBean.getTelephone());
                    distributionBean.setShipping(frDbBean.getFhfs());
                    distributionBean.setTransport1(frDbBean.getYsfs());
                    distributionBean.setExpressPay(frDbBean.getYfPay());
                    this.distributionDAO.saveEntityBean(distributionBean);
                    outBean.setDistributeBean(distributionBean);

                    outBean.setTotal(total);
                    outBean.setStatus(OutConstant.BUY_STATUS_PASS);
                    outBean.setInway(OutConstant.IN_WAY);
                    outDAO.saveEntityBean(outBean);
                    baseDAO.saveAllEntityBeans(baseBeans);
                    this.addLog3(outBean.getFullId(), 0, OutConstant.BUY_STATUS_PASS, 0, "提交");

                    //入库提交后直接变动库存
                    int result = this.processBuyOutInWay(null, fullId, outBean);
                    outBean.setFrFlag(true);
                    this.createPackage(outBean);
                }
                catch (Exception e)
                {
                    _logger.error(e, e);
                    errorMessage = StringUtils.subString(e.getMessage(), 1024);
                    //有问题回滚
                    outDAO.deleteEntityBean(outBean.getFullId());
                    baseDAO.deleteEntityBeansByFK(outBean.getFullId());
                } finally {
                    for(FrDbBean frDbBean1: frDbBeanList){
                        this.frDbDAO.updateStatus(frDbBean1.getId(),fullId, errorMessage);
                    }
                }
            }
        }
        _logger.info("***finish frDbJob()****");
    }

    @Override
    @Transactional(rollbackFor = {MYException.class})
    public void updateOutbackStatusJob() {
        _logger.info("***updateOutbackStatusJob is running***");
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addIntCondition("OutBean.type","=", OutConstant.OUT_TYPE_INBILL);
        conditionParse.addIntCondition("OutBean.outType","=", OutConstant.OUTTYPE_IN_SWATCH);
        conditionParse.addCondition("OutBean.outTime",">=","2016-01-01");
        conditionParse.addCondition(" and OutBean.outbackStatus != '已入库'");
        List<OutBean> lyBackBeans = this.outDAO.queryEntityBeansByCondition(conditionParse);
        if (!ListTools.isEmptyOrNull(lyBackBeans)){
            this.setOutbackStatus(lyBackBeans);
        }
        _logger.info("***updateOutbackStatusJob finished***");
    }

    private void setOutbackStatus(List<OutBean> list){
        List<OutBackItemBean> outBackItemBeanList = this.outBackItemDAO.listEntityBeans();
        List<OutBackBean> outBackBeanList = this.outBackDAO.listEntityBeans();
        for (OutBean out : list) {
            String fullId = out.getFullId();
            String outBackId = this.findOutBackId(outBackItemBeanList, fullId);
            if (outBackId != null) {
                String status = this.findOutBackStatus(outBackBeanList, outBackId);
                if (!StringTools.isNullOrNone(status)){
                    this.outDAO.updateOutbackStatus(fullId, status);
                }
            }
        }
    }

    private String findOutBackId(List<OutBackItemBean> outBackItemBeanList, String outId){
        for (OutBackItemBean item : outBackItemBeanList){
            if (outId.equals(item.getReoano())){
                return item.getOutBackId();
            }
        }
        return null;
    }

    private String findOutBackStatus(List<OutBackBean> outBackBeanList, String outBackId){
        String result = "";
        for (OutBackBean outBackBean: outBackBeanList){
            if (outBackBean.getId().equals(outBackId)){
                int status = outBackBean.getStatus();
                if (OutConstant.OUTBACK_STATUS_SAVE == status){
                    return "待到货";
                } else if (OutConstant.OUTBACK_STATUS_CLAIM == status){
                    return "待认领";
                } else if (OutConstant.OUTBACK_STATUS_CHECK == status){
                    return "待验货";
                } else if (OutConstant.OUTBACK_STATUS_IN == status){
                    return "待入库";
                }  else if (OutConstant.OUTBACK_STATUS_FINISH == status){
                    return "已入库";
                }

            }
        }
        return result;
    }

    @Override
    public void statsExceptionalInBackJob() {
        triggerLog.info("***statsExceptionalInBackJob running***");
        try {
            String sql1 = "select sum(base.amount) as amount,base.outId from t_center_base base left outer join t_center_out OutBean on base.outid=OutBean.fullid where OutBean.type=0 and OutBean.outTime>='2014-01-01' group by base.outId";
            Map<String, Integer> map1 = new HashMap<String, Integer>();
            List<Map> list1 = this.baseDAO.queryBaseBeans(sql1);
            for (Map map : list1) {
                _logger.info(map);
                map1.put((String) map.get("outId"), ((BigDecimal) map.get("amount")).intValue());
            }
            _logger.info("****map1****"+map1);

            String sql2 = "select sum(base.amount) as amount,base.outId, OutBean.refOutFullId from t_center_base base left outer join t_center_out OutBean on base.outid=OutBean.fullid where OutBean.type=1 and OutBean.outTime>='2014-01-01' and OutBean.refOutFullId!='' group by base.outId";
            Map<String, Integer> map2 = new HashMap<String, Integer>();
            List<Map> list2 = this.baseDAO.queryBaseBeans(sql2);
            for (Map map : list2) {
                _logger.info(map);
                String refOutFullId = (String) map.get("refOutFullId");
                if (map2.containsKey(refOutFullId)) {
                    map2.put(refOutFullId, map2.get(refOutFullId) + ((BigDecimal) map.get("amount")).intValue());
                } else {
                    map2.put(refOutFullId, ((BigDecimal) map.get("amount")).intValue());
                }
            }
            _logger.info("***map2****"+map2);
            _logger.info("***************************");
            for (String outId : map2.keySet()) {
                if (map1.containsKey(outId) && map2.get(outId) > map1.get(outId)) {
                    triggerLog.info(outId+"***in***"+map2.get(outId)+"***out***"+map1.get(outId));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            _logger.error(e,e);
        }
    }

    public String createNewBuyBean(OutBean outBean) throws MYException {
        // 触发产生退货凭证 TAX_ADD
        Collection<OutListener> listenerMapValues = listenerMapValues();

        //hardcode to avoid null user
        User user = this.userDAO.findUserByName("周苏东");
        for (OutListener listener : listenerMapValues)
        {
            listener.onConfirmOutOrBuy(user, outBean);
        }

        outDAO.modifyOutStatus(outBean.getFullId(), OutConstant.BUY_STATUS_PASS);

        // 记录退货审批日志 操作人系统，自动审批
        FlowLogBean log = new FlowLogBean();

        log.setActor("系统");

        log.setDescription("线下空开空退系统自动审批");
        log.setFullId(outBean.getFullId());
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(OutConstant.BUY_STATUS_SAVE);

        log.setAfterStatus(OutConstant.BUY_STATUS_PASS);

        flowLogDAO.saveEntityBean(log);
        return outBean.getFullId();
    }

    @Override
    public OutBean createNewOutBean(OutBean out, OutBackItemBean item) throws MYException {
        String newOutId;

        OutBean newOutBean = new OutBean();

        BeanUtil.copyProperties(newOutBean, out);
        newOutBean.setType(OutConstant.OUT_TYPE_OUTBILL);
        newOutBean.setOutType(item.getOutType());

        String id = getAll(commonDAO.getSquence());
        String flag = OutHelper.getSailHead(newOutBean.getType(), newOutBean.getOutType());

        String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");

        newOutId = flag + time + id;

        newOutBean.setId(getOutId(id));

        newOutBean.setFullId(newOutId);

        newOutBean.setOutTime(TimeTools.now_short());

        newOutBean.setChangeTime(TimeTools.now());

        long add = 3 * 24 * 3600 * 1000L;

        String arrveDate = TimeTools.getStringByFormat(new Date(new Date().getTime() + add), "yyyy-MM-dd");

        newOutBean.setArriveDate(arrveDate);

        newOutBean.setManagerTime(TimeTools.now());

        newOutBean.setChangeTime(TimeTools.now());

        newOutBean.setFeedBackVisit(0);
        newOutBean.setFeedBackCheck(0);

        newOutBean.setDescription( "线下空开空退,原单：" + out.getFullId());

        String customerId = item.getOutCustomerId();
        newOutBean.setCustomerId(customerId);
        CustomerVO customerVO = this.customerMainDAO.findVO(customerId);
        if (customerVO == null){
            _logger.error("customer not exists:"+customerId);
        } else{
            newOutBean.setCustomerName(customerVO.getName());
        }

        DistributionBean distributionBean = new DistributionBean();
        distributionBean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_DISTRIBUTION_PRIFIX));
        distributionBean.setOutId(newOutId);
        distributionBean.setShipping(OutConstant.OUT_SHIPPING_NOTSHIPPING);
        distributionBean.setReceiver(item.getOutReceiver());
        distributionDAO.saveEntityBean(distributionBean);
        _logger.info("save saveEntityBean***"+distributionBean);
        newOutBean.setDistributeBean(distributionBean);
        // 付款方式:客户信用和业务员信用额度担保
        newOutBean.setReserve3(2);
        newOutBean.setStatus(OutConstant.STATUS_SEC_PASS);
        outDAO.saveEntityBean(newOutBean);

        //原单更新付款状态
        if (newOutBean.getType() == OutConstant.OUT_TYPE_OUTBILL
                && isSwatchToSail(newOutBean.getFullId())) {
            // 检查是否溢出
            try {
                boolean ret = checkIfAllSwithToSail(newOutBean.getRefOutFullId());

                if (ret) {
                    // add 原领样单自动变为已付款
                    String srcFullId = newOutBean.getRefOutFullId();

                    outDAO.updatePay(srcFullId, OutConstant.PAY_YES);
                    _logger.info("***out update to pay***" + srcFullId);
                }
            }catch (Exception e){
                _logger.error(e,e);
            }
        }

        List<BaseBean> baseList = new ArrayList<BaseBean>();

        newOutBean.setBaseList(baseList);

        BaseBean baseBean = out.getBaseList().get(0);
        BaseBean newBaseBean = new BaseBean();
        BeanUtil.copyProperties(newBaseBean, baseBean);
        newBaseBean.setId(commonDAO.getSquenceString());
        newBaseBean.setOutId(newOutId);
        baseList.add(baseBean);
        baseDAO.saveEntityBean(newBaseBean);

        _logger.info("create new out in offlineStorageInJob "+newOutBean+"***with base bean***"+newBaseBean);
        // 触发产生退货凭证 TAX_ADD
        Collection<OutListener> listenerMapValues = listenerMapValues();

        //hardcode user
        User user = this.userDAO.findUserByName("周苏东");
        for (OutListener listener : listenerMapValues)
        {
            listener.onConfirmOutOrBuy(user, newOutBean);
        }

        //直接到“已出库”状态
        this.outDAO.modifyOutStatus(newOutId, OutConstant.STATUS_PASS);

        // 记录退货审批日志 操作人系统，自动审批
        FlowLogBean log = new FlowLogBean();

        log.setActor("系统");

        log.setDescription("线下空开空退系统自动审批");
        log.setFullId(newOutBean.getFullId());
        log.setOprMode(PublicConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(OutConstant.STATUS_SAVE);

        log.setAfterStatus(OutConstant.STATUS_PASS);

        flowLogDAO.saveEntityBean(log);

        return newOutBean;
    }


    @Override
    public ProductImportBean getProductImportBean(OutBean out, CustomerBean customerBean,String productId) throws MYException{
        ProductBean productBean = this.productDAO.find(productId);
        String customerName = out.getCustomerName();
        String channel = out.getChannel();
        String branchName = null;
        if (customerBean!= null){
            branchName = customerBean.getReserve1();
        }
        return this.getProductImportBean(customerName, branchName, productBean.getCode(), channel,out.getPodate(), out.getOutType(), false);
    }

    @Override
    public ProductImportBean getProductImportBean(String customerName,String branchName, String productCode, String channel,
                                                   String citicOrderDate, int outType, boolean isImport) throws MYException {
        String template = "getProductImportBean with customerName:%s branchName:%s productCode:%s channel:%s citicOrderDate:%s outType:%d";
        _logger.info(String.format(template, customerName, branchName, productCode, channel, citicOrderDate, outType));
        String appName = ConfigLoader.getProperty("appName");
        String bank = "";
        
        if (OutConstant.LS_PMKH.equals(customerName) || OutConstant.QB_INDUSTRY.equals(customerName)
                || OutConstant.LS_INDUSTRY.equals(customerName)){
            bank = customerName;
        } else if (!StringTools.isNullOrNone(customerName)) {
            bank = StringUtils.subString(customerName, 4);
        }

        if (StringTools.isNullOrNone(bank)){
            throw new MYException("银行不能为空!");
        }
        //Step1: 先根据支行+代码+渠道+银行+帐套+是否体内匹配
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addCondition("customerName", "=", customerName);
        this.addProductCodeCondition(conditionParse, productCode, isImport);
        this.addChannelCondition(conditionParse, channel);
        conditionParse.addCondition("bank", "=", bank);
        this.addItemCondition(conditionParse, outType, appName);

        List<ProductImportBean> productImportBeans = this.productImportDAO.queryEntityBeansByCondition(conditionParse);
        _logger.info("***productImportBeans1***" + productImportBeans);
        boolean outdated = this.ignoreOutdated(productImportBeans, branchName, citicOrderDate);
        _logger.info("***productImportBeans1 ignored***" + productImportBeans);
        if (ListTools.isEmptyOrNull(productImportBeans) && !StringTools.isNullOrNone(branchName)) {
            //step2: 如果支行无法匹配，就对比分行+代码+渠道+银行+帐套+是否体内
            conditionParse = new ConditionParse();
            this.addProductCodeCondition(conditionParse, productCode, isImport);
            this.addBranchCondition(conditionParse, branchName);
            this.addChannelCondition(conditionParse, channel);
            conditionParse.addCondition("bank", "=", bank);
            this.addItemCondition(conditionParse, outType, appName);

            productImportBeans = this.productImportDAO.queryEntityBeansByCondition(conditionParse);
            _logger.info("***productImportBeans2***" + productImportBeans);
            outdated = this.ignoreOutdated(productImportBeans, branchName, citicOrderDate);
            _logger.info("***productImportBeans2 ignored***" + productImportBeans);
        }

        if (ListTools.isEmptyOrNull(productImportBeans)) {
            //step3: 如果支行和分行都无法匹配，就根据银行+代码+渠道+帐套+是否体内
            conditionParse = new ConditionParse();
            conditionParse.addCondition("bank", "=", bank);
            this.addProductCodeCondition(conditionParse, productCode, isImport);
            this.addChannelCondition(conditionParse, channel);
            //支行取空的
            this.addBranchCondition(conditionParse, null);
            this.addItemCondition(conditionParse, outType, appName);
            productImportBeans = this.productImportDAO.queryEntityBeansByCondition(conditionParse);
            _logger.info("***productImportBeans3***" + productImportBeans);
            outdated = this.ignoreOutdated(productImportBeans, branchName, citicOrderDate);
            _logger.info("***productImportBeans3 ignored***" + productImportBeans);
        }

        if (ListTools.isEmptyOrNull(productImportBeans)) {
            //step4: 根据银行+分行+代码+帐套+是否体内+渠道为空
            conditionParse = new ConditionParse();
            conditionParse.addCondition("bank", "=", bank);
            this.addProductCodeCondition(conditionParse, productCode, isImport);
            this.addBranchCondition(conditionParse, branchName);
            this.addChannelCondition(conditionParse, null);
            this.addItemCondition(conditionParse, outType, appName);
            productImportBeans = this.productImportDAO.queryEntityBeansByCondition(conditionParse);
            _logger.info(conditionParse+"***productImportBeans4***" + productImportBeans);
            outdated = this.ignoreOutdated(productImportBeans, branchName, citicOrderDate);
            _logger.info("***productImportBeans4 ignored***" + productImportBeans);
        }

        if (ListTools.isEmptyOrNull(productImportBeans)) {
            //step5: 最后只根据银行+代码+帐套+是否体内+渠道为空
            conditionParse = new ConditionParse();
            conditionParse.addCondition("bank", "=", bank);
            this.addProductCodeCondition(conditionParse, productCode, isImport);
            //支行和渠道都取空的
            this.addBranchCondition(conditionParse, null);
            this.addChannelCondition(conditionParse, null);
            this.addItemCondition(conditionParse, outType, appName);
            productImportBeans = this.productImportDAO.queryEntityBeansByCondition(conditionParse);
            _logger.info(conditionParse+"***productImportBeans5***" + productImportBeans);
            outdated = this.ignoreOutdated(productImportBeans, branchName, citicOrderDate);
            _logger.info("***productImportBeans5 ignored***" + productImportBeans);
        }

        if (ListTools.isEmptyOrNull(productImportBeans)) {
            if (outdated){
                String msg = appName+"订单日期不是有效日期:"+citicOrderDate;
                _logger.error(msg);
                throw new MYException(msg);
            } else{
                String msg = appName+"未配置产品主数据(客户+银行产品编码):"+customerName+"+"+productCode;
                _logger.error(msg);
                throw new MYException(msg);
            }
        } else if (productImportBeans.size() > 1) {
            _logger.error("产品主数据配置不能超过1条:" + productImportBeans);
            StringBuilder builder = new StringBuilder();
            builder.append(appName+"产品主数据配置不能超过1条:" + customerName + "+" + productCode)
                    .append("<br>");
            throw new MYException(builder.toString());
        } else {
            ProductImportBean productImportBean = productImportBeans.get(0);
            _logger.info("***find product import bean***"+productImportBean);
            return productImportBean;
        }
    }

    /**
     * 去掉已经过期的产品配置
     * @param productImportBeans
     * @param branchName
     * @param citicOrderDate
     */
    private boolean ignoreOutdated(List<ProductImportBean> productImportBeans,
                            String branchName, String citicOrderDate){
        boolean flag = false;
        if (!ListTools.isEmptyOrNull(productImportBeans)){
            //检查时间是否有效
            for (Iterator<ProductImportBean> iterator=productImportBeans.iterator();iterator.hasNext();){
                ProductImportBean pib = iterator.next();
//                //分行必须对应，要么分行为空
//                if (!StringTools.isNullOrNone(branchName) && !StringTools.isNullOrNone(pib.getBranchName())
//                        && !branchName.equals(pib.getBranchName())) {
//                    continue;
//                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date end = sdf.parse(pib.getOfflineDate());
                    Date begin = sdf.parse(pib.getOnMarketDate());
                    Date citicDate = sdf.parse(citicOrderDate);
                    if (citicDate.before(begin) || citicDate.after(end)) {
                        _logger.warn(" citicDate out of date:" + pib);
                        iterator.remove();
                        flag = true;
                    }
                } catch (ParseException e) {
                    _logger.error(" Exception parse Date:", e);
                    iterator.remove();
                }
            }
        }
        return flag;
    }

    private void addProductCodeCondition(ConditionParse conditionParse, String productCode, boolean isImport){
        if (isImport){
            conditionParse.addCondition("bankProductCode", "=", productCode);
        } else{
            conditionParse.addCondition("code", "=", productCode);
        }
    }

    private void addBranchCondition(ConditionParse conditionParse, String branchName){
        if (StringTools.isNullOrNone(branchName)) {
            // 空格查询会被框架过滤掉!!!
            conditionParse.addCondition(" and (branchName='' or branchName is null)");
        } else {
            conditionParse.addCondition("branchName", "=", branchName);
        }
    }

    private void addChannelCondition(ConditionParse conditionParse, String channel){
        if (StringTools.isNullOrNone(channel)) {
//            conditionParse.addCondition("channel", "=", "");
            // 空格查询会被框架过滤掉!!!
            conditionParse.addCondition(" and (channel='' or channel is null)");
        } else {
            conditionParse.addCondition("channel", "=", channel);
        }
    }

    private void addItemCondition(ConditionParse conditionParse, int outType, String appName){
        if (outType == OutConstant.OUTTYPE_OUT_COMMON){
            //销售出库
            conditionParse.addCondition("isSell", "=", "在售");
        } else {
            //其他所有出库类型
            conditionParse.addCondition(" and isSell in ('在售','非在售')");
        }

        if (AppConstant.APP_NAME_TW.equals(appName)){
            //体外
            conditionParse.addCondition(" and item in('体外','全部')");
        } else {
            conditionParse.addCondition(" and item in('体内','全部')");
        }
    }

    @Override
    public double getGrossProfit(OutBean out, CustomerBean customerBean, String productId) {
	    try {
            ProductImportBean productImportBean = this.getProductImportBean(out, customerBean, productId);
            if (productImportBean != null){
                return productImportBean.getGrossProfit();
            }
        }catch (Exception e){
	        _logger.error(e);
        }
        return 0;
    }

    @Override
    public double getCash(OutBean out,CustomerBean customerBean,String productId) {
        try {
            ProductImportBean productImportBean = this.getProductImportBean(out, customerBean, productId);
            if (productImportBean != null){
                return productImportBean.getCash();
            }
        }catch (Exception e){
            _logger.error(e);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = {MYException.class})
    public void createCustomerJob() {
        _logger.info("***createCustomerJob is running***");
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addCondition("type","=", OutConstant.OUT_TYPE_OUTBILL);
        conditionParse.addCondition("customerCreated","=", 0);
//        conditionParse.addItemCondition("fullId","=","SO1612191143546540578");
//        conditionParse.addItemCondition("outTime",">=", "2016-12-19");
        conditionParse.addCondition(" and (customerName like '%钱币交易部%' or customerName like '%微店%' or customerName like '%天猫%' or customerName like '%永银藏品店%')");

        List<OutBean> outBeanList = this.outDAO.queryEntityBeansByCondition(conditionParse);

        List<OutBean> outBeanList1 = this.outDAO.queryDirectOut();

        List<OutBean> list = new ArrayList<OutBean>();
        if (!ListTools.isEmptyOrNull(outBeanList)){
            list.addAll(outBeanList);
        }
        if (!ListTools.isEmptyOrNull(outBeanList1)){
            list.addAll(outBeanList1);
        }

        if (!ListTools.isEmptyOrNull(list)) {
            for (OutBean out : list) {
                String fullId = out.getFullId();
                String customerName = out.getCustomerName();
                try {
                    //配送方式不为自提与空发的，将收货人转为个人类型客户
                    List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(fullId);
                    if (!ListTools.isEmptyOrNull(distList)) {
                        DistributionBean distributionBean = distList.get(0);
                        int shipping = distributionBean.getShipping();
                        if (shipping != OutConstant.OUT_SHIPPING_SELFSERVICE &&
                                shipping != OutConstant.OUT_SHIPPING_NOTSHIPPING) {
                            String mobile = distributionBean.getMobile();
                            String receiver = distributionBean.getReceiver();
                            //按手机为索引，不能重复，如有不同姓名对应同一手机的，增加到客户的地址息中
                            ConditionParse conditionParse1 = new ConditionParse();
                            conditionParse1.addCondition("handphone", "=", mobile);
                            List<CustomerContactBean> contactBeanList = this.customerContactDAO.queryEntityBeansByCondition(conditionParse1);
                            if (ListTools.isEmptyOrNull(contactBeanList)) {
                                CustomerBean customerBean = new CustomerBean();
                                String id = commonDAO.getSquenceString();
                                String code = commonDAO.getSquenceString20();
                                customerBean.setId(id);
                                customerBean.setCode(code);
                                customerBean.setType(CustomerConstant.NATURE_INDIVIDUAL);
                                customerBean.setCreateTime(TimeTools.now());
                                customerBean.setLogTime(TimeTools.now());
                                customerBean.setStatus(CustomerConstant.REAL_STATUS_USED);

                                customerBean.setName(receiver);
                                customerBean.setSimpleName(receiver);

                                //                        客户来源 字段取值规则
//                        包括“钱币交易部”的客户，按-号后的字段对应来源
//                        包括“XX银行XX微店”、“XX银行XX天猫”的客户，来源定义为“银行”
//                        包括 “永银藏品店”的客户，来源定义为“藏品”
                                if (customerName.contains("钱币交易部")) {
                                    String[] temp = customerName.split("-");
                                    if (temp.length == 2) {
                                        customerBean.setFromType(this.findEnum(temp[1]));
                                    } else {
                                        customerBean.setFromType(99);
                                    }
                                } else if (customerName.contains("银行") &&
                                        (customerName.contains("微店") || customerName.contains("天猫"))) {
                                    customerBean.setFromType(this.findEnum("银行"));
                                } else if (customerName.contains("永银藏品店")) {
                                    customerBean.setFromType(this.findEnum("藏品"));
                                } else {
                                    //其他
                                    customerBean.setFromType(99);
                                }

//                      客户分类1 设为 个人，客户类型设为终端，客户分类2设为  新客户，客户等级设为一般客户，开发进程设为 成交客户，行业设为 其他
                                customerBean.setProtype(0);
                                customerBean.setProtype2(0);
                                customerBean.setSelltype(1);
                                customerBean.setQqtype(2);
                                customerBean.setRtype(5);
                                customerBean.setIndustry(99);

                                //3.	收货人姓名、地址、电话分别对应客户基本信息中的姓名、省、市、地址、手机，
                                // 按手机为索引，不能重复，如有不同姓名对应同一手机的，增加到客户的地址息中
                                customerBean.setProvinceId(distributionBean.getProvinceId());
                                customerBean.setCityId(distributionBean.getCityId());
                                customerBean.setAddress(distributionBean.getAddress());

                                this.customerMainDAO.saveEntityBean(customerBean);
                                _logger.info("***create customer***" + customerBean.getName());

                                //4.	同时将收货信息记为客户的地址信息，同时将收货人记入联系人信息
                                CustomerContactBean contact = new CustomerContactBean();
                                contact.setId(commonDAO.getSquenceString20());
                                contact.setCustomerId(customerBean.getId());
                                contact.setName(receiver);
                                contact.setHandphone(distributionBean.getMobile());
                                this.customerContactDAO.saveEntityBean(contact);

                                CustomerDistAddrBean distAddrBean = new CustomerDistAddrBean();
                                distAddrBean.setId(commonDAO.getSquenceString20());
                                distAddrBean.setCustomerId(customerBean.getId());
                                distAddrBean.setProvinceId(distributionBean.getProvinceId());
                                distAddrBean.setCityId(distributionBean.getCityId());
                                distAddrBean.setAddress(distributionBean.getAddress());
                                distAddrBean.setFullAddress(distributionBean.getAddress());
                                distAddrBean.setTelephone(mobile);
                                distAddrBean.setContact(receiver);
                                distAddrBean.setShipping(shipping);
                                distAddrBean.setTransport1(distributionBean.getTransport1());
                                distAddrBean.setTransport2(distributionBean.getTransport2());
                                distAddrBean.setExpressPay(distributionBean.getExpressPay());
                                distAddrBean.setTransportPay(distributionBean.getTransportPay());
                                this.customerDistAddrDAO.saveEntityBean(distAddrBean);

                                CustomerIndividualBean customerIndividualBean = new CustomerIndividualBean();
                                //ID必须与customer一致，否则有问题
                                BeanUtil.copyProperties(customerIndividualBean, customerBean);
                                customerIndividualBean.setSimpleName(customerIndividualBean.getName());
                                this.customerIndividualDAO.saveEntityBean(customerIndividualBean);

                                StafferVSCustomerBean vs = new StafferVSCustomerBean();
                                //系统生成的客户，不挂在原来的业务员名下，挂在 陈永-支持 名下
                                ConditionParse conditionParse2 =  new ConditionParse();
                                conditionParse2.addCondition("name","=","陈永-支持");
                                List<StafferBean> stafferBeanList = this.stafferDAO.queryEntityBeansByCondition(conditionParse2);
                                if (ListTools.isEmptyOrNull(stafferBeanList)){
                                    vs.setStafferId(out.getStafferId());
                                } else{
                                    vs.setStafferId(stafferBeanList.get(0).getId());
                                }

                                vs.setCustomerId(customerBean.getId());
                                this.stafferVSCustomerDAO.saveEntityBean(vs);

                                this.outDAO.updateCustomerCreated(out.getFullId(), true);
                            } else {
                                _logger.info("mobile already exist**" + mobile);
                                // update address
                                //4.	同时将收货信息记为客户的地址信息，同时将收货人记入联系人信息
                                ConditionParse conditionParse2 = new ConditionParse();
                                conditionParse2.addCondition("provinceId","=", distributionBean.getProvinceId());
                                conditionParse2.addCondition("cityId","=", distributionBean.getCityId());
                                conditionParse2.addCondition("address", "like", "%"+StringUtils.subString(distributionBean.getAddress(), 6));
                                List<CustomerDistAddrBean> contactBeans = this.customerDistAddrDAO.queryEntityBeansByCondition(conditionParse2);
                                if (ListTools.isEmptyOrNull(contactBeans)){
                                    CustomerContactBean contactBean = contactBeanList.get(0);
                                    CustomerDistAddrBean distAddrBean = new CustomerDistAddrBean();
                                    distAddrBean.setId(commonDAO.getSquenceString20());
                                    distAddrBean.setCustomerId(contactBean.getCustomerId());
                                    distAddrBean.setProvinceId(distributionBean.getProvinceId());
                                    distAddrBean.setCityId(distributionBean.getCityId());
                                    distAddrBean.setAddress(distributionBean.getAddress());
                                    distAddrBean.setFullAddress(distributionBean.getAddress());
                                    distAddrBean.setTelephone(mobile);
                                    distAddrBean.setContact(receiver);
                                    distAddrBean.setShipping(shipping);
                                    distAddrBean.setTransport1(distributionBean.getTransport1());
                                    distAddrBean.setTransport2(distributionBean.getTransport2());
                                    distAddrBean.setExpressPay(distributionBean.getExpressPay());
                                    distAddrBean.setTransportPay(distributionBean.getTransportPay());
                                    this.customerDistAddrDAO.saveEntityBean(distAddrBean);
                                    _logger.info("add distAddrBean***"+distAddrBean);
                                }
                                this.outDAO.updateCustomerCreated(out.getFullId(), true);
                            }
                        }
                    }
                } catch (Exception e) {
                    _logger.error(fullId+" Fail to save customer "+customerName,e);
                }
            }
        }
        _logger.info("***finish createCustomerJob***");
    }

    private int findEnum(String val){
        ConditionParse conditionParse2 = new ConditionParse();
        conditionParse2.addWhereStr();
        conditionParse2.addCondition("type","=","106");
        conditionParse2.addCondition("val","=",val);
        List<EnumBean> enumBeans = this.enumDAO.queryEntityBeansByCondition(conditionParse2);
        if(ListTools.isEmptyOrNull(enumBeans)){
            return 99;
        } else{
            return Integer.valueOf(enumBeans.get(0).getKey());
        }
    }

    @Override
    public double getSailConfigPrice(ProductBean productBean) {
        // 根据配置获取结算价
        double sailPrice = productBean.getSailPrice();
        List<PriceConfigBean> pcblist = priceConfigDAO.querySailPricebyProductId(productBean.getId());

        if (!ListTools.isEmptyOrNull(pcblist))
        {
            PriceConfigBean cb = priceConfigManager.calcSailPrice(pcblist.get(0));

            sailPrice = cb.getSailPrice();
        }

        return sailPrice;
    }

    @Override
    public String getCostPriceKey(String outId, String productId) throws MYException {
	    ConditionParse conditionParse = new ConditionParse();
	    conditionParse.addWhereStr();
	    conditionParse.addCondition("outId","=", outId);
	    conditionParse.addCondition("productId","=", productId);
	    List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByCondition(conditionParse);
	    if (ListTools.isEmptyOrNull(baseBeans)){
	        throw new MYException("base表为空:"+outId);
        } else if (baseBeans.size() >1){
            throw new MYException(outId+"商品成本有多行:"+productId);
        } else{
	        return baseBeans.get(0).getCostPriceKey();
        }
    }

    @Override
    public BaseBean getBaseBean(String outId, String productId, String costPriceKey) throws MYException{
        List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByFK(outId);
        if (ListTools.isEmptyOrNull(baseBeans)){
            throw new MYException("BaseBean不存在:"+outId);
        } else{
            for (BaseBean baseBean : baseBeans){
                if (productId.equals(baseBean.getProductId()) && baseBean.getCostPriceKey().equals(costPriceKey)){
                    return baseBean;
                }
            }
            return baseBeans.get(0);
        }
    }

    @Override
    public double getIprice(OutImportBean bean, ProductBean product) {
        if (product == null){
            String msg = " 客户（网点）不存在:"+bean.getComunicatonBranchName();
            _logger.error(msg);
            return 0;
        }

        double sailPrice = product.getSailPrice();

        // 根据配置获取结算价
        List<PriceConfigBean> pcblist = priceConfigDAO.querySailPricebyProductId(product.getId());

        if (!ListTools.isEmptyOrNull(pcblist))
        {
            PriceConfigBean cb = priceConfigManager.calcSailPrice(pcblist.get(0));

            sailPrice = cb.getSailPrice();
        }

        String stafferId = bean.getStafferId();
        if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
        {
            stafferId = bean.getStafferId();
        }else
        {
            List<CustomerBean> cbeans = customerMainDAO.queryByName(bean.getComunicatonBranchName());

            if (ListTools.isEmptyOrNull(cbeans))
            {
                String msg = " 客户（网点）不存在:"+bean.getComunicatonBranchName();
                _logger.error(msg);
                return 0;
            }
            else{
                String customerId = cbeans.get(0).getId();
                StafferVSCustomerVO vsCustVO = stafferVSCustomerDAO.findVOByUnique(customerId);
                if (vsCustVO!= null){
                    stafferId = vsCustVO.getStafferId();
                }
            }
        }

        final StafferBean stafferBean = stafferDAO.find(stafferId);
        if (stafferBean == null){
            _logger.error("staffer not exist***"+stafferId);
            return 0;
        } else if (product == null){
            _logger.error("product is null!");
            return 0;
        }
        // 获取销售配置
        SailConfBean sailConf = sailConfigManager.findProductConf(stafferBean,
                product);

        double iprice = 0;
        //#647
        if(sailConf.getIprice() > 0){
            iprice = sailConf.getIprice();
        } else{
            // 事业部结算价(产品结算价 * (1 + 总部结算率 + 事业部结算率))
            iprice = sailPrice
                    * (1 + sailConf.getIratio() / 1000.0d + sailConf
                    .getPratio() / 1000.0d);
        }
        _logger.info("****sailConf****"+sailConf);
        return iprice;
    }


    /**
     * 找到对应的赠送单
     * @return
     */
    public List<OutBean> getZsOrders(int action, String fullId, int status, boolean checkStatus){
        if (fullId.startsWith("ZS")){
            return new ArrayList<>();
        }
        ConditionParse con1 = new ConditionParse();
        con1.addWhereStr();
        con1.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
        con1.addIntCondition("OutBean.outtype", "=", OutConstant.OUTTYPE_OUT_PRESENT);
        con1.addCondition("OutBean.refOutFullId","=", fullId);
        if (action == 0){
            //审核通过
            if (checkStatus){
                if (status == OutConstant.STATUS_MANAGER_PASS
                        || status == OutConstant.STATUS_FLOW_PASS){
                    //当前为待商务审批状态的赠送单
                    con1.addIntCondition("OutBean.status", "=", OutConstant.STATUS_SUBMIT);
                } else if (status == OutConstant.STATUS_PASS){
                    //当前为待库管审批状态的赠送单
                    con1.addIntCondition("OutBean.status", "=", OutConstant.STATUS_FLOW_PASS);
                } else {
                    con1.addIntCondition("OutBean.status", "=", status);
                }
            }
        } else if (action == 1){
            //退回
            if (checkStatus){
                //和当前待退回的OS单状态一致的赠送单
                con1.addIntCondition("OutBean.status", "=", status);
            }
        }

        List<DistributionBean> distList = distributionDAO.queryEntityBeansByFK(fullId);
        List<OutBean> outBeans = this.outDAO.queryEntityBeansByCondition(con1);
        if (ListTools.isEmptyOrNull(distList)){
            return outBeans;
        } else{
            DistributionBean distributionBean = distList.get(0);
            List<OutBean> result = new ArrayList<>();
            for (OutBean outBean: outBeans){
                List<DistributionBean> distList2 = distributionDAO.queryEntityBeansByFK(outBean.getFullId());
                if (!ListTools.isEmptyOrNull(distList2)){
                    DistributionBean distributionBean2 = distList2.get(0);
                    //ZS单和SO单收货信息一致才过单
                    if (distributionBean.getShipping() == distributionBean2.getShipping()){
                        if (distributionBean.getShipping() == 0){
                            //自提：收货人，电话一致
                            if (distributionBean.getReceiver().equals(distributionBean2.getReceiver())
                                    && distributionBean.getMobile().equals(distributionBean2.getMobile())){
                                result.add(outBean);
                            } else{
                                _logger.warn("***shipping0 not equal***"+outBean.getFullId());
                            }
                        } else if (distributionBean.getShipping() == 2){
                            //第三方快递：地址、收货人、电话完全一致
                            if (distributionBean.getReceiver().equals(distributionBean2.getReceiver())
                                    && distributionBean.getMobile().equals(distributionBean2.getMobile())
                                    && StringUtils.subString(distributionBean.getAddress(),6).equals(StringUtils.subString(distributionBean2.getAddress(),6))){
                                result.add(outBean);
                            } else{
                                _logger.warn("***shipping2 not equal***"+outBean.getFullId());
                            }
                        }
                    } else{
                        _logger.warn("***shipping not equal***"+outBean.getFullId());
                    }
                }
            }
            return result;
        }
    }
    /**
     * @return the mailAttchmentPath
     */
    public String getStafferCreditStorePath()
    {
        return ConfigLoader.getProperty("stafferCreditStore");
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
     * @return the sailConfigDAO
     */
    public SailConfigDAO getSailConfigDAO()
    {
        return sailConfigDAO;
    }

    /**
     * @param sailConfigDAO
     *            the sailConfigDAO to set
     */
    public void setSailConfigDAO(SailConfigDAO sailConfigDAO)
    {
        this.sailConfigDAO = sailConfigDAO;
    }

    /**
     * @return the sailConfigManager
     */
    public SailConfigManager getSailConfigManager()
    {
        return sailConfigManager;
    }

    /**
     * @param sailConfigManager
     *            the sailConfigManager to set
     */
    public void setSailConfigManager(SailConfigManager sailConfigManager)
    {
        this.sailConfigManager = sailConfigManager;
    }

    /**
     * @return the sailTranApplyDAO
     */
    public SailTranApplyDAO getSailTranApplyDAO()
    {
        return sailTranApplyDAO;
    }

    /**
     * @param sailTranApplyDAO
     *            the sailTranApplyDAO to set
     */
    public void setSailTranApplyDAO(SailTranApplyDAO sailTranApplyDAO)
    {
        this.sailTranApplyDAO = sailTranApplyDAO;
    }

	public ClientFacade getClientFacade() {
		return clientFacade;
	}

	public void setClientFacade(ClientFacade clientFacade) {
		this.clientFacade = clientFacade;
	}

	public CustomerMainDAO getCustomerMainDAO() {
		return customerMainDAO;
	}

	public void setCustomerMainDAO(CustomerMainDAO customerMainDAO) {
		this.customerMainDAO = customerMainDAO;
	}

	public ClientManager getClientManager() {
		return clientManager;
	}

	public void setClientManager(ClientManager clientManager) {
		this.clientManager = clientManager;
	}

	public PromotionDAO getPromotionDAO() {
		return promotionDAO;
	}

	public void setPromotionDAO(PromotionDAO promotionDAO) {
		this.promotionDAO = promotionDAO;
	}

	public OutRepaireDAO getOutRepaireDAO()
	{
		return outRepaireDAO;
	}

	public void setOutRepaireDAO(OutRepaireDAO outRepaireDAO)
	{
		this.outRepaireDAO = outRepaireDAO;
	}

	public BaseRepaireDAO getBaseRepaireDAO()
	{
		return baseRepaireDAO;
	}

	public void setBaseRepaireDAO(BaseRepaireDAO baseRepaireDAO)
	{
		this.baseRepaireDAO = baseRepaireDAO;
	}

	public ProductCombinationDAO getProductCombinationDAO()
	{
		return productCombinationDAO;
	}

	public void setProductCombinationDAO(ProductCombinationDAO productCombinationDAO)
	{
		this.productCombinationDAO = productCombinationDAO;
	}

	public DistributionDAO getDistributionDAO()
	{
		return distributionDAO;
	}

	public void setDistributionDAO(DistributionDAO distributionDAO)
	{
		this.distributionDAO = distributionDAO;
	}

	public PriceConfigManager getPriceConfigManager()
	{
		return priceConfigManager;
	}

	public void setPriceConfigManager(PriceConfigManager priceConfigManager)
	{
		this.priceConfigManager = priceConfigManager;
	}

	public AuditRuleManager getAuditRuleManager()
	{
		return auditRuleManager;
	}

	public void setAuditRuleManager(AuditRuleManager auditRuleManager)
	{
		this.auditRuleManager = auditRuleManager;
	}

	public CommonMailManager getCommonMailManager()
	{
		return commonMailManager;
	}

	public void setCommonMailManager(CommonMailManager commonMailManager)
	{
		this.commonMailManager = commonMailManager;
	}

	public AttachmentDAO getAttachmentDAO()
	{
		return attachmentDAO;
	}

	public void setAttachmentDAO(AttachmentDAO attachmentDAO)
	{
		this.attachmentDAO = attachmentDAO;
	}

	public AddressDAO getAddressDAO()
	{
		return addressDAO;
	}

	public void setAddressDAO(AddressDAO addressDAO)
	{
		this.addressDAO = addressDAO;
	}

	public SwatchStatsDAO getSwatchStatsDAO()
	{
		return swatchStatsDAO;
	}

	public void setSwatchStatsDAO(SwatchStatsDAO swatchStatsDAO)
	{
		this.swatchStatsDAO = swatchStatsDAO;
	}

	public SwatchStatsItemDAO getSwatchStatsItemDAO()
	{
		return swatchStatsItemDAO;
	}

	public void setSwatchStatsItemDAO(SwatchStatsItemDAO swatchStatsItemDAO)
	{
		this.swatchStatsItemDAO = swatchStatsItemDAO;
	}

	/**
	 * @return the appOutVSOutDAO
	 */
	public AppOutVSOutDAO getAppOutVSOutDAO()
	{
		return appOutVSOutDAO;
	}

	/**
	 * @param appOutVSOutDAO the appOutVSOutDAO to set
	 */
	public void setAppOutVSOutDAO(AppOutVSOutDAO appOutVSOutDAO)
	{
		this.appOutVSOutDAO = appOutVSOutDAO;
	}

	/**
	 * @return the distributionBaseDAO
	 */
	public DistributionBaseDAO getDistributionBaseDAO()
	{
		return distributionBaseDAO;
	}

	/**
	 * @param distributionBaseDAO the distributionBaseDAO to set
	 */
	public void setDistributionBaseDAO(DistributionBaseDAO distributionBaseDAO)
	{
		this.distributionBaseDAO = distributionBaseDAO;
	}

	/**
	 * @return the statsDeliveryRankDAO
	 */
	public StatsDeliveryRankDAO getStatsDeliveryRankDAO()
	{
		return statsDeliveryRankDAO;
	}

	/**
	 * @param statsDeliveryRankDAO the statsDeliveryRankDAO to set
	 */
	public void setStatsDeliveryRankDAO(StatsDeliveryRankDAO statsDeliveryRankDAO)
	{
		this.statsDeliveryRankDAO = statsDeliveryRankDAO;
	}

	/**
	 * @return the deliveryRankVSOutDAO
	 */
	public DeliveryRankVSOutDAO getDeliveryRankVSOutDAO()
	{
		return deliveryRankVSOutDAO;
	}

	/**
	 * @param deliveryRankVSOutDAO the deliveryRankVSOutDAO to set
	 */
	public void setDeliveryRankVSOutDAO(DeliveryRankVSOutDAO deliveryRankVSOutDAO)
	{
		this.deliveryRankVSOutDAO = deliveryRankVSOutDAO;
	}

	public PreConsignDAO getPreConsignDAO()
	{
		return preConsignDAO;
	}

	public void setPreConsignDAO(PreConsignDAO preConsignDAO)
	{
		this.preConsignDAO = preConsignDAO;
	}

	/**
	 * @return the shipManager
	 */
	public ShipManager getShipManager()
	{
		return shipManager;
	}

	/**
	 * @param shipManager the shipManager to set
	 */
	public void setShipManager(ShipManager shipManager)
	{
		this.shipManager = shipManager;
	}

	/**
	 * @return the provinceDAO
	 */
	public ProvinceDAO getProvinceDAO()
	{
		return provinceDAO;
	}

	/**
	 * @param provinceDAO the provinceDAO to set
	 */
	public void setProvinceDAO(ProvinceDAO provinceDAO)
	{
		this.provinceDAO = provinceDAO;
	}

	/**
	 * @return the cityDAO
	 */
	public CityDAO getCityDAO()
	{
		return cityDAO;
	}

	/**
	 * @param cityDAO the cityDAO to set
	 */
	public void setCityDAO(CityDAO cityDAO)
	{
		this.cityDAO = cityDAO;
	}

	/**
	 * @return the areaDAO
	 */
	public AreaDAO getAreaDAO()
	{
		return areaDAO;
	}

	/**
	 * @param areaDAO the areaDAO to set
	 */
	public void setAreaDAO(AreaDAO areaDAO)
	{
		this.areaDAO = areaDAO;
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

	/**
	 * @return the outPayTagDAO
	 */
	public OutPayTagDAO getOutPayTagDAO()
	{
		return outPayTagDAO;
	}

	/**
	 * @param outPayTagDAO the outPayTagDAO to set
	 */
	public void setOutPayTagDAO(OutPayTagDAO outPayTagDAO)
	{
		this.outPayTagDAO = outPayTagDAO;
	}

	/**
	 * @return the zjrcManager
	 */
	public ZJRCManager getZjrcManager()
	{
		return zjrcManager;
	}

	/**
	 * @param zjrcManager the zjrcManager to set
	 */
	public void setZjrcManager(ZJRCManager zjrcManager)
	{
		this.zjrcManager = zjrcManager;
	}


    /**
     * @return the zjrcOutDAO
     */
    public ZJRCOutDAO getZjrcOutDAO() {
        return zjrcOutDAO;
    }

    /**
     * @param zjrcOutDAO the zjrcOutDAO to set
     */
    public void setZjrcOutDAO(ZJRCOutDAO zjrcOutDAO) {
        this.zjrcOutDAO = zjrcOutDAO;
    }

    public CustomerIndividualDAO getCustomerIndividualDAO() {
        return customerIndividualDAO;
    }

    public void setCustomerIndividualDAO(CustomerIndividualDAO customerIndividualDAO) {
        this.customerIndividualDAO = customerIndividualDAO;
    }


    /**
     * @return the packageDAO
     */
    public PackageDAO getPackageDAO() {
        return packageDAO;
    }


    /**
     * @param packageDAO the packageDAO to set
     */
    public void setPackageDAO(PackageDAO packageDAO) {
        this.packageDAO = packageDAO;
    }

    public AutoApproveOutDAO getAutoApproveOutDAO() {
        return autoApproveOutDAO;
    }

    public void setAutoApproveOutDAO(AutoApproveOutDAO autoApproveOutDAO) {
        this.autoApproveOutDAO = autoApproveOutDAO;
    }

    public ProductExchangeConfigDAO getProductExchangeConfigDAO() {
        return productExchangeConfigDAO;
    }

    public void setProductExchangeConfigDAO(ProductExchangeConfigDAO productExchangeConfigDAO) {
        this.productExchangeConfigDAO = productExchangeConfigDAO;
    }

    public TempConsignDAO getTempConsignDAO() {
        return tempConsignDAO;
    }

    public void setTempConsignDAO(TempConsignDAO tempConsignDAO) {
        this.tempConsignDAO = tempConsignDAO;
    }

    public OutBackDAO getOutBackDAO() {
        return outBackDAO;
    }

    public void setOutBackDAO(OutBackDAO outBackDAO) {
        this.outBackDAO = outBackDAO;
    }

    public OutBackItemDAO getOutBackItemDAO() {
        return outBackItemDAO;
    }

    public void setOutBackItemDAO(OutBackItemDAO outBackItemDAO) {
        this.outBackItemDAO = outBackItemDAO;
    }

    public ProductImportDAO getProductImportDAO() {
        return productImportDAO;
    }

    public void setProductImportDAO(ProductImportDAO productImportDAO) {
        this.productImportDAO = productImportDAO;
    }

    public EnumDAO getEnumDAO() {
        return enumDAO;
    }

    public void setEnumDAO(EnumDAO enumDAO) {
        this.enumDAO = enumDAO;
    }

    public CustomerDistAddrDAO getCustomerDistAddrDAO() {
        return customerDistAddrDAO;
    }

    public void setCustomerDistAddrDAO(CustomerDistAddrDAO customerDistAddrDAO) {
        this.customerDistAddrDAO = customerDistAddrDAO;
    }

    public CustomerContactDAO getCustomerContactDAO() {
        return customerContactDAO;
    }

    public void setCustomerContactDAO(CustomerContactDAO customerContactDAO) {
        this.customerContactDAO = customerContactDAO;
    }

    public PriceConfigDAO getPriceConfigDAO() {
        return priceConfigDAO;
    }

    public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO) {
        this.priceConfigDAO = priceConfigDAO;
    }

    public DhZjbDAO getDhZjbDAO() {
        return dhZjbDAO;
    }

    public void setDhZjbDAO(DhZjbDAO dhZjbDAO) {
        this.dhZjbDAO = dhZjbDAO;
    }

    public FrDbDAO getFrDbDAO() {
        return frDbDAO;
    }

    public void setFrDbDAO(FrDbDAO frDbDAO) {
        this.frDbDAO = frDbDAO;
    }


	public StorageLogDAO getStorageLogDAO() {
		return storageLogDAO;
	}

	public void setStorageLogDAO(StorageLogDAO storageLogDAO) {
		this.storageLogDAO = storageLogDAO;
	}
	
	

	private String[] getParam(ParamterMap request, String param){
    	String[] list = {};
    	if(request.getParameter(param)!=null){
    		list = request.getParameter(param).split("~");
    		_logger.debug("request.getParameter(param):"+request.getParameter(param));
    	}
    	
    	return list;
    }
}
