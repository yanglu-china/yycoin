package com.china.center.oa.job.manager.impl;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.BranchRelationBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import jxl.Workbook;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 新郑农商邮件JOB
 */
public class XznsShipJobManagerImpl extends AbstractShipJobManager{
    private final Log _logger = LogFactory.getLog(getClass());

    final String XZNS = "新郑农商";

    @Override
    protected String getKey(PackageItemBean itemBean) {
        //其他银行不考虑渠道
        return itemBean.getCustomerId();
    }

    @Override
    protected BranchRelationBean getRelation(String customerId, String channel) {
        //其他银行不考虑渠道
        return this.getRelationByCustomerId(customerId,"");
    }

    @Override
    protected String getTestCk() {
        return "CK201711191448114358";
    }


    @Override
    protected boolean needSendMail(String customerName, String channel) {
        if (customerName.indexOf(XZNS) != -1 ){
            return true;
        }
        return false;
    }

    @Override
    protected boolean createMailAttachment(int index, String customerName,String channel, List<PackageItemBean> beans,
                                           String branchName, String fileName, boolean ignoreLyOrders) {
        if (customerName.indexOf(XZNS) != -1 ){
            _logger.info("***createMailAttachment package XZNS "+beans+"***branch***"+branchName+"***file name***"+fileName);
            boolean result = false;
            WritableWorkbook wwb = null;

            WritableSheet ws = null;

            OutputStream out = null;

            try
            {
                out = new FileOutputStream(fileName);

                // create a excel
                wwb = Workbook.createWorkbook(out);

                ws = wwb.createSheet("发货信息", 0);

                // 横向
                ws.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE, PaperSize.A4,0.5d,0.5d);

                // 标题字体
                WritableFont font = new WritableFont(WritableFont.ARIAL, 11,
                        WritableFont.BOLD, false,
                        jxl.format.UnderlineStyle.NO_UNDERLINE,
                        jxl.format.Colour.BLACK);

                WritableFont font2 = new WritableFont(WritableFont.ARIAL, 9,
                        WritableFont.BOLD, false,
                        jxl.format.UnderlineStyle.NO_UNDERLINE,
                        jxl.format.Colour.BLACK);

                WritableFont font3 = new WritableFont(WritableFont.ARIAL, 9,
                        WritableFont.NO_BOLD, false,
                        jxl.format.UnderlineStyle.NO_UNDERLINE,
                        jxl.format.Colour.BLACK);

                WritableFont font4 = new WritableFont(WritableFont.ARIAL, 9,
                        WritableFont.BOLD, false,
                        jxl.format.UnderlineStyle.NO_UNDERLINE,
                        jxl.format.Colour.BLUE);

                WritableCellFormat format = new WritableCellFormat(font);

                format.setAlignment(jxl.format.Alignment.CENTRE);
                format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

                WritableCellFormat format2 = new WritableCellFormat(font2);

                format2.setAlignment(jxl.format.Alignment.LEFT);
                format2.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                format2.setWrap(true);

                WritableCellFormat format21 = new WritableCellFormat(font2);
                format21.setAlignment(jxl.format.Alignment.RIGHT);

                WritableCellFormat format3 = new WritableCellFormat(font3);
                format3.setBorder(jxl.format.Border.ALL,
                        jxl.format.BorderLineStyle.THIN);

                WritableCellFormat format31 = new WritableCellFormat(font3);
                format31.setBorder(jxl.format.Border.ALL,
                        jxl.format.BorderLineStyle.THIN);
                format31.setAlignment(jxl.format.Alignment.RIGHT);

                WritableCellFormat format4 = new WritableCellFormat(font4);
                format4.setBorder(jxl.format.Border.ALL,
                        jxl.format.BorderLineStyle.THIN);

                WritableCellFormat format41 = new WritableCellFormat(font4);
                format41.setBorder(jxl.format.Border.ALL,
                        jxl.format.BorderLineStyle.THIN);
                format41.setAlignment(jxl.format.Alignment.CENTRE);
                format41.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);

                int i = 0, j = 0, i1 = 1;

                //set column width
                ws.setColumnView(0, 40);
                ws.setColumnView(1, 40);
                ws.setColumnView(2, 40);


                // 正文表格
                ws.addCell(new Label(0, i, "交易流水号", format3));
                ws.addCell(new Label(1, i, "快递公司", format3));
                ws.addCell(new Label(2, i, "快递单号", format3));


                for (PackageItemBean each : beans)
                {
                    //#351 filter LY orders
                    if (ignoreLyOrders && each.getOutId().startsWith("LY")){
                        continue;
                    }
                    i++;

                    //交易流水号取银行订单号
                    ConditionParse con3 = new ConditionParse();
                    con3.addWhereStr();
                    con3.addCondition("OutImportBean.oano", "=", each.getOutId());
                    List<OutImportBean> importBeans = this.outImportDAO.queryEntityBeansByCondition(con3);
                    String citicNo = "";
                    if (!ListTools.isEmptyOrNull(importBeans)){
                        for (OutImportBean b: importBeans){
                            if (!StringTools.isNullOrNone(b.getCiticNo())){
                                citicNo = b.getCiticNo();
                            }
                        }
                    }
                    ws.addCell(new Label(j++, i, citicNo, format3));


                    //快递公司
                    ws.addCell(new Label(j++, i, each.getTransportName1(), format3));
                    //2016/4/5 #2 快递单号改取package表的transportNo
                    String transportNo = each.getTransportNo();
                    ws.addCell(new Label(j++, i, transportNo, format3));

                    j = 0;
                    result = true;
                }

            }
            catch (Throwable e)
            {
                e.printStackTrace();
            }
            finally
            {
                if (wwb != null)
                {
                    try
                    {
                        wwb.write();
                        wwb.close();
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
            return result;
        } else{
            return false;
        }
    }


    private void setWS(WritableSheet ws, int i, int rowHeight, boolean mergeCell)
            throws WriteException, RowsExceededException
    {
        if (mergeCell) ws.mergeCells(0, i, 9, i);

        ws.setRowView(i, rowHeight);
    }
}
