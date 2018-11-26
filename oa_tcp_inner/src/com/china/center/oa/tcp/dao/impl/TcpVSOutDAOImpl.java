/**
 * File Name: TcpShareDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tcp.bean.TcpVSOutBean;
import com.china.center.oa.tcp.dao.TcpVSOutDAO;


/**
 * TcpVSOutDAOImpl
 * 
 * @author ZHUZHU
 * @version 2018-11-26
 * @see TcpVSOutDAOImpl
 * @since 3.0
 */
public class TcpVSOutDAOImpl extends BaseDAO<TcpVSOutBean, TcpVSOutBean> implements TcpVSOutDAO
{
    @Override
    public boolean deleteByApplyId(String applyId) {
        this.jdbcOperation.delete("where refId = ?", TcpVSOutBean.class, applyId);

        return true;
    }
}
