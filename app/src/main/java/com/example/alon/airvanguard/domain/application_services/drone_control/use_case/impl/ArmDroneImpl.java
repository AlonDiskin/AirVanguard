package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.UseCase;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.ArmDrone;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

import javax.inject.Inject;

/**
 * {@link ArmDrone} implementation class.
 */

public class ArmDroneImpl implements ArmDrone,UseCase<Void,Void> {

    private DroneControlTower mDroneControlTower;

    @Inject
    public ArmDroneImpl(@NonNull DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Override
    public void arm() {
        executeUseCase(null);
    }

    @Nullable
    @Override
    public Void executeUseCase(@Nullable Void param) {
        mDroneControlTower.armDrone();
        return null;
    }
}
