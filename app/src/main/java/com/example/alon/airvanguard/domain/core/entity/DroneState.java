package com.example.alon.airvanguard.domain.core.entity;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;

/**
 * Current state of a drone.
 */

public class DroneState {

    private boolean connected;
    private boolean armed;
    private boolean flying;
    private DroneControlTower.DroneMode mode;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isArmed() {
        return armed;
    }

    public void setArmed(boolean armed) {
        this.armed = armed;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public DroneControlTower.DroneMode getMode() {
        return mode;
    }

    public void setMode(DroneControlTower.DroneMode mode) {
        this.mode = mode;
    }
}
