package com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="map")
public class NbBankQueryHisBal {

	private String totalNum;
	
	private String queryDate;
	
	private String queryCustNo;
	
	private List<NbBankQueryHisBalRow> list;

	@XmlElement
	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	@XmlElement
	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}

	@XmlElement
	public String getQueryCustNo() {
		return queryCustNo;
	}

	public void setQueryCustNo(String queryCustNo) {
		this.queryCustNo = queryCustNo;
	}

	@XmlElement
	public List<NbBankQueryHisBalRow> getList() {
		return list;
	}

	public void setList(List<NbBankQueryHisBalRow> list) {
		this.list = list;
	}
	
}
