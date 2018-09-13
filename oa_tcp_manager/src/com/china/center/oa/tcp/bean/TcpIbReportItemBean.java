/**
 * File Name: TcpApplyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.tools.TimeTools;

import java.io.Serializable;


/**
 * 中收激励统计明细表
 * 
 * @author ZHUZHU
 * @version 2015-04-13
 * @see com.china.center.oa.tcp.bean.TcpIbReportItemBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_TCPIBREPORT_ITEM")
public class TcpIbReportItemBean implements Serializable
{
    @Id
    private String id = "";

    /**
     * 对应的TcpIbReportBean ID
     */
    @FK
    private String refId = "";

    private String customerName = "";

    private String fullId = "";

    private String productName = "";

    private String productId = "";

    private double price = 0.0d;

    private int amount = 0;

    /**
     * 中收金额
     */
    private double ibMoney = 0.0d;

    /**
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

    /**
     * default constructor
     */
    public TcpIbReportItemBean()
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


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFullId() {
        return fullId;
    }

    public void setFullId(String fullId) {
        this.fullId = fullId;
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

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    @Override
    public String toString() {
        return "TcpIbReportItemBean{" +
                "id='" + id + '\'' +
                ", refId='" + refId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", fullId='" + fullId + '\'' +
                ", productName='" + productName + '\'' +
                ", productId='" + productId + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", ibMoney=" + ibMoney +
                ", motivationMoney=" + motivationMoney +
                ", ibMoney2=" + ibMoney2 +
                ", motivationMoney2=" + motivationMoney2 +
                ", platformFee=" + platformFee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TcpIbReportItemBean that = (TcpIbReportItemBean) o;

        if (Double.compare(that.price, price) != 0) return false;
        if (amount != that.amount) return false;
        if (Double.compare(that.ibMoney, ibMoney) != 0) return false;
        if (Double.compare(that.motivationMoney, motivationMoney) != 0) return false;
        if (Double.compare(that.ibMoney2, ibMoney2) != 0) return false;
        if (Double.compare(that.motivationMoney2, motivationMoney2) != 0) return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (fullId != null ? !fullId.equals(that.fullId) : that.fullId != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        return productId != null ? productId.equals(that.productId) : that.productId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = customerName != null ? customerName.hashCode() : 0;
        result = 31 * result + (fullId != null ? fullId.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + amount;
        temp = Double.doubleToLongBits(ibMoney);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(motivationMoney);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(ibMoney2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(motivationMoney2);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
