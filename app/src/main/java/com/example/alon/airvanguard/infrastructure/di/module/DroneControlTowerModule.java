package com.example.alon.airvanguard.infrastructure.di.module;

import android.content.Context;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTowerImpl;
import com.o3dr.android.client.ControlTower;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alon on 4/21/17.
 */
@Module
public class DroneControlTowerModule {

    @Provides
    @Singleton
    ControlTower providesControlTower(Context appContext) {
        return new ControlTower(appContext);
    }

    @Provides
    @Singleton
    DroneControlTower providesDroneControlTower(ControlTower controlTower) {
        return new DroneControlTowerImpl(controlTower);
    }
}
