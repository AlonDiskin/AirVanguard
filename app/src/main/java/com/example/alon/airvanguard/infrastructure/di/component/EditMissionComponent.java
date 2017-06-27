package com.example.alon.airvanguard.infrastructure.di.component;

import com.example.alon.airvanguard.infrastructure.di.module.EditMissionModule;
import com.example.alon.airvanguard.infrastructure.di.scope.ActivityScope;
import com.example.alon.airvanguard.presentation.edit_mission.EditMissionActivity;

import dagger.Component;

/**
 * Created by alon on 5/19/17.
 */
@ActivityScope
@Component(modules = EditMissionModule.class, dependencies = AppComponent.class)
public interface EditMissionComponent {

    void inject(EditMissionActivity activity);
}
