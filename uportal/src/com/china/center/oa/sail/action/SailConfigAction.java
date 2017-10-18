/**
 * File Name: SailConfigAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-12-17<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.action;


import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.actionhelper.query.HandleResult;
import com.china.center.common.MYException;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.dao.ShowDAO;
import com.china.center.oa.publics.manager.OrgManager;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.dao.SailConfDAO;
import com.china.center.oa.sail.dao.SailConfigDAO;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.sail.vo.SailConfVO;
import com.china.center.tools.BeanUtil;
import com.china.center.tools.CommonTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.StringTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * SailConfigAction
 * 
 * @author ZHUZHU
 * @version 2011-12-17
 * @see com.china.center.oa.sail.action.SailConfigAction
 * @since 3.0
 */
public class SailConfigAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private SailConfigDAO sailConfigDAO = null;

    private SailConfigManager sailConfigManager = null;

    private ShowDAO showDAO = null;

    private SailConfDAO sailConfDAO = null;

    private OrgManager orgManager = null;

    private static final String QUERYSAILCONFIG = "querySailConfig";

    /**
     * default constructor
     */
    public SailConfigAction()
    {
    }

    /**
     * querySailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward querySailConfig(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSAILCONFIG, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSAILCONFIG, request, condtion,
            this.sailConfDAO, new HandleResult<SailConfVO>()
            {
                public void handle(SailConfVO obj)
                {

                }
            });

        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * preForAddSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddSailConfig(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        List<PrincipalshipBean> industryList = orgManager.listAllIndustry();

        for (PrincipalshipBean principalshipBean : industryList)
        {
            principalshipBean.setName(principalshipBean.getParentName() + "-->"
                                      + principalshipBean.getName());
        }

        request.setAttribute("industryList", industryList);

        return mapping.findForward("addSailConfig");
    }

    /**
     * addSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addSailConfig(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        SailConfBean bean = new SailConfBean();

        String sailType = request.getParameter("sailType");
        String productType = request.getParameter("productType");

        try
        {
            BeanUtil.getBean(bean, request);

            if (StringTools.isNullOrNone(sailType))
            {
                bean.setSailType( -1);
            }

            if (StringTools.isNullOrNone(productType))
            {
                bean.setProductType( -1);
            }

            User user = Helper.getUser(request);

            sailConfigManager.addBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("querySailConfig");
    }


    public ActionForward importSailConfig(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        RequestDataStream rds = new RequestDataStream(request);

        boolean importError = false;

        List<SailConfBean> importItemList = new ArrayList<SailConfBean>();

        StringBuilder builder = new StringBuilder();

        try
        {
            rds.parser();
        }
        catch (Exception e1)
        {
            _logger.error(e1, e1);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importSailConfig");
        }

        if ( !rds.haveStream())
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

            return mapping.findForward("importSailConfig");
        }

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
                    SailConfBean item = new SailConfBean();

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

            return mapping.findForward("importSailConfig");
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

            return mapping.findForward("importSailConfig");
        } else{
            try{
//                this.travelApplyManager.importBankBulevel(null, importItemList);

                request.setAttribute(KeyConstant.MESSAGE, "导入成功");
            }catch(Exception e){
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getMessage());

                return mapping.findForward("importSailConfig");
            }
        }

        return mapping.findForward("importSailConfig");
    }

    private String[] fillObj(String[] obj)
    {
        String[] result = new String[9];

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
     * addSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateSailConfig(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        SailConfBean bean = new SailConfBean();

        String sailType = request.getParameter("sailType");
        String productType = request.getParameter("productType");

        try
        {
            BeanUtil.getBean(bean, request);

            if (StringTools.isNullOrNone(sailType))
            {
                bean.setSailType( -1);
            }

            if (StringTools.isNullOrNone(productType))
            {
                bean.setProductType( -1);
            }

            User user = Helper.getUser(request);

            sailConfigManager.updateBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("querySailConfig");
    }

    /**
     * findSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findSailConfig(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        SailConfVO vo = sailConfDAO.findVO(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "不存在");

            return mapping.findForward("querySailConfig");
        }

        request.setAttribute("bean", vo);

        if ("1".equals(update))
        {
            return mapping.findForward("updateSailConfig");
        }

        return mapping.findForward("detailSailConfig");
    }

    /**
     * deleteSailConfig
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteSailConfig(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            sailConfigManager.deleteConf(user, id);

            ajax.setSuccess("成功删除");
        }
        catch (MYException e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    /**
     * @return the sailConfigDAO
     */
    public SailConfigDAO getSailConfigDAO()
    {
        return sailConfigDAO;
    }

    /**
     * @param sailConfigDAO
     *            the sailConfigDAO to set
     */
    public void setSailConfigDAO(SailConfigDAO sailConfigDAO)
    {
        this.sailConfigDAO = sailConfigDAO;
    }

    /**
     * @return the sailConfigManager
     */
    public SailConfigManager getSailConfigManager()
    {
        return sailConfigManager;
    }

    /**
     * @param sailConfigManager
     *            the sailConfigManager to set
     */
    public void setSailConfigManager(SailConfigManager sailConfigManager)
    {
        this.sailConfigManager = sailConfigManager;
    }

    /**
     * @return the showDAO
     */
    public ShowDAO getShowDAO()
    {
        return showDAO;
    }

    /**
     * @param showDAO
     *            the showDAO to set
     */
    public void setShowDAO(ShowDAO showDAO)
    {
        this.showDAO = showDAO;
    }

    /**
     * @return the sailConfDAO
     */
    public SailConfDAO getSailConfDAO()
    {
        return sailConfDAO;
    }

    /**
     * @param sailConfDAO
     *            the sailConfDAO to set
     */
    public void setSailConfDAO(SailConfDAO sailConfDAO)
    {
        this.sailConfDAO = sailConfDAO;
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
}
