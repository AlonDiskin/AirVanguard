package com.example.alon.airvanguard.domain.application_services.drone_control.facade;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.application_services.common.Domain;
import com.example.alon.airvanguard.domain.application_services.common.AsyncUseCase.*;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository.*;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.*;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository.*;

import java.util.ArrayList;
import java.util.List;

/**
 * DroneControl domain layer facade.
 */

public interface DroneControlDomain extends Domain {

    /**
     * Get the current map type as was
     * selected by the user in app prefs.
     *
     * @return map type.
     */
    int getMapType();

    DataUnitType getDataUnitType();

    /**
     * Add a listener for incoming distress calls.
     *
     * @param listener listener to be invoked upon
     * incoming distress call.
     */
    void addDistressCallListener(@NonNull DistressCallListener listener);

    /**
     * Remove the given {@code listener}. No
     * incoming distress calls will be passed to this
     * listener after removal.
     *
     * @param listener the listener to remove.
     */
    void removeDistressCallListener(@NonNull DistressCallListener listener);

    /**
     * Connect the given listener
     *
     * @param listener
     */
    void connectDroneControlTower(@NonNull DroneTowerListener listener);

    /**
     * Loads all existing distress calls, and
     * invokes the {@code success} call back upon finishing.
     *
     * @param success loading success callback
     * @param failure
     */
    void getAllDistressCalls(@NonNull Success<List<DistressCall>> success, @NonNull Failure failure);

    /**
     * changes the current drone mode to the
     * given {@code mode}.
     *
     * @param mode the new mode
     */
    void changeDroneMode(@NonNull String mode);

    /**
     * Arms the current drone.
     */
    void armDrone();

    /**
     * Takes off the current drone to the specifed {@code height}.
     *
     * @param height takeoff height.
     */
    void droneTakeoff(int height);

    /**
     * Uploads the given mission {@code points}.
     *
     * @param points
     */
    void uploadMission(@NonNull ArrayList<MissionPoint> points);

    /**
     *
     * @param listener
     */
    void registerDroneEventsListener(DroneEventListener listener);

    void unregisterDroneEventsListener(DroneEventListener listener);

    /**
     * Establish connection with a drone.
     *
     * @param type connection type.
     */
    void connectDrone(DroneConnectionType type);

    /**
     * Disconnect the current drone.
     */
    void disconnectDrone();

    /**
     * Sets the drone arming.
     *
     * @param armDrone
     * @param failure
     */
    void setDroneArming(boolean armDrone,DroneArmingFailure failure);

    void addPrefsListener(OnPrefChangeListener listener);

    void removePrefsListener(OnPrefChangeListener listener);
}
