package com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name="Head")
public class SfResponseHead implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value;

	@XmlValue
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
