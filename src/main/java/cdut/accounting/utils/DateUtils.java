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
}
