/*
 * File Name: Product.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-25
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import com.china.center.jdbc.annotation.*;
import com.china.center.oa.publics.IdInterface;
import java.io.Serializable;


/**
 * #798
 * 
 * @author ZHUZHU
 * @version 2019-10-23
 * @see
 * @since
 */
@Entity
@Table(name = "t_center_twthproduct")
public class TwthProductBean implements Serializable, IdInterface
{
    @Id
    private String id = "";

    private String productId = "";

    private String productName = "";

    private String twProductId = "";

    private String twProductName = "";

    private double twPrice = 0.0;

    private String createTime = "";

    private String creater = "";

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTwProductId() {
        return twProductId;
    }

    public void setTwProductId(String twProductId) {
        this.twProductId = twProductId;
    }

    public String getTwProductName() {
        return twProductName;
    }

    public void setTwProductName(String twProductName) {
        this.twProductName = twProductName;
    }

    public double getTwPrice() {
        return twPrice;
    }

    public void setTwPrice(double twPrice) {
        this.twPrice = twPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Override
    public String toString() {
        return "TwthProductBean{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", twProductId='" + twProductId + '\'' +
                ", twProductName='" + twProductName + '\'' +
                ", twPrice=" + twPrice +
                ", createTime='" + createTime + '\'' +
                ", creater='" + creater + '\'' +
                '}';
    }
}
