package com.alvindizon.basejavaapp.ui.screens.main;

import com.alvindizon.basejavaapp.data.preferences.ParamRepository;
import com.alvindizon.basejavaapp.ui.common.BaseViewModel;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel {

    private final ParamRepository paramRepository;

    @Inject
    public MainViewModel(ParamRepository paramRepository) {
        this.paramRepository = paramRepository;
    }
}
