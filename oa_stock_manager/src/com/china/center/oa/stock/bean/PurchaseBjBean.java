package com.china.center.oa.stock.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

import java.io.Serializable;

@Entity(name = "比价表")
@Table(name = "T_PURCHASE_BJ")
public class PurchaseBjBean implements Serializable {
    @Id(autoIncrement = true)
    private int id;

    private String bjNo;

    private String bjStatus;

    private String demandQRId;

    private String demandId;

    private int pjId;

    private String pj;

    private String changeTime;

    private String supplier;

    private double price;

    private String invoiceType;

    private String taxableEntity;

    private String adviseSupplier;

    private String confirmSupplier;

    private String remarks;

    private int spStatus;

    private String spRemarks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBjNo() {
        return bjNo;
    }

    public void setBjNo(String bjNo) {
        this.bjNo = bjNo;
    }

    public String getBjStatus() {
        return bjStatus;
    }

    public void setBjStatus(String bjStatus) {
        this.bjStatus = bjStatus;
    }

    public String getDemandQRId() {
        return demandQRId;
    }

    public void setDemandQRId(String demandQRId) {
        this.demandQRId = demandQRId;
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

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getTaxableEntity() {
        return taxableEntity;
    }

    public void setTaxableEntity(String taxableEntity) {
        this.taxableEntity = taxableEntity;
    }

    public String getAdviseSupplier() {
        return adviseSupplier;
    }

    public void setAdviseSupplier(String adviseSupplier) {
        this.adviseSupplier = adviseSupplier;
    }

    public String getConfirmSupplier() {
        return confirmSupplier;
    }

    public void setConfirmSupplier(String confirmSupplier) {
        this.confirmSupplier = confirmSupplier;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getSpStatus() {
        return spStatus;
    }

    public void setSpStatus(int spStatus) {
        this.spStatus = spStatus;
    }

    public String getSpRemarks() {
        return spRemarks;
    }

    public void setSpRemarks(String spRemarks) {
        this.spRemarks = spRemarks;
    }
}
