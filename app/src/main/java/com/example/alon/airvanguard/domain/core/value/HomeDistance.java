package com.example.alon.airvanguard.domain.core.value;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

/**
 * Created by alon on 6/27/17.
 */

public class HomeDistance {

    private double distance;
    private DroneControlTower.DataUnitType unitType;

    public HomeDistance(double distance, DroneControlTower.DataUnitType unitType) {
        this.distance = distance;
        this.unitType = unitType;
    }

    public double getDistance() {
        return distance;
    }

    public DroneControlTower.DataUnitType getUnitType() {
        return unitType;
    }

    @Override
    public String toString() {
        if (this.unitType == DroneControlTower.DataUnitType.METRIC) {
            return String.format("%3.1f", this.distance) + "m";

        } else {
            return String.format("%3.1f", this.distance) + "ft";
        }
    }
}
