/**
 * File Name: BaseBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-26
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import com.china.center.jdbc.annotation.*;
import java.io.Serializable;


/**
 * #281
 * 
 * @author ZHUZHU
 * @version 2016-07-26
 * @see
 * @since
 */
@Entity
@Table(name = "T_CENTER_OLBASE")
public class OlBaseBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String outId = "";

    private String oaNo = "";

    private String productCode = "";

    private String productName = "";

    private int amount = 0;

    /**
     * 业务员销售价格
     */
    private double price = 0.0d;


    /**2015/04/11 中收激励功能
     * 中收金额
     */
    private double ibMoney = -1;

    /**2015/04/11 中收激励功能
     * 激励金额
     */
    private double motivationMoney = -1;

    private double ibMoney2 = -1;

    private double motivationMoney2 = -1;

    /**
     * 平台手续费
     */
    private double platformFee = -1;

    private double cash = -1;

    private double cash2 = -1;

    private double grossProfit = -1;

    private String productImportId = "";

    private String changeTime = "";

    private String depot = "";

    private String depotpart = "";

    /**
     * #779
     */
    private double settlePrice = 0.0d;

    /**
     * default constructor
     */
    public OlBaseBean()
    {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getOaNo() {
        return oaNo;
    }

    public void setOaNo(String oaNo) {
        this.oaNo = oaNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
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

    public double getCash2() {
        return cash2;
    }

    public void setCash2(double cash2) {
        this.cash2 = cash2;
    }

    public String getDepotpart() {
        return depotpart;
    }

    public void setDepotpart(String depotpart) {
        this.depotpart = depotpart;
    }

    public double getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(double settlePrice) {
        this.settlePrice = settlePrice;
    }

    @Override
    public String toString() {
        return "OlBaseBean{" +
                "id='" + id + '\'' +
                ", outId='" + outId + '\'' +
                ", oaNo='" + oaNo + '\'' +
                ", productCode='" + productCode + '\'' +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", ibMoney=" + ibMoney +
                ", motivationMoney=" + motivationMoney +
                ", ibMoney2=" + ibMoney2 +
                ", motivationMoney2=" + motivationMoney2 +
                ", platformFee=" + platformFee +
                ", changeTime='" + changeTime + '\'' +
                ", depot='" + depot + '\'' +
                ", depotpart='" + depotpart + '\'' +
                ", cash=" + cash +
                ", cash2=" + cash2 +
                ", grossProfit=" + grossProfit +
                ", productImportId='" + productImportId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OlBaseBean that = (OlBaseBean) o;

        if (getAmount() != that.getAmount()) return false;
        if (!getOutId().equals(that.getOutId())) return false;
        if (!getProductCode().equals(that.getProductCode())) return false;
        return getProductName().equals(that.getProductName());

    }

    @Override
    public int hashCode() {
        int result = getOutId().hashCode();
        result = 31 * result + getProductCode().hashCode();
        result = 31 * result + getProductName().hashCode();
        result = 31 * result + getAmount();
        return result;
    }
}
