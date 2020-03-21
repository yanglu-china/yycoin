package com.china.center.oa.sail.dao.impl;

import com.china.center.jdbc.inter.impl.BaseDAO;
import com.china.center.oa.sail.bean.DhResultVO;
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
        String sql = " select a.id,a.depotpartid,a.depotid,a.price,a.stockid,a.dhno,a.createUser,a.productId,a.cg_amount,a.ydh_amount,b.sd_amount,b.zj_hg_amount,b.sccg_cldz,a.ydh_amount-b.zj_hg_amount as bhg_amount,b.sccg_rkfx " +
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
                        String sccg_cldz = rst.getString("sccg_cldz");
                        String createUser = rst.getString("createUser");
                        String stockId = rst.getString("stockid");
                        String depotpartId = rst.getString("depotpartid");
                        String depotId = rst.getString("depotid");
                        double price = rst.getFloat("price");
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
                        wrap.setSccgCldz(sccg_cldz);
                        wrap.setCreateUser(createUser);
                        wrap.setDepotId(depotId);
                        wrap.setDepotpartId(depotpartId);
                        wrap.setPrice(price);
                        result.add(wrap);
                    }
                });

        return result;
    }


    @Override
    public List<DhResultVO> queryDhInfo2() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT ")
                .append("c.id as cid ,c.dhno,c.productId,c.stockid,c.createUser,c.depotid,c.depotpartid,c.price,")
                .append("c.ydh_amount,c.sd_amount,c.zj_cj_amount,c.zj_hg_amount,c.ydh_amount-c.zj_hg_amount as bhg_amount,")
                .append("c.wl_remarks,c.type,")
                .append("d.id as did,d.sccg_rk_type,d.sccg_rkamount, d.sccg_rkck,d.sccg_rkcq, d.sccg_cldz,d.pid ")
                .append("FROM t_center_dh_result c ")
                .append("LEFT JOIN t_center_dh_result_zb d on c.id = d.pid where c.status = '结束' and c.processedFlag = 0");

        final List<DhResultVO> result = new LinkedList<>();

        this.jdbcOperation.query(sb.toString(), new Object[] {},
                new RowCallbackHandler()
                {

                    public void processRow(ResultSet rst)
                            throws SQLException
                    {
                        DhResultVO wrap = new DhResultVO();
                        int resultId = rst.getInt("cid");
                        int zbId = rst.getInt("did");
                        String dhno = rst.getString("dhno");
                        String productId = rst.getString("productId");
                        int ydh_amount = rst.getInt("ydh_amount");
                        int zj_hg_amount = rst.getInt("zj_hg_amount");
                        int bhg_amount = rst.getInt("bhg_amount");
                        int sdAmount = rst.getInt("sd_amount");
                        //入库仓库
                        String sccg_rkfx = rst.getString("sccg_rkck");
                        //入库仓区 TODO
                        String sccg_rkcq = rst.getString("sccg_rkcq");
                        String sccg_cldz = rst.getString("sccg_cldz");
                        String createUser = rst.getString("createUser");
                        String stockId = rst.getString("stockid");
                        String depotpartId = rst.getString("depotpartid");
                        String depotId = rst.getString("depotid");
                        int type = rst.getInt("type");
                        double price = rst.getFloat("price");

                        wrap.setResultId(resultId);
                        wrap.setZbId(zbId);
                        wrap.setStockId(stockId);
                        wrap.setDhNo(dhno);
                        wrap.setProductId(productId);
                        wrap.setYdhAmount(ydh_amount);
                        wrap.setZjHgAmount(zj_hg_amount);
                        wrap.setZjBhgAmount(bhg_amount);
                        wrap.setSdAmount(sdAmount);
                        wrap.setSccgRkfx(sccg_rkfx);
                        wrap.setSccgCldz(sccg_cldz);
                        wrap.setCreateUser(createUser);
                        wrap.setDepotId(depotId);
                        wrap.setDepotpartId(depotpartId);
                        wrap.setPrice(price);
                        wrap.setType(type);
                        result.add(wrap);
                    }
                });

        return result;
    }

    @Override
    public boolean updateProcessedFlag(int id, String outId) {
        String sql = "update t_center_dh_zjb set processedFlag = 1, outId = ? " +
                " where id = ?";

        int i = jdbcOperation.update(sql, outId, id);

        return i != 0;
    }

    @Override
    public boolean updateProcessedFlag2(int id, int flag) {
        String sql = "update t_center_dh_result set processedFlag = ? " +
                " where id = ?";

        int i = jdbcOperation.update(sql, flag, id);

        return i != 0;
    }

    @Override
    public boolean updateOutId(int id, String outId) {
        String sql = "update t_center_dh_result_zb set outid = ? " +
                " where id = ?";

        int i = jdbcOperation.update(sql, outId,id);

        return i != 0;
    }
}
