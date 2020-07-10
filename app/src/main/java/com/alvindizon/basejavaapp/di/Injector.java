package com.alvindizon.basejavaapp.di;


import android.app.Service;

import androidx.fragment.app.FragmentActivity;

import com.alvindizon.basejavaapp.BaseJavaApp;
import com.alvindizon.basejavaapp.di.component.ActivityComponent;
import com.alvindizon.basejavaapp.di.component.AppComponent;
import com.alvindizon.basejavaapp.di.component.ServiceComponent;
import com.alvindizon.basejavaapp.di.module.ActivityModule;
import com.alvindizon.basejavaapp.di.module.ServiceModule;

public class Injector {
    public static AppComponent get() {
        return BaseJavaApp.get().getAppComponent();
    }

    public static ActivityComponent getActivityComponent(FragmentActivity activity) {
        return get().activityComponent(new ActivityModule(activity));
    }

    public static ServiceComponent getServiceComponent(Service service) {
        return get().serviceComponent(new ServiceModule(service));
    }

}
