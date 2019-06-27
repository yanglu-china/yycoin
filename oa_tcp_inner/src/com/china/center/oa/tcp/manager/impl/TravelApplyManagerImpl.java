/**
 * File Name: TravelApplyManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.manager.impl;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.oa.publics.bean.*;
import com.china.center.oa.publics.dao.*;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.oa.tcp.bean.*;
import com.china.center.oa.tcp.dao.*;
import com.china.center.oa.tcp.manager.TcpFlowManager;
import com.china.center.tools.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.iaop.annotation.IntegrationAOP;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.budget.bean.BudgetItemBean;
import com.china.center.oa.budget.bean.BudgetLogBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.manager.BudgetManager;
import com.china.center.oa.finance.bean.BankBean;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.BankDAO;
import com.china.center.oa.finance.manager.BillManager;
import com.china.center.oa.group.dao.GroupVSStafferDAO;
import com.china.center.oa.group.vs.GroupVSStafferBean;
import com.china.center.oa.mail.bean.MailBean;
import com.china.center.oa.mail.manager.MailMangaer;
import com.china.center.oa.publics.constant.IDPrefixConstant;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.helper.UserHelper;
import com.china.center.oa.publics.manager.NotifyManager;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.listener.TcpPayListener;
import com.china.center.oa.tcp.manager.TravelApplyManager;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.vo.TcpShareVO;
import com.china.center.oa.tcp.vo.TravelApplyItemVO;
import com.china.center.oa.tcp.vo.TravelApplyVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;


/**
 * TravelApplyManagerImpl
 * 
 * @author ZHUZHU
 * @version 2011-7-17
 * @see TravelApplyManagerImpl
 * @since 3.0
 */
@IntegrationAOP
public class TravelApplyManagerImpl extends AbstractListenerManager<TcpPayListener> implements TravelApplyManager
{
    private final Log operationLog = LogFactory.getLog("opr");
    
    private final Log badLog = LogFactory.getLog("bad");
    
    private final Log _logger = LogFactory.getLog(getClass());

    private TcpApplyDAO tcpApplyDAO = null;

    private TcpFlowDAO tcpFlowDAO = null;
    
    private StafferDAO stafferDAO = null;

    private GroupVSStafferDAO groupVSStafferDAO = null;

    private TcpPrepaymentDAO tcpPrepaymentDAO = null;

    private TcpShareDAO tcpShareDAO = null;

    private TcpIbDAO tcpIbDAO = null;

    private TcpIbReportDAO tcpIbReportDAO = null;

    private TcpIbReportItemDAO tcpIbReportItemDAO = null;

    private TcpVSOutDAO tcpVSOutDAO = null;

    private TravelApplyDAO travelApplyDAO = null;

    private TravelApplyItemDAO travelApplyItemDAO = null;

    private TravelApplyPayDAO travelApplyPayDAO = null;

    private CommonDAO commonDAO = null;

    private BankDAO bankDAO = null;

    private TcpHandleHisDAO tcpHandleHisDAO = null;

    private LogDAO logDAO          = null;

    private MailMangaer mailMangaer = null;

    private NotifyManager notifyManager = null;

    private BudgetManager budgetManager = null;

    private OrgManager orgManager = null;

    private BillManager billManager = null;

    private TcpFlowManager tcpFlowManager = null;

    private BudgetItemDAO budgetItemDAO = null;

    private AttachmentDAO attachmentDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private TcpApproveDAO tcpApproveDAO = null;

    private OutDAO outDAO = null;

    private BaseDAO baseDAO = null;

    private BankBuLevelDAO bankBuLevelDAO = null;

    /**
     * default constructor
     */
    public TravelApplyManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tcp.manager.TravelApplyManager#addTravelApplyBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.tcp.bean.TravelApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addTravelApplyBean(User user, TravelApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);
        
        bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_TCP_PREFIX));

        bean.setStafferId(user.getStafferId());

        // 借款人就是自己
        if (StringTools.isNullOrNone(bean.getBorrowStafferId()))
        {
            bean.setBorrowStafferId(user.getStafferId());
        }

        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
        TCPHelper.setFlowKey(bean);
        _logger.info("***addTravelApplyBean***"+bean);
        // TravelApplyItemBean
        List<TravelApplyItemBean> itemList = bean.getItemList();
        
        if(null != itemList && itemList.size() > 0)
        {
        	for (TravelApplyItemBean travelApplyItemBean : itemList)
            {
                travelApplyItemBean.setId(commonDAO.getSquenceString20());
                travelApplyItemBean.setParentId(bean.getId());
            }

            travelApplyItemDAO.saveAllEntityBeans(itemList);
        }
        
        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
        {
            long temp = 0L;

            List<TravelApplyPayBean> payList = bean.getPayList();
            
            if(null != payList && payList.size() > 0)
            {
	            for (TravelApplyPayBean travelApplyPayBean : payList)
	            {
	                travelApplyPayBean.setId(commonDAO.getSquenceString20());
	                travelApplyPayBean.setParentId(bean.getId());
	
	                temp += travelApplyPayBean.getMoneys();
	            }
	            travelApplyPayDAO.saveAllEntityBeans(payList);
            }

            bean.setBorrowTotal(temp);

        }
        else
        {
            bean.setBorrowTotal(0);
        }
        // 合法性校验
        checkApply(bean);

        List<TcpShareBean> shareList = bean.getShareList();
        
        if(null != shareList && shareList.size() > 0)
        {
	        for (TcpShareBean tcpShareBean : shareList)
	        {
	            tcpShareBean.setId(commonDAO.getSquenceString20());
	            tcpShareBean.setRefId(bean.getId());
	        }
	        tcpShareDAO.saveAllEntityBeans(shareList);
        }

        //2015/4/11 中收激励
        List<TcpIbBean> ibList = bean.getIbList();

        if(!ListTools.isEmptyOrNull(ibList))
        {
            for (TcpIbBean ib : ibList)
            {
                ib.setId(commonDAO.getSquenceString20());
                ib.setRefId(bean.getId());
                ib.setLogTime(TimeTools.now());
            }
            this.tcpIbDAO.saveAllEntityBeans(ibList);
        }

        // #585
        List<TcpVSOutBean> tcpVSOutBeans = bean.getTcpVSOutBeanList();
        if(!ListTools.isEmptyOrNull(tcpVSOutBeans))
        {
            for (TcpVSOutBean ib : tcpVSOutBeans)
            {
                ib.setId(commonDAO.getSquenceString20());
                ib.setRefId(bean.getId());
                ib.setLogTime(TimeTools.now());
            }
            try {
                this.tcpVSOutDAO.saveAllEntityBeans(tcpVSOutBeans);
            }catch (Exception e){
                e.printStackTrace();
                _logger.error(e);
                throw new MYException("中收激励重复申请,请检查表T_CENTER_VS_TCPOUT!");
            }
        }

        List<AttachmentBean> attachmentList = bean.getAttachmentList();

        if(null != attachmentList && attachmentList.size() > 0)
        {
	        for (AttachmentBean attachmentBean : attachmentList)
	        {
	            attachmentBean.setId(commonDAO.getSquenceString20());
	            attachmentBean.setRefId(bean.getId());
	        }

	        attachmentDAO.saveAllEntityBeans(attachmentList);
        }
        
        travelApplyDAO.saveEntityBean(bean);

        saveApply(user, bean);

        saveFlowLog(user, TcpConstanst.TCP_STATUS_INIT, bean, "自动提交保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tcp.manager.TravelApplyManager#addTravelApplyBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.tcp.bean.TravelApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addVocAndWorkTravelApplyBean(User user, TravelApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);
        
        bean.setId(commonDAO.getSquenceString20(IDPrefixConstant.ID_TCP_PREFIX));

        bean.setStafferId(user.getStafferId());

//        // 借款人就是自己
        if (StringTools.isNullOrNone(bean.getBorrowStafferId()))
        {
            bean.setBorrowStafferId(user.getStafferId());
        }

        //bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 获取flowKey
        //TCPHelper.setFlowKey(bean);
        _logger.info(bean.getId() + " set to EXTRA_WORK_AND_LEAVE_CEO**********");
        bean.setFlowKey(TcpFlowConstant.EXTRA_WORK_AND_LEAVE_CEO);
        
//         合法性校验
        checkApply(bean);

        List<AttachmentBean> attachmentList = bean.getAttachmentList();

        if(null != attachmentList && attachmentList.size() > 0)
        {
	        for (AttachmentBean attachmentBean : attachmentList)
	        {
	            attachmentBean.setId(commonDAO.getSquenceString20());
	            attachmentBean.setRefId(bean.getId());
	        }

	        attachmentDAO.saveAllEntityBeans(attachmentList);
        }
        
        travelApplyDAO.saveEntityBean(bean);

        saveApply(user, bean);

        saveFlowLog(user, bean.getStatus(), bean, "自动提交保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean submitTravelApplyBean(User user, String id, String processId)
        throws MYException
    {
        _logger.info("***submitTravelApplyBean***"+processId);
        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("只能操作自己的申请");
        }

        // 预算占用
        //#495 脱离预算
//        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
//        {
//            checkBudget(user, bean, 0);
//        }


        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        // 进入审批状态
        int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, token.getNextStatus(), 0);

        int oldStatus = bean.getStatus();

        bean.setStatus(newStatus);

        travelApplyDAO.updateStatus(bean.getId(), newStatus);

        // 中收在此产生凭证借：营业费用-中收 (5504-47)贷：预提费用 (2191)
        this.setIbMotivationFlag(user, bean, true);
        
        // 记录操作日志
        saveFlowLog(user, oldStatus, bean, "提交申请", PublicConstant.OPRMODE_SUBMIT);

        return true;
    }

//    /**
//     * #301 跳过由同一个人处理的多个审批环节,递归找到下一环节处理人
//     * get High level manager Id from  bank level table
//     * @param originator 发起人
//     * @param stafferId 当前环节处理人
//     * @param nextStatus
//     * @return
//     */
//    private TcpFlowBean getNextProcessor(String originator, String stafferId, String flowKey, int nextStatus) throws  MYException{
//        String template = "getNextProcessor with originator:%s stafferId:%s flowKey:%s nextStatus:%s";
//        _logger.info(String.format(template, originator, stafferId, flowKey, String.valueOf(nextStatus)));
//        TcpFlowBean result = new TcpFlowBean();
//
//        String nextProcessor = "";
//        try {
//            if (nextStatus == TcpConstanst.TCP_STATUS_PROVINCE_MANAGER
//                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_MANAGER
//                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_DIRECTOR
//                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_CEO) {
//                nextProcessor = this.bankBuLevelDAO.queryHighLevelManagerId(flowKey, nextStatus, stafferId, originator);
//                if (stafferId.equals(nextProcessor)){
//                    TcpFlowBean token = tcpFlowDAO.findByUnique(flowKey, nextStatus);
//                    _logger.info("***next token***"+token);
//                    // 下一环节如果已经是pool,直接返回
//                    if (token!= null && token.getNextPlugin().contains("pool")){
//                        token.setNextProcessor(nextProcessor);
//                        _logger.info("***return token***"+token);
//                        return token;
//                    }
//                    return getNextProcessor(originator, nextProcessor, flowKey, token.getNextStatus());
//                } else{
//                    result.setNextProcessor(nextProcessor);
//                    result.setNextStatus(nextStatus);
//                    _logger.info("****nextProcessor***"+nextProcessor+"***nextStatus***"+nextStatus);
//                }
//            }
//        }catch(Exception e){
//            _logger.error(e);
//            throw new MYException(stafferId+"T_CENTER_BANKBU_LEVEL表中stafferId没有处理人："+nextStatus);
//        }
//
//        return result;
//    }

