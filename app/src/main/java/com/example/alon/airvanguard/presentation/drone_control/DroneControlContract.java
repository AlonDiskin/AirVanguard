package com.example.alon.airvanguard.presentation.drone_control;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.*;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;
import com.example.alon.airvanguard.presentation.map.BaseMapContract;

import java.util.ArrayList;

/**
 * MVP contract between the {@link DroneControlContract.View}
 * and {@link DroneControlContract.Presenter}.
 */
public interface DroneControlContract {

    interface View extends BaseMapContract.View {

        /**
         * Navigate to the settings UI.
         */
        void showSettingsScreen();

        void removeDistressCall(@NonNull DistressCall distressCall);

        boolean isMapShowing();

        /**
         * Notify the user that drone connection was
         * established successfully.
         */
        void notifyDroneConnected();

        /**
         * Notify the user that drone was disconnected.
         */
        void notifyDroneDisconnected();

        /**
         * Update drone telemetry {@code speed} value
         *
         * @param speed drone speed value
         */
        void updateDroneAirSpeed(String speed);

        /**
         * Update drone telemetry {@code altitude} value.
         *
         * @param altitude drone altitude value
         */
        void updateDroneAltitude(String altitude);

        /**
         * Update drone telemetry vertical {@code speed}
         * value.
         *
         * @param speed drone vertical speed value.
         */
        void updateDroneVerticalSpeed(String speed);

        /**
         * Update drone geo location
         *
         * @param lat drone lat value
         * @param lon drone lon value
         */
        void updateDroneLocation(double lat,double lon);

        void updateDroneDistanceFromHome(String distance);

        /**
         * Update drone telemetry vehicle mode
         *
         * @param mode drone mode.
         */
        void updateDroneVehicleMode(String mode);

        /**
         * Update the drone arming state.
         *
         * @param isArmed drone arming state.
         */
        void updateDroneArmingState(boolean isArmed);

        /**
         * Navigate to edit mission UI.
         */
        void showEditMissionScreen();

        /**
         * Update drone telemetry battery vale
         * to be full.
         */
        void showDroneBatteryFull();

        /**
         * Update drone telemetry battery vale
         * to be in the 90s percentage range.
         */
        void showDroneBattery90Range(int battery);

        /**
         * Update drone telemetry battery vale
         * to be in the 80s percentage range.
         */
        void showDroneBattery80Range(int battery);

        /**
         * Update drone telemetry battery vale
         * to be in the 60 to 80 percentage range.
         */
        void showDroneBattery60Range(int battery);

        /**
         * Update drone telemetry battery vale
         * to be in the 50s percentage range.
         */
        void showDroneBattery50Range(int battery);

        /**
         * Update drone telemetry battery vale
         * to be in the 30 to 50 percentage range.
         */
        void showDroneBattery30Range(int battery);

        /**
         * Update drone telemetry battery vale
         * to be in the 20s percentage range.
         */
        void showDroneBattery20Range(int battery);

        /**
         * Alert the user that the battery value
         * is low and below 10 percentage range.
         *
         * @param battery current battery value
         */
        void showDroneBatteryAlertRange(int battery);

        void showVerticalSpeedUp();

        void showVerticalSpeedDown();

        void showNoVerticalSpeed();

        /**
         * Notify the user that drone connection failed.
         *
         * @param errorMessage connection failure reason.
         */
        void notifyDroneConnectionFailed(String errorMessage);

        void notifyDroneArmingFailed(String reason);

        void initTelemetryMetric();

        void initTelemetryImperial();

        void initDroneControlPanel();
    }

    interface Presenter extends BaseMapContract.Presenter {

        /**
         * Navigate to the the settings screen.
         */
        void openSettingsScreen();

        /**
         * Navigate to the edit mission screen.
         */
        void openEditMissionScreen();

        /**
         * Connect the drone to app.
         *
         * @param type drone connection type.
         */
        void connectDrone(DroneConnectionType type);

        /**
         * Disconnect currently connected drone.
         */
        void disconnectDrone();

        /**
         * Change the drone vehicle mode.
         *
         * @param mode the new drone mode.
         */
        void changeDroneMode(@NonNull String mode);

        /**
         * Takeoff the drone to given {@code height}.
         *
         * @param height takeoff height value.
         */
        void droneTakeoff(int height);

        /**
         * Arm the drone.
         */
        void armDrone();

        void uploadMission(@NonNull ArrayList<MissionPoint> points);

        void setDroneArming(boolean armDrone);

        void onSettingsScreenClosed();

        void initTelemetryView();
    }
}
