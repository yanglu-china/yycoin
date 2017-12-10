package com.china.center.oa.sail.manager.impl;

import com.sf.integration.expressservice.service.CommonExpressServiceService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.misc.BASE64Encoder;

import javax.xml.namespace.QName;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author SunQi
 * @Description:
 * @date Create in 2017/9/25 14:48
 */
public class SfRouteService {

    public static String loadFile(String fileName) {
        InputStream fis;
        try {
            fis = new FileInputStream(fileName);
            byte[] bs = new byte[fis.available()];
            fis.read(bs);
            String res = new String(bs, "utf8");
            fis.close();
            return res;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5EncryptAndBase64(String str) {
        return encodeBase64(md5Encrypt(str));
    }

    private static byte[] md5Encrypt(String encryptStr) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(encryptStr.getBytes("utf8"));
            return md5.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String encodeBase64(byte[] b) {
        BASE64Encoder base64Encode = new BASE64Encoder();
        String str = base64Encode.encode(b);
        return str;
    }

    public List<SfRouteBean> queryRoute(String mainnoId) {
        List<SfRouteBean> routeList = new ArrayList<SfRouteBean>();
//        String uri = "http://bsp-ois.sit.sf-express.com:9080/bsp-ois/ws/sfexpressService?wsdl";  //WEBSERVICE 地址
//        String uri = "http://218.17.248.244:11080/bsp-oisp/ws/sfexpressService?wsdl";

        String uri = "http://bsp-oisp.sf-express.com/bsp-oisp/ws/sfexpressService?wsdl";
        String checkWord = "Ca5bvGScarYHwINuvk3Uem4smvagSyov";

        String xmlFile = "<Request service=\"RouteService\" lang=\"zh-CN\"> \n" +                //请求xml
                "  <Head>0251659966</Head>  \n" +
                "  <Body> \n" +
                "    <RouteRequest tracking_type=\"1\" method_type=\"1\" tracking_number=\"" + mainnoId +
                "\"/> \n" +
                "  </Body> \n" +
                "</Request>";
//        String checkWord = "j8DzkIFgmlomPt0aLuwU";       //密钥
//        String checkWord = "9OKu3lis4FIY";
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.sf.integration.expressservice.service.CommonExpressServiceService.class.getResource(".");
            url = new URL(baseUrl, uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        CommonExpressServiceService service = new CommonExpressServiceService(url,
                new QName("http://service.expressservice.integration.sf.com/", "CommonExpressServiceService"));

        //xml报文与checkWord前后连接
        String xc = xmlFile + checkWord;

        //MD5+base64编码
        String verifyCode = this.md5EncryptAndBase64(xc);

        //请求返回
        String res = service.getCommonExpressServicePort().sfexpressService(xmlFile, verifyCode);
        System.out.println(res);
        Document doc = Jsoup.parse(res);
        Elements routes = doc.getElementsByTag("Route");
        for (Element route : routes) {
            String remark = route.attr("remark");
            String accept_time = route.attr("accept_time");
            String accept_address = route.attr("accept_address");
            String opcode = route.attr("opcode");

            SfRouteBean sfRouteBean = new SfRouteBean();
            sfRouteBean.setRemark(remark);
            sfRouteBean.setAccept_time(accept_time);
            sfRouteBean.setAccept_address(accept_address);
            sfRouteBean.setOpcode(opcode);
            routeList.add(sfRouteBean);
        }

        Collections.sort(routeList, new Comparator<SfRouteBean>() {
            public int compare(SfRouteBean o1, SfRouteBean o2) {
                return o2.getAccept_time().compareTo(o1.getAccept_time());
            }
        });
        return routeList;
    }


    public static void main(String[] args) {
        SfRouteService re = new SfRouteService();
        List<SfRouteBean> sfRouteBeans = null;
//        re.queryRoute("617233164588");
        sfRouteBeans = re.queryRoute("601642231164");
        System.out.println(sfRouteBeans);
//        re.queryRoute("601144837451");

//        System.out.println(re.queryRoute("444001003588"));

    }

}
