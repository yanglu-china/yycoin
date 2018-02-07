/*
 * File Name: OutLogBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: zhuAchen
 * CreateTime: 2008-1-13
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.oa.publics.constant.PublicConstant;


/**
 * 审批日志表(全局使用的哦)
 * 
 * @author ZHUZHU
 * @version 2008-1-13
 * @see
 * @since
 */
@Entity(name = "审批记录")
@Table(name = "T_CENTER_APPROVELOG")
public class FlowLogBean implements Serializable
{
    @Id(autoIncrement = true)
    private String id = "";

    /**
     * 外键
     */
    @FK
    private String fullId = "";

    /**
     * 操作者
     */
    private String actor = "";

    private String actorId = "";

    private int oprMode = PublicConstant.OPRMODE_PASS;

    /**
     * 涉及数量
     */
    private int oprAmount = 0;

    private int preStatus = 0;

    private int afterStatus = 0;

    private String logTime = "";

    private String description = "";

    /**
     * default constructor
     */
    public FlowLogBean()
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
     * @return the fullId
     */
    public String getFullId()
    {
        return fullId;
    }

    /**
     * @return the actor
     */
    public String getActor()
    {
        return actor;
    }

    /**
     * @return the oprMode
     */
    public int getOprMode()
    {
        return oprMode;
    }

    /**
     * @return the oprAmount
     */
    public int getOprAmount()
    {
        return oprAmount;
    }

    /**
     * @return the logTime
     */
    public String getLogTime()
    {
        return logTime;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
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
     * @param fullId
     *            the fullId to set
     */
    public void setFullId(String fullId)
    {
        this.fullId = fullId;
    }

    /**
     * @param actor
     *            the actor to set
     */
    public void setActor(String actor)
    {
        this.actor = actor;
    }

    /**
     * @param oprMode
     *            the oprMode to set
     */
    public void setOprMode(int oprMode)
    {
        this.oprMode = oprMode;
    }

    /**
     * @param oprAmount
     *            the oprAmount to set
     */
    public void setOprAmount(int oprAmount)
    {
        this.oprAmount = oprAmount;
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
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the preStatus
     */
    public int getPreStatus()
    {
        return preStatus;
    }

    /**
     * @return the afterStatus
     */
    public int getAfterStatus()
    {
        return afterStatus;
    }

    /**
     * @param preStatus
     *            the preStatus to set
     */
    public void setPreStatus(int preStatus)
    {
        this.preStatus = preStatus;
    }

    /**
     * @param afterStatus
     *            the afterStatus to set
     */
    public void setAfterStatus(int afterStatus)
    {
        this.afterStatus = afterStatus;
    }

    /**
     * @return the actorId
     */
    public String getActorId()
    {
        return actorId;
    }

    /**
     * @param actorId
     *            the actorId to set
     */
    public void setActorId(String actorId)
    {
        this.actorId = actorId;
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
            .append("FlowLogBean ( ")
            .append(super.toString())
            .append(TAB)
            .append("id = ")
            .append(this.id)
            .append(TAB)
            .append("fullId = ")
            .append(this.fullId)
            .append(TAB)
            .append("actor = ")
            .append(this.actor)
            .append(TAB)
            .append("actorId = ")
            .append(this.actorId)
            .append(TAB)
            .append("oprMode = ")
            .append(this.oprMode)
            .append(TAB)
            .append("oprAmount = ")
            .append(this.oprAmount)
            .append(TAB)
            .append("preStatus = ")
            .append(this.preStatus)
            .append(TAB)
            .append("afterStatus = ")
            .append(this.afterStatus)
            .append(TAB)
            .append("logTime = ")
            .append(this.logTime)
            .append(TAB)
            .append("description = ")
            .append(this.description)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlowLogBean that = (FlowLogBean) o;

        if (oprMode != that.oprMode) return false;
        if (oprAmount != that.oprAmount) return false;
        if (afterStatus != that.afterStatus) return false;
        if (!fullId.equals(that.fullId)) return false;
        if (!actor.equals(that.actor)) return false;
        if (!actorId.equals(that.actorId)) return false;
        if (!logTime.equals(that.logTime)) return false;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        int result = fullId.hashCode();
        result = 31 * result + actor.hashCode();
        result = 31 * result + actorId.hashCode();
        result = 31 * result + oprMode;
        result = 31 * result + oprAmount;
        result = 31 * result + afterStatus;
        result = 31 * result + logTime.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }
}
