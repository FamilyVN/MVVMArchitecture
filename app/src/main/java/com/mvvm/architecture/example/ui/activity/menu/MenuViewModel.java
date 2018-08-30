package com.mvvm.architecture.example.ui.activity.menu;

import com.mvvm.architecture.template.base.BaseViewModel;

public class MenuViewModel extends BaseViewModel<MenuNavigator> {
    public void openAdMobTest() {
        getNavigator().openAdMobTest();
    }
}
