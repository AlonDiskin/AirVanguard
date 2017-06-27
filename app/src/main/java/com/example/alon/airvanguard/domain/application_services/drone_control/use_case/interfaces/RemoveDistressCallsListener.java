package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository.*;

/**
 * Remove Distress Calls Listener use case interface.
 */

public interface RemoveDistressCallsListener {

    /**
     * Remove the given listener from {@link DistressCallRepository}.
     *
     * @param listener {@link DistressCallListener}
     */
    void removeListener(@NonNull DistressCallListener listener);
}
