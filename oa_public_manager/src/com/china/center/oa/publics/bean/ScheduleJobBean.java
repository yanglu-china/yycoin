package com.china.center.oa.publics.bean;


import com.china.center.jdbc.annotation.*;
import com.china.center.jdbc.annotation.enums.Element;

import java.io.Serializable;
import java.util.Date;
/**
 * ScheduleJobBean
 *
 * @author huang.zhengyu
 * @version 2016-10-14
 * @see ScheduleJobBean
 * @since 1.0
 */
@Entity
@Table(name = "t_center_schedule_job")
public class ScheduleJobBean  implements Serializable {

	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_PAUSED = "2	";
	public static final String STATUS_NOT_RUNNING = "0";

	public static final String CONCURRENT_IS = "1";
	public static final String CONCURRENT_NOT = "0";

	public static final String CMD_START = "start";
	public static final String CMD_STOP = "stop";

	@Id
	private int id;

	private Date createTime;

	private Date updateTime;
	/**
	 * 任务名称
	 */
	@Html(title = "任务名称",  maxLength = 100)
	private String jobName;
	/**
	 * 任务分组
	 */
	@Html(title = "任务组",  maxLength = 100)
	private String jobGroup;
	/**
	 * 任务状态 是否启动任务
	 */
	@Html(title = "状态",  maxLength = 100)
	private String jobStatus;
	/**
	 * cron表达式
	 */
	@Html(title = "触发器表达式",  maxLength = 100)
	private String cronExpression;
	/**
	 * 描述
	 */
	@Html(title = "描述", type = Element.TEXTAREA, maxLength = 200)
	private String description;
	/**
	 * 任务执行时调用哪个类的方法 包名+类名
	 */
	@Html(title = "执行类",  maxLength = 200)
	private String beanClass;
	/**
	 * 任务是否有状态
	 */
	private String isConcurrent;
	/**
	 * spring bean
	 */
	private String springId;
	/**
	 * 任务调用的方法名
	 */
	@Html(title = "执行方法",  maxLength = 100)
	private String methodName;

	@Ignore
	private String nextFireTime;


	/**
	 * default constructor
	 */
	public ScheduleJobBean()
	{
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}

	public String getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(String isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public String getSpringId() {
		return springId;
	}

	public void setSpringId(String springId) {
		this.springId = springId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getNextFireTime() { return nextFireTime; }

	public void setNextFireTime(String nextFireTime) { this.nextFireTime = nextFireTime; }

}