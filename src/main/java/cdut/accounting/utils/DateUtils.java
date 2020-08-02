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
     * 将字符串日期转化为日期区间
     * <p>自动判断字符串日期格式，比如2020-07转化为2020-07-01 00:00——2020-08-01 00:00，
     * 2020-07-25转化为2020-07-25 00:00——2020-07-26 00:00
     * </p>
     *
     * @return Date数组，包含两个元素，第一个元素是开始时间，第二个是结束时间
     */
    public static Date[] convert(String date) {
        Calendar time = Calendar.getInstance();
        Date d1 = null, d2 = null;
        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = 1;
            // 月份加1还是日期加1
            int field = Calendar.MONTH;
            if (date.length() == 10) {
                day = Integer.parseInt(date.substring(8, 10));
                field = Calendar.DATE;
            }
            time.set(year, month - 1, day, 1, 0, 0);
            d1 = time.getTime();
            time.add(field, 1);
            d2 = time.getTime();

        } catch (Exception e) {
            throw new DateFormatException();
        }
        return new Date[]{d1, d2};
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
            time.set(year, month - 1, 1, 0, 0, 0);
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
