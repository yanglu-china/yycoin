/**
 * File Name: StockItemDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.stock.bean.PurchaseBjBean;


public interface PurchaseBjDAO extends DAO<PurchaseBjBean, PurchaseBjBean>
{
    boolean updateIsUsed(String bjNo,int isUsed);

}
