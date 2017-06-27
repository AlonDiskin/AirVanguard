package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository.*;

/**
 * Adding a distress call listener use case
 */

public interface AddDistressCallListener {

    /**
     * Add a distress call listener to {@link DistressCallRepository}.
     *
     * @param listener a listener that will be invoked whenever a distress call
     *              is added to the repository.
     */
    void addListener(@NonNull DistressCallListener listener);
}
