/**
 * File Name: DownFileAction.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-6-27<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.action;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.center.china.osgi.config.ConfigLoader;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.dao.AttachmentDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.oa.publics.Helper;
import com.china.center.tools.FileTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.UtilStream;


/**
 * DownFileAction
 * 
 * @author ZHUZHU
 * @version 2010-6-27
 * @see DownFileAction
 * @since 1.0
 */
public class DownFileAction extends DispatchAction
{
    private final Log _logger = LogFactory.getLog(getClass());

    private AttachmentDAO attachmentDAO = null;
    /**
     * default constructor
     */
    public DownFileAction()
    {
    }

    /**
     * 下载模板
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                          HttpServletResponse response)
        throws ServletException, IOException
    {
        String path = Helper.getRootPath();

        path = FileTools.formatPath(path) + "template/template.xls";

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=template.xls");

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }

    /**
     * 获得下载的文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downProfitTemplateFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException, IOException
    {
        String path = Helper.getRootPath();

        path = FileTools.formatPath(path) + "template/profitTemplate.xls";

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename=template.xls");

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }

    /**
     * 获得下载的文件
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downTemplateFileByName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response)
        throws ServletException, IOException
    {
        String fileName = request.getParameter("fileName");

        if (fileName.contains("/") || fileName.contains("\\"))
        {
            return null;
        }

        String path = Helper.getRootPath();

        path = path + "template/" + fileName;

        File file = new File(path);

        OutputStream out = response.getOutputStream();

        response.setContentLength((int)file.length());

        response.setContentType("application/x-dbf");

        response.setHeader("Content-Disposition", "attachment; filename="
                                                  + StringTools.getStringBySet(fileName, "GBK", "ISO8859-1"));

        UtilStream us = new UtilStream(new FileInputStream(file), out);

        us.copyAndCloseStream();

        return null;
    }

    public AttachmentDAO getAttachmentDAO() {
        return attachmentDAO;
    }

    public void setAttachmentDAO(AttachmentDAO attachmentDAO) {
        this.attachmentDAO = attachmentDAO;
    }

    /**
     * 根据单据Id下载附件，
     *单个时直接下载，多个时压缩下载
     * id: 标识，关键字；
     * type:tcp, finance,
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public ActionForward downPayAttachmentsById(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                                HttpServletResponse response)
            throws ServletException, IOException
    {
        String id = request.getParameter("id");
        String type = request.getParameter("type");

        if(StringTools.isNullOrNone(type)){
            type = "tcp";
        }

        if (StringTools.isNullOrNone(id))
        {
            return null;
        }

        final List<AttachmentBean> attachmentsList = attachmentDAO.queryEntityBeansByCondition("where refid=? and attachmentType = ? ", id, AttachmentBean.AttachmentType_FK);

        String path = ConfigLoader.getProperty("tcpAttachmentPath");
        if("finance".equals(type)){
            path = ConfigLoader.getProperty("financeAttachmentPath");
        }

        String filename = "";

        _logger.debug("attachmentsList.size(): "+attachmentsList.size());

        if(attachmentsList.size()==0){
            return null;
        }else if(attachmentsList.size()==1){
            //直接下载
            AttachmentBean bean = attachmentsList.get(0);
            path += bean.getPath();
            filename = bean.getName();

            File file = new File(path);
            OutputStream out = response.getOutputStream();

            response.setContentType("application/x-dbf");

            response.setHeader("Content-Disposition", "attachment; filename="
                    + StringTools.getStringBySet(filename,
                    "GBK", "ISO8859-1"));

            UtilStream us = new UtilStream(new FileInputStream(file), out);

            us.copyAndCloseStream();

        }else if(attachmentsList.size()>1) {
            //压缩后下载
            filename = "付款附件.zip";
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + StringTools.getStringBySet(filename,
                    "GBK", "ISO8859-1"));

            List<String> exportFilePathList = new ArrayList<String>();

            for(AttachmentBean bean : attachmentsList){
                String path1 = path + bean.getPath();
                exportFilePathList.add(path1);
            }

            ZipOutputStream zipos = null;
            try {
                zipos = new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()));
                zipos.setMethod(ZipOutputStream.DEFLATED);// 设置压缩方法DEFLATED
            } catch (Exception e) {
                e.printStackTrace();
            }
            DataOutputStream os = null;
            // 循环将文件写入压缩流
            for (String filePath : exportFilePathList) {
                File file = new File(filePath);
                try {
                    // 添加ZipEntry，并ZipEntry中写入文件流
                    zipos.putNextEntry(new ZipEntry(file.getName()));
                    os = new DataOutputStream(zipos);
                    InputStream is = new FileInputStream(file);
                    byte[] b = new byte[1000];
                    int length = 0;
                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }
                    is.close();
                    zipos.closeEntry();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 关闭流
            try {
                os.flush();
                os.close();
                zipos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;

    }
}
