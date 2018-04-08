package com.china.center.oa.product.vo;

import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
import com.china.center.oa.product.bean.ProductBOMBean;

@SuppressWarnings("serial")
@Entity(inherit = true)
public class ProductBOMVO extends ProductBOMBean
{
	@Relationship(relationField = "productId")
	private String productName = "";
	
	@Relationship(relationField = "productId", tagField = "reserve4")
	private String reserve4 = "";
	
	@Relationship(relationField = "productId", tagField = "consumeInDay")
	private int consumeInDay = 0;
	
	@Relationship(relationField = "subProductId")
	private String subProductName = "";
	
	@Relationship(relationField = "subProductId", tagField = "code")
	private String code = "";

	@Ignore
	private String bomJson = "";

	@Ignore
	/**
	 * #93 可用数量
	 */
	private int pamount = 0;

	@Ignore
	private double price = 0.0d;

	@Ignore
	/**
	 * #283 最近一次产品合成的配件价格
	 */
	private double lastPrice = 0.0d;

	@Ignore
	private String srcRelation = "";
	
	@Ignore
	private List<ProductBOMVO> voList = null;
	
	public ProductBOMVO()
	{
	}

	public String getSrcRelation() {
		return srcRelation;
	}

	public void setSrcRelation(String srcRelation) {
		this.srcRelation = srcRelation;
	}

	/**
	 * @return the productName
	 */
	public String getProductName()
	{
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	/**
	 * @return the subProductName
	 */
	public String getSubProductName()
	{
		return subProductName;
	}

	/**
	 * @param subProductName the subProductName to set
	 */
	public void setSubProductName(String subProductName)
	{
		this.subProductName = subProductName;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return the reserve4
	 */
	public String getReserve4() {
		return reserve4;
	}

	/**
	 * @param reserve4 the reserve4 to set
	 */
	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}

	/**
	 * @return the consumeInDay
	 */
	public int getConsumeInDay() {
		return consumeInDay;
	}

	/**
	 * @param consumeInDay the consumeInDay to set
	 */
	public void setConsumeInDay(int consumeInDay) {
		this.consumeInDay = consumeInDay;
	}

	/**
	 * @return the bomJson
	 */
	public String getBomJson()
	{
		return bomJson;
	}

	/**
	 * @param bomJson the bomJson to set
	 */
	public void setBomJson(String bomJson)
	{
		this.bomJson = bomJson;
	}

	/**
	 * @return the voList
	 */
	public List<ProductBOMVO> getVoList()
	{
		return voList;
	}

	/**
	 * @param voList the voList to set
	 */
	public void setVoList(List<ProductBOMVO> voList)
	{
		this.voList = voList;
	}

	public int getPamount() {
		return pamount;
	}

	public void setPamount(int pamount) {
		this.pamount = pamount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	@Override
	public String toString() {
		return "ProductBOMVO{" +
				"productName='" + productName + '\'' +
				", reserve4='" + reserve4 + '\'' +
				", consumeInDay=" + consumeInDay +
				", subProductName='" + subProductName + '\'' +
				", code='" + code + '\'' +
				", bomJson='" + bomJson + '\'' +
				", pamount=" + pamount +
				", price=" + price +
				", lastPrice=" + lastPrice +
				", srcRelation='" + srcRelation + '\'' +
				"} " + super.toString();
	}
}
