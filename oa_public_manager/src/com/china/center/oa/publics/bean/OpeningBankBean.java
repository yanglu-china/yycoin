package com.china.center.oa.publics.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "T_CENTER_OPENING_BANK")
public class OpeningBankBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String bankName;
	
	private String unionBankCode;
	
	private String bankTypeName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getUnionBankCode() {
		return unionBankCode;
	}

	public void setUnionBankCode(String unionBankCode) {
		this.unionBankCode = unionBankCode;
	}

	public String getBankTypeName() {
		return bankTypeName;
	}

	public void setBankTypeName(String bankTypeName) {
		this.bankTypeName = bankTypeName;
	}

}
