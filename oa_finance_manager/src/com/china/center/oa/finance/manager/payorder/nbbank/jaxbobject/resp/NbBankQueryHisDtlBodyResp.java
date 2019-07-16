package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryHisDtlBodyResp {
	
	private NbBankHeadResp headResp;
	
	private NbBankQueryHisDtlTotalResp totalResp;
	
	private NbBankQueryHisDtlLoopResp loopResp;

	@XmlElement(name="head")
	public NbBankHeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(NbBankHeadResp headResp) {
		this.headResp = headResp;
	}

	@XmlElement(name="map")
	public NbBankQueryHisDtlTotalResp getTotalResp() {
		return totalResp;
	}

	public void setTotalResp(NbBankQueryHisDtlTotalResp totalResp) {
		this.totalResp = totalResp;
	}

	@XmlElement(name="loopData")
	public NbBankQueryHisDtlLoopResp getLoopResp() {
		return loopResp;
	}

	public void setLoopResp(NbBankQueryHisDtlLoopResp loopResp) {
		this.loopResp = loopResp;
	}

}
