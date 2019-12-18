package com.china.center.oa.payorder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.oa.finance.manager.payorder.sf.jaxobject.req.SfOrderService;
import com.china.center.oa.finance.manager.payorder.sf.jaxobject.req.SfRequestService;
import com.china.center.oa.finance.manager.payorder.sf.jaxobject.req.SfRequestServiceBody;
import com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp.SfResponseOrderResponse;
import com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp.SfResponseRlsDetail;
import com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp.SfResponseRlsInfo;
import com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp.SfResponseService;
import com.china.center.oa.finance.manager.payorder.sf.jaxobject.resp.SfResponseServiceBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sf.csim.express.service.CallExpressServiceTools;
import com.sf.dto.RlsInfoDto;
import com.sf.dto.WaybillDto;
import com.sf.util.Base64ImageTools;
import com.sf.util.MyJsonUtil;

public class SFPrintUtil {
	
	private final Log sfLog = LogFactory.getLog("taobao");
	
	//此处替换为您在丰桥平台获取的顾客编码
	private final String clientCode="ZX_TcMqP";
	//prod private final String clientCode="ZX_TcMqP";
	
	
	//此处替换为您在丰桥平台获取的校验码
	private final String checkword="pAS2zS3iYywfAQnVCZJLRsP85vFB74cj";
	
	private final String sf_custid = "7551234567";
	
	private final String reqURL="https://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";
	
	/**
	 * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【二联单】
	 */
	String url9 = "http://101.37.13.154:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=image";
	
	
	public String toPrint(Map<String,String> paramMap) throws Exception {
		
		SfRequestService sfRequestService = new SfRequestService();
		
		SfRequestServiceBody body = new SfRequestServiceBody();
		
		sfRequestService.setService("OrderService");
		sfRequestService.setLang("zh-CN");
		
		SfOrderService orderService = new SfOrderService();
		orderService.setOrderid(paramMap.get("orderid"));
		orderService.setJ_company("永银文化发展集团有限公司");
		orderService.setJ_contact("乔纯维");
		orderService.setJ_tel("13951084037");
		orderService.setJ_province("江苏省");
		orderService.setJ_city("南京市");
		orderService.setJ_address("南京市江宁区秣陵街道将军大道558号中航工业金城(永银文化)");
		
		orderService.setD_company(paramMap.get("dcompany"));
		orderService.setD_contact(paramMap.get("dcontact"));
		orderService.setD_tel(paramMap.get("dtel"));
		orderService.setD_province(paramMap.get("dprovince"));
		orderService.setD_city(paramMap.get("dcity"));
		orderService.setD_address(paramMap.get("daddress"));
		
		orderService.setCustid(sf_custid);
		//寄方付
		orderService.setPay_method("1");
		//顺丰标快
		orderService.setExpress_type("1");
		
		body.setSfOrderService(orderService);
		sfRequestService.setHead(clientCode);
		sfRequestService.setBody(body);
		
		String reqXml = marshallerRequest(sfRequestService);
	    
		sfLog.info("请求报文："+reqXml);
        String respXml= CallExpressServiceTools.callSfExpressServiceByCSIM(reqURL, reqXml, clientCode, checkword);
      
        String sfNumber  = "";
		 if (respXml != null) {
			 sfLog.info("--------------------------------------");
			 sfLog.info("返回报文: "+ respXml);
			 sfLog.info("--------------------------------------");
             
             SfResponseService responseService = (SfResponseService) unMarShallerResp(respXml,new SfResponseService());
             String head = responseService.getHead();
             if(StringUtils.isNotEmpty(head) && "ok".equalsIgnoreCase(head))
             {
            	 SfResponseServiceBody responseBody = responseService.getBody();
            	 if(responseBody != null)
            	 {
            		 SfResponseOrderResponse orderResponse = responseBody.getOrderResponse();
            		 if(orderResponse != null)
            		 {
            			 SfResponseRlsInfo rlsInfo = orderResponse.getRslInfo();
            			 if(rlsInfo != null)
            			 {
            				 String filePath = paramMap.get("filePath");
            				 SfResponseRlsDetail rlsDetail = rlsInfo.getRlsDetail();
            				 sfNumber = orderResponse.getMailno();
            				 WayBillPrinterTools(orderResponse,rlsDetail,orderService,filePath);
            			 }
            		 }
            	 }
             }
             else
             {
            	 String error = responseService.getError().getValue();
            	 throw new Exception("顺丰面单打印出错:" +  error);
             }
             
         }
		 return sfNumber;
	}
	
	
	/**
	 * 返回顺丰单号
	 * @param orderResponse
	 * @param rlsDetail
	 * @param orderService
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public void WayBillPrinterTools(SfResponseOrderResponse orderResponse,SfResponseRlsDetail rlsDetail,
											SfOrderService orderService, String filePath) throws Exception {

		/********* 2联150 丰密运单 **************/
		/**
		 * 调用打印机 不弹出窗口 适用于批量打印【二联单】
		 */
		String url7 = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=noAlertPrint";
		/**
		 * 调用打印机 弹出窗口 可选择份数 适用于单张打印【二联单】
		 */
		String url8 = "http://localhost:4040/sf/waybill/print?type=V2.0.FM_poster_100mm150mm&output=print";


