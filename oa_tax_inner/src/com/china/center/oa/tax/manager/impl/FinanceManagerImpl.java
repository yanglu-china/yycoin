/**
 * File Name: FinanceManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.manager.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.china.center.oa.finance.bean.*;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.InBillDAO;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.constant.*;
import com.china.center.oa.publics.dao.*;
import com.china.center.tools.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.tax.bean.CheckViewBean;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceItemMidBean;
import com.china.center.oa.tax.bean.FinanceItemTempBean;
import com.china.center.oa.tax.bean.FinanceMidBean;
import com.china.center.oa.tax.bean.FinanceMonthBean;
import com.china.center.oa.tax.bean.FinanceShowBean;
import com.china.center.oa.tax.bean.FinanceTempBean;
import com.china.center.oa.tax.bean.FinanceTurnBean;
import com.china.center.oa.tax.bean.FinanceTurnRollbackBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.bean.UnitBean;
import com.china.center.oa.tax.constanst.CheckConstant;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.CheckViewDAO;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.dao.FinanceItemDAO;
import com.china.center.oa.tax.dao.FinanceItemMidDAO;
import com.china.center.oa.tax.dao.FinanceItemTempDAO;
import com.china.center.oa.tax.dao.FinanceMidDAO;
import com.china.center.oa.tax.dao.FinanceMonthDAO;
import com.china.center.oa.tax.dao.FinanceShowDAO;
import com.china.center.oa.tax.dao.FinanceTempDAO;
import com.china.center.oa.tax.dao.FinanceTurnDAO;
import com.china.center.oa.tax.dao.FinanceTurnRollbackDAO;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.dao.UnitDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.helper.TaxHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.oa.tax.vo.FinanceItemVO;
import com.china.center.oa.tax.vo.FinanceTurnVO;

import static com.china.center.oa.finance.constant.FinanceConstant.INBILL_STATUS_NOREF;

/**
 * FinanceManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-2-7
 * @see FinanceManagerImpl
 * @since 1.0
 */
@IntegrationAOP
public class FinanceManagerImpl implements FinanceManager {
    private final Log          operationLog       = LogFactory.getLog("opr");

    private final Log          triggerLog         = LogFactory.getLog("trigger");

    private final Log _logger = LogFactory.getLog(getClass());

    private FinanceDAO         financeDAO         = null;

    private FinanceTempDAO     financeTempDAO     = null;

    private FinanceItemTempDAO financeItemTempDAO = null;

    private CommonDAO          commonDAO          = null;

    private CheckViewDAO       checkViewDAO       = null;

    private FinanceItemDAO     financeItemDAO     = null;

    private FinanceMidDAO      financeMidDAO      = null;

    private FinanceItemMidDAO  financeItemMidDAO  = null;

    private TaxDAO             taxDAO             = null;

    private DutyDAO            dutyDAO            = null;

    private FinanceTurnDAO     financeTurnDAO     = null;

    private FinanceMonthDAO    financeMonthDAO    = null;

    private BillManager        billManager        = null;

    private OrgManager         orgManager         = null;
    
    private StafferDAO         stafferDAO         = null;
    
    private FinanceTurnRollbackDAO financeTurnRollbackDAO = null;
    
    private ParameterDAO parameterDAO = null;
    
    private DepotDAO depotDAO = null;
    
    private PrincipalshipDAO principalshipDAO = null;
    
    private ProductDAO productDAO = null;
    
    private UnitDAO unitDAO = null;
    
    private FinanceShowDAO financeShowDAO = null;

    private AttachmentDAO attachmentDAO = null;

    private InBillDAO inBillDAO = null;

    private BankDAO bankDAO        = null;
    
    private PlatformTransactionManager transactionManager = null;
    
    /**
     * 是否锁定凭证增加
     */
    private static boolean     LOCK_FINANCE       = false;

    private static Object      FINANCE_ADD_LOCK   = new Object();
    
    private static boolean  ASYNQUERY = false;

    /**
     * default constructor
     */
    public FinanceManagerImpl() {
    }

    public boolean addFinanceBeanWithoutTransactional(User user, FinanceBean bean, boolean checkNull)
            throws MYException {
        synchronized (FINANCE_ADD_LOCK) {
            return addInner(user, bean, true,  true, true);
        }
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean addFinanceBeanWithTransactional(User user, FinanceBean bean, boolean checkNull)
            throws MYException {
        synchronized (FINANCE_ADD_LOCK) {
            return addInner(user, bean, true,  true, true);
        }
    }

    @Override
    public boolean addFinanceBeanWithoutTransactional(User user, FinanceBean bean, int type)
            throws MYException {
        if (type == 0)
            return addFinanceBeanWithoutTransactional(user, bean, true);
        synchronized (FINANCE_ADD_LOCK) {
            return addMidInner(user, bean, true, false);
        }
    }

    @Override
    public boolean addFinanceBeanWithoutTransactional(User user, FinanceBean bean, int type, boolean autoPayFlag) throws MYException {
        if (type == 0)
            return addFinanceBeanWithoutTransactional(user, bean, autoPayFlag);
        synchronized (FINANCE_ADD_LOCK) {
            return addMidInner(user, bean, true, autoPayFlag);
        }
    }

    private boolean addInner(User user, FinanceBean bean, boolean mainTable, boolean checkTime, boolean setLogTime) throws MYException {
        String appName = ConfigLoader.getProperty("appName");
        if (AppConstant.APP_NAME_TW.equals(appName)){
            bean.setId(commonDAO.getSquenceString(IDPrefixConstant.ID_FINANCE_PREFIX_TW));
        } else{
            bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_FINANCE_PREFIX));
        }
        bean.setName(bean.getId());
        _logger.info(appName+" addInner for FinanceBean:"+bean);
        if (StringTools.isNullOrNone(bean.getCreaterId()) && user!= null) {
            bean.setCreaterId(user.getStafferId());
        }

        // 允许自己制定凭证日期
        if (StringTools.isNullOrNone(bean.getFinanceDate())) {
            bean.setFinanceDate(TimeTools.now_short());
        }

        if (checkTime){
            checkTime(bean);
        }

        // 入库时间
        if (setLogTime){
            bean.setLogTime(TimeTools.now());
        }

        if (OATools.getManagerFlag() && StringTools.isNullOrNone(bean.getDutyId())) {
            String msg = "凭证必须有纳税实体的属性";
            _logger.error(msg);
            throw new MYException(msg);
        }

        // 默认纳税实体
        if (bean.getType() == TaxConstanst.FINANCE_TYPE_MANAGER
                && StringTools.isNullOrNone(bean.getDutyId())) {
            bean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
        }

        if (bean.getType() == TaxConstanst.FINANCE_TYPE_DUTY
                && StringTools.isNullOrNone(bean.getDutyId())) {
            String msg = "普通凭证必须有纳税实体的属性";
            _logger.error(msg);
            throw new MYException(msg);
        }
        DutyBean duty = dutyDAO.find(bean.getDutyId());

        // 管理属性
        bean.setType(duty.getMtype());

        List<FinanceItemBean> itemList = bean.getItemList();

        boolean isTurn = FinanceHelper.isTurnFinance(itemList);

        if (isLOCK_FINANCE() && !isTurn) {
            String msg = "被锁定结转,不能增加凭证";
            _logger.error(msg);
            throw new MYException(msg);
        }

        Map<String, List<FinanceItemBean>> pareMap = new HashMap<String, List<FinanceItemBean>>();

        long inTotal = 0;

        long outTotal = 0;

        // 整理出凭证对(且校验凭证的合法性)
        for (FinanceItemBean financeItemBean : itemList) {
            financeItemBean.setId(commonDAO.getSquenceString20());

            financeItemBean.setFinanceDate(bean.getFinanceDate());

            financeItemBean.setName(financeItemBean.getId());

            FinanceHelper.copyFinanceItem(bean, financeItemBean);

            financeItemBean.setPid(bean.getId());

            financeItemBean.setType(bean.getType());

            String taxId = financeItemBean.getTaxId();

            if (StringTools.isNullOrNone(taxId)) {
                String msg = "缺少科目信息,请确认操作";
                _logger.error(msg);
                throw new MYException(msg);
            }

            TaxBean tax = taxDAO.find(taxId);

            if (tax == null) {
                String msg = "科目不存在,请确认操作:"+taxId;
                _logger.error(msg);
                throw new MYException(msg);
            }

            // 必须是最小科目哦
            if (tax.getBottomFlag() != TaxConstanst.TAX_BOTTOMFLAG_ITEM) {
                String msg = tax.getName() + tax.getId()+"[%s]科目必须是最小科目,请确认操作";
                _logger.error(msg);
                throw new MYException("[%s]科目必须是最小科目,请确认操作", tax.getName() + tax.getId());
            }

            // 不是结转需要检查辅助核算项
            if (!isTurn) {
                checkItem(financeItemBean, tax, bean.isCheckOrg(), bean.isCheckUnit());
            }

            // 拷贝凭证的父级ID
            TaxHelper.copyParent(financeItemBean, tax);

            String key = financeItemBean.getPareId();

            if (pareMap.get(key) == null) {
                pareMap.put(key, new ArrayList<FinanceItemBean>());
            }

            pareMap.get(key).add(financeItemBean);

            // 必须有一个为0
            if (financeItemBean.getInmoney() * financeItemBean.getOutmoney() != 0) {
                String msg = financeItemBean.getId()+"借方金额或者贷方金额不能都不为0";
                _logger.error(msg);
                throw new MYException(msg);
            }

            inTotal += financeItemBean.getInmoney();

            outTotal += financeItemBean.getOutmoney();
            
            _logger.debug("getInmoney():"+financeItemBean.getInmoney()+", getOutmoney():"+financeItemBean.getOutmoney());
        }
        
        if(bean.getInmoney() == 0){
        	bean.setInmoney(inTotal);
        }

        if(bean.getOutmoney() == 0){
        	bean.setOutmoney(outTotal);
        }

        if (inTotal != outTotal) {
            String msg = FinanceHelper.longToString(inTotal)+"总借[%s],总贷[%s]不等,凭证增加错误:"+FinanceHelper.longToString(outTotal);
            _logger.error(msg);
            throw new MYException("总借[%s],总贷[%s]不等,凭证增加错误", FinanceHelper.longToString(inTotal),
                    FinanceHelper.longToString(outTotal));
        }

        // CORE 核对借贷必相等的原则
        checkPare(pareMap);
        
        _logger.debug("after checkPare ...mainTable:"+mainTable);
        if (mainTable) {
            // 核心锁
//            commonDAO.updatePublicLock();

            String financeDate = bean.getFinanceDate();

            // 外层conn获取最大索引
            int findMaxMonthIndex1 = financeDAO.findMaxMonthIndexByOut(financeDate.substring(0, 8)
                    + "01", financeDate.substring(0, 8) + "31");

            // 当前事务内获取最大索引
            int findMaxMonthIndex2 = financeDAO.findMaxMonthIndexByInner(
                    financeDate.substring(0, 8) + "01", financeDate.substring(0, 8) + "31");

            // 设置MonthIndex(高并发会重复)
            bean.setMonthIndex(Math.max(findMaxMonthIndex1, findMaxMonthIndex2) + 1);

            financeDAO.saveEntityBean(bean);

            financeItemDAO.saveAllEntityBeans(itemList);
            
            _logger.debug("financeDAO add...");

            //2015/4/28 add debug info for 5101
            if (!ListTools.isEmptyOrNull(itemList)){
                _logger.info("created FinanceItemBean size:"+itemList.size());
                for (FinanceItemBean item : itemList){
                    if ("5101".equals(item.getTaxId()) || "5101".equals(item.getTaxId0())){
                        _logger.info(item.getId()+" saved with getInmoney:"+item.getInmoney()+"***getOutmoney:"+item.getOutmoney());
                    }
                }
            }

        } else {
            // 保存到临时的
            FinanceTempBean temp = new FinanceTempBean();

            BeanUtil.copyProperties(temp, bean);

            financeTempDAO.saveEntityBean(temp);

            for (FinanceItemBean eachItem : itemList) {
                FinanceItemTempBean tempItem = new FinanceItemTempBean();

                BeanUtil.copyProperties(tempItem, eachItem);

                financeItemTempDAO.saveEntityBean(tempItem);
            }
        }
        // 手工增加增加成功后需要更新
        if (bean.getCreateType() == TaxConstanst.FINANCE_CREATETYPE_HAND
                && !StringTools.isNullOrNone(bean.getRefId())) {
            billManager.updateBillBeanChecksWithoutTransactional(user, bean.getRefId(),
                    "增加手工凭证自动更新收款单核对状态:" + FinanceHelper.createFinanceLink(bean.getId()), true);
        }

