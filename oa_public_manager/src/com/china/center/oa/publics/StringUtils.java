package com.china.center.oa.publics;

import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
     * 从源字符串中截取deliminator1和deliminator2之间的部分,找不到就返回空
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

        return "";
    }

    /**
     * 随机数
     * @param sn
     * @return
     */
    public static String generateSerialNo(int sn){
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

        return date+String.format("%03d", sn);
    }

    public static String[] fillObj(String[] obj, int length)
    {
        String[] result = new String[length];

        for (int i = 0; i < result.length; i++ )
        {
            if (i < obj.length)
            {
                result[i] = obj[i];
            }
            else
            {
                result[i] = "";
            }
        }

        return result;
    }

    /**
     * check date is valid format
     * @param date
     * @param format
     * @return
     */
    public static boolean isDateValid(String date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try{
            sdf.parse(date);
            return true;
        }catch(ParseException e){
            return false;
        }
    }

    public static String listToString(List<String> list, String deliminator1)
    {
        StringBuilder builder = new StringBuilder();

        for (String str : list)
        {
            builder.append(str).append(deliminator1);
        }

        return builder.toString();
    }

    public static String getSecondPart(String str, String deliminator){
        String[] arrs = str.split(deliminator);
//        for (String s: arrs){
//            System.out.println(s);
//        }
//        System.out.println(arrs);
        if(arrs.length >=2){
            return arrs[1];
        } else{
            return str;
        }
    }

    public static SortedSet<String> getMonthKeys(String begin, String end){
        SortedSet<String> result = new TreeSet();
        result.add(begin);
        String monthKey = begin;
        String nextKey = TimeTools.getStringByOrgAndDaysAndFormat(monthKey, 32, "yyyyMM");
        while (!nextKey.equals(end)){
            result.add(nextKey);
            monthKey = nextKey;
            nextKey = TimeTools.getStringByOrgAndDaysAndFormat(monthKey, 32, "yyyyMM");
        }
        result.add(end);
        return result;
    }

    public static String getMonthKey(String date){
        Calendar cal = Calendar.getInstance();

        // 本月时间
        cal.setTime(TimeTools.getDateByFormat(date, TimeTools.SHORT_FORMAT));

        // 下个月的1号
//        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.add(Calendar.MONTH, -1);

//        System.out.println(new Date(cal.getTime().getTime()));
        String turnMonth = TimeTools.getStringByFormat(new Date(cal.getTime().getTime()),
                "yyyyMM");
        return turnMonth;
    }

    public static void main(String[] args){
        String str = extract("数据接口批量导入，银行单号E20180115154115081900008.","银行单号", Pattern.quote("."));
        System.out.println(str);
        System.out.println(getSecondPart("YZ0096700 貔貅手串黑曜石升级版（普17）", " "));
        SortedSet<String> set = getMonthKeys("201810", "201902");
        System.out.println(set.size());
        System.out.println(set);
        System.out.println(getMonthKey("2016-01-31"));
        System.out.println(getMonthKey("2016-03-31"));
        System.out.println(getMonthKey("2016-05-31"));
        System.out.println(getMonthKey("2016-07-31"));
        System.out.println(getMonthKey("2016-07-30"));
        System.out.println(getMonthKey("2016-08-31"));
        System.out.println(getMonthKey("2016-08-30"));
        System.out.println(getMonthKey("2016-06-30"));
        System.out.println(getMonthKey("2016-05-01"));
        System.out.println(getMonthKey("2019-01-31"));
        System.out.println(getMonthKey("2019-01-30"));
    }
}
