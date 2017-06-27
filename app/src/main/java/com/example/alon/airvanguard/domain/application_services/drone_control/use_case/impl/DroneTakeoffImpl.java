package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.UseCase;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.DroneTakeoff;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

import javax.inject.Inject;

/**
 * {@link DroneTakeoff} implementation class.
 */

public class DroneTakeoffImpl implements DroneTakeoff,UseCase<Integer,Void> {

    private DroneControlTower mDroneControlTower;

    @Inject
    public DroneTakeoffImpl(DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }
    @Override
    public Void executeUseCase(@Nullable Integer param) {
        mDroneControlTower.takeoff(param);
        return null;
    }

    @Override
    public void takeoff(int height) {
        executeUseCase(height);
    }
}
