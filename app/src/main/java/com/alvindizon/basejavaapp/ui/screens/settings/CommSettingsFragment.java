package com.alvindizon.basejavaapp.ui.screens.settings;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;

import com.alvindizon.basejavaapp.R;
import com.alvindizon.basejavaapp.di.Injector;
import com.alvindizon.basejavaapp.ui.common.BasePrefFragment;
import com.alvindizon.basejavaapp.ui.viewmodel.ViewModelFactory;

import javax.inject.Inject;


public class CommSettingsFragment extends BasePrefFragment {

    private SettingsViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    public CommSettingsFragment() {
    }

    @Override
    protected void performDependencyInjection() {
        Injector.getActivityComponent(requireActivity()).inject(this);
    }

    @Override
    protected BasePrefFragment getFragment() {
        return CommSettingsFragment.this;
    }

    @Override
    protected int getPrefResId() {
        return R.xml.prefs_comm;
    }

    @Override
    protected int getSettingTitleId() {
        return R.string.settings_comm;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(SettingsViewModel.class);
    }

    @Override
    protected void bindSummary() {
        bindPreferenceSummaryToValue(R.string.key_mobile_ip);
        bindPreferenceSummaryToValue(R.string.key_mobile_secondary_ip);
        bindPreferenceSummaryToValue(R.string.key_wifi_ip);
        bindPreferenceSummaryToValue(R.string.key_wifi_secondary_ip);
        bindPreferenceSummaryToValue(R.string.key_mobile_port);
        bindPreferenceSummaryToValue(R.string.key_mobile_secondary_port);
        bindPreferenceSummaryToValue(R.string.key_wifi_port);
        bindPreferenceSummaryToValue(R.string.key_wifi_secondary_port);
        bindPreferenceSummaryToValue(R.string.key_conn_timeout);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String stringValue = value.toString();
        // TODO you can do input validation here; return false if input to setting is invalid (use findPreference)
        // check if MID/TID have correct lengths, do not apply new value if invalid
//        if(preference == findPreference(getString(R.string.key_tid))) {
//            if(!viewModel.isValidTid(stringValue)) {
//                dialogManager.displayInfoDialog(R.string.err_invalid_tid);
//                return false;
//            }
//
        setDefaultSummary(preference, value);

        return true;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        String preferenceTitle = preference.getTitle().toString();
        // TODO you can handle clicks on individual settings items here
//        if (preferenceTitle.contentEquals(getString(R.string.pref_title_print_sys_config))) {
//
//        }
        return super.onPreferenceTreeClick(preference);
    }

}
