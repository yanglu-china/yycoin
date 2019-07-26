package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankDownLoadUnionBankBodyResp {

	
	private NbBankHeadResp headResp;
	
	private NbBankDownLoadUnionBankResp downLoadResp;

	@XmlElement(name="head")
	public NbBankHeadResp getHeadResp() {
		return headResp;
	}

	public void setHeadResp(NbBankHeadResp headResp) {
		this.headResp = headResp;
	}

	@XmlElement(name="map")
	public NbBankDownLoadUnionBankResp getDownLoadResp() {
		return downLoadResp;
	}

	public void setDownLoadResp(NbBankDownLoadUnionBankResp downLoadResp) {
		this.downLoadResp = downLoadResp;
	}
	
	
	
}
