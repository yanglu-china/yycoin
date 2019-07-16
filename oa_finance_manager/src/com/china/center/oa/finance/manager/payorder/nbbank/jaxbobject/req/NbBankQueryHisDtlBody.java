package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryHisDtlBody {
	
	private NbBankHead head;
	
	private NbBankQueryHisDtl queryHisDtl;

	@XmlElement(name = "head")
	public NbBankHead getHead() {
		return head;
	}

	public void setHead(NbBankHead head) {
		this.head = head;
	}

	@XmlElement(name = "map")
	public NbBankQueryHisDtl getQueryHisDtl() {
		return queryHisDtl;
	}

	public void setQueryHisDtl(NbBankQueryHisDtl queryHisDtl) {
		this.queryHisDtl = queryHisDtl;
	}

}
