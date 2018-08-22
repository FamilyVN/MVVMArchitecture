package com.mvvm.architecture.template.data;

import com.mvvm.architecture.template.data.network.AppService;
import com.mvvm.architecture.template.data.network.response.BaseResponse;

import io.reactivex.Single;

/**
 * Created by FRAMGIA\mai.dai.dien on 8/22/18.
 */

public class Repository {
    private AppService mService;
    private static Repository sInstance;
    // For Singleton instantiation
    private static final Object LOCK = new Object();

    public static synchronized Repository getInstance(AppService service) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Repository(service);
            }
        }
        return sInstance;
    }

    private Repository(AppService service) {
        mService = service;
    }

    // function for demo use api, when implement in real project can remove it
    public Single<BaseResponse> loadData() {
        return mService.loadData();
    }
}
