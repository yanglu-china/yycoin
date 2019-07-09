/**
 * File Name: FinanceDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-2-6<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tax.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.tax.bean.FinanceBean;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tax.dao.FinanceDAO;
import com.china.center.oa.tax.vo.FinanceVO;


/**
 * FinanceDAOImpl
 * 
 * @author ZHUZHU
 * @version 2011-2-6
 * @see FinanceDAOImpl
 * @since 1.0
 */
public class FinanceDAOImpl extends BaseDAO<FinanceBean, FinanceVO> implements FinanceDAO
{
    private final Log _logger = LogFactory.getLog(getClass());

    private IbatisDaoSupport ibatisDaoSupport = null;

    public boolean updateCheck(String id, String reason)
    {
        String sql = BeanTools.getUpdateHead(claz) + "set status = ?, checks = ? where id = ?";

        this.jdbcOperation.update(sql, TaxConstanst.FINANCE_STATUS_CHECK, reason, id);

        return true;
    }

    public boolean updateMonthIndex(String id, int monthIndex)
    {
        String sql = BeanTools.getUpdateHead(claz) + "set monthIndex = ? where id = ?";

        this.jdbcOperation.update(sql, monthIndex, id);

        return true;
    }

    public int updateLockToEnd(String beginTime, String endTime)
    {
        String sql = BeanTools.getUpdateHead(claz)
                     + "set locks = ? where financeDate >= ? and financeDate <= ?";

        return this.jdbcOperation.update(sql, TaxConstanst.FINANCE_LOCK_YES, beginTime, endTime);
    }

    public int updateLockToBegin(String beginTime, String endTime)
    {
        String sql = BeanTools.getUpdateHead(claz)
                     + "set locks = ? where financeDate >= ? and financeDate <= ?";

        return this.jdbcOperation.update(sql, TaxConstanst.FINANCE_LOCK_NO, beginTime, endTime);
    }

    public List<FinanceBean> queryRefFinanceItemByBillId(String billId)
    {
        ConditionParse condtion = new ConditionParse();
        condtion.addWhereStr();
        condtion.addCondition("refId", "=", billId);

        List<FinanceBean> result1 = this.queryEntityBeansByCondition(condtion);

        condtion.clear();
        condtion.addWhereStr();
        condtion.addCondition("refBill", "=", billId);

        List<FinanceBean> result2 = this.queryEntityBeansByCondition(condtion);

        return sumList(result1, result2);
    }

    public List<FinanceBean> queryRefFinanceItemByOutId(String outId)
    {
        ConditionParse condtion = new ConditionParse();
        condtion.addWhereStr();
        condtion.addCondition("refId", "=", outId);

        List<FinanceBean> result1 = this.queryEntityBeansByCondition(condtion);

        condtion.clear();
        condtion.addWhereStr();
        condtion.addCondition("refOut", "=", outId);

        List<FinanceBean> result2 = this.queryEntityBeansByCondition(condtion);

        return sumList(result1, result2);
    }

    public List<FinanceBean> queryRefFinanceItemByRefId(String refId)
    {
        ConditionParse condtion = new ConditionParse();
        condtion.addWhereStr();
        condtion.addCondition("refId", "=", refId);

        return this.queryEntityBeansByCondition(condtion);
    }

    public List<FinanceBean> queryRefFinanceItemByStockId(String stockId)
    {
        ConditionParse condtion = new ConditionParse();
        condtion.addWhereStr();
        condtion.addCondition("refId", "=", stockId);

        List<FinanceBean> result1 = this.queryEntityBeansByCondition(condtion);

        condtion.clear();
        condtion.addWhereStr();
        condtion.addCondition("refStock", "=", stockId);

        List<FinanceBean> result2 = this.queryEntityBeansByCondition(condtion);

        return sumList(result1, result2);
    }

    private List<FinanceBean> sumList(List<FinanceBean> result1, List<FinanceBean> result2)
    {
        List<FinanceBean> result = new ArrayList();

        result.addAll(result1);

        for (FinanceBean each : result2)
        {
            if ( !result.contains(each))
            {
                result.add(each);
            }
        }

        return result;
    }

