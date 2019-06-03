package com.china.center.oa.finance.dao.impl;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.finance.dao.TaoBaoTokenDAO;
import com.china.center.oa.finance.vs.TaobaoLogisticsBean;
import com.china.center.oa.finance.vs.TaobaoTokenBean;
import com.china.center.oa.sail.bean.PackageBean;

public class TaoBaoTokenDAOImpl extends BaseDAO<TaobaoTokenBean, TaobaoTokenBean> implements TaoBaoTokenDAO {
	
	public List<TaobaoTokenBean> queryLastToken()
	{
		return this.jdbcOperation.queryObjectsBySql("SELECT * FROM t_center_taobao_token ORDER BY createtime DESC LIMIT 0,1").list(this.clazVO);
	}
	
	@Override
	@Transactional
	public void insert(TaobaoTokenBean bean) {
		this.jdbcOperation.save(bean);
	}

	@Override
	public List<TaobaoLogisticsBean> queryTaobaoLogisticsList() {
		
		StringBuilder sqlBuffer = new StringBuilder();
		sqlBuffer.append("SELECT DISTINCT a.id,a.transportNo,c.transport1,d.name AS logisticsName,d.tbcode AS logosticsCode,c.citicNo");
		sqlBuffer.append(" FROM t_center_package a,t_center_package_item b,t_center_out_import c,t_center_express d");
		sqlBuffer.append(" WHERE a.transportno <> '' AND a.id=b.packageid AND b.outId=c.OANo AND d.id=c.transport1 ");
		sqlBuffer.append(" AND a.customerid=651827562 and taobaoflag=0");
		return this.jdbcOperation.queryObjectsBySql(sqlBuffer.toString()).list(TaobaoLogisticsBean.class);
	}

	@Override
	@Transactional
	public void updatePackageStatus(String packageId) {
		String sql = "update t_center_package set taobaoflag=1 where id=?";
		this.jdbcOperation.update(sql,packageId);
		
	}
}
