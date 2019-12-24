package com.china.center.oa.finance.vo;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;

@Entity(name = "资金付款日志表")
@Table(name = "T_CENTER_PAYLISTLOG")
public class PayOrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String billNo;
	
	private String billType;
	
	private String billTypeDesc;
	
	private String billDate;
	
	private String billStatus;
	
	private String payeeBank;
	
	private String payeeBankAccName;
	
	private String payeeBankAcc;
	
	private String payeeAmount;
	
	private String remark;
	
	private String logTime;
	
	private String description;
	
	private String cityId;
	
	private String cityName;
	
	private String approveTime;
	
	private String approveName;

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getBillTypeDesc() {
		return billTypeDesc;
	}

	public void setBillTypeDesc(String billTypeDesc) {
		this.billTypeDesc = billTypeDesc;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public String getPayeeBank() {
		return payeeBank;
	}

	public void setPayeeBank(String payeeBank) {
		this.payeeBank = payeeBank;
	}

	public String getPayeeBankAccName() {
		return payeeBankAccName;
	}

	public void setPayeeBankAccName(String payeeBankAccName) {
		this.payeeBankAccName = payeeBankAccName;
	}

	public String getPayeeBankAcc() {
		return payeeBankAcc;
	}

	public void setPayeeBankAcc(String payeeBankAcc) {
		this.payeeBankAcc = payeeBankAcc;
	}

	public String getPayeeAmount() {
		return payeeAmount;
	}

	public void setPayeeAmount(String payeeAmount) {
		this.payeeAmount = payeeAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getApproveName() {
		return approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	
}
