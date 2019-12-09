package com.china.center.oa.finance.manager.payorder.sf.jaxobject.req;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Body")
public class SfRequestServiceBody implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SfOrderService sfOrderService;

	@XmlElement(name = "Order")
	public SfOrderService getSfOrderService() {
		return sfOrderService;
	}

	public void setSfOrderService(SfOrderService sfOrderService) {
		this.sfOrderService = sfOrderService;
	}
	
	

}
