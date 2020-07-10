package com.alvindizon.basejavaapp.di.component;

import com.alvindizon.basejavaapp.di.module.ActivityModule;
import com.alvindizon.basejavaapp.di.module.ViewModelModule;
import com.alvindizon.basejavaapp.ui.screens.main.MainActivity;
import com.alvindizon.basejavaapp.ui.screens.settings.CommSettingsFragment;
import com.alvindizon.basejavaapp.ui.screens.settings.SettingsMenuFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {ActivityModule.class, ViewModelModule.class})
public interface ActivityComponent {
    // TODO Add your activities or fragments here
    void inject(MainActivity activity);

    void inject(SettingsMenuFragment fragment);

    void inject(CommSettingsFragment fragment);
}
