package com.china.center.oa.publics;

import com.china.center.tools.StringTools;

import java.util.regex.Pattern;

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

    /**
     * 从源字符串中截取deliminator1和deliminator2之间的部分
     * @param source
     * @param deliminator1
     * @param deliminator2
     * @return
     */
    public static String extract(String source, String deliminator1, String deliminator2){
        if (!StringTools.isNullOrNone(source)){
            String[] arr1 = source.split(deliminator1);
            if (arr1.length >= 2){
                String part2 = arr1[1];
                if (!StringTools.isNullOrNone(part2)){
                    String[] arr2 = part2.split(deliminator2);
                    return arr2[0];
                }
            }
        }

        return source;
    }

    public static void main(String[] args){
        String str = extract("数据接口批量导入，银行单号E20180115154115081900008.","银行单号", Pattern.quote("."));
        System.out.println(str);
    }
}
