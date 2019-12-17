package com.china.center.oa.finance.manager.payorder;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.AesCode;
import com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformLocator;
import com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice.ErpPlatformSoap11BindingStub;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankDownLoadUnionBankBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankHead;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryAccList;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryAccListBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryCrudTlBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryCurdTl;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryHisBal;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryHisBalBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryHisDtl;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryHisDtlBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryTransfer;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryTransferBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankTransfer;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankTransferBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankDownLoadUnionBankBodyResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankDownLoadUnionBankResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankHeadResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryAccListBodyResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryAccListLoopData;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryAccListLoopResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryCrudTlBodyResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryCrudTlLoopData;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryCrudTlLoopResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryCrudTlTotalResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryHisBalBodyResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryHisBalLoopData;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryHisBalLoopResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryHisDtlBodyResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryHisDtlLoopData;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryHisDtlLoopResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryHisDtlTotalResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryTransferBodyResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryTransferResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankTranferResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankTransferBodyResp;

/**
 * 宁波银行资金平台付款类
 * @author Administrator
 *
 */
public class NbBankPayImpl {
	
	private final Log _logger = LogFactory.getLog("taobao");
	/**
	 * erp系统代码
	 */
	//test
	 private static final String erpSysCode="erpnjg002";
	 //prod
	//private static final String erpSysCode="ERP001";
	
	/**
	 * 客户编号 
	 */
	 //test
	private static final String custNo = "0000070401";
	//prod
	//private static final String custNo = "0000243857";
	
//	test地址
	private static final String NbBankPayUrl = "http://101.37.13.154:8090/BisOutPlatform/services/erpPlatform?wsdl";
//	内网地址
//	private static final String NbBankPayUrl = "http://10.10.10.168:8090/BisOutPlatform/services/erpPlatform?wsdl";
//	外网地址
	//private static final String NbBankPayUrl = "http://218.94.130.10:6868/BisOutPlatform/services/erpPlatform?wsdl";
	
	
	
