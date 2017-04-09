/**
 * File Name: AuthManager.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: huang.zhengyu<br>
 * CreateTime: 2016-10-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.manager;


import com.center.china.osgi.publics.ListenerManager;
import com.china.center.common.MYException;
import com.china.center.oa.publics.bean.AuthBean;
import com.china.center.oa.publics.bean.ScheduleJobBean;
import com.china.center.oa.publics.listener.AuthListener;
import org.quartz.SchedulerException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
 * AuthManager
 * 
 * @author huang.zhengyu
 * @version 2016-10-15
 * @see ScheduleJobManager
 * @since 1.0
 */
public interface ScheduleJobManager
{

    /**
     * 启动一个job
     * @param jobId
     * @throws SchedulerException
     */
    void startJob(Long jobId) throws SchedulerException, MYException;

    /**
     * 停止一个job
     * @param jobId
     * @throws SchedulerException
     */
    void stopJob(Long jobId) throws SchedulerException, MYException;

    /**
     * 添加一个job
     * @param job
     * @throws SchedulerException
     */
    void addJob(ScheduleJobBean job) throws SchedulerException;
    /**
     * 暂停一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    void pauseJob(ScheduleJobBean scheduleJob) throws SchedulerException;

    /**
     * 恢复一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    void resumeJob(ScheduleJobBean scheduleJob) throws SchedulerException ;

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    void deleteJob(ScheduleJobBean scheduleJob) throws SchedulerException;

    /**
     * 立即执行job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    void runAJobNow(ScheduleJobBean scheduleJob) throws SchedulerException;

    /**
     * 更新job时间表达式
     *
     * @param scheduleJob
     * @throws SchedulerException
     * @throws ParseException
     */
    void updateJobCron(ScheduleJobBean scheduleJob) throws SchedulerException, ParseException;

    /**
     * 更改任务 cron表达式
     *
     * @throws SchedulerException
     * @throws ParseException
     */
    void updateCron(Long jobId, String cron) throws SchedulerException, ParseException;

    /**
     * 从数据库中取 区别于getAllJob
     *
     * @return
     */
    List<ScheduleJobBean> getAllTask();

    /**
     * 添加到数据库中 区别于addJob
     */
    void addTask(ScheduleJobBean job);

    /**
     * 从数据库中查询job
     */
    ScheduleJobBean getTaskById(Long jobId);

    /**
     * 从内存中查询job的nextFireTime
     */
    void getJob(ScheduleJobBean job) throws SchedulerException;

}
