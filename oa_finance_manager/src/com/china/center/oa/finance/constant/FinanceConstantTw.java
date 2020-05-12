/**
 * File Name: FinanceConstant.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-12-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.constant;


import com.china.center.jdbc.annotation.Defined;


/**体外收付款单
 * FinanceConstant
 * 
 * @author smartman
 * @version 2020-03-07
 * @see FinanceConstantTw
 * @since 1.0
 */
public interface FinanceConstantTw
{

    /**
     * 销售收入
     */
    @Defined(key = "inbillTypeTw", value = "销售回款")
    int INBILL_TYPE_SAILOUT = FinanceConstant.INBILL_TYPE_SAILOUT;

    /**
     * 银行贷款(The loan is interest free)
     */
//    @Defined(key = "inbillTypeTw", value = "银行贷款")
    int INBILL_TYPE_LOAN = 1;

    /**
     * 卖出公司资产
     */
//    @Defined(key = "inbillTypeTw", value = "卖出公司资产")
    int INBILL_TYPE_ASSETS = 2;

    /**
     * 利息
     */
    @Defined(key = "inbillTypeTw", value = "利息")
    int INBILL_TYPE_INTEREST = FinanceConstant.INBILL_TYPE_INTEREST;

    /**
     * 坏账
     */
//    @Defined(key = "inbillTypeTw", value = "销售坏账")
    int INBILL_TYPE_BADOUT = 4;

    /**
     * 预收转费用
     */
//    @Defined(key = "inbillTypeTw", value = "预收转费用")
    int INBILL_TYPE_FEE = 5;

    /**
     * 个人还款
     */
    @Defined(key = "inbillTypeTw", value = "个人还款")
    int INBILL_TYPE_UNBORROW = FinanceConstant.INBILL_TYPE_UNBORROW;

    /**
     * 贷款-本金
     */
    @Defined(key = "inbillTypeTw", value = "贷款-本金")
    int INBILL_TYPE_DKBJ = FinanceConstant.INBILL_TYPE_DKBJ;

    /**
     * 理财-本金
     */
    @Defined(key = "inbillTypeTw", value = "理财-本金")
    int INBILL_TYPE_LCBJ = FinanceConstant.INBILL_TYPE_LCBJ;

    /**
     * 理财-收益
     */
    @Defined(key = "inbillTypeTw", value = "理财-收益")
    int INBILL_TYPE_LCSY = FinanceConstant.INBILL_TYPE_LCSY;

    /**
     * 钱生钱
     */
    @Defined(key = "inbillTypeTw", value = "钱生钱")
    int INBILL_TYPE_QSQ = FinanceConstant.INBILL_TYPE_QSQ;

    /**
     * 银行卡利息收入
     */
    @Defined(key = "inbillTypeTw", value = "银行卡利息收入")
    int INBILL_TYPE_YHKLXSR = FinanceConstant.INBILL_TYPE_YHKLXSR;

    /**
     * #900 20200307新增类型
     * 金银料来款
     */
    @Defined(key = "inbillTypeTw", value = "金银料来款")
    int INBILL_TYPE_JYLLK = 21;

    /**
     * 永银付款
     */
    @Defined(key = "inbillTypeTw", value = "永银付款")
    int INBILL_TYPE_YHFK= 22;

    /**
     * 积分退回
     */
    @Defined(key = "inbillTypeTw", value = "积分退回")
    int INBILL_TYPE_JFTH= 23;

    /**
     * 积分抵借款
     */
    @Defined(key = "inbillTypeTw", value = "积分抵借款")
    int INBILL_TYPE_JFDJK= 24;

    /**
     * 采购过账
     */
    @Defined(key = "inbillTypeTw", value = "采购过账")
    int INBILL_TYPE_CGGZ= 25;

    /**
     * 回购款
     */
    @Defined(key = "inbillTypeTw", value = "回购款")
    int INBILL_TYPE_HGK= 26;

    /**
     * 体育还款
     */
    @Defined(key = "inbillTypeTw", value = "体育还款")
    int INBILL_TYPE_TYHK= 27;

    /**
     * 钱生钱本金
     */
    @Defined(key = "inbillTypeTw", value = "钱生钱本金")
    int INBILL_TYPE_QSQBJ= 28;


    /**
     * 采购退款
     */
    @Defined(key = "inbillTypeTw", value = "采购退款")
    int INBILL_TYPE_PURCHASEBACK = FinanceConstant.INBILL_TYPE_PURCHASEBACK;
    
    /**
     * 转账
     */
    @Defined(key = "inbillTypeTw", value = "转账")
    int INBILL_TYPE_TRANSFER = FinanceConstant.INBILL_TYPE_TRANSFER;

