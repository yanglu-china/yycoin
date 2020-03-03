package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.annotation.*;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.finance.constant.InvoiceinsConstants;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.constant.InvoiceConstant;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_INVOICEINS_IMPORT")
public class InvoiceinsImportBean implements Serializable
{
    public static final String INVOICE_FOLLOW_OUT = "票随货发";
    public static final String INVOICE_ALONE = "单独发";

	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String batchId = "";
	
	/**
	 * 销售单/委托结算单
	 */
	private String outId = "";
	
	private String customerId = "";
	
	/**
	 * 业务员
	 */
	private String stafferId = "";
	
	/**
	 * 0:销售 1：结算单
	 */
	private int type = 0;
	
	private double invoiceMoney = 0.0d;
	
	/**
	 * 发票号
	 * 1发票号： 1开票申请单 ： N 销售单
	 */
	private String invoiceNum = "";

	private String invoiceCode = "";

	/**
	 * #671
	 */
	private String invoiceType = InvoiceinsConstants.INVOICE_TYPE_ZZ;
    /**
     * #169 虚拟发票号
     */
    private String virtualInvoiceNum = "";
	
	/**
	 * 纳税实体全部为 永银文化， 开票人 为 纳税实体对应的人
	 */
	private String invoiceId = "";
	
	private String invoiceHead = "";
	
	/**
	 * 开票日期
	 */
	private String invoiceDate = "";
	
	/**
	 * 关联生成的开票申请
	 */
	@FK(index = AnoConstant.FK_FIRST)
	private String refInsId = "";
	
	/**
	 * 导入人
	 */
	private String stafferName = "";
	
	private String logTime = "";
	
	private int shipping = 0;
	
	/**
	 * 运输方式1
	 */
	private int transport1 = 0;
	
	/**
	 * 运输方式2
	 */
	private int transport2 = 0;
	
	/**
	 * 省
	 */
	private String provinceId = "";
	
	/**
	 * 市
	 */
	private String cityId = "";
	
	/**
	 * 区
	 */
	private String areaId = "";
	
	/**
	 * 详细地址
	 */
	private String address = "";
	
	/**
	 * 收货人
	 */
	private String receiver = "";
	
	/**
	 * 手机
	 */
	private String mobile = "";
	
	/**
	 * 固话
	 */
	private String telephone = "";
	
	/**
     * 快递运费支付方式 - deliverPay
     */
    private int expressPay = -1;
    
    /**
     * 货运运费支付方式 - deliverPay
     */
    private int transportPay = -1;
    
    private String description = "";

	/**
	 * #860 其他备注
	 */
	private String otherDescription = "";
    
    /**
     * 1:同销售单一致 2：新配送地址
     */
    private int addrType = 0;

    /**2015/1/29
     * 票随货发
     */
    private String invoiceFollowOut = "";

