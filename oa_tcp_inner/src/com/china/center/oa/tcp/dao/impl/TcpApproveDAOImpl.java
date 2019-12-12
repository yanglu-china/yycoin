/**
 * File Name: TcpApproveDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao.impl;


import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tcp.bean.TcpApproveBean;
import com.china.center.oa.tcp.dao.TcpApproveDAO;
import com.china.center.oa.tcp.vo.TcpApproveVO;
import java.util.List;


/**
 * TcpApproveDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-7-20
 * @see TcpApproveDAOImpl
 * @since 3.0
 */
public class TcpApproveDAOImpl extends BaseDAO<TcpApproveBean, TcpApproveVO> implements TcpApproveDAO
{

    @Override
    public List<TcpApproveBean> queryPreInvoiceType(int status,String customerId) {
        String sql = "select TcpApproveBean.* from t_center_tcpapprove TcpApproveBean left join t_center_preinvoice PreInvoiceBean " +
                "on TcpApproveBean.applyId=PreInvoiceBean.id where TcpApproveBean.type in(22,24) and TcpApproveBean.status=? " +
                "and PreInvoiceBean.customerId= ? order by TcpApproveBean.logTime desc;";

        return this.jdbcOperation.queryForListBySql(sql, claz,status, customerId);
    }

    @Override
    public boolean updateApproverId(String id, String approverId)
    {
        this.jdbcOperation.updateField("approverId", approverId, id, claz);

        return true;
    }
}
