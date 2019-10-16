/**
 * File Name: OutManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.manager;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.center.china.osgi.publics.ListenerManager;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.china.center.common.MYException;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProductImportBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.wrap.ResultBean;
import com.china.center.oa.sail.bean.*;
import com.china.center.oa.sail.listener.OutListener;
import com.china.center.oa.sail.vo.OutInterface;
import com.china.center.oa.sail.wrap.BatchBackWrap;
import com.china.center.tools.ListTools;


/**
 * OutManager
 * 
 * @author ZHUZHU
 * @version 2010-11-7
 * @see OutManager
 * @since 1.0
 */
public interface OutManager extends ListenerManager<OutListener>
{
    String addOut(final OutBean outBean, final Map dataMap, final User user)
        throws MYException;
    

    /**
     * coloneOutAndSubmitAffair(接受调拨生成入库单)
     * 
     * @param outBean
     * @param user
     * @return
     * @throws MYException
     */
    String coloneOutAndSubmitAffair(final OutBean outBean, final User user, int type)
        throws MYException;

    /**
     * coloneOutWithAffair(事务提交,入库单)
     * 
     * @param outBean
     * @param user
     * @param type
     * @return
     * @throws MYException
     */
    String coloneOutWithAffair(final OutBean outBean, final User user, int type)
        throws MYException;
    
    /**
     *批量退库
     * 
     * @param outBean
     * @param user
     * @param type
     * @return
     * @throws MYException
     */
    String coloneOutWithAffair1(final OutBean outBean, final User user, int type)
        throws MYException;
    
    String diaoBo(final OutBean outBean, final Map dataMap, final User user,String proid[],String amount[])
    throws MYException;
    
    int submitDiaoBo(final String fullId, final User user, final int storageType)
    throws MYException;

    /**
     * 2015/10/20 入库-换货
     * @param outBean
     * @param dataMap
     * @param user
     * @param proid
     * @param amount
     * @return
     * @throws MYException
     */
    String exchange(final OutBean outBean, final Map dataMap, final User user,String proid[],String amount[])
            throws MYException;

    /**
     * 2015/10/20 入库-换货提交
     * @param fullId
     * @param user
     * @param storageType
     * @return
     * @throws MYException
     */
    int submitExchange(final String fullId, final User user, final int storageType)
            throws MYException;
    
    int pass1(final String fullId, final User user, final int nextStatus,
            final String reason, final String depotpartId)
    throws MYException;
    
    int submitWithOutAffair1(final String fullId, final User user, int type)
    throws MYException;
    
    int processOutStutus1(final String fullId, final User user, final OutBean outBean)
    throws MYException;
    
    

    /**
     * coloneOutWithOutAffair(自动生成个人领样的退货入库单,且在保存态)
     * 
     * @param outBean
     * @param user
     * @return
     * @throws MYException
     */
    String coloneOutWithoutAffair(final OutBean outBean, final User user, int type)
        throws MYException;

    /**
     * 采购入库的操作/调拨回滚(没有事务)
     * 
     * @param outBean
     * @param user
     * @param type
     * @return
     * @throws MYException
     */
    String coloneOutAndSubmitWithOutAffair(OutBean outBean, User user, int type)
        throws MYException;

    /**
     * submit
     * 
     * @param fullId
     * @param user
     * @param storageType
     *            库存变动类型
     * @return 修改后的单据状态
     * @throws MYException
     */
    int submit(final String fullId, final User user, int storageType)
        throws MYException;


    /**  2014/12/29 拆分成品行退货
     * submit
     *
     * @param fullId
     * @param user
     * @param storageType
     *            库存变动类型
     * @param baseBeans 成品行
     * @return 修改后的单据状态
     * @throws MYException
     */
    int submit2(final String fullId, final User user, int storageType, List<BaseBean> baseBeans)
            throws MYException;

    /**  2015/10/23 入库换货
     * submit
     *
     * @param fullId
     * @param user
     * @param storageType
     *            库存变动类型
     * @param baseBeans 成品行
     * @return 修改后的单据状态
     * @throws MYException
     */
    int submit3(final String fullId, final User user, int storageType, List<BaseBean> baseBeans)
            throws MYException;

    /**
     * submitWithOutAffair(采购入库的时候使用)
     * 
     * @param fullId
     * @param user
     * @return
     * @throws MYException
     */
    // int submitWithOutAffair(final String fullId, final User user, int type)
    // throws MYException;
    /**
     * reject
     * 
     * @param fullId
     * @param user
     * @param reason
     * @return 修改后的单据状态
     * @throws MYException
     */
    int reject(final String fullId, final User user, final String reason)
        throws MYException;

    /**
     * #593
     * @param fullId
     * @param user
     * @param reason
     * @return
     * @throws MYException
     */
    int rejectWithZs(final String fullId, final int status, final User user, final String reason)
            throws MYException;

