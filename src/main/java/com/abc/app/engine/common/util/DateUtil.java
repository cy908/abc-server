package com.abc.app.engine.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    private static SimpleDateFormat sdf(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 日期转字符串
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        return sdf(pattern).format(date);
    }

    /**
     * {@link Constant#DATETIME_FORMAT}
     * 
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return format(date, Constant.DATETIME_FORMAT);
    }

    /**
     * {@link Constant#DATE_FORMAT}
     * 
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return format(date, Constant.DATE_FORMAT);
    }

    /**
     * {@link Constant#TIME_FORMAT}
     * 
     * @param date
     * @return
     */
    public static String formatTime(Date date) {
        return format(date, Constant.TIME_FORMAT);
    }

    /**
     * 字符串转日期
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
        try {
            return sdf(pattern).parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * {@link Constant#DATETIME_FORMAT}
     * 
     * @param date
     * @return
     */
    public static Date parseDateTime(String date) {
        return parse(date, Constant.DATETIME_FORMAT);
    }

    /**
     * {@link Constant#DATE_FORMAT}
     * 
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        return parse(date, Constant.DATE_FORMAT);
    }

    /**
     * {@link Constant#TIME_FORMAT}
     * 
     * @param date
     * @return
     */
    public static Date parseTime(String date) {
        return parse(date, Constant.TIME_FORMAT);
    }

}