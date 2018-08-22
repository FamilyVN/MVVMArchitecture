package com.mvvm.architecture.template.data;

import com.mvvm.architecture.template.data.network.AppService;
import com.mvvm.architecture.template.data.network.response.BaseResponse;

import io.reactivex.Single;

/**
 * Created by FRAMGIA\mai.dai.dien on 8/22/18.
 */
public class Repository {
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static Repository sInstance;
    private AppService mService;

    private Repository(AppService service) {
        mService = service;
    }

    public static synchronized Repository getInstance(AppService service) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new Repository(service);
            }
        }
        return sInstance;
    }

    // function for demo use api, when implement in real project can remove it
    public Single<BaseResponse> loadData() {
        return mService.loadData();
    }
}
