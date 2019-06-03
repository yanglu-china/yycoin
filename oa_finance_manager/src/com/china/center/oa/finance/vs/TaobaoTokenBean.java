package com.china.center.oa.finance.vs;

import java.io.Serializable;

import com.china.center.jdbc.annotation.Column;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Table;

@Entity
@Table(name = "T_CENTER_TAOBAO_TOKEN")
public class TaobaoTokenBean implements Serializable {

	@Column(name = "token")
    private String token = "";

	@Column(name="createtime")
    private String createtime = "";


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	@Override
	public String toString() {
		return "TaobaoToken [token=" + token + ", createtime=" + createtime + "]";
	}
	
}
