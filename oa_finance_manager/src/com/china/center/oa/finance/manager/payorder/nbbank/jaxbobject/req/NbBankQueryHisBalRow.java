package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "row")
public class NbBankQueryHisBalRow {

	private String bankAcc;

	@XmlElement(name="bankAcc")
	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}
	
}
