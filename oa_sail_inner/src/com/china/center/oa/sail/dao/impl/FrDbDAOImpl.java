package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.FrDbBean;
import com.china.center.oa.sail.dao.FrDbDAO;


public class FrDbDAOImpl extends BaseDAO<FrDbBean, FrDbBean> implements FrDbDAO {

    @Override
    public void updateStatus(String outId, int status, String dbNo) {
        String sql = "update t_center_frdb set status = ?, dbNo = ? where outId = ?";

        jdbcOperation.update(sql, status, dbNo, outId);
    }
}