    /**
     * pass
     * 
     * @param fullId
     * @param user
     * @param nextStatus
     * @param reason
     * @param depotpartId
     * @return 修改后的单据状态
     * @throws MYException
     */
    int pass(final String fullId, final User user, final int nextStatus, final String reason,
             final String customerDescription,
             final String depotpartId)
        throws MYException;
    
    /**
     * pass
     * 
     * @param fullId
     * @param user
     * @param nextStatus
     * @param reason
     * @param depotpartId
     * @return 修改后的单据状态
     * @throws MYException
     */
    int passBuyReturn(final String fullId, final User user, final int nextStatus, final String reason,
             final String customerDescription,
             final String depotpartId)
        throws MYException;

    /**
     * #593 自动通过赠送单
     * @param fullId
     * @param user
     * @param nextStatus
     * @param reason
     * @param customerDescription
     * @param depotpartId
     * @return
     * @throws MYException
     */
    int passWithZs(final String fullId, final User user, final int nextStatus, final String reason,
             final String customerDescription,
             final String depotpartId)
            throws MYException;

    boolean check(final String fullId, final User user, final String checks)
        throws MYException;

    boolean checkOutBalance(final String id, final User user, final String checks)
        throws MYException;

    OutBean findOutById(final String fullId);

    boolean delOut(User user, final String fullId)
        throws MYException;

    /**
     * #230 退库驳回时，只改状态
     * @param user
     * @param fullId
     * @param reason
     * @return
     * @throws MYException
     */
    boolean rejectOutBack(User user, final String fullId, String reason)throws MYException;

    /**
     * 转调处理(仅仅用于)
     * 
     * @param out
     * @return
     * @throws MYException
     */
    boolean updateOut(final OutBean out)
        throws MYException;

    /**
     * 增加代销结算
     * 
     * @param bean
     * @return
     * @throws MYException
     */
    boolean addOutBalance(final User user, OutBalanceBean bean)
        throws MYException;

    /**
     * passOutBalance
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean passOutBalance(final User user, String id)
        throws MYException;

    /**
     * passOutBalance
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean passOutBalanceToDepot(final User user, String id)
        throws MYException;

    /**
     * deleteOutBalance
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean deleteOutBalance(final User user, String id)
        throws MYException;

    /**
     * rejectOutBalance
     * 
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean rejectOutBalance(final User user, String id, String reason)
        throws MYException;

    /**
     * 销售单付款
     * 
     * @param user
     * @param fullId
     * @param reason
     * @return
     */
    boolean payOut(final User user, String fullId, String reason)
        throws MYException;


    /**
     * 更新紧急状态
     *
     * @param user
     * @param fullId
     * @return
     */
    boolean updateEmergency(final User user,String fullId) throws MYException;

    /**
     * fourcePayOut
     * 
     * @param user
     * @param fullId
     * @param reason
     * @return
     * @throws MYException
     */
    boolean fourcePayOut(final User user, String fullId, String reason)
        throws MYException;

    /**
     * payOutWithoutTransactional(销售单付款)
     * 
     * @param user
     * @param fullId
     * @param reason
     * @return
     * @throws MYException
     */
    boolean payOutWithoutTransactional(final User user, String fullId, String reason)
        throws MYException;

    /**
     * payOutWithoutTransactional(更新销售单的付款状态,除了参数检查其他不抛出异常)
     * 
     * @param user
     * @param fullId
     * @param reason
     * @return
     * @throws MYException
     */
    boolean payOutWithoutTransactional2(final User user, String fullId, String reason)
        throws MYException;

    /**
     * payBaddebts
     * 
     * @param user
     * @param fullId
     * @return
     * @throws MYException
     */
    boolean payBaddebts(final User user, String fullId, double bad)
        throws MYException;

    /**
     * updateInvoice
     * 
     * @param user
     * @param fullId
     * @return
     * @throws MYException
     */
    boolean updateInvoice(final User user, String fullId, String invoiceId)
        throws MYException;

    /**
     * initPayOut(返回付款状态且坏账为0)
     * 
     * @param user
     * @param fullId
     * @return
     * @throws MYException
     */
    boolean initPayOut(final User user, String fullId)
        throws MYException;

    boolean mark(String fullId, boolean status);

    boolean modifyReDate(String fullId, String reDate);

    /**
     * 获得out在日志里面的状态
     * 
     * @param fullId
     * @return
     */
    int findOutStatusInLog(String fullId);

    /**
     * 是否是领样转销售
     * 
     * @param fullId
     * @return
     */
    boolean isSwatchToSail(String fullId);

    String addSwatchToSail(final User user, final OutBean outBean)
        throws MYException;

