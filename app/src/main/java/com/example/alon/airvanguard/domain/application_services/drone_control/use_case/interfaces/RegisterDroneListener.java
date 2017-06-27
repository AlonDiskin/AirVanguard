package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.*;

/**
 * Register drone events listener use case.
 */

public interface RegisterDroneListener {

    /**
     * Register the given {@code listener} a drone
     * events listener.
     *
     * @param listener the listener instance to register.
     */
    void register(DroneEventListener listener);
}
