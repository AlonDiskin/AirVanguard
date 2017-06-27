package com.example.alon.airvanguard.presentation.settings;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.application_services.settings.facade.SettingsDomain;
import com.example.alon.airvanguard.presentation.common.BasePresenter;

import javax.inject.Inject;

/**
 * Settings feature presenter class.
 */

public class SettingsPresenter extends BasePresenter<SettingsContract.View,SettingsDomain> implements SettingsContract.Presenter {

    @Inject
    public SettingsPresenter(@NonNull SettingsContract.View view, @NonNull SettingsDomain domain) {
        super(view, domain);
    }

    @Override
    public void changeDataUnitTypeChange(int type) {
        getDomain().changeDataUnitType(type);
    }
}
