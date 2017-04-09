package com.china.center.oa.publics.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Html;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.oa.publics.constant.PublicConstant;

import java.io.Serializable;
import java.util.Date;

/**
 * ScheduleJobLogBean
 *
 * @author huang.zhengyu
 * @version 2016-10-25
 * @see ScheduleJobLogBean
 * @since 1.0
 */
@Entity
@Table(name = "t_center_schedule_job_log")
public class ScheduleJobLogBean implements Serializable {

    @Id(autoIncrement = true)
    private int id;

    private String jobId;

    /**
     * 任务名称
     */
    @Html(title = "任务名称", maxLength = 100)
    private String jobName;
    /**
     * 任务分组
     */
    @Html(title = "任务分组", maxLength = 100)
    private String jobGroup;


    /**
     * 执行结果  成功/失败
     */
    private int result = PublicConstant.SCHEDULE_LOG_RESULT_SUCCESS;


    private String fireTime;

    private String nextFireTime;

    private int refireCount;

    private String message;

    /**
     * default constructor
     */
    public ScheduleJobLogBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getFireTime() {
        return fireTime;
    }

    public void setFireTime(String fireTime) {
        this.fireTime = fireTime;
    }

    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public int getRefireCount() {
        return refireCount;
    }

    public void setRefireCount(int refireCount) {
        this.refireCount = refireCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ScheduleJobLogBean{" +
                "id=" + id +
                ", jobId='" + jobId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", result=" + result +
                ", fireTime='" + fireTime + '\'' +
                ", nextFireTime='" + nextFireTime + '\'' +
                ", refireCount=" + refireCount +
                ", message='" + message + '\'' +
                '}';
    }
}