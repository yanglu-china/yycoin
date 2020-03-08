/**
 * File Name: BillListenerTaxGlueImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-11<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.glue.listener.impl;


import java.util.ArrayList;
import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.InBillBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.constant.FinanceConstantTw;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.listener.BillListener;
import com.china.center.oa.product.dao.ProviderDAO;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.DepartmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.bean.FinanceItemBean;
import com.china.center.oa.tax.bean.FinanceTagBean;
import com.china.center.oa.tax.bean.TaxBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.constanst.TaxItemConstanst;
import com.china.center.oa.tax.dao.TaxDAO;
import com.china.center.oa.tax.helper.FinanceHelper;
import com.china.center.oa.tax.manager.FinanceManager;
import com.china.center.oa.tax.manager.FinanceTagManager;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;


/**
 * TODO_OSGI 销售单驳回后,应收转预收/坏账取消
 * 
 * @author ZHUZHU
 * @version 2011-6-11
 * @see BillListenerTaxGlueImpl
 * @since 3.0
 */
public class BillListenerTaxGlueImpl implements BillListener
{
    private DutyDAO dutyDAO = null;

    private DepartmentDAO departmentDAO = null;

    private TaxDAO taxDAO = null;

    private CommonDAO commonDAO = null;

    private ProviderDAO providerDAO = null;

    private FinanceManager financeManager = null;

    private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;

    private BankDAO bankDAO = null;

    private StafferDAO stafferDAO = null;

    private OutBillDAO outBillDAO = null;
    
    private FinanceTagManager financeTagManager = null;
    
    /**
     * default constructor
     */
    public BillListenerTaxGlueImpl()
    {
    }

    /**
     * 销售单驳回后,应收转预收(坏账取消)
     */
    public void onFeeInReceiveToPre(User user, OutBean bean, List<InBillBean> list)
        throws MYException
    {
        for (InBillBean inBillBean : list)
        {
            if ( !TaxGlueHelper.bankGoon(inBillBean.getBankId(), this.taxDAO))
            {
                continue;
            }

            BankBean bank = bankDAO.find(inBillBean.getBankId());

            if (bank == null)
            {
                throw new MYException("银行不存在,请确认操作");
            }

            mainFinanceInPreToPay(user, bean, inBillBean, bank);
        }
    }

    /**
     * 预收转移的监听 <br>
     * 贷：张三预收-10000 贷：李四预收10000
     */
    public void onChageBillToStaffer(User user, List<InBillBean> inBillList, StafferBean target)
        throws MYException
    {
        for (InBillBean inBillBean : inBillList)
        {
            BankBean bank = bankDAO.find(inBillBean.getBankId());

            if (bank == null)
            {
                throw new MYException("银行不存在,请确认操作");
            }

            TaxGlueHelper.bankGoon(bank, this.taxDAO);

            mainChageBillToStaffer(user, target, inBillBean, bank);
            
            mainChageBillToStaffer1(user, target, inBillBean, bank);
        }

    }

    /**
     * 贷：张三预收-10000 贷：李四预收10000
     * 
     * @param user
     * @param target
     * @param inBillBean
     * @param bank
     * @throws MYException
     */
    private void mainChageBillToStaffer(User user, StafferBean target, InBillBean inBillBean,
                                        BankBean bank)
        throws MYException
    {
        StafferBean srcStaffer = stafferDAO.find(inBillBean.getOwnerId());

        if (srcStaffer == null)
        {
            // 全部从系统管理员
            srcStaffer = stafferDAO.find(StafferConstant.SUPER_STAFFER);
        }

        if (srcStaffer == null)
        {
            throw new MYException("staffer不存在："+inBillBean.getOwnerId());
        }

        FinanceBean financeBean = new FinanceBean();

        String name = "预收迁移从:" + srcStaffer.getName() + "到:" + target.getName() + '.';

        financeBean.setName(name);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BILL_CHANGE);

        // 这里也是关联的收款单号
        financeBean.setRefId(inBillBean.getId());

        financeBean.setRefBill(inBillBean.getId());

