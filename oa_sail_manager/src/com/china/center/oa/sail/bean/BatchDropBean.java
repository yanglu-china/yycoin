package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 批量报废
 */

public class BatchDropBean implements Serializable
{

	private String batchId = "";
	
	private OutBean outBean;

	private List<BaseBean> baseBeanList;

	public BatchDropBean(){
	    this.outBean = new OutBean();
	    this.baseBeanList = new ArrayList<BaseBean>();
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public OutBean getOutBean() {
        return outBean;
    }

    public void setOutBean(OutBean outBean) {
        this.outBean = outBean;
    }

	public List<BaseBean> getBaseBeanList() {
		return baseBeanList;
	}

	public void setBaseBeanList(List<BaseBean> baseBeanList) {
		this.baseBeanList = baseBeanList;
	}

}
