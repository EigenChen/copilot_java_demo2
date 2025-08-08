package org.suntek.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author GitHub Copilot
 * @date 2025-08-06
 */
public class DateTimeUtils {
    
    /**
     * 时间戳格式
     */
    public static final String TSFORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 日期格式
     */
    public static final String DATEFORMAT = "yyyy-MM-dd";
    
    /**
     * 格式化日期
     *
     * @param date 日期
     * @param pattern 格式
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    
}
