package cdut.accounting.utils;

import cdut.accounting.exception.DateFormatException;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtils {
    /**
     * 验证日期格式是否正确
     *
     * @param date 日期（格式为2020-07，2020-07-01）
     */
    public static void validateDate(String date) {
        if (date.length() != 7 && date.length() != 10) {
            throw new DateFormatException();
        }
    }

    /**
     * 将字符串日期转化为日期对象
     *
     * @param date 只包含年月(如2020-07)
     */
    public static Date convertByMonth(String date) {
        // TODO 正则
        Calendar time = Calendar.getInstance();
        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            time.set(year, month - 1, 0, 0, 0, 0);
        } catch (Exception e) {
            throw new DateFormatException();
        }
        return time.getTime();
    }

    public static Date convertByDay(String date) {
        Calendar time = Calendar.getInstance();
        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));
            time.set(year, month - 1, day, 0, 0, 0);
        } catch (Exception e) {
            throw new DateFormatException();
        }
        return time.getTime();
    }

    /**
     * 获取一个月的开头和结尾时间
     *
     * @param date 日期对象
     */
    public static Date[] getPeriodByMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date start = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        Date end = calendar.getTime();
        return new Date[]{start, end};
    }

    /**
     * 获取一天的开头和结尾时间
     *
     * @param date 日期对象
     */
    public static Date[] getPeriodByDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date start = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date end = calendar.getTime();
        return new Date[]{start, end};
    }
}
