 package com.china.center.oa.sail.action;

 import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.china.center.actionhelper.common.JSONPageSeparateTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.actionhelper.json.AjaxResult;
import com.china.center.common.MYException;
import com.china.center.common.taglib.DefinedCommon;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.client.bean.CiticBranchBean;
import com.china.center.oa.client.bean.CiticVSStafferBean;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.client.dao.CiticBranchDAO;
import com.china.center.oa.client.dao.CiticVSStafferDAO;
import com.china.center.oa.client.dao.CustomerMainDAO;
import com.china.center.oa.client.dao.StafferVSCustomerDAO;
import com.china.center.oa.client.vo.StafferVSCustomerVO;
import com.china.center.oa.client.vs.StafferVSCustomerBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.ProductImportBean;
import com.china.center.oa.product.bean.StorageBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.product.dao.DepotpartDAO;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.dao.ProductImportDAO;
import com.china.center.oa.product.dao.StorageDAO;
import com.china.center.oa.product.dao.StorageRelationDAO;
import com.china.center.oa.product.helper.StorageRelationHelper;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.publics.Helper;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.publics.bean.CityBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.EnumBean;
import com.china.center.oa.publics.bean.ProvinceBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.AppConstant;
import com.china.center.oa.publics.dao.AreaDAO;
import com.china.center.oa.publics.dao.CityDAO;
import com.china.center.oa.publics.dao.DutyDAO;
import com.china.center.oa.publics.dao.EnumDAO;
import com.china.center.oa.publics.dao.ProvinceDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.dao.UserDAO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.publics.vo.UserVO;
import com.china.center.oa.sail.bean.AutoApproveBean;
import com.china.center.oa.sail.bean.BankSailBean;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.BatchApproveBean;
import com.china.center.oa.sail.bean.BatchDropBean;
import com.china.center.oa.sail.bean.BatchSwatchBean;
import com.china.center.oa.sail.bean.ConsignBean;
import com.china.center.oa.sail.bean.DistributionBean;
import com.china.center.oa.sail.bean.EstimateProfitBean;
import com.china.center.oa.sail.bean.ExpressBean;
import com.china.center.oa.sail.bean.ImportProcurementBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.OutImportLogBean;
import com.china.center.oa.sail.bean.PresentFlagBean;
import com.china.center.oa.sail.bean.ReplenishmentBean;
import com.china.center.oa.sail.bean.SailConfBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.dao.BankSailDAO;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.BatchApproveDAO;
import com.china.center.oa.sail.dao.BatchSwatchDAO;
import com.china.center.oa.sail.dao.ConsignDAO;
import com.china.center.oa.sail.dao.EstimateProfitDAO;
import com.china.center.oa.sail.dao.ExpressDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutImportDAO;
import com.china.center.oa.sail.dao.OutImportLogDAO;
import com.china.center.oa.sail.dao.OutImportResultDAO;
import com.china.center.oa.sail.dao.PresentFlagDAO;
import com.china.center.oa.sail.dao.ReplenishmentDAO;
import com.china.center.oa.sail.helper.OutImportHelper;
import com.china.center.oa.sail.manager.OutImportManager;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.sail.manager.SailConfigManager;
import com.china.center.oa.sail.vo.BaseVO;
import com.china.center.oa.sail.vo.OutImportVO;
import com.china.center.oa.stock.constant.StockConstant;
import com.china.center.oa.stock.manager.StockManager;
import com.china.center.oa.stockvssail.listener.FechProductListener;
import com.china.center.oa.tax.bean.UnitBean;
import com.china.center.oa.tax.dao.UnitDAO;
import com.china.center.tools.CommonTools;
import com.china.center.tools.ListTools;
import com.china.center.tools.MathTools;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.RequestTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.china.center.tools.WriteFileBuffer;

 /**
  *
  * 中信订单处理
  *
  * @author fangliwen 2013-10-15
  */
 public class OutImportAction extends DispatchAction
 {
     private final Log _logger = LogFactory.getLog(getClass());

     private CustomerMainDAO customerMainDAO = null;

     private OutImportDAO outImportDAO = null;

     private OutImportManager outImportManager = null;

     private OutImportResultDAO outImportResultDAO  = null;

     private ReplenishmentDAO replenishmentDAO  = null;

     private StorageRelationManager storageRelationManager = null;

     private ProductDAO productDAO = null;

     private OutImportLogDAO outImportLogDAO = null;

     private StafferDAO stafferDAO = null;

     private UserDAO userDAO = null;

     private StafferVSCustomerDAO stafferVSCustomerDAO = null;

     private CiticVSStafferDAO citicVSStafferDAO = null;

     private CiticBranchDAO citicBranchDAO = null;

     private ExpressDAO expressDAO = null;

     private BatchApproveDAO batchApproveDAO = null;

     private BatchSwatchDAO batchSwatchDAO = null;

     private ConsignDAO consignDAO = null;

     private DepotDAO depotDAO = null;

     private DepotpartDAO depotpartDAO = null;

     private OutDAO outDAO = null;

     private DutyDAO dutyDAO = null;

     private BaseDAO baseDAO = null;

     private StorageDAO storageDAO = null;

     private StorageRelationDAO storageRelationDAO = null;

     private ProvinceDAO provinceDAO = null;

     private CityDAO cityDAO = null;

     private AreaDAO areaDAO = null;

     private BankSailDAO bankSailDAO = null;

     private EstimateProfitDAO estimateProfitDAO = null;

     private PriceConfigDAO priceConfigDAO = null;

     private InvoiceinsDAO invoiceinsDAO = null;

     private EnumDAO enumDAO = null;

     private PriceConfigManager priceConfigManager = null;

     private SailConfigManager sailConfigManager = null;

     private OutManager outManager = null;

     private ProductImportDAO productImportDAO = null;

     private PresentFlagDAO presentFlagDAO = null;
     
     private StockManager stockManager = null;
     
     private FechProductListener fechProductListenerTaxGlueImpl =null;
     
     private UnitDAO unitDAO;

     private static String QUERYOUTIMPORT = "queryOutImport";

     private static String QUERYOUTIMPORTLOG = "queryOutImportLog";

     private static String QUERYBANKSAIL = "queryBankSail";



     public OutImportAction()
     {

     }

     /**
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward importOut(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         _logger.info("***********importOut now*************");
         User user = Helper.getUser(request);

         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<OutImportBean> importItemList = new ArrayList<OutImportBean>();

         StringBuilder builder = new StringBuilder();
         _logger.info("***********importOut step1*************");
         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOut");
         }
         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOut");
         }

         String itype = rds.getParameter("type");

         ReaderFile reader = ReadeFileFactory.getXLSReader();
          try
         {
             reader.readFile(rds.getUniqueInputStream());
             while (reader.hasNext())
             {
                 String[] obj = StringUtils.fillObj((String[])reader.next(), 50);

                 // 第一行忽略
                 if (reader.getCurrentLineNumber() == 1)
                 {
                     continue;
                 }

                 // #761 列3、4、5都为空表示是空行过滤掉
                 if (StringTools.isNullOrNone(obj[3])
                         && StringTools.isNullOrNone(obj[4])
                         && StringTools.isNullOrNone(obj[5])) {
                     continue;
                 }

                 int currentNumber = reader.getCurrentLineNumber();

                 if (obj.length >= 2 )
                 {
                     OutImportBean bean = new OutImportBean();

                     bean.setItype(MathTools.parseInt(itype));

                     boolean error = innerAdd(1, bean, obj, builder, currentNumber);

                     if (!importError)
                     {
                         importError = error;
                     }

                     bean.setLogTime(TimeTools.now());

                     // 操作人
                     bean.setReason(user.getStafferId());

                     importItemList.add(bean);

                 }
                 else
                 {
                     builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("数据长度不足32格错误")
                         .append("<br>");

                     importError = true;
                 }
             }


         }catch (Exception e)
         {
             _logger.error(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

             return mapping.findForward("importOut");
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

          if (importItemList.size()>= 500){
              builder
                      .append("一次导入不得超过500行："+importItemList.size())
                      .append("<br>");

              importError = true;
          }

         if (importError){

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());

             return mapping.findForward("importOut");
         }

         String batchId = "";

         _logger.info("before outImportManager.addBean***");

         try
         {
             batchId = outImportManager.addBean(importItemList);
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importOut");
         }

         // 异步处理 - 只针对初始或失败的行项目
         List<OutImportBean> list = outImportDAO.queryEntityBeansByFK(batchId);

         if (!ListTools.isEmptyOrNull(list))
         {
             _logger.info("before outImportManager.processAsyn***"+list.size());
             outImportManager.processAsyn(list);
         }

         return mapping.findForward("queryOutImport");
     }

     /**
      * #770 重写中信导入,合并两种类型
      * @param flag 1 代表中信,2代表其他(原浦发导入)
      * @param bean
      * @param obj
      * @param builder
      * @param currentNumber
      * @return
      */
     private boolean innerAdd(int flag, OutImportBean bean, String[] obj, StringBuilder builder, int currentNumber)
     {
         boolean importError = false;
         ProductBean productBean = null;

         // 订单类型
         String ot = obj[0];
         if ( !StringTools.isNullOrNone(ot))
         {
             String outType = ot.trim();
             String [] outTypes = OutImportConstant.outTypesArr;

             for(int i = 0; i< outTypes.length; i++)
             {
                 if (outType.equals(outTypes[i]))
                 {
                     bean.setOutType(OutImportConstant.outTypeiArr[i]);

                     break;
                 }
             }

             if (bean.getOutType() == -1)
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("订单类型不存在")
                         .append("<br>");

                 importError = true;
             }
         }else
         {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("订单类型不能为空")
                     .append("<br>");

             importError = true;
         }

         // 二级分行名称
         String secondBranch = obj[1];
         if ( !StringTools.isNullOrNone(secondBranch))
         {
             bean.setSecondBranch(secondBranch.trim());
         }

         // 联行网点号
         String lhwd = obj[2];
         if ( !StringTools.isNullOrNone(lhwd))
         {
             bean.setComunicationBranch(lhwd.trim());
             bean.setLhwd(lhwd.trim());
         }


         // 网点名称 - 永银的客户,不做强制要求事先须存在，没有实时创建一条 -- 20131125 修改为客户须事先存在
         // comunicatonBranchName
         String cn = obj[3];
         if ( !StringTools.isNullOrNone(cn))
         {
             String custName = cn.trim();
             List<CustomerBean> cbeans = customerMainDAO.queryByName(custName);
             if (ListTools.isEmptyOrNull(cbeans))
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("网点名称（客户）" + custName + "不存在")
                         .append("<br>");

                 importError = true;
             }else{
                 //#731
                 CustomerBean customerBean = cbeans.get(0);
                 if (customerBean.getOstatus() !=0){
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("客户已停用：" + custName )
                             .append("<br>");

                     importError = true;
                 }

                 if (bean.getOutType() != OutConstant.OUTTYPE_OUT_SWATCH)
                 {
                     StafferVSCustomerBean vsBean = stafferVSCustomerDAO.findByUnique(customerBean.getId());

                     if (null == vsBean)
                     {
                         builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("网点名称（客户）" + custName + "没有与业务员挂靠关系")
                                 .append("<br>");

                         importError = true;
                     }else{
                         StafferBean stafferBean = this.stafferDAO.find(vsBean.getStafferId());
                         if (stafferBean!= null &&
                                 ("停用".equals(stafferBean.getName()) || "重复".equals(stafferBean.getName()))){
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("停用或重复名下的客户不能导入" + custName)
                                     .append("<br>");

                             importError = true;
                         } else {
                             bean.setComunicatonBranchName(custName);
                             bean.setStafferId2(stafferBean.getId());

                             String branchName = this.customerMainDAO.queryBranchName(custName);
                             if(StringTools.isNullOrNone(branchName)){
                                 builder
                                         .append("第[" + currentNumber + "]错误:")
                                         .append("t_center_customer表中reserver1字段(分行)不能为空：" + custName)
                                         .append("<br>");

                                 importError = true;
                             } else{
                                 bean.setBranchName(branchName);
                             }
                         }
                     }
                 }else{
                     bean.setComunicatonBranchName("公共客户");
                 }
             }
         }
         else
         {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("网点名称不能为空")
                     .append("<br>");

             importError = true;
         }

         // 商品编码
         String code = obj[4];
         if ( !StringTools.isNullOrNone(code))
         {
             bean.setProductCode(code.trim());
         } else if (flag == 1)
         {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("商品编码不可为空")
                     .append("<br>");

             importError = true;
         }

         // 商品名称
         String productName = obj[5];
         if (flag == 1){
             if ( !StringTools.isNullOrNone(productName))
             {
                 // 姓氏
                 bean.setFirstName("N/A");
             }
         } else{
             if ( !StringTools.isNullOrNone(productName))
             {
                 String name = productName.trim();

                 // 姓氏
                 bean.setFirstName("N/A");
                 productBean = productDAO.findByName(name);
                 if (null == productBean)
                 {
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("产品["+name+"]的产品不存在,请创建")
                             .append("<br>");

                     importError = true;
                 }else{
                     bean.setProductId(productBean.getId());
                     bean.setProductName(productBean.getName());
                 }
             }
             else
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("产品不可为空")
                         .append("<br>");

                 importError = true;
             }
         }


         // 数量
         String amt = obj[6];
         if ( !StringTools.isNullOrNone(amt))
         {
             int amount = MathTools.parseInt(amt.trim());

             if (amount <= 0){
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("数量须大于0")
                         .append("<br>");

                 importError = true;
             }else{
                 bean.setAmount(amount);
             }
         }
         else
         {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("数量不可为空")
                     .append("<br>");

             importError = true;
         }

         // 单价
         String p = obj[7];
         if ( !StringTools.isNullOrNone(p))
         {
             double price = MathTools.parseDouble(p.trim());
             bean.setPrice(price);

             if (bean.getOutType() != OutConstant.OUTTYPE_OUT_PRESENT){
                 if (price <= 0)
                 {
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("非赠送类型时，单价须大于0")
                             .append("<br>");

                     importError = true;
                 }
             } else if (bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT
                 && bean.getPrice() != 0)
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("赠送类型订单单价须为0")
                         .append("<br>");

                 importError = true;
             }
         }
         else
         {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("单价不可为空")
                     .append("<br>");

             importError = true;
         }

         // 规格
         String style = obj[8];
         if ( !StringTools.isNullOrNone(style))
         {
             bean.setStyle(style.trim());
         }

         // 金额
         String v = obj[9];
         if ( !StringTools.isNullOrNone(v))
         {
             double value = MathTools.parseDouble(v.trim());
             bean.setValue(value);

             if (value != (bean.getAmount() * bean.getPrice()))
             {
                 bean.setValue(bean.getAmount() * bean.getPrice());
             }
         }
         else
         {
             bean.setValue(bean.getAmount() * bean.getPrice());
         }

         //  中收金额
         String mid = obj[10];
         if ( !StringTools.isNullOrNone(mid))
         {
             String value = mid.trim();

             if (value.equals("无")) {
                 bean.setMidValue(0);
             } else {
                 double midValue = MathTools.parseDouble(value);

                 if (midValue <= 0) {
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("中收为0请用'无'代替")
                             .append("<br>");

                     importError = true;
                 } else {
                     bean.setMidValue(midValue);
                 }
             }
         }


         // 计划交付日期
         String arriveDate = obj[11];
         if ( !StringTools.isNullOrNone(arriveDate))
         {
             bean.setArriveDate(arriveDate.trim());
         }

         // 库存类型
         String st = obj[12];
         if ( !StringTools.isNullOrNone(st))
         {
             int storageType = OutImportHelper.getStorageType(st.trim());
             bean.setStorageType(storageType);
         }

         // 中信订单号
         String citicNo = obj[13];
         if ( !StringTools.isNullOrNone(citicNo))
         {
             bean.setCiticNo(citicNo);
         }
         else
         {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("银行订单号不能为空")
                     .append("<br>");

             importError = true;
         }

         // 开票性质
         String in = obj[14];
         if ( !StringTools.isNullOrNone(in))
         {
             int invoiceNature = OutImportHelper.getInvoiceNature(in.trim());
             if (invoiceNature == -1){
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("开票性质只能为:"+OutImportHelper.getInvoiceNature())
                         .append("<br>");

                 importError = true;
             } else{
                 bean.setInvoiceNature(invoiceNature);
             }
         }

         // 开票抬头
         String ih = obj[15];
         if ( !StringTools.isNullOrNone(ih))
         {
             bean.setInvoiceHead(ih.trim());
         }

         //#770 增值税--购方名称、增值税--购方税号、增值税--银行信息、增值税--购方地址电话
         //增值税--购方名称
         String gfmc = obj[16];
         if (!StringTools.isNullOrNone(gfmc)){
             bean.setGfmc(gfmc.trim());
         }

         String gfsh = obj[17];
         if (!StringTools.isNullOrNone(gfsh)){
             bean.setGfsh(gfsh.trim());
         }

         String gfyh = obj[18];
         if (!StringTools.isNullOrNone(gfyh)){
             bean.setGfyh(gfyh.trim());
         }

         String gfdz = obj[19];
         if (!StringTools.isNullOrNone(gfdz)){
             bean.setGfdz(gfdz.trim());
         }

         // 绑定单号
         String bindNo = obj[20];
         if ( !StringTools.isNullOrNone(bindNo))
         {
             bean.setBindNo(bindNo.trim());
         }

         // 开票类型
         String invoiceType = obj[21];
         if ( !StringTools.isNullOrNone(invoiceType))
         {
               bean.setInvoiceType(OutImportHelper.getInvoiceType(invoiceType.trim()));
         }

         // 开票品名
         String invoiceName = obj[22];
         if ( !StringTools.isNullOrNone(invoiceName))
         {
             bean.setInvoiceName(invoiceName.trim());
         }

         // 开票金额
         String invoiceMoney = obj[23];
         if ( !StringTools.isNullOrNone(invoiceMoney))
         {
             bean.setInvoiceMoney(MathTools.parseDouble(invoiceMoney.trim()));
         }

         // 省
         String provinceName = obj[24];
         if ( !StringTools.isNullOrNone(provinceName))
         {
             ProvinceBean province = provinceDAO.findByUnique(provinceName.trim());
             if (null != province) {
                 bean.setProvinceId(province.getId());
             }
         }

         // 市
         String cityName = obj[25];
         if ( !StringTools.isNullOrNone(cityName))
         {
             CityBean city = cityDAO.findByUnique(cityName.trim());
             if (null != city){
                 bean.setCityId(city.getId());

                 if (!city.getParentId().equals(bean.getProvinceId())){
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("省市信息不一致")
                             .append("<br>");

                     importError = true;
                 }
             }
         }

         // 详细地址
         String address = obj[26];
         if ( !StringTools.isNullOrNone(address))
         {
             bean.setAddress(address.trim());
         }

         // 收货人
         String receiver = obj[27];
         if ( !StringTools.isNullOrNone(receiver))
         {
             bean.setReceiver(receiver.trim());
         }

         // 收货人手机
         String phone = obj[28];
         if ( !StringTools.isNullOrNone(phone))
         {
             bean.setHandPhone(phone.trim());
         }

         // 姓氏
         String firstName = obj[29];
         if ( !StringTools.isNullOrNone(firstName))
         {
             bean.setFirstName(firstName.trim());
         }
         else
             bean.setFirstName("N/A");

         // 银行订单日期
         String citicDate = obj[30];
         if ( !StringTools.isNullOrNone(citicDate))
         {
             String date = citicDate.trim();
             String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
             Pattern pattern = Pattern.compile(eL);
             Matcher m = pattern.matcher(date);
             boolean dateFlag = m.matches();
             if (!dateFlag) {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("银行订单日期格式错误，如 2000-01-01")
                         .append("<br>");

                 importError = true;
             }else{
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                 try {
                     Date now = new Date();
                     Date date1 = sdf.parse(date);
                     if (date1.after(now)) {
                         builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("银行订单日期必须小于等于当天日期")
                                 .append("<br>");

                         importError = true;
                     }
                 } catch (ParseException e) {
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("银行订单日期格式错误，如 2000-01-01")
                             .append("<br>");

                     importError = true;
                 }
                 bean.setCiticOrderDate(date);
             }
         } else {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("中信订单日期不能为空")
                     .append("<br>");

             importError = true;
         }

         // 仓库
         String depotName = obj[31];
         if ( !StringTools.isNullOrNone(ot))
         {
             DepotBean depot = depotDAO.findByUnique(depotName.trim());

             if (null == depot)
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("仓库" + depotName + "不存在")
                         .append("<br>");

                 importError = true;
             }else{
                 bean.setDepotId(depot.getId());
             }
         }else
         {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("仓库不能为空")
                     .append("<br>");

             importError = true;
         }

         // 仓区
         String depotpartName = obj[32];
         if ( !StringTools.isNullOrNone(depotpartName))
         {
             DepotpartBean depotpart = depotpartDAO.findByUnique(depotpartName.trim());

             if (null == depotpart)
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("仓区[" + depotpartName + "]不存在")
                         .append("<br>");

                 importError = true;
             }else{
                 if (!depotpart.getLocationId().equals(bean.getDepotId()))
                 {
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("仓区[" +depotpartName + "]对应的仓库不是导入中的仓库")
                             .append("<br>");

                     importError = true;
                 }else
                 {
                     bean.setDepotpartId(depotpart.getId());
                     bean.setComunicationBranch(depotpart.getName());
                 }
             }
         }else
         {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("仓区不能为空")
                     .append("<br>");

             importError = true;
         }

         // 职员 - 领样类型时 必须
         String stafferName = obj[33];
         if ( !StringTools.isNullOrNone(stafferName))
         {
             StafferBean staffer = stafferDAO.findByUnique(stafferName.trim());

             if (null == staffer)
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("职员["+stafferName.trim()+"]不存在")
                         .append("<br>");

                 importError = true;
             }else{
                 bean.setStafferId(staffer.getId());
             }
         }else
         {
             if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("领样类型库单时职员不能为空")
                         .append("<br>");

                 importError = true;
             }
         }

         // 备注
         String description = obj[34];
         if ( !StringTools.isNullOrNone(description))
         {
             bean.setDescription(description.trim());
         }

         // 发货方式
         String ship = obj[35];
         if ( !StringTools.isNullOrNone(ship))
         {
             boolean has = false;
             String shipping = ship.trim();
             for (int i = 0 ; i < OutImportConstant.shipping.length; i++)
             {
                 if (OutImportConstant.shipping[i].equals(shipping))
                 {
                     has = true;

                     bean.setShipping(OutImportConstant.ishipping[i]);

                     break;
                 }
             }

             if (!has)
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("发货方式不对,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,代收货款,空发]中之一")
                         .append("<br>");

                 importError = true;
             }

             //仓库为“可发库”时，发货方式控制不可选择“空发
             if(DepotConstant.CENTER_DEPOT_ID.equals(bean.getDepotId())
                     && bean.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING){
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("仓库为“可发库”时，发货方式控制不可选择'空发'")
                         .append("<br>");

                 importError = true;
             }
             //仓库为“空退空开库（仅限商务部操作）”时，发货方式控制只能选择“空发”
             else if(DepotConstant.KTKK_SW_DEPOT_ID.equals(bean.getDepotId())
                     && bean.getShipping() != OutConstant.OUT_SHIPPING_NOTSHIPPING){
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("仓库为“空退空开库（仅限商务部操作）”时，发货方式控制只能选择“空发”'")
                         .append("<br>");

                 importError = true;
             }
         }else
         {
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("发货方式不能为空,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,代收货款,空发]中之一")
                     .append("<br>");

             importError = true;
         }

         //校验省、市、地址、收货人信息
         if (bean.getShipping() == OutConstant.OUT_SHIPPING_3PL || bean.getShipping() == OutConstant.OUT_SHIPPING_TRANSPORT ||
                 bean.getShipping() == OutConstant.OUT_SHIPPING_3PLANDDTRANSPORT){
            if( StringTools.isNullOrNone(bean.getProvinceId())) {
                builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("快递、货运时省名为空或不存在")
                        .append("<br>");

                importError = true;
            }

            if (StringTools.isNullOrNone(bean.getCityId())){
                builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("快递、货运时市名为空或不存在")
                        .append("<br>");

                importError = true;
            }

            if (StringTools.isNullOrNone(bean.getAddress()) || bean.getAddress().length()< 5){
                builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("快递、货运时详细地址不能少于5个字")
                        .append("<br>");

                importError = true;
            }

            if (StringTools.isNullOrNone(bean.getReceiver()) || bean.getReceiver().length() < 2){
                builder
                        .append("第[" + currentNumber + "]错误:")
                        .append("快递、货运时收货人不能少于2个字")
                        .append("<br>");

                importError = true;
            }

             if (bean.getHandPhone()!= null && bean.getHandPhone().length() != 11) {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("收货人手机号码不是11位")
                         .append("<br>");

                 importError = true;
             }
         }

         //校验快递和快递支付方式
         if (bean.getShipping() == OutConstant.OUT_SHIPPING_3PL || bean.getShipping() == OutConstant.OUT_SHIPPING_3PLANDDTRANSPORT
                 || bean.getShipping() == OutConstant.OUT_SHIPPING_PROXY)
         {
             // 如果发货方式是快递或快递+货运 ,则快递须为必填
             String t1 = obj[36];
             if ( !StringTools.isNullOrNone(t1))
             {
                 String transport1 = t1.trim();
                 ExpressBean express = expressDAO.findByUnique(transport1);

                 if (null == express)
                 {
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("快递方式"+ transport1 +"不存在")
                             .append("<br>");

                     importError = true;
                 }else{
                     bean.setTransport1(MathTools.parseInt(express.getId()));
                 }
             }else
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("快递方式不能为空")
                         .append("<br>");

                 importError = true;
             }

             // 快递支付方式也不能为空
             String ep = obj[37];
             if ( !StringTools.isNullOrNone(ep))
             {
                 String expressPay = ep.trim();

                 boolean isexists = false;

                 for (int i = 0; i < OutImportConstant.expressPay.length; i++)
                 {
                     if (expressPay.equals(OutImportConstant.expressPay[i]))
                     {
                         isexists = true;

                         bean.setExpressPay(OutImportConstant.iexpressPay[i]);

                         break;
                     }
                 }

                 if (!isexists)
                 {
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("快递支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
                             .append("<br>");

                     importError = true;
                 }
             }else
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("快递支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
                         .append("<br>");

                 importError = true;
             }
         }

         // 校验货运和货运支付方式
         if (bean.getShipping() == OutConstant.OUT_SHIPPING_TRANSPORT || bean.getShipping() ==  OutConstant.OUT_SHIPPING_3PLANDDTRANSPORT)
         {
             // 如果发货方式是快递或快递+货运 ,则快递须为必填
             String t2 = obj[38];
             if ( !StringTools.isNullOrNone(t2))
             {
                 String transport2 = t2.trim();

                 ExpressBean express = expressDAO.findByUnique(transport2);

                 if (null == express)
                 {
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("货运方式"+ transport2 +"不存在")
                             .append("<br>");

                     importError = true;
                 }else{
                     bean.setTransport2(MathTools.parseInt(express.getId()));
                 }
             }else
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("货运方式不能为空")
                         .append("<br>");

                 importError = true;
             }

             // 快递支付方式也不能为空
             String tp = obj[39];
             if ( !StringTools.isNullOrNone(tp))
             {
                 String expressPay = tp.trim();

                 boolean isexists = false;

                 for (int i = 0; i < OutImportConstant.expressPay.length; i++)
                 {
                     if (expressPay.equals(OutImportConstant.expressPay[i]))
                     {
                         isexists = true;

                         bean.setTransportPay(OutImportConstant.iexpressPay[i]);

                         break;
                     }
                 }

                 if (!isexists)
                 {
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("货运支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
                             .append("<br>");

                     importError = true;
                 }
             }else
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("货运支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
                         .append("<br>");

                 importError = true;
             }
         }

         // 回款天数
         String reday = obj[40];
         if ( !StringTools.isNullOrNone(reday))
         {
             bean.setReday(MathTools.parseInt(reday.trim()));
         }else{
             bean.setReday(OutImportConstant.CITIC_REDAY);
         }

         //二级类型
         String pf = obj[41];
         if (bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT
                 || bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH) {
             if ( !StringTools.isNullOrNone(pf))
             {
                 String presentFlag = pf.trim();

                 ConditionParse conditionParse = new ConditionParse();
                 conditionParse.addWhereStr();
                 conditionParse.addCondition("name","=",presentFlag);
                 List<PresentFlagBean> presentFlagBeans = this.presentFlagDAO.queryEntityBeansByCondition(conditionParse);
                 if (ListTools.isEmptyOrNull(presentFlagBeans)){
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("二级类型不存在")
                             .append("<br>");

                     importError = true;
                 } else{
                     bean.setPresentFlag(Integer.valueOf(presentFlagBeans.get(0).getType()));
                 }
             }else{
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("赠送或个人领样单据时二级类型不能为空")
                         .append("<br>");

                 importError = true;
             }
         }

         // 2015/09/29 客户姓名
         String cname = obj[42];
         if ( !StringTools.isNullOrNone(cname))
         {
             bean.setCustomerName(cname.trim());
         }

         // #426 2017/2/28 固定电话
         String tel = obj[43];
         if ( !StringTools.isNullOrNone(tel))
         {
             bean.setTelephone(tel.trim());
         }

         // #461 手机和电话不能都为空
         if (StringTools.isNullOrNone(bean.getHandPhone()) && StringTools.isNullOrNone(bean.getTelephone())){
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("收货人手机或固定电话不能都为空")
                     .append("<br>");

             importError = true;
         }

         // #62 2017/6/13 是否直邮,状态默认为0 输入值为N时为0，Y时为1
         String di = obj[44];
         if ( !StringTools.isNullOrNone(di))
         {
             String direct = di.trim();
             if ("Y".equalsIgnoreCase(direct)){
                 bean.setDirect(1);
             } else if ("N".equalsIgnoreCase(direct)){
                 bean.setDirect(0);
             } else {
                 builder.append("第[" + currentNumber + "]错误:")
                         .append("是否直邮只能为Y或N")
                         .append("<br>");

                 importError = true;
             }
         } else{
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("是否直邮不能为空")
                     .append("<br>");

             importError = true;
         }


         String cnl = obj[45];
         if ( !StringTools.isNullOrNone(cnl))
         {
             String channel = cnl.trim();

             if (channel.length()>4){
                 builder.append("第[" + currentNumber + "]错误:")
                         .append("渠道不超过4个字符")
                         .append("<br>");

                 importError = true;
             } else {
                 final String type = "311";
                 EnumBean enumBean = new EnumBean();
                 enumBean.setType(type);
                 enumBean.setValue(channel);
                 List<EnumBean> enumBeans = this.enumDAO.findByType(type);
                 if (!ListTools.isEmptyOrNull(enumBeans) && enumBeans.contains(enumBean)){
                     bean.setChannel(channel);
                 } else{
                     builder.append("第[" + currentNumber + "]错误:")
                             .append("渠道只能是"+EnumBean.join(enumBeans))
                             .append("<br>");

                     importError = true;
                 }
             }
         }

         //中收激励金额
         if (flag == 1){
             try {
                 ProductImportBean productImportBean = null;
                 //#505 中信导入、银行领样导入都读取Product import表
                 String customerName = bean.getComunicatonBranchName();
                 int industry = this.checkStafferIndustry(bean.getStafferId2());
                 if (industry == OutConstant.QB_INDUSTRY_MJ){
                     //#899 当登录人是孟君，读取在售表BANK= 零售拍卖客户
                     productImportBean = this.outManager.getProductImportBean(OutConstant.LS_PMKH, null,
                             bean.getProductCode(), null, bean.getCiticOrderDate(), bean.getOutType(), true);
                 } else if(industry == OutConstant.QB_INDUSTRY_S){
                     //钱币事业部
                     try {
                         productImportBean = this.outManager.getProductImportBean(OutConstant.QB_INDUSTRY, null,
                                 bean.getProductCode(), null, bean.getCiticOrderDate(), bean.getOutType(), true);
                     }catch (MYException e){
                         //2020-01-30 如果职员挂靠钱币事业部，读取在售表BANK= 钱币事业部，没找到产品，就再根据客户银行找产品，如还没找到，就报错
                         productImportBean = this.outManager.getProductImportBean(customerName, bean.getBranchName(),
                                 bean.getProductCode(), bean.getChannel(), bean.getCiticOrderDate(), bean.getOutType(), true);
                     }
                 } else if(industry == OutConstant.LS_INDUSTRY_S){
                     //零售事业部
                     try {
                         productImportBean = this.outManager.getProductImportBean(OutConstant.LS_INDUSTRY, null,
                                 bean.getProductCode(), null, bean.getCiticOrderDate(), bean.getOutType(), true);
                     }catch (MYException e){
                         //如果没找到产品，就再根据客户银行找产品，如还没找到，就报错
                         productImportBean = this.outManager.getProductImportBean(customerName, bean.getBranchName(),
                                 bean.getProductCode(), bean.getChannel(), bean.getCiticOrderDate(), bean.getOutType(), true);
                     }
                 } else{
                     productImportBean = this.outManager.getProductImportBean(customerName, bean.getBranchName(),
                             bean.getProductCode(), bean.getChannel(), bean.getCiticOrderDate(), bean.getOutType(), true);
                 }

                 productBean = this.productDAO.findByUnique(productImportBean.getCode());
                 if (productBean == null){
                     builder.append("第[" + currentNumber + "]错误:")
                             .append("产品编码不存在:"+productImportBean.getCode())
                             .append("<br>");

                     importError = true;
                 } else{
                     //#907 匹配到的主数据渠道必须与导入模板中一致
                     //case1 主数据渠道为空,但是导入模板非空
                     //case2 主数据渠道不为空,但与模版不一致
                     //2020-03012 暂先不上线
//                     String channel = productImportBean.getChannel();
//                     if ((StringTools.isNullOrNone(channel) && !StringTools.isNullOrNone(bean.getChannel()))
//                     || (!StringTools.isNullOrNone(channel) && !channel.equals(bean.getChannel()))){
//                         builder.append("第[" + currentNumber + "]错误:")
//                                 .append("产品主数据渠道必须与导入模板中一致:"+productImportBean.getId())
//                                 .append("<br>");
//
//                         importError = true;
//                     }
                     bean.setProductId(productBean.getId());
                     bean.setProductName(productBean.getName());
                     bean.setProductImportId(productImportBean.getId());
                 }

                 //中信导入的销售出库才设置中收激励金额
                 if (bean.getOutType() == OutConstant.OUTTYPE_OUT_COMMON
                         && bean.getItype() == 0){
                     bean.setIbMoney(productImportBean.getIbMoney());
                     bean.setMotivationMoney(productImportBean.getMotivationMoney());
                     bean.setIbMoney2(productImportBean.getIbMoney2());
                     bean.setMotivationMoney2(productImportBean.getMotivationMoney2());
                     bean.setYkibMoney(productImportBean.getYkibMoney());

                     if (this.isOnlineCustomer(bean.getComunicatonBranchName())){
                         bean.setPlatformFee(bean.getPrice()*0.01);
                     } else{
                         bean.setPlatformFee(productImportBean.getPlatformFee());
                     }

                     bean.setCash(productImportBean.getCash());
                     bean.setCash2(productImportBean.getCash2());
                     bean.setGrossProfit(productImportBean.getGrossProfit());

                     //#625 OA出库单数量等于折算系数*开单数量
                     double amount = Math.round(bean.getAmount()*productImportBean.getRated());
                     bean.setAmount(new BigDecimal(amount).intValueExact());
                     bean.setPrice(bean.getPrice()/productImportBean.getRated());
                     bean.setValue(bean.getAmount() * bean.getPrice());
                 }
             }catch (MYException e){
                 _logger.error(e);
                 builder.append("第[" + currentNumber + "]错误:")
                         .append(e.getMessage())
                         .append("<br>");

                 importError = true;
             }
         } else{
             if (this.isOnlineCustomer(bean.getComunicatonBranchName())){
                 bean.setPlatformFee(bean.getPrice()*0.01);
             }
         }


         //交货方式
         String dlv = obj[46];
         if ( !StringTools.isNullOrNone(dlv))
         {
             String delivery = dlv.trim();
             if ("现货".equalsIgnoreCase(delivery)){
                 bean.setDelivery(delivery);
             } else if ("预售".equalsIgnoreCase(delivery)){
                 bean.setDelivery(delivery);
             } else {
                 builder.append("第[" + currentNumber + "]错误:")
                         .append("交货方式只能为现货/预售")
                         .append("<br>");

                 importError = true;
             }
         }

         //pos付款方
         String posPayer = obj[47];
         if ( !StringTools.isNullOrNone(posPayer))
         {
             bean.setPosPayer(posPayer.trim());
         }

         //推荐标示
         String recommendation = obj[48];
         if ( !StringTools.isNullOrNone(recommendation))
         {
             bean.setRecommendation(recommendation.trim());
         }

         //#629 虚拟订单
         String vs = obj[49];
         if ( !StringTools.isNullOrNone(vs))
         {
             String virtualStatus = vs.trim();
             if ("是".equals(virtualStatus)){
                 bean.setVirtualStatus(1);
             } else if("否".equals(virtualStatus)){
                 bean.setVirtualStatus(0);
             } else{
                 builder.append("第[" + currentNumber + "]错误:")
                         .append("虚拟订单只能为是/否")
                         .append("<br>");

                 importError = true;
             }
         }

         //#669 订单导入控制结算价不能为0
         double iprice = this.outManager.getIprice(bean, productBean);
         if (iprice == 0)
         {
             builder.append("第[" + currentNumber + "]错误:")
                     .append("业务员结算价不能为0")
                     .append("<br>");

             importError = true;
         }

         return importError;
     }

     private boolean isOnlineCustomer(String customerName){
         if ("永银文化-天猫".equals(customerName)
                 || "钱币交易部-淘宝".equals(customerName)
                 || "中金国华-天猫".equals(customerName)
                || "钱币交易部-京东".equals(customerName)
                ||"中金国华-京东".equals(customerName)){
             return true;
         } else{
             return false;
         }
     }

     /**
      * 检查职员事业部
      * @param stafferId
      * @return
      */
     private int checkStafferIndustry(String stafferId){
         if (StringTools.isNullOrNone(stafferId)){
             return 0;
         } else{
             StafferVO sb = this.stafferDAO.findVO(stafferId);
             if (sb == null){
                 return 0;
             } else{
                 //孟君工号
                 if ("YY02472".equals(sb.getCode())){
                     return OutConstant.QB_INDUSTRY_MJ;
                 } else if (OutConstant.QB_INDUSTRY_ID.equals(sb.getIndustryId())){
                    return OutConstant.QB_INDUSTRY_S;
                 } else if (OutConstant.LS_INDUSTRY_ID.equals(sb.getIndustryId())){
                     return OutConstant.LS_INDUSTRY_S;
                 } else{
                     return 0;
                 }
             }
         }
     }



     /**
      * importOutForPufa
      * 浦发销售导入
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward importOutForPufa(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         User user = Helper.getUser(request);

         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<OutImportBean> importItemList = new ArrayList<OutImportBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOut");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOut");
         }

         String itype = rds.getParameter("type");

         ReaderFile reader = ReadeFileFactory.getXLSReader();

         try
         {
             reader.readFile(rds.getUniqueInputStream());

             while (reader.hasNext())
             {
                 String[] obj = StringUtils.fillObj((String[])reader.next(), 50);

                 // 第一行忽略
                 if (reader.getCurrentLineNumber() == 1)
                 {
                     continue;
                 }

                 // #761 列3、4、5都为空表示是空行过滤掉
                 if (StringTools.isNullOrNone(obj[3])
                         && StringTools.isNullOrNone(obj[4])
                         && StringTools.isNullOrNone(obj[5])) {
                     continue;
                 }

                 int currentNumber = reader.getCurrentLineNumber();

                 if (obj.length >= 2 )
                 {
                     OutImportBean bean = new OutImportBean();

                     boolean error = innerAdd(2, bean, obj, builder, currentNumber);

                     if (!importError)
                     {
                         importError = error;
                     }

                     bean.setLogTime(TimeTools.now());

                     bean.setItype(MathTools.parseInt(itype));

                     bean.setReason(user.getStafferId());

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

             return mapping.findForward("importOut");
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

             return mapping.findForward("importOut");
         }

         String batchId = "";

         try
         {
             batchId = outImportManager.addPufaBean(importItemList);
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importOut");
         }

         _logger.info("***finish add batchId****"+batchId);
         // 异步处理 - 只针对初始或失败的行项目
         List<OutImportBean> list = outImportDAO.queryEntityBeansByFK(batchId);

         if (!ListTools.isEmptyOrNull(list))
         {
             _logger.info("***before processAsync****"+list.size());
             outImportManager.processAsyn(list);
         }else{
             _logger.warn("out import list is empty!!!");
         }

         return mapping.findForward("queryOutImport");
     }

     /**
      * 中信销售-》普通导入1
      * @param bean
      * @param obj
      * @param builder
      * @param currentNumber
      * @return
      */
     private boolean innerAddForPufa(OutImportBean bean, String[] obj, StringBuilder builder, int currentNumber)
     {
         boolean importError = false;
         ProductBean pbean = null;

         // 分行名称 （相当于客户）
//         if ( !StringTools.isNullOrNone(obj[0]))
//         {
//             bean.setBranchName(obj[0]);
//         }

         // 二级分行名称
         if ( !StringTools.isNullOrNone(obj[1]))
         {
             bean.setSecondBranch(obj[1]);
         }

         // 联行网点号
         if ( !StringTools.isNullOrNone(obj[2]))
         {
             bean.setComunicationBranch(obj[2]);
             bean.setLhwd(obj[2]);
         }

         // 订单类型
         if ( !StringTools.isNullOrNone(obj[29]))
         {
             String outType = obj[29];

             String [] outTypes = OutImportConstant.outTypesArr;

             for(int i = 0; i< outTypes.length; i++)
             {
                 if (outType.equals(outTypes[i]))
                 {
                     bean.setOutType(OutImportConstant.outTypeiArr[i]);

                     break;
                 }
             }

             if (bean.getOutType() == -1)
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("订单类型不存在")
                 .append("<br>");

                 importError = true;
             }
         }else
         {
             builder
             .append("第[" + currentNumber + "]错误:")
             .append("订单类型不能为空")
             .append("<br>");

             importError = true;
         }

         // 网点名称 - 永银的客户,不做强制要求事先须存在，没有不能进行
         if ( !StringTools.isNullOrNone(obj[3]))
         {
             String custName = obj[3].trim();

//             CustomerBean cBean = customerMainDAO.findByUnique(custName);
             List<CustomerBean> cbeans = customerMainDAO.queryByName(custName);
             if (ListTools.isEmptyOrNull(cbeans))
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("网点名称（客户）" + custName + "不存在")
                 .append("<br>");

                 importError = true;
             }else{
                 //#731
                 CustomerBean customerBean = cbeans.get(0);
                 if (customerBean.getOstatus() !=0){
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("客户已停用：" + custName )
                             .append("<br>");

                     importError = true;
                 }

                 if (bean.getOutType() != OutConstant.OUTTYPE_OUT_SWATCH)
                 {
                     StafferVSCustomerBean vsBean = stafferVSCustomerDAO.findByUnique(cbeans.get(0).getId());

                     if (null == vsBean)
                     {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("网点名称（客户）" + custName + "没有与业务员挂靠关系")
                         .append("<br>");

                         importError = true;
                     }else{
                         StafferBean stafferBean = this.stafferDAO.find(vsBean.getStafferId());
                         if (stafferBean!= null &&
                                 ("停用".equals(stafferBean.getName()) || "重复".equals(stafferBean.getName()))){
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("停用或重复名下的客户不能导入" + custName)
                                     .append("<br>");

                             importError = true;
                         } else {
                             bean.setComunicatonBranchName(custName);

                             String branchName = this.customerMainDAO.queryBranchName(custName);
                             if(StringTools.isNullOrNone(branchName)){
                                 builder
                                         .append("第[" + currentNumber + "]错误:")
                                         .append("t_center_customer表中reserver1字段(分行)不能为空：" + custName)
                                         .append("<br>");

                                 importError = true;
                             } else{
                                 bean.setBranchName(branchName);
                             }
                         }
                     }
                 }else{
                     bean.setComunicatonBranchName("公共客户");
                 }
             }
         }
         else
         {
             builder
             .append("第[" + currentNumber + "]错误:")
             .append("网点名称不能为空")
             .append("<br>");

             importError = true;
         }

         // 2015/11/16 中原银行导入，需设置商品编码
         if ( !StringTools.isNullOrNone(obj[4]))
         {
             String code = obj[4].trim();

             bean.setProductCode(code);
         }

         // 商品名称
         if ( !StringTools.isNullOrNone(obj[5]))
         {
             String name = obj[5].trim();

             // 姓氏
             bean.setFirstName("N/A");

             pbean = productDAO.findByName(name);

             if (null == pbean)
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("产品["+name+"]的产品不存在,请创建")
                 .append("<br>");

                 importError = true;
             }else{
                 bean.setProductId(pbean.getId());

                 bean.setProductName(pbean.getName());
             }
         }
         else
         {
             builder
             .append("第[" + currentNumber + "]错误:")
             .append("产品不可为空")
             .append("<br>");

             importError = true;
         }

         // 数量
         if ( !StringTools.isNullOrNone(obj[6]))
         {
             int amount = MathTools.parseInt(obj[6]);

             if (amount <= 0){
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("数量须大于0")
                 .append("<br>");

                 importError = true;
             }else{
                 bean.setAmount(amount);
             }
         }
         else
         {
             builder
             .append("第[" + currentNumber + "]错误:")
             .append("数量不可为空")
             .append("<br>");

             importError = true;
         }

         // 单价
         if ( !StringTools.isNullOrNone(obj[7]))
         {
             double price = MathTools.parseDouble(obj[7].trim());

             bean.setPrice(price);

             if (bean.getOutType() != OutConstant.OUTTYPE_OUT_PRESENT){

                 if (price <= 0)
                 {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("非赠送类型时，单价须大于0")
                     .append("<br>");

                     importError = true;
                 }
             }else if (bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT
                     && bean.getPrice() != 0)
             {
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("赠送类型订单单价须为0")
                         .append("<br>");

                 importError = true;
             }
         }
         else
         {
             builder
             .append("第[" + currentNumber + "]错误:")
             .append("单价不可为空")
             .append("<br>");

             importError = true;
         }

         // 规格
         if ( !StringTools.isNullOrNone(obj[8]))
         {
             bean.setStyle(obj[8]);
         }

         // 金额
         if ( !StringTools.isNullOrNone(obj[9]))
         {
             double value = MathTools.parseDouble(obj[9].trim());

             bean.setValue(value);

             if (value != (bean.getAmount() * bean.getPrice()))
             {
                 bean.setValue(bean.getAmount() * bean.getPrice());
             }
         }
         else
         {
             bean.setValue(bean.getAmount() * bean.getPrice());
         }

         // 中收金额
         if ( !StringTools.isNullOrNone(obj[10]))
         {
             String value = obj[10].trim();

             if (value.equals("无")) {
                 bean.setMidValue(0);
             } else {
                 double midValue = MathTools.parseDouble(value);

                 if (midValue <= 0) {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("中收为0请用'无'代替")
                     .append("<br>");

                     importError = true;
                 } else {
                     bean.setMidValue(midValue);
                 }
             }
         }
         //2015/12/25 所有银行导入订单“中收金额”不用必填
 //		else {
 //			builder
 //            .append("第[" + currentNumber + "]错误:")
 //            .append("中收不能为空，若没有，请用'无'代替")
 //            .append("<br>");
 //
 //			importError = true;
 //		}

         // 计划交付日期
         if ( !StringTools.isNullOrNone(obj[11]))
         {
             bean.setArriveDate(obj[11]);
         }
         // #151: 2015/12/25 所有银行导入订单“计划交付日期”不用必填
 //		else
 //		{
 //			builder
 //            .append("第[" + currentNumber + "]错误:")
 //            .append("计划交付日期不能为空")
 //            .append("<br>");
 //
 //			importError = true;
 //		}

         // 库存类型
         if ( !StringTools.isNullOrNone(obj[12]))
         {
             int storageType = OutImportHelper.getStorageType(obj[12]);

             bean.setStorageType(storageType);
         }

         // 中信订单号
         if ( !StringTools.isNullOrNone(obj[13]))
         {
             bean.setCiticNo(obj[13]);
         }
         else
         {
             builder
             .append("第[" + currentNumber + "]错误:")
             .append("浦发银行订单号不能为空")
             .append("<br>");

             importError = true;
         }

         // 开票性质
         if ( !StringTools.isNullOrNone(obj[14]))
         {
             int invoiceNature = OutImportHelper.getInvoiceNature(obj[14]);
             if (invoiceNature == -1){
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("开票性质只能为:"+OutImportHelper.getInvoiceNature())
                         .append("<br>");

                 importError = true;
             } else{
                 bean.setInvoiceNature(invoiceNature);
             }
         }

         // 开票抬头
         if ( !StringTools.isNullOrNone(obj[15]))
         {
             bean.setInvoiceHead(obj[15]);
         }

         // 开票要求
         if ( !StringTools.isNullOrNone(obj[16]))
         {
             bean.setInvoiceCondition(obj[16]);
         }

         // 绑定单号
         if ( !StringTools.isNullOrNone(obj[17]))
         {
             bean.setBindNo(obj[17]);
         }

         // 开票类型
         if ( !StringTools.isNullOrNone(obj[18]))
         {
             int invoiceType = OutImportHelper.getInvoiceType(obj[18]);

             bean.setInvoiceType(invoiceType);
         }

         // 开票品名
         if ( !StringTools.isNullOrNone(obj[19]))
         {
             bean.setInvoiceName(obj[19]);
         }

         // 开票金额
         if ( !StringTools.isNullOrNone(obj[20]))
         {
             double invoiceMoney = MathTools.parseDouble(obj[20]);

             bean.setInvoiceMoney(invoiceMoney);
         }

         // 中信订单日期
         if ( !StringTools.isNullOrNone(obj[27]))
         {
             String date = obj[27].trim();

             String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
             Pattern p = Pattern.compile(eL);
             Matcher m = p.matcher(date);
             boolean dateFlag = m.matches();
             if (!dateFlag) {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("中信订单日期格式错误，如 2000-01-01")
                 .append("<br>");

                 importError = true;
             }else{
                 bean.setCiticOrderDate(date);
             }
         }else{
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("中信订单日期不能为空")
                     .append("<br>");

             importError = true;
         }

         // 仓库
         if ( !StringTools.isNullOrNone(obj[30]))
         {
             String depotName = obj[30];

             DepotBean depot = depotDAO.findByUnique(depotName);

             if (null == depot)
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("仓库" + depotName + "不存在")
                 .append("<br>");

                 importError = true;
             }else
             {
                 bean.setDepotId(depot.getId());
             }
         }else
         {
             builder
             .append("第[" + currentNumber + "]错误:")
             .append("仓库不能为空")
             .append("<br>");

             importError = true;
         }

         // 仓区
         if ( !StringTools.isNullOrNone(obj[31]))
         {
             String depotpartName = obj[31];

             DepotpartBean depotpart = depotpartDAO.findByUnique(depotpartName);

             if (null == depotpart)
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("仓区[" + depotpartName + "]不存在")
                 .append("<br>");

                 importError = true;
             }else{
                 if (!depotpart.getLocationId().equals(bean.getDepotId()))
                 {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("仓区[" +depotpartName + "]对应的仓库不是导入中的仓库")
                     .append("<br>");

                     importError = true;
                 }else{
                     bean.setDepotpartId(depotpart.getId());
                     bean.setComunicationBranch(depotpart.getName());
                 }
             }
         }else
         {
             builder
             .append("第[" + currentNumber + "]错误:")
             .append("仓区不能为空")
             .append("<br>");

             importError = true;
         }

         // 职员 - 领样类型时 必须
         if ( !StringTools.isNullOrNone(obj[32]))
         {
             String name = obj[32].trim();

             StafferBean staffer = stafferDAO.findByUnique(name);

             if (null == staffer)
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("职员["+name+"]不存在")
                 .append("<br>");

                 importError = true;
             }else{
                 bean.setStafferId(staffer.getId());
             }
         }else
         {
             if (bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("领样类型库单时职员不能为空")
                 .append("<br>");

                 importError = true;
             }
         }

         // 备注
         if ( !StringTools.isNullOrNone(obj[33]))
         {
             bean.setDescription(obj[33]);
         }

         // 发货方式
         if ( !StringTools.isNullOrNone(obj[34]))
         {
             boolean has = false;

             String shipping = obj[34].trim();

             for (int i = 0 ; i < OutImportConstant.shipping.length; i++)
             {
                 if (OutImportConstant.shipping[i].equals(shipping))
                 {
                     has = true;

                     bean.setShipping(OutImportConstant.ishipping[i]);

                     break;
                 }
             }

             if (!has)
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("发货方式不对,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,代收货款,空发]中之一")
                 .append("<br>");

                 importError = true;
             }

             //仓库为“可发库”时，发货方式控制不可选择“空发
             if(DepotConstant.CENTER_DEPOT_ID.equals(bean.getDepotId())
                     && bean.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING){
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("仓库为“可发库”时，发货方式控制不可选择'空发'")
                         .append("<br>");

                 importError = true;
             }
             //仓库为“空退空开库（仅限商务部操作）”时，发货方式控制只能选择“空发”
             else if(DepotConstant.KTKK_SW_DEPOT_ID.equals(bean.getDepotId())
                     && bean.getShipping() != OutConstant.OUT_SHIPPING_NOTSHIPPING){
                 builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("仓库为“空退空开库（仅限商务部操作）”时，发货方式控制只能选择“空发”'")
                         .append("<br>");

                 importError = true;
             }
         }else
         {
             builder
             .append("第[" + currentNumber + "]错误:")
             .append("发货方式不能为空,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,代收货款,空发]中之一")
             .append("<br>");

             importError = true;
         }

         if (bean.getShipping() == OutConstant.OUT_SHIPPING_3PL || bean.getShipping() == OutConstant.OUT_SHIPPING_3PLANDDTRANSPORT
                 || bean.getShipping() == OutConstant.OUT_SHIPPING_PROXY)
         {
             // 如果发货方式是快递或快递+货运 ,则快递须为必填
             if ( !StringTools.isNullOrNone(obj[35]))
             {
                 String transport1 = obj[35].trim();

                 ExpressBean express = expressDAO.findByUnique(transport1);

                 if (null == express)
                 {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("快递方式"+ transport1 +"不存在")
                     .append("<br>");

                     importError = true;
                 }else{
                     bean.setTransport1(MathTools.parseInt(express.getId()));
                 }
             }else
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("快递方式不能为空")
                 .append("<br>");

                 importError = true;
             }

             // 快递支付方式也不能为空
             if ( !StringTools.isNullOrNone(obj[36]))
             {
                 String expressPay = obj[36].trim();

                 boolean isexists = false;

                 for (int i = 0; i < OutImportConstant.expressPay.length; i++)
                 {
                     if (expressPay.equals(OutImportConstant.expressPay[i]))
                     {
                         isexists = true;

                         bean.setExpressPay(OutImportConstant.iexpressPay[i]);

                         break;
                     }
                 }

                 if (!isexists)
                 {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("快递支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
                     .append("<br>");

                     importError = true;
                 }
             }else
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("快递支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
                 .append("<br>");

                 importError = true;
             }
         }

         if (bean.getShipping() == 3 || bean.getShipping() == 4)
         {
             // 如果发货方式是快递或快递+货运 ,则快递须为必填
             if ( !StringTools.isNullOrNone(obj[37]))
             {
                 String transport1 = obj[37].trim();

                 ExpressBean express = expressDAO.findByUnique(transport1);

                 if (null == express)
                 {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("货运方式"+ transport1 +"不存在")
                     .append("<br>");

                     importError = true;
                 }else{
                     bean.setTransport2(MathTools.parseInt(express.getId()));
                 }
             }else
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("货运方式不能为空")
                 .append("<br>");

                 importError = true;
             }

             // 快递支付方式也不能为空
             if ( !StringTools.isNullOrNone(obj[38]))
             {
                 String expressPay = obj[38].trim();

                 boolean isexists = false;

                 for (int i = 0; i < OutImportConstant.expressPay.length; i++)
                 {
                     if (expressPay.equals(OutImportConstant.expressPay[i]))
                     {
                         isexists = true;

                         bean.setTransportPay(OutImportConstant.iexpressPay[i]);

                         break;
                     }
                 }

                 if (!isexists)
                 {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("货运支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
                     .append("<br>");

                     importError = true;
                 }
             }else
             {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("货运支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
                 .append("<br>");

                 importError = true;
             }
         }

         // 省
         if ( !StringTools.isNullOrNone(obj[21]))
         {
             String name = obj[21].trim();

             ProvinceBean province = provinceDAO.findByUnique(name);

             if (null != province)
                 bean.setProvinceId(province.getId());
             else {
                 if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("快递、货运时省名系统不存在")
                     .append("<br>");

                     importError = true;
                 }
             }
         } else {
             if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("快递、货运时省不能为空")
                 .append("<br>");

                 importError = true;
             }
         }

         // 市
         if ( !StringTools.isNullOrNone(obj[22]))
         {
             String name = obj[22].trim();

             CityBean city = cityDAO.findByUnique(name);

             if (null != city)
                 bean.setCityId(city.getId());
             else {
                 if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("快递、货运时市不存在")
                     .append("<br>");

                     importError = true;
                 }
             }
         } else {
             if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("快递、货运时市不能为空")
                 .append("<br>");

                 importError = true;
             }
         }

         // 详细地址
         if ( !StringTools.isNullOrNone(obj[23]))
         {
             bean.setAddress(obj[23].trim());

             if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                 if (bean.getAddress().length() < 5) {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("快递、货运时详细地址不能少于5个字")
                     .append("<br>");

                     importError = true;
                 }
             }

         } else {
             if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("快递、货运时详细地址不能为空")
                 .append("<br>");

                 importError = true;
             }
         }

         // 收货人
         if ( !StringTools.isNullOrNone(obj[24]))
         {
             bean.setReceiver(obj[24].trim());

             if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                 if (bean.getReceiver().length() < 2) {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("快递、货运时收货人不能少于2个字")
                     .append("<br>");

                     importError = true;
                 }
             }
         } else {
             if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("快递、货运时收货人不能为空")
                 .append("<br>");

                 importError = true;
             }
         }

         // 收货人手机
         if ( !StringTools.isNullOrNone(obj[25]))
         {
             bean.setHandPhone(obj[25].trim());

             if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
                 if (bean.getHandPhone().length() != 11) {
                     builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("收货人手机号码不是11位")
                     .append("<br>");

                     importError = true;
                 }
             }
         }
