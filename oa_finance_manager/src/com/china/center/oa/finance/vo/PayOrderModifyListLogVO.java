package com.china.center.oa.finance.vo;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;

@Entity(name = "资金付款数据修改历史表")
@Table(name = "T_CENTER_PAYLISTMODIFYLOG")
public class PayOrderModifyListLogVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String outid;
	
	private String type;
	
	private String originalBankName;
	
	private String originalUserName;
	
	private String originalBankNo;
	
	private String newBankName;
	
	private String newUserName;
	
	private String newBankNo;
	
	private String money;
	
	private String originalProvince;
	
	private String originalCity;
	
	private String newProvince;
	
	private String newCity;
	
	private String description; 
	
	private String outidtime;
	
	private String status;
	
	private String outbillid;
	
	private String operator;
	
	private String paytime;
	
	private String payaccount;
	
	private String paybank;
	
	private String bankstatus;
	
	private String bankpaytime;
	
	private String payBankId;
	
	private String operatorId;
	
	private String message;
	
	private String updateTime;
	
	private String modifyStafferId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOutid() {
		return outid;
	}

	public void setOutid(String outid) {
		this.outid = outid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getOriginalProvince() {
		return originalProvince;
	}

	public void setOriginalProvince(String originalProvince) {
		this.originalProvince = originalProvince;
	}

	public String getOriginalCity() {
		return originalCity;
	}

	public void setOriginalCity(String originalCity) {
		this.originalCity = originalCity;
	}

	public String getNewProvince() {
		return newProvince;
	}

	public void setNewProvince(String newProvince) {
		this.newProvince = newProvince;
	}

	public String getNewCity() {
		return newCity;
	}

	public void setNewCity(String newCity) {
		this.newCity = newCity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOutidtime() {
		return outidtime;
	}

	public void setOutidtime(String outidtime) {
		this.outidtime = outidtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutbillid() {
		return outbillid;
	}

	public void setOutbillid(String outbillid) {
		this.outbillid = outbillid;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPaytime() {
		return paytime;
	}

	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}

	public String getPayaccount() {
		return payaccount;
	}

	public void setPayaccount(String payaccount) {
		this.payaccount = payaccount;
	}

	public String getPaybank() {
		return paybank;
	}

	public void setPaybank(String paybank) {
		this.paybank = paybank;
	}

	public String getBankstatus() {
		return bankstatus;
	}

	public void setBankstatus(String bankstatus) {
		this.bankstatus = bankstatus;
	}

	public String getBankpaytime() {
		return bankpaytime;
	}

	public void setBankpaytime(String bankpaytime) {
		this.bankpaytime = bankpaytime;
	}

	public String getPayBankId() {
		return payBankId;
	}

	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOriginalBankName() {
		return originalBankName;
	}

	public void setOriginalBankName(String originalBankName) {
		this.originalBankName = originalBankName;
	}

	public String getOriginalUserName() {
		return originalUserName;
	}

	public void setOriginalUserName(String originalUserName) {
		this.originalUserName = originalUserName;
	}

	public String getOriginalBankNo() {
		return originalBankNo;
	}

	public void setOriginalBankNo(String originalBankNo) {
		this.originalBankNo = originalBankNo;
	}

	public String getNewBankName() {
		return newBankName;
	}

	public void setNewBankName(String newBankName) {
		this.newBankName = newBankName;
	}

	public String getNewUserName() {
		return newUserName;
	}

	public void setNewUserName(String newUserName) {
		this.newUserName = newUserName;
	}

	public String getNewBankNo() {
		return newBankNo;
	}

	public void setNewBankNo(String newBankNo) {
		this.newBankNo = newBankNo;
	}

	public String getModifyStafferId() {
		return modifyStafferId;
	}

	public void setModifyStafferId(String modifyStafferId) {
		this.modifyStafferId = modifyStafferId;
	}

}
