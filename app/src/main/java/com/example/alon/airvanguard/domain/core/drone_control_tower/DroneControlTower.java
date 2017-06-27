package com.example.alon.airvanguard.domain.core.drone_control_tower;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.core.entity.Location;
import com.example.alon.airvanguard.domain.core.entity.DroneState;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;
import com.example.alon.airvanguard.domain.core.value.AirSpeed;
import com.example.alon.airvanguard.domain.core.value.DroneAltitude;
import com.example.alon.airvanguard.domain.core.value.HomeDistance;
import com.example.alon.airvanguard.domain.core.value.VerticalSpeed;

import java.util.ArrayList;

/**
 * Domain model interface. This interface defines
 * methods for interacting with , and controlling a
 * drone.
 */

public interface DroneControlTower {

    /**
     * Enumeration for unit system types.
     */
    enum DataUnitType {METRIC, IMPERIAL}

    enum DroneMode {LAND,RTL,GUIDED}

    /**
     * Enumeration that is representing different
     * communication connection types with a drone.
     */
    enum DroneConnectionType {UDP,USB}

    /**
     *
     */
    interface DroneTowerListener {

        void onConnectedToDroneTower();

        void onDisconnectedFromTower();
    }

    interface DroneArmingFailure {

        void onArmingFailed(String errorMessage);
    }

    /**
     * Defines listener invocation methods for
     * various drone events.
     */
    interface DroneEventListener {

        void onDroneConnected();

        void onDroneConnectionFailed(String reason);

        void onDroneDisconnected();

        void onDroneAltitudeChange(DroneAltitude altitude);

        void onDroneVerticalSpeedChange(VerticalSpeed speed);

        void onDroneAirSpeedChange(AirSpeed speed);

        void onDroneLocationChange(double lat, double lon);

        void onDroneBatteryChange(double battery);

        void onDroneVehicleModeChange(String mode);

        void onDroneArmingStateChange(boolean isArmed);

        void onDroneDistanceFromHomeChange(HomeDistance distance);
    }

    /**
     * Get current state of the  drone.
     *
     * @param state drone state.
     */
    void getCurrentState(DroneState state);

    /**
     * Set the unit system type to be used
     * by the control tower for telemetry output.
     *
     * @param type unit system type.
     */
    void setUnitSystemType(DataUnitType type);

    void connectTower(@NonNull DroneTowerListener listener);


    void connectDrone(DroneConnectionType type);

    /**
     * Register a drone events listener
     *
     * @param listener the listener to register.
     */
    void registerDroneEventsListener(DroneEventListener listener);

    /**
     * Unregister a drone events listener
     *
     * @param listener the listener to unregister.
     */
    void unregisterDroneEventsListener(DroneEventListener listener);

    void disconnectTower();

    /**
     * Disconnect currently connected drone.
     */
    void disconnectDrone();

    void setDroneMode(@NonNull String mode);

    /**
     * Arms the currently connected drone
     */
    void armDrone();

    void setDroneArming(boolean armDrone,DroneArmingFailure failure);

    /**
     * Starts a drone takeoff to the specified height.
     *
     * @param height takeoff height.
     */
    void takeoff(int height);

    @Nullable
    Location getDroneLocation();

    void uploadMission(@NonNull ArrayList<MissionPoint> points);
}
