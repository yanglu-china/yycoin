/**
 * File Name: TcpApplyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;


import com.china.center.jdbc.annotation.*;
import com.china.center.oa.tcp.constanst.TcpConstanst;

import java.io.Serializable;


/**
 * 中收激励申请表
 * 
 * @author ZHUZHU
 * @version 2015-04-09
 * @see TcpIbBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_TCPIB")
public class TcpIbBean implements Serializable
{
    @Id
    private String id = "";

    @FK
    private String refId = "";

    /**
     * 申请类型：中收: 0 激励：1 中收2: 2 激励2：3
     */
    private int type = TcpConstanst.IB_TYPE;

    private String customerName = "";

    //2015/5/17 fullId保存一次申请时的所有订单号，以;分隔
    private String fullId = "";

    private String productName = "";

    @Ignore
    private String productId = "";

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
     * 中收金额2
     */
    private double ibMoney2 = 0.0d;

    /**
     * 激励金额2
     */
    private double motivationMoney2 = 0.0d;

    /**
     * 平台手续费
     */
    private double platformFee = 0.0d;

    private String logTime = "";

    /**
     * default constructor
     */
    public TcpIbBean()
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
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
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

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(double platformFee) {
        this.platformFee = platformFee;
    }

    @Override
    public String toString() {
        return "TcpIbBean{" +
                "id='" + id + '\'' +
                ", refId='" + refId + '\'' +
                ", type=" + type +
                ", customerName='" + customerName + '\'' +
                ", fullId='" + fullId + '\'' +
                ", productName='" + productName + '\'' +
                ", productId='" + productId + '\'' +
                ", amount=" + amount +
                ", ibMoney=" + ibMoney +
                ", motivationMoney=" + motivationMoney +
                ", ibMoney2=" + ibMoney2 +
                ", motivationMoney2=" + motivationMoney2 +
                ", logTime='" + logTime + '\'' +
                '}';
    }
}
