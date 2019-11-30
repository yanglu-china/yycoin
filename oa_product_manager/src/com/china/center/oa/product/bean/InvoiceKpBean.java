/*
 * File Name: Product.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2007-3-25
 * Grant: open source to everybody
 */
package com.china.center.oa.product.bean;


import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.IdInterface;

import java.io.Serializable;


/**
 * #854
 * 
 * @author smartman
 * @version 2019-11-30
 * @see
 * @since
 */
@Entity
@Table(name = "t_center_invoice_kp")
public class InvoiceKpBean implements Serializable
{
    @Id
    private String id = "";

    private String name = "";

    private String kpslid = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKpslid() {
        return kpslid;
    }

    public void setKpslid(String kpslid) {
        this.kpslid = kpslid;
    }

    @Override
    public String toString() {
        return "InvoiceKpBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", kpslid='" + kpslid + '\'' +
                '}';
    }
}