		/********* 3联210 丰密运单 **************/
		/**
		 * 调用打印机 不弹出窗口 适用于批量打印【三联单】
		 */
		String url10 = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=noAlertPrint";
		/**
		 * 调用打印机 弹出窗口 可选择份数 适用于单张打印【三联单】
		 */
		String url11 = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=print";

		/**
		 * 直接输出图片的BASE64编码字符串 可以使用html标签直接转换成图片【三联单】
		 */
		String url12 = "http://localhost:4040/sf/waybill/print?type=V3.0.FM_poster_100mm210mm&output=image";

		// 根据业务需求确定请求地址
		String reqURL = url9;

		// 电子面单顶部是否需要logo
		boolean topLogo = true;// true 需要logo false 不需要logo
		if (reqURL.contains("V2.0") && topLogo) {
			reqURL = reqURL.replace("V2.0", "V2.1");
		}

		if (reqURL.contains("V3.0") && topLogo) {
			reqURL = reqURL.replace("V3.0", "V3.1");
		}

		sfLog.info(reqURL);

		/** 注意 需要使用对应业务场景的url **/
		URL myURL = new URL(reqURL);

		// 其中127.0.0.1:4040为打印服务部署的地址（端口如未指定，默认为4040），
		// type为模板类型（支持两联、三联，尺寸为100mm*150mm和100mm*210mm，type为poster_100mm150mm和poster_100mm210mm）
		// A5 poster_100mm150mm A5 poster_100mm210mm
		// output为输出类型,值为print或image，如不传，
		// 默认为print（print 表示直接打印，image表示获取图片的BASE64编码字符串）
		// V2.0/V3.0模板顶部是带logo的 V2.1/V3.1顶部不带logo

		HttpURLConnection httpConn = (HttpURLConnection) myURL.openConnection();
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		httpConn.setUseCaches(false);
		httpConn.setRequestMethod("POST");
		// httpConn.setRequestProperty("Content-Type",
		// "application/json;charset=utf-8");
		httpConn.setRequestProperty("Content-Type", "text/plain;charset=utf-8");

		httpConn.setConnectTimeout(5000);
		httpConn.setReadTimeout(3 * 5000);

		List<WaybillDto> waybillDtoList = new ArrayList<WaybillDto>();
		WaybillDto dto = new WaybillDto();

		// 这个必填
		dto.setAppId(clientCode);// 对应clientCode
		dto.setAppKey(checkword);// 对应checkWord

		dto.setMailNo(orderResponse.getMailno());
		// dto.setMailNo("SF7551234567890,SF2000601520988,SF2000601520997");//子母单方式

		// 签回单号 签单返回服务 会打印两份快单 其中第二份作为返寄的单
		// dto.setReturnTrackingNo("SF1060081717189");