//    private String getNextProcessor(String stafferId, String flowKey, int nextStatus) throws  MYException{
//        String nextProcessor = "";
//        try {
//            if (nextStatus == TcpConstanst.TCP_STATUS_PROVINCE_MANAGER
//                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_MANAGER
//                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_DIRECTOR
//                    || nextStatus == TcpConstanst.TCP_STATUS_REGIONAL_CEO) {
//                nextProcessor = this.bankBuLevelDAO.queryHighLevelManagerId(flowKey, nextStatus, stafferId);
//                if (stafferId.equals(nextProcessor)){
//                    TcpFlowBean token = tcpFlowDAO.findByUnique(flowKey, nextStatus);
//                    _logger.info("***next token***"+token);
//                    return getNextProcessor(nextProcessor, flowKey, token.getNextStatus());
//                }
//                return nextProcessor;
//            }else {
//                return "";
//            }
//        }catch(Exception e){
//            _logger.error(e);
//            throw new MYException(stafferId+"T_CENTER_BANKBU_LEVEL表中stafferId没有处理人："+nextStatus);
//        }
//    }
    
    /**
     * 获取部门负责人
     * @param proid
     * @return
     */
    public String getSailOutDepart(String proid)
    {
    	String tmp = proid;
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200001");

        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
            {
            	proid = vo.getId();
            	break;
            }
            else
            {
            	proid = null;
            	continue;
            }
            
        }
        return proid;
    }
    
    /**
     * 获取大区负责人
     * @param proid
     * @return
     */
    public String getSailOutLargeArea(String proid)
    {
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200002");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
            {
            	proid = vo.getId();
            	break;
            }
            else
            {
            	proid = null;
            	continue;
            }
            
        }
        return proid;
    }
    
    /**
     * 获取事业部负责人
     * @param proid
     * @return
     */
    public String getSailOutCareer(String proid)
    {

        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200003");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
            {
            	proid = vo.getId();
            	break;
            }
            else
            {
            	proid = null;
            	continue;
            }
            
        }
        return proid;
    }
    
    
    /**
     * 获取部门负责人
     * @param proid
     * @return
     */
    public String getDepart(String proid)
    {
    	String tmp = proid;
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200001");

        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
            {
            	proid = vo.getId();
            	break;
            }
            else
            {
            	proid = null;
            	continue;
            }
            
        }
        return proid;
    }
    
    /**
     * 获取中心总监负责人
     * @param proid
     * @return
     */
    public String getCenterInspectorGeneral(String proid)
    {
        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200011");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
            {
            	proid = vo.getId();
            	break;
            }
            else
            {
            	proid = null;
            	continue;
            }
            
        }
        return proid;
    }
    
    /**
     * 获取财务总监负责人
     * @param proid
     * @return
     */
    public String getFinanceInspectorGeneral(String proid)
    {

        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200004");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
            {
            	proid = vo.getId();
            	break;
            }
            else
            {
            	proid = null;
            	continue;
            }
            
        }
        return proid;
    }
    
    /**
     * 获取总裁
     * @param proid
     * @return
     */
    public String getPresident(String proid)
    {

        List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220110406000200005");
        String tmp = proid;
        for (GroupVSStafferBean groupVSStafferBean : vs)
        {
        	StafferVO vo = stafferDAO.findVO(groupVSStafferBean.getStafferId());
            if(null != vo && vo.getId().equals(tmp))
            {
            	proid = vo.getId();
            	break;
            }
            else
            {
            	proid = null;
            	continue;
            }
            
        }
        return proid;
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean submitVocationAndWork(User user, String id, String processId)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("只能操作自己的申请");
        }

        // 进入审批状态
        this.tcpFlowManager.saveApprove(user, processId, bean, bean.getStatus(), 0);

        int oldStatus = bean.getStatus();

        travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());

        // 记录操作日志
        saveFlowLog(user, oldStatus, bean, "提交申请", PublicConstant.OPRMODE_SUBMIT);

        return true;
    }
    
    
	@Transactional(rollbackFor = MYException.class)
	public boolean passTravelApplyBean(User user, TcpParamWrap param)
	    throws MYException
	{
	    _logger.info("****passTravelApplyBean****"+param);
	    String id = param.getId();
	    String processId = param.getProcessId();
	    String reason = param.getReason();
	
	    JudgeTools.judgeParameterIsNull(user, id);
	
	    TravelApplyVO bean = findVO(id);
	
	    if (bean == null)
	    {
	        throw new MYException("数据错误,请确认操作");
	    }
	
	    // 权限
	    checkAuth(user, id);
	
	    int oldStatus = bean.getStatus();
	
	    // 分支处理
	    logicProcess(user, param, bean, oldStatus);

        //#441 2017/3/29 每一步通过时再次设置
        this.setIbMotivationFlag(user, bean, false);

	    // 获得当前的处理环节
	    TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());
	    if (token == null)
	    {
	        throw new MYException("数据错误,请确认操作");
	    }

        _logger.info("**************token.getNextPlugin " + token.getNextPlugin());
	    // 群组模式
	    if (token.getNextPlugin().startsWith("group"))
	    {
	        int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, token.getNextStatus(), 0);
	        if (newStatus != oldStatus)
	        {
	            bean.setStatus(newStatus);
	
	            travelApplyDAO.updateStatus(bean.getId(), newStatus);
	        }
	
	        // 记录操作日志
	        saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	    }
	    // 共享池模式
	    if (token.getNextPlugin().startsWith("pool"))
	    {
	        this.pool(token, user, bean, oldStatus, reason);
	    }
	    // 插件模式
	    else if (token.getNextPlugin().startsWith("plugin"))
	    {
	        // plugin:travelSingeAll(意思是权签人会签)
	        if (token.getNextPlugin().equalsIgnoreCase("plugin:travelSingeAll"))
	        {
	            List<String> processList = new ArrayList();
	
	            // 先处理一个
	            List<TcpShareVO> shareVOList = bean.getShareVOList();
	
	            if (ListTools.isEmptyOrNull(shareVOList))
	            {
	                throw new MYException("下环节里面没有人员,请确认操作");
	            }
	
	            for (TcpShareVO tcpShareVO : shareVOList)
	            {
	                processList.add(tcpShareVO.getApproverId());
	            }
	
	            int newStatus = this.tcpFlowManager.saveApprove(user, processList, bean, token.getNextStatus(),
	                TcpConstanst.TCP_POOL_COMMON);
	
	            bean.setStatus(newStatus);                
	            
	            travelApplyDAO.updateStatus(bean.getId(), newStatus);
	
	            // 记录操作日志
	            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	        }
            //#495
	        else if (token.getNextPlugin().equalsIgnoreCase("plugin:higherUpShare"))
                {
                    _logger.info("******TravelApplyManagerImpl plugin:higherUpShare******");
                    List<String> processList = new ArrayList();

                    // 先处理一个
                    List<TcpShareVO> shareVOList = bean.getShareVOList();

                    if (ListTools.isEmptyOrNull(shareVOList))
                    {
                        throw new MYException("下环节里面没有人员,请确认操作");
                    }

                    //previous approver
                    BankBuLevelBean bankBuLevelBeanPre = this.bankBuLevelDAO.find(bean.getStafferId());
                    String commiterApprover = TcpFlowManagerImpl.findApprover(bankBuLevelBeanPre);

                    for (TcpShareVO tcpShareVO : shareVOList)
                    {
                        String bearId = tcpShareVO.getBearId();
                        //#628
                        BankBuLevelBean bankBuLevelBean = this.bankBuLevelDAO.find(bearId);
                        String sybname = bankBuLevelBean.getSybname();
                        
                        _logger.debug("***sybname："+sybname+", bearId:"+bearId+", commiterApprover:"+commiterApprover
                        		+ ", shareVOList.size(): "+shareVOList.size());
                        
                        String bearLeader = "";
                        
                        if("渠道事业部".equals(sybname) || !sybname.contains("事业部")){
                        	//原来的流程
                            StafferBean stafferBean = this.stafferDAO.find(bearId);
                            // 承担人直属上级审批
                            bearLeader = String.valueOf(stafferBean.getSuperiorLeader());
                            //StafferBean commiter = this.stafferDAO.find(bean.getStafferId());
                            
                            _logger.debug("渠道事业部 ***bearLeader："+bearLeader+", commiterApprover:"+commiterApprover);
                            
                            if (!StringTools.isNullOrNone(bearLeader)
                                    && !processList.contains(bearLeader)
                                    //如果承担人直属上级与提交人直属上级一致，则过滤掉
                                    && !bearLeader.equals(commiterApprover)){
                                processList.add(bearLeader);
                            }
                        	
                        }else if("终端事业部".equals(sybname)){
                        	bearLeader = this.bankBuLevelDAO.queryManagerId("", bearId, "");
                        	
                        	_logger.debug("终端事业部 ***bearLeader："+bearLeader+", commiterApprover:"+commiterApprover);
                        	
                            if (!StringTools.isNullOrNone(bearLeader)
                                    && !processList.contains(bearLeader)
                                    //如果承担人直属上级与提交人直属上级一致，则过滤掉
                                    && !bearLeader.equals(commiterApprover)){
                                processList.add(bearLeader);
                            }
                        }else if("银行事业部".equals(sybname)){
                        	bearLeader = this.findShareApprover(bankBuLevelBean);
                        	
                        	_logger.debug("银行事业部 ***bearLeader："+bearLeader+", commiterApprover:"+commiterApprover);
                        	
                            if (StringTools.isNullOrNone(bearLeader)){
                            	//最高层级为provincemanager和NAME时，由regionaldirector审批
                            	bearLeader = bankBuLevelBean.getRegionalDirectorId();
                            }  
                            
                            if (!processList.contains(bearLeader)
                                    && !bearLeader.equals(commiterApprover)){
                                processList.add(bearLeader);
                            }

                        }

                    }

                    int newStatus = 0;
                    if (ListTools.isEmptyOrNull(processList)){
                        //直接跳过承担人直属上级审批环节到待财务审批环节
                        _logger.info("***ignore same leader***"+bean.getId());
                        TcpFlowBean newToken = tcpFlowDAO.findByUnique(bean.getFlowKey(), TcpConstanst.TCP_STATUS_HIGHER_UP_SHARE);
                        if (newToken.getNextPlugin().startsWith("pool"))
                        {
                            this.pool(newToken, user, bean, oldStatus, reason);
                        }
                    } else{
                        newStatus = this.tcpFlowManager.saveApprove(user, processList, bean, token.getNextStatus(),
                                TcpConstanst.TCP_POOL_COMMON);
                        bean.setStatus(newStatus);
                        travelApplyDAO.updateStatus(bean.getId(), newStatus);
                        // 记录操作日志
                        saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                    }
                }
            //#248
            else if (token.getNextPlugin().equalsIgnoreCase("plugin:regionalManager")
                    || token.getNextPlugin().equalsIgnoreCase("plugin:regionalDirector")
                    || token.getNextPlugin().equalsIgnoreCase("plugin:regionalCEO"))
            {
                List<String> processList = new ArrayList();
//                String nextProcessor = this.getNextProcessor(user.getStafferId(), token.getFlowKey(), token.getNextStatus());
                TcpFlowBean nextToken = this.tcpFlowManager.getNextProcessor(bean.getStafferId(), user.getStafferId(), token.getFlowKey(), token.getNextStatus());
                _logger.info("****next Token***"+nextToken);
                if (nextToken!= null && nextToken.getNextPlugin().contains("pool")){
                    this.pool(nextToken, user,bean,oldStatus,reason);
                } else{
                    int nextStatusIgnoreDuplicate = nextToken.getNextStatus();
                    String nextProcessor = nextToken.getNextProcessor();
                    if (!StringTools.isNullOrNone(nextProcessor)){
                        processList.add(nextProcessor);
                    }
                    _logger.info("***processList***"+processList.size());

                    int newStatus = this.tcpFlowManager.saveApprove(user, processList, bean, nextStatusIgnoreDuplicate,
                            TcpConstanst.TCP_POOL_COMMON);

                    bean.setStatus(newStatus);

                    travelApplyDAO.updateStatus(bean.getId(), newStatus);

                    // 记录操作日志
                    saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                }
            }
	    }
	    // 结束模式
	    else if (token.getNextPlugin().startsWith("end"))
	    {
	        // 结束了需要清空
	        tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
	
	        bean.setStatus(token.getNextStatus());
	
	        travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());
	
	        // 记录操作日志
	        saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	    }


	    return true;
	}
	
    //#628
    private String findShareApprover(BankBuLevelBean bankBuLevelBean){
    	String rst = "";
    	String id = bankBuLevelBean.getId();
    	if(bankBuLevelBean.getSybmanagerId().equals(id)){
    		rst = bankBuLevelBean.getZcId();
    	}else if(bankBuLevelBean.getManagerId().equals(id)){
    		rst = bankBuLevelBean.getSybmanagerId();
    	}else if(bankBuLevelBean.getRegionalDirectorId().equals(id)){
    		rst = bankBuLevelBean.getManagerId();
    	}else if(bankBuLevelBean.getRegionalManagerId().equals(id)){
    		rst = bankBuLevelBean.getRegionalDirectorId();
    	}
    	return rst;
    }

	private void pool(TcpFlowBean token, User user, TravelApplyVO bean, int oldStatus, String reason) throws MYException
    {
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

        int newStatus = this.tcpFlowManager.saveApprove(user, processList, bean, token.getNextStatus(),
                TcpConstanst.TCP_POOL_POOL);

        if (newStatus != oldStatus)
        {
            bean.setStatus(newStatus);

            // 会签结束，且没有借款的情况，后续状态直接置为结束
            if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO && newStatus == TcpConstanst.TCP_STATUS_WAIT_PAY )
            {
                newStatus = TcpConstanst.TCP_STATUS_END;

                // 结束了要清空
                tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

                bean.setStatus(newStatus);
            }

            travelApplyDAO.updateStatus(bean.getId(), newStatus);
        }

        // 记录操作日志
        saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
    }
    
    

    /**
     * 请假加班审批
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean passVocAndWorkTravelApplyBean(User user, TcpParamWrap param)
        throws MYException
    {
        String id = param.getId();
        String processId = param.getProcessId();
        String reason = param.getReason();
        
        JudgeTools.judgeParameterIsNull(user, id);
        
        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        
        // 权限
        checkAuth(user, id);

        int oldStatus = bean.getStatus();
        
        // 获得当前的处理环节
        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());
        if (token == null)
        {
            throw new MYException("数据错误,请确认操作");
        }
        
        // 请假类型，请假时间在2天以内时，流程直接到人事审批
        if (bean.getPurposeType() == 31) {
        	
        	if (bean.getStatus() != TcpConstanst.TCP_STATUS_HR) {
        		
          		String begind = TimeTools.changeFormat(bean.getBeginDate(), "yyyy-MM-dd HH:mm", "yyyy-MM-dd");
          		String endd = TimeTools.changeFormat(bean.getEndDate(), "yyyy-MM-dd HH:mm", "yyyy-MM-dd");
          		
          		int days = TimeTools.cdate(endd, begind);
        		
          		if (days > 0 && days <= 2) {
          			token = tcpFlowDAO.findByUnique(bean.getFlowKey(), TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO);
                    if (token == null)
                    {
                        throw new MYException("数据错误,请确认操作");
                    }
                    
                    List<GroupVSStafferBean> vs = groupVSStafferDAO.queryEntityBeansByFK("A220140624000200001");
                    
                    if (ListTools.isEmptyOrNull(vs)) {
                    	throw new MYException("数据错误,系统群组-人力资源未配置人员");
                    } else {
                    	processId = vs.get(0).getStafferId();
                    	
                    	StafferBean staffer = stafferDAO.find(processId);
                    	
                    	if (null == staffer) {
                    		throw new MYException("数据错误,系统群组-人力资源配置人员不存在");
                    	}
                    }
          		}
        		
        	}
        }
        
        // 群组模式
        if (token.getNextPlugin().startsWith("group"))
        {
            int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, token.getNextStatus(), 0);
            
            if (newStatus != oldStatus)
            {
                bean.setStatus(newStatus);

                travelApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 共享池模式
        if (token.getNextPlugin().startsWith("pool"))
        {
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

            int newStatus = this.tcpFlowManager.saveApprove(user, processList, bean, token.getNextStatus(),
                TcpConstanst.TCP_POOL_POOL);

            if (newStatus != oldStatus)
            {
                bean.setStatus(newStatus);
                
                // 会签结束，且没有借款的情况，后续状态直接置为结束
                if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO && newStatus == TcpConstanst.TCP_STATUS_WAIT_PAY )
                {
                    newStatus = TcpConstanst.TCP_STATUS_END;
                    
                    // 结束了要清空
                    tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
                    
                    bean.setStatus(newStatus);
                }

                travelApplyDAO.updateStatus(bean.getId(), newStatus);
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }
        // 插件模式
        else if (token.getNextPlugin().startsWith("plugin"))
        {
            // plugin:travelSingeAll(意思是权签人会签)
            if (token.getNextPlugin().equalsIgnoreCase("plugin:travelSingeAll"))
            {
                List<String> processList = new ArrayList();

                // 先处理一个
                List<TcpShareVO> shareVOList = bean.getShareVOList();

                if (ListTools.isEmptyOrNull(shareVOList))
                {
                    throw new MYException("下环节里面没有人员,请确认操作");
                }

                for (TcpShareVO tcpShareVO : shareVOList)
                {
                    processList.add(tcpShareVO.getApproverId());
                }

                int newStatus = this.tcpFlowManager.saveApprove(user, processList, bean, token.getNextStatus(),
                    TcpConstanst.TCP_POOL_COMMON);

                bean.setStatus(newStatus);
                
                travelApplyDAO.updateStatus(bean.getId(), newStatus);

                // 记录操作日志
                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
            }
        }
        // 结束模式
        else if (token.getNextPlugin().startsWith("end"))
        {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            bean.setStatus(token.getNextStatus());

            travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
        }

        return true;
    }
    
    /**
     *请假申请 
     */
    
    @Transactional(rollbackFor = MYException.class)
    public boolean vocationAndWork(TravelApplyVO bean,StafferVO staffervo,User user,String processId,
    		String reason,TcpParamWrap param,int oldStatus)
        throws MYException
    {
    	try
        {
    		long days = 0;
        	String beginDate = bean.getBeginDate();
          	String endDate = bean.getEndDate();
          	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      		long to = sdf.parse(endDate).getTime();
      		long from = sdf.parse(beginDate).getTime();
      		days = (to - from) / (1000 * 60 * 60 * 24);//请假时间，以天为单位
      		String postid = staffervo.getPostId();
      		StafferBean processBean = stafferDAO.find(user.getStafferId());
      		
      		 if((bean.getStatus() == TcpConstanst.TCP_STATUS_HR) && user.getStafferId().trim().equals("103565"))
      		{
      			 // 结束了要清空
	      	       tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
	      	                    
	      	       bean.setStatus(TcpConstanst.TCP_STATUS_END);
	
	      	       travelApplyDAO.updateStatus(bean.getId(), TcpConstanst.TCP_STATUS_END);
	      	            // 记录操作日志
	      	       saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	      	       return true;
      		}
      		else if(null != processBean && processBean.getPostId().equals("20"))
      		{
      			int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);//直接提交到徐景(人事部)
                
                if (newStatus != oldStatus)
                {
                    bean.setStatus(newStatus);

                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
                }

                // 记录操作日志
                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                
      	        return true;
      		}
      		else
      		{
      			
      		if(days >= 0 && days < 3)
	      		{
	      			int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
	                
	                if (newStatus != oldStatus)
	                {
	                    bean.setStatus(newStatus);
	
	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
	                }
	
	                // 记录操作日志
	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	                
	      	        return true;
	      		}
      		
      		
      		if((null != postid && postid.equals("4") && days > 2 && days < 6) 
      				|| (null != postid && postid.equals("4") && days > 5))
	      		{
      				if(bean.getStatus() == 4)
      				{
      					int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
      	                
      	                if (newStatus != oldStatus)
      	                {
      	                    bean.setStatus(newStatus);

      	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
      	                }

      	                // 记录操作日志
      	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
      	                
      	      	        return true;
      				}
      				else
      				{
      					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
		                
		                if (newStatus != oldStatus)
		                {
		                    bean.setStatus(newStatus);
	
		                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
		                }
	
		                // 记录操作日志
		                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
		                
		      	        return true;
      				}
	      			
	      		 }
      		
      		if((null != postid && postid.equals("17") && days > 2 && days < 6) 
      				|| (null != postid && postid.equals("17") && days > 5))
	      		{
	      			int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
	                
	                if (newStatus != oldStatus)
	                {
	                    bean.setStatus(newStatus);
	
	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
	                }
	
	                // 记录操作日志
	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	                
	      	        return true;
	      		}
      		
      		if(null != postid && postid.equals("18") && days > 2 && days < 6)
      		{
      			int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
                
                if (newStatus != oldStatus)
                {
                    bean.setStatus(newStatus);

                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
                }

                // 记录操作日志
                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                
      	        return true;
      		 }
      		
      		if(null != postid && postid.equals("18") && days > 5)
      		{
      			if(bean.getStatus() == 28)
  				{
      				int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
                    
                    if (newStatus != oldStatus)
                    {
                        bean.setStatus(newStatus);

                        travelApplyDAO.updateStatus(bean.getId(), newStatus);
                    }

                    // 记录操作日志
                    saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                    
          	        return true;
  				}
  				else
  				{
  					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
	                
	                if (newStatus != oldStatus)
	                {
	                    bean.setStatus(newStatus);

	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
	                }

	                // 记录操作日志
	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
	                
	      	        return true;
  				}
      		 }
      		if((null != postid && postid.equals("16") && days > 2 && days < 6) || 
      				(null != postid && postid.equals("16") && days > 5))
      		{
      			int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
                
                if (newStatus != oldStatus)
                {
                    bean.setStatus(newStatus);

                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
                }

                // 记录操作日志
                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
                
      	        return true;
      		 }
      		
      		}
        }
        catch(ParseException e)
        {
        	throw new MYException("请假日期计算错误");
        }
  
        return true;
    }
    
    
    /**
     *加班,撤销申请 
     *
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addWork(TravelApplyVO bean,StafferVO staffervo,User user,String processId,
    		String reason,TcpParamWrap param,int oldStatus)
        throws MYException
    {
	      		String postid = staffervo.getPostId();
		    	if((bean.getPurposeType() != -1 && bean.getPurposeType() == 21)
		    			||(bean.getPurposeType() != -1 && bean.getPurposeType() == 12)
		    			||(bean.getPurposeType() != -1 && bean.getPurposeType() == 22)
		    			||(bean.getPurposeType() != -1 && bean.getPurposeType() == 32))
		  		{
		    		StafferBean processBean = stafferDAO.find(processId);
		      		
		    		if((bean.getStatus() == TcpConstanst.TCP_STATUS_HR) && user.getStafferId().trim().equals("103565"))
		      		{
		      		// 结束了要清空
			      	       tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
			      	                    
			      	       bean.setStatus(TcpConstanst.TCP_STATUS_END);
			
			      	       travelApplyDAO.updateStatus(bean.getId(), TcpConstanst.TCP_STATUS_END);
			      	            // 记录操作日志
			      	       saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			      	       return true;
		      		}
		    		else if(null != processBean && processBean.getPostId().equals("20"))
		      		{
		    			int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
		                
		                if (newStatus != oldStatus)
		                {
		                    bean.setStatus(newStatus);
	
		                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
		                }
	
		                // 记录操作日志
		                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
		                
		      	        return true;
		      		}
		      		else
		      		{
			  			if(null != postid && postid.equals("4"))
				      		{
			      				if(bean.getStatus() == TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO)//待总裁审批
			      				{
			      					int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			      	                
			      	                if (newStatus != oldStatus)
			      	                {
			      	                    bean.setStatus(newStatus);

			      	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			      	                }

			      	                // 记录操作日志
			      	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			      	                
			      	      	        return true;
			      				}
			      				else if(processBean.getPostId().equals("16"))//待事业部经理审批
			      				{
			      					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
					                if (newStatus != oldStatus)
					                {
					                    bean.setStatus(newStatus);
				
					                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
					                }
				
					                // 记录操作日志
					                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
					                
					      	        return true;
			      				}
			      				else if(processBean.getPostId().equals("20"))
			      				{
			      					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
					                
					                if (newStatus != oldStatus)
					                {
					                    bean.setStatus(newStatus);
				
					                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
					                }
				
					                // 记录操作日志
					                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
					                
					      	        return true;
			      				}
				      			
				      		 }
			  			
			  			if(null != postid && postid.equals("17"))
			      		{
			  				if(bean.getStatus() == 28)
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			  	                
			  	                if (newStatus != oldStatus)
			  	                {
			  	                    bean.setStatus(newStatus);

			  	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			  	                }

			  	                // 记录操作日志
			  	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			  	                
			  	      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("16"))
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("20"))
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			      			
			      		 }
			  			
			  			if(null != postid && postid.equals("16"))
			      		{
			  				if(bean.getStatus() == 28)
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			  	                
			  	                if (newStatus != oldStatus)
			  	                {
			  	                    bean.setStatus(newStatus);

			  	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			  	                }

			  	                // 记录操作日志
			  	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			  	                
			  	      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("16"))
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("20"))
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			      			
			      		 }
			  			
			  			if(null != postid && postid.equals("18"))
			      		{
			  				if(bean.getStatus() == 28)
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			  	                
			  	                if (newStatus != oldStatus)
			  	                {
			  	                    bean.setStatus(newStatus);

			  	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			  	                }

			  	                // 记录操作日志
			  	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			  	                
			  	      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("16"))
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("20"))
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			      			
			      		 }
			  			
			  			if(null != postid && postid.equals("20"))
			      		{
			  				if(bean.getStatus() == 28)
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, "103565", bean, TcpConstanst.TCP_STATUS_HR, 0);
			  	                
			  	                if (newStatus != oldStatus)
			  	                {
			  	                    bean.setStatus(newStatus);

			  	                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
			  	                }

			  	                // 记录操作日志
			  	                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
			  	                
			  	      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("16"))
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_LOCATION, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			  				else if(processBean.getPostId().equals("20"))
			  				{
			  					int newStatus = this.tcpFlowManager.saveApprove(user, processId, bean, TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO, 0);
				                
				                if (newStatus != oldStatus)
				                {
				                    bean.setStatus(newStatus);
			
				                    travelApplyDAO.updateStatus(bean.getId(), newStatus);
				                }
			
				                // 记录操作日志
				                saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_PASS);
				                
				      	        return true;
			  				}
			      			
			      		 }
		      		}
		  		}
    	return true;
    }

    /**
     * logicProcess
     * 
     * @param user
     * @param param
     * @param bean
     * @param oldStatus
     * @throws MYException
     */
    private void logicProcess(User user, TcpParamWrap param, TravelApplyVO bean, int oldStatus)
        throws MYException
    {
        // 这里需要特殊处理的(稽核修改金额)
        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_CHECK)
        {
            // 稽核需要重新整理pay和重新预算
            if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES
            		|| bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO_BUTHOLD)
            {
            	if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
            	{
            		List<TravelApplyPayBean> newPayList = (List<TravelApplyPayBean>)param.getOther();

                    long newBrrow = 0L;

                    for (TravelApplyPayBean travelApplyPayBean : newPayList)
                    {
                        newBrrow += travelApplyPayBean.getCmoneys();
                    }

                    bean.setBorrowTotal(newBrrow);

                    bean.setPayList(newPayList);
                    
                    // 成功后更新支付列表
                    travelApplyPayDAO.updateAllEntityBeans(newPayList);
            	}

                // 如果没有借款不需要重新预算
                //#495 脱离预算
                //checkBudget(user, bean, 1);

                // 不借款仅用于占检查预算且占预算，所以借款额还是0
                if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO_BUTHOLD)
                {
                	bean.setBorrowTotal(0);
                }

                travelApplyDAO.updateBorrowTotal(param.getId(), bean.getBorrowTotal());
                
                String scompliance = param.getCompliance();
                // 更新合规标识
                travelApplyDAO.updateCompliance(param.getId(), scompliance);
            }
        }

        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_PAY)
        {
            String dutyId = param.getDutyId();

            if (dutyId == null)
            {
                throw new MYException("缺少纳税实体,请确认操作");
            }

            travelApplyDAO.updateDutyId(bean.getId(), dutyId);
        }

        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_PAY
            && bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
        {
            // 财务付款
            List<OutBillBean> outBillList = (List<OutBillBean>)param.getOther();

            String dutyId = param.getDutyId();

            double total = 0.0d;
            StringBuffer idBuffer = new StringBuffer();
            for (OutBillBean outBill : outBillList)
            {
                BankBean bank = bankDAO.find(outBill.getBankId());

                if (bank == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                if ( !bank.getDutyId().equals(dutyId))
                {
                    throw new MYException("银行和纳税实体不对应,请确认操作");
                }

                // 生成付款单
                createOutBill(user, outBill, bean);

                total += outBill.getMoneys();

                idBuffer.append(outBill.getId()).append(';');
            }

            if (MathTools.doubleToLong2(total) != bean.getBorrowTotal())
            {
                throw new MYException("付款金额[%.2f]不等于借款金额[%.2f]", total, MathTools
                    .longToDouble2(bean.getBorrowTotal()));
            }

            Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

            for (TcpPayListener tcpPayListener : listenerMapValues)
            {
            	// 只有中收申请，在支付确认后，红冲提交时产生的凭证
            	if (bean.getIbType() == TcpConstanst.IB_TYPE
                        || bean.getIbType() == TcpConstanst.IB_TYPE2) {
            		tcpPayListener.onSubmitMidTravelApply(user, bean, -1);
            	}
            	
                // TODO_OSGI 这里是出差申请的借款生成凭证/中收
                tcpPayListener.onPayTravelApply(user, bean, outBillList);
            }

            // 更新预算使用状态
            budgetManager.updateBudgetLogUserTypeByRefIdWithoutTransactional(user, bean.getId(),
                BudgetConstant.BUDGETLOG_USERTYPE_REAL, idBuffer.toString());
        }

        // 采购货比三家
        if (oldStatus == TcpConstanst.TCP_STATUS_WAIT_BUY
            && bean.getType() == TcpConstanst.TCP_APPLYTYPE_STOCK)
        {
            List<TravelApplyItemBean> newItemList = (List<TravelApplyItemBean>)param.getOther2();
            List<TravelApplyItemVO> itemVOList = bean.getItemVOList();

            long total = 0L;
            
            if(null != itemVOList && itemVOList.size()>0)
            {
            	
	            for (TravelApplyItemVO travelApplyItemVO : itemVOList)
	            {
	            	if(null != newItemList && newItemList.size() > 0)
	            	{
		                for (TravelApplyItemBean travelApplyItemBean : newItemList)
		                {
		                    if (travelApplyItemVO.getId().equals(travelApplyItemBean.getId()))
		                    {
		                        travelApplyItemVO.setCheckPrices(travelApplyItemBean.getCheckPrices());
		                        travelApplyItemVO.setMoneys(travelApplyItemBean.getMoneys());
		                        travelApplyItemVO.setPurpose(travelApplyItemBean.getPurpose());
		                    }
		                }
		                // 更新采购项
		                travelApplyItemDAO.updateEntityBean(travelApplyItemVO);
		
		                total += travelApplyItemVO.getMoneys();
	            	}  
	            }
            }
            travelApplyDAO.updateTotal(param.getId(), total);
            ////#495 脱离预算
//            if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
//            {
//                // 更新预算(重新加入预占)
//                checkBudget(user, bean, 1);
//            }
        }
    }

    /**
     * createOutBill
     * 
     * @param user
     * @param outBill
     * @param apply
     * @throws MYException
     */
    private void createOutBill(User user, OutBillBean outBill, TravelApplyVO apply)
        throws MYException
    {
        // 自动生成付款单
        outBill.setDescription(DefinedCommon.getValue("tcpType", apply.getType()) + "申请借款的付款:"
                               + apply.getId());

        outBill.setLocationId(user.getLocationId());

        outBill.setLogTime(TimeTools.now());

        outBill.setType(FinanceConstant.OUTBILL_TYPE_BORROW);

        outBill.setOwnerId(apply.getBorrowStafferId());

        outBill.setStafferId(user.getStafferId());

        outBill.setProvideId("");

        // 借款的单据
        outBill.setStockId(apply.getId());

        outBill.setStockItemId("");

        billManager.addOutBillBeanWithoutTransaction(user, outBill);
    }

    /**
     * 权限
     * 
     * @param user
     * @param id
     * @throws MYException
     */
    private void checkAuth(User user, String id)
        throws MYException
    {
        List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(id);

        boolean hasAuth = false;

        for (TcpApproveBean tcpApproveBean : approveList)
        {
            if (tcpApproveBean.getApproverId().equals(user.getStafferId()))
            {
                hasAuth = true;

                break;
            }
        }

        if ( !hasAuth)
        {
            throw new MYException("没有操作权限,请确认操作");
        }
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rejectTravelApplyBean(User user, TcpParamWrap param)
        throws MYException
    {
        String id = param.getId();
        String reason = param.getReason();
        String type = param.getType();

        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 权限
        checkAuth(user, id);
        
        // 中收申请，同时删除凭证。如果此时凭证所在的月份已经月结，要求反月结才能继续驳回操作
        if (bean.isMidOrMotivation()) {
            this.resetIbMotivationFlag(bean);

        	Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

            for (TcpPayListener tcpPayListener : listenerMapValues)
            {
                tcpPayListener.onRejectMidTravelApply(user, bean);
            }
        }
        
        // 获得当前的处理环节
        // TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        // 获得上一步
        FlowLogBean lastLog = flowLogDAO.findLastLog(bean.getId());

        if (lastLog == null)
        {
            // 驳回到初始
            type = "1";
        }
        else
        {
            int nextStatus = lastLog.getAfterStatus();

            // 驳回上一步到处是也是type=1
            if (TCPHelper.isTravelApplyInit(nextStatus))
            {
                type = "1";
            }
        }

        // 驳回到初始
        if ("1".equals(type))
        {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            // 清空预占的金额
            budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());

            int oldStatus = bean.getStatus();

            bean.setStatus(TcpConstanst.TCP_STATUS_REJECT);

            travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 这里驳回需要删除pay
            if (false && bean.getType() == TcpConstanst.TCP_APPLYTYPE_STOCK)
            {
                travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT);
        }
        // 驳回到上一步
        else
        {
            // 获得上一步的人
            String actorId = lastLog.getActorId();

            //int nextStatus = lastLog.getAfterStatus();
            int nextStatus = lastLog.getPreStatus();

            int newStatus = this.tcpFlowManager.saveApprove(user, actorId, bean, nextStatus,
                    TcpConstanst.TCP_POOL_COMMON);

            int oldStatus = bean.getStatus();

            bean.setStatus(newStatus);

            travelApplyDAO.updateStatus(bean.getId(), newStatus);

            if (TCPHelper.isTravelApplyInit(newStatus))
            {
                // 清空预占的金额
                budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT_PRE);
        }
        
        return true;
    }

    private void setIbMotivationFlag(User user, TravelApplyBean bean, boolean submit)throws MYException{
        if (bean.isMidOrMotivation()) {
            if (bean.isImportFlag()){
                //2015/4/12 中收激励设置对应SO标志位
                List<TcpIbBean> ibList = this.tcpIbDAO.queryEntityBeansByFK(bean.getId());
                if (!ListTools.isEmptyOrNull(ibList)){
                    _logger.info(bean.getId()+" with TcpIbBean list size:"+ibList.size());
                    for (TcpIbBean ib : ibList){
                        String outIds = ib.getFullId();
                        if (!StringTools.isNullOrNone(outIds)){
                            StringTokenizer  st = new  StringTokenizer(outIds,";");
                            while(st.hasMoreTokens()) {
                                String outId = st.nextToken();
                                OutBean out = this.outDAO.find(outId);
                                if (out!= null){
                                    if (bean.getIbType() == TcpConstanst.IB_TYPE){
                                        out.setIbFlag(1);
                                        out.setIbApplyId(bean.getId());
                                    } else if (bean.getIbType() == TcpConstanst.MOTIVATION_TYPE){
                                        out.setMotivationFlag(1);
                                        out.setMotivationApplyId(bean.getId());
                                    }  else if (bean.getIbType() == TcpConstanst.IB_TYPE2){
                                        out.setIbFlag2(1);
                                        out.setIbApplyId2(bean.getId());
                                    } else if (bean.getIbType() == TcpConstanst.MOTIVATION_TYPE2){
                                        out.setMotivationFlag2(1);
                                        out.setMotivationApplyId2(bean.getId());
                                    } else if (bean.getIbType() == TcpConstanst.PLATFORM_TYPE){
                                        out.setPlatformFlag(1);
                                        out.setPlatformApplyId(bean.getId());
                                    }

                                    _logger.info(out+" OutBean set IB flag**********");
                                    this.outDAO.updateEntityBean(out);
                                }
                            }
                        } else{
                            _logger.info("no out for TcpIbBean:"+ib.getId());
                        }
                    }
                } else{
                    _logger.error("***no TcpIbBean found for:"+bean.getId());
                    throw new MYException("中收激励明细表不能为空t_center_tcpib)!");
                }
            }

            //2015/7/19 只有中收申请才需要生成凭证
            //提交时才生成凭证
            // 中收在此产生凭证借：营业费用-中收 (5504-47)贷：预提费用 (2191)
            if (submit){
                if (bean.getIbType() == TcpConstanst.IB_TYPE
                        || bean.getIbType() == TcpConstanst.IB_TYPE2){
                    Collection<TcpPayListener> listenerMapValues = this.listenerMapValues();

                    for (TcpPayListener tcpPayListener : listenerMapValues)
                    {
                        _logger.info("************tcpPayListener************"+tcpPayListener);
                        tcpPayListener.onSubmitMidTravelApply(user, bean);
                    }
                }
            }
        }
    }
    private void resetIbMotivationFlag(TravelApplyBean bean){
        if (bean.isImportFlag()) {
            //中收激励重置SO单标志位
            List<TcpIbBean> ibList = this.tcpIbDAO.queryEntityBeansByFK(bean.getId());
            if (!ListTools.isEmptyOrNull(ibList)){
                _logger.info("TcpIbBean list size:"+ibList.size());
                for (TcpIbBean ib : ibList){
                    String outIds = ib.getFullId();
                    if (!StringTools.isNullOrNone(outIds)){
                        StringTokenizer  st = new  StringTokenizer(outIds,";");
                        while(st.hasMoreTokens()) {
                            String outId = st.nextToken();
                            OutBean out = this.outDAO.find(outId);
                            if (out!= null){
                                if (bean.getIbType() == TcpConstanst.IB_TYPE){
                                    out.setIbFlag(0);
                                    out.setIbApplyId("");
                                } else if (bean.getIbType() == TcpConstanst.MOTIVATION_TYPE){
                                    out.setMotivationFlag(0);
                                    out.setMotivationApplyId("");
                                } else if (bean.getIbType() == TcpConstanst.IB_TYPE2){
                                    out.setIbFlag2(0);
                                    out.setIbApplyId2("");
                                } else if (bean.getIbType() == TcpConstanst.MOTIVATION_TYPE2){
                                    out.setMotivationFlag2(0);
                                    out.setMotivationApplyId2("");
                                } else if (bean.getIbType() == TcpConstanst.PLATFORM_TYPE){
                                    out.setPlatformFlag(0);
                                    out.setPlatformApplyId("");
                                }

                                _logger.info(out+" OutBean reset IB flag before delete bean**********"+bean.getId());
                                this.outDAO.updateEntityBean(out);
                            }
                        }
                    } else{
                        _logger.info("no out for TcpIbBean:"+ib.getId());
                    }
                }
            }

            this.tcpIbDAO.deleteByApplyId(bean.getId());
            this.tcpVSOutDAO.deleteByApplyId(bean.getId());
        }
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectVocationAndWork(User user, TcpParamWrap param)
        throws MYException
    {
        String id = param.getId();
        String reason = param.getReason();
        String type = param.getType();

        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyVO bean = findVO(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // 权限
        checkAuth(user, id);

        // 获得当前的处理环节
        // TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

        // 获得上一步
        FlowLogBean lastLog = flowLogDAO.findLastLog(bean.getId());

        if (lastLog == null)
        {
            // 驳回到初始
            type = "1";
        }
        else
        {
            int nextStatus = lastLog.getAfterStatus();

            // 驳回上一步到处是也是type=1
            if (TCPHelper.isTravelApplyInit(nextStatus))
            {
                type = "1";
            }
        }

        // 驳回到初始
        if ("1".equals(type))
        {
            // 结束了需要清空
            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());

            // 清空预占的金额
            budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());

            int oldStatus = bean.getStatus();

            bean.setStatus(TcpConstanst.TCP_STATUS_REJECT);

            travelApplyDAO.updateStatus(bean.getId(), bean.getStatus());

            // 这里驳回需要删除pay
            if (false && bean.getType() == TcpConstanst.TCP_APPLYTYPE_STOCK)
            {
                travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT);
        }
        // 驳回到上一步
        else
        {
            // 获得上一步的人
            String actorId = lastLog.getActorId();

            //int nextStatus = lastLog.getAfterStatus();
            int nextStatus = lastLog.getPreStatus();

            this.tcpFlowManager.saveApprove(user, actorId, bean, nextStatus,
                TcpConstanst.TCP_POOL_COMMON);

            int oldStatus = bean.getStatus();
            int newStatus = 2;//部门经理
            
            if(oldStatus==6)
            {
            	newStatus = 4;
            }
            else if(oldStatus==4)
            {
            	newStatus = 2;
            }
            bean.setStatus(newStatus);

            travelApplyDAO.updateStatus(bean.getId(), newStatus);

            if (TCPHelper.isTravelApplyInit(newStatus))
            {
                // 清空预占的金额
                budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());
            }

            // 记录操作日志
            saveFlowLog(user, oldStatus, bean, reason, PublicConstant.OPRMODE_REJECT_PRE);
        }

        return true;
    }
    


