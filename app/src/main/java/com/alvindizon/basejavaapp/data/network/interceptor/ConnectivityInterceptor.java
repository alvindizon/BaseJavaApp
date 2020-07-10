package com.alvindizon.basejavaapp.data.network.interceptor;


import com.alvindizon.basejavaapp.data.network.exception.NoNetworkException;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class ConnectivityInterceptor implements Interceptor {

    private NetworkMonitor monitor;

    @Inject
    public ConnectivityInterceptor(LiveNetworkMonitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (monitor.isConnected()) {
            return chain.proceed(request);
        } else {
            throw new NoNetworkException();
        }
    }
}
