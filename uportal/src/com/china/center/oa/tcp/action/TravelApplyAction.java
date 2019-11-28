/**
 * File Name: TravelApplyAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.action;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.china.center.oa.publics.Util;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.center.china.osgi.config.ConfigLoader;
import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.center.china.osgi.publics.file.writer.WriteFile;
import com.center.china.osgi.publics.file.writer.WriteFileFactory;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.HandleQueryCondition;
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.budget.bean.BudgetBean;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.budget.dao.BudgetDAO;
import com.china.center.oa.budget.dao.BudgetItemDAO;
import com.china.center.oa.budget.dao.FeeItemDAO;
import com.china.center.oa.budget.vo.BudgetVO;
import com.china.center.oa.budget.vo.FeeItemVO;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.finance.bean.OutBillBean;
import com.china.center.oa.finance.dao.OutBillDAO;
import com.china.center.oa.finance.dao.PayOrderDAO;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProductImportBean;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductImportDAO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.ExportIbReportItemData;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.constant.StafferConstant;
import com.china.center.oa.publics.dao.AttachmentDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.PrincipalshipDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.publics.vo.FlowLogVO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutImportDAO;
import com.china.center.oa.sail.helper.OutHelper;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tcp.bean.BankBuLevelBean;
import com.china.center.oa.tcp.bean.ExpenseApplyBean;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.bean.TcpFlowBean;
import com.china.center.oa.tcp.bean.TcpIbBean;
import com.china.center.oa.tcp.bean.TcpIbReportBean;
import com.china.center.oa.tcp.bean.TcpIbReportItemBean;
import com.china.center.oa.tcp.bean.TcpShareBean;
import com.china.center.oa.tcp.bean.TcpVSOutBean;
import com.china.center.oa.tcp.bean.TravelApplyBean;
import com.china.center.oa.tcp.bean.TravelApplyItemBean;
import com.china.center.oa.tcp.bean.TravelApplyPayBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.BankBuLevelDAO;
import com.china.center.oa.tcp.dao.ExpenseApplyDAO;
import com.china.center.oa.tcp.dao.MayCurConsumeSubmitDAO;
import com.china.center.oa.tcp.dao.TcpApplyDAO;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.dao.TcpFlowDAO;
import com.china.center.oa.tcp.dao.TcpHandleHisDAO;
import com.china.center.oa.tcp.dao.TcpIbDAO;
import com.china.center.oa.tcp.dao.TcpIbReportDAO;
import com.china.center.oa.tcp.dao.TcpIbReportItemDAO;
import com.china.center.oa.tcp.dao.TcpPrepaymentDAO;
import com.china.center.oa.tcp.dao.TcpShareDAO;
import com.china.center.oa.tcp.dao.TcpVSOutDAO;
import com.china.center.oa.tcp.dao.TravelApplyDAO;
import com.china.center.oa.tcp.dao.TravelApplyItemDAO;
import com.china.center.oa.tcp.dao.TravelApplyPayDAO;
import com.china.center.oa.tcp.helper.TCPHelper;
import com.china.center.oa.tcp.manager.TcpFlowManager;
import com.china.center.oa.tcp.manager.TravelApplyManager;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import com.china.center.oa.tcp.vo.TcpHandleHisVO;
import com.china.center.oa.tcp.vo.TcpShareVO;
import com.china.center.oa.tcp.vo.TravelApplyItemVO;
import com.china.center.oa.tcp.vo.TravelApplyVO;
import com.china.center.oa.tcp.wrap.TcpParamWrap;
import com.china.center.osgi.jsp.ElTools;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.FileTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.SequenceTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.UtilStream;
import com.china.center.tools.WriteFileBuffer;


/**
 * TravelApplyAction
 * 
 * @author ZHUZHU
 * @version 2011-7-20
 * @see TravelApplyAction
 * @since 3.0
 */
