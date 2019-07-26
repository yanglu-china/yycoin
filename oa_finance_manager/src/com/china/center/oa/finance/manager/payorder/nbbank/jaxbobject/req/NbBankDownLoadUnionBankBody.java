package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="body")
public class NbBankDownLoadUnionBankBody {
	
	private NbBankHead head;
	
	private String map;
	
	@XmlElement(name="head")
	public NbBankHead getHead() {
		return head;
	}

	public void setHead(NbBankHead head) {
		this.head = head;
	}

	@XmlElement(name="map")
	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

}
