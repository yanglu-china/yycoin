package com.china.center.oa.publics;

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
}
