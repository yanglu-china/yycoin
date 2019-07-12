package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="map")
public class NbBankQueryTransfer {

	/**
	 * ERP���뵥��
	 */
	private String erpReqNo;
	
	/**
	 * ����ϵͳ�����
	 */
	private String billCode;

	@XmlElement
	public String getErpReqNo() {
		return erpReqNo;
	}

	public void setErpReqNo(String erpReqNo) {
		this.erpReqNo = erpReqNo;
	}

	@XmlElement
	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	
}
