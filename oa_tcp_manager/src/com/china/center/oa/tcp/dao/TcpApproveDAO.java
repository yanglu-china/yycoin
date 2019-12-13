/**
 * File Name: TcpApproveDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.vo.TcpApproveVO;

import java.util.List;


/**
 * TcpApproveDAO
 * 
 * @author ZHUZHU
 * @version 2011-7-20
 * @see TcpApproveDAO
 * @since 3.0
 */
public interface TcpApproveDAO extends DAO<TcpApproveBean, TcpApproveVO>
{
        List<TcpApproveBean> queryPreInvoiceType(int status,String customerId);

        boolean updateApproverId(String id, String approverId);
}
