package com.china.center.oa.job.manager.impl;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductImportBean;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.sail.bean.BranchRelationBean;
import com.china.center.oa.sail.bean.OutImportBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.oa.sail.constanst.ShipConstant;
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
 * 南京银行邮件JOB
 */
public class NjShipJobManagerImpl extends AbstractShipJobManager{
    private final Log _logger = LogFactory.getLog(getClass());

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
    protected boolean needSendMail(String customerName, String channel) {
        if (customerName.indexOf("南京银行") != -1 ){
            return true;
        }
        return false;
    }

    @Override
    protected String getTestCk() {
        return "CK201711191448114358";
    }

    @Override
    protected boolean createMailAttachment(int index, String customerName,String channel, List<PackageItemBean> beans,
                                        String branchName, String fileName, boolean ignoreLyOrders) {
        if (customerName.indexOf("南京银行") != -1 ){
            _logger.info("***createMailAttachmentNj package "+beans+"***branch***"+branchName+"***file name***"+fileName);
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
                ws.setColumnView(0, 5);
                ws.setColumnView(1, 40);
                ws.setColumnView(2, 40);
                ws.setColumnView(3, 20);
                ws.setColumnView(4, 30);
                ws.setColumnView(5, 30);
                ws.setColumnView(6, 30);
                ws.setColumnView(7, 40);

                // 正文表格
                ws.addCell(new Label(0, i, "序号", format3));
                ws.addCell(new Label(1, i, "产品代码", format3));
                ws.addCell(new Label(2, i, "产品名称", format3));
                ws.addCell(new Label(3, i, "产品规格", format3));
                ws.addCell(new Label(4, i, "产品块号", format3));
                ws.addCell(new Label(5, i, "入库状态", format3));
                ws.addCell(new Label(6, i, "收藏证书号", format3));
                ws.addCell(new Label(7, i, "出厂序号", format3));


                for (PackageItemBean each : beans)
                {
                    //#351 filter LY orders
                    if (ignoreLyOrders && each.getOutId().startsWith("LY")){
                        continue;
                    }//#210
                    else if(each.getOutId().startsWith("ZS") || each.getOutId().startsWith("A")){
                        continue;
                    }

                    for (int number = 0;number< each.getAmount();number++){
                        i++;
                        ws.addCell(new Label(j++, i, String.valueOf(i1++), format3));
                        setWS(ws, i, 300, false);

                        //产品代码
                        ws.addCell(new Label(j++, i, this.shipManager.getProductCode(each), format3));
                        //产品名称
                        ws.addCell(new Label(j++, i, this.shipManager.convertProductName(each, customerName), format3));

                        //产品规格
                        String spec = "";
                        ProductImportBean productImportBean = this.shipManager.getProductImportBean(each,"南京银行");
                        if (productImportBean!= null){
                            spec = productImportBean.getWeight();
                        }
                        ws.addCell(new Label(j++, i, spec, format3));


                        //产品块号 日期+编号
                        String serialNo = StringUtils.generateSerialNo(index*100+i);
                        ws.addCell(new Label(j++, i, serialNo, format3));
                        //入库状态
                        ws.addCell(new Label(j++, i, "正常", format3));
                        //收藏证书号
                        ws.addCell(new Label(j++, i, "", format3));
                        //出厂序号
                        ws.addCell(new Label(j++, i, "", format3));

                        j = 0;
                        result = true;
                    }

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
