/**
 * File Name: ComposeItemBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.*;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * ComposeItemBean
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see ComposeItemBean
 * @since 1.0
 */
@Entity
@Table(name = "T_CENTER_COMPOSE_ITEM")
public class ComposeItemBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    @FK
    private String parentId = "";

    @Join(tagClass = ProductBean.class)
    private String productId = "";

    @Join(tagClass = DepotBean.class, type = JoinType.LEFT)
    private String deportId = "";

    @Join(tagClass = DepotpartBean.class, type = JoinType.LEFT)
    private String depotpartId = "";

    @Join(tagClass = StorageBean.class, type = JoinType.LEFT)
    private String storageId = "";

    private String relationId = "";

    private int mtype = PublicConstant.MANAGER_TYPE_MANAGER;

    private int amount = 0;

    private double price = 0.0d;

    private String logTime = "";
    
    /**
     * 进项税率
     */
    private double inputRate = 0.0d;

    /**
     * 产品分拆时标明是库存还是费用
     */
    private int stype = ProductConstant.DECOMPOSE_PRODUCT_STORAGE;

    private double virtualPrice = 0.0d;

    /**
     * #925 单品码
     */
    private String sn;

    /**
     * #313
     */
    @Ignore
    private double assemblyRate = 0.0;
    
    /**
     * default constructor
     */
    public ComposeItemBean()
    {
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
     * @return the parentId
     */
    public String getParentId()
    {
        return parentId;
    }

    /**
     * @param parentId
     *            the parentId to set
     */
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
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
     * @return the deportId
     */
    public String getDeportId()
    {
        return deportId;
    }

    /**
     * @param deportId
     *            the deportId to set
     */
    public void setDeportId(String deportId)
    {
        this.deportId = deportId;
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
     * @return the relationId
     */
    public String getRelationId()
    {
        return relationId;
    }

    /**
     * @param relationId
     *            the relationId to set
     */
    public void setRelationId(String relationId)
    {
        this.relationId = relationId;
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
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
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

	/**
	 * @return the stype
	 */
	public int getStype()
	{
		return stype;
	}

	/**
	 * @param stype the stype to set
	 */
	public void setStype(int stype)
	{
		this.stype = stype;
	}

    public double getAssemblyRate() {
        return assemblyRate;
    }

    public void setAssemblyRate(double assemblyRate) {
        this.assemblyRate = assemblyRate;
    }

    public double getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(double virtualPrice) {
        this.virtualPrice = virtualPrice;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        return "ComposeItemBean{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", productId='" + productId + '\'' +
                ", deportId='" + deportId + '\'' +
                ", depotpartId='" + depotpartId + '\'' +
                ", storageId='" + storageId + '\'' +
                ", relationId='" + relationId + '\'' +
                ", mtype=" + mtype +
                ", amount=" + amount +
                ", price=" + price +
                ", logTime='" + logTime + '\'' +
                ", inputRate=" + inputRate +
                ", stype=" + stype +
                ", virtualPrice=" + virtualPrice +
                ", sn='" + sn + '\'' +
                ", assemblyRate=" + assemblyRate +
                '}';
    }
}
