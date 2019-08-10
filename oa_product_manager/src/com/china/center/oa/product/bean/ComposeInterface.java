package com.china.center.oa.product.bean;

import java.util.List;

public interface ComposeInterface {
    String getProductId();

    void setProductId(String productId);

    String getDirTargerName();

    List<ComposeItemBean> getItemList();

    int getMtype();

    void setMtype(int mtype);
}
