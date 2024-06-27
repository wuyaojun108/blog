package org.wyj.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String convertTimestampToStr(long timestamp, String pattern) {
        Date date = new Date(timestamp);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }
}
