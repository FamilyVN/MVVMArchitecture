package com.mvvm.architecture.template.data.network;

import com.mvvm.architecture.BuildConfig;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FRAMGIA\mai.dai.dien on 8/22/18.
 */
public class NetworkHelper {
    public static AppService getService() {
        return getRestAdapter().create(AppService.class);
    }

    public static Retrofit getRestAdapter() {
        return new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(getHttpClient())
            .build();
    }

    public static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    public static OkHttpClient getHttpClient() {
        // todo add timeout
        return new OkHttpClient.Builder()
            // add api key for each request. (can change later or remove)
            .addInterceptor(new AppNetworkInterceptor())
            // add logging to log request
            .addNetworkInterceptor(getLoggingInterceptor())
            .build();
    }
}
