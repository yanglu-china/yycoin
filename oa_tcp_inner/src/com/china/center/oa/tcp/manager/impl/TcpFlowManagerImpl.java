/**
 * File Name: TcpFlowManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-9-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.manager.impl;


import java.util.ArrayList;
import java.util.List;

import com.china.center.common.taglib.DefinedCommon;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.helper.UserHelper;
import com.china.center.oa.tcp.bean.*;
import com.china.center.oa.tcp.dao.*;
import com.china.center.oa.tcp.vo.ExpenseApplyVO;
import com.china.center.oa.tcp.vo.TravelApplyVO;
import com.china.center.tools.StringTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.finance.bean.PreInvoiceApplyBean;
import com.china.center.oa.finance.dao.PreInvoiceApplyDAO;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.manager.TcpFlowManager;
import com.china.center.tools.JudgeTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;


/**
 * TcpFlowManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-9-6
 * @see TcpFlowManagerImpl
 * @since 3.0
 */
@IntegrationAOP
public class TcpFlowManagerImpl implements TcpFlowManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private MailMangaer mailMangaer = null;

    private TcpApproveDAO tcpApproveDAO = null;

    private CommonDAO commonDAO = null;

    private TravelApplyDAO travelApplyDAO = null;

    private ExpenseApplyDAO expenseApplyDAO = null;

    private TcpFlowDAO tcpFlowDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;
    
    private PreInvoiceApplyDAO preInvoiceApplyDAO = null;
    
    private FlowLogDAO flowLogDAO = null;

    private BankBuLevelDAO bankBuLevelDAO = null;

    private StafferDAO stafferDAO = null;

    /**
     * default constructor
     */
    public TcpFlowManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tcp.manager.TcpFlowManager#drawApprove(com.center.china.osgi.publics.User,
     *      java.lang.String)
     */
    @IntegrationAOP(lock = TcpFlowConstant.DRAW_LOCK)
    @Transactional(rollbackFor = MYException.class)
    public boolean drawApprove(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TcpApproveBean approve = tcpApproveDAO.find(id);

        if (approve == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !approve.getApproverId().equals(user.getStafferId()))
        {
            throw new MYException("没有权限,请确认操作");
        }

        if (approve.getPool() != TcpConstanst.TCP_POOL_POOL)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 删除其他人的认领
        tcpApproveDAO.deleteEntityBeansByFK(approve.getApplyId());

        approve.setId(commonDAO.getSquenceString20());

        approve.setPool(TcpConstanst.TCP_POOL_COMMON);

        tcpApproveDAO.saveEntityBean(approve);

        return true;
    }

    @IntegrationAOP(lock = TcpFlowConstant.DRAW_LOCK)
    @Transactional(rollbackFor = MYException.class)
    public boolean odrawApprove(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TcpApproveBean approve = tcpApproveDAO.find(id);

        if (approve == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !approve.getApproverId().equals(user.getStafferId()))
        {
            throw new MYException("没有权限,请确认操作");
        }

        if (approve.getPool() != TcpConstanst.TCP_POOL_COMMON)
        {
            throw new MYException("数据错误,请确认操作");
        }

        AbstractTcpBean atb = null;

        if (approve.getType() <= 10)
        {
            atb = travelApplyDAO.find(approve.getApplyId());
        }
        else
        {
            atb = expenseApplyDAO.find(approve.getApplyId());
        }

        if (atb == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO
            .findByFlowKeyAndNextStatus(atb.getFlowKey(), atb.getStatus());

        if (token == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !token.getNextPlugin().startsWith("pool"))
        {
            throw new MYException("当前环节不能退领,请确认操作");
        }

        String groupId = token.getNextPlugin().substring(5);

        List<GroupVSStafferBean> vsList = groupVSStafferDAO.queryEntityBeansByFK(groupId);

        if (ListTools.isEmptyOrNull(vsList))
        {
            throw new MYException("当前群组内没有人员,请确认操作");
        }

        List<String> processList = new ArrayList();

        for (GroupVSStafferBean groupVSStafferBean : vsList)
        {
            processList.add(groupVSStafferBean.getStafferId());
        }

        // 删除全部
        tcpApproveDAO.deleteEntityBeansByFK(approve.getApplyId());

        // 恢复工单池
        for (String processId : processList)
        {
            TcpApproveBean newApprove = new TcpApproveBean();

            newApprove.setId(commonDAO.getSquenceString20());
            newApprove.setApplyerId(atb.getStafferId());
            newApprove.setApplyId(approve.getApplyId());
            newApprove.setApproverId(processId);
            newApprove.setFlowKey(atb.getFlowKey());
            newApprove.setLogTime(TimeTools.now());
            newApprove.setDepartmentId(atb.getDepartmentId());
            newApprove.setName(atb.getName());
            newApprove.setStatus(atb.getStatus());
            newApprove.setTotal(atb.getTotal());
            newApprove.setType(atb.getType());
            newApprove.setStype(atb.getStype());
            newApprove.setPool(TcpConstanst.TCP_POOL_POOL);
            newApprove.setCheckTotal(atb.getBorrowTotal());

            if (atb instanceof ExpenseApplyBean)
            {
                newApprove.setPayType( ((ExpenseApplyBean)atb).getPayType());
            }
            else
            {
                newApprove.setPayType(TcpConstanst.PAYTYPE_GPAY_BO);
            }

            tcpApproveDAO.saveEntityBean(newApprove);
        }

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean endApprove(User user, String id)
        throws MYException
    {
    	JudgeTools.judgeParameterIsNull(user, id);

    	TcpApproveBean approve = tcpApproveDAO.find(id);

        if (approve == null)
        {
            throw new MYException("数据错误,请确认操作1");
        }

        PreInvoiceApplyBean apply = preInvoiceApplyDAO.find(approve.getApplyId());
        
        if (null == apply)
        {
        	throw new MYException("数据错误,请确认操作2");
        }
        
        if (apply.getStatus() != TcpConstanst.TCP_STATUS_APPLY_RELATE)
        {
        	throw new MYException("状态不是待申请人关联销售,请确认操作");
        }
        
    	preInvoiceApplyDAO.updateStatus(approve.getApplyId(), TcpConstanst.TCP_STATUS_END);
    	
    	tcpApproveDAO.deleteEntityBeansByFK(approve.getApplyId());
    	
        FlowLogBean log = new FlowLogBean();

        log.setFullId(approve.getApplyId());

        log.setActor(user.getStafferName());

        log.setActorId(user.getStafferId());

        log.setOprMode(PublicConstant.OPRMODE_PASS);

        log.setDescription(user.getStafferName() + " 强制结束");

        log.setLogTime(TimeTools.now());

        log.setPreStatus(TcpConstanst.TCP_STATUS_APPLY_RELATE);

        log.setAfterStatus(TcpConstanst.TCP_STATUS_END);

        flowLogDAO.saveEntityBean(log);
    	
    	return true;
    }

    @Override
    public int saveApprove(User user, String processId, TcpInterface bean, int nextStatus, int pool) throws MYException{
        List<String> processList = new ArrayList();

        processList.add(processId);

        return this.saveApprove(user, processList, bean, nextStatus, pool);
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public int saveApprove(User user, List<String> processList, TcpInterface bean, int nextStatus, int pool) throws MYException {
        int result = nextStatus;
        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());
        if (token == null || token.getSingeAll() == 0 )
        {
            // 清除之前的处理人
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
        }
        else
        {
            // 仅仅删除自己的
            List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(bean.getId());
            _logger.info("***approveList***"+approveList.size());
            for (TcpApproveBean tcpApproveBean : approveList)
            {
                if (tcpApproveBean.getApproverId().equals(user.getStafferId()))
                {
                    tcpApproveDAO.deleteEntityBean(tcpApproveBean.getId());
                }
            }
        }

        List<TcpApproveBean> appList = tcpApproveDAO.queryEntityBeansByFK(bean.getId());
        _logger.info("***appList***"+appList.size());
        if (token == null || appList.size() == 0 || token.getSingeAll() == 0)
        {
//            String nextProcessor = this.getNextProcessor(user.getStafferId(),token.getFlowKey(), token.getNextStatus());
            TcpFlowBean nextToken = this.getNextProcessor(bean.getStafferId(), user.getStafferId(),token.getFlowKey(), token.getNextStatus());
            int nextStatusIgnoreDuplicate = nextToken.getNextStatus();
            String nextProcessor = nextToken.getNextProcessor();
            if (!StringTools.isNullOrNone(nextProcessor) && !processList.contains(nextProcessor)){
                processList.add(nextProcessor);
            }
            _logger.info(nextStatus+"***nextStatusIgnoreDuplicate***"+nextStatusIgnoreDuplicate+"***processList***"+processList.size()+"***nextProcessor***"+nextProcessor);
            if (nextStatusIgnoreDuplicate!= 0){
                result = nextStatusIgnoreDuplicate;
            }
            for (String processId : processList)
            {
            	//#640 check
            	if(StringTools.isNullOrNone(processId) || "0".equals(processId) || "null".equals(processId) ){
            		_logger.info("***save TcpApproveBean*** ignored processId: "+processId);
            		continue;
            	}
            	
                // 进入审批状态
                TcpApproveBean approve = new TcpApproveBean();

                approve.setId(commonDAO.getSquenceString20());
                approve.setApplyerId(bean.getStafferId());
                approve.setApplyId(bean.getId());
                approve.setApproverId(processId);
                approve.setFlowKey(bean.getFlowKey());
                approve.setLogTime(TimeTools.now());
                approve.setDepartmentId(bean.getDepartmentId());
                approve.setName(bean.getName());
                approve.setStatus(result);
//                approve.setStatus(nextStatusIgnoreDuplicate);
                approve.setTotal(bean.getTotal());
                approve.setCheckTotal(bean.getBorrowTotal());
                approve.setType(bean.getType());
                approve.setStype(bean.getStype());
                approve.setPool(pool);

                //#394 payType字段和报销单一致
                approve.setPayType(bean.getPayType());

                tcpApproveDAO.saveEntityBean(approve);
                _logger.info("***save TcpApproveBean***"+approve);
            }

            // 如果是共享的不发送邮件
            if (pool == TcpConstanst.TCP_POOL_COMMON)
            {
                MailBean mail = new MailBean();

                mail.setTitle(bean.getStafferName() + "的"
                        + DefinedCommon.getValue("tcpType", bean.getType()) + "申请["
                        + bean.getName() + "]等待您的处理.");

                mail.setContent(mail.getContent());

                mail.setSenderId(StafferConstant.SUPER_STAFFER);

                mail.setReveiveIds(StringUtils.listToString(processList,";"));

                mail.setReveiveIds2(bean.getStafferId());

                //#820 提交的报销在邮件中链接打开报错
                if (bean instanceof ExpenseApplyVO){
                    mail.setHref(TcpConstanst.TCP_EXPENSE_PROCESS_URL + bean.getId());
                }
                else if(bean.getType()== TcpConstanst.VOCATION_WORK)
                {
                    mail.setHref(TcpConstanst.TCP_COMMIS_PROCESS_URL + bean.getId());
                }
                else
                {
                    mail.setHref(TcpConstanst.TCP_TRAVELAPPLY_PROCESS_URL + bean.getId());
                }
                // send mail
                mailMangaer.addMailWithoutTransactional(UserHelper.getSystemUser(), mail);
            }
        }
        else
        {
            // 会签
            result = bean.getStatus();
            _logger.info("***nextStatus***"+result);
        }

        return result;
    }

    /**
     * #301 跳过由同一个人处理的多个审批环节,递归找到下一环节处理人
     * get High level manager Id from  bank level table
     * @param originator 发起人
     * @param stafferId 当前环节处理人
     * @param nextStatus
     * @return
     */
    public TcpFlowBean getNextProcessor(String originator, String stafferId, String flowKey, int nextStatus) throws  MYException{
        String template = "getNextProcessor with originator:%s stafferId:%s flowKey:%s nextStatus:%s";
        _logger.info(String.format(template, originator, stafferId, flowKey, String.valueOf(nextStatus)));
        TcpFlowBean result = new TcpFlowBean();

        String nextProcessor = "";
        try {
            if (nextStatus == TcpConstanst.TCP_STATUS_PROVINCE_MANAGER
                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_MANAGER
                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_DIRECTOR
                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_CEO
                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_BUSINESS
                    ) {
                nextProcessor = this.bankBuLevelDAO.queryHighLevelManagerId(flowKey, nextStatus, stafferId, originator);
                //CEO这个环节如果和发起人一致不能跳过,财务审批前必须有有个人处理下
                if (originator.equals(nextProcessor) && nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_CEO){
                    result.setNextProcessor(nextProcessor);
                    result.setNextStatus(nextStatus);
                    _logger.info("****nextProcessor***"+nextProcessor+"***nextStatus***"+nextStatus);
                } else if (stafferId.equals(nextProcessor)){
                    TcpFlowBean token = tcpFlowDAO.findByUnique(flowKey, nextStatus);
                    _logger.info("***next token***"+token);
                    // 下一环节如果已经是pool,直接返回
                    if (token!= null && token.getNextPlugin().contains("pool")){
                        token.setNextProcessor(nextProcessor);
                        _logger.info("***return token***"+token);
                        return token;
                    }
                    return getNextProcessor(originator, nextProcessor, flowKey, token.getNextStatus());
                } else{
                    result.setNextProcessor(nextProcessor);
                    result.setNextStatus(nextStatus);
                    _logger.info("****nextProcessor***"+nextProcessor+"***nextStatus***"+nextStatus);
                }
            }
            //#495
            else if(nextStatus == TcpConstanst.TCP_STATUS_HIGHER_UP){
            	/*
                StafferBean stafferBean = this.stafferDAO.find(stafferId);
                nextProcessor = String.valueOf(stafferBean.getSuperiorLeader());
                */
            	
            	BankBuLevelBean bankBuLevelBean = this.bankBuLevelDAO.find(stafferId);
            	
            	_logger.debug("****stafferId***"+stafferId+", bankBuLevelBean==null:"+(bankBuLevelBean==null));
            	
            	nextProcessor = findApprover(bankBuLevelBean);
            	
                result.setNextProcessor(nextProcessor);
                result.setNextStatus(nextStatus);
                _logger.info("TCP_STATUS_HIGHER_UP****nextProcessor***"+nextProcessor+"***nextStatus***"+nextStatus);
            }
            //#627
            else if(nextStatus == TcpConstanst.TCP_STATUS_CENTER_MANAGER){
                nextProcessor = this.bankBuLevelDAO.queryManagerId(flowKey, stafferId, originator);
                result.setNextProcessor(nextProcessor);
                result.setNextStatus(nextStatus);
                _logger.info("TCP_STATUS_CENTER_MANAGER****nextProcessor***"+nextProcessor+"***nextStatus***"+nextStatus);
            }            
        }catch(Exception e){
            _logger.error(e);
            throw new MYException(stafferId+"T_CENTER_BANKBU_LEVEL表中stafferId没有处理人："+nextStatus);
        }

        return result;
    }
    
    //#628
    public static String findApprover(BankBuLevelBean bankBuLevelBean) throws MYException{
    	String rst = "";
    	
    	if(!bankBuLevelBean.isLevelsEntire()){
    		throw new MYException("员工["+bankBuLevelBean.getId()+":"+bankBuLevelBean.getName()+"]层级数据不完整！");
    	}
    	
    	String id = bankBuLevelBean.getId();
    	if(bankBuLevelBean.getZcId().equals(id)){
    		rst = bankBuLevelBean.getZcId();
    	}else if(bankBuLevelBean.getSybmanagerId().equals(id)){
    		rst = bankBuLevelBean.getZcId();
    	}else if(bankBuLevelBean.getManagerId().equals(id)){
    		rst = bankBuLevelBean.getSybmanagerId();
    	}else if(bankBuLevelBean.getRegionalManagerId().equals(id)){
    		rst = bankBuLevelBean.getManagerId();
    	}else {
    		rst = bankBuLevelBean.getRegionalManagerId();
    	}
    	return rst;
    }

    /**
     * @return the tcpApproveDAO
     */
    public TcpApproveDAO getTcpApproveDAO()
    {
        return tcpApproveDAO;
    }

    /**
     * @param tcpApproveDAO
     *            the tcpApproveDAO to set
     */
    public void setTcpApproveDAO(TcpApproveDAO tcpApproveDAO)
    {
        this.tcpApproveDAO = tcpApproveDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the travelApplyDAO
     */
    public TravelApplyDAO getTravelApplyDAO()
    {
        return travelApplyDAO;
    }

    /**
     * @param travelApplyDAO
     *            the travelApplyDAO to set
     */
    public void setTravelApplyDAO(TravelApplyDAO travelApplyDAO)
    {
        this.travelApplyDAO = travelApplyDAO;
    }

    /**
     * @return the expenseApplyDAO
     */
    public ExpenseApplyDAO getExpenseApplyDAO()
    {
        return expenseApplyDAO;
    }

    /**
     * @param expenseApplyDAO
     *            the expenseApplyDAO to set
     */
    public void setExpenseApplyDAO(ExpenseApplyDAO expenseApplyDAO)
    {
        this.expenseApplyDAO = expenseApplyDAO;
    }

    /**
     * @return the tcpFlowDAO
     */
    public TcpFlowDAO getTcpFlowDAO()
    {
        return tcpFlowDAO;
    }

    /**
     * @param tcpFlowDAO
     *            the tcpFlowDAO to set
     */
    public void setTcpFlowDAO(TcpFlowDAO tcpFlowDAO)
    {
        this.tcpFlowDAO = tcpFlowDAO;
    }

    /**
     * @return the groupVSStafferDAO
     */
    public GroupVSStafferDAO getGroupVSStafferDAO()
    {
        return groupVSStafferDAO;
    }

    /**
     * @param groupVSStafferDAO
     *            the groupVSStafferDAO to set
     */
    public void setGroupVSStafferDAO(GroupVSStafferDAO groupVSStafferDAO)
    {
        this.groupVSStafferDAO = groupVSStafferDAO;
    }

	public PreInvoiceApplyDAO getPreInvoiceApplyDAO()
	{
		return preInvoiceApplyDAO;
	}

	public void setPreInvoiceApplyDAO(PreInvoiceApplyDAO preInvoiceApplyDAO)
	{
		this.preInvoiceApplyDAO = preInvoiceApplyDAO;
	}

	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

    public MailMangaer getMailMangaer() {
        return mailMangaer;
    }

    public void setMailMangaer(MailMangaer mailMangaer) {
        this.mailMangaer = mailMangaer;
    }

    public BankBuLevelDAO getBankBuLevelDAO() {
        return bankBuLevelDAO;
    }

    public void setBankBuLevelDAO(BankBuLevelDAO bankBuLevelDAO) {
        this.bankBuLevelDAO = bankBuLevelDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }
}
