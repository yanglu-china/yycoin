package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="map")
public class NbBankQueryAccListLoopData {

	private String bankAcc;
	
	private String accName;
	
	private String corpCode;
	
	private String corpName;
	
	private String bankName;
	
	private String bankType;
	
	private String curName;
	
	private String balance;

	@XmlElement
	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	@XmlElement
	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	@XmlElement
	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	@XmlElement
	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	@XmlElement
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@XmlElement
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	@XmlElement
	public String getCurName() {
		return curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}

	@XmlElement
	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "NbBankQueryAccListLoopData [bankAcc=" + bankAcc + ", accName=" + accName + ", corpCode=" + corpCode
				+ ", corpName=" + corpName + ", bankName=" + bankName + ", bankType=" + bankType + ", curName="
				+ curName + ", balance=" + balance + "]";
	}
	
}
