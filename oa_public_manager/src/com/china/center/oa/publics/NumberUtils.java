package com.china.center.oa.publics;

import java.math.BigDecimal;


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

    public static void main(String[] args){
        System.out.println(roundDouble(1.7000000476837158));
    }
}
