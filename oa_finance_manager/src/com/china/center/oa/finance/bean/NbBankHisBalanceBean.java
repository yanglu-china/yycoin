package com.china.center.oa.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_NBBANK_HISBALANCE")
public class NbBankHisBalanceBean  implements Serializable{

	@Id
	private String business_date;
	
	private String bankAcc;
	
	private String accName;
	
	private String corpCode;
	
	private String corpName;
	
	private String bankName;
	
	private String curName;
	
	private BigDecimal balance;
	
	private String accNameRemark;
	
	private String accAttribute;
	
	private String accTypeRemark;

	public String getBusiness_date() {
		return business_date;
	}

	public void setBusiness_date(String business_date) {
		this.business_date = business_date;
	}

	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCurName() {
		return curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAccNameRemark() {
		return accNameRemark;
	}

	public void setAccNameRemark(String accNameRemark) {
		this.accNameRemark = accNameRemark;
	}

	public String getAccAttribute() {
		return accAttribute;
	}

	public void setAccAttribute(String accAttribute) {
		this.accAttribute = accAttribute;
	}

	public String getAccTypeRemark() {
		return accTypeRemark;
	}

	public void setAccTypeRemark(String accTypeRemark) {
		this.accTypeRemark = accTypeRemark;
	}
	

}
