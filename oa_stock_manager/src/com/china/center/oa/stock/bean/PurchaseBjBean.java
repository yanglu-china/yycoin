package com.china.center.oa.stock.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Table;

import java.io.Serializable;

@Entity(name = "比价表")
@Table(name = "T_PURCHASE_BJ")
public class PurchaseBjBean implements Serializable {
    @Id(autoIncrement = true)
    private int id;

    /**
     * 比价单号
     */
    private String bjNo;

    /**
     * 比价流程状态；1信息填写完毕给肖东柯；2肖东柯审批结束
     */
    private String bjStatus;

    /**
     * 需求确认单号
     */
    private String demandQRId;

    /**
     * 需求单号
     */
    private String demandId;

    /**
     * 配件
     */
    private int pjId;

    /**
     * 配件ID
     */
    private String pj;

    private String changeTime;

    /**
     * 供应商编码
     */
    private String supplier;

    @Ignore
    private String providerId;

    @Ignore
    private String providerName;

    private double price;

    /**
     * 发票类型
     */
    private String invoiceType;

    /**
     * 纳税实体
     */
    private String taxableEntity;

    /**
     * 建议供应商
     */
    private String adviseSupplier;

    /**
     * 确认供应商
     */
    private String confirmSupplier;

    private String remarks;

    /**
     * 三方比价审批状态；1通过0驳回
     */
    private int spStatus;

    /**
     * 审批备注
     */
    private String spRemarks;

    private String arrivalDate;

    @Ignore
    private int amount;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public String toString() {
        return "PurchaseBjBean{" +
                "id=" + id +
                ", bjNo='" + bjNo + '\'' +
                ", bjStatus='" + bjStatus + '\'' +
                ", demandQRId='" + demandQRId + '\'' +
                ", demandId='" + demandId + '\'' +
                ", pjId=" + pjId +
                ", pj='" + pj + '\'' +
                ", changeTime='" + changeTime + '\'' +
                ", supplier='" + supplier + '\'' +
                ", providerId='" + providerId + '\'' +
                ", providerName='" + providerName + '\'' +
                ", price=" + price +
                ", invoiceType='" + invoiceType + '\'' +
                ", taxableEntity='" + taxableEntity + '\'' +
                ", adviseSupplier='" + adviseSupplier + '\'' +
                ", confirmSupplier='" + confirmSupplier + '\'' +
                ", remarks='" + remarks + '\'' +
                ", spStatus=" + spStatus +
                ", spRemarks='" + spRemarks + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", amount=" + amount +
                '}';
    }
}
