package com.mvvm.architecture.template.data.network;

import com.mvvm.architecture.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by FRAMGIA\mai.dai.dien on 8/22/18.
 */
public class AppNetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        HttpUrl url = originalHttpUrl.newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build();
        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
            .url(url);
        Request request = requestBuilder
            .build();
        return chain.proceed(request);
    }
}
