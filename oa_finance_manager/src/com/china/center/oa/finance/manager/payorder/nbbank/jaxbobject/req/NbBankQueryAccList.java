package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *      账号信息列表查询
 * @author Administrator
 *
 */
@XmlRootElement(name="map")
public class NbBankQueryAccList {
	
	private String queryCustNo;

	@XmlElement
	public String getQueryCustNo() {
		return queryCustNo;
	}

	public void setQueryCustNo(String queryCustNo) {
		this.queryCustNo = queryCustNo;
	}
	

}
