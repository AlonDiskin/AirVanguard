package com.example.alon.airvanguard.domain.application_services.drone_control.facade;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.application_services.common.AsyncUseCase.*;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.AddDistressCallListener;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.AddPrefsListener;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.ArmDrone;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.ChangeDroneMode;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.ConnectControlTower;
import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.UploadMission;
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
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.*;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository.*;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.DroneTowerListener;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * {@link DroneControlDomain} implementation class.
 */

public class DroneControlDomainImpl implements DroneControlDomain {

    private GetMapType mGetMapType;
    private AddDistressCallListener mAddDistressCalListener;
    private RemoveDistressCallsListener mRemoveDistressCalListener;
    private ConnectControlTower mConnectControlTower;
    private GetAllDistressCalls mGetAllDistressCalls;
    private ConnectDrone mConnectDrone;
    private ChangeDroneMode mChangeDroneMode;
    private ArmDrone mArmDrone;
    private DroneTakeoff mDroneTakeoff;
    private UploadMission mUploadMission;
    private RegisterDroneListener mRegisterDroneListener;
    private UnregisterDroneListener mUnregisterDroneListener;
    private SetDroneArming mSetDroneArming;
    private DisconnectDrone mDisconnectDrone;
    private AddPrefsListener mAddPrefsListener;
    private RemovePrefsListener mRemovePrefsListener;
    private GetDataUnitType mGetDataUnitType;

    @Inject
    public DroneControlDomainImpl(@NonNull GetMapType getMapType,
                                  @NonNull AddDistressCallListener setDistressCallListener,
                                  @NonNull RemoveDistressCallsListener removeDistressCallListener,
                                  @NonNull ConnectControlTower connectControlTower,
                                  @NonNull GetAllDistressCalls getAllDistressCalls,
                                  @NonNull ConnectDrone connectDrone,
                                  @NonNull ChangeDroneMode changeDroneMode,
                                  @NonNull ArmDrone armDrone,
                                  @NonNull DroneTakeoff droneTakeoff,
                                  @NonNull UploadMission uploadMission,
                                  @NonNull RegisterDroneListener registerDroneListener,
                                  @NonNull UnregisterDroneListener unregisterDroneListener,
                                  @NonNull SetDroneArming setDroneArming,
                                  @NonNull DisconnectDrone disconnectDrone,
                                  @NonNull AddPrefsListener addPrefsListener,
                                  @NonNull RemovePrefsListener removePrefsListener,
                                  @NonNull GetDataUnitType getDataUnitType) {
        mGetMapType = getMapType;
        mAddDistressCalListener = setDistressCallListener;
        mRemoveDistressCalListener = removeDistressCallListener;
        mConnectControlTower = connectControlTower;
        mGetAllDistressCalls = getAllDistressCalls;
        mConnectDrone = connectDrone;
        mChangeDroneMode = changeDroneMode;
        mArmDrone = armDrone;
        mDroneTakeoff = droneTakeoff;
        mUploadMission = uploadMission;
        mRegisterDroneListener = registerDroneListener;
        mUnregisterDroneListener = unregisterDroneListener;
        mSetDroneArming = setDroneArming;
        mDisconnectDrone = disconnectDrone;
        mAddPrefsListener = addPrefsListener;
        mRemovePrefsListener = removePrefsListener;
        mGetDataUnitType = getDataUnitType;
    }

    @Override
    public int getMapType() {
        return mGetMapType.getType();
    }

    @Override
    public DataUnitType getDataUnitType() {
        return mGetDataUnitType.execute();
    }

    @Override
    public void addDistressCallListener(@NonNull DistressCallListener listener) {
        mAddDistressCalListener.addListener(listener);
    }

    @Override
    public void removeDistressCallListener(@NonNull DistressCallListener listener) {
        mRemoveDistressCalListener.removeListener(listener);
    }

    @Override
    public void connectDroneControlTower(@NonNull DroneTowerListener listener) {
        mConnectControlTower.executeUseCase(listener,null,null);
    }

    @Override
    public void getAllDistressCalls(@NonNull Success<List<DistressCall>> success, @NonNull Failure failure) {
        mGetAllDistressCalls.getCalls(success,failure);
    }

    @Override
    public void changeDroneMode(@NonNull String mode) {
        mChangeDroneMode.changeMode(mode);
    }

    @Override
    public void armDrone() {
        mArmDrone.arm();
    }

    @Override
    public void droneTakeoff(int height) {
        mDroneTakeoff.takeoff(height);
    }

    @Override
    public void uploadMission(@NonNull ArrayList<MissionPoint> wayPoints) {
        mUploadMission.executeUseCase(wayPoints);
    }

    @Override
    public void registerDroneEventsListener(DroneEventListener listener) {
        mRegisterDroneListener.register(listener);
    }

    @Override
    public void unregisterDroneEventsListener(DroneEventListener listener) {
        mUnregisterDroneListener.execute(listener);
    }

    @Override
    public void connectDrone(DroneConnectionType type) {
        mConnectDrone.execute(type);
    }

    @Override
    public void disconnectDrone() {
        mDisconnectDrone.execute();
    }

    @Override
    public void setDroneArming(boolean armDrone,DroneArmingFailure failure) {
        mSetDroneArming.setArming(armDrone,failure);
    }

    @Override
    public void addPrefsListener(OnPrefChangeListener listener) {
        mAddPrefsListener.execute(listener);
    }

    @Override
    public void removePrefsListener(OnPrefChangeListener listener) {
        mRemovePrefsListener.execute(listener);
    }
}
