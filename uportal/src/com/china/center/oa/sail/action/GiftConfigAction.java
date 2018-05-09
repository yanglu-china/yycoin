
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
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProductVSGiftBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductVSGiftDAO;
import com.china.center.oa.product.manager.GiftConfigManager;
import com.china.center.oa.product.vo.ProductVSGiftVO;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.tools.*;
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
 * GiftConfigAction
 * 
 * @author ZHUZHU
 * @version 2015-04-22
 * @see com.china.center.oa.sail.action.GiftConfigAction
 * @since 3.0
 */
public class GiftConfigAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private ProductVSGiftDAO productVSGiftDAO = null;

    private GiftConfigManager giftConfigManager = null;

    private ProductDAO productDAO = null;

    private static final String QUERYSAILCONFIG = "queryGiftConfig";

    /**
     * default constructor
     */
    public GiftConfigAction()
    {
    }

    /**
     * queryGiftConfig
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward queryGiftConfig(ActionMapping mapping, ActionForm form,
                                         HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        _logger.info("queryGiftConfig********************");
        ConditionParse condtion = new ConditionParse();

        condtion.addWhereStr();

        ActionTools.processJSONQueryCondition(QUERYSAILCONFIG, request, condtion);

        String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYSAILCONFIG, request, condtion,
            this.productVSGiftDAO, new HandleResult<ProductVSGiftVO>()
            {
                public void handle(ProductVSGiftVO obj)
                {

                }
            });
        _logger.info("queryGiftConfig********************"+jsonstr);
        return JSONTools.writeResponse(response, jsonstr);
    }

    /**
     * #310
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward importGiftConfig(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
            throws ServletException
    {
        User user = Helper.getUser(request);

        RequestDataStream rds = new RequestDataStream(request);

        boolean importError = false;

        List<ProductVSGiftBean> importItemList = new ArrayList<ProductVSGiftBean>();

        StringBuilder builder = new StringBuilder();

        final String url = "importGiftConfig";

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
                String[] obj = StringUtils.fillObj((String[])reader.next(), 34);

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
                    ProductVSGiftBean item = new ProductVSGiftBean();

                    // 活动描述
                    if ( !StringTools.isNullOrNone(obj[0]))
                    {
                        String activity = obj[0].trim();
                        item.setActivity(activity);
                    } else{
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("活动描述必填")
                                .append("<br>");

                        importError = true;
                    }

                    // 适用银行
                    if ( !StringTools.isNullOrNone(obj[1]))
                    {
                        String bank = obj[1].trim();
                        item.setBank(bank);
                    }

                    // 不适用银行
                    if ( !StringTools.isNullOrNone(obj[2]))
                    {
                        String excludeBank = obj[2].trim();
                        item.setExcludeBank(excludeBank);
                    }

                    // 适用分行
                    if ( !StringTools.isNullOrNone(obj[3]))
                    {
                        String branch = obj[3].trim();
                        item.setBranchName(branch);
                    }

                    // 不适用分行
                    if ( !StringTools.isNullOrNone(obj[4]))
                    {
                        String excludeBranch = obj[4].trim();
                        item.setExcludeBranchName(excludeBranch);
                    }

                    // 适用支行
                    if ( !StringTools.isNullOrNone(obj[5]))
                    {
                        String customerName = obj[5].trim();
                        item.setCustomerName(customerName);
                    }

                    // 不适用支行
                    if ( !StringTools.isNullOrNone(obj[6]))
                    {
                        String excludeCustomer = obj[6].trim();
                        item.setExcludeCustomerName(excludeCustomer);
                    }

                    // 适用渠道
                    if ( !StringTools.isNullOrNone(obj[7]))
                    {
                        String channel = obj[7].trim();
                        item.setChannel(channel);
                    }

                    // 不适用渠道
                    if ( !StringTools.isNullOrNone(obj[8]))
                    {
                        String excludeChannel = obj[8].trim();
                        item.setExcludeChannel(excludeChannel);
                    }

                    // 适用事业部
                    if ( !StringTools.isNullOrNone(obj[9]))
                    {
                        String industryName = obj[9].trim();
                        item.setIndustryName(industryName);
                    }

                    // 不适用事业部
                    if ( !StringTools.isNullOrNone(obj[10]))
                    {
                        String excludeIndustryName = obj[10].trim();
                        item.setExcludeIndustryName(excludeIndustryName);
                    }

                    // 适用大区
                    if ( !StringTools.isNullOrNone(obj[11]))
                    {
                        String industryName2 = obj[11].trim();
                        item.setIndustryName2(industryName2);
                    }

                    // 不适用大区
                    if ( !StringTools.isNullOrNone(obj[12]))
                    {
                        String excludeIndustryName2 = obj[12].trim();
                        item.setExcludeIndustryName2(excludeIndustryName2);
                    }

                    // 适用部门
                    if ( !StringTools.isNullOrNone(obj[13]))
                    {
                        String industryName3 = obj[13].trim();
                        item.setIndustryName3(industryName3);
                    }

                    // 不适用部门
                    if ( !StringTools.isNullOrNone(obj[14]))
                    {
                        String excludeIndustryName3 = obj[14].trim();
                        item.setExcludeIndustryName3(excludeIndustryName3);
                    }

                    // 适用人员
                    if ( !StringTools.isNullOrNone(obj[15]))
                    {
                        String stafferName = obj[15].trim();
                        item.setStafferName(stafferName);
                    }

                    // 不适用人员
                    if ( !StringTools.isNullOrNone(obj[16]))
                    {
                        String excludeStafferName = obj[16].trim();
                        item.setExcludeStafferName(excludeStafferName);
                    }

                    // 适用省份
                    if ( !StringTools.isNullOrNone(obj[17]))
                    {
                        String province = obj[17].trim();
                        item.setProvince(province);
                    }

                    // 不适用省份
                    if ( !StringTools.isNullOrNone(obj[18]))
                    {
                        String excludeProvince = obj[18].trim();
                        item.setExcludeProvince(excludeProvince);
                    }

                    // 适用城市
                    if ( !StringTools.isNullOrNone(obj[19]))
                    {
                        String city = obj[19].trim();
                        item.setCity(city);
                    }

                    // 不适用城市
                    if ( !StringTools.isNullOrNone(obj[20]))
                    {
                        String excludeCity = obj[20].trim();
                        item.setExcludeCity(excludeCity);
                    }

                    // 公司承担比例
                    if ( !StringTools.isNullOrNone(obj[21]))
                    {
                        String companyShare = obj[21].trim();
                        item.setCompanyShare(Integer.valueOf(companyShare));
                    }

                    // 个人承担比例
                    if ( !StringTools.isNullOrNone(obj[22]))
                    {
                        String stafferShare = obj[22].trim();
                        item.setStafferShare(Integer.valueOf(stafferShare));
                    }

                    if (item.getCompanyShare()+ item.getStafferShare()!= 100 &&
                            item.getCompanyShare()+item.getStafferShare()!= 0){
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("公司和个人承担比例之和必须为100或者0!")
                                .append("<br>");

                        importError = true;
                    }

                    // 开始日期
                    if ( !StringTools.isNullOrNone(obj[23]))
                    {
                        String beginDate = obj[23].trim();
                        item.setBeginDate(beginDate);
                        if (!StringUtils.isDateValid(beginDate, "yyyy-MM-dd")){
                            builder.append("第[" + currentNumber + "]错误:")
                                    .append("开始日期必须为XXXX-XX-XX格式")
                                    .append("<br>");

                            importError = true;
                        }
                    }

                    // 结束日期
                    if ( !StringTools.isNullOrNone(obj[24]))
                    {
                        String endDate = obj[24].trim();
                        item.setEndDate(endDate);
                        if (!StringUtils.isDateValid(endDate, "yyyy-MM-dd")){
                            builder.append("第[" + currentNumber + "]错误:")
                                    .append("结束日期必须为XXXX-XX-XX格式")
                                    .append("<br>");

                            importError = true;
                        }
                    }

                    // 销售商品品名
                    if ( !StringTools.isNullOrNone(obj[25]))
                    {
                        String productName = obj[25].trim();
                        ProductBean productBean = this.productDAO.findByName(productName);
                        if (productBean == null){
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("销售商品品名不存在:"+productName)
                                    .append("<br>");

                            importError = true;
                        } else{
                            item.setProductId(productBean.getId());
                        }
                    } else{
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("销售商品品名必填")
                                .append("<br>");

                        importError = true;
                    }


                    // 销售商品数量
                    if ( !StringTools.isNullOrNone(obj[26]))
                    {
                        String sailAmount = obj[26].trim();
                        item.setSailAmount(Integer.valueOf(sailAmount));
                    } else{
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("销售商品数量必填")
                                .append("<br>");

                        importError = true;
                    }

                    // 赠送商品品名
                    if ( !StringTools.isNullOrNone(obj[27]))
                    {
                        String giftProductName = obj[27].trim();
                        ProductBean productBean = this.productDAO.findByName(giftProductName);
                        if (productBean == null){
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("赠送商品品名不存在:"+giftProductName)
                                    .append("<br>");

                            importError = true;
                        } else{
                            item.setGiftProductId(productBean.getId());
                        }
                    } else{
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("赠送商品品名必填")
                                .append("<br>");

                        importError = true;
                    }


                    // 赠送商品数量
                    if ( !StringTools.isNullOrNone(obj[28]))
                    {
                        String amount = obj[28].trim();
                        item.setAmount(Integer.valueOf(amount));
                    } else{
                        builder
                                .append("第[" + currentNumber + "]错误:")
                                .append("赠送商品数量必填")
                                .append("<br>");

                        importError = true;
                    }

                    // 赠送商品品名2
                    if ( !StringTools.isNullOrNone(obj[29]))
                    {
                        String giftProductName2 = obj[29].trim();
                        ProductBean productBean = this.productDAO.findByName(giftProductName2);
                        if (productBean == null){
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("赠送商品品名2不存在:"+giftProductName2)
                                    .append("<br>");

                            importError = true;
                        } else{
                            item.setGiftProductId2(productBean.getId());
                        }
                    }


                    // 赠送商品数量2
                    if ( !StringTools.isNullOrNone(obj[30]))
                    {
                        String amount2 = obj[30].trim();
                        item.setAmount2(Integer.valueOf(amount2));
                    }

                    // 赠送商品品名3
                    if ( !StringTools.isNullOrNone(obj[31]))
                    {
                        String giftProductName3 = obj[31].trim();
                        ProductBean productBean = this.productDAO.findByName(giftProductName3);
                        if (productBean == null){
                            builder
                                    .append("第[" + currentNumber + "]错误:")
                                    .append("赠送商品品名3不存在:"+giftProductName3)
                                    .append("<br>");

                            importError = true;
                        } else{
                            item.setGiftProductId3(productBean.getId());
                        }
                    }


                    // 赠送商品数量3
                    if ( !StringTools.isNullOrNone(obj[32]))
                    {
                        String amount3 = obj[32].trim();
                        item.setAmount3(Integer.valueOf(amount3));
                    }

                    //描述
                    if ( !StringTools.isNullOrNone(obj[33]))
                    {
                        String description = obj[33].trim();
                        item.setDescription(description);
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
        } else{
            try{
                this.giftConfigManager.importBeans(user,importItemList);
                request.setAttribute(KeyConstant.MESSAGE, "导入成功");
            }catch(Exception e){
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getMessage());

                return mapping.findForward(url);
            }
        }

        return mapping.findForward(url);
    }


    /**
     * preForAddGiftConfig
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward preForAddGiftConfig(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request,
                                             HttpServletResponse response)
        throws ServletException
    {
        return mapping.findForward("addGiftConfig");
    }

    /**
     * addGiftConfig
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward addGiftConfig(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ProductVSGiftBean bean = new ProductVSGiftBean();

//        String sailType = request.getParameter("sailType");
//        String productType = request.getParameter("productType");

        try
        {
            BeanUtil.getBean(bean, request);
            _logger.info("create ProductVSGiftBean:"+bean);

            User user = Helper.getUser(request);

            this.giftConfigManager.addBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "增加失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryGiftConfig");
    }

    /**
     * updateGiftConfig
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward updateGiftConfig(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        ProductVSGiftBean bean = new ProductVSGiftBean();

        String amount = request.getParameter("amount");
        String sailAmount = request.getParameter("sailAmount");
        String activity = request.getParameter("activity");
        String bank = request.getParameter("bank");
        String beginDate = request.getParameter("beginDate");
        String endDate = request.getParameter("endDate");
        String description = request.getParameter("description");

        try
        {
            BeanUtil.getBean(bean, request);
//            bean.setAmount(Integer.valueOf(amount));
//            bean.setSailAmount(Integer.valueOf(sailAmount));
//            bean.setActivity(activity);
//            bean.setBank(bank);
//            bean.setBeginDate(beginDate);
//            bean.setEndDate(endDate);
//            bean.setDescription(description);
            _logger.info("update ProductVSGiftBean:"+bean);

            User user = Helper.getUser(request);

            giftConfigManager.updateBean(user, bean);

            request.setAttribute(KeyConstant.MESSAGE, "成功操作");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            request.setAttribute(KeyConstant.ERROR_MESSAGE, "更新失败:" + e.getMessage());
        }

        CommonTools.removeParamers(request);

        return mapping.findForward("queryGiftConfig");
    }

    /**
     * findGiftConfig
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward findGiftConfig(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        String id = request.getParameter("id");

        String update = request.getParameter("update");

        ProductVSGiftVO vo = this.productVSGiftDAO.findVO(id);

        if (vo == null)
        {
            request.setAttribute(KeyConstant.ERROR_MESSAGE, "不存在");

            return mapping.findForward("queryGiftConfig");
        }

        request.setAttribute("bean", vo);

        if ("1".equals(update))
        {
            _logger.info("*********update gift config*************"+vo);
            return mapping.findForward("updateGiftConfig");
        }

        return mapping.findForward("detailGiftConfig");
    }

    /**
     * deleteGiftConfig
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     */
    public ActionForward deleteGiftConfig(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
        throws ServletException
    {
        AjaxResult ajax = new AjaxResult();

        try
        {
            String id = request.getParameter("id");

            User user = Helper.getUser(request);

            giftConfigManager.deleteBean(user, id);

            ajax.setSuccess("成功删除");
        }
        catch (Exception e)
        {
            _logger.warn(e, e);

            ajax.setError("删除失败:" + e.getMessage());
        }

        return JSONTools.writeResponse(response, ajax);
    }

    public ProductVSGiftDAO getProductVSGiftDAO() {
        return productVSGiftDAO;
    }

    public void setProductVSGiftDAO(ProductVSGiftDAO productVSGiftDAO) {
        this.productVSGiftDAO = productVSGiftDAO;
    }

    public GiftConfigManager getGiftConfigManager() {
        return giftConfigManager;
    }

    public void setGiftConfigManager(GiftConfigManager giftConfigManager) {
        this.giftConfigManager = giftConfigManager;
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}