    /**
     * 其他
     */
    @Defined(key = "inbillTypeTw", value = "其他")
    int INBILL_TYPE_OTHER = FinanceConstant.INBILL_TYPE_OTHER;

    


    /**
     * 采购付款
     */
    @Defined(key = "outbillTypeTw", value = "采购付款")
    int OUTBILL_TYPE_STOCK = FinanceConstant.OUTBILL_TYPE_STOCK;

    /**
     * 买固定资产
     */
//    @Defined(key = "outbillTypeTw", value = "买固定资产")
    int OUTBILL_TYPE_BUY_ASSET = 1;

    /**
     * 买低值用品(买办公品)
     */
//    @Defined(key = "outbillTypeTw", value = "买办公低值品")
    int OUTBILL_TYPE_BUY_COMMON = 2;

    /**
     * 买无形资产
     */
//    @Defined(key = "outbillTypeTw", value = "买无形资产")
    int OUTBILL_TYPE_BUY_ABS = 3;

    /**
     * 支付代摊费用
     */
//    @Defined(key = "outbillTypeTw", value = "支付代摊费用")
    int OUTBILL_TYPE_PAY_APPORTION = 4;

    /**
     * 长期股权投资
     */
//    @Defined(key = "outbillTypeTw", value = "长期股权投资")
    int OUTBILL_TYPE_BUY_STOCK = 5;

    /**
     * 个人借款
     */
//    @Defined(key = "outbillTypeTw", value = "个人借款")
    int OUTBILL_TYPE_BORROW = 6;

    /**
     * 押金和定金
     */
//    @Defined(key = "outbillTypeTw", value = "押金和定金")
    int OUTBILL_TYPE_CASH = 7;

    /**
     * 费用报销
     */
//    @Defined(key = "outbillTypeTw", value = "费用报销")
    int OUTBILL_TYPE_WIPEOUT = 8;

    /**
     * 税金
     */
//    @Defined(key = "outbillTypeTw", value = "税金")
    int OUTBILL_TYPE_DUTY = 9;

    /**
     * 销售/预收退款
     */
    @Defined(key = "outbillTypeTw", value = "销售/预收退款")
    int OUTBILL_TYPE_OUTBACK = FinanceConstant.OUTBILL_TYPE_OUTBACK;

    /**
     * 手续费
     */
    @Defined(key = "outbillTypeTw", value = "手续费")
    int OUTBILL_TYPE_HANDLING = FinanceConstant.OUTBILL_TYPE_HANDLING;

    /**
     * 资金-销售费用
     */
//    @Defined(key = "outbillTypeTw", value = "资金-销售费用")
    int OUTBILL_TYPE_SALECHARGE = 11;
    
    /**
     * 采购预付款
     */
    @Defined(key = "outbillTypeTw", value = "采购预付款")
    int OUTBILL_TYPE_STOCKPREPAY = FinanceConstant.OUTBILL_TYPE_STOCKPREPAY;

    /**
     * 工资
     */
    @Defined(key = "outbillTypeTw", value = "工资")
    int OUTBILL_TYPE_SALARY = FinanceConstant.OUTBILL_TYPE_SALARY;

    /**
     * 采购过账
     */
    @Defined(key = "outbillTypeTw", value = "采购过账")
    int OUTBILL_TYPE_CGGZ = FinanceConstant.OUTBILL_TYPE_CGGZ;

    /**
     * 永银付款
     */
    @Defined(key = "outbillTypeTw", value = "永银付款")
    int OUTBILL_TYPE_YYPAY = FinanceConstant.OUTBILL_TYPE_YYPAY;

    /**
     * 金银料来款
     */
//    @Defined(key = "outbillTypeTw", value = "金银料来款")
    int OUTBILL_TYPE_MATERIAL_IN = FinanceConstant.OUTBILL_TYPE_MATERIAL_IN;

    /**
     * 回购
     */
//    @Defined(key = "outbillTypeTw", value = "回购")
    int OUTBILL_TYPE_RE_PURCHASE = 20;

    /**
     * 集邮走账
     */
//    @Defined(key = "outbillTypeTw", value = "集邮走账")
    int OUTBILL_TYPE_STAMP = 21;

    /**
     * 拍卖
     */
//    @Defined(key = "outbillTypeTw", value = "拍卖")
    int OUTBILL_TYPE_AUCTION = 22;

    /**
     * 特殊订单款
     */
    @Defined(key = "outbillTypeTw", value = "特殊订单款")
    int OUTBILL_TYPE_SPECIAL = 23;


    /**
     * 离职人员报销
     */
//    @Defined(key = "outbillTypeTw", value = "离职人员报销")
    int OUTBILL_TYPE_LEAVE_EXPENSE = 24;

