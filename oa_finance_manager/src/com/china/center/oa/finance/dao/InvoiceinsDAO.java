/**
 * File Name: InvoiceinsDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-1-1<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.finance.dao;


import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.vo.InvoiceinsVO;


/**
 * InvoiceinsDAO
 * 
 * @author ZHUZHU
 * @version 2011-1-1
 * @see InvoiceinsDAO
 * @since 3.0
 */
public interface InvoiceinsDAO extends DAO<InvoiceinsBean, InvoiceinsVO>
{
	int countInvoiceinsByConstion(ConditionParse condition);

    List<InvoiceinsVO> queryInvoiceinsByConstion(ConditionParse condition,
                                                   PageSeparate page);

    String getLatestPrintSignal();

    void updatePrintStatus();
}
