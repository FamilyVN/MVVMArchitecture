package com.mvvm.architecture.example.ui.activity.notification.alarm;

import android.arch.lifecycle.ViewModelProviders;

import com.mvvm.architecture.R;
import com.mvvm.architecture.databinding.ActivityNotificationWithAlarmManagerBinding;
import com.mvvm.architecture.template.base.BaseActivity;
import com.mvvm.architecture.template.lifecycle.Constant;
import com.mvvm.architecture.template.utils.NotificationUtils;
import com.mvvm.architecture.template.utils.SpManager;

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
        mViewModel.setNavigator(this);
    }

    @Override
    public void initListener() {
        // notification
        // setup first notification
        NotificationUtils.checkSetupNotification(this);
        //
        mBinding.switchNotification.setChecked(NotificationUtils.showNotification());
        mBinding.switchNotification.setOnCheckedChangeListener(
            (buttonView, isChecked) -> {
                SpManager.getInstance().putBoolean(Constant.SHOW_NOTIFICATION, isChecked);
                NotificationUtils
                    .checkSetupNotification(NotificationWithAlarmManagerActivity.this);
            });
    }
}