    /**
     * 社保
     */
    @Defined(key = "outbillTypeTw", value = "社保")
    int OUTBILL_TYPE_SB = 30;

    /**
     * 公积金
     */
    @Defined(key = "outbillTypeTw", value = "公积金")
    int OUTBILL_TYPE_GJJ = 31;

    /**
     * 贷款-本金
     */
    @Defined(key = "outbillTypeTw", value = "贷款-本金")
    int OUTBILL_TYPE_DKBJ = 32;

    /**
     * 贷款-利息（月付）
     */
    @Defined(key = "outbillTypeTw", value = "贷款-利息（月付）")
    int OUTBILL_TYPE_DKLXYF = 33;

    /**
     * 贷款-利息（季付）
     */
    @Defined(key = "outbillTypeTw", value = "贷款-利息（季付）")
    int OUTBILL_TYPE_DKLXJF = 34;

    /**
     * 理财-本金
     */
    @Defined(key = "outbillTypeTw", value = "理财-本金")
    int OUTBILL_TYPE_LCBJ = 35;

    /**
     * 钱生钱
     */
    @Defined(key = "outbillTypeTw", value = "钱生钱")
    int OUTBILL_TYPE_QSQ = 37;

    /**
     * 税金-所得税
     */
    @Defined(key = "outbillTypeTw", value = "税金-所得税")
    int OUTBILL_TYPE_SJSDS = 38;

    /**
     * 税金-增值税
     */
    @Defined(key = "outbillTypeTw", value = "税金-增值税")
    int OUTBILL_TYPE_SJZZS = 39;

    /**
     * 税金-个税（旧货）
     */
    @Defined(key = "outbillTypeTw", value = "税金-个税（旧货）")
    int OUTBILL_TYPE_SJGSJH = 40;

    /**
     * 税金-个税（工资）
     */
    @Defined(key = "outbillTypeTw", value = "税金-个税（工资）")
    int OUTBILL_TYPE_SJGSGZ = 41;

    /**
     * 税金-印花税
     */
    @Defined(key = "outbillTypeTw", value = "税金-印花税")
    int OUTBILL_TYPE_SJYHS = 42;

    /**
     * 税金-教育附加
     */
    @Defined(key = "outbillTypeTw", value = "税金-教育附加")
    int OUTBILL_TYPE_SJJYFJ = 43;

    /**
     * 税金-城市维护建设费
     */
    @Defined(key = "outbillTypeTw", value = "税金-城市维护建设费")
    int OUTBILL_TYPE_SJWHF = 44;

    /**
     * 税金-消费税
     */
    @Defined(key = "outbillTypeTw", value = "税金-消费税")
    int OUTBILL_TYPE_SJXFS = FinanceConstant.OUTBILL_TYPE_SJXFS;


    /**
     * 个人借款-激励
     */
    @Defined(key = "outbillTypeTw", value = "个人借款-激励")
    int OUTBILL_TYPE_GRJK_JL = 46;

    /**
     * 个人借款-通用费用报销
     */
    @Defined(key = "outbillTypeTw", value = "个人借款-通用费用报销")
    int OUTBILL_TYPE_GRJK_BX = 47;

    /**
     * 钱生钱本金
     */
    @Defined(key = "outbillTypeTw", value = "钱生钱本金")
    int OUTBILL_TYPE_QSQBJ = 48;

    /**
     * 集资利息
     */
    @Defined(key = "outbillTypeTw", value = "集资利息")
    int OUTBILL_TYPE_JZLX = 49;

    /**
     * 房贷利息
     */
    @Defined(key = "outbillTypeTw", value = "房贷利息")
    int OUTBILL_TYPE_FDLX = 50;

    /**
     * 奖金、提成等
     */
    @Defined(key = "outbillTypeTw", value = "奖金、提成等")
    int OUTBILL_TYPE_JJTC = 51;

    /**
     * 总裁室费用
     */
    @Defined(key = "outbillTypeTw", value = "总裁室费用")
    int OUTBILL_TYPE_ZCSFY = 52;

    /**
     * 体育借款
     */
    @Defined(key = "outbillTypeTw", value = "体育借款")
    int OUTBILL_TYPE_TYJK = 53;
    
    /**
     * 转账
     */
    @Defined(key = "outbillTypeTw", value = "转账")
    int OUTBILL_TYPE_TRANSFER = FinanceConstant.OUTBILL_TYPE_TRANSFER;

    /**
     * 其他
     */
    @Defined(key = "outbillTypeTw", value = "其他")
    int OUTBILL_TYPE_OTHER = FinanceConstant.OUTBILL_TYPE_OTHER;


}
