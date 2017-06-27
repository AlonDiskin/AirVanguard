package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.RegisterDroneListener;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

import javax.inject.Inject;

/**
 * {@link RegisterDroneListener} implementation class.
 */

public class RegisterDroneListenerImpl implements RegisterDroneListener {

    private DroneControlTower mDroneControlTower;

    @Inject
    public RegisterDroneListenerImpl(DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Override
    public void register(DroneControlTower.DroneEventListener listener) {
        mDroneControlTower.registerDroneEventsListener(listener);
    }
}
