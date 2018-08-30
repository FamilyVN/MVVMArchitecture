package com.mvvm.architecture.example.service;

import android.app.IntentService;
import android.content.Intent;

import com.mvvm.architecture.template.utils.NotificationUtils;

public class SchedulingService extends IntentService {
    public SchedulingService() {
        super(SchedulingService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationUtils.startAlarmNotification(this);
    }
}
