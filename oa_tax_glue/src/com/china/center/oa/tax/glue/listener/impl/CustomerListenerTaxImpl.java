/**
 * File Name: CustomerListenerTaxImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-8-21<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.client.bean.AssignApplyBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.listener.ClientListener;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 客户删除的凭证监听
 * 
 * @author ZHUZHU
 * @version 2011-8-21
 * @see CustomerListenerTaxImpl
 * @since 3.0
 */
public class CustomerListenerTaxImpl implements ClientListener
{
    private final Log _logger = LogFactory.getLog(getClass());

    private FinanceItemDAO financeItemDAO = null;

    private TcpApproveDAO tcpApproveDAO = null;

    private FlowLogDAO flowLogDAO = null;

    /**
     * default constructor
     */
    public CustomerListenerTaxImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.customer.listener.CustomerListener#onDelete(com.china.center.oa.customer.bean.CustomerBean)
     */
    public void onDelete(CustomerBean bean)
        throws MYException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        condtion.addCondition("unitId", "=", bean.getId());

        int countByCondition = financeItemDAO.countByCondition(condtion.toString());

        if (countByCondition > 0)
        {
            throw new MYException("客户[%s]已经生成[%d]条凭证数据,不能删除", bean.getName(), countByCondition);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.customer.listener.CustomerListener#onNoPayBusiness(com.china.center.oa.customer.bean.CustomerBean)
     */
    public double onNoPayBusiness(CustomerBean bean)
    {
        return 0;
    }

    /**
     * 通过其他实现
     */
    @Override
    @Transactional(rollbackFor = MYException.class)
    public void onChangeCustomerRelation(User user, AssignApplyBean apply, CustomerBean cus, String destStafferId)
        throws MYException
    {
        // NA
        _logger.info("****onChangeCustomerRelation***");
        // #847 状态为‘待申请人关联销售’的预开票会跟随客户转移到最新的业务员名下预开票待我处理中，原预开票申请中申请单不调整
        List<TcpApproveBean> approveBeans = tcpApproveDAO.queryPreInvoiceType(TcpConstanst.TCP_STATUS_APPLY_RELATE,cus.getId());
        if(!ListTools.isEmptyOrNull(approveBeans)){
            _logger.info(approveBeans.size()+" approveBeans to be transferred ***"+approveBeans);
            for (TcpApproveBean bean: approveBeans){
                //更新处理人
                this.tcpApproveDAO.updateApproverId(bean.getId(), destStafferId);

                String oldStaffer = bean.getApproverId();
                FlowLogBean log = new FlowLogBean();
                log.setActor(user.getStafferName());
                log.setDescription("变更预开票处理人,从:" + oldStaffer + "到:" + destStafferId);
                log.setFullId(bean.getId());
                log.setOprMode(PublicConstant.OPRMODE_PASS);
                log.setLogTime(TimeTools.now());
                log.setPreStatus(bean.getStatus());
                log.setAfterStatus(bean.getStatus());
                flowLogDAO.saveEntityBean(log);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "CustomerListener.TaxImpl";
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

    public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO) {
        this.tcpApproveDAO = tcpApproveDAO;
    }

    public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
        this.flowLogDAO = flowLogDAO;
    }
}
