/**
 * File Name: OutDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.TwOutBean;
import com.china.center.oa.sail.vo.TwOutVO;


/**
 * OlOutDAO
 * 
 * @author ZHUZHU
 * @version 2016-07-27
 * @see TwOutDAO
 * @since 1.0
 */
public interface TwOutDAO extends DAO<TwOutBean, TwOutVO>
{
    public void updateStatus(String olFullId, int status);
}
