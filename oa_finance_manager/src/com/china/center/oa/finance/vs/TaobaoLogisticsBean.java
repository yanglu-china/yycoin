package com.china.center.oa.finance.vs;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TaobaoLogisticsBean implements Serializable {

	private String id;

	/**
	 * 物流(快递)单号
	 */
	private String transportNo;

	/**
	 * 物流公司id
	 */
	private String transport1;

	private String logisticsName;

	private String logosticsCode;

	private String customerId;

	/**
	 * 淘宝交易id
	 */
	private String citicNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTransportNo() {
		return transportNo;
	}

	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}

	public String getTransport1() {
		return transport1;
	}

	public void setTransport1(String transport1) {
		this.transport1 = transport1;
	}

	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public String getLogosticsCode() {
		return logosticsCode;
	}

	public void setLogosticsCode(String logosticsCode) {
		this.logosticsCode = logosticsCode;
	}

	public String getCiticNo() {
		return citicNo;
	}

	public void setCiticNo(String citicNo) {
		this.citicNo = citicNo;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
