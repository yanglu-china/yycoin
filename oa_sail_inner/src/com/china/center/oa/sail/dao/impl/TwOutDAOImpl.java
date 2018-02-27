package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.TwOutBean;
import com.china.center.oa.sail.dao.TwOutDAO;
import com.china.center.oa.sail.vo.TwOutVO;

public class TwOutDAOImpl extends BaseDAO<TwOutBean, TwOutVO> implements TwOutDAO {

    @Override
    public void updateStatus(String olFullId, int status) {
        String sql = "update t_center_twout set status = ? where fullid = ?";

        jdbcOperation.update(sql, status, olFullId);
    }
}
