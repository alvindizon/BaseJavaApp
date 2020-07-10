package com.alvindizon.basejavaapp.di.component;

import com.alvindizon.basejavaapp.di.module.ActivityModule;
import com.alvindizon.basejavaapp.di.module.AppModule;
import com.alvindizon.basejavaapp.di.module.DbModule;
import com.alvindizon.basejavaapp.di.module.NetworkModule;
import com.alvindizon.basejavaapp.di.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class,
        DbModule.class,
        NetworkModule.class})
public interface AppComponent {
    // TODO if your Application class needs objects list them here
    ActivityComponent activityComponent(ActivityModule module);
    ServiceComponent serviceComponent(ServiceModule serviceModule);
}
