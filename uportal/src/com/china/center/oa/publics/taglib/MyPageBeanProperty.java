//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.china.center.oa.publics.taglib;

import com.china.center.common.taglib.BodyTagCenterSupport;
import com.china.center.common.taglib.WriteBeanProperty;
import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.annotation.Html;
import java.lang.reflect.Field;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

public class MyPageBeanProperty extends BodyTagCenterSupport {
    private int width = 15;
    private String align = "left";
    private String index = "";
    private String trId = "";
    private String field = "";
    private String endTag = "";
    private String value = "";
    private String innerString = "";
    private String outString = "";
    private Html html = null;
    private int cell = 1;

    public MyPageBeanProperty() {
    }

    public int getLastWidth() {
        byte tatol = 100;
        Integer ints = (Integer)this.pageContext.getAttribute("CENTER_CELLS_INIT_ISP_CELLS_INIT" + this.index);
        int ll = tatol / ints.intValue() - this.width;
        return ll > 0?ll:5;
    }

    public int doStartTag() throws JspException {
        StringBuffer buffer = new StringBuffer();
        int allIndex = ((Integer)this.pageContext.getAttribute("CENTER_CELL_INDEX_ISP_CELL_INDEX" + this.index)).intValue();
        int cells = ((Integer)this.pageContext.getAttribute("CENTER_CELLS_INIT_ISP_CELLS_INIT" + this.index)).intValue();
        int trIndex = ((Integer)this.pageContext.getAttribute("CENTER_TRS_INDEX_ISP_TRS_INDEX" + this.index)).intValue();
        String claz = (String)this.pageContext.getAttribute("CENTER_BEAN_CLASS_ISP_BEAN_CLASS");
        int opr = ((Integer)this.pageContext.getAttribute("CENTER_BEAN_CLASS_ISP_BEAN_OPR")).intValue();
        HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();

        Object oval = null;
        if(opr == 1 || opr == 2) {
            oval = request.getAttribute("bean");
        }

        Class cla = null;
        try{
            if (oval == null) {
                cla = Class.forName(claz);
            } else{
                cla = oval.getClass();
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        Field var20 = BeanTools.getFieldIgnoreCase(this.field, cla);
        System.out.println("***opr***"+opr+"***field***"+this.field);
        System.out.println("claz***"+claz+"***cla***"+cla+"***oval***"+oval);
        if(this.field == null || var20 == null) {
            throw new JspException(this.field + " not exist in " + claz);
        } else {
            this.html = BeanTools.getPropertyHtml(var20);
            if(this.html == null) {
                throw new JspException(this.field + " do not have Html");
            } else {
                if(allIndex % cells == 0 || this.cell <= 0) {
//                    cellk = null;
                    String var21;
                    if(trIndex % 2 == 0) {
                        var21 = "content1";
                    } else {
                        var21 = "content2";
                    }

                    String var22 = "";
                    if(!this.isNullOrNone(this.trId)) {
                        var22 = "id=\'" + this.trId + "\'";
                    }

                    buffer.append("<tr class=\'" + var21 + "\' id=\'" + var20.getName() + "_TR\'").append(var22).append(">\r\n");
                }

                int var23 = this.cell <= 0?cells * 2 - 1:this.cell * 2 - 1;
                if((opr == 1 || opr == 2) && this.isNullOrNone(this.value)) {
                    oval = request.getAttribute("bean");
                    if(oval != null) {
                        var20.setAccessible(true);

                        try {
                            Object var24 = var20.get(oval);
                            if(var24 != null) {
                                this.value = this.escapeHtml(var24.toString());
                            }
                        } catch (Exception var17) {
                            var17.printStackTrace();
                        }
                    }
                }

                String[] var25 = WriteBeanProperty.writeProperty(cla, this.field, this.value, var23, this.innerString, this.outString, this.width + "%", this.getLastWidth() + "%", this.align, request.getContextPath(), opr);
                this.value = "";
                this.endTag = var25[1];
                this.writeContext(buffer.toString() + var25[0]);
                return 1;
            }
        }
    }

    public int doEndTag() throws JspException {
        StringBuffer buffer = new StringBuffer();
        int allIndex = ((Integer)this.pageContext.getAttribute("CENTER_CELL_INDEX_ISP_CELL_INDEX" + this.index)).intValue() + this.cell;
        int cells = ((Integer)this.pageContext.getAttribute("CENTER_CELLS_INIT_ISP_CELLS_INIT" + this.index)).intValue();
        int trIndex = ((Integer)this.pageContext.getAttribute("CENTER_TRS_INDEX_ISP_TRS_INDEX" + this.index)).intValue();
        this.writeEnd(buffer);
        if(allIndex % cells == 0 || this.cell <= 0) {
            buffer.append("</tr>");
            this.pageContext.setAttribute("CENTER_TRS_INDEX_ISP_TRS_INDEX" + this.index, new Integer(trIndex + 1));
        }

        this.pageContext.setAttribute("CENTER_CELL_INDEX_ISP_CELL_INDEX" + this.index, new Integer(allIndex));
        this.writeContext(buffer.toString());
        return 6;
    }

    private String escapeHtml(String value) {
        if(value != null && value.length() != 0) {
            StringBuffer result = null;
            String filtered = null;

            for(int i = 0; i < value.length(); ++i) {
                filtered = null;
                switch(value.charAt(i)) {
                    case '\"':
                        filtered = "&quot;";
                        break;
                    case '&':
                        filtered = "&amp;";
                        break;
                    case '\'':
                        filtered = "&#39;";
                        break;
                    case '<':
                        filtered = "&lt;";
                        break;
                    case '>':
                        filtered = "&gt;";
                }

                if(result == null) {
                    if(filtered != null) {
                        result = new StringBuffer(value.length() + 50);
                        if(i > 0) {
                            result.append(value.substring(0, i));
                        }

                        result.append(filtered);
                    }
                } else if(filtered == null) {
                    result.append(value.charAt(i));
                } else {
                    result.append(filtered);
                }
            }

            return result == null?value:result.toString();
        } else {
            return value;
        }
    }

    private void writeEnd(StringBuffer buffer) {
        String line = "\r\n";
        buffer.append(this.endTag).append(line);
    }

    public String getIndex() {
        return this.index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getAlign() {
        return this.align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getTrId() {
        return this.trId;
    }

    public void setTrId(String trId) {
        this.trId = trId;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInnerString() {
        return this.innerString;
    }

    public void setInnerString(String innerString) {
        this.innerString = innerString;
    }

    public int getCell() {
        return this.cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public String getOutString() {
        return this.outString;
    }

    public void setOutString(String outString) {
        this.outString = outString;
    }
}
