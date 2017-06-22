package com.china.center.oa.publics.taglib;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.china.center.common.taglib.*;
import com.china.center.jdbc.annotation.Defined;
import com.china.center.oa.budget.constant.BudgetConstant;
import com.china.center.oa.commission.constant.BlackConstant;
import com.china.center.oa.customer.constant.ClientConstant;
import com.china.center.oa.customer.constant.CreditConstant;
import com.china.center.oa.customer.constant.CustomerConstant;
import com.china.center.oa.finance.constant.BackPayApplyConstant;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.constant.StockPayApplyConstant;
import com.china.center.oa.gm.constant.GroupConstant;
import com.china.center.oa.gm.constant.MailConstant;
import com.china.center.oa.product.constant.*;
import com.china.center.oa.publics.bean.EnumBean;
import com.china.center.oa.publics.bean.JobConstant;
import com.china.center.oa.publics.constant.*;
import com.china.center.oa.publics.dao.EnumDAO;
import com.china.center.oa.publics.manager.impl.DefaultDymOptionImpl;
import com.china.center.oa.sail.constanst.*;
import com.china.center.oa.stock.constant.StockConstant;
import com.china.center.oa.tax.constanst.CheckConstant;
import com.china.center.oa.tax.constanst.FinaConstant;
import com.china.center.oa.tax.constanst.TaxConstanst;
import com.china.center.oa.tcp.constanst.TcpConstanst;
import com.china.center.oa.tcp.constanst.TcpFlowConstant;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import com.china.center.common.taglib.MapBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.List;


public class MyPageSelectOption extends PageSelectOption {
    private final Log _logger = LogFactory.getLog(getClass());
    private EnumDAO enumDAO = null;

    public MyPageSelectOption() {}

    public void init(){
        dymOption = new DefaultDymOptionImpl();
        if (optionMap.isEmpty()){
            List<Class> constantClasses = new ArrayList<Class>();

            constantClasses.add(AlarmConstant.class);

            constantClasses.add(BackPayApplyConstant.class);
            constantClasses.add(BlackConstant.class);
            constantClasses.add(BudgetConstant.class);

            constantClasses.add(CheckConstant.class);
            constantClasses.add(ClientConstant.class);
            constantClasses.add(CustomerConstant.class);
            constantClasses.add(CreditConstant.class);
            constantClasses.add(CommonConstant.class);
            constantClasses.add(ComposeConstant.class);

            constantClasses.add(DepotConstant.class);
            constantClasses.add(DutyConstant.class);

            constantClasses.add(FinanceConstant.class);
            constantClasses.add(FinaConstant.class);
            constantClasses.add(TaxConstanst.class);

            constantClasses.add(GroupConstant.class);

            constantClasses.add(JobConstant.class);

            constantClasses.add(InvoiceConstant.class);

            constantClasses.add(MailConstant.class);

            constantClasses.add(OutConstant.class);
            constantClasses.add(OutImportConstant.class);
            constantClasses.add(OperationConstant.class);

            constantClasses.add(PublicConstant.class);
            constantClasses.add(PriceChangeConstant.class);
            constantClasses.add(ProviderConstant.class);
            constantClasses.add(ProductApplyConstant.class);
            constantClasses.add(ProductConstant.class);
            constantClasses.add(PromotionConstant.class);

            constantClasses.add(SailConstant.class);
            constantClasses.add(ShipConstant.class);
            constantClasses.add(StorageConstant.class);
            constantClasses.add(StockConstant.class);
            constantClasses.add(StockPayApplyConstant.class);
            constantClasses.add(StafferConstant.class);

            constantClasses.add(TcpConstanst.class);
            constantClasses.add(TcpFlowConstant.class);

            //refer to DefinedTools.java
            for (Class clz: constantClasses){
                for(Field field  : clz.getDeclaredFields())
                {
                    Defined defined = field.getAnnotation(Defined.class);
                    if (defined!= null && Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())){
                        String key = defined.key();
                        List<MapBean> mapBeanList = optionMap.get(key);
                        if (mapBeanList == null){
                            mapBeanList = new ArrayList<MapBean>();
                            optionMap.put(key, mapBeanList);
                        }
                        MapBean mapBean = new MapBean();
                        Class<?> t = field.getType();
                        try {
                            if (t == int.class) {
                                mapBean.setKey(String.valueOf(field.getInt(null)));
                            } else if (t == double.class) {
                                mapBean.setKey(String.valueOf(field.getDouble(null)));
                            } else if (t == String.class) {
                                mapBean.setKey(String.valueOf(field.get(null)));
                            }
                        }catch(Exception e){e.printStackTrace();}

                        mapBean.setValue(defined.value());
                        mapBeanList.add(mapBean);
                    }
                }
            }

            List<EnumBean> enumBeanList = this.enumDAO.listEntityBeans();
            for (EnumBean enumBean: enumBeanList){
                List<MapBean> mapBeanList = optionMap.get(enumBean.getType());
                if (mapBeanList == null){
                    mapBeanList = new ArrayList<MapBean>();
                    optionMap.put(enumBean.getType(), mapBeanList);
                }
                MapBean mapBean = new MapBean();
                mapBean.setKey(enumBean.getKey());
                mapBean.setValue(enumBean.getValue());
                mapBeanList.add(mapBean);
            }
        }
        _logger.info("****MyPageSelectOption init***"+optionMap);

    }

    public EnumDAO getEnumDAO() {
        return enumDAO;
    }

    public void setEnumDAO(EnumDAO enumDAO) {
        this.enumDAO = enumDAO;
    }
}

