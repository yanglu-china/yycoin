package com.china.center.oa.sail.wrap;

import java.io.Serializable;

public class BatchBackWrap implements Serializable
{
	/** 批量退货的产品  */
	private String productId = "";
	
	/** 委托代销 行项目ID */
	private String baseId = "";
	
	/** 产品所在的原销售单 */
	private String refOutFullId = "";
	
	/** 结算单退货时关联原结算单号 */
	private String refId = "";
	
	/** 针对委托代销，类型：0,未结算的产品 1,已结算的产品 */
	private int type = 0;
	
	/** 参与退货的数量 */
	private int amount = 0;
	
	/** 产品 */
	private String productName = "";

	/**#441
	 * 成本的string值
	 */
	private String costPriceKey = "";

	private String depotId = "";

	private String depotpartId = "";

	private String depotpartName = "";

	private String transportNo = "";

	private String description = "";

	public BatchBackWrap()
	{
		
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getRefOutFullId()
	{
		return refOutFullId;
	}

	public void setRefOutFullId(String refOutFullId)
	{
		this.refOutFullId = refOutFullId;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public String getRefId()
	{
		return refId;
	}

	public void setRefId(String refId)
	{
		this.refId = refId;
	}

	public String getBaseId()
	{
		return baseId;
	}

	public void setBaseId(String baseId)
	{
		this.baseId = baseId;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public String getCostPriceKey() {
		return costPriceKey;
	}

	public void setCostPriceKey(String costPriceKey) {
		this.costPriceKey = costPriceKey;
	}

	public String getDepotId() {
		return depotId;
	}

	public void setDepotId(String depotId) {
		this.depotId = depotId;
	}

	public String getTransportNo() {
		return transportNo;
	}

	public void setTransportNo(String transportNo) {
		this.transportNo = transportNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDepotpartId() {
		return depotpartId;
	}

	public void setDepotpartId(String depotpartId) {
		this.depotpartId = depotpartId;
	}

	public String getDepotpartName() {
		return depotpartName;
	}

	public void setDepotpartName(String depotpartName) {
		this.depotpartName = depotpartName;
	}

	@Override
	public String toString() {
		return "BatchBackWrap{" +
				"productId='" + productId + '\'' +
				", baseId='" + baseId + '\'' +
				", refOutFullId='" + refOutFullId + '\'' +
				", refId='" + refId + '\'' +
				", type=" + type +
				", amount=" + amount +
				", productName='" + productName + '\'' +
				", costPriceKey='" + costPriceKey + '\'' +
				", depotId='" + depotId + '\'' +
				", depotpartId='" + depotpartId + '\'' +
				", depotpartName='" + depotpartName + '\'' +
				", transportNo='" + transportNo + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
