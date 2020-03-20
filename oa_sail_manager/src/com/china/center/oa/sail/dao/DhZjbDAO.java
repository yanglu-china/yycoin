/**
 * File Name: OutDAO.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-11-7<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.sail.dao;


import com.china.center.jdbc.inter.DAO;
import com.china.center.oa.sail.bean.DhResultVO;
import com.china.center.oa.sail.bean.DhZjbBean;
import com.china.center.oa.sail.bean.DhZjbVO;
import java.util.List;


/**
 * DhZjbDAO
 * 
 * @author smartman
 * @version 2018-11-10
 * @see DhZjbDAO
 * @since 1.0
 */
public interface DhZjbDAO extends DAO<DhZjbBean, DhZjbBean>
{
    /**
     * 到货信息
     * @return
     */
    List<DhZjbVO> queryDhInfo();

    List<DhResultVO> queryDhInfo2();

    boolean updateProcessedFlag(int id, String outId);
    boolean updateProcessedFlag2(int id, int flag);
    boolean updateOutId(int id, String outId);
}
