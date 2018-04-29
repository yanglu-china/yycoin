package com.china.center.oa.sail.bean;

public interface BaseInterface {
    String getId();

    String getProductId();

    String getProductName();

    int getAmount();

    double getPrice();

    double getValue();

    void setProductType(int productType);

    void setOldGoods(int oldGoods);

    double getCostPrice();

    void setCostPrice(double costPrice);

    void setDescription(String description);

    double getInputPrice();

    double getIprice();

    double getPprice();
}