//         else {
//             if (bean.getShipping() == 2 || bean.getShipping() == 3 || bean.getShipping() == 4) {
//                 builder
//                 .append("第[" + currentNumber + "]错误:")
//                 .append("快递、货运时收货人手机不能为空")
//                 .append("<br>");
//
//                 importError = true;
//             }
//         }

         // 回款天数
         if ( !StringTools.isNullOrNone(obj[39]))
         {
             bean.setReday(MathTools.parseInt(obj[39].trim()));
         }else{
             bean.setReday(OutImportConstant.CITIC_REDAY);
         }

         if (bean.getOutType() == OutConstant.OUTTYPE_OUT_PRESENT
                 || bean.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH) {
             if ( !StringTools.isNullOrNone(obj[40]))
             {
                 String presentFlag = obj[40].trim();
                 ConditionParse conditionParse = new ConditionParse();
                 conditionParse.addWhereStr();
                 conditionParse.addCondition("name","=",presentFlag);
                 List<PresentFlagBean> presentFlagBeans = this.presentFlagDAO.queryEntityBeansByCondition(conditionParse);
                 if (ListTools.isEmptyOrNull(presentFlagBeans)){
                     builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("二级类型不存在")
                             .append("<br>");

                     importError = true;
                 } else{
                     bean.setPresentFlag(Integer.valueOf(presentFlagBeans.get(0).getType()));
                 }
//                 String [] presentFlags = OutImportConstant.outPresentTypesArr;
//
//                 for(int i = 0; i< presentFlags.length; i++)
//                 {
//                     if (presentFlag.equals(presentFlags[i]))
//                     {
//                         bean.setPresentFlag(OutImportConstant.outPresentTypeiArr[i]);
//
//                         break;
//                     }
//                 }
//
//                 if (bean.getPresentFlag() == 0)
//                 {
//                     builder
//                     .append("第[" + currentNumber + "]错误:")
//                     .append("二级类型不存在")
//                     .append("<br>");
//
//                     importError = true;
//                 }
             }else{
                 builder
                 .append("第[" + currentNumber + "]错误:")
                 .append("赠送或个人领样单据时二级类型不能为空")
                 .append("<br>");

                 importError = true;
             }
         }
