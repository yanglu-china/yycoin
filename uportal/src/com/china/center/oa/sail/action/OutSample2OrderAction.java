package com.china.center.oa.sail.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import com.center.china.osgi.publics.User;
import com.center.china.osgi.publics.file.read.ReadeFileFactory;
import com.center.china.osgi.publics.file.read.ReaderFile;
import com.china.center.actionhelper.common.KeyConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.wrap.Sample2OrderWrap;
import com.china.center.tools.RequestDataStream;
import com.china.center.tools.StringTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.oa.sail.manager.OutSample2OrderManager;

import com.china.center.oa.publics.dao.UserDAO;

public class OutSample2OrderAction  extends DispatchAction{
	
	protected final Log _logger = LogFactory.getLog(getClass());

	protected UserDAO userDAO = null;

	protected OutDAO outDAO = null;

	protected BaseDAO baseDAO = null;
	
	protected OutSample2OrderManager outSample2OrderManager = null;
	
	/**
	 * #952 领样转订单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ActionForward batchSample2Order(ActionMapping mapping, ActionForm form,
											  HttpServletRequest request, HttpServletResponse response)
			throws ServletException
	{
		final int totalColumns = 6;

		RequestDataStream rds = new RequestDataStream(request);

		User user = (User) request.getSession().getAttribute("user");

		boolean importError = false;

		List<Sample2OrderWrap> importItemList = new ArrayList<Sample2OrderWrap>();

		StringBuilder builder = new StringBuilder();
		try
		{
			rds.parser();
		}
		catch (Exception e1)
		{
			_logger.error(e1, e1);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward("batchSample2Order");
		}

		if ( !rds.haveStream())
		{
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "解析失败");

			return mapping.findForward("batchSample2Order");
		}

		ReaderFile reader = ReadeFileFactory.getXLSReader();

		try
		{
			reader.readFile(rds.getUniqueInputStream());

			Map<String, Sample2OrderWrap> summaryMap = new HashMap<String, Sample2OrderWrap>();

			//转新订单号对应累计转数量
			Map<String, Sample2OrderWrap> destId2AmountMap = new HashMap<String, Sample2OrderWrap>();

			while (reader.hasNext())
			{
				String[] obj = StringUtils.fillObj((String[])reader.next(), totalColumns);

				// 第一行忽略
				if (reader.getCurrentLineNumber() == 1)
				{
					continue;
				}

				int currentNumber = reader.getCurrentLineNumber();

				for(int i=0;i<totalColumns;i++){
					if (StringTools.isNullOrNone(obj[i]))
					{
						builder
								.append("第[" + currentNumber + "]行数据不完整。")
								.append("<br>");
						importError = true;
						continue;
					}
				}

				if (obj.length >= 2 )
				{
					Sample2OrderWrap bean = new Sample2OrderWrap();

					// 类型
					String type = obj[0].trim();
					bean.setTypeName(type);

					//领样单号
				    String outId = obj[1].trim();
				    bean.setSampleId(outId);
					OutBean out = this.outDAO.find(outId);
					List<BaseBean> baseBeans = this.baseDAO.queryEntityBeansByCondition("where outId = ?", outId);

					out.setBaseList(baseBeans);

					bean.setSampleOut(out);
					
					//总数
					int totalCount = 0;
					for(BaseBean baseBean : baseBeans){
						totalCount+= baseBean.getAmount();
					}

					if (out == null){
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("领样单号不存在:"+outId)
								.append("<br>");

						importError = true;
					} else{
						int status = out.getStatus();
						if(status != OutConstant.STATUS_SEC_PASS){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("只有已发货的订单可以操作")
									.append("<br>");

							importError = true;
						}
					}

					//被转业务员
					String srcStaffer = obj[2].trim();
					bean.setSrcStaffer(srcStaffer);

					if(!srcStaffer.equals(out.getStafferName())){
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("领样单业务员不一致")
								.append("<br>");
						importError = true;
					}

					//转新订单
					String destOrderId = obj[3].trim();
					bean.setDestOrderId(destOrderId);
					OutBean destOut = this.outDAO.find(destOrderId);
					List<BaseBean> destBaseBeans = this.baseDAO.queryEntityBeansByCondition("where outId = ?", destOrderId);

					bean.setDestOut(destOut);
					
					if (destOut == null){
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("新订单号不存在:"+destOrderId)
								.append("<br>");

						importError = true;
					} else{
						if(destBaseBeans==null || destBaseBeans.size() == 0){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("新订单号找不到对应的base项:"+destOrderId)
									.append("<br>");

							importError = true;
						}

						bean.setDestOrderAmount(baseBeans.get(0).getAmount());

						if(!destBaseBeans.get(0).getProductName().equals(baseBeans.get(0).getProductName())){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("转新订单和领样单号对应的品名不一致")
									.append("<br>");

							importError = true;
						}
					}

					Sample2OrderWrap sample2OrderWrap = destId2AmountMap.get(destOrderId);
					if(sample2OrderWrap==null){
						destId2AmountMap.put(destOrderId, bean);
					}else{
						sample2OrderWrap.setAmount(sample2OrderWrap.getAmount()+bean.getAmount());
					}

					//转业务员
					String destStaffer = obj[4].trim();
					bean.setDestStaffer(destStaffer);

					if(!destStaffer.equals(destOut.getStafferName())){
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("新订单业务员不一致")
								.append("<br>");
						importError = true;
					}

					//转数量
					String amountStr = obj[5].trim();
					bean.setAmount(Integer.parseInt(amountStr));

					//单行数量验证
					int backCount = this.getBackCount(outId);
					if(bean.getAmount()>(totalCount - backCount)){
						builder
								.append("第[" + currentNumber + "]错误:")
								.append("转订单数量超出最高可转数量："+outId)
								.append("<br>");
						importError = true;
						continue;
					}

					//累计验证
					Sample2OrderWrap wrap = summaryMap.get(outId);
					if(wrap==null){
						summaryMap.put(outId, bean);
					}else{
						wrap.setAmount(wrap.getAmount()+bean.getAmount());
						if(wrap.getAmount()>(totalCount - backCount)){
							builder
									.append("第[" + currentNumber + "]错误:")
									.append("合并数量超出最高可转数量："+outId)
									.append("<br>");
							importError = true;
						}
					}

					importItemList.add(bean);
				}

				//校验对应同一新订单号的累计转数量=新订单的数量
				for(Sample2OrderWrap item : destId2AmountMap.values()){
					if(item.getAmount() != item.getDestOrderAmount()){
						builder
								.append("对应同一新订单号"+item.getDestOrderId()+"的累计转数量("+item.getAmount()+")!=新订单的数量("+item.getDestOrderAmount()+")")
								.append("<br>");
						importError = true;
					}
				}

			}
		}catch (Exception e)
		{
			_logger.error(e, e);

			request.setAttribute(KeyConstant.ERROR_MESSAGE, e.toString());

			return mapping.findForward("batchSample2Order");
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

			return mapping.findForward("batchSample2Order");
		}

		try
		{
			this.outSample2OrderManager.batchHandle(importItemList, user);
            request.setAttribute(KeyConstant.MESSAGE,
                    "领样转订单申请成功,共生成" + importItemList.size() +"张订单");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute(KeyConstant.ERROR_MESSAGE, "导入出错:"+ e.getMessage());

			return mapping.findForward("batchSample2Order");
		}
		return mapping.findForward("batchSample2Order");
	}

	private int getBackCount(String outId){
		int backCount = 0;

		ConditionParse con = new ConditionParse();
		con.clear();

		con.addWhereStr();

		con.addCondition("OutBean.refOutFullId", "=", outId);

		con.addIntCondition("OutBean.type", "=", OutConstant.OUT_TYPE_INBILL);

		con.addCondition("and OutBean.status !=2");

		con.addIntCondition("OutBean.outType", "=", OutConstant.OUTTYPE_IN_SWATCH);

		List<OutBean> refBuyList = this.outDAO.queryEntityBeansByCondition(con);

		for (OutBean outBean : refBuyList)
		{
			List<BaseBean> list = baseDAO.queryEntityBeansByFK(outBean
					.getFullId());

			for(BaseBean baseBean : list){
				backCount += baseBean.getAmount();
			}
		}

		return backCount;
	}

}
