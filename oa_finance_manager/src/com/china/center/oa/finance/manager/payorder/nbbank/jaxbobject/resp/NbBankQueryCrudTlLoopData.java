package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="map")
public class NbBankQueryCrudTlLoopData {

	private String serialId;
	
	private String bankAcc;
	
	private String bankName;
	
	private String accName;
	
	private String oppAccNo;
	
	private String oppAccName;
	
	private String oppAccBank;
	
	private String cdSign;
	
	private String amt;
	
	private String bal;
	
	private String voucherNo;
	
	private String transDate;
	
	private String cur;
	
	private String uses;
	
	private String abs;

	@XmlElement
	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	@XmlElement
	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	@XmlElement
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@XmlElement
	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	@XmlElement
	public String getOppAccNo() {
		return oppAccNo;
	}

	public void setOppAccNo(String oppAccNo) {
		this.oppAccNo = oppAccNo;
	}

	@XmlElement
	public String getOppAccName() {
		return oppAccName;
	}

	public void setOppAccName(String oppAccName) {
		this.oppAccName = oppAccName;
	}

	@XmlElement
	public String getOppAccBank() {
		return oppAccBank;
	}

	public void setOppAccBank(String oppAccBank) {
		this.oppAccBank = oppAccBank;
	}

	@XmlElement
	public String getCdSign() {
		return cdSign;
	}

	public void setCdSign(String cdSign) {
		this.cdSign = cdSign;
	}

	@XmlElement
	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	@XmlElement
	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

	@XmlElement
	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	@XmlElement
	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	@XmlElement
	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	@XmlElement
	public String getUses() {
		return uses;
	}

	public void setUses(String uses) {
		this.uses = uses;
	}

	@XmlElement
	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}
	
	@Override
	public String toString() {
		return "NbBankQueryCrudTlLoopData [serialId=" + serialId + ", bankAcc=" + bankAcc + ", bankName=" + bankName
				+ ", accName=" + accName + ", oppAccNo=" + oppAccNo + ", oppAccName=" + oppAccName + ", oppAccBank="
				+ oppAccBank + ", cdSign=" + cdSign + ", amt=" + amt + ", bal=" + bal + ", voucherNo=" + voucherNo
				+ ", transDate=" + transDate + ", cur=" + cur + ", uses=" + uses + ", abs=" + abs + "]";
	}
	
}
