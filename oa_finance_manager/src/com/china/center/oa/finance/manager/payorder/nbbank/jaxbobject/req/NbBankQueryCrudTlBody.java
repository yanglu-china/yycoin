package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryCrudTlBody {
	
	private NbBankHead head;
	
	private NbBankQueryCurdTl queryCrudTl;

	@XmlElement(name="head")
	public NbBankHead getHead() {
		return head;
	}

	public void setHead(NbBankHead head) {
		this.head = head;
	}

	@XmlElement(name="map")
	public NbBankQueryCurdTl getQueryCrudTl() {
		return queryCrudTl;
	}

	public void setQueryCrudTl(NbBankQueryCurdTl queryCrudTl) {
		this.queryCrudTl = queryCrudTl;
	}
	
}