	/**
	 * 转账申请接口
	 */
	public Map<String,String> erpTransfer(Map<String,String> map)
	{
		_logger.info("start erpTransfer");
		Map<String,String> retMap = new HashMap<String,String>();
		try {
			URL url = new URL(NbBankPayUrl);
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			NbBankTransferBody body = new NbBankTransferBody();
			//请求头
			NbBankHead head = new NbBankHead();
			head.setCustNo(custNo);
			head.setErpSysCode(erpSysCode);
			head.setTradeName("ERP_TRANSFER");
			//请求body
			NbBankTransfer transfer = new NbBankTransfer();
			transfer.setPayerAccNo(map.get("payerAccNo"));
			transfer.setPayerCorpCode("1000");
			transfer.setPayerCorpName("永银文化发展集团有限公司");
//			transfer.setPayerAccNo("70170122000004786");
//			transfer.setPayerCorpCode("1000");
//			transfer.setPayerCorpName("798931559");
			
//			transfer.setPayerAccNo("952A9997220008092");
//			transfer.setPayerCorpCode("1000");
//			transfer.setPayerCorpName("798931559");
//			transfer.setErpPayerCorpCode("");
//			transfer.setPayeeAccNo("80010122000162044");
//			transfer.setPayeeAccName("575338758");
			transfer.setPayeeBankName(map.get("payeeBankName"));
			transfer.setPayeeBankCode(map.get("payeeBankCode"));
//			transfer.setPayeeProv("浙江省");
//			transfer.setPayeeCity("绍兴市");
			transfer.setPayeeAccNo(map.get("payeeAccNo"));
			transfer.setPayeeAccName(map.get("payeeAccName"));
//			transfer.setPayeeBankName("上海浦东发展银行南京分行");
//			transfer.setPayeeBankCode("");
//			transfer.setPayeeProv("江苏省");
//			transfer.setPayeeCity("南京市");
			
			
			transfer.setPayMoney(map.get("payMoney"));
			transfer.setAreaSign(map.get("areaSign"));
			transfer.setDifSign(map.get("difSign"));
			transfer.setPayPurpose(map.get("payPurpose"));
			transfer.setErpReqNo(map.get("erpReqNo"));
			transfer.setErpReqUser("");
//			transfer.setAllowEditPayeeAcc("0");
//			transfer.setAllowEditPayMoney("0");
//			transfer.setAllowEditPayerAcc("1");
//			transfer.setReverse1("");
//			transfer.setReverse2("");
//			transfer.setReverse3("");
//			transfer.setReverse4("");
			transfer.setReverse5("1");
			
			
			body.setHead(head);
			body.setTransfer(transfer);
			JAXBContext context = JAXBContext.newInstance(NbBankTransferBody.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //编码格式
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// 是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// 是否省略xm头声明信息
            StringWriter writer = new StringWriter();
            marshaller.marshal(body, writer);
            _logger.info(writer.toString());
            String encryptStr = AesCode.encrypt(writer.toString());
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			_logger.info(decryptUTF8Str);
			JAXBContext contextResult = JAXBContext.newInstance(NbBankTransferBodyResp.class);
			Unmarshaller unmarshaller = contextResult.createUnmarshaller();
			StringReader reader = new StringReader(decryptUTF8Str);
			NbBankTransferBodyResp transferBodyResp =  (NbBankTransferBodyResp) unmarshaller.unmarshal(reader);
			NbBankHeadResp headResp = transferBodyResp.getHeadResp();
			String retCode = headResp.getRetCode();
			if("0".equals(retCode))
			{
				NbBankTranferResp transferResp = transferBodyResp.getTransferResp();
				String billCode = transferResp.getBillCode();
				retMap.put("retCode", headResp.getRetCode());
				retMap.put("retMsg", headResp.getRetMsg());
				retMap.put("billCode", billCode);
				
			}
			else
			{
				retMap.put("retCode", headResp.getRetCode());
				retMap.put("retMsg", headResp.getRetMsg());
				_logger.info("报文出错，错误码:" + headResp.getRetCode() + ";错误描述:" + headResp.getRetMsg());
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_logger.error("NbBankPayImpl erpTransfer error",e);
			e.printStackTrace();
			retMap.put("retCode", "9999");
			retMap.put("retMsg", e.getMessage());
		}
		_logger.info("end erpTransfer");
		return retMap;
	}
	
	/**
	 * 付款结果查询
	 */
	public Map<String,String> queryTransfer(String billCode)
	{
		Map<String,String> retMap = new HashMap<String,String>();
		_logger.info("start queryTransfer");
		try {
			URL url = new URL(NbBankPayUrl);
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			NbBankQueryTransferBody queryBody = new NbBankQueryTransferBody();
			
			//请求头
			NbBankHead head = new NbBankHead();
			head.setCustNo(custNo);
			head.setErpSysCode(erpSysCode);
			head.setTradeName("ERP_QUERYTRANSFER"); 
			
			//请求体
			NbBankQueryTransfer queryTransfer = new NbBankQueryTransfer();
			queryTransfer.setErpReqNo(billCode);
//			queryTransfer.setBillCode(billCode);
			
			queryBody.setHead(head);
			queryBody.setQueryTransfer(queryTransfer);
			
			String reqXml = marshallerRequest(queryBody);
			_logger.info(reqXml);
            String encryptStr = AesCode.encrypt(reqXml);
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			_logger.info(decryptUTF8Str);
			NbBankQueryTransferBodyResp queryBodyResp =  (NbBankQueryTransferBodyResp) unMarShallerResp(decryptUTF8Str, new NbBankQueryTransferBodyResp());
			
		    NbBankHeadResp headResp = queryBodyResp.getHeadResp();
		    String retCode = headResp.getRetCode();
		    if("0".equals(retCode))
			{
				NbBankQueryTransferResp queryTransferResp = queryBodyResp.getQueryTransferResp();
				String payState = queryTransferResp.getPayState();
				String payMsg = queryTransferResp.getPayMsg();
				_logger.info(billCode + " payState is :" + payState + ";payMsg is:" + payMsg);
				retMap.put("retCode", headResp.getRetCode());
				retMap.put("retMsg", headResp.getRetMsg());
				retMap.put("payState", payState);
				retMap.put("payMsg", payMsg);
			}
			else
			{
				retMap.put("retCode", headResp.getRetCode());
				retMap.put("retMsg", headResp.getRetMsg());
				_logger.error("报文出错，错误码:" + headResp.getRetCode() + ";错误描述:" + headResp.getRetMsg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_logger.error("NbBankPayImpl queryTransfer error",e);
			e.printStackTrace();
			retMap.put("retCode", "9999");
			retMap.put("retMsg", e.getMessage());
		}
		_logger.info("end queryTransfer");
		return retMap;
	
	}
	
	/**
	 * 查询当日明细
	 */
	public void queryCurdTl(String bankAcc)
	{
		try {
			URL url = new URL(NbBankPayUrl);
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			NbBankQueryCrudTlBody queryCrudBody = new NbBankQueryCrudTlBody();
			
			//请求头
			NbBankHead head = new NbBankHead();
			head.setCustNo(custNo);
			head.setErpSysCode(erpSysCode);
			head.setTradeName("ERP_QUERYCURDTL"); 
			
			//请求体
			NbBankQueryCurdTl queryCurdTl = new NbBankQueryCurdTl();
			queryCurdTl.setBankAcc(bankAcc);
			
			queryCrudBody.setHead(head);
			queryCrudBody.setQueryCrudTl(queryCurdTl);
			
			String reqXml = marshallerRequest(queryCrudBody);
			_logger.info(reqXml);
            String encryptStr = AesCode.encrypt(reqXml);
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			_logger.info(decryptUTF8Str);
			NbBankQueryCrudTlBodyResp queryBodyResp =  (NbBankQueryCrudTlBodyResp) unMarShallerResp(decryptUTF8Str, new NbBankQueryCrudTlBodyResp());
			
		    NbBankHeadResp headResp = queryBodyResp.getHeadResp();
		    String retCode = headResp.getRetCode();
		    if("0".equals(retCode))
			{
				NbBankQueryCrudTlTotalResp totalResp = queryBodyResp.getTotalResp();
				String total = totalResp.getRecordTotal();
				
				_logger.info("total is :" + total);
				
				NbBankQueryCrudTlLoopResp loopResp = queryBodyResp.getLoopResp();
			    List<NbBankQueryCrudTlLoopData> respList = loopResp.getLoopData();
			    if(respList != null)
			    {
			    	for(NbBankQueryCrudTlLoopData loopData:respList)
			    	{
			    		_logger.info(loopData.toString());
			    	}
			    }
				
			}
			else
			{
				_logger.error("报文出错，错误码:" + headResp.getRetCode() + ";错误描述:" + headResp.getRetMsg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_logger.error("NbBankPayImpl queryCurdTl error",e);
			e.printStackTrace();
		}
	
	
	}
	
	/**
	 * 查询账号信息列表
	 */
	public void queryAccList()
	{
		try {
			URL url = new URL(NbBankPayUrl);
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			NbBankQueryAccListBody queryAccListBody = new NbBankQueryAccListBody();
			
			//请求头
			NbBankHead head = new NbBankHead();
			head.setCustNo(custNo);
			head.setErpSysCode(erpSysCode);
			head.setTradeName("ERP_QUERYACCLIST"); 
			
			//请求体
			NbBankQueryAccList queryAccList = new NbBankQueryAccList();
			queryAccList.setQueryCustNo(custNo);
			
			queryAccListBody.setHead(head);
			queryAccListBody.setQueryAccList(queryAccList);
			
			String reqXml = marshallerRequest(queryAccListBody);
			_logger.info(reqXml);
            String encryptStr = AesCode.encrypt(reqXml);
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			_logger.info(decryptUTF8Str);
			NbBankQueryAccListBodyResp queryBodyResp =  (NbBankQueryAccListBodyResp) unMarShallerResp(decryptUTF8Str, new NbBankQueryAccListBodyResp());
			
		    NbBankHeadResp headResp = queryBodyResp.getHeadResp();
		    String retCode = headResp.getRetCode();
		    if("0".equals(retCode))
			{
		    	NbBankQueryAccListLoopResp loopResp = queryBodyResp.getLoopResp();
			    List<NbBankQueryAccListLoopData> respList = loopResp.getLoopData();
			    if(respList != null)
			    {
			    	for(NbBankQueryAccListLoopData loopData:respList)
			    	{
			    		_logger.info(loopData.toString());
			    	}
			    }
				
			}
			else
			{
				_logger.error("报文出错，错误码:" + headResp.getRetCode() + ";错误描述:" + headResp.getRetMsg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_logger.error("NbBankPayImpl queryAccList error",e);
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 查询历史明细
	 */
	public List<NbBankQueryHisDtlLoopData> queryHisDtl(Map<String,String> paramMap)
	{
		List<NbBankQueryHisDtlLoopData> respList = new ArrayList<NbBankQueryHisDtlLoopData>();
		try {
			URL url = new URL(NbBankPayUrl);
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			NbBankQueryHisDtlBody queryHisBody = new NbBankQueryHisDtlBody();
			
			//请求头
			NbBankHead head = new NbBankHead();
			head.setCustNo(custNo);
			head.setErpSysCode(erpSysCode);
			head.setTradeName("ERP_QUERYHISDTL"); 
			
			//请求体
			NbBankQueryHisDtl queryHis = new NbBankQueryHisDtl();
			queryHis.setBankAcc(paramMap.get("bankAcc"));
			queryHis.setQueryDateBegin(paramMap.get("beginDate"));
			queryHis.setQueryDateEnd(paramMap.get("endDate"));
			
			queryHisBody.setHead(head);
			queryHisBody.setQueryHisDtl(queryHis);
			
			String reqXml = marshallerRequest(queryHisBody);
			_logger.info(reqXml);
            String encryptStr = AesCode.encrypt(reqXml);
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			_logger.info(decryptUTF8Str);
			NbBankQueryHisDtlBodyResp queryBodyResp =  (NbBankQueryHisDtlBodyResp) unMarShallerResp(decryptUTF8Str, new NbBankQueryHisDtlBodyResp());
			
		    NbBankHeadResp headResp = queryBodyResp.getHeadResp();
		    String retCode = headResp.getRetCode();
		    if("0".equals(retCode))
			{
		    	NbBankQueryHisDtlTotalResp totalResp = queryBodyResp.getTotalResp();
		    	_logger.info(totalResp.getRecordTotal());
		    	
		    	NbBankQueryHisDtlLoopResp loopResp = queryBodyResp.getLoopResp();
		    	
			    respList = loopResp.getLoopData();
			}
			else
			{
				_logger.error("报文出错，错误码:" + headResp.getRetCode() + ";错误描述:" + headResp.getRetMsg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			_logger.error("NbBankPayImpl queryHisDtl error",e);
			e.printStackTrace();
		}
		return respList;
	
	}
	
	
	/**
	 * 	查询账户余额
	 */
	public List<NbBankQueryHisBalLoopData> queryHisBalance(Map<String,String> paramMap)
	{
		List<NbBankQueryHisBalLoopData> respList = new ArrayList<NbBankQueryHisBalLoopData>();
		try {
			URL url = new URL(NbBankPayUrl);
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			NbBankQueryHisBalBody reqData = new NbBankQueryHisBalBody();
			
			//请求头
			NbBankHead head = new NbBankHead();
			head.setCustNo(custNo);
			head.setErpSysCode(erpSysCode);
			head.setTradeName("ERP_QUERYHISBAL"); 
			
			//请求体
			NbBankQueryHisBal queryHisBalBody = new NbBankQueryHisBal();
			queryHisBalBody.setQueryDate(paramMap.get("queryDate"));
			queryHisBalBody.setQueryCustNo(custNo);
			queryHisBalBody.setTotalNum("");
			
			reqData.setHead(head);
			reqData.setQueryHisBal(queryHisBalBody);
			
			String reqXml = marshallerRequest(reqData);
			_logger.info(reqXml);
            String encryptStr = AesCode.encrypt(reqXml);
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			_logger.info(decryptUTF8Str);
			NbBankQueryHisBalBodyResp queryBodyResp =  (NbBankQueryHisBalBodyResp) unMarShallerResp(decryptUTF8Str, new NbBankQueryHisBalBodyResp());
			
		    NbBankHeadResp headResp = queryBodyResp.getHeadResp();
		    String retCode = headResp.getRetCode();
		    if("0".equals(retCode))
			{
		    	NbBankQueryHisBalLoopResp totalResp = queryBodyResp.getLoopResp();
		    	
		    	respList = totalResp.getLoopData();
		    	
			}
			else
			{
				_logger.error("报文出错，错误码:" + headResp.getRetCode() + ";错误描述:" + headResp.getRetMsg());
			}
		} catch (Exception e) {
			_logger.error("NbBankPayImpl queryHisBalance error",e);
			e.printStackTrace();
		}
		return respList;
	
	}
	
	/**
	 *  	联行号文件下载地址查询
	 */
	public String downloadUnionBankCode()
	{
		String fileUrl = "";
		try
		{
			URL url = new URL(NbBankPayUrl);
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			//请求头
			NbBankHead head = new NbBankHead();
			head.setErpSysCode(erpSysCode);
			head.setCustNo(custNo);
			head.setTradeName("ERP_UNIONBANKCODE_DOWNURL"); 
			
			NbBankDownLoadUnionBankBody body = new NbBankDownLoadUnionBankBody();
			body.setHead(head);
			body.setMap("");
			String reqXml = marshallerRequest(body);
			_logger.info(reqXml);
            String encryptStr = AesCode.encrypt(reqXml);
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			_logger.info(decryptUTF8Str);
			
			
			NbBankDownLoadUnionBankBodyResp bodyResp = (NbBankDownLoadUnionBankBodyResp) unMarShallerResp(decryptUTF8Str, new NbBankDownLoadUnionBankBodyResp());
			
			NbBankHeadResp headResp = bodyResp.getHeadResp();
		    String retCode = headResp.getRetCode();
		    if("0".equals(retCode))
			{
		    	NbBankDownLoadUnionBankResp resp = bodyResp.getDownLoadResp();
		    	fileUrl = resp.getFileUrl();
			}
		}
		catch(Exception e)
		{
			_logger.error("NbBankPayImpl downloadUnionBankCode error",e);
		}
		return fileUrl;
	}
	
	/**
	   *  构建请求xml 
	 * @param <T>
	 * @param xmlObj
	 * @param clazz
	 * @return
	 */
	public String marshallerRequest(Object xmlObj)
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
	public Object unMarShallerResp(String xml,Object obj)
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
	
	public static void main(String[] args) {
		NbBankPayImpl impl = new NbBankPayImpl();
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("queryDate", "2019-10-30");
		impl.queryHisBalance(paramMap);
	}
}