//
//         // 2015/05/04 中收金额
//         if ( !StringTools.isNullOrNone(obj[41]))
//         {
//             bean.setIbMoney(MathTools.parseDouble(obj[41].trim()));
//         }
//
//         // 激励金额
//         if ( !StringTools.isNullOrNone(obj[42]))
//         {
//             bean.setMotivationMoney(MathTools.parseDouble(obj[42].trim()));
//         }
//
//         // 2015/09/29 客户姓名
//         if ( !StringTools.isNullOrNone(obj[43]))
//         {
//             bean.setCustomerName(obj[43].trim());
//         }


         //#65 中收激励从Product import表读取
         //#72 普通导入暂时不自动获取
//         ConditionParse conditionParse = new ConditionParse();
//         conditionParse.addCondition("bankProductCode", "=", bean.getProductCode());
//         List<ProductImportBean> productImportBeans = this.productImportDAO.queryEntityBeansByCondition(conditionParse);
//         if (!ListTools.isEmptyOrNull(productImportBeans)){
//             ProductImportBean productImportBean = productImportBeans.get(0);
//             bean.setIbMoney(productImportBean.getIbMoney());
//             bean.setMotivationMoney(productImportBean.getMotivationMoney());
//         } else{
//             String msg = "该产品未配置中收激励金额:"+bean.getProductName();
//             _logger.error(msg);
//             builder
//                     .append("第[" + currentNumber + "]错误:")
//                     .append("该产品未配置中收激励金额:"+bean.getProductName())
//                     .append("<br>");
//
//             importError = true;
//         }

         // 2015/09/29 客户姓名
         if ( !StringTools.isNullOrNone(obj[41]))
         {
             bean.setCustomerName(obj[41].trim());
         }

         if (this.isOnlineCustomer(bean.getComunicatonBranchName())){
             bean.setPlatformFee(bean.getPrice()*0.01);
         }

         // #426 2017/2/28 固定电话
         if ( !StringTools.isNullOrNone(obj[42]))
         {
             bean.setTelephone(obj[42].trim());
//             if (bean.getTelephone().length() != 12) {
//                 builder
//                         .append("第[" + currentNumber + "]错误:")
//                         .append("固定电话号码不是12位")
//                         .append("<br>");
//
//                 importError = true;
//             }
         }

         // #461 手机和电话不能都为空
         if (StringTools.isNullOrNone(bean.getHandPhone()) && StringTools.isNullOrNone(bean.getTelephone())){
             builder
                     .append("第[" + currentNumber + "]错误:")
                     .append("收货人手机或固定电话不能都为空")
                     .append("<br>");

             importError = true;
         }

         // #62 2017/6/13 是否直邮,状态默认为0 输入值为N时为0，Y时为1
         if ( !StringTools.isNullOrNone(obj[43]))
         {
             String direct = obj[43].trim();
             if ("Y".equalsIgnoreCase(direct)){
                 bean.setDirect(1);
             } else if ("N".equalsIgnoreCase(direct)){
                 bean.setDirect(0);
             } else {
                 builder.append("第[" + currentNumber + "]错误:")
                         .append("是否直邮只能为Y或N")
                         .append("<br>");

                 importError = true;
             }
         }


         if ( !StringTools.isNullOrNone(obj[44]))
         {
             String channel = obj[44].trim();

             if (channel.length()>4){
                 builder.append("第[" + currentNumber + "]错误:")
                         .append("渠道不超过4个字符")
                         .append("<br>");

                 importError = true;
             } else{
                 final String type = "311";
                 EnumBean enumBean = new EnumBean();
                 enumBean.setType(type);
                 enumBean.setValue(channel);
                 List<EnumBean> enumBeans = this.enumDAO.findByType(type);
                 if (!ListTools.isEmptyOrNull(enumBeans) && enumBeans.contains(enumBean)){
                     bean.setChannel(channel);
                 } else{
                     builder.append("第[" + currentNumber + "]错误:")
                             .append("渠道只能是"+EnumBean.join(enumBeans))
                             .append("<br>");

                     importError = true;
                 }
             }
         }

         //交货方式
         if ( !StringTools.isNullOrNone(obj[45]))
         {
             String delivery = obj[45].trim();
             if ("现货".equalsIgnoreCase(delivery)){
                 bean.setDelivery(delivery);
             } else if ("预售".equalsIgnoreCase(delivery)){
                 bean.setDelivery(delivery);
             } else {
                 builder.append("第[" + currentNumber + "]错误:")
                         .append("交货方式只能为现货/预售")
                         .append("<br>");

                 importError = true;
             }
         }

         //pos付款方
         if ( !StringTools.isNullOrNone(obj[46]))
         {
             String posPayer = obj[46].trim();
             bean.setPosPayer(posPayer);
         }

         //推荐标示
         if ( !StringTools.isNullOrNone(obj[47]))
         {
             String recommendation = obj[47].trim();
             bean.setRecommendation(recommendation);
         }

         //#629 虚拟订单
         if ( !StringTools.isNullOrNone(obj[48]))
         {
             String virtualStatus = obj[48].trim();
             if ("是".equals(virtualStatus)){
                 bean.setVirtualStatus(1);
             } else if("否".equals(virtualStatus)){
                 bean.setVirtualStatus(0);
             } else{
                 builder.append("第[" + currentNumber + "]错误:")
                         .append("虚拟订单只能为是/否")
                         .append("<br>");

                 importError = true;
             }
         }

         //#669 订单导入控制结算价不能为0
         double iprice = this.outManager.getIprice(bean, pbean);
         if (iprice == 0)
         {
             builder.append("第[" + currentNumber + "]错误:")
                     .append("业务员结算价不能为0")
                     .append("<br>");

             importError = true;
         }
         return importError;
     }

     /**
      *
      * @param obj
      * @return
      */
     private String[] fillObj(String[] obj)
     {
         String[] result = new String[50];

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
      * 批量更新配送地址
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchUpdateDistAddr(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         _logger.info("***batchUpdateDistAddr***");
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<DistributionBean> importItemList = new ArrayList<DistributionBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("uploadDistAddress");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("uploadDistAddress");
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
                     DistributionBean bean = new DistributionBean();
                     OutBean out = null;
                     // 单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String outId = obj[0].trim();

                         out = outDAO.find(outId);

                         if (null == out){
                             /*builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("销售单不存在")
                             .append("<br>");

                             importError = true;*/
                         }else{
                             if (out.getType() != 0){
                                 builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("单号不是销售单")
                                 .append("<br>");

                                 importError = true;
                             }else{
                                 if (out.getStatus() == 3 || out.getStatus() == 4){
                                     builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("销售单已是发货态不能批量更新")
                                     .append("<br>");

                                     importError = true;
                                 }else{
                                     bean.setOutId(outId);
                                 }
                             }
                         }


                     }else{
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("销售单号不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 省
                     if ( !StringTools.isNullOrNone(obj[1]))
                     {
                         String name = obj[1].trim();

                         ProvinceBean province = provinceDAO.findByUnique(name);

                         if (null == province){
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("省不存在")
                             .append("<br>");

                             importError = true;
                         }else{
                             bean.setProvinceId(province.getId());
                         }
                     }else{
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("省不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 市
                     if ( !StringTools.isNullOrNone(obj[2]))
                     {
                         String name = obj[2].trim();

                         List<CityBean> cityList = cityDAO.queryEntityBeansByCondition("where name=? and parentid=?", name, bean.getProvinceId());

                         if (ListTools.isEmptyOrNull(cityList)){
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("市不存在")
                             .append("<br>");

                             importError = true;
                         }else{
                             bean.setCityId(cityList.get(0).getId());
                         }

                     }else{
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("市不能为空")
                         .append("<br>");

                         importError = true;
                     }


                     // 详细地址
                     if ( !StringTools.isNullOrNone(obj[3]))
                     {
                         bean.setAddress(obj[3].trim());
                     }else{
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("详细地址不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 收货人
                     if ( !StringTools.isNullOrNone(obj[4]))
                     {
                         bean.setReceiver(obj[4].trim());
                     }else{
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("收货人不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 收货人电话
                     if ( !StringTools.isNullOrNone(obj[5]))
                     {
                         bean.setMobile(obj[5].trim());
                     }else{
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("收货人电话不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 收货人固定电话
                     if ( !StringTools.isNullOrNone(obj[6]))
                     {
                         bean.setTelephone(obj[6].trim());
                         if (bean.getTelephone().length()!=12){
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("固定电话长度不是12位")
                                     .append("<br>");

                             importError = true;
                         }
                     }

                     // 发货方式
                     if ( !StringTools.isNullOrNone(obj[7]))
                     {
                         boolean has = false;

                         String shipping = obj[7].trim();

                         for (int i = 0 ; i < OutImportConstant.shipping.length; i++)
                         {
                             if (OutImportConstant.shipping[i].equals(shipping))
                             {
                                 has = true;

                                 bean.setShipping(OutImportConstant.ishipping[i]);

                                 break;
                             }
                         }

                         if (!has)
                         {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("发货方式不对,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,代收货款,空发]中之一")
                             .append("<br>");

                             importError = true;
                         }

                         //仓库为“可发库”时，发货方式控制不可选择“空发
                         if(out!= null && DepotConstant.CENTER_DEPOT_ID.equals(out.getLocation())
                                 && bean.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING){
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("仓库为“可发库”时，发货方式控制不可选择'空发'")
                                     .append("<br>");

                             importError = true;
                         }
                         //仓库为“空退空开库（仅限商务部操作）”时，发货方式控制只能选择“空发”
                         else if(out!= null && DepotConstant.KTKK_SW_DEPOT_ID.equals(out.getLocation())
                                 && bean.getShipping() != OutConstant.OUT_SHIPPING_NOTSHIPPING){
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("仓库为“空退空开库（仅限商务部操作）”时，发货方式控制只能选择“空发”'")
                                     .append("<br>");

                             importError = true;
                         }
                     }else
                     {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("发货方式不能为空,须为[自提,公司第三方快递,第三方货运,第三方快递+货运,代收货款,空发]中之一")
                         .append("<br>");

                         importError = true;
                     }

                     // 快递公司
                     String transportName = obj[8].trim();
                     if ( !StringTools.isNullOrNone(transportName))
                     {
                         ExpressBean express = expressDAO.findByUnique(transportName);

                         if (null == express)
                         {
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("快递方式"+ transportName +"不存在")
                                     .append("<br>");

                             importError = true;
                         }else{
                             int expressId = MathTools.parseInt(express.getId());
 //							if (bean.getShipping() == 2){
 //								bean.setTransport1();
 //							}

                             if (bean.getShipping() == OutConstant.OUT_SHIPPING_3PL
                                     || bean.getShipping() == OutConstant.OUT_SHIPPING_PROXY){
                                 bean.setTransport1(expressId);
                             } else if (bean.getShipping() == OutConstant.OUT_SHIPPING_TRANSPORT){
                                 bean.setTransport2(expressId);
                             } else if (bean.getShipping() == OutConstant.OUT_SHIPPING_3PLANDDTRANSPORT){
                                 bean.setTransport1(expressId);
                                 bean.setTransport2(expressId);
                             }
                         }
                     }

                     //#290 支付方式
                     if ( !StringTools.isNullOrNone(obj[9]))
                     {
                         String expressPay = obj[9].trim();

                         boolean isexists = false;

                         for (int i = 0; i < OutImportConstant.expressPay.length; i++)
                         {
                             if (expressPay.equals(OutImportConstant.expressPay[i]))
                             {
                                 isexists = true;

                                 int pay = OutImportConstant.iexpressPay[i];

                                 if (bean.getShipping() == OutConstant.OUT_SHIPPING_3PL
                                         || bean.getShipping() == OutConstant.OUT_SHIPPING_PROXY){
                                     bean.setExpressPay(pay);
                                 } else if (bean.getShipping() == OutConstant.OUT_SHIPPING_TRANSPORT){
                                     bean.setTransportPay(pay);
                                 } else if (bean.getShipping() == OutConstant.OUT_SHIPPING_3PLANDDTRANSPORT){
                                     bean.setExpressPay(pay);
                                     bean.setTransportPay(pay);
                                 }

                                 break;
                             }
                         }

                         if (!isexists)
                         {
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
                                     .append("<br>");

                             importError = true;
                         }
                     }else
                     {
                         builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
                                 .append("<br>");

                         importError = true;
                     }


                     // 销售单备注
                     if ( !StringTools.isNullOrNone(obj[10]))
                     {
                         bean.setDescription(obj[10].trim());
                     }

                     importItemList.add(bean);

                 }
                 else
                 {
                     builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("数据长度不足32格错误")
                         .append("<br>");

                     importError = true;
                 }
             }
         }catch (Exception e)
         {
             _logger.error(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

             return mapping.findForward("uploadDistAddress");
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

             return mapping.findForward("uploadDistAddress");
         }

         try
         {
             outImportManager.batchUpdateDistAddr(importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量更新出错:"+ e.getErrorContent());
         }

         return mapping.findForward("uploadDistAddress");
     }

     /**
      * 批量更新账期
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchUpdateRedate(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<OutBean> importItemList = new ArrayList<OutBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importRedate");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importRedate");
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
                     OutBean bean = new OutBean();

                     // 单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String outId = obj[0].trim();

                         OutBean out = outDAO.find(outId);

                         if (null == out){
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("销售单不存在")
                             .append("<br>");

                             importError = true;
                         }else{
                             if (out.getType() != 0){
                                 builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("单号不是销售单")
                                 .append("<br>");

                                 importError = true;
                             }else{
 //            					if (out.getStatus() == 3 || out.getStatus() == 4){
 //                        			builder
 //                                    .append("第[" + currentNumber + "]错误:")
 //                                    .append("销售单已是发货态不能批量更新")
 //                                    .append("<br>");
 //
 //                        			importError = true;
 //            					}else{
                                     bean.setFullId(outId);
 //            					}
                             }
                         }


                     }else{
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("销售单号不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 延期天数
                     if ( !StringTools.isNullOrNone(obj[1]))
                     {
                         int reday = MathTools.parseInt(obj[1].trim());

                         if (reday <= 0)
                         {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("延期天数须为数字")
                             .append("<br>");

                             importError = true;
                         }

                         bean.setReday(reday);
                     }else{
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("延期天数不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     importItemList.add(bean);
                 }
                 else
                 {
                     builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("数据长度不足32格错误")
                         .append("<br>");

                     importError = true;
                 }
             }
         }catch (Exception e)
         {
             _logger.error(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

             return mapping.findForward("importRedate");
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

             return mapping.findForward("importRedate");
         }

         try
         {
             outImportManager.batchUpdateRedate(importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量更新出错:"+ e.getErrorContent());
         }

         return mapping.findForward("importRedate");
     }

     /**
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward processOutImport(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         AjaxResult ajaxResult = new AjaxResult();

         String batchId = request.getParameter("batchId");

         List<OutImportBean> list = outImportDAO.queryEntityBeansByFK(batchId);

         ajaxResult.setSuccess();

         if (!ListTools.isEmptyOrNull(list))
         {
             try
             {
                 outImportManager.process(list);

                 ajaxResult.setSuccess("处理成功");
             }
             catch (MYException e)
             {
                 _logger.warn(e, e);

                 ajaxResult.setError("处理失败:" + e.getErrorContent());
             }
         }

         return JSONTools.writeResponse(response, ajaxResult);
     }

     /**
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward queryOutImportResult(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         return null;
     }

     /**
      * 日志查询
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward queryOutImportLog(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         ConditionParse condtion = new ConditionParse();

         condtion.addWhereStr();

         ActionTools.processJSONQueryCondition(QUERYOUTIMPORTLOG, request, condtion);

         String batchId = request.getParameter("batchId");

         if (!StringTools.isNullOrNone(batchId))
         {
             condtion.addCondition("OutImportLogBean.batchId", "=", batchId);
         }

         condtion.addCondition(" order by OutImportLogBean.logTime desc");

         String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYOUTIMPORTLOG, request, condtion,
             this.outImportLogDAO);

         return JSONTools.writeResponse(response, jsonstr);
     }

     /**
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward queryOutImport(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         User user = Helper.getUser(request);

         ConditionParse condtion = new ConditionParse();

         condtion.addWhereStr();

         String sbatchId = request.getParameter("sbatchId");

         if (!StringTools.isNullOrNone(sbatchId))
         {
             condtion.addCondition("OutImportBean.batchId", "=", sbatchId);
         }

         setCondition(condtion, user);

         Map<String, String> initMap = initLogTime1(request, condtion);

         ActionTools.processJSONDataQueryCondition(QUERYOUTIMPORT, request,
                 condtion, initMap);

         condtion.addCondition(" order by OutImportBean.logTime desc");

         String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYOUTIMPORT, request, condtion,
             this.outImportDAO);

         return JSONTools.writeResponse(response, jsonstr);
     }

     private  void setCondition(ConditionParse condtion, User user)
     {
         CiticBranchBean bean = citicBranchDAO.findByUnique(user.getStafferId());

         if (null != bean)
         {
             List<CiticVSStafferBean> vsList = citicVSStafferDAO.queryEntityBeansByFK(bean.getId());

             StringBuffer sb = new StringBuffer();

             final String TAB = ",";
             final String TAB2 = "'";

             sb.append("(");

             // 指定查询数据范围
             for(int i = 0 ; i < vsList.size(); i++ )
             {
                 sb.append(TAB2);
                 sb.append(vsList.get(i).getCustomerId());
                 sb.append(TAB2);

                 if(i+1 < vsList.size())
                 {
                     sb.append(TAB);
                 }
             }

             sb.append(")");

             condtion.addCondition(" and OutImportBean.customerId in " + sb.toString());
         }
     }

     /**
      * 负库存查询
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward queryReplenishment(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         List<ReplenishmentBean> list = null;

         try
         {
             list = outImportManager.queryReplenishmentBean();
         }
         catch(MYException e)
         {
             _logger.error(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "查询出现异常.");

             mapping.findForward("error");
         }

         request.setAttribute("list", list);

         return mapping.findForward("queryReplenishment");
     }

     /**
      * 导出
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward exportOutImport(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         OutputStream out = null;

         String filenName = "ExportOutImport_" + TimeTools.now("MMddHHmmss") + ".csv";

         response.setContentType("application/x-dbf");

         response.setHeader("Content-Disposition", "attachment; filename="
                 + filenName);

         WriteFile write = null;

         ConditionParse condtion = JSONPageSeparateTools.getCondition(request,
                 QUERYOUTIMPORT);

         int count = outImportDAO.countVOByCondition(condtion.toString());

         if (count > 150000)
         {
             return ActionTools.toError("导出数量大于150000,请重新选择时间段导出", mapping, request);
         }

         try
         {
             out = response.getOutputStream();

             write = WriteFileFactory.getMyTXTWriter();

             write.openFile(out);

             write.writeLine("序号,中信订单号,OA单号,申请人,网点名称,品名,数量,金额,中收金额");

             PageSeparate page = new PageSeparate();

             page.reset2(count, 2000);

             WriteFileBuffer line = new WriteFileBuffer(write);

             while (page.nextPage())
             {
                 List<OutImportVO> voFList = outImportDAO.queryEntityVOsByCondition(
                         condtion, page);

                 for (OutImportVO each : voFList)
                 {
                     line.reset();

                     line.writeColumn(each.getId());

                     line.writeColumn("["+each.getCiticNo()+"]");

                     line.writeColumn(each.getOANo());

                     // 申请人取客户挂靠人
                     if (!StringTools.isNullOrNone(each.getOANo())){
                         OutBean outBean = outDAO.find(each.getOANo());

                         if (null == outBean){
                             line.writeColumn("");
                         }else{
                             line.writeColumn(outBean.getStafferName());
                         }
                     }else{
                         line.writeColumn("");
                     }

                     line.writeColumn(each.getComunicatonBranchName());

                     line.writeColumn(each.getProductName());
                     line.writeColumn(each.getAmount());
                     line.writeColumn(each.getValue());
                     line.writeColumn(each.getMidValue());

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
      * 批量报废单
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchDropImport(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException{
    	 _logger.debug("batchDropImport...");
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<BatchDropBean> importItemList = new ArrayList<BatchDropBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("batchDropImport");
         }

         if ( !rds.haveStream())
         {
        	 _logger.error("has no stream...");
        	 
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("batchDropImport");
         }

         //constants forceBuyTypes
         List<String> forceBuyTypes = new ArrayList<String>();
         for(int i=50; i<56; i++){
             forceBuyTypes.add(DefinedCommon.getValue("forceBuyTypes", i));
         }
         
         _logger.debug("batchDropImport..."+forceBuyTypes.size());

         ReaderFile reader = ReadeFileFactory.getXLSReader();

         try
         {
             reader.readFile(rds.getUniqueInputStream());
             
             _logger.debug("batchDropImport..."+forceBuyTypes.size());

             while (reader.hasNext())
             {
                 String[] obj = fillObj((String[])reader.next());
                 
                 int currentNumber = reader.getCurrentLineNumber();
                 _logger.debug("batchDropImport...currentNumber:"+currentNumber);

                 // 第一行忽略
                 if (currentNumber == 1)
                 {
                	 _logger.debug("第一行忽略");
                     continue;
                 }

                 if (StringTools.isNullOrNone(obj[0]))
                 {
                	 _logger.debug("第一栏为空...");
                     continue;
                 }
                 
                 _logger.debug("batchDropImport obj.length:"+obj.length);

                 if (obj.length >= 17 )
                 {
                     BatchDropBean bean = new BatchDropBean();

                     bean.getOutBean().setType(OutConstant.OUT_TYPE_INBILL);

                     String cellValue = "";
                     //入库类型
                     cellValue = obj[0];
                     if("报废".equals(cellValue)){
                         int outType = OutConstant.OUTTYPE_IN_DROP2;
                         bean.getOutBean().setOutType(outType);
                     }else{
                         builder.append("第[" + currentNumber + "]错误:入库类型应为报废<br>");
                         importError = true;
                     }

                     //入库日期
                     cellValue = obj[1];
                     if(!StringTools.isNullOrNone(cellValue)){
                         if(!this.checkDateFormat(cellValue)){
                             builder.append("第[" + currentNumber + "]错误:入库日期格式应为yyyy-MM-dd<br>");
                             importError = true;
                         }else{
                             bean.getOutBean().setOutTime(cellValue);
                         }

                     }else{
                         builder.append("第[" + currentNumber + "]错误:入库日期必填<br>");
                         importError = true;
                     }

                     //入库事由

                     cellValue = obj[2];
                     if(forceBuyTypes.contains(cellValue)){
                         bean.getOutBean().setForceBuyType(50 + forceBuyTypes.indexOf(cellValue));
                     }else{
                         builder.append("第[" + currentNumber + "]错误:入库事由填写错误<br>");
                         importError = true;
                     }

                     //报废类型
                     cellValue = obj[3];
                     int dropType = 10;
                     if(!StringTools.isNullOrNone(cellValue)){
                         if("盘亏".equals(cellValue)) {
                             bean.getOutBean().setDropType(OutConstant.DROPTYPE_INVENTORY_SHORTAGE);
                         }else if("领样退回报废".equals(cellValue)) {
                             bean.getOutBean().setDropType(OutConstant.DROPTYPE_SAMPLE_SCRAP);
                         }else if("不良品报废".equals(cellValue)) {
                             bean.getOutBean().setDropType(OutConstant.DROPTYPE_DEFECTIVE);
                         }else if("其他".equals(cellValue)) {
                             bean.getOutBean().setDropType(OutConstant.DROPTYPE_OTHERS);
                         }else{
                             builder.append("第[" + currentNumber + "]错误:报废类型错误<br>");
                             importError = true;
                         }
                     }else{
                         builder.append("第[" + currentNumber + "]错误:报废类型必填<br>");
                         importError = true;
                     }

                     //纳税实体
                     cellValue = obj[4];
                     _logger.debug("纳税实体:"+cellValue);
                     ConditionParse conditionParse1 = new ConditionParse();
                     conditionParse1.addWhereStr();
                     conditionParse1.addCondition("name","=",cellValue);

                     List<DutyBean> dutyEntities = dutyDAO.queryEntityBeansByCondition(conditionParse1);
                     if(dutyEntities.size()>0){
                         bean.getOutBean().setDutyId(dutyEntities.get(0).getId());
                     }else{
                         builder.append("第[" + currentNumber + "]错误:纳税实体不存在<br>");
                         importError = true;
                     }

                     //业务员/职员
                     cellValue = obj[5];
                     if(!StringTools.isNullOrNone(cellValue)){
                         UserVO user = userDAO.findUserByName(cellValue);
                         if(user!=null){
                             bean.getOutBean().setStafferId(user.getStafferId());
                             bean.getOutBean().setStafferName(user.getName());
                             
                             bean.getOutBean().setReserve9(user.getStafferId());
                         }else{
                             builder.append("第[" + currentNumber + "]错误:业务员/职员不存在<br>");
                             importError = true;
                         }
                     }else{
                         builder.append("第[" + currentNumber + "]错误:业务员/职员必填<br>");
                         importError = true;
                     }
                     
                     BaseBean baseBean = new BaseBean();

                     //OA品名
                     cellValue = obj[6];
                     if(!StringTools.isNullOrNone(cellValue)){
                         ProductBean productBean = productDAO.findByName(cellValue);
                         if(productBean!=null){
                             baseBean.setProductId(productBean.getId());
                             baseBean.setProductName(productBean.getName());
                         }else{
                             builder.append("第[" + currentNumber + "]错误:OA品名不存在<br>");
                             importError = true;
                         }
                     }else{
                         builder.append("第[" + currentNumber + "]错误:OA品名必填<br>");
                         importError = true;
                     }

                     //单位
                     cellValue = obj[9];
                     String unit = "套";
                     if(!StringTools.isNullOrNone(cellValue)){
                         unit = cellValue;
                     }
                     baseBean.setUnit(unit);

                     //单价
                     cellValue = obj[10];
                     if(!StringTools.isNullOrNone(cellValue) && !"0".equals(cellValue)){
                         try{
                             baseBean.setPrice(Double.parseDouble(cellValue));
                         }catch(Exception ex){
                             builder.append("第[" + currentNumber + "]错误:单价错误<br>");
                             importError = true;
                         }
                     }else{
                         builder.append("第[" + currentNumber + "]错误:单价必填且不为0<br>");
                         importError = true;
                     }

                     //总金额
                     cellValue = obj[11];
                     if(!StringTools.isNullOrNone(cellValue) && !"0".equals(cellValue)){
                         try{
                             baseBean.setValue(Double.parseDouble(cellValue));
                         }catch(Exception ex){
                             builder.append("第[" + currentNumber + "]错误:总金额错误<br>");
                             importError = true;
                         }
                     }else{
                         builder.append("第[" + currentNumber + "]错误:总金额必填且不为0<br>");
                         importError = true;
                     }

                     //源仓库
                     cellValue = obj[12];
                     DepotBean depotBean = null;
                     if(!StringTools.isNullOrNone(cellValue)){
                         ConditionParse conditionParse = new ConditionParse();
                         conditionParse.addWhereStr();
                         conditionParse.addCondition("name", "=", cellValue);
                         List<DepotBean> depotBeans = this.depotDAO.queryEntityBeansByCondition(conditionParse);
                         if(depotBeans.size()>0){
                             depotBean = depotBeans.get(0);
                        	 _logger.debug("depotBean.getId():"+depotBean.getId()+",depotBean.getName():"+depotBean.getName());
                             bean.getOutBean().setLocationId("999");
                             bean.getOutBean().setLocation(depotBean.getId());

                             baseBean.setLocationId(depotBean.getId());
                             baseBean.setDepotName(depotBean.getName());
                         }else{
                             builder.append("第[" + currentNumber + "]错误:源仓库不存在<br>");
                             importError = true;
                         }
                     }else{
                         builder.append("第[" + currentNumber + "]错误:源仓库必填<br>");
                         importError = true;
                     }

                     //仓区
                     DepotpartBean depotpartBean = null;
                     if(depotBean!=null){
                         cellValue = obj[13];
                         if(!StringTools.isNullOrNone(cellValue)){
                             ConditionParse conditionParse = new ConditionParse();
                             conditionParse.addWhereStr();
                             conditionParse.addCondition("name", "=", cellValue);
                             conditionParse.addCondition("locationId", "=", depotBean.getId());
                             List<DepotpartBean> depotpartBeans = depotpartDAO.queryEntityBeansByCondition(conditionParse);
                             if(depotpartBeans.size()>0){
                                 depotpartBean = depotpartBeans.get(0);
                                 _logger.debug("depotpartBean.getId():"+depotpartBean.getId()+",depotpartBean.getName():"+depotpartBean.getName());
                                 baseBean.setDepotpartId(depotpartBean.getId());
                                 baseBean.setDepotpartName(depotpartBean.getName());
                             }else{
                                 builder.append("第[" + currentNumber + "]错误:仓区不存在或与源仓库不对应<br>");
                                 importError = true;
                             }
                         }else{
                             builder.append("第[" + currentNumber + "]错误:仓区必填<br>");
                             importError = true;
                         }
                     }

                     //储位
                     StorageBean storageBean = null;
                     if(depotpartBean!=null){
                         cellValue = obj[14];
                         if(!StringTools.isNullOrNone(cellValue)){
                             ConditionParse conditionParse = new ConditionParse();
                             conditionParse.addWhereStr();
                             conditionParse.addCondition("name", "=", cellValue);
                             conditionParse.addCondition("depotpartId", "=", depotpartBean.getId());
                             _logger.debug("name:"+cellValue+",depotpartBean.getId():"+depotpartBean.getId());
                             List<StorageBean> storageBeans = storageDAO.queryEntityBeansByCondition(conditionParse);
                             if(storageBeans.size()>0){
                                 storageBean = storageBeans.get(0);
                                 baseBean.setStorageId(storageBean.getId());
                             }else{
                                 builder.append("第[" + currentNumber + "]错误:储位不存在或与仓区不对应<br>");
                                 importError = true;
                             }
                         }else{
                             builder.append("第[" + currentNumber + "]错误:储位必填<br>");
                             importError = true;
                         }
                     }

                     //虚料金额
                     cellValue = obj[15];
                     if(StringTools.isNullOrNone(cellValue)){
                         cellValue = "0";
                     }

                     try{
                         baseBean.setVirtualPrice(Double.parseDouble(cellValue));
                     }catch(Exception ex){
                         builder.append("第[" + currentNumber + "]错误:虚料金额错误<br>");
                         importError = true;
                     }


                     //入库单备注
                     cellValue = obj[16];
                     baseBean.setDescription(cellValue);

                     if(storageBean!=null){

                         String locationId = baseBean.getLocationId();
                         String depotpartId = baseBean.getDepotpartId();
                         String storageId = baseBean.getStorageId();
                         String productId = baseBean.getProductId();

                         ConditionParse conditionParse = new ConditionParse();
                         conditionParse.addWhereStr();
                         conditionParse.addCondition("locationId", "=", locationId);
                         conditionParse.addCondition("depotpartId", "=", depotpartId);
                         conditionParse.addCondition("storageId", "=", storageId);
                         conditionParse.addCondition("productId", "=", productId);

                         List<StorageRelationBean> storageRelations = storageRelationDAO.queryEntityBeansByCondition(conditionParse);
                         StorageRelationBean storageRelationBean = null;
                         if(storageRelations.size()>0){
                             storageRelationBean = storageRelations.get(0);
                         }
                         //成本
                         cellValue = obj[7];
                         if(!StringTools.isNullOrNone(cellValue)){
                             try{
                                 double price = Double.parseDouble(cellValue);
                                 double price0 = storageRelationBean.getPrice();
                                 long lp = Math.round(price * 100);
                                 long lp0 = Math.round(price0 * 100);
                                 if(lp != lp0){
                                     builder.append("第[" + currentNumber + "]错误:成本与库存不匹配<br>");
                                     importError = true;
                                 }else{
                                	 baseBean.setCostPrice(price);
                                     baseBean.setCostPriceKey(StorageRelationHelper.getPriceKey(price));
                                 }
                             }catch(Exception ex){
                                 builder.append("第[" + currentNumber + "]错误:成本填写错误<br>");
                                 importError = true;
                             }

                         }else{
                             builder.append("第[" + currentNumber + "]错误:成本必填<br>");
                             importError = true;
                         }

                         //数量
                         cellValue = obj[8];
                         if(!StringTools.isNullOrNone(cellValue)){
                             int amount = Integer.parseInt(cellValue);
                             int amount0 = storageRelationBean.getAmount();
                             _logger.debug("导入值amount:"+amount+",参照值amount0:"+amount0);
                             if(amount < 0){
                                 if(Math.abs(amount)>amount0){
                                     builder.append("第[" + currentNumber + "]错误:数量大于库存<br>");
                                     importError = true;
                                 }else{
                                     baseBean.setAmount(amount);
                                 }
                             }else{
                                 builder.append("第[" + currentNumber + "]错误:数量应为负数<br>");
                                 importError = true;
                             }
                         }else{
                             builder.append("第[" + currentNumber + "]错误:数量必填<br>");
                             importError = true;
                         }
                     }

                     bean.getBaseBeanList().add(baseBean);
                     
                     _logger.debug("add bean...");

                     importItemList.add(bean);
                 }
                 else
                 {
                     builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("数据长度不足17格错误")
                         .append("<br>");

                     importError = true;
                 }
             }


         }catch (Exception e)
         {
             _logger.error(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

             return mapping.findForward("batchDropImport");
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

             return mapping.findForward("batchDropImport");
         }

         String batchId = "";

         User user = Helper.getUser(request);

         try
         {
             batchId = outImportManager.batchDrop(user, importItemList);
             request.setAttribute(KeyConstant.MESSAGE, "批量报废申请成功");
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("batchDropImport");
         }

         request.setAttribute("batchId", batchId);

         request.setAttribute("flag", 0);

         //return queryBatchDrop(mapping, form, request, response);
         return mapping.findForward("batchDropImport");
     }

     private boolean checkDateFormat(String dateStr){
         boolean flag = true;
         if(dateStr.length() != 10){
             flag = false;
         }
         String[] strs =dateStr.split("-");
         if(strs.length!=3){
             flag = false;
         }
         return flag;
     }

     public ActionForward queryBatchDrop(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         String batchId = RequestTools.getValueFromRequest(request, "batchId");

         CommonTools.saveParamers(request);

         // 查询
         List<BatchApproveBean> baList = batchApproveDAO.queryEntityBeansByFK(batchId);

         request.setAttribute("baList", baList);

         return mapping.findForward("queryBatchDrop");
     }

     /**
      * 批量审核
      * 导入要审核的单子
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchApproveImport(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<BatchApproveBean> importItemList = new ArrayList<BatchApproveBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importBatchApprove");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importBatchApprove");
         }

         String type = rds.getParameter("mode");

         if (StringTools.isNullOrNone(type))
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "参数错误");

             return mapping.findForward("importBatchApprove");
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
                     BatchApproveBean bean = new BatchApproveBean();

                     bean.setType(MathTools.parseInt(type));

                     // 中信订单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         bean.setOutId(obj[0]);
                     }
                     else
                     {
                         throw new MYException("中信银行订单号不能为空");
                     }

                     //2014/12/9 导入时取消检查结算价为0的控制，将此检查移到“商务审批”通过环节
                     List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByFK(bean.getOutId());
                     String appName = ConfigLoader.getProperty("appName");
                     if (!ListTools.isEmptyOrNull(baseBeans)){
                         _logger.info(bean.getOutId()+"**************baseBeans size ************"+baseBeans.size());
                         for (BaseBean base : baseBeans){
                             // 业务员结算价，总部结算价
                             ProductBean product = productDAO.find(base.getProductId());

                             if (null == product)
                             {
                                 throw new RuntimeException("产品不存在");
                             }

                             double sailPrice = product.getSailPrice();

                             // 根据配置获取结算价
                             List<PriceConfigBean> pcblist = priceConfigDAO.querySailPricebyProductId(product.getId());

                             if (!ListTools.isEmptyOrNull(pcblist))
                             {
                                 PriceConfigBean cb = priceConfigManager.calcSailPrice(pcblist.get(0));

                                 sailPrice = cb.getSailPrice();
                             }
                             //2020-4-15 add 专业市场把检查结算价大于0的校验去掉，默认结算价等于成本价,有结算价就取结算价，没有结算价就取成本价
                             if(sailPrice == 0)
                             {
                            	 if(appName.equalsIgnoreCase(AppConstant.APP_NAME_ZYSC))
                                 {
                                	 //没有结算价取成本价
                                	 String locationId= base.getLocationId();
                                	 String productId = base.getProductId();
                                	 String depotpartid = base.getDepotpartId();
                                	 ConditionParse condparse = new ConditionParse();
                                	 condparse.addWhereStr();
                                	 condparse.addCondition("locationid", "=", locationId);
                                	 condparse.addCondition("productId", "=", productId);
                                	 condparse.addCondition("depotpartid", "=", depotpartid);
                                	 List<StorageRelationBean> srBeanList = storageRelationDAO.queryEntityBeansByCondition(condparse);
                                	 if(srBeanList.size() > 0)
                                	 {
                                		 sailPrice = srBeanList.get(0).getPrice();
                                	 }
                                	 
                                 }
                             }
                             //end add

                             
                             String stafferId = "";
                             OutBean out = this.outDAO.find(bean.getOutId());
                             if (out.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH)
                             {
                                 stafferId = out.getStafferId();
                             }else
                             {
                                 StafferVSCustomerVO vsCustVO = stafferVSCustomerDAO.findVOByUnique(out.getCustomerId());

                                 stafferId = vsCustVO.getStafferId();
                             }
                             final StafferBean stafferBean = stafferDAO.find(stafferId);
                             // 获取销售配置
                             SailConfBean sailConf = sailConfigManager.findProductConf(stafferBean,
                                     product);

                             if (sailConf == null){
                                 throw new RuntimeException("销售配置不存在:"+base.getProductId());
                             }

                             // 总部结算价(产品结算价 * (1 + 总部结算率))
                             base.setPprice(sailPrice
                                     * (1 + sailConf.getPratio() / 1000.0d));

                             if (sailConf.getIprice() >0){
                                 base.setIprice(sailConf.getIprice());
                             } else{
                                 // 事业部结算价(产品结算价 * (1 + 总部结算率 + 事业部结算率))
                                 base.setIprice(sailPrice
                                         * (1 + sailConf.getIratio() / 1000.0d + sailConf
                                         .getPratio() / 1000.0d));
                             }

                             // 业务员结算价就是事业部结算价
                             base.setInputPrice(base.getIprice());

                             //2014/12/9 导入时取消检查结算价为0的控制，将此检查移到“商务审批”通过环节
                             if(!appName.equalsIgnoreCase(AppConstant.APP_NAME_ZYSC))
                             {
                            	 _logger.info(base.getProductName()+"***getInputPrice***"+base.getInputPrice());
                                 if (base.getInputPrice() == 0)
                                 {
                                     String msg = bean.getOutId()+ "结算价不能为0:"+base.getProductName() ;
                                     _logger.warn(msg);
                                     request.setAttribute(KeyConstant.ERROR_MESSAGE,msg);
                                     throw new MYException(msg);
                                 }  else{
                                     _logger.debug(base.getProductName()+"更新结算价");
                                     try{
                                         this.outManager.updateBase(base);
                                     }catch(Exception e){
                                         e.printStackTrace();
                                     }
                                 }
                             }
                         }
                     }

                     // 申请人
                     if ( !StringTools.isNullOrNone(obj[1]))
                     {
                         String applyId =  obj[1];

                         StafferBean sb = stafferDAO.findByUnique(applyId);

                         if (sb == null)
                         {
                             throw new MYException("申请人不存在");
                         }else{
                             bean.setApplyId(applyId);
                         }
                     }
                     else
                     {
                         throw new MYException("申请人不能为空");
                     }

                     // 审批动作
                     if ( !StringTools.isNullOrNone(obj[2]))
                     {
                         String action = obj[2];
                         if (!action.equals("通过") && !action.equals("驳回"))
                         {
                             throw new MYException("审批动作只能是通过或驳回");
                         }

                         bean.setAction(action);
                     }
                     else
                     {
                         throw new MYException("审批动作不存在");
                     }

                     // 审批意见
                     if ( !StringTools.isNullOrNone(obj[3]))
                     {
                         bean.setReason(obj[3]);
                     }

                     // 客户备注
                     if ( !StringTools.isNullOrNone(obj[4]))
                     {
                         bean.setDescription(obj[4].trim());
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

             return mapping.findForward("importBatchApprove");
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

             return mapping.findForward("importBatchApprove");
         }

         String batchId = "";

         try
         {
             batchId = outImportManager.batchApproveImport(importItemList, MathTools.parseInt(type));
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importBatchApprove");
         }

         request.setAttribute("batchId", batchId);

         request.setAttribute("flag", 0);

         return queryBatchApprove(mapping, form, request, response);
     }

     public ActionForward queryBatchApprove(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         String batchId = RequestTools.getValueFromRequest(request, "batchId");

         CommonTools.saveParamers(request);

         // 查询
         List<BatchApproveBean> baList = batchApproveDAO.queryEntityBeansByFK(batchId);

         request.setAttribute("baList", baList);

         return mapping.findForward("queryBatchApprove");
     }

     /**
      * queryBatchSwatch
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward queryBatchSwatch(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         String batchId = RequestTools.getValueFromRequest(request, "batchId");

         CommonTools.saveParamers(request);

         // 查询
         List<BatchSwatchBean> baList = batchSwatchDAO.queryEntityBeansByFK(batchId);

         request.setAttribute("baList", baList);

         return mapping.findForward("queryBatchSwatch");
     }

     /**
      * 批量审核 处理
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchApprove(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         String batchId = request.getParameter("batchId");

         User user = Helper.getUser(request);

         try{
             outImportManager.batchApprove(user, batchId);

             request.setAttribute(KeyConstant.MESSAGE, "批量审批成功");
         }catch(MYException e)
         {
             _logger.warn(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量审批失败");
         }

         request.setAttribute("batchId", batchId);

         request.setAttribute("flag", 1);

         return queryBatchApprove(mapping, form, request, response);
     }

     /**
      * batchSwacth
      *
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchSwatch(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         String batchId = request.getParameter("batchId");

         User user = Helper.getUser(request);

         try{
             outImportManager.batchSwatch(user, batchId);

             request.setAttribute(KeyConstant.MESSAGE, "批量处理成功");
         }catch(MYException e)
         {
             _logger.warn(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "批量处理失败");
         }

         request.setAttribute("batchId", batchId);

         request.setAttribute("flag", 1);

         return queryBatchSwatch(mapping, form, request, response);
     }

     public ActionForward exportBatchSwatch(ActionMapping mapping, ActionForm form,
                                 HttpServletRequest request, HttpServletResponse response)
             throws ServletException
     {
         String batchId = request.getParameter("batchId");

         OutputStream out = null;

         String filenName = "ExportBatchSwatch_" + TimeTools.now("MMddHHmmss") + ".csv";

         User user = (User) request.getSession().getAttribute("user");

         ConditionParse con = new ConditionParse();

         List<BatchSwatchBean> batchSwatchBeans = this.batchSwatchDAO.queryEntityBeansByFK(batchId);

         if (ListTools.isEmptyOrNull(batchSwatchBeans))
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
             line.writeColumn("批次");
             line.writeColumn("单号");
             line.writeColumn("商品名");
             line.writeColumn("数量");
             line.writeColumn("动作");
             line.writeColumn("客户");
             line.writeColumn("处理结果");
             line.writeColumn("目的仓库");
             line.writeColumn("备注");
             line.writeColumn("快递单号");

             line.writeLine();

             for (Iterator<BatchSwatchBean> iter = batchSwatchBeans.iterator(); iter.hasNext();)
             {
                 BatchSwatchBean ib = iter.next();
                 line.writeColumn(ib.getBatchId());
                 line.writeColumn(ib.getOutId());
                 line.writeColumn(ib.getProductName());
                 line.writeColumn(ib.getAmount());
                 line.writeColumn(ib.getAction());
                 line.writeColumn(ib.getCustomerName());
                 line.writeColumn(ib.getResult());
                 line.writeColumn(ib.getDirDeport());
                 line.writeColumn(ib.getDescription());
                 line.writeColumn(ib.getTransportNo());

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

     /**
      * 领样/巡展 导入式生成退货或销售
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchSwatchImport(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<BatchSwatchBean> importItemList = new ArrayList<BatchSwatchBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importBatchSwatch");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importBatchSwatch");
         }

         Map<String,String> map = new HashMap<String, String>();

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
                     BatchSwatchBean bean = new BatchSwatchBean();

                     // 审批动作
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String action = obj[0];
                         if (!action.equals("退货"))
                         {
                             throw new MYException("类型只能是领样转退货");
                         }

                         bean.setAction(action);
                     }
                     else
                     {
                         throw new MYException("类型不能为空");
                     }

                     // 中信订单号
                     if ( !StringTools.isNullOrNone(obj[1]))
                     {
                         String outId = obj[1].trim();

                         // 须为销售领样单
                         OutBean out = outDAO.find(outId);

                         if (null == out)
                         {
                             throw new MYException("销售单[%s]不存在", outId);
                         }

                         if (out.getType() == OutConstant.OUT_TYPE_OUTBILL &&
                                 (out.getOutType() == OutConstant.OUTTYPE_OUT_SWATCH
                                 || out.getOutType() == OutConstant.OUTTYPE_OUT_SHOW
                                 //2015/3/17 新增银行领样 （与银行铺货类拟）
                                 || out.getOutType() == OutConstant.OUTTYPE_OUT_BANK_SWATCH
                                 || out.getOutType() == OutConstant.OUTTYPE_OUT_SHOWSWATCH) )
                         {
                             if (out.getStatus() == OutConstant.STATUS_SEC_PASS || out.getStatus() == OutConstant.STATUS_PASS)
                             {
                                 bean.setOutId(outId);
                             }else{
                                 throw new MYException("销售状态不是已出库");
                             }
                         }
                         else
                         {
                             throw new MYException("销售类型错误");
                         }
                     }
                     else
                     {
                         throw new MYException("中信银行订单号不能为空");
                     }

                     // 商品
                     if ( !StringTools.isNullOrNone(obj[2]))
                     {
                         String productName = obj[2].trim();
                         ProductBean productBean = this.productDAO.findByName(productName);
                         if (productBean == null){
                             throw new MYException("产品不存在:"+productName);
                         } else{
                             bean.setProductName(productName);
                         }
                     }else
                     {
                         throw new MYException("商品不能为空");
                     }

                     // 数量
                     if ( !StringTools.isNullOrNone(obj[3]))
                     {
                         int amount = MathTools.parseInt(obj[3].trim());

                         if (amount <= 0)
                         {
                             throw new MYException("数量须大于0");
                         }

                         bean.setAmount(amount);
                     }else
                     {
                         throw new MYException("数量不能为空");
                     }

                     // 客户
                     if ( !StringTools.isNullOrNone(obj[4]))
                     {
                         String cust = obj[4];

//                         CustomerBean custBean = customerMainDAO.findByUnique(cust);
//
//                         if (null == custBean)
//                         {
//                             throw new MYException(cust+ " 客户不存在");
//                         }else{
//                             bean.setCustomerId(custBean.getId());
//                         }
                         List<CustomerBean> cbeans = customerMainDAO.queryByName(cust);

                         if (ListTools.isEmptyOrNull(cbeans))
                         {
                             throw new MYException(cust+ " 客户不存在");
                         }
                         else {
                             bean.setCustomerName(cust);
                             bean.setCustomerId(cbeans.get(0).getId());
                         }
                     }else
                     {
                         throw new MYException("客户不能为空");
                     }

                     // 目的仓库
                     if ( !StringTools.isNullOrNone(obj[5]))
                     {
                         String dirDeport = obj[5];

                         DepotBean deportBean = depotDAO.findByUnique(dirDeport);

                         if (null == deportBean)
                         {
                             throw new MYException(dirDeport+ " 仓库不存在");
                         }else{
                             bean.setDirDeport(deportBean.getId());
                         }
                     }else
                     {
                         throw new MYException("目的仓库不能为空");
                     }

                     if (map.containsKey(bean.getOutId() + "~" + bean.getProductName()))
                     {
                         throw new MYException("一次导入时同一单同一产品不能重复出现");
                     }else
                     {
                         map.put(bean.getOutId() + "~" + bean.getProductName(), bean.getOutId() + "~" + bean.getProductName());
                     }

                     //2015/10/28 个人或银行领样批量退单备注写错栏位
                     //备注
                     bean.setDescription(obj[6].trim());

                     //2016/3/22 #202
                     if ( !StringTools.isNullOrNone(obj[7]))
                     {
                         String transportNo = obj[7].trim();
                         bean.setTransportNo(transportNo);
                     }else
                     {
                         throw new MYException("快递单号不能为空");
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

             return mapping.findForward("importBatchSwatch");
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

             return mapping.findForward("importBatchSwatch");
         }

         String batchId = "";

         try
         {
             batchId = outImportManager.batchSwatchImport(importItemList);
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importBatchSwatch");
         }

         request.setAttribute("batchId", batchId);

         request.setAttribute("flag", 0);

         return queryBatchSwatch(mapping, form, request, response);
     }

     /**
      * 批量导入发货信息,并根据配送单号更新
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward importConsign(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<ConsignBean> importItemList = new ArrayList<ConsignBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importConsign");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importConsign");
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
                     ConsignBean bean = new ConsignBean();

                     // 配送单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         bean.setDistId(obj[0]);
                     }
                     else
                     {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("出库单不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     if ( !StringTools.isNullOrNone(obj[1]))
                     {
                         bean.setReveiver(obj[1]);
                     }

                     if ( !StringTools.isNullOrNone(obj[2]))
                     {
                         bean.setApplys(obj[2]);
                     }

                     // 发货方式
                     if ( !StringTools.isNullOrNone(obj[3]))
                     {
                         boolean has = false;

                         String shipping = obj[3].trim();

                         for (int i = 0 ; i < OutImportConstant.shipping.length; i++)
                         {
                             if (OutImportConstant.shipping[i].equals(shipping))
                             {
                                 has = true;

                                 bean.setShipping(OutImportConstant.ishipping[i]);

                                 break;
                             }
                         }

                         if (!has)
                         {
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("发货方式不对,须为[自提,公司,第三方快递,第三方货运,第三方快递+货运,空发]中之一")
                                     .append("<br>");

                             importError = true;
                         }
                     }else
                     {
                         builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("发货方式不能为空,须为[自提,公司,第三方快递,第三方货运,第三方快递+货运,空发]中之一")
                                 .append("<br>");

                         importError = true;
                     }

                     //发货公司
                     if ( !StringTools.isNullOrNone(obj[4]))
                     {
                         // transport 须是在transport1 下面的
                         String transport = obj[4].trim();

                         ExpressBean express = expressDAO.findByUnique(transport);

 //            			TransportBean tb = consignDAO.findTransportByName(transport);

                         if (null == express)
                         {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("发货公司不存在")
                             .append("<br>");

                             importError = true;
                         } else{
                             bean.setTransport(express.getId());
                         }
                     }
                     else
                     {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("发货公司不能为空")
                         .append("<br>");

                         importError = true;
                     }

 //                    //支付方式
 //                    if ( !StringTools.isNullOrNone(obj[5]))
 //                    {
 //                        String expressPay = obj[5].trim();
 //
 //                        boolean isexists = false;
 //
 //                        for (int i = 0; i < OutImportConstant.expressPay.length; i++)
 //                        {
 //                            if (expressPay.equals(OutImportConstant.expressPay[i]))
 //                            {
 //                                isexists = true;
 //
 //                                bean.setPay(OutImportConstant.iexpressPay[i]);
 //
 //                                break;
 //                            }
 //                        }
 //
 //                        if (!isexists)
 //                        {
 //                            builder
 //                                    .append("第[" + currentNumber + "]错误:")
 //                                    .append("支付方式不存在,只能为[业务员支付,公司支付,客户支付]之一")
 //                                    .append("<br>");
 //
 //                            importError = true;
 //                        }
 //                    }else
 //                    {
 //                        builder
 //                                .append("第[" + currentNumber + "]错误:")
 //                                .append("支付方式不能为空,只能为[业务员支付,公司支付,客户支付]之一")
 //                                .append("<br>");
 //
 //                        importError = true;
 //                    }

                     if ( !StringTools.isNullOrNone(obj[5]))
                     {
                         bean.setTransportNo(obj[5]);
                     }else
                     {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("发货单号不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     if ( !StringTools.isNullOrNone(obj[6]))
                     {
                         bean.setSendPlace(obj[6]);
                     }

                     if ( !StringTools.isNullOrNone(obj[7]))
                     {
                         bean.setPreparer(obj[7]);
                     }

                     if ( !StringTools.isNullOrNone(obj[8]))
                     {
                         bean.setChecker(obj[8]);
                     }

                     if ( !StringTools.isNullOrNone(obj[9]))
                     {
                         bean.setPackager(obj[9]);
                     }

                     if ( !StringTools.isNullOrNone(obj[10]))
                     {
                         bean.setPackageTime(obj[10]);
                     }

                     if ( !StringTools.isNullOrNone(obj[11]))
                     {
                         bean.setPackageTime(bean.getPackageTime() + " " + obj[11]);
                     }

                     if ( !StringTools.isNullOrNone(obj[12]))
                     {
                         bean.setMathine(obj[12]);
                     }

                     if ( !StringTools.isNullOrNone(obj[13]))
                     {
                         bean.setPackageAmount(obj[13]);
                     }

                     if ( !StringTools.isNullOrNone(obj[14]))
                     {
                         bean.setPackageWeight(obj[14]);
                     }

                     if ( !StringTools.isNullOrNone(obj[15]))
                     {
                         bean.setTransportFee(obj[15]);
                     }

                     //2015/6/25 顺丰收货日期必填
                     if ( !StringTools.isNullOrNone(obj[16]))
                     {
                         String date = obj[16].trim();
                         try {
                             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                             sdf.parse(date);
                         }catch(ParseException e){
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("顺丰收货日期格式必须按照XXXX-XX-XX XX:XX:XX")
                                     .append("<br>");

                             importError = true;
                         }
                         bean.setSfReceiveDate(date);
                     }else
                     {
                         builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("顺丰收货日期不能为空")
                                 .append("<br>");

                         importError = true;
                     }

                     importItemList.add(bean);

                 }
                 else
                 {
                     builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("数据长度不足27格错误")
                         .append("<br>");

                     importError = true;
                 }
             }
         }catch (Exception e)
         {
             _logger.error(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

             return mapping.findForward("importConsign");
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

             return mapping.findForward("importConsign");
         }

         try
         {
             outImportManager.batchUpdateConsign(importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importConsign");
         }

         return mapping.findForward("importConsign");
     }

     /**
      * 批量导入销售单号， 目的 根据fullId, 更新emergency
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward importOutId(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<ConsignBean> importItemList = new ArrayList<ConsignBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOutId");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOutId");
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
                     ConsignBean bean = new ConsignBean();

                     // 销售单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String outId = obj[0].trim();

                         // 须为销售领样单
                         OutBean out = outDAO.find(outId);

                         if (null == out)
                         {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("销售单"+ outId +"不存在")
                             .append("<br>");

                             importError = true;
                         } else {
                             if (out.getType() == OutConstant.OUT_TYPE_OUTBILL) {
                                 if (out.getStatus() == OutConstant.STATUS_SEC_PASS || out.getStatus() == OutConstant.STATUS_PASS)
                                 {
                                     builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("销售单号已是发货态，不能操作.")
                                     .append("<br>");

                                     importError = true;
                                 }else{
                                     bean.setFullId(outId);
                                 }
                             } else {
                                 builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("销售单号须是销售单，不能为入库单.")
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

             return mapping.findForward("importConsign");
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

             return mapping.findForward("importOutId");
         }

         try
         {
             outImportManager.batchUpdateEmergency(importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importOutId");
         }

         return mapping.findForward("importOutId");
     }


     /**
      * 批量导入紧急订单， 目的 根据fullId, 更新emergency
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward importEmergencyOut(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response)
             throws ServletException
     {
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<ConsignBean> importItemList = new ArrayList<ConsignBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importEmergencyOut");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importEmergencyOut");
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
                     ConsignBean bean = new ConsignBean();

                     // 销售单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String outId = obj[0].trim();

                         // 须为销售领样单
                         OutBean out = outDAO.find(outId);

                         if (null == out)
                         {
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("销售单或发票号" + outId + "不存在")
                                     .append("<br>");

 //                            importError = true;

                             //2015/4/8 允许导入发票号
                             _logger.info("OutBean Does not exist:"+outId);
                             InvoiceinsBean insBean = invoiceinsDAO.find(outId);
                             if (insBean == null){
                                 importError = true;
                             }
                             bean.setFullId(outId);
                         } else {
                             if (out.getType() == OutConstant.OUT_TYPE_OUTBILL) {
                                 if (out.getStatus() == OutConstant.STATUS_SEC_PASS || out.getStatus() == OutConstant.STATUS_PASS)
                                 {
                                     builder
                                             .append("第[" + currentNumber + "]错误:")
                                             .append("销售单号已是发货态，不能操作.")
                                             .append("<br>");
                                     _logger.warn(builder.toString());
 //                                    importError = true;
                                     bean.setFullId(outId);
                                 }else{
                                     bean.setFullId(outId);
                                 }
                             } else {
                                 builder
                                         .append("第[" + currentNumber + "]错误:")
                                         .append("销售单号须是销售单，不能为入库单.")
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

             return mapping.findForward("importEmergencyOut");
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

             return mapping.findForward("importEmergencyOut");
         }

         try
         {
             outImportManager.batchUpdateEmergency(importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importOutId");
         }

         return mapping.findForward("importOutId");
     }

     /**
      * #150: 2015/12/26 导入订单自动库管审批通过
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward importOutAutoApprove(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse response)
             throws ServletException
     {
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<AutoApproveBean> importItemList = new ArrayList<AutoApproveBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOutAutoApprove");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOutAutoApprove");
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
                     AutoApproveBean bean = new AutoApproveBean();

                     // 销售单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String outId = obj[0].trim();
                         bean.setFullId(outId);

                         // 须为销售单
                         OutBean out = outDAO.find(outId);

                         if (null == out)
                         {
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("销售单" + outId + "不存在")
                                     .append("<br>");
                             importError = true;
                         } else {
                             if (out.getType() == OutConstant.OUT_TYPE_OUTBILL) {
                                 if (out.getStatus() != OutConstant.STATUS_SUBMIT)
                                 {
                                     builder
                                             .append("第[" + currentNumber + "]错误:")
                                             .append("销售单状态必须是待商务审批.")
                                             .append("<br>");
                                     importError = true;
                                 }
                             } else {
                                 builder
                                         .append("第[" + currentNumber + "]错误:")
                                         .append("销售单号须是销售单，不能为入库单.")
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

             return mapping.findForward("importOutAutoApprove");
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

             return mapping.findForward("importOutAutoApprove");
         }

         try
         {
             this.outManager.importOutAutoApprove(importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量导入成功");
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importOutAutoApprove");
         }

         return mapping.findForward("importOutAutoApprove");
     }

     /**
      * 2016/2/13 #171:批量更新未审批原因
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchUpdateReason(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response)
             throws ServletException
     {
         final String url = "batchUpdateReason";
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<OutBean> importItemList = new ArrayList<OutBean>();

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
                     OutBean bean = new OutBean();

                     // 销售单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String outId = obj[0].trim();
                         bean.setFullId(outId);

                         // 须为销售单
                         OutBean out = outDAO.find(outId);

                         if (null == out)
                         {
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("销售单" + outId + "不存在")
                                     .append("<br>");
                             importError = true;
                         } else {
                             if (out.getType() == OutConstant.OUT_TYPE_OUTBILL) {
 //                                if (out.getStatus() != OutConstant.STATUS_SUBMIT)
 //                                {
 //                                    builder
 //                                            .append("第[" + currentNumber + "]错误:")
 //                                            .append("销售单状态必须是待商务审批.")
 //                                            .append("<br>");
 //                                    importError = true;
 //                                }
                                 //TODO
                             } else {
                                 builder
                                         .append("第[" + currentNumber + "]错误:")
                                         .append("销售单号须是销售单，不能为入库单.")
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

                     // 原因
                     if ( !StringTools.isNullOrNone(obj[1]))
                     {
                         String reason = obj[1];
                         bean.setReason(reason);

                         //检查符合配置项
                         ConditionParse conditionParse = new ConditionParse();
                         conditionParse.addWhereStr();
                         conditionParse.addCondition("type", "=", "310");
                         conditionParse.addCondition("val", "=", reason);

                         List<EnumBean> enumBeans = this.enumDAO.queryEntityBeansByCondition(conditionParse);
                         if (ListTools.isEmptyOrNull(enumBeans)){
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("原因与标准名称不符.")
                                     .append("<br>");

                             importError = true;
                         }
                     } else{
                         builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("原因不能为空")
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
            this.outManager.batchUpdateReason(importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量导入成功");
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward(url);
         }

         return mapping.findForward(url);
     }

     /**
      * importOutDepot
      * 		批量导入销售单仓库及库区
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward importOutDepot(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<BaseBean> importItemList = new ArrayList<BaseBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOutId");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importOutId");
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
                     BaseBean bean = new BaseBean();

                     // 销售单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String outId = obj[0].trim();

                         // 须为销售领样单
                         OutBean out = outDAO.find(outId);

                         if (null == out)
                         {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("销售单"+ outId +"不存在")
                             .append("<br>");

                             importError = true;
                         } else {
                             if (out.getType() == OutConstant.OUT_TYPE_OUTBILL) {
                                 if (out.getStatus() != OutConstant.STATUS_SUBMIT)
                                 {
                                     builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("销售单号不是待商务审批态，不能操作.")
                                     .append("<br>");

                                     importError = true;
                                 }else{
                                     bean.setOutId(outId);
                                 }
                             } else {
                                 builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("销售单号须是销售单，不能为入库单.")
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

                     // 商品
                     if (!StringTools.isNullOrNone(obj[1]))
                     {
                         String name = obj[1].trim();

                         if (!StringTools.isNullOrNone(bean.getOutId())) {
                             List<BaseBean> baseList = baseDAO.queryEntityBeansByCondition(" where BaseBean.outid = ? and BaseBean.productname=?", bean.getOutId(), name);

                             if (ListTools.isEmptyOrNull(baseList)) {
                                 builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("销售单产品"+ name +"不存在")
                                 .append("<br>");

                                 importError = true;
                             } else {
                                 bean.setProductId(baseList.get(0).getProductId());
                             }
                         }
                     }
                     else
                     {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("销售单产品不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 仓库
                     if ( !StringTools.isNullOrNone(obj[2]))
                     {
                         String depotName = obj[2];

                         DepotBean depot = depotDAO.findByUnique(depotName);

                         if (null == depot)
                         {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("仓库" + depotName + "不存在")
                             .append("<br>");

                             importError = true;
                         }else{
                             bean.setLocationId(depot.getId());
                         }
                     }else
                     {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("仓库不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 仓区
                     if ( !StringTools.isNullOrNone(obj[3]))
                     {
                         String depotpartName = obj[3].trim();

                         DepotpartBean depotpart = depotpartDAO.findByUnique(depotpartName);

                         if (null == depotpart)
                         {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("仓区[" + depotpartName + "]不存在")
                             .append("<br>");

                             importError = true;
                         }else{
                             if (!depotpart.getLocationId().equals(bean.getLocationId()))
                             {
                                 builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("仓区[" +depotpartName + "]对应的仓库不是导入中的仓库")
                                 .append("<br>");

                                 importError = true;
                             }else
                             {
                                 bean.setDepotpartId(depotpart.getId());
                                 bean.setDepotpartName(depotpart.getName());
                             }
                         }
                     }else
                     {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("仓区不能为空")
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

             return mapping.findForward("importConsign");
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

             return mapping.findForward("importOutId");
         }

         try
         {
             outImportManager.batchUpdateDepot(importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量更新成功");
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importOutId");
         }

         return mapping.findForward("importOutId");
     }

     /**
      * preForSplitOut
      *
      * 按商品合计数量，统一检查是可能库与需要的库存
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward preForSplitOut(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         String id = request.getParameter("id");

         OutImportLogBean logBean = outImportLogDAO.find(id);

         if (null == logBean){
             return ActionTools.toError("数据异常,请重新操作", "queryOutImportLog", mapping, request);
         }

         if (logBean.getStatus() != OutImportConstant.LOGSTATUS_SUCCESSFULL)
         {
             return ActionTools.toError("只能预占导入成功的批次", "queryOutImportLog", mapping, request);
         }

         String batchId = logBean.getBatchId();

         List<OutImportBean> resultList = outImportManager.preUseAmountCheck(batchId);

         request.setAttribute("batchId", batchId);

         request.setAttribute("resultList", resultList);

         return mapping.findForward("querySplitOut");
     }

     /**
      * processSplitOut
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward processSplitOut(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         String batchId = request.getParameter("batchId");

         outImportManager.processSplitOut(batchId);

         request.setAttribute("sbatchId", batchId);

         request.setAttribute(KeyConstant.MESSAGE, "预占操作完成，结果可以在导入数据中查看");

         return mapping.findForward("queryOutImport");
     }

     /**
      * 2015/9/18 批量预占导入  batchProcessSplitOut
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchProcessSplitOut(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response)
             throws ServletException
     {
         _logger.info("*************batchProcessSplitOut*************");

         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<OutBean> importItemList = new ArrayList<OutBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("batchProcessSplitOut");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("batchProcessSplitOut");
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
                     OutBean bean = new OutBean();

                     // 销售单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String outId = obj[0].trim();

                         OutBean out = outDAO.find(outId);

                         if (null == out)
                         {
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("销售单或发票号" + outId + "不存在")
                                     .append("<br>");
                             importError = true;
                         } else {
                             bean.setFullId(outId);
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

             return mapping.findForward("batchProcessSplitOut");
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

             return mapping.findForward("batchProcessSplitOut");
         }

         try
         {
             outImportManager.batchProcessSplitOut(importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量预占库存成功");
         }
         catch(Exception e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getStackTrace());

             return mapping.findForward("batchProcessSplitOut");
         }

         return mapping.findForward("batchProcessSplitOut");
     }

     /**
      * 2016/3/10 #190
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward batchUpdateProductName(ActionMapping mapping, ActionForm form,
                                               HttpServletRequest request, HttpServletResponse response)
             throws ServletException
     {
         String url = "batchUpdateProductName";
         _logger.info("*************batchUpdateProductName*************");

         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<BaseVO> importItemList = new ArrayList<BaseVO>();

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
                     BaseVO bean = new BaseVO();

                     // 销售单号
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String outId = obj[0].trim();

                         OutBean out = outDAO.find(outId);

                         if (null == out)
                         {
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("销售单" + outId + "不存在")
                                     .append("<br>");
                             importError = true;
                         } else {
                             //只要状态不为“待库管审批”、“已出库”或“已发货”，均可更换品名
                             bean.setOutId(outId);
                             if (out.getStatus() == OutConstant.STATUS_FLOW_PASS ||
                                     out.getStatus() == OutConstant.STATUS_PASS ||
                                     out.getStatus() == OutConstant.STATUS_SEC_PASS){
                                 builder
                                         .append("第[" + currentNumber + "]错误:")
                                         .append(outId + "待库管审批、已出库、已发货销售单不能修改品名")
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

                     // 原商品名
                     if ( !StringTools.isNullOrNone(obj[1]))
                     {
                         String productName = obj[1].trim();
                         bean.setProductName(productName);

                         ProductBean productBean = this.productDAO.findByName(productName);
                         if (productBean == null){
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("原商品名不存在:"+productName)
                                     .append("<br>");

                             importError = true;
                         } else{
                             bean.setProductId(productBean.getId());
                         }
                     }
                     else
                     {
                         builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("原商品名不能为空")
                                 .append("<br>");

                         importError = true;
                     }

                     // 新商品名
                     if ( !StringTools.isNullOrNone(obj[2]))
                     {
                         String destProductName = obj[2].trim();
                         bean.setDestProductName(destProductName);

                         ProductBean productBean = this.productDAO.findByName(destProductName);
                         if (productBean == null){
                             builder
                                     .append("第[" + currentNumber + "]错误:")
                                     .append("新商品名不存在:"+destProductName)
                                     .append("<br>");

                             importError = true;
                         } else{
                             bean.setDestProductId(productBean.getId());
                         }
                     }
                     else
                     {
                         builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("新商品名不能为空")
                                 .append("<br>");


                         importError = true;
                     }

                     // 单价
                     if ( !StringTools.isNullOrNone(obj[3])) {
                         String price = obj[3].trim();
                         bean.setPrice(Double.valueOf(price));
                     } else
                     {
                         builder
                                 .append("第[" + currentNumber + "]错误:")
                                 .append("单价不能为空")
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
             User user = Helper.getUser(request);
             outImportManager.batchUpdateProductName(user, importItemList);

             request.setAttribute(KeyConstant.MESSAGE, "批量修改商品名成功");
         }
         catch(Exception e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getStackTrace());

             return mapping.findForward(url);
         }

         return mapping.findForward(url);
     }


     /**
      * importBankSail
      * 银行销售导入
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward importBankSail(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         User user = Helper.getUser(request);

         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<BankSailBean> importItemList = new ArrayList<BankSailBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importBankSail");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importBankSail");
         }

         String itype = rds.getParameter("type");

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
                     BankSailBean bean = new BankSailBean();

                     // 销售日期
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
                         Pattern p = Pattern.compile(eL);
                         Matcher m = p.matcher(obj[0].trim());
                         boolean dateFlag = m.matches();
                         if (!dateFlag) {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("销售日期格式错误，如 2000-01-01")
                             .append("<br>");

                             importError = true;
                         }

                         bean.setOutTime(obj[0].trim());
                     } else {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("销售日期不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 网点名称（客户）
                     if ( !StringTools.isNullOrNone(obj[1]))
                     {
                         String name = obj[1].trim();

//                         CustomerBean customer = customerMainDAO.findByUnique(name + "（银行）");
//
//                         if (null == customer) {
//                             builder
//                             .append("第[" + currentNumber + "]错误:")
//                             .append("网点名称（客户）不存在")
//                             .append("<br>");
//
//                             importError = true;
//                         } else {
//                             bean.setCustomerId(customer.getId());
//                             bean.setCustomerName(customer.getName());
//                         }
                         List<CustomerBean> cbeans = customerMainDAO.queryByName(name + "（银行）");

                         if (ListTools.isEmptyOrNull(cbeans))
                         {
                          builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("网点名称（客户）不存在")
                             .append("<br>");

                             importError = true;
                         }
                         else{
                              bean.setCustomerId(cbeans.get(0).getId());
                             bean.setCustomerName(cbeans.get(0).getName());
                         }
                     } else {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("网点名称（客户）不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 金额
                     if ( !StringTools.isNullOrNone(obj[2]))
                     {
                         double value = MathTools.parseDouble(obj[2].trim());

                         if (value <= 0) {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("金额只能是大于0的数字")
                             .append("<br>");

                             importError = true;
                         } else {
                             bean.setValue(value);
                         }

                     } else {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("金额不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 商品
                     if ( !StringTools.isNullOrNone(obj[3]))
                     {
                         bean.setProductName(obj[3].trim());
                     } else {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("商品不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 数量
                     if ( !StringTools.isNullOrNone(obj[4]))
                     {
                         int amount = MathTools.parseInt(obj[4]);

                         if (amount <= 0) {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("商品不能为空")
                             .append("<br>");

                             importError = true;
                         } else {
                             bean.setAmount(amount);
                         }
                     } else {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("商品不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     //  中收
                     if ( !StringTools.isNullOrNone(obj[5]))
                     {
                         double value = MathTools.parseDouble(obj[5].trim());

                         if (value < 0) {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("中收只能是大于或等于0的数字")
                             .append("<br>");

                             importError = true;
                         } else {
                             bean.setMidincome(value);
                         }

                     } else {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("中收不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     bean.setLogTime(TimeTools.now());
                     bean.setStafferName(user.getStafferName());

                     bean.setType(MathTools.parseInt(itype));

                     importItemList.add(bean);

                 }
                 else
                 {
                     builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("数据长度不足32格错误")
                         .append("<br>");

                     importError = true;
                 }
             }


         }catch (Exception e)
         {
             _logger.error(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

             return mapping.findForward("importBankSail");
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

             return mapping.findForward("importBankSail");
         }

         try
         {
             outImportManager.addBankSail(user, importItemList);
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importBankSail");
         }

         return mapping.findForward("importBankSail");
     }

     /**
      * queryBankSail
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward queryBankSail(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         ConditionParse condtion = new ConditionParse();

         condtion.addWhereStr();

         // 默认 查询条件
         Map<String, String> initMap = initLogTime(request, condtion);

         ActionTools.processJSONDataQueryCondition(QUERYBANKSAIL, request,
                 condtion, initMap);

         condtion.addCondition(" order by BankSailBean.logTime desc");

         String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYBANKSAIL, request, condtion,
             this.bankSailDAO);

         return JSONTools.writeResponse(response, jsonstr);
     }

     private Map<String, String> initLogTime(HttpServletRequest request,
             ConditionParse condtion) {
         Map<String, String> changeMap = new HashMap<String, String>();

         String alogTime = request.getParameter("beginDate");

         String blogTime = request.getParameter("beginDate1");

         if (StringTools.isNullOrNone(alogTime)
                 && StringTools.isNullOrNone(blogTime)) {
             changeMap.put("beginDate", TimeTools.now_short(-30));

             changeMap.put("beginDate1", TimeTools.now_short());

             condtion.addCondition("BankSailBean.logTime", ">=",
                     TimeTools.now_short(-30) + " 00:00:00");

             condtion.addCondition("BankSailBean.logTime", "<=",
                     TimeTools.now_short() + " 23:59:59");
         }

         return changeMap;
     }

     private Map<String, String> initLogTime1(HttpServletRequest request,
             ConditionParse condtion) {
         Map<String, String> changeMap = new HashMap<String, String>();

         String alogTime = request.getParameter("beginDate");

         String blogTime = request.getParameter("beginDate1");

         if (StringTools.isNullOrNone(alogTime)
                 && StringTools.isNullOrNone(blogTime)) {
             changeMap.put("beginDate", TimeTools.now_short(-30));

             changeMap.put("beginDate1", TimeTools.now_short());

             condtion.addCondition("OutImportBean.logTime", ">=",
                     TimeTools.now_short(-30) + " 00:00:00");

             condtion.addCondition("OutImportBean.logTime", "<=",
                     TimeTools.now_short() + " 23:59:59");
         }

         return changeMap;
     }

     /**
      * deleteBankSail
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward deleteBankSail(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         String batchId = request.getParameter("id");

         AjaxResult ajax = new AjaxResult();

         try {
             User user = Helper.getUser(request);

             outImportManager.deleteBankSail(user, batchId);

             ajax.setSuccess("删除成功");
         } catch (MYException e) {
             _logger.warn(e,e);

             ajax.setError("删除失败");
         }

         return JSONTools.writeResponse(response, ajax);
     }

     /**
      * importEstimateProfit
      */
     public ActionForward importEstimateProfit(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         User user = Helper.getUser(request);

         RequestDataStream rds = new RequestDataStream(request);

         boolean importError = false;

         List<EstimateProfitBean> importItemList = new ArrayList<EstimateProfitBean>();

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importEstimateProfit");
         }

         if ( !rds.haveStream())
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importEstimateProfit");
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
                     EstimateProfitBean bean = new EstimateProfitBean();

                     // 产品
                     if ( !StringTools.isNullOrNone(obj[0]))
                     {
                         String name = obj[0].trim();

                         bean.setProductName(name);

                     } else {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("产品不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     // 预估毛利
                     if ( !StringTools.isNullOrNone(obj[1]))
                     {
                         double value = MathTools.parseDouble(obj[1].trim());

                         if (value <= 0) {
                             builder
                             .append("第[" + currentNumber + "]错误:")
                             .append("预估毛利 只能为大于0的数字")
                             .append("<br>");

                             importError = true;
                         } else {
                             bean.setProfit(value);
                         }

                     } else {
                         builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("预估毛利不能为空")
                         .append("<br>");

                         importError = true;
                     }

                     bean.setLogTime(TimeTools.now());
                     bean.setStafferName(user.getStafferName());

                     importItemList.add(bean);

                 }
                 else
                 {
                     builder
                         .append("第[" + currentNumber + "]错误:")
                         .append("数据长度不足32格错误")
                         .append("<br>");

                     importError = true;
                 }
             }


         }catch (Exception e)
         {
             _logger.error(e, e);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

             return mapping.findForward("importEstimateProfit");
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

             return mapping.findForward("importEstimateProfit");
         }

         try
         {
             outImportManager.addEstimateProfit(user, importItemList);
         }
         catch(MYException e)
         {
             request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getErrorContent());

             return mapping.findForward("importEstimateProfit");
         }

         return mapping.findForward("importEstimateProfit");
     }

     /**
      *
      * @param mapping
      * @param form
      * @param request
      * @param response
      * @return
      * @throws ServletException
      */
     public ActionForward deleteEstimateProfit(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     throws ServletException
     {
         String id = request.getParameter("id");

         AjaxResult ajax = new AjaxResult();

         try {
             User user = Helper.getUser(request);

             outImportManager.deleteEstimateProfit(user, id);

             ajax.setSuccess("删除成功");
         } catch (MYException e) {
             _logger.warn(e,e);

             ajax.setError("删除失败");
         }

         return JSONTools.writeResponse(response, ajax);
     }
     
     /**
      * 批量导入采购订单
      * @param mapping
      * @param form
      * @param request
      * @param response
      */
     public ActionForward importProcurement(ActionMapping mapping, ActionForm form,
             HttpServletRequest request, HttpServletResponse response)
     {

         User user = Helper.getUser(request);

         RequestDataStream rds = new RequestDataStream(request);

         StringBuilder builder = new StringBuilder();

         try
         {
             rds.parser();
             if (!rds.haveStream())
             {
            	 request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");
                 return mapping.findForward("importProcurement");
             }
         }
         catch (Exception e1)
         {
             _logger.error(e1, e1);

             request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

             return mapping.findForward("importProcurement");
         }

         ReaderFile reader = ReadeFileFactory.getXLSReader();
         List<ImportProcurementBean> beanList = new ArrayList<ImportProcurementBean>();

         try
         {
             reader.readFile(rds.getUniqueInputStream());

             while (reader.hasNext())
             {
                 // 第一行忽略
                 if (reader.getCurrentLineNumber() == 1)
                 {
                     continue;
                 }

                 String[] obj = fillObj((String[])reader.next());
                 int currentNumber = reader.getCurrentLineNumber();

                 ImportProcurementBean bean = new ImportProcurementBean();

                 // 产品
                 if (StringTools.isNullOrNone(obj[0]))
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("产品不能为空");
                	 break;

                 }
                 String productName = obj[0].trim();
                 List<ProductBean> productList = productDAO.queryEntityBeansByCondition(" where name=?", productName);
                 if(productList.size() == 0)
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("产品:" + productName + "不存在");
                	 break;
                 }
                 ProductBean prod = productList.get(0);
                 if (StringTools.isNullOrNone(obj[1]))
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("数量不能为空");
                	 break;
                 }
                 int productNum = MathTools.parseInt(obj[1].trim());

                 if (productNum <= 0) {
                     builder.append("第[" + currentNumber + "]行错误:").append("数量不能小于0");

                 }
                 if (StringTools.isNullOrNone(obj[2]))
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("成本不能为空");
                	 break;
                 }
                 double productCost = MathTools.parseDouble(obj[2].trim());
                 if (productCost <= 0) {
                     builder.append("第[" + currentNumber + "]行错误:").append("成本不能小于0");
                     break;
                 }
                 if (StringTools.isNullOrNone(obj[3]))
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("入库仓库不能为空");
                	 break;
                 }
                 List<DepotBean> depotBeanList = depotDAO.queryEntityBeansByCondition(" where name=?", obj[3].trim());
                 if(depotBeanList.size() == 0)
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("入库仓库:" +  obj[3].trim() +"不存在");
                	 break;
                 }
                 DepotBean depot = depotBeanList.get(0);
                 if (StringTools.isNullOrNone(obj[4]))
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("入库仓区不能为空");
                	 break;
                 }
                 List<DepotpartBean> depotpartBeanList = depotpartDAO.queryEntityBeansByCondition(" where name=?", obj[4].trim());
                 if(depotpartBeanList.size() == 0)
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("入库仓区" + obj[4].trim() +"不存在");
                	 break;
                 }
                 if (StringTools.isNullOrNone(obj[5]))
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("供应商不能为空");
                	 break;
                 }
                 List<UnitBean> unitBeanList = unitDAO.queryEntityBeansByCondition(" where name=?", obj[5].trim());
                 if(unitBeanList.size() == 0)
                 {
                	 builder.append("第[" + currentNumber + "]行错误:").append("供应商:" + obj[5].trim() +"不存在");
                	 break;
                 }
                 DepotpartBean depotpart = depotpartBeanList.get(0);
                 UnitBean unitBean = unitBeanList.get(0);
                 bean.setProductName(productName);
                 bean.setProductNum(productNum);
                 bean.setProductCost(new BigDecimal(productCost));
                 bean.setDepotName(obj[3].trim());
                 bean.setDepotpartName(obj[4].trim());
                 bean.setDepotId(depot.getId());
                 bean.setDepotpartId(depotpart.getId());
                 bean.setLocationId(depotpart.getLocationId());
                 bean.setProductId(prod.getId());
                 bean.setUnitId(unitBean.getId());
                 bean.setUnitName(unitBean.getName());
                 bean.setRemark(obj[6]);
                 beanList.add(bean);
             }
             
             if (builder.length() > 0){
                 request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ builder.toString());
                 return mapping.findForward("importProcurement");
             }
             //合并bean
             List<ImportProcurementBean> resultBeanList = new ArrayList<ImportProcurementBean>();
             Map<String,ImportProcurementBean> keyMap = new HashMap<String, ImportProcurementBean>();
             for(ImportProcurementBean bean : beanList)
             {
            	 StringBuffer sb= new StringBuffer();
            	 String productName = bean.getProductName();
            	 double productCost = bean.getProductCost().doubleValue();
            	 String depotName = bean.getDepotName();
            	 String depotpart = bean.getDepotpartName();
            	 String unitName = bean.getUnitName();
            	 sb.append(productName);
            	 sb.append(productCost);
            	 sb.append(depotName);
            	 sb.append(depotpart);
            	 sb.append(unitName);
            	 String key = sb.toString();
            	 if(keyMap.containsKey(key))
            	 {
            		 ImportProcurementBean mapObj = keyMap.get(key);
            		 int pnum = mapObj.getProductNum();
            		 
            		 pnum = pnum + bean.getProductNum();
            		 mapObj.setProductNum(pnum);
            	 }
            	 else
            	 {
            		 keyMap.put(key, bean);
            	 }
             }
             resultBeanList = new ArrayList<ImportProcurementBean>(keyMap.values());
        	 autoToOut(user,resultBeanList);

         }catch (Exception e)
         {
        	 e.printStackTrace();
             _logger.error(e, e);
             request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());
             return mapping.findForward("importProcurement");
         }
         finally
         {
             try
             {
                 reader.close();
                 rds.close();
             }
             catch (IOException e)
             {
                 _logger.error(e, e);
             }
         }
         request.setAttribute(KeyConstant.MESSAGE, "导入成功");
         return mapping.findForward("importProcurement");
     
     }
     
     private void autoToOut(final User user, List<ImportProcurementBean> beanList)
    	        throws MYException
     {
    	 
	        for (ImportProcurementBean item : beanList)
	        {
	            List<BaseBean> baseList = new ArrayList<BaseBean>();

	            OutBean out = new OutBean();

	            out.setStatus(OutConstant.STATUS_SAVE);

	            out.setStafferName(user.getStafferName());

	            out.setStafferId(user.getStafferId());

	            out.setType(OutConstant.OUT_TYPE_INBILL);

	            out.setOutType(OutConstant.OUTTYPE_IN_COMMON);

	            out.setOutTime(TimeTools.now_short());

	            out.setDepartment("采购部");
	            out.setDescription(item.getRemark());

	            // 所在区域
	            out.setLocationId(user.getLocationId());

	            out.setCustomerName(item.getUnitName());
	            out.setCustomerId(item.getUnitId());
	            // 目的仓库通过仓区自动获取
	            out.setLocation(item.getLocationId());

	          //2014/12/16 根据实际入库数量计算金额
	            BigDecimal valDel = new BigDecimal("0.00");
	            valDel = new BigDecimal(item.getProductNum()).multiply(item.getProductCost());
	            valDel = valDel.setScale(2, BigDecimal.ROUND_HALF_UP);
	            out.setTotal(valDel.doubleValue());

	            out.setInway(OutConstant.IN_WAY_NO);

//	            out.setDutyId(each.getDutyId());

	            // 管理类型
	            out.setMtype(StockConstant.MANAGER_TYPE_COMMON);
	            out.setOutType(OutConstant.OUTTYPE_IN_COMMON);

	            out.setHasConfirm(1);
	            
	            BaseBean baseBean = new BaseBean();

//	            baseBean.setValue(item.getTotal());
	            baseBean.setLocationId(out.getLocation());

	            //2014/12/14 入库单根据实际入库数量分批次生成
	            baseBean.setAmount(item.getProductNum());
//	            baseBean.setAmount(item.getAmount());

	            baseBean.setProductName(item.getProductName());
	            baseBean.setUnit("套");
	            baseBean.setPrice(item.getProductCost().doubleValue());
//	            baseBean.setValue(item.getTotal()); 
	            
	            baseBean.setValue(valDel.doubleValue());

//	            baseBean.setShowId(item.getShowId());

	            baseBean.setCostPrice(item.getProductCost().doubleValue());

	            baseBean.setMtype(StockConstant.MANAGER_TYPE_COMMON);

	            baseBean.setProductId(item.getProductId());
//	            baseBean.setCostPriceKey(StorageRelationHelper.getPriceKey(item.getPrice()));

	            //#545
//	            if (this.isVirtualProduct(item.getProductId())){
//	                baseBean.setVirtualPrice(item.getPrice());
//	                baseBean.setVirtualPriceKey(StorageRelationHelper.getPriceKey(baseBean
//	                        .getVirtualPrice()));
//	            } else{
//	                baseBean.setVirtualPrice(0);
//	                baseBean.setVirtualPriceKey(StorageRelationHelper.getPriceKey(baseBean
//	                        .getVirtualPrice()));
//	            }

                baseBean.setOwnerName("公共");
                baseBean.setOwner("0");
	                
//	                if(product.getSailType()==ProductConstant.SAILTYPE_REPLACE)
//	                {
//	                	product.setSailPrice(each.getPrice());//采购商品的结算价更新为此张采购单的成本价
////	                	each.setPrice(productVo.getSailPrice());
//	                	productDAO.updateEntityBean(product);
//	                }
//	                stockItemDAO.saveEntityBean(each);

	            // 来源于入库的仓区
	            baseBean.setDepotpartId(item.getDepotpartId());
	            baseBean.setDepotpartName(item.getDepotpartName());

	            // 成本
//	            baseBean.setDescription(String.valueOf(item.getPrice()));
	            
	            baseBean.setInputRate(0.0d);
	            
	            baseList.add(baseBean);

	            out.setBaseList(baseList);

	            // CORE 采购单生成入库单
	            String fullId = outManager.coloneOutWithAffair(out, user,
	                StorageConstant.OPR_STORAGE_OUTBILLIN);
	            System.out.println(fullId);
//	            fechProductListenerTaxGlueImpl.onFechProduct(user, bean, each, out);
	        
	        }
     }

     public UnitDAO getUnitDAO() {
		return unitDAO;
	}

	public void setUnitDAO(UnitDAO unitDAO) {
		this.unitDAO = unitDAO;
	}

	public FechProductListener getFechProductListenerTaxGlueImpl() {
		return fechProductListenerTaxGlueImpl;
	}

	public void setFechProductListenerTaxGlueImpl(FechProductListener fechProductListenerTaxGlueImpl) {
		this.fechProductListenerTaxGlueImpl = fechProductListenerTaxGlueImpl;
	}

	public CustomerMainDAO getCustomerMainDAO() {
         return customerMainDAO;
     }

     public void setCustomerMainDAO(CustomerMainDAO customerMainDAO) {
         this.customerMainDAO = customerMainDAO;
     }

     public OutImportDAO getOutImportDAO()
     {
         return outImportDAO;
     }

     public void setOutImportDAO(OutImportDAO outImportDAO)
     {
         this.outImportDAO = outImportDAO;
     }

     public OutImportManager getOutImportManager()
     {
         return outImportManager;
     }

     public void setOutImportManager(OutImportManager outImportManager)
     {
         this.outImportManager = outImportManager;
     }

     public OutImportResultDAO getOutImportResultDAO()
     {
         return outImportResultDAO;
     }

     public void setOutImportResultDAO(OutImportResultDAO outImportResultDAO)
     {
         this.outImportResultDAO = outImportResultDAO;
     }

     public ReplenishmentDAO getReplenishmentDAO()
     {
         return replenishmentDAO;
     }

     public void setReplenishmentDAO(ReplenishmentDAO replenishmentDAO)
     {
         this.replenishmentDAO = replenishmentDAO;
     }

     public StorageRelationManager getStorageRelationManager()
     {
         return storageRelationManager;
     }

     public void setStorageRelationManager(
             StorageRelationManager storageRelationManager)
     {
         this.storageRelationManager = storageRelationManager;
     }

     public ProductDAO getProductDAO()
     {
         return productDAO;
     }

     public void setProductDAO(ProductDAO productDAO)
     {
         this.productDAO = productDAO;
     }

     public OutImportLogDAO getOutImportLogDAO()
     {
         return outImportLogDAO;
     }

     public void setOutImportLogDAO(OutImportLogDAO outImportLogDAO)
     {
         this.outImportLogDAO = outImportLogDAO;
     }

     public StafferDAO getStafferDAO()
     {
         return stafferDAO;
     }

     public void setStafferDAO(StafferDAO stafferDAO)
     {
         this.stafferDAO = stafferDAO;
     }

     public StafferVSCustomerDAO getStafferVSCustomerDAO()
     {
         return stafferVSCustomerDAO;
     }

     public void setStafferVSCustomerDAO(StafferVSCustomerDAO stafferVSCustomerDAO)
     {
         this.stafferVSCustomerDAO = stafferVSCustomerDAO;
     }

     public CiticBranchDAO getCiticBranchDAO()
     {
         return citicBranchDAO;
     }

     public void setCiticBranchDAO(CiticBranchDAO citicBranchDAO)
     {
         this.citicBranchDAO = citicBranchDAO;
     }

     public CiticVSStafferDAO getCiticVSStafferDAO()
     {
         return citicVSStafferDAO;
     }

     public void setCiticVSStafferDAO(CiticVSStafferDAO citicVSStafferDAO)
     {
         this.citicVSStafferDAO = citicVSStafferDAO;
     }

     /**
      * @return the expressDAO
      */
     public ExpressDAO getExpressDAO()
     {
         return expressDAO;
     }

     /**
      * @param expressDAO the expressDAO to set
      */
     public void setExpressDAO(ExpressDAO expressDAO)
     {
         this.expressDAO = expressDAO;
     }

     /**
      * @return the batchApproveDAO
      */
     public BatchApproveDAO getBatchApproveDAO()
     {
         return batchApproveDAO;
     }

     /**
      * @param batchApproveDAO the batchApproveDAO to set
      */
     public void setBatchApproveDAO(BatchApproveDAO batchApproveDAO)
     {
         this.batchApproveDAO = batchApproveDAO;
     }

     /**
      * @return the batchSwatchDAO
      */
     public BatchSwatchDAO getBatchSwatchDAO()
     {
         return batchSwatchDAO;
     }

     /**
      * @param batchSwatchDAO the batchSwatchDAO to set
      */
     public void setBatchSwatchDAO(BatchSwatchDAO batchSwatchDAO)
     {
         this.batchSwatchDAO = batchSwatchDAO;
     }

     /**
      * @return the consignDAO
      */
     public ConsignDAO getConsignDAO()
     {
         return consignDAO;
     }

     /**
      * @param consignDAO the consignDAO to set
      */
     public void setConsignDAO(ConsignDAO consignDAO)
     {
         this.consignDAO = consignDAO;
     }

     public DepotDAO getDepotDAO()
     {
         return depotDAO;
     }

     public void setDepotDAO(DepotDAO depotDAO)
     {
         this.depotDAO = depotDAO;
     }

     public DepotpartDAO getDepotpartDAO()
     {
         return depotpartDAO;
     }

     public void setDepotpartDAO(DepotpartDAO depotpartDAO)
     {
         this.depotpartDAO = depotpartDAO;
     }

     public OutDAO getOutDAO()
     {
         return outDAO;
     }

     public void setOutDAO(OutDAO outDAO)
     {
         this.outDAO = outDAO;
     }

     public ProvinceDAO getProvinceDAO()
     {
         return provinceDAO;
     }

     public void setProvinceDAO(ProvinceDAO provinceDAO)
     {
         this.provinceDAO = provinceDAO;
     }

     public CityDAO getCityDAO()
     {
         return cityDAO;
     }

     public void setCityDAO(CityDAO cityDAO)
     {
         this.cityDAO = cityDAO;
     }

     public AreaDAO getAreaDAO()
     {
         return areaDAO;
     }

     public void setAreaDAO(AreaDAO areaDAO)
     {
         this.areaDAO = areaDAO;
     }

     /**
      * @return the baseDAO
      */
     public BaseDAO getBaseDAO()
     {
         return baseDAO;
     }

     /**
      * @param baseDAO the baseDAO to set
      */
     public void setBaseDAO(BaseDAO baseDAO)
     {
         this.baseDAO = baseDAO;
     }

     public UserDAO getUserDAO() {
         return userDAO;
     }

     public void setUserDAO(UserDAO userDAO) {
         this.userDAO = userDAO;
     }

     public DutyDAO getDutyDAO() {
         return dutyDAO;
     }

     public void setDutyDAO(DutyDAO dutyDAO) {
         this.dutyDAO = dutyDAO;
     }

     public StorageDAO getStorageDAO() {
         return storageDAO;
     }

     public void setStorageDAO(StorageDAO storageDAO) {
         this.storageDAO = storageDAO;
     }

     public StorageRelationDAO getStorageRelationDAO() {
         return storageRelationDAO;
     }

     public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO) {
         this.storageRelationDAO = storageRelationDAO;
     }

     /**
      * @return the bankSailDAO
      */
     public BankSailDAO getBankSailDAO()
     {
         return bankSailDAO;
     }

     /**
      * @param bankSailDAO the bankSailDAO to set
      */
     public void setBankSailDAO(BankSailDAO bankSailDAO)
     {
         this.bankSailDAO = bankSailDAO;
     }

     /**
      * @return the estimateProfitDAO
      */
     public EstimateProfitDAO getEstimateProfitDAO()
     {
         return estimateProfitDAO;
     }

     /**
      * @param estimateProfitDAO the estimateProfitDAO to set
      */
     public void setEstimateProfitDAO(EstimateProfitDAO estimateProfitDAO)
     {
         this.estimateProfitDAO = estimateProfitDAO;
     }

     public PriceConfigDAO getPriceConfigDAO() {
         return priceConfigDAO;
     }

     public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO) {
         this.priceConfigDAO = priceConfigDAO;
     }

     public InvoiceinsDAO getInvoiceinsDAO() {
         return invoiceinsDAO;
     }

     public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO) {
         this.invoiceinsDAO = invoiceinsDAO;
     }

     public PriceConfigManager getPriceConfigManager() {
         return priceConfigManager;
     }

     public void setPriceConfigManager(PriceConfigManager priceConfigManager) {
         this.priceConfigManager = priceConfigManager;
     }

     public OutManager getOutManager() {
         return outManager;
     }

     public void setOutManager(OutManager outManager) {
         this.outManager = outManager;
     }

     public SailConfigManager getSailConfigManager() {
         return sailConfigManager;
     }

     public void setSailConfigManager(SailConfigManager sailConfigManager) {
         this.sailConfigManager = sailConfigManager;
     }

     public EnumDAO getEnumDAO() {
         return enumDAO;
     }

     public void setEnumDAO(EnumDAO enumDAO) {
         this.enumDAO = enumDAO;
     }

     public ProductImportDAO getProductImportDAO() {
         return productImportDAO;
     }

     public void setProductImportDAO(ProductImportDAO productImportDAO) {
         this.productImportDAO = productImportDAO;
     }

     public PresentFlagDAO getPresentFlagDAO() {
         return presentFlagDAO;
     }

     public void setPresentFlagDAO(PresentFlagDAO presentFlagDAO) {
         this.presentFlagDAO = presentFlagDAO;
     }

	public StockManager getStockManager() {
		return stockManager;
	}

	public void setStockManager(StockManager stockManager) {
		this.stockManager = stockManager;
	}
     
 }
