package com.yetistudios.rsww.rswwflight.util;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DatetimeUtil {

    @SneakyThrows
    public static long datetimeStrToUnix(String datetimeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.parse(datetimeStr).getTime() / 1000L;
    }

    public static long dateAndTimeToUnix(String date, String time) {
        return datetimeStrToUnix(String.format("%s %s", date, time));
    }

    @SneakyThrows
    public static long dateStrToUnix(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.parse(date).getTime() / 1000L;
    }
}
