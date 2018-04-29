package com.china.center.oa.sail.vo;

public interface DistributionVOInterface extends DistributionInterface{

    String getProvinceName();

    String getCityName();

    void setTransport(String transport);

    void setTransportNo(String transportNo);
}
