/**
 * File Name: FlowLogDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-9-20<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.publics.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.china.center.jdbc.inter.IbatisDaoSupport;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.tools.TimeTools;


/**
 * FlowLogDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-9-20
 * @see FlowLogDAOImpl
 * @since 1.0
 */
public class FlowLogDAOImpl extends BaseDAO<FlowLogBean, FlowLogBean> implements FlowLogDAO
{

    private IbatisDaoSupport ibatisDaoSupport = null;

    public FlowLogBean findLastLog(String refId)
    {
        ConditionParse con = new ConditionParse();

        con.addWhereStr();

        con.addCondition("fullId", "=", refId);

        con.addCondition("order by id desc");

        List<FlowLogBean> list = this.queryEntityBeansByCondition(con);

        if (list.size() > 0)
        {
            return list.get(0);
        }

        return null;
    }
    
    public String getApproveTime(String fullId, String afterStatus){
        Map<String, String> paramterMap = new HashMap<String, String>();

        paramterMap.put("fullId", fullId);
        paramterMap.put("afterStatus", afterStatus);

        Object time = getIbatisDaoSupport().queryForObject(
            "FlowLogDAO.getApproveTime", paramterMap);

        return time==null?"":(String)time;
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
