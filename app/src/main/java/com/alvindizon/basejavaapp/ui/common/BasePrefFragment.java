package com.alvindizon.basejavaapp.ui.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.XmlRes;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.alvindizon.basejavaapp.R;

public abstract class BasePrefFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {

    protected abstract void performDependencyInjection();

    protected abstract BasePrefFragment getFragment();

    @XmlRes
    protected abstract int getPrefResId();

    // bind summaries of specific preferences based on preference screens
    protected abstract void bindSummary();

    @StringRes
    protected abstract int getSettingTitleId();

    protected Toolbar toolbar;

    @Override
    public void onAttach(@NonNull Context context) {
        performDependencyInjection();
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = super.onCreateView(inflater, container, savedInstanceState);
        String title = requireContext().getString(getSettingTitleId());
        assert root != null;
        toolbar = root.findViewById(R.id.toolbar).findViewById(R.id.toolbar);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(title);

        // go back to previous screen on backpress/ up icon press
        setupBackBehavior(getFragment());
        return root;
    }

    protected void setupBackBehavior(Fragment fragment) {
        toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(fragment).navigateUp());
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
            new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    NavHostFragment.findNavController(fragment).navigateUp();
                }
            }
        );
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(getPrefResId());
        bindSummary();
    }

    protected void bindPreferenceSummaryToValue(int prefKeyId) {
        Preference preference = findPreference(getString(prefKeyId));
        preference.setOnPreferenceChangeListener(this);
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(preference.getContext());

        String preferenceString = sharedPreferences.getString(preference.getKey(), "");

        onPreferenceChange(preference, preferenceString);
    }

    protected void setDefaultSummary(Preference preference, Object value) {
        String stringValue = value.toString();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                CharSequence[] labels = listPreference.getEntries();
                preference.setSummary(labels[prefIndex]);
            }
        } else {
            preference.setSummary(stringValue);
        }
    }

}
