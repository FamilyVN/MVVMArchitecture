package com.mvvm.architecture.template.ui.activity.main;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mvvm.architecture.R;
import com.mvvm.architecture.databinding.ActivityMainBinding;
import com.mvvm.architecture.template.base.BaseActivity;
import com.mvvm.architecture.template.base.EventStatus;
import com.mvvm.architecture.template.data.Repository;
import com.mvvm.architecture.template.data.network.NetworkHelper;
import com.mvvm.architecture.template.viewmodel.main.MainViewModel;
import com.mvvm.architecture.template.viewmodel.main.MainViewModelFactory;

/**
 * Created by FRAMGIA\mai.dai.dien on 8/22/18.
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.getLoading().observe(this, isLoading -> {
            if (isLoading != null && isLoading) {
                showLoading();
            } else {
                hideLoading();
            }
        });
        mViewModel.getViewEvent().observe(this, error -> {
            if (error == null) {
                return;
            }
            String event = error.getContentIfNotHandled();
            if (!TextUtils.isEmpty(event)) {
                switch (event) {
                    case EventStatus.NETWORK_ERROR:
                        showAlertMessage(error.getPayload());
                        break;
                    case EventStatus.SUCCESS:
                        // handle success
                        break;
                    default:
                        // todo show error or do something here
                }
            }
        });
        mViewModel.loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        Repository repository = Repository.getInstance(NetworkHelper.getService());
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        return ViewModelProviders.of(this, factory).get(MainViewModel.class);
    }

    @Override
    public void initViews() {
        //todo init view here
    }
}
