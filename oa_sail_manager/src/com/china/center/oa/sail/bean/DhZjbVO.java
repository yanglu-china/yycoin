package com.china.center.oa.sail.bean;


/**
 *
 */
public class DhZjbVO extends DhZjbBean {
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

    @Override
    public String toString() {
        return "DhZjbVO{" +
                "zjHgAmount=" + zjHgAmount +
                ", zjBhgAmount=" + zjBhgAmount +
                ", sdAmount=" + sdAmount +
                ", sccgRkfx='" + sccgRkfx + '\'' +
                ", sccgCldz='" + sccgCldz + '\'' +
                ", depotpartId='" + depotpartId + '\'' +
                ", depotId='" + depotId + '\'' +
                ", price=" + price +
                "} " + super.toString();
    }
}
