package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="map")
public class NbBankQueryHisDtlTotalResp {

	private String recordTotal;

	@XmlElement
	public String getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(String recordTotal) {
		this.recordTotal = recordTotal;
	}
	
}
