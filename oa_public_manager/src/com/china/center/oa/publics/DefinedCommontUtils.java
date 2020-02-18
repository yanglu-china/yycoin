package com.china.center.oa.publics;

import com.china.center.common.MYException;
import com.china.center.jdbc.annotation.Defined;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


/**
 * enhance DefinedCommon
 */
public class DefinedCommontUtils {
    /**
     * 扫描class文件,根据@Defind的(key,value)找到相应的字段值
     * @param clz
     * @param definedKey
     * @param definedValue
     * @return
     * @throws MYException
     */
    public static int getValue(Class clz, String definedKey, String definedValue) throws MYException{
        for(Field field  : clz.getDeclaredFields())
        {
            Defined defined = field.getAnnotation(Defined.class);
            if (defined!= null && Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())){
                String key = defined.key();
                String value = defined.value();
//                System.out.println(key);
//                System.out.println(value);
                if (definedKey.equals(key) && definedValue.equals(value)){
                    Class<?> t = field.getType();
                    try {
                        if (t == int.class) {
                            return field.getInt(null);
                        }
                    }catch(Exception e){e.printStackTrace();}
                }
            }
        }
        throw new MYException("not found "+definedKey+":"+definedValue);
    }

//    public static void main(String[] args){
//        try {
//            int value = DefinedCommontUtils.getValue(FinanceConstant.class, "inbillType", "个人还款");
//            System.out.println(value);
//        }catch(MYException e){
//            e.printStackTrace();
//        }
//    }
}
