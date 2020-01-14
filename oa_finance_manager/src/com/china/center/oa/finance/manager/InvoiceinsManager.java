/**
 * File Name: InvoiceinsManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.manager;


import java.util.List;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.InvoiceBindOutBean;
import com.china.center.oa.finance.bean.InvoiceStorageBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.bean.InvoiceinsImportBean;
import com.china.center.oa.finance.listener.InvoiceinsListener;
import com.china.center.oa.finance.vo.InvoiceinsVO;
import com.china.center.oa.sail.bean.OutTransferBean;


/**
 * InvoiceinsManager
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InvoiceinsManager
 * @since 3.0
 */
public interface InvoiceinsManager extends ListenerManager<InvoiceinsListener>
{
    boolean addInvoiceinsBean(User user, InvoiceinsBean bean)
        throws MYException;

    boolean passInvoiceinsBean(User user, InvoiceinsBean bean, String reason)
        throws MYException;

    boolean checkInvoiceinsBean(User user, String id, String reason)
        throws MYException;

    boolean rejectInvoiceinsBean(User user, String id, String reason)
        throws MYException;

    /**
     * 总部核对
     * 
     * @param id
     * @return
     * @throws MYException
     */
    boolean checkInvoiceinsBean2(User user, String id, String checks, String refId)
        throws MYException;

    boolean deleteInvoiceinsBean(User user, String id)
        throws MYException;

    InvoiceinsVO findVO(String id);

    void clearRejectInvoiceinsBean()
        throws MYException;
    
    boolean confirmInvoice(User user, String id)
    throws MYException;
    
    boolean confirmPay(User user, String id)
    throws MYException;
    
    boolean backInvoiceins(User user, String id)
    throws MYException;
    
    int backInvoiceins(User user, List<String> ids)
    throws MYException;
    
    boolean importInvoice(User user, List<InvoiceStorageBean> list)
    throws MYException;
    
    boolean refConfirmInvoice(User user, List<InvoiceBindOutBean> vsList)
	throws MYException;
    
    boolean refInvoice(User user, List<InvoiceBindOutBean> vsList)
	throws MYException;
    
    String importInvoiceins(User user, List<InvoiceinsImportBean> list)
    throws MYException;
    
    boolean batchUpdateInsNum(User user, List<InvoiceinsImportBean> list)
    throws MYException;
    
    boolean process(List<InvoiceinsImportBean> list)
	throws MYException;

    boolean process2(List<InvoiceinsImportBean> list)
            throws MYException;

    boolean processAsyn(List<InvoiceinsImportBean> list);

    /**
     * #169 开票流程变更
     * @param list
     * @return
     */
    boolean processAsyn2(List<InvoiceinsImportBean> list);
    
    void checkImportIns(List<InvoiceinsImportBean> list, StringBuilder sb);

    //2015/1/28 票随货发Job 5分钟运行一次
    void insFollowOutJob() throws MYException;

    /**
     * 2016/3/6 #182 票随货发JOB自动库管审批通过
     * @throws MYException
     */
    void autoApproveJob() throws MYException;

    //2015/4/8 设置发票号A开头对应的CK单为紧急
    public boolean updateEmergency(User user, String fullId) throws MYException;

    /**
     * 2015/8/28 批量发票转移
     * @param list
     * @return
     * @throws MYException
     */
    boolean batchTransferInvoiceins(List<OutTransferBean> list) throws MYException;

    public boolean batchConfirm(User user, List<InvoiceinsVO> beanList) throws MYException;

    boolean batchConfirmAndCreatePackage(User user, List<InvoiceinsVO> beanList) throws MYException;

    void generateInvoiceins(String packageId,String insId,String fphm);

    /**
     * 合并开票品名
     * @param insId
     * @return
     */
    String concatSpmc(String insId);
}
