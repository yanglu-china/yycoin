/**
 * File Name: StockListenerSailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-5<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stockvssail.listener.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.constant.ModuleConstant;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.stock.bean.StockItemArrivalBean;
import com.china.center.oa.stock.dao.StockItemArrivalDAO;
import com.china.center.oa.stock.vo.StockItemArrivalVO;
import com.china.center.tools.ListTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.helper.StorageRelationHelper;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.dao.InvoiceDAO;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.stock.bean.StockBean;
import com.china.center.oa.stock.bean.StockItemBean;
import com.china.center.oa.stock.constant.StockConstant;
import com.china.center.oa.stock.dao.StockItemDAO;
import com.china.center.oa.stock.listener.StockListener;
import com.china.center.oa.stock.vo.StockItemVO;
import com.china.center.oa.stock.vo.StockVO;
import com.china.center.oa.stockvssail.listener.FechProductListener;
import com.china.center.oa.stockvssail.manager.FechProductManager;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * StockListenerSailImpl
 * 
 * @author ZHUZHU
 * @version 2010-12-5
 * @see StockListenerSailImpl
 * @since 3.0
 */
public class StockListenerSailImpl extends AbstractListenerManager<FechProductListener> implements StockListener, FechProductManager
{
    private OutManager outManager = null;

    private StockItemDAO stockItemDAO = null;

    private StockItemArrivalDAO stockItemArrivalDAO = null;

    private final Log _logger = LogFactory.getLog(getClass());
    
    private DepotpartDAO depotpartDAO = null;
    
    private ProductDAO productDAO = null;
    
    private InvoiceDAO invoiceDAO = null;

    private LogDAO logDAO = null;

    private BaseDAO baseDAO = null;

    private PriceConfigDAO priceConfigDAO = null;

    private PriceConfigManager priceConfigManager = null;

    /**
     * default constructor
     */
    public StockListenerSailImpl()
    {
    }

    /**
     * 生成入库单
     */
    public void onEndStockItem(User user, StockBean bean, final StockItemBean each)
        throws MYException
    {
        _logger.info("****onEndStockItem***"+each.getClass());
        if (each instanceof StockItemArrivalBean){
            StockItemArrivalBean arrivalBean = (StockItemArrivalBean)each;
            this.autoToOut(user, bean, arrivalBean);
        } else{
            autoToOut(user, bean, each);
        }
    }

    /**
     * 根据采购单生成自动入库单，每个采购单体生成一个入库单
     * 
     * @param user
     * @param bean
     * @param each
     * @throws MYException
     */
    private void autoToOut(final User user, StockBean bean, final StockItemBean each)
        throws MYException
    {
    	if (bean.getMtype() == StockConstant.MANAGER_TYPE_COMMON2
    			|| bean.getMtype() == StockConstant.MANAGER_TYPE_COMMON3)
    		bean.setMtype(StockConstant.MANAGER_TYPE_COMMON);
    	
        List<StockItemVO> items = stockItemDAO.queryEntityVOsByFK(bean.getId());
        for (StockItemVO item : items)
        {
            if ( !item.getId().equals(each.getId()))
            {
                continue;
            }

            List<BaseBean> baseList = new ArrayList<BaseBean>();

            OutBean out = new OutBean();

            out.setStatus(OutConstant.STATUS_SAVE);

            out.setStafferName(user.getStafferName());

            out.setStafferId(user.getStafferId());

            out.setType(OutConstant.OUT_TYPE_INBILL);

            out.setOutType(OutConstant.OUTTYPE_IN_COMMON);

            out.setOutTime(TimeTools.now_short());

            out.setDepartment("采购部");

            out.setCustomerId(item.getProviderId());

            out.setCustomerName(item.getProviderName());

            // 所在区域
            out.setLocationId(user.getLocationId());

            String depotpartId = each.getDepotpartId();

            DepotpartBean depotpartBean = depotpartDAO.find(depotpartId);

            if (depotpartBean == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 目的仓库通过仓区自动获取
            out.setLocation(depotpartBean.getLocationId());

            out.setTotal(item.getTotal());

            out.setInway(OutConstant.IN_WAY_NO);

            out.setDutyId(each.getDutyId());

            // 管理类型
            out.setMtype(bean.getMtype());

            out.setInvoiceId(each.getInvoiceType());
            
            // 没有发票直接置为 发票已确认 态
            if (StringTools.isNullOrNone(out.getInvoiceId())) {
            	out.setHasConfirm(1);
            }
            
            //采购单备注加到入库单
            ProductBean product = productDAO.find(each.getProductId());
            out.setDescription("采购单自动转换成入库单,采购单单号:" + bean.getId() + "，" + bean.getDescription()+"；");

            BaseBean baseBean = new BaseBean();

//            baseBean.setValue(item.getTotal());
            baseBean.setLocationId(out.getLocation());

            //2014/12/14 入库单根据实际入库数量分批次生成
            _logger.info("*************autoToOut getWarehouseNum************"+each.getWarehouseNum());
            baseBean.setAmount(each.getWarehouseNum());
//            baseBean.setAmount(item.getAmount());

            baseBean.setProductName(item.getProductName());
            baseBean.setUnit("套");
            baseBean.setPrice(item.getPrice());
//            baseBean.setValue(item.getTotal());
            //2014/12/16 根据实际入库数量计算金额
            baseBean.setValue(item.getPrice()*each.getWarehouseNum());

            baseBean.setShowId(item.getShowId());

            baseBean.setCostPrice(item.getPrice());

            baseBean.setMtype(bean.getMtype());

            baseBean.setProductId(item.getProductId());
            baseBean.setCostPriceKey(StorageRelationHelper.getPriceKey(item.getPrice()));

            // 这里记住哦
            String on = ((StockVO)bean).getOwerName();
            if (bean.getStockType() == StockConstant.STOCK_SAILTYPE_PUBLIC)
            {
                baseBean.setOwnerName("公共");
                baseBean.setOwner("0");
                
//                if(product.getSailType()==ProductConstant.SAILTYPE_REPLACE)
//                {
//                	product.setSailPrice(each.getPrice());//采购商品的结算价更新为此张采购单的成本价
////                	each.setPrice(productVo.getSailPrice());
//                	productDAO.updateEntityBean(product);
//                }
//                stockItemDAO.saveEntityBean(each);
            }
            else
            {
                baseBean.setOwnerName(on);

                baseBean.setOwner(bean.getOwerId());
            }

            // 来源于入库的仓区
            baseBean.setDepotpartId(each.getDepotpartId());

            DepotpartBean deport = depotpartDAO.find(each.getDepotpartId());

            if (deport != null)
            {
                baseBean.setDepotpartName(deport.getName());
            }
            else
            {
                throw new MYException("仓区不存在");
            }

            // 成本
            baseBean.setDescription(String.valueOf(item.getPrice()));
            
            // 进项税率
            double inputRate = 0.0d;
            
            if (!StringTools.isNullOrNone(item.getInvoiceType()))
            {
                InvoiceBean invoice = invoiceDAO.find(item.getInvoiceType());
                
                if (null != invoice)
                {
                	inputRate = invoice.getVal()/100;
                }
            }
            
            baseBean.setInputRate(inputRate);
            
            baseList.add(baseBean);

            out.setBaseList(baseList);

            // CORE 采购单生成入库单
            String fullId = outManager.coloneOutAndSubmitWithOutAffair(out, user,
                StorageConstant.OPR_STORAGE_OUTBILLIN);

            //#545
            this.updateSailPrice(user,out);

            item.setHasRef(StockConstant.STOCK_ITEM_HASREF_YES);

            item.setRefOutId(fullId);

            out.setFullId(fullId);

            // 修改采购项的属性
            stockItemDAO.updateEntityBean(item);

            // TAX_ADD 采购到货后生成管理凭证
            Collection<FechProductListener> listenerMapValues = this.listenerMapValues();

            for (FechProductListener fechProductListener : listenerMapValues)
            {
                fechProductListener.onFechProduct(user, bean, each, out);
            }
        }
    }

    private void updateSailPrice(User user,OutBean outBean){
        List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByFK(outBean.getFullId());
        if (!ListTools.isEmptyOrNull(baseBeans)){
            for(BaseBean baseBean: baseBeans){
                String productId = baseBean.getProductId();
                ProductBean product = this.productDAO.find(productId);
                if (product!= null && product.getSailPriceFlag() ==  1){
                    List<PriceConfigBean> list1 = priceConfigDAO.querySailPricebyProductId(productId);
                    if (ListTools.isEmptyOrNull(list1)){
                        // 日志
                        StringBuilder sb = new StringBuilder();
                        sb.append("取自采购入库单:"+outBean.getFullId()+".修改人:"+user.getName())
                                .append(".原产品结算价:").append(product.getSailPrice())
                                .append(".更新为:").append(baseBean.getPrice());
                        this.log(user, productId, OperationConstant.OPERATION_UPDATE, sb.toString(), ModuleConstant.MODULE_PRICE_CONFIG);

                        //如果没有config配置项，直接更新product的sailPrice
                        product.setSailPrice(baseBean.getPrice());//采购商品的结算价更新为此张采购单的成本价
                        productDAO.updateEntityBean(product);
                    } else{
                        PriceConfigBean first = list1.get(0);
                        //有配置项，先更新配置项的辅料字段，然后通过公式算出结算价，然后更新回product的sailPrice
                        this.priceConfigDAO.updateGsPriceUp(productId, baseBean.getPrice());
                        //log
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("取自采购入库单:"+outBean.getFullId()+".修改人:"+user.getName())
                                .append(".原产品辅料价:").append(first.getGsPriceUp())
                                .append(".更新为:").append(baseBean.getPrice());
                        this.log(user, productId, OperationConstant.OPERATION_UPDATE, sb2.toString(), ModuleConstant.MODULE_PRICE_CONFIG2);

                        first.setGsPriceUp(baseBean.getPrice());
                        PriceConfigBean cb = this.priceConfigManager.calcSailPrice(first);
                        if (cb!= null){
                            // 日志
                            StringBuilder sb = new StringBuilder();
                            sb.append("取自采购入库单:"+outBean.getFullId()+".修改人:"+user.getName())
                                    .append(".原产品结算价:").append(product.getSailPrice())
                                    .append(".更新为:").append(cb.getSailPrice());
                            this.log(user, productId, OperationConstant.OPERATION_UPDATE, sb.toString(), ModuleConstant.MODULE_PRICE_CONFIG);

                            product.setSailPrice(cb.getSailPrice());
                            productDAO.updateEntityBean(product);
                        }
                    }
                }
            }
        }
    }


//    @Override
//    public void onEndStockItem(User user, StockBean stockBean, StockItemArrivalBean stockItemArrivalBean) throws MYException {
//        //To change body of implemented methods use File | Settings | File Templates.
//        this.autoToOut(user, stockBean, stockItemArrivalBean);
//    }

    private void autoToOut(final User user, StockBean bean, final StockItemArrivalBean each)
            throws MYException
    {
        _logger.info("***autoToOut with StockItemArrivalBean***"+each);
        if (bean.getMtype() == StockConstant.MANAGER_TYPE_COMMON2
                || bean.getMtype() == StockConstant.MANAGER_TYPE_COMMON3)
            bean.setMtype(StockConstant.MANAGER_TYPE_COMMON);

        StockItemArrivalVO item = this.stockItemArrivalDAO.findVO(each.getId());

            List<BaseBean> baseList = new ArrayList<BaseBean>();

            OutBean out = new OutBean();

            out.setStatus(OutConstant.STATUS_SAVE);

            out.setStafferName(user.getStafferName());

            out.setStafferId(user.getStafferId());

            out.setType(OutConstant.OUT_TYPE_INBILL);

            out.setOutType(OutConstant.OUTTYPE_IN_COMMON);

            out.setOutTime(TimeTools.now_short());

            out.setDepartment("采购部");

            out.setCustomerId(item.getProviderId());

            out.setCustomerName(item.getProviderName());

            // 所在区域
            out.setLocationId(user.getLocationId());

            String depotpartId = each.getDepotpartId();

            DepotpartBean depotpartBean = depotpartDAO.find(depotpartId);

            if (depotpartBean == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            // 目的仓库通过仓区自动获取
            out.setLocation(depotpartBean.getLocationId());

//            out.setTotal(item.getTotal());
            out.setTotal(item.getPrice() * each.getWarehouseNum());

            out.setInway(OutConstant.IN_WAY_NO);

            out.setDutyId(each.getDutyId());

            // 管理类型
            out.setMtype(bean.getMtype());

            out.setInvoiceId(each.getInvoiceType());

            // 没有发票直接置为 发票已确认 态
            if (StringTools.isNullOrNone(out.getInvoiceId())) {
                out.setHasConfirm(1);
            }

            //采购单备注加到入库单
            ProductBean product = productDAO.find(each.getProductId());
            out.setDescription("采购单自动转换成入库单,采购单单号:" + bean.getId() + "，" + bean.getDescription()+"；");

            BaseBean baseBean = new BaseBean();

            baseBean.setLocationId(out.getLocation());

            //2014/12/14 入库单根据实际入库数量分批次生成
            baseBean.setAmount(each.getWarehouseNum());
            baseBean.setProductName(item.getProductName());
            baseBean.setUnit("套");
            baseBean.setPrice(item.getPrice());

            //2014/12/16 根据实际入库数量计算金额
            baseBean.setValue(item.getPrice()*each.getWarehouseNum());

            baseBean.setShowId(item.getShowId());

            baseBean.setCostPrice(item.getPrice());

            baseBean.setMtype(bean.getMtype());

            baseBean.setProductId(item.getProductId());
            baseBean.setCostPriceKey(StorageRelationHelper.getPriceKey(item.getPrice()));

            // 这里记住哦
            String on = ((StockVO)bean).getOwerName();
            if (bean.getStockType() == StockConstant.STOCK_SAILTYPE_PUBLIC)
            {
                baseBean.setOwnerName("公共");
                baseBean.setOwner("0");

//                if(product.getSailType()==ProductConstant.SAILTYPE_REPLACE)
//                {
//                    // 日志
//                    StringBuilder sb = new StringBuilder();
//                    sb.append("修改人:").append(user.getStafferName())
//                            .append(".原产品结算价:").append(product.getSailPrice());
//
//                    product.setSailPrice(each.getPrice());//采购商品的结算价更新为此张采购单的成本价
//                    productDAO.updateEntityBean(product);
//
//                    this.log(user, each.getProductId(), OperationConstant.OPERATION_UPDATE, sb.toString());
//                }
            }
            else
            {
                baseBean.setOwnerName(on);

                baseBean.setOwner(bean.getOwerId());
            }

            // 来源于入库的仓区
            baseBean.setDepotpartId(each.getDepotpartId());

            DepotpartBean deport = depotpartDAO.find(each.getDepotpartId());

            if (deport != null)
            {
                baseBean.setDepotpartName(deport.getName());
            }
            else
            {
                throw new MYException("仓区不存在");
            }

            // 成本
            baseBean.setDescription(String.valueOf(item.getPrice()));

            // 进项税率
            double inputRate = 0.0d;

            if (!StringTools.isNullOrNone(item.getInvoiceType()))
            {
                InvoiceBean invoice = invoiceDAO.find(item.getInvoiceType());

                if (null != invoice)
                {
                    inputRate = invoice.getVal()/100;
                }
            }

            baseBean.setInputRate(inputRate);

            baseList.add(baseBean);

            out.setBaseList(baseList);

            // CORE 采购单生成入库单
            String fullId = outManager.coloneOutAndSubmitWithOutAffair(out, user,
                    StorageConstant.OPR_STORAGE_OUTBILLIN);

            //#545
            this.updateSailPrice(user,out);

            if (item.getFechProduct() == StockConstant.STOCK_ITEM_FECH_YES){
                item.setHasRef(StockConstant.STOCK_ITEM_HASREF_YES);
            } else if (item.getFechProduct() ==StockConstant.STOCK_ITEM_FECH_PART){
                item.setHasRef(StockConstant.STOCK_ITEM_HASREF_PART);
            }

            item.setRefOutId(fullId);

            out.setFullId(fullId);

            // 修改采购到货项的属性
            this.stockItemArrivalDAO.updateEntityBean(item);

            //更新对应采购行是否拿货标志(stockId,productId,providerId)三者必须都一致
            //采购行和到货行是1对多关系
            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addWhereStr();
            conditionParse.addCondition("stockId", "=", bean.getId());
            conditionParse.addCondition("productId", "=", item.getProductId());
            conditionParse.addCondition("providerId", "=", item.getProviderId());

            List<StockItemArrivalBean> arrivalBeans = this.stockItemArrivalDAO.queryEntityBeansByCondition(conditionParse);
            boolean allArrived = true;
            if (!ListTools.isEmptyOrNull(arrivalBeans)){
                for (StockItemArrivalBean arrivalBean: arrivalBeans){
                    _logger.info(arrivalBean.getFechProduct());
                    if (arrivalBean.getFechProduct() != StockConstant.STOCK_ITEM_FECH_YES){
                        allArrived = false;
                        break;
                    }
                }
            }

        _logger.info("****allArrived****"+allArrived+"***con***"+conditionParse.toString());

            //找到对应的采购行(只有一条)
            List<StockItemBean> itemBeans = this.stockItemDAO.queryEntityBeansByCondition(conditionParse);
            if (!ListTools.isEmptyOrNull(itemBeans)){
                StockItemBean itemBean = itemBeans.get(0);

                if (allArrived){
                    itemBean.setFechProduct(StockConstant.STOCK_ITEM_FECH_YES);
                    itemBean.setHasRef(StockConstant.STOCK_ITEM_HASREF_YES);
                } else{
                    itemBean.setFechProduct(StockConstant.STOCK_ITEM_FECH_PART);
                    itemBean.setHasRef(StockConstant.STOCK_ITEM_HASREF_PART);
                }

                if (StringTools.isNullOrNone(itemBean.getRefOutId())){
                    itemBean.setRefOutId(fullId);
                } else{
                    itemBean.setRefOutId(itemBean.getRefOutId()+";"+fullId);
                }

                _logger.info(itemBean+" ***update hasRef status***");
                this.stockItemDAO.updateEntityBean(itemBean);
            }


            // TAX_ADD 采购到货后生成管理凭证
            Collection<FechProductListener> listenerMapValues = this.listenerMapValues();

            for (FechProductListener fechProductListener : listenerMapValues)
            {
                fechProductListener.onFechProduct(user, bean, each, out);
            }
    }


    private void log(User user, String id,String operation, String reason,String module) {
        // 记录审批日志
        LogBean log = new LogBean();

        log.setFkId(id);

        log.setLocationId(user.getLocationId());
        log.setStafferId(user.getStafferId());
        log.setLogTime(TimeTools.now());
//        log.setModule(ModuleConstant.MODULE_PRICE_CONFIG);
        log.setModule(module);
        log.setOperation(operation);
        log.setLog(reason);

        logDAO.saveEntityBean(log);
    }

    @Override
	public void onWaitFetchStock(User user, StockBean bean, StockItemBean item)
			throws MYException
	{
    	// do nothing
	}
    
    @Override
	public void onCheckStockPay(User user, String stockId, String stockItemId, String reason)
			throws MYException
	{
		// do nothing
	}
    
    public String getListenerType()
    {
        return "StockListener.SailImpl";
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

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	/**
	 * @return the invoiceDAO
	 */
	public InvoiceDAO getInvoiceDAO()
	{
		return invoiceDAO;
	}

	/**
	 * @param invoiceDAO the invoiceDAO to set
	 */
	public void setInvoiceDAO(InvoiceDAO invoiceDAO)
	{
		this.invoiceDAO = invoiceDAO;
	}

    public StockItemArrivalDAO getStockItemArrivalDAO() {
        return stockItemArrivalDAO;
    }

    public void setStockItemArrivalDAO(StockItemArrivalDAO stockItemArrivalDAO) {
        this.stockItemArrivalDAO = stockItemArrivalDAO;
    }

    public LogDAO getLogDAO() {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
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
}
