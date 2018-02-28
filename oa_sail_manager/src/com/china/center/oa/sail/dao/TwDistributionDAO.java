package com.china.center.oa.sail.dao;

import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.TwDistributionBean;
import com.china.center.oa.sail.vo.TwDistributionVO;

public interface TwDistributionDAO extends DAO<TwDistributionBean, TwDistributionVO>
{
    boolean updateOutboundDate(String id, String outboundDate);
}
