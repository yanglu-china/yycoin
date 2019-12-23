package com.china.center.oa.finance.bean;

import java.io.Serializable;

public class InvoiceBatchBackBean implements Serializable {
	
	private String stafferName;
	private String outId;
	private String invoiceNum;
	private String memo;
	private int lineNumber;

    public InvoiceBatchBackBean() {
    }

	public String getStafferName() {
		return stafferName;
	}

	public void setStafferName(String stafferName) {
		this.stafferName = stafferName;
	}

	public String getOutId() {
		return outId;
	}

	public void setOutId(String outId) {
		this.outId = outId;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("invoiceNum:"+this.invoiceNum+",");
		builder.append("memo:"+this.memo+",");
		builder.append("outId:"+this.outId+",");
		builder.append("stafferName:"+this.stafferName+",");
		return builder.toString();
	}
	
}
