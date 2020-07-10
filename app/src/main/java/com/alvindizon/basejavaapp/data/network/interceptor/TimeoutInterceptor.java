package com.alvindizon.basejavaapp.data.network.interceptor;


import com.alvindizon.basejavaapp.data.preferences.ParamRepository;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class TimeoutInterceptor implements Interceptor {

    private final ParamRepository paramRepository;

    @Inject
    public TimeoutInterceptor(ParamRepository paramRepository) {
        this.paramRepository = paramRepository;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        int newConnectTimeout = paramRepository.getConnectionTimeout();

        return chain
                .withConnectTimeout(newConnectTimeout, TimeUnit.SECONDS)
                .proceed(request.newBuilder().build());
    }
}
