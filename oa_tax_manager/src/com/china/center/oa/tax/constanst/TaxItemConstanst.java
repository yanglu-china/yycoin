/**
 * File Name: TaxItemConstanst.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-6-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.constanst;

/**
 * TaxItemConstanst
 * 
 * @author ZHUZHU
 * @version 2011-6-12
 * @see TaxItemConstanst
 * @since 3.0
 */
public interface TaxItemConstanst
{
    /**
     * 应收账款(客户/职员/部门)
     */
    String REVEIVE_PRODUCT = "1132";

    /**
     * 其它应收-坏账(职员/部门)
     */
    String BAD_REVEIVE_PRODUCT = "1133-10";

    /**
     * 其他应收款-样品(部门/职员/产品/仓库 )
     */
    String OTHER_REVEIVE_PRODUCT = "1133-14";

    /**
     * 库存商品(产品/仓库)
     */
    String DEPOR_PRODUCT = "1243";

    /**
     * 库存商品-中转(产品/仓库)
     */
    String DEPOR_PRODUCT_TEMP = "1245";

    /**
     * 委托代销商品(产品/仓库)
     */
    String CONSIGN_PRODUCT = "1261";
    
    /**
     * 委托加工物资
     */
    String CONSIGN_PROCESS = "1251";

    /**
     * 应付账款-货款(单位)
     */
    String PAY_PRODUCT = "2122-01";

    /**
     * 预收账款(客户/职员/部门)
     */
    String PREREVEIVE_PRODUCT = "2131";

    /**
     * 营业外收入
     */
    String EXT_RECEIVE = "5301";

    /**
     * 主营业务税金及附加(部门)
     */
    String MAIN_TAX = "5402";

    /**
     * 主营业务收入(部门/职员/产品/仓区)
     */
    String MAIN_RECEIVE = "5101";

    /**
     * 主营业务成本(部门/职员/产品/仓区)
     */
    String MAIN_COST = "5401";

    /**
     * 调价收入(部门)
     */
    String PRICECHANGE_REVEIVE = "5202";

    /**
     * 业务招待费-招待应酬费(部门/职员)
     */
    String RECEIVE_COMMON = "5504-005";
    
    /**
     * 营业费用-买赠费用(单位/部门/职员/纳税实体)
     */
    String RECEIVE_COMMON1 = "5504-48";

    /**
     * 营业费用-客情买赠
     */
    String YYFY_KQMZ = "5504-89";

    /**
     * 营业费用-买赠费用
     */
    String YYFY_XSMZ = "5504-88";

    /**
     * 管理费用-客情买赠
     */
    String GLFY_KQMZ = "5504-93";

    /**
     * 福利费
     */
    String FLY = "5505-003";

    /**
     * 福利费2
     */
    String FLY2 = "5504-003";

    /**
     * 集资利息
     */
    String JZLX = "5506-21";

    /**
     * 银行手续费-部门(部门/职员)
     */
    String HAND_BANK_DEPARTMENT = "5504-017";

    /**
     * 银行手续费-个人(部门/职员)
     */
    String HAND_BANK_PERSON = "5504-017";

    /**
     * 营业费用-运输费(部门/职员)
     */
    String TRAN_FEE = "5505-05";
    
    /**
     * 营业费用-运输费(部门/职员)
     */
    String TRAN_FEE1 = "5504-05";

    /**
     * 营业外支出(无)
     */
    String OTHER_PAY = "5601";

    /**
     * 本年利润(无)
     */
    String YEAR_PROFIT = "3131";

    /**
     * 未分配利润（无）
     */
    String NO_PROFIT = "3141-12";

    /**
     * 其他应收款_备用金(部门/职员)
     */
    String OTHER_RECEIVE_BORROW = "1133-01";
    
    /**
     * 预付账款
     */
    String PREPAID_ACCOUNTS= "1151";
    
    /**
     * 资金往来手续费
     */
    String HAND_BANK_FINA = "5506-07";
    
