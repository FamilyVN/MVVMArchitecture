package com.mvvm.architecture.sample.ui.activity.example;

import com.mvvm.architecture.base.ui.activity.BaseViewModel;

public class ExampleViewModel extends BaseViewModel<ExampleNavigator> {
    public void goToFeedBack() {
        getNavigator().goToFeedBack();
    }

    public void onShareApp() {
        getNavigator().onShareApp();
    }
}
