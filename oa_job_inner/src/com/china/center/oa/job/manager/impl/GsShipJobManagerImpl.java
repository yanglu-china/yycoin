package com.china.center.oa.job.manager.impl;

import com.china.center.oa.sail.bean.BranchRelationBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.tools.TimeTools;

/**
 * 甘肃银行邮件
 */
public class GsShipJobManagerImpl extends ShipJobManagerImpl{

    @Override
    protected boolean needSendMail(String customerName, String channel) {
        if (customerName.indexOf("甘肃银行") != -1 ){
            return true;
        }
        return false;
    }

    @Override
    protected String getKey(PackageItemBean itemBean) {
        //根据支行邮件地址分组
        String customerId = itemBean.getCustomerId();
        String channel = this.getChannel(itemBean);
        BranchRelationBean branchRelationBean = this.getRelation(customerId, channel);
        if (branchRelationBean == null){
            return itemBean.getCustomerId()+"_"+this.getChannel(itemBean);
        } else{
            return branchRelationBean.getSubBranchMail();
        }
    }

    @Override
    protected String getAttachmentFileName(BranchRelationBean bean) {
        String fileName = getShippingAttachmentPath() + "/甘肃银行"
                + "_" +bean.getSubBranchMail()+"_"+ TimeTools.now("yyyyMMddHHmmss") + ".xls";
        return fileName;
    }

    @Override
    protected BranchRelationBean getRelation(String customerId, String channel) {
        return this.getRelationByCustomerId(customerId, channel);
    }
}
