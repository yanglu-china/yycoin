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
import com.china.center.oa.publics.bean.AlarmBean;
import com.china.center.oa.publics.bean.ScheduleJobBean;
import com.china.center.oa.publics.dao.AlarmDAO;
import com.china.center.oa.publics.dao.ScheduleJobDAO;
import com.china.center.tools.ListTools;

import java.util.List;


/**
 * ScheduleJobDAOImpl
 * 
 * @author huang.zhengyu
 * @version 2016-10-15
 * @see ScheduleJobDAOImpl
 * @since 1.0
 */
public class ScheduleJobDAOImpl extends BaseDAO<ScheduleJobBean, ScheduleJobBean> implements ScheduleJobDAO
{
    public ScheduleJobBean findByJobName(String jobName)
    {
        List<ScheduleJobBean> list = this.jdbcOperation.queryForList("where jobName = ?", claz, jobName);

        if (ListTools.isEmptyOrNull(list) || list.size() != 1)
        {
            return null;
        }

        return list.get(0);
    }
}
