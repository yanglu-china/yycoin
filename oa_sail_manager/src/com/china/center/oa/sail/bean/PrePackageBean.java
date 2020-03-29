package com.china.center.oa.sail.bean;

import com.china.center.jdbc.annotation.*;
import com.china.center.jdbc.annotation.enums.JoinType;
import java.io.Serializable;


@SuppressWarnings("serial")
@Entity(name = "预先分配快递单")
@Table(name = "T_CENTER_PREPACKAGE")
public class PrePackageBean implements Serializable
{
    /**
     * ID
     */
    @Id(autoIncrement = true)
    private String id = "";


    /**
     * 类型:1代表招商银行直邮，2代表京东，3代表天猫，其他后续再更新
     */
    private int type = 0;

    /**
     * 银行订单号
     */
    private String citicNo = "";

    /**
     * 快递公司
     */
    @Join(tagClass = ExpressBean.class, type = JoinType.LEFT)
    private int transport = 0;

    /**
     * 快递单号
     */
    private String transportNo = "";

    /**
     * 0代表未生成CK单，1代表已生成CK单
     */
    private int status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCiticNo() {
        return citicNo;
    }

    public void setCiticNo(String citicNo) {
        this.citicNo = citicNo;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PrePackageBean{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", citicNo='" + citicNo + '\'' +
                ", transport=" + transport +
                ", transportNo='" + transportNo + '\'' +
                ", status=" + status +
                '}';
    }
}