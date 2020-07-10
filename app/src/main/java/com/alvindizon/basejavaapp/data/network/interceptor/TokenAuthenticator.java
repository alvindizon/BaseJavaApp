package com.alvindizon.basejavaapp.data.network.interceptor;

import com.alvindizon.basejavaapp.BuildConfig;
import com.alvindizon.basejavaapp.data.network.api.SampleLoginApi;
import com.alvindizon.basejavaapp.data.network.model.LoginToken;
import com.alvindizon.basejavaapp.data.preferences.ParamRepository;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.HttpException;

@Singleton
public class TokenAuthenticator implements Authenticator {

    private static final String AUTHORIZATION = "Authorization";
    private ParamRepository paramRepo;
    private SampleLoginApi sampleLoginApi;
    private Request newRequest;

    @Inject
    public TokenAuthenticator(ParamRepository paramRepo, SampleLoginApi sampleLoginApi) {
        this.paramRepo = paramRepo;
        this.sampleLoginApi = sampleLoginApi;
        this.newRequest = null;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        /**
         * "Implementations should check if the initial request already included an attempt to authenticate.
         * If so it is likely that further attempts will not be useful and the authenticator should give up."
         *
         */
        LoginToken newToken = null;
        try {
            newToken = refreshAccessToken(false).blockingGet();
        } catch (Exception e) {
            if(e instanceof HttpException) {
                HttpException httpException = (HttpException) e;
                int statusCode = httpException.code();
                if(statusCode != 400) {
                    newToken = refreshAccessToken(true).blockingGet();
                }
            }
        }

        if(newToken != null) {
            paramRepo.saveToken(newToken);
            String newAuthorization = newToken.getToken_type() + " " + newToken.getAccess_token();
            newRequest = response.request().newBuilder()
                    .addHeader(AUTHORIZATION, newAuthorization)
                    .build();
        }

        return newRequest;
    }

    private Single<LoginToken> refreshAccessToken(boolean isSecondaryFlag) {
        String refreshUrl = paramRepo.getBaseUrlWithVersion(isSecondaryFlag) + "Login";
        String grantType = "refresh_token";
        // wrap call to get token around rxjava + blockingGet so that we can intercept Exceptions
        return sampleLoginApi.refreshToken(refreshUrl, BuildConfig.ApiVer, BuildConfig.ApiKey, paramRepo.getRefreshToken(), grantType);
    }
}
