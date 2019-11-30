/**
 * File Name: ProductDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-8-15<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.product.bean.InvoiceKpBean;
import com.china.center.oa.product.bean.ProductBean;
import com.china.center.oa.product.bean.TwthProductBean;
import com.china.center.oa.product.vo.ProductVO;

import java.util.List;


/**
 * ProductDAO
 * 
 * @author ZHUZHU
 * @version 2010-8-15
 * @see ProductDAO
 * @since 1.0
 */
public interface ProductDAO extends DAO<ProductBean, ProductVO>
{
    boolean updateStatus(String id, int status);

    ProductBean findByName(String name);
    
    int getmaxMidName(String refProductId);

    List<ProductBean> queryBomByProductId(String productId);

    TwthProductBean queryTwProduct(String productId);

    InvoiceKpBean queryInvoiceKp(String spmc);
}
