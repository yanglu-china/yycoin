package com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class SfResponseService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlAttribute(name="service")
	private String service;
	
	@XmlElement(name="Head")
	private String head;
	
	@XmlElement(name="ERROR",required = false)
	private SfResponseError error;
	
	@XmlElement(name="Body",required = false)
	private SfResponseServiceBody body;
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public SfResponseError getError() {
		return error;
	}

	public void setError(SfResponseError error) {
		this.error = error;
	}

	public SfResponseServiceBody getBody() {
		return body;
	}

	public void setBody(SfResponseServiceBody body) {
		this.body = body;
	}
	
}
