package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryTransferBody {

	private NbBankHead head;
	
	private NbBankQueryTransfer queryTransfer;

	@XmlElement(name="head")
	public NbBankHead getHead() {
		return head;
	}

	public void setHead(NbBankHead head) {
		this.head = head;
	}

	@XmlElement(name="map")
	public NbBankQueryTransfer getQueryTransfer() {
		return queryTransfer;
	}

	public void setQueryTransfer(NbBankQueryTransfer queryTransfer) {
		this.queryTransfer = queryTransfer;
	}
	
}
