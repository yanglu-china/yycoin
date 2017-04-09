package com.china.center.oa.publics.manager.impl;

import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.ScheduleJobBean;
import com.china.center.oa.publics.dao.ScheduleJobDAO;
import com.china.center.oa.publics.manager.ScheduleJobManager;
import com.china.center.oa.publics.trigger.QuartzJobFactory;
import com.china.center.oa.publics.trigger.QuartzJobFactoryDisallowConcurrentExecution;
import com.china.center.tools.TimeTools;
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
 * ScheduleJobBeanTaskManager:计划任务管理
 *
 * @author huang.zhengyu
 * @version 2016-10-14
 * @see ScheduleJobTaskManagerImpl
 * @since 1.0
 */

public class ScheduleJobTaskManagerImpl implements ScheduleJobManager {

    public final Logger log = Logger.getLogger(this.getClass());

    private StdScheduler myScheduler;

    private ScheduleJobDAO scheduleJobDAO;

//    static final String jobGroupName = "DEFAULT";
    public static final String TRIGGER_GROUP_NAME = "triggerGroup";
    public static final String TRIGGER_SUFFIX = "_trigger";

    /**
     * default constructor
     */
    public ScheduleJobTaskManagerImpl(){

    }


    /**
     * 从数据库中取 区别于getAllJob
     *
     * @return
     */
    public List<ScheduleJobBean> getAllTask() {
        return scheduleJobDAO.listEntityBeans();
    }


    /**
     * 添加到数据库中 区别于addJob
     */
    public void addTask(ScheduleJobBean job) {
        job.setCreateTime(new Date());
        scheduleJobDAO.saveEntityBean(job);
    }

    /**
     * 从数据库中查询job
     */
    public ScheduleJobBean getTaskById(Long jobId) {
        return scheduleJobDAO.find(jobId);
    }

    /**
     * 更改任务状态
     *
     * @throws SchedulerException
     */
    @Transactional(rollbackFor = MYException.class)
    public void changeStatus(Long jobId, String cmd) throws SchedulerException, MYException {
        ScheduleJobBean job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        if (ScheduleJobBean.CMD_STOP.equals(cmd)) {
            deleteJob(job);
            job.setJobStatus(ScheduleJobBean.STATUS_NOT_RUNNING);
        } else if (ScheduleJobBean.CMD_START.equals(cmd)) {
            job.setJobStatus(ScheduleJobBean.STATUS_RUNNING);
            addJob(job);
        }
        job.setUpdateTime(new Date());
        scheduleJobDAO.updateEntityBean(job);
    }

    /**
     * 更改任务 cron表达式
     *
     * @throws SchedulerException
     * @throws ParseException
     */
    @Transactional(rollbackFor = MYException.class)
    public void updateCron(Long jobId, String cron) throws SchedulerException, ParseException {
        ScheduleJobBean job = getTaskById(jobId);
        if (job == null) {
            return;
        }
        job.setCronExpression(cron);
        if (ScheduleJobBean.STATUS_RUNNING.equals(job.getJobStatus())) {
            updateJobCron(job);
        }
        job.setUpdateTime(new Date());
        scheduleJobDAO.updateEntityBean(job);

    }

    /**
     * 启动一个job
     * @param jobId
     * @throws SchedulerException
     */

    @Transactional(rollbackFor = MYException.class)
    public void startJob(Long jobId) throws SchedulerException,MYException
    {
        changeStatus(jobId, ScheduleJobBean.CMD_START);
    }

    /**
     * 停止一个job
     * @param jobId
     * @throws SchedulerException
     */
    @Transactional(rollbackFor = MYException.class)
    public void stopJob(Long jobId) throws SchedulerException, MYException
    {
        changeStatus(jobId, ScheduleJobBean.CMD_STOP);
    }

