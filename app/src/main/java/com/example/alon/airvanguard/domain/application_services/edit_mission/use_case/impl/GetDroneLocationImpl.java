package com.example.alon.airvanguard.domain.application_services.edit_mission.use_case.impl;

import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.UseCase;
import com.example.alon.airvanguard.domain.application_services.edit_mission.use_case.interfaces.GetDroneLocation;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.core.entity.Location;

import javax.inject.Inject;

/**
 * {@link GetDroneLocation} implementation class
 */

public class GetDroneLocationImpl implements GetDroneLocation,UseCase<Void,Location> {

    private DroneControlTower mDroneControlTower;

    @Inject
    public GetDroneLocationImpl(DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Override
    public Location getLocation() {
        return executeUseCase(null);
    }

    @Nullable
    @Override
    public Location executeUseCase(@Nullable Void param) {
        return mDroneControlTower.getDroneLocation();
    }
}
