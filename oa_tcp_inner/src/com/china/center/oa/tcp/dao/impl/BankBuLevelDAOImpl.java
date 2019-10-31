/**
 * File Name: TcpApplyDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2011-7-10<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.tcp.dao.impl;


import java.util.ArrayList;
import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.tcp.bean.BankBuLevelBean;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import com.china.center.oa.tcp.dao.BankBuLevelDAO;

public class BankBuLevelDAOImpl extends BaseDAO<BankBuLevelBean, BankBuLevelBean> implements BankBuLevelDAO
{
    @Override
    public List<BankBuLevelBean> queryByBearType(String bearType) {
        List<BankBuLevelBean> result = new ArrayList<BankBuLevelBean>();
        if ("2".equals(bearType)){
            //province manager
            result = jdbcOperation.queryObjectsBySql(
                    "select distinct provinceManagerId, provinceManager from T_CENTER_BANKBU_LEVEL")
                    .setMaxResults(600).list(BankBuLevelBean.class);
            for (BankBuLevelBean bean : result){
                bean.setId(bean.getProvinceManagerId());
                bean.setName(bean.getProvinceManager());
            }
        } else if ("3".equals(bearType)){
            //regionalManager
            result = jdbcOperation.queryObjectsBySql(
                    "select distinct regionalManagerId,regionalManager from T_CENTER_BANKBU_LEVEL")
                    .setMaxResults(600).list(BankBuLevelBean.class);
            for (BankBuLevelBean bean : result){
                bean.setId(bean.getRegionalManagerId());
                bean.setName(bean.getRegionalManager());
            }
        } else if ("4".equals(bearType)){
            //regionalDirector
            result = jdbcOperation.queryObjectsBySql(
                    "select distinct regionalDirectorId,regionalDirector from T_CENTER_BANKBU_LEVEL")
                    .setMaxResults(600).list(BankBuLevelBean.class);
            for (BankBuLevelBean bean : result){
                bean.setId(bean.getRegionalDirectorId());
                bean.setName(bean.getRegionalDirector());
            }
        }

        return result;
    }

    @Override
    public List<BankBuLevelBean> queryByBearTypeAndManager(String bearType, String manager) {
        List<BankBuLevelBean> result = new ArrayList<BankBuLevelBean>();
        if ("2".equals(bearType)){
            //provinceManager
            result = jdbcOperation.queryObjectsBySql(
                    "select id, name from T_CENTER_BANKBU_LEVEL where provinceManagerId='"+manager+"'")
                    .setMaxResults(600).list(BankBuLevelBean.class);
        } else if ("3".equals(bearType)){
            //regionalManager
            result = jdbcOperation.queryObjectsBySql(
                    "select id,name from T_CENTER_BANKBU_LEVEL where regionalManagerId='"+manager+"'")
                    .setMaxResults(600).list(BankBuLevelBean.class);
        } else if ("4".equals(bearType)){
            //regionalDirector
            result = jdbcOperation.queryObjectsBySql(
                    "select id,name from T_CENTER_BANKBU_LEVEL where regionalDirectorId='"+manager+"'")
                    .setMaxResults(600).list(BankBuLevelBean.class);
        }

        return result;
    }

    @Override
    public String queryHighLevelManagerId(String flowKey, int bearType, String stafferId, String originator) {
        List<BankBuLevelBean> result = new ArrayList<BankBuLevelBean>();
        if (TcpFlowConstant.WORK_APPLY_MARKETING.equals(flowKey)) {
            if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_MANAGER) {
                //regionalManager
                result = jdbcOperation.queryObjectsBySql(
                        "select id,regionalManagerId from T_CENTER_BANKBU_LEVEL where id='" + stafferId + "'")
                        .setMaxResults(600).list(BankBuLevelBean.class);
                if (result.size() == 1) {
                    return result.get(0).getRegionalManagerId();
                } else {
                    //#341 考虑到一人多岗情况,优先根据发起人选择
                    for (BankBuLevelBean bean : result) {
                        if (bean.getId().equals(originator)) {
                            return bean.getRegionalManagerId();
                        }
                    }
                }
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_DIRECTOR) {
                //regionalDirector
                result = jdbcOperation.queryObjectsBySql(
                        "select id,regionalDirectorId from T_CENTER_BANKBU_LEVEL where regionalManagerId='" + stafferId + "'")
                        .setMaxResults(600).list(BankBuLevelBean.class);
                if (result.size() == 1) {
                    return result.get(0).getRegionalDirectorId();
                } else {
                    //#341 考虑到一人多岗情况,优先根据发起人选择
                    for (BankBuLevelBean bean : result) {
                        if (bean.getId().equals(originator)) {
                            return bean.getRegionalDirectorId();
                        }
                    }
                }
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_CEO) {
                //manager -> sybmanager
/*                result = jdbcOperation.queryObjectsBySql(
                        "select id, managerId from T_CENTER_BANKBU_LEVEL where regionalDirectorId='" + stafferId + "'")
                        .setMaxResults(600).list(BankBuLevelBean.class);*/
                
                result = jdbcOperation.queryObjectsBySql(
                        "select id, sybmanagerId from T_CENTER_BANKBU_LEVEL where regionalDirectorId='" + stafferId + "'")
                        .setMaxResults(600).list(BankBuLevelBean.class);
                
                if (result.size() == 1) {
                    return result.get(0).getSybmanagerId();
                } else {
                    //#341 考虑到一人多岗情况,优先根据发起人选择
                    for (BankBuLevelBean bean : result) {
                        if (bean.getId().equals(originator)) {
                            return bean.getSybmanagerId();
                        }
                    }
                }
            }
        } else {
            if (bearType == TcpConstanst.TCP_STATUS_PROVINCE_MANAGER) {
                //provinceManager
                result = jdbcOperation.queryObjectsBySql(
                        "select id,provinceManagerId from T_CENTER_BANKBU_LEVEL where id='" + stafferId + "'")
                        .setMaxResults(600).list(BankBuLevelBean.class);
                if (result.size() == 1) {
                    return result.get(0).getProvinceManagerId();
                } else {
                    //#341 考虑到一人多岗情况,优先根据发起人选择
                    for (BankBuLevelBean bean : result) {
                        if (bean.getId().equals(originator)) {
                            return bean.getProvinceManagerId();
                        }
                    }
                }
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_MANAGER) {
                //regionalManager
                result = jdbcOperation.queryObjectsBySql(
                        "select id,regionalManagerId from T_CENTER_BANKBU_LEVEL where provinceManagerId='" + stafferId + "'")
                        .setMaxResults(600).list(BankBuLevelBean.class);
                if (result.size() == 1) {
                    return result.get(0).getRegionalManagerId();
                } else {
                    //#341 考虑到一人多岗情况,优先根据发起人选择
                    for (BankBuLevelBean bean : result) {
                        if (bean.getId().equals(originator)) {
                            return bean.getRegionalManagerId();
                        }
                    }
                }
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_DIRECTOR) {
                //regionalDirector
                result = jdbcOperation.queryObjectsBySql(
                        "select id,regionalDirectorId from T_CENTER_BANKBU_LEVEL where regionalManagerId='" + stafferId + "'")
                        .setMaxResults(600).list(BankBuLevelBean.class);
                if (result.size() == 1) {
                    return result.get(0).getRegionalDirectorId();
                } else {
                    //#341 考虑到一人多岗情况,优先根据发起人选择
                    for (BankBuLevelBean bean : result) {
                        if (bean.getId().equals(originator)) {
                            return bean.getRegionalDirectorId();
                        }
                    }
                }
            } else if (bearType == TcpConstanst.TCP_STATUS_REGIONAL_CEO) {
                //manager -> sybmanager
/*                result = jdbcOperation.queryObjectsBySql(
                        "select id,managerId from T_CENTER_BANKBU_LEVEL where regionalDirectorId='" + stafferId + "'")
                        .setMaxResults(600).list(BankBuLevelBean.class);*/
                
                result = jdbcOperation.queryObjectsBySql(
                        "select id,sybmanagerId from T_CENTER_BANKBU_LEVEL where regionalDirectorId='" + stafferId + "'")
                        .setMaxResults(600).list(BankBuLevelBean.class);
                
                if (result.size() == 1) {
                    return result.get(0).getManagerId();
                } else {
                    //#341 考虑到一人多岗情况,优先根据发起人选择
                    for (BankBuLevelBean bean : result) {
                        if (bean.getId().equals(originator)) {
                            return bean.getManagerId();
                        }
                    }
                }
            }
        }
        return "";
    }
    
    public String queryManagerId(String flowKey, String stafferId, String originator) {
    	String rst = "";
    	List<BankBuLevelBean> result = new ArrayList<BankBuLevelBean>();
        //Manager
        result = jdbcOperation.queryObjectsBySql(
                "select id,managerId from T_CENTER_BANKBU_LEVEL where id='" + stafferId + "'")
                .setMaxResults(600).list(BankBuLevelBean.class);
        if (result.size() > 0) {
        	rst = result.get(0).getManagerId();
        	if(result.size()>1){
                for (BankBuLevelBean bean : result) {
                    if (bean.getId().equals(originator)) {
                        rst = bean.getManagerId();
                        break;
                    }
                }
        	}
        } 
        
        return rst;
    }
}
