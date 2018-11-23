package com.china.center.oa.sail.bean;

import com.china.center.jdbc.annotation.Column;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;

import java.io.Serializable;

/**
 *
 */
@SuppressWarnings("serial")
@Entity(name = "预到货")
@Table(name = "t_center_dh_zjb")
public class DhZjbBean implements Serializable {
    private int id;

    private String stockId = "";

    private String productId = "";

    /**
     * 采购数量
     */
    @Column(name = "cg_amount")
    private int cgAmount;

    /**
     * 预到货数量
     */
    @Column(name = "ydh_amount")
    private int ydhAmount;

    private String transportNo;

    /**
     * 到货单号
     */
    private String dhNo;

    private String status;

    private String createTime;

    private String createUser;

    /**
     * 是否生成调拨单标志位
     */
    private int processed = 0;

    private String outId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getCgAmount() {
        return cgAmount;
    }

    public void setCgAmount(int cgAmount) {
        this.cgAmount = cgAmount;
    }

    public int getYdhAmount() {
        return ydhAmount;
    }

    public void setYdhAmount(int ydhAmount) {
        this.ydhAmount = ydhAmount;
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    public String getDhNo() {
        return dhNo;
    }

    public void setDhNo(String dhNo) {
        this.dhNo = dhNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }

    @Override
    public String toString() {
        return "DhZjbBean{" +
                "id=" + id +
                ", stockId='" + stockId + '\'' +
                ", productId='" + productId + '\'' +
                ", cgAmount=" + cgAmount +
                ", ydhAmount=" + ydhAmount +
                ", transportNo='" + transportNo + '\'' +
                ", dhNo='" + dhNo + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", processed=" + processed +
                ", outId='" + outId + '\'' +
                '}';
    }
}
