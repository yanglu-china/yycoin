package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;

public class NbBankTransfer {
	
	/**
	 * �����˺�
	 */
	private String payerAccNo;
	
	/**
	 * ���λ����
	 */
	private String payerCorpCode;
	
	/**
	 * ���λ����
	 */
	private String payerCorpName;
	
	/**
	 * ERP��λ����
	 */
	private String erpPayerCorpCode;
	
	/**
	 * �տ����˺�
	 */
	private String payeeAccNo;
	
	/**
	 * �տ��˻���
	 */
	private String payeeAccName;
	
	/**
	 * �տ�������
	 */
	private String payeeBankName;
	
	/**
	 * �տ������������к�
	 */
	private String payeeBankCode;
	
	/**
	 * �տ���ʡ
	 */
	private String payeeProv;
	
	/**
	 * �տ�����
	 */
	private String payeeCity;
	
	/**
	 * ������ 
	 * ��ʽ��10000.01 ��Χ��0.01-999999999999999.99
	 */
	private String payMoney;
	
	/**
	 * ͬ����ر�ʶ
	 * 0 ͬ�� 1 ��� Ĭ�� 0
	 */
	private String areaSign;
	
	/**
	 * ͬ�п��б�ʶ
	 * 0 ͬ�� 1 ���� Ĭ�� 0
	 */
	private String difSign;
	
	/**
	 * ������;--���10������
	 */
	private String payPurpose;
	
	/**
	 * ERP����
	 */
	private String erpReqNo;
	
	/**
	 * ERP������
	 */
	private String erpReqUser;
	
	/**
	 * �Ƿ������޸��տ����˺� 0 ������ 1 ���� Ĭ�� 0
	 */
	private String allowEditPayeeAcc;
	
	/**
	 * �Ƿ������޸ĸ����� 0 ������ 1 ���� Ĭ�� 0

	 */
	private String allowEditPayMoney;
	
	/**
	 * �Ƿ������޸ĸ����˺� 0 ������ 1 ���� Ĭ�� 1
	 */
	private String allowEditPayerAcc;
	
	/**
	 * �����ֶ�һ
	 */
	private String reverse1;
	
	private String reverse2;
	
	private String reverse3;
	
	private String reverse4;
	
	private String reverse5;

	@XmlElement
	public String getPayerAccNo() {
		return payerAccNo;
	}

	public void setPayerAccNo(String payerAccNo) {
		this.payerAccNo = payerAccNo;
	}

	@XmlElement
	public String getPayerCorpCode() {
		return payerCorpCode;
	}

	public void setPayerCorpCode(String payerCorpCode) {
		this.payerCorpCode = payerCorpCode;
	}

	@XmlElement
	public String getPayerCorpName() {
		return payerCorpName;
	}

	public void setPayerCorpName(String payerCorpName) {
		this.payerCorpName = payerCorpName;
	}

	@XmlElement
	public String getErpPayerCorpCode() {
		return erpPayerCorpCode;
	}

	public void setErpPayerCorpCode(String erpPayerCorpCode) {
		this.erpPayerCorpCode = erpPayerCorpCode;
	}

	@XmlElement
	public String getPayeeAccNo() {
		return payeeAccNo;
	}

	public void setPayeeAccNo(String payeeAccNo) {
		this.payeeAccNo = payeeAccNo;
	}

	@XmlElement
	public String getPayeeAccName() {
		return payeeAccName;
	}

	public void setPayeeAccName(String payeeAccName) {
		this.payeeAccName = payeeAccName;
	}

	@XmlElement
	public String getPayeeBankName() {
		return payeeBankName;
	}

	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	@XmlElement
	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	public void setPayeeBankCode(String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}

	@XmlElement
	public String getPayeeProv() {
		return payeeProv;
	}

	public void setPayeeProv(String payeeProv) {
		this.payeeProv = payeeProv;
	}

	@XmlElement
	public String getPayeeCity() {
		return payeeCity;
	}

	public void setPayeeCity(String payeeCity) {
		this.payeeCity = payeeCity;
	}

	@XmlElement
	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}

	@XmlElement
	public String getAreaSign() {
		return areaSign;
	}

	public void setAreaSign(String areaSign) {
		this.areaSign = areaSign;
	}

	@XmlElement
	public String getDifSign() {
		return difSign;
	}

	public void setDifSign(String difSign) {
		this.difSign = difSign;
	}

	@XmlElement
	public String getPayPurpose() {
		return payPurpose;
	}

	public void setPayPurpose(String payPurpose) {
		this.payPurpose = payPurpose;
	}

	@XmlElement
	public String getErpReqNo() {
		return erpReqNo;
	}

	public void setErpReqNo(String erpReqNo) {
		this.erpReqNo = erpReqNo;
	}

	@XmlElement
	public String getErpReqUser() {
		return erpReqUser;
	}

	public void setErpReqUser(String erpReqUser) {
		this.erpReqUser = erpReqUser;
	}

	@XmlElement
	public String getAllowEditPayeeAcc() {
		return allowEditPayeeAcc;
	}

	public void setAllowEditPayeeAcc(String allowEditPayeeAcc) {
		this.allowEditPayeeAcc = allowEditPayeeAcc;
	}

	@XmlElement
	public String getAllowEditPayMoney() {
		return allowEditPayMoney;
	}

	public void setAllowEditPayMoney(String allowEditPayMoney) {
		this.allowEditPayMoney = allowEditPayMoney;
	}

	@XmlElement
	public String getAllowEditPayerAcc() {
		return allowEditPayerAcc;
	}

	public void setAllowEditPayerAcc(String allowEditPayerAcc) {
		this.allowEditPayerAcc = allowEditPayerAcc;
	}

	@XmlElement
	public String getReverse1() {
		return reverse1;
	}

	public void setReverse1(String reverse1) {
		this.reverse1 = reverse1;
	}

	@XmlElement
	public String getReverse2() {
		return reverse2;
	}

	public void setReverse2(String reverse2) {
		this.reverse2 = reverse2;
	}

	@XmlElement
	public String getReverse3() {
		return reverse3;
	}

	public void setReverse3(String reverse3) {
		this.reverse3 = reverse3;
	}

	@XmlElement
	public String getReverse4() {
		return reverse4;
	}

	public void setReverse4(String reverse4) {
		this.reverse4 = reverse4;
	}

	@XmlElement
	public String getReverse5() {
		return reverse5;
	}

	public void setReverse5(String reverse5) {
		this.reverse5 = reverse5;
	}
	
	
}
