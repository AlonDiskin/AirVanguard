package com.example.alon.airvanguard.domain.application_services.edit_mission.use_case.interfaces;

import com.example.alon.airvanguard.domain.core.entity.Location;

/**
 * Get Drone Location use case interface
 */

public interface GetDroneLocation {

    /**
     *
     * @return the current drone {@link Location}
     */
    Location getLocation();
}
