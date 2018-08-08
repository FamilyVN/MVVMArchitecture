package com.mvvm.architecture.utils;

import java.util.Calendar;
import java.util.Date;

public class ConvertUtils {
    public static long convertDateToTimeInMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }
}
