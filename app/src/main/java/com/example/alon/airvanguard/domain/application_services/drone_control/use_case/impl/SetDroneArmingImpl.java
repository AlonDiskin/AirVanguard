package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.SetDroneArming;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.*;

import javax.inject.Inject;

/**
 * Created by alon on 6/18/17.
 */

public class SetDroneArmingImpl implements SetDroneArming {

    private DroneControlTower mDroneControlTower;

    @Inject
    public SetDroneArmingImpl(DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Override
    public void setArming(boolean armDrone,DroneArmingFailure failure) {
        mDroneControlTower.setDroneArming(armDrone,failure);
    }
}