    public int findMaxMonthIndexByOut(String beginDate, String endDate)
    {
        String sql = "select max(monthIndex) from " + BeanTools.getTableName(claz)
                     + " where financeDate >= ? and financeDate <= ?";

        Connection con = null;

        PreparedStatement ps = null;

        ResultSet rs = null;

        try
        {
            con = this.jdbcOperation.getDataSource().getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, beginDate);
            ps.setString(2, endDate);

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            throw new RuntimeException(e);
        }
        finally
        {
            if (rs != null)
            {
                try
                {
                    rs.close();
                }
                catch (Throwable e)
                {
                    _logger.error(e, e);
                }
            }

            if (ps != null)
            {
                try
                {
                    ps.close();
                }
                catch (Throwable e)
                {
                    _logger.error(e, e);
                }
            }

            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (Throwable e)
                {
                    _logger.error(e, e);
                }
            }
        }
    }
    
    /**
     * 清除费用凭证, (此方法耗时太长，原因未知，)
     * @param id 报销或申请的id
     * @return
     */
    public int clearFinance(String id)
    {
    	long t1 = System.currentTimeMillis();
    	_logger.debug("清除费用凭证，start: "+(Calendar.getInstance().getTime()));
    	int rst = 0;
    	StringBuffer sqlBuffer = new StringBuffer();

        Connection con = null;

        Statement st = null;

        try
        {
        	int n1 = 0;
        	int n2 = 0;
            con = this.jdbcOperation.getDataSource().getConnection();

            con.setAutoCommit(false);
			st = con.createStatement();
			
			sqlBuffer = new StringBuffer();
			sqlBuffer.append(" delete from t_center_financeitem");
			sqlBuffer.append(" where pid in (select id from t_center_finance where refId='"+id+"')");
			n1 = st.executeUpdate(sqlBuffer.toString());
			
			rst += n1;

            sqlBuffer = new StringBuffer();
            sqlBuffer.append("delete from t_center_finance where refId='"+id+"'");
			n2 = st.executeUpdate(sqlBuffer.toString());
			
			rst += n2;
			
			con.commit();
			
			_logger.debug("清除费用凭证，id: "+id+",t_center_financeitem 删除 "+ n1 + ", t_center_finance 删除 "+n2);
        }
        catch (Exception e)
        {
            _logger.error(e, e);

            throw new RuntimeException(e);
        }
        finally
        {

            if (st != null)
            {
                try
                {
                    st.close();
                }
                catch (Throwable e)
                {
                    _logger.error(e, e);
                }
            }

            if (con != null)
            {
                try
                {
                    con.close();
                }
                catch (Throwable e)
                {
                    _logger.error(e, e);
                }
            }
        }
        long t2 = System.currentTimeMillis();
        
        _logger.debug("清除费用凭证，time elapse (ms): "+(t2-t1));
        
        return rst;
    }

    public int findMaxMonthIndexByInner(String beginDate, String endDate)
    {
        String sql = "select max(monthIndex) from " + BeanTools.getTableName(claz)
                     + " where financeDate >= ? and financeDate <= ?";

        return this.jdbcOperation.queryForInt(sql, beginDate, endDate);
    }

    public List<String> queryDuplicateMonthIndex(String date)
    {
        Map<String, String> paramterMap = new HashMap();

        paramterMap.put("beginTime", date + " 00:00:00");

        paramterMap.put("endTime", date + " 23:59:59");

        List<String> result = getIbatisDaoSupport().queryForList(
            "FinanceDAOImpl.queryDuplicateMonthIndex", paramterMap);

        return result;
    }

    /**
     * @return the ibatisDaoSupport
     */
    public IbatisDaoSupport getIbatisDaoSupport()
    {
        return ibatisDaoSupport;
    }

    /**
     * @param ibatisDaoSupport
     *            the ibatisDaoSupport to set
     */
    public void setIbatisDaoSupport(IbatisDaoSupport ibatisDaoSupport)
    {
        this.ibatisDaoSupport = ibatisDaoSupport;
    }

}