//    /**
//     * saveApprove
//     *
//     * @param user
//     * @param processList
//     * @param bean
//     * @param nextStatus
//     * @param pool
//     * @return
//     * @throws MYException
//     */
//    private int saveApprove(User user, List<String> processList, TravelApplyVO bean,
//                            int nextStatus, int pool)
//        throws MYException
//    {
//        // 获得当前的处理环节
//        TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());
//        if (token == null || token.getSingeAll() == 0 )
//        {
//            // 清除之前的处理人
//            tcpApproveDAO.deleteEntityBeansByFK(bean.getId());
//        }
//        else
//        {
//            // 仅仅删除自己的
//            List<TcpApproveBean> approveList = tcpApproveDAO.queryEntityBeansByFK(bean.getId());
//            _logger.info("***approveList***"+approveList.size());
//            for (TcpApproveBean tcpApproveBean : approveList)
//            {
//                if (tcpApproveBean.getApproverId().equals(user.getStafferId()))
//                {
//                    tcpApproveDAO.deleteEntityBean(tcpApproveBean.getId());
//                }
//            }
//        }
//
//        List<TcpApproveBean> appList = tcpApproveDAO.queryEntityBeansByFK(bean.getId());
//        _logger.info("***appList***"+appList.size());
//        if (token == null || appList.size() == 0 || token.getSingeAll() == 0)
//        {
////            String nextProcessor = this.getNextProcessor(user.getStafferId(),token.getFlowKey(), token.getNextStatus());
//            TcpFlowBean nextToken = this.getNextProcessor(bean.getStafferId(), user.getStafferId(),token.getFlowKey(), token.getNextStatus());
//            int nextStatusIgnoreDuplicate = nextToken.getNextStatus();
//            String nextProcessor = nextToken.getNextProcessor();
//            if (!StringTools.isNullOrNone(nextProcessor) && !processList.contains(nextProcessor)){
//                processList.add(nextProcessor);
//            }
//            _logger.info(nextStatus+"***nextStatusIgnoreDuplicate***"+nextStatusIgnoreDuplicate+"***processList***"+processList.size()+"***nextProcessor***"+nextProcessor);
//            if (nextStatusIgnoreDuplicate!= 0){
//                nextStatus = nextStatusIgnoreDuplicate;
//            }
//            for (String processId : processList)
//            {
//                // 进入审批状态
//                TcpApproveBean approve = new TcpApproveBean();
//
//                approve.setId(commonDAO.getSquenceString20());
//                approve.setApplyerId(bean.getStafferId());
//                approve.setApplyId(bean.getId());
//                approve.setApproverId(processId);
//                approve.setFlowKey(bean.getFlowKey());
//                approve.setLogTime(TimeTools.now());
//                approve.setDepartmentId(bean.getDepartmentId());
//                approve.setName(bean.getName());
//                approve.setStatus(nextStatus);
////                approve.setStatus(nextStatusIgnoreDuplicate);
//                approve.setTotal(bean.getTotal());
//                approve.setCheckTotal(bean.getBorrowTotal());
//                approve.setType(bean.getType());
//                approve.setStype(bean.getStype());
//                approve.setPool(pool);
//                approve.setPayType(TcpConstanst.PAYTYPE_GPAY_BO);
//
//                tcpApproveDAO.saveEntityBean(approve);
//                _logger.info("***save TcpApproveBean***"+approve);
//
//            }
//
//            // 如果是共享的不发送邮件
//            if (pool == TcpConstanst.TCP_POOL_COMMON)
//            {
//                MailBean mail = new MailBean();
//
//                mail.setTitle(bean.getStafferName() + "的"
//                              + DefinedCommon.getValue("tcpType", bean.getType()) + "申请["
//                              + bean.getName() + "]等待您的处理.");
//
//                mail.setContent(mail.getContent());
//
//                mail.setSenderId(StafferConstant.SUPER_STAFFER);
//
//                mail.setReveiveIds(listToString(processList));
//
//                mail.setReveiveIds2(bean.getStafferId());
//
//                if(bean.getType()== TcpConstanst.VOCATION_WORK)
//                {
//                	mail.setHref(TcpConstanst.TCP_COMMIS_PROCESS_URL + bean.getId());
//                }
//                else
//                {
//                	mail.setHref(TcpConstanst.TCP_TRAVELAPPLY_PROCESS_URL + bean.getId());
//                }
//                // send mail
//                mailMangaer.addMailWithoutTransactional(UserHelper.getSystemUser(), mail);
//            }
//        }
//        else
//        {
//            // 会签
//            nextStatus = bean.getStatus();
//            _logger.info("***nextStatus***"+nextStatus);
//        }
//
//        return nextStatus;
//    }


    /**
     * 进入审批状态
     * 
     * @param processId
     * @param bean
     * @param pool
     * @throws MYException
     */
