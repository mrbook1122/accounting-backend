package cdut.accounting.utils;

/**
 * 格式化数字工具类
 */
public class NumberUtils {
    /**
     * 格式化小数保留两位
     */
    public static double formatDouble(double d) {
        int a = (int) (d * 100);
        return a / 100.0;
    }
}
