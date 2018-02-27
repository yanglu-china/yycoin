package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.OlOutBean;
import com.china.center.oa.sail.bean.TwOutBean;
import com.china.center.oa.sail.dao.OlOutDAO;
import com.china.center.oa.sail.dao.TwOutDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 16-7-27
 * Time: 下午9:16
 * To change this template use File | Settings | File Templates.
 */
public class TwOutDAOImpl extends BaseDAO<TwOutBean, TwOutBean> implements TwOutDAO {

    @Override
    public void updateStatus(String olFullId, int status) {
        String sql = "update t_center_twout set status = ? where fullid = ?";

        jdbcOperation.update(sql, status, olFullId);
    }
}
