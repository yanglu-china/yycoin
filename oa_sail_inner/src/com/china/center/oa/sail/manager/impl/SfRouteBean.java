package com.china.center.oa.sail.manager.impl;

/**
 * @author SunQi
 * @Description:
 * @date Create in 2017/9/21 13:45
 */
public class SfRouteBean {
    //顺丰订单号
    private String mainno;
    //客户订单号
    private String orderid;
    //路由节点发生的时间；
    private String accept_time;
    //路由节点发生的地点
    private String accept_address;
    //路由节点具体描述
    private String remark;
    //路由节点操作码
    private String opcode;


    public String getMainno() {
        return mainno;
    }

    public void setMainno(String mainno) {
        this.mainno = mainno;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(String accept_time) {
        this.accept_time = accept_time;
    }

    public String getAccept_address() {
        return accept_address;
    }

    public void setAccept_address(String accept_address) {
        this.accept_address = accept_address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    @Override
    public String toString() {
        return "SfRouteBean{" +
                "mainno='" + mainno + '\'' +
                ", orderid='" + orderid + '\'' +
                ", accept_time='" + accept_time + '\'' +
                ", accept_address='" + accept_address + '\'' +
                ", remark='" + remark + '\'' +
                ", opcode='" + opcode + '\'' +
                '}';
    }
}
