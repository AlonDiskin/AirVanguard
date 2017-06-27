package com.example.alon.airvanguard.domain.application_services.settings.use_case.impl;

import com.example.alon.airvanguard.domain.application_services.settings.use_case.interfaces.ChangeDataUnitType;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

import javax.inject.Inject;

/**
 * {@link ChangeDataUnitType} use case implementation.
 */

public class ChangeDataUnitTypeImpl implements ChangeDataUnitType {

    private DroneControlTower mDroneControlTower;

    @Inject
    public ChangeDataUnitTypeImpl(DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Override
    public void execute(int type) {
        switch (type) {
            case 0:
                mDroneControlTower.setUnitSystemType(DroneControlTower.DataUnitType.METRIC);
                break;

            case 1:
                mDroneControlTower.setUnitSystemType(DroneControlTower.DataUnitType.IMPERIAL);
                break;

            default:
                break;

        }
    }
}
