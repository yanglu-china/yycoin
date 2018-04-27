package com.china.center.oa.sail.vo;

import com.china.center.oa.publics.bean.AttachmentBean;

import java.util.List;

public interface OutVOInterface extends OutInterface{
    String getIndustryName();

    String getIndustryName3();

    void setPresentFlagName(String presentFlagName);

    void setCustomerAddress(String customerAddress);
}
