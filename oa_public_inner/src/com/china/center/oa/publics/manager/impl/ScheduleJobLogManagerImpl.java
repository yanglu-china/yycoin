package com.china.center.oa.publics.manager.impl;

import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.ScheduleJobBean;
import com.china.center.oa.publics.bean.ScheduleJobLogBean;
import com.china.center.oa.publics.dao.ScheduleJobDAO;
import com.china.center.oa.publics.dao.ScheduleJobLogDAO;
import com.china.center.oa.publics.manager.ScheduleJobLogManager;
import com.china.center.oa.publics.manager.ScheduleJobManager;
import com.china.center.oa.publics.trigger.QuartzJobFactory;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdScheduler;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
 * ScheduleJobLogManagerImpl:计划任务日志管理
 *
 * @author huang.zhengyu
 * @version 2016-10-25
 * @see ScheduleJobLogManagerImpl
 * @since 1.0
 */

public class ScheduleJobLogManagerImpl implements ScheduleJobLogManager {

    public final Logger log = Logger.getLogger(this.getClass());

    private ScheduleJobLogDAO scheduleJobLogDAO;

    /**
     * default constructor
     */
    public ScheduleJobLogManagerImpl(){

    }

    @Transactional(rollbackFor = MYException.class)
    @Override
    public void save(ScheduleJobLogBean log) throws MYException {
        this.scheduleJobLogDAO.saveEntityBean(log);
    }

    public ScheduleJobLogDAO getScheduleJobLogDAO() {
        return scheduleJobLogDAO;
    }

    public void setScheduleJobLogDAO(ScheduleJobLogDAO scheduleJobLogDAO) {
        this.scheduleJobLogDAO = scheduleJobLogDAO;
    }


}
