package com.china.center.oa.sail.constanst;

import com.china.center.jdbc.annotation.Defined;

public interface ShipConstant
{
	/**
	 * 发货状态 初始
	 */
	@Defined(key = "shipStatus", value = "初始")
	int SHIP_STATUS_INIT = 0;
	
	/**
	 * 发货状态 已拣配
	 */
	@Defined(key = "shipStatus", value = "已拣配")
	int SHIP_STATUS_PICKUP = 1;
	
	/**
	 * 发货状态 已发货
	 */
	@Defined(key = "shipStatus", value = "已发货")
	int SHIP_STATUS_CONSIGN = 2;
	
	/**
	 * 发货状态 已打印
	 */
	@Defined(key = "shipStatus", value = "已打印")
	int SHIP_STATUS_PRINT = 3;

	/**
	 * #328
	 */
	@Defined(key = "shipStatus", value = "打印发票")
	int SHIP_STATUS_PRINT_INVOICEINS = 5;

	/**
	 * #95 快递100状态+10
	 */
	@Defined(key = "shipStatus", value = "在途")
	int SHIP_STATUS_PRINT_ZAITU = 10;

//	@Defined(key = "shipStatus", value = "揽件")
//	int SHIP_STATUS_PRINT_JIAN = 11;
//
//	@Defined(key = "shipStatus", value = "疑难")
//	int SHIP_STATUS_PRINT_PROBLEM = 12;
//
	@Defined(key = "shipStatus", value = "签收")
	int SHIP_STATUS_PRINT_SIGNED = 13;

	@Defined(key = "shipStatus", value = "退签")
	int SHIP_STATUS_PRINT_RE_SIGNED = 14;

//	@Defined(key = "shipStatus", value = "派件")
//	int SHIP_STATUS_PRINT_PAIJIAN = 15;

	@Defined(key = "shipStatus", value = "退回")
	int SHIP_STATUS_PRINT_RETURN = 16;

	/**
	 * 快递100状态
	 */
	int KD_100_STATUS_SIGNED = 3;
	int KD_100_STATUS_RE_SIGNED = 4;
	int KD_100_STATUS_RETURN = 6;

	/**
	 * #184 SF route status
	 * 50,30,607 已收件
	 */
	int SF_STATUS_50 = 50;
	int SF_STATUS_30 = 30;
	int SF_STATUS_607 = 607;

	/**
	 * 已到达目的地网点，即将派件
	 */
	int SF_STATUS_130 = 130;
	int SF_STATUS_123 = 123;

	/**
	 * 已签收
	 */
	int SF_STATUS_80 = 80;
	int SF_STATUS_8000 = 8000;

	/**
	 * 快件已退回
	 */
	int SF_STATUS_631 = 631;
	int SF_STATUS_648 = 648;
	int SF_STATUS_99 = 99;





	/**
     * 2015/3/19 "商品拣配”增加查询条件 “发票单发”
     * CK单中只有A或FP开头的单号
     */
    @Defined(key = "invoiceShipStatus", value = "发票单发")
    int INVOICE_SHIP_ALONE = 0;

    @Defined(key = "invoiceShipStatus", value = "非发票单发")
    int INVOICE_SHIP_FOLLOW_OUT = 1;

    @Defined(key = "zsShipStatus", value = "赠品单发")
    int ZS_SHIP_ALONE = 0;

    @Defined(key = "zsShipStatus", value = "混合")
    int ZS_SHIP_FOLLOW_OUT = 1;

	@Defined(key = "pickupStatus", value = "已捡配")
	int PICKUP_YES = 0;

	@Defined(key = "pickupStatus", value = "未捡配")
	int PICKUP_NO = 1;


	@Defined(key = "directStatus", value = "非直邮")
	int DIRECT_YES = 0;

	@Defined(key = "directStatus", value = "直邮")
	int DIRECT_NO = 1;

	int BANK_TYPE_PF = 1;//浦发
	int BANK_TYPE_OTHER = 2;//其他

	String CHANNEL_HJWD = "黄金微店";
	String CHANNEL_XPJD = "小浦金店";
	String CHANNEL_FHWD = "分行微店";
}
