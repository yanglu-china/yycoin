package com.china.center.oa.publics;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class NumberUtils {

    /**
     * 四舍五入保留2位小数
     * @param value
     * @return
     */
    public static double roundDouble(double value){
        BigDecimal bd = new BigDecimal(value);
        double v1 = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return v1;
    }

    /**
     * 根据精度四舍五入
     * @param value
     * @param scale
     * @return
     */
    public static double roundDouble(double value, int scale){
        BigDecimal bd = new BigDecimal(value);
        double v1 = bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        return v1;
    }

    public static boolean equals(double x, double y) {
        return (Double.isNaN(x) && Double.isNaN(y)) || x == y;
    }

    /**
     *
     * @param x
     * @param y
     * @param eps default 0.001
     * @return
     */
    public static boolean equals(double x, double y, double eps) {
        return equals(x, y) || (Math.abs(y - x) <= eps);
    }

    /**
     * 浮点数四舍五入为整数
     * @param value
     * @return
     */
    public static int roundInt(double value){
        return new BigDecimal(Math.round(value)).intValueExact();
    }

    /**
     * find Entry with max value, return NULL if exists multiple entries with same max value
     * @param map
     * @return
     */
    public static Map.Entry findMax(Map<String, Double> map){
        Map.Entry<String, Double> maxEntry = null;
        //find max entry
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (maxEntry == null || entry.getValue()
                    .compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        //检查是否有多个最大值
        int maxValue = 0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (entry.getValue().compareTo(maxEntry.getValue()) == 0){
                maxValue += 1;
            }
        }

        if (maxValue == 1){
            return maxEntry;
        } else{
            return null;
        }
    }

    public static void main(String[] args){
        System.out.println(roundDouble(1.7000000476837158));
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("90000000000000000000", 1000.2);
        map.put("90000000000000000001", 1900.2);
        map.put("90000000000000000003", 800.3);
        Map.Entry max = findMax(map);
        System.out.println(max);
        map.put("90000000000000000004", 1900.2);
        max = findMax(map);
        System.out.println(max);
        System.out.println(roundDouble(875999.92/14,4));
    }
}
