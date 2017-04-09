
package com.china.center.oa.publics.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.publics.bean.ScheduleJobBean;
import com.china.center.oa.publics.bean.ScheduleJobLogBean;

import java.util.List;


/**
 * ScheduleJobLogDAO
 * 
 * @author huang.zhengyu
 * @version 2016-10-25
 * @see ScheduleJobLogDAO
 * @since 1.0
 */
public interface ScheduleJobLogDAO extends DAO<ScheduleJobLogBean, ScheduleJobLogBean>
{
    List<ScheduleJobLogBean> findByJobId(String jobId);
}
