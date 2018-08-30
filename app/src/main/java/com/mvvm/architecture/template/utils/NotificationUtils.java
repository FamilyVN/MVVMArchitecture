package com.mvvm.architecture.template.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.mvvm.architecture.R;
import com.mvvm.architecture.example.service.AlarmBootReceiver;
import com.mvvm.architecture.example.service.SchedulingService;
import com.mvvm.architecture.template.lifecycle.Constant;

import java.util.Calendar;

public class NotificationUtils {
    public static final String KEY_ID = "KEY_ID";
    private static final String CHANNEL_NOTIFICATION_ID = "notification_id";
    private static final String CHANNEL_STATUS_BAR_ID = "status_bar_id";
    private static final int NOTIFICATION_ID_MORNING = 2;
    private static final int NOTIFICATION_ID_AFTERNOON = 3;
    private static final int NOTIFICATION_ID_NIGHT = 4;
    private static final int NUMBER_NOTIFICATION = 3;

    public static boolean showNotification() {
        return SpManager.getInstance().getBoolean(Constant.SHOW_NOTIFICATION, false);
    }

    public static void checkSetupNotification(Context context) {
        Log.d("TAG", "showNotification = " + showNotification());
        if (showNotification()) {
            createAlarmNotification(context);
        } else {
            stopAlarmNotification(context);
        }
    }

    public static void createAlarmNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;
        Intent intent = new Intent(context, SchedulingService.class);
        for (int i = 0; i < NUMBER_NOTIFICATION; i++) {
            intent.putExtra(KEY_ID, i + 2);
            /* index notification = {
                 NOTIFICATION_ID_MORNING,
                 NOTIFICATION_ID_AFTERNOON,
                 NOTIFICATION_ID_NIGHT
                 } */
            PendingIntent pendingIntent = PendingIntent.getService(context, i + 2, intent, 0);
            Calendar calendar = Calendar.getInstance();
            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
            switch (i + 2) {
                case NOTIFICATION_ID_MORNING:
                    calendar.set(Calendar.HOUR_OF_DAY, 9);
//                    nếu quá giờ thì chuyển sang ngày hôm sau mới báo
                    if (hourOfDay >= 9) {
                        calendar.add(Calendar.DATE, 1);
                    }
                    break;
                case NOTIFICATION_ID_AFTERNOON:
                    calendar.set(Calendar.HOUR_OF_DAY, 12);
                    if (hourOfDay >= 12) {
                        calendar.add(Calendar.DATE, 1);
                    }
                    break;
                case NOTIFICATION_ID_NIGHT:
                    calendar.set(Calendar.HOUR_OF_DAY, 18);
                    if (hourOfDay >= 18) {
                        calendar.add(Calendar.DATE, 1);
                    }
                    break;
            }
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
            }
        }
        enabledAutoBoot(context, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
    }

    public static void stopAlarmNotification(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;
        Intent intent = new Intent(context, SchedulingService.class);
        for (int i = 0; i < NUMBER_NOTIFICATION; i++) {
            PendingIntent pendingIntent =
                PendingIntent.getService(context, i + 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        }
        enabledAutoBoot(context, PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    private static void enabledAutoBoot(Context context, int enabled) {
        ComponentName componentName = new ComponentName(context, AlarmBootReceiver.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager
            .setComponentEnabledSetting(componentName, enabled, PackageManager.DONT_KILL_APP);
    }

    public static void startAlarmNotification(Context context) {
        createNotificationChannel(context, CHANNEL_NOTIFICATION_ID);
    }

    /* tạo 1 kênh cho notification với API >= 26 -> bắt buộc*/
    public static void createNotificationChannel(Context context, String channelId) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.app_name);
            String description = context.getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            channel.setShowBadge(false);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager =
                context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
