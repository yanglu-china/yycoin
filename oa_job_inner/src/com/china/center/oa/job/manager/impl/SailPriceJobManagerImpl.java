package com.china.center.oa.job.manager.impl;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.job.manager.JobManager;
import com.china.center.oa.product.bean.ComposeItemBean;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ComposeConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.ComposeItemDAO;
import com.china.center.oa.product.dao.ComposeProductDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.manager.ComposeProductManager;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SailPriceJobManagerImpl implements JobManager {
    private final Log _logger = LogFactory.getLog(getClass());

    private ComposeProductDAO composeProductDAO = null;

    private ComposeItemDAO composeItemDAO = null;

    private ProductDAO productDAO = null;

    private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;

    private LogDAO logDAO = null;

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
                this.composeProductManager.updateSailPrice(null, bean, items);
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
                        if(product.getSailType()==ProductConstant.SAILTYPE_REPLACE)
                        {
                            // 日志
                            StringBuilder sb = new StringBuilder();
                            sb.append("取自采购入库单:"+outBean.getFullId()+".修改人:系统JOB采购")
                                    .append(".原产品结算价:").append(product.getSailPrice())
                                    .append(".更新为:").append(baseBean.getPrice());

                            product.setSailPrice(baseBean.getPrice());//采购商品的结算价更新为此张采购单的成本价
                            productDAO.updateEntityBean(product);

                            this.log(productId, OperationConstant.OPERATION_UPDATE, sb.toString());
                        }
                    }
                }
            }
        }
    }

    private void log(String id, String operation, String reason) {
        // 记录审批日志
        LogBean log = new LogBean();

        log.setFkId(id);

        log.setLocationId("系统");
        log.setStafferId("系统");
        log.setLogTime(TimeTools.now());
        log.setModule(ModuleConstant.MODULE_PRICE_CONFIG);
        log.setOperation(operation);
        log.setLog(reason);

        logDAO.saveEntityBean(log);
    }
}
