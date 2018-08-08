package com.mvvm.architecture.utils;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.IOException;

public class CommonUtils {
    /**
     * @return true -> tức là có bật mạng wifi hoặc mạng dữ liệu
     */
    public static boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param view tác dụng chính là ẩn(tắt) bàn phím của thiết bị đi
     */
    public static void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager =
            (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

    /**
     * @param context
     * @return xác định xem có bật GPS không ?
     */
    public static boolean isGpsOn(Context context) {
        LocationManager locationManager =
            (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
