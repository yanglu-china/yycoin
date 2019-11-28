package com.china.center.oa.publics.bean;

import com.china.center.jdbc.annotation.Ignore;

import java.util.List;

public class AttachmentLinksBean {
    @Ignore
    private List<AttachmentBean> attachmentList;

    @Ignore
    protected String attachment1;
    @Ignore
    protected String attachment2;
    @Ignore
    protected String attachment3;
    @Ignore
    protected String attachment4;

    @Ignore
    protected String attachmentLink1;
    @Ignore
    protected String attachmentLink2;
    @Ignore
    protected String attachmentLink3;
    @Ignore
    protected String attachmentLink4;

    @Ignore
    protected boolean hideAttachment1 = true;
    @Ignore
    protected boolean hideAttachment2 = true;
    @Ignore
    protected boolean hideAttachment3 = true;
    @Ignore
    protected boolean hideAttachment4 = true;

    public List<AttachmentBean> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<AttachmentBean> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public String getAttachment1() {
        return attachment1;
    }

    public void setAttachment1(String attachment1) {
        this.attachment1 = attachment1;
    }

    public String getAttachment2() {
        return attachment2;
    }

    public void setAttachment2(String attachment2) {
        this.attachment2 = attachment2;
    }

    public String getAttachment3() {
        return attachment3;
    }

    public void setAttachment3(String attachment3) {
        this.attachment3 = attachment3;
    }

    public String getAttachment4() {
        return attachment4;
    }

    public void setAttachment4(String attachment4) {
        this.attachment4 = attachment4;
    }

    public String getAttachmentLink1() {
        return attachmentLink1;
    }

    public void setAttachmentLink1(String attachmentLink1) {
        this.attachmentLink1 = attachmentLink1;
    }

    public String getAttachmentLink2() {
        return attachmentLink2;
    }

    public void setAttachmentLink2(String attachmentLink2) {
        this.attachmentLink2 = attachmentLink2;
    }

    public String getAttachmentLink3() {
        return attachmentLink3;
    }

    public void setAttachmentLink3(String attachmentLink3) {
        this.attachmentLink3 = attachmentLink3;
    }

    public String getAttachmentLink4() {
        return attachmentLink4;
    }

    public void setAttachmentLink4(String attachmentLink4) {
        this.attachmentLink4 = attachmentLink4;
    }

    public boolean isHideAttachment1() {
        return hideAttachment1;
    }

    public void setHideAttachment1(boolean hideAttachment1) {
        this.hideAttachment1 = hideAttachment1;
    }

    public boolean isHideAttachment2() {
        return hideAttachment2;
    }

    public void setHideAttachment2(boolean hideAttachment2) {
        this.hideAttachment2 = hideAttachment2;
    }

    public boolean isHideAttachment3() {
        return hideAttachment3;
    }

    public void setHideAttachment3(boolean hideAttachment3) {
        this.hideAttachment3 = hideAttachment3;
    }

    public boolean isHideAttachment4() {
        return hideAttachment4;
    }

    public void setHideAttachment4(boolean hideAttachment4) {
        this.hideAttachment4 = hideAttachment4;
    }

    public void handleAttachmentLinks(){
        if(attachmentList!=null && attachmentList.size()>0){
            AttachmentBean bean = attachmentList.get(0);
            this.attachment1 = bean.getName();
            this.attachmentLink1 = bean.getPath();
            this.hideAttachment1 = false;

            if(attachmentList.size()>1){
                bean = attachmentList.get(1);
                this.attachment2 = bean.getName();
                this.attachmentLink2 = bean.getPath();
                this.hideAttachment2 = false;
            }

            if(attachmentList.size()>2){
                bean = attachmentList.get(2);
                this.attachment3 = bean.getName();
                this.attachmentLink3 = bean.getPath();
                this.hideAttachment3 = false;
            }

            if(attachmentList.size()>3){
                bean = attachmentList.get(3);
                this.attachment4 = bean.getName();
                this.attachmentLink4 = bean.getPath();
                this.hideAttachment4 = false;            }
        }
    }

}
