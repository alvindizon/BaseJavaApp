package com.alvindizon.basejavaapp.ui.screens.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alvindizon.basejavaapp.data.preferences.ParamRepository;
import com.alvindizon.basejavaapp.ui.common.BaseViewModel;

import javax.inject.Inject;

public class SettingsViewModel extends BaseViewModel {

    private final ParamRepository paramRepository;
    private MutableLiveData<SettingsUIState> uiState = new MutableLiveData<>();

    @Inject
    public SettingsViewModel(ParamRepository paramRepository) {
        super();
        this.paramRepository = paramRepository;
    }

    public LiveData<SettingsUIState> getUiState() {
        return uiState;
    }
}
