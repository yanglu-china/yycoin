package com.china.center.oa.tcp.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "T_CENTER_MAYCUR_CONSUMESUBMIT")
public class MayCurConsumeSubmitBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String report_id;
	
	private String report_type;
	
	private String formSubType;
	
	private String name;
	
	private String date;
	
	private String amount;
	
	private String approvedAmount;
	
	private String cost_center;
	
	private String departmentBusinessCode;
	
	private String departmentName;
	
	private String departmentFullName;
	
	private String reim_user_code;
	
	private String reimUserName;
	
	private String cover_user_code;
	
	private String coverUserName;
	
	private String status;
	
	private String pay_amount;
	
	private String pay_method ;
	
	private String subsidiary_name;
	
	private String subsidiary_code;
	
	private String createdAt;
	
	private String submittedAt;
	
	private String approvedAt;
	
	private String settledAt;
	
	private String modifiedAt;
	
	private String deleteFlag;
	
	private String exportflag;
	
	private String exporttime;
	
	private String savetime;
	
	private String createflag;
	
	private String createtime;
	
	private String paymentstatus;
	
	private String paymenttime;
	
	private String oaorderid;

	public String getReport_id() {
		return report_id;
	}

	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getFormSubType() {
		return formSubType;
	}

	public void setFormSubType(String formSubType) {
		this.formSubType = formSubType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(String approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public String getCost_center() {
		return cost_center;
	}

	public void setCost_center(String cost_center) {
		this.cost_center = cost_center;
	}

	public String getDepartmentBusinessCode() {
		return departmentBusinessCode;
	}

	public void setDepartmentBusinessCode(String departmentBusinessCode) {
		this.departmentBusinessCode = departmentBusinessCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentFullName() {
		return departmentFullName;
	}

	public void setDepartmentFullName(String departmentFullName) {
		this.departmentFullName = departmentFullName;
	}

	public String getReim_user_code() {
		return reim_user_code;
	}

	public void setReim_user_code(String reim_user_code) {
		this.reim_user_code = reim_user_code;
	}

	public String getReimUserName() {
		return reimUserName;
	}

	public void setReimUserName(String reimUserName) {
		this.reimUserName = reimUserName;
	}

	public String getCover_user_code() {
		return cover_user_code;
	}

	public void setCover_user_code(String cover_user_code) {
		this.cover_user_code = cover_user_code;
	}

	public String getCoverUserName() {
		return coverUserName;
	}

	public void setCoverUserName(String coverUserName) {
		this.coverUserName = coverUserName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPay_amount() {
		return pay_amount;
	}

	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getSubsidiary_name() {
		return subsidiary_name;
	}

	public void setSubsidiary_name(String subsidiary_name) {
		this.subsidiary_name = subsidiary_name;
	}

	public String getSubsidiary_code() {
		return subsidiary_code;
	}

	public void setSubsidiary_code(String subsidiary_code) {
		this.subsidiary_code = subsidiary_code;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(String submittedAt) {
		this.submittedAt = submittedAt;
	}

	public String getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(String approvedAt) {
		this.approvedAt = approvedAt;
	}

	public String getSettledAt() {
		return settledAt;
	}

	public void setSettledAt(String settledAt) {
		this.settledAt = settledAt;
	}

	public String getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(String modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getExportflag() {
		return exportflag;
	}

	public void setExportflag(String exportflag) {
		this.exportflag = exportflag;
	}

	public String getExporttime() {
		return exporttime;
	}

	public void setExporttime(String exporttime) {
		this.exporttime = exporttime;
	}

	public String getSavetime() {
		return savetime;
	}

	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}

	public String getCreateflag() {
		return createflag;
	}

	public void setCreateflag(String createflag) {
		this.createflag = createflag;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getPaymentstatus() {
		return paymentstatus;
	}

	public void setPaymentstatus(String paymentstatus) {
		this.paymentstatus = paymentstatus;
	}

	public String getPaymenttime() {
		return paymenttime;
	}

	public void setPaymenttime(String paymenttime) {
		this.paymenttime = paymenttime;
	}

	public String getOaorderid() {
		return oaorderid;
	}

	public void setOaorderid(String oaorderid) {
		this.oaorderid = oaorderid;
	}


}
