package com.china.center.oa.sail.wrap;

import java.io.Serializable;

import com.china.center.oa.sail.bean.OutBean;

public class Sample2OrderWrap implements Serializable
{
	/** 领样转销售；领样转领样；领样转赠送 */
	
	private String typeName = "";
	
	/** 领样单号  */
	private String sampleId = "";
	
	private OutBean sampleOut = null;
	
	/** 被转业务员 */
	private String srcStaffer = "";
	
	/** 转业务员 */
	private String destStaffer = "";
	
	/** 转新订单 */
	private String destOrderId = "";

	private OutBean destOut = null;
	
	/** 转数量 */
	private int amount = 0;

	/** 转新订单数量 */
	private int destOrderAmount = 0;
	
	public Sample2OrderWrap()
	{
		
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public String getSrcStaffer() {
		return srcStaffer;
	}

	public void setSrcStaffer(String srcStaffer) {
		this.srcStaffer = srcStaffer;
	}



	public String getDestStaffer() {
		return destStaffer;
	}

	public void setDestStaffer(String destStaffer) {
		this.destStaffer = destStaffer;
	}

	public String getDestOrderId() {
		return destOrderId;
	}

	public void setDestOrderId(String destOrderId) {
		this.destOrderId = destOrderId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getDestOrderAmount() {
		return destOrderAmount;
	}

	public void setDestOrderAmount(int destOrderAmount) {
		this.destOrderAmount = destOrderAmount;
	}

	public OutBean getSampleOut() {
		return sampleOut;
	}

	public void setSampleOut(OutBean sampleOut) {
		this.sampleOut = sampleOut;
	}

	public OutBean getDestOut() {
		return destOut;
	}

	public void setDestOut(OutBean destOut) {
		this.destOut = destOut;
	}

	@Override
	public String toString() {
		return "Sample2OrderWrap{" +
				"typeName='" + typeName + 
				", sampleId='" + sampleId + 
				", srcStaffer='" + srcStaffer +
				", destStaffer='" + destStaffer + 
				", destOrderId=" + destOrderId +
				", amount=" + amount +
				", destOrderAmount=" + destOrderAmount +
				'}';
	}
}
