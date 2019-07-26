package com.china.center.oa.finance.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_NBBANK_HISDATA")
public class NbBankHisDataBean  implements Serializable{

	@Id
	private String serialId;
	
	private String bankAcc;
	
	private String bankName;
	
	private String accName;
	
	private String oppAccNo;
	
	private String oppAccName;
	
	private String oppAccBank;
	
	private String cdSign;
	
	private BigDecimal amt;
	
	private BigDecimal bal;
	
	private String voucherNo;
	
	private String transDate;
	
	private String cur;
	
	private String uses;
	
	private String remark;

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getOppAccNo() {
		return oppAccNo;
	}

	public void setOppAccNo(String oppAccNo) {
		this.oppAccNo = oppAccNo;
	}

	public String getOppAccName() {
		return oppAccName;
	}

	public void setOppAccName(String oppAccName) {
		this.oppAccName = oppAccName;
	}

	public String getOppAccBank() {
		return oppAccBank;
	}

	public void setOppAccBank(String oppAccBank) {
		this.oppAccBank = oppAccBank;
	}

	public String getCdSign() {
		return cdSign;
	}

	public void setCdSign(String cdSign) {
		this.cdSign = cdSign;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getCur() {
		return cur;
	}

	public void setCur(String cur) {
		this.cur = cur;
	}

	public String getUses() {
		return uses;
	}

	public void setUses(String uses) {
		this.uses = uses;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
