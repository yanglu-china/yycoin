package com.china.center.oa.job.manager.impl;

import com.china.center.oa.sail.bean.BranchRelationBean;
import com.china.center.oa.sail.bean.OutBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.tools.ListTools;
import jxl.Workbook;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 浦发银行邮件JOB
 */
public class PfShipJobManagerImpl extends AbstractShipJobManager{
    private final Log _logger = LogFactory.getLog(getClass());

    @Override
    protected String getKey(PackageItemBean itemBean) {
        //浦发银行同一客户还需要根据渠道分组
        return itemBean.getCustomerId()+"_"+this.getChannel(itemBean);
    }

    @Override
    protected BranchRelationBean getRelation(String customerId, String channel) {
        return this.getRelationByCustomerId(customerId, channel);
    }

    @Override
    protected String getTestCk() {
        return "CK201801161697089589";
    }

    @Override
    protected boolean createMailAttachment(int index, String customerName, String channel,List<PackageItemBean> beans, String branchName, String fileName, boolean ignoreLyOrders) {
        boolean result = false;
        //#219 浦发银行非小浦金店模板
        if (customerName.indexOf("浦发银行") != -1 && !"小浦金店".equals(channel)){
            _logger.info("***createPfMailAttachment with package "+beans+"***branch***"+branchName+"***file name***"+fileName);
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
                String title = String.format("永银文化创意产业发展有限责任公司%s 产品发货清单", this.getYesterday());

                // 完成标题
                ws.addCell(new Label(1, i, title, format));

                //set column width
                ws.setColumnView(0, 15);
                ws.setColumnView(1, 40);
                ws.setColumnView(2, 40);
                ws.setColumnView(3, 20);
                ws.setColumnView(4, 40);
                ws.setColumnView(5, 10);
                ws.setColumnView(6, 5);
                ws.setColumnView(7, 10);
                ws.setColumnView(8, 20);
                ws.setColumnView(9, 20);
                ws.setColumnView(10, 10);

                i++;
                // 正文表格
                ws.addCell(new Label(0, i, "交易日期", format3));
                ws.addCell(new Label(1, i, "交易机构", format3));
                ws.addCell(new Label(2, i, "配货机构", format3));
                ws.addCell(new Label(3, i, "产品代码", format3));
                ws.addCell(new Label(4, i, "产品名称", format3));
                ws.addCell(new Label(5, i, "零售价", format3));
                ws.addCell(new Label(6, i, "数量", format3));
                ws.addCell(new Label(7, i, "快递公司", format3));
                ws.addCell(new Label(8, i, "快递单号", format3));
                ws.addCell(new Label(9, i, "发票号", format3));
                ws.addCell(new Label(10, i, "证书号", format3));


                _logger.info("***itemList size***"+beans.size());
//                this.mergeInvoiceNum(itemList);
                //TODO
                List<PackageItemBean> itemList = this.mergeItems(beans);
                _logger.info("***after merge itemList size***"+itemList.size());
                if (!ListTools.isEmptyOrNull(itemList)){
                    for (PackageItemBean each : itemList)
                    {
                        //#351 filter LY orders
                        if (ignoreLyOrders && each.getOutId().startsWith("LY")){
                            continue;
                        }
                        i++;

                        //交易日期
                        String outId = each.getOutId();
                        OutBean outBean = outDAO.find(outId);
                        if (outBean!= null){
                            each.setPoDate(outBean.getPodate());
                        } else if(out == null && outId.startsWith("A")){
                            each.setPoDate(each.getOutTime());
                        }

                        ws.addCell(new Label(j++, i, each.getPoDate(), format3));

                        //交易机构
                        ws.addCell(new Label(j++, i, this.getCustomerName(each), format3));

                        String[] temp = this.getCommunicationBranchNameAndProductCodeFromOutImport(each.getOutId());
                        //配货机构
                        ws.addCell(new Label(j++, i, temp[1], format3));

                        if(each.getOutId().startsWith("A")){
                            //产品代码
                            ws.addCell(new Label(j++, i, "", format3));

                            //产品名称
                            ws.addCell(new Label(j++, i, "", format3));
                        } else{
                            //产品代码
                            ws.addCell(new Label(j++, i, temp[0], format3));

                            //产品名称
                            ws.addCell(new Label(j++, i, this.shipManager.convertProductName(each,customerName), format3));
                        }


                        //零售价
                        ws.addCell(new Label(j++, i, String.valueOf(each.getPrice()), format3));

                        //数量
                        ws.addCell(new Label(j++, i, String.valueOf(each.getAmount()), format3));

                        //快递公司
                        ws.addCell(new Label(j++, i, each.getTransportName1(), format3));
                        //2016/4/5 #2 快递单号改取package表的transportNo
                        String transportNo = each.getTransportNo();
                        ws.addCell(new Label(j++, i, transportNo, format3));

                        //发票号
                        if(each.getOutId().startsWith("A")){
                            ws.addCell(new Label(j++, i, each.getProductName(), format3));
                        } else{
                            ws.addCell(new Label(j++, i, each.getInvoiceNum(), format3));
                        }

                        //证书号
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
        }
        return result;
    }
}
