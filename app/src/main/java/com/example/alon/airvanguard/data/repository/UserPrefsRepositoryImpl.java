package com.example.alon.airvanguard.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;

import javax.inject.Inject;

/**
 * {@link UserPrefsRepository} implementation class.
 */

public class UserPrefsRepositoryImpl implements UserPrefsRepository {

    private SharedPreferences mSharedPreferences;
    private Context mAppContext;
    private SharedPreferences.OnSharedPreferenceChangeListener mListener;

    @Inject
    public UserPrefsRepositoryImpl(@NonNull SharedPreferences sharedPreferences,@NonNull Context context) {
        mSharedPreferences = sharedPreferences;
        mAppContext = context;

    }

    @Override
    public int getMapType() {
        return Integer.parseInt(mSharedPreferences.getString(
                mAppContext.getString(R.string.pref_key_map_type),
                mAppContext.getString(R.string.pref_default_map_type)));
    }

    @Override
    public int getUnitsType() {
        return Integer.parseInt(mSharedPreferences.getString(
                mAppContext.getString(R.string.pref_key_unit_type),
                mAppContext.getString(R.string.pref_default_unit_type)));
    }

    @Override
    public void addListener(OnPrefChangeListener listener) {
        mListener = (sharedPreferences, key) -> {
            if (key.equals(mAppContext.getString(R.string.pref_key_map_type))) {
                listener.onMapTypeChange(getMapType());

            } else if (key.equals(mAppContext.getString(R.string.pref_key_unit_type))) {
                listener.onUnitTypeChange(getUnitsType());
            }
        };
        mSharedPreferences.registerOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    public void removeListener(OnPrefChangeListener listener) {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(mListener);
    }
}
