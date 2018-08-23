package com.mvvm.architecture.template.data.network;

import com.mvvm.architecture.template.data.network.response.BaseResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by FRAMGIA\mai.dai.dien on 8/22/18.
 */
public interface AppService {
    // function for demo use api, when implement in real project can remove it
    @GET("test")
    Single<BaseResponse> loadData();
}
