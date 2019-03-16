package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.FrDbBean;
import com.china.center.oa.sail.dao.FrDbDAO;
import com.china.center.tools.StringTools;


public class FrDbDAOImpl extends BaseDAO<FrDbBean, FrDbBean> implements FrDbDAO {

    @Override
    public void updateStatus(int id, String dbNo, String errorMessage) {
        if (StringTools.isNullOrNone(errorMessage)){
            String sql = "update t_center_frdb set status = 2, dbNo = ? where id = ?";
            jdbcOperation.update(sql, dbNo, id);
        } else{
            String sql = "update t_center_frdb set errorMessage = ? where id = ?";
            jdbcOperation.update(sql, errorMessage, id);
        }


    }
}