public class TravelApplyAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private TravelApplyManager travelApplyManager = null;

    private TcpApplyDAO tcpApplyDAO = null;

    private TcpFlowDAO tcpFlowDAO = null;
    
    private TcpApproveDAO tcpApproveDAO = null;

    private TcpPrepaymentDAO tcpPrepaymentDAO = null;
    
    protected ProductDAO productDAO = null;

    private ProductImportDAO productImportDAO = null;

    private TcpShareDAO tcpShareDAO = null;

    private TcpIbDAO tcpIbDAO = null;

    private TcpIbReportDAO tcpIbReportDAO = null;

    private TcpIbReportItemDAO tcpIbReportItemDAO = null;

    private TcpFlowManager tcpFlowManager = null;

    private TravelApplyDAO travelApplyDAO = null;
    
    private TravelApplyItemDAO travelApplyItemDAO = null;

    private TravelApplyPayDAO travelApplyPayDAO = null;

    private BudgetItemDAO budgetItemDAO = null;

    private FeeItemDAO feeItemDAO = null;

    private FlowLogDAO flowLogDAO = null;

    private TcpHandleHisDAO tcpHandleHisDAO = null;

    private ExpenseApplyDAO expenseApplyDAO = null;

    private OutBillDAO outBillDAO = null;
    
    private StafferDAO stafferDAO = null;

    private DutyDAO dutyDAO = null;

    private FinanceDAO financeDAO = null;

    private BudgetDAO budgetDAO = null;
    
    private PrincipalshipDAO principalshipDAO = null;
    
    private AttachmentDAO attachmentDAO = null;

    private OutDAO outDAO = null;

    private OutImportDAO outImportDAO = null;

    private CustomerMainDAO customerMainDAO = null;

    private BaseDAO baseDAO = null;

    private BankBuLevelDAO bankBuLevelDAO = null;

    private OrgManager orgManager      = null;

    private TcpVSOutDAO tcpVSOutDAO = null;
    
    private PayOrderDAO payOrderDao;
    
    private MayCurConsumeSubmitDAO mayCurConsumeSubmitDAO;
    
    private static String QUERYSELFTRAVELAPPLY = "tcp.querySelfTravelApply";
    
    private static String QUERYSELFCOMMISSION = "tcp.querySelfCommission";

    private static String QUERYALLTRAVELAPPLY = "tcp.queryAllTravelApply";

    private static String QUERYSELFAPPROVE = "tcp.queryTcpSelfApprove";

    private static String QUERYPOOLAPPROVE = "tcp.queryTcpPoolApprove";

    private static String RPTQUERYTRAVELAPPLY = "rptQueryTravelApply";

    private static String QUERYTCPHIS = "tcp.queryTcpHis";

    /**
     * default constructor
     */
    public TravelApplyAction()
    {
    }

    /**
     * queryTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfTravelApply(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSELFTRAVELAPPLY, request, condtion);

        String type = request.getParameter("type");

        if ("2".equals(type))
        {
            condtion.addCondition("AND (TravelApplyBean.stafferId = '" + user.getStafferId()
                                  + "' OR TravelApplyBean.borrowStafferId = '"
                                  + user.getStafferId() + "')");
        }
        else
        {
            condtion.addCondition("TravelApplyBean.stafferId", "=", user.getStafferId());
        }
        condtion.addIntCondition("TravelApplyBean.type", "=", type);
        condtion.addCondition(" and TravelApplyBean.purposeType not in (21,31,22,12,32)");

        condtion.addCondition("order by TravelApplyBean.logTime desc");


        request.getSession().setAttribute(JSONPageSeparateTools.getConditionAttributeNameInSession(request, QUERYSELFTRAVELAPPLY), condtion);
        request.getSession().setAttribute("A_condtion", condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFTRAVELAPPLY, request,
            condtion, this.travelApplyDAO, new HandleResult<TravelApplyVO>()
            {
                public void handle(TravelApplyVO vo)
                {
                    TCPHelper.chageVO(vo);

                    // 当前处理人
                    List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(vo.getId());

                    for (TcpApproveVO tcpApproveVO : approveList)
                    {
                        if (tcpApproveVO.getPool() == TcpConstanst.TCP_POOL_COMMON)
                        {
                            vo.setProcesser(vo.getProcesser() + tcpApproveVO.getApproverName()
                                            + ';');
                        }
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }


    /**
     * 所有的中收/激励/平台手续费申请
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfIb(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
            throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSELFTRAVELAPPLY, request, condtion);

        condtion.addCondition("TravelApplyBean.stafferId", "=", user.getStafferId());
        condtion.addCondition(" and TravelApplyBean.type in(7,8,9,10,16,17)");
        condtion.addCondition(" and TravelApplyBean.purposeType not in (21,31,22,12,32)");

        condtion.addCondition("order by TravelApplyBean.logTime desc");


        request.getSession().setAttribute(JSONPageSeparateTools.getConditionAttributeNameInSession(request, QUERYSELFTRAVELAPPLY), condtion);
        request.getSession().setAttribute("A_condtion", condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFTRAVELAPPLY, request,
                condtion, this.travelApplyDAO, new HandleResult<TravelApplyVO>()
                {
                    public void handle(TravelApplyVO vo)
                    {
                        TCPHelper.chageVO(vo);

                        // 当前处理人
                        List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(vo.getId());

                        for (TcpApproveVO tcpApproveVO : approveList)
                        {
                            if (tcpApproveVO.getPool() == TcpConstanst.TCP_POOL_COMMON)
                            {
                                vo.setProcesser(vo.getProcesser() + tcpApproveVO.getApproverName()
                                        + ';');
                            }
                        }
                    }
                });

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    /**
     * queryAllVocationAndWork
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllVocationAndWork(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSELFTRAVELAPPLY, request, condtion);

        condtion.addCondition("TravelApplyBean.stafferId", "=", user.getStafferId());
        
        condtion.addCondition(" and TravelApplyBean.purposeType in (21,31,22,12,32)");

        condtion.addCondition("order by TravelApplyBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSELFTRAVELAPPLY, request,
            condtion, this.travelApplyDAO, new HandleResult<TravelApplyVO>()
            {
                public void handle(TravelApplyVO vo)
                {
                    TCPHelper.chageVO(vo);

                    // 当前处理人
                    List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(vo.getId());

                    for (TcpApproveVO tcpApproveVO : approveList)
                    {
                        if (tcpApproveVO.getPool() == TcpConstanst.TCP_POOL_COMMON)
                        {
                            vo.setProcesser(vo.getProcesser() + tcpApproveVO.getApproverName()
                                            + ';');
                        }
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }
    
    

    /**
     * queryAllTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryAllTravelApply(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYALLTRAVELAPPLY, request, condtion);

        condtion.addCondition("order by TravelApplyBean.logTime desc");

        final List<AttachmentBean> attachmentsList = attachmentDAO.queryEntityBeansByCondition("where attachmentType = ? ", AttachmentBean.AttachmentType_FK);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYALLTRAVELAPPLY, request,
            condtion, this.travelApplyDAO, new HandleResult<TravelApplyVO>()
            {
                public void handle(TravelApplyVO vo)
                {
                    TCPHelper.chageVO(vo);

                    int attachmentCount = attachmentCount(attachmentsList, vo.getId());
                    if(attachmentCount>0){
                        String attachmentUrl = "../admin/down.do?method=downPayAttachmentsById&id="+vo.getId();
                        vo.setAttachmentUrl(attachmentUrl);
                        vo.setAttachmentsHint("附件（"+attachmentCount+"）");
                    }

                    // 当前处理人
                    List<TcpApproveVO> approveList = tcpApproveDAO.queryEntityVOsByFK(vo.getId());

                    for (TcpApproveVO tcpApproveVO : approveList)
                    {
                        if (tcpApproveVO.getPool() == TcpConstanst.TCP_POOL_COMMON)
                        {
                            vo.setProcesser(vo.getProcesser() + tcpApproveVO.getApproverName()
                                            + ';');
                        }
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryPoolApprove
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryPoolApprove(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String cacheKey = QUERYPOOLAPPROVE;

        ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

        condtion.addCondition("TcpApproveBean.approverId", "=", user.getStafferId());

        condtion.addIntCondition("TcpApproveBean.pool", "=", TcpConstanst.TCP_POOL_POOL);

        condtion.addCondition("order by TcpApproveBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey, request, condtion,
            this.tcpApproveDAO, new HandleResult<TcpApproveVO>()
            {
                public void handle(TcpApproveVO vo)
                {
                    TCPHelper.getTcpApproveVO(vo);

                    if (vo.getType() <= 10)
                    {
                        vo.setUrl(TcpConstanst.TCP_TRAVELAPPLY_DETAIL_URL + vo.getApplyId());
                    }
                    else
                    {
                        vo.setUrl(TcpConstanst.TCP_EXPENSE_DETAIL_URL + vo.getApplyId());
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * queryTcpHis
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryTcpHis(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String cacheKey = QUERYTCPHIS;

        ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

        condtion.addCondition("VTcpHandleHisBean.stafferId", "=", user.getStafferId());

        condtion.addCondition("order by VTcpHandleHisBean.logTime desc");

        String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey, request, condtion,
            this.tcpHandleHisDAO, new HandleResult<TcpHandleHisVO>()
            {
                public void handle(TcpHandleHisVO vo)
                {
                    TCPHelper.getTcpHandleHisVO(vo);

                    if (vo.getType() <= 10)
                    {
                        TravelApplyBean bean = travelApplyDAO.find(vo.getRefId());

                        if (bean != null)
                        {
                            vo.setMoneyStr1(TCPHelper.longToDouble2(bean.getTotal()));
                            vo.setMoneyStr2(TCPHelper.longToDouble2(bean.getBorrowTotal()));
                        }
                    }
                    else
                    {
                        ExpenseApplyBean bean = expenseApplyDAO.find(vo.getRefId());

                        if (bean != null)
                        {
                            vo.setMoneyStr1(TCPHelper.longToDouble2(bean.getTotal()));
                            vo.setMoneyStr2(TCPHelper.longToDouble2(bean.getRefMoney()));
                        }
                    }
                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * 查询待我处理的
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySelfApprove(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        final String mode = request.getParameter("mode");
        
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        String cacheKey = QUERYSELFAPPROVE;

        ActionTools.processJSONQueryCondition(cacheKey, request, condtion);

        if (mode.equals("98"))
        {
        	condtion.addIntCondition("TcpApproveBean.status", "=", TcpConstanst.TCP_STATUS_APPLY_RELATE);
        }else{
        	condtion.addCondition("TcpApproveBean.approverId", "=", user.getStafferId());
        }
        
        if ("0".equals(mode) || StringTools.isNullOrNone(mode)){
            // 预开票与预收退款及退票
        	condtion.addCondition(" and TcpApproveBean.type not in (22, 23, 24)");
        } else if ("999".equals(mode)) {
            //23 预收退款
        	condtion.addIntCondition("TcpApproveBean.type", "=", TcpConstanst.TCP_BACKPREPAY);
        }else{
//        	condtion.addIntCondition("TcpApproveBean.type", "=", TcpConstanst.TCP_PREINVOICE);
            //预开票及退票
            condtion.addCondition(" and TcpApproveBean.type in('22,24') ");
        }

        condtion.addIntCondition("TcpApproveBean.pool", "=", TcpConstanst.TCP_POOL_COMMON);

        condtion.addCondition("order by TcpApproveBean.logTime desc");

        final List<AttachmentBean> attachmentsList = attachmentDAO.queryEntityBeansByCondition("where attachmentType = ? ", AttachmentBean.AttachmentType_FK);

        String jsonstr = ActionTools.queryVOByJSONAndToString(cacheKey, request, condtion,
                this.tcpApproveDAO, new HandleResult<TcpApproveVO>() {
            public void handle(TcpApproveVO vo) {
                TCPHelper.getTcpApproveVO(vo);

                int attachmentCount = attachmentCount(attachmentsList, vo.getApplyId());
                if(attachmentCount>0){
                    String attachmentUrl = "../admin/down.do?method=downPayAttachmentsById&id="+vo.getApplyId();
                    vo.setAttachmentUrl(attachmentUrl);
                    vo.setAttachmentsHint("附件（"+attachmentCount+"）");
                }

                if (mode.equals("98")) {
                    vo.setUrl(TcpConstanst.PREINVOICE_DETAIL_URL + vo.getApplyId());
                }
            }
        });


        return JSONTools.writeResponse(response, jsonstr);
    }

    private static int attachmentCount(List<AttachmentBean> attachmentsList, String id){
        int count = 0;
        for(AttachmentBean bean : attachmentsList){
            if(bean.getRefId().equals(id)){
                count ++;
            }
        }
        return count;
    }

    /**
     * preForAddTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddTravelApply(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        prepareInner(request);

        String type = request.getParameter("type");

        return mapping.findForward("addTravelApply" + type);
    }
    
    /**
     * preForAddTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddVocationAndWork(ActionMapping mapping, ActionForm form,
                                              HttpServletRequest request,
                                              HttpServletResponse response)
        throws ServletException
    {
        prepareInner(request);
        
        User user = Helper.getUser(request);

        StafferBean stafferBean = stafferDAO.find(user.getStafferId());
        
        String postid = stafferBean.getPostId();
        
        if(postid.equals("4")||postid==null||("").equals(postid))
        {
        	request.setAttribute("tempStatus",TcpConstanst.TCP_STATUS_WAIT_DEPARTMENT);
        }
        else if(postid.equals("17"))
        {
        	request.setAttribute("tempStatus",TcpConstanst.TCP_STATUS_WAIT_LOCATION);
        }
        else if(postid.equals("18"))
        {
        	request.setAttribute("tempStatus",TcpConstanst.TCP_STATUS_WAIT_LOCATION);
        }
        else if(postid.equals("16"))
        {
        	request.setAttribute("tempStatus",TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO);
        }
        
        return mapping.findForward("addVocationAndWork");
    }
    
   
    /**
     * prepareInner
     * 
     * @param request
     */
    private void prepareInner(HttpServletRequest request)
    {
        String type = request.getParameter("type");

        List<FeeItemVO> feeItemList = feeItemDAO.listEntityVOs();

        // 只有出差的特殊处理
        if ("0".equals(type))
        {
            for (Iterator iterator = feeItemList.iterator(); iterator.hasNext();)
            {
                FeeItemVO feeItemVO = (FeeItemVO)iterator.next();

                if (feeItemVO.getId().equals(BudgetConstant.FEE_ITEM_TRAVELLING))
                {
                    iterator.remove();
                    break;
                }
            }
        }

        request.setAttribute("feeItemList", feeItemList);

        // 群组
        request.setAttribute("pluginType", "group");
        request.setAttribute("pluginValue", TcpFlowConstant.GROUP_DM);
    }

    /**
     * deleteTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteTravelApply(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            travelApplyManager.deleteTravelApplyBean(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    

    /**
     * findTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findTravelApply(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        User user = Helper.getUser(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");
        
        String ttflag = request.getParameter("ttflag");
        
        TravelApplyVO bean = travelApplyManager.findVO(id);
        
        if (bean == null)
        {
            return ActionTools.toError("单据不存在："+id, mapping, request);
        } else{
            //2015/4/12 中收激励导入功能
            if ((bean.isMidOrMotivation())
                    && bean.isImportFlag()){
                List<TcpIbBean> ibList = this.tcpIbDAO.queryEntityBeansByFK(bean.getId());
                 _logger.info("************TcpIbBean list size:"+ibList.size());
                bean.setIbList(ibList);
            }
        }
        
        int purposeType = bean.getPurposeType();
        
        prepareInner(request);

        request.setAttribute("bean", bean);

        request.setAttribute("update", update);

        // 查询关联的付款单和凭证
        List<OutBillBean> billList = outBillDAO.queryEntityBeansByFK(id);

        request.setAttribute("billList", billList);

        List<FinanceBean> financeList = financeDAO.queryEntityBeansByFK(id);

        request.setAttribute("financeList", financeList);
        
        //add by zhangxian 2019-08-09
        //查询银行回单附件
    	ConditionParse cond = new ConditionParse();
    	cond.addCondition("refid", "=", id);
    	cond.addCondition("flag","=",1);
    	List<AttachmentBean> subList =  attachmentDAO.queryEntityBeansByCondition(cond);
        request.setAttribute("payOrderAttachmentList", subList);
        
        //end add

        if(purposeType == 12 || purposeType == 22 ||
        		purposeType == 32 || purposeType == 21 ||purposeType == 31)
        {
        	StafferVO staffervo = stafferDAO.findVO(bean.getStafferId());
        	
        	request.setAttribute("staffervo", staffervo);
        	
        	// 获取审批日志
            List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);

            List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

            for (FlowLogBean flowLogBean : logs)
            {
                logsVO.add(TCPHelper.getTCPFlowLogVO(flowLogBean));
            }

            request.setAttribute("logList", logsVO);
        	
            if((null != ttflag && ttflag.equals("11")) || (null !=update && update.equals("1")))
            {
            	// 获得当前的处理环节
                TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

                request.setAttribute("token", token);

                if (token.getNextPlugin().startsWith("group"))
                {
                    // 群组
                    request.setAttribute("pluginType", "group");

                    request.setAttribute("pluginValue", token.getNextPlugin().substring(6));
                }
                else
                {
                    request.setAttribute("pluginType", "");
                    request.setAttribute("pluginValue", "");
                }
            	
            	return mapping.findForward("processVocationAndWork");
            }
            else
            {
            	return mapping.findForward("detailVocationAndWork");
            }
        }
        
        // 2是稽核修改
        if ("1".equals(update) || "3".equals(update))
        {
            if ( !TCPHelper.canTravelApplyUpdate(bean))
            {
                return ActionTools.toError("申请当前状态下不能被修改", mapping, request);
            }

            List<AttachmentBean> attachmentList = bean.getAttachmentList();

            String attacmentIds = "";

            for (AttachmentBean attachmentBean : attachmentList)
            {
                attacmentIds = attacmentIds + attachmentBean.getId() + ";";
            }

            request.setAttribute("attacmentIds", attacmentIds);

            List<TravelApplyItemVO> itemVOList = bean.getItemVOList();

            // 出差特殊处理屏蔽差旅费
            if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_TRAVEL)
            {
                for (Iterator iterator = itemVOList.iterator(); iterator.hasNext();)
                {
                    TravelApplyItemVO travelApplyItemVO = (TravelApplyItemVO)iterator.next();

                    if (travelApplyItemVO.getFeeItemId().equals(BudgetConstant.FEE_ITEM_TRAVELLING))
                    {
                        iterator.remove();

                        break;
                    }
                }
            }

            return mapping.findForward("updateTravelApply" + bean.getType());
        }

        // 获取审批日志
        List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);

        List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

        for (FlowLogBean flowLogBean : logs)
        {
            logsVO.add(TCPHelper.getTCPFlowLogVO(flowLogBean));
        }

        request.setAttribute("logList", logsVO);

        // 处理
        if ("2".equals(update))
        {
            // 先鉴权
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
                return ActionTools.toError("没有处理的权限", mapping, request);
            }

            // 获得当前的处理环节
            TcpFlowBean token = tcpFlowDAO.findByUnique(bean.getFlowKey(), bean.getStatus());

            if (token == null){
                return ActionTools.toError("T_CENTER_TCPFLOW不存在flowKey:"+bean.getFlowKey()+" status:"+bean.getStatus(), mapping, request);
            }
            request.setAttribute("token", token);

            String nextPlugin = token.getNextPlugin();
            if (nextPlugin.startsWith("group"))
            {
                // 群组
                request.setAttribute("pluginType", "group");

                request.setAttribute("pluginValue", token.getNextPlugin().substring(6));
            } else
            {
                request.setAttribute("pluginType", "");
                request.setAttribute("pluginValue", "");
            }
            return mapping.findForward("processTravelApply" + bean.getType());
        }

        return mapping.findForward("detailTravelApply" + bean.getType());
    }

    /**
     * downAttachmentFile
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downAttachmentFile(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        String path = getAttachmentPath();
        String id = request.getParameter("id");
        AttachmentBean bean = attachmentDAO.find(id);

        if (bean == null)
        {
            return ActionTools.toError(mapping, request);
        }

        path += bean.getPath();

        File file = new File(path);
        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                                                  + StringTools.getStringBySet(bean.getName(),
                                                      "GBK", "ISO8859-1"));

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }

    /**
     * addTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateTravelApply(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {
        TravelApplyBean bean = new TravelApplyBean();

        // 模板最多20M
        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 20L);

        try
        {
            rds.parser();
        }
        catch (FileUploadBase.SizeLimitExceededException e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过20M");

            return mapping.findForward("error");
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");

            return mapping.findForward("error");
        }
        BeanUtil.getBean(bean, rds.getParmterMap());

        String addOrUpdate = rds.getParameter("addOrUpdate");

        String oprType = rds.getParameter("oprType");

        String processId = rds.getParameter("processId");

        String importFlag = rds.getParameter("importFlag");

//        String ibType = rds.getParameter("ibType");

        _logger.info(addOrUpdate+"****importFlag:"+importFlag+"***bean***"+bean);

//        if (!StringTools.isNullOrNone(importFlag)){
//
//        }

        // 出差申请的特殊处理
        if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_TRAVEL)
        {
            changeTravel(bean, rds);
        }

        // 更新中收激励申请时需要重新解析附件
        if (bean.isMidOrMotivation() && "1".equals(addOrUpdate)){
            String filePath = this.parserIbAttachment(request, rds, bean);
            if ( StringTools.isNullOrNone(filePath))
            {
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "附件上传失败,请重新上传!");

                return mapping.findForward("error");
            }

            //<customerName,ibMoneyTotal>
            Map<String, Double>  customerToIbMap = new HashMap<String,Double>();
            //<customerName,motivationMoneyTotal>
            Map<String, Double>  customerToMotivationMap = new HashMap<String,Double>();

            //<customerName,ibMoneyTotal2>
            Map<String, Double>  customerToIbMap2 = new HashMap<String,Double>();
            //<customerName,motivationMoneyTotal2>
            Map<String, Double>  customerToMotivationMap2 = new HashMap<String,Double>();

            Map<String, Double>  customerToPlatformMap = new HashMap<String,Double>();

            //<customerName,List<outId>>
            Map<String, List<String>>  customerToOutMap = new HashMap<String,List<String>>();

            ReaderFile reader = ReadeFileFactory.getXLSReader();
            int type = 0;
            boolean importError = false;
            StringBuilder builder = new StringBuilder();
            List<TcpIbBean> importItemList = new ArrayList<TcpIbBean>();
            List<TcpVSOutBean> importItemList2 = new ArrayList<>();
            bean.setIbList(importItemList);
            bean.setTcpVSOutBeanList(importItemList2);
            try
            {
                FileInputStream fs = new FileInputStream(filePath);
                reader.readFile(fs);

                String ibType2 = "";

                while (reader.hasNext())
                {
                    String[] obj = fillObj2((String[])reader.next());

                    // 第一行忽略
                    if (reader.getCurrentLineNumber() == 1)
                    {
                        continue;
                    }

                    if (StringTools.isNullOrNone(obj[0]))
                    {
                        continue;
                    }

                    int currentNumber = reader.getCurrentLineNumber();

                    if (obj.length >= 2 )
                    {
                        TcpIbBean item = new TcpIbBean();
                        TcpVSOutBean tcpVSOutBean = new TcpVSOutBean();

                        // 申请类型
                        if ( !StringTools.isNullOrNone(obj[0]))
                        {
                            String name = obj[0];

                            if (currentNumber == 2){
                                ibType2 = name;
                            }

                            //导入模板中申请类型必须一致
                            if (!ibType2.equals(name)){
                                builder
                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                                        .append("申请类型必须一致")
                                        .append("</font><br>");

                                importError = true;
                            }

                            if (TcpConstanst.IB_TYPE_STR.equals(name)){
                                item.setType(TcpConstanst.IB_TYPE);
                                type = TcpConstanst.IB_TYPE;
                            } else if (TcpConstanst.MOTIVATION_TYPE_STR.equals(name)){
                                item.setType(TcpConstanst.MOTIVATION_TYPE);
                                type = TcpConstanst.MOTIVATION_TYPE;
                            } else if (TcpConstanst.IB_TYPE_STR2.equals(name)){
                                item.setType(TcpConstanst.IB_TYPE2);
                                type = TcpConstanst.IB_TYPE2;
                            } else if (TcpConstanst.MOTIVATION_TYPE_STR2.equals(name)){
                                item.setType(TcpConstanst.MOTIVATION_TYPE2);
                                type = TcpConstanst.MOTIVATION_TYPE2;
                            } else if (TcpConstanst.PLATFORM_TYPE_STR.equals(name)){
                                item.setType(TcpConstanst.PLATFORM_TYPE);
                                type = TcpConstanst.PLATFORM_TYPE;
                            } else{
                                builder
                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                                        .append("申请类型只能为中收或激励")
                                        .append("</font><br>");

                                importError = true;
                            }
                        }else{
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("申请类型必填")
                                    .append("</font><br>");

                            importError = true;
                        }
                        tcpVSOutBean.setType(item.getType());

                        // 客户名
                        if ( !StringTools.isNullOrNone(obj[1]))
                        {
                            String name = obj[1];
                            ConditionParse con = new ConditionParse();
                            con.addWhereStr();
                            con.addCondition("name", "=",name);
                            List<CustomerBean> cbeanList = customerMainDAO.queryEntityBeansByCondition(con);
                            if (ListTools.isEmptyOrNull(cbeanList)){
                                builder
                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                                        .append("客户名不存在")
                                        .append("</font><br>");

                                importError = true;
                            }

                            item.setCustomerName(name);
                            tcpVSOutBean.setCustomerName(name);

                            // 订单号
                            if ( !StringTools.isNullOrNone(obj[2]))
                            {
                                String outId = obj[2];
                                item.setFullId(outId);
                                tcpVSOutBean.setFullId(outId);
                                OutBean out = this.outDAO.find(outId);
                                if (out == null){
                                    builder
                                            .append("<font color=red>第[" + currentNumber + "]行错误:")
                                            .append("订单号[").append(item.getFullId())
                                            .append("]不存在")
                                            .append("</font><br>");

                                    importError = true;
                                }else{
                                    //同一个订单不能重复提交中收报销申请
                                    if (out.getIbFlag() == 1){
                                        if (type == TcpConstanst.IB_TYPE){
                                            builder
                                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                                    .append("订单号不能重复提交中收报销申请:"+outId)
                                                    .append("</font><br>");

                                            importError = true;
                                        }
                                    }

                                    //同一个订单不能重复提交激励报销申请
                                    if (out.getMotivationFlag() == 1){
                                        if(type == TcpConstanst.MOTIVATION_TYPE){
                                            builder
                                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                                    .append("订单号不能重复提交激励报销申请:"+outId)
                                                    .append("</font><br>");

                                            importError = true;
                                        }
                                    }

                                    //同一个订单不能重复提交中收2报销申请
                                    if (out.getIbFlag2() == 1){
                                        if (type == TcpConstanst.IB_TYPE2){
                                            builder
                                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                                    .append("订单号不能重复提交中收2报销申请:"+outId)
                                                    .append("</font><br>");

                                            importError = true;
                                        }
                                    }

                                    //同一个订单不能重复提交激励2报销申请
                                    if (out.getMotivationFlag2() == 1){
                                        if(type == TcpConstanst.MOTIVATION_TYPE2){
                                            builder
                                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                                    .append("订单号不能重复提交激励2报销申请:"+outId)
                                                    .append("</font><br>");

                                            importError = true;
                                        }
                                    }

                                    //同一个订单不能重复提交平台手续费报销申请
                                    if (out.getPlatformFlag() == 1){
                                        if(type == TcpConstanst.PLATFORM_TYPE){
                                            builder
                                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                                    .append("订单号不能重复提交平台手续费报销申请:"+outId)
                                                    .append("</font><br>");

                                            importError = true;
                                        }
                                    }

                                    //检查订单的付款状态
                                    if (out.getPay() == OutConstant.PAY_NOT){
                                        builder
                                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                                .append("订单未付款")
                                                .append("</font><br>");

                                        importError = true;
                                    }

                                    //#441 2017/3/29 再次检查是否已提交申请过
                                    ConditionParse conditionParse1 = new ConditionParse();
                                    conditionParse1.addWhereStr();
                                    conditionParse1.addCondition("type","=",type);
                                    conditionParse1.addCondition("fullId","like","%"+outId+"%");
                                    List<TcpIbBean> ibList = this.tcpIbDAO.queryEntityBeansByCondition(conditionParse1);
                                    if(!ListTools.isEmptyOrNull(ibList)){
                                        for (TcpIbBean ib:ibList){
                                            TravelApplyBean apply = this.travelApplyDAO.find(ib.getRefId());
                                            if (apply!= null && apply.getStatus()!=0 && apply.getStatus()!=1){
                                                builder
                                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                                                        .append(outId+"订单号已提交激励报销申请:"+apply.getId())
                                                        .append("</font><br>");

                                                importError = true;
                                            }
                                        }
                                    }

                                    if (customerToOutMap.containsKey(item.getCustomerName())){
                                        List<String> oudIds = customerToOutMap.get(item.getCustomerName());
                                        oudIds.add(outId);
                                    }else{
                                        List<String> oudIds = new ArrayList<String>();
                                        oudIds.add(outId);
                                        customerToOutMap.put(item.getCustomerName(), oudIds);
                                    }
                                }
                            } else{
                                builder
                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                                        .append("订单号必填")
                                        .append("</font><br>");

                                importError = true;
                            }

                            //商品名
                            if ( !StringTools.isNullOrNone(obj[3]))
                            {
                                String productName = obj[3];

                                ConditionParse con2 = new ConditionParse();
                                con2.addWhereStr();
                                con2.addCondition("name", "=",productName);
                                List<ProductBean> productList = this.productDAO.queryEntityBeansByCondition(con2);
                                if (ListTools.isEmptyOrNull(productList)){
                                    builder
                                            .append("<font color=red>第[" + currentNumber + "]行错误:")
                                            .append("商品名[").append(productName)
                                            .append("]不存在")
                                            .append("</font><br>");

                                    importError = true;
                                }
                                item.setProductName(productName);
                                tcpVSOutBean.setProductName(productName);

                                //#401
                                ConditionParse conditionParse = new ConditionParse();
                                conditionParse.addCondition("fullId","=", item.getFullId());
                                List<TcpIbReportItemBean> ibReportList = this.tcpIbReportItemDAO.queryEntityBeansByCondition(conditionParse);
                                if (ListTools.isEmptyOrNull(ibReportList)){
                                    builder.append("<font color=red>第[" + currentNumber + "]行错误:")
                                            .append("订单号[").append(item.getFullId())
                                            .append("]").append("不在中收激励统计清单中")
                                            .append("<br>");
                                    importError = true;
                                } else {
                                    //#383
                                    ConditionParse conditionParse2 = new ConditionParse();
                                    conditionParse2.addCondition("fullId", "=", item.getFullId());
                                    conditionParse2.addCondition("productName", "=", productName);
                                    List<TcpIbReportItemBean> ibReportList2 = this.tcpIbReportItemDAO.queryEntityBeansByCondition(conditionParse);
                                    if (ListTools.isEmptyOrNull(ibReportList2)) {
                                        builder.append("<font color=red>第[" + currentNumber + "]行错误:")
                                                .append("订单号[").append(item.getFullId())
                                                .append("]").append("和品名不符：" + productName)
                                                .append("<br>");
                                        importError = true;
                                    }
                                }
                            }

                            //数量
                            if ( !StringTools.isNullOrNone(obj[4]))
                            {
                                String amount = obj[4];
                                item.setAmount(Integer.valueOf(amount));
                                tcpVSOutBean.setAmount(item.getAmount());
                            }

                            //中收/激励金额
                            if ( !StringTools.isNullOrNone(obj[5]))
                            {
                                String money = obj[5];
                                //导入时未检查申请金额是否等于系统记录的中收或激励金额，不等应该报错
                                if (type == TcpConstanst.IB_TYPE){
                                    item.setIbMoney(MathTools.parseDouble(money));
                                    tcpVSOutBean.setIbMoney(MathTools.parseDouble(money));
                                    if (customerToIbMap.containsKey(item.getCustomerName())){
                                        customerToIbMap.put(item.getCustomerName(),customerToIbMap.get(item.getCustomerName())+item.getIbMoney()*item.getAmount());
                                    } else{
                                        customerToIbMap.put(item.getCustomerName(), item.getIbMoney()*item.getAmount());
                                    }
                                } else if (type == TcpConstanst.MOTIVATION_TYPE){
                                    item.setMotivationMoney(MathTools.parseDouble(money));
                                    tcpVSOutBean.setMotivationMoney(MathTools.parseDouble(money));
                                    if(customerToMotivationMap.containsKey(item.getCustomerName())){
                                        customerToMotivationMap.put(item.getCustomerName(),
                                                customerToMotivationMap.get(item.getCustomerName())+item.getMotivationMoney()*item.getAmount());
                                    }else{
                                        customerToMotivationMap.put(item.getCustomerName(),item.getMotivationMoney()*item.getAmount());
                                    }
                                } else if (type == TcpConstanst.IB_TYPE2){
                                    item.setIbMoney2(MathTools.parseDouble(money));
                                    tcpVSOutBean.setIbMoney2(MathTools.parseDouble(money));
                                    if (customerToIbMap2.containsKey(item.getCustomerName())){
                                        customerToIbMap2.put(item.getCustomerName(),customerToIbMap2.get(item.getCustomerName())+item.getIbMoney2()*item.getAmount());
                                    } else{
                                        customerToIbMap2.put(item.getCustomerName(), item.getIbMoney2()*item.getAmount());
                                    }
                                }else if (type == TcpConstanst.MOTIVATION_TYPE2){
                                    item.setMotivationMoney2(MathTools.parseDouble(money));
                                    tcpVSOutBean.setMotivationMoney2(MathTools.parseDouble(money));
                                    if(customerToMotivationMap2.containsKey(item.getCustomerName())){
                                        customerToMotivationMap2.put(item.getCustomerName(),
                                                customerToMotivationMap2.get(item.getCustomerName())+item.getMotivationMoney2()*item.getAmount());
                                    }else{
                                        customerToMotivationMap2.put(item.getCustomerName(),item.getMotivationMoney2()*item.getAmount());
                                    }
                                }else if (type == TcpConstanst.PLATFORM_TYPE){
                                    item.setPlatformFee(MathTools.parseDouble(money));
                                    tcpVSOutBean.setPlatformFee(MathTools.parseDouble(money));
                                    if(customerToPlatformMap.containsKey(item.getCustomerName())){
                                        customerToPlatformMap.put(item.getCustomerName(),
                                                customerToPlatformMap.get(item.getCustomerName())+item.getPlatformFee()*item.getAmount());
                                    }else{
                                        customerToPlatformMap.put(item.getCustomerName(),item.getPlatformFee()*item.getAmount());
                                    }
                                }
                            } else{
                                builder
                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                                        .append("中收或激励金额必填")
                                        .append("</font><br>");

                                importError = true;
                            }
                        }
                        else
                        {
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("数据长度不足2格错误")
                                    .append("<br>");

                            importError = true;
                        }
                        importItemList2.add(tcpVSOutBean);
                    }
                }

                _logger.info(importItemList2+"***type**"+type+"***customerToIbMap2***"+customerToIbMap2);
                //每个客户的申请金额不得大于统计的可申请的中收或激励金额
                if (type == TcpConstanst.IB_TYPE){
                    for(String customerName : customerToIbMap.keySet()){
                        ConditionParse con = new ConditionParse();
                        con.addWhereStr();
                        con.addCondition("customerName","=",customerName);
                        List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                        if (ListTools.isEmptyOrNull(ibReportList)){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("可申请中收金额不足："+0)
                                    .append("<br>");
                            importError = true;
                        } else {
                            TcpIbReportBean ib = ibReportList.get(0);
                            double currentIb = customerToIbMap.get(customerName);
                            double currentIb2 = this.roundDouble(currentIb);
                            if (currentIb2>ib.getIbMoneyTotal()){
                                builder.append("客户[").append(customerName)
                                        .append("]").append("当前申请金额：" + currentIb2 + "大于可申请中收金额：" + ib.getIbMoneyTotal())
                                        .append("<br>");
                                importError = true;
                            }
                        }
                    }
                } else if (type == TcpConstanst.MOTIVATION_TYPE){
                    for(String customerName : customerToMotivationMap.keySet()){
                        ConditionParse con = new ConditionParse();
                        con.addWhereStr();
                        con.addCondition("customerName","=",customerName);
                        List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                        if (ListTools.isEmptyOrNull(ibReportList)){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("可申请激励金额不足："+0)
                                    .append("<br>");
                            importError = true;
                        } else {
                            TcpIbReportBean ib = ibReportList.get(0);
                            double currentMot = customerToMotivationMap.get(customerName);
                            double currentMot2 = this.roundDouble(currentMot);
                            if (currentMot2> ib.getMotivationMoneyTotal()){
                                builder.append("客户[").append(customerName)
                                        .append("]").append("当前申请金额："+currentMot2+"大于可申请激励金额："+ib.getMotivationMoneyTotal())
                                        .append("<br>");
                                importError = true;
                            }
                        }
                    }
                } else if (type == TcpConstanst.IB_TYPE2){
                    for(String customerName : customerToIbMap2.keySet()){
                        ConditionParse con = new ConditionParse();
                        con.addWhereStr();
                        con.addCondition("customerName","=",customerName);
                        List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                        if (ListTools.isEmptyOrNull(ibReportList)){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("可申请中收2金额不足："+0)
                                    .append("<br>");
                            importError = true;
                        } else {
                            TcpIbReportBean ib = ibReportList.get(0);
                            double currentIb = customerToIbMap2.get(customerName);
                            double currentIb2 = this.roundDouble(currentIb);
                            if (currentIb2>ib.getIbMoneyTotal2()){
                                builder.append("客户[").append(customerName)
                                        .append("]").append("当前申请金额：" + currentIb2 + "大于可申请中收2金额：" + ib.getIbMoneyTotal2())
                                        .append("<br>");
                                importError = true;
                            }
                        }
                    }
                } else if (type == TcpConstanst.MOTIVATION_TYPE2){
                    for(String customerName : customerToMotivationMap2.keySet()){
                        ConditionParse con = new ConditionParse();
                        con.addWhereStr();
                        con.addCondition("customerName","=",customerName);
                        List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                        if (ListTools.isEmptyOrNull(ibReportList)){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("可申请其他费用金额不足："+0)
                                    .append("<br>");
                            importError = true;
                        } else {
                            TcpIbReportBean ib = ibReportList.get(0);
                            double currentMot = customerToMotivationMap2.get(customerName);
                            double currentMot2 = this.roundDouble(currentMot);
                            if (currentMot2> ib.getMotivationMoneyTotal2()){
                                builder.append("客户[").append(customerName)
                                        .append("]").append("当前申请金额："+currentMot2+"大于可申请其他费用金额："+ib.getMotivationMoneyTotal2())
                                        .append("<br>");
                                importError = true;
                            }
                        }
                    }
                } else if (type == TcpConstanst.PLATFORM_TYPE){
                    for(String customerName : customerToPlatformMap.keySet()){
                        ConditionParse con = new ConditionParse();
                        con.addWhereStr();
                        con.addCondition("customerName","=",customerName);
                        List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                        if (ListTools.isEmptyOrNull(ibReportList)){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("可申请平台收费金额不足："+0)
                                    .append("<br>");
                            importError = true;
                        } else {
                            TcpIbReportBean ib = ibReportList.get(0);
                            double currentMot = customerToPlatformMap.get(customerName);
                            double currentMot2 = this.roundDouble(currentMot);
                            if (currentMot2> ib.getPlatformFeeTotal()){
                                builder.append("客户[").append(customerName)
                                        .append("]").append("当前申请金额："+currentMot2+"大于可申请平台手续费金额："+ib.getPlatformFeeTotal())
                                        .append("<br>");
                                importError = true;
                            }
                        }
                    }
                }

                if (importError){
                    request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

                    //remove the uploaded file
                    File file = new File(filePath);
                    if (file.exists()){
                        boolean success = file.delete();
                        _logger.info(filePath + " delete temp file:" + success);
                    } else{
                        _logger.info("***no temp file**************"+filePath);
                    }

                    return mapping.findForward("error");
                }

//        request.setAttribute("imp", true);
                request.setAttribute("import", true);

                //2015/5/12 根据客户分组
                if (type == TcpConstanst.IB_TYPE){
                    for (String name : customerToIbMap.keySet()){
                        TcpIbBean item = new TcpIbBean();
                        item.setCustomerName(name);
                        item.setIbMoney(this.roundDouble(customerToIbMap.get(name)));
                        item.setType(type);
                        item.setFullId(this.listToString(customerToOutMap.get(name)));
                        importItemList.add(item);
                    }
                } else if (type == TcpConstanst.MOTIVATION_TYPE){
                    for (String name : customerToMotivationMap.keySet()){
                        TcpIbBean item = new TcpIbBean();
                        item.setCustomerName(name);
                        item.setMotivationMoney(this.roundDouble(customerToMotivationMap.get(name)));
                        item.setType(type);
                        item.setFullId(this.listToString(customerToOutMap.get(name)));
                        importItemList.add(item);
                    }
                } else if (type == TcpConstanst.IB_TYPE2){
                    for (String name : customerToIbMap2.keySet()){
                        TcpIbBean item = new TcpIbBean();
                        item.setCustomerName(name);
                        item.setIbMoney2(this.roundDouble(customerToIbMap2.get(name)));
                        item.setType(type);
                        item.setFullId(this.listToString(customerToOutMap.get(name)));
                        importItemList.add(item);
                    }
                } else if (type == TcpConstanst.MOTIVATION_TYPE2) {
                    for (String name : customerToMotivationMap2.keySet()) {
                        TcpIbBean item = new TcpIbBean();
                        item.setCustomerName(name);
                        item.setMotivationMoney2(this.roundDouble(customerToMotivationMap2.get(name)));
                        item.setType(type);
                        item.setFullId(this.listToString(customerToOutMap.get(name)));
                        importItemList.add(item);
                    }
                } else if (type == TcpConstanst.PLATFORM_TYPE) {
                    for (String name : customerToPlatformMap.keySet()) {
                        TcpIbBean item = new TcpIbBean();
                        item.setCustomerName(name);
                        item.setPlatformFee(this.roundDouble(customerToPlatformMap.get(name)));
                        item.setType(type);
                        item.setFullId(this.listToString(customerToOutMap.get(name)));
                        importItemList.add(item);
                    }
                }
                rds.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

                return mapping.findForward("error");
            }
            finally
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    _logger.error(e, e);
                }
            }

        } else {
            ActionForward afor = parserAttachment(mapping, request, rds, bean);

            if (afor != null) {
                return afor;
            }
            rds.close();
        }

        StafferBean stafferBean = stafferDAO.find(bean.getStafferId());
        
        bean.setStype(stafferBean.getOtype());
        
        // 中收赋上纳税实体
        if (bean.isMidOrMotivation()) {
        	bean.setDutyId(PublicConstant.DEFAULR_DUTY_ID);
        }
        
        // 子项的组装
        fillTravel(rds, bean);
        try
        {
            User user = Helper.getUser(request);

            bean.setLogTime(TimeTools.now());
            if ("0".equals(addOrUpdate))
            {
                if (ListTools.isEmptyOrNull(bean.getTcpVSOutBeanList())){
                    List<TcpVSOutBean> tcpVSOutBeans = (List<TcpVSOutBean>)request.getSession().getAttribute("tcpVSOutBeans");
                    _logger.info("***get from session***"+tcpVSOutBeans);
                    bean.setTcpVSOutBeanList(tcpVSOutBeans);
                    request.getSession().removeAttribute("tcpVSOutBeans");
                }
                travelApplyManager.addTravelApplyBean(user, bean);
            }
            else
            {
                travelApplyManager.updateTravelApplyBean(user, bean);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功保存费用申请");
            // 提交
            if ("1".equals(oprType))
            {
                travelApplyManager.submitTravelApplyBean(user, bean.getId(), processId);
            }

            request.setAttribute(KeyConstant.MESSAGE, "成功提交费用申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "操作费用申请失败:" + e.getMessage());
        }

        if (bean.isMidOrMotivation()){
            return mapping.findForward("querySelfTravelApply7");
        } else{
            return mapping.findForward("querySelfTravelApply" + bean.getType());
        }
    }
    
    
    
    /**
     * addOrUpdateVocationAndWork
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addOrUpdateVocationAndWork(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException,MYException,Exception
    {
	        TravelApplyBean bean = new TravelApplyBean();
	        
	        User user = Helper.getUser(request);
	
	        BeanUtil.getBean(bean, request.getParameterMap());
	
	        String addOrUpdate = request.getParameter("addOrUpdate");
	        
	        String processId = request.getParameter("processId");
	        
	        StafferBean stafferBean = stafferDAO.find(user.getStafferId());
	        
	        String postid = stafferBean.getPostId();
	        
	        if(null == postid || ("").equals(postid.trim()) || postid.length()<1)
	        {
	        	return ActionTools.toError("职员属性不能为空，请修改您的职员属性", mapping, request);
	        }
	        
	        if(postid.equals("16"))
	        {
	        	bean.setStatus(TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO);
	        }
	        else if(postid.equals("20"))
	        {
	        	bean.setStatus(TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO);
	        }
	        else if (postid.equals("17")) {
	        	bean.setStatus(TcpConstanst.TCP_STATUS_WAIT_DEPUTYCEO);
	        }
	        else {
	        	bean.setStatus(TcpConstanst.TCP_STATUS_WAIT_DEPARTMENT);
	        }
	        
	        bean.setType(TcpConstanst.VOCATION_WORK);
	        
	        try
	        {
	        
	        if(bean.getPurposeType()==21)
	        {
	        	bean.setName("加班申请");
	        }
	        else if(bean.getPurposeType()==31)
	        {
	        	bean.setName("请假申请");
	        }
	        else if(bean.getPurposeType()==12)
	        {
	        	bean.setName("出差撤销");
	        	TravelApplyVO travevo = travelApplyManager.findVO(bean.getOldNumber());
	        	if(travevo == null)
	        	{
	        		return ActionTools.toError("申请单号错误，请重新提交", mapping, request);
	        	}
	        	else
	        	{
	        		String beginDate = bean.getBeginDate();
	              	String endDate = bean.getEndDate();
	              	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	          		long to = sdf.parse(endDate).getTime();
	          		long from = sdf.parse(beginDate).getTime();
	          		String beginDate1 = travevo.getBeginDate();
	              	String endDate1 = travevo.getEndDate();
	          		long to1 = sdf.parse(endDate1).getTime();
	          		long from1 = sdf.parse(beginDate1).getTime();
	          		if(((to - from) / (1000 * 60)) > ((to1 - from1) / (1000 * 60)) 
	          				&& to > to1 && from < from1)
	          		{
	          			return ActionTools.toError("原申请已过结束时间，无法撤销", mapping, request);
	          		}
	        	}
	        }
	        else if(bean.getPurposeType()==22)
	        {
	        	bean.setName("加班撤销");
	        	TravelApplyVO travevo = travelApplyManager.findVO(bean.getOldNumber());
	        	if(travevo == null)
	        	{
	        		return ActionTools.toError("申请单号错误，请重新提交", mapping, request);
	        	}
	        	else
	        	{
	        		if(travevo.getPurposeType()!=21)
	        		{
	        			return ActionTools.toError("此单号不属于加班类型，请重新提交", mapping, request);
	        		}
	        		String beginDate = bean.getBeginDate();
	              	String endDate = bean.getEndDate();
	              	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	          		long to = sdf.parse(endDate).getTime();
	          		long from = sdf.parse(beginDate).getTime();
	          		String beginDate1 = travevo.getBeginDate();
	              	String endDate1 = travevo.getEndDate();
	          		long to1 = sdf.parse(endDate1).getTime();
	          		long from1 = sdf.parse(beginDate1).getTime();
	          		if(((to - from) / (1000 * 60)) > ((to1 - from1) / (1000 * 60)) 
	          				&& to > to1 && from < from1)
	          		{
	          			throw new MYException("原申请已过结束时间，无法撤销");
	          		}
	        	}
	        }
	        else if(bean.getPurposeType()==32)
	        {
	        	bean.setName("请假撤销");
	        	TravelApplyVO travevo = travelApplyManager.findVO(bean.getOldNumber());
	        	if(travevo == null)
	        	{
	        		return ActionTools.toError("申请单号错误，请重新提交", mapping, request);
	        	}
	        	else
	        	{
	        		if(travevo.getPurposeType()!=31)
	        		{
	        			return ActionTools.toError("此单号不属于请假类型，请重新提交", mapping, request);
	        		}
	        		String beginDate = bean.getBeginDate();
	              	String endDate = bean.getEndDate();
	              	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	          		long to = sdf.parse(endDate).getTime();
	          		long from = sdf.parse(beginDate).getTime();
	          		String beginDate1 = travevo.getBeginDate();
	              	String endDate1 = travevo.getEndDate();
	          		long to1 = sdf.parse(endDate1).getTime();
	          		long from1 = sdf.parse(beginDate1).getTime();
	          		if(((to - from) / (1000 * 60)) > ((to1 - from1) / (1000 * 60)) 
	          				&& (to > to1 || from < from1))
	          		{
	          			return ActionTools.toError("原申请已过结束时间，无法撤销", mapping, request);
	          		}
	        	}
	        }
        
    			bean.setStype(stafferBean.getOtype());
    			
	            bean.setLogTime(TimeTools.now());
	            
	            if(bean.getPurposeType() == 31 && bean.getVocationType() == 7)
	        	{
			        String beginDate = bean.getBeginDate();
			    	String endDate = bean.getEndDate();
			    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					long to = sdf.parse(endDate).getTime();
					long from = sdf.parse(beginDate).getTime();
					long mins = (to - from) / (1000 * 60);//请假时间，以分钟为单位
//		            if("调休时间对比")//此处占位
//		            {
//		            	request.setAttribute(KeyConstant.ERROR_MESSAGE, "调休时间不能大于调休余额.");

//		            return mapping.findForward("error");
//		            }
	        	}
	            if ("0".equals(addOrUpdate))
	            {
	                travelApplyManager.addVocAndWorkTravelApplyBean(user, bean);
	            }
	            else
	            {
	                travelApplyManager.updateTravelApplyBean(user, bean);
	            }
	
	            // 提交
	            travelApplyManager.submitVocationAndWork(user, bean.getId(), processId);
	
	            request.setAttribute(KeyConstant.MESSAGE, "成功提交申请");
	        }
	        catch (MYException e)
	        {
	            _logger.warn(e, e);
	
	            request.setAttribute(KeyConstant.ERROR_MESSAGE, "申请操作失败:" + e.getMessage());
	        }

	        return mapping.findForward("queryAllVocationAndWork");
    }
    
    /**
     * findTraveApp
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findVocationAndWork(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)throws ServletException
    {
    	User user = Helper.getUser(request);

        String id = request.getParameter("id");

        String update = request.getParameter("update");

        TravelApplyVO bean = travelApplyManager.findVO(id);
        
        StafferVO staffervo = stafferDAO.findVO(bean.getStafferId());
        
        //prepareInner(request);
        request.setAttribute("update", update);
        request.setAttribute("bean", bean);
        request.setAttribute("staffervo", staffervo);
    	// 获取审批日志
        List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(id);

        List<FlowLogVO> logsVO = new ArrayList<FlowLogVO>();

        for (FlowLogBean flowLogBean : logs)
        {
            logsVO.add(TCPHelper.getTCPFlowLogVO(flowLogBean));
        }

        request.setAttribute("logList", logsVO);
        
        return mapping.findForward("updateVocationAndWork");
    }
    
    

    /**
     * rptQueryTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward rptQueryTravelApply(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        CommonTools.saveParamers(request);

        String cacheKey = RPTQUERYTRAVELAPPLY;

        final User user = Helper.getUser(request);

        List<TravelApplyVO> voList = ActionTools.commonQueryInPageSeparate(cacheKey, request,
            this.travelApplyDAO, new HandleQueryCondition()
            {
                public void setQueryCondition(HttpServletRequest request, ConditionParse condtion)
                {
                    String name = request.getParameter("name");

                    String id = request.getParameter("id");

                    String type = request.getParameter("type");

//                    String stype = request.getParameter("stype");
                    String stype = "";

                    if ( !StringTools.isNullOrNone(name))
                    {
                        condtion.addCondition("TravelApplyBean.name", "like", name);
                    }

                    if ( !StringTools.isNullOrNone(id))
                    {
                        condtion.addCondition("TravelApplyBean.id", "like", id);
                    }

                    if ( !StringTools.isNullOrNone(stype))
                    {
                        condtion.addIntCondition("TravelApplyBean.stype", "=", stype);
                    }

                    // 查询自己借款的单据
                    condtion.addCondition("TravelApplyBean.borrowStafferId", "=", user
                        .getStafferId());

                    // 查询结束(申请或借款,到财务支付,流程就算结束)
                    condtion.addIntCondition("TravelApplyBean.status", ">=",
                        TcpConstanst.TCP_STATUS_LAST_CHECK);

                    if ( !StringTools.isNullOrNone(type))
                    {
                        if ("98".equals(type))
                        {
                            condtion.addCondition("and TravelApplyBean.type in (2, 3)");
                        } else if ("7".equals(type))
                        {
                            condtion.addCondition("and TravelApplyBean.type in (7, 9, 16)");
                        }
                        else
                        {
                            condtion.addIntCondition("TravelApplyBean.type", "=", type);
                        }
                    }

                    condtion.addIntCondition("TravelApplyBean.feedback", "=",
                        TcpConstanst.TCP_APPLY_FEEDBACK_NO);
                }
            });

        for (TravelApplyVO travelApplyVO : voList)
        {
        	//add by zhangxian 2019-09-11
        	//判断申请单是否为每刻的单据,利用markettingflag来识别
        	String id = travelApplyVO.getId();
        	int count = mayCurConsumeSubmitDAO.countByCondition(" where oaorderid=?", id);
        	if(count == 1)
        	{
        		travelApplyVO.setMarketingFlag(1);
        	}
        	else
        	{
        		travelApplyVO.setMarketingFlag(0);
        	}
        	//end add
            TCPHelper.chageVO(travelApplyVO);
        }

        return mapping.findForward(cacheKey);
    }

    /**
     * 处理
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processTravelApplyBean(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {

    	//此处参数标识是不是带附件提交，如有附件则参数为null,走if逻辑处理
    	String tempStatus = request.getParameter("status");
    	//货比三家存有上传附件
    	if(null == tempStatus || tempStatus == "" )
    	{
    		
	        RequestDataStream rds = new RequestDataStream(request, 1024 * 1024 * 20L);
	        try
	        {
	            rds.parser();
	        }
	        catch (FileUploadBase.SizeLimitExceededException e)
	        {
	            _logger.error(e, e);
	
	            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:附件超过20M");
	
	            return mapping.findForward("error");
	        }
	        catch (Exception e)
	        {
	            _logger.error(e, e);
	
	            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败");
	
	            return mapping.findForward("error");
	        }
        
        String id = rds.getParameter("id");
        String oprType = rds.getParameter("oprType");
        String reason = rds.getParameter("reason");
        String processId = rds.getParameter("processId");
        String compliance = rds.getParameter("compliance");
        
        TravelApplyVO travelApplyVo = travelApplyManager.findVO(id);
        _logger.info("***travelApplyVO****"+travelApplyVo);
        Map<String, InputStream> streamMap = rds.getStreamMap();
        List<AttachmentBean> attachmentList = travelApplyVo.getAttachmentList() == null ?new ArrayList<AttachmentBean>():travelApplyVo.getAttachmentList();
        
        _logger.info("***streamMap.isEmpty()****"+streamMap.isEmpty());
        
        	if(!streamMap.isEmpty())
        	{
        		
		        for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
		        {
		            AttachmentBean attachmentBean = new AttachmentBean();
		
		            FileOutputStream out = null;
		
		            UtilStream ustream = null;
		
		            try
		            {
		                String savePath = mkdir(this.getAttachmentPath());
		
		                String fileAlais = SequenceTools.getSequence();
		
		                String fileName = FileTools.getFileName(rds.getFileName(entry.getKey()));
		
		                String rabsPath = '/' + savePath + '/' + fileAlais + "."
		                                  + FileTools.getFilePostfix(fileName).toLowerCase();
		
		                String filePath = this.getAttachmentPath() + '/' + rabsPath;
		                
		                attachmentBean.setName(fileName);
		
		                attachmentBean.setPath(rabsPath);
		
		                attachmentBean.setLogTime(TimeTools.now());
		
		                out = new FileOutputStream(filePath);
		
		                ustream = new UtilStream(entry.getValue(), out);
		
		                ustream.copyStream();
		
		                attachmentList.add(attachmentBean);
		            }
		            catch (IOException e)
		            {
		                _logger.error(e, e);
		
		                request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");
		
		            }
		            finally
		            {
		                if (ustream != null)
		                {
		                    try
		                    {
		                        ustream.close();
		                    }
		                    catch (IOException e)
		                    {
		                        _logger.error(e, e);
		                    }
		                }
		            }
		        }
        	}
        	
        if (StringTools.isNullOrNone(compliance))
            compliance = "";
        
	        try
	        {
	            User user = Helper.getUser(request);
	
	            TcpParamWrap param = new TcpParamWrap();
	
	            param.setId(id);
	            param.setType(oprType);
	            param.setReason(reason);
	            param.setProcessId(processId);
	            param.setCompliance(compliance);
	            
	            //param.setAttachmentList(attachmentList);
	            
	            if (travelApplyVo.getType() == TcpConstanst.TCP_APPLYTYPE_MID
                        ||travelApplyVo.getType() == TcpConstanst.TCP_APPLYTYPE_PUBLIC){
                    // #394 中收申请待财务支付时需要上传附件，改为从RDS中获取表单数据,form页面也改为multipart/form-data
                    fillWrap(rds, param);
                } else{
                    // 组装参数
                    fillWrap(request, param);
                }

	            travelApplyVo.setAttachmentList(attachmentList);
	            travelApplyManager.updateAttachmentList(user, travelApplyVo);
	            // 提交
	            if ("0".equals(oprType))
	            {
	                travelApplyManager.passTravelApplyBean(user, param);
	            }
	            else
	            {
	                travelApplyManager.rejectTravelApplyBean(user, param);
	            }
	            
	            request.setAttribute(KeyConstant.MESSAGE, "成功处理借款申请");
	        }
	        catch (MYException e)
	        {
	            _logger.warn(e, e);
	
	            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理借款申请失败:" + e.getMessage());
	        }
    	 }
    	else
    	{
    		 	String id = request.getParameter("id");
    	        String oprType = request.getParameter("oprType");
    	        String reason = request.getParameter("reason");
    	        String processId = request.getParameter("processId");
    	        String compliance = request.getParameter("compliance");
    	        if (StringTools.isNullOrNone(compliance))
    	            compliance = "";
    	        
    	        try
    	        {
    	            User user = Helper.getUser(request);

    	            TcpParamWrap param = new TcpParamWrap();

    	            param.setId(id);
    	            param.setType(oprType);
    	            param.setReason(reason);
    	            param.setProcessId(processId);
    	            param.setCompliance(compliance);

    	            // 组装参数
    	            fillWrap(request, param);

    	            // 提交
    	            if ("0".equals(oprType))
    	            {
    	                travelApplyManager.passTravelApplyBean(user, param);
    	            }
    	            else
    	            {
    	                travelApplyManager.rejectTravelApplyBean(user, param);
    	            }

    	            request.setAttribute(KeyConstant.MESSAGE, "成功处理借款申请");
    	        }
    	        catch (MYException e)
    	        {
    	            _logger.warn(e, e);

    	            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理借款申请失败:" + e.getMessage());
    	        }
    	}
        return mapping.findForward("querySelfApprove");
    }
    
    
    /**
     * 处理请假加班申请
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward processVocationAndWork(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException
    {

	        String id = request.getParameter("id");
	        String oprType = request.getParameter("oprType");
	        String reason = request.getParameter("reason");
	        String processId = request.getParameter("processId");
	        try
	        {
            User user = Helper.getUser(request);

            TcpParamWrap param = new TcpParamWrap();

            param.setId(id);
            param.setType(oprType);
            param.setReason(reason);
            param.setProcessId(processId);

            // 提交
            if ("0".equals(oprType))
            {
                travelApplyManager.passVocAndWorkTravelApplyBean(user, param);
            }
            else
            {
                travelApplyManager.rejectVocationAndWork(user, param);
            }
            
            request.setAttribute(KeyConstant.MESSAGE, "成功处理请假加班申请");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "处理请假加班申请失败:" + e.getMessage());
        }
        
        return mapping.findForward("querySelfApprove");
    }
    

    /**
     * fillWrap
     * 
     * @param request
     * @param param
     * @throws MYException
     */
    private void fillWrap(HttpServletRequest request, TcpParamWrap param)
        throws MYException
    {
        String[] ppid = request.getParameterValues("p_cid");
        // 稽核处理
        if (ppid != null && ppid.length > 0)
        {
            String[] pcmoneysList = request.getParameterValues("p_cmoneys");
            String[] pcdescriptionList = request.getParameterValues("p_cdescription");
            List<TravelApplyPayBean> payList = travelApplyPayDAO
                .queryEntityBeansByFK(param.getId());
            for (int i = 0; i < ppid.length; i++ )
            {
                for (TravelApplyPayBean travelApplyPayBean : payList)
                {
                    if (travelApplyPayBean.getId().equals(ppid[i]))
                    {
                        travelApplyPayBean.setCmoneys(MathTools.doubleToLong2(pcmoneysList[i]));
                        travelApplyPayBean.setCdescription(pcdescriptionList[i]);
                    }
                }
            }

            param.setOther(payList);
        }

        // 处理采购货比三家
        String[] cids = request.getParameterValues("i_cid");

        if (cids != null && cids.length > 0)
        {
            String[] checkPrices = request.getParameterValues("i_checkPrices");
            String[] moneys = request.getParameterValues("i_moneys");
            String[] purpose = request.getParameterValues("i_purpose");

            List<TravelApplyItemBean> list = new ArrayList();

            long m1 = 0L;
            for (int i = 0; i < cids.length; i++ )
            {
                if (StringTools.isNullOrNone(cids[i]))
                {
                    continue;
                }

                TravelApplyItemBean item = new TravelApplyItemBean();

                item.setId(cids[i]);
                item.setCheckPrices(MathTools.doubleToLong2(checkPrices[i]));
                item.setMoneys(MathTools.doubleToLong2(moneys[i]));
                item.setPurpose(purpose[i]);

                m1 += item.getMoneys();

                list.add(item);
            }

            param.setOther2(list);
        }
        
        String[] bankIds = request.getParameterValues("bankId");

        // 财务付款
        if (bankIds != null && bankIds.length > 0)
        {
            String[] payTypes = request.getParameterValues("payType");
            String[] moneys = request.getParameterValues("money");

            List<OutBillBean> outBillList = new ArrayList<OutBillBean>();

            for (int i = 0; i < bankIds.length; i++ )
            {
                if (StringTools.isNullOrNone(bankIds[i]))
                {
                    continue;
                }

                OutBillBean outBill = new OutBillBean();

                outBill.setBankId(bankIds[i]);

                outBill.setPayType(MathTools.parseInt(payTypes[i]));

                outBill.setMoneys(MathTools.parseDouble(moneys[i]));

                outBillList.add(outBill);
            }

            param.setOther(outBillList);
        }
        String dutyId = request.getParameter("dutyId");

        if (dutyId != null)
        {
            param.setDutyId(dutyId);
        }
    }

    private void fillWrap(RequestDataStream rds, TcpParamWrap param)
            throws MYException
    {
        _logger.info("****fillWrap***");
        List<String> ppid = rds.getParameters("p_cid");
        // 稽核处理
        if (ppid != null && ppid.size() > 0)
        {
            List<String> pcmoneysList = rds.getParameters("p_cmoneys");
            List<String> pcdescriptionList = rds.getParameters("p_cdescription");
            List<TravelApplyPayBean> payList = travelApplyPayDAO
                    .queryEntityBeansByFK(param.getId());
            for (int i = 0; i < ppid.size(); i++ )
            {
                for (TravelApplyPayBean travelApplyPayBean : payList)
                {
                    if (travelApplyPayBean.getId().equals(ppid.get(i)))
                    {
                        travelApplyPayBean.setCmoneys(MathTools.doubleToLong2(pcmoneysList.get(i)));
                        travelApplyPayBean.setCdescription(pcdescriptionList.get(i));
                    }
                }
            }

            param.setOther(payList);
        }

        // 处理采购货比三家
        List<String> cids = rds.getParameters("i_cid");

        if (cids != null && cids.size() > 0)
        {
            List<String> checkPrices = rds.getParameters("i_checkPrices");
            List<String> moneys = rds.getParameters("i_moneys");
            List<String> purpose = rds.getParameters("i_purpose");

            List<TravelApplyItemBean> list = new ArrayList();

            long m1 = 0L;
            for (int i = 0; i < cids.size(); i++ )
            {
                if (StringTools.isNullOrNone(cids.get(i)))
                {
                    continue;
                }

                TravelApplyItemBean item = new TravelApplyItemBean();

                item.setId(cids.get(i));
                item.setCheckPrices(MathTools.doubleToLong2(checkPrices.get(i)));
                item.setMoneys(MathTools.doubleToLong2(moneys.get(i)));
                item.setPurpose(purpose.get(i));

                m1 += item.getMoneys();

                list.add(item);
            }

            param.setOther2(list);
        }

        List<String> bankIds = rds.getParameters("bankId");
        // 财务付款
        if (bankIds != null && bankIds.size() > 0)
        {
            List<String> payTypes = rds.getParameters("payType");
            List<String> moneys = rds.getParameters("money");

            List<OutBillBean> outBillList = new ArrayList<OutBillBean>();

            for (int i = 0; i < bankIds.size(); i++ )
            {
                if (StringTools.isNullOrNone(bankIds.get(i)))
                {
                    continue;
                }

                OutBillBean outBill = new OutBillBean();

                outBill.setBankId(bankIds.get(i));

                outBill.setPayType(MathTools.parseInt(payTypes.get(i)));

                outBill.setMoneys(MathTools.parseDouble(moneys.get(i)));

                outBillList.add(outBill);
            }

            param.setOther(outBillList);
        }
        String dutyId = rds.getParameter("dutyId");

        if (dutyId != null)
        {
            param.setDutyId(dutyId);
        }
    }

    /**
     * 认领
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward drawApprove(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String ids = request.getParameter("ids");

            User user = Helper.getUser(request);

            String[] splits = ids.split(";");

            for (String id : splits)
            {
                if ( !StringTools.isNullOrNone(id))
                {
                    tcpFlowManager.drawApprove(user, id);
                }
            }

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * 退领
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward odrawApprove(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            tcpFlowManager.odrawApprove(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }
    
    /**
     * 强制结束
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward endApprove(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            tcpFlowManager.endApprove(user, id);

            ajax.setSuccess("成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("操作失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * exportTCP
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportTCP(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "TCP_" + TimeTools.now("MMddHHmmss") + ".csv";

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYSELFAPPROVE);

        int count = this.tcpApproveDAO.countVOByCondition(condtion.toString());

        try
        {
            out = response.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,标识,单号,目的,申请人,系列,单据类型,部门,付款类型,申请费用,稽核费用");

            PageSeparate page = new PageSeparate();

            page.reset2(count, 2000);

            WriteFileBuffer line = new WriteFileBuffer(write);

            while (page.nextPage())
            {
                List<TcpApproveVO> voFList = tcpApproveDAO
                    .queryEntityVOsByCondition(condtion, page);

                for (TcpApproveVO vo : voFList)
                {
                    TCPHelper.getTcpApproveVO(vo);

                    line.reset();

                    line.writeColumn("[" + vo.getLogTime() + "]");
                    line.writeColumn(vo.getId());
                    line.writeColumn(vo.getApplyId());
                    line.writeColumn(StringTools.getExportString(vo.getName()));
                    line.writeColumn(vo.getApplyerName());
                    line.writeColumn(ElTools.get("tcpStype", vo.getStype()));

                    line.writeColumn(ElTools.get("tcpType", vo.getType()));
                    line.writeColumn(changeString(vo.getDepartmentName()));
                    line.writeColumn(ElTools.get("tcpPayType", vo.getPayType()));
                    line.writeColumn(changeString(vo.getShowTotal()));
                    line.writeColumn(changeString(vo.getShowCheckTotal()));

                    line.writeLine();
                }
            }

            write.close();
        }
        catch (Throwable e)
        {
            _logger.error(e, e);

            return null;
        }
        finally
        {
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

            if (write != null)
            {

                try
                {
                    write.close();
                }
                catch (IOException e1)
                {
                }
            }
        }

        return null;
    }

    /**
     * exportTravelApply
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportTravelApply(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        OutputStream out = null;

        String filenName = "TCP_APPLY_" + TimeTools.now("MMddHHmmss") + ".csv";

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=" + filenName);

        WriteFile write = null;

        ConditionParse condtion = JSONPageSeparateTools.getCondition(request, QUERYALLTRAVELAPPLY);

        int count = this.travelApplyDAO.countVOByCondition(condtion.toString());

        try
        {
            out = response.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            write.writeLine("日期,标识,目的,申请人,系列,类型,状态,借款,关联报销,借款金额,申请费用,费用开始日期,费用结束日期,承担人,流程结束日期");

            PageSeparate page = new PageSeparate();

            page.reset2(count, 2000);

            WriteFileBuffer line = new WriteFileBuffer(write);

            while (page.nextPage())
            {
                List<TravelApplyVO> voFList = travelApplyDAO.queryEntityVOsByCondition(condtion,
                        page);

                for (TravelApplyVO vo : voFList)
                {
                    line.reset();

                    TCPHelper.chageVO(vo);

                    line.writeColumn("[" + vo.getLogTime() + "]");
                    line.writeColumn(vo.getId());
                    line.writeColumn(StringTools.getExportString(vo.getName()));
                    line.writeColumn(vo.getStafferName());

                    line.writeColumn(ElTools.get("tcpStype", vo.getStype()));
                    line.writeColumn(ElTools.get("tcpType", vo.getType()));
                    line.writeColumn(ElTools.get("tcpStatus", vo.getStatus()));
                    line.writeColumn(ElTools.get("travelApplyBorrow", vo.getBorrow()));
                    line.writeColumn(ElTools.get("tcpApplyFeedback", vo.getFeedback()));

                    line.writeColumn(changeString(vo.getShowBorrowTotal()));
                    line.writeColumn(changeString(vo.getShowTotal()));

                    //费用开始日期和费用结束日期
                    List<TravelApplyItemVO> itemVOList = travelApplyItemDAO.queryEntityVOsByFK(vo.getId());
                    if (ListTools.isEmptyOrNull(itemVOList)){
                        line.writeColumn("");
                        line.writeColumn("");
                    } else{
                        line.writeColumn(itemVOList.get(0).getBeginDate());
                        line.writeColumn(itemVOList.get(0).getEndDate());
                    }

                    //取费用分担里面“承担人”字段
                    List<TcpShareVO> shareVOList = tcpShareDAO.queryEntityVOsByFK(vo.getId());
                    if(ListTools.isEmptyOrNull(shareVOList)){
                        line.writeColumn("");
                    } else{
                        line.writeColumn(shareVOList.get(0).getBearName());
                    }

                    //#370
                    List<FlowLogBean> logs = flowLogDAO.queryEntityBeansByFK(vo.getId());
                    line.writeColumn(FlowLogBean.getFinishTime(logs));
                    line.writeLine();
                }
            }

            write.close();
        }
        catch (Throwable e)
        {
            _logger.error(e, e);

            return null;
        }
        finally
        {
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

            if (write != null)
            {

                try
                {
                    write.close();
                }
                catch (IOException e1)
                {
                }
            }
        }

        return null;
    }

    private String changeString(String str)
    {
        return str.replaceAll(",", "");
    }

    /**
     * 变成long
     * 
     * @param bean
     * @param rds
     */
    private void changeTravel(TravelApplyBean bean, RequestDataStream rds)
    {
        String airplaneCharges = rds.getParameter("airplaneCharges");

        bean.setAirplaneCharges(TCPHelper.doubleToLong2(airplaneCharges));

        String trainCharges = rds.getParameter("trainCharges");

        bean.setTrainCharges(TCPHelper.doubleToLong2(trainCharges));

        String busCharges = rds.getParameter("busCharges");

        bean.setBusCharges(TCPHelper.doubleToLong2(busCharges));

        String hotelCharges = rds.getParameter("hotelCharges");

        bean.setHotelCharges(TCPHelper.doubleToLong2(hotelCharges));

        String entertainCharges = rds.getParameter("entertainCharges");

        bean.setEntertainCharges(TCPHelper.doubleToLong2(entertainCharges));

        String allowanceCharges = rds.getParameter("allowanceCharges");

        bean.setAllowanceCharges(TCPHelper.doubleToLong2(allowanceCharges));

        String other1Charges = rds.getParameter("other1Charges");

        bean.setOther1Charges(TCPHelper.doubleToLong2(other1Charges));

        String other2Charges = rds.getParameter("other2Charges");

        bean.setOther2Charges(TCPHelper.doubleToLong2(other2Charges));
    }

    private long getTravelItemTotal(TravelApplyBean bean)
    {
        long total = 0L;

        total += bean.getAirplaneCharges();
        total += bean.getTrainCharges();

        total += bean.getBusCharges();
        total += bean.getHotelCharges();

        total += bean.getEntertainCharges();
        total += bean.getAllowanceCharges();

        total += bean.getOther1Charges();
        total += bean.getOther2Charges();

        return total;
    }

    /**
     * fillTravel
     * 
     * @param rds
     * @param bean
     */
    private void fillTravel(RequestDataStream rds, TravelApplyBean bean)
    {
        // 费用明细
        List<TravelApplyItemBean> itemList = new ArrayList<TravelApplyItemBean>();

        bean.setItemList(itemList);
        // i_beginDate
        List<String> beginDateList = rds.getParameters("i_beginDate");
        List<String> productNameList = rds.getParameters("i_productName");
        List<String> amountList = rds.getParameters("i_amount");
        List<String> pricesList = rds.getParameters("i_prices");
        List<String> endDateList = rds.getParameters("i_endDate");
        List<String> feeItemList = rds.getParameters("i_feeItem");
        List<String> moneysList = rds.getParameters("i_moneys");
        List<String> descriptionList = rds.getParameters("i_description");

        // 存在没有的可能
        if (feeItemList != null && feeItemList.size() > 0)
        {
            for (int i = 0; i < feeItemList.size(); i++ )
            {
                String each = feeItemList.get(i);

                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                // 过滤差旅费
                if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_TRAVEL
                    && BudgetConstant.FEE_ITEM_TRAVELLING.equals(each))
                {
                    continue;
                }
                TravelApplyItemBean item = new TravelApplyItemBean();

                if (beginDateList != null && beginDateList.size() > 0)
                {
                    item.setBeginDate(beginDateList.get(i));
                }

                if (endDateList != null && endDateList.size() > 0)
                {
                    item.setEndDate(endDateList.get(i));
                }

                if (productNameList != null && productNameList.size() > 0)
                {
                    item.setProductName(productNameList.get(i));
                }

                if (amountList != null && amountList.size() > 0)
                {
                    item.setAmount(MathTools.parseInt(amountList.get(i)));
                }

                if (pricesList != null && pricesList.size() > 0)
                {
                    item.setPrices(MathTools.doubleToLong2(pricesList.get(i)));
                }
                item.setFeeItemId(feeItemList.get(i));
                item.setMoneys(TCPHelper.doubleToLong2(moneysList.get(i)));
                item.setDescription(descriptionList.get(i));

                itemList.add(item);
            }
        }

        // 特殊处理
        if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_TRAVEL)
        {
            // 自动组装差旅费
            TravelApplyItemBean travelItem = new TravelApplyItemBean();
            travelItem.setBeginDate(bean.getBeginDate());
            travelItem.setEndDate(bean.getEndDate());
            travelItem.setFeeItemId(BudgetConstant.FEE_ITEM_TRAVELLING);
            travelItem.setMoneys(getTravelItemTotal(bean));
            travelItem.setDescription("系统自动组装的差旅费");

            itemList.add(0, travelItem);
        }

        // 总费用
        long total = 0L;

        for (TravelApplyItemBean each : itemList)
        {
            total += each.getMoneys();
        }

        bean.setTotal(total);

        // 采购默认全部借款
        if (bean.getType() == TcpConstanst.TCP_APPLYTYPE_STOCK)
        {
            bean.setBorrowTotal(bean.getTotal());
        }
        List<TravelApplyPayBean> payList = new ArrayList<TravelApplyPayBean>();

        bean.setPayList(payList);
        // 收款明细
        if (bean.getBorrow() == TcpConstanst.TRAVELAPPLY_BORROW_YES)
        {
            List<String> receiveTypeList = rds.getParameters("p_receiveType");
            List<String> bankList = rds.getParameters("p_bank");
            List<String> userNameList = rds.getParameters("p_userName");
            List<String> bankNoList = rds.getParameters("p_bankNo");
            List<String> pmoneysList = rds.getParameters("p_moneys");
            List<String> pdescriptionList = rds.getParameters("p_description");
            List<String> bankprovinceList = rds.getParameters("bankprovince");
            List<String> bankcityList = rds.getParameters("bankcity");

            if (receiveTypeList != null && receiveTypeList.size() > 0)
            {

                for (int i = 0; i < receiveTypeList.size(); i++ )
                {
                    String each = receiveTypeList.get(i);

                    if (StringTools.isNullOrNone(each))
                    {
                        continue;
                    }

                    TravelApplyPayBean pay = new TravelApplyPayBean();

                    pay.setReceiveType(MathTools.parseInt(receiveTypeList.get(i)));
                    pay.setBankName(bankList.get(i));
                    pay.setUserName(userNameList.get(i));
                    pay.setBankNo(bankNoList.get(i));
                    pay.setMoneys(TCPHelper.doubleToLong2(pmoneysList.get(i)));
                    pay.setDescription(pdescriptionList.get(i));
                    pay.setBankprovince(bankprovinceList.get(i));
                    pay.setBankcity(bankcityList.get(i));

                    payList.add(pay);
                }

                long paytotal = 0L;

                for (TravelApplyPayBean each : payList)
                {
                    paytotal += each.getMoneys();
                }

                bean.setBorrowTotal(paytotal);
            }
        }

        // 费用分担
        List<TcpShareBean> shareList = new ArrayList<TcpShareBean>();

        bean.setShareList(shareList);

        List<String> budgetIdeList = rds.getParameters("s_budgetId");
        List<String> departmentIdList = rds.getParameters("s_departmentId");
        List<String> approverIdList = rds.getParameters("s_approverId");
        List<String> bearIdList = rds.getParameters("s_bearId");
        List<String> ratioList = rds.getParameters("s_ratio");
        int rtotal = 0;
        if(null != ratioList && ratioList.size() > 0)
        {
	        for (String each : ratioList)
	        {
	            rtotal += MathTools.parseInt(each);
	        }
        }
        int shareType = 0;

        if (rtotal != 100)
        {
            shareType = 1;
        }

        if(null != bearIdList && bearIdList.size() > 0 )
        {
	        for (int i = 0; i < bearIdList.size(); i++ )
	        {
	            String each = bearIdList.get(i);
	
	            if (StringTools.isNullOrNone(each))
	            {
	                continue;
	            }
	
	            TcpShareBean share = new TcpShareBean();

	            if (budgetIdeList != null){
                    share.setBudgetId(budgetIdeList.get(i));
                }

                if (departmentIdList!= null){
                    share.setDepartmentId(departmentIdList.get(i));
                }

                if (approverIdList!= null){
                    share.setApproverId(approverIdList.get(i));
                }
	
	            if (bearIdList == null || bearIdList.size() < i
	                || StringTools.isNullOrNone(bearIdList.get(i)))
	            {
	                share.setBearId(bean.getStafferId());
	            }
	            else
	            {
	                share.setBearId(bearIdList.get(i));
	            }
	
	            // 自动识别是分担还是金额
	            if (shareType == 0)
	            {
	                share.setRatio(MathTools.parseInt(ratioList.get(i).trim()));
	            }
	            else
	            {
	                share.setRealMonery(TCPHelper.doubleToLong2(ratioList.get(i).trim()));
	            }
	
	            shareList.add(share);
	        }
        }

        // 中收激励明细
        _logger.info("********read ib parameters***********");
        List<TcpIbBean> ibList = new ArrayList<TcpIbBean>();

        List<String> typeList = rds.getParameters("ib_type");
        List<String> customerNameList = rds.getParameters("customerName");
        List<String> fullIdList = rds.getParameters("fullId");
