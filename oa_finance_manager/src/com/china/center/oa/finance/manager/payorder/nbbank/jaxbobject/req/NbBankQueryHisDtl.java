package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="map")
public class NbBankQueryHisDtl {
	
	private String bankAcc;
	
	private String queryDateBegin;
	
	private String queryDateEnd;
	
	private String queryAmtBegin;
	
	private String queryAmtEnd;
	
	private String queryOppAccName;

	@XmlElement
	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
	}

	@XmlElement
	public String getQueryDateBegin() {
		return queryDateBegin;
	}

	public void setQueryDateBegin(String queryDateBegin) {
		this.queryDateBegin = queryDateBegin;
	}

	@XmlElement
	public String getQueryDateEnd() {
		return queryDateEnd;
	}

	public void setQueryDateEnd(String queryDateEnd) {
		this.queryDateEnd = queryDateEnd;
	}

	@XmlElement
	public String getQueryAmtBegin() {
		return queryAmtBegin;
	}

	public void setQueryAmtBegin(String queryAmtBegin) {
		this.queryAmtBegin = queryAmtBegin;
	}

	@XmlElement
	public String getQueryAmtEnd() {
		return queryAmtEnd;
	}

	public void setQueryAmtEnd(String queryAmtEnd) {
		this.queryAmtEnd = queryAmtEnd;
	}

	@XmlElement
	public String getQueryOppAccName() {
		return queryOppAccName;
	}

	public void setQueryOppAccName(String queryOppAccName) {
		this.queryOppAccName = queryOppAccName;
	}
	
}
