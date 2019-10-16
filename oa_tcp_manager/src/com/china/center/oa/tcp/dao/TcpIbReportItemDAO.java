/**
 * File Name: TcpShareDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.ExportIbReportItemData;
import com.china.center.oa.tcp.bean.TcpIbReportItemBean;
import com.china.center.oa.tcp.vo.TcpIbReportItemVO;


/**
 * TcpShareDAO
 * 
 * @author ZHUZHU
 * @version 2015-14-10
 * @see com.china.center.oa.tcp.dao.TcpIbReportItemDAO
 * @since 3.0
 */
public interface TcpIbReportItemDAO extends DAO<TcpIbReportItemBean, TcpIbReportItemVO>
{
	public List<ExportIbReportItemData> queryExportReportItemData(String customerName);
}
