package com.example.alon.airvanguard.presentation.settings;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.infrastructure.app.DistressCallNotificationService;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String LOG_TAG = SettingsFragment.class.getSimpleName();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//        if (key.equals(getString(R.string.pref_key_distress_notification))) {
//            if (sharedPreferences.getBoolean(key,false)) {
//                getActivity().startService(new Intent(getContext(),
//                        DistressCallNotificationService.class));
//            } else {
//                getActivity().stopService(new Intent(getContext(),
//                        DistressCallNotificationService.class));
//            }
//        }

        if (key.equals(getString(R.string.pref_key_unit_type))) {
            ((SettingsChangeListener)getActivity()).onDataUnitTypeChange(Integer.parseInt(sharedPreferences
                    .getString(key,getString(R.string.pref_default_unit_type))));
        }
    }

    @Override
    public void onDestroy() {
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    interface SettingsChangeListener {

        void onDataUnitTypeChange(int type);
    }
}
