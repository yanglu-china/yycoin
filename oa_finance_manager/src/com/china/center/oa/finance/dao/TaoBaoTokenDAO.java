package com.china.center.oa.finance.dao;

import java.util.List;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.finance.vs.TaobaoLogisticsBean;
import com.china.center.oa.finance.vs.TaobaoTokenBean;

public interface TaoBaoTokenDAO extends DAO<TaobaoTokenBean, TaobaoTokenBean> {

	public List<TaobaoTokenBean> queryLastToken(String customerId);

	public void insert(TaobaoTokenBean bean);

	public List<TaobaoLogisticsBean> queryTaobaoLogisticsList();

	public void updatePackageStatus(String packageId);

}
