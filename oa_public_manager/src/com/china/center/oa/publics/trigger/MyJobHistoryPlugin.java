package com.china.center.oa.publics.trigger;

import com.china.center.common.MYException;
import com.china.center.oa.publics.SpringUtils;
import com.china.center.oa.publics.bean.ScheduleJobBean;
import com.china.center.oa.publics.bean.ScheduleJobLogBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.ScheduleJobDAO;
import com.china.center.oa.publics.dao.ScheduleJobLogDAO;
import com.china.center.oa.publics.manager.ScheduleJobLogManager;
import com.china.center.tools.TimeTools;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;
import org.quartz.plugins.history.LoggingJobHistoryPlugin;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Created by aaronhuang on 2016/10/23.
 */


public class MyJobHistoryPlugin extends LoggingJobHistoryPlugin {

    public final Logger log = Logger.getLogger(this.getClass());

    private ScheduleJobLogManager logManager;

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        Trigger trigger = context.getTrigger();
        Object[] args = null;
        String result;

        ScheduleJobLogBean jobLog = new ScheduleJobLogBean();

        if(jobException != null) {
//            if(!this.getLog().isWarnEnabled()) {
//                return;
//            }

            result = jobException.getMessage();
            args = new Object[]{context.getJobDetail().getName(), context.getJobDetail().getGroup(), new Date(), trigger.getName(), trigger.getGroup(), trigger.getPreviousFireTime(), trigger.getNextFireTime(), new Integer(context.getRefireCount()), result};
            jobLog.setFireTime(TimeTools.now());
            jobLog.setNextFireTime(TimeTools.getString(trigger.getNextFireTime()));
            jobLog.setRefireCount(new Integer(context.getRefireCount()));
            jobLog.setMessage(MessageFormat.format(this.getJobFailedMessage(), args));
            jobLog.setResult(PublicConstant.SCHEDULE_LOG_RESULT_FAIL);

            this.getLog().warn(MessageFormat.format(this.getJobFailedMessage(), args), jobException);

            saveScheduleJobLog(context, jobLog);

        } else {
//            if(!this.getLog().isInfoEnabled()) {
//                return;
//            }

            result = String.valueOf(context.getResult());
            args = new Object[]{context.getJobDetail().getName(), context.getJobDetail().getGroup(), new Date(), trigger.getName(), trigger.getGroup(), trigger.getPreviousFireTime(), trigger.getNextFireTime(), new Integer(context.getRefireCount()), result};

            jobLog.setFireTime(TimeTools.now());
            jobLog.setNextFireTime(TimeTools.getString(trigger.getNextFireTime()));
            jobLog.setRefireCount(new Integer(context.getRefireCount()));
            jobLog.setMessage(MessageFormat.format(this.getJobSuccessMessage(), args));
//            由于定时任务的log已经保存到数据库,不用输出到日志文件
//            this.getLog().info(MessageFormat.format(this.getJobSuccessMessage(), args));

//            if(!"createPackageJob".equalsIgnoreCase(context.getJobDetail().getName())){
                saveScheduleJobLog(context, jobLog);
//            }

        }

    }

    @Transactional(rollbackFor = MYException.class)
    private void saveScheduleJobLog(JobExecutionContext context, ScheduleJobLogBean jobLog) {
        logManager = SpringUtils.getBean("scheduleJobLogManager");
        if(logManager != null ){
            ScheduleJobBean scheduleJob = (ScheduleJobBean) context.getMergedJobDataMap().get("scheduleJob");
            jobLog.setJobName(scheduleJob.getJobName());
            jobLog.setJobId(Integer.toString(scheduleJob.getId()));
            jobLog.setJobGroup(scheduleJob.getJobGroup());

            try {
                logManager.save(jobLog);
            } catch (MYException e) {
                log.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
