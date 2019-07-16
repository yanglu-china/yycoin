package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryAccListBodyResp {
	
	private NbBankHeadResp headResp;
	
	private NbBankQueryAccListLoopResp loopResp;

	@XmlElement(name="head")
	public NbBankHeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(NbBankHeadResp headResp) {
		this.headResp = headResp;
	}

	@XmlElement(name="loopData")
	public NbBankQueryAccListLoopResp getLoopResp() {
		return loopResp;
	}

	public void setLoopResp(NbBankQueryAccListLoopResp loopResp) {
		this.loopResp = loopResp;
	}
	

}
