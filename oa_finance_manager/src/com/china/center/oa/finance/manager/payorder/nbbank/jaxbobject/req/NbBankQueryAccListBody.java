package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryAccListBody {
	
	private NbBankHead head;
	
	private NbBankQueryAccList queryAccList;

	@XmlElement(name="head")
	public NbBankHead getHead() {
		return head;
	}

	public void setHead(NbBankHead head) {
		this.head = head;
	}

	@XmlElement(name="map")
	public NbBankQueryAccList getQueryAccList() {
		return queryAccList;
	}

	public void setQueryAccList(NbBankQueryAccList queryAccList) {
		this.queryAccList = queryAccList;
	}

}
