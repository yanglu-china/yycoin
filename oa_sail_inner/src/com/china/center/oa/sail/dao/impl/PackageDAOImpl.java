package com.china.center.oa.sail.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.jdbc.util.PageSeparate;
import com.china.center.oa.sail.bean.PackageBean;
import com.china.center.oa.sail.bean.PrePackageBean;
import com.china.center.oa.sail.dao.PackageDAO;
import com.china.center.oa.sail.vo.PackageVO;
import com.china.center.tools.ListTools;
import com.china.center.tools.TimeTools;

public class PackageDAOImpl extends BaseDAO<PackageBean, PackageVO> implements PackageDAO
{

	public List<PackageVO> queryVOsByCondition(ConditionParse con)
	{
		return this.queryEntityVOsByCondition(con);
	}

	public int countByCon(ConditionParse con)
	{
		String sql = "select count(distinct PackageBean.id) from t_center_package PackageBean, T_CENTER_PACKAGE_ITEM PackageItemBean where PackageBean.id = PackageItemBean.packageId ";
		
		return this.jdbcOperation.queryForInt(sql + con.toString());
	}

	public List<PackageVO> queryVOsByCon(ConditionParse con,
			PageSeparate page)
	{
		String sql = "SELECT distinct CustomerBean.NAME AS customerName, e1.NAME AS transportName1, e2.NAME AS transportName2, PrincipalshipBean.NAME AS locationName, PackageBean.ID, PackageBean.CUSTOMERID, PackageBean.SHIPPING, PackageBean.TRANSPORT1, PackageBean.EXPRESSPAY, PackageBean.TRANSPORT2, PackageBean.TRANSPORTPAY, PackageBean.ADDRESS, PackageBean.RECEIVER, PackageBean.MOBILE, PackageBean.AMOUNT, PackageBean.PRODUCTCOUNT, PackageBean.TOTAL, PackageBean.STATUS, PackageBean.STAFFERNAME, PackageBean.INDUSTRYNAME, PackageBean.DEPARTNAME, PackageBean.LOCATIONID, PackageBean.LOGTIME, PackageBean.PICKUPID, PackageBean.INDEX_POS, PackageBean.SHIPTIME ";
		
		sql += " FROM T_CENTER_PACKAGE PackageBean  LEFT OUTER JOIN T_CENTER_EXPRESS e1 ON (PackageBean.transport1 = e1.id )  LEFT OUTER JOIN T_CENTER_EXPRESS e2 ON (PackageBean.transport2 = e2.id )  LEFT OUTER JOIN T_CENTER_CUSTOMER_MAIN CustomerBean ON (PackageBean.customerId = CustomerBean.id )  LEFT OUTER JOIN T_CENTER_PRINCIPALSHIP PrincipalshipBean ON (PackageBean.locationId = PrincipalshipBean.id ), T_CENTER_PACKAGE_ITEM PackageItemBean";
		
		sql += " where PackageBean.id = PackageItemBean.packageId ";
		
		sql += con.toString();
		
		return this.jdbcOperation.queryObjectsBySqlAndPageSeparate(sql, page, this.clazVO);
	}

	@Override
	public boolean updateStatus(String packageId, int status) {
		String sql = "update t_center_package set status = ?,logTime = ? where id = ?";

		jdbcOperation.update(sql, status, TimeTools.now(),packageId);

		return true;
	}

	@Override
	public List<PackageBean> queryPackagesByOutId(String outId) {
		String sql = "SELECT PackageBean.* from t_center_package PackageBean " +
				"where PackageBean.id in ( SELECT PackageItemBean.packageId FROM t_center_package_item PackageItemBean " +
				"WHERE PackageItemBean.packageId = PackageBean.id AND PackageItemBean.outId = ?)";
		return this.jdbcOperation.queryForListBySql(sql, claz, outId);
	}


	@Override
	public PrePackageBean queryPrePackage(String citicNo, int status) {
		String sql = "select * from t_center_prepackage where citicNo = ? and status = ?";
		List<PrePackageBean> result = this.jdbcOperation.queryForListBySql(sql,PrePackageBean.class,citicNo, status);
		if (ListTools.isEmptyOrNull(result)){
			return null;
		} else{
			return result.get(0);
		}
	}

	@Override
	public boolean updatePrePackageStatus(String citicNo, int status) {
		String sql = "update t_center_prepackage set status = ? where citicNo = ?";

		jdbcOperation.update(sql, status, citicNo);

		return true;
	}
}
