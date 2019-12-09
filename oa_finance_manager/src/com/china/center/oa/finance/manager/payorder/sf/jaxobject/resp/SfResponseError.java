package com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class SfResponseError implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String value;

	private String code;

	@XmlValue
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@XmlAttribute(name="code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
