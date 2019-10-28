package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="loopData")
public class NbBankQueryHisBalLoopResp {

	private List<NbBankQueryHisBalLoopData> loopData;

	@XmlElement(name="row")
	public List<NbBankQueryHisBalLoopData> getLoopData() {
		return loopData;
	}

	public void setLoopData(List<NbBankQueryHisBalLoopData> loopData) {
		this.loopData = loopData;
	}
	
	
}
