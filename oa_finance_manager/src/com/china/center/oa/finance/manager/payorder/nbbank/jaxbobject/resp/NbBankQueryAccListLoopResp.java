package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="loopData")
public class NbBankQueryAccListLoopResp {
	
	private List<NbBankQueryAccListLoopData> loopData;

	
	@XmlElement(name = "map")
	public List<NbBankQueryAccListLoopData> getLoopData() {
		return loopData;
	}

	public void setLoopData(List<NbBankQueryAccListLoopData> loopData) {
		this.loopData = loopData;
	}

}