//        List<String> productNameList2 = rds.getParameters("productName");
//        List<String> amountList2 = rds.getParameters("amount");
        List<String> ibMoneyList = rds.getParameters("ibMoney");
        List<String> motivationMoneyList = rds.getParameters("motivationMoney");
        List<String> ibMoneyList2 = rds.getParameters("ibMoney2");
        List<String> motivationMoneyList2 = rds.getParameters("motivationMoney2");
        List<String> platformFeeList2 = rds.getParameters("platformFee");

        if(!ListTools.isEmptyOrNull(typeList))
        {
            for (int i = 0; i < typeList.size(); i++ )
            {
                String type = typeList.get(i);

                if (StringTools.isNullOrNone(type))
                {
                    continue;
                }

                TcpIbBean ib = new TcpIbBean();

                ib.setType(Integer.valueOf(type));
                ib.setCustomerName(customerNameList.get(i));
                ib.setFullId(fullIdList.get(i));
//                ib.setProductName(productNameList2.get(i));
//                ib.setAmount(Integer.valueOf(amountList2.get(i)));
                if (ibMoneyList!= null) {
                    ib.setIbMoney(MathTools.parseDouble(ibMoneyList.get(i)));
                }
                if (motivationMoneyList!= null) {
                    ib.setMotivationMoney(MathTools.parseDouble(motivationMoneyList.get(i)));
                }

                if (ibMoneyList2!= null) {
                    ib.setIbMoney2(MathTools.parseDouble(ibMoneyList2.get(i)));
                }
                if (motivationMoneyList2!= null) {
                    ib.setMotivationMoney2(MathTools.parseDouble(motivationMoneyList2.get(i)));
                }

                if (platformFeeList2!= null){
                    ib.setPlatformFee(MathTools.parseDouble(platformFeeList2.get(i)));
                }

                ibList.add(ib);
            }
        }

        if(!ListTools.isEmptyOrNull(ibList)){
            bean.setIbList(ibList);
        }
        _logger.info("*********ibList size****"+ibList.size());
    }

    /**
     * parserAttachment
     * 
     * @param mapping
     * @param request
     * @param rds
     * @param travelApply
     * @return
     */
    private ActionForward parserAttachment(ActionMapping mapping, HttpServletRequest request,
                                           RequestDataStream rds, TravelApplyBean travelApply)
    {
        List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

        travelApply.setAttachmentList(attachmentList);

        String addOrUpdate = rds.getParameter("addOrUpdate");

        // 更新新加入之前
        if ("1".equals(addOrUpdate))
        {
            String attacmentIds = rds.getParameter("attacmentIds");

            String[] split = attacmentIds.split(";");

            for (String each : split)
            {
                if (StringTools.isNullOrNone(each))
                {
                    continue;
                }

                AttachmentBean att = attachmentDAO.find(each);

                if (att != null)
                {
                    attachmentList.add(att);
                }
            }
        }

        //2015/5/14 add imported IB attachment
        String importIbPath = rds.getParameter("importIbPath");
        String importIbName = rds.getParameter("importIbName");
        _logger.info("*****importIbPath*************"+importIbPath+"***importIbName***"+importIbName);
        if (!StringTools.isNullOrNone(importIbPath)){
            AttachmentBean bean = new AttachmentBean();
            bean.setName(importIbName);
            bean.setPath(importIbPath);
            bean.setLogTime(TimeTools.now());
            attachmentList.add(bean);
            _logger.info("*****create AttachmentBean for IB import*************"+bean);
        }

        // parser attachment
        if ( !rds.haveStream())
        {
            return null;
        }

        Map<String, InputStream> streamMap = rds.getStreamMap();

        for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
        {
            AttachmentBean bean = new AttachmentBean();

            FileOutputStream out = null;

            UtilStream ustream = null;

            try
            {
                String savePath = mkdir(this.getAttachmentPath());

                String fileAlais = SequenceTools.getSequence();

                String fileName = FileTools.getFileName(rds.getFileName(entry.getKey()));

                String rabsPath = '/' + savePath + '/' + fileAlais + "."
                                  + FileTools.getFilePostfix(fileName).toLowerCase();

                String filePath = this.getAttachmentPath() + '/' + rabsPath;

                bean.setName(fileName);

                bean.setPath(rabsPath);

                bean.setLogTime(TimeTools.now());

                out = new FileOutputStream(filePath);

                ustream = new UtilStream(entry.getValue(), out);

                ustream.copyStream();

                attachmentList.add(bean);
            }
            catch (IOException e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

                return mapping.findForward("querySelfTravelApply");
            }
            finally
            {
                if (ustream != null)
                {
                    try
                    {
                        ustream.close();
                    }
                    catch (IOException e)
                    {
                        _logger.error(e, e);
                    }
                }
            }
        }



        return null;
    }

    private String mkdir(String root)
    {
        String path = TimeTools.now("yyyy/MM/dd/HH") + "/"
                      + SequenceTools.getSequence(String.valueOf(new Random().nextInt(1000)));

        FileTools.mkdirs(root + '/' + path);

        return path;
    }

    /**
     * @return the flowAtt
     */
    public String getAttachmentPath()
    {
        return ConfigLoader.getProperty("tcpAttachmentPath");
    }

    /**
     * 导入费用分摊比例 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importShare(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response)
	throws ServletException
	{
        RequestDataStream rds = new RequestDataStream(request);
        
        boolean importError = false;
        
        List<TcpShareVO> importItemList = new ArrayList<TcpShareVO>(); 
        
        StringBuilder builder = new StringBuilder();       
        
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importShare");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importShare");
        }
        
        String type = rds.getParameter("type");

        request.setAttribute("type", type);
        
        ReaderFile reader = ReadeFileFactory.getXLSReader();
        
        try
        {
            reader.readFile(rds.getUniqueInputStream());

            while (reader.hasNext())
            {
                String[] obj = fillObj((String[])reader.next());

                // 第一行忽略
                if (reader.getCurrentLineNumber() == 1)
                {
                    continue;
                }

                if (StringTools.isNullOrNone(obj[0]))
                {
                    continue;
                }
                
                int currentNumber = reader.getCurrentLineNumber();

                if (obj.length >= 2 )
                {
                	TcpShareVO item = new TcpShareVO();

                	// 预算
                    if ( !StringTools.isNullOrNone(obj[0]))
                    {
                        String name = obj[0];

                        BudgetBean bean = budgetDAO.findByUnique(name);

                        if (null == bean)
                        {
                            builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("预算不存在")
                                .append("</font><br>");

                            importError = true;
                        }
                        else
                        {
                        	//有效性检查
                            if (bean.getCarryStatus() != 1)
                            {
                                builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("不是执行中的预算")
                                .append("</font><br>");

                                importError = true;
                            }

                            if (bean.getType() != 2)
                            {
                                builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("不是部门预算")
                                    .append("</font><br>");

                                importError = true;
                            }

                            if (bean.getLevel() != 2)
                            {
                                builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("不是月度预算")
                                .append("</font><br>");

                                importError = true;
                            }

                            if (bean.getStatus() != 3)
                            {
                                builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("不存在通过状态预算")
                                .append("</font><br>");

                                importError = true;
                            }

                        }

                        if (!importError)
                        {
                            item.setBudgetId(bean.getId());
                            item.setBudgetName(bean.getName());
                            item.setDepartmentId(bean.getBudgetDepartment());

                            PrincipalshipBean prin = principalshipDAO.find(bean.getBudgetDepartment());

                            if (null != prin)
                            	item.setDepartmentName(prin.getName());

                            item.setApproverId(bean.getSigner());
                            
                            StafferBean staffer = stafferDAO.find(bean.getSigner());
                            
                            if (null != staffer)
                            	item.setApproverName(staffer.getName());
                        }

                    }

                    // 承担人
                    if ( !StringTools.isNullOrNone(obj[1]))
                    {
                        String name = obj[1];

                        StafferBean staff = stafferDAO.findByUnique(name);

                        if (null == staff)
                        {
                            builder
                            .append("<font color=red>第[" + currentNumber + "]行错误:")
                            .append("承担人不存在")
                            .append("</font><br>");

                            importError = true;
                        }
                        else
                        {
                        	item.setBearId(staff.getId());
                            item.setBearName(staff.getName());
                        }
                    }

                    // 分摊比例/金额
                    if ( !StringTools.isNullOrNone(obj[2]))
                    {
                        String showRealMonery = obj[2];

                        item.setShowRealMonery(showRealMonery);

                    }
                    
                    importItemList.add(item);
                    
                }
                else
                {
                    builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("数据长度不足2格错误")
                        .append("<br>");
                    
                    importError = true;
                }
            }
            
            
        }
        catch (Exception e)
        {
            _logger.error(e, e);
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importShare");
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                _logger.error(e, e);
            }
        }
        
        rds.close();
        
        if (importError){
            
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward("importShare");
        }
        
        request.setAttribute("imp", true);
        
        TravelApplyVO bean = new TravelApplyVO();

        bean.setShareVOList(importItemList);
        
        request.setAttribute("bean", bean);
        
        prepareInner(request);
        
        int itype = MathTools.parseInt(type);

        if (itype <= 10)
        {
            if (itype == 8){
                return mapping.findForward("addTravelApply8import");
            } else{
                return mapping.findForward("addTravelApply" + type);
            }
        }
        
        return mapping.findForward("addExpense" + type);
	}

    /**
     * #231 导入银行业务部层级关系
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importBankBuLevel(ActionMapping mapping, ActionForm form,
                                           HttpServletRequest request,
                                           HttpServletResponse response)
            throws ServletException
    {
        _logger.info("***importBankBuLevel***");
        RequestDataStream rds = new RequestDataStream(request);

        boolean importError = false;

        List<BankBuLevelBean> importItemList = new ArrayList<BankBuLevelBean>();

        StringBuilder builder = new StringBuilder();

        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBankBuLevel");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importBankBuLevel");
        }

        ReaderFile reader = ReadeFileFactory.getXLSReader();

        try
        {
            reader.readFile(rds.getUniqueInputStream());

            while (reader.hasNext())
            {
                String[] obj = fillObj20((String[])reader.next());

                // 第一行忽略
                if (reader.getCurrentLineNumber() == 1)
                {
                    continue;
                }

                if (StringTools.isNullOrNone(obj[0]))
                {
                    continue;
                }

                int currentNumber = reader.getCurrentLineNumber();

                if (obj.length >= 2 )
                {
                    BankBuLevelBean item = new BankBuLevelBean();

                    // 专员编码
                    if ( !StringTools.isNullOrNone(obj[0]))
                    {
                        String id = obj[0];
                        item.setId(id);
                    }else{
                        builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("专员编码必填")
                                .append("</font><br>");

                        importError = true;
                    }

                    // 专员姓名
                    if ( !StringTools.isNullOrNone(obj[1]))
                    {
                        String name = obj[1];
                        item.setName(name);

                        ConditionParse conditionParse = new ConditionParse();
                        conditionParse.addCondition("id","=",item.getId());
                        conditionParse.addCondition("name","=",item.getName());
                        List<StafferBean> stafferBeans = this.stafferDAO.queryEntityBeansByCondition(conditionParse);
                        if (ListTools.isEmptyOrNull(stafferBeans)){
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("专员编码与人员必须与oaSTAFFER表里的 ID,NAME 一致")
                                    .append("</font><br>");

                            importError = true;
                        }
                    } else{
                        builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("专员名称必填")
                                .append("</font><br>");

                        importError = true;
                    }

                    //省级团队
                    if ( !StringTools.isNullOrNone(obj[2]))
                    {
                        String provinceName = obj[2];
                        item.setProvinceName(provinceName);
                    }

                    //省级经理编码
                    if ( !StringTools.isNullOrNone(obj[3]))
                    {
                        String provinceManagerId = obj[3];
                        item.setProvinceManagerId(provinceManagerId);
                    }

                    //省级经理姓名
                    if ( !StringTools.isNullOrNone(obj[4]))
                    {
                        String provinceManager = obj[4];
                        item.setProvinceManager(provinceManager);
                    }

                    //二级区域
                    if ( !StringTools.isNullOrNone(obj[5]))
                    {
                        String regionalName = obj[5];
                        item.setRegionalName(regionalName);
                    }

                    //区域经理编码
                    if ( !StringTools.isNullOrNone(obj[6]))
                    {
                        String regionalManagerId = obj[6];
                        item.setRegionalManagerId(regionalManagerId);
                    }

                    //区域经理姓名
                    if ( !StringTools.isNullOrNone(obj[7]))
                    {
                        String regionalManager = obj[7];
                        item.setRegionalManager(regionalManager);
                    }

                    //大区
                    if ( !StringTools.isNullOrNone(obj[8]))
                    {
                        String dqName = obj[8];
                        item.setDqName(dqName);
                    }


                    //大区总编码
                    if ( !StringTools.isNullOrNone(obj[9]))
                    {
                        String regionalDirectorId = obj[9];
                        item.setRegionalDirectorId(regionalDirectorId);
                    }

                    //大区总姓名
                    if ( !StringTools.isNullOrNone(obj[10]))
                    {
                        String regionalDirector = obj[10];
                        item.setRegionalDirector(regionalDirector);
                    }

                    importItemList.add(item);
                }
                else
                {
                    builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("数据长度不足2格错误")
                            .append("<br>");

                    importError = true;
                }
            }
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importBankBuLevel");
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                _logger.error(e, e);
            }
        }

        rds.close();

        if (importError){

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward("importBankBuLevel");
        } else{
            try{
                this.travelApplyManager.importBankBulevel(null, importItemList);

                request.setAttribute(KeyConstant.MESSAGE, "导入成功");
            }catch(Exception e){
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getMessage());

                return mapping.findForward("importBankBuLevel");
            }
        }

        return mapping.findForward("importBankBuLevel");
    }

    /**
     * 查询预算项
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryBudget(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        _logger.info("***queryBudget***");

        JsonMapper mapper = new JsonMapper();
        AppResult result = new AppResult();

        List<BudgetVO> budgetBeans = null;

        try
        {
            ConditionParse conditionParse = new ConditionParse();
            conditionParse.addIntCondition("BudgetBean.carryStatus","=",BudgetConstant.BUDGET_CARRY_DOING);
            conditionParse.addIntCondition("BudgetBean.type","=",BudgetConstant.BUDGET_TYPE_DEPARTMENT);
            conditionParse.addIntCondition("BudgetBean.level","=",BudgetConstant.BUDGET_LEVEL_MONTH);
            conditionParse.addIntCondition("BudgetBean.status", "=", BudgetConstant.BUDGET_STATUS_PASS);
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String today = sdf.format(date);

            conditionParse.addCondition("BudgetBean.endDate", ">=", today);
            conditionParse.addCondition("BudgetBean.beginDate","<=",today);

            budgetBeans = this.budgetDAO.queryEntityVOsByCondition(conditionParse);

            result.setSuccessAndObj("操作成功", budgetBeans);
        }
        catch(Exception e)
        {
            _logger.warn(e, e);

            result.setError("创建失败");
        }

        String jsonstr = mapper.toJson(result);

        _logger.info("***queryBudget***" + jsonstr);

        return JSONTools.writeResponse(reponse, jsonstr);
    }

    /**
     * #231 经理查询
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryZy(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        String bearType = request.getParameter("bearType");
        _logger.info("***queryZy***"+bearType);

        JsonMapper mapper = new JsonMapper();
        AppResult result = new AppResult();

        try
        {
            List<BankBuLevelBean> bankBuLevelBeans = this.bankBuLevelDAO.queryByBearType(bearType);
            this.filterObsoltedStaffer(bankBuLevelBeans);

            result.setSuccessAndObj("操作成功", bankBuLevelBeans);
        }
        catch(Exception e)
        {
            _logger.warn(e, e);

            result.setError("创建失败");
        }

        String jsonstr = mapper.toJson(result);

        _logger.info("***queryZy result***" + jsonstr);

        return JSONTools.writeResponse(reponse, jsonstr);
    }

    /**
     * 下属专员查询 #231
     * @param mapping
     * @param form
     * @param request
     * @param reponse
     * @return
     * @throws ServletException
     */
    public ActionForward queryZy2(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse reponse)
            throws ServletException
    {
        String bearType = request.getParameter("bearType");
        String manager = request.getParameter("manager");
        String budgetId = request.getParameter("budgetId");
        _logger.info("***queryZy2***"+bearType+"***"+manager+"***"+budgetId);

        JsonMapper mapper = new JsonMapper();
        AppResult result = new AppResult();

        List<BankBuLevelBean> bankBuLevelBeans = this.bankBuLevelDAO.queryByBearTypeAndManager(bearType,manager);
        this.filterObsoltedStaffer(bankBuLevelBeans);
        result.setSuccessAndObj("操作成功", bankBuLevelBeans);

        if (!StringTools.isNullOrNone(budgetId)){
            BudgetVO budgetVO = this.budgetDAO.findVO(budgetId);

            PrincipalshipBean org = orgManager.findPrincipalshipById(budgetVO.getBudgetDepartment());

            if (org != null) {
                budgetVO.setBudgetFullDepartmentName(org.getFullName());
            }
            result.setExtraObj(budgetVO);
        }

        String jsonstr = mapper.toJson(result);

        _logger.info("***queryZy2 result***"+jsonstr);

        return JSONTools.writeResponse(reponse, jsonstr);
    }

    private void filterObsoltedStaffer(List<BankBuLevelBean> bankBuLevelBeans){
        //#57
        if(!ListTools.isEmptyOrNull(bankBuLevelBeans)){
            for (Iterator<BankBuLevelBean> iterator=bankBuLevelBeans.iterator();iterator.hasNext();){
                BankBuLevelBean bean = iterator.next();
                if (!StringTools.isNullOrNone(bean.getId())){
                    StafferBean stafferBean = this.stafferDAO.find(bean.getId());
                    if (stafferBean!= null && stafferBean.getStatus()== StafferConstant.STATUS_DROP)
                    {
                        iterator.remove();
                    }
                }
            }
        }
    }


    /**
     * 导入银行中收激励数据
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importIb(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException
    {
        double begin = System.currentTimeMillis();
        RequestDataStream rds = new RequestDataStream(request);

        boolean importError = false;

        List<TcpIbBean> importItemList = new ArrayList<TcpIbBean>();

        List<TcpVSOutBean> importItemList2 = new ArrayList<>();

        //#596 重复行监测
        Set<String> importItems = new HashSet<>();

        StringBuilder builder = new StringBuilder();
        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importShare");
        }
        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importIb");
        }

        //<customerName,ibMoneyTotal>
        Map<String, Double>  customerToIbMap = new HashMap<String,Double>();
        //<customerName,motivationMoneyTotal>
        Map<String, Double>  customerToMotivationMap = new HashMap<String,Double>();

        //<customerName,ibMoneyTotal2>
        Map<String, Double>  customerToIbMap2 = new HashMap<String,Double>();
        //<customerName,motivationMoneyTotal2>
        Map<String, Double>  customerToMotivationMap2 = new HashMap<String,Double>();

        //<customerName,platformFeeTotal>
        Map<String, Double>  customerToPlatformMap = new HashMap<String,Double>();

        //<customerName,List<outId>>
        Map<String, List<String>>  customerToOutMap = new HashMap<String,List<String>>();

        ReaderFile reader = ReadeFileFactory.getXLSReader();
        int type = 0;

        TravelApplyVO vo = new TravelApplyVO();
        vo.setIbList(importItemList);
        vo.setImportFlag(true);

        //save attachment
        String filePath = this.parserIbAttachment(request,rds,vo) ;

        if ( StringTools.isNullOrNone(filePath))
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "附件上传失败");

            return mapping.findForward("importIb");
        }

        List<TcpShareVO> shareList = new ArrayList<TcpShareVO>();
        List<CustomerBean> customerBeans = customerMainDAO.listEntityBeans();
        List<ProductBean> productBeans = productDAO.listEntityBeans();
        List<BudgetBean> budgetBeans = budgetDAO.listEntityBeans();
        List<TcpVSOutBean> tcpVSOutBeans = tcpVSOutDAO.listEntityBeans();
        try
        {
            FileInputStream fs = new FileInputStream(filePath);
            reader.readFile(fs);

            String ibType = "";

            while (reader.hasNext())
            {
                String[] obj = fillObj2((String[])reader.next());

                // 第一行忽略
                if (reader.getCurrentLineNumber() == 1)
                {
                    continue;
                }

                if (StringTools.isNullOrNone(obj[0]))
                {
                    continue;
                }

                int currentNumber = reader.getCurrentLineNumber();

                if (obj.length >= 2 )
                {
                    TcpIbBean item = new TcpIbBean();
                    TcpVSOutBean vsOutBean = new TcpVSOutBean();
                    // 申请类型
                    if ( !StringTools.isNullOrNone(obj[0]))
                    {
                        String name = obj[0];

                        if (currentNumber == 2){
                            ibType = name;
                        }

                        //导入模板中申请类型必须一致
                        if (!ibType.equals(name)){
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("申请类型必须一致")
                                    .append("</font><br>");

                            importError = true;
                        }

                        if (TcpConstanst.IB_TYPE_STR.equals(name)){
                            item.setType(TcpConstanst.IB_TYPE);
                            type = TcpConstanst.IB_TYPE;
                        } else if (TcpConstanst.MOTIVATION_TYPE_STR.equals(name)){
                            item.setType(TcpConstanst.MOTIVATION_TYPE);
                            type = TcpConstanst.MOTIVATION_TYPE;
                        } else if (TcpConstanst.IB_TYPE_STR2.equals(name)){
                            item.setType(TcpConstanst.IB_TYPE2);
                            type = TcpConstanst.IB_TYPE2;
                        } else if (TcpConstanst.MOTIVATION_TYPE_STR2.equals(name)){
                            item.setType(TcpConstanst.MOTIVATION_TYPE2);
                            type = TcpConstanst.MOTIVATION_TYPE2;
                        } else if (TcpConstanst.PLATFORM_TYPE_STR.equals(name)){
                            item.setType(TcpConstanst.PLATFORM_TYPE);
                            type = TcpConstanst.PLATFORM_TYPE;
                        } else if (TcpConstanst.MOTIVATION_TYPE_STR_NOT_PAYED.equals(name)){
                            item.setType(TcpConstanst.MOTIVATION_TYPE3);
                            type = TcpConstanst.MOTIVATION_TYPE3;
                        } else{
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("申请类型只能为中收或激励")
                                    .append("</font><br>");

                            importError = true;
                        }
                    }else{
                        builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("申请类型必填")
                                .append("</font><br>");

                        importError = true;
                    }
                    vsOutBean.setType(item.getType());

                    // 客户名
                    if ( !StringTools.isNullOrNone(obj[1]))
                    {
                        String name = obj[1].trim();
                        CustomerBean customerBean = this.getCustomer(customerBeans, name);
                        if (customerBean == null){
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("客户名不存在")
                                    .append("</font><br>");

                            importError = true;
                        }
                        item.setCustomerName(name);
                        vsOutBean.setCustomerName(name);

                    String stafferId = "";
                    // 订单号
                    if ( !StringTools.isNullOrNone(obj[2]))
                    {
                        String outId = obj[2];
                        item.setFullId(outId);
                        vsOutBean.setFullId(outId);
                        //#596
                        if (importItems.contains(outId)){
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("申请模板中订单号重复:").append(outId)
                                    .append("</font><br>");
                            importError = true;
                        } else{
                            importItems.add(outId);
                        }

                        OutBean out = this.outDAO.find(outId);
                        if (out == null){
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("订单号[").append(item.getFullId())
                                    .append("]不存在")
                                    .append("</font><br>");

                            importError = true;
                        }else{
                            String customerName = this.getCustomerName(customerBeans, out.getCustomerId());
                            if (!item.getCustomerName().equals(customerName)){
                                builder
                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                                        .append(outId+"订单号和客户名不匹配:"+item.getCustomerName())
                                        .append("</font><br>");

                                importError = true;
                            }
                            stafferId = out.getStafferId();
                            //同一个订单不能重复提交中收报销申请
                            if (out.getIbFlag() == 1){
                                if (type == TcpConstanst.IB_TYPE){
                                    builder
                                            .append("<font color=red>第[" + currentNumber + "]行错误:")
                                            .append("订单号已经提交中收报销申请:"+outId)
                                            .append("</font><br>");

                                    importError = true;
                                }
                            }

                            //同一个订单不能重复提交激励报销申请
                            if (out.getMotivationFlag() == 1){
                                if(type == TcpConstanst.MOTIVATION_TYPE){
                                    builder
                                            .append("<font color=red>第[" + currentNumber + "]行错误:")
                                            .append("订单号已经提交激励报销申请:"+outId)
                                            .append("</font><br>");

                                    importError = true;
                                }
                            }

                            //同一个订单不能重复提交中收2报销申请
                            if (out.getIbFlag2() == 1){
                                if (type == TcpConstanst.IB_TYPE2){
                                    builder
                                            .append("<font color=red>第[" + currentNumber + "]行错误:")
                                            .append("订单号已经提交中收2报销申请:"+outId)
                                            .append("</font><br>");

                                    importError = true;
                                }
                            }

                            //同一个订单不能重复提交激励2报销申请
                            if (out.getMotivationFlag2() == 1){
                                if(type == TcpConstanst.MOTIVATION_TYPE2){
                                    builder
                                            .append("<font color=red>第[" + currentNumber + "]行错误:")
                                            .append("订单号已经提交激励2报销申请:"+outId)
                                            .append("</font><br>");

                                    importError = true;
                                }
                            }

                            //同一个订单不能重复提交平台手续费报销申请
                            if (out.getPlatformFlag() == 1){
                                if(type == TcpConstanst.PLATFORM_TYPE){
                                    builder
                                            .append("<font color=red>第[" + currentNumber + "]行错误:")
                                            .append("订单号已经提交平台手续费报销申请:"+outId)
                                            .append("</font><br>");

                                    importError = true;
                                }
                            }

                            //检查订单的付款状态
                            if (out.getPay() == OutConstant.PAY_NOT
                                    && type != TcpConstanst.MOTIVATION_TYPE3){
                                builder
                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                                        .append("订单未付款")
                                        .append("</font><br>");

                                importError = true;
                            }

                            //#591 检查中间表
//                            ConditionParse conditionParse2 = new ConditionParse();
//                            conditionParse2.addWhereStr();
//                            conditionParse2.addCondition("type","=",type);
//                            conditionParse2.addCondition("fullId","=",outId);
//                            List<TcpVSOutBean> tcpVSOutBeans = this.tcpVSOutDAO.queryEntityBeansByCondition(conditionParse2);
//                            if(!ListTools.isEmptyOrNull(tcpVSOutBeans)){
//                                TcpVSOutBean tcpVSOutBean = tcpVSOutBeans.get(0);
//                                builder
//                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
//                                        .append(outId+"订单号已提交中收激励申请(t_center_vs_tcpout表):"+tcpVSOutBean.getRefId())
//                                        .append("</font><br>");
//
//                                importError = true;
//                            }
                            TcpVSOutBean tcpVSOutBean = this.getTcpVSOut(tcpVSOutBeans, type, outId);
                            _logger.info("***tcpVSOutBean****"+tcpVSOutBean);
                            if(tcpVSOutBean!= null){
                                builder
                                        .append("<font color=red>第[" + currentNumber + "]行错误:")
                                        .append(outId+"订单号已提交中收激励申请(t_center_vs_tcpout表):"+tcpVSOutBean.getRefId())
                                        .append("</font><br>");

                                importError = true;
                            }

                            //#441 2017/3/29 再次检查是否已提交申请过
                            ConditionParse conditionParse1 = new ConditionParse();
                            conditionParse1.addWhereStr();
                            conditionParse1.addCondition("type","=",type);
                            conditionParse1.addCondition("fullId","like","%"+outId+"%");
                            List<TcpIbBean> ibList = this.tcpIbDAO.queryEntityBeansByCondition(conditionParse1);
                            if(!ListTools.isEmptyOrNull(ibList)){
                                for (TcpIbBean ib:ibList){
                                    TravelApplyBean apply = this.travelApplyDAO.find(ib.getRefId());
                                    if (apply!= null && apply.getStatus()!=0 && apply.getStatus()!=1){
                                        builder
                                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                                .append(outId+"订单号已提交中收激励申请(T_CENTER_TCPIB表):"+apply.getId())
                                                .append("</font><br>");

                                        importError = true;
                                    }
                                }
                            }

                            //2015/8/12 激励申请时，检查订单为已出库或已发货
                            //2015/11/27 激励申请时订单状态限制取消
//                            if(type == TcpConstanst.MOTIVATION_TYPE){
//                                if (out.getStatus() != OutConstant.STATUS_PASS &&
//                                        out.getStatus() != OutConstant.STATUS_SEC_PASS){
//                                    builder
//                                            .append("<font color=red>第[" + currentNumber + "]行错误:")
//                                            .append("激励申请时订单状态必须为已出库或已发货")
//                                            .append("</font><br>");
//
//                                    importError = true;
//                                }
//                            }

                            if (customerToOutMap.containsKey(item.getCustomerName())){
                                List<String> oudIds = customerToOutMap.get(item.getCustomerName());
                                oudIds.add(outId);
                            }else{
                                List<String> oudIds = new ArrayList<String>();
                                oudIds.add(outId);
                                customerToOutMap.put(item.getCustomerName(), oudIds);
                            }
                        }
                    } else{
                        builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("订单号必填")
                                .append("</font><br>");

                        importError = true;
                    }

                    //商品名
                    if ( !StringTools.isNullOrNone(obj[3]))
                    {
                        String productName = obj[3];
                        ProductBean productBean = this.getProduct(productBeans, productName);
                        if (productBean == null){
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("商品名[").append(productName)
                                    .append("]不存在")
                                    .append("</font><br>");

                            importError = true;
                        }
                        item.setProductName(productName);
                        vsOutBean.setProductName(productName);

                        //#401
                        ConditionParse conditionParse = new ConditionParse();
                        conditionParse.addCondition("fullId","=", item.getFullId());
                        List<TcpIbReportItemBean> ibReportList = this.tcpIbReportItemDAO.queryEntityBeansByCondition(conditionParse);
                        if (ListTools.isEmptyOrNull(ibReportList)){
                            builder.append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("订单号[").append(item.getFullId())
                                    .append("]").append("不在中收激励统计清单中")
                                    .append("<br>");
                            importError = true;
                        } else {
                            //#383
                            ConditionParse conditionParse2 = new ConditionParse();
                            conditionParse2.addCondition("fullId", "=", item.getFullId());
                            conditionParse2.addCondition("productName", "=", productName);
                            List<TcpIbReportItemBean> ibReportList2 = this.tcpIbReportItemDAO.queryEntityBeansByCondition(conditionParse);
                            if (ListTools.isEmptyOrNull(ibReportList2)) {
                                builder.append("<font color=red>第[" + currentNumber + "]行错误:")
                                        .append("订单号[").append(item.getFullId())
                                        .append("]").append("和品名不符：" + productName)
                                        .append("<br>");
                                importError = true;
                            }
                        }
                    }

                    //数量
                    if ( !StringTools.isNullOrNone(obj[4]))
                    {
                        String amount = obj[4];
                        item.setAmount(Integer.valueOf(amount));
                        vsOutBean.setAmount(item.getAmount());
                    }

                    //中收/激励金额
                    if ( !StringTools.isNullOrNone(obj[5]))
                    {
                        String money = obj[5];
                        //导入时未检查申请金额是否等于系统记录的中收或激励金额，不等应该报错
//                        ConditionParse con1 = new ConditionParse();
//                        con1.addWhereStr();
//                        con1.addCondition("BaseBean.outId","=",item.getFullId());
//                        con1.addCondition("and exists(select p.id from t_center_product p where p.name = '"+item.getProductName()+"' and p.id=BaseBean.productId)");
//                        List<com.china.center.oa.sail.bean.BaseBean> baseBeans = this.baseDAO.queryEntityBeansByCondition(con1);

                        if (type == TcpConstanst.IB_TYPE){
                            item.setIbMoney(MathTools.parseDouble(money));
                            vsOutBean.setIbMoney(MathTools.parseDouble(money));
                            if (customerToIbMap.containsKey(item.getCustomerName())){
                                customerToIbMap.put(item.getCustomerName(),customerToIbMap.get(item.getCustomerName())+item.getIbMoney()*item.getAmount());
                            } else{
                                customerToIbMap.put(item.getCustomerName(), item.getIbMoney()*item.getAmount());
                            }
                        } else if (type == TcpConstanst.MOTIVATION_TYPE || type == TcpConstanst.MOTIVATION_TYPE3){
                            item.setMotivationMoney(MathTools.parseDouble(money));
                            vsOutBean.setMotivationMoney(MathTools.parseDouble(money));
                            if(customerToMotivationMap.containsKey(item.getCustomerName())){
                                customerToMotivationMap.put(item.getCustomerName(),
                                        customerToMotivationMap.get(item.getCustomerName())+item.getMotivationMoney()*item.getAmount());
                            }else{
                                customerToMotivationMap.put(item.getCustomerName(),item.getMotivationMoney()*item.getAmount());
                            }
                        } else if (type == TcpConstanst.IB_TYPE2){
                            item.setIbMoney2(MathTools.parseDouble(money));
                            vsOutBean.setIbMoney2(MathTools.parseDouble(money));
                            if (customerToIbMap2.containsKey(item.getCustomerName())){
                                customerToIbMap2.put(item.getCustomerName(),customerToIbMap2.get(item.getCustomerName())+item.getIbMoney2()*item.getAmount());
                            } else{
                                customerToIbMap2.put(item.getCustomerName(), item.getIbMoney2()*item.getAmount());
                            }
                        }else if (type == TcpConstanst.MOTIVATION_TYPE2){
                            item.setMotivationMoney2(MathTools.parseDouble(money));
                            vsOutBean.setMotivationMoney2(MathTools.parseDouble(money));
                            if(customerToMotivationMap2.containsKey(item.getCustomerName())){
                                customerToMotivationMap2.put(item.getCustomerName(),
                                        customerToMotivationMap2.get(item.getCustomerName())+item.getMotivationMoney2()*item.getAmount());
                            }else{
                                customerToMotivationMap2.put(item.getCustomerName(),item.getMotivationMoney2()*item.getAmount());
                            }
                        } else if (type == TcpConstanst.PLATFORM_TYPE){
                            item.setPlatformFee(MathTools.parseDouble(money));
                            vsOutBean.setPlatformFee(MathTools.parseDouble(money));
                            if(customerToPlatformMap.containsKey(item.getCustomerName())){
                                customerToPlatformMap.put(item.getCustomerName(),
                                        customerToPlatformMap.get(item.getCustomerName())+item.getPlatformFee()*item.getAmount());
                            } else{
                                customerToPlatformMap.put(item.getCustomerName(),item.getPlatformFee()*item.getAmount());
                            }
                        }
                    } else{
                        builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("中收或激励金额必填")
                                .append("</font><br>");

                        importError = true;
                    }


                    //预算
                    if ( !StringTools.isNullOrNone(obj[6]))
                    {
                        TcpShareVO share = new TcpShareVO();
                        String budget = obj[6];
                        share.setBudgetName(budget);
                        BudgetBean budgetBean = this.getBudget(budgetBeans, budget);
                        if(budgetBean == null){
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("预算项不存在")
                                    .append("</font><br>");

                            importError = true;
                        } else{
                            share.setBudgetId(budgetBean.getId());
                            share.setBudgetName(budget);
                            share.setDepartmentId(budgetBean.getBudgetDepartment());

                            PrincipalshipBean prin = principalshipDAO.find(budgetBean.getBudgetDepartment());
                            if (null != prin)
                                share.setDepartmentName(prin.getName());
                            share.setApproverId(budgetBean.getSigner());
                            StafferBean staffer = stafferDAO.find(budgetBean.getSigner());
                            if (null != staffer)
                                share.setApproverName(staffer.getName());

                            _logger.info("***stafferId***"+stafferId);
                            //激励申请取开单人对应的省级经理
                            if (type == TcpConstanst.MOTIVATION_TYPE && !StringTools.isNullOrNone(stafferId)){
                                String provinceManagerId = this.bankBuLevelDAO.queryHighLevelManagerId(TcpFlowConstant.TRAVELAPPLY_MOTIVATION,
                                        TcpConstanst.TCP_STATUS_PROVINCE_MANAGER, stafferId, stafferId);
                                _logger.info("****provinceManagerId***"+provinceManagerId+"***stafferId***"+stafferId);
                                if(!StringTools.isNullOrNone(provinceManagerId)){
                                    share.setBearId(provinceManagerId);
                                    StafferBean provinceManager = stafferDAO.find(provinceManagerId);
                                    if (provinceManager != staffer)
                                        share.setBearName(provinceManager.getName());

                                    double money = item.getAmount()*item.getMotivationMoney();
                                    share.setRealMonery(TCPHelper.doubleToLong2(String.valueOf(money)));
                                }
                            }

                            shareList.add(share);
                        }
                    } else if (type == TcpConstanst.MOTIVATION_TYPE){
                        builder
                                .append("<font color=red>第[" + currentNumber + "]行错误:")
                                .append("激励申请预算项必填")
                                .append("</font><br>");

                        importError = true;
                    }

                }
                else
                {
                    builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("数据长度不足2格错误")
                            .append("<br>");

                    importError = true;
                }
                importItemList2.add(vsOutBean);
            }
        }

            _logger.info(importItemList2+"***type**"+type+"***customerToIbMap2***"+customerToIbMap2);
            //每个客户的申请金额不得大于统计的可申请的中收或激励金额
            if (type == TcpConstanst.IB_TYPE){
                for(String customerName : customerToIbMap.keySet()){
                    ConditionParse con = new ConditionParse();
                    con.addWhereStr();
                    con.addCondition("customerName","=",customerName);
                    List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                    if (ListTools.isEmptyOrNull(ibReportList)){
                        builder.append("客户[").append(customerName)
                                .append("]").append("可申请中收金额不足："+0)
                                .append("<br>");
                        importError = true;
                    } else {
                        TcpIbReportBean ib = ibReportList.get(0);
                        double currentIb = customerToIbMap.get(customerName);
                        double currentIb2 = this.roundDouble(currentIb);
                        if (currentIb2>ib.getIbMoneyTotal()){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("当前申请金额：" + currentIb2 + "大于可申请中收金额：" + ib.getIbMoneyTotal())
                                    .append("<br>");
                            importError = true;
                        }
                    }
                }
            } else if (type == TcpConstanst.MOTIVATION_TYPE || type == TcpConstanst.MOTIVATION_TYPE3){
                for(String customerName : customerToMotivationMap.keySet()){
                    ConditionParse con = new ConditionParse();
                    con.addWhereStr();
                    con.addCondition("customerName","=",customerName);
                    List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                    if (ListTools.isEmptyOrNull(ibReportList)){
                        builder.append("客户[").append(customerName)
                                .append("]").append("可申请激励金额不足："+0)
                                .append("<br>");
                        importError = true;
                    } else {
                        TcpIbReportBean ib = ibReportList.get(0);
                        double currentMot = customerToMotivationMap.get(customerName);
                        double currentMot2 = this.roundDouble(currentMot);
                        if (currentMot2> ib.getMotivationMoneyTotal()){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("当前申请金额："+currentMot2+"大于可申请激励金额："+ib.getMotivationMoneyTotal())
                                    .append("<br>");
                            importError = true;
                        }
                    }
                }
            } else if (type == TcpConstanst.IB_TYPE2){
                for(String customerName : customerToIbMap2.keySet()){
                    ConditionParse con = new ConditionParse();
                    con.addWhereStr();
                    con.addCondition("customerName","=",customerName);
                    List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                    if (ListTools.isEmptyOrNull(ibReportList)){
                        builder.append("客户[").append(customerName)
                                .append("]").append("可申请中收2金额不足："+0)
                                .append("<br>");
                        importError = true;
                    } else {
                        TcpIbReportBean ib = ibReportList.get(0);
                        double currentIb = customerToIbMap2.get(customerName);
                        double currentIb2 = this.roundDouble(currentIb);
                        if (currentIb2>ib.getIbMoneyTotal2()){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("当前申请金额：" + currentIb2 + "大于可申请中收2金额：" + ib.getIbMoneyTotal2())
                                    .append("<br>");
                            importError = true;
                        }
                    }
                }
            } else if (type == TcpConstanst.MOTIVATION_TYPE2){
                for(String customerName : customerToMotivationMap2.keySet()){
                    ConditionParse con = new ConditionParse();
                    con.addWhereStr();
                    con.addCondition("customerName","=",customerName);
                    List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                    if (ListTools.isEmptyOrNull(ibReportList)){
                        builder.append("客户[").append(customerName)
                                .append("]").append("可申请其他费用金额不足："+0)
                                .append("<br>");
                        importError = true;
                    } else {
                        TcpIbReportBean ib = ibReportList.get(0);
                        double currentMot = customerToMotivationMap2.get(customerName);
                        double currentMot2 = this.roundDouble(currentMot);
                        if (currentMot2> ib.getMotivationMoneyTotal2()){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("当前申请金额："+currentMot2+"大于可申请其他费用金额："+ib.getMotivationMoneyTotal2())
                                    .append("<br>");
                            importError = true;
                        }
                    }
                }
            } else if (type == TcpConstanst.PLATFORM_TYPE){
                for(String customerName : customerToPlatformMap.keySet()){
                    ConditionParse con = new ConditionParse();
                    con.addWhereStr();
                    con.addCondition("customerName","=",customerName);
                    List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
                    if (ListTools.isEmptyOrNull(ibReportList)){
                        builder.append("客户[").append(customerName)
                                .append("]").append("可申请平台手续费不足："+0)
                                .append("<br>");
                        importError = true;
                    } else {
                        TcpIbReportBean ib = ibReportList.get(0);
                        double currentMot = customerToPlatformMap.get(customerName);
                        double currentMot2 = this.roundDouble(currentMot);
                        if (currentMot2> ib.getPlatformFeeTotal()){
                            builder.append("客户[").append(customerName)
                                    .append("]").append("当前申请金额："+currentMot2+"大于平台手续费金额："+ib.getPlatformFeeTotal())
                                    .append("<br>");
                            importError = true;
                        }
                    }
                }
            }

            //#357 激励费用根据单据对应的省级经理合并分担
            Map<String,TcpShareVO> managerToMoney = new HashMap<String,TcpShareVO>();
            if (type == TcpConstanst.MOTIVATION_TYPE
                    || type == TcpConstanst.MOTIVATION_TYPE3){
                for(TcpShareVO share: shareList){
                    String bearId = share.getBearId();
                    if (!StringTools.isNullOrNone(bearId)){
                        TcpShareVO tcpShareVO = managerToMoney.get(bearId);
                        if (tcpShareVO == null){
                            managerToMoney.put(bearId, share);
                        } else{
                            tcpShareVO.setRealMonery(share.getRealMonery()+tcpShareVO.getRealMonery());
                        }
                    }
                }
                if(!managerToMoney.isEmpty()){
                    List<TcpShareVO> tcpShareVOList = new ArrayList<TcpShareVO>();
                    for(TcpShareVO tcpShareVO: managerToMoney.values()){
                        tcpShareVO.setShowRealMonery(MathTools.longToDoubleStr2(tcpShareVO.getRealMonery()));
                        tcpShareVOList.add(tcpShareVO);
                    }
                    _logger.info("***tcpShareVOList***"+tcpShareVOList);
                    vo.setShareVOList(tcpShareVOList);
                }
            } else if (type == TcpConstanst.IB_TYPE && shareList.size()>=1){
                vo.setShareVOList(shareList.subList(0,1));
            }

            double end = System.currentTimeMillis();
            _logger.info("***import IB time elapsed***"+(end-begin)/1000);

            if (importError){
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

                //remove the uploaded file
                File file = new File(filePath);
                if (file.exists()){
                    boolean success = file.delete();
                    _logger.info(filePath + " delete temp file:" + success);
                } else{
                    _logger.info("***no temp file**************"+filePath);
                }

                return mapping.findForward("importIb");
            }

        request.setAttribute("imp", true);
            request.setAttribute("import", true);

            //2015/5/12 根据客户分组
            if (type == TcpConstanst.IB_TYPE){
                for (String name : customerToIbMap.keySet()){
                    TcpIbBean bean = new TcpIbBean();
                    bean.setCustomerName(name);
                    bean.setIbMoney(this.roundDouble(customerToIbMap.get(name)));
                    bean.setType(type);
                    bean.setFullId(this.listToString(customerToOutMap.get(name)));
                    importItemList.add(bean);
                }
            } else if (type == TcpConstanst.MOTIVATION_TYPE
                    || type == TcpConstanst.MOTIVATION_TYPE3){
                for (String name : customerToMotivationMap.keySet()){
                    TcpIbBean bean = new TcpIbBean();
                    bean.setCustomerName(name);
                    bean.setMotivationMoney(this.roundDouble(customerToMotivationMap.get(name)));
                    bean.setType(type);
                    bean.setFullId(this.listToString(customerToOutMap.get(name)));
                    importItemList.add(bean);
                }
            } else if (type == TcpConstanst.IB_TYPE2){
                for (String name : customerToIbMap2.keySet()){
                    TcpIbBean bean = new TcpIbBean();
                    bean.setCustomerName(name);
                    bean.setIbMoney2(this.roundDouble(customerToIbMap2.get(name)));
                    bean.setType(type);
                    bean.setFullId(this.listToString(customerToOutMap.get(name)));
                    importItemList.add(bean);
                }
            } else if (type == TcpConstanst.MOTIVATION_TYPE2){
                for (String name : customerToMotivationMap2.keySet()){
                    TcpIbBean bean = new TcpIbBean();
                    bean.setCustomerName(name);
                    bean.setMotivationMoney2(this.roundDouble(customerToMotivationMap2.get(name)));
                    bean.setType(type);
                    bean.setFullId(this.listToString(customerToOutMap.get(name)));
                    importItemList.add(bean);
                }
            } else if (type == TcpConstanst.PLATFORM_TYPE){
                for (String name : customerToPlatformMap.keySet()){
                    TcpIbBean bean = new TcpIbBean();
                    bean.setCustomerName(name);
                    bean.setPlatformFee(this.roundDouble(customerToPlatformMap.get(name)));
                    bean.setType(type);
                    bean.setFullId(this.listToString(customerToOutMap.get(name)));
                    importItemList.add(bean);
                }
            }

            vo.setIbType(type);
            request.setAttribute("bean", vo);

            prepareInner(request);

            //save attachment
//            this.parserIbAttachment(mapping,request,rds,bean) ;

            rds.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward("importIb");
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                _logger.error(e, e);
            }
        }


        request.getSession().setAttribute("tcpVSOutBeans",importItemList2);
        if (type  == TcpConstanst.IB_TYPE) {
            return mapping.findForward("addTravelApply7import");
        } if (type  == TcpConstanst.IB_TYPE2) {
            return mapping.findForward("addTravelApply9import");
        } else if (type == TcpConstanst.MOTIVATION_TYPE) {
            return mapping.findForward("addTravelApply8import");
        } else if (type == TcpConstanst.MOTIVATION_TYPE2) {
            return mapping.findForward("addTravelApply10import");
        } else if (type == TcpConstanst.PLATFORM_TYPE) {
            return mapping.findForward("addTravelApply16import");
        } else if (type == TcpConstanst.MOTIVATION_TYPE3) {
            return mapping.findForward("addTravelApply17import");
        } else{
            return mapping.findForward("addTravelApply7import");
        }
    }

    private CustomerBean getCustomer(List<CustomerBean> customerBeans, String name){
        for (CustomerBean customerBean: customerBeans){
            if (customerBean.getName().equals(name)){
                return customerBean;
            }
        }
        return null;
    }

    private String getCustomerName(List<CustomerBean> customerBeans, String id){
        for (CustomerBean customerBean: customerBeans){
            if (customerBean.getId().equals(id)){
                return customerBean.getName();
            }
        }
        return "";
    }

    private ProductBean getProduct(List<ProductBean> productBeans, String name){
        for(ProductBean productBean: productBeans){
            if(productBean.getName().equals(name)){
                return productBean;
            }
        }
        return null;
    }

    private BudgetBean getBudget(List<BudgetBean> budgetBeans, String name){
        for(BudgetBean budgetBean: budgetBeans){
            if (budgetBean.getName().equals(name)){
                return budgetBean;
            }
        }
        return null;
    }

    private TcpVSOutBean getTcpVSOut(List<TcpVSOutBean> tcpVSOutBeans, int type, String outId){
        for (TcpVSOutBean tcpVSOutBean: tcpVSOutBeans){
            if(tcpVSOutBean.getType() == type && tcpVSOutBean.getFullId().equals(outId)){
                return tcpVSOutBean;
            }
        }
        return null;
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

    private String listToString(List<String> outIds){
        StringBuilder sb = new StringBuilder();
        if (!ListTools.isEmptyOrNull(outIds)){
            for (String outId: outIds){
                sb.append(outId).append(";");
            }
        }
        return sb.toString();
    }

    /**2015/5/13 导入中收激励模板时先保存附件
     * parserIbAttachment
     *
     * @param request
     * @param rds
     * @param travelApply
     * @return
     */
    private String parserIbAttachment( HttpServletRequest request,
                                           RequestDataStream rds, TravelApplyBean travelApply)
    {
        List<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();

        travelApply.setAttachmentList(attachmentList);

        // rds.getStreamMap() 只能调用一次,Java中InputStream无法重复读取
        Map<String, InputStream> streamMap = rds.getStreamMap();
        for (Map.Entry<String, InputStream> entry : streamMap.entrySet())
        {
            AttachmentBean bean = new AttachmentBean();

            FileOutputStream out = null;

            UtilStream ustream = null;

            try
            {
                String path = this.getAttachmentPath();
                String savePath = mkdir(path);
                String fileAlais = SequenceTools.getSequence();

                String fileName = FileTools.getFileName(rds.getFileName(entry.getKey()));

                String rabsPath = '/' + savePath + '/' + fileAlais + "."
                        + FileTools.getFilePostfix(fileName).toLowerCase();

                String filePath = path + '/' + rabsPath;

                bean.setName(fileName);

                bean.setPath(rabsPath);

                bean.setLogTime(TimeTools.now());

                out = new FileOutputStream(filePath);

                _logger.info("**********entry value******"+entry.getValue().available());

                ustream = new UtilStream(entry.getValue(), out);
                ustream.copyStream();

                attachmentList.add(bean);
                return filePath;
            }
            catch (Exception e)
            {
                _logger.error(e, e);

                request.setAttribute(KeyConstant.ERROR_MESSAGE, "保存失败");

            }
            finally
            {
                if (ustream != null)
                {
                    try
                    {
                        ustream.close();
                    }
                    catch (IOException e)
                    {
                        _logger.error(e, e);
                    }
                }
            }
        }

        return null;
    }

    /**
     * 2016/3/16 #195
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward batchUpdateIbMoney(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        String url = "batchUpdateIbMoney";
        _logger.info("*************batchUpdateIbMoney*************");

        User user = (User) request.getSession().getAttribute("user");

        RequestDataStream rds = new RequestDataStream(request);

        boolean importError = false;

        List<TcpIbBean> importItemList = new ArrayList<TcpIbBean>();

        StringBuilder builder = new StringBuilder();

        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward(url);
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward(url);
        }

        ReaderFile reader = ReadeFileFactory.getXLSReader();

        try
        {
            reader.readFile(rds.getUniqueInputStream());

            while (reader.hasNext())
            {
                String[] obj = fillObj4((String[])reader.next());

                // 第一行忽略
                if (reader.getCurrentLineNumber() == 1)
                {
                    continue;
                }

                if (StringTools.isNullOrNone(obj[0]))
                {
                    continue;
                }

                int currentNumber = reader.getCurrentLineNumber();

                if (obj.length >= 2 )
                {
                    TcpIbBean bean = new TcpIbBean();

                    //类型
                    if (!StringTools.isNullOrNone(obj[0])){
                        String name = obj[0].trim();
                        if (TcpConstanst.IB_TYPE_STR.equals(name)){
                            bean.setType(TcpConstanst.IB_TYPE);
                        } else if (TcpConstanst.MOTIVATION_TYPE_STR.equals(name)){
                            bean.setType(TcpConstanst.MOTIVATION_TYPE);
                        } else if (TcpConstanst.IB_TYPE_STR2.equals(name)){
                            bean.setType(TcpConstanst.IB_TYPE2);
                        } else if (TcpConstanst.MOTIVATION_TYPE_STR2.equals(name)){
                            bean.setType(TcpConstanst.MOTIVATION_TYPE2);
                        } else if (TcpConstanst.PLATFORM_TYPE_STR.equals(name)){
                            bean.setType(TcpConstanst.PLATFORM_TYPE);
                        } else{
                            builder
                                    .append("<font color=red>第[" + currentNumber + "]行错误:")
                                    .append("申请类型只能为中收、激励、平台手续费")
                                    .append("</font><br>");

                            importError = true;
                        }
                    }

                    // 销售单号
                    if ( !StringTools.isNullOrNone(obj[1]))
                    {
                        String outId = obj[1].trim();

                        OutBean out = outDAO.find(outId);

                        if (null == out)
                        {
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("销售单" + outId + "不存在")
                                    .append("<br>");
                            importError = true;
                        } else {
                            //中收或激励申请标识为1的，不允许再更改对应金额
                            bean.setFullId(outId);
                            if (out.getIbFlag() == 1 && bean.getType() == TcpConstanst.IB_TYPE){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append(outId + "中收申请标识为1的，不允许再更改对应金额")
                                        .append("<br>");
                                importError = true;
                            } else if (out.getMotivationFlag() == 1 && bean.getType() == TcpConstanst.MOTIVATION_TYPE){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append(outId + "激励申请标识为1的，不允许再更改对应金额")
                                        .append("<br>");
                                importError = true;
                            } else if (out.getIbFlag2() == 1 && bean.getType() == TcpConstanst.IB_TYPE2){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append(outId + "中收2申请标识为1的，不允许再更改对应金额")
                                        .append("<br>");
                                importError = true;
                            } else if (out.getMotivationFlag2() == 1 && bean.getType() == TcpConstanst.MOTIVATION_TYPE2){
                                builder
                                        .append("第[" + currentNumber + "]错误:")
                                        .append(outId + "激励2申请标识为1的，不允许再更改对应金额")
                                        .append("<br>");
                                importError = true;
                            }
                        }
                    }
                    else
                    {
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("销售单号不能为空")
                                .append("<br>");

                        importError = true;
                    }

                    // 商品名
                    if ( !StringTools.isNullOrNone(obj[2]))
                    {
                        String productName = obj[2].trim();
                        bean.setProductName(productName);

                        ProductBean productBean = this.productDAO.findByName(productName);
                        if (productBean == null){
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("商品名不存在:" + productName)
                                    .append("<br>");

                            importError = true;
                        }else{
                            bean.setProductId(productBean.getId());
                        }
                    }
                    else
                    {
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("商品名不能为空")
                                .append("<br>");

                        importError = true;
                    }

                    // 金额
                    if ( !StringTools.isNullOrNone(obj[3])) {
                        String price = obj[3].trim();
                        if (TcpConstanst.IB_TYPE == bean.getType()){
                            bean.setIbMoney(Double.valueOf(price));
                        } else if (TcpConstanst.MOTIVATION_TYPE == bean.getType()){
                            bean.setMotivationMoney(Double.valueOf(price));
                        } else if (TcpConstanst.IB_TYPE2 == bean.getType()){
                            bean.setIbMoney2(Double.valueOf(price));
                        } else if (TcpConstanst.MOTIVATION_TYPE2 == bean.getType()){
                            bean.setMotivationMoney2(Double.valueOf(price));
                        } else if (TcpConstanst.PLATFORM_TYPE == bean.getType()){
                            bean.setPlatformFee(Double.valueOf(price));
                        }
                    } else
                    {
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("金额不能为空")
                                .append("<br>");

                        importError = true;
                    }

                    importItemList.add(bean);
                }
                else
                {
                    builder
                            .append("第[" + currentNumber + "]错误:")
                            .append("数据长度不足26格错误")
                            .append("<br>");

                    importError = true;
                }
            }
        }catch (Exception e)
        {
            _logger.error(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

            return mapping.findForward(url);
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch (IOException e)
            {
                _logger.error(e, e);
            }
        }

        rds.close();

        if (importError){

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

            return mapping.findForward(url);
        }

        try
        {
            travelApplyManager.batchUpdateIbMoney(user, importItemList);

            request.setAttribute(KeyConstant.MESSAGE, "批量修改中收激励金额成功");
        }
        catch(Exception e)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getStackTrace());

            return mapping.findForward(url);
        }

        return mapping.findForward(url);
    }


    /**
     * 2015/4/13 中收激励统计(登录人名下客户)
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryIbReport(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException
    {
        String queryType = request.getParameter("queryType");
    	User user = (User) request.getSession().getAttribute("user");
        try{
            List<TcpIbReportBean> ibReportList = new ArrayList<TcpIbReportBean>();
            String customerName = Util.getString(request.getParameter("customerName"));
            if("1".equals(queryType)){
                String stafferId = user.getStafferId();
                ibReportList = this.tcpIbReportDAO.queryEntityBeansByCustomerStaffer(customerName, stafferId);
            }else{
                ConditionParse con = new ConditionParse();
                if (!StringTools.isNullOrNone(customerName)){
                    con.addWhereStr();
                    con.addCondition("customerName","like","%"+customerName+"%");
                }
                ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);
            }
            _logger.info("ibReportList size********"+ibReportList.size());
            request.setAttribute("ibReportList", ibReportList);
            request.setAttribute("customerName", customerName);
            request.setAttribute("queryType", queryType);
        }catch (Exception e){
            e.printStackTrace();
            _logger.error("Exception:",e);
        }
        return mapping.findForward("ibReport");
    }

    /**
     * 2015/4/13 中收激励统计明细
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward ibReportDetail(ActionMapping mapping, ActionForm form,
                                  HttpServletRequest request,
                                  HttpServletResponse response)
            throws ServletException
    {
        try{
            String ibReportId = request.getParameter("ibReportId");
            _logger.info("**********ibReportDetail with ibReportId:"+ibReportId);
            List<TcpIbReportItemBean> ibReportItems = this.getTcpIbReportItemDAO().queryEntityBeansByFK(ibReportId);
            request.setAttribute("ibReportItems", ibReportItems);
            request.setAttribute("ibReportId", ibReportId);
        }catch (Exception e){
            e.printStackTrace();
            _logger.error("Exception:",e);
        }

        return mapping.findForward("ibReportDetail");
    }


    /**
     * export中收激励统计(CSV导出)
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward export(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        OutputStream out = null;

        String filenName = "Export_" + TimeTools.now("MMddHHmmss") + ".csv";

        User user = (User) request.getSession().getAttribute("user");

        String customerName = request.getParameter("customerName");
        _logger.info("export ibReport with customer name:"+customerName);
        ConditionParse con = new ConditionParse();
        if (!StringTools.isNullOrNone(customerName)){
            con.addWhereStr();
            con.addCondition("customerName","like","%"+customerName+"%");
        }
        List<TcpIbReportBean> ibReportList = this.tcpIbReportDAO.queryEntityBeansByCondition(con);

        if (ListTools.isEmptyOrNull(ibReportList))
        {
            return null;
        }

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                + filenName);

        WriteFile write = null;

        try
        {
            out = response.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            WriteFileBuffer line = new WriteFileBuffer(write);
            line.writeColumn("客户名");
            line.writeColumn("中收金额");
            line.writeColumn("激励金额");
            line.writeColumn("中收2金额");
            line.writeColumn("其他费用金额");
            line.writeColumn("平台手续费");

            line.writeLine();

            for (Iterator<TcpIbReportBean> iter = ibReportList.iterator(); iter.hasNext();)
            {
                TcpIbReportBean ib = iter.next();
                line.writeColumn(ib.getCustomerName());
                line.writeColumn(ib.getIbMoneyTotal());
                line.writeColumn(ib.getMotivationMoneyTotal());
                line.writeColumn(ib.getIbMoneyTotal2());
                line.writeColumn(ib.getMotivationMoneyTotal2());
                line.writeColumn(ib.getPlatformFeeTotal());

                line.writeLine();

            }
        }
        catch (Exception e)
        {
            _logger.error(e, e);
            return null;
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

        return null;
    }


    /** 2015/7/6
     * export 所有客户中收激励明细导出CSV
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportDetail(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        BufferedOutputStream out = null;

        String filenName = "Export_" + TimeTools.now("MMddHHmmss") + ".csv";

        String customerName = request.getParameter("customerName");
        _logger.info("export ibReport detail with customer name:" + customerName);

//        ConditionParse con = new ConditionParse();
//        con.addWhereStr();
//
//        //#138 导出重复
////        Set<String> orders = new HashSet<String>();
////        Map<String,TcpIbReportItemBean> orders = new HashMap<String, TcpIbReportItemBean>();
//        List<TcpIbReportItemBean> ibReportList = this.tcpIbReportItemDAO.queryEntityBeansByCondition(con);
//
//        if (ListTools.isEmptyOrNull(ibReportList))
//        {
//            return null;
//        }
        
        List<ExportIbReportItemData> exportDataList = tcpIbReportItemDAO.queryExportReportItemData(customerName);
        if(exportDataList == null || exportDataList.size() == 0)
        {
        	return null;
        }

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                + filenName);

        WriteFile write = null;

        try
        {
            out = new BufferedOutputStream(response.getOutputStream());

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            WriteFileBuffer line = new WriteFileBuffer(write);
            line.writeColumn("客户名");
            line.writeColumn("订单号");
            line.writeColumn("商品名");
            line.writeColumn("银行产品代码");
            line.writeColumn("商品单价");
            line.writeColumn("商品数量");
            line.writeColumn("中收金额");
            line.writeColumn("激励金额");
            line.writeColumn("中收2金额");
            line.writeColumn("其他费用金额");
            line.writeColumn("平台手续费");
            line.writeColumn("订单状态");
            line.writeColumn("申请人");
            line.writeColumn("银行销售日期");
            line.writeColumn("付款状态");
            line.writeColumn("付款时间");
            line.writeColumn("渠道");

            line.writeLine();

            for (ExportIbReportItemData ib:exportDataList)
            {
                String fullId = ib.getFullId();
                //#166同1SO单的只导出了一行
                //2016/3/7 #166 同一SO单导出重复是正常情况，因为正好SO单两个商品行的商品和数量是一样的。
//                TcpIbReportItemBean ibInMap = orders.get(fullId);
//                if (ibInMap == null){
//                    orders.put(fullId, ib);
//                } else{
//                    if (ibInMap.equals(ib)){
//                        _logger.info(ibInMap+"***duplicate IB report item***"+ib);
//                        continue;
//                    }
//                }

                line.writeColumn(ib.getCustomerName());
                line.writeColumn(fullId);
                line.writeColumn(ib.getProductName());

                String bankProductCode = ib.getOimport_bankproduct_code();
                String productCode = ib.getProductcode();
                if(StringUtils.isEmpty(bankProductCode))
                {
                	if(StringUtils.isNotEmpty(customerName))
                	{
                		ConditionParse conditionParse = new ConditionParse();
                		conditionParse.addCondition("code", "=", productCode);
                		conditionParse.addCondition("bank", "=", customerName.substring(0, 4));
                		
                		List<ProductImportBean> beans = this.productImportDAO.queryEntityBeansByCondition(conditionParse);
                		if (!ListTools.isEmptyOrNull(beans)) {
                			ProductImportBean productImportBean = beans.get(0);
                			bankProductCode = productImportBean.getBankProductCode();
                		}
                	}
                }
                line.writeColumn(bankProductCode);
                line.writeColumn(ib.getPrice());
                line.writeColumn(ib.getAmount());

                line.writeColumn(ib.getIbMoney());
                line.writeColumn(ib.getMotivationMoney());
                line.writeColumn(ib.getIbMoney2());
                line.writeColumn(ib.getMotivationMoney2());
                line.writeColumn(ib.getPlatformFee());

                String outType = ib.getOuttype();
                if("0".equals(outType))
                {
                	line.writeColumn(DefinedCommon.getValue("outStatus",ib.getStatus()));
                }
                else
                {
                	line.writeColumn(DefinedCommon.getValue("buyStatus", outType));
                }
                line.writeColumn(ib.getStaffername());
                line.writeColumn(ib.getPodate());
                line.writeColumn(DefinedCommon.getValue("outPay",
                		ib.getPay()));
                line.writeColumn(ib.getPaytime());
                line.writeColumn(ib.getChannel());
                

                line.writeLine();


            }
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            _logger.error(e, e);
            return null;
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

        return null;
    }

    private String getBankProductCode(TcpIbReportItemBean ib){
        String productId = ib.getProductId();
        String customerName = ib.getCustomerName();
        String outId = ib.getFullId();
        String bankProductCode = "";
        try {
            //先取out_import表吧，取OANO对应的productcode,
            List<OutImportBean> importBeans = outImportDAO.queryEntityBeansByFK(outId, AnoConstant.FK_FIRST);
            if (!ListTools.isEmptyOrNull(importBeans)) {
                for (OutImportBean outImportBean : importBeans) {
                    if (!StringTools.isNullOrNone(outImportBean.getProductCode())) {
                        bankProductCode = outImportBean.getProductCode();
                        break;
                    }
                }
            }

            //如果在表里没有对应的OANO，再去product_import表取个值
            if (StringTools.isNullOrNone(bankProductCode) && !StringTools.isNullOrNone(productId)) {
                ProductBean productBean = this.productDAO.find(productId);
                if (productBean != null) {
                    String productCode = productBean.getCode();
                    //#291

                    if (!StringTools.isNullOrNone(productCode) && customerName != null && customerName.length() >= 4) {
                        ConditionParse conditionParse = new ConditionParse();
                        conditionParse.addCondition("code", "=", productCode);
                        conditionParse.addCondition("bank", "=", customerName.substring(0, 4));

                        List<ProductImportBean> beans = this.productImportDAO.queryEntityBeansByCondition(conditionParse);
                        if (!ListTools.isEmptyOrNull(beans)) {
                            ProductImportBean productImportBean = beans.get(0);
                            bankProductCode = productImportBean.getBankProductCode();
                        }
                    }
                }
            }
        }catch (Exception e){
            _logger.error(e);
        }
        return bankProductCode;
    }

    /**
     * export中收激励明细导出CSV
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward exportIbDetail(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        OutputStream out = null;

        String filenName = "Export_" + TimeTools.now("MMddHHmmss") + ".csv";

        String ibReportId = request.getParameter("ibReportId");
        ConditionParse con = new ConditionParse();
        con.addWhereStr();
        con.addCondition("refId","=",ibReportId);

        List<TcpIbReportItemBean> ibReportList = this.tcpIbReportItemDAO.queryEntityBeansByCondition(con);

        if (ListTools.isEmptyOrNull(ibReportList))
        {
            return null;
        }

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                + filenName);

        WriteFile write = null;

        try
        {
            out = response.getOutputStream();

            write = WriteFileFactory.getMyTXTWriter();

            write.openFile(out);

            WriteFileBuffer line = new WriteFileBuffer(write);
            line.writeColumn("客户名");
            line.writeColumn("订单号");
            line.writeColumn("商品名");
            line.writeColumn("银行产品代码");
            line.writeColumn("商品单价");
            line.writeColumn("商品数量");
            line.writeColumn("中收金额");
            line.writeColumn("激励金额");
            line.writeColumn("中收2金额");
            line.writeColumn("其他费用金额");
            line.writeColumn("平台手续费");
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

                //#356 导出银行产品代码
                line.writeColumn(this.getBankProductCode(ib));
                line.writeColumn(ib.getPrice());
                line.writeColumn(ib.getAmount());
                line.writeColumn(ib.getIbMoney());
                line.writeColumn(ib.getMotivationMoney());
                line.writeColumn(ib.getIbMoney2());
                line.writeColumn(ib.getMotivationMoney2());
                line.writeColumn(ib.getPlatformFee());

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
            return null;
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

        return null;
    }
    
    public BudgetDAO getBudgetDAO()
	{
		return budgetDAO;
	}

	public void setBudgetDAO(BudgetDAO budgetDAO)
	{
		this.budgetDAO = budgetDAO;
	}

	/**
     * 
     * @param obj
     * @return
     */
    private String[] fillObj(String[] obj)
    {
        String[] result = new String[3];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }

    private String[] fillObj4(String[] obj)
    {
        String[] result = new String[4];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }

    private String[] fillObj2(String[] obj)
    {
        String[] result = new String[7];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }

    private String[] fillObj20(String[] obj)
    {
        String[] result = new String[20];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }
    
    /**
     * @return the travelApplyManager
     */
    public TravelApplyManager getTravelApplyManager()
    {
        return travelApplyManager;
    }

    /**
     * @param travelApplyManager
     *            the travelApplyManager to set
     */
    public void setTravelApplyManager(TravelApplyManager travelApplyManager)
    {
        this.travelApplyManager = travelApplyManager;
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
     * @return the feeItemDAO
     */
    public FeeItemDAO getFeeItemDAO()
    {
        return feeItemDAO;
    }

    /**
     * @param feeItemDAO
     *            the feeItemDAO to set
     */
    public void setFeeItemDAO(FeeItemDAO feeItemDAO)
    {
        this.feeItemDAO = feeItemDAO;
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
     * @return the tcpFlowManager
     */
    public TcpFlowManager getTcpFlowManager()
    {
        return tcpFlowManager;
    }

    /**
     * @param tcpFlowManager
     *            the tcpFlowManager to set
     */
    public void setTcpFlowManager(TcpFlowManager tcpFlowManager)
    {
        this.tcpFlowManager = tcpFlowManager;
    }

    /**
     * @return the outBillDAO
     */
    public OutBillDAO getOutBillDAO()
    {
        return outBillDAO;
    }

    /**
     * @param outBillDAO
     *            the outBillDAO to set
     */
    public void setOutBillDAO(OutBillDAO outBillDAO)
    {
        this.outBillDAO = outBillDAO;
    }

    /**
     * @return the financeDAO
     */
    public FinanceDAO getFinanceDAO()
    {
        return financeDAO;
    }

    /**
     * @param financeDAO
     *            the financeDAO to set
     */
    public void setFinanceDAO(FinanceDAO financeDAO)
    {
        this.financeDAO = financeDAO;
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
     * @return the dutyDAO
     */
    public DutyDAO getDutyDAO()
    {
        return dutyDAO;
    }

    /**
     * @param dutyDAO
     *            the dutyDAO to set
     */
    public void setDutyDAO(DutyDAO dutyDAO)
    {
        this.dutyDAO = dutyDAO;
    }

    public StafferDAO getStafferDAO() {
        return stafferDAO;
    }

    public void setStafferDAO(StafferDAO stafferDAO) {
        this.stafferDAO = stafferDAO;
    }

	public PrincipalshipDAO getPrincipalshipDAO() {
		return principalshipDAO;
	}

	public void setPrincipalshipDAO(PrincipalshipDAO principalshipDAO) {
		this.principalshipDAO = principalshipDAO;
	}

	public ProductDAO getProductDAO() {
		return productDAO;
	}

	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

    public OutDAO getOutDAO() {
        return outDAO;
    }

    public void setOutDAO(OutDAO outDAO) {
        this.outDAO = outDAO;
    }

    public CustomerMainDAO getCustomerMainDAO() {
        return customerMainDAO;
    }

    public void setCustomerMainDAO(CustomerMainDAO customerMainDAO) {
        this.customerMainDAO = customerMainDAO;
    }

    public TcpIbDAO getTcpIbDAO() {
        return tcpIbDAO;
    }

    public void setTcpIbDAO(TcpIbDAO tcpIbDAO) {
        this.tcpIbDAO = tcpIbDAO;
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

    public BaseDAO getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO baseDAO) {
        this.baseDAO = baseDAO;
    }

    public BankBuLevelDAO getBankBuLevelDAO() {
        return bankBuLevelDAO;
    }

    public void setBankBuLevelDAO(BankBuLevelDAO bankBuLevelDAO) {
        this.bankBuLevelDAO = bankBuLevelDAO;
    }

    public OrgManager getOrgManager() {
        return orgManager;
    }

    public void setOrgManager(OrgManager orgManager) {
        this.orgManager = orgManager;
    }

    public ProductImportDAO getProductImportDAO() {
        return productImportDAO;
    }

    public void setProductImportDAO(ProductImportDAO productImportDAO) {
        this.productImportDAO = productImportDAO;
    }

    public OutImportDAO getOutImportDAO() {
        return outImportDAO;
    }

    public void setOutImportDAO(OutImportDAO outImportDAO) {
        this.outImportDAO = outImportDAO;
    }

    public TcpVSOutDAO getTcpVSOutDAO() {
        return tcpVSOutDAO;
    }

    public void setTcpVSOutDAO(TcpVSOutDAO tcpVSOutDAO) {
        this.tcpVSOutDAO = tcpVSOutDAO;
    }

	public PayOrderDAO getPayOrderDao() {
		return payOrderDao;
	}

	public void setPayOrderDao(PayOrderDAO payOrderDao) {
		this.payOrderDao = payOrderDao;
	}

	public MayCurConsumeSubmitDAO getMayCurConsumeSubmitDAO() {
		return mayCurConsumeSubmitDAO;
	}

	public void setMayCurConsumeSubmitDAO(MayCurConsumeSubmitDAO mayCurConsumeSubmitDAO) {
		this.mayCurConsumeSubmitDAO = mayCurConsumeSubmitDAO;
	}
    
    
}
