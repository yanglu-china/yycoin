package com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class SfResponseRlsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rls_errormsg;
	
	private String invoke_result;
	
	private String rls_code;
	
	private SfResponseRlsDetail rlsDetail;

	@XmlAttribute(name="rls_errormsg")
	public String getRls_errormsg() {
		return rls_errormsg;
	}

	public void setRls_errormsg(String rls_errormsg) {
		this.rls_errormsg = rls_errormsg;
	}

	@XmlAttribute(name="invoke_result")
	public String getInvoke_result() {
		return invoke_result;
	}

	public void setInvoke_result(String invoke_result) {
		this.invoke_result = invoke_result;
	}

	@XmlAttribute(name="rls_code")
	public String getRls_code() {
		return rls_code;
	}

	public void setRls_code(String rls_code) {
		this.rls_code = rls_code;
	}

	@XmlElement(name="rls_detail")
	public SfResponseRlsDetail getRlsDetail() {
		return rlsDetail;
	}

	public void setRlsDetail(SfResponseRlsDetail rlsDetail) {
		this.rlsDetail = rlsDetail;
	}
	
}
