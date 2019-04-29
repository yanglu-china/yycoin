/**
 * File Name: TcpApplyDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tcp.bean.BankBuLevelBean;

import java.util.List;


public interface BankBuLevelDAO extends DAO<BankBuLevelBean, BankBuLevelBean>
{
    List<BankBuLevelBean> queryByBearType(String bearType);

    List<BankBuLevelBean> queryByBearTypeAndManager(String bearType, String manager);

    /**
     *
     * @param flowKey
     * @param bearType
     * @param stafferId 当前环节处理人
     * @param originator 发起人
     * @return
     */
    String queryHighLevelManagerId(String flowKey, int bearType, String stafferId, String originator);
    
    /**
    *
    * @param flowKey
    * @param stafferId 当前环节处理人
    * @param originator 发起人
    * @return
    */
    public String queryManagerId(String flowKey, String stafferId, String originator); 

}