//    private int saveApprove(User user, String processId, TravelApplyVO bean, int nextStatus,
//                            int pool)
//        throws MYException
//    {
//        List<String> processList = new ArrayList();
//
//        processList.add(processId);
//
//        return saveApprove(user, processList, bean, nextStatus, pool);
//    }
//

    /**
     * 校验预算(且占用预算)
     * 
     * @param user
     * @param bean
     * @param type
     *            0:new add 1:update
     * @throws MYException
     */
    private void checkBudget(User user, TravelApplyVO bean, int type)
        throws MYException
    {
        // 不借款的不占用预算
        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO)
        {
            return;
        }

        // 不借款，但占预算
        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_NO_BUTHOLD)
        {
        	if (bean.getBorrowTotal() == 0)
        	{
        		return;
        	}
        }
        
        if (type == 1)
        {
            // 先删除之前的
            budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());
        }

        // 借款比
        double borrowRadio = 1.0d;

        if (bean.getTotal() != 0)
        {
            borrowRadio = (double)bean.getBorrowTotal() / (double)bean.getTotal();
        }

        List<TravelApplyItemVO> itemVOList = bean.getItemVOList();

        List<TcpShareVO> shareVOList = bean.getShareVOList();

        List<BudgetLogBean> logList = new ArrayList();

        long hasUse = 0L;

        int shareType = 0;

        for (Iterator iterator = shareVOList.iterator(); iterator.hasNext();)
        {
            TcpShareVO tcpShareVO = (TcpShareVO)iterator.next();

            // 每个申请项扣除的费用
            for (Iterator ite = itemVOList.iterator(); ite.hasNext();)
            {
                TravelApplyItemVO travelApplyItemVO = (TravelApplyItemVO)ite.next();

                if (travelApplyItemVO.getMoneys() == 0)
                {
                    continue;
                }

                // 预算鉴权()
                BudgetLogBean log = new BudgetLogBean();

                logList.add(log);

                log.setBudgetId(tcpShareVO.getBudgetId());

                BudgetItemBean item = budgetItemDAO.findByBudgetIdAndFeeItemId(tcpShareVO
                    .getBudgetId(), travelApplyItemVO.getFeeItemId());

                if (item == null)
                {

                    throw new MYException("预算[%s]里面缺少预算项[%s],请确认操作", tcpShareVO.getBudgetName(),
                        travelApplyItemVO.getFeeItemName());
                }

                log.setBudgetItemId(item.getId());

                log.setDepartmentId(tcpShareVO.getDepartmentId());

                log.setFeeItemId(travelApplyItemVO.getFeeItemId());

                log.setFromType(BudgetConstant.BUDGETLOG_FROMTYPE_TCP);

                log.setLocationId(user.getLocationId());

                log.setLog(DefinedCommon.getValue("tcpType", bean.getType()) + "申请[" + bean.getId()
                           + "]占用预算");

                log.setLogTime(TimeTools.now());

                log.setRefId(bean.getId());

                log.setRefSubId(travelApplyItemVO.getId());

                // 使用人
                log.setStafferId(bean.getBorrowStafferId());

                // 预占
                log.setUserType(BudgetConstant.BUDGETLOG_USERTYPE_PRE);

                long useMoney = 0;

                if (tcpShareVO.getRatio() != 0)
                {
                    // 这里肯定有误差的
                    useMoney = Math.round( (tcpShareVO.getRatio() / 100.0) * borrowRadio
                                          * travelApplyItemVO.getMoneys());
                }
                else
                {
                    // 使用实际的金额
                    useMoney = Math.round( (getShareratio(shareVOList, tcpShareVO) / 100.0d)
                                          * borrowRadio * travelApplyItemVO.getMoneys());

                    shareType = 1;
                }

                log.setMonery(useMoney);

                hasUse += useMoney;
            }
        }

        // 实际的二次调整
        if (shareType == 1)
        {
            resetShareratio(shareVOList, bean.getBorrowTotal());

            // 这里的实际使用的误差比较复杂(每个分担必须等于)/存在稽核后预算变小,但是实际的分担较大
            for (TcpShareVO tcpShareVO : shareVOList)
            {
                long btotal = 0;

                for (BudgetLogBean budgetLogBean : logList)
                {
                    if (budgetLogBean.getBudgetId().equals(tcpShareVO.getBudgetId()))
                    {
                        btotal += budgetLogBean.getMonery();
                    }
                }

                // 金额有差异
                if (btotal != tcpShareVO.getRealMonery())
                {
                    // 多余的金额(可能是负数哦)
                    long cache = tcpShareVO.getRealMonery() - btotal;

                    for (BudgetLogBean budgetLogBean : logList)
                    {
                        if (budgetLogBean.getBudgetId().equals(tcpShareVO.getBudgetId()))
                        {
                            if (budgetLogBean.getMonery() + cache >= 0)
                            {
                                budgetLogBean.setMonery(budgetLogBean.getMonery() + cache);

                                cache = 0;

                                break;
                            }
                            else
                            {
                                // 强制为0
                                budgetLogBean.setMonery(0);

                                // 顺差减少
                                cache = budgetLogBean.getMonery() + cache;
                            }
                        }
                    }

                    if (cache > 0)
                    {
                        // 这里说明有问题啊
                        badLog.equals("申请借款[" + bean.getId() + "]在分担上存在越界:" + cache + ".预算为:"
                                      + tcpShareVO.getBudgetName());
                    }
                }
            }
        }

        // 消除最终的误差
        long lasttotla = 0;

        for (BudgetLogBean budgetLogBean : logList)
        {
            lasttotla += budgetLogBean.getMonery();
        }

        long chae = lasttotla - bean.getBorrowTotal();

        if (chae != 0)
        {
            for (BudgetLogBean budgetLogBean : logList)
            {
                if (budgetLogBean.getMonery() > chae)
                {
                    budgetLogBean.setMonery(budgetLogBean.getMonery() - chae);

                    break;
                }
            }
        }

        // 进入使用日志,如果超出预算会抛出异常的
        budgetManager.addBudgetLogListWithoutTransactional(user, bean.getId(), logList);
    }

    private int getShareratio(List<TcpShareVO> shareVOList, TcpShareVO vo)
    {
        double total = 0;

        for (TcpShareVO tcpShareVO : shareVOList)
        {
            total += tcpShareVO.getRealMonery();
        }

        int ratio = (int) (vo.getRealMonery() / total * 100);

        if (ratio == 0)
        {
            ratio = 1;
        }

        return ratio;
    }

    /**
     * 修改后金额自动重新设置分担
     * 
     * @param shareVOList
     * @param newBorrowal
     */
    private void resetShareratio(List<TcpShareVO> shareVOList, long newBorrowal)
    {
        double total = 0;

        for (TcpShareVO tcpShareVO : shareVOList)
        {
            total += tcpShareVO.getRealMonery();
        }

        if (total == newBorrowal)
        {
            return;
        }

        // 比例啊
        double ratio = newBorrowal / (total + 0.0d);

        long newtotal = 0;

        for (TcpShareVO tcpShareVO : shareVOList)
        {
            tcpShareVO.setRealMonery(Math.round(tcpShareVO.getRealMonery() * ratio));

            newtotal += tcpShareVO.getRealMonery();
        }

        // 修复分担
        if (newtotal != newBorrowal)
        {
            // 多余的需要加入
            long cache = newBorrowal - newtotal;

            for (TcpShareVO tcpShareVO : shareVOList)
            {
                if (tcpShareVO.getRealMonery() + cache >= 0)
                {
                    tcpShareVO.setRealMonery(tcpShareVO.getRealMonery() + cache);

                    break;
                }
            }
        }

    }

    /**
     * checkApply
     * 
     * @param bean
     * @throws MYException
     */
    private void checkApply(TravelApplyBean bean)
        throws MYException
    {
        //#495 提交人直属上级
        if (bean.getFlowKey().equals(TcpFlowConstant.WORKFLOW_2018)){
            StafferBean stafferBean = this.stafferDAO.find(bean.getStafferId());
            if (StringTools.isNullOrNone(stafferBean.getSuperiorLeader())){
                throw new MYException("提交人直属上级不能为空!");
            }
        }
        
        // 验证
        if (bean.getBorrowTotal() > bean.getTotal())
        {
            throw new MYException("借款金额[%f]大于总费用[%f]", bean.getBorrowTotal() / 100.0d, bean
                .getTotal() / 100.0d);
        }

        int ratioTotal = 0;

        int shareTotal = 0;
        if(null != bean.getShareList() && bean.getShareList().size() > 0)
        {
        	
	        for (TcpShareBean tcpShareBean : bean.getShareList())
	        {
	            tcpShareBean.setId(commonDAO.getSquenceString20());
	            tcpShareBean.setRefId(bean.getId());
	
	            ratioTotal += tcpShareBean.getRatio();
	            shareTotal += tcpShareBean.getRealMonery();

	            //#495
                if (bean.getFlowKey().equals(TcpFlowConstant.WORKFLOW_2018)){
                    StafferBean staffer = this.stafferDAO.find(tcpShareBean.getBearId());
                    if (StringTools.isNullOrNone(staffer.getSuperiorLeader())){
                        throw new MYException("承担人直属上级不能为空:"+staffer.getName());
                    }
                }
	        }
        }
        // 要么全是0,就是根据金额去分担(支持非比例分担)
        if (ratioTotal != 100 && ratioTotal != 0)
        {
            throw new MYException("分担比例之和必须是100");
        }

        if (ratioTotal > 0)
        {
        	if(null != bean.getShareList() && bean.getShareList().size() > 0)
        	{
        		
	            for (TcpShareBean tcpShareBean : bean.getShareList())
	            {
	                if (tcpShareBean.getRatio() <= 0)
	                {
	                    throw new MYException("分担比例不能小于0,且必须是整数");
	                }
	            }
        	}
        }
        //#571
//        else
//        {
//        	if(null != bean.getShareList() && bean.getShareList().size() > 0)
//        	{
//	            for (TcpShareBean tcpShareBean : bean.getShareList())
//	            {
//	                if (tcpShareBean.getRealMonery() <= 0)
//	                {
//	                    throw new MYException("分担金额比例不能小于0");
//	                }
//	            }
//        	}
//        }

        // 下面的申请暂时不实现分担,无法实现
//        if (ratioTotal == 0 && bean.getBorrowTotal() != shareTotal)
//        {
//            throw new MYException("费用申请借款的总金额[%.2f]必须和分担的金额[%.2f]一致", MathTools.longToDouble2(bean
//                .getTotal()), MathTools.longToDouble2(shareTotal));
//        }
    }

    /**
     * saveApply
     * 
     * @param user
     * @param bean
     */
    public void saveApply(User user, TravelApplyBean bean)
    {
        TcpApplyBean apply = new TcpApplyBean();

        apply.setId(bean.getId());
        apply.setName(bean.getId());
        apply.setFlowKey(bean.getFlowKey());
        apply.setApplyId(bean.getId());
        apply.setApplyId(user.getStafferId());
        apply.setDepartmentId(bean.getDepartmentId());
        apply.setType(bean.getType());
        apply.setStype(bean.getStype());
        apply.setStatus(TcpConstanst.TCP_STATUS_INIT);
        apply.setTotal(bean.getTotal());
        apply.setLogTime(bean.getLogTime());
        apply.setDescription(bean.getDescription());
        apply.setPayType(TcpConstanst.PAYTYPE_GPAY_BO);

        tcpApplyDAO.saveEntityBean(apply);
    }
    

    /**
     * saveFlowLog/PublicConstant.OPRMODE_PASS
     * 
     * @param user
     * @param preStatus
     * @param apply
     * @param reason
     * @param oprMode
     */
    private void saveFlowLog(User user, int preStatus, TravelApplyBean apply, String reason,
                             int oprMode)
    {
        FlowLogBean log = new FlowLogBean();

        log.setFullId(apply.getId());

        log.setActor(user.getStafferName());

        log.setActorId(user.getStafferId());

        log.setOprMode(oprMode);

        log.setDescription(reason);

        log.setLogTime(TimeTools.now());

        log.setPreStatus(preStatus);

        log.setAfterStatus(apply.getStatus());

        flowLogDAO.saveEntityBean(log);

        // 先删除
        ConditionParse condition = new ConditionParse();
        condition.addWhereStr();
        condition.addCondition("stafferId", "=", user.getStafferId());
        condition.addCondition("refId", "=", apply.getId());
        tcpHandleHisDAO.deleteEntityBeansByCondition(condition);

        // 记录处理历史
        TcpHandleHisBean his = new TcpHandleHisBean();
        his.setId(commonDAO.getSquenceString20());
        his.setLogTime(TimeTools.now());
        his.setRefId(apply.getId());
        his.setStafferId(user.getStafferId());
        his.setApplyId(apply.getStafferId());
        his.setType(apply.getType());
        his.setName(apply.getName());

        tcpHandleHisDAO.saveEntityBean(his);
    }
    

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.tcp.manager.TravelApplyManager#updateTravelApplyBean(com.center.china.osgi.publics.User,
     *      com.china.center.oa.tcp.bean.TravelApplyBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateTravelApplyBean(User user, TravelApplyBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        TravelApplyBean old = travelApplyDAO.find(bean.getId());

        if (old == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("只能修改自己的申请");
        }

        //2016/2/5 #170
        bean.setImportFlag(old.isImportFlag());
        bean.setIbType(old.getIbType());

//        bean.setStatus(TcpConstanst.TCP_STATUS_INIT);

        // 借款人就是自己
        if (StringTools.isNullOrNone(bean.getBorrowStafferId()))
        {
            bean.setBorrowStafferId(user.getStafferId());
        }

        // 获取flowKey
//        if (!bean.getFlowKey().equals(TcpFlowConstant.EXTRA_WORK_AND_LEAVE_CEO))
//        	TCPHelper.setFlowKey(bean);

        _logger.info(bean.getId() + " change to EXTRA_WORK_AND_LEAVE_CEO from " + old.getFlowKey());
//        bean.setFlowKey(TcpFlowConstant.EXTRA_WORK_AND_LEAVE_CEO);
        //2015/3/28 不能设置为TcpFlowConstant.EXTRA_WORK_AND_LEAVE_CEO，退回重新提交后都变成请假流程了
        if (StringTools.isNullOrNone(old.getFlowKey())){
            bean.setFlowKey(TcpFlowConstant.EXTRA_WORK_AND_LEAVE_CEO);
        } else{
            bean.setFlowKey(old.getFlowKey());
        }

        // 先清理
        travelApplyItemDAO.deleteEntityBeansByFK(bean.getId());
        travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
        tcpShareDAO.deleteEntityBeansByFK(bean.getId());
        attachmentDAO.deleteEntityBeansByFK(bean.getId());
        tcpIbDAO.deleteEntityBeansByFK(bean.getId());
        tcpVSOutDAO.deleteEntityBeansByFK(bean.getId());

        // TravelApplyItemBean
        List<TravelApplyItemBean> itemList = bean.getItemList();
        if(null != itemList && itemList.size() > 0)
        {
        	
	        for (TravelApplyItemBean travelApplyItemBean : itemList)
	        {
	            travelApplyItemBean.setId(commonDAO.getSquenceString20());
	            travelApplyItemBean.setParentId(bean.getId());
	        }
	
	        travelApplyItemDAO.saveAllEntityBeans(itemList);
        }

        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
        {
            long temp = 0L;

            List<TravelApplyPayBean> payList = bean.getPayList();
            if(null != payList && payList.size() > 0)
            {
	            for (TravelApplyPayBean travelApplyPayBean : payList)
	            {
	                travelApplyPayBean.setId(commonDAO.getSquenceString20());
	                travelApplyPayBean.setParentId(bean.getId());
	
	                temp += travelApplyPayBean.getMoneys();
	            }
	
	            travelApplyPayDAO.saveAllEntityBeans(payList);
            }
            bean.setBorrowTotal(temp);
        }
        else
        {
            bean.setBorrowTotal(0);
        }

        checkApply(bean);

        List<TcpShareBean> shareList = bean.getShareList();
        if(null != shareList && shareList.size() > 0)
        {
	        for (TcpShareBean tcpShareBean : shareList)
	        {
	            tcpShareBean.setId(commonDAO.getSquenceString20());
	            tcpShareBean.setRefId(bean.getId());
	        }
	
	        tcpShareDAO.saveAllEntityBeans(shareList);
        }
        
        List<AttachmentBean> attachmentList = bean.getAttachmentList();
        if(null != attachmentList && attachmentList.size() > 0)
        {
	        for (AttachmentBean attachmentBean : attachmentList)
	        {
	            attachmentBean.setId(commonDAO.getSquenceString20());
	            attachmentBean.setRefId(bean.getId());
	        }
	
	        attachmentDAO.saveAllEntityBeans(attachmentList);
        }
        
        travelApplyDAO.updateEntityBean(bean);

        if (bean.isMidOrMotivation()){
            List<TcpIbBean> ibList = bean.getIbList();
            if(!ListTools.isEmptyOrNull(ibList))
            {
                for (TcpIbBean ib : ibList)
                {
                    ib.setId(commonDAO.getSquenceString20());
                    ib.setRefId(bean.getId());
                    ib.setLogTime(TimeTools.now());
                }
                this.tcpIbDAO.saveAllEntityBeans(ibList);
            } else{
                throw new MYException("T_CENTER_TCPIB数据生成有误!");
            }

            // #489
            List<TcpVSOutBean> tcpVSOutBeans = bean.getTcpVSOutBeanList();

            if(!ListTools.isEmptyOrNull(tcpVSOutBeans))
            {
                for (TcpVSOutBean ib : tcpVSOutBeans)
                {
                    ib.setId(commonDAO.getSquenceString20());
                    ib.setRefId(bean.getId());
                    ib.setLogTime(TimeTools.now());
                }
                try {
                    this.tcpVSOutDAO.saveAllEntityBeans(tcpVSOutBeans);
                }catch (Exception e){
                    _logger.error(e);
                    throw new MYException("中收激励重复申请,请检查表T_CENTER_VS_TCPOUT");
                }
            }
        }


        saveFlowLog(user, old.getStatus(), bean, "自动修改保存", PublicConstant.OPRMODE_SAVE);

        return true;
    }
    
    
    /**
     * 增加附件
     * @param user
     * @param bean
     * @return
     * @throws MYException
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean updateAttachmentList(User user, TravelApplyBean bean)
    {
    	
    	List<AttachmentBean> attachmentList = bean.getAttachmentList();
    	
    	attachmentDAO.deleteByEntityBeans(attachmentList);
    	
//    	String rootPath = ConfigLoader.getProperty("tcpAttachmentPath");
//    	
//    	for (AttachmentBean attachmentBean : attachmentList)
//        {
//            FileTools.deleteFile(rootPath + attachmentBean.getPath());
//        }

        for (AttachmentBean attachmentBean : attachmentList)
        {
            attachmentBean.setId(commonDAO.getSquenceString20());
            attachmentBean.setRefId(bean.getId());
        }
        attachmentDAO.saveAllEntityBeans(attachmentList);

        boolean b = travelApplyDAO.updateEntityBean(bean);
    	return b;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteTravelApplyBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        TravelApplyBean bean = travelApplyDAO.find(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !TCPHelper.canTravelApplyDelete(bean))
        {
            throw new MYException("不是初始态和驳回态,不能删除");
        }

        // 删除
        travelApplyItemDAO.deleteEntityBeansByFK(bean.getId());
        travelApplyPayDAO.deleteEntityBeansByFK(bean.getId());
        tcpShareDAO.deleteEntityBeansByFK(bean.getId());
        flowLogDAO.deleteEntityBeansByFK(bean.getId());
        // 删除预算使用,一般都是空
        budgetManager.deleteBudgetLogListWithoutTransactional(user, bean.getId());

        String rootPath = ConfigLoader.getProperty("tcpAttachmentPath");

        List<AttachmentBean> attachmenList = attachmentDAO.queryEntityBeansByFK(id);

        for (AttachmentBean attachmentBean : attachmenList)
        {
            FileTools.deleteFile(rootPath + attachmentBean.getPath());
        }

        attachmentDAO.deleteEntityBeansByFK(bean.getId());
        this.resetIbMotivationFlag(bean);
//        if (bean.isImportFlag()) {
//            //2015/5/19 中收激励重置SO单标志位
//            List<TcpIbBean> ibList = this.tcpIbDAO.queryEntityBeansByFK(bean.getId());
//            if (!ListTools.isEmptyOrNull(ibList)){
//                _logger.info("TcpIbBean list size:"+ibList.size());
//                for (TcpIbBean ib : ibList){
//                    String outIds = ib.getFullId();
//                    if (!StringTools.isNullOrNone(outIds)){
//                        StringTokenizer  st = new  StringTokenizer(outIds,";");
//                        while(st.hasMoreTokens()) {
//                            String outId = st.nextToken();
//                            OutBean out = this.outDAO.find(outId);
//                            if (out!= null){
//                                if (bean.getIbType() == TcpConstanst.IB_TYPE){
//                                    out.setIbFlag(0);
//                                    out.setIbApplyId("");
//                                } else if (bean.getIbType() == TcpConstanst.MOTIVATION_TYPE){
//                                    out.setMotivationFlag(0);
//                                    out.setMotivationApplyId("");
//                                }
//
//                                _logger.info(out+" OutBean reset IB flag before delete bean**********"+bean.getId());
//                                this.outDAO.updateEntityBean(out);
//                            }
//                        }
//                    } else{
//                        _logger.info("no out for TcpIbBean:"+ib.getId());
//                    }
//                }
//
//            }
//
//        }

        travelApplyDAO.deleteEntityBean(id);

        tcpApplyDAO.deleteEntityBean(id);

        operationLog.info(user + " delete TravelApplyBean:" + bean);

        return true;
    }
    

    public TravelApplyVO findVO(String id)
    {
        TravelApplyVO bean = travelApplyDAO.findVO(id);

        if (bean == null)
        {
            return bean;
        }

        // 部门
        PrincipalshipBean depa = orgManager.findPrincipalshipById(bean.getDepartmentId());

        if (depa != null)
        {
            bean.setDepartmentName(depa.getFullName());
        }

        List<TravelApplyItemVO> itemVOList = travelApplyItemDAO.queryEntityVOsByFK(id);

        bean.setItemVOList(itemVOList);

        for (TravelApplyItemVO travelApplyItemVO : itemVOList)
        {
            travelApplyItemVO.setShowMoneys(TCPHelper
                .formatNum2(travelApplyItemVO.getMoneys() / 100.0d));
        }

        List<AttachmentBean> attachmentList = attachmentDAO.queryEntityVOsByFK(id);

        bean.setAttachmentList(attachmentList);

        List<TravelApplyPayBean> payList = travelApplyPayDAO.queryEntityVOsByFK(id);

        bean.setPayList(payList);

        List<TcpShareVO> shareList = tcpShareDAO.queryEntityVOsByFK(id);
        _logger.info(id+"***shareList***"+shareList.size());
        if (ListTools.isEmptyOrNull(shareList)){
            List<TcpShareBean> tcpShareBeans = tcpShareDAO.queryEntityBeansByFK(id);
            shareList = new ArrayList<>();
            for(TcpShareBean tcpShareBean : tcpShareBeans){
                TcpShareVO vo = new TcpShareVO();
                vo.setId(tcpShareBean.getId());
                vo.setRefId(tcpShareBean.getRefId());
                vo.setRatio(tcpShareBean.getRatio());
                vo.setRealMonery(tcpShareBean.getRealMonery());
                vo.setBearId(tcpShareBean.getBearId());
                StafferBean stafferBean = this.stafferDAO.find(tcpShareBean.getBearId());
                if(stafferBean!= null){
                    vo.setBearName(stafferBean.getName());
                }
                shareList.add(vo);
            }
        }
        for (TcpShareVO tcpShareVO : shareList)
        {
            if (!StringTools.isNullOrNone(tcpShareVO.getDepartmentId())){
                PrincipalshipBean dep = orgManager.findPrincipalshipById(tcpShareVO.getDepartmentId());

                if (dep != null)
                {
                    tcpShareVO.setDepartmentName(dep.getFullName());
                }
            }


            if (tcpShareVO.getRatio() == 0)
            {
                tcpShareVO
                        .setShowRealMonery(MathTools.longToDoubleStr2(tcpShareVO.getRealMonery()));
            }
            else
            {
                tcpShareVO.setShowRealMonery(String.valueOf(tcpShareVO.getRatio()));
            }
        }

        bean.setShareVOList(shareList);

        TCPHelper.chageVO(bean);

        // 当前处理人
        List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(bean.getId());

        for (TcpApproveVO tcpApproveVO : approveList)
        {
            bean.setProcesser(bean.getProcesser() + tcpApproveVO.getApproverName() + ';');
        }

        // 流程描述
        List<TcpFlowBean> flowList = tcpFlowDAO.queryEntityBeansByFK(bean.getFlowKey());

        Collections.sort(flowList, new Comparator<TcpFlowBean>() {
            public int compare(TcpFlowBean o1, TcpFlowBean o2) {
                return Integer.parseInt(o1.getId()) - Integer.parseInt(o2.getId());
            }
        });

        StringBuffer sb = new StringBuffer();

        for (TcpFlowBean tcpFlowBean : flowList)
        {
            if (bean.getStatus() == tcpFlowBean.getCurrentStatus())
            {
                sb.append("<font color=red>").append(
                    DefinedCommon.getValue("tcpStatus", tcpFlowBean.getCurrentStatus())).append(
                    "</font>").append("->");
            }
            else
            {
                sb
                    .append(DefinedCommon.getValue("tcpStatus", tcpFlowBean.getCurrentStatus()))
                    .append("->");
            }
        }

        if (bean.getStatus() == TcpConstanst.TCP_STATUS_END)
        {
            sb.append("<font color=red>").append(
                DefinedCommon.getValue("tcpStatus", TcpConstanst.TCP_STATUS_END)).append("</font>");
        }
        else
        {
            sb.append(DefinedCommon.getValue("tcpStatus", TcpConstanst.TCP_STATUS_END));
        }

        bean.setFlowDescription(sb.toString());

        return bean;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void ibReportJob() throws MYException {
        //To change body of implemented methods use File | Settings | File Templates.
        //每天夜里跑JOB统计从15.4.1日到当天的未申请的中收和激励数据
        //所有中收激励统计均为“已出库”、“已发货”状态的订单，退库订单的状态均为“待核对”状态
        //销售退库订单的对应中收、激励金额为负数也列入统计及明细
        _logger.info("*****ibReportJob running******");
        //#417 先把数据清理掉
        this.tcpIbReportItemDAO.deleteAllEntityBean();
        this.tcpIbReportDAO.deleteAllEntityBean();

        //根据customerId分组
        Map<String, List<OutBean>>  customerToOutMap = new HashMap<String,List<OutBean>>();

        final String beginDate = "2015-04-01";
        //所有中收激励统计均为销售出库订单
        ConditionParse con = new ConditionParse();
        con.addWhereStr();
        //销售单
        con.addCondition("OutBean.type","=",  OutConstant.OUT_TYPE_OUTBILL);
        //销售出库
        con.addCondition("OutBean.outType","=", OutConstant.OUTTYPE_OUT_COMMON);
        //#148
        con.addCondition(" and OutBean.status !=2 ");
        con.addCondition("outTime", ">", beginDate);
        con.addCondition(" and (OutBean.ibFlag =0 or OutBean.motivationFlag=0 or OutBean.ibFlag2 =0 or OutBean.motivationFlag2=0 or OutBean.platformFlag=0)");
        List<OutBean> outList = this.outDAO.queryEntityBeansByCondition(con);
        if (!ListTools.isEmptyOrNull(outList)){
            _logger.info("ibReport outList1 size:"+outList.size());
             for (OutBean out: outList){
                 String customerId = out.getCustomerId();
                 if (customerToOutMap.containsKey(customerId)){
                     List<OutBean> outVOs = customerToOutMap.get(customerId);
                     outVOs.add(out);
                 }else{
                     List<OutBean> outVOList = new ArrayList<OutBean>();
                     outVOList.add(out);
                     customerToOutMap.put(customerId, outVOList);
                 }
             }
        }

        //退库订单的状态均为“待核对”状态
        ConditionParse con1 = new ConditionParse();
        //入库单
        con1.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);
        //add begin time
        //销售退库
        con1.addCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_OUTBACK);
        con1.addCondition("outTime",">",beginDate);
        con.addCondition(" and (OutBean.ibFlag =0 or OutBean.motivationFlag=0)");
        //#315 “待核对”和结束状态
        con1.addCondition(" and OutBean.status in(3,4)");
        List<OutBean> outList2 = this.outDAO.queryEntityBeansByCondition(con1);
        if (!ListTools.isEmptyOrNull(outList2)){
            _logger.info("ibReport outList2 size:"+outList2.size());
            for (OutBean out: outList2){
                // #361 XT单的原单如果是ZS单忽略掉
                String refOutId = out.getRefOutFullId();
                if (!StringTools.isNullOrNone(refOutId) && refOutId.startsWith("ZS")){
                    continue;
                }
                String customerId = out.getCustomerId();
                if (customerToOutMap.containsKey(customerId)){
                    List<OutBean> outVOs = customerToOutMap.get(customerId);
                    outVOs.add(out);
                }else{
                    List<OutBean> outVOList = new ArrayList<OutBean>();
                    outVOList.add(out);
                    customerToOutMap.put(customerId, outVOList);
                }
            }
        }

        final double zero = 0.000001;
        for (String customerId : customerToOutMap.keySet()){
            List<TcpIbReportItemBean> itemList = new ArrayList<TcpIbReportItemBean>();

            TcpIbReportBean ibReport = new TcpIbReportBean();
            ibReport.setId(commonDAO.getSquenceString20());
            ibReport.setCustomerId(customerId);
            List<OutBean> outVOs = customerToOutMap.get(customerId);
            if (!ListTools.isEmptyOrNull(outVOs)){
                ibReport.setCustomerName(outVOs.get(0).getCustomerName());
                double ibTotal = 0.0d;
                double moTotal = 0.0d;
                double ibTotal2 = 0.0d;
                double moTotal2 = 0.0d;
                double platformTotal = 0.0d;
                for (OutBean out: outVOs){
                    List<BaseBean> baseList = this.baseDAO.queryEntityBeansByFK(out.getFullId());
                    if (!ListTools.isEmptyOrNull(baseList)){
                        for (BaseBean base : baseList){
                            TcpIbReportItemBean item = new TcpIbReportItemBean();
                            item.setCustomerName(out.getCustomerName());
                            item.setFullId(out.getFullId());
                            item.setProductId(base.getProductId());
                            item.setProductName(base.getProductName());
                            item.setAmount(base.getAmount());
                            item.setPrice(base.getPrice());
                            //销售退库
                            if (out.getType() == OutConstant.OUT_TYPE_INBILL && out.getOutType() == OutConstant.OUTTYPE_IN_OUTBACK) {
//                                item.setIbMoney(this.roundDouble(-base.getAmount()*base.getIbMoney()));
//                                ibTotal -= base.getAmount()*base.getIbMoney();
//                                item.setMotivationMoney(this.roundDouble(-base.getAmount()*base.getMotivationMoney()));
//                                moTotal -= base.getAmount()*base.getMotivationMoney();

//                                //已经结算过的中收需要退款
                                if (out.getIbFlag() == 0){
                                    item.setIbMoney(this.roundDouble(-base.getAmount()*base.getIbMoney()));
                                    ibTotal -= base.getAmount()*base.getIbMoney();
                                }

                                if (out.getIbFlag2() == 0){
                                    item.setIbMoney2(this.roundDouble(-base.getAmount()*base.getIbMoney2()));
                                    ibTotal2 -= base.getAmount()*base.getIbMoney2();
                                }
//
//                                //已经结算过的激励需要退款
                                if (out.getMotivationFlag() == 0){
                                    item.setMotivationMoney(this.roundDouble(-base.getAmount()*base.getMotivationMoney()));
                                    moTotal -= base.getAmount()*base.getMotivationMoney();
                                }

                                if (out.getMotivationFlag2() == 0){
                                    item.setMotivationMoney2(this.roundDouble(-base.getAmount()*base.getMotivationMoney2()));
                                    moTotal2 -= base.getAmount()*base.getMotivationMoney2();
                                }

                                if(out.getPlatformFlag() == 0){
                                    item.setPlatformFee(this.roundDouble(-base.getAmount()*base.getPlatformFee()));
                                    platformTotal -= base.getAmount()*base.getPlatformFee();
                                }
                            } else{
                                //出库
                                if (out.getIbFlag() == 0){
                                    item.setIbMoney(this.roundDouble(base.getAmount()*base.getIbMoney()));
                                    ibTotal += base.getAmount()*base.getIbMoney();
                                }

                                if (out.getIbFlag2() == 0){
                                    item.setIbMoney2(this.roundDouble(base.getAmount()*base.getIbMoney2()));
                                    ibTotal2 += base.getAmount()*base.getIbMoney2();
                                }

                                if (out.getMotivationFlag() ==0){
                                    item.setMotivationMoney(this.roundDouble(base.getAmount()*base.getMotivationMoney()));
                                    moTotal += base.getAmount()*base.getMotivationMoney();
                                }

                                if (out.getMotivationFlag2() ==0){
                                    item.setMotivationMoney2(this.roundDouble(base.getAmount()*base.getMotivationMoney2()));
                                    moTotal2 += base.getAmount()*base.getMotivationMoney2();
                                }

                                if(out.getPlatformFlag() ==0){
                                    item.setPlatformFee(this.roundDouble(base.getAmount()*base.getPlatformFee()));
                                    platformTotal += base.getAmount()*base.getPlatformFee();
                                }
                            }

                            if (Math.abs(item.getIbMoney()) > zero || Math.abs(item.getMotivationMoney())> zero
                                    || Math.abs(item.getIbMoney2()) > zero || Math.abs(item.getMotivationMoney2())> zero
                                    || Math.abs(item.getPlatformFee()) > zero){
//                                _logger.info("****create TcpIbReportItemBean**********"+item);
                                itemList.add(item);
                            }
                        }
                    } else{
                        _logger.error("****no BaseBean list found:"+out.getId());
                    }
                }
                ibReport.setIbMoneyTotal(this.roundDouble(ibTotal));
                ibReport.setMotivationMoneyTotal(this.roundDouble((moTotal)));
                ibReport.setIbMoneyTotal2(this.roundDouble(ibTotal2));
                ibReport.setMotivationMoneyTotal2(this.roundDouble((moTotal2)));
                ibReport.setPlatformFeeTotal(this.roundDouble(platformTotal));
            }

            if (ListTools.isEmptyOrNull(itemList)){
                _logger.error("***No item created***"+ibReport);
            } else {
                this.tcpIbReportDAO.saveEntityBean(ibReport);
                _logger.info("****save ibReport**********"+ibReport);

                for (TcpIbReportItemBean item : itemList) {
                    item.setId(commonDAO.getSquenceString20());
                    item.setRefId(ibReport.getId());
                    _logger.info("****create TcpIbReportItemBean***"+item);
                }
                this.tcpIbReportItemDAO.saveAllEntityBeans(itemList);
            }

        }
        _logger.info("************finish ibReport job*************");
    }

    /**
     * 2015/9/18 保留2位小数四舍五入
     * @param value
     * @return
     */
    private double roundDouble(double value){
        BigDecimal bd = new BigDecimal(value);
        double v1 = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return v1;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void ibReportJobMonthly() throws MYException {
        //To change body of implemented methods use File | Settings | File Templates.
        String ibReportMonthly = ConfigLoader.getProperty("ibReportMonthly");
        String filenName = ibReportMonthly+"/IB_Monthly_Report_" + TimeTools.now("MMddHHmmss") + ".csv";

        _logger.info("ibReportJobMonthly export ibReport now***"+filenName);

        ConditionParse con = new ConditionParse();
        con.addWhereStr();

        List<TcpIbReportItemBean> ibReportList = this.tcpIbReportItemDAO.queryEntityBeansByCondition(con);

        if (ListTools.isEmptyOrNull(ibReportList))
        {
            return ;
        }

        WriteFile write = null;
        OutputStream out = null;

        try
        {
            out = new FileOutputStream(filenName);
            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            WriteFileBuffer line = new WriteFileBuffer(write);
            line.writeColumn("客户名");
            line.writeColumn("订单号");
            line.writeColumn("商品名");
            line.writeColumn("商品数量");
            line.writeColumn("中收金额");
            line.writeColumn("激励金额");
            line.writeColumn("订单状态");
            line.writeColumn("申请人");
            line.writeColumn("银行销售日期");

            line.writeLine();

            for (Iterator<TcpIbReportItemBean> iter = ibReportList.iterator(); iter.hasNext();)
            {
                TcpIbReportItemBean ib = iter.next();

                line.writeColumn(ib.getCustomerName());
                line.writeColumn(ib.getFullId());
                line.writeColumn(ib.getProductName());
                line.writeColumn(ib.getAmount());
                line.writeColumn(ib.getIbMoney());
                line.writeColumn(ib.getMotivationMoney());

                //2015/7/11导出申请人和银行销售日期
                OutBean outBean = this.outDAO.find(ib.getFullId());
                if (outBean!= null){
                    line.writeColumn(OutHelper.getOutStatus(outBean));
                    line.writeColumn(outBean.getStafferName());
                    line.writeColumn(outBean.getPodate());
                }
                line.writeLine();
            }

        }
        catch (Exception e)
        {
            _logger.error(e, e);
            return;
        }
        finally
        {
            if (write != null)
            {
                try
                {
                    write.close();
                }
                catch (Exception e1)
                {
                }
            }
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException e1)
                {
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void batchUpdateIbMoney(User user, List<TcpIbBean> list) throws MYException {
        _logger.info("***batchUpdateIbMoney***"+list);
        for (TcpIbBean each: list){
            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addWhereStr();
            String outId = each.getFullId();
            String productId = each.getProductId();
            OutBean out = this.outDAO.find(outId);
            if (out == null){
                _logger.info("数据错误，原单不存在:"+outId);
                throw new MYException("数据错误，原单不存在:"+outId);
            } else {
                if (out.getIbFlag() == 1 && each.getType() == TcpConstanst.IB_TYPE){
                    _logger.info("已申请中收,不允许再修改中收金额:"+outId);
                    throw new MYException("已申请中收,不允许再修改中收金额:"+outId);
                } else if (out.getIbFlag2() == 1 && each.getType() == TcpConstanst.IB_TYPE2){
                    _logger.info("已申请中收2,不允许再修改中收2金额:"+outId);
                    throw new MYException("已申请中收2,不允许再修改中收2金额:"+outId);
                } else if (out.getMotivationFlag() == 1 && each.getType() == TcpConstanst.MOTIVATION_TYPE){
                    _logger.info("已申请激励,不允许再修改激励金额:"+outId);
                    throw new MYException("已申请激励,不允许再修改激励金额:"+outId);
                } else if (out.getMotivationFlag2() == 1 && each.getType() == TcpConstanst.MOTIVATION_TYPE2){
                    _logger.info("已申请激励2,不允许再修改激励2金额:"+outId);
                    throw new MYException("已申请激励2,不允许再修改激励2金额:"+outId);
                }
            }
            conditionParse.addCondition("outId", "=", outId);
            conditionParse.addCondition("productId", "=", productId);
            List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByCondition(conditionParse);
            if (!ListTools.isEmptyOrNull(baseBeans)){
                for (BaseBean bean : baseBeans){
                    StringBuilder sb = new StringBuilder();
                    String module = "中收";
                    sb.append("修改人:").append(user.getStafferName())
                            .append(".商品:").append(each.getProductName());
                    if (each.getType() == TcpConstanst.IB_TYPE){
                        sb.append("中收金额从").append(bean.getIbMoney())
                                .append("修改为").append(each.getIbMoney());
                        bean.setIbMoney(each.getIbMoney());
                        module = "中收";
                    } else if (each.getType() == TcpConstanst.IB_TYPE2){
                        sb.append("中收2金额从").append(bean.getIbMoney2())
                                .append("修改为").append(each.getIbMoney2());
                        bean.setIbMoney2(each.getIbMoney2());
                        module = "中收2";
                    } else if (each.getType() == TcpConstanst.MOTIVATION_TYPE){
                        sb.append("激励金额从").append(bean.getMotivationMoney())
                                .append("修改为").append(each.getMotivationMoney());
                        bean.setMotivationMoney(each.getMotivationMoney());
                        module = "激励";
                    } else if (each.getType() == TcpConstanst.MOTIVATION_TYPE2){
                        sb.append("激励2金额从").append(bean.getMotivationMoney2())
                                .append("修改为").append(each.getMotivationMoney2());
                        bean.setMotivationMoney2(each.getMotivationMoney2());
                        module = "激励2";
                    } else if (each.getType() == TcpConstanst.PLATFORM_TYPE){
                        sb.append("平台手续费金额从").append(bean.getPlatformFee())
                                .append("修改为").append(each.getPlatformFee());
                        bean.setPlatformFee(each.getPlatformFee());
                        module = "平台手续费";
                    }
                    this.baseDAO.updateEntityBean(bean);
                    _logger.info("***update base bean***" + bean);
                    _logger.info(sb.toString());

                    //日志
                    this.log(user, outId, module, "修改",sb.toString());
                }
            }

            //#694
            this.outDAO.updateFlowTime(outId, TimeTools.now());
        }
    }

    private void log(User user, String id, String module, String operation, String reason) {
        // 记录审批日志
        LogBean log = new LogBean();

        log.setFkId(id);

        log.setLocationId(user.getLocationId());
        log.setStafferId(user.getStafferId());
        log.setLogTime(TimeTools.now());
        log.setModule(module);
        log.setOperation(operation);
        log.setLog(reason);

        logDAO.saveEntityBean(log);
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void importBankBulevel(User user, List<BankBuLevelBean> bankBuLevelBeans) throws MYException {
        //To change body of implemented methods use File | Settings | File Templates.
        for(BankBuLevelBean bean : bankBuLevelBeans){
            BankBuLevelBean bb = this.bankBuLevelDAO.find(bean.getId());
            if (bb == null){
                this.bankBuLevelDAO.saveEntityBean(bean);
            } else{
                BeanUtil.copyProperties(bb, bean);
                this.bankBuLevelDAO.updateEntityBean(bb);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public void exceptionalIbReportJob() throws MYException {
        _logger.info("***exceptionalIbReportJob running***");
        ConditionParse conditionParse = new ConditionParse();
        conditionParse.addWhereStr();
        conditionParse.addCondition(" and type in(7,8)");
        conditionParse.addCondition(" and flowKey in('travel-ib-apply','travel-motivation')");
        //去掉初始和駁回狀態
        conditionParse.addCondition(" and status not in(0,1)");
        //TODO
//        conditionParse.addCondition(" and id='UT201701210052361731'");
//        conditionParse.addCondition(" and id='UT201606141556744885'");
        List<TravelApplyBean> beans = this.travelApplyDAO.queryEntityBeansByCondition(conditionParse);
//        badLog.info("***beans size***"+beans.size());
        Map<String,List<String>> customerToOutMap = new HashMap<String,List<String>>();
        Map<String,String> outToApplyMap = new HashMap<String,String>();
        for(TravelApplyBean bean: beans){
            List<TcpIbBean> tcpIbBeenList = this.tcpIbDAO.queryEntityBeansByFK(bean.getId());
            for (TcpIbBean ib: tcpIbBeenList){
                String outIds = ib.getFullId();
                String customer = ib.getCustomerName();
                if (!StringTools.isNullOrNone(outIds)) {
                    StringTokenizer st = new StringTokenizer(outIds, ";");
                    while (st.hasMoreTokens()) {
                        String outId = st.nextToken();
                        OutBean out = this.outDAO.find(outId);
                        if (out != null) {
//                            badLog.info(outId+"***ib***"+out.getIbFlag()+"***motivation***"+out.getMotivationFlag());
                            if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_MID
                                    && TcpFlowConstant.TRAVELAPPLY_IB.equals(bean.getFlowKey()) && out.getIbFlag() != 1) {
                                List<String> outList = customerToOutMap.get(customer);
                                if (outList == null){
                                    outList = new ArrayList<String>();
                                    customerToOutMap.put(customer,outList);
                                }
                                outList.add(outId);
                                outToApplyMap.put(outId,bean.getId());
                                _logger.warn("修改中收标志:"+outId);
                                this.outDAO.updateIbFlag(outId,1, bean.getId());
                            }

                            if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_MOTIVATION
                                    && TcpFlowConstant.TRAVELAPPLY_MOTIVATION.equals(bean.getFlowKey()) && out.getMotivationFlag() != 1) {
                                List<String> outList = customerToOutMap.get(customer);
                                if (outList == null){
                                    outList = new ArrayList<String>();
                                    customerToOutMap.put(customer,outList);
                                }
                                outList.add(outId);
                                outToApplyMap.put(outId,bean.getId());
                                //TODO
                                _logger.warn("修改激励标志:"+outId);
                                this.outDAO.updateMotivationFlag(outId,1, bean.getId());
                            }
                        }
                    }
                }
            }
        }

        for (String customer: customerToOutMap.keySet()){
            _logger.warn(customer);
            List<String> outList = customerToOutMap.get(customer);
            for(String outId:outList){
                _logger.warn(outId+":"+outToApplyMap.get(outId));
            }
        }
        _logger.info("********duplicate apply for OUT***************");
        //check duplicate
        for (String fullId: outToApplyMap.keySet()){
            ConditionParse conditionParse1 = new ConditionParse();
            conditionParse1.addWhereStr();
            conditionParse1.addCondition("fullId","like","%"+fullId+"%");
            List<TcpIbBean> ibList = this.tcpIbDAO.queryEntityBeansByCondition(conditionParse1);
            if(!ListTools.isEmptyOrNull(ibList) && ibList.size()>=2){
                TcpIbBean one = ibList.get(0);
                TcpIbBean two = ibList.get(1);
                if(one.getType() == two.getType()){
                    //check travel apply
                    TravelApplyBean apply1 = this.getBean(beans,one.getRefId());
                    TravelApplyBean apply2 = this.getBean(beans,two.getRefId());
                    if ((apply1!= null && apply1.getStatus()!=0 && apply1.getStatus()!=1)
                            && (apply2!= null && apply2.getStatus()!=0 && apply2.getStatus()!=1)){
                        _logger.warn(fullId);
                    }
                }
            }
        }
        _logger.info("****finish exceptionalIbReportJob***");
    }

    private TravelApplyBean getBean(List<TravelApplyBean> beans, String id){
        for (TravelApplyBean bean: beans){
            if (bean.getId().equals(id)){
                return bean;
            }
        }
        return null;
    }

    /**
     * @return the tcpApplyDAO
     */
    public TcpApplyDAO getTcpApplyDAO()
    {
        return tcpApplyDAO;
    }

    /**
     * @param tcpApplyDAO
     *            the tcpApplyDAO to set
     */
    public void setTcpApplyDAO(TcpApplyDAO tcpApplyDAO)
    {
        this.tcpApplyDAO = tcpApplyDAO;
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
     * @return the tcpPrepaymentDAO
     */
    public TcpPrepaymentDAO getTcpPrepaymentDAO()
    {
        return tcpPrepaymentDAO;
    }

    /**
     * @param tcpPrepaymentDAO
     *            the tcpPrepaymentDAO to set
     */
    public void setTcpPrepaymentDAO(TcpPrepaymentDAO tcpPrepaymentDAO)
    {
        this.tcpPrepaymentDAO = tcpPrepaymentDAO;
    }

    /**
     * @return the tcpShareDAO
     */
    public TcpShareDAO getTcpShareDAO()
    {
        return tcpShareDAO;
    }

    /**
     * @param tcpShareDAO
     *            the tcpShareDAO to set
     */
    public void setTcpShareDAO(TcpShareDAO tcpShareDAO)
    {
        this.tcpShareDAO = tcpShareDAO;
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
     * @return the travelApplyItemDAO
     */
    public TravelApplyItemDAO getTravelApplyItemDAO()
    {
        return travelApplyItemDAO;
    }

    /**
     * @param travelApplyItemDAO
     *            the travelApplyItemDAO to set
     */
    public void setTravelApplyItemDAO(TravelApplyItemDAO travelApplyItemDAO)
    {
        this.travelApplyItemDAO = travelApplyItemDAO;
    }

    /**
     * @return the travelApplyPayDAO
     */
    public TravelApplyPayDAO getTravelApplyPayDAO()
    {
        return travelApplyPayDAO;
    }

    /**
     * @param travelApplyPayDAO
     *            the travelApplyPayDAO to set
     */
    public void setTravelApplyPayDAO(TravelApplyPayDAO travelApplyPayDAO)
    {
        this.travelApplyPayDAO = travelApplyPayDAO;
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
     * @return the flowLogDAO
     */
    public FlowLogDAO getFlowLogDAO()
    {
        return flowLogDAO;
    }

    /**
     * @param flowLogDAO
     *            the flowLogDAO to set
     */
    public void setFlowLogDAO(FlowLogDAO flowLogDAO)
    {
        this.flowLogDAO = flowLogDAO;
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
     * @return the attachmentDAO
     */
    public AttachmentDAO getAttachmentDAO()
    {
        return attachmentDAO;
    }

    /**
     * @param attachmentDAO
     *            the attachmentDAO to set
     */
    public void setAttachmentDAO(AttachmentDAO attachmentDAO)
    {
        this.attachmentDAO = attachmentDAO;
    }

    /**
     * @return the orgManager
     */
    public OrgManager getOrgManager()
    {
        return orgManager;
    }

    /**
     * @param orgManager
     *            the orgManager to set
     */
    public void setOrgManager(OrgManager orgManager)
    {
        this.orgManager = orgManager;
    }

    /**
     * @return the budgetManager
     */
    public BudgetManager getBudgetManager()
    {
        return budgetManager;
    }

    /**
     * @param budgetManager
     *            the budgetManager to set
     */
    public void setBudgetManager(BudgetManager budgetManager)
    {
        this.budgetManager = budgetManager;
    }

    /**
     * @return the budgetItemDAO
     */
    public BudgetItemDAO getBudgetItemDAO()
    {
        return budgetItemDAO;
    }

    /**
     * @param budgetItemDAO
     *            the budgetItemDAO to set
     */
    public void setBudgetItemDAO(BudgetItemDAO budgetItemDAO)
    {
        this.budgetItemDAO = budgetItemDAO;
    }

    /**
     * @return the notifyManager
     */
    public NotifyManager getNotifyManager()
    {
        return notifyManager;
    }

    /**
     * @param notifyManager
     *            the notifyManager to set
     */
    public void setNotifyManager(NotifyManager notifyManager)
    {
        this.notifyManager = notifyManager;
    }

    /**
     * @return the mailMangaer
     */
    public MailMangaer getMailMangaer()
    {
        return mailMangaer;
    }

    /**
     * @param mailMangaer
     *            the mailMangaer to set
     */
    public void setMailMangaer(MailMangaer mailMangaer)
    {
        this.mailMangaer = mailMangaer;
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

    /**
     * @return the billManager
     */
    public BillManager getBillManager()
    {
        return billManager;
    }

    /**
     * @param billManager
     *            the billManager to set
     */
    public void setBillManager(BillManager billManager)
    {
        this.billManager = billManager;
    }

    /**
     * @return the tcpHandleHisDAO
     */
    public TcpHandleHisDAO getTcpHandleHisDAO()
    {
        return tcpHandleHisDAO;
    }

    /**
     * @param tcpHandleHisDAO
     *            the tcpHandleHisDAO to set
     */
    public void setTcpHandleHisDAO(TcpHandleHisDAO tcpHandleHisDAO)
    {
        this.tcpHandleHisDAO = tcpHandleHisDAO;
    }

    /**
     * @return the bankDAO
     */
    public BankDAO getBankDAO()
    {
        return bankDAO;
    }

    /**
     * @param bankDAO
     *            the bankDAO to set
     */
    public void setBankDAO(BankDAO bankDAO)
    {
        this.bankDAO = bankDAO;
    }

	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

    public TcpIbDAO getTcpIbDAO() {
        return tcpIbDAO;
    }

    public void setTcpIbDAO(TcpIbDAO tcpIbDAO) {
        this.tcpIbDAO = tcpIbDAO;
    }

    public OutDAO getOutDAO() {
        return outDAO;
    }

    public void setOutDAO(OutDAO outDAO) {
        this.outDAO = outDAO;
    }

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public TcpIbReportDAO getTcpIbReportDAO() {
        return tcpIbReportDAO;
    }

    public void setTcpIbReportDAO(TcpIbReportDAO tcpIbReportDAO) {
        this.tcpIbReportDAO = tcpIbReportDAO;
    }

    public TcpIbReportItemDAO getTcpIbReportItemDAO() {
        return tcpIbReportItemDAO;
    }

    public void setTcpIbReportItemDAO(TcpIbReportItemDAO tcpIbReportItemDAO) {
        this.tcpIbReportItemDAO = tcpIbReportItemDAO;
    }

    public LogDAO getLogDAO() {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

    public BankBuLevelDAO getBankBuLevelDAO() {
        return bankBuLevelDAO;
    }

    public void setBankBuLevelDAO(BankBuLevelDAO bankBuLevelDAO) {
        this.bankBuLevelDAO = bankBuLevelDAO;
    }

    public TcpFlowManager getTcpFlowManager() {
        return tcpFlowManager;
    }

    public void setTcpFlowManager(TcpFlowManager tcpFlowManager) {
        this.tcpFlowManager = tcpFlowManager;
    }

    public TcpVSOutDAO getTcpVSOutDAO() {
        return tcpVSOutDAO;
    }

    public void setTcpVSOutDAO(TcpVSOutDAO tcpVSOutDAO) {
        this.tcpVSOutDAO = tcpVSOutDAO;
    }
}
