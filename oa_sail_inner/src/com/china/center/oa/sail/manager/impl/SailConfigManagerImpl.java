/**
 * File Name: SailConfigManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.manager.impl;


import java.util.List;

import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.constant.ModuleConstant;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.tools.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.expression.Expression;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.bean.ShowBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.ShowDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.bean.SailConfigBean;
import com.china.center.oa.sail.constanst.SailConstant;
import com.china.center.oa.sail.dao.SailConfDAO;
import com.china.center.oa.sail.dao.SailConfigDAO;
import com.china.center.oa.sail.helper.SailConfigHelper;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.sail.vo.SailConfigVO;


/**
 * SailConfigManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see SailConfigManagerImpl
 * @since 3.0
 */
@IntegrationAOP
public class SailConfigManagerImpl implements SailConfigManager
{
    private final Log operationLog = LogFactory.getLog("opr");

    private final Log _logger = LogFactory.getLog(getClass());

    private SailConfigDAO sailConfigDAO = null;

    private SailConfDAO sailConfDAO = null;

    private CommonDAO commonDAO = null;

    private ShowDAO showDAO = null;

    private LogDAO logDAO          = null;

    /**
     * default constructor
     */
    public SailConfigManagerImpl()
    {
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean addBean(User user, List<SailConfigBean> list)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, list);

        SailConfigBean baseBean = list.get(0);

        String pareId = baseBean.getPareId();

        if (StringTools.isNullOrNone(pareId))
        {
            pareId = commonDAO.getSquenceString();
        }

        for (SailConfigBean sailConfigBean : list)
        {
            ShowBean show = showDAO.find(sailConfigBean.getShowId());

            if (show == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            String productType = DefinedCommon.getValue("productType", sailConfigBean
                .getProductType());

            String productSailType = DefinedCommon.getValue("productSailType", sailConfigBean
                .getSailType());

            String msg = show.getName() + "+" + productType + "+" + productSailType;

            Expression exp = new Expression(sailConfigBean, this);

            exp.check("#showId && #sailType && #productType &unique @sailConfigDAO", "销售组合已经存在:"
                                                                                     + msg);

            // 保证pare里面的配置是一致的
            BeanUtil.copyProperties(sailConfigBean, baseBean);

            sailConfigBean.setShowId(show.getId());

            sailConfigBean.setId(commonDAO.getSquenceString20());

            sailConfigBean.setPareId(pareId);
        }

        sailConfigDAO.saveAllEntityBeans(list);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean addBean(User user, SailConfBean bean, boolean importFlag)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString20());
        bean.setOperator(user.getStafferName());
        bean.setLogTime(TimeTools.now());
        if ( !StringTools.isNullOrNone(bean.getProductId()) && !"0".equals(bean.getProductId()))
        {
            bean.setType(SailConstant.SAILCONFIG_ONLYPRODUCT);

            bean.setSailType( -1);

            bean.setProductType( -1);
        }
        else
        {
            if (bean.getProductType() == -1 && bean.getSailType() == -1)
            {
                throw new MYException("数据错误");
            }

            bean.setProductId("0");

            if (bean.getProductType() != -1)
            {
                bean.setType(SailConstant.SAILCONFIG_PRODUCTTYPE);

                bean.setSailType( -1);
            }

            if (bean.getSailType() != -1)
            {
                bean.setType(SailConstant.SAILCONFIG_SAILTTYPE);

                bean.setProductType( -1);
            }
        }

        if (importFlag){
            //#169
            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addWhereStr();
            conditionParse.addCondition("sailType","=",bean.getSailType());
            conditionParse.addCondition("productType","=",bean.getProductType());
            conditionParse.addCondition("productId","=",bean.getProductId());
            conditionParse.addCondition("industryId","=",bean.getIndustryId());
            List<SailConfBean> beans = this.sailConfDAO.queryEntityBeansByCondition(conditionParse);
            if (!ListTools.isEmptyOrNull(beans)){
                SailConfBean old = sailConfDAO.find(beans.get(0).getId());
                _logger.info("update sail conf,old:" + old);

                StringBuilder sb = new StringBuilder();
                sb.append("修改人:").append(user.getStafferName())
                        .append(".原结算价信息:").append(old.toString());
                //日志
                this.log(user, bean.getId(), OperationConstant.OPERATION_UPDATE,sb.toString());

                old.setPratio(bean.getPratio());
                old.setIratio(bean.getIratio());
                old.setDescription(bean.getDescription());
                old.setLogTime(bean.getLogTime());
                old.setOperator(bean.getOperator());

                return this.sailConfDAO.updateEntityBean(old);
            }
        } else{
            Expression exp = new Expression(bean, this);

            exp.check("#sailType && #productType && #productId && #industryId &unique @sailConfDAO",
                    "结算价格配置组合已经存在");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("创建人:").append(user.getStafferName());
        //日志
        this.log(user, bean.getId(), OperationConstant.OPERATION_NEW,sb.toString());
        return sailConfDAO.saveEntityBean(bean);
    }