        if (bean.getCreateType() == TaxConstanst.FINANCE_CREATETYPE_HAND){
            List<AttachmentBean> attachmentList = bean.getAttachmentList();

            if(null != attachmentList && attachmentList.size() > 0)
            {
                for (AttachmentBean attachmentBean : attachmentList)
                {
                    attachmentBean.setId(commonDAO.getSquenceString20());
                    attachmentBean.setRefId(bean.getId());
                }

                attachmentDAO.saveAllEntityBeans(attachmentList);
            }
        }

        return true;
    }

    /**
     * addInner
     * 
     *
     * @param user
     * @param bean
     * @param mainTable 是否增加到主表
     * @param autoPayFlag
     * @return
     * @throws MYException
     */
    private boolean addMidInner(User user, FinanceBean bean, boolean mainTable, boolean autoPayFlag) throws MYException {
        //2014/12/30 针对后台job生成的请求不验证User等
        if (!autoPayFlag){
            JudgeTools.judgeParameterIsNull(user, bean, bean.getItemList());
        }

        String appName = ConfigLoader.getProperty("appName");
        if (AppConstant.APP_NAME_TW.equals(appName)){
            bean.setId(commonDAO.getSquenceString(IDPrefixConstant.ID_FINANCE_PREFIX_TW));
        } else {
            bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_FINANCE_PREFIX));
        }

        bean.setName(bean.getId());

        if (StringTools.isNullOrNone(bean.getCreaterId())) {
            bean.setCreaterId(user.getStafferId());
        }

        // 允许自己制定凭证日期
        if (StringTools.isNullOrNone(bean.getFinanceDate())) {
            bean.setFinanceDate(TimeTools.now_short());
        }

        checkTime(bean);

        // 入库时间
        bean.setLogTime(TimeTools.now());

        if (OATools.getManagerFlag() && StringTools.isNullOrNone(bean.getDutyId())) {
            throw new MYException("凭证必须有纳税实体的属性");
        }

        // 默认纳税实体
        if (bean.getType() == TaxConstanst.FINANCE_TYPE_MANAGER
                && StringTools.isNullOrNone(bean.getDutyId())) {
            bean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
        }

        if (bean.getType() == TaxConstanst.FINANCE_TYPE_DUTY
                && StringTools.isNullOrNone(bean.getDutyId())) {
            throw new MYException("普通凭证必须有纳税实体的属性");
        }

        DutyBean duty = dutyDAO.find(bean.getDutyId());

        // 管理属性
        bean.setType(duty.getMtype());

        List<FinanceItemBean> itemList = bean.getItemList();

        boolean isTurn = FinanceHelper.isTurnFinance(itemList);

        if (isLOCK_FINANCE() && !isTurn) {
            throw new MYException("被锁定结转,不能增加凭证");
        }

        Map<String, List<FinanceItemBean>> pareMap = new HashMap<String, List<FinanceItemBean>>();

        long inTotal = 0;

        long outTotal = 0;

        // 整理出凭证对(且校验凭证的合法性)
        for (FinanceItemBean financeItemBean : itemList) {
            financeItemBean.setId(commonDAO.getSquenceString20());

            financeItemBean.setFinanceDate(bean.getFinanceDate());

            financeItemBean.setName(financeItemBean.getId());

            FinanceHelper.copyFinanceItem(bean, financeItemBean);

            financeItemBean.setPid(bean.getId());

            financeItemBean.setType(bean.getType());

            String taxId = financeItemBean.getTaxId();

            if (StringTools.isNullOrNone(taxId)) {
                throw new MYException("缺少科目信息,请确认操作");
            }

            TaxBean tax = taxDAO.find(taxId);

            if (tax == null) {
                throw new MYException("科目不存在,请确认操作");
            }

            // 必须是最小科目哦
            if (tax.getBottomFlag() != TaxConstanst.TAX_BOTTOMFLAG_ITEM) {
                throw new MYException("[%s]科目必须是最小科目,请确认操作", tax.getName() + tax.getId());
            }

            // 不是结转需要检查辅助核算项
            if (!isTurn) {
                checkItem(financeItemBean, tax, bean.isCheckOrg(), bean.isCheckUnit());
            }

            // 拷贝凭证的父级ID
            TaxHelper.copyParent(financeItemBean, tax);

            String key = financeItemBean.getPareId();

            if (pareMap.get(key) == null) {
                pareMap.put(key, new ArrayList<FinanceItemBean>());
            }

            pareMap.get(key).add(financeItemBean);

            // 必须有一个为0
            if (financeItemBean.getInmoney() * financeItemBean.getOutmoney() != 0) {
                throw new MYException("借方金额或者贷方金额不能都不为0");
            }

            inTotal += financeItemBean.getInmoney();

            outTotal += financeItemBean.getOutmoney();
        }

        bean.setInmoney(inTotal);

        bean.setOutmoney(outTotal);

 
        if (inTotal != outTotal) {
            throw new MYException("总借[%s],总贷[%s]不等,凭证增加错误", FinanceHelper.longToString(inTotal),
                    FinanceHelper.longToString(outTotal));
        }

        // CORE 核对借贷必相等的原则
        checkPare(pareMap);

        if (mainTable) {
            // 核心锁
            commonDAO.updatePublicLock1();

            String financeDate = bean.getFinanceDate();

            // 外层conn获取最大索引
            int findMaxMonthIndex1 = financeDAO.findMaxMonthIndexByOut(financeDate.substring(0, 8)
                    + "01", financeDate.substring(0, 8) + "31");

            // 当前事务内获取最大索引
            int findMaxMonthIndex2 = financeDAO.findMaxMonthIndexByInner(
                    financeDate.substring(0, 8) + "01", financeDate.substring(0, 8) + "31");

            // 设置MonthIndex(高并发会重复)
            bean.setMonthIndex(Math.max(findMaxMonthIndex1, findMaxMonthIndex2) + 1);

            // 保存到临时的
            FinanceMidBean temp = new FinanceMidBean();

            BeanUtil.copyProperties(temp, bean);

            financeMidDAO.saveEntityBean(temp);

            for (FinanceItemBean eachItem : itemList) {
                FinanceItemMidBean tempItem = new FinanceItemMidBean();

                BeanUtil.copyProperties(tempItem, eachItem);

                financeItemMidDAO.saveEntityBean(tempItem);
            }
        }

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public void fixMonthIndex() {
        triggerLog.info("begin fixMonthIndex...");

        String date = TimeTools.now_short(-1);

        // 更新昨天重复的
        List<String> duplicateMonthIndex = financeDAO.queryDuplicateMonthIndex(date);

        if (ListTools.isEmptyOrNull(duplicateMonthIndex)) {
            triggerLog.info("fixMonthIndex empty");

            triggerLog.info("end fixMonthIndex...");

            return;
        }

        for (String eachId : duplicateMonthIndex) {
            FinanceBean bean = financeDAO.find(eachId);

            if (bean == null) {
                continue;
            }

            String financeDate = bean.getFinanceDate();

            // 外层conn获取最大索引
            int findMaxMonthIndex1 = financeDAO.findMaxMonthIndexByOut(financeDate.substring(0, 8)
                    + "01", financeDate.substring(0, 8) + "31");

            // 当前事务内获取最大索引
            int findMaxMonthIndex2 = financeDAO.findMaxMonthIndexByInner(
                    financeDate.substring(0, 8) + "01", financeDate.substring(0, 8) + "31");

            int newMaxMonthIndex = Math.max(findMaxMonthIndex1, findMaxMonthIndex2) + 1;

            triggerLog.info("fix MonthIndex from:" + eachId + ".Old MonthIndex is:"
                    + bean.getMonthIndex() + ",and new MonthIndex is:" + newMaxMonthIndex);

            // 更新
            financeDAO.updateMonthIndex(eachId, newMaxMonthIndex);
        }

        triggerLog.info("end fixMonthIndex...");
    }

    @IntegrationAOP(auth = AuthConstant.FINANCE_TURN, lock = TaxConstanst.FINANCETURN_OPR_LOCK)
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteFinanceTurnBean(User user, String id) throws MYException {
        JudgeTools.judgeParameterIsNull(user, id);

        try {
            // 锁定
            setLOCK_FINANCE(true);

            FinanceTurnBean turn = financeTurnDAO.find(id);

            if (turn == null) {
                throw new MYException("数据错误,请确认操作");
            }

            FinanceTurnVO last = financeTurnDAO.findLastVO();

            if (last != null) {
                if (!turn.getId().equals(last.getId())) {
                    throw new MYException("只能撤销最近的月结:" + last.getMonthKey());
                }
            }

            if (last.getId().equals("A9990000000000000001")) {
                throw new MYException("初始化月结不能撤销,请确认操作");
            }

            // 删除月结产生的凭证
            ConditionParse con = new ConditionParse();
            con.addWhereStr();
            con.addCondition("financeDate", ">=",
                    TimeTools.getShortStringByLongString(turn.getStartTime()));
            con.addCondition("financeDate", "<=",
                    TimeTools.getShortStringByLongString(turn.getEndTime()));
            con.addIntCondition("createType", "=", TaxConstanst.FINANCE_CREATETYPE_TURN);

            List<String> idList = financeDAO.queryEntityIdsByCondition(con);

            for (String each : idList) {
                // 删除凭证
                financeDAO.deleteEntityBean(each);

                // 删除凭证项
                financeItemDAO.deleteEntityBeansByFK(each);

                // 删除需要记录操作日志
                operationLog.info(user.getStafferName() + "删除了损益结转凭证:" + each);
            }

            con.clear();
            con.addWhereStr();
            con.addCondition("financeDate", ">=",
                    TimeTools.getShortStringByLongString(turn.getStartTime()));
            con.addCondition("financeDate", "<=",
                    TimeTools.getShortStringByLongString(turn.getEndTime()));
            con.addIntCondition("createType", "=", TaxConstanst.FINANCE_CREATETYPE_PROFIT);

            idList = financeDAO.queryEntityIdsByCondition(con);

            for (String each : idList) {
                // 删除凭证
                financeDAO.deleteEntityBean(each);

                // 删除凭证项
                financeItemDAO.deleteEntityBeansByFK(each);

                // 删除需要记录操作日志
                operationLog.info(user.getStafferName() + "删除了利润结转凭证:" + each);
            }

            // 删除明细
            financeMonthDAO.deleteEntityBeansByFK(turn.getMonthKey());

            // 删除结转
            financeTurnDAO.deleteEntityBean(id);

            // 解锁凭证,恢复修改和删除
            financeDAO.updateLockToBegin(TimeTools.getShortStringByLongString(turn.getStartTime()),
                    TimeTools.getShortStringByLongString(turn.getEndTime()));

            // 增加撤销记录
            FinanceTurnRollbackBean rollbackBean = financeTurnRollbackDAO.findByUnique(turn.getMonthKey());
            
            if (null == rollbackBean)
            {
                FinanceTurnRollbackBean bean = new FinanceTurnRollbackBean();
                
                bean.setMonthKey(turn.getMonthKey());
                bean.setLogTime(TimeTools.now());
                
                financeTurnRollbackDAO.saveEntityBean(bean);
            }
            
            operationLog.info(user.getStafferName() + "进行了" + turn.getMonthKey() + "的结转撤销(操作成功)");

        } finally {
            // 解锁
            setLOCK_FINANCE(false);
        }

        return true;
    }

    /**
     * 结转
     */
    @IntegrationAOP(auth = AuthConstant.FINANCE_TURN, lock = TaxConstanst.FINANCETURN_OPR_LOCK)
    @Transactional(rollbackFor = MYException.class)
    public boolean addFinanceTurnBean(User user, FinanceTurnBean bean) throws MYException {
        try {
//            JudgeTools.judgeParameterIsNull(user, bean);
            JudgeTools.judgeParameterIsNull(bean);

            // 锁定
            setLOCK_FINANCE(true);

            // 时间校验
            checkAddTurn(bean);

            bean.setId(commonDAO.getSquenceString20());

            if (user!= null){
                bean.setStafferId(user.getStafferId());
            }

            String changeFormat = TimeTools.changeFormat(bean.getMonthKey(), "yyyyMM", "yyyy-MM");

            // 设置起止时间
            bean.setStartTime(changeFormat + "-01 00:00:00");

            bean.setEndTime(changeFormat + "-31 23:59:59");

            // 损益结转
            List<FinanceItemBean> itemList = createTurnFinance(user, bean, changeFormat);

            // 利润结转
            createProfitFinance(user, bean, changeFormat, itemList);

            // 产生月结数据(科目/借总额/贷总额/KEY/LOGTIME)
            createMonthData(user, bean, changeFormat, itemList);

            // 锁定凭证,不能修改和删除
            int amount = financeDAO.updateLockToEnd(changeFormat + "-01", changeFormat + "-31");

            bean.setAmount(amount);

            bean.setLogTime(TimeTools.now());

            // 保存后本月的凭证就不能增加了
            financeTurnDAO.saveEntityBean(bean);

            if (user == null){
                operationLog.info( "系统进行了" + bean.getMonthKey() + "的结转(操作成功)");
            } else{
                operationLog.info(user.getStafferName() + "进行了" + bean.getMonthKey() + "的结转(操作成功)");
            }

            return true;
        } finally {
            // 解锁
            setLOCK_FINANCE(false);
        }
    }

    /**
     * 产生月结数据
     * 
     * @param user
     * @param bean
     * @param changeFormat
     */
    private void createMonthData(User user, FinanceTurnBean bean, String changeFormat,
            List<FinanceItemBean> itemList) {
        _logger.info("**************createMonthData now************");
        List<TaxBean> taxList = taxDAO.listEntityBeansByOrder("order by id");

        for (TaxBean taxBean : taxList) {
            ConditionParse condition = new ConditionParse();

            condition.addWhereStr();

            condition.addCondition("financeDate", ">=", changeFormat + "-01");

            condition.addCondition("financeDate", "<=", changeFormat + "-31");

            // 所有的科目都月结
            condition.addCondition("taxId" + taxBean.getLevel(), "=", taxBean.getId());

            long inMonetTotal = financeItemDAO.sumInByCondition(condition);

            long outMonetTotal = financeItemDAO.sumOutByCondition(condition);

            FinanceMonthBean fmb = new FinanceMonthBean();

            fmb.setId(commonDAO.getSquenceString20());

            if(user!= null){
                fmb.setStafferId(user.getStafferId());
            }

            fmb.setMonthKey(bean.getMonthKey());

            FinanceHelper.copyTax(taxBean, fmb);

            // 当期发生
            fmb.setInmoneyTotal(inMonetTotal);

            fmb.setOutmoneyTotal(outMonetTotal);

            // 当期累计
            if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN) {
                fmb.setLastTotal(inMonetTotal - outMonetTotal);
            } else {
                fmb.setLastTotal(outMonetTotal - inMonetTotal);
            }

            // 获取最近的一个月
            FinanceTurnVO lastTurn = financeTurnDAO.findLastVO();

            FinanceMonthBean lastMonth = financeMonthDAO.findByUnique(taxBean.getId(),
                    lastTurn.getMonthKey());

            // 科目整个系统的累加数
            if (lastMonth != null) {
                // 累加之前所有的值(滚动累加)
                fmb.setInmoneyAllTotal(lastMonth.getInmoneyAllTotal() + inMonetTotal);

                fmb.setOutmoneyAllTotal(lastMonth.getOutmoneyAllTotal() + outMonetTotal);
            } else {
                // 没有作为初始值(第一个月是没有的)
                fmb.setInmoneyAllTotal(inMonetTotal);

                fmb.setOutmoneyAllTotal(outMonetTotal);
            }

            // 期末余额
            if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN) {
                fmb.setLastAllTotal(fmb.getInmoneyAllTotal() - fmb.getOutmoneyAllTotal());
            } else {
                fmb.setLastAllTotal(fmb.getOutmoneyAllTotal() - fmb.getInmoneyAllTotal());
            }

            // 损益类
            if (taxBean.getPtype().equals(TaxConstanst.TAX_PTYPE_LOSS)) {
                // 结转金额(每个损益科目)
                long total = 0L;

                // 因为每个duty生成一个,所以这里是循环获取所有
                for (FinanceItemBean financeItemBean : itemList) {
                    if (financeItemBean.getTaxId().equals(taxBean.getId())) {
                        if (financeItemBean.getForward() == TaxConstanst.TAX_FORWARD_IN) {
                            total += financeItemBean.getInmoney();
                        }

                        if (financeItemBean.getForward() == TaxConstanst.TAX_FORWARD_OUT) {
                            total += financeItemBean.getOutmoney();
                        }
                    }
                }

                fmb.setMonthTurnTotal(total);
            }

            fmb.setLogTime(TimeTools.now());

            //2015/4/28 add debug info for 5101
            if ("5101".equals(fmb.getTaxId())){
                _logger.info("5101 sum sql:"+condition.toString());
                _logger.info("5101 getInmoneyTotal:"+fmb.getInmoneyTotal()+"***getOutmoneyTotal:"+fmb.getOutmoneyTotal()+"***getLastTotal:"+fmb.getLastTotal());
                _logger.info("5101 getInmoneyAllTotal:"+fmb.getInmoneyAllTotal()+"***getOutmoneyAllTotal:"+fmb.getOutmoneyAllTotal()+"***getLastAllTotal:"+fmb.getLastAllTotal());
                _logger.info("5101 getMonthTurnTotal:"+fmb.getMonthTurnTotal());
            }

            financeMonthDAO.saveEntityBean(fmb);
        }
    }

    /**
     * checkTurnTime
     * 
     * @param bean
     * @throws MYException
     */
    private void checkAddTurn(FinanceTurnBean bean) throws MYException {
        FinanceTurnVO topTurn = financeTurnDAO.findLastVO();

        if (topTurn != null) {
            // 2011017
            String monthKey = topTurn.getMonthKey();

            String nextKey = TimeTools.getStringByOrgAndDaysAndFormat(monthKey, 32, "yyyyMM");

            if (!nextKey.equals(bean.getMonthKey())) {
                throw new MYException("上次结转的是[%s],本次结转的是[%s],应该结转的是[%s],请确认操作", monthKey,
                        bean.getMonthKey(), nextKey);
            }
        }

        // 结转时间不能小于当前时间
        String changeFormat = TimeTools.changeFormat(bean.getMonthKey(), "yyyyMM", "yyyy-MM");

        String monthEnd = TimeTools.getMonthEnd(changeFormat + "-01");

        if (TimeTools.now_short().compareTo(monthEnd) < 0) {
            throw new MYException("结转只能在月末或者下月发生,不能提前结转");
        }

        // 查询是否都已经核对
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("financeDate", ">=", changeFormat + "-01");
        con.addCondition("financeDate", "<=", changeFormat + "-31");

        con.addIntCondition("status", "=", TaxConstanst.FINANCE_STATUS_NOCHECK);

//        int count = financeDAO.countByCondition(con.toString());
//
//        if (count > 0) {
//            throw new MYException("当前[" + changeFormat + "]下存在:" + count + "个凭证没有核对,不能月结");
//        }
    }

    /**
     * 结转凭证
     * 
     * @param user
     * @param bean
     * @param changeFormat
     * @throws MYException
     */
    private List<FinanceItemBean> createTurnFinance(User user, FinanceTurnBean bean,
            String changeFormat) throws MYException {

        List<FinanceItemBean> resultList = new LinkedList<FinanceItemBean>();
        List<DutyBean> dutyList = dutyDAO.listEntityBeans();

        // 每个纳税实体生成一个凭证
        for (DutyBean dutyBean : dutyList) {
            List<FinanceItemBean> itemList = new LinkedList<FinanceItemBean>();

            // 产生凭证(结转/利润结转)
            List<TaxBean> taxList = taxDAO.queryEntityBeansByFK(TaxConstanst.TAX_PTYPE_LOSS);

            FinanceBean financeBean = new FinanceBean();

            String name = "";
            if (user == null){
                name =  "系统损益结转" + bean.getMonthKey() + ":"
                        + dutyBean.getName();
            } else{
                name = user.getStafferName() + "损益结转" + bean.getMonthKey() + ":"
                        + dutyBean.getName();
            }


            financeBean.setName(name);

            financeBean.setDutyId(dutyBean.getId());

            financeBean.setType(dutyBean.getMtype());

            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_TURN);

            if (user!= null){
                financeBean.setCreaterId(user.getStafferId());
            }

            financeBean.setDescription(financeBean.getName());

            // 这里的日期是本月最后一天哦
            financeBean.setFinanceDate(TimeTools.getMonthEnd(changeFormat + "-01"));

            financeBean.setLogTime(TimeTools.now());

            String pare = commonDAO.getSquenceString();

            // 本年利润
            String itemTaxId = TaxItemConstanst.YEAR_PROFIT;

            TaxBean yearTax = taxDAO.findByUnique(itemTaxId);

            if (yearTax == null) {
                _logger.error("yearTax is null:"+TaxItemConstanst.YEAR_PROFIT);
                throw new MYException("数据错误,请确认操作");
            }

            for (TaxBean taxBean : taxList) {
                if (taxBean.getBottomFlag() == TaxConstanst.TAX_BOTTOMFLAG_ROOT) {
                    _logger.info("TAX_BOTTOMFLAG_ROOT not count");
                    continue;
                }

                ConditionParse condition = new ConditionParse();

                condition.addWhereStr();

                condition.addCondition("financeDate", ">=", changeFormat + "-01");
                condition.addCondition("financeDate", "<=", changeFormat + "-31");

                condition.addCondition("dutyId", "=", dutyBean.getId());

                condition.addCondition("taxId", "=", taxBean.getId());

                // 每个纳税实体的统计就是全部
                long inMonetTotal = financeItemDAO.sumInByCondition(condition);

                long outMonetTotal = financeItemDAO.sumOutByCondition(condition);

                if (inMonetTotal == 0 && outMonetTotal == 0) {
                    _logger.warn(dutyBean.getId()+" inMonetTotal == outMonetTotal == 0"+taxBean.getId());
                    continue;
                }

                // 空的删除
                if ((inMonetTotal == outMonetTotal) && (itemList.size() != 0)) {
                    _logger.warn(dutyBean.getId()+" inMonetTotal == outMonetTotal"+taxBean.getId());
                    continue;
                }

                // 借方(损益每月都结转的,所以损益的期初都是0)(借:本年利润 贷损益,这里的辅助核算型可以为空的)
                if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN) {
                    String eachName = "借:本年利润,贷:" + taxBean.getName();

                    // 借:本年利润 贷:损益科目
                    FinanceItemBean itemInEach = new FinanceItemBean();

                    itemInEach.setPareId(pare);

                    itemInEach.setName(eachName);

                    itemInEach.setDutyId(financeBean.getDutyId());

                    itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

                    FinanceHelper.copyFinanceItem(financeBean, itemInEach);

                    // 科目拷贝
                    FinanceHelper.copyTax(yearTax, itemInEach);

                    itemInEach.setInmoney(inMonetTotal - outMonetTotal);

                    itemInEach.setOutmoney(0);

                    itemInEach.setDescription(eachName);


                    itemList.add(itemInEach);

                    FinanceItemBean itemOutEach = new FinanceItemBean();

                    itemOutEach.setPareId(pare);

                    itemOutEach.setName(eachName);

                    itemOutEach.setDutyId(financeBean.getDutyId());

                    itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

                    FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

                    // 科目拷贝
                    FinanceHelper.copyTax(taxBean, itemOutEach);

                    itemOutEach.setInmoney(0);

                    itemOutEach.setOutmoney(inMonetTotal - outMonetTotal);

                    itemOutEach.setDescription(eachName);

                    itemList.add(itemOutEach);
                } else {
                    String eachName = "借:" + taxBean.getName() + ",贷:本年利润";

                    // 借:损益科目 贷:本年利润
                    FinanceItemBean itemInEach = new FinanceItemBean();

                    itemInEach.setPareId(pare);

                    itemInEach.setName(eachName);

                    itemInEach.setDutyId(financeBean.getDutyId());

                    itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

                    FinanceHelper.copyFinanceItem(financeBean, itemInEach);

                    // 科目拷贝
                    FinanceHelper.copyTax(taxBean, itemInEach);


                    if ("5101".equals(itemInEach.getTaxId())){
                        _logger.info("create in FinanceItemBean  with SQL:"+condition.toString());
                        _logger.info("create in FinanceItemBean  with outMonetTotal:"+outMonetTotal+"***inMonetTotal:"+inMonetTotal);
                    }

                    itemInEach.setInmoney(outMonetTotal - inMonetTotal);
                    if ("5101".equals(itemInEach.getTaxId())){
                        _logger.info("create in FinanceItemBean  with setInmoney:"+itemInEach.getInmoney());
                    }

                    itemInEach.setOutmoney(0);

                    itemInEach.setDescription(eachName);

                    itemList.add(itemInEach);

                    // 贷方
                    FinanceItemBean itemOutEach = new FinanceItemBean();

                    itemOutEach.setPareId(pare);

                    itemOutEach.setName(eachName);

                    itemOutEach.setDutyId(financeBean.getDutyId());

                    itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

                    FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

                    // 科目拷贝
                    FinanceHelper.copyTax(yearTax, itemOutEach);

                    itemOutEach.setInmoney(0);

                    itemOutEach.setOutmoney(outMonetTotal - inMonetTotal);

                    itemOutEach.setDescription(eachName);

                    itemList.add(itemOutEach);
                }
            }

            // 如果都是空,不增加
            if (itemList.isEmpty()) {
                continue;
            }

            financeBean.setItemList(itemList);

            resultList.addAll(itemList);

            financeBean.setLocks(TaxConstanst.FINANCE_LOCK_YES);

            financeBean.setStatus(TaxConstanst.FINANCE_STATUS_CHECK);

            financeBean.setChecks("月结凭证,无需核对");

            // 入库
            addFinanceBeanWithoutTransactional(user, financeBean, true);
        }

        return resultList;
    }

    /**
     * 利润结转
     * 
     * @param user
     * @param bean
     * @param changeFormat
     * @return
     * @throws MYException
     */
    private List<FinanceItemBean> createProfitFinance(User user, FinanceTurnBean bean,
            String changeFormat, List<FinanceItemBean> itemNearList) throws MYException {
        List<FinanceItemBean> resultList = new LinkedList<FinanceItemBean>();

        List<DutyBean> dutyList = dutyDAO.listEntityBeans();

        // 每个纳税实体生成一个凭证
        for (DutyBean dutyBean : dutyList) {
            // 利润结转
            long profit = 0L;

            // itemNearList里面有很多的本年利润
            for (FinanceItemBean financeItemBean : itemNearList) {
                if (financeItemBean.getTaxId().equals(TaxItemConstanst.YEAR_PROFIT)
                        && financeItemBean.getForward() == TaxConstanst.TAX_FORWARD_IN
                        && financeItemBean.getDutyId().equals(dutyBean.getId())) {
                    profit = profit - financeItemBean.getInmoney();
                }

                if (financeItemBean.getTaxId().equals(TaxItemConstanst.YEAR_PROFIT)
                        && financeItemBean.getForward() == TaxConstanst.TAX_FORWARD_OUT
                        && financeItemBean.getDutyId().equals(dutyBean.getId())) {
                    profit = profit + financeItemBean.getOutmoney();
                }
            }

            // 没有利润结转
            if (profit == 0) {
                _logger.info("no profit for duty:"+dutyBean.getId());
                continue;
            }

            FinanceBean financeBean = new FinanceBean();

            String name = "";
            if (user == null){
                name =  "系统利润结转:" + bean.getMonthKey() + ":"
                        + dutyBean.getName();
            } else {
                name = user.getStafferName() + "利润结转:" + bean.getMonthKey() + ":"
                        + dutyBean.getName();
            }

            financeBean.setName(name);

            financeBean.setType(dutyBean.getMtype());

            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_PROFIT);

            financeBean.setDutyId(dutyBean.getId());

            if(user!= null){
                financeBean.setCreaterId(user.getStafferId());
            }

            financeBean.setDescription(financeBean.getName());

            // 这里的日期是本月最后一天哦
            financeBean.setFinanceDate(TimeTools.getMonthEnd(changeFormat + "-01"));

            financeBean.setLogTime(TimeTools.now());

            List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

            // 凭证对
            String pare = commonDAO.getSquenceString();

            // 本年利润
            String itemTaxId = TaxItemConstanst.YEAR_PROFIT;

            TaxBean yearTax = taxDAO.findByUnique(itemTaxId);

            if (yearTax == null) {
                _logger.error("yearTax is null:"+itemTaxId);
                throw new MYException("数据错误,请确认操作");
            }

            // 未分配利润
            TaxBean noProfit = taxDAO.findByUnique(TaxItemConstanst.NO_PROFIT);

            if (noProfit == null) {
                _logger.error("noProfit is null:"+TaxItemConstanst.NO_PROFIT);
                throw new MYException("数据错误,请确认操作");
            }

            String eachName = "借:本年利润,贷:未分配利润";

            // 借:本年利润 贷:损益科目
            FinanceItemBean itemInEach = new FinanceItemBean();

            itemInEach.setPareId(pare);

            itemInEach.setName(eachName);

            itemInEach.setForward(TaxConstanst.TAX_FORWARD_IN);

            FinanceHelper.copyFinanceItem(financeBean, itemInEach);

            // 科目拷贝
            FinanceHelper.copyTax(yearTax, itemInEach);

            itemInEach.setInmoney(profit);

            itemInEach.setOutmoney(0);

            itemInEach.setDescription(eachName);

            itemList.add(itemInEach);

            FinanceItemBean itemOutEach = new FinanceItemBean();

            itemOutEach.setPareId(pare);

            itemOutEach.setName(eachName);

            itemOutEach.setForward(TaxConstanst.TAX_FORWARD_OUT);

            FinanceHelper.copyFinanceItem(financeBean, itemOutEach);

            // 科目拷贝
            FinanceHelper.copyTax(noProfit, itemOutEach);

            itemOutEach.setInmoney(0);

            itemOutEach.setOutmoney(profit);

            itemOutEach.setDescription(eachName);

            itemList.add(itemOutEach);

            financeBean.setItemList(itemList);

            financeBean.setLocks(TaxConstanst.FINANCE_LOCK_YES);

            financeBean.setStatus(TaxConstanst.FINANCE_STATUS_CHECK);

            financeBean.setChecks("月结凭证,无需核对");

            // 入库
            addFinanceBeanWithoutTransactional(user, financeBean, true);

            resultList.addAll(itemList);
        }

        return resultList;
    }

    /**
     * checkItem
     * 
     * @param financeItemBean
     * @param tax
     * @throws MYException
     */
    private void checkItem(FinanceItemBean financeItemBean, TaxBean tax, boolean checkOrg, boolean checkUnit) throws MYException {
        if (checkUnit){
            if ( tax.getUnit() == TaxConstanst.TAX_CHECK_YES
                    && StringTools.isNullOrNone(financeItemBean.getUnitId())) {
                throw new MYException("科目[%s]下辅助核算型-单位必须存在,请确认操作", tax.getName());
            }

            if (tax.getDepartment() == TaxConstanst.TAX_CHECK_YES
                    && StringTools.isNullOrNone(financeItemBean.getDepartmentId())) {
                throw new MYException("科目[%s]下辅助核算型-部门必须存在,请确认操作", tax.getName());
            }

            if (tax.getStaffer() == TaxConstanst.TAX_CHECK_YES
                    && StringTools.isNullOrNone(financeItemBean.getStafferId())) {
                throw new MYException("科目[%s]下辅助核算型-职员必须存在,请确认操作", tax.getName());
            }

            if (tax.getProduct() == TaxConstanst.TAX_CHECK_YES
                    && StringTools.isNullOrNone(financeItemBean.getProductId())) {
                throw new MYException("科目[%s]下辅助核算型-产品必须存在,请确认操作", tax.getName());
            }

            if (tax.getDepot() == TaxConstanst.TAX_CHECK_YES
                    && StringTools.isNullOrNone(financeItemBean.getDepotId())) {
                throw new MYException("科目[%s]下辅助核算型-仓库必须存在,请确认操作", tax.getName());
            }

            if (tax.getDuty() == TaxConstanst.TAX_CHECK_YES
                    && StringTools.isNullOrNone(financeItemBean.getDuty2Id())) {
                throw new MYException("科目[%s]下辅助核算型-纳税实体必须存在,请确认操作", tax.getName());
            }
        }
        
        // 检查人员是否属于部门下面
        if (!StringTools.isNullOrNone(financeItemBean.getDepartmentId()) 
                && !StringTools.isNullOrNone(financeItemBean.getStafferId()))
        {
            StafferBean sbean = stafferDAO.find(financeItemBean.getStafferId());
            
            if (null == sbean)
            {
                throw new MYException("核算项中人员不存在,ID:[%s]",financeItemBean.getStafferId());
            }
            
            PrincipalshipBean prinBean = orgManager.findPrincipalshipById(financeItemBean.getDepartmentId());
            
            if (null == prinBean)
            {
                throw new MYException("核算项中部门不存在,ID:[%s]",financeItemBean.getDepartmentId());
            }
            
            List<PrincipalshipBean> prinList = orgManager.querySubPrincipalship(financeItemBean.getDepartmentId());
            
            for(Iterator<PrincipalshipBean> iterator = prinList.iterator(); iterator.hasNext();)
            {
                PrincipalshipBean pBean = iterator.next();
                
                if (pBean.getStatus() == 1)
                    iterator.remove();
            }
            
            if (checkOrg && prinList.size() > 1)
            {
                throw new MYException("核算项中人员[%s]的核算项部门[%s]不是最末级部门",sbean.getName(),prinBean.getName());
            }
            
            if (checkOrg
                &&!orgManager.isStafferBelongOrg(financeItemBean.getStafferId(), financeItemBean.getDepartmentId()))
            {
                throw new MYException("核算项中人员[%s]不属于核算中的部门[%s],请确认操作", sbean.getName(), prinBean.getName());
            }

        }
    }

    @Transactional(rollbackFor = MYException.class)
    @Override
    public void addFinanceTurnJob() throws MYException {
        String beginMonthKey =  ConfigLoader.getProperty("beginMonthKey");
        String endMonthKey =  ConfigLoader.getProperty("endMonthKey");
        if (!StringTools.isNullOrNone(beginMonthKey) && !StringTools.isNullOrNone(endMonthKey)){
            SortedSet<String> monthKeys = StringUtils.getMonthKeys(beginMonthKey, endMonthKey);
            //计算次数过多会把系统卡死!
            if (monthKeys.size()<= 120){
                for (String monthKey : monthKeys){
                    FinanceTurnBean bean = new FinanceTurnBean();
                    bean.setMonthKey(monthKey);
                    this.addFinanceTurnBean(null, bean);
                }
            }
        }
    }

    @Transactional(rollbackFor = MYException.class)
    @Override
    public void yscfJob() throws MYException {
        _logger.info("****yscfJob running****");
        List<AdvanceReceiptBean> advanceReceiptBeans = this.inBillDAO.queryYscf();
        if(!ListTools.isEmptyOrNull(advanceReceiptBeans)){
            for(AdvanceReceiptBean advanceReceiptBean: advanceReceiptBeans){
                _logger.info("***AdvanceReceiptBean size***"+advanceReceiptBeans.size());
                String billId = advanceReceiptBean.getSf();
                InBillBean inBillBean = this.inBillDAO.find(billId);
                if (inBillBean == null){
                    _logger.error("inbill not found***"+billId);
                } else if (advanceReceiptBean.getSyMoney()>0){
                    InBillBean newInbillBean = new InBillBean();
                    BeanUtil.copyProperties(newInbillBean, inBillBean);

                    newInbillBean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_BILL_PREFIX));
                    newInbillBean.setStatus(INBILL_STATUS_NOREF);
                    newInbillBean.setMoneys(advanceReceiptBean.getSyMoney());
                    newInbillBean.setSrcMoneys(advanceReceiptBean.getSyMoney());
                    newInbillBean.setDescription("从收款单:"+billId + " 收到预收(金额):"+ advanceReceiptBean.getSyMoney());
                    newInbillBean.setOutId("");
                    //TODO check
                    String providerId = advanceReceiptBean.getProvider();
                    newInbillBean.setCustomerId(providerId);
                    newInbillBean.setLogTime(TimeTools.now());
                    this.inBillDAO.saveEntityBean(newInbillBean);

                    //原SF单生成凭证，借预收，贷应付，金额取advancereceipt表中行记录的gjmoney字段值，金额均为正值，客户ID为供应商ID
                    FinanceBean financeBean = new FinanceBean();

                    String name = "预收拆分JOB生成";
                    financeBean.setName(name);
                    financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BILL_SPLIT);
                    financeBean.setRefId("");
                    financeBean.setRefBill(billId);

                    BankBean bank = bankDAO.find(inBillBean.getBankId());
                    if (bank == null)
                    {
                        _logger.error("银行不存在:"+inBillBean.getBankId());
                        continue;
                    }
                    financeBean.setDutyId(bank.getDutyId());

                    financeBean.setCreaterId("系统");
                    financeBean.setDescription(financeBean.getName());
                    financeBean.setFinanceDate(TimeTools.now_short());
                    financeBean.setLogTime(TimeTools.now());

                    List<FinanceItemBean> itemList = new ArrayList<>();

                    String stafferId = inBillBean.getOwnerId();
                    if(StringTools.isNullOrNone(stafferId)){
                       _logger.error("ownerId is empty:"+billId);
                       continue;
                    }
                    StafferBean stafferBean = stafferDAO.find(stafferId);
                    if (stafferBean == null){
                        _logger.error("staffer not found***"+stafferId);
                        continue;
                    }
                    // 借：预收 贷：应付
                    createAddItem2(null, stafferBean, stafferBean, financeBean, itemList,
                            billId, advanceReceiptBean.getGjMoney(), inBillBean.getCustomerId(), providerId);

                    financeBean.setItemList(itemList);

                    this.addFinanceBeanWithoutTransactional(null, financeBean, true);
                    this.inBillDAO.updateYscfStatus(advanceReceiptBean.getId());

                    inBillBean.setStatus(FinanceConstant.INBILL_STATUS_PAYMENTS);
                    inBillBean.setMoneys(advanceReceiptBean.getGjMoney());
                    this.inBillDAO.updateEntityBean(inBillBean);
                    _logger.info("***create finance bean***"+financeBean);
                }
            }
        }
        _logger.info("****yscfJob finished****");
    }

    private void createAddItem2(User user, StafferBean target,
                                StafferBean srcStaffer,
                                FinanceBean financeBean, List<FinanceItemBean> itemList,
                                String billId, double moneys, String srcCustomerId, String destCustomerId)
            throws MYException
    {
        String name = financeBean.getName() + billId + '.';

        // 预收账款
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName(name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.PREREVEIVE_PRODUCT);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        itemIn.setInmoney(FinanceHelper.doubleToLong(moneys));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户/职员/部门
        itemIn.setDepartmentId(srcStaffer.getPrincipalshipId());
        itemIn.setStafferId(srcStaffer.getId());
        itemIn.setUnitId(srcCustomerId);
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName(name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 应付账款
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.PAY_PRODUCT);

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);
        itemOut.setInmoney(0);
        itemOut.setOutmoney(FinanceHelper.doubleToLong(moneys));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
        itemOut.setDepartmentId(target.getPrincipalshipId());
        itemOut.setStafferId(target.getId());
        itemOut.setUnitId(destCustomerId);
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemOut);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean addFinanceBean(User user, FinanceBean bean) throws MYException {
        return addFinanceBeanWithoutTransactional(user, bean, true);
    }

    @IntegrationAOP(auth = AuthConstant.FINANCE_OPR)
    @Transactional(rollbackFor = MYException.class)
    public boolean moveTempFinanceBeanToRelease(User user, String id) throws MYException {
        FinanceTempBean temp = financeTempDAO.find(id);

        if (temp == null) {
            throw new MYException("数据错误,请确认操作");
        }

        List<FinanceItemTempBean> tempItemList = financeItemTempDAO.queryEntityBeansByFK(id);

        // 删除临时凭证
        financeTempDAO.deleteEntityBean(id);

        // 删除临时凭证项
        financeItemTempDAO.deleteEntityBeansByFK(id);

        // 增加新凭证
        FinanceBean bean = new FinanceBean();

        BeanUtil.copyProperties(bean, temp);

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        for (FinanceItemTempBean financeItemTempBean : tempItemList) {
            FinanceItemBean item = new FinanceItemBean();

            BeanUtil.copyProperties(item, financeItemTempBean);

            itemList.add(item);
        }

        bean.setItemList(itemList);

        bean.setStatus(TaxConstanst.FINANCE_STATUS_NOCHECK);

        addFinanceBeanWithoutTransactional(user, bean, true);

        return true;
    }

    @IntegrationAOP(auth = AuthConstant.FINANCE_OPR)
    @Transactional(rollbackFor = MYException.class)
    public boolean addTempFinanceBean(User user, FinanceBean bean) throws MYException {
        return addInner(user, bean, false,  true, true);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateFinanceBean(User user, FinanceBean bean) throws MYException {
        return updateInner(user, bean, true);
    }

    @IntegrationAOP(auth = AuthConstant.FINANCE_OPR)
    @Transactional(rollbackFor = MYException.class)
    public boolean updateTempFinanceBean(User user, FinanceBean bean) throws MYException {
        return updateInner(user, bean, false);
    }

    public boolean updateRefCheckByRefIdWithoutTransactional(String refId, String check) {
        List<FinanceBean> financeList = financeDAO.queryEntityBeansByFK(refId);

        for (FinanceBean financeBean : financeList) {
            financeBean.setRefChecks(check);

            financeDAO.updateEntityBean(financeBean);
        }

        return true;
    }

    /**
     * updateInner
     * 
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    private boolean updateInner(User user, FinanceBean bean, boolean mainTable) throws MYException {
        JudgeTools.judgeParameterIsNull(user, bean, bean.getItemList());

        FinanceBean old = null;

        if (mainTable) {
            old = financeDAO.find(bean.getId());
        } else {
            FinanceTempBean temp = financeTempDAO.find(bean.getId());

            if (temp == null) {
                throw new MYException("数据错误,请确认操作");
            }

            FinanceBean fb = new FinanceBean();

            BeanUtil.copyProperties(fb, temp);

            old = fb;
        }

        if (old == null) {
            throw new MYException("数据错误,请确认操作");
        }

        if (old.getStatus() == TaxConstanst.FINANCE_STATUS_CHECK
                || old.getLocks() == TaxConstanst.FINANCE_LOCK_YES) {
            throw new MYException("已经被核对(锁定)不能修改,请重新操作");
        }

        bean.setType(old.getType());
        bean.setCreateType(old.getCreateType());
        bean.setStatus(old.getStatus());
        bean.setChecks(old.getChecks());
        bean.setLogTime(old.getLogTime());
        bean.setCreaterId(old.getCreaterId());
        bean.setName(old.getName());
        bean.setMonthIndex(old.getMonthIndex());

        // 保存关联
        bean.setRefId(old.getRefId());
        bean.setRefOut(old.getRefOut());
        bean.setRefBill(old.getRefBill());
        bean.setRefStock(old.getRefStock());
        bean.setRefChecks(old.getRefChecks());

        // 标识成更改
        bean.setUpdateFlag(TaxConstanst.FINANCE_UPDATEFLAG_YES);
        // 保存最后一个修改人
        bean.setCreaterId(user.getStafferId());

        // 允许自己制定凭证日期
        if (StringTools.isNullOrNone(bean.getFinanceDate())) {
            bean.setFinanceDate(old.getFinanceDate());
        } else {
            String a = bean.getFinanceDate().substring(0, 8);
            String b = old.getFinanceDate().substring(0, 8);

            if (!a.equals(b)) {
                throw new MYException("凭证日期修改不能跨月");
            }
        }

        checkTime(bean);

        if (OATools.getManagerFlag() && StringTools.isNullOrNone(bean.getDutyId())) {
            throw new MYException("凭证必须有纳税实体的属性");
        }

        // 默认纳税实体
        if (bean.getType() == TaxConstanst.FINANCE_TYPE_MANAGER
                && StringTools.isNullOrNone(bean.getDutyId())) {
            bean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
        }

        if (bean.getType() == TaxConstanst.FINANCE_TYPE_DUTY
                && StringTools.isNullOrNone(bean.getDutyId())) {
            throw new MYException("税务凭证必须有纳税实体的属性");
        }

        DutyBean duty = dutyDAO.find(bean.getDutyId());

        bean.setType(duty.getMtype());

        List<FinanceItemBean> itemList = bean.getItemList();

        boolean isTurn = FinanceHelper.isTurnFinance(itemList);

        if (isLOCK_FINANCE() && !isTurn) {
            throw new MYException("被锁定结转,不能修改凭证");
        }

        Map<String, List<FinanceItemBean>> pareMap = new HashMap<String, List<FinanceItemBean>>();

        long inTotal = 0;

        long outTotal = 0;

        // 整理出凭证对(且校验凭证的合法性)
        for (FinanceItemBean financeItemBean : itemList) {
            financeItemBean.setId(commonDAO.getSquenceString20());

            financeItemBean.setName(financeItemBean.getId());

            financeItemBean.setPid(bean.getId());

            FinanceHelper.copyFinanceItem(bean, financeItemBean);

            financeItemBean.setLogTime(TimeTools.now());

            financeItemBean.setType(bean.getType());

            String taxId = financeItemBean.getTaxId();

            if (StringTools.isNullOrNone(taxId)) {
                throw new MYException("缺少科目信息,请确认操作");
            }

            TaxBean tax = taxDAO.find(taxId);

            if (tax == null) {
                throw new MYException("科目不存在,请确认操作");
            }

            // 必须是最小科目哦
            if (tax.getBottomFlag() != TaxConstanst.TAX_BOTTOMFLAG_ITEM) {
                throw new MYException("[%s]科目必须是最小科目,请确认操作", tax.getName() + tax.getId());
            }

            // 不是结转需要检查辅助核算项
            if (!isTurn) {
                checkItem(financeItemBean, tax, bean.isCheckOrg(), bean.isCheckUnit());
            }

            // 拷贝凭证的父级ID
            TaxHelper.copyParent(financeItemBean, tax);

            String key = financeItemBean.getPareId();

            if (pareMap.get(key) == null) {
                pareMap.put(key, new ArrayList<FinanceItemBean>());
            }

            pareMap.get(key).add(financeItemBean);

            // 必须有一个为0
            if (financeItemBean.getInmoney() * financeItemBean.getOutmoney() != 0) {
                throw new MYException("借方金额或者贷方金额不能都不为0");
            }

            inTotal += financeItemBean.getInmoney();

            outTotal += financeItemBean.getOutmoney();
        }

        bean.setInmoney(inTotal);

        bean.setOutmoney(outTotal);

        if (inTotal != outTotal) {
            throw new MYException("总借[%s],总贷[%s]不等,凭证增加错误", FinanceHelper.longToString(inTotal),
                    FinanceHelper.longToString(outTotal));
        }

        // 修改总金额可以的
        if (bean.getInmoney() != old.getInmoney()) {
            operationLog.warn("原单据金额和当前金额不等,凭证修改提示.NEW:" + bean + " // OLD:" + old);
        }

        // CORE 核对借贷必相等的原则
        checkPare(pareMap);

        if (mainTable) {
            financeDAO.updateEntityBean(bean);

            // 先删除
            financeItemDAO.deleteEntityBeansByFK(bean.getId());

            financeItemDAO.saveAllEntityBeans(itemList);
        } else {
            // 修改到临时的
            FinanceTempBean temp = new FinanceTempBean();

            BeanUtil.copyProperties(temp, bean);

            financeTempDAO.updateEntityBean(temp);

            // 先删除
            financeItemTempDAO.deleteEntityBeansByFK(bean.getId());

            for (FinanceItemBean eachItem : itemList) {
                FinanceItemTempBean tempItem = new FinanceItemTempBean();

                BeanUtil.copyProperties(tempItem, eachItem);

                financeItemTempDAO.saveEntityBean(tempItem);
            }
        }

        operationLog.info(user.getStafferName() + "修改了凭证:" + old);

        return true;
    }

    /**
     * 校验时间
     * 
     * @param bean
     * @throws MYException
     */
    private void checkTime(FinanceBean bean) throws MYException {
        // 校验凭证时间不能大于当前时间,也不能小于最近的结算时间
        if (bean.getFinanceDate().compareTo(TimeTools.now_short()) > 0) {
            throw new MYException("凭证时间不能大于[%s]", TimeTools.now_short());
        }

        String monthKey = TimeTools.changeFormat(bean.getFinanceDate(), TimeTools.SHORT_FORMAT,
                "yyyyMM");

        List<FinanceTurnBean> turnList = financeTurnDAO
                .listEntityBeansByOrder("order by monthKey desc");

        if (turnList.size() > 0) {
            FinanceTurnBean topTurn = turnList.get(0);

            if (monthKey.compareTo(topTurn.getMonthKey()) <= 0) {
                throw new MYException("[%s]已经结转,不能增加此月的凭证", topTurn.getMonthKey());
            }
        }
    }

    /**
     * checkPare
     * 
     * @param pareMap
     * @throws MYException
     */
    private void checkPare(Map<String, List<FinanceItemBean>> pareMap) throws MYException {
        // 核对借贷必相等的原则
        Set<String> keySet = pareMap.keySet();

        for (String key : keySet) {
            List<FinanceItemBean> pareList = pareMap.get(key);

            long inMoney = 0;

            long outMoney = 0;

            for (FinanceItemBean item : pareList) {
                inMoney += item.getInmoney();

                outMoney += item.getOutmoney();
            }

            if (inMoney != outMoney) {
                throw new MYException("借[%d],贷[%d]不等,凭证错误", inMoney, outMoney);
            }
        }
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteFinanceBean(User user, String id) throws MYException {
        return deleteFinanceBeanWithoutTransactional(user, id);
    }

    @IntegrationAOP(auth = AuthConstant.FINANCE_OPR)
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteTempFinanceBean(User user, String id) throws MYException {
        // 删除凭证
        financeTempDAO.deleteEntityBean(id);

        // 删除凭证项
        financeItemTempDAO.deleteEntityBeansByFK(id);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public String copyFinanceBean(User user, String id, String financeDate) throws MYException {
        JudgeTools.judgeParameterIsNull(user, id);

        FinanceBean bean = financeDAO.find(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        List<FinanceItemBean> itemList = financeItemDAO.queryEntityBeansByFK(id);

        bean.setItemList(itemList);

        bean.setFinanceDate(financeDate);

        // 重置状态
        bean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_HAND);
        bean.setStatus(TaxConstanst.FINANCE_STATUS_NOCHECK);
        bean.setLocks(TaxConstanst.FINANCE_LOCK_NO);

        bean.setRefBill("");
        bean.setRefOut("");
        bean.setRefId("");
        bean.setRefStock("");

        bean.setDescription("拷贝" + id + "生成的凭证");
        bean.setChecks("");
        bean.setCreaterId(user.getStafferId());

        addFinanceBeanWithoutTransactional(user, bean, true);

        return bean.getId();
    }

    public boolean deleteFinanceBeanWithoutTransactional(User user, String id) throws MYException {
        JudgeTools.judgeParameterIsNull(user, id);

        FinanceBean old = financeDAO.find(id);

        if (old == null) {
            throw new MYException("数据错误,请重新操作");
        }

        if (old.getLocks() == TaxConstanst.FINANCE_LOCK_YES) {
            throw new MYException("已经被锁定,不能删除,请重新操作");
        }

        // 结转的凭证不能删除
        if (old.getCreateType() == TaxConstanst.FINANCE_CREATETYPE_TURN
                || old.getCreateType() == TaxConstanst.FINANCE_CREATETYPE_PROFIT) {
            throw new MYException("结转的凭证不能被删除的,请重新操作");
        }

        // 获取凭证项
        old.setItemList(financeItemDAO.queryEntityBeansByFK(id));

        // 删除凭证
        financeDAO.deleteEntityBean(id);

        // 删除凭证项
        financeItemDAO.deleteEntityBeansByFK(id);

        // 删除需要记录操作日志
        operationLog.info(user.getStafferName() + "删除了凭证:" + old);

        // 备份到临时表里面
        old.setStatus(TaxConstanst.FINANCE_STATUS_HIDDEN);

        FinanceTempBean tmp = new FinanceTempBean();

        BeanUtil.copyProperties(tmp, old);

        List<FinanceItemBean> itemList = old.getItemList();

        List<FinanceItemTempBean> itemTempList = new ArrayList();

        for (FinanceItemBean financeItemBean : itemList) {
            FinanceItemTempBean titem = new FinanceItemTempBean();

            BeanUtil.copyProperties(titem, financeItemBean);

            itemTempList.add(titem);
        }

        financeTempDAO.saveEntityBean(tmp);

        financeItemTempDAO.saveAllEntityBeans(itemTempList);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateFinanceCheck(User user, String id, String reason) throws MYException {
        JudgeTools.judgeParameterIsNull(user, id);

        financeDAO.updateCheck(id, reason);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean checks2(User user, String id, int type, String reason) throws MYException {
        JudgeTools.judgeParameterIsNull(user, id);

        String tableName = "";

        if (type == CheckConstant.CHECK_TYPE_COMPOSE) {
            tableName = "T_CENTER_COMPOSE";
        } else if (type == CheckConstant.CHECK_TYPE_CHANGE) {
            tableName = "T_CENTER_PRICE_CHANGE";
        } else if (type == CheckConstant.CHECK_TYPE_INBILL) {
            tableName = "T_CENTER_INBILL";
        } else if (type == CheckConstant.CHECK_TYPE_OUTBILL) {
            tableName = "T_CENTER_OUTBILL";
        } else if (type == CheckConstant.CHECK_TYPE_STOCK) {
            tableName = "T_CENTER_STOCK";
        } else if (type == CheckConstant.CHECK_TYPE_BUY) {
            tableName = "T_CENTER_OUT";
        } else if (type == CheckConstant.CHECK_TYPE_CUSTOMER) {
            tableName = "T_CENTER_CUSTOMER_MAIN";
        } else if (type == CheckConstant.CHECK_TYPE_BASEBALANCE) {
            tableName = "T_CENTER_OUTBALANCE";
        } else {
            throw new MYException("数据错误,请确认操作");
        }

        checkViewDAO.updateCheck(tableName, id, reason);

        checkViewDAO.deleteEntityBean(id);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean checks(User user, String id, String reason) throws MYException {
        JudgeTools.judgeParameterIsNull(user, id);

        CheckViewBean bean = checkViewDAO.find(id);

        if (bean == null) {
            throw new MYException("数据错误,请确认操作");
        }

        return checks2(user, id, bean.getType(), reason);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteChecks(User user, String id) throws MYException {
        checkViewDAO.deleteEntityBean(id);
        return true;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Deprecated
    public boolean moveMidFinanceBeanToReleaseWithoutTrans(final String outId) throws MYException {
                
        List<FinanceMidBean> fmbList = financeMidDAO.queryRefFinanceMidBeanByCondition(outId);
        
        if (null != fmbList && fmbList.size() > 0) {
            
            FinanceMidBean financeMidBean = fmbList.get(0);

            FinanceBean financeBean = new FinanceBean();

            BeanUtil.copyProperties(financeBean, financeMidBean);

            financeBean.setFinanceDate(TimeTools.now_short());

            financeBean.setLogTime(TimeTools.now());

            financeMidDAO.deleteEntityBean(financeMidBean.getId());

            financeDAO.saveEntityBean(financeBean);

            List<FinanceItemMidBean> fimbList = financeItemMidDAO
                    .queryRefFinanceItemMidBeanByCondition(outId);

            for (FinanceItemMidBean financeItemMidBean : fimbList) {

                FinanceItemBean financeItemBean = new FinanceItemBean();

                BeanUtil.copyProperties(financeItemBean, financeItemMidBean);

                financeItemBean.setLogTime(financeBean.getLogTime());
                financeItemBean.setFinanceDate(financeBean.getFinanceDate());

                financeItemMidDAO.deleteEntityBean(financeItemMidBean.getId());

                financeItemDAO.saveEntityBean(financeItemBean);

            }
        }

        return true;
    }

    @Override
	public boolean asynQueryFinanceTax(final String begin, final String end)
			throws MYException
	{
    	JudgeTools.judgeParameterIsNull(begin, end);
    	
    	if (ASYNQUERY)
    	{
    		throw new MYException("已经有一个后台任务在运行中,请稍后再试");
    	}
    	
    	Thread athread = new Thread(){
    		public void run(){
    			triggerLog.info("asynQueryFinanceTax 开始处理...");
    			
    			TransactionTemplate tran = new TransactionTemplate(transactionManager);

    			try
    			{
    				ASYNQUERY = true;
    				
    				tran.execute(new TransactionCallback()
    				{
    					public Object doInTransaction(TransactionStatus arg0)
    					{
    						processAsynQuery(begin, end);
    						
    						return Boolean.TRUE;
    					}

    				});
    			}
    			catch (Exception e)
    			{
    				triggerLog.error(e, e);
    			}finally{
    				ASYNQUERY = false;
    				
    				triggerLog.info("asynQueryFinanceTax 处理结束 ...");
    			}
    		}
    	};

    	athread.start();
        
		return true;
	}
    
    private void processAsynQuery(String begin, String end) {
    	
    	financeShowDAO.deleteAllEntityBean();
    	
    	List<TaxBean> taxList = taxDAO.listEntityBeansByOrder("order by id");
    	
    	for (Iterator<TaxBean> iterator = taxList.iterator(); iterator.hasNext();)
        {
            TaxBean each = (TaxBean)iterator.next();

            if ( !String.valueOf(each.getLevel()).equals("0"))
            {
                iterator.remove();
            }
        }
    	
    	List<FinanceShowBean> showList = new LinkedList<FinanceShowBean>();

        // 查询
        for (TaxBean taxBean : taxList)
        {
            FinanceShowBean show = new FinanceShowBean(0);

            show.setTaxId(taxBean.getId());

            show.setTaxName(taxBean.getName());

            show.setForwardName(FinanceHelper.getForwardName(taxBean));

            // 本期借方/贷方
            ConditionParse condtion = getQueryCondition2(begin, end, 0);

            createTaxQuery(taxBean, condtion);

            long[] sumCurrMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

            show.setShowCurrInmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[0]));
            show.setShowCurrOutmoney(FinanceHelper.longToString(sumCurrMoneryByCondition[1]));

            // 期初余额
            condtion = getQueryCondition2(begin, end, 1);

            createTaxQuery(taxBean, condtion);

            FinanceItemVO head = sumHeadInner(null, condtion, taxBean);

            show.setShowBeginAllmoney(FinanceHelper.longToString(head.getLastmoney()));

            // 当前累计(从当年1月到选择的结束日期)
            condtion = getQueryCondition2(begin, end, 2);

            createTaxQuery(taxBean, condtion);

            long[] sumAllMoneryByCondition = financeItemDAO.sumMoneryByCondition(condtion);

            show.setShowAllInmoney(FinanceHelper.longToString(sumAllMoneryByCondition[0]));
            show.setShowAllOutmoney(FinanceHelper.longToString(sumAllMoneryByCondition[1]));

            // 期末余额
            long currentLast = 0L;

            if (taxBean.getForward() == TaxConstanst.TAX_FORWARD_IN)
            {
                currentLast = sumCurrMoneryByCondition[0] - sumCurrMoneryByCondition[1];
            }
            else
            {
                currentLast = sumCurrMoneryByCondition[1] - sumCurrMoneryByCondition[0];
            }

            // 期初+当期发生=期末
            show.setShowLastmoney(FinanceHelper.longToString(head.getLastmoney() + currentLast));

            showList.add(show);
        }
    	
        financeShowDAO.saveAllEntityBeans(showList);
    	
    }
    
    private ConditionParse getQueryCondition2(String begin, String end, int type)
	{
	    ConditionParse condtion = new ConditionParse();
	
	    condtion.addWhereStr();
	
	    if (type == 0)
	    {
	        String beginDate = begin;
	
	        condtion.addCondition("FinanceItemBean.financeDate", ">=", beginDate);
	
	        String endDate = end;
	
	        condtion.addCondition("FinanceItemBean.financeDate", "<=", endDate);
	    }
	
	    // 结转 开始日期前的结余(整个表查询哦)
	    if (type == 1)
	    {
	        String begin1 = parameterDAO.getString(SysConfigConstant.TAX_EXPORT_POINT);
	
	        // 开始日期前的结余
	        String beginDate = begin;
	
	        // 这里的时间是默认的
	        condtion.addCondition("FinanceItemBean.financeDate", ">=", begin1);
	
	        condtion.addCondition("FinanceItemBean.financeDate", "<", beginDate);
	    }
	
	    // 当前累计(从当年1月到选择的结束日期)
	    if (type == 2)
	    {
	        String endDate = end;
	
	        // 从当年1月
	        condtion.addCondition("FinanceItemBean.financeDate", ">=", getYearBegin(endDate
	            .substring(0, 4)
	                                                                                + "-01-01"));
	
	        condtion.addCondition("FinanceItemBean.financeDate", "<=", endDate);
	    }
	
	    return condtion;
	}
    
    /**
     * getYearBegin
     * 
     * @param begin
     * @return
     */
    private String getYearBegin(String begin)
    {
        String beginConfig = parameterDAO.getString(SysConfigConstant.TAX_EXPORT_POINT);

        if (StringTools.isNullOrNone(beginConfig))
        {
            return begin;
        }

        if (beginConfig.compareTo(begin) > 0)
        {
            return beginConfig;
        }

        return begin;
    }
    
    /**
     * createTaxQuery
     * 
     * @param taxBean
     * @param condtion
     */
    private void createTaxQuery(TaxBean taxBean, ConditionParse condtion)
    {
        condtion.addCondition("FinanceItemBean.taxId" + taxBean.getLevel(), "=", taxBean.getId());
    }
    
    /**
     * sumHeadInner
     * 
     * @param preQueryCondition
     * @param tax
     * @return
     */
    private FinanceItemVO sumHeadInner(FinanceMonthBean monthTurn,
                                       ConditionParse preQueryCondition, TaxBean tax)
    {
        // 期初余额
        long last = 0L;

        FinanceItemVO head = new FinanceItemVO();

        // 借方
        long[] sumMoneryByCondition = financeItemDAO.sumMoneryByCondition(preQueryCondition);

        if (monthTurn != null)
        {
            // 通过月结查询和增量查询
            sumMoneryByCondition[0] += monthTurn.getInmoneyAllTotal();

            sumMoneryByCondition[1] += monthTurn.getOutmoneyAllTotal();
        }

        head.setInmoney(sumMoneryByCondition[0]);

        head.setOutmoney(sumMoneryByCondition[1]);

        if (tax.getForward() == TaxConstanst.TAX_FORWARD_IN)
        {
            last = sumMoneryByCondition[0] - sumMoneryByCondition[1];
        }
        else
        {
            last = sumMoneryByCondition[1] - sumMoneryByCondition[0];
        }

        head.setTaxId(tax.getId());

        fillItemVO(head);

        // 开始日期前累计余额
        head.setLastmoney(last);

        head.setShowLastmoney(FinanceHelper.longToString(last));

        return head;
    }
    
    /**
     * fillItemVO
     * 
     * @param item
     */
    protected void fillItemVO(FinanceItemVO item)
    {
        TaxBean tax = taxDAO.find(item.getTaxId());

        item.setForward(tax.getForward());

        if (tax.getDepartment() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getDepartmentId()))
        {
            PrincipalshipBean depart = principalshipDAO.find(item.getDepartmentId());

            if (depart != null)
            {
                item.setDepartmentName(depart.getName());
            }
        }

        if (tax.getStaffer() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getStafferId()))
        {
            StafferBean sb = stafferDAO.find(item.getStafferId());

            if (sb != null)
            {
                item.setStafferName(sb.getName());
            }
        }

        if (tax.getUnit() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getUnitId()))
        {
            UnitBean unit = unitDAO.find(item.getUnitId());

            if (unit != null)
            {
                item.setUnitName(unit.getName());
            }
        }

        if (tax.getProduct() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getProductId()))
        {
            ProductBean product = productDAO.find(item.getProductId());

            if (product != null)
            {
                item.setProductName(product.getName());
                item.setProductCode(product.getCode());
            }
        }

        if (tax.getDepot() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getDepotId()))
        {
            DepotBean depot = depotDAO.find(item.getDepotId());

            if (depot != null)
            {
                item.setDepotName(depot.getName());
            }
        }

        if (tax.getDuty() == TaxConstanst.TAX_CHECK_YES
            || !StringTools.isNullOrNone(item.getDuty2Id()))
        {
            DutyBean duty2 = dutyDAO.find(item.getDuty2Id());

            if (duty2 != null)
            {
                item.setDuty2Name(duty2.getName());
            }
        }

        item.getShowInmoney();
        item.getShowOutmoney();

        long last = 0;

        if (tax.getForward() == TaxConstanst.TAX_FORWARD_IN)
        {
            last = item.getInmoney() - item.getOutmoney();
            item.setForwardName("借");
        }
        else
        {
            last = item.getOutmoney() - item.getInmoney();
            item.setForwardName("贷");
        }

        item.setLastmoney(last);

        item.setShowLastmoney(FinanceHelper.longToString(last));

        item.getShowChineseInmoney();
        item.getShowChineseOutmoney();
        item.getShowChineseLastmoney();
    }


    @Override
    public String getDkbjTaxId(String bankName) {
        if (bankName.contains("浦发银行")){
            return TaxItemConstanst.DQJK_GSDK_PF;
        } else if (bankName.contains("中信银行")){
            return TaxItemConstanst.DQJK_GSDK_ZX;
        } else if (bankName.contains("南京银行")){
            return TaxItemConstanst.DQJK_GSDK_NJ;
        } else if (bankName.contains("江苏银行")){
            return TaxItemConstanst.DQJK_GSDK_JS;
        } else if (bankName.contains("北京银行")){
            return TaxItemConstanst.DQJK_GSDK_BJ;
        } else if (bankName.contains("交通银行")){
            return TaxItemConstanst.DQJK_GSDK_JT;
        } else if (bankName.contains("宁波银行健康路支行")){
            return TaxItemConstanst.DQJK_GSDK_NB_JK;
        } else{
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void repairFinanceBeanJob() {
        _logger.info("repairFinanceBeanJob running***");
        ReaderFile reader = ReadeFileFactory.getXLSReader();
        try
        {
            InputStream is = new FileInputStream("E:\\data.xls");
            reader.readFile(is);

            while (reader.hasNext())
            {
                String[] obj = StringUtils.fillObj((String[])reader.next(),100);
                int currentNumber = reader.getCurrentLineNumber();
                if (obj.length >= 2 )
                {
                    String inbillId = obj[0];
                    System.out.println(inbillId);
                    String paymentId = obj[14];
                    System.out.println(paymentId);
                    String dutyId = obj[24];
                    System.out.println(dutyId);
                    FinanceBean financeBean = new FinanceBean();
                    String name = "系统自动补录："+paymentId;

                    financeBean.setName(name);

                    financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

                    financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BILL_GETPAY);

                    // 关联的回款单号
                    financeBean.setRefId(paymentId);
                    //关联的收款单
                    financeBean.setRefBill(inbillId);
                    financeBean.setCreaterId("系统");
                    String outId = obj[4];
                    financeBean.setRefOut(outId);
                    financeBean.setDutyId(dutyId);
                    financeBean.setDescription(financeBean.getName());

                    String logTime = obj[15];
                    System.out.println(logTime);
                    financeBean.setFinanceDate(TimeTools.getFormatDateStr(logTime));
                    financeBean.setLogTime(logTime);

                    List<FinanceItemBean> itemList = new ArrayList<>();

                    String stafferId = obj[9];
                    String customerId = obj[8];
                    String bankId = obj[3];
                    String money = obj[7];
                    System.out.println(financeBean);
                    _logger.info(financeBean);
                    // 银行对应的暂记户科目/应收账款 --> 改为直接从预收到应收账款
                    this.createAddItem3(name, bankId,stafferId, customerId,
                            MathTools.parseDouble(money), financeBean, itemList);

                    financeBean.setItemList(itemList);
                    this.addInner(null, financeBean, true, false, false);
                    _logger.info(financeBean);
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            _logger.error(e);
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        _logger.info("****repairFinanceBeanJob finished***");
    }

    private void createAddItem3(String name, String bankId, String stafferId, String customerId,
                                double money, FinanceBean financeBean,
                                List<FinanceItemBean> itemList) throws MYException {

        // 银行对应的暂记户科目（没有手续费）/应收账款
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("银行暂记户:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 预收账款(客户/职员/部门)
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.PREREVEIVE_PRODUCT);

        if (inTax == null) {
            throw new MYException("银行[%s]缺少对应的暂记户科目,请确认操作", bankId);
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = money;

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 申请人
        StafferBean staffer = stafferDAO.find(stafferId);

        if (staffer == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 辅助核算 客户/职员/部门
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(stafferId);
        itemIn.setUnitId(customerId);
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("应收账款:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 应收账款(客户/职员/部门)
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.REVEIVE_PRODUCT);

        if (outTax == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = money;

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
        itemOut.setDepartmentId(staffer.getPrincipalshipId());
        itemOut.setStafferId(stafferId);
        itemOut.setUnitId(customerId);
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemOut);
    }

    public void createFinanceItem(User user, PaymentBean bean,
                                   String itemInName,String itemOutName,
                                   String itemTaxIdIn, String itemTaxIdOut,
                                   FinanceBean financeBean, List<FinanceItemBean> itemList)
            throws MYException
    {
        // 借:
        FinanceItemBean itemIn = new FinanceItemBean();
        String pareId = commonDAO.getSquenceString();
        itemIn.setPareId(pareId);
        itemIn.setName(itemInName);
        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);
        FinanceHelper.copyFinanceItem(financeBean, itemIn);
        TaxBean inTax = taxDAO.findByUnique(itemTaxIdIn);
        if (inTax == null)
        {
            throw new MYException("[%s]缺少对应科目,请确认操作", itemTaxIdIn);
        }
        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);
        double inMoney = bean.getMoney();
        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));
        itemIn.setOutmoney(0);
        itemIn.setDescription(itemIn.getName());
        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();
        itemOut.setPareId(pareId);
        itemOut.setName(itemOutName);
        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);
        FinanceHelper.copyFinanceItem(financeBean, itemOut);
        if (itemTaxIdOut == null){
            throw new MYException("[%s]缺少银行科目,请确认操作");
        }
        TaxBean outTax = taxDAO.findByUnique(itemTaxIdOut);
        if (outTax == null)
        {
            throw new MYException("[%s]缺少科目,请确认操作", itemTaxIdOut);
        }
        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);
        double outMoney = bean.getMoney();
        itemOut.setInmoney(0);
        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));
        itemOut.setDescription(itemOut.getName());

        // 辅助核算 部门/职员/客户
        if (TaxItemConstanst.YHSXF.equals(itemTaxIdOut)
                ||TaxItemConstanst.OTHER_RECEIVE_BORROW.equals(itemTaxIdOut)
                ||TaxItemConstanst.TZSY.equals(itemTaxIdOut)){
            String stafferId = user.getStafferId();
            StafferBean staffer = this.stafferDAO.find(stafferId);
            if (staffer!= null){
                itemOut.setDepartmentId(staffer.getPrincipalshipId());
                itemOut.setStafferId(stafferId);
                itemOut.setUnitId(bean.getCustomerId());
                itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);
            }
        }

        itemList.add(itemOut);
    }

    /**
     * @return the financeDAO
     */
    public FinanceDAO getFinanceDAO() {
        return financeDAO;
    }

    /**
     * @param financeDAO the financeDAO to set
     */
    public void setFinanceDAO(FinanceDAO financeDAO) {
        this.financeDAO = financeDAO;
    }

    /**
     * @return the financeItemDAO
     */
    public FinanceItemDAO getFinanceItemDAO() {
        return financeItemDAO;
    }

    /**
     * @param financeItemDAO the financeItemDAO to set
     */
    public void setFinanceItemDAO(FinanceItemDAO financeItemDAO) {
        this.financeItemDAO = financeItemDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO() {
        return commonDAO;
    }

    /**
     * @param commonDAO the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the checkViewDAO
     */
    public CheckViewDAO getCheckViewDAO() {
        return checkViewDAO;
    }

    /**
     * @param checkViewDAO the checkViewDAO to set
     */
    public void setCheckViewDAO(CheckViewDAO checkViewDAO) {
        this.checkViewDAO = checkViewDAO;
    }

    /**
     * @return the taxDAO
     */
    public TaxDAO getTaxDAO() {
        return taxDAO;
    }

    /**
     * @param taxDAO the taxDAO to set
     */
    public void setTaxDAO(TaxDAO taxDAO) {
        this.taxDAO = taxDAO;
    }

    /**
     * @return the billManager
     */
    public BillManager getBillManager() {
        return billManager;
    }

    /**
     * @param billManager the billManager to set
     */
    public void setBillManager(BillManager billManager) {
        this.billManager = billManager;
    }

    /**
     * @return the financeTurnDAO
     */
    public FinanceTurnDAO getFinanceTurnDAO() {
        return financeTurnDAO;
    }

    /**
     * @param financeTurnDAO the financeTurnDAO to set
     */
    public void setFinanceTurnDAO(FinanceTurnDAO financeTurnDAO) {
        this.financeTurnDAO = financeTurnDAO;
    }

    /**
     * @return the financeMonthDAO
     */
    public FinanceMonthDAO getFinanceMonthDAO() {
        return financeMonthDAO;
    }

    /**
     * @param financeMonthDAO the financeMonthDAO to set
     */
    public void setFinanceMonthDAO(FinanceMonthDAO financeMonthDAO) {
        this.financeMonthDAO = financeMonthDAO;
    }

    /**
     * @return the lOCK_FINANCE
     */
    public synchronized static boolean isLOCK_FINANCE() {
        return LOCK_FINANCE;
    }

    /**
     * @param lock_finance the lOCK_FINANCE to set
     */
    public synchronized static void setLOCK_FINANCE(boolean lock_finance) {
        LOCK_FINANCE = lock_finance;
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO() {
        return dutyDAO;
    }

    /**
     * @param dutyDAO the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO) {
        this.dutyDAO = dutyDAO;
    }

    /**
     * @return the financeTempDAO
     */
    public FinanceTempDAO getFinanceTempDAO() {
        return financeTempDAO;
    }

    /**
     * @param financeTempDAO the financeTempDAO to set
     */
    public void setFinanceTempDAO(FinanceTempDAO financeTempDAO) {
        this.financeTempDAO = financeTempDAO;
    }

    /**
     * @return the financeItemTempDAO
     */
    public FinanceItemTempDAO getFinanceItemTempDAO() {
        return financeItemTempDAO;
    }

    /**
     * @param financeItemTempDAO the financeItemTempDAO to set
     */
    public void setFinanceItemTempDAO(FinanceItemTempDAO financeItemTempDAO) {
        this.financeItemTempDAO = financeItemTempDAO;
    }

    public FinanceMidDAO getFinanceMidDAO() {
        return financeMidDAO;
    }

    public void setFinanceMidDAO(FinanceMidDAO financeMidDAO) {
        this.financeMidDAO = financeMidDAO;
    }

    public FinanceItemMidDAO getFinanceItemMidDAO() {
        return financeItemMidDAO;
    }

    public void setFinanceItemMidDAO(FinanceItemMidDAO financeItemMidDAO) {
        this.financeItemMidDAO = financeItemMidDAO;
    }

    public OrgManager getOrgManager() {
        return orgManager;
    }

    public void setOrgManager(OrgManager orgManager) {
        this.orgManager = orgManager;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

    public FinanceTurnRollbackDAO getFinanceTurnRollbackDAO() {
        return financeTurnRollbackDAO;
    }

    public void setFinanceTurnRollbackDAO(FinanceTurnRollbackDAO financeTurnRollbackDAO) {
        this.financeTurnRollbackDAO = financeTurnRollbackDAO;
    }

	/**
	 * @return the parameterDAO
	 */
	public ParameterDAO getParameterDAO()
	{
		return parameterDAO;
	}

	/**
	 * @param parameterDAO the parameterDAO to set
	 */
	public void setParameterDAO(ParameterDAO parameterDAO)
	{
		this.parameterDAO = parameterDAO;
	}

	/**
	 * @return the depotDAO
	 */
	public DepotDAO getDepotDAO()
	{
		return depotDAO;
	}

	/**
	 * @param depotDAO the depotDAO to set
	 */
	public void setDepotDAO(DepotDAO depotDAO)
	{
		this.depotDAO = depotDAO;
	}

	/**
	 * @return the principalshipDAO
	 */
	public PrincipalshipDAO getPrincipalshipDAO()
	{
		return principalshipDAO;
	}

	/**
	 * @param principalshipDAO the principalshipDAO to set
	 */
	public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO)
	{
		this.principalshipDAO = principalshipDAO;
	}

	/**
	 * @return the productDAO
	 */
	public ProductDAO getProductDAO()
	{
		return productDAO;
	}

	/**
	 * @param productDAO the productDAO to set
	 */
	public void setProductDAO(ProductDAO productDAO)
	{
		this.productDAO = productDAO;
	}

	/**
	 * @return the unitDAO
	 */
	public UnitDAO getUnitDAO()
	{
		return unitDAO;
	}

	/**
	 * @param unitDAO the unitDAO to set
	 */
	public void setUnitDAO(UnitDAO unitDAO)
	{
		this.unitDAO = unitDAO;
	}

	/**
	 * @return the financeShowDAO
	 */
	public FinanceShowDAO getFinanceShowDAO()
	{
		return financeShowDAO;
	}

	/**
	 * @param financeShowDAO the financeShowDAO to set
	 */
	public void setFinanceShowDAO(FinanceShowDAO financeShowDAO)
	{
		this.financeShowDAO = financeShowDAO;
	}

    public AttachmentDAO getAttachmentDAO() {
        return attachmentDAO;
    }

    public void setAttachmentDAO(AttachmentDAO attachmentDAO) {
        this.attachmentDAO = attachmentDAO;
    }

    public void setInBillDAO(InBillDAO inBillDAO) {
        this.inBillDAO = inBillDAO;
    }

    public void setBankDAO(BankDAO bankDAO) {
        this.bankDAO = bankDAO;
    }

    /**
	 * @return the transactionManager
	 */
	public PlatformTransactionManager getTransactionManager()
	{
		return transactionManager;
	}

	/**
	 * @param transactionManager the transactionManager to set
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager)
	{
		this.transactionManager = transactionManager;
	}

	public static void main(String[] args){
	    FinanceManagerImpl imp = new FinanceManagerImpl();
	    imp.repairFinanceBeanJob();
    }
}
