/**
 * File Name: TcpShareDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tcp.bean.TcpIbBean;
import com.china.center.oa.tcp.vo.TcpIbVO;


/**
 * TcpShareDAO
 * 
 * @author ZHUZHU
 * @version 2015-14-10
 * @see com.china.center.oa.tcp.dao.TcpIbDAO
 * @since 3.0
 */
public interface TcpIbDAO extends DAO<TcpIbBean, TcpIbVO>
{

    boolean deleteByApplyId(String applyId);
}
