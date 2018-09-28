package com.china.center.oa.job.manager.impl;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.BranchRelationBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.oa.sail.vo.PackageVO;
import com.china.center.tools.TimeTools;

import java.util.List;

/**
 * 甘肃银行邮件
 */
public class GsShipJobManagerImpl extends ShipJobManagerImpl{

    @Override
    protected List<PackageVO> getPackageList() {
        ConditionParse con = new ConditionParse();
        con.addWhereStr();
        con.addIntCondition("PackageBean.sendMailFlag", "=", 0);
        con.addCondition("PackageBean.logTime", ">=", "2017-04-27 00:00:00");
        //自提类的也不在发送邮件范围内
        con.addIntCondition("PackageBean.shipping","!=", 0);
        //#236 已发货和在途都要发邮件
        con.addCondition(" and PackageBean.status in(2,10)");

        //step1: 根据支行customerId+channel对CK单合并
        List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);
        return packageList;
    }

    @Override
    protected boolean needSendMail(String customerName, String channel) {
        if (customerName.indexOf("甘肃银行") != -1 &&
                "网上商城".equals(channel) ){
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
        String fileName = getShippingAttachmentPath() + "/甘肃银行"+"_"+ TimeTools.now("yyyyMMddHHmmss") + ".xls";
        return fileName;
    }

    @Override
    protected BranchRelationBean getRelation(String customerId, String channel) {
        return this.getRelationByCustomerId(customerId, channel);
    }
}
