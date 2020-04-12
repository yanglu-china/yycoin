package com.china.center.oa.commission.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;

@SuppressWarnings("serial")
@Entity(inherit = true)
@Table(name = "T_CENTER_BLACK_OUT_DETAIL")
public class BlackOutDetailBean extends AbstractBlackOutDetailBean
{
	private String baseId = "";
	/**
	 * 
	 */
	public BlackOutDetailBean()
	{
	}

	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}

	@Override
	public String toString() {
		return "BlackOutDetailBean{" +
				"baseId='" + baseId + '\'' +
				"} " + super.toString();
	}
}
