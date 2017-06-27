package com.example.alon.airvanguard.domain.application_services.edit_mission.facade;

import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.AsyncUseCase.*;
import com.example.alon.airvanguard.domain.application_services.common.Domain;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.core.entity.Location;

import java.util.List;

/**
 * EditMission domain layer facade interface
 */

public interface EditMissionDomain extends Domain {

    /**
     *
     * @return the current map type used by maps
     * in the application, as chosen by the user
     */
    int getMapType();

    /**
     *
     * @return last known location of the drone,
     * or null if non exist.
     */
    @Nullable
    Location getDroneLocation();

    void loadDistressCalls(Success<List<DistressCall>> success, Failure failure);
}
