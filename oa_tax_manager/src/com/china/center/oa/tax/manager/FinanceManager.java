/**
 * File Name: FinanceManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.manager;


import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.PaymentBean;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceTurnBean;

import java.util.List;


/**
 * FinanceManager
 * 
 * @author ZHUZHU
 * @version 2011-2-7
 * @see FinanceManager
 * @since 1.0
 */
public interface FinanceManager
{
    /**
     * 事务的
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addFinanceBean(User user, FinanceBean bean)
        throws MYException;

    /**
     * 事务的
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addTempFinanceBean(User user, FinanceBean bean)
        throws MYException;

    /**
     * 结转
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addFinanceTurnBean(User user, FinanceTurnBean bean)
        throws MYException;

    /**
     * 删除月结
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deleteFinanceTurnBean(User user, String id)
        throws MYException;

    boolean updateFinanceBean(User user, FinanceBean bean)
        throws MYException;

    boolean updateTempFinanceBean(User user, FinanceBean bean)
        throws MYException;

    /**
     * #565
     * @return
     * @throws MYException
     */
    void addFinanceTurnJob() throws MYException;


    /**
     * #823 预收拆分JOB
     * @throws MYException
     */
    void yscfJob() throws MYException;
    /** 2014/12/30 add checkNull parameter for 自动审批job
     * addFinanceBeanWithoutTransactional(无事务的)
     * 
     *
     * @param user
     * @param bean
     * @param checkNull
     * @return
     * @throws MYException
     */
    boolean addFinanceBeanWithoutTransactional(User user, FinanceBean bean, boolean checkNull)
        throws MYException;
    
    public boolean addFinanceBeanWithTransactional(User user, FinanceBean bean, boolean checkNull)
        throws MYException;

    boolean addFinanceBeanWithoutTransactional(User user, FinanceBean bean, int type)
    throws MYException;

    /**
     * 2014/12/30 后台job自动审批坏账为0收款申请
     * @param user
     * @param bean
     * @param type
     * @param autoPayFlag
     * @return
     * @throws MYException
     */
    boolean addFinanceBeanWithoutTransactional(User user, FinanceBean bean, int type, boolean autoPayFlag)
            throws MYException;
    
    boolean deleteFinanceBean(User user, String id)
        throws MYException;

    boolean deleteTempFinanceBean(User user, String id)
        throws MYException;

    /**
     * copyFinanceBean
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    String copyFinanceBean(User user, String id, String financeDate)
        throws MYException;

    /**
     * 没有事务的删除
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deleteFinanceBeanWithoutTransactional(User user, String id)
        throws MYException;

    boolean checks(User user, String id, String reason)
        throws MYException;

    boolean checks2(User user, String id, int type, String reason)
        throws MYException;

    boolean deleteChecks(User user, String id)
        throws MYException;

    boolean updateFinanceCheck(User user, String id, String reason)
        throws MYException;

    /**
     * 移动凭证
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean moveTempFinanceBeanToRelease(User user, String id)
        throws MYException;

    /**
     * updateRefCheckByRefIdWithoutTransactional
     * 
     * @param refId
     * @param check
     * @return
     */
    boolean updateRefCheckByRefIdWithoutTransactional(String refId, String check);

    /**
     * fixMonthIndex
     */
    void fixMonthIndex();
    
    /**
     * 将勾款时产生的勾款凭证，预收/应收，且是销售单未审批的情况下产生的，当库管审批时，
     * 将之前产生的凭证移到正式凭证表中。
     * 
     * @param outId
     * @return
     * @throws MYException
     */
    boolean moveMidFinanceBeanToReleaseWithoutTrans(String outId) throws MYException;
    
    /**
     * 异步查询科目余额数据
     * @param begin
     * @param end
     * @return
     * @throws MYException
     */
    boolean asynQueryFinanceTax(String begin, String end) throws MYException;

    /**
     * #900
     * @param bankName
     * @return 贷款本金对应银行科目
     */
    String getDkbjTaxId(String bankName);

    /**
     * #901
     */
    void repairFinanceBeanJob();

    void createFinanceItem(User user, PaymentBean bean,
                                   String itemInName,String itemOutName,
                                   String itemTaxIdIn, String itemTaxIdOut,
                                   FinanceBean financeBean, List<FinanceItemBean> itemList) throws MYException;
}