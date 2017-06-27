package com.example.alon.airvanguard.infrastructure.di.component;

import com.example.alon.airvanguard.infrastructure.di.module.SettingsModule;
import com.example.alon.airvanguard.infrastructure.di.scope.ActivityScope;
import com.example.alon.airvanguard.presentation.settings.SettingsActivity;

import dagger.Component;

/**
 * Created by alon on 6/27/17.
 */
@ActivityScope
@Component(modules = SettingsModule.class,dependencies = AppComponent.class)
public interface SettingsComponent {

    void inject(SettingsActivity activity);
}
