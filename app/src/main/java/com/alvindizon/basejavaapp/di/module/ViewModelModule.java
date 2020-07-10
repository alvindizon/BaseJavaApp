package com.alvindizon.basejavaapp.di.module;

import androidx.lifecycle.ViewModel;

import com.alvindizon.basejavaapp.data.preferences.ParamRepository;
import com.alvindizon.basejavaapp.ui.screens.main.MainViewModel;
import com.alvindizon.basejavaapp.ui.screens.settings.SettingsViewModel;
import com.alvindizon.basejavaapp.ui.viewmodel.ViewModelFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public class ViewModelModule {


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory provideViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

// TODO add viewmodels here
// TODO if your viewmodel has no additional dependency, then it uses the default, no-arg constructor, so no need to add them here,
//      and no need to inject ViewModelFactory to Fragments as well
    @Provides
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    ViewModel provideMainViewModel(ParamRepository paramRepository) {
        return new MainViewModel(paramRepository);
    }

    @Provides
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    ViewModel provideSettingsViewModel(ParamRepository paramRepository) {
        return new SettingsViewModel(paramRepository);
    }
}
