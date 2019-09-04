package com.china.center.oa.publics;

import com.china.center.tools.ListTools;

import java.util.List;

public final class CollectionUtils {

    /**
     * find item by Id
     * @param items
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends IdInterface> T find(List<T> items, String id){
        if (ListTools.isEmptyOrNull(items)){
            return null;
        }
        for (T item: items){
            if (item.getId().equals(id)){
                return item;
            }
        }
        return null;
    }
}
