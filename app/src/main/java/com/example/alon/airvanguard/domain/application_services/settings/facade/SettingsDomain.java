package com.example.alon.airvanguard.domain.application_services.settings.facade;

import com.example.alon.airvanguard.domain.application_services.common.Domain;

/**
 * Settings feature domain facade.
 */

public interface SettingsDomain extends Domain {

    /**
     * Starts the notification service that will notify
     * the app user about incoming distress calls by
     * sending a status bar notification.
     */
    void startDistressCallNotificationService();

    /**
     * Stops the distress call status bar
     * notification service.
     */
    void stopDistressCallNotificationService();

    /**
     * Change the current drone telemetry data
     * units type to the given {@code type}.
     *
     * @param type
     */
    void changeDataUnitType(int type);
}
