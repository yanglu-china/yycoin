package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="map")
public class NbBankDownLoadUnionBankResp {

	private String fileUrl;

	@XmlElement
	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
