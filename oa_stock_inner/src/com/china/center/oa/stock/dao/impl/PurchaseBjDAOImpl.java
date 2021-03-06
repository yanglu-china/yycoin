/**
 * File Name: StockItemDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.stock.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.stock.bean.PurchaseBjBean;
import com.china.center.oa.stock.dao.PurchaseBjDAO;



public class PurchaseBjDAOImpl extends BaseDAO<PurchaseBjBean, PurchaseBjBean> implements PurchaseBjDAO
{
    @Override
    public boolean updateIsUsed(String bjNo, int isUsed) {
        String sql = "update T_PURCHASE_BJ set isUsed = ? " +
                " where bjNo = ?";

        int i = jdbcOperation.update(sql, isUsed, bjNo);

        return i != 0;
    }
}
