package com.example.alon.airvanguard.domain.core.value;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

/**
 * Created by alon on 6/27/17.
 */

public class DroneAltitude {

    private double altitude;
    private DroneControlTower.DataUnitType unitType;

    public DroneAltitude(double altitude, DroneControlTower.DataUnitType unitType) {
        this.altitude = altitude;
        this.unitType = unitType;
    }

    public double getAltitude() {
        return altitude;
    }

    public DroneControlTower.DataUnitType getUnitType() {
        return unitType;
    }

    @Override
    public String toString() {
        if (this.unitType == DroneControlTower.DataUnitType.METRIC) {
            return String.format("%3.1f", this.altitude) + "m";

        } else {
            return String.format("%3.1f", this.altitude) + "ft";
        }
    }
}