    /**
     * 销售单还剩余未付款的金额(这里坏账是在内的),仅仅是普通销售的<br>
     * 销售金额 + 退货返还金额 - 收款金额 - 坏账 - 退货实物价值
     * 
     * @param user
     * @param fullId
     * @return
     */
    double outNeedPayMoney(User user, String fullId);

    /**
     * 检查销售单的回款明细(如果金额溢出抛出异常)0:收支相等 -1:费用不足 1:费用超支
     * 
     * @param user
     * @param out
     * @throws MYException
     */
    ResultBean checkOutPayStatus(User user, OutInterface out);

    /**
     * initPriceKey
     * 
     * @return
     */
    int[] initPriceKey();

    /**
     * 初始化销售单的产品销售类型(管理还是普通)
     */
    void initPmtype();

    /**
     * 初始化退单的结算价
     */
    void initOutBackBasePrice();

    /**
     * 初始化总部事业部结算价
     */
    void initOutPrice();

    /**
     * 导出信用
     */
    void exportAllStafferCredit();

    /**
     * 职员信用导出
     * 
     * @param write
     * @param stafferList
     * @throws IOException
     */
    void writeStafferCredit(WriteFile write, List<StafferBean> stafferList)
        throws IOException;

    /**
     * 销售单移交申请
     * 
     * @param user
     * @param fullId
     * @return
     * @throws MYException
     */
    boolean tranOut(User user, String fullId, OutBean outBean)
        throws MYException;

    boolean rejectTranApply(User user, String id, String reason)
        throws MYException;

    boolean passTranApply(User user, String id)
        throws MYException;

    /**
     * 销售出库的完全移交(无事务)
     * 
     * @param user
     * @param outList
     * @param staffer
     * @return
     * @throws MYException
     */
    boolean tranCompleteOutListNT(User user, List<OutBean> outList, StafferBean staffer)
        throws MYException;
    
    /**
     * 更新客户信用分及业务员信用额度 
     * 
     * @param out
     * @param out
     * @return
     * @throws MYException
     */
    boolean updateCusAndBusVal(OutBean out,String id)
        throws MYException;
    
    /**
     * 委托代销的完全移交(无事务)
     * 
     * @param user
     * @param outList
     * @param staffer
     * @return
     * @throws MYException
     */
    boolean tranCompleteConsignOutListNT(User user, List<OutBean> outList, StafferBean staffer)
        throws MYException;
    
    /**
     * 空开空退
     * @param user
     * @param outRepaireBean
     * @return
     * @throws MYException
     */
    boolean outRepaire(User user, OutRepaireBean outRepaireBean)
    	throws MYException;
    
    /**
     * 驳回
     * @param user
     * @param id
     * @param reason 原因
     * @return
     * @throws MYException
     */
    boolean rejectOutRepaireApply(User user, String id, String reason)
    throws MYException;

    /**
     * 空开空退通过
     * @param user
     * @param id
     * @return
     * @throws MYException
     */
    boolean passOutRepaireApply(User user, String id)
    throws MYException;

    boolean kckj(User user, String fullId, String reason)  throws MYException;
    
    String addOutStep2(final OutBean outBean, final User user)
    throws MYException;
    
    /**
     * 货到付款销售单，检查一小时内有没有付款，如未付款，则自动驳回
     * @return
     */
    void handleCheckPay();
    
    void sendOutMail(OutBean out,String subject);
    
    void preCheckBeforeSubmit(String fullId)
    	throws MYException;
    
    /**
     * 拆分单据
     * @param id
     * @return
     * @throws MYException
     */
    String [] splitOut(String id) throws MYException;
    
    /**
     * 根据客户及产品查询可退货的委托代销单
     * @param customerId
     * @param productId
     * @return
     * @throws MYException
     */
    List<BatchBackWrap> queryConsignCanBack(String customerId, String productId, int amount, StringBuilder builder) 
    throws MYException;
    
    /**
     * 根据客户及产品查询可退货的领样、巡展单
     * @param customerId
     * @param productId
     * @return
     * @throws MYException
     */
    List<BatchBackWrap> queryOutSwatchCanBack(String stafferId, String customerId, String productId, int amount, StringBuilder builder)
    throws MYException;
    
    /**
     * 根据客户及产品查询可退货的销售出库单
     * @param customerId
     * @param productId
     * @return
     * @throws MYException
     */
    List<BatchBackWrap> queryOutCanBack(String customerId, String productId, int amount, int type, int otype, StringBuilder builder) 
    throws MYException;
    
    /**
     * 批量增加
     * @param user
     * @param beanList
     * @return
     * @throws MYException
     */
    boolean addBatchOutBalance(final User user, List<OutBalanceBean> beanList)
    throws MYException;
    
    /**
     * 批量增加
     * @param user
     * @param beanList
     * @return
     * @throws MYException
     */
    boolean addBatchOut(final User user, List<OutBean> beanList, int type)
    throws MYException;
    
