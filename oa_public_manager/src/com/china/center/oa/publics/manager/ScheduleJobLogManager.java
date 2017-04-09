/**
 * File Name: AuthManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: huang.zhengyu<br>
 * CreateTime: 2016-10-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.ScheduleJobBean;
import com.china.center.oa.publics.bean.ScheduleJobLogBean;
import org.quartz.SchedulerException;

import java.text.ParseException;
import java.util.List;


/**
 * AuthManager
 * 
 * @author huang.zhengyu
 * @version 2016-10-15
 * @see ScheduleJobLogManager
 * @since 1.0
 */
public interface ScheduleJobLogManager
{

    /**
     * 保存定时任务日志
     * @param log
     * @throws MYException
     */
    void save(ScheduleJobLogBean log) throws MYException;

}
