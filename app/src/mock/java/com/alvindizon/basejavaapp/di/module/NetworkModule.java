package com.alvindizon.basejavaapp.di.module;

import android.content.Context;

import com.alvindizon.basejavaapp.data.network.api.SampleApi;
import com.alvindizon.basejavaapp.data.network.api.SampleLoginApi;
import com.alvindizon.basejavaapp.data.network.cert.CustomTrust;
import com.alvindizon.basejavaapp.data.network.interceptor.ApiErrorInterceptor;
import com.alvindizon.basejavaapp.data.network.interceptor.MockResponseInterceptor;
import com.alvindizon.basejavaapp.data.network.interceptor.TimeoutInterceptor;
import com.alvindizon.basejavaapp.data.network.interceptor.TokenAuthenticator;
import com.alvindizon.basejavaapp.data.network.interceptor.TokenInterceptor;
import com.alvindizon.basejavaapp.util.Const;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class NetworkModule {

    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    ApiErrorInterceptor provideApiErrorInterceptor() {
        return new ApiErrorInterceptor();
    }

    @Provides
    @Singleton
    CustomTrust provideCustomTrust(Context context) {
        CustomTrust customTrust = new CustomTrust(context);
        customTrust.initializeDefaultCerts();
        // TODO add PEM file in your assets folder if backend uses self-signed cert
//        if(BuildConfig.DEBUG) {
//            customTrust.addCertificates(R.raw.pem_file);
//        }
        return customTrust;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                     ApiErrorInterceptor apiErrorInterceptor,
                                     TokenAuthenticator tokenAuthenticator,
                                     TokenInterceptor tokenInterceptor,
                                     TimeoutInterceptor timeoutInterceptor,
                                     MockResponseInterceptor mockResponseInterceptor,
                                     CustomTrust customTrust) {
        return new OkHttpClient.Builder()
                .sslSocketFactory(customTrust.getSslSocketFactory(), customTrust.getTrustManager())
                .hostnameVerifier(customTrust.getHostnameVerifier())
                .addInterceptor(timeoutInterceptor)
                .addInterceptor(apiErrorInterceptor)
                .addInterceptor(tokenInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(mockResponseInterceptor)
                .authenticator(tokenAuthenticator)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofitMoshi(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    SampleLoginApi provideSampleLoginApi(HttpLoggingInterceptor httpLoggingInterceptor,
                                         TimeoutInterceptor timeoutInterceptor,
                                         MockResponseInterceptor mockResponseInterceptor,
                                         CustomTrust customTrust) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(customTrust.getSslSocketFactory(), customTrust.getTrustManager())
                .hostnameVerifier((hostname, session) -> true)
                .addInterceptor(timeoutInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(mockResponseInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(SampleLoginApi.class);
    }

    @Provides
    @Singleton
    SampleApi provideSampleApi(Retrofit retrofit) {
        return retrofit.create(SampleApi.class);
    }
}