    /**
     * 添加任务
     *
     * @param job
     * @throws SchedulerException
     */
    public void addJob(ScheduleJobBean job) throws SchedulerException {
        if (job == null
                || !ScheduleJobBean.STATUS_RUNNING.equals(job.getJobStatus())) {
            return;
        }

        Class clazz = ScheduleJobBean.CONCURRENT_IS.equals(job.getIsConcurrent()) ? QuartzJobFactory.class : QuartzJobFactoryDisallowConcurrentExecution.class;

        JobDetail jobDetail = new JobDetail(job.getJobName() , job.getJobGroup(), clazz);

        jobDetail.getJobDataMap().put("scheduleJob", job);

        CronTrigger trigger = new CronTrigger(job.getJobName() + TRIGGER_SUFFIX, TRIGGER_GROUP_NAME ,  job.getJobName() , job.getJobGroup());
        // 触发器表达式
        try {
            trigger.setCronExpression(job.getCronExpression());
        } catch (ParseException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
//        log.info(trigger.getFullJobName() + "|trigger:" + trigger.getFullName());
        myScheduler.scheduleJob(jobDetail, trigger);

    }

    @PostConstruct
    public void init() throws Exception {
        myScheduler.start();

        // 这里获取任务信息数据
        List<ScheduleJobBean> jobList = scheduleJobDAO.listEntityBeans();

        if (jobList == null || jobList.isEmpty()) {
            log.info("schedule job list is empty");
        }

        for (ScheduleJobBean job : jobList) {
//            System.out.println("-------" + job.getJobName() + "|" + job.getCronExpression() + ":" + job.getJobStatus());
            addJob(job);
        }


    }


    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void pauseJob(ScheduleJobBean scheduleJob) throws SchedulerException {
        myScheduler.pauseJob(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//        Trigger trigger = myScheduler.getTrigger(scheduleJob.getJobName() + "_trigger", TRIGGER_GROUP_NAME);
    }

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void resumeJob(ScheduleJobBean scheduleJob) throws SchedulerException {
        myScheduler.resumeJob(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//        JobDetail jd = myScheduler.getJobDetail(scheduleJob.getJobName(), scheduleJob.getJobGroup());
//        Trigger trigger = myScheduler.getTrigger(scheduleJob.getJobName() + "_trigger", TRIGGER_GROUP_NAME);
    }

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(ScheduleJobBean scheduleJob) throws SchedulerException {
        myScheduler.deleteJob(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void runAJobNow(ScheduleJobBean scheduleJob) throws SchedulerException {
        myScheduler.triggerJob(scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     * @throws ParseException
     */
    @Transactional(rollbackFor = MYException.class)
    public void updateJobCron(ScheduleJobBean scheduleJob) throws SchedulerException, ParseException {

        CronTrigger trigger = new CronTrigger(scheduleJob.getJobName() + TRIGGER_SUFFIX,
                TRIGGER_GROUP_NAME , scheduleJob.getJobName(), scheduleJob.getJobGroup());
//        trigger.setJobName(scheduleJob.getJobName());
        // 触发器表达式
        trigger.setCronExpression(scheduleJob.getCronExpression());

        myScheduler.rescheduleJob(trigger.getName(), TRIGGER_GROUP_NAME, trigger);
    }

    public void getJob(ScheduleJobBean job) throws SchedulerException {
        JobDetail jobDetail = myScheduler.getJobDetail(job.getJobName() , job.getJobGroup());
        if(jobDetail == null){
            job.setJobStatus(ScheduleJobBean.STATUS_NOT_RUNNING);
        } else {
            job.setJobStatus(ScheduleJobBean.STATUS_RUNNING);
        }
        Trigger trigger = myScheduler.getTrigger(job.getJobName() + TRIGGER_SUFFIX, TRIGGER_GROUP_NAME);
        if(trigger != null ){
            job.setNextFireTime(TimeTools.getString(trigger.getNextFireTime()));
        }
    }

    public StdScheduler getMyScheduler() {
        return myScheduler;
    }

    public void setMyScheduler(StdScheduler myScheduler) {
        this.myScheduler = myScheduler;
    }

    public ScheduleJobDAO getScheduleJobDAO() {
        return scheduleJobDAO;
    }

    public void setScheduleJobDAO(ScheduleJobDAO scheduleJobDAO) {
        this.scheduleJobDAO = scheduleJobDAO;
    }
}
