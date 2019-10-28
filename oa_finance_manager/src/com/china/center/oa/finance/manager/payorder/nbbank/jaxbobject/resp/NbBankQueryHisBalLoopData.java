package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.internal.txw2.annotation.XmlElement;

@XmlRootElement(name="row")
public class NbBankQueryHisBalLoopData {

	private String bankAcc;
	
	private String accName;
	
	private String corpCode;
	
	private String corpName;
	
	private String bankName;
	
	private String curName;
	
	private String balance;
	
	private String accNameRemark;
	
	private String accAttribute;
	
	private String accTypeRemark;

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

	@XmlElement
	public String getAccNameRemark() {
		return accNameRemark;
	}

	public void setAccNameRemark(String accNameRemark) {
		this.accNameRemark = accNameRemark;
	}

	@XmlElement
	public String getAccAttribute() {
		return accAttribute;
	}

	public void setAccAttribute(String accAttribute) {
		this.accAttribute = accAttribute;
	}

	@XmlElement
	public String getAccTypeRemark() {
		return accTypeRemark;
	}

	public void setAccTypeRemark(String accTypeRemark) {
		this.accTypeRemark = accTypeRemark;
	}

	@Override
	public String toString() {
		return "NbBankQueryHisBalLoopData [bankAcc=" + bankAcc + ", accName=" + accName + ", corpCode=" + corpCode
				+ ", corpName=" + corpName + ", bankName=" + bankName + ", curName=" + curName + ", balance=" + balance
				+ ", accNameRemark=" + accNameRemark + ", accAttribute=" + accAttribute + ", accTypeRemark="
				+ accTypeRemark + "]";
	}
	
	
}
