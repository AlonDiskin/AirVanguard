package com.example.alon.airvanguard.infrastructure.di.module;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.AddPrefsListenerImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.ConnectDroneImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.DisconnectDroneImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.GetAllDistressCallsImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.DroneTakeoffImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.AddDistressCallListenerImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.ArmDroneImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.ChangeDroneModeImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.UploadMission;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.GetDataUnitTypeImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.RegisterDroneListenerImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.RemovePrefsListenerImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.SetDroneArmingImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.UnregisterDroneListenerImp;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.AddDistressCallListener;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.AddPrefsListener;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.ArmDrone;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.ChangeDroneMode;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.ConnectDrone;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.DisconnectDrone;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.DroneTakeoff;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetAllDistressCalls;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetDataUnitType;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.GetMapType;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.RegisterDroneListener;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.RemoveDistressCallsListener;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.RemovePrefsListener;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.SetDroneArming;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.UnregisterDroneListener;
import com.example.alon.airvanguard.infrastructure.di.scope.ActivityScope;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.ConnectControlTower;
import com.example.alon.airvanguard.domain.application_services.drone_control.facade.DroneControlDomain;
import com.example.alon.airvanguard.domain.application_services.drone_control.facade.DroneControlDomainImpl;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.GetMapTypeImpl;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl.RemoveDistressCallsListenerImpl;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;
import com.example.alon.airvanguard.presentation.drone_control.DroneControlContract;
import com.example.alon.airvanguard.presentation.drone_control.DroneControlPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alon on 4/18/17.
 */
@Module
public class DroneControlModule {

    private DroneControlContract.View mView;

    public DroneControlModule(@NonNull DroneControlContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    DroneControlContract.View providesView() {
        return mView;
    }

    @Provides
    @ActivityScope
    GetMapType providesGetMapLookUseCase(UserPrefsRepository userPrefsRepository) {
        return new GetMapTypeImpl(userPrefsRepository);
    }

    @Provides
    @ActivityScope
    AddDistressCallListener setDistressCallListenerUseCase(DistressCallRepository repository) {
        return new AddDistressCallListenerImpl(repository);
    }

    @Provides
    @ActivityScope
    RemoveDistressCallsListener removeDistressCallListenerUseCase(DistressCallRepository repository) {
        return new RemoveDistressCallsListenerImpl(repository);
    }

    @Provides
    @ActivityScope
    ConnectControlTower providesConnectControlTower(DroneControlTower droneControlTower,
                                                    UserPrefsRepository userPrefsRepository) {
        return new ConnectControlTower(droneControlTower,userPrefsRepository);
    }

    @Provides
    @ActivityScope
    GetAllDistressCalls providesGetAllDistressCalls(DistressCallRepository distressCallRepository) {
        return new GetAllDistressCallsImpl(distressCallRepository);
    }

    @Provides
    @ActivityScope
    ConnectDrone providesConnectDrone(DroneControlTower droneControlTower) {
        return new ConnectDroneImpl(droneControlTower);
    }

    @Provides
    @ActivityScope
    RegisterDroneListener provideRegisterDroneListener(DroneControlTower droneControlTower) {
        return new RegisterDroneListenerImpl(droneControlTower);
    }

    @Provides
    @ActivityScope
    UnregisterDroneListener provideUnregisterDroneListener(DroneControlTower droneControlTower) {
        return new UnregisterDroneListenerImp(droneControlTower);
    }

    @Provides
    @ActivityScope
    ChangeDroneMode provideChangeDroneMode(DroneControlTower droneControlTower) {
        return new ChangeDroneModeImpl(droneControlTower);
    }

    @Provides
    @ActivityScope
    ArmDrone provideArmDrone(DroneControlTower droneControlTower) {
        return new ArmDroneImpl(droneControlTower);
    }

    @Provides
    @ActivityScope
    DroneTakeoff provideDroneTakeoff(DroneControlTower droneControlTower) {
        return new DroneTakeoffImpl(droneControlTower);
    }

    @Provides
    @ActivityScope
    UploadMission provideUploadMission(DroneControlTower droneControlTower) {
        return new UploadMission(droneControlTower);
    }

    @Provides
    @ActivityScope
    SetDroneArming provideSetDroneArming(DroneControlTower droneControlTower) {
        return new SetDroneArmingImpl(droneControlTower);
    }

    @Provides
    @ActivityScope
    DisconnectDrone provideDisconnectDrone(DroneControlTower droneControlTower) {
        return new DisconnectDroneImpl(droneControlTower);
    }

    @Provides
    @ActivityScope
    AddPrefsListener provideAddPrefsListener(UserPrefsRepository userPrefsRepository) {
        return new AddPrefsListenerImpl(userPrefsRepository);
    }

    @Provides
    @ActivityScope
    RemovePrefsListener provideRemovePrefsListener(UserPrefsRepository userPrefsRepository) {
        return new RemovePrefsListenerImpl(userPrefsRepository);
    }

    @Provides
    @ActivityScope
    GetDataUnitType provideGetDataUnitType(UserPrefsRepository userPrefsRepository) {
        return new GetDataUnitTypeImpl(userPrefsRepository);
    }

    @Provides
    @ActivityScope
    DroneControlDomain providesDomain(GetMapTypeImpl getMapType,
                                      AddDistressCallListener setDistressCallListener,
                                      RemoveDistressCallsListener removeDistressCallListener,
                                      ConnectControlTower connectControlTower,
                                      GetAllDistressCalls getAllDistressCalls,
                                      ConnectDrone connectDrone,
                                      ChangeDroneMode changeDroneMode,
                                      ArmDrone armDrone,
                                      DroneTakeoff droneTakeoff,
                                      UploadMission uploadMission,
                                      RegisterDroneListener registerDroneListener,
                                      UnregisterDroneListener unregisterDroneListener,
                                      SetDroneArming setDroneArming,
                                      DisconnectDrone disconnectDrone,
                                      AddPrefsListener addPrefsListener,
                                      RemovePrefsListener removePrefsListener,
                                      GetDataUnitType getDataUnitType) {
        return new DroneControlDomainImpl(getMapType,setDistressCallListener,removeDistressCallListener,
                connectControlTower,getAllDistressCalls,connectDrone,changeDroneMode, armDrone,
                droneTakeoff,uploadMission,registerDroneListener,unregisterDroneListener,setDroneArming,
                disconnectDrone,addPrefsListener,removePrefsListener,getDataUnitType);
    }

    @Provides
    @ActivityScope
    DroneControlContract.Presenter providesPresenter(DroneControlContract.View view,DroneControlDomain domain) {
        return new DroneControlPresenter(view, domain);
    }
}
