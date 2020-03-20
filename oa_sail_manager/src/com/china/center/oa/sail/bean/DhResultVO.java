package com.china.center.oa.sail.bean;


import com.china.center.jdbc.annotation.Column;

import java.io.Serializable;

/**
 *
 */
public class DhResultVO implements Serializable {

    private int resultId;

    private int zbId;

    private int type;

    private String stockId = "";

    private String productId = "";


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
    /**
     * 质检合格数量
     */
    private int zjHgAmount;

    /**
     * 质检不合格数量
     */
    private int zjBhgAmount;

    /**
     * 实到数量
     */
    private int sdAmount;

    /**
     * 生产采购目的仓库
     */
    private String sccgRkfx = "";

    private String sccgCldz = "";

    private String depotpartId;

    /**
     * 源仓库
     */
    private String depotId;

    private double price;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getZjHgAmount() {
        return zjHgAmount;
    }

    public void setZjHgAmount(int zjHgAmount) {
        this.zjHgAmount = zjHgAmount;
    }

    public String getSccgRkfx() {
        return sccgRkfx;
    }

    public void setSccgRkfx(String sccgRkfx) {
        this.sccgRkfx = sccgRkfx;
    }

    public int getZjBhgAmount() {
        return zjBhgAmount;
    }

    public void setZjBhgAmount(int zjBhgAmount) {
        this.zjBhgAmount = zjBhgAmount;
    }

    public int getSdAmount() {
        return sdAmount;
    }

    public void setSdAmount(int sdAmount) {
        this.sdAmount = sdAmount;
    }

    public String getDepotpartId() {
        return depotpartId;
    }

    public void setDepotpartId(String depotpartId) {
        this.depotpartId = depotpartId;
    }

    public String getDepotId() {
        return depotId;
    }

    public void setDepotId(String depotId) {
        this.depotId = depotId;
    }

    public String getSccgCldz() {
        return sccgCldz;
    }

    public void setSccgCldz(String sccgCldz) {
        this.sccgCldz = sccgCldz;
    }

    public int getResultId() {
        return resultId;
    }

    public void setResultId(int resultId) {
        this.resultId = resultId;
    }

    public int getZbId() {
        return zbId;
    }

    public void setZbId(int zbId) {
        this.zbId = zbId;
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
        return "DhResultVO{" +
                "resultId=" + resultId +
                ", zbId=" + zbId +
                ", type=" + type +
                ", stockId='" + stockId + '\'' +
                ", productId='" + productId + '\'' +
                ", ydhAmount=" + ydhAmount +
                ", transportNo='" + transportNo + '\'' +
                ", dhNo='" + dhNo + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createUser='" + createUser + '\'' +
                ", processed=" + processed +
                ", outId='" + outId + '\'' +
                ", zjHgAmount=" + zjHgAmount +
                ", zjBhgAmount=" + zjBhgAmount +
                ", sdAmount=" + sdAmount +
                ", sccgRkfx='" + sccgRkfx + '\'' +
                ", sccgCldz='" + sccgCldz + '\'' +
                ", depotpartId='" + depotpartId + '\'' +
                ", depotId='" + depotId + '\'' +
                ", price=" + price +
                '}';
    }
}
