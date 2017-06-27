package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetDataUnitType;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;

import javax.inject.Inject;

/**
 * {@link GetDataUnitType} implementation.
 */

public class GetDataUnitTypeImpl implements GetDataUnitType {

    private UserPrefsRepository mUserPrefsRepository;

    @Inject
    public GetDataUnitTypeImpl(UserPrefsRepository userPrefsRepository) {
        mUserPrefsRepository = userPrefsRepository;
    }

    @Override
    public DroneControlTower.DataUnitType execute() {
        int unitTypePref = mUserPrefsRepository.getUnitsType();

        if (unitTypePref == 0) {
            return DroneControlTower.DataUnitType.METRIC;

        } else {
            return DroneControlTower.DataUnitType.IMPERIAL;
        }
    }
}
