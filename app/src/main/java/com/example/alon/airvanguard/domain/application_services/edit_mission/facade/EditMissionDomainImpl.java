package com.example.alon.airvanguard.domain.application_services.edit_mission.facade;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.application_services.common.AsyncUseCase.*;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetAllDistressCalls;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetMapType;
import com.example.alon.airvanguard.domain.application_services.edit_mission.use_case.interfaces.GetDroneLocation;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.core.entity.Location;

import java.util.List;

import javax.inject.Inject;

/**
 * {@link EditMissionDomain} implementation class
 */

public class EditMissionDomainImpl implements EditMissionDomain {

    private GetMapType mGetMapType;
    private GetDroneLocation mGetDroneLocation;
    private GetAllDistressCalls mGetAllDistressCalls;

    @Inject
    public EditMissionDomainImpl(@NonNull GetMapType getMapType,
                                 @NonNull GetDroneLocation getDroneLocation,
                                 @NonNull GetAllDistressCalls getAllDistressCalls) {
        mGetMapType = getMapType;
        mGetDroneLocation = getDroneLocation;
        mGetAllDistressCalls = getAllDistressCalls;
    }

    @Override
    public int getMapType() {
        return mGetMapType.getType();
    }

    @Override
    public Location getDroneLocation() {
        return mGetDroneLocation.getLocation();
    }

    @Override
    public void loadDistressCalls(Success<List<DistressCall>> success, Failure failure) {
        mGetAllDistressCalls.getCalls(success,failure);
    }

}
