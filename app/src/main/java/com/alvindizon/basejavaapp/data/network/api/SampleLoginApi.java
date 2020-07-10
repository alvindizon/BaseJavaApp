package com.alvindizon.basejavaapp.data.network.api;

import com.alvindizon.basejavaapp.data.network.model.LoginToken;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;
// TODO add REST API calls as needed
// TODO depending on your app, you may remove this if your app has no login
public interface SampleLoginApi {
    @FormUrlEncoded
    @POST()
    Single<LoginToken> requestLogin(
            @Url String url,
            @Header("X-API-VERSION") String apiVersion,
            @Header("X-API-KEY") String apiKey,
            @Field("Username") String username,
            @Field("Password") String password,
            @Field("grant_type") String grantType);

    @FormUrlEncoded
    @POST()
    Single<LoginToken> refreshToken(
            @Url String url,
            @Header("X-API-VERSION") String apiVersion,
            @Header("X-API-KEY") String apiKey,
            @Field("refresh_token") String refreshToken,
            @Field("grant_type") String grantType
    );
}
