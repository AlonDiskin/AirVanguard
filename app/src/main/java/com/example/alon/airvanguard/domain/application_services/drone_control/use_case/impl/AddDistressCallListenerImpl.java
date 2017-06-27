package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.UseCase;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.AddDistressCallListener;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository.*;

import javax.inject.Inject;

/**
 * {@link AddDistressCallListener} implementation class.
 */

public class AddDistressCallListenerImpl implements AddDistressCallListener, UseCase<DistressCallListener,Void> {

    private DistressCallRepository mDistressCallRepository;

    @Inject
    public AddDistressCallListenerImpl(@NonNull DistressCallRepository distressCallRepository) {
        mDistressCallRepository = distressCallRepository;
    }

    @Override
    public void addListener(@NonNull DistressCallListener listener) {
        executeUseCase(listener);
    }

    @Nullable
    @Override
    public Void executeUseCase(@Nullable DistressCallListener param) {
        mDistressCallRepository.addDistressCallListener(param);
        return null;
    }
}
