package com.china.center.oa.sail.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;

import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name = "t_center_frdb")
public class FrDbBean implements Serializable {
    private int id;

    /**
     * 流程ID
     */
    private String outId;

    /**
     * 体内、体外
     */
    private String sys;

    private String productId;

    private String productName;


    /**
     * 目的库
     */
    private String mdk;

    /**
     * 源仓库
     */
    private String yck;


    /**
     * #631 源仓区
     */
    private String ycq;

    /**
     * 虚料金额
     */
    private double virtualPrice;

    /**
     * 成本
     */
    private double cb;

    /**
     * 发货方式
     */
    private int fhfs;

    /**
     * 运输方式
     */
    private int ysfs;

    /**
     * 运费支付方式
     */
    private int yfPay;

    private String provinceId;

    private String cityId;

    private String address;

    private String receiver;

    private String telephone;

    private String dutyId;

    private String changeUser;

    private String changeTime;

    /**
     * 0:初始 1:通过  2:已生成DB单
     */
    private int status;

    /**
     * DB单号
     */
    private String dbno;

    /**
     * 实际数量
     */
    private int newAmount;

    private String errorMessage;

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    public String getSys() {
        return sys;
    }

    public void setSys(String sys) {
        this.sys = sys;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMdk() {
        return mdk;
    }

    public void setMdk(String mdk) {
        this.mdk = mdk;
    }

    public String getYck() {
        return yck;
    }

    public void setYck(String yck) {
        this.yck = yck;
    }

    public double getCb() {
        return cb;
    }

    public void setCb(double cb) {
        this.cb = cb;
    }

    public int getFhfs() {
        return fhfs;
    }

    public void setFhfs(int fhfs) {
        this.fhfs = fhfs;
    }

    public int getYsfs() {
        return ysfs;
    }

    public void setYsfs(int ysfs) {
        this.ysfs = ysfs;
    }

    public int getYfPay() {
        return yfPay;
    }

    public void setYfPay(int yfPay) {
        this.yfPay = yfPay;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDutyId() {
        return dutyId;
    }

    public void setDutyId(String dutyId) {
        this.dutyId = dutyId;
    }

    public String getChangeUser() {
        return changeUser;
    }

    public void setChangeUser(String changeUser) {
        this.changeUser = changeUser;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDbno() {
        return dbno;
    }

    public void setDbno(String dbno) {
        this.dbno = dbno;
    }

    public int getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(int newAmount) {
        this.newAmount = newAmount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getYcq() {
        return ycq;
    }

    public void setYcq(String ycq) {
        this.ycq = ycq;
    }

    public double getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(double virtualPrice) {
        this.virtualPrice = virtualPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FrDbBean{" +
                "id=" + id +
                ", outId='" + outId + '\'' +
                ", sys='" + sys + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", mdk='" + mdk + '\'' +
                ", yck='" + yck + '\'' +
                ", ycq='" + ycq + '\'' +
                ", virtualPrice=" + virtualPrice +
                ", cb=" + cb +
                ", fhfs=" + fhfs +
                ", ysfs=" + ysfs +
                ", yfPay=" + yfPay +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", address='" + address + '\'' +
                ", receiver='" + receiver + '\'' +
                ", telephone='" + telephone + '\'' +
                ", dutyId='" + dutyId + '\'' +
                ", changeUser='" + changeUser + '\'' +
                ", changeTime='" + changeTime + '\'' +
                ", status=" + status +
                ", dbno='" + dbno + '\'' +
                ", newAmount=" + newAmount +
                ", errorMessage='" + errorMessage + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
