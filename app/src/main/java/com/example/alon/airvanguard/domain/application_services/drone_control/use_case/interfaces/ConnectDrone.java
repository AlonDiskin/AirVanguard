package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.*;

/**
 * Connect drone use case.
 */

public interface ConnectDrone {

    /**
     * Execute this use case by connecting to
     * a drone with the specified connection {@code type}.
     *
     * @param type communication connection type.
     */
    void execute(DroneConnectionType type);
}
