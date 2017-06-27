package com.example.alon.airvanguard.infrastructure.di.component;


import android.content.Context;

import com.example.alon.airvanguard.infrastructure.di.module.AppModule;
import com.example.alon.airvanguard.infrastructure.di.module.DroneControlTowerModule;
import com.example.alon.airvanguard.infrastructure.di.module.RepositoryModule;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alon on 4/18/17.
 */
@Singleton
@Component(modules = {AppModule.class, RepositoryModule.class, DroneControlTowerModule.class})
public interface AppComponent {

    Context context();

    UserPrefsRepository userPrefsRepository();

    DistressCallRepository distressCallRepository();

    DroneControlTower droneControlTower();
}
