package com.china.center.oa.publics.trigger;

import com.china.center.oa.publics.bean.ScheduleJobBean;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * 计划任务执行处 无状态
 *
 * @author huang.zhengyu
 * @version 2016-10-14
 * @see QuartzJobFactoryDisallowConcurrentExecution
 * @since 1.0
 */
public class QuartzJobFactoryDisallowConcurrentExecution implements StatefulJob {
	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ScheduleJobBean scheduleJob = (ScheduleJobBean) context.getMergedJobDataMap().get("scheduleJob");
		TaskUtils.invokMethod(scheduleJob);
	}
}