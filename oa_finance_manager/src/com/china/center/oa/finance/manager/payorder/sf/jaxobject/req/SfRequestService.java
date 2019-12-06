package com.china.center.oa.finance.manager.payorder.sf.jaxobject.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
public class SfRequestService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String service;
	
	private String lang;
	
	private String request;
	
	private String head;
	
	private SfRequestServiceBody body;

	@XmlAttribute(name = "service")
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	@XmlAttribute(name = "lang")
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@XmlElement(name = "Head")
	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	@XmlElement(name = "Body")
	public SfRequestServiceBody getBody() {
		return body;
	}

	public void setBody(SfRequestServiceBody body) {
		this.body = body;
	}

}
