package com.mvvm.architecture.template.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConvertUtils {
    /**
     * @param date
     * @return
     */
    public static long convertDateToTimeInMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * @param data
     * @return new data after convert by pattern
     */
    public static Double convertLocation(Double data) {
        String pattern = "0.0000000000";
        return convertLocation(data, pattern);
    }

    /**
     * @param data
     * @param pattern
     * @return
     */
    public static Double convertLocation(Double data, String pattern) {
        DecimalFormat decimalFormat =
            (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        decimalFormat.applyPattern(pattern);
        Double dataConvert = null;
        try {
            dataConvert = Double.parseDouble(decimalFormat.format(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataConvert;
    }

    /**
     * @param kilometresPerHour
     * @return
     */
    private static double fromKmhToMph(double kilometresPerHour) {
        return kilometresPerHour / 1.6093442101;
    }

    /**
     * @param kilometresPerHour
     * @return
     */
    private static double fromKmhToKnot(double kilometresPerHour) {
        return kilometresPerHour / 1.8519995164;
    }

    /**
     * @param kilometresPerHour
     * @return
     */
    private static double fromKmhToFts(double kilometresPerHour) {
        return kilometresPerHour * 0.9113444444;
    }

    /**
     * @param kilometresPerHour
     * @return
     */
    private static double fromKmhToMetresPerSecond(double kilometresPerHour) {
        return kilometresPerHour / 3.6;
    }
}
