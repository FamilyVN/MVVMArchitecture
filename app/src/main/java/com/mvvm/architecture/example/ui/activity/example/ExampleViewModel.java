package com.mvvm.architecture.example.ui.activity.example;

import com.mvvm.architecture.template.base.BaseViewModel;

public class ExampleViewModel extends BaseViewModel<ExampleNavigator> {
    public void goToFeedBack() {
        getNavigator().goToFeedBack();
    }

    public void onShareApp() {
        getNavigator().onShareApp();
    }
}
