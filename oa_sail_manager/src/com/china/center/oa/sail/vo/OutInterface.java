package com.china.center.oa.sail.vo;

import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.sail.bean.BaseInterface;

import java.util.List;

public interface OutInterface {
    String getFullId();

    String getCustomerId();

    String getStafferId();

    String getStafferName();

    double getTotal();

    String getOutTime();

    String getDescription();

    void setDescription(String description);

    String getCustomerDescription();

    int getEmergency();

    String getCustomerName();

    int getType();

    int getOutType();

    String getPodate();

    String getRefOutFullId();

    String getSwbz();

    List<AttachmentBean> getAttachmentList();

    void setAttachmentList(List<AttachmentBean> attachmentList);

    String getEventId();

    String getIndustryId();

    int getStatus();

    void setStatus(int status);

    String getDestinationId();

    int getInway();

    int getPresentFlag();

    void setPay(int pay);

    double getBadDebts();

    double getPromValue();

    List<? extends BaseInterface> getBaseList();
}
