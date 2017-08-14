package com.china.center.oa.sail.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

import java.io.Serializable;

/**
 * Created by smartman on 2017/84.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_PRESENTFLAG")
public class PresentFlagBean implements Serializable{
    @Id
    private int id;
    private String type;
    private String name;
    private String preType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreType() {
        return preType;
    }

    public void setPreType(String preType) {
        this.preType = preType;
    }

    @Override
    public String toString() {
        return "PresentFlagBean{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", preType='" + preType + '\'' +
                '}';
    }
}
