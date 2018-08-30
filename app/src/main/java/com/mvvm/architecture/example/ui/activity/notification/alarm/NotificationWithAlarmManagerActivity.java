package com.mvvm.architecture.example.ui.activity.notification.alarm;

import android.arch.lifecycle.ViewModelProviders;

import com.mvvm.architecture.R;
import com.mvvm.architecture.databinding.ActivityNotificationWithAlarmManagerBinding;
import com.mvvm.architecture.template.base.BaseActivity;

public class NotificationWithAlarmManagerActivity extends
    BaseActivity<ActivityNotificationWithAlarmManagerBinding, NotificationWithAlarmManagerViewModel>
    implements NotificationWithAlarmManagerNavigator {
    @Override
    public int getLayoutId() {
        return R.layout.activity_notification_with_alarm_manager;
    }

    @Override
    public NotificationWithAlarmManagerViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this).get(NotificationWithAlarmManagerViewModel.class);
        return mViewModel;
    }

    @Override
    public void initViews() {
        mViewDataBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
    }
}
