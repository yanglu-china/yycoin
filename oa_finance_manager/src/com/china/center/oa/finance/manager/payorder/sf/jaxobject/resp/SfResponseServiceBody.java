package com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Body")
public class SfResponseServiceBody implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SfResponseOrderResponse orderResponse;

	@XmlElement(name="OrderResponse")
	public SfResponseOrderResponse getOrderResponse() {
		return orderResponse;
	}

	public void setOrderResponse(SfResponseOrderResponse orderResponse) {
		this.orderResponse = orderResponse;
	}
}
