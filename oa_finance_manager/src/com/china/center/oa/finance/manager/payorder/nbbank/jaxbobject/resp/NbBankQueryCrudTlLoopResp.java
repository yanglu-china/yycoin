package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="loopData")
public class NbBankQueryCrudTlLoopResp {
	
	List<NbBankQueryCrudTlLoopData> loopData;

	@XmlElement(name="map")
	public List<NbBankQueryCrudTlLoopData> getLoopData() {
		return loopData;
	}

	public void setLoopData(List<NbBankQueryCrudTlLoopData> loopData) {
		this.loopData = loopData;
	}
	
	
}
