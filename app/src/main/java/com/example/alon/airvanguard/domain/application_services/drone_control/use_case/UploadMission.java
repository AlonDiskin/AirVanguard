package com.example.alon.airvanguard.domain.application_services.drone_control.use_case;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.UseCase;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by alon on 5/25/17.
 */

public class UploadMission implements UseCase<ArrayList<MissionPoint>,Void> {

    private DroneControlTower mDroneControlTower;

    @Inject
    public UploadMission(@NonNull DroneControlTower droneControlTower) {
        mDroneControlTower = droneControlTower;
    }

    @Nullable
    @Override
    public Void executeUseCase(@Nullable ArrayList<MissionPoint> param) {
        mDroneControlTower.uploadMission(param);
        return null;
    }
}
