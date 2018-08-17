package com.mvvm.architecture.example.ui.activity.example;

import com.mvvm.architecture.template.ui.activity.BaseViewModel;

public class ExampleViewModel extends BaseViewModel<ExampleNavigator> {
    public void goToFeedBack() {
        getNavigator().goToFeedBack();
    }

    public void onShareApp() {
        getNavigator().onShareApp();
    }
}
