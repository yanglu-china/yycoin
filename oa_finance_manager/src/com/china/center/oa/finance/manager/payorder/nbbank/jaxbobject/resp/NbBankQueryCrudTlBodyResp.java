package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryCrudTlBodyResp {
	
	private NbBankHeadResp headResp;
	
	private NbBankQueryCrudTlTotalResp totalResp;
	
	private NbBankQueryCrudTlLoopResp loopResp;

	@XmlElement(name="head")
	public NbBankHeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(NbBankHeadResp headResp) {
		this.headResp = headResp;
	}

	@XmlElement(name="map")
	public NbBankQueryCrudTlTotalResp getTotalResp() {
		return totalResp;
	}

	public void setTotalResp(NbBankQueryCrudTlTotalResp totalResp) {
		this.totalResp = totalResp;
	}

	@XmlElement(name="loopData")
	public NbBankQueryCrudTlLoopResp getLoopResp() {
		return loopResp;
	}

	public void setLoopResp(NbBankQueryCrudTlLoopResp loopResp) {
		this.loopResp = loopResp;
	}

}
