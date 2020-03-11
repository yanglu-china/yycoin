/*
 * File Name: CTApproveTimeBean.java
 * CopyRight: Copyright by www.center.china
 * Description:
 * Creater: GLQ
 * CreateTime: 2020-03-10
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.bean;


import java.io.Serializable;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;



/**
 * 采购退货审批时间
 * @author think
 *
 */
@Entity(name = "采购退货审批时间")
@Table(name = "v_center_ct_approvetime")
public class CTApproveTimeBean implements Serializable
{

	@Id
    private String fullId = "";

    private String logTime = "";

    /**
     * default constructor
     */
    public CTApproveTimeBean()
    {
    }

	public String getFullId() {
		return fullId;
	}

	public void setFullId(String fullId) {
		this.fullId = fullId;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	/**
     * Constructs a <code>String</code> with all attributes in name = value format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        return "FlowLogBean{" +
                ", fullId='" + fullId + '\'' +
                ", logTime='" + logTime + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = fullId.hashCode();
        result = 31 * result + logTime.hashCode();
        return result;
    }

}
