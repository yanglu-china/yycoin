package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "body")
public class NbBankTransferBody {
	
	private NbBankHead head;
	
	private NbBankTransfer transfer;

	@XmlElement(name = "head")
	public NbBankHead getHead() {
		return head;
	}

	public void setHead(NbBankHead head) {
		this.head = head;
	}

	@XmlElement(name = "map")
	public NbBankTransfer getTransfer() {
		return transfer;
	}

	public void setTransfer(NbBankTransfer transfer) {
		this.transfer = transfer;
	}
	
}