    private void log(User user, String id,String operation, String reason) {
        // 记录审批日志
        LogBean log = new LogBean();

        log.setFkId(id);

        log.setLocationId(user.getLocationId());
        log.setStafferId(user.getStafferId());
        log.setLogTime(TimeTools.now());
        log.setModule(ModuleConstant.MODULE_PRICE_CONFIG);
        log.setOperation(operation);
        log.setLog(reason);

        logDAO.saveEntityBean(log);
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean importSailConfig(User user, List<SailConfBean> beans) throws MYException {
        if(ListTools.isEmptyOrNull(beans)){
            throw new MYException("导入内容为空,请确认模板!");
        } else{
            for (SailConfBean bean: beans){
                this.addBean(user, bean,true);
            }
        }

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteConf(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        SailConfBean conf = sailConfDAO.find(id);

        if (conf == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        operationLog.info("delete sail conf:" + conf);

        return sailConfDAO.deleteEntityBean(id);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateBean(User user, SailConfBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        SailConfBean old = sailConfDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        operationLog.info("update sail conf,old:" + old);

        bean.setProductType(old.getProductType());
        bean.setSailType(old.getSailType());
        bean.setProductId(old.getProductId());
        bean.setIndustryId(old.getIndustryId());

        if ( !StringTools.isNullOrNone(bean.getProductId()) && !"0".equals(bean.getProductId()))
        {
            bean.setType(SailConstant.SAILCONFIG_ONLYPRODUCT);

            bean.setSailType( -1);

            bean.setProductType( -1);
        }
        else
        {
            if (bean.getProductType() == -1 && bean.getSailType() == -1)
            {
                throw new MYException("数据错误");
            }

            bean.setProductId("0");

            if (bean.getProductType() != -1)
            {
                bean.setType(SailConstant.SAILCONFIG_PRODUCTTYPE);

                bean.setSailType( -1);
            }

            if (bean.getSailType() != -1)
            {
                bean.setType(SailConstant.SAILCONFIG_SAILTTYPE);

                bean.setProductType( -1);
            }
        }

        Expression exp = new Expression(bean, this);

        exp.check("#sailType && #productType && #productId && #industryId &unique2 @sailConfDAO",
            "结算价格配置组合已经存在");

        sailConfDAO.updateEntityBean(bean);

        operationLog.info("update sail conf,new:" + bean);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteBean(User user, String pareId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, pareId);

        sailConfigDAO.deleteEntityBeansByFK(pareId);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateBean(User user, String pareId, List<SailConfigBean> bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, pareId);

        sailConfigDAO.deleteEntityBeansByFK(pareId);

        return addBean(user, bean);
    }

    public SailConfigVO findVO(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        SailConfigVO obj = sailConfigDAO.findVO(id);

        if (obj == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        List<SailConfigVO> list = sailConfigDAO.queryEntityVOsByFK(obj.getPareId());

        for (SailConfigVO sailConfigVO : list)
        {
            obj.setShowAllName(obj.getShowAllName() + '/' + sailConfigVO.getShowName());
        }

        obj.setShowAllName(obj.getShowAllName().substring(1));

        SailConfigHelper.changeVO(obj);

        return obj;
    }

    public SailConfBean findProductConf(StafferBean sb, ProductBean productBean)
    {
        if ( !OATools.isChangeToV5())
        {
            // 返回默认的
            SailConfBean result = new SailConfBean();

            result.setIratio(0);

            result.setPratio(0);

            return result;
        }

        String industryId = sb.getIndustryId();
        
        if (StringTools.isNullOrNone(industryId))
        {
        	industryId = "OA";
        }
        
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addIntCondition("type", "=", SailConstant.SAILCONFIG_ONLYPRODUCT);

        con.addCondition("productId", "=", productBean.getId());

        con.addCondition("industryId", "=", industryId);

        List<SailConfBean> conf = sailConfDAO.queryEntityBeansByCondition(con.toString());

        if (conf.size() > 0)
        {
            return conf.get(0);
        }

        // 然后就是产品类型
        con.clear();

        con.addWhereStr();

        con.addIntCondition("type", "=", SailConstant.SAILCONFIG_PRODUCTTYPE);

        con.addIntCondition("productType", "=", productBean.getType());

        con.addCondition("industryId", "=", industryId);

        conf = sailConfDAO.queryEntityBeansByCondition(con.toString());

        if (conf.size() > 0)
        {
            return conf.get(0);
        }

        // 然后就是产品类型
        con.clear();

        con.addWhereStr();

        con.addIntCondition("type", "=", SailConstant.SAILCONFIG_SAILTTYPE);

        con.addIntCondition("sailType", "=", productBean.getSailType());

        con.addCondition("industryId", "=", industryId);

        conf = sailConfDAO.queryEntityBeansByCondition(con.toString());

        if (conf.size() > 0)
        {
            return conf.get(0);
        }

        // 返回默认的
        SailConfBean result = new SailConfBean();

        result.setIratio(0);

        result.setPratio(0);

        // 先查询产品
        return result;
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
     * @return the sailConfDAO
     */
    public SailConfDAO getSailConfDAO()
    {
        return sailConfDAO;
    }

    /**
     * @param sailConfDAO
     *            the sailConfDAO to set
     */
    public void setSailConfDAO(SailConfDAO sailConfDAO)
    {
        this.sailConfDAO = sailConfDAO;
    }

    public LogDAO getLogDAO() {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }
}
