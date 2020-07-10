package com.alvindizon.basejavaapp.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.alvindizon.basejavaapp.R;

import javax.inject.Inject;
import javax.inject.Singleton;

// TODO add methods for other return types as needed
@Singleton
public class PreferenceHelper {

    private final SharedPreferences sharedPreferences;
    private final Context context;

    @Inject
    public PreferenceHelper(Context context) {
        this.context = context;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        final SharedPreferences defaultValueSp = context.getSharedPreferences(PreferenceManager.KEY_HAS_SET_DEFAULT_VALUES, Context.MODE_PRIVATE);

        if (!defaultValueSp.getBoolean(PreferenceManager.KEY_HAS_SET_DEFAULT_VALUES, false)) {
            // for the first preference xml, do this
            PreferenceManager.setDefaultValues(context, R.xml.prefs_comm, false);
            // subsequent pref xml files should be set like this
//            PreferenceManager.setDefaultValues(context, R.xml.prefs_merch, true);
        }
    }

    public String get(int key, String defaultValue) {
        return sharedPreferences.getString(context.getResources().getString(key), defaultValue);
    }

    public boolean get(int key, boolean defaultValue) {
        return sharedPreferences.getBoolean(context.getResources().getString(key), defaultValue);
    }

    public void set(int key, String value) {
        sharedPreferences.edit().putString(context.getResources().getString(key), value).apply();
    }

    public void set(int key, int value) {
        sharedPreferences.edit().putString(context.getString(key), context.getString(value)).apply();
    }

    public void set(int key, boolean value) {
        sharedPreferences.edit().putBoolean(context.getString(key), value).apply();
    }

    public String get(int keyId, int defaultValueResId) {
        return sharedPreferences.getString(context.getString(keyId), context.getString(defaultValueResId));
    }

    public void set(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public void set(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public void remove(int keyId) {
        sharedPreferences.edit().remove(context.getString(keyId)).apply();
    }

}