		// 收件人信息
		dto.setConsignerProvince(orderService.getD_province());
		dto.setConsignerCity(orderService.getD_city());
//		dto.setConsignerCounty("南山区");
		dto.setConsignerAddress(orderService.getD_address()); // 详细地址建议最多30个字 字段过长影响打印效果
		dto.setConsignerCompany(orderService.getD_company());
		dto.setConsignerMobile(orderService.getD_tel());
		dto.setConsignerName(orderService.getD_contact());
//		dto.setConsignerShipperCode("518052");
		dto.setConsignerTel(orderService.getD_tel());

		// 寄件人信息
		dto.setDeliverProvince(orderService.getJ_province());
		dto.setDeliverCity(orderService.getJ_city());
//		dto.setDeliverCounty("拱墅区");
		dto.setDeliverCompany(orderService.getJ_company());
		dto.setDeliverAddress(orderService.getJ_address());// 详细地址建议最多30个字 字段过长影响打印效果
		dto.setDeliverName(orderService.getJ_contact());
		dto.setDeliverMobile(orderService.getJ_tel());
//		dto.setDeliverShipperCode("310000");
		dto.setDeliverTel(orderService.getJ_tel());

		dto.setDestCode(orderResponse.getDestcode());// 目的地代码 参考顺丰地区编号
		dto.setZipCode(orderResponse.getOrigincode());// 原寄地代码 参考顺丰地区编号

		// 快递类型
		// 1 ：标准快递 2.顺丰特惠 3： 电商特惠 5：顺丰次晨 6：顺丰即日 7.电商速配 15：生鲜速配
		dto.setExpressType(1);

		// COD代收货款金额,只需填金额, 单位元- 此项和月结卡号绑定的增值服务相关
//		dto.setCodValue("0");

//		dto.setInsureValue("501");// 声明货物价值的保价金额,只需填金额,单位元
		dto.setMonthAccount(sf_custid);// 月结卡号
		dto.setPayMethod(1);//

		/** 丰密运单相关 **/

		List<RlsInfoDto> rlsInfoDtoList = new ArrayList<RlsInfoDto>();
		RlsInfoDto rlsMain = new RlsInfoDto();
		// 主运单号
		rlsMain.setWaybillNo(dto.getMailNo());
		rlsMain.setDestRouteLabel(rlsDetail.getDestRouteLabel());
		rlsMain.setPrintIcon(rlsDetail.getPrintIcon());
		rlsMain.setProCode(rlsDetail.getProCode());
		rlsMain.setAbFlag("A");
		rlsMain.setXbFlag("XB");
		rlsMain.setCodingMapping(rlsDetail.getCodingMapping());
		rlsMain.setCodingMappingOut("1A");
		rlsMain.setDestTeamCode(rlsDetail.getDestTeamCode());
		rlsMain.setSourceTransferCode(rlsDetail.getDestTransferCode());
		// 对应下订单设置路由标签返回字段twoDimensionCode 该参
		rlsMain.setQrcode(rlsDetail.getTwoDimensionCode());
		rlsInfoDtoList.add(rlsMain);

//		if (dto.getReturnTrackingNo() != null) {
//			RlsInfoDto rlsBack = new RlsInfoDto();
//			// 签回运单号Z
//			rlsBack.setWaybillNo(dto.getReturnTrackingNo());
//			rlsBack.setDestRouteLabel("021WTF");
//			rlsBack.setPrintIcon("11110000");
//			rlsBack.setProCode("T4");
//			rlsBack.setAbFlag("A");
//			rlsBack.setXbFlag("XB");
//			rlsBack.setCodingMapping("1A");
//			rlsBack.setCodingMappingOut("F33");
//			rlsBack.setDestTeamCode("87654321");
//			rlsBack.setSourceTransferCode("755WE-571A3");
//			// 对应下订单设置路由标签返回字段twoDimensionCode 该参
//			rlsBack.setQRCode("MMM={'k1':'21WT','k2':'755WE','k3':'','k4':'T4','k5':'SF1060081717189','k6':''}");
//			rlsInfoDtoList.add(rlsBack);
//		}

		// 设置丰密运单必要参数
		dto.setRlsInfoDtoList(rlsInfoDtoList);
		// 客户个性化Logo 必须是个可以访问的图片本地路径地址或者互联网图片地址
		// dto.setCustLogo("D:\\ibm.jpg");

