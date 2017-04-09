//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.china.center.oa.publics.taglib;

import com.china.center.common.taglib.DymOption;
import com.china.center.common.taglib.MapBean;
import com.china.center.oa.publics.listener.QueryListener;
import com.china.center.oa.publics.manager.impl.QueryManagerImpl;
import com.china.center.tools.InnerReflect;
import com.china.center.tools.ReflectException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyDefaultDymOptionImpl implements DymOption {
    public MyDefaultDymOptionImpl() {
    }

    public List<MapBean> getOptionList(String var1) {
        ArrayList var2 = new ArrayList();
        Map var3 = QueryManagerImpl.getStaticListenerMap();
        System.out.println(var1+"***key***"+var3);
        QueryListener var4 = (QueryListener)var3.get(var1);
        if(var4 == null) {
            return var2;
        } else {
            List var5 = var4.getBeanList();
            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
                Object var7 = var6.next();

                try {
                    MapBean var8 = new MapBean();
                    var8.setKey(InnerReflect.get(var7, "id", new Object[0]).toString());
                    var8.setValue(InnerReflect.get(var7, "name", new Object[0]).toString());
                    var2.add(var8);
                } catch (ReflectException var9) {
                    ;
                }
            }

            return var2;
        }
    }
}
