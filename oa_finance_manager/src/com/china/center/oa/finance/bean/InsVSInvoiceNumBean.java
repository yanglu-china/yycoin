package com.china.center.oa.finance.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.finance.constant.InvoiceinsConstants;

/**
 * 开票对应
 * @author smart
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_VS_INVOICENUM")
public class InsVSInvoiceNumBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
	private String insId = "";
	
	private String invoiceNum = "";

	/** #456
	 * 发票代码
	 */
	private String invoiceCode = "";

	private String invoiceType = InvoiceinsConstants.INVOICE_TYPE_ZZ;
	
	private double moneys = 0.0d;

	public InsVSInvoiceNumBean()
	{
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the insId
	 */
	public String getInsId()
	{
		return insId;
	}

	/**
	 * @param insId the insId to set
	 */
	public void setInsId(String insId)
	{
		this.insId = insId;
	}

	/**
	 * @return the invoiceNum
	 */
	public String getInvoiceNum()
	{
		return invoiceNum;
	}

	/**
	 * @param invoiceNum the invoiceNum to set
	 */
	public void setInvoiceNum(String invoiceNum)
	{
		this.invoiceNum = invoiceNum;
	}

	/**
	 * @return the moneys
	 */
	public double getMoneys()
	{
		return moneys;
	}

	/**
	 * @param moneys the moneys to set
	 */
	public void setMoneys(double moneys)
	{
		this.moneys = moneys;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	@Override
	public String toString() {
		return "InsVSInvoiceNumBean{" +
				"id='" + id + '\'' +
				", insId='" + insId + '\'' +
				", invoiceNum='" + invoiceNum + '\'' +
				", invoiceCode='" + invoiceCode + '\'' +
				", moneys=" + moneys +
				'}';
	}
}
