package com.china.center.oa.publics.bean;

import java.io.Serializable;

import com.china.center.oa.tcp.bean.TcpIbReportItemBean;

public class ExportIbReportItemData extends TcpIbReportItemBean implements Serializable {

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

}