    /**
     * 主营业务收入_折扣(部门/职员)
     */
    String MAIN_RECEIVE_REBATE = "5104";
    
    /**
     * 报废 - 销售系 产品报废损失
     */
    String DROP0 = "5504-30";
    
    /**
     * 报废 - 职能系 产品报废损失
     */
    String DROP1 = "5505-30";
    
    /**
     * 报废 - 存货破损扣款
     */
    String DROP2 = "1133-06";
    
    /**
     * 报废 - 存货破损扣款/产品报废损失
     */
    String DROP3 = "1133-06/5504-30";
    
    /**
     * 合成尾差
     */
    String COMPOSEDIFF = "5505-33";
    
    /**
     * 销售费用_业务员开票税金
     */
    String SAIL_INVOICEINS_FEE = "5504-37";
    
    /**
     * 主营业务税金及附加
     */
    String MAIN_INVOICEINS = "5402";
    
    /**
     * 销售费用(部门/职员)
     */
    String SAIL_CHARGE = "5504-25";
    
    /**
     * 其它应付款-财务待处理
     */
    String OTHERPAY_TRANSFER = "2181-55";
    
    /**
     * 样品毛利准备
     */
    String SWATCH_GROSS = "1142";
    
    /**
     * 营业费用-中收
     */
    String SALE_FEE_MID = "5504-47";


    /**
     * 营业费用-激励
     */
    String SALE_FEE_MOTIVATION = "5504-0001";


    /**
     * 营业费用-平台手续费
     */
    String SALE_FEE_PLATFORM = "5504-55";

    /**
     * 体外营业费用-平台手续费
     */
    String SALE_FEE_PLATFORM_TW = "5504-56";
    
    /**
     * 预提费用
     */
    String PRE_FEE = "2191";

    /**
     * 应付职工薪酬-工资-工资-工资 #371
     */
    String SALARY = "2151-01-01-01";

    String CGGZ = "2181-99";

    String YYPAY = "2181-98";

    String MATERIAL = "2181-97";

    String RE_PURCHASE = "2181-96";

    String STAMP = "2181-95";

    String AUCTION = "1133-17";

    String SPECIAL = "1133-16";

    String LEAVE_EXPENSE = "2181-02";
    
    /**
     * 	其他应收款-个人
     */
    String TAX_OTHER_PERSON = "1133-21";

    //应付职工薪酬-社保-社保
    String YFZGXC_SB = "2151-02-01";
    //应付职工薪酬-公积金-公积金
    String YFZGXC_GJJ = "2151-03-01";
    //短期借款-公司贷款
//    String DQJK_GSDK = "2101-02";
    //浦发
    String DQJK_GSDK_PF = "2101-02-01";
    //南京
    String DQJK_GSDK_NJ = "2101-02-03";
    //江苏
    String DQJK_GSDK_JS = "2101-02-06";
    //北京
    String DQJK_GSDK_BJ = "2101-02-14";
    //中信
    String DQJK_GSDK_ZX = "2101-02-15";
    //交通
    String DQJK_GSDK_JT = "2101-02-20";
    //宁波健康
    String DQJK_GSDK_NB_JK = "2101-02-17";
    //贷款利息
    String DKLX = "5506-11";
    //应付利息
    String YFLX = "2231";
    //其他货币资金-基金
    String QTHBZJ_JJ = "1009-09";
    //应交所得税
    String YJSDS = "2171-06";
    //未交增值税
    String WJZZS = "2171-02";
    //应交税费-个调税
    String YJSF_GDS = "2171-12";
    //应交印花税
    String YJYHS = "2171-03";
    //应交教育费附加
    String YJJYFFJ = "2171-07";
    //应交城市维护建设税
    String YJCSWHF = "2171-08";
    //应交消费税
    String YJXFS = "2171-04";

    //银行存款
    String YHCK = "1002";
    //其他应付款-暂记户
//    String QTYSK_ZJH = "2181-04";

    //投资收益
    String TZSY = "5201";
}
