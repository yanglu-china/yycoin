/**
 * File Name: BaseBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-26
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * 库存的子项
 * 
 * @author ZHUZHU
 * @version 2007-3-26
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_BASE")
public class BaseBean implements Serializable, BaseInterface
{
    @Id
    private String id = "";

    @FK
    private String outId = "";

    private String productId = "";

    private String productName = "";

    private String showName = "";

    private String showId = "";

    @Join(tagClass = DepotBean.class, type = JoinType.LEFT)
    private String locationId = "";

    private String unit = "";

    private int amount = 0;

    private int mtype = PublicConstant.MANAGER_TYPE_MANAGER;

    private int inway = 0;

    /**
     * 储位(仓库下面是通过 仓区+产品+价格+产品所有者获取具体的信息的,所以storageId不使用了)
     */
    private String storageId = "";

    /**
     * 产品所在的仓区
     */
    private String depotpartId = "";

    private String depotpartName = "";

    /**
     * 产品的所有者
     */
    private String owner = "0";

    /**
     * 产品的所有者名称
     */
    private String ownerName = "公共";

    /**
     * 业务员销售价格
     */
    private double price = 0.0d;

    private double price2 = 0.0d;

    /**
     * 总部结算价格
     */
    private double pprice = 0.0d;

    /**
     * 事业部结算价格
     */
    private double iprice = 0.0d;

    /**
     * 输入价格(销售里面:业务员结算价)
     */
    private double inputPrice = 0.0d;

    /**
     * 成本
     */
    private double costPrice = 0.0d;

    /**
     * 成本的string值
     */
    private String costPriceKey = "";

    private double virtualPrice = 0.0d;

    private String virtualPriceKey = "";

    /**
     * #925 单品码
     */
    private String sn;

    /**
     * 总销售价
     */
    private double value = 0.0d;

    /**
     * 开发票的金额(已经开票的金额)
     */
    private double invoiceMoney = 0.0d;

    /**
     * 成本(和costPrice一样)
     */
    private String description = "";

    /**
	 * 毛利
	 */
	private double profit = 0.0d;
	
	 /**
	  * 毛利率
	  */
	private double profitRatio = 0.0d;


    /**2015/04/11 中收激励功能
     * 中收金额
     */
    private double ibMoney = 0.0d;

    /**
     * #513 预扣中收金额
     */
    private double ykibMoney = 0.0d;

    /**2015/04/11 中收激励功能
     * 激励金额
     */
    private double motivationMoney = 0.0d;


    /**
     * 中收2金额
     */
    private double ibMoney2 = 0.0d;

    /**
     * 激励2金额
     */
    private double motivationMoney2 = 0.0d;

    /**
     * 平台手续费
     */
    private double platformFee = 0.0d;

    private double cash =0.0d;

    /**
     * #575 业务员单品奖励
     */
    private double cash2 =0.0d;

    private double grossProfit =0.0d;
    
    /**
     * 配送方式 deliverType
     */
    private int deliverType = -1;
    
    /**
     * 快递运费支付方式 - deliverPay(停用)
     */
    private int expressPay = -1;
    
    /**
     * 货运运费支付方式 - deliverPay(停用)
     */
    private int transportPay = -1;
    
    /**
     * 销售的产品类型
     */
    @Ignore
    private int productType = -1;
    
    /**
     * 旧货
     */
    private int oldGoods = -1;
    
    /**
     * 仓库名
     */
    @Ignore
    private String depotName = "";
    
    /**
     * 税率
     */
    private double taxrate = 0.0d;
    
    /**
     * 税额
     */
    private double tax = 0.0d;
    
    /**
     * 进项税率（采购入库时有）
     */
    private double inputRate = 0.0d;

    /**
     * 关联行项目ID
     */
    private String refId = "";

    /**
     * #470 导入时产品匹配ID
     */
    private String productImportId = "";


    /**
     * 2016/3/29 # 导入同一SO单的同一商品行时临时校验用
     * 已开票金额(临时)
     */
    @Ignore
    private double tempInvoiceMoney = 0.0d;
    
    /**
     * default constructor
     */
    public BaseBean()
    {
    }

    /**
     * @return the amount
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * @return the productId
     */
    public String getProductId()
    {
        return productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    /**
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * @return the unit
     */
    public String getUnit()
    {
        return unit;
    }

    /**
     * @param unit
     *            the unit to set
     */
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    /**
     * @return the value
     */
    public double getValue()
    {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(double value)
    {
        this.value = value;
    }

    /**
     * @return the outId
     */
    public String getOutId()
    {
        return outId;
    }

    /**
     * @param outId
     *            the outId to set
     */
    public void setOutId(String outId)
    {
        this.outId = outId;
    }

    /**
     * @return 返回 locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the inway
     */
    public int getInway()
    {
        return inway;
    }

    /**
     * @param inway
     *            the inway to set
     */
    public void setInway(int inway)
    {
        this.inway = inway;
    }

    /**
     * @return the storageId
     */
    public String getStorageId()
    {
        return storageId;
    }

    /**
     * @param storageId
     *            the storageId to set
     */
    public void setStorageId(String storageId)
    {
        this.storageId = storageId;
    }

    /**
     * @return the showName
     */
    public String getShowName()
    {
        return showName;
    }

    /**
     * @param showName
     *            the showName to set
     */
    public void setShowName(String showName)
    {
        this.showName = showName;
    }

    /**
     * @return the showId
     */
    public String getShowId()
    {
        return showId;
    }

    /**
     * @param showId
     *            the showId to set
     */
    public void setShowId(String showId)
    {
        this.showId = showId;
    }

    /**
     * @return the costPrice
     */
    public double getCostPrice()
    {
        return costPrice;
    }

    /**
     * @param costPrice
     *            the costPrice to set
     */
    public void setCostPrice(double costPrice)
    {
        this.costPrice = costPrice;
    }

    /**
     * @return the owner
     */
    public String getOwner()
    {
        return owner;
    }

    /**
     * @param owner
     *            the owner to set
     */
    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    /**
     * @return the ownerName
     */
    public String getOwnerName()
    {
        return ownerName;
    }

    /**
     * @param ownerName
     *            the ownerName to set
     */
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    /**
     * @return the costPriceKey
     */
    public String getCostPriceKey()
    {
        return costPriceKey;
    }

    /**
     * @param costPriceKey
     *            the costPriceKey to set
     */
    public void setCostPriceKey(String costPriceKey)
    {
        this.costPriceKey = costPriceKey;
    }

    /**
     * @return the depotpartId
     */
    public String getDepotpartId()
    {
        return depotpartId;
    }

    /**
     * @param depotpartId
     *            the depotpartId to set
     */
    public void setDepotpartId(String depotpartId)
    {
        this.depotpartId = depotpartId;
    }

    /**
     * @return the depotpartName
     */
    public String getDepotpartName()
    {
        return depotpartName;
    }

    /**
     * @param depotpartName
     *            the depotpartName to set
     */
    public void setDepotpartName(String depotpartName)
    {
        this.depotpartName = depotpartName;
    }

    public int getOldGoods()
	{
		return oldGoods;
	}

	public void setOldGoods(int oldGoods)
	{
		this.oldGoods = oldGoods;
	}

	public String getDepotName()
	{
		return depotName;
	}

	public void setDepotName(String depotName)
	{
		this.depotName = depotName;
	}

	public double getTaxrate()
	{
		return taxrate;
	}

	public void setTaxrate(double taxrate)
	{
		this.taxrate = taxrate;
	}

	public double getTax()
	{
		return tax;
	}

	public void setTax(double tax)
	{
		this.tax = tax;
	}

	/**
	 * @return the inputRate
	 */
	public double getInputRate()
	{
		return inputRate;
	}

	/**
	 * @param inputRate the inputRate to set
	 */
	public void setInputRate(double inputRate)
	{
		this.inputRate = inputRate;
	}

	/*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( (costPriceKey == null) ? 0 : costPriceKey.hashCode());
        result = prime * result + ( (depotpartId == null) ? 0 : depotpartId.hashCode());
        result = prime * result + ( (owner == null) ? 0 : owner.hashCode());
        result = prime * result + ( (productId == null) ? 0 : productId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if ( ! (obj instanceof BaseBean)) return false;
        final BaseBean other = (BaseBean)obj;
        if (costPriceKey == null)
        {
            if (other.costPriceKey != null) return false;
        }
        else if ( !costPriceKey.equals(other.costPriceKey)) return false;

        if (virtualPriceKey == null)
        {
            if (other.virtualPriceKey != null) return false;
        }
        else if ( !virtualPriceKey.equals(other.virtualPriceKey)) return false;
//        if (depotpartId == null)
//        {
//            if (other.depotpartId != null) return false;
//        }
//        else if ( !depotpartId.equals(other.depotpartId)) return false;
        if (owner == null)
        {
            if (other.owner != null) return false;
        }
        else if ( !owner.equals(other.owner)) return false;
        if (productId == null)
        {
            if (other.productId != null) return false;
        }
        else if ( !productId.equals(other.productId)) return false;
        return true;
    }

    /**
     * 这里没有仓区
     * 
     * @param obj
     * @return
     */
    public boolean equals2(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if ( ! (obj instanceof BaseBean)) return false;
        final BaseBean other = (BaseBean)obj;
        if (costPriceKey == null)
        {
            if (other.costPriceKey != null) return false;
        }
        else if ( !costPriceKey.equals(other.costPriceKey)) return false;

        if (virtualPriceKey == null)
        {
            if (other.virtualPriceKey != null) return false;
        }
        else if ( !virtualPriceKey.equals(other.virtualPriceKey)) return false;

        if (owner == null)
        {
            if (other.owner != null) return false;
        }
        else if ( !owner.equals(other.owner)) return false;

        if (productId == null)
        {
            if (other.productId != null) return false;
        }
        else if ( !productId.equals(other.productId)) return false;
        return true;
    }

    /**
     * @return the inputPrice
     */
    public double getInputPrice()
    {
        return inputPrice;
    }

    /**
     * @param inputPrice
     *            the inputPrice to set
     */
    public void setInputPrice(double inputPrice)
    {
        this.inputPrice = inputPrice;
    }

    /**
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
    }

    /**
     * @return the invoiceMoney
     */
    public double getInvoiceMoney()
    {
        return invoiceMoney;
    }

    /**
     * @param invoiceMoney
     *            the invoiceMoney to set
     */
    public void setInvoiceMoney(double invoiceMoney)
    {
        this.invoiceMoney = invoiceMoney;
    }

    /**
     * @return the pprice
     */
    public double getPprice()
    {
        return pprice;
    }

    /**
     * @param pprice
     *            the pprice to set
     */
    public void setPprice(double pprice)
    {
        this.pprice = pprice;
    }

    /**
     * @return the iprice
     */
    public double getIprice()
    {
        return iprice;
    }

    /**
     * @param iprice
     *            the iprice to set
     */
    public void setIprice(double iprice)
    {
        this.iprice = iprice;
    }

    public double getProfit()
	{
		return profit;
	}

	public void setProfit(double profit)
	{
		this.profit = profit;
	}

	public double getProfitRatio()
	{
		return profitRatio;
	}

	public void setProfitRatio(double profitRatio)
	{
		this.profitRatio = profitRatio;
	}

	public int getDeliverType()
	{
		return deliverType;
	}

	public void setDeliverType(int deliverType)
	{
		this.deliverType = deliverType;
	}

	public int getExpressPay()
	{
		return expressPay;
	}

	public void setExpressPay(int expressPay)
	{
		this.expressPay = expressPay;
	}

	public int getTransportPay()
	{
		return transportPay;
	}

	public void setTransportPay(int transportPay)
	{
		this.transportPay = transportPay;
	}

	public int getProductType()
	{
		return productType;
	}

	public void setProductType(int productType)
	{
		this.productType = productType;
	}

    public double getIbMoney() {
        return ibMoney;
    }

    public void setIbMoney(double ibMoney) {
        this.ibMoney = ibMoney;
    }

    public double getMotivationMoney() {
        return motivationMoney;
    }

    public void setMotivationMoney(double motivationMoney) {
        this.motivationMoney = motivationMoney;
    }

    /**
	 * @return the refId
	 */
	public String getRefId()
	{
		return refId;
	}

	/**
	 * @param refId the refId to set
	 */
	public void setRefId(String refId)
	{
		this.refId = refId;
	}

    public double getTempInvoiceMoney() {
        return tempInvoiceMoney;
    }

    public void setTempInvoiceMoney(double tempInvoiceMoney) {
        this.tempInvoiceMoney = tempInvoiceMoney;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getIbMoney2() {
        return ibMoney2;
    }

    public void setIbMoney2(double ibMoney2) {
        this.ibMoney2 = ibMoney2;
    }

    public double getMotivationMoney2() {
        return motivationMoney2;
    }

    public void setMotivationMoney2(double motivationMoney2) {
        this.motivationMoney2 = motivationMoney2;
    }

    public double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(double platformFee) {
        this.platformFee = platformFee;
    }

    public String getProductImportId() {
        return productImportId;
    }

    public void setProductImportId(String productImportId) {
        this.productImportId = productImportId;
    }

    public double getYkibMoney() {
        return ykibMoney;
    }

    public void setYkibMoney(double ykibMoney) {
        this.ykibMoney = ykibMoney;
    }

    public double getCash2() {
        return cash2;
    }

    public void setCash2(double cash2) {
        this.cash2 = cash2;
    }

    public double getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(double virtualPrice) {
        this.virtualPrice = virtualPrice;
    }

    public String getVirtualPriceKey() {
        return virtualPriceKey;
    }

    public void setVirtualPriceKey(String virtualPriceKey) {
        this.virtualPriceKey = virtualPriceKey;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "id='" + id + '\'' +
                ", outId='" + outId + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", showName='" + showName + '\'' +
                ", showId='" + showId + '\'' +
                ", locationId='" + locationId + '\'' +
                ", unit='" + unit + '\'' +
                ", amount=" + amount +
                ", mtype=" + mtype +
                ", inway=" + inway +
                ", storageId='" + storageId + '\'' +
                ", depotpartId='" + depotpartId + '\'' +
                ", depotpartName='" + depotpartName + '\'' +
                ", owner='" + owner + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", price=" + price +
                ", price2=" + price2 +
                ", pprice=" + pprice +
                ", iprice=" + iprice +
                ", inputPrice=" + inputPrice +
                ", costPrice=" + costPrice +
                ", costPriceKey='" + costPriceKey + '\'' +
                ", virtualPrice=" + virtualPrice +
                ", virtualPriceKey='" + virtualPriceKey + '\'' +
                ", sn='" + sn + '\'' +
                ", value=" + value +
                ", invoiceMoney=" + invoiceMoney +
                ", description='" + description + '\'' +
                ", profit=" + profit +
                ", profitRatio=" + profitRatio +
                ", ibMoney=" + ibMoney +
                ", ykibMoney=" + ykibMoney +
                ", motivationMoney=" + motivationMoney +
                ", ibMoney2=" + ibMoney2 +
                ", motivationMoney2=" + motivationMoney2 +
                ", platformFee=" + platformFee +
                ", cash=" + cash +
                ", cash2=" + cash2 +
                ", grossProfit=" + grossProfit +
                ", deliverType=" + deliverType +
                ", expressPay=" + expressPay +
                ", transportPay=" + transportPay +
                ", productType=" + productType +
                ", oldGoods=" + oldGoods +
                ", depotName='" + depotName + '\'' +
                ", taxrate=" + taxrate +
                ", tax=" + tax +
                ", inputRate=" + inputRate +
                ", refId='" + refId + '\'' +
                ", productImportId='" + productImportId + '\'' +
                ", tempInvoiceMoney=" + tempInvoiceMoney +
                '}';
    }
}
