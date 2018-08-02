package com.china.center.oa.stock.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

import java.io.Serializable;

@Entity(name = "采购需求确认表")
@Table(name = "T_PURCHASE_PJ_XQQR")
public class PurchaseXqqrBean implements Serializable {
    @Id(autoIncrement = true)
    private int id;

    /**
     * 需求单号
     */
    private String demandId;

    /**
     * 配件
     */
    private int pjId;

    private String pj;

    private String changetime;

    /**
     * 需求确认单号
     */
    private String demandQRId;

    /**
     * 采购数量
     */
    private int purchaseAmount;

    /**
     * 已采购数量
     */
    private int nhNum;

    /**
     * 预计采购到货时间
     */
    private String arrivalDate;


    /**
     * 采购确认时间
     */
    private String purchaseQRTime;

    /**
     * 备注
     */
    private String remarks;


    /**
     * 采购人
     */
    private int purchaser;

    /**
     * 是否采购；0初始；99待三方比价；1比价；2不比价；3强制结束
     */
    private int purchaseStatus;


    private int operator;

    /**
     * 比价流程状态；1信息填写完毕给肖东柯；2肖东柯审批结束
     */
    private int bjStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public int getPjId() {
        return pjId;
    }

    public void setPjId(int pjId) {
        this.pjId = pjId;
    }

    public String getPj() {
        return pj;
    }

    public void setPj(String pj) {
        this.pj = pj;
    }

    public String getChangetime() {
        return changetime;
    }

    public void setChangetime(String changetime) {
        this.changetime = changetime;
    }

    public String getDemandQRId() {
        return demandQRId;
    }

    public void setDemandQRId(String demandQRId) {
        this.demandQRId = demandQRId;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(int purchaser) {
        this.purchaser = purchaser;
    }

    public int getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(int purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public String getPurchaseQRTime() {
        return purchaseQRTime;
    }

    public void setPurchaseQRTime(String purchaseQRTime) {
        this.purchaseQRTime = purchaseQRTime;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getBjStatus() {
        return bjStatus;
    }

    public void setBjStatus(int bjStatus) {
        this.bjStatus = bjStatus;
    }

    public int getNhNum() {
        return nhNum;
    }

    public void setNhNum(int nhNum) {
        this.nhNum = nhNum;
    }

    @Override
    public String toString() {
        return "PurchaseXqqrBean{" +
                "id=" + id +
                ", demandId='" + demandId + '\'' +
                ", pjId=" + pjId +
                ", pj='" + pj + '\'' +
                ", changetime='" + changetime + '\'' +
                ", demandQRId='" + demandQRId + '\'' +
                ", purchaseAmount=" + purchaseAmount +
                ", nhNum=" + nhNum +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", purchaseQRTime='" + purchaseQRTime + '\'' +
                ", remarks='" + remarks + '\'' +
                ", purchaser=" + purchaser +
                ", purchaseStatus=" + purchaseStatus +
                ", operator=" + operator +
                ", bjStatus=" + bjStatus +
                '}';
    }
}
