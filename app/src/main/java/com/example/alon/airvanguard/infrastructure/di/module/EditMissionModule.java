package com.example.alon.airvanguard.infrastructure.di.module;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.GetAllDistressCallsImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.GetMapTypeImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetAllDistressCalls;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetMapType;
import com.example.alon.airvanguard.domain.application_services.edit_mission.use_case.interfaces.GetDroneLocation;
import com.example.alon.airvanguard.domain.application_services.edit_mission.use_case.impl.GetDroneLocationImpl;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.application_services.edit_mission.facade.EditMissionDomain;
import com.example.alon.airvanguard.domain.application_services.edit_mission.facade.EditMissionDomainImpl;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;
import com.example.alon.airvanguard.infrastructure.di.scope.ActivityScope;
import com.example.alon.airvanguard.presentation.edit_mission.EditMissionContract;
import com.example.alon.airvanguard.presentation.edit_mission.EditMissionPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger di {@link Module} for Edit Mission feature
 */
@Module
public class EditMissionModule {

    private EditMissionContract.View mView;

    public EditMissionModule(@NonNull EditMissionContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    EditMissionContract.View provideView() {
        return mView;
    }

    @Provides
    @ActivityScope
    GetMapType providesGetMapLookUseCase(UserPrefsRepository userPrefsRepository) {
        return new GetMapTypeImpl(userPrefsRepository);
    }

    @Provides
    @ActivityScope
    GetDroneLocation provideGetDroneLocation(DroneControlTower droneControlTower) {
        return new GetDroneLocationImpl(droneControlTower);
    }

    @Provides
    @ActivityScope
    GetAllDistressCalls provideGetAllDistressCalls(DistressCallRepository repository) {
        return new GetAllDistressCallsImpl(repository);
    }

    @Provides
    @ActivityScope
    EditMissionDomain provideDomain(GetMapType getMapType,
                                    GetDroneLocation getDroneLocation,
                                    GetAllDistressCalls getAllDistressCalls) {
        return new EditMissionDomainImpl(getMapType,getDroneLocation,getAllDistressCalls);
    }

    @Provides
    @ActivityScope
    EditMissionContract.Presenter providePresenter(EditMissionContract.View view,EditMissionDomain domain) {
        return new EditMissionPresenter(view, domain);
    }
}
