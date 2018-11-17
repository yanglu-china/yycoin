/**
 * File Name: StockManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.manager.impl;


import java.util.*;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.helper.StorageRelationHelper;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.DhZjbVO;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DhZjbDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.stock.bean.*;
import com.china.center.oa.stock.dao.*;
import com.china.center.oa.stock.vo.StockItemArrivalVO;
import com.china.center.tools.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.note.bean.ShortMessageTaskBean;
import com.china.center.oa.note.constant.ShortMessageConstant;
import com.china.center.oa.note.dao.ShortMessageTaskDAO;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AuthConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.SysConfigConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.LocationDAO;
import com.china.center.oa.publics.dao.ParameterDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.helper.AuthHelper;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.stock.constant.PriceConstant;
import com.china.center.oa.stock.constant.StockConstant;
import com.china.center.oa.stock.listener.StockListener;
import com.china.center.oa.stock.manager.PriceAskManager;
import com.china.center.oa.stock.manager.StockManager;
import com.china.center.oa.stock.vo.PriceAskProviderBeanVO;
import com.china.center.oa.stock.vo.StockItemVO;
import com.china.center.oa.stock.vo.StockVO;


/**
 * StockManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see StockManagerImpl
 * @since 1.0
 */
@Exceptional
public class StockManagerImpl extends AbstractListenerManager<StockListener> implements StockManager
{
    private StockDAO stockDAO = null;

    private PurchaseBjDAO purchaseBjDAO = null;

    private CommonDAO commonDAO = null;
    
    private final Log _logger = LogFactory.getLog(getClass());

    /**
     * 流程日志
     */
    private FlowLogDAO flowLogDAO = null;

    private StockItemDAO stockItemDAO = null;

    private LocationDAO locationDAO = null;

    private ParameterDAO parameterDAO = null;

    private StafferDAO stafferDAO = null;

    private ProductDAO productDAO = null;

    private PriceAskManager priceAskManager = null;

    private PriceAskDAO priceAskDAO = null;

    private ShortMessageTaskDAO shortMessageTaskDAO = null;

    private String stockLocation = "";

    private PriceAskProviderDAO priceAskProviderDAO = null;
    
    private StockWorkDAO stockWorkDAO = null;

    private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;

    private DhZjbDAO dhZjbDAO = null;

    private DepotDAO depotDAO = null;

    private DepotpartDAO depotpartDAO = null;

    private StockItemArrivalDAO stockItemArrivalDAO = null;

    private PurchaseXqqrDAO purchaseXqqrDAO = null;

    private SailConfigManager sailConfigManager = null;

    private PriceConfigDAO priceConfigDAO = null;

    private PriceConfigManager priceConfigManager = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#addBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.stock.bean.StockBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addStockBean(User user, StockBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        LocationBean lb = locationDAO.find(bean.getLocationId());

        if (lb == null)
        {
            throw new MYException("区域不存在");
        }

        // 修改为定长
        bean.setId(lb.getCode() + "_CG" + TimeTools.now("yyyyMMddHHmm")
                   + commonDAO.getSquenceString());

        bean.setStatus(StockConstant.STOCK_STATUS_INIT);

        if (StringTools.isNullOrNone(bean.getStafferId()))
        {
            bean.setStafferId(user.getStafferId());
        }

        // 保存采购主单据
        stockDAO.saveEntityBean(bean);
        
        if (bean.getMtype() == StockConstant.MANAGER_TYPE_COMMON2
        		|| bean.getMtype() == StockConstant.MANAGER_TYPE_COMMON3)
        {
        	bean.setMtype(StockConstant.MANAGER_TYPE_COMMON);
        }

        // 保存采购的产品
        List<StockItemBean> items = bean.getItem();

