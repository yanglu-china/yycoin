/**
 * File Name: StorageRelationListenerSailImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: smartman<br>
 * CreateTime: 2019-07-03<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.listener.impl;


import com.china.center.oa.product.listener.StorageRelationListener;
import com.china.center.oa.product.vo.StorageRelationVO;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.sail.dao.OutDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * StorageRelationListenerProductImpl(产品合成提交后预占库存)
 * 
 * @author smartman
 * @version 2019-07-03
 * @see StorageRelationListenerProductImpl
 * @since 3.0
 */
public class StorageRelationListenerProductImpl implements StorageRelationListener
{
    private final Log _logger = LogFactory.getLog(getClass());

    private OutDAO outDAO = null;

    /**
     * default constructor
     */
    public StorageRelationListenerProductImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.listener.StorageRelationListener#onFindMaySailStorageRelation(com.china.center.oa.product.vs.StorageRelationBean)
     */
    public int onFindPreassignByStorageRelation(StorageRelationBean bean)
    {
        // 商品合成已提交是预占库存的
        int sumInCompose = outDAO.sumNotEndProductInCompose(bean.getProductId(), bean
            .getDepotpartId(), bean.getPrice());

        String template = "***onFindPreassignByStorageRelation with sumInOut:%d  for bean:%s";
        _logger.info(String.format(template, sumInCompose, bean));

        if (sumInCompose < 0)
        {
            return 0;
        }

        return sumInCompose;
    }

    public int onFindPreassignByStorageRelation2(String depotpartId, String productId, String stafferId)
    {
        return 0;
    }
    
    public int onFindInwayByStorageRelation(StorageRelationBean relation)
    {

        return 0;
    }

    public List<StorageRelationVO> onExportOtherStorageRelation()
    {
        return new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.center.china.osgi.publics.ParentListener#getListenerType()
     */
    public String getListenerType()
    {
        return "StorageRelationListener.ProductImpl";
    }

    /**
     * @return the outDAO
     */
    public OutDAO getOutDAO()
    {
        return outDAO;
    }

    /**
     * @param outDAO
     *            the outDAO to set
     */
    public void setOutDAO(OutDAO outDAO)
    {
        this.outDAO = outDAO;
    }
}
