package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankTransferBodyResp {

	private NbBankHeadResp headResp;
	
	private NbBankTranferResp transferResp;

	@XmlElement(name="head")
	public NbBankHeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(NbBankHeadResp headResp) {
		this.headResp = headResp;
	}

	@XmlElement(name="map")
	public NbBankTranferResp getTransferResp() {
		return transferResp;
	}

	public void setTransferResp(NbBankTranferResp transferResp) {
		this.transferResp = transferResp;
	}
	
}
