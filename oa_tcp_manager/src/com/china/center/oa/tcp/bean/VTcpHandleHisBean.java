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

import com.china.center.oa.tcp.bean.TcpStatusBean;


/**
 * VTcpHandleHisBean
 * 
 * @author Guliqiang
 * @version 2011-9-18
 * @see TcpHandleHisBean
 * @since 3.0
 */
@Entity(inherit = true)
@Table(name = "V_CENTER_TCPHANDLEHIS")
public class VTcpHandleHisBean extends TcpHandleHisBean
{
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
