package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryTransferBodyResp {
	
	private NbBankHeadResp headResp;
	
	private NbBankQueryTransferResp queryTransferResp;

	@XmlElement(name="head")
	public NbBankHeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(NbBankHeadResp headResp) {
		this.headResp = headResp;
	}

	@XmlElement(name="map")
	public NbBankQueryTransferResp getQueryTransferResp() {
		return queryTransferResp;
	}

	public void setQueryTransferResp(NbBankQueryTransferResp queryTransferResp) {
		this.queryTransferResp = queryTransferResp;
	}
	
	
}
