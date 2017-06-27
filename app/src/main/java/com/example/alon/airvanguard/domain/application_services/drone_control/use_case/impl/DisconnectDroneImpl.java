package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.DisconnectDrone;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

import javax.inject.Inject;

/**
 * {@link DisconnectDrone} implementation class.
 */

public class DisconnectDroneImpl implements DisconnectDrone {

    private DroneControlTower mDroneControlTower;

    @Inject
    public DisconnectDroneImpl(DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Override
    public void execute() {
        mDroneControlTower.disconnectDrone();
    }
}