    /** 2016/3/2 #169
     * 产品
     */
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT)
    private String productId = "";

    private String productName = "";

    private int amount = 0;

	/**#197
	 * 增值税开票信息
	 */
	private String zzsInfo = "";

    /**
     * 是否拆分开票
     */
    private boolean splitFlag = false;

	private String spmc = "";

	/**
	 * #767 购方名称
	 */
	private String gfmc = "";

	/**
	 * 购方税号
	 */
	private String gfsh = "";


	/**
	 * 购方开户银行及银行账号
	 */
	private String gfyh = "";

	/**
	 * 购方企业地址及联系电话
	 */
	private String gfdz = "";

	/**
	 * 规格型号
	 */
	private String fpgg = "";

	/**
	 * 计量单位
	 */
	private String fpdw = "";

	/**
	 * #878标志位
	 */
	private int created = -1;

	/**
	 * 
	 */
	public InvoiceinsImportBean()
	{
	}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public boolean isSplitFlag() {
        return splitFlag;
    }

    public void setSplitFlag(boolean splitFlag) {
        this.splitFlag = splitFlag;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the batchId
	 */
	public String getBatchId()
	{
		return batchId;
	}

	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(String batchId)
	{
		this.batchId = batchId;
	}

	/**
	 * @return the outId
	 */
	public String getOutId()
	{
		return outId;
	}

	/**
	 * @param outId the outId to set
	 */
	public void setOutId(String outId)
	{
		this.outId = outId;
	}

	/**
	 * @return the invoiceMoney
	 */
	public double getInvoiceMoney()
	{
		return invoiceMoney;
	}

	/**
	 * @param invoiceMoney the invoiceMoney to set
	 */
	public void setInvoiceMoney(double invoiceMoney)
	{
		this.invoiceMoney = invoiceMoney;
	}

	/**
	 * @return the invoiceNum
	 */
	public String getInvoiceNum()
	{
		return invoiceNum;
	}

	/**
	 * @param invoiceNum the invoiceNum to set
	 */
	public void setInvoiceNum(String invoiceNum)
	{
		this.invoiceNum = invoiceNum;
	}

	/**
	 * @return the invoiceId
	 */
	public String getInvoiceId()
	{
		return invoiceId;
	}

	/**
	 * @param invoiceId the invoiceId to set
	 */
	public void setInvoiceId(String invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	/**
	 * @return the invoiceHead
	 */
	public String getInvoiceHead()
	{
		return invoiceHead;
	}

	/**
	 * @param invoiceHead the invoiceHead to set
	 */
	public void setInvoiceHead(String invoiceHead)
	{
		this.invoiceHead = invoiceHead;
	}

	/**
	 * @return the refInsId
	 */
	public String getRefInsId()
	{
		return refInsId;
	}

	/**
	 * @param refInsId the refInsId to set
	 */
	public void setRefInsId(String refInsId)
	{
		this.refInsId = refInsId;
	}

	/**
	 * @return the stafferName
	 */
	public String getStafferName()
	{
		return stafferName;
	}

	/**
	 * @param stafferName the stafferName to set
	 */
	public void setStafferName(String stafferName)
	{
		this.stafferName = stafferName;
	}

	/**
	 * @return the logTime
	 */
	public String getLogTime()
	{
		return logTime;
	}

	/**
	 * @param logTime the logTime to set
	 */
	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	/**
	 * @return the invoiceDate
	 */
	public String getInvoiceDate()
	{
		return invoiceDate;
	}

	/**
	 * @param invoiceDate the invoiceDate to set
	 */
	public void setInvoiceDate(String invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId()
	{
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId)
	{
		this.customerId = customerId;
	}

	/**
	 * @return the type
	 */
	public int getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type)
	{
		this.type = type;
	}

	/**
	 * @return the stafferId
	 */
	public String getStafferId()
	{
		return stafferId;
	}

	/**
	 * @param stafferId the stafferId to set
	 */
	public void setStafferId(String stafferId)
	{
		this.stafferId = stafferId;
	}

	public int getShipping() {
		return shipping;
	}

	public void setShipping(int shipping) {
		this.shipping = shipping;
	}

	public int getTransport1() {
		return transport1;
	}

	public void setTransport1(int transport1) {
		this.transport1 = transport1;
	}

	public int getTransport2() {
		return transport2;
	}

	public void setTransport2(int transport2) {
		this.transport2 = transport2;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public int getExpressPay() {
		return expressPay;
	}

	public void setExpressPay(int expressPay) {
		this.expressPay = expressPay;
	}

	public int getTransportPay() {
		return transportPay;
	}

	public void setTransportPay(int transportPay) {
		this.transportPay = transportPay;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the addrType
	 */
	public int getAddrType() {
		return addrType;
	}

	/**
	 * @param addrType the addrType to set
	 */
	public void setAddrType(int addrType) {
		this.addrType = addrType;
	}


    /**
     * @return the invoiceFollowOut
     */
    public String getInvoiceFollowOut() {
        return invoiceFollowOut;
    }


    /**
     * @param invoiceFollowOut the invoiceFollowOut to set
     */
    public void setInvoiceFollowOut(String invoiceFollowOut) {
        this.invoiceFollowOut = invoiceFollowOut;
    }

    public String getVirtualInvoiceNum() {
        return virtualInvoiceNum;
    }

    public void setVirtualInvoiceNum(String virtualInvoiceNum) {
        this.virtualInvoiceNum = virtualInvoiceNum;
    }

	public String getZzsInfo() {
		return zzsInfo;
	}

	public void setZzsInfo(String zzsInfo) {
		this.zzsInfo = zzsInfo;
	}

	public String getSpmc() {
		return spmc;
	}

	public void setSpmc(String spmc) {
		this.spmc = spmc;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getGfmc() {
		return gfmc;
	}

	public void setGfmc(String gfmc) {
		this.gfmc = gfmc;
	}

	public String getGfsh() {
		return gfsh;
	}

	public void setGfsh(String gfsh) {
		this.gfsh = gfsh;
	}

	public String getGfyh() {
		return gfyh;
	}

	public void setGfyh(String gfyh) {
		this.gfyh = gfyh;
	}

	public String getGfdz() {
		return gfdz;
	}

	public void setGfdz(String gfdz) {
		this.gfdz = gfdz;
	}

	public String getFpgg() {
		return fpgg;
	}

	public void setFpgg(String fpgg) {
		this.fpgg = fpgg;
	}

	public String getFpdw() {
		return fpdw;
	}

	public void setFpdw(String fpdw) {
		this.fpdw = fpdw;
	}

	public String getOtherDescription() {
		return otherDescription;
	}

	public void setOtherDescription(String otherDescription) {
		this.otherDescription = otherDescription;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "InvoiceinsImportBean{" +
				"id='" + id + '\'' +
				", batchId='" + batchId + '\'' +
				", outId='" + outId + '\'' +
				", customerId='" + customerId + '\'' +
				", stafferId='" + stafferId + '\'' +
				", type=" + type +
				", invoiceMoney=" + invoiceMoney +
				", invoiceNum='" + invoiceNum + '\'' +
				", invoiceCode='" + invoiceCode + '\'' +
				", invoiceType='" + invoiceType + '\'' +
				", virtualInvoiceNum='" + virtualInvoiceNum + '\'' +
				", invoiceId='" + invoiceId + '\'' +
				", invoiceHead='" + invoiceHead + '\'' +
				", invoiceDate='" + invoiceDate + '\'' +
				", refInsId='" + refInsId + '\'' +
				", stafferName='" + stafferName + '\'' +
				", logTime='" + logTime + '\'' +
				", shipping=" + shipping +
				", transport1=" + transport1 +
				", transport2=" + transport2 +
				", provinceId='" + provinceId + '\'' +
				", cityId='" + cityId + '\'' +
				", areaId='" + areaId + '\'' +
				", address='" + address + '\'' +
				", receiver='" + receiver + '\'' +
				", mobile='" + mobile + '\'' +
				", telephone='" + telephone + '\'' +
				", expressPay=" + expressPay +
				", transportPay=" + transportPay +
				", description='" + description + '\'' +
				", addrType=" + addrType +
				", invoiceFollowOut='" + invoiceFollowOut + '\'' +
				", productId='" + productId + '\'' +
				", productName='" + productName + '\'' +
				", amount=" + amount +
				", zzsInfo='" + zzsInfo + '\'' +
				", splitFlag=" + splitFlag +
				", spmc='" + spmc + '\'' +
				", gfmc='" + gfmc + '\'' +
				", gfsh='" + gfsh + '\'' +
				", gfyh='" + gfyh + '\'' +
				", gfdz='" + gfdz + '\'' +
				", fpgg='" + fpgg + '\'' +
				", fpdw='" + fpdw + '\'' +
				'}';
	}
}
