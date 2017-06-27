package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

/**
 * Created by alon on 6/27/17.
 */

public interface GetDataUnitType {

    DroneControlTower.DataUnitType execute();
}
