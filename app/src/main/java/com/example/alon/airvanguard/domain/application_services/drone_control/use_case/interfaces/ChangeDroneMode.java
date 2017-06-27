package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import android.support.annotation.NonNull;

/**
 * Change drone mode use case.
 */

public interface ChangeDroneMode {

    /**
     * Change the current mode of the drone.
     *
     * @param mode
     */
    void changeMode(@NonNull String mode);
}
