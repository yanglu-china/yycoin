package com.china.center.oa.job.manager.impl;

import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.sail.bean.BranchRelationBean;
import com.china.center.oa.sail.bean.PackageItemBean;
import com.china.center.oa.sail.vo.PackageVO;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;
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
 * 浦发银行小浦金店邮件JOB
 */
public class XpShipJobManagerImpl extends AbstractShipJobManager{
    private final Log _logger = LogFactory.getLog(getClass());

    @Override
    protected String getKey(PackageItemBean itemBean) {
        //小浦金店根据支行邮件地址分组
        String customerId = itemBean.getCustomerId();
        String channel = this.getChannel(itemBean);
        BranchRelationBean branchRelationBean = this.getRelation(customerId, channel);
        if (branchRelationBean == null){
            return itemBean.getCustomerId()+"_"+this.getChannel(itemBean);
        } else{
            return branchRelationBean.getSubBranchMail();
        }
    }

    @Override
    protected String getAttachmentFileName(BranchRelationBean bean) {
        String fileName = getShippingAttachmentPath() + "/小浦金店"
                + "_" +bean.getSubBranchMail()+"_"+ TimeTools.now("yyyyMMddHHmmss") + ".xls";
        return fileName;
    }

    @Override
    protected BranchRelationBean getRelation(String customerId, String channel) {
        return this.getRelationByCustomerId(customerId, channel);
    }

    @Override
    protected String getTitle() {
        return "小浦金店邮件";
    }

    @Override
    protected String getTestCk() {
        return "CK201801161697089589";
    }

    @Override
    protected List<PackageVO> getPackageList() {
        ConditionParse con = new ConditionParse();
        con.addWhereStr();
        con.addIntCondition("PackageBean.sendMailFlag", "=", 0);
        con.addCondition("PackageBean.logTime", ">=", "2017-04-27 00:00:00");
        //自提类的也不在发送邮件范围内
        con.addIntCondition("PackageBean.shipping","!=", 0);
        //#236 已发货和在途都要发邮件
        con.addCondition(" and PackageBean.status in(2,10)");

        //step1: 根据支行customerId+channel对CK单合并
        List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);
        return packageList;
    }

    @Override
    protected boolean needSendMail(String customerName, String channel) {
        if (customerName.indexOf("浦发银行") != -1 && "小浦金店".equals(channel)){
            return true;
        }
        return false;
    }

    @Override
    protected boolean createMailAttachment(int index, String customerName, String channel,List<PackageItemBean> beans, String branchName, String fileName, boolean ignoreLyOrders) {
        boolean result = false;
        if (customerName.indexOf("浦发银行") != -1 && "小浦金店".equals(channel)){
            String template = "Job:%s createMailAttachmentXp with package:%s branch:%s file:%s channel:%s";
            _logger.info(String.format(template, this.getClass(),beans.toString(),branchName, fileName, channel));
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
                ws.setColumnView(0, 40);
                ws.setColumnView(1, 20);
                ws.setColumnView(2, 40);


                i++;
                // 正文表格
                ws.addCell(new Label(0, i, "银行单号", format3));
                ws.addCell(new Label(1, i, "快递公司", format3));
                ws.addCell(new Label(2, i, "快递单号", format3));

                _logger.info("***itemList size***"+beans.size());
                List<PackageItemBean> itemList = this.mergeItems(beans);
                _logger.info("***after merge itemList size***"+itemList);
                if (!ListTools.isEmptyOrNull(itemList)){
                    for (PackageItemBean each : itemList)
                    {
                        //#351 filter LY orders
                        if (ignoreLyOrders && each.getOutId().startsWith("LY")){
                            continue;
                        } else if(each.getOutId().startsWith("ZS") || each.getOutId().startsWith("A")){
                            continue;
                        }

                        i++;

                        //银行单号
                        String outId = each.getOutId();
                        String citicNo = this.getCiticNoFromOutImport(each);
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
