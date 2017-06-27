package com.example.alon.airvanguard.domain.core.value;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

/**
 * Created by alon on 6/27/17.
 */

public class AirSpeed {

    private double airSpeed;
    private DroneControlTower.DataUnitType unitType;

    public AirSpeed(double airSpeed, DroneControlTower.DataUnitType unitType) {
        this.airSpeed = airSpeed;
        this.unitType = unitType;
    }

    public double getAirSpeed() {
        return airSpeed;
    }

    public DroneControlTower.DataUnitType getUnitType() {
        return unitType;
    }

    @Override
    public String toString() {
        if (unitType == DroneControlTower.DataUnitType.METRIC) {
            return String.format("%3.1f", this.airSpeed) + "kmh";

        } else {
            return String.format("%3.1f", this.airSpeed) + "mph";
        }
    }
}
