/**
 * File Name: TcpShareDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao.impl;


import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.ExportIbReportItemData;
import com.china.center.oa.tcp.bean.TcpIbReportItemBean;
import com.china.center.oa.tcp.dao.TcpIbReportItemDAO;
import com.china.center.oa.tcp.vo.TcpIbReportItemVO;


/**
 * TcpIbReportItemDAOImpl
 * 
 * @author ZHUZHU
 * @version 2015-04-13
 * @see com.china.center.oa.tcp.dao.impl.TcpIbReportItemDAOImpl
 * @since 3.0
 */
public class TcpIbReportItemDAOImpl extends BaseDAO<TcpIbReportItemBean, TcpIbReportItemVO> implements TcpIbReportItemDAO
{


	@Override
	public List<ExportIbReportItemData> queryExportReportItemData(String customerName) {
		String sql="SELECT aaa.*,c.productCode AS oimport_bankproduct_code FROM " + 
				"(SELECT a.*,b.type AS outtype,b.staffername,b.podate,b.pay,b.paytime,b.channel,b.status,p.code  AS productcode " + 
				"FROM T_CENTER_TCPIBREPORT_ITEM a ,T_CENTER_OUT b,t_center_product p WHERE a.fullId=b.fullId AND a.productId=p.id ";
		if(StringUtils.isNotEmpty(customerName))
		{
			sql = sql +" AND a.customerName LIKE '%" + customerName.trim() + "%'";
		}
	
		sql = sql + ") aaa " + 
				"LEFT JOIN t_center_out_import c ON aaa.fullid=c.OANo";
		return jdbcOperation.queryForListBySql(sql, ExportIbReportItemData.class);
	}

}
