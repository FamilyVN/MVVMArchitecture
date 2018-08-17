package com.mvvm.architecture.template.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtils {
    public static final String TIME_FORMAT_TYPE = "TIME_FORMAT_TYPE";
    public static final int TIME_FORMAT_TYPE_12 = 12;
    public static final int TIME_FORMAT_TYPE_24 = 24;

    public static String formatDateOfWeek(Date date) {
        if (date == null) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE", Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String formatHour(Date date, String timeZone) {
        if (date == null) return "00:00";
        int type =
            SpManager.getInstance().getInt(TIME_FORMAT_TYPE, TIME_FORMAT_TYPE_24);
        String pattern = type == TIME_FORMAT_TYPE_24 ? "HH:mm" : "hh:mm aa";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(date);
    }

    public static String formatDayHour(Date date, String timeZone) {
        if (date == null) return "00:00";
        int type =
            SpManager.getInstance().getInt(TIME_FORMAT_TYPE, TIME_FORMAT_TYPE_24);
        String pattern =
            type == TIME_FORMAT_TYPE_24 ? "HH:mm MMM dd, yyyy" : "hh:mm aa MMM dd, yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        return simpleDateFormat.format(date);
    }

    public static String formatDateFull(Date date) {
        if (date == null) return "";
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return format.format(date);
    }

    public static String formatDayOfWeekFull(Date date) {
        if (date == null) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}
