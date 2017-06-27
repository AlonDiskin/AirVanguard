package com.example.alon.airvanguard.presentation.drone_control.dialogs;

import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.*;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.presentation.drone_control.ConnectDroneDialog;
import com.example.alon.airvanguard.presentation.drone_control.DroneControlActivity;

/**
 * {@link DroneControlActivity} dialogs actions listener callbacks.
 */

public interface DroneControlDialogs {

    /**
     * Called upon {@link DroneTakeoffDialog} height selection.
     *
     * @param height
     */
    void onDroneTakeoffHeightSelected(int height);

    /**
     * Called when the user has selected connection
     * {@code type} from {@link ConnectDroneDialog}.
     *
     * @param type connection type with the drone.
     */
    void onDroneConnectionTypeSelected(DroneConnectionType type);

    /**
     * Called when the user has confirmed drone
     * disconnection by clicking the positive button
     * of {@link DisconnectDroneDialog}.
     */
    void onDisconnectConfirmed();

    void onEditDistressCallMission(DistressCall call);
}
