package com.china.center.oa.publics;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {


    /**
     * days between today and day
     * @param day
     * @return
     */
    public static int daysBetweenToday(String day){
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateTime start = new DateTime(date);
        DateTime end = new DateTime(new Date());
        return Days.daysBetween(start.toLocalDate(), end.toLocalDate()).getDays();
    }

    /**
     * months between today and day
     * @param day
     * @return
     */
    public static int monthsBetweenToday(String day){
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateTime start = new DateTime(date);
        DateTime now = new DateTime(new Date());
        DateTime lastDayOfMonth = now.dayOfMonth().withMaximumValue();
        return Months.monthsBetween(start.toLocalDate(), lastDayOfMonth.toLocalDate()).getMonths();
    }

    public static void main(String[] args){
        int diff = monthsBetweenToday("2018-06-30");
        System.out.println(diff);
    }
}
