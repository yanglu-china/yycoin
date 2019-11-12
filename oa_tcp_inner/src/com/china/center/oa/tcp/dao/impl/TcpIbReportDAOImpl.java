/**
 * File Name: TcpShareDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tcp.bean.TcpIbReportBean;
import com.china.center.oa.tcp.dao.TcpIbReportDAO;
import com.china.center.oa.tcp.vo.TcpIbReportVO;


/**
 * TcpIbReportDAOImpl
 * 
 * @author ZHUZHU
 * @version 2015-04-13
 * @see com.china.center.oa.tcp.dao.impl.TcpIbReportDAOImpl
 * @since 3.0
 */
public class TcpIbReportDAOImpl extends BaseDAO<TcpIbReportBean, TcpIbReportVO> implements TcpIbReportDAO
{
	private IbatisDaoSupport ibatisDaoSupport = null;
	
	public List<TcpIbReportBean> queryEntityBeansByCustomerStaffer(String customerName, String stafferId){
        Map<String, Object> paramterMap = new HashMap<String, Object>();

        paramterMap.put("customerName", customerName);
        paramterMap.put("stafferId", stafferId);

        return (List<TcpIbReportBean>)this.ibatisDaoSupport.queryForList(
            "TcpIbReportDAO.queryEntityBeansByCustomerStaffer", paramterMap);
	}

	public IbatisDaoSupport getIbatisDaoSupport() {
		return ibatisDaoSupport;
	}

	public void setIbatisDaoSupport(IbatisDaoSupport ibatisDaoSupport) {
		this.ibatisDaoSupport = ibatisDaoSupport;
	}
	
}
