package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.example.alon.airvanguard.domain.application_services.common.UseCase;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetMapType;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;

import javax.inject.Inject;

/**
 * Created by alon on 4/18/17.
 */

public class GetMapTypeImpl implements GetMapType,UseCase<Void,Integer> {

    private UserPrefsRepository mUserPrefsRepository;

    @Inject
    public GetMapTypeImpl(@NonNull UserPrefsRepository userPrefsRepository) {
        mUserPrefsRepository = userPrefsRepository;
    }

    @Override
    public int getType() {
        return mUserPrefsRepository.getMapType();
    }

    @Override
    public Integer executeUseCase(@Nullable Void param) {
        return mUserPrefsRepository.getMapType();
    }
}
