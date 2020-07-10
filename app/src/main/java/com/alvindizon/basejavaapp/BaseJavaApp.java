package com.alvindizon.basejavaapp;

import android.app.Application;

import com.alvindizon.basejavaapp.di.component.AppComponent;
import com.alvindizon.basejavaapp.di.component.DaggerAppComponent;
import com.alvindizon.basejavaapp.di.module.AppModule;

import timber.log.Timber;

public class BaseJavaApp extends Application {

    private static BaseJavaApp INSTANCE;

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Timber.d("onCreate()");

    }

    public static BaseJavaApp get() {
        return INSTANCE;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
