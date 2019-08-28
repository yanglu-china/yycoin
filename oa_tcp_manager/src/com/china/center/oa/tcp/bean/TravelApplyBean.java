/**
 * File Name: TravelApplyBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.tcp.constanst.TcpConstanst;


/**
 * 借款申请
 * 
 * @author ZHUZHU
 * @version 2011-7-10
 * @see TravelApplyBean
 * @since 3.0
 */
@Entity(inherit = true)
@Table(name = "T_CENTER_TRAVELAPPLY")
public class TravelApplyBean extends AbstractTcpBean implements Serializable
{
    @Id
    @Html(title = "标识", must = true, maxLength = 40)
    private String id = "";

    @Html(title = "原申请单号", must = false,maxLength = 40)
    private String oldNumber = "";
    
    
    
    @Html(title = "目的", maxLength = 200, must = true, type = Element.TEXTAREA)
    private String qingJiapurpose = "";
    
    /**
     * 
     */
    @Html(title = "借款", must = true, type = Element.SELECT)
    private int borrow = TcpConstanst.TRAVELAPPLY_BORROW_NO;
    
    /**
     * 
     */
    @Html(title = "目的类型", must = true, type = Element.SELECT)
    private int purposeType=TcpConstanst.PURPOSETYPE ;
    
    /**
     * 是否借款
     */
    @Html(title = "请假类型", type = Element.SELECT)
    private int vocationType=TcpConstanst.VOCATIONTYPE ;

    /**
     * 是否报销冲抵
     */
    private int feedback = TcpConstanst.TCP_APPLY_FEEDBACK_NO;


    /**
     * 2015/4/12 中收激励导入功能
     * 0: 非导入 1：导入
     */
    private boolean importFlag = false;
    /**
     * 中收申请类型：中收: 0 激励：1 中收2: 2 其他费用：3 平台手续费：4 未回款订单激励:5
     */
    private int ibType = -1;

    /**
     * #248 默认非营销
     */
    @Html(title = "非营销/营销", must = true, type = Element.SELECT)
    private int marketingFlag = 0;

    /**
     * default constructor
     */
    public TravelApplyBean()
    {
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    public int getMarketingFlag() {
        return marketingFlag;
    }

    public void setMarketingFlag(int marketingFlag) {
        this.marketingFlag = marketingFlag;
    }

    /**
     * @return the borrow
     */
    public int getBorrow()
    {
        return borrow;
    }

    /**
     * @param borrow
     *            the borrow to set
     */
    public void setBorrow(int borrow)
    {
        this.borrow = borrow;
    }

    /**
     * @return the feedback
     */
    public int getFeedback()
    {
        return feedback;
    }

    /**
     * @param feedback
     *            the feedback to set
     */
    public void setFeedback(int feedback)
    {
        this.feedback = feedback;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        return "TravelApplyBean{" +
                "id='" + id + '\'' +
                ", oldNumber='" + oldNumber + '\'' +
                ", qingJiapurpose='" + qingJiapurpose + '\'' +
                ", borrow=" + borrow +
                ", purposeType=" + purposeType +
                ", vocationType=" + vocationType +
                ", feedback=" + feedback +
                ", importFlag=" + importFlag +
                ", ibType=" + ibType +
                ", marketingFlag=" + marketingFlag +
                "} " + super.toString();
    }

    public int getPurposeType() {
		return purposeType;
	}

	public void setPurposeType(int purposeType) {
		this.purposeType = purposeType;
	}

	public int getVocationType() {
		return vocationType;
	}

	public void setVocationType(int vocationType) {
		this.vocationType = vocationType;
	}

	public String getOldNumber() {
		return oldNumber;
	}

	public void setOldNumber(String oldNumber) {
		this.oldNumber = oldNumber;
	}

	public String getQingJiapurpose() {
		return qingJiapurpose;
	}

	public void setQingJiapurpose(String qingJiapurpose) {
		this.qingJiapurpose = qingJiapurpose;
	}

    public boolean isImportFlag() {
        return importFlag;
    }

    public void setImportFlag(boolean importFlag) {
        this.importFlag = importFlag;
    }

    public int getIbType() {
        return ibType;
    }

    public void setIbType(int ibType) {
        this.ibType = ibType;
    }
}
