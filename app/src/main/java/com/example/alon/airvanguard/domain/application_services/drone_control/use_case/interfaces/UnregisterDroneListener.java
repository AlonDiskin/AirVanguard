package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.*;

/**
 * Unregister drone events listener use case.
 */

public interface UnregisterDroneListener {

    /**
     * Execute this use case by unregistering
     * the given {@code listener} instance.
     *
     * @param listener the listener to unregister.
     */
    void execute(DroneEventListener listener);
}
