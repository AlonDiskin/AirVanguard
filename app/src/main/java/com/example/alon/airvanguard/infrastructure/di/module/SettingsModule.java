package com.example.alon.airvanguard.infrastructure.di.module;

import com.example.alon.airvanguard.domain.application_services.settings.facade.SettingsDomain;
import com.example.alon.airvanguard.domain.application_services.settings.facade.SettingsDomainImpl;
import com.example.alon.airvanguard.domain.application_services.settings.use_case.interfaces.ChangeDataUnitType;
import com.example.alon.airvanguard.domain.application_services.settings.use_case.impl.ChangeDataUnitTypeImpl;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.infrastructure.di.scope.ActivityScope;
import com.example.alon.airvanguard.presentation.settings.SettingsContract;
import com.example.alon.airvanguard.presentation.settings.SettingsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alon on 6/27/17.
 */
@Module
public class SettingsModule {

    private SettingsContract.View mView;

    public SettingsModule(SettingsContract.View view) {
        mView = view;
    }

    @ActivityScope
    @Provides
    SettingsContract.View provideView() {
        return mView;
    }

    @ActivityScope
    @Provides
    ChangeDataUnitType provideChangeDataUnitType(DroneControlTower droneControlTower) {
        return new ChangeDataUnitTypeImpl(droneControlTower);
    }

    @ActivityScope
    @Provides
    SettingsDomain provideDomain(ChangeDataUnitType changeDataUnitType) {
        return new SettingsDomainImpl(changeDataUnitType);
    }

    @ActivityScope
    @Provides
    SettingsContract.Presenter providePresenter(SettingsContract.View view,SettingsDomain domain) {
        return new SettingsPresenter(view, domain);
    }
}