        for (StockItemBean stockItemBean : items)
        {
            stockItemBean.setStockId(bean.getId());

            if (OATools.getManagerFlag())
            {
                ProductBean product = productDAO.find(stockItemBean.getProductId());

                // 判断是否同类
                /*if ( !String.valueOf(bean.getMtype()).equals(product.getReserve4()))
                {
                    throw new MYException("产品的管理类型和采购的管理类型不一致");
                }*/
                stockItemBean.setMtype(MathTools.parseInt(product.getReserve4()));
                
            }

            // stockItemBean.setMtype(bean.getMtype());

            stockItemDAO.saveEntityBean(stockItemBean);

            //2015/12/11 默认生成到货信息
            StockItemArrivalBean arrivalBean = new StockItemArrivalBean();
            BeanUtil.copyProperties(arrivalBean, stockItemBean);
            arrivalBean.setId("");
            this.stockItemArrivalDAO.saveEntityBean(arrivalBean);
        }

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    @Override
    public boolean addStockArrivalBean(User user, List<StockItemArrivalBean> list) throws MYException {
        for (StockItemArrivalBean bean : list){
            this.stockItemArrivalDAO.saveEntityBean(bean);
        }
        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    @Override
    public boolean updateStockArrivalBean(User user, StockBean stockBean) throws MYException {
        _logger.info(stockBean.getId() + "***updateStockArrivalBean***");

        List<StockItemArrivalBean> arrivalBeans = stockBean.getArrivalBeans();
        //remove original
        List<StockItemArrivalBean> stockItemArrivalBeans = this.stockItemArrivalDAO.queryEntityBeansByFK(stockBean.getId());
        if (!ListTools.isEmptyOrNull(stockItemArrivalBeans)){
            for (StockItemArrivalBean bean : stockItemArrivalBeans){
                if (this.contains(arrivalBeans, bean)){
                    continue;
                }else{
                    this.stockItemArrivalDAO.deleteEntityBean(bean.getId());
                }
            }
        }

        for (StockItemArrivalBean bean : arrivalBeans){
            if (StringTools.isNullOrNone(bean.getId())){
                this.stockItemArrivalDAO.saveEntityBean(bean);
            } else{
                StockItemArrivalBean stockItemArrivalBean = this.stockItemArrivalDAO.find(bean.getId());
                if (stockItemArrivalBean!= null){
                    stockItemArrivalBean.setProductId(bean.getProductId());
                    stockItemArrivalBean.setAmount(bean.getAmount());
                    stockItemArrivalBean.setDeliveryDate(bean.getDeliveryDate());
                    stockItemArrivalBean.setArrivalDate(bean.getArrivalDate());
                    this.stockItemArrivalDAO.updateEntityBean(stockItemArrivalBean);
                }
            }

            this.addLog(user, stockBean.getId(), StockConstant.STOCK_STATUS_STOCKMANAGERPASS, stockBean, -1, bean.toString(), null);
        }


        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private boolean contains(List<StockItemArrivalBean> stockItemArrivalBeans, StockItemArrivalBean bean){
        for (StockItemArrivalBean arrivalBean: stockItemArrivalBeans){
            if (bean.getId().equals(arrivalBean.getId())){
                return true;
            }
        }
        return false;
    }

    /*
             * (non-Javadoc)
             *
             * @see com.china.center.oa.stock.manager.StockManager#delStockBean(com.center.china.osgi.publics.User,
             *      java.lang.String)
             */
    @Transactional(rollbackFor = MYException.class)
    public boolean delStockBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        if ( !user.getId().equals(sb.getUserId()))
        {
            throw new MYException("没有权限");
        }

        if (sb.getStatus() != StockConstant.STOCK_STATUS_REJECT
            && sb.getStatus() != StockConstant.STOCK_STATUS_INIT)
        {
            throw new MYException("采购单不存在初始或者驳回状态不能删除");
        }

        // 更新采购主单据
        stockDAO.deleteEntityBean(id);

        // 先删除
        stockItemDAO.deleteEntityBeansByFK(id);

        flowLogDAO.deleteEntityBeansByFK(id);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#endStock(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = MYException.class)
    public String endStock(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        String fullId = "";

        StockBean bean = stockDAO.find(id);

        if (bean == null)
        {
            throw new MYException("采购单不存在");
        }

        // 结束和和经理通过都是可以结束采购单的
        if (bean.getStatus() != StockConstant.STOCK_STATUS_END
            && bean.getStatus() != StockConstant.STOCK_STATUS_STOCKMANAGERPASS)
        {
            throw new MYException("采购单不存在采购中,不能结束采购");
        }

        // 更新状态并且记录日志
        updateStockStatus(user, id, StockConstant.STOCK_STATUS_LASTEND,
            PublicConstant.OPRMODE_PASS, "采购结束");

        // 更新发货备注
        stockDAO.updateConsign(id, reason);

        return fullId;
    }

    /**
     * 更新采购单的状态
     * 
     * @param user
     * @return
     * @throws MYException
     */
    private boolean updateStockStatus(final User user, final String id, int nextStatus,
                                      int oprMode, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean old = stockDAO.find(id);

        if (old == null)
        {
            throw new MYException("采购单不存在");
        }

        // 更新采购主单据
        stockDAO.updateStatus(id, nextStatus);

        // 只有驳回
        if (oprMode == PublicConstant.OPRMODE_REJECT)
        {
            // 回退信息
            old.setWillDate(0);

            old.setDutyId("");

            old.setInvoice(0);

            old.setInvoiceType("");

            old.setExceptStatus(StockConstant.EXCEPTSTATUS_COMMON);

            old.setStatus(nextStatus);

            // 更新采购主单据
            stockDAO.updateEntityBean(old);
        }

        addLog(user, id, nextStatus, old, oprMode, reason, null);

        return true;
    }

    /**
     * 增加log
     * 
     * @param user
     * @param id
     * @param status
     * @param sb
     */
    private void addLog(final User user, final String id, int status, StockBean sb, int oprMode,
                        String reason, String reserved1)
    {
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setLogTime(TimeTools.now());

        log.setFullId(id);

        // 操作类型
        log.setOprMode(oprMode);

        log.setPreStatus(sb.getStatus());

        log.setAfterStatus(status);

        log.setDescription(reason);

        log.setReserved1(reserved1);

        flowLogDAO.saveEntityBean(log);
    }

    private void addLog2(final String id, int preStatus, int afterStatus, int oprMode,String reason)
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

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#findStockVO(java.lang.String)
     */
    public StockVO findStockVO(String id)
    {
        StockVO vo = stockDAO.findVO(id);

        List<StockItemVO> itemVO = stockItemDAO.queryEntityVOsByFK(id);
        List<StockItemArrivalVO> arrivalVOs = stockItemArrivalDAO.queryEntityVOsByFK(id);

        Map divMap = new HashMap();
        Map outTimeMap = new HashMap();

        //2014/12/14 查询商品对应的入库数量总数
        //2016/4/12 commented! totalWarehouseNum will be saved to DB when fetch product by arrival bean
//        if (!ListTools.isEmptyOrNull(itemVO)){
////            ConditionParse con = new ConditionParse();
////            con.addCondition("OutBean.description","like","%"+id+"%");
////            con.addIntCondition("OutBean.status","=",3);
//
//
////            con.addCondition("left join t_center_out OutBean on BaseBean.outId=OutBean.fullid where OutBean.description like '%'"+id+"%'");
//            for (StockItemVO item : itemVO){
//                int totalWarehouseNum = 0;
//                String outId = item.getRefOutId();
//                if (!StringTools.isNullOrNone(outId)){
//                    ConditionParse con = new ConditionParse();
//                    con.addCondition("BaseBean.productId","=",item.getProductId());
//                    con.addCondition("BaseBean.outId","=",item.getRefOutId());
//
////                List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByCondition(con);
//                    List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByCondition(con);
//                    if (!ListTools.isEmptyOrNull(baseBeans)){
//                        _logger.info("item.getProductId()**"+item.getProductId()+"***item.getRefOutId()***"+item.getRefOutId()+"***baseBeans size***"+baseBeans.size());
//
//                        for (BaseBean base: baseBeans){
//                            totalWarehouseNum += base.getAmount();
////                         OutBean out = this.outDAO.find(base.getOutId());
////                         if (out!= null){
////                            outTimeMap.put(base.getId(), out.getOutTime());
////                         }
//                        }
//
//                    }
//                }
//
//                item.setTotalWarehouseNum(totalWarehouseNum);
////                     item.setBaseBeans(baseBeans);
////                     divMap.put(item.getId(),
////                             this.createTable(baseBeans, outTimeMap));
//                String msg = item.getId()+"******************totalWarehouseNum******************"+totalWarehouseNum;
//                System.out.println(msg);
//                _logger.info(msg);
//             }
//        }

        vo.setDivMap(divMap);
        vo.setItemVO(itemVO);
        vo.setStockItemArrivalVOs(arrivalVOs);

        if (StringTools.isNullOrNone(vo.getOwerName()))
        {
            vo.setOwerName("公共");
        }

        return vo;
    }

//    private String createTable(List<BaseBean> list, Map outTimeMap)
//    {
//        StringBuffer buffer = new StringBuffer();
//
//        buffer.append("<table width='100%' border='0' cellspacing='1'>");
//        buffer.append("<tr align='center' class='content0'>");
//        buffer.append("<td width='25%' align='center'>采购单号</td>");
//        buffer.append("<td width='50%' align='center'>品名</td>");
//        buffer.append("<td width='10%' align='center'>入库数量</td>");
//        buffer.append("<td width='15%' align='center'>入库日期</td>");
//
//        int index = 0;
//        String cls = null;
//        for (BaseBean bean : list)
//        {
//            if (index % 2 == 0)
//            {
//                cls = "content1";
//            }
//            else
//            {
//                cls = "content2";
//            }
//
//            buffer.append("<tr class='" + cls + "'>");
//
//            buffer.append("<td width='25%' align='center'>"
//                    + bean.getOutId() + "</td>");
//            buffer.append("<td width='50%' align='center'>"
//                    + StringTools.getLineString(bean.getProductName()) + "</td>");
//            buffer.append("<td width='10%' align='center'>" + bean.getAmount() + "</td>");
//            buffer.append(" <td width='15%' align='center'>" + outTimeMap.get(bean.getId()) + "</td>");
//
//            index++ ;
//        }
//
//        buffer.append("</table>");
//
//        return StringTools.getLineString(buffer.toString());
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#passStock(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @Transactional(rollbackFor = {MYException.class, DataAccessException.class})
    public boolean passStock(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockVO sb = stockDAO.findVO(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        int nextStatus = getNextStatus(user, sb);

        List<StockItemVO> itemList = stockItemDAO.queryEntityVOsByFK(id);

        double total = 0.0d;

        // 如果是结束需要验证是否是外网询价
        checkEndStock(sb, nextStatus, itemList);

        // 待采购拿货 需要全部的item都询价结束
        if (nextStatus == StockConstant.STOCK_STATUS_STOCKMANAGERPASS
            || nextStatus == StockConstant.STOCK_STATUS_STOCKPASS 
            || nextStatus == StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO
            || nextStatus == StockConstant.STOCK_STATUS_STOCKPASS_CEO)
        {
            for (StockItemBean stockItemBean : itemList)
            {
                //2015/10/24 不再检查询价状态
//                if (stockItemBean.getStatus() != StockConstant.STOCK_ITEM_STATUS_ASK)
//                {
//                    throw new MYException("采购单下存在没有询价的产品,不能通过");
//                }

                total += stockItemBean.getTotal();
            }

            stockDAO.updateTotal(id, total);
        }

        String reason = "";

        // 区域经理通过的时候,要是代销采购直接跳过询价
        if (nextStatus == StockConstant.STOCK_STATUS_MANAGERPASS)
        {
            // 代销采购
            if (sb.getStype() == StockConstant.STOCK_STYPE_ISAIL)
            {
                nextStatus = StockConstant.STOCK_STATUS_PRICEPASS;

                total = 0.0d;

                // 这里直接处理无需询价的逻辑
                for (StockItemBean stockItemBean : itemList)
                {
                    ProductBean product = productDAO.find(stockItemBean.getProductId());

                    if (StringTools.isNullOrNone(product.getMainProvider()))
                    {
                        throw new MYException("产品[%s]没有主供应商,不能通过", product.getName());
                    }

                    stockItemBean.setProviderId(product.getMainProvider());

                    stockItemBean.setStatus(StockConstant.STOCK_ITEM_STATUS_ASK);

                    // 自动拿货
                    stockItemBean.setFechProduct(StockConstant.STOCK_ITEM_FECH_YES);

                    stockItemBean.setPrice(stockItemBean.getPrePrice());

                    stockItemBean.setTotal(stockItemBean.getPrice() * stockItemBean.getAmount());

                    stockItemDAO.updateEntityBean(stockItemBean);

                    total += stockItemBean.getTotal();
                }

                stockDAO.updateTotal(id, total);

                reason = "代销采购无需询价";
            }
            else
            {
                // 直接生成询价单(每个采购项一个)
                //2015/10/24 采购提交后不再生成询价单
                for (StockItemBean stockItemBean : itemList)
                {
                    PriceAskBean autoAskBean = setAutoAskBean(sb, stockItemBean, user);

                    priceAskManager.addPriceAskBeanWithoutTransactional(user, autoAskBean);
                }
                _logger.info("Do not create PriceAskBean now!");
            }
        }

        // 采购主管通过后到 单比金额大于5万，或者是选择价格不是最低，需要采购经理审核
        // 0 - 3W 
        // 3 - 50W
        // 50 - 100W
        // 100W - 
        if (sb.getStatus() == StockConstant.STOCK_STATUS_PRICEPASS)
        {
            //2015/12/2 检查是否维护到货信息
            List<StockItemArrivalBean> stockItemArrivalBeans = this.stockItemArrivalDAO.queryEntityBeansByFK(sb.getId());
            if (ListTools.isEmptyOrNull(stockItemArrivalBeans)){
                throw new MYException("采购单没有维护到货信息,不能通过");
            }
            // 检查是否询价
            //2015/10/24 取消检查询价
//            for (StockItemBean stockItemBean : itemList)
//            {
//                if (stockItemBean.getStatus() != StockConstant.STOCK_ITEM_STATUS_ASK)
//                {
//                    throw new MYException("采购单下存在没有询价的产品,不能通过");
//                }
//            }

//            if ( !checkMin(itemList))
//            {
//                stockDAO.updateExceptStatus(id, StockConstant.EXCEPTSTATUS_EXCEPTION_MIN);
//
//                nextStatus = StockConstant.STOCK_STATUS_STOCKPASS_CEO;
//            }

//            StringBuilder sbStr = new StringBuilder();
//
//            if ( !checkItemMoney(itemList, sbStr))
//            {
//                stockDAO.updateExceptStatus(id, StockConstant.EXCEPTSTATUS_EXCEPTION_MONEY);
//
//                // 获得董事长的人员
//                List<StafferBean> sbList = stafferDAO
//                    .queryStafferByAuthId(AuthConstant.STOCK_NOTICE_CHAIRMA);
//
//                for (StafferBean stafferBean : sbList)
//                {
//                    sendSMSInner(user, stafferBean, id, sb, sbStr.toString());
//                }
//
//                nextStatus = StockConstant.STOCK_STATUS_STOCKPASS_CEO;
//            }
        }

        // 如果是采购代销的采购主管通过直接到
        if (nextStatus == StockConstant.STOCK_STATUS_STOCKMANAGERPASS
        		|| nextStatus == StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO
        		|| nextStatus == StockConstant.STOCK_STATUS_STOCKPASS_CEO)
        {
            for (StockItemVO stockItemVO : itemList)
            {
                // 这里先校验是否已经配置
                if (StringTools.isNullOrNone(stockItemVO.getDutyId()))
                {
                    throw new MYException("请先配置税务属性");
                }
            }

            // 代销采购
            if (sb.getStype() == StockConstant.STOCK_STYPE_ISAIL)
            {
                // 直接到待结束采购
                nextStatus = StockConstant.STOCK_STATUS_END;
            }
        }

        // 当结束的时候 把各个item的状态标志成结束(给询价作为参考)
        if (nextStatus == StockConstant.STOCK_STATUS_END)
        {
            // 检验是否都已经入库
            boolean all = true;

            for (StockItemBean stockItemBean : itemList)
            {
                int fetched = stockItemBean.getFechProduct();
                if (fetched == StockConstant.STOCK_ITEM_FECH_NO ||
                        fetched == StockConstant.STOCK_ITEM_FECH_PART)
                {
                    all = false;

                    break;
                }
            }

            if ( !all)
            {
                throw new MYException("还没有全部拿货不能通过");
            }

            for (StockItemBean stockItemBean2 : itemList)
            {
                // 更新状态
                stockItemBean2.setStatus(StockConstant.STOCK_ITEM_STATUS_END);

                // 更新记录时间
                stockItemBean2.setLogTime(TimeTools.now());

                stockItemDAO.updateEntityBean(stockItemBean2);
            }
        }

        // 生成采购付款申请
        if (nextStatus == StockConstant.STOCK_STATUS_STOCKMANAGERPASS)
        {
        	// 采购入库
            Collection<StockListener> listenerMapValues = this.listenerMapValues();
            for (StockListener stockListener : listenerMapValues)
            {
            	for (StockItemVO item : itemList)
            	{
                    stockListener.onWaitFetchStock(user, sb, item);
            	}
            }
        }
        
        // 处理外网询价的特殊流程
        if (nextStatus == StockConstant.STOCK_STATUS_MANAGERPASS
            && sb.getType() == PriceConstant.PRICE_ASK_TYPE_NET)
        {
            // 直接到(采购主管)
            nextStatus = StockConstant.STOCK_STATUS_PRICEPASS;

            reason = "";
        }

        updateStockStatus(user, id, nextStatus, PublicConstant.OPRMODE_PASS, reason);

        if(!StringTools.isNullOrNone(sb.getBjNo())){
            this.purchaseBjDAO.updateIsUsed(sb.getBjNo(), 1);
        }

        return true;
    }

    /**
     * 自动生成采购询价
     * 
     * @param stock
     * @param item
     * @param user
     */
    private PriceAskBean setAutoAskBean(StockBean stock, StockItemBean item, User user)
    {
        PriceAskBean bean = new PriceAskBean();

        bean.setId(SequenceTools.getSequence("ASK", 5));

        bean.setProductId(item.getProductId());

        bean.setAmount(item.getAmount());

        bean.setSrcamount(item.getAmount());

        // 默认是内外网询价的
        bean.setType(PriceConstant.PRICE_ASK_TYPE_BOTH);

        if (stock.getMode() == StockConstant.STOCK_MODE_SAIL)
        {
            bean.setSrc(PriceConstant.PRICE_ASK_SRC_STOCK);
        }
        else
        {
            bean.setSrc(PriceConstant.PRICE_ASK_SRC_MAKE);
        }

        bean.setRefStock(stock.getId());

        bean.setAreaId(stock.getAreaId());

        bean.setUserId(user.getId());

        bean.setLogTime(TimeTools.now());

        bean.setStatus(PriceConstant.PRICE_COMMON);

        bean.setLocationId(user.getLocationId());

        bean.setDescription(stock.getId() + "自动生成的询价单");

        ProductBean product = productDAO.find(bean.getProductId());

        if (product != null)
        {
            bean.setProductType(product.getType());
        }

        bean.setUserId(stock.getUserId());

        bean.setLocationId(stock.getLocationId());

        bean.setProcessTime(TimeTools.getDateString(1, TimeTools.LONG_FORMAT));

        bean.setAskDate(TimeTools.getDateString(1, "yyyyMMdd"));

        bean.setSrcamount(bean.getAmount());

        bean.setStockMode(stock.getMode());

        return bean;
    }

    /**
     * 检查采购的是否是最小的价格
     * 
     * @param item
     * @return
     */
    private boolean checkMin(List<StockItemVO> item)
    {
        List<PriceAskProviderBean> ppbs = null;

        for (StockItemBean stockItemBean : item)
        {
            ppbs = priceAskProviderDAO.queryEntityBeansByFK(stockItemBean.getId());

            double min = Integer.MAX_VALUE;
            for (PriceAskProviderBean priceAskProviderBean : ppbs)
            {
                min = Math.min(priceAskProviderBean.getPrice(), min);
            }

            if (stockItemBean.getPrice() > min)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查采购的单比价格有没有超过5万的
     * 
     * @param item
     * @return
     */
    private boolean checkItemMoney(List<StockItemVO> item, StringBuilder sbStr)
    {
        int max = parameterDAO.getInt(SysConfigConstant.STOCK_MAX_SINGLE_MONEY);

        for (StockItemVO stockItemBean : item)
        {
            if (stockItemBean.getTotal() >= max)
            {
                sbStr.append("[" + stockItemBean.getProductName() + "]的采购金额为:"
                             + MathTools.formatNum(stockItemBean.getTotal()) + ",请注意");

                return false;
            }
        }

        return true;
    }
    
    /**
     * 0 - 3
     * 3 - 50
     * 50 - 100
     * 100 - 
     * @param stock
     * @return
     */
    private int checkMoney(StockBean stock)
    {
        if (stock.getTotal() <= 30000) {
        	return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;
        } else if (stock.getTotal() > 30000 && stock.getTotal() <= 500000) {
        	return StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO;
        } else if (stock.getTotal() > 500000 && stock.getTotal() <= 1000000) {
        	return StockConstant.STOCK_STATUS_STOCKPASS_CEO;
        } else {
        	return StockConstant.STOCK_STATUS_STOCKPASS;
        }
    }

    /**
     * checkSubmit
     * 
     * @param sb
     * @param nextStatus
     * @param itemList
     * @throws MYException
     */
    private void checkEndStock(StockBean sb, int nextStatus, List<StockItemVO> itemList)
        throws MYException
    {
        if (nextStatus == StockConstant.STOCK_STATUS_END
            && sb.getType() == PriceConstant.PRICE_ASK_TYPE_NET)
        {
            // 需要校验数量是
            for (StockItemBean iitem : itemList)
            {
                if (StringTools.isNullOrNone(iitem.getPriceAskProviderId()))
                {
                    throw new MYException("数据错误,请重新操作");
                }

                // 事务内已经被使用的
                int sum = stockItemDAO.sumNetProductByPid(iitem.getPriceAskProviderId());

                PriceAskProviderBeanVO ppb = priceAskProviderDAO.findVO(iitem
                    .getPriceAskProviderId());

                if (ppb == null)
                {
                    throw new MYException("数据错误,请重新操作");
                }

                if (ppb.getSupportAmount() < sum)
                {
                    throw new MYException(
                        "外网采购中供应商[%s]提供的产品[%s]数量只有%d已经使用了%d,你请求的采购数量[%d]已经超出(可能其他业务员已经采购一空)", ppb
                            .getProviderName(), ppb.getProductName(), ppb.getSupportAmount(),
                        (sum - iitem.getAmount()), iitem.getAmount());
                }
            }
        }
    }

    /**
     * 获得下一个状态
     * 
     * @param user
     * @param stock
     * @return
     * @throws MYException
     */
    private int getNextStatus(User user, StockBean stock)
        throws MYException
    {
    	int current = stock.getStatus();
    	
        if (current == StockConstant.STOCK_STATUS_INIT)
        {
            return StockConstant.STOCK_STATUS_MANAGERPASS;
        }

        if (current == StockConstant.STOCK_STATUS_REJECT)
        {
            return StockConstant.STOCK_STATUS_MANAGERPASS;
        }

//        if (current == StockConstant.STOCK_STATUS_SUBMIT)
//        {
//            return StockConstant.STOCK_STATUS_MANAGERPASS;
//        }

        if (current == StockConstant.STOCK_STATUS_MANAGERPASS)
        {
            if (AuthHelper.containAuth(user, AuthConstant.STOCK_MANAGER_PASS))
            {
                return StockConstant.STOCK_STATUS_PRICEPASS;
            }
            else
            {
                throw new MYException("没有权限");
            }
        }

        // 这里是采购主管的操作(如果没有异常忽略采购经理)
        if (current == StockConstant.STOCK_STATUS_PRICEPASS)
        {
        	if (checkMoney(stock) == StockConstant.STOCK_STATUS_STOCKMANAGERPASS) {
        		return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;	
        	} else {
        		return StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO;
        	}
        }

        if (current == StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO)
        {
        	if (checkMoney(stock) == StockConstant.STOCK_STATUS_STOCKPASS_DUTYCEO) {
        		return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;
        	} else {
        		return StockConstant.STOCK_STATUS_STOCKPASS_CEO;
        	}
        }
        
        if (current == StockConstant.STOCK_STATUS_STOCKPASS_CEO)
        {
        	if (checkMoney(stock) == StockConstant.STOCK_STATUS_STOCKPASS_CEO) {
        		return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;
        	} else {
        		return StockConstant.STOCK_STATUS_STOCKPASS;
        	}
        }
        
        if (current == StockConstant.STOCK_STATUS_STOCKPASS)
        {
            if (AuthHelper.containAuth(user, AuthConstant.STOCK_NOTICE_CHAIRMA))
            {
                return StockConstant.STOCK_STATUS_STOCKMANAGERPASS;
            }
            else
            {
                throw new MYException("没有权限");
            }
        }

        if (current == StockConstant.STOCK_STATUS_STOCKMANAGERPASS)
        {
            return StockConstant.STOCK_STATUS_END;
        }

        return StockConstant.STOCK_STATUS_INIT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#rejectStock(com.center.china.osgi.publics.User,
     *      java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectStock(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        if (sb.getStatus() == StockConstant.STOCK_STATUS_REJECT
            || sb.getStatus() == StockConstant.STOCK_STATUS_INIT
            || sb.getStatus() == StockConstant.STOCK_STATUS_END
            || sb.getStatus() == StockConstant.STOCK_STATUS_LASTEND)
        {
            throw new MYException("采购单状态错误");
        }

        int nextStatus = StockConstant.STOCK_STATUS_REJECT;

        recoverStockItemAsk(id);

        if(!StringTools.isNullOrNone(sb.getBjNo())){
            this.purchaseBjDAO.updateIsUsed(sb.getBjNo(), 0);
        }

        return updateStockStatus(user, id, nextStatus, PublicConstant.OPRMODE_REJECT, reason);
    }

    /**
     * 恢复采购询价的初始状态
     * 
     * @param id
     */
    private void recoverStockItemAsk(final String id)
    {
        List<StockItemBean> item = stockItemDAO.queryEntityBeansByFK(id);

        // 删除询价
        for (StockItemBean stockItemBean : item)
        {
            stockItemBean.setProviderId("");

            stockItemBean.setPriceAskProviderId("");

            stockItemBean.setStatus(StockConstant.STOCK_ITEM_STATUS_INIT);

            stockItemBean.setPrice(0.0d);

            stockItemBean.setTotal(0.0d);

            // 先修改item的一些值
            stockItemDAO.updateEntityBean(stockItemBean);

            // 删除先前的询价
            priceAskProviderDAO.deleteEntityBeansByFK(stockItemBean.getId());
        }

        // 删除自动生成的询价单
        List<PriceAskBean> refAskList = priceAskDAO.queryEntityBeansByFK(id);

        for (PriceAskBean priceAskBean : refAskList)
        {
            // 删除具体的报价
            priceAskProviderDAO.deleteEntityBeansByFK(priceAskBean.getId());
        }

        // 删除自动询价单
        priceAskDAO.deleteEntityBeansByFK(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#rejectStockToAsk(com.center.china.osgi.publics.User,
     *      java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean rejectStockToAsk(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        // 只有在询价员通过下才能驳回到询价员
        if (sb.getStatus() != StockConstant.STOCK_STATUS_PRICEPASS)
        {
            throw new MYException("采购单状态错误");
        }

        if ( !AuthHelper.containAuth(user, AuthConstant.STOCK_INNER_STOCK_PASS))
        {
            throw new MYException("不能操作");
        }

        recoverStockItemAsk(id);

        return updateStockStatus(user, id, StockConstant.STOCK_STATUS_MANAGERPASS,
            PublicConstant.OPRMODE_REJECT, reason);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#stockItemAsk(com.china.center.oa.stock.bean.StockItemBean)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean stockItemAsk(StockItemBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(bean);

        bean.setStatus(StockConstant.STOCK_ITEM_STATUS_ASK);

        bean.setTotal(bean.getPrice() * bean.getAmount());

        // 先修改item的一些值
        stockItemDAO.updateEntityBean(bean);

        // 删除先前的询价
        priceAskProviderDAO.deleteEntityBeansByFK(bean.getId());

        for (PriceAskProviderBean priceAskProviderBean : bean.getAsks())
        {
            priceAskProviderDAO.saveEntityBean(priceAskProviderBean);
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#stockItemAskChange(java.lang.String, java.lang.String)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean stockItemAskChange(String itemId, String providerId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(itemId, providerId);

        PriceAskProviderBean bean = priceAskProviderDAO.findBeanByAskIdAndProviderId(itemId,
                providerId, PriceConstant.PRICE_ASK_TYPE_INNER);

        if (bean == null)
        {
            throw new MYException("系统错误");
        }

        StockItemBean item = stockItemDAO.find(itemId);

        if (item == null)
        {
            throw new MYException("系统错误");
        }

        item.setProviderId(providerId);

        item.setPrice(bean.getPrice());

        item.setTotal(bean.getPrice() * item.getAmount());

        // 更新item
        stockItemDAO.updateEntityBean(item);

        List<StockItemBean> items = stockItemDAO.queryEntityBeansByFK(item.getStockId());

        double total = 0.0d;

        for (StockItemBean stockItemBean : items)
        {
            if (stockItemBean.getStatus() != StockConstant.STOCK_ITEM_STATUS_ASK)
            {
                throw new MYException("采购单下存在没有询价的产品,不能通过");
            }

            total += stockItemBean.getTotal();
        }

        // 更新采购单据的总金额
        stockDAO.updateTotal(item.getStockId(), total);

        return true;
    }


    /**
     * 2015/12/5 根据到货信息拿货
     * @param user
     * @param arrivalItemId
     * @param depotpartId
     * @param warehouseNum
     * @param toBeWarehouse
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = {MYException.class})
    @Override
    public boolean fetchProductByArrivalBean(User user, String arrivalItemId, String depotpartId,
                                             int warehouseNum, int toBeWarehouse,
                                             String demandQRId) throws MYException {
        StockItemArrivalVO item = this.stockItemArrivalDAO.findVO(arrivalItemId);

        if (item == null)
        {
            throw new MYException("到货行不存在："+arrivalItemId);
        }

        String stockId = item.getStockId();
        StockBean stock = stockDAO.findVO(stockId);

        _logger.info("fetchProduct with stockId:" + stockId + ";itemId:" + arrivalItemId + ";depotpartId:" + depotpartId +
                ";warehouseNum:" + warehouseNum + ";toBeWarehouse:" + toBeWarehouse);

        if (stock == null)
        {
            throw new MYException("采购单不存在："+stockId);
        }

        //2015/12/6 检查拿货数量
        if (item.getFechProduct() == StockConstant.STOCK_ITEM_FECH_YES)
        {
            _logger.info(arrivalItemId+" already fetched****");
            throw new MYException("已经拿货");
        } else if (warehouseNum > toBeWarehouse){
            //本次入库数量大于待入库数量
            _logger.error(arrivalItemId+" 本次入库数量大于待入库数量");
            throw new MYException("本次入库数量大于待入库数量");
        } else if (warehouseNum == toBeWarehouse){
            //如果本次入库数量==待入库数量，则本产品入库结束
            item.setFechProduct(StockConstant.STOCK_ITEM_FECH_YES);
            _logger.info(arrivalItemId + " set to STOCK_ITEM_FECH_YES****");
        } else{
            item.setFechProduct(StockConstant.STOCK_ITEM_FECH_PART);
        }

        try{
            item.setDepotpartId(depotpartId);
            item.setTotalWarehouseNum(warehouseNum+item.getTotalWarehouseNum());

            // 更新item
            this.stockItemArrivalDAO.updateEntityBean(item);

            //2016/4/12 TODO 更新采购行已入库数量
            // 根据productId和providerId找到对应的采购行<productId,providerId>组合必须唯一
            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addCondition("productId","=", item.getProductId());
            conditionParse.addCondition("providerId", "=", item.getProviderId());
            List<StockItemBean> itemBeans = this.stockItemDAO.queryEntityBeansByCondition(conditionParse);
            if (!ListTools.isEmptyOrNull(itemBeans)){
                StockItemBean itemBean = itemBeans.get(0);
                itemBean.setTotalWarehouseNum(warehouseNum+itemBean.getTotalWarehouseNum());
                this.stockItemDAO.updateEntityBean(itemBean);
                _logger.info("***update stock item bean***"+itemBean);
            } else{
                _logger.warn("****not found stock item bean***"+item);
            }
            item.setWarehouseNum(warehouseNum);


            // 根据productId和demandQRId找到对应的需求采购确认行,<productId,demandQRId>组合必须唯一
            if (!StringTools.isNullOrNone(demandQRId)){
                ConditionParse conditionParse1 = new ConditionParse();
                conditionParse1.addCondition("demandQRId","=", demandQRId);
                conditionParse1.addCondition("pjId", "=", item.getProductId());
                List<PurchaseXqqrBean> xqqrBeans = this.purchaseXqqrDAO.queryEntityBeansByCondition(conditionParse1);
                if (!ListTools.isEmptyOrNull(xqqrBeans)){
                    PurchaseXqqrBean itemBean = xqqrBeans.get(0);
                    itemBean.setNhNum(warehouseNum+itemBean.getNhNum());
                    this.purchaseXqqrDAO.updateEntityBean(itemBean);
                    _logger.info("***update PurchaseXqqrBean***"+itemBean);
                } else{
                    _logger.warn(demandQRId+"****not foundPurchaseXqqrBean***"+item);
                }
            }


            // 采购入库
            Collection<StockListener> listenerMapValues = this.listenerMapValues();
            for (StockListener stockListener : listenerMapValues){
                _logger.info("***listener***"+stockListener);
            }
            for (StockListener stockListener : listenerMapValues)
            {
                _logger.info("***create stock in bean***"+item);
                stockListener.onEndStockItem(user, stock, item);
            }
            List<StockItemArrivalBean> items = this.stockItemArrivalDAO.queryEntityBeansByFK(stockId);
            boolean all = true;

            //检查是否所有的到货行已入库完毕
            if (!ListTools.isEmptyOrNull(items)){
                _logger.info(stockId+" found item size*****"+items.size());
                for (StockItemBean stockItemBean : items)
                {
                    int fetched = stockItemBean.getFechProduct();
                    _logger.info(stockItemBean.getId()+"**stockItemBean.getFechProduct()*"+fetched);
                    //未拿货或部分拿货
                    if (fetched == StockConstant.STOCK_ITEM_FECH_NO || fetched == StockConstant.STOCK_ITEM_FECH_PART)
                    {
                        all = false;
                        break;
                    }
                }
            }


            addLog(user, item.getStockId(), stock.getStatus(), stock, PublicConstant.OPRMODE_PASS,
                    "询价人拿货数量:"+warehouseNum, item.getProductId());


            //所有商品结束入库时，将采购单状态置为“待结束采购”
            if (all)
            {
                _logger.info("****************修改成待结束采购****************");
                // 修改成待结束采购
                updateStockStatus(user, item.getStockId(), StockConstant.STOCK_STATUS_END,
                        PublicConstant.OPRMODE_PASS, "拿货结束");
            }else{
                _logger.info(item.getStockId()+"***stock buy Not finished yet***");
            }
        }catch(Exception e){
            e.printStackTrace();
            _logger.error("Exception when fetchProduct:",e);
            throw new MYException("系统错误,请确认操作:"+e.getMessage());
        }

        return true;
    }

    /*
             * (non-Javadoc)
             *
             * @see com.china.center.oa.stock.manager.StockManager#stockItemAskForNet(com.china.center.oa.stock.bean.StockItemBean,
             *      java.util.List)
             */
    @Transactional(rollbackFor = {MYException.class})
    public boolean stockItemAskForNet(StockItemBean oldItem, List<StockItemBean> newItemList)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(oldItem, newItemList);

        // 删除旧的采购申请
        stockItemDAO.deleteEntityBean(oldItem.getId());
        
        StockBean bean = stockDAO.find(oldItem.getStockId());
        
        if (bean.getMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
        {
        	bean.setWillDate(30);
        	bean.setInvoice(StockConstant.INVOICE_YES);
        }

        double total = 0.0d;
        for (StockItemBean stockItemBean : newItemList)
        {
            stockItemBean.setStatus(StockConstant.STOCK_ITEM_STATUS_ASK);
            stockItemBean.setTotal(stockItemBean.getAmount() * stockItemBean.getPrice());

            if (bean.getMtype() == PublicConstant.MANAGER_TYPE_MANAGER)
            {
            	// 直接完成税务配置  -- 不用再单独录入，直接通过
            	stockItemBean.setDutyId(PublicConstant.MANAGER_DUTY_ID);
            	stockItemBean.setInvoiceType("");
            }

            total += stockItemBean.getTotal();
        }

        stockItemDAO.saveAllEntityBeans(newItemList);

        stockDAO.updateTotal(oldItem.getStockId(), total);
        
        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateStockNearlyPayDate(User user, String id, String nearlyPayDate)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        StockBean sb = stockDAO.find(id);

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        // TEMPIMPL 采购防止用户ID为空
        if (StringTools.isNullOrNone(sb.getStafferId()))
        {
            sb.setStafferId(user.getStafferId());
        }

        sb.setNearlyPayDate(nearlyPayDate);

        // 更新采购主单据
        stockDAO.updateEntityBean(sb);

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.stock.manager.StockManager#updateStockBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.stock.bean.StockBean)
     */
    @Transactional(rollbackFor = {MYException.class})
    public boolean updateStockBean(User user, StockBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        StockBean sb = stockDAO.find(bean.getId());

        if (sb == null)
        {
            throw new MYException("采购单不存在");
        }

        if (sb.getStatus() != StockConstant.STOCK_STATUS_REJECT
            && sb.getStatus() != StockConstant.STOCK_STATUS_INIT)
        {
            throw new MYException("采购单不存在驳回状态不能修改");
        }

        if (StringTools.isNullOrNone(bean.getStafferId()))
        {
            bean.setStafferId(user.getStafferId());
        }

        // 更新采购主单据
        stockDAO.updateEntityBean(bean);

        // 先删除
        stockItemDAO.deleteEntityBeansByFK(bean.getId());
        stockItemArrivalDAO.deleteEntityBeansByFK(bean.getId());

        List<StockItemBean> items = bean.getItem();

        for (StockItemBean stockItemBean : items)
        {
            stockItemBean.setStockId(bean.getId());

            if (OATools.getManagerFlag())
            {
                ProductBean product = productDAO.find(stockItemBean.getProductId());

                // 判断是否同类
                if ( !String.valueOf(bean.getMtype()).equals(product.getReserve4()))
                {
                    throw new MYException("产品的管理类型和采购的管理类型不一致");
                }
            }

            stockItemBean.setMtype(bean.getMtype());

            stockItemDAO.saveEntityBean(stockItemBean);

            //2016/4/7 重新提交采购单后，默认重新生成到货信息
            StockItemArrivalBean arrivalBean = new StockItemArrivalBean();
            BeanUtil.copyProperties(arrivalBean, stockItemBean);
            arrivalBean.setId("");
            this.stockItemArrivalDAO.saveEntityBean(arrivalBean);
        }

        return true;
    }

    @Transactional(rollbackFor = {MYException.class})
    public boolean updateStockDutyConfig(User user, StockBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        StockBean old = stockDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("采购单不存在");
        }

        if (old.getStatus() != StockConstant.STOCK_STATUS_PRICEPASS)
        {
            throw new MYException("采购单不存在带采购主管审批状态不能配置");
        }

        old.setWillDate(bean.getWillDate());

        old.setInvoice(bean.getInvoice());

        // 更新采购主单据
        stockDAO.updateEntityBean(old);

        List<StockItemBean> items = bean.getItem();

        List<StockItemBean> oldItems = stockItemDAO.queryEntityBeansByFK(bean.getId());

        // 仅仅更新showID
        for (StockItemBean newItem : items)
        {
            for (StockItemBean oldItem : oldItems)
            {
                if (oldItem.getId().equals(newItem.getId()))
                {
                    oldItem.setShowId(newItem.getShowId());
                    oldItem.setDutyId(newItem.getDutyId());
                    oldItem.setInvoiceType(newItem.getInvoiceType());
                }
            }
        }

        stockItemDAO.updateAllEntityBeans(oldItems);
        
        return true;
    }

    public boolean payStockItemWithoutTransaction(User user, String stockItemId)
        throws MYException
    {
//        JudgeTools.judgeParameterIsNull(user, stockItemId);
        JudgeTools.judgeParameterIsNull(stockItemId);

        StockItemBean item = stockItemDAO.find(stockItemId);

        if (item == null)
        {
            throw new MYException("数据错误,请重新操作");
        }

        if (item.getPay() == StockConstant.STOCK_PAY_YES)
        {
            throw new MYException("已经付款,请重新操作");
        }

        stockItemDAO.updatePay(stockItemId, StockConstant.STOCK_PAY_YES);

        List<StockItemBean> itemList = stockItemDAO.queryEntityBeansByFK(item.getStockId());

        boolean allPay = true;

        for (StockItemBean stockItemBean : itemList)
        {
            if (stockItemBean.getPay() != StockConstant.STOCK_PAY_YES)
            {
                allPay = false;
                break;
            }
        }

        if (allPay)
        {
            // 修改采购单付款
            stockDAO.updatePayStatus(item.getStockId(), StockConstant.STOCK_PAY_YES);
        }

        return true;
    }

	@Transactional(rollbackFor = MYException.class)
	public boolean confirmProduct(User user, String itemId) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, itemId);
		
		StockItemBean item = stockItemDAO.find(itemId);
		
		if (null == item)
		{
			throw new MYException("数据错误");
		}
		
		stockItemDAO.updateExtraStatus(itemId, 1);
		
		List<StockItemBean> itemList = stockItemDAO.queryEntityBeansByFK(item.getStockId());
		
		int count = 0;
		for (StockItemBean each : itemList)
		{
			count += each.getExtraStatus();
		}
		
		if (count == itemList.size())
		{
			stockDAO.updateExtraStatus(item.getStockId(), 1);
		}
		
		return true;
	}
    
	@Transactional(rollbackFor = MYException.class)
	public boolean endProduct(User user, String id, String reason) throws MYException
	{
		JudgeTools.judgeParameterIsNull(user, id, reason);
		
		StockBean stock = stockDAO.find(id);
		
		if (null == stock)
		{
			throw new MYException("数据错误");
		}
		
		if (stock.getStatus() == StockConstant.STOCK_STATUS_LASTEND)
		{
			throw new MYException("已是采购结束态,不用异常结束");
		}
		
		List<StockItemBean> itemList = stockItemDAO.queryEntityBeansByFK(id);
		
		// checkAndUpdate status to end
        Collection<StockListener> listenerMapValues = this.listenerMapValues();
        
        for (StockListener stockListener : listenerMapValues)
        {
        	for (StockItemBean each : itemList)
        	{
        		stockListener.onCheckStockPay(user, id, each.getId(), reason);            		
        	}
        }
        
        updateStockStatus(user, id, StockConstant.STOCK_STATUS_LASTEND, PublicConstant.OPRMODE_PASS, "异常结束原因:" + reason);
		
		List<StockWorkBean> stockWorkList = stockWorkDAO.queryEntityBeansByFK(id);
		
		for (StockWorkBean each : stockWorkList)
		{
			if (each.getStatus() != StockConstant.STOCKWORK_STATUS_END)
			{
				each.setStatus(StockConstant.STOCKWORK_STATUS_END);
				each.setDescription(each.getDescription() + ";异常结束原因:" + reason);
				
				stockWorkDAO.updateEntityBean(each);
			}
		}
		
		_logger.info(user.getStafferName() + " 异常结束采购单:" + id);
		
		return true;
	}
	
    /**
     * sendSMSInner
     * 
     * @param user
     * @param sb
     * @param id
     * @param sbvo
     * @param message
     */
    private void sendSMSInner(User user, StafferBean sb, String id, StockVO sbvo, String message)
    {
        // send short message
        ShortMessageTaskBean sms = new ShortMessageTaskBean();

        sms.setId(commonDAO.getSquenceString20());

        sms.setFk(id);

        sms.setType(104);

        sms.setHandId(RandomTools.getRandomMumber(4));

        sms.setStatus(ShortMessageConstant.STATUS_INIT);

        sms.setMtype(ShortMessageConstant.MTYPE_ONLY_SEND);

        sms.setFktoken("0");

        sms.setMessage(sbvo.getUserName() + "发起的采购单" + message);

        sms.setReceiver(sb.getHandphone());

        sms.setStafferId(sb.getId());

        sms.setLogTime(TimeTools.now());

        // 24 hour
        sms.setEndTime(TimeTools.now(1));

        // internal
        sms.setSendTime(TimeTools.now());

        // add sms
        shortMessageTaskDAO.saveEntityBean(sms);
    }

    @Override
    @Transactional(rollbackFor = {MYException.class})
    public void dhDiaoboJob() {
        _logger.info("***dhDiaoboJob running***");
        List<DhZjbVO> dhZjbVOList = this.dhZjbDAO.queryDhInfo();
        if (!ListTools.isEmptyOrNull(dhZjbVOList)){
            _logger.info("***dhZjbVOList size***"+dhZjbVOList.size());
            DepotpartBean depotpart = depotpartDAO.findByUnique("不良品仓");

            for(DhZjbVO vo: dhZjbVOList){
                //合格数量调拨单
                OutBean outBean =  new OutBean();

                String depotId = vo.getSccgRkfx();

                outBean.setDescription("到货调拨JOB(合格),到货单号:"+vo.getDhNo());
                outBean.setType(OutConstant.OUT_TYPE_INBILL);
                outBean.setOutType(OutConstant.OUTTYPE_IN_MOVEOUT);

                String id = OutHelper.getAll(commonDAO.getSquence());
                String time = TimeTools.getStringByFormat(new Date(), "yyMMddHHmm");
                String flag = OutHelper.getSailHead(outBean.getType(), outBean.getOutType());

                String fullId = flag + time + id;
                outBean.setId(OutHelper.getOutId(id));
                outBean.setFullId(fullId);

                String stafferId = vo.getCreateUser();
                StafferBean stafferBean = this.stafferDAO.find(stafferId);
                if (stafferBean == null){
                    _logger.error("staffer not exists:"+stafferId);
                    continue;
                } else{
                    outBean.setIndustryId(stafferBean.getIndustryId());
                    outBean.setIndustryId2(stafferBean.getIndustryId2());
                    outBean.setIndustryId3(stafferBean.getIndustryId3());
                    // 增加职员的ID
                    outBean.setStafferId(vo.getCreateUser());
                    outBean.setStafferName(stafferBean.getName());
                }

                String now = TimeTools.now();
                String nowShort = TimeTools.now_short();
                outBean.setOutTime(nowShort);
                outBean.setLogTime(now);
                outBean.setChangeTime(now);

                outBean.setOperatorName("系统");
                outBean.setReserve1(OutConstant.MOVEOUT_DIAOBO);

                outBean.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);
                outBean.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);

                BaseBean baseBean = new BaseBean();

                baseBean.setId(commonDAO.getSquenceString());
                baseBean.setOutId(fullId);

                //目的仓库
                baseBean.setLocationId(depotId);
                DepotpartBean defaultOKDepotpart = depotpartDAO.findDefaultOKDepotpart(depotId);
                if (defaultOKDepotpart == null){
                    _logger.error("defaultOKDepotpart not found:"+depotId);
                    continue;
                } else{
                    baseBean.setDepotpartId(defaultOKDepotpart.getId());
                    baseBean.setDepotpartName(defaultOKDepotpart.getName());
                }

                String productId = vo.getProductId();
                ProductBean product = this.productDAO.find(productId);
                if (product == null){
                    _logger.error("No product found "+productId);
                    continue;
                }
                baseBean.setProductId(productId);
                baseBean.setProductName(product.getName());
                baseBean.setUnit("套");
                baseBean.setAmount(-vo.getZjHgAmount());

                StockItemBean stockItemBean = this.getStockItem(vo.getStockId(), productId);
                if (stockItemBean == null){
                    _logger.error(vo.getStockId()+" No stock item found:"+productId);
                    continue;
                } else{
                    baseBean.setPrice(stockItemBean.getPrice());
                    baseBean.setValue(baseBean.getAmount() * baseBean.getPrice());
                    baseBean.setCostPrice(stockItemBean.getPrice());
                    baseBean.setCostPriceKey(StorageRelationHelper
                            .getPriceKey(baseBean.getCostPrice()));
                }

                baseBean.setOwner("0");
                baseBean.setOwnerName("公共");
                baseBean.setMtype(PublicConstant.MANAGER_TYPE_COMMON);

                // 业务员结算价，总部结算价
                double sailPrice = product.getSailPrice();

                // 根据配置获取结算价
                List<PriceConfigBean> pcblist = priceConfigDAO.querySailPricebyProductId(product.getId());

                if (!ListTools.isEmptyOrNull(pcblist))
                {
                    PriceConfigBean cb = priceConfigManager.calcSailPrice(pcblist.get(0));

                    sailPrice = cb.getSailPrice();
                }

                // 获取销售配置
                SailConfBean sailConf = sailConfigManager.findProductConf(stafferBean,
                        product);

                // 总部结算价(产品结算价 * (1 + 总部结算率))
                baseBean.setPprice(sailPrice
                        * (1 + sailConf.getPratio() / 1000.0d));

                // 事业部结算价(产品结算价 * (1 + 总部结算率 + 事业部结算率))
                baseBean.setIprice(sailPrice
                        * (1 + sailConf.getIratio() / 1000.0d + sailConf
                        .getPratio() / 1000.0d));

                // 业务员结算价就是事业部结算价
                baseBean.setInputPrice(baseBean.getIprice());

                if (baseBean.getInputPrice() == 0)
                {
                    _logger.error(baseBean.getProductName() + " 业务员结算价不能为0");
                    continue;
                }

                // 仓库
                DepotBean depotBean = this.depotDAO.findByUnique("生产作业库");
                if (depotBean == null){
                    _logger.error("生产作业库 not exist!");
                    continue;
                }
                outBean.setLocation(depotBean.getId());
                outBean.setLocationId("999");
                outBean.setDestinationId(depotId);

                outBean.setCustomerId("99");
                outBean.setCustomerName("公共客户");
                outBean.setDepartment("公共部门");

                outBean.setDutyId("90201008080000000001");
                outBean.setPmtype(PublicConstant.MANAGER_TYPE_COMMON);

                outBean.setTotal(baseBean.getValue());
                outBean.setStatus(OutConstant.BUY_STATUS_PASS);
                outDAO.saveEntityBean(outBean);
                baseDAO.saveEntityBean(baseBean);
                this.addLog2(outBean.getFullId(),0, OutConstant.BUY_STATUS_PASS, 0,"提交");
                _logger.info("create out in dhDiaboJob "+outBean+"***with base bean***"+baseBean);

                if(depotpart!= null){
                    //不合格数量调拨单
                    OutBean outBean2 =  new OutBean();

                    outBean2.setDestinationId(depotpart.getId());
                    outBean2.setDescription("到货调拨JOB(不合格),到货单号:"+vo.getDhNo());

                    outBean2.setType(OutConstant.OUT_TYPE_INBILL);
                    outBean2.setOutType(OutConstant.OUTTYPE_IN_MOVEOUT);

                    String id2 = OutHelper.getAll(commonDAO.getSquence());
                    String fullId2 = flag + time + id2;
                    outBean2.setId(OutHelper.getOutId(id2));
                    outBean2.setFullId(fullId2);

                    outBean2.setIndustryId(stafferBean.getIndustryId());
                    outBean2.setIndustryId2(stafferBean.getIndustryId2());
                    outBean2.setIndustryId3(stafferBean.getIndustryId3());

                    // 增加职员的ID
                    outBean2.setStafferId(vo.getCreateUser());
                    outBean2.setStafferName(stafferBean.getName());

                    outBean2.setOutTime(nowShort);
                    outBean2.setLogTime(now);
                    outBean2.setChangeTime(now);

                    outBean2.setOperatorName("系统");
                    outBean2.setReserve1(OutConstant.MOVEOUT_DIAOBO);

                    outBean2.setCustomerId(CustomerConstant.PUBLIC_CUSTOMER_ID);
                    outBean2.setCustomerName(CustomerConstant.PUBLIC_CUSTOMER_NAME);

                    BaseBean baseBean2 = new BaseBean();

                    baseBean2.setId(commonDAO.getSquenceString());
                    baseBean2.setOutId(fullId2);

                    //目的仓库
                    baseBean2.setLocationId(depotpart.getLocationId());
                    baseBean2.setDepotpartId(depotpart.getId());
                    baseBean2.setDepotpartName(depotpart.getName());

                    baseBean2.setProductId(productId);
                    baseBean2.setProductName(product.getName());
                    baseBean2.setUnit("套");
                    baseBean2.setAmount(-vo.getZjBhgAmount());

                    baseBean2.setPrice(stockItemBean.getPrice());
                    baseBean2.setValue(baseBean2.getAmount() * baseBean2.getPrice());
                    baseBean2.setCostPrice(stockItemBean.getPrice());
                    baseBean2.setCostPriceKey(StorageRelationHelper
                            .getPriceKey(baseBean2.getCostPrice()));

                    baseBean2.setOwner("0");
                    baseBean2.setOwnerName("公共");
                    baseBean2.setMtype(PublicConstant.MANAGER_TYPE_COMMON);

                    // 总部结算价(产品结算价 * (1 + 总部结算率))
                    baseBean2.setPprice(sailPrice
                            * (1 + sailConf.getPratio() / 1000.0d));
                    // 事业部结算价(产品结算价 * (1 + 总部结算率 + 事业部结算率))
                    baseBean2.setIprice(sailPrice
                            * (1 + sailConf.getIratio() / 1000.0d + sailConf
                            .getPratio() / 1000.0d));
                    // 业务员结算价就是事业部结算价
                    baseBean2.setInputPrice(baseBean2.getIprice());

                    //仓库
                    outBean2.setLocation(depotpart.getLocationId());
                    outBean2.setLocationId("999");
                    outBean2.setDestinationId(depotpart.getId());

                    outBean2.setCustomerId("99");
                    outBean2.setCustomerName("公共客户");
                    outBean2.setDepartment("公共部门");

                    outBean2.setDutyId("90201008080000000001");
                    outBean2.setPmtype(PublicConstant.MANAGER_TYPE_COMMON);

                    outBean2.setTotal(baseBean2.getValue());
                    outBean2.setStatus(OutConstant.BUY_STATUS_PASS);
                    outDAO.saveEntityBean(outBean2);
                    baseDAO.saveEntityBean(baseBean2);
                    this.addLog2(outBean2.getFullId(),0, OutConstant.BUY_STATUS_PASS, 0,"提交");
                    _logger.info("create out2 in dhDiaboJob "+outBean2+"***with base bean2***"+baseBean2);
                }

                this.dhZjbDAO.updateProcessedFlag(vo.getId());
            }
        }
        _logger.info("***dhDiaoboJob finished***");
    }

    private StockItemBean getStockItem(String stockId, String productId){
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addWhereStr();
        conditionParse.addCondition("stockId","=",stockId);
        conditionParse.addCondition("productId","=", productId);
        List<StockItemBean> stockItemBeans = this.stockItemDAO.queryEntityBeansByCondition(conditionParse);
        if (ListTools.isEmptyOrNull(stockItemBeans)){
            return null;
        } else{
            return stockItemBeans.get(0);
        }
    }

    /**
     * @return the stockDAO
     */
    public StockDAO getStockDAO()
    {
        return stockDAO;
    }

    /**
     * @param stockDAO
     *            the stockDAO to set
     */
    public void setStockDAO(StockDAO stockDAO)
    {
        this.stockDAO = stockDAO;
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
     * @return the shortMessageTaskDAO
     */
    public ShortMessageTaskDAO getShortMessageTaskDAO()
    {
        return shortMessageTaskDAO;
    }

    /**
     * @param shortMessageTaskDAO
     *            the shortMessageTaskDAO to set
     */
    public void setShortMessageTaskDAO(ShortMessageTaskDAO shortMessageTaskDAO)
    {
        this.shortMessageTaskDAO = shortMessageTaskDAO;
    }

    /**
     * @return the stockLocation
     */
    public String getStockLocation()
    {
        return stockLocation;
    }

    /**
     * @param stockLocation
     *            the stockLocation to set
     */
    public void setStockLocation(String stockLocation)
    {
        this.stockLocation = stockLocation;
    }

    /**
     * @return the priceAskProviderDAO
     */
    public PriceAskProviderDAO getPriceAskProviderDAO()
    {
        return priceAskProviderDAO;
    }

    /**
     * @param priceAskProviderDAO
     *            the priceAskProviderDAO to set
     */
    public void setPriceAskProviderDAO(PriceAskProviderDAO priceAskProviderDAO)
    {
        this.priceAskProviderDAO = priceAskProviderDAO;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
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
     * @return the priceAskManager
     */
    public PriceAskManager getPriceAskManager()
    {
        return priceAskManager;
    }

    /**
     * @param priceAskManager
     *            the priceAskManager to set
     */
    public void setPriceAskManager(PriceAskManager priceAskManager)
    {
        this.priceAskManager = priceAskManager;
    }

    /**
     * @return the priceAskDAO
     */
    public PriceAskDAO getPriceAskDAO()
    {
        return priceAskDAO;
    }

    /**
     * @param priceAskDAO
     *            the priceAskDAO to set
     */
    public void setPriceAskDAO(PriceAskDAO priceAskDAO)
    {
        this.priceAskDAO = priceAskDAO;
    }

	/**
	 * @return the stockWorkDAO
	 */
	public StockWorkDAO getStockWorkDAO()
	{
		return stockWorkDAO;
	}

	/**
	 * @param stockWorkDAO the stockWorkDAO to set
	 */
	public void setStockWorkDAO(StockWorkDAO stockWorkDAO)
	{
		this.stockWorkDAO = stockWorkDAO;
	}

    /**
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    /**
     * @param baseDAO the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public StockItemArrivalDAO getStockItemArrivalDAO() {
        return stockItemArrivalDAO;
    }

    public void setStockItemArrivalDAO(StockItemArrivalDAO stockItemArrivalDAO) {
        this.stockItemArrivalDAO = stockItemArrivalDAO;
    }

    public PurchaseBjDAO getPurchaseBjDAO() {
        return purchaseBjDAO;
    }

    public void setPurchaseBjDAO(PurchaseBjDAO purchaseBjDAO) {
        this.purchaseBjDAO = purchaseBjDAO;
    }

    public PurchaseXqqrDAO getPurchaseXqqrDAO() {
        return purchaseXqqrDAO;
    }

    public void setPurchaseXqqrDAO(PurchaseXqqrDAO purchaseXqqrDAO) {
        this.purchaseXqqrDAO = purchaseXqqrDAO;
    }

    public OutDAO getOutDAO() {
        return outDAO;
    }

    public void setOutDAO(OutDAO outDAO) {
        this.outDAO = outDAO;
    }

    public DhZjbDAO getDhZjbDAO() {
        return dhZjbDAO;
    }

    public void setDhZjbDAO(DhZjbDAO dhZjbDAO) {
        this.dhZjbDAO = dhZjbDAO;
    }

    public DepotpartDAO getDepotpartDAO() {
        return depotpartDAO;
    }

    public void setDepotpartDAO(DepotpartDAO depotpartDAO) {
        this.depotpartDAO = depotpartDAO;
    }

    public SailConfigManager getSailConfigManager() {
        return sailConfigManager;
    }

    public void setSailConfigManager(SailConfigManager sailConfigManager) {
        this.sailConfigManager = sailConfigManager;
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

    public DepotDAO getDepotDAO() {
        return depotDAO;
    }

    public void setDepotDAO(DepotDAO depotDAO) {
        this.depotDAO = depotDAO;
    }
}
