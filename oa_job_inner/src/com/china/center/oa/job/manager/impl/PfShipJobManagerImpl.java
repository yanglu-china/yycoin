package com.china.center.oa.job.manager.impl;

import com.china.center.oa.sail.bean.PackageItemBean;

import java.util.List;

public class PfShipJobManagerImpl extends AbstractShipJobManager{

    @Override
    protected String getKey(String customerId) {
        return null;
    }

    @Override
    protected void createMailAttachment(String customerName, List<PackageItemBean> beans, String branchName, String fileName, boolean ignoreLyOrders) {

    }
}
