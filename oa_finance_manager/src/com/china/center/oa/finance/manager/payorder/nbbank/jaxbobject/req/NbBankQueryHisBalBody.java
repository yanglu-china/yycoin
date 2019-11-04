package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryHisBalBody {
	
	private NbBankHead head;
	
	private NbBankQueryHisBal queryHisBal;

	@XmlElement(name="head")
	public NbBankHead getHead() {
		return head;
	}

	public void setHead(NbBankHead head) {
		this.head = head;
	}

	@XmlElement(name="map")
	public NbBankQueryHisBal getQueryHisBal() {
		return queryHisBal;
	}

	public void setQueryHisBal(NbBankQueryHisBal queryHisBal) {
		this.queryHisBal = queryHisBal;
	}

	
}
