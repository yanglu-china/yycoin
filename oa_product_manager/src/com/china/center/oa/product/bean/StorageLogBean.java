/**
 * 文 件 名: StorageBean.java <br>
 * 版 权: centerchina Technologies Co., Ltd. Copyright YYYY-YYYY, All rights reserved
 * <br>
 * 描 述: <描述> <br>
 * 修 改 人: admin <br>
 * 修改时间: 2008-1-5 <br>
 * 跟踪单号: <跟踪单号> <br>
 * 修改单号: <修改单号> <br>
 * 修改内容: <修改内容> <br>
 */
package com.china.center.oa.product.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.constant.StorageConstant;


/**
 * 储位变动日志
 * 
 * @author ZHUZHU
 * @version [版本号, 2008-1-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Entity(name = "储位记录")
@Table(name = "T_CENTER_STORAGELOG")
public class StorageLogBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    private String serializeId = "";

    private int type = StorageConstant.OPR_STORAGE_INIT;

    /**
     * 储位前数量
     */
    private int preAmount = 0;

    /**
     * 储位后数量
     */
    private int afterAmount = 0;

    /**
     * 仓区下整个产品的数量
     */
    private int preAmount1 = 0;

    /**
     * 仓区下整个产品的数量(价格)
     */
    private int preAmount11 = 0;

    /**
     * 仓区下整个产品的数量
     */
    private int afterAmount1 = 0;

    /**
     * 仓区此价格下的数量
     */
    private int afterAmount11 = 0;

    /**
     * 仓库下整个产品的数量
     */
    private int preAmount2 = 0;

    /**
     * 仓库此价格下的数量
     */
    private int preAmount22 = 0;

    /**
     * 仓库下整个产品的数量
     */
    private int afterAmount2 = 0;

    private int afterAmount22 = 0;

    /**
     * 异动数量
     */
    private int changeAmount = 0;

    @Join(tagClass = DepotpartBean.class, type = JoinType.LEFT)
    private String depotpartId = "";

    @Join(tagClass = StorageBean.class, type = JoinType.LEFT)
    private String storageId = "";

    @Join(tagClass = ProductBean.class)
    private String productId = "";

    @Join(tagClass = DepotBean.class)
    private String locationId = "";

    private String logTime = "";

    private String priceKey = "";

    private String virtualPriceKey = "";

    /**
     * 库存拥有者
     */
    private String owner = "";

    /**
     * 关联主键
     */
    private String refId = "";

    private double price = 0.0d;

    private double virtualPrice = 0.0d;

    /**
     * #925 单品码
     */
    private String sn;

    /**
     * 操作的用户
     */
    private String user = "";

    private String description = "";
    
    //add by fang 2012.5.4 @Ignore
    @Ignore
    private String industryId = "";
    
    @Ignore
    private String industryId2 = "";

    public StorageLogBean()
    {
    }

    /**
     * @return 返回 id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return 返回 depotpartId
     */
    public String getDepotpartId()
    {
        return depotpartId;
    }

    /**
     * @param
     */
    public void setDepotpartId(String depotpartId)
    {
        this.depotpartId = depotpartId;
    }

    /**
     * @return 返回 description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return 返回 productId
     */
    public String getProductId()
    {
        return productId;
    }

    /**
     * @param
     */
    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    /**
     * @return 返回 type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return 返回 preAmount
     */
    public int getPreAmount()
    {
        return preAmount;
    }

    /**
     * @param
     */
    public void setPreAmount(int preAmount)
    {
        this.preAmount = preAmount;
    }

    /**
     * @return 返回 afterAmount
     */
    public int getAfterAmount()
    {
        return afterAmount;
    }

    /**
     * @param
     */
    public void setAfterAmount(int afterAmount)
    {
        this.afterAmount = afterAmount;
    }

    /**
     * @return 返回 changeAmount
     */
    public int getChangeAmount()
    {
        return changeAmount;
    }

    /**
     * @param
     */
    public void setChangeAmount(int changeAmount)
    {
        this.changeAmount = changeAmount;
    }

    /**
     * @return 返回 storageId
     */
    public String getStorageId()
    {
        return storageId;
    }

    /**
     * @param
     */
    public void setStorageId(String storageId)
    {
        this.storageId = storageId;
    }

    /**
     * @return 返回 logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return 返回 locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the user
     */
    public String getUser()
    {
        return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user)
    {
        this.user = user;
    }

    /**
     * @return the serializeId
     */
    public String getSerializeId()
    {
        return serializeId;
    }

    /**
     * @param serializeId
     *            the serializeId to set
     */
    public void setSerializeId(String serializeId)
    {
        this.serializeId = serializeId;
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
     * @return the preAmount1
     */
    public int getPreAmount1()
    {
        return preAmount1;
    }

    /**
     * @param preAmount1
     *            the preAmount1 to set
     */
    public void setPreAmount1(int preAmount1)
    {
        this.preAmount1 = preAmount1;
    }

    /**
     * @return the afterAmount1
     */
    public int getAfterAmount1()
    {
        return afterAmount1;
    }

    /**
     * @param afterAmount1
     *            the afterAmount1 to set
     */
    public void setAfterAmount1(int afterAmount1)
    {
        this.afterAmount1 = afterAmount1;
    }

    /**
     * @return the preAmount2
     */
    public int getPreAmount2()
    {
        return preAmount2;
    }

    /**
     * @param preAmount2
     *            the preAmount2 to set
     */
    public void setPreAmount2(int preAmount2)
    {
        this.preAmount2 = preAmount2;
    }

    /**
     * @return the afterAmount2
     */
    public int getAfterAmount2()
    {
        return afterAmount2;
    }

    /**
     * @param afterAmount2
     *            the afterAmount2 to set
     */
    public void setAfterAmount2(int afterAmount2)
    {
        this.afterAmount2 = afterAmount2;
    }

    /**
     * @return the priceKey
     */
    public String getPriceKey()
    {
        return priceKey;
    }

    /**
     * @param priceKey
     *            the priceKey to set
     */
    public void setPriceKey(String priceKey)
    {
        this.priceKey = priceKey;
    }

    /**
     * @return the preAmount11
     */
    public int getPreAmount11()
    {
        return preAmount11;
    }

    /**
     * @param preAmount11
     *            the preAmount11 to set
     */
    public void setPreAmount11(int preAmount11)
    {
        this.preAmount11 = preAmount11;
    }

    /**
     * @return the afterAmount11
     */
    public int getAfterAmount11()
    {
        return afterAmount11;
    }

    /**
     * @param afterAmount11
     *            the afterAmount11 to set
     */
    public void setAfterAmount11(int afterAmount11)
    {
        this.afterAmount11 = afterAmount11;
    }

    /**
     * @return the preAmount22
     */
    public int getPreAmount22()
    {
        return preAmount22;
    }

    /**
     * @param preAmount22
     *            the preAmount22 to set
     */
    public void setPreAmount22(int preAmount22)
    {
        this.preAmount22 = preAmount22;
    }

    /**
     * @return the afterAmount22
     */
    public int getAfterAmount22()
    {
        return afterAmount22;
    }

    /**
     * @param afterAmount22
     *            the afterAmount22 to set
     */
    public void setAfterAmount22(int afterAmount22)
    {
        this.afterAmount22 = afterAmount22;
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
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }
    
    
    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getIndustryId2() {
        return industryId2;
    }

    public void setIndustryId2(String industryId2) {
        this.industryId2 = industryId2;
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

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        return "StorageLogBean{" +
                "id='" + id + '\'' +
                ", serializeId='" + serializeId + '\'' +
                ", type=" + type +
                ", preAmount=" + preAmount +
                ", afterAmount=" + afterAmount +
                ", preAmount1=" + preAmount1 +
                ", preAmount11=" + preAmount11 +
                ", afterAmount1=" + afterAmount1 +
                ", afterAmount11=" + afterAmount11 +
                ", preAmount2=" + preAmount2 +
                ", preAmount22=" + preAmount22 +
                ", afterAmount2=" + afterAmount2 +
                ", afterAmount22=" + afterAmount22 +
                ", changeAmount=" + changeAmount +
                ", depotpartId='" + depotpartId + '\'' +
                ", storageId='" + storageId + '\'' +
                ", productId='" + productId + '\'' +
                ", locationId='" + locationId + '\'' +
                ", logTime='" + logTime + '\'' +
                ", priceKey='" + priceKey + '\'' +
                ", virtualPriceKey='" + virtualPriceKey + '\'' +
                ", owner='" + owner + '\'' +
                ", refId='" + refId + '\'' +
                ", price=" + price +
                ", virtualPrice=" + virtualPrice +
                ", sn='" + sn + '\'' +
                ", user='" + user + '\'' +
                ", description='" + description + '\'' +
                ", industryId='" + industryId + '\'' +
                ", industryId2='" + industryId2 + '\'' +
                '}';
    }
}
