package com.china.center.oa.finance.manager.payorder.byttersoft.bedp.webservice;

import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.AesCode;
import com.china.center.oa.finance.manager.payorder.NbBankPayImpl;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankHead;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryAccList;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryAccListBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryCrudTlBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryCurdTl;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryTransfer;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankQueryTransferBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankTransfer;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.req.NbBankTransferBody;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankHeadResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryAccListBodyResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryAccListLoopData;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryAccListLoopResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryCrudTlBodyResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryCrudTlLoopData;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryCrudTlLoopResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryCrudTlTotalResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryTransferBodyResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankQueryTransferResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankTranferResp;
import com.china.center.oa.finance.manager.payorder.nbbank.jaxbobject.resp.NbBankTransferBodyResp;

public class test {
	
	private final Log _logger = LogFactory.getLog("taobao");
	
	private String erpSysCode="erpnjg002";
	
	private String custNo = "0000212667";

	public static void main(String[] args) {
//		System.out.println("00000000000000000000000000000000000");
//		System.out.println("11111111111111111111111111111111111");
//		System.out.println("33333333333333333333333333333333333");
//		System.out.println("44444444444444444444444444444444444");
		test t = new test();
//		t.erpTransfer();
//		t.queryAccList();
//		t.queryTransfer();
//		t.queryCurdTl();
		NbBankPayImpl impl = new NbBankPayImpl();
		impl.queryCurdTl("125902780610502");
//		impl.queryTransfer("YT201907171774075939");
	}
	