    /**
     * 统计某一人的个人领样
     * @param stafferId
     * @param begin
     * @param end
     * @return
     */
    SwatchStatsBean statsPersonalSwatch(String stafferId, String begin, String end);
    
    String processSwatchCheck(OutBean out);
    
    double getSwatchMoney(String stafferId);
    
    /**
     * 批量生成结算单
     * @param user
     * @param list
     * @return
     * @throws MYException
     */
    boolean batchSettleOut(User user, List<OutBalanceBean> list)
    throws MYException;
    
    /**
     * 批量领样转销售
     * @param user
     * @param list
     * @return
     * @throws MYException
     */
    boolean batchSwatchOut(User user, List<OutBean> list)
    throws MYException;
    
    void addSwatchToSailWithoutTrans(final User user, final OutBean outBean)
    throws MYException;
    
    void directPassWithoutTrans(final User user, final OutBean outBean, final int type)
    throws MYException;
    
    boolean updateDistPrintCount(String id)
    	throws MYException;
    
    /**
     * 统计中信发货排序数据
     * @param user
     * @param list
     * @return
     * @throws MYException
     */
    String statsAndAddRank(User user, List<OutBean> list, String batchId)
    	throws MYException;
    
    boolean updateStatsRank(String id, String type, String value)
    	throws MYException;
    
    boolean sortsRank(User user, String batchId)
    	throws MYException;
    
    String passSortRank(User user, String batchId)
    	throws MYException;
    
    boolean undoSortRank(String batchId)
    	throws MYException;
    
    /**
     * 根据总的数量找到对应的库存批次，确定成本
     * @param baseList
     * @return
     * @throws MYException
     */
    List<BaseBean> splitBase(List<BaseBean> baseList) throws MYException;
    
    boolean checkSwithToSail(String outId) throws MYException;
    
    boolean checkOutBack(String outId) throws MYException;

    boolean checkOutBack(String outId, String productId, String priceKey, int amount) throws MYException;

    int processBuyOutInWay(final User user, final String fullId, final OutBean outBean)
            throws MYException;

    /**
     * 更新BaseBean
     *
     * @param base
     * @return
     * @throws MYException
     */
    boolean updateBase(final BaseBean base) throws MYException;

    public void createPackage(final OutBean outBean);

    /** 2015/2/25
     * 更新紫金农商订单状态
     * @param fullId
     * @return
     * @throws MYException
     */
    public  boolean updateZjrcOutStatus(final String fullId) throws MYException;

    /**
     * 2015/12/26 #150:导入自动库管审批订单
     * @param beans
     * @return
     * @throws MYException
     */
    public boolean importOutAutoApprove(List<AutoApproveBean> beans) throws MYException;

    /**
     * #150: 自动库管审批Job
     */
    public void autoApproveOutJob();

    /**
     * #150: 自动库管审批
     * @param fullId
     * @param user
     * @param nextStatus
     * @param reason
     * @return
     */
    public int autoApprove(String fullId, User user, int nextStatus,String reason) throws MYException;

    /**
     * 2016/2/14 #171
     * @param list
     * @return
     * @throws MYException
     */
    boolean batchUpdateReason(List<OutBean> list) throws MYException;

    /**
     * #326 领样销售退库快递单状态更新
     */
    void updateOutbackStatusJob();

    void statsExceptionalInBackJob();

    /**
     * 异地调拨JOB
     */
    void frDbJob();

    /**
     * #349
     * @param out
     * @return
     * @throws MYException
     */
    String createNewBuyBean(OutBean out) throws MYException;

    OutBean createNewOutBean(OutBean out, OutBackItemBean item) throws MYException;

    ProductImportBean getProductImportBean(OutBean out, CustomerBean customerBean, String productId) throws MYException;

    /**
     * #470
     * @param customerName
     * @param branchName
     * @param productCode
     * @param channel
     * @param citicOrderDate
     * @param outType
     * @param isImport
     * @return
     * @throws MYException
     */
    ProductImportBean getProductImportBean(String customerName, String branchName, String productCode, String channel,
                                            String citicOrderDate, int outType, boolean isImport) throws MYException;

    double getGrossProfit(OutBean out, CustomerBean customerBean,String productId);

    double getCash(OutBean out, CustomerBean customerBean,String productId);

    void createCustomerJob();

    /**
     * 根据配置获取基础结算价
     * @param productBean
     * @return
     */
    double getSailConfigPrice(ProductBean productBean);

    String getCostPriceKey(String outId, String productId) throws MYException;


    BaseBean getBaseBean(String outId, String productId, String costPriceKey) throws MYException;

    double getIprice(OutImportBean bean, ProductBean product);

    List<OutBean> getZsOrders(int action, String outId, int status, boolean checkStatus);
}
