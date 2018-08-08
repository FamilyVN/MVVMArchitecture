package com.mvvm.architecture.view.ui.activity.example;

import com.mvvm.architecture.view.ui.activity.base.BaseViewModel;

public class ExampleViewModel extends BaseViewModel<ExampleNavigator> {
    public void goToFeedBack() {
        getNavigator().goToFeedBack();
    }

    public void onShareApp() {
        getNavigator().onShareApp();
    }
}