	public void erpTransfer()
	{
		try {
			URL url = new URL("http://101.37.13.154:8090/BisOutPlatform/services/erpPlatform?wsdl");
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			NbBankTransferBody body = new NbBankTransferBody();
			//����ͷ
			NbBankHead head = new NbBankHead();
			head.setCustNo(custNo);
			head.setErpSysCode(erpSysCode);
			head.setTradeName("ERP_TRANSFER");
			//����body
			NbBankTransfer transfer = new NbBankTransfer();
			transfer.setPayerAccNo("952A9997220008092");
			transfer.setPayerCorpCode("9520");
			transfer.setPayerCorpName("�Ϻ��ֶ���չ�����Ͼ�����");
//			transfer.setPayerAccNo("70170122000004786");
//			transfer.setPayerCorpCode("1000");
//			transfer.setPayerCorpName("798931559");
			
//			transfer.setPayerAccNo("952A9997220008092");
//			transfer.setPayerCorpCode("1000");
//			transfer.setPayerCorpName("798931559");
			transfer.setErpPayerCorpCode("");
//			transfer.setPayeeAccNo("80010122000162044");
//			transfer.setPayeeAccName("575338758");
//			transfer.setPayeeBankName("�������йɷ����޹�˾���˷���");
//			transfer.setPayeeBankCode("");
//			transfer.setPayeeProv("�㽭ʡ");
//			transfer.setPayeeCity("������");
			transfer.setPayeeAccNo("95200078801300000003");
			transfer.setPayeeAccName("�ַ�2000040752");
			transfer.setPayeeBankName("�Ϻ��ֶ���չ�����Ͼ�����");
			transfer.setPayeeBankCode("");
			transfer.setPayeeProv("����ʡ");
			transfer.setPayeeCity("�Ͼ���");
			
			
			transfer.setPayMoney("0.01");
			transfer.setAreaSign("1");
			transfer.setDifSign("1");
			transfer.setPayPurpose("����");
			transfer.setErpReqNo(String.valueOf(System.currentTimeMillis()));
			transfer.setErpReqUser("");
			transfer.setAllowEditPayeeAcc("0");
			transfer.setAllowEditPayMoney("0");
			transfer.setAllowEditPayerAcc("1");
			transfer.setReverse1("");
			transfer.setReverse2("");
			transfer.setReverse3("");
			transfer.setReverse4("");
			transfer.setReverse5("1");
			
			
			body.setHead(head);
			body.setTransfer(transfer);
			JAXBContext context = JAXBContext.newInstance(NbBankTransferBody.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");// //�����ʽ
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);// �Ƿ��ʽ�����ɵ�xml��
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);// �Ƿ�ʡ��xmͷ������Ϣ
            StringWriter writer = new StringWriter();
            marshaller.marshal(body, writer);
            System.out.println(writer.toString());
            String encryptStr = AesCode.encrypt(writer.toString());
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			System.out.println(decryptUTF8Str);
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
				System.out.println("billcode is :" + billCode);
				
			}
			else
			{
				System.out.println("���ĳ���������:" + headResp.getRetCode() + ";��������:" + headResp.getRetMsg());
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������ѯ
	 */
	public void queryTransfer()
	{

		try {
			URL url = new URL("http://101.37.13.154:8090/BisOutPlatform/services/erpPlatform?wsdl");
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			NbBankQueryTransferBody queryBody = new NbBankQueryTransferBody();
			
			//����ͷ
			NbBankHead head = new NbBankHead();
			head.setCustNo(custNo);
			head.setErpSysCode(erpSysCode);
			head.setTradeName("ERP_QUERYTRANSFER"); 
			
			//������
			NbBankQueryTransfer queryTransfer = new NbBankQueryTransfer();
//			queryTransfer.setErpReqNo("1557363466548");1557458757565
			queryTransfer.setBillCode("ERP190510000001");
			
			queryBody.setHead(head);
			queryBody.setQueryTransfer(queryTransfer);
			
			String reqXml = marshallerRequest(queryBody);
            System.out.println(reqXml);
            String encryptStr = AesCode.encrypt(reqXml);
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			System.out.println(decryptUTF8Str);
			NbBankQueryTransferBodyResp queryBodyResp =  (NbBankQueryTransferBodyResp) unMarShallerResp(decryptUTF8Str, new NbBankQueryTransferBodyResp());
			
		    NbBankHeadResp headResp = queryBodyResp.getHeadResp();
		    String retCode = headResp.getRetCode();
		    if("0".equals(retCode))
			{
				NbBankQueryTransferResp queryTransferResp = queryBodyResp.getQueryTransferResp();
				String payState = queryTransferResp.getPayState();
				String payMsg = queryTransferResp.getPayMsg();
				System.out.println("payState is :" + payState + ";payMsg is:" + payMsg);
				
			}
			else
			{
				System.out.println("���ĳ���������:" + headResp.getRetCode() + ";��������:" + headResp.getRetMsg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	 * ��ѯ������ϸ
	 */
	public void queryCurdTl()
	{
		try {
			URL url = new URL("http://101.37.13.154:8090/BisOutPlatform/services/erpPlatform?wsdl");
			ErpPlatformLocator locator = new ErpPlatformLocator();
			ErpPlatformSoap11BindingStub stub = (ErpPlatformSoap11BindingStub) locator.geterpPlatformHttpSoap11Endpoint(url);
			
			NbBankQueryCrudTlBody queryCrudBody = new NbBankQueryCrudTlBody();
			
			//����ͷ
			NbBankHead head = new NbBankHead();
			head.setCustNo(custNo);
			head.setErpSysCode(erpSysCode);
			head.setTradeName("ERP_QUERYCURDTL"); 
			
			//������
			NbBankQueryCurdTl queryCurdTl = new NbBankQueryCurdTl();
			queryCurdTl.setBankAcc("70170122000004786");
			
			queryCrudBody.setHead(head);
			queryCrudBody.setQueryCrudTl(queryCurdTl);
			
			String reqXml = marshallerRequest(queryCrudBody);
            System.out.println(reqXml);
            String encryptStr = AesCode.encrypt(reqXml);
			String result = stub.serverErpXml(encryptStr);
			String decryptUTF8Str = AesCode.decrypt2GBK(result);
			System.out.println(decryptUTF8Str);
			NbBankQueryCrudTlBodyResp queryBodyResp =  (NbBankQueryCrudTlBodyResp) unMarShallerResp(decryptUTF8Str, new NbBankQueryCrudTlBodyResp());
			
		    NbBankHeadResp headResp = queryBodyResp.getHeadResp();
		    String retCode = headResp.getRetCode();
		    if("0".equals(retCode))
			{
				NbBankQueryCrudTlTotalResp totalResp = queryBodyResp.getTotalResp();
				String total = totalResp.getRecordTotal();
				
				System.out.println("total is :" + total);
				
				NbBankQueryCrudTlLoopResp loopResp = queryBodyResp.getLoopResp();
			    List<NbBankQueryCrudTlLoopData> respList = loopResp.getLoopData();
			    if(respList != null)
			    {
			    	for(NbBankQueryCrudTlLoopData loopData:respList)
			    	{
			    		System.out.println(loopData.toString());
			    	}
			    }
				
			}
			else
			{
				System.out.println("���ĳ���������:" + headResp.getRetCode() + ";��������:" + headResp.getRetMsg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
	
	/**
	 * 查询账户信息列表
	 */
	public void queryAccList()
	{
		try {
			URL url = new URL("http://101.37.13.154:8090/BisOutPlatform/services/erpPlatform?wsdl");
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
	   *  ��������xml 
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
			//�����ʽ
	        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	        // �Ƿ��ʽ�����ɵ�xml��
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        // �Ƿ�ʡ��xmͷ������Ϣ
	        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
	        
            marshaller.marshal(xmlObj, writer);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return writer.toString();
		
	}
	
	/**
	 * ����xml to bean
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
	
}
