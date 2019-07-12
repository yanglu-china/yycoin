package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="map")
public class NbBankQueryCurdTl {

	/**
	 * ����ѯ�˺�
	 */
	private String bankAcc;
	
	/**
	 * ��Χ��  ��ʽ��0.01
	 */
	private String queryAmtBegin;
	
	/**
	 * ��Χֹ ��ʽ��9999999.99
	 */
	private String queryAmtEnd;
	
	/**
	 * �Է�����
	 */
	private String queryOppAccName;

	@XmlElement
	public String getBankAcc() {
		return bankAcc;
	}

	public void setBankAcc(String bankAcc) {
		this.bankAcc = bankAcc;
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
