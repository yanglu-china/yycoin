package com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class SfResponseOrderResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String filter_result;
	
	private String destcode;
	
	private String mailno;
	
	private String origincode;
	
	private String orderid;

	private SfResponseRlsInfo rslInfo;
	
	@XmlAttribute(name="filter_result")
	public String getFilter_result() {
		return filter_result;
	}

	public void setFilter_result(String filter_result) {
		this.filter_result = filter_result;
	}

	@XmlAttribute(name="destcode")
	public String getDestcode() {
		return destcode;
	}

	public void setDestcode(String destcode) {
		this.destcode = destcode;
	}

	@XmlAttribute(name="mailno")
	public String getMailno() {
		return mailno;
	}

	public void setMailno(String mailno) {
		this.mailno = mailno;
	}

	@XmlAttribute(name="origincode")
	public String getOrigincode() {
		return origincode;
	}

	public void setOrigincode(String origincode) {
		this.origincode = origincode;
	}

	@XmlAttribute(name="orderid")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	@XmlElement(name="rls_info")
	public SfResponseRlsInfo getRslInfo() {
		return rslInfo;
	}

	public void setRslInfo(SfResponseRlsInfo rslInfo) {
		this.rslInfo = rslInfo;
	}

}
