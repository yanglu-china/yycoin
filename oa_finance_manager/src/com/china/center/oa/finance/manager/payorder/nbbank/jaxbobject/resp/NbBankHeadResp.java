package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="head")
public class NbBankHeadResp {

	private String erpSysCode;
	
	private String custNo;
	
	private String tradeName;
	
	private String retCode;
	
	private String retMsg;
	
	private String signData;

	@XmlElement
	public String getErpSysCode() {
		return erpSysCode;
	}

	public void setErpSysCode(String erpSysCode) {
		this.erpSysCode = erpSysCode;
	}

	@XmlElement
	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	@XmlElement
	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	@XmlElement
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	@XmlElement
	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	@XmlElement
	public String getSignData() {
		return signData;
	}

	public void setSignData(String signData) {
		this.signData = signData;
	}
	
}
