/**
 * File Name: TcpHandleHisBean.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-18<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.Unique;
import com.china.center.oa.publics.bean.StafferBean;

/**
 * TcpHandleHisBean
 * 
 * @author ZHUZHU
 * @version 2011-9-18
 * @see TcpHandleHisBean
 * @since 3.0
 */
@Entity
@Table(name = "T_CENTER_TCPHANDLEHIS")
public class TcpHandleHisBean implements Serializable
{
    @Id
    protected String id = "";

    protected String name = "";

    protected int type = 0;

    @FK
    protected String refId = "";

    /**
     * 处理人
     */
    @Unique(dependFields = "refId")
    @Join(tagClass = StafferBean.class, alias = "SB1")
    protected String stafferId = "";

    /**
     * 申请人
     */
    @Join(tagClass = StafferBean.class, alias = "SB2")
    protected String applyId = ""; 

    protected String logTime = "";

    /**
     * default constructor
     */
    public TcpHandleHisBean()
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
     * @return the refId
     */
    public String getRefId()
    {
        return refId;
    }

    /**
     * @param refId
     *            the refId to set
     */
    public void setRefId(String refId)
    {
        this.refId = refId;
    }

    /**
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @param logTime
     *            the logTime to set
     */
    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    /**
     * @return the applyId
     */
    public String getApplyId()
    {
        return applyId;
    }

    /**
     * @param applyId
     *            the applyId to set
     */
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("TcpHandleHisBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("name = ")
            .append(this.name)
            .append(TAB)
            .append("type = ")
            .append(this.type)
            .append(TAB)
            .append("refId = ")
            .append(this.refId)
            .append(TAB)
            .append("stafferId = ")
            .append(this.stafferId)
            .append(TAB)
            .append("applyId = ")
            .append(this.applyId)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

}
