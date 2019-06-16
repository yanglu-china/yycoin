/**
 * File Name: SailConfBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2012-2-12<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.jdbc.clone.DataClone;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.sail.constanst.SailConstant;


/**
 * SailConfBean
 * 
 * @author ZHUZHU
 * @version 2012-2-12
 * @see SailConfBean
 * @since 3.0
 */
@Entity(cache = false)
@Table(name = "T_CENTER_SAILCONF")
public class SailConfBean implements DataClone<SailConfBean>, Serializable
{
    @Id
    private String id = "";

    /**
     * ProductConstant.SAILTYPE_SELF
     */
    @Html(title = "销售类型", type = Element.SELECT)
    private int sailType = -1;

    /**
     * 产品类型/ProductConstant.PRODUCT_TYPE_OTHER
     */
    @Html(title = "产品类型", type = Element.SELECT)
    private int productType = -1;

    /**
     * 0:产品(0) 1:产品类型(1) 2：销售类型(2)
     */
    private int type = SailConstant.SAILCONFIG_ONLYPRODUCT;

    @Html(title = "品名", type = Element.INPUT, name = "productName", readonly = true)
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT)
    private String productId = "";

    @Html(title = "事业部", type = Element.SELECT, must = true)
    @Join(tagClass = PrincipalshipBean.class)
    private String industryId = "";

    @Html(title = "总部结算率(‰)", type = Element.INPUT)
    private double pratio = 0.0d;

    @Html(title = "事业部结算率(‰)", type = Element.INPUT)
    private double iratio = 0.0d;

    @Html(title = "起始时间", type = Element.INPUT)
    private String beginDate = "";

    @Html(title = "截止时间", type = Element.INPUT)
    private String endDate = "";

    @Html(title = "描述", maxLength = 100, type = Element.TEXTAREA)
    private String description = "";

    @Html(title = "更新时间", type = Element.INPUT)
    private String logTime = "";

    @Html(title = "更新人", type = Element.INPUT)
    private String operator = "";

    @Html(title = "固定业务员结算价", type = Element.INPUT)
    private double iprice = 0;
    /**
     * Copy Constructor
     * 
     * @param sailConfBean
     *            a <code>SailConfBean</code> object
     */
    public SailConfBean(SailConfBean sailConfBean)
    {
        this.id = sailConfBean.id;
        this.sailType = sailConfBean.sailType;
        this.productType = sailConfBean.productType;
        this.type = sailConfBean.type;
        this.productId = sailConfBean.productId;
        this.industryId = sailConfBean.industryId;
        this.pratio = sailConfBean.pratio;
        this.iratio = sailConfBean.iratio;
        this.beginDate = sailConfBean.beginDate;
        this.endDate = sailConfBean.endDate;
        this.description = sailConfBean.description;
    }

    /**
     * default constructor
     */
    public SailConfBean()
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

    /**
     * @return the sailType
     */
    public int getSailType()
    {
        return sailType;
    }

    /**
     * @param sailType
     *            the sailType to set
     */
    public void setSailType(int sailType)
    {
        this.sailType = sailType;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the productId
     */
    public String getProductId()
    {
        return productId;
    }

    /**
     * @param productId
     *            the productId to set
     */
    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    /**
     * @return the industryId
     */
    public String getIndustryId()
    {
        return industryId;
    }

    /**
     * @param industryId
     *            the industryId to set
     */
    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
    }

    public double getPratio() {
        return pratio;
    }

    public void setPratio(double pratio) {
        this.pratio = pratio;
    }

    public double getIratio() {
        return iratio;
    }

    public void setIratio(double iratio) {
        this.iratio = iratio;
    }

    /**
     * @return the beginDate
     */
    public String getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param beginDate
     *            the beginDate to set
     */
    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * @param endDate
     *            the endDate to set
     */
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the productType
     */
    public int getProductType()
    {
        return productType;
    }

    /**
     * @param productType
     *            the productType to set
     */
    public void setProductType(int productType)
    {
        this.productType = productType;
    }

    public SailConfBean clones()
    {
        return new SailConfBean(this);
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public double getIprice() {
        return iprice;
    }

    public void setIprice(double iprice) {
        this.iprice = iprice;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        return "SailConfBean{" +
                "id='" + id + '\'' +
                ", sailType=" + sailType +
                ", productType=" + productType +
                ", type=" + type +
                ", productId='" + productId + '\'' +
                ", industryId='" + industryId + '\'' +
                ", pratio=" + pratio +
                ", iratio=" + iratio +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", description='" + description + '\'' +
                ", logTime='" + logTime + '\'' +
                ", operator='" + operator + '\'' +
                ", iprice=" + iprice +
                '}';
    }

}
