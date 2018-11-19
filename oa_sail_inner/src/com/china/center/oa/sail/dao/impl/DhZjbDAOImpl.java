package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.DhZjbBean;
import com.china.center.oa.sail.bean.DhZjbVO;
import com.china.center.oa.sail.dao.DhZjbDAO;
import org.springframework.jdbc.core.RowCallbackHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 18-11-10
 * Time: 下午9:16
 * To change this template use File | Settings | File Templates.
 */
public class DhZjbDAOImpl extends BaseDAO<DhZjbBean, DhZjbBean> implements DhZjbDAO {
    @Override
    public List<DhZjbVO> queryDhInfo() {
        String sql = " select a.id,a.stockid,a.dhno,a.createUser,a.productId,a.cg_amount,a.ydh_amount,b.sd_amount,b.zj_hg_amount,a.ydh_amount-b.zj_hg_amount as bhg_amount,b.sccg_rkfx " +
                "from t_center_dh_zjb a left join t_center_dh_result b on a.dhno=b.dhno and a.productid=b.productid " +
                "where a.status='结束' and a.processedFlag=0";


        final List<DhZjbVO> result = new LinkedList<>();

        this.jdbcOperation.query(sql, new Object[] {},
                new RowCallbackHandler()
                {

                    public void processRow(ResultSet rst)
                            throws SQLException
                    {
                        DhZjbVO wrap = new DhZjbVO();
                        int id = rst.getInt("id");
                        String dhno = rst.getString("dhno");
                        String productId = rst.getString("productId");
                        int cg_amount = rst.getInt("cg_amount");
                        int ydh_amount = rst.getInt("ydh_amount");
                        int zj_hg_amount = rst.getInt("zj_hg_amount");
                        int bhg_amount = rst.getInt("bhg_amount");
                        int sdAmount = rst.getInt("sd_amount");
                        String sccg_rkfx = rst.getString("sccg_rkfx");
                        String createUser = rst.getString("createUser");
                        String stockId = rst.getString("stockid");
                        wrap.setId(id);
                        wrap.setStockId(stockId);
                        wrap.setDhNo(dhno);
                        wrap.setProductId(productId);
                        wrap.setCgAmount(cg_amount);
                        wrap.setYdhAmount(ydh_amount);
                        wrap.setZjHgAmount(zj_hg_amount);
                        wrap.setZjBhgAmount(bhg_amount);
                        wrap.setSdAmount(sdAmount);
                        wrap.setSccgRkfx(sccg_rkfx);
                        wrap.setCreateUser(createUser);
                        result.add(wrap);
                    }
                });

        return result;
    }

    @Override
    public boolean updateProcessedFlag(int id) {
        String sql = "update t_center_dh_zjb set processedFlag = 1 " +
                " where id = ?";

        int i = jdbcOperation.update(sql, id);

        return i != 0;
    }
}
