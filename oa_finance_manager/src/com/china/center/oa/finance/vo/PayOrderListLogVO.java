package com.china.center.oa.finance.vo;

import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Ignore;
import com.china.center.oa.publics.bean.AttachmentBean;

public class PayOrderListLogVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String outid;
	
	private String type;
	
	private String bankName;
	
	private String userName;
	
	private String bankNo;
	
	private String money;
	
	private String province;
	
	private String city;
	
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
	
	 /**
	  *	 附件列表
     */
    @Ignore
    private List<AttachmentBean> attachmentList;

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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public List<AttachmentBean> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<AttachmentBean> attachmentList) {
		this.attachmentList = attachmentList;
	}

}