		// 备注相关
//		dto.setMainRemark("");
//		dto.setChildRemark("");
//		dto.setReturnTrackingRemark("");

		// 加密项
		dto.setEncryptCustName(true);// 加密寄件人及收件人名称
		dto.setEncryptMobile(true);// 加密寄件人及收件人联系手机

//		CargoInfoDto cargo1 = new CargoInfoDto();
//		cargo1.setCargo("苹果7S");
//		cargo1.setCargoCount(1);
//		cargo1.setCargoUnit("件");
//		cargo1.setSku("00015645");
//		cargo1.setRemark("手机贵重物品 小心轻放");
//
//		List<CargoInfoDto> cargoInfoList = new ArrayList<CargoInfoDto>();
//		cargoInfoList.add(cargo1);
//
//		dto.setCargoInfoDtoList(cargoInfoList);

		waybillDtoList.add(dto);

		sfLog.info("请求参数： " + MyJsonUtil.object2json(waybillDtoList));

		ObjectMapper objectMapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter, waybillDtoList);

		httpConn.getOutputStream().write(stringWriter.toString().getBytes("UTF-8"));

		httpConn.getOutputStream().flush();
		httpConn.getOutputStream().close();
		InputStream in = httpConn.getInputStream();

		BufferedReader in2 = new BufferedReader(new InputStreamReader(in));

		String y = "";

		String strImg = "";
		while ((y = in2.readLine()) != null) {

			strImg = y.substring(y.indexOf("[") + 1, y.length() - "]".length() - 1);
			if (strImg.startsWith("\"")) {
				strImg = strImg.substring(1, strImg.length());
			}
			if (strImg.endsWith("\"")) {
				strImg = strImg.substring(0, strImg.length() - 1);
			}

		}

		// 将换行全部替换成空
		strImg = strImg.replace("\\n", "");
		// System.out.println(strImg);

//		List<String> fileNameList = new ArrayList<String>();

		if (strImg.contains("\",\"")) {
			// 如子母单及签回单需要打印两份或者以上
			String[] arr = strImg.split("\",\"");
			/** 输出图片到本地 支持.jpg、.png格式 **/
			for (int i = 0; i < arr.length; i++) {
				String fileName = filePath + "/" + orderService.getOrderid() + ".jpg";
				Base64ImageTools.generateImage(arr[i].toString(), fileName);
//				fileNameList.add(fileName);

			}
		} else {
			String fileName = filePath + "/" + orderService.getOrderid() + ".jpg";
			Base64ImageTools.generateImage(strImg, fileName);
//			fileNameList.add(fileName);

		}
//		return fileNameList;
//		writeImageFont(files);
		// 如需调用本地打印机(非服务端打印机请使用url9/url12 并且取消以下注释)
//		int high = 0;
//		if (reqURL.contains("image") && !files.isEmpty()) {
//			if (reqURL.contains("V2")) {
//				high = 150;
//			} else {
//				high = 210;
//			}
//			for (String fileName : files) {
//				PrintUtil.drawImage(fileName, high, false);// false为不弹出打印框
//			}
//		}
	}
	
	
	/**
	   *  构建请求xml 
	 * @param <T>
	 * @param xmlObj
	 * @param clazz
	 * @return
	 */
	public static String marshallerRequest(Object xmlObj)
	{
		JAXBContext context;
		StringWriter writer = new StringWriter();
		try {
			context = JAXBContext.newInstance(xmlObj.getClass());
			Marshaller marshaller = context.createMarshaller();
			//编码格式
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	        // 是否格式化生成的xml串
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        // 是否省略xm头声明信息
	        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
	        
          marshaller.marshal(xmlObj, writer);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return writer.toString();
		
	}
	
	/**
	 * 解析xml to bean
	 * @param xml
	 * @param obj
	 * @return
	 */
	public static Object unMarShallerResp(String xml,Object obj)
	{
		Object _clazz = null;
		try {
			JAXBContext contextResult = JAXBContext.newInstance(obj.getClass());
			Unmarshaller unmarshaller = contextResult.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			_clazz = unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return _clazz;
	}
}
