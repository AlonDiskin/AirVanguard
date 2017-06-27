package com.example.alon.airvanguard.domain.application_services.settings.facade;

import com.example.alon.airvanguard.domain.application_services.settings.use_case.interfaces.ChangeDataUnitType;

import javax.inject.Inject;

/**
 * {@link SettingsDomain} implementation.
 */

public class SettingsDomainImpl implements SettingsDomain {

    private ChangeDataUnitType mChangeDataUnitType;

    @Inject
    public SettingsDomainImpl(ChangeDataUnitType changeDataUnitType) {
        mChangeDataUnitType = changeDataUnitType;
    }

    @Override
    public void startDistressCallNotificationService() {

    }

    @Override
    public void stopDistressCallNotificationService() {

    }

    @Override
    public void changeDataUnitType(int type) {
        mChangeDataUnitType.execute(type);
    }
}
