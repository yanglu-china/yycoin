package com.china.center.oa.job.manager.impl;

import com.china.center.oa.sail.bean.BranchRelationBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.oa.sail.constanst.ShipConstant;

import java.util.List;

/**
 * 除浦发银行邮件JOB
 */
public class ShipJobManagerImpl extends AbstractShipJobManager{

    @Override
    protected String getKey(String customerId, PackageItemBean itemBean) {
        //其他银行不考虑渠道
        return customerId;
    }

    @Override
    protected BranchRelationBean getRelation(String customerId, String channel) {
        //其他银行不考虑渠道
        return this.getRelationByCustomerId(customerId,"");
    }

    @Override
    protected void createMailAttachment(String customerName,String channel, List<PackageItemBean> beans,
                                        String branchName, String fileName, boolean ignoreLyOrders) {
        //浦发上海分行
        if (customerName.indexOf("浦发银行") != -1 ){
            //refer to #117 and JobManagerImpl
            continue;
        }else if (customerName.indexOf("南京银行") != -1 ){
            index += 1;
            boolean result = createMailAttachmentNj(index, customerName, beans,branchName,fileName,true);
            if (!result){
                continue;
            }
        } else{
            this.createMailAttachment(ShipConstant.BANK_TYPE_OTHER, customerName, beans,branchName, fileName, true);
        }
    }
}
