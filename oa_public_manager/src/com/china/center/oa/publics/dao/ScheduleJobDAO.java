
package com.china.center.oa.publics.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.ScheduleJobBean;


/**
 * ScheduleJobDAO
 * 
 * @author huang.zhengyu
 * @version 2016-10-15
 * @see ScheduleJobDAO
 * @since 1.0
 */
public interface ScheduleJobDAO extends DAO<ScheduleJobBean, ScheduleJobBean>
{
    ScheduleJobBean findByJobName(String jobName);

}
