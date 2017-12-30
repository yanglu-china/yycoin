package com.china.center.oa.publics;

import com.china.center.tools.StringTools;

public class StringUtils {

    /**
     * 返回前n个字符
     * @param str
     * @param n
     * @return
     */
    public static String subString(String str, int n){
        if (str!= null && str.length()>=n) {
            return str.substring(0,n);
        }else{
            return str;
        }
    }

    /**
     * 连接字符串
     * @param str1
     * @param str2
     * @param deliminator
     * @return
     */
    public static String concat(String str1, String str2, String deliminator){
        if (StringTools.isNullOrNone(str1)){
            return str2;
        } else if (StringTools.isNullOrNone(str2)){
            return str1;
        } else{
            return str1+deliminator+str2;
        }
    }
}
