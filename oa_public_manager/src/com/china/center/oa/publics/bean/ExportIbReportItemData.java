package com.china.center.oa.publics.bean;

import java.io.Serializable;

public class ExportIbReportItemData  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String outtype;
	
	private String staffername;
	
	private String podate;
	
	private String pay;
	
	private String paytime;
	
	private String channel;
	
	private String status;
	
	private String productcode;
	
	private String oimport_bankproduct_code;
	
    private String id = "";

    /**
     * 对应的TcpIbReportBean ID
     */
    private String refId = "";

    private String customerName = "";

    private String fullId = "";

    private String productName = "";

    private String productId = "";

    private double price = 0.0d;

    private int amount = 0;

    /**
     * 中收金额
     */
    private double ibMoney = 0.0d;

    /**
     * 激励金额
     */
    private double motivationMoney = 0.0d;

    /**
     * 中收2金额
     */
    private double ibMoney2 = 0.0d;

    /**
     * 激励2金额
     */
    private double motivationMoney2 = 0.0d;

    /**
     * 平台手续费
     */
    private double platformFee = 0.0d;

	public String getOuttype() {
		return outtype;
	}

	public void setOuttype(String outtype) {
		this.outtype = outtype;
	}

	public String getStaffername() {
		return staffername;
	}

	public void setStaffername(String staffername) {
		this.staffername = staffername;
	}

	public String getPodate() {
		return podate;
	}

	public void setPodate(String podate) {
		this.podate = podate;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public String getOimport_bankproduct_code() {
		return oimport_bankproduct_code;
	}

	public void setOimport_bankproduct_code(String oimport_bankproduct_code) {
		this.oimport_bankproduct_code = oimport_bankproduct_code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getFullId() {
		return fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getIbMoney() {
		return ibMoney;
	}

	public void setIbMoney(double ibMoney) {
		this.ibMoney = ibMoney;
	}

	public double getMotivationMoney() {
		return motivationMoney;
	}

	public void setMotivationMoney(double motivationMoney) {
		this.motivationMoney = motivationMoney;
	}

	public double getIbMoney2() {
		return ibMoney2;
	}

	public void setIbMoney2(double ibMoney2) {
		this.ibMoney2 = ibMoney2;
	}

	public double getMotivationMoney2() {
		return motivationMoney2;
	}

	public void setMotivationMoney2(double motivationMoney2) {
		this.motivationMoney2 = motivationMoney2;
	}

	public double getPlatformFee() {
		return platformFee;
	}

	public void setPlatformFee(double platformFee) {
		this.platformFee = platformFee;
	}

}
