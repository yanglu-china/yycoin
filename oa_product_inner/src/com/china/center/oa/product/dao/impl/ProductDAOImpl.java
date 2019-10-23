/**
 * File Name: ProductDAOImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao.impl;


import java.util.List;

import com.china.center.jdbc.annosql.tools.BeanTools;
import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.TwthProductBean;
import com.china.center.oa.product.dao.ProductDAO;
import com.china.center.oa.product.vo.ProductVO;
import com.china.center.tools.ListTools;


/**
 * ProductDAOImpl
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductDAOImpl
 * @since 1.0
 */
public class ProductDAOImpl extends BaseDAO<ProductBean, ProductVO> implements ProductDAO
{
    public boolean updateStatus(String id, int status)
    {
        return this.jdbcOperation.updateField("status", status, id, claz) > 0;
    }

    public ProductBean findByName(String name)
    {
        ConditionParse con = new ConditionParse();
        con.addWhereStr();

        con.addCondition("name", "=", name);

        List<ProductBean> productList = queryEntityBeansByCondition(con.toString());

        if (productList.size() > 0)
        {
            return productList.get(0);
        }

        return null;
    }

	@Override
	public int getmaxMidName(String refProductId)
	{
		String sql = "select max(midName) from " + BeanTools.getTableName(claz) + " where refProductId = ?";
		
		return this.jdbcOperation.queryForInt(sql, refProductId);
	}

    @Override
    public List<ProductBean> queryBomByProductId(String s) {
        String sql = "select product.* from t_center_product product left join t_center_productbom bom on product.id=bom.subProductId where productId="+s;

        return this.jdbcOperation.queryForListBySql(sql, claz);
    }

    @Override
    public TwthProductBean queryTwProduct(String productId) {
        String sql = "select * from t_center_twthproduct  where productId="+productId;

        List<TwthProductBean> list = this.jdbcOperation.queryForListBySql(sql, TwthProductBean.class);
        if (ListTools.isEmptyOrNull(list)){
            return null;
        } else{
            return list.get(0);
        }
    }
}
