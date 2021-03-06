package com.china.center.oa.sail.bean;

import java.math.BigDecimal;
import java.util.List;

public class ImportProcurementBean {
	
	private String productName;
	
	private String productId;
	
	private int productNum;
	
	private BigDecimal productCost;
	
	private String depotName;
	
	private String depotId;
	
	private String depotpartName;
	
	private String depotpartId;
	
	private String locationId;
	
	private String unitId;
	
	private String unitName;
	
	private String remark;
	
	List<ImportProcurementBean> ipList;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductNum() {
		return productNum;
	}

	public void setProductNum(int productNum) {
		this.productNum = productNum;
	}

	public BigDecimal getProductCost() {
		return productCost;
	}

	public void setProductCost(BigDecimal productCost) {
		this.productCost = productCost;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getDepotpartName() {
		return depotpartName;
	}

	public void setDepotpartName(String depotpartName) {
		this.depotpartName = depotpartName;
	}

	public String getDepotId() {
		return depotId;
	}

	public void setDepotId(String depotId) {
		this.depotId = depotId;
	}

	public String getDepotpartId() {
		return depotpartId;
	}

	public void setDepotpartId(String depotpartId) {
		this.depotpartId = depotpartId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<ImportProcurementBean> getIpList() {
		return ipList;
	}

	public void setIpList(List<ImportProcurementBean> ipList) {
		this.ipList = ipList;
	}

}
