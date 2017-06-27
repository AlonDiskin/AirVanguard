package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.UseCase;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.ChangeDroneMode;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

import javax.inject.Inject;

/**
 * {@link ChangeDroneMode} implementation class.
 */

public class ChangeDroneModeImpl implements ChangeDroneMode,UseCase<String,Void> {

    private DroneControlTower mDroneControlTower;

    @Inject
    public ChangeDroneModeImpl(@NonNull DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Override
    public void changeMode(@NonNull String mode) {
        executeUseCase(mode);
    }

    @Override
    public Void executeUseCase(@Nullable String param) {
        mDroneControlTower.setDroneMode(param);
        return null;
    }
}
