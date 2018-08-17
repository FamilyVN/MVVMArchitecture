package com.mvvm.architecture;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.mvvm.architecture.template.utils.SpManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SpManager.getInstance().init(this, BuildConfig.APPLICATION_ID);
        MobileAds.initialize(this, getString(R.string.ad_mob_app_id));
    }
}
