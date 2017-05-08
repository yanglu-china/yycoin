package com.china.center.oa.schedulejob.action;

import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.ScheduleJobBean;
import com.china.center.oa.publics.dao.ScheduleJobDAO;
import com.china.center.oa.publics.dao.ScheduleJobLogDAO;
import com.china.center.oa.publics.manager.ScheduleJobManager;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.quartz.SchedulerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 定时任务管理
 *
 * @author huangzhengyu 2016-10-16
 */
public class ScheduleJobAction extends DispatchAction
{
	private final Log _logger = LogFactory.getLog(getClass());

	private ScheduleJobManager scheduleJobManager = null;

	private ScheduleJobDAO scheduleJobDAO = null;

	private ScheduleJobLogDAO scheduleJobLogDAO = null;

	private static String QUERYALLJOBS = "queryAllJobs";

	private static String QUERYJOBLOGS = "queryJobLogs";

	public ScheduleJobAction()
	{
		
	}
	
	/**
	 * queryAllScheduleJobs
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward queryAllJobs(ActionMapping mapping, ActionForm form,
            								HttpServletRequest request, HttpServletResponse response)
	throws ServletException
	{
		ConditionParse condtion = new ConditionParse();
		
		condtion.addWhereStr();

//		User user = Helper.getUser(request);
//		condtion.addCondition("CustomerApproveBean.applyId", "=", user.getStafferId());
		
		ActionTools.processJSONQueryCondition(QUERYALLJOBS, request, condtion);

		condtion.addCondition("order by ScheduleJobBean.jobName asc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYALLJOBS, request,
		condtion, this.scheduleJobDAO , new HandleResult<ScheduleJobBean>()
				{
					public void handle(ScheduleJobBean obj)
					{
						try {
							scheduleJobManager.getJob(obj);
						} catch (SchedulerException e) {
							e.printStackTrace();
						}
					}
				});
		
		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * findJob
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward findJob(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								  HttpServletResponse response)
			throws ServletException
	{
		String id = request.getParameter("id");

		ScheduleJobBean bean = this.scheduleJobDAO.findVO(id);

		if (bean == null)
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

			return mapping.findForward("queryAllJob");
		}

		request.setAttribute("bean", bean);

		return mapping.findForward("updateJob");
	}

	public ActionForward queryJobLogs(ActionMapping mapping, ActionForm form,
									  HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		String jobId = request.getParameter("jobId");
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		condtion.addCondition("ScheduleJobLogBean.jobId", "=", jobId);

		ActionTools.processJSONQueryCondition(QUERYJOBLOGS, request, condtion);

		condtion.addCondition("order by ScheduleJobLogBean.id desc");

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYJOBLOGS, request,
				condtion, this.scheduleJobLogDAO);

		return JSONTools.writeResponse(response, jsonstr);
	}

	/**
	 * updateJob
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward updateJob(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response)
			throws ServletException
	{
		String id = request.getParameter("id");

		String cronExpression = request.getParameter("cronExpression");
		_logger.info("update job:" + id + " with cronExpression:" + cronExpression);

		try
		{
			ScheduleJobBean bean = this.scheduleJobDAO.findVO(id);

			if (bean == null)
			{
				request.setAttribute(KeyConstant.ERROR_MESSAGE, "数据异常,请重新操作");

				return mapping.findForward("queryAllJob");
			}

			//TODO : 添加日志记录

			if(!bean.getCronExpression().equalsIgnoreCase(cronExpression)){
				scheduleJobManager.updateCron(Long.parseLong(id), cronExpression);
				bean.setCronExpression(cronExpression);
			}

			request.setAttribute(KeyConstant.MESSAGE, "成功更改任务:" + bean.getJobName());

		}
		catch (SchedulerException e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
		}catch (Exception e)
		{
			_logger.warn(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作失败:" + e.getMessage());
		}

		CommonTools.removeParamers(request);

		return mapping.findForward("queryAllJob");
	}

	/**
	 * preForAddJob
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward preForAddJob(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								 HttpServletResponse response)
			throws ServletException
	{
		return mapping.findForward("addJob");
	}

	/**
	 * addJob
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward addJob(ActionMapping mapping, ActionForm form, HttpServletRequest request,
								   HttpServletResponse response)
			throws ServletException
	{
		ScheduleJobBean bean = new ScheduleJobBean();
		try
		{
			BeanUtil.getBean(bean, request);

			scheduleJobManager.addTask(bean);
            scheduleJobManager.addJob(bean);

			request.setAttribute(KeyConstant.MESSAGE, "成功增加:" + bean.getJobName());
		}
        catch (SchedulerException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

		CommonTools.removeParamers(request);

		return mapping.findForward("queryAllJob");
	}
	/**
	 * pauseJob
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward pauseJob(ActionMapping mapping, ActionForm form,
										   HttpServletRequest request,
										   HttpServletResponse response)
			throws ServletException
	{
		String id = request.getParameter("id");

		ScheduleJobBean apply = new ScheduleJobBean();

		AjaxResult ajax = new AjaxResult();

		try
		{
			ScheduleJobBean bean = scheduleJobDAO.find(id);

			if (bean == null)
			{
				ajax.setError("定时任务[" + id + "]不存在，请检查是否选中了任务，或者任务是否已经被删除。");

				return JSONTools.writeResponse(response, ajax);
			}

			//TODO : 添加日志记录
//			User user = Helper.getUser(request);

			scheduleJobManager.pauseJob(bean);

			ajax.setSuccess("成功暂停:" + bean.getJobName());
		}
		catch (SchedulerException e)
		{
			_logger.warn(e, e);

			ajax.setError("暂停任务[" + id + "]失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * resumeJob
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward resumeJob(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request,
								  HttpServletResponse response)
			throws ServletException
	{
		String id = request.getParameter("id");

		ScheduleJobBean apply = new ScheduleJobBean();

		AjaxResult ajax = new AjaxResult();

		try
		{
			ScheduleJobBean bean = scheduleJobDAO.find(id);

			if (bean == null)
			{
				ajax.setError("定时任务不存在，请检查是否选中了任务，或者任务是否已经被删除。");

				return JSONTools.writeResponse(response, ajax);
			}

			//TODO : 添加日志记录
//			User user = Helper.getUser(request);

			scheduleJobManager.resumeJob(bean);

			ajax.setSuccess("成功恢复任务:" + bean.getJobName());
		}
		catch (SchedulerException e)
		{
			_logger.warn(e, e);

			ajax.setError("恢复任务[" + id + "]失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * startJob
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward startJob(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request,
								   HttpServletResponse response)
			throws ServletException
	{
		String id = request.getParameter("id");

		ScheduleJobBean apply = new ScheduleJobBean();

		AjaxResult ajax = new AjaxResult();

		try
		{
			//TODO : 添加日志记录
//			User user = Helper.getUser(request);

			scheduleJobManager.startJob(Long.parseLong(id));

			ajax.setSuccess("成功启动任务:" + id);

		}
		catch (SchedulerException e)
		{
			_logger.warn(e, e);

			ajax.setError("启动任务[" + id + "]失败:" + e.getMessage());
		}catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("启动任务[" + id + "]失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	/**
	 * stopJob
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward stopJob(ActionMapping mapping, ActionForm form,
								  HttpServletRequest request,
								  HttpServletResponse response)
			throws ServletException
	{
		String id = request.getParameter("id");

		AjaxResult ajax = new AjaxResult();

		try
		{
			//TODO : 添加日志记录
//			User user = Helper.getUser(request);

			scheduleJobManager.stopJob(Long.parseLong(id));

			ajax.setSuccess("成功停止任务:" + id);

		}
		catch (SchedulerException e)
		{
			_logger.warn(e, e);

			ajax.setError("停止任务[" + id + "]失败:" + e.getMessage());
		}catch (MYException e)
		{
			_logger.warn(e, e);

			ajax.setError("停止任务[" + id + "]失败:" + e.getMessage());
		}

		return JSONTools.writeResponse(response, ajax);
	}

	public ScheduleJobManager getScheduleJobManager() {
		return scheduleJobManager;
	}

	public void setScheduleJobManager(ScheduleJobManager scheduleJobManager) {
		this.scheduleJobManager = scheduleJobManager;
	}

	public ScheduleJobDAO getScheduleJobDAO() {
		return scheduleJobDAO;
	}

	public void setScheduleJobDAO(ScheduleJobDAO scheduleJobDAO) {
		this.scheduleJobDAO = scheduleJobDAO;
	}

	public ScheduleJobLogDAO getScheduleJobLogDAO() {
		return scheduleJobLogDAO;
	}

	public void setScheduleJobLogDAO(ScheduleJobLogDAO scheduleJobLogDAO) {
		this.scheduleJobLogDAO = scheduleJobLogDAO;
	}
}
