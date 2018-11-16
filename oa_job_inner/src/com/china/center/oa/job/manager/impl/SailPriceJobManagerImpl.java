package com.china.center.oa.job.manager.impl;

import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.job.manager.JobManager;
import com.china.center.oa.product.bean.ComposeItemBean;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ComposeConstant;
import com.china.center.oa.product.dao.ComposeItemDAO;
import com.china.center.oa.product.dao.ComposeProductDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.manager.ComposeProductManager;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.constant.ModuleConstant;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

public class SailPriceJobManagerImpl implements JobManager {
    private final Log _logger = LogFactory.getLog(getClass());

    private ComposeProductDAO composeProductDAO = null;

    private ComposeItemDAO composeItemDAO = null;

    private ProductDAO productDAO = null;

    private PriceConfigDAO priceConfigDAO = null;

    private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;

    private LogDAO logDAO = null;

    private PriceConfigManager priceConfigManager = null;

    private ComposeProductManager composeProductManager = null;

    public ComposeProductManager getComposeProductManager() {
        return composeProductManager;
    }

    public void setComposeProductManager(ComposeProductManager composeProductManager) {
        this.composeProductManager = composeProductManager;
    }

    public ComposeProductDAO getComposeProductDAO() {
        return composeProductDAO;
    }

    public void setComposeProductDAO(ComposeProductDAO composeProductDAO) {
        this.composeProductDAO = composeProductDAO;
    }

    public PriceConfigManager getPriceConfigManager() {
        return priceConfigManager;
    }

    public void setPriceConfigManager(PriceConfigManager priceConfigManager) {
        this.priceConfigManager = priceConfigManager;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public ComposeItemDAO getComposeItemDAO() {
        return composeItemDAO;
    }

    public void setComposeItemDAO(ComposeItemDAO composeItemDAO) {
        this.composeItemDAO = composeItemDAO;
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

    public LogDAO getLogDAO() {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

    public PriceConfigDAO getPriceConfigDAO() {
        return priceConfigDAO;
    }

    public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO) {
        this.priceConfigDAO = priceConfigDAO;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void run() throws MYException {
        _logger.info("****SailPriceJobManagerImpl is running*****");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);

        // 今天通过的合成单
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addWhereStr();
        conditionParse.addCondition("type","=", ComposeConstant.COMPOSE_TYPE_COMPOSE);
        conditionParse.addCondition("status","=",ComposeConstant.STATUS_MANAGER_PASS);
        conditionParse.addCondition("logTime", ">=" ,today);
        conditionParse.addCondition(" order by ComposeProductBean.logTime asc");
        Map<String, ComposeProductBean> productId2LatestCompose = new HashMap<>();
        List<ComposeProductBean> composeProductBeanList = this.composeProductDAO.queryEntityBeansByCondition(conditionParse);
        if (!ListTools.isEmptyOrNull(composeProductBeanList)){
            _logger.info("composeProductBeanList size***"+composeProductBeanList.size());
            for (ComposeProductBean composeProductBean : composeProductBeanList){
                productId2LatestCompose.put(composeProductBean.getProductId(), composeProductBean);
            }

            _logger.info("productId2LatestCompose values***"+productId2LatestCompose.values());
            for (ComposeProductBean bean: productId2LatestCompose.values()){
                List<ComposeItemBean> items = this.composeItemDAO.queryEntityBeansByFK(bean.getId());
                this.updateSailPrice(bean, items);
            }
        }

        // 今天通过的采购单
        ConditionParse conditionParse2 = new ConditionParse();
        conditionParse2.addWhereStr();
        conditionParse2.addCondition("type","=", OutConstant.OUT_TYPE_INBILL);
        conditionParse2.addCondition("outType","=",OutConstant.OUTTYPE_IN_COMMON);
        conditionParse2.addCondition("flowTime", ">=" ,today);
        conditionParse2.addCondition("description","like","采购单自动转换成入库单%");
        conditionParse2.addCondition(" order by OutBean.flowTime asc");
        List<OutBean> outBeans = this.outDAO.queryEntityBeansByCondition(conditionParse2);
        if (!ListTools.isEmptyOrNull(outBeans)){
            _logger.info("latest buy size***"+outBeans.size());
            for(OutBean outBean : outBeans){
                List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByFK(outBean.getFullId());
                if (!ListTools.isEmptyOrNull(baseBeans)){
                    for(BaseBean baseBean: baseBeans){
                        String productId = baseBean.getProductId();
                        ProductBean product = this.productDAO.find(productId);
                        if (product!= null && product.getSailPriceFlag() ==  1
                                && this.needUpdate(productId)){
                            List<PriceConfigBean> list1 = priceConfigDAO.querySailPricebyProductId(productId);
                            if (ListTools.isEmptyOrNull(list1)){
                                // 日志
                                StringBuilder sb = new StringBuilder();
                                sb.append("取自采购入库单:"+outBean.getFullId()+".修改人:系统JOB采购")
                                        .append(".原产品结算价:").append(product.getSailPrice())
                                        .append(".更新为:").append(baseBean.getPrice());
                                this.log(productId, OperationConstant.OPERATION_UPDATE, sb.toString(), ModuleConstant.MODULE_PRICE_CONFIG);

                                //如果没有config配置项，直接更新product的sailPrice
                                product.setSailPrice(baseBean.getPrice());//采购商品的结算价更新为此张采购单的成本价
                                productDAO.updateEntityBean(product);
                            } else{
                                PriceConfigBean first = list1.get(0);
                                //有配置项，先更新配置项的辅料字段，然后通过公式算出结算价，然后更新回product的sailPrice
                                this.priceConfigDAO.updateGsPriceUp(productId, baseBean.getPrice());
                                //log
                                StringBuilder sb2 = new StringBuilder();
                                sb2.append("取自采购入库单:"+outBean.getFullId()+".修改人:系统JOB采购")
                                        .append(".原产品辅料价:").append(first.getGsPriceUp())
                                        .append(".更新为:").append(baseBean.getPrice());
                                this.log(productId, OperationConstant.OPERATION_UPDATE, sb2.toString(), ModuleConstant.MODULE_PRICE_CONFIG2);

                                first.setGsPriceUp(baseBean.getPrice());
                                PriceConfigBean cb = this.priceConfigManager.calcSailPrice(first);
                                if (cb!= null){
                                    // 日志
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("取自采购入库单:"+outBean.getFullId()+".修改人:系统JOB采购")
                                            .append(".原产品结算价:").append(product.getSailPrice())
                                            .append(".更新为:").append(cb.getSailPrice());
                                    this.log(productId, OperationConstant.OPERATION_UPDATE, sb.toString(), ModuleConstant.MODULE_PRICE_CONFIG);

                                    product.setSailPrice(cb.getSailPrice());
                                    productDAO.updateEntityBean(product);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // #430 检查是否虚料
    private boolean isVirtualProduct(String productId){
        ProductBean productBean = this.productDAO.find(productId);
        if (productBean!= null && productBean.getVirtualFlag() == 1){
            return true;
        }
//        ConditionParse conditionParse = new ConditionParse();
//        conditionParse.addWhereStr();
//        conditionParse.addIntCondition("virtualFlag","=", "1");
//        List<ProductBean> productBeans = this.productDAO.queryEntityBeansByCondition(conditionParse);
//        if(productBeans != null){
//            for(ProductBean productBean: productBeans){
//                if (productId.equals(productBean.getId())){
//                    return true;
//                }
//            }
//        }
        return false;
    }

    private void updateSailPrice(ComposeProductBean bean, List<ComposeItemBean> itemList){
        // #440 合成时修改产品总部结算价
        int type = 0;
        final String KTKC_DEFAULT = "A1201310151011526377";
        double sailPrice = -1;
        double virtualProductPrice = 0;
        for (ComposeItemBean composeItemBean : itemList)
        {
            // 如果合成单中“源仓区”为“空退空开库（仅限商务部操作）”，则忽略，不读取这种情况下合成单的产品价格
            if (KTKC_DEFAULT.equals(composeItemBean.getDepotpartId())){
                type = 1;
                break;
            }

            //  如果合成单中源仓区不是空退空开库，但是含有虚料，计算公式为：总部结算价=合成单对应的最终价格-（虚料产品对应的数量/合成单合成总数量）*虚料产品单价
            if (this.isVirtualProduct(composeItemBean.getProductId())){
                type = 2;
                virtualProductPrice += ((double)composeItemBean.getAmount()/bean.getAmount())*composeItemBean.getPrice();
            }
        }

        if (type == 0){
            // 如果合成单中源仓区不是空退空开库，并且不含虚料，则直接取合成单对应的产品“最终价格”
            sailPrice = bean.getPrice();
        } else if (type == 2){
            // 如果合成单中源仓区不是空退空开库，但是含有虚料
            sailPrice = bean.getPrice() - virtualProductPrice;
        }

        if (sailPrice > 0){
            String productId = bean.getProductId();
            ProductBean productBean = this.productDAO.find(productId);
            if (productBean!= null && productBean.getSailPriceFlag() ==  1
                    && this.needUpdate(productId)){
                List<PriceConfigBean> list1 = priceConfigDAO.querySailPricebyProductId(productId);
                if (ListTools.isEmptyOrNull(list1)){
                    // 日志
                    StringBuilder sb = new StringBuilder();
                    sb.append("取自合成单:"+bean.getId()+".修改人:系统JOB合成")
                            .append(".原产品结算价:").append(productBean.getSailPrice())
                            .append(".更新为:").append(sailPrice);
                    this.log(bean.getProductId(), OperationConstant.OPERATION_UPDATE, sb.toString(), ModuleConstant.MODULE_PRICE_CONFIG);

                    //如果没有config配置项，直接更新product的sailPrice
                    productBean.setSailPrice(sailPrice);
                    productDAO.updateEntityBean(productBean);
                } else {
                    PriceConfigBean first = list1.get(0);
                    //有配置项，先更新配置项的辅料字段，然后通过公式算出结算价，然后更新回product的sailPrice
                    this.priceConfigDAO.updateGsPriceUp(productId, sailPrice);
                    //log
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("取自合成单:"+bean.getId()+".修改人:系统JOB采购")
                            .append(".原产品辅料价:").append(first.getGsPriceUp())
                            .append(".更新为:").append(sailPrice);
                    this.log(bean.getProductId(), OperationConstant.OPERATION_UPDATE, sb2.toString(), ModuleConstant.MODULE_PRICE_CONFIG2);
                    first.setGsPriceUp(sailPrice);
                    PriceConfigBean cb = this.priceConfigManager.calcSailPrice(first);
                    if (cb!= null){
                        // 日志
                        StringBuilder sb = new StringBuilder();
                        sb.append("取自合成单:"+bean.getId()+".修改人:系统JOB采购")
                                .append(".原产品结算价:").append(productBean.getSailPrice())
                                .append(".更新为:").append(cb.getSailPrice());
                        this.log(bean.getProductId(), OperationConstant.OPERATION_UPDATE, sb.toString(), ModuleConstant.MODULE_PRICE_CONFIG);

                        productBean.setSailPrice(cb.getSailPrice());
                        productDAO.updateEntityBean(productBean);
                    }
                }

            }
        } else{
            _logger.warn("sailPrice is 0:"+bean);
        }
    }

    private boolean needUpdate(String productId){
        // test only!
        Set<String> productIds = new HashSet<String>();
        productIds.add("635255280");
        productIds.add("635255282");
        productIds.add("635255284");
        productIds.add("635255286");
        productIds.add("635255288");
        productIds.add("635255290");
        productIds.add("635255292");
        productIds.add("635255294");
        productIds.add("635255296");
        productIds.add("635255298");
        productIds.add("15047423");
        productIds.add("573333316");
        productIds.add("598351394");
        productIds.add("625888572");

        if (!productIds.isEmpty()){
            return productIds.contains(productId);
        }

        return true;
    }

    private void log(String id, String operation, String reason, String module) {
        // 记录审批日志
        LogBean log = new LogBean();

        log.setFkId(id);
        log.setLocationId("系统");
        log.setStafferId("系统");
        log.setLogTime(TimeTools.now());
        log.setModule(module);
        log.setOperation(operation);
        log.setLog(reason);

        logDAO.saveEntityBean(log);
    }
}
