package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

/**
 * DroneTakeoff use case interface.
 */

public interface DroneTakeoff {

    /**
     * Takes off the drone to the chosen altitude.
     * @param height takeoff height.
     */
    void takeoff(int height);
}
