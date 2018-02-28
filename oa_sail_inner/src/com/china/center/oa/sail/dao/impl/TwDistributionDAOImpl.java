package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.TwDistributionBean;
import com.china.center.oa.sail.dao.TwDistributionDAO;
import com.china.center.oa.sail.vo.TwDistributionVO;

public class TwDistributionDAOImpl extends BaseDAO<TwDistributionBean, TwDistributionVO> implements
		TwDistributionDAO
{
	@Override
	public boolean updateOutboundDate(String id, String outboundDate) {
		String sql = "update t_center_twdistribution set outboundDate = ? where outid = ?";

		int i = jdbcOperation.update(sql, outboundDate, id);

		if (i == 0)
		{
			return false;
		}else
		{
			return true;
		}
	}

}
