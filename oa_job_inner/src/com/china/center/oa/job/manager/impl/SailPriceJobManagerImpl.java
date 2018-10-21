package com.china.center.oa.job.manager.impl;

import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.job.manager.JobManager;
import com.china.center.oa.product.bean.ComposeItemBean;
import com.china.center.oa.product.bean.ComposeProductBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.constant.ComposeConstant;
import com.china.center.oa.product.dao.ComposeItemDAO;
import com.china.center.oa.product.dao.ComposeProductDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.manager.ComposeProductManager;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.tools.ListTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

    @Override
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
                //TODO
                List<ComposeItemBean> items = this.composeItemDAO.queryEntityBeansByFK(bean.getId());
                this.composeProductManager.updateSailPrice(null, bean, items);
            }
        }

    }
}