        financeBean.setDutyId(bank.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 贷：张三预收-10000 贷：李四预收10000
        createAddItem2(user, bank, target, srcStaffer, inBillBean, financeBean, itemList);
        
        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean, true);
    }
    
    /**
     * 贷：张三预收-10000 贷：李四预收10000
     * 
     * @param user
     * @param target
     * @param inBillBean
     * @param bank
     * @throws MYException
     */
    private void mainChageBillToStaffer1(User user, StafferBean target, InBillBean inBillBean,
                                        BankBean bank)
        throws MYException
    {
        StafferBean srcStaffer = stafferDAO.find(inBillBean.getOwnerId());

        if (srcStaffer == null)
        {
            // 全部从系统管理员
            srcStaffer = stafferDAO.find(StafferConstant.SUPER_STAFFER);
        }

        if (srcStaffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        FinanceBean financeBean = new FinanceBean();

        String name = "预收迁移从:" + srcStaffer.getName() + "到:" + target.getName() + '.';

        financeBean.setName(name);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BILL_CHANGE);

        // 这里也是关联的收款单号
        financeBean.setRefId(inBillBean.getId());

        financeBean.setRefBill(inBillBean.getId());

        financeBean.setDutyId(bank.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 贷：张三预收-10000 贷：李四预收10000
        createAddItem22(user, bank, target, srcStaffer, inBillBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean, true);
    }
    
    private void mainFinanceInPreToPay(User user, OutBean bean, InBillBean inBillBean, BankBean bank)
        throws MYException
    {
        FinanceBean financeBean = new FinanceBean();

        String name = "销售单驳回/删除应收转预收:" + bean.getFullId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BILL_MAYTOREAL_BACK);

        // 这里也是关联的回款单号
        financeBean.setRefId(inBillBean.getPaymentId());

        financeBean.setRefBill(inBillBean.getId());

        financeBean.setRefOut(bean.getFullId());

        financeBean.setDutyId(bank.getDutyId());

        financeBean.setCreaterId(user.getStafferId());

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 预收账款/应收账款
        createAddItem(user, bank, bean, inBillBean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean, true);
    }

    /**
     * 预收账款（负数）/应收账款（负数）
     * 
     * @param user
     * @param bank
     * @param outBean
     * @param inBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem(User user, BankBean bank, OutBean outBean, InBillBean inBillBean,
                               FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        // 申请人
        StafferBean staffer = stafferDAO.find(outBean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "销售单驳回/删除应收转预收:" + outBean.getFullId() + '.';

        // 预收账款（负数）/应收账款
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("预收账款:" + name);

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
        double inMoney = -inBillBean.getMoneys();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户/职员/部门
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(outBean.getStafferId());
        itemIn.setUnitId(outBean.getCustomerId());
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

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = -inBillBean.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
        itemOut.setDepartmentId(staffer.getPrincipalshipId());
        itemOut.setStafferId(outBean.getStafferId());
        itemOut.setUnitId(outBean.getCustomerId());
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemOut);
    }

    /**
     * 借：张三预收10000 贷：李四预收10000
     * 
     * @param user
     * @param bank
     * @param target
     * @param inBillBean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem2(User user, BankBean bank, StafferBean target,
                                StafferBean srcStaffer, InBillBean inBillBean,
                                FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        String name = financeBean.getName() + inBillBean.getId() + '.';

        // 预收账款（负数）/应收账款
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
        double inMoney = inBillBean.getMoneys();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户/职员/部门
        itemIn.setDepartmentId(srcStaffer.getPrincipalshipId());
        itemIn.setStafferId(srcStaffer.getId());
        itemIn.setUnitId(inBillBean.getCustomerId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemIn);

        // 贷方
        /*FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName(name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 预收账款(客户/职员/部门)
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.PREREVEIVE_PRODUCT);

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = inBillBean.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
        itemOut.setDepartmentId(target.getPrincipalshipId());
        itemOut.setStafferId(target.getId());
        itemOut.setUnitId(inBillBean.getCustomerId());
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);*/
        
     // 银行对应的暂记户科目（没有手续费）/(1)应收账款(2)预收账款
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行暂记户:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        TaxBean outTax = taxDAO.findTempByBankId(bank.getId());

        if (outTax == null) {
            throw new MYException("银行[%s]缺少对应的暂记户科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        // 当前发生额
        double outMoney = inBillBean.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }
    
    /**
     * 银行对应的暂记户科目/预收账款
     * 
     * @param user
     * @param bank
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem22(User user, BankBean bank, StafferBean target, StafferBean srcStaffer, InBillBean inBillBean,
            FinanceBean financeBean, List<FinanceItemBean> itemList) throws MYException {
    	
        String name = financeBean.getName() + inBillBean.getId() + '.';

        // 银行对应的暂记户科目（没有手续费）/(1)应收账款(2)预收账款
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("银行暂记户:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findTempByBankId(bank.getId());

        if (inTax == null) {
            throw new MYException("银行[%s]缺少对应的暂记户科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        // double inMoney = item.getMoneys();
        double inMoney = inBillBean.getMoneys();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 NA
        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("预收账款:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 预收账款(客户/职员/部门)
        TaxBean outTax = taxDAO.findByUnique(TaxItemConstanst.PREREVEIVE_PRODUCT);

        if (outTax == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 申请人
        StafferBean staffer = stafferDAO.find(inBillBean.getOwnerId());

        if (staffer == null) {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        // double outMoney = item.getMoneys();
        double outMoney = inBillBean.getMoneys(); // 回款全部先转成预收

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 客户/职员/部门
        itemOut.setDepartmentId(staffer.getPrincipalshipId());
        itemOut.setStafferId(inBillBean.getOwnerId());
        itemOut.setUnitId(inBillBean.getCustomerId());
        itemOut.setUnitType(TaxConstanst.UNIT_TYPE_CUSTOMER);

        itemList.add(itemOut);
    }

    @Override
    public void onCreateOutBill(User user, OutBillBean bean) throws MYException 
    {
        // 转账&&手续费
        if (bean.getType() == FinanceConstant.OUTBILL_TYPE_TRANSFER
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_HANDLING)
        {
            processOutBillTransferAndHanding(user, bean);
            
            return ;
        }
        
        // 采购付款
        else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_STOCK)
        {            
            processOutBillStock(user, bean);
            
            return;
        }

        // #371 工资
        else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SALARY)
        {
            this.processOutBillSalary(user, bean);

            return;
        }

        // #404
        else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_CGGZ
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_YYPAY
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_MATERIAL_IN
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_RE_PURCHASE
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_STAMP
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_AUCTION
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SPECIAL
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_LEAVE_EXPENSE
                //#900
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SB
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_GJJ
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_DKBJ
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_DKLXYF
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_DKLXJF
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_LCBJ
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_QSQ
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SJSDS
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SJZZS
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SJGSJH
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SJGSGZ
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SJYHS
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SJJYFJ
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SJWHF
                || bean.getType() == FinanceConstant.OUTBILL_TYPE_SJXFS
                //体外付款单
                || bean.getType() == FinanceConstantTw.OUTBILL_TYPE_GRJK_JL
                || bean.getType() == FinanceConstantTw.OUTBILL_TYPE_GRJK_BX
                || bean.getType() == FinanceConstantTw.OUTBILL_TYPE_QSQBJ
                || bean.getType() == FinanceConstantTw.OUTBILL_TYPE_JZLX
                || bean.getType() == FinanceConstantTw.OUTBILL_TYPE_FDLX
                || bean.getType() == FinanceConstantTw.OUTBILL_TYPE_JJTC
                || bean.getType() == FinanceConstantTw.OUTBILL_TYPE_ZCSFY
                || bean.getType() == FinanceConstantTw.OUTBILL_TYPE_TYJK
                )
        {
            this.processOutBill(user, bean);

            return;
        }
    
    }
    
    /**
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void processOutBillTransferAndHanding(User user, OutBillBean bean) throws MYException 
    {
        OutBillBean outBillBean = outBillDAO.find(bean.getId());

        if (outBillBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        
        BankBean bank = bankDAO.find(outBillBean.getBankId()); 
        
        if (bank == null)
        {
            throw new MYException("银行不存在,请确认操作");
        }

        BankBean destBank = null;
        // 转账要检查目的账户
        if (outBillBean.getType() == FinanceConstant.OUTBILL_TYPE_TRANSFER){
            
            destBank = bankDAO.find(outBillBean.getDestBankId());
            
            if (destBank == null){
                
                throw new MYException("转账目的银行不存在，请确认操作");
            }
        }
        
        FinanceBean financeBean = new FinanceBean();

        String name = "手续费:"+ outBillBean.getId();

        if (outBillBean.getType() == FinanceConstant.OUTBILL_TYPE_TRANSFER){
            
            name = "转账:"+ outBillBean.getId();
        }
        
        financeBean.setName(name);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BILL_OUTPAY);

        // 这里也是关联的收款单号
        financeBean.setRefId(outBillBean.getId());

        financeBean.setRefBill("");

        financeBean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);

        if (user == null){
            financeBean.setCreaterId("系统");
        } else{
            financeBean.setCreaterId(user.getStafferId());
        }


        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 借：手续费 贷：银行科目
        if (outBillBean.getType() == FinanceConstant.OUTBILL_TYPE_HANDLING){
            
            createAddItem3(user, bank, outBillBean, financeBean, itemList);
        }else
        {
            createAddItem4(user, bank, destBank, outBillBean, financeBean, itemList);
        }

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean, true);
    }
    
    private void createAddItem3(User user, BankBean bank, OutBillBean outBillBean,
            FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
        {

        // 申请人
        StafferBean staffer = stafferDAO.find(outBillBean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "手续费"+outBillBean.getId();

        // 手续费
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("资金:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.HAND_BANK_FINA);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = outBillBean.getMoneys();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 客户/职员/部门
        itemIn.setDepartmentId(staffer.getPrincipalshipId());
        itemIn.setStafferId(outBillBean.getStafferId());

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 银行科目
        TaxBean outTax = taxDAO.findByBankId(bank.getId());

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = outBillBean.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 N/A

        itemList.add(itemOut);
    
        }
    
    private void createAddItem4(User user, BankBean bank, BankBean destBank, OutBillBean outBillBean,
            FinanceBean financeBean, List<FinanceItemBean> itemList)
        throws MYException
    {
        // 申请人
        StafferBean staffer = stafferDAO.find(outBillBean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "转账"+outBillBean.getId();

        String pareId = commonDAO.getSquenceString();
        
        // 转 账
        // 贷方改为借方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 银行科目
        TaxBean outTax = taxDAO.findByBankId(destBank.getId());

        if (outTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = outBillBean.getMoneys();

        itemOut.setOutmoney(0);

        itemOut.setInmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 N/A

        itemList.add(itemOut);
        
        FinanceItemBean itemIn = new FinanceItemBean();

        itemIn.setPareId(pareId);

        itemIn.setName("财务待处理:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

//        TaxBean inTax = taxDAO.find(TaxItemConstanst.OTHERPAY_TRANSFER);
        // 银行科目 - 转账源账户
        TaxBean inTax = taxDAO.findByBankId(bank.getId());

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = outBillBean.getMoneys();

        itemIn.setOutmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setInmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 N/A

        itemList.add(itemIn);
    }

    /**
     * #404
     * @param user
     * @param bean
     * @throws MYException
     */
    private void processOutBill(User user, OutBillBean bean) throws MYException
    {

        OutBillBean outBillBean = outBillDAO.find(bean.getId());

        if (outBillBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 兼容性
        if ( !TaxGlueHelper.bankGoon(bean.getBankId(), this.taxDAO))
        {
            return;
        }

        BankBean bank = bankDAO.find(bean.getBankId());

        if (bank == null)
        {
            throw new MYException("银行不存在,请确认操作");
        }

        FinanceBean financeBean = new FinanceBean();

        String name = "";
        if (bean.getType() == FinanceConstant.OUTBILL_TYPE_CGGZ){
            name = "采购过账(人工):" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_CGGZ);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_YYPAY){
            name = "永银付款(人工):" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_YYPAY);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_MATERIAL_IN){
            name = "金银料来款(人工):" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_MATERIAL_IN);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_RE_PURCHASE){
            name = "回购(人工):" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_RE_PURCHASE);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_STAMP){
            name = "集邮走账(人工):" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_STAMP);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_AUCTION){
            name = "拍卖(人工):" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_AUCTION);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SPECIAL){
            name = "特殊订单款(人工):" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SPECIAL);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_LEAVE_EXPENSE){
            name = "离职人员报销(人工):" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_LEAVE_EXPENSE);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SB){
            name = "社保:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SB);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_GJJ){
            name = "公积金:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_GJJ);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_DKBJ){
            name = "贷款-本金:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_DKBJ);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_DKLXYF){
            name = "贷款-利息（月付）:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_DKLXYF);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_DKLXJF){
            name = "贷款-利息（季付）:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_DKLXJF);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_LCBJ){
            name = "理财-本金:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_LCBJ);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJSDS){
            name = "税金-所得税:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_SDS);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJZZS){
            name = "税金-增值税:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_ZZS);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJGSJH){
            name = "税金-个税（旧货）:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_GSJH);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJGSGZ){
            name = "税金-个税（工资）:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_GSGZ);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJYHS){
            name = "税金-印花税:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_YHS);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJJYFJ){
            name = "税金-教育附加:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_JYFJ);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJWHF){
            name = "税金-城市维护建设费:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_WHF);
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJXFS){
            name = "税金-消费税:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_XFS);
        }
        //体外付款单凭证
        else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_GRJK_JL){
            name = "个人借款-激励:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_GRJK_JL);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_GRJK_BX){
            name = "个人借款-通用费用报销:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_GRJK_BX);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_QSQBJ){
            name = "钱生钱本金:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_QSQBJ);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_JZLX){
            name = "集资利息:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_JZLX);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_FDLX){
            name = "房贷利息:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_FDLX);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_JJTC){
            name = "奖金、提成等:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_JJTC);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_ZCSFY){
            name = "总裁室费用:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_ZCSFY);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_TYJK){
            name = "体育借款:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_TYJK);
        }

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);


        // 付款单申请
        financeBean.setRefId(bean.getId());

        financeBean.setRefBill(bean.getId());

        financeBean.setDutyId(bank.getDutyId());

        if (user == null){
            financeBean.setCreaterId("系统");
        } else{
            financeBean.setCreaterId(user.getStafferId());
        }

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<>();

        this.createAddItem1(user, bank, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        //#478s
        financeBean.setCheckUnit(false);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean, true);

    }

    /**
     * #371
     * @param user
     * @param bean
     * @throws MYException
     */
    private void processOutBillSalary(User user, OutBillBean bean) throws MYException
    {

        OutBillBean outBillBean = outBillDAO.find(bean.getId());

        if (outBillBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 兼容性
        if ( !TaxGlueHelper.bankGoon(bean.getBankId(), this.taxDAO))
        {
            return;
        }

        BankBean bank = bankDAO.find(bean.getBankId());

        if (bank == null)
        {
            throw new MYException("银行不存在,请确认操作");
        }

        FinanceBean financeBean = new FinanceBean();

        String name = "资金-工资(人工):" + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_BILL_SALARY);

        // 付款单申请
        financeBean.setRefId(bean.getId());

        financeBean.setRefBill(bean.getId());

        financeBean.setDutyId(bank.getDutyId());

        if (user == null){
            financeBean.setCreaterId("系统");
        } else{
            financeBean.setCreaterId(user.getStafferId());
        }

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        this.createAddSalaryItem1(user, bank, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean, true);

    }

    /**
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void processOutBillStock(User user, OutBillBean bean) throws MYException 
    {
        
        OutBillBean outBillBean = outBillDAO.find(bean.getId());

        if (outBillBean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 兼容性
        if ( !TaxGlueHelper.bankGoon(bean.getBankId(), this.taxDAO))
        {
            return;
        }

        BankBean bank = bankDAO.find(bean.getBankId());

        if (bank == null)
        {
            throw new MYException("银行不存在,请确认操作");
        }

        FinanceBean financeBean = new FinanceBean();

        String name = "采购付款(人工):" + bean.getId() + '.';

        financeBean.setName(name);

        financeBean.setType(TaxConstanst.FINANCE_TYPE_MANAGER);

        financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_STOCK_PAY);

        // 付款单申请
        financeBean.setRefId(bean.getId());

        financeBean.setRefBill(bean.getId());

        financeBean.setDutyId(bank.getDutyId());

        if (user == null){
            financeBean.setCreaterId("系统");
        } else{
            financeBean.setCreaterId(user.getStafferId());
        }

        financeBean.setDescription(financeBean.getName());

        financeBean.setFinanceDate(TimeTools.now_short());

        financeBean.setLogTime(TimeTools.now());

        List<FinanceItemBean> itemList = new ArrayList<FinanceItemBean>();

        // 应付账款-供应商/银行科目
        createAddStockItem1(user, bank, bean, financeBean, itemList);

        financeBean.setItemList(itemList);

        financeManager.addFinanceBeanWithoutTransactional(user, financeBean, true);

    }
    
    /**
     * 
     * @param user
     * @param bank
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddStockItem1(User user, BankBean bank,OutBillBean bean, FinanceBean financeBean,
            List<FinanceItemBean> itemList)
        throws MYException
        {
        // 申请人
        StafferBean staffer = stafferDAO.find(bean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "采购付款(人工):" + bean.getId() + '.';

        // 应付账款-供应商/银行科目
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("应付账款-货款:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        // 应付账款-货款(单位)
        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.PAY_PRODUCT);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = bean.getMoneys();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 单位
        itemIn.setUnitId(bean.getProvideId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_PROVIDE);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行科目:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 银行科目
        TaxBean outTax = taxDAO.findByBankId(bean.getBankId());

        if (outTax == null)
        {
            throw new MYException("银行[%s]缺少对应的科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = bean.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }

    /**
     * #371
     * @param user
     * @param bank
     * @param bean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddSalaryItem1(User user, BankBean bank,OutBillBean bean, FinanceBean financeBean,
                                     List<FinanceItemBean> itemList)
            throws MYException
    {
        // 申请人
        StafferBean staffer = stafferDAO.find(bean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "应付职工薪酬-工资-工资-工资(人工):" + bean.getId() + '.';

        // 应付职工薪酬-工资-工资-工资-供应商/银行科目
        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName("应付职工薪酬-工资-工资-工资:" + name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);

        TaxBean inTax = taxDAO.findByUnique(TaxItemConstanst.SALARY);

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = bean.getMoneys();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 单位
        itemIn.setUnitId(bean.getProvideId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_PROVIDE);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行存款:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 银行科目
        TaxBean outTax = taxDAO.findByBankId(bean.getBankId());

        if (outTax == null)
        {
            throw new MYException("银行[%s]缺少对应的科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = bean.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }

    /**
     * #404
     * @param user
     * @param bank
     * @param bean
     * @param financeBean
     * @param itemList
     * @throws MYException
     */
    private void createAddItem1(User user, BankBean bank,OutBillBean bean, FinanceBean financeBean,
                                      List<FinanceItemBean> itemList)
            throws MYException
    {
        // 申请人
        StafferBean staffer = stafferDAO.find(bean.getStafferId());

        if (staffer == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        String name = "";
        String taxId = "";

        if (bean.getType() == FinanceConstant.OUTBILL_TYPE_CGGZ){
            name = "其他应付款-生产部:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_CGGZ);
            taxId = TaxItemConstanst.CGGZ;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_YYPAY){
            name = "其他应付款-永银付款:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_YYPAY);
            taxId = TaxItemConstanst.YYPAY;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_MATERIAL_IN){
            name = "其他应付款-金银料:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_MATERIAL_IN);
            taxId = TaxItemConstanst.MATERIAL;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_RE_PURCHASE){
            name = "其他应付款-回购:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_RE_PURCHASE);
            taxId = TaxItemConstanst.RE_PURCHASE;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_STAMP){
            name = "其他应付款-集邮付款:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_STAMP);
            taxId = TaxItemConstanst.STAMP;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_AUCTION){
            name = "其他应收款-拍卖:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_AUCTION);
            taxId = TaxItemConstanst.AUCTION;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SPECIAL){
            name = "其他应收款-特殊订单:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_SPECIAL);
            taxId = TaxItemConstanst.SPECIAL;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_LEAVE_EXPENSE){
            name = "其他应收款-离职人员报销:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_LEAVE_EXPENSE);
            taxId = TaxItemConstanst.LEAVE_EXPENSE;
        }
        //#900
        else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SB){
            name = "社保:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SB);
            taxId = TaxItemConstanst.YFZGXC_SB;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_GJJ){
            name = "公积金:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_GJJ);
            taxId = TaxItemConstanst.YFZGXC_GJJ;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_DKBJ){
            name = "贷款-本金:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_DKBJ);
            taxId = financeManager.getDkbjTaxId(bank.getName());
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_DKLXYF){
            name = "贷款-利息（月付）:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_DKLXYF);
            taxId = TaxItemConstanst.DKLX;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_DKLXJF){
            name = "贷款-利息（季付）:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_DKLXJF);
            taxId = TaxItemConstanst.YFLX;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_LCBJ){
            name = "理财-本金:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_LCBJ);
            taxId = TaxItemConstanst.QTHBZJ_JJ;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJSDS){
            name = "税金-所得税:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_SDS);
            taxId = TaxItemConstanst.YJSDS;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJZZS){
            name = "税金-增值税:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_ZZS);
            taxId = TaxItemConstanst.WJZZS;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJGSJH){
            name = "税金-个税（旧货）:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_GSJH);
            taxId = TaxItemConstanst.YJSF_GDS;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJGSGZ){
            name = "税金-个税（工资）:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_GSGZ);
            taxId = TaxItemConstanst.YJSF_GDS;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJYHS){
            name = "税金-印花税:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_YHS);
            taxId = TaxItemConstanst.YJYHS;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJJYFJ){
            name = "税金-教育附加:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_JYFJ);
            taxId = TaxItemConstanst.YJJYFFJ;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJWHF){
            name = "税金-城市维护建设费:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_WHF);
            taxId = TaxItemConstanst.YJCSWHF;
        } else if (bean.getType() == FinanceConstant.OUTBILL_TYPE_SJXFS){
            name = "税金-消费税:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_SJ_XFS);
            taxId = TaxItemConstanst.YJXFS;
        }
        //TODO 体外付款单
        else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_GRJK_JL){
            name = "个人借款-激励:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_GRJK_JL);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_GRJK_BX){
            name = "个人借款-通用费用报销:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_GRJK_BX);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_QSQBJ){
            name = "钱生钱本金:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_QSQBJ);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_JZLX){
            name = "集资利息:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_JZLX);
            taxId = TaxItemConstanst.YFLX_JZLX;
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_FDLX){
            name = "房贷利息:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_FDLX);
            taxId = TaxItemConstanst.YFLX_FDLX;
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_JJTC){
            name = "奖金、提成等:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_JJTC);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_ZCSFY){
            name = "总裁室费用:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_ZCSFY);
        }else if (bean.getType() == FinanceConstantTw.OUTBILL_TYPE_TYJK){
            name = "体育借款:" + bean.getId() + '.';
            financeBean.setCreateType(TaxConstanst.FINANCE_CREATETYPE_INNERBILL_TYJK);
            taxId = TaxItemConstanst.OTHER_YYTY;
        }

        if (StringTools.isNullOrNone(taxId)){
            throw new MYException("银行科目不存在:"+bank.getName());
        }
        TaxBean inTax = null;
        if (taxId!= null){
            inTax = taxDAO.findByUnique(taxId);
        }

        if (inTax == null)
        {
            throw new MYException("数据错误,请确认taxId:"+taxId);
        }

        FinanceItemBean itemIn = new FinanceItemBean();

        String pareId = commonDAO.getSquenceString();

        itemIn.setPareId(pareId);

        itemIn.setName( name);

        itemIn.setForward(TaxConstanst.TAX_FORWARD_IN);

        FinanceHelper.copyFinanceItem(financeBean, itemIn);



        // 科目拷贝
        FinanceHelper.copyTax(inTax, itemIn);

        // 当前发生额
        double inMoney = bean.getMoneys();

        itemIn.setInmoney(FinanceHelper.doubleToLong(inMoney));

        itemIn.setOutmoney(0);

        itemIn.setDescription(itemIn.getName());

        // 辅助核算 单位
        itemIn.setUnitId(bean.getProvideId());
        itemIn.setUnitType(TaxConstanst.UNIT_TYPE_PROVIDE);

        itemList.add(itemIn);

        // 贷方
        FinanceItemBean itemOut = new FinanceItemBean();

        itemOut.setPareId(pareId);

        itemOut.setName("银行存款:" + name);

        itemOut.setForward(TaxConstanst.TAX_FORWARD_OUT);

        FinanceHelper.copyFinanceItem(financeBean, itemOut);

        // 银行科目
        TaxBean outTax = taxDAO.findByBankId(bean.getBankId());

        if (outTax == null)
        {
            throw new MYException("银行[%s]缺少对应的科目,请确认操作", bank.getName());
        }

        // 科目拷贝
        FinanceHelper.copyTax(outTax, itemOut);

        double outMoney = bean.getMoneys();

        itemOut.setInmoney(0);

        itemOut.setOutmoney(FinanceHelper.doubleToLong(outMoney));

        itemOut.setDescription(itemOut.getName());

        // 辅助核算 NA
        itemList.add(itemOut);
    }
    
	@Override
	public void onAddInBill(User user, InBillBean inBillBean)
			throws MYException
	{
		FinanceTagBean tag = new FinanceTagBean();
		
		tag.setType("INBILL");
		tag.setTypeName("收款单生成");
		tag.setFullId(inBillBean.getId());
		tag.setStatsTime(TimeTools.now());
		
		if (inBillBean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
			if (inBillBean.getDutyId().equals(PublicConstant.DEFAULR_DUTY_ID))
			{
				tag.setTag("YSP1");
			}else{
				tag.setTag("YSP2");
			}
			
			tag.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
		}else{
			tag.setTag("YSG1");
			tag.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
		}
		
		financeTagManager.addFinanceTagBeanWithoutTransaction(user, tag);		
	}
    
    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "BillListener.TaxGlueImpl";
    }

    /**
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }

    /**
     * @return the departmentDAO
     */
    public DepartmentDAO getDepartmentDAO()
    {
        return departmentDAO;
    }

    /**
     * @param departmentDAO
     *            the departmentDAO to set
     */
    public void setDepartmentDAO(DepartmentDAO departmentDAO)
    {
        this.departmentDAO = departmentDAO;
    }

    /**
     * @return the taxDAO
     */
    public TaxDAO getTaxDAO()
    {
        return taxDAO;
    }

    /**
     * @param taxDAO
     *            the taxDAO to set
     */
    public void setTaxDAO(TaxDAO taxDAO)
    {
        this.taxDAO = taxDAO;
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
     * @return the providerDAO
     */
    public ProviderDAO getProviderDAO()
    {
        return providerDAO;
    }

    /**
     * @param providerDAO
     *            the providerDAO to set
     */
    public void setProviderDAO(ProviderDAO providerDAO)
    {
        this.providerDAO = providerDAO;
    }

    /**
     * @return the financeManager
     */
    public FinanceManager getFinanceManager()
    {
        return financeManager;
    }

    /**
     * @param financeManager
     *            the financeManager to set
     */
    public void setFinanceManager(FinanceManager financeManager)
    {
        this.financeManager = financeManager;
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }

    /**
     * @return the baseDAO
     */
    public BaseDAO getBaseDAO()
    {
        return baseDAO;
    }

    /**
     * @param baseDAO
     *            the baseDAO to set
     */
    public void setBaseDAO(BaseDAO baseDAO)
    {
        this.baseDAO = baseDAO;
    }

    /**
     * @return the bankDAO
     */
    public BankDAO getBankDAO()
    {
        return bankDAO;
    }

    /**
     * @param bankDAO
     *            the bankDAO to set
     */
    public void setBankDAO(BankDAO bankDAO)
    {
        this.bankDAO = bankDAO;
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

    public OutBillDAO getOutBillDAO() {
        return outBillDAO;
    }

    public void setOutBillDAO(OutBillDAO outBillDAO) {
        this.outBillDAO = outBillDAO;
    }

	/**
	 * @return the financeTagManager
	 */
	public FinanceTagManager getFinanceTagManager()
	{
		return financeTagManager;
	}

	/**
	 * @param financeTagManager the financeTagManager to set
	 */
	public void setFinanceTagManager(FinanceTagManager financeTagManager)
	{
		this.financeTagManager = financeTagManager;
	}
}
