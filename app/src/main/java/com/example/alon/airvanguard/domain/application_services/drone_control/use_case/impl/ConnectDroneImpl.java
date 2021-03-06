package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.ConnectDrone;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

import javax.inject.Inject;

/**
 * {@link ConnectDrone} implementation class.
 */

public class ConnectDroneImpl implements ConnectDrone {

    private DroneControlTower mDroneControlTower;

    @Inject
    public ConnectDroneImpl(DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Override
    public void execute(DroneControlTower.DroneConnectionType type) {
        mDroneControlTower.connectDrone(type);
    }
}
