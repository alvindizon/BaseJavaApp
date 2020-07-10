package com.alvindizon.basejavaapp.data.network.interceptor;


import java.io.IOException;

import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class ApiErrorInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String errorMessage;

        switch (response.code()) {
            // TODO add custom exceptions depending on status codes here
            default:
                return response;
        }
    }
}
