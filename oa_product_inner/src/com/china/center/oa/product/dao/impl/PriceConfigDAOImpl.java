package com.china.center.oa.product.dao.impl;

import java.util.List;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.PriceConfigBean;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.dao.PriceConfigDAO;
import com.china.center.oa.product.vo.PriceConfigVO;
import com.china.center.tools.TimeTools;

public class PriceConfigDAOImpl extends BaseDAO<PriceConfigBean, PriceConfigVO> implements PriceConfigDAO
{
	public List<PriceConfigBean> querySailPricebyProductId(String productId)
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("PriceConfigBean.productId", "=", productId);
		
		con.addIntCondition("PriceConfigBean.type", "=", ProductConstant.PRICECONFIG_SETTLE);
		
		return this.queryEntityBeansByCondition(con);
	}

	public List<PriceConfigBean> queryMinPricebyProductIdAndIndustryId(
			String productId, String industryId)
	{
		ConditionParse con = new ConditionParse();
		
		con.addWhereStr();
		
		con.addCondition("PriceConfigBean.productId", "=", productId);
		
		con.addIntCondition("PriceConfigBean.type", "=", ProductConstant.PRICECONFIG_SAIL);
		
		con.addCondition("and PriceConfigBean.industryId like '%" + industryId + "%'");
		
		return this.queryEntityBeansByCondition(con);
	}

	@Override
	public boolean updatePrice(String productId, double price) {
		String sql = "update T_CENTER_PRICE_CONFIG set price = ? where productId = ?";

		int i = jdbcOperation.update(sql, price, productId);

		return i != 0;
	}

	@Override
	public boolean updateGsPriceUp(String productId, double gsPriceUp) {
		String sql = "update T_CENTER_PRICE_CONFIG set gsPriceUp = ? where productId = ?";

		int i = jdbcOperation.update(sql, gsPriceUp, productId);

		return i != 0;
	}
}
