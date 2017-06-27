package com.example.alon.airvanguard.domain.core.value;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

/**
 * Created by alon on 6/27/17.
 */

public class VerticalSpeed {

    private double speed;
    private DroneControlTower.DataUnitType type;

    public VerticalSpeed(double speed, DroneControlTower.DataUnitType type) {
        this.speed = speed;
        this.type = type;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public DroneControlTower.DataUnitType getType() {
        return type;
    }

    @Override
    public String toString() {
        if (type == DroneControlTower.DataUnitType.METRIC) {
            return String.format("%3.1f", getSpeed()) + "m/s";

        } else {
            return String.format("%3.1f", getSpeed()) + "ft/s";
        }
    }
}
