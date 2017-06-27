package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.UseCase;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.RemoveDistressCallsListener;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository.*;

import javax.inject.Inject;

/**
 * {@link RemoveDistressCallsListener} implementation class.
 */

public class RemoveDistressCallsListenerImpl implements RemoveDistressCallsListener,UseCase<DistressCallListener,Void> {

    private DistressCallRepository mDistressCallRepository;

    @Inject
    public RemoveDistressCallsListenerImpl(@NonNull DistressCallRepository distressCallRepository) {
        mDistressCallRepository = distressCallRepository;
    }

    @Nullable
    @Override
    public Void executeUseCase(@Nullable DistressCallListener param) {
        if (param != null) {
            mDistressCallRepository.removeDistressCallListener(param);
        }
        return null;
    }

    @Override
    public void removeListener(@NonNull DistressCallListener listener) {
        executeUseCase(listener);
    }
}
