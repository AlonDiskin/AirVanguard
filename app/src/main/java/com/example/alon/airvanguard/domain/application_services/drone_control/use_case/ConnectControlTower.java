package com.example.alon.airvanguard.domain.application_services.drone_control.use_case;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.common.AsyncUseCase;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.DroneTowerListener;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;

import javax.inject.Inject;

import static com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.DataUnitType.IMPERIAL;
import static com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.DataUnitType.METRIC;

/**
 * Created by alon on 4/21/17.
 */

public class ConnectControlTower implements AsyncUseCase<DroneTowerListener,Void> {

    private DroneControlTower mDroneControlTower;
    private UserPrefsRepository mUserPrefsRepository;

    @Inject
    public ConnectControlTower(@NonNull DroneControlTower droneControlTower,
                               @NonNull UserPrefsRepository userPrefsRepository) {
        mDroneControlTower = droneControlTower;
        mUserPrefsRepository = userPrefsRepository;
    }

    @Override
    public void executeUseCase(@Nullable DroneTowerListener param, @Nullable Success<Void> success, @Nullable Failure failure) {
        if (param != null) {
            mDroneControlTower.connectTower(param);
            switch (mUserPrefsRepository.getUnitsType()) {
                case 0:
                    mDroneControlTower.setUnitSystemType(METRIC);
                    break;

                case 1:
                    mDroneControlTower.setUnitSystemType(IMPERIAL);
                    break;
            }

        }
    }
}
