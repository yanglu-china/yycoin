package com.china.center.oa.stock.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

import java.io.Serializable;

@Entity(name = "比价表")
@Table(name = "T_PURCHASE_PJ_XQQR")
public class PurchaseXqqrBean implements Serializable {
    @Id(autoIncrement = true)
    private int id;

    private String demandId;

    private int pjId;

    private String pj;

    private String changetime;

    private String demandQRId;

    private int purchaseAmount;

    private String arrivalDate;

    private String remarks;

    private int purchaser;

    private int purchaseStatus;

    private String purchaseQRTime;

    private int operator;

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
}
