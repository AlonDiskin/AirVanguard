package com.example.alon.airvanguard.presentation.drone_control;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.application_services.drone_control.facade.DroneControlDomain;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.core.value.AirSpeed;
import com.example.alon.airvanguard.domain.core.value.DroneAltitude;
import com.example.alon.airvanguard.domain.core.value.HomeDistance;
import com.example.alon.airvanguard.domain.core.value.VerticalSpeed;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;
import com.example.alon.airvanguard.presentation.common.BasePresenter;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * {@link DroneControlContract.Presenter} implementation class. Fulfilling the
 * role of a presenter for {@link DroneControlContract.View} in the drone control feature.
 */

public class DroneControlPresenter extends BasePresenter<DroneControlContract.View, DroneControlDomain>
        implements DroneControlContract.Presenter, DistressCallRepository.DistressCallListener,
        DroneControlTower.DroneEventListener,DroneControlTower.DroneTowerListener,UserPrefsRepository.OnPrefChangeListener {

    private static final String LOG_TAG = DroneControlPresenter.class.getSimpleName();

    @Inject
    public DroneControlPresenter(@NonNull DroneControlContract.View view, @NonNull DroneControlDomain domain) {
        super(view, domain);
        getDomain().connectDroneControlTower(this);
        getDomain().registerDroneEventsListener(this);
    }

    @Override
    public void onViewVisible() {
        super.onViewVisible();
        getDomain().addDistressCallListener(this);
    }

    @Override
    public void onViewDestroy() {
        getDomain().removeDistressCallListener(this);
        super.onViewDestroy();
    }

    @Override
    public void onRemovePresenter() {
        super.onRemovePresenter();
        getDomain().disconnectDrone();
    }

    @Override
    public void openSettingsScreen() {
        getDomain().addPrefsListener(this);
        getView().showSettingsScreen();
    }

    @Override
    public void openEditMissionScreen() {
        getView().showEditMissionScreen();
    }

    @Override
    public void loadAllDistressCalls() {
        getDomain().getAllDistressCalls(result -> {
            if (isViewExist()) {
                getView().showDistressCalls(result);
            }
        }, Throwable::printStackTrace);
    }

    @Override
    public void connectDrone(DroneControlTower.DroneConnectionType type) {
        getDomain().connectDrone(type);
    }

    @Override
    public void disconnectDrone() {
        getDomain().disconnectDrone();
    }

    @Override
    public int getMapType() {
        return getDomain().getMapType();
    }

    @Override
    public void changeDroneMode(@NonNull String mode) {
        getDomain().changeDroneMode(mode);
    }

    @Override
    public void droneTakeoff(int height) {
        getDomain().droneTakeoff(height);
    }

    @Override
    public void armDrone() {
        getDomain().armDrone();
    }

    @Override
    public void uploadMission(@NonNull ArrayList<MissionPoint> wayPoints) {
        getDomain().uploadMission(wayPoints);
    }

    @Override
    public void setDroneArming(boolean armDrone) {
        getDomain().setDroneArming(armDrone,errorMessage -> {
            if (isViewVisible()) {
                getView().notifyDroneArmingFailed(errorMessage);
            }
        });
    }

    @Override
    public void onSettingsScreenClosed() {
        getDomain().removePrefsListener(this);
    }

    @Override
    public void initTelemetryView() {
        switch (getDomain().getDataUnitType()) {
            case METRIC:
                getView().initTelemetryMetric();
                break;

            case IMPERIAL:
                getView().initTelemetryImperial();
                break;

            default:
                break;
        }
    }

    @Override
    public void onCallAdded(@NonNull DistressCall call) {
        if (isViewExist()) {
            getView().showDistressCall(call);
        }
    }

    @Override
    public void onCallRemoved(@NonNull DistressCall call) {
        if (isViewExist()) {
            getView().removeDistressCall(call);
        }
    }

    @Override
    public void onDroneConnected() {
        if (isViewVisible()) {
            getView().notifyDroneConnected();
        }
    }

    @Override
    public void onDroneConnectionFailed(String reason) {
        if (isViewExist()) {
            getView().notifyDroneConnectionFailed(reason);
        }
    }

    @Override
    public void onDroneDisconnected() {
        if (isViewExist()) {
            getView().notifyDroneDisconnected();
            // clear telemetry view
            initTelemetryView();


        }
    }

    @Override
    public void onDroneAltitudeChange(DroneAltitude altitude) {
        if (isViewVisible()) {
            getView().updateDroneAltitude(altitude.toString());
        }
    }

    @Override
    public void onDroneVerticalSpeedChange(VerticalSpeed speed) {
        if (isViewVisible()) {
            if (speed.getSpeed() == 0) {
                getView().showNoVerticalSpeed();

            } else if (speed.getSpeed() > 0) {
                getView().showVerticalSpeedUp();

            } else {
                speed.setSpeed(speed.getSpeed() * (-1));
                getView().showVerticalSpeedDown();
            }

            getView().updateDroneVerticalSpeed(speed.toString());
        }
    }

    @Override
    public void onDroneAirSpeedChange(AirSpeed speed) {
        if (isViewVisible()) {
            getView().updateDroneAirSpeed(speed.toString());
        }
    }

    @Override
    public void onDroneLocationChange(double lat, double lon) {
        if (isViewVisible()) {
            getView().updateDroneLocation(lat,lon);
        }
    }

    @Override
    public void onDroneBatteryChange(double battery) {
        if (isViewVisible()) {
            int batteryValue = (int) battery;

            if (batteryValue == 100) {
                getView().showDroneBatteryFull();

            } else if (batteryValue >= 90) {
                getView().showDroneBattery90Range(batteryValue);

            } else if (batteryValue < 90 && batteryValue >= 80) {
                getView().showDroneBattery80Range(batteryValue);

            } else if (batteryValue < 80 && batteryValue >= 60) {
                getView().showDroneBattery60Range(batteryValue);

            } else if (batteryValue < 60 && batteryValue >= 50) {
                getView().showDroneBattery50Range(batteryValue);

            } else if (batteryValue < 50 && batteryValue >= 30) {
                getView().showDroneBattery30Range(batteryValue);

            } else if (batteryValue < 30 && batteryValue >= 20) {
                getView().showDroneBattery20Range(batteryValue);

            } else {
                getView().showDroneBatteryAlertRange(batteryValue);
            }
        }
    }

    @Override
    public void onDroneVehicleModeChange(String mode) {
        if (isViewVisible()) {
            getView().updateDroneVehicleMode(mode);
        }
    }

    @Override
    public void onDroneArmingStateChange(boolean isArmed) {
        if (isViewExist()) {
            getView().updateDroneArmingState(isArmed);
        }
    }

    @Override
    public void onDroneDistanceFromHomeChange(HomeDistance distance) {
        if (isViewVisible()) {
            getView().updateDroneDistanceFromHome(distance.toString());
        }
    }

    @Override
    public void onConnectedToDroneTower() {

    }

    @Override
    public void onDisconnectedFromTower() {

    }

    @Override
    public void onMapTypeChange(int mapType) {
        if (isViewExist() && getView().isMapShowing()) {
            getView().setMapType(mapType);
        }
    }

    @Override
    public void onUnitTypeChange(int unitType) {

    }

}
