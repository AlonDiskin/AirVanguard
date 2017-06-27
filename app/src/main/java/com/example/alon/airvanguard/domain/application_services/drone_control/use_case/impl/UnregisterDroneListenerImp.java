package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.UnregisterDroneListener;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

import javax.inject.Inject;

/**
 * {@link UnregisterDroneListener} implementation class.
 */

public class UnregisterDroneListenerImp implements UnregisterDroneListener {

    private DroneControlTower mDroneControlTower;

    @Inject
    public UnregisterDroneListenerImp(DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Override
    public void execute(DroneControlTower.DroneEventListener listener) {
        mDroneControlTower.unregisterDroneEventsListener(listener);
    }
}
