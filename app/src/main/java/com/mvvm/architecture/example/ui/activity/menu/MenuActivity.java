package com.mvvm.architecture.example.ui.activity.menu;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;

import com.mvvm.architecture.R;
import com.mvvm.architecture.databinding.ActivityMenuBinding;
import com.mvvm.architecture.example.ui.activity.example.ExampleActivity;
import com.mvvm.architecture.example.ui.activity.notification.alarm.NotificationWithAlarmManagerActivity;
import com.mvvm.architecture.template.base.BaseActivity;

public class MenuActivity extends BaseActivity<ActivityMenuBinding, MenuViewModel>
    implements MenuNavigator {
    @Override
    public int getLayoutId() {
        return R.layout.activity_menu;
    }

    @Override
    public MenuViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        return mViewModel;
    }

    @Override
    public void initViews() {
        mViewModel.setNavigator(this);
    }

    @Override
    public void openAdMobTest() {
        Intent intent = new Intent(this, ExampleActivity.class);
        startActivity(intent);
    }

    @Override
    public void openNotificationWithAlarmManager() {
        Intent intent = new Intent(this, NotificationWithAlarmManagerActivity.class);
        startActivity(intent);
    }
}
