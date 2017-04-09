/**
 * File Name: AlarmDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: huang.zhengyu<br>
 * CreateTime: 2016-10-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.publics.bean.ScheduleJobBean;
import com.china.center.oa.publics.bean.ScheduleJobLogBean;
import com.china.center.oa.publics.dao.ScheduleJobDAO;
import com.china.center.oa.publics.dao.ScheduleJobLogDAO;
import com.china.center.tools.ListTools;

import java.util.List;


/**
 * ScheduleJobLogDAOImpl
 *
 * @author huang.zhengyu
 * @version 2016-10-25
 * @see ScheduleJobLogDAOImpl
 * @since 1.0
 */
public class ScheduleJobLogDAOImpl extends BaseDAO<ScheduleJobLogBean, ScheduleJobLogBean> implements ScheduleJobLogDAO
{
    @Override
    public List<ScheduleJobLogBean> findByJobId(String jobId) {
        List<ScheduleJobLogBean> list = this.jdbcOperation.queryForList("where jobId = ?", claz, jobId);

        if (ListTools.isEmptyOrNull(list) || list.size() != 1)
        {
            return null;
        }

        return list;
    }
}
