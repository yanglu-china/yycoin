package com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

public class SfResponseRlsDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String waybillNo;
	
	private String sourceTransferCode;
	
	private String sourceCityCode;
	
	private String sourceDeptCode;
	
	private String sourceTeamCode;
	
	private String destCityCode;
	
	private String destDeptCode;
	
	private String destDeptCodeMapping;
	
	private String destTeamCode;
	
	private String destTransferCode;
	
	private String destRouteLabel;
	
	private String proName;
	
	private String cargoTypeCode;
	
	private String limitTypeCode;
	
	private String expressTypeCode;
	
	private String codingMapping;
	
	private String xbFlag;
	
	private String printFlag;
	
	private String twoDimensionCode;
	
	private String proCode;
	
	private String printIcon;

	@XmlAttribute(name="waybillNo")
	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	@XmlAttribute(name="sourceTransferCode")
	public String getSourceTransferCode() {
		return sourceTransferCode;
	}

	public void setSourceTransferCode(String sourceTransferCode) {
		this.sourceTransferCode = sourceTransferCode;
	}

	@XmlAttribute(name="sourceCityCode")
	public String getSourceCityCode() {
		return sourceCityCode;
	}

	public void setSourceCityCode(String sourceCityCode) {
		this.sourceCityCode = sourceCityCode;
	}

	@XmlAttribute(name="sourceDeptCode")
	public String getSourceDeptCode() {
		return sourceDeptCode;
	}

	public void setSourceDeptCode(String sourceDeptCode) {
		this.sourceDeptCode = sourceDeptCode;
	}

	@XmlAttribute(name="sourceTeamCode")
	public String getSourceTeamCode() {
		return sourceTeamCode;
	}

	public void setSourceTeamCode(String sourceTeamCode) {
		this.sourceTeamCode = sourceTeamCode;
	}

	@XmlAttribute(name="destCityCode")
	public String getDestCityCode() {
		return destCityCode;
	}

	public void setDestCityCode(String destCityCode) {
		this.destCityCode = destCityCode;
	}

	@XmlAttribute(name="destDeptCode")
	public String getDestDeptCode() {
		return destDeptCode;
	}

	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}
	
	@XmlAttribute(name="destDeptCodeMapping")
	public String getDestDeptCodeMapping() {
		return destDeptCodeMapping;
	}

	public void setDestDeptCodeMapping(String destDeptCodeMapping) {
		this.destDeptCodeMapping = destDeptCodeMapping;
	}

	@XmlAttribute(name="destTeamCode")
	public String getDestTeamCode() {
		return destTeamCode;
	}

	public void setDestTeamCode(String destTeamCode) {
		this.destTeamCode = destTeamCode;
	}

	@XmlAttribute(name="destTransferCode")
	public String getDestTransferCode() {
		return destTransferCode;
	}

	public void setDestTransferCode(String destTransferCode) {
		this.destTransferCode = destTransferCode;
	}

	@XmlAttribute(name="destRouteLabel")
	public String getDestRouteLabel() {
		return destRouteLabel;
	}

	public void setDestRouteLabel(String destRouteLabel) {
		this.destRouteLabel = destRouteLabel;
	}

	@XmlAttribute(name="proName")
	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	@XmlAttribute(name="cargoTypeCode")
	public String getCargoTypeCode() {
		return cargoTypeCode;
	}

	public void setCargoTypeCode(String cargoTypeCode) {
		this.cargoTypeCode = cargoTypeCode;
	}

	@XmlAttribute(name="limitTypeCode")
	public String getLimitTypeCode() {
		return limitTypeCode;
	}

	public void setLimitTypeCode(String limitTypeCode) {
		this.limitTypeCode = limitTypeCode;
	}
	
	@XmlAttribute(name="expressTypeCode")
	public String getExpressTypeCode() {
		return expressTypeCode;
	}

	public void setExpressTypeCode(String expressTypeCode) {
		this.expressTypeCode = expressTypeCode;
	}

	@XmlAttribute(name="codingMapping")
	public String getCodingMapping() {
		return codingMapping;
	}

	public void setCodingMapping(String codingMapping) {
		this.codingMapping = codingMapping;
	}

	@XmlAttribute(name="xbFlag")
	public String getXbFlag() {
		return xbFlag;
	}

	public void setXbFlag(String xbFlag) {
		this.xbFlag = xbFlag;
	}

	@XmlAttribute(name="printFlag")
	public String getPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(String printFlag) {
		this.printFlag = printFlag;
	}
	
	@XmlAttribute(name="twoDimensionCode")
	public String getTwoDimensionCode() {
		return twoDimensionCode;
	}

	public void setTwoDimensionCode(String twoDimensionCode) {
		this.twoDimensionCode = twoDimensionCode;
	}

	@XmlAttribute(name="proCode")
	public String getProCode() {
		return proCode;
	}

	public void setProCode(String proCode) {
		this.proCode = proCode;
	}

	@XmlAttribute(name="printIcon")
	public String getPrintIcon() {
		return printIcon;
	}

	public void setPrintIcon(String printIcon) {
		this.printIcon = printIcon;
	}

	@Override
	public String toString() {
		return "SfResponseRlsDetail [waybillNo=" + waybillNo + ", sourceTransferCode=" + sourceTransferCode
				+ ", sourceCityCode=" + sourceCityCode + ", sourceDeptCode=" + sourceDeptCode + ", sourceTeamCode="
				+ sourceTeamCode + ", destCityCode=" + destCityCode + ", destDeptCode=" + destDeptCode
				+ ", destDeptCodeMapping=" + destDeptCodeMapping + ", destTeamCode=" + destTeamCode
				+ ", destTransferCode=" + destTransferCode + ", destRouteLabel=" + destRouteLabel + ", proName="
				+ proName + ", cargoTypeCode=" + cargoTypeCode + ", limitTypeCode=" + limitTypeCode
				+ ", expressTypeCode=" + expressTypeCode + ", codingMapping=" + codingMapping + ", xbFlag=" + xbFlag
				+ ", printFlag=" + printFlag + ", twoDimensionCode=" + twoDimensionCode + ", proCode=" + proCode
				+ ", printIcon=" + printIcon + "]";
	}

}
