package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="loopData")
public class NbBankQueryHisDtlLoopResp {
	
	List<NbBankQueryHisDtlLoopData> loopData;

	@XmlElement(name = "map")
	public List<NbBankQueryHisDtlLoopData> getLoopData() {
		return loopData;
	}

	public void setLoopData(List<NbBankQueryHisDtlLoopData> loopData) {
		this.loopData = loopData;
	}
	

}
