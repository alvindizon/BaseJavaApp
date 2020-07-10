package com.alvindizon.basejavaapp.data.network.interceptor;

import com.alvindizon.basejavaapp.data.preferences.ParamRepository;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class TokenInterceptor implements Interceptor {

    private static final String AUTHORIZATION = "Authorization";
    private ParamRepository paramRepository;

    @Inject
    public TokenInterceptor(ParamRepository paramRepository) {
        this.paramRepository = paramRepository;
    }

    @Override
    public synchronized Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();

        /*
         * If request already had Authorization header
         * or if there is no available Authorization value (user has not yet logged in)
         * then proceed with request as is
         * */
        if (originalRequest.header(AUTHORIZATION) != null || paramRepository.getAuthorization() == null) {
            return chain.proceed(originalRequest);
        }

        /**
         * Add authorization header to request
         */
        Request authorizedRequest = originalRequest.newBuilder()
                .header(AUTHORIZATION, paramRepository.getAuthorization())
                .build();

        return chain.proceed(authorizedRequest);
    }
}
