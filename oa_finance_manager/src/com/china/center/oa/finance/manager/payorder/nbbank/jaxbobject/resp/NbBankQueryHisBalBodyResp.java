package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankQueryHisBalBodyResp {
	
	private NbBankHeadResp headResp;
	
	private NbBankQueryHisBalLoopResp loopResp;

	@XmlElement(name="head")
	public NbBankHeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(NbBankHeadResp headResp) {
		this.headResp = headResp;
	}

	@XmlElement(name="loopData")
	public NbBankQueryHisBalLoopResp getLoopResp() {
		return loopResp;
	}

	public void setLoopResp(NbBankQueryHisBalLoopResp loopResp) {
		this.loopResp = loopResp;
	}
	
	

}
