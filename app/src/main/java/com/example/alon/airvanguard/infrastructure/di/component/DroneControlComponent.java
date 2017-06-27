package com.example.alon.airvanguard.infrastructure.di.component;

import com.example.alon.airvanguard.infrastructure.di.module.DroneControlModule;
import com.example.alon.airvanguard.infrastructure.di.scope.ActivityScope;
import com.example.alon.airvanguard.presentation.drone_control.DroneControlActivity;

import dagger.Component;

/**
 * Created by alon on 4/18/17.
 */
@ActivityScope
@Component(modules = DroneControlModule.class,dependencies = AppComponent.class)
public interface DroneControlComponent {

    void inject(DroneControlActivity activity);
}
