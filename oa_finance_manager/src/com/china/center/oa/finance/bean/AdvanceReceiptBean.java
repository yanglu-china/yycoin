package com.china.center.oa.finance.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

import java.io.Serializable;

/**
 * #823
 */
@Entity
@Table(name = "t_center_advancereceipt")
public class AdvanceReceiptBean implements Serializable {

    @Id
    private String id = "";

    private String provider = "";

    private String sf = "";

    private int status = 0;

    private double gjMoney = 0.0;

    private double syMoney = 0.0;

    private String changeUser = "";

    private String changeTime = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSf() {
        return sf;
    }

    public void setSf(String sf) {
        this.sf = sf;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getGjMoney() {
        return gjMoney;
    }

    public void setGjMoney(double gjMoney) {
        this.gjMoney = gjMoney;
    }

    public double getSyMoney() {
        return syMoney;
    }

    public void setSyMoney(double syMoney) {
        this.syMoney = syMoney;
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

    @Override
    public String toString() {
        return "AdvanceReceiptBean{" +
                "id='" + id + '\'' +
                ", provider='" + provider + '\'' +
                ", sf='" + sf + '\'' +
                ", status=" + status +
                ", gjMoney=" + gjMoney +
                ", syMoney=" + syMoney +
                ", changeUser='" + changeUser + '\'' +
                ", changeTime='" + changeTime + '\'' +
                '}';
    }
}
