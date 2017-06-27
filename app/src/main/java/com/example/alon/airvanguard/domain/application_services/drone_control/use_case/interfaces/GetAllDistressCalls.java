package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.application_services.common.AsyncUseCase.*;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;

import java.util.List;

/**
 * GetAllDistressCalls use case interface.
 */

public interface GetAllDistressCalls {

    /**
     * Retrieve all {@link DistressCall}s from {@link DistressCallRepository}.
     *
     * @param success use case success callback.
     * @param failure use case failure callback.
     */
    void getCalls(@NonNull Success<List<DistressCall>> success, @NonNull Failure failure);
}
