package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.AsyncUseCase;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetAllDistressCalls;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * {@link GetAllDistressCalls} implementation class.
 */

public class GetAllDistressCallsImpl implements GetAllDistressCalls,AsyncUseCase<Void,List<DistressCall>> {

    private DistressCallRepository mDistressCallRepository;

    @Inject
    public GetAllDistressCallsImpl(@NonNull DistressCallRepository distressCallRepository) {
        mDistressCallRepository = distressCallRepository;
    }

    @Override
    public void executeUseCase(@Nullable Void param, @Nullable Success<List<DistressCall>> success, @Nullable Failure failure) {
        mDistressCallRepository.getAll(success::onSuccess, failure::onFailure);
    }

    @Override
    public void getCalls(@NonNull Success<List<DistressCall>> success, @NonNull Failure failure) {
        executeUseCase(null,success,failure);
    }
}
