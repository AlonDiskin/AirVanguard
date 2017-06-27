package com.example.alon.airvanguard.domain.core.drone_control_tower;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.alon.airvanguard.domain.core.entity.CirclePoint;
import com.example.alon.airvanguard.domain.core.entity.LandPoint;
import com.example.alon.airvanguard.domain.core.entity.Location;
import com.example.alon.airvanguard.domain.core.entity.DroneState;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;
import com.example.alon.airvanguard.domain.core.entity.RtlPoint;
import com.example.alon.airvanguard.domain.core.entity.WayPoint;
import com.example.alon.airvanguard.domain.core.value.AirSpeed;
import com.example.alon.airvanguard.domain.core.value.DroneAltitude;
import com.example.alon.airvanguard.domain.core.value.HomeDistance;
import com.example.alon.airvanguard.domain.core.value.VerticalSpeed;
import com.o3dr.android.client.ControlTower;
import com.o3dr.android.client.Drone;
import com.o3dr.android.client.interfaces.DroneListener;
import com.o3dr.android.client.interfaces.TowerListener;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.attribute.AttributeEvent;
import com.o3dr.services.android.lib.drone.attribute.AttributeType;
import com.o3dr.services.android.lib.drone.connection.ConnectionParameter;
import com.o3dr.services.android.lib.drone.connection.ConnectionResult;
import com.o3dr.services.android.lib.drone.connection.ConnectionType;
import com.o3dr.services.android.lib.drone.mission.Mission;
import com.o3dr.services.android.lib.drone.mission.item.command.ReturnToLaunch;
import com.o3dr.services.android.lib.drone.mission.item.spatial.Circle;
import com.o3dr.services.android.lib.drone.mission.item.spatial.Land;
import com.o3dr.services.android.lib.drone.mission.item.spatial.Waypoint;
import com.o3dr.services.android.lib.drone.property.Altitude;
import com.o3dr.services.android.lib.drone.property.Battery;
import com.o3dr.services.android.lib.drone.property.CameraProxy;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.drone.property.Home;
import com.o3dr.services.android.lib.drone.property.Speed;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.services.android.lib.drone.property.Type;
import com.o3dr.services.android.lib.drone.property.VehicleMode;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * {@link DroneControlTower} implementation class.
 */

public class DroneControlTowerImpl implements DroneControlTower {

    private final String LOG_TAG = DroneControlTowerImpl.class.getSimpleName();
    private DataUnitType mUnitType;
    private ControlTower mControlTower;
    private Drone mDrone = new Drone();
    private int droneType = Type.TYPE_UNKNOWN;
    private final Handler mHandler = new Handler();
    private DroneListener mDroneListener;

    @Inject
    public DroneControlTowerImpl(@NonNull ControlTower controlTower) {
        mControlTower = controlTower;
    }

    @Override
    public void disconnectTower() {
        mControlTower.disconnect();
    }

    @Override
    public void connectDrone(DroneConnectionType type) {
        // register drone to control tower
        mControlTower.registerDrone(mDrone, mHandler);

        // establish a connection between drone and control tower
        switch (type) {
            case UDP:
                connectUdp();
                break;

            case USB:
                connectUsb();
                break;

            default:
                break;
        }

        // test
        CameraProxy proxy  = mDrone.getAttribute(AttributeType.CAMERA);
        Log.d(LOG_TAG,"Camera name: " + proxy.getCameraDetail().getName());
        mDrone.triggerCamera();
    }

    @Override
    public void registerDroneEventsListener(DroneEventListener listener) {
        registerDroneListener(listener);
    }

    @Override
    public void unregisterDroneEventsListener(DroneEventListener listener) {
        mDrone.unregisterDroneListener(mDroneListener);
    }

    @Override
    public void getCurrentState(DroneState state) {

    }

    @Override
    public void setUnitSystemType(DataUnitType type) {
        mUnitType = type;
    }

    @Override
    public void connectTower(@NonNull DroneTowerListener listener) {
           mControlTower.connect(new TowerListener() {
               @Override
               public void onTowerConnected() {
                   listener.onConnectedToDroneTower();
               }

               @Override
               public void onTowerDisconnected() {
                    listener.onDisconnectedFromTower();
               }
           });
    }

    @Override
    public void disconnectDrone() {
        Log.d(LOG_TAG,"disconnecting drone");
        mDrone.disconnect();
        //mControlTower.unregisterDrone(mDrone);

    }

    @Override
    public void setDroneMode(@NonNull String mode) {
        if (mDrone != null && mDrone.isConnected()) {
            for(VehicleMode vehicleMode : VehicleMode.getVehicleModePerDroneType(Type.TYPE_COPTER)) {
                if (vehicleMode.getLabel().equals(mode)) {
                    mDrone.changeVehicleMode(vehicleMode);
                }
            }
        }
    }

    @Override
    public void armDrone() {
        if (mDrone != null && mDrone.isConnected()) {
            State state = mDrone.getAttribute(AttributeType.STATE);
            VehicleMode vehicleMode = state.getVehicleMode();

            if ((vehicleMode.getMode() == VehicleMode.COPTER_GUIDED.getMode()) && !state.isArmed()) {
                mDrone.arm(true);
            }
        }


    }

    @Override
    public void setDroneArming(boolean armDrone,DroneArmingFailure failure) {
        if (mDrone != null && mDrone.isConnected()) {
            mDrone.arm(armDrone);
        } else {
            failure.onArmingFailed("Drone is not connected!");
        }
    }

    @Override
    public void takeoff(int height) {
        if (mDrone != null && mDrone.isConnected() && ((State)mDrone.getAttribute(AttributeType.STATE)).isArmed() &&
                !((State)mDrone.getAttribute(AttributeType.STATE)).isFlying()) {
            mDrone.doGuidedTakeoff(height);
        }
    }

    @Nullable
    @Override
    public Location getDroneLocation() {
        Location droneLocation = null;
        if (mDrone != null && mDrone.isConnected()) {
            Gps gps = mDrone.getAttribute(AttributeType.GPS);
            droneLocation = new Location(gps.getPosition().getLatitude(),
                    gps.getPosition().getLongitude());
        }

        return droneLocation;
    }

    @Override
    public void uploadMission(@NonNull ArrayList<MissionPoint> points) {
        if (mDrone != null && mDrone.isConnected()) {
            State vehicleState = mDrone.getAttribute(AttributeType.STATE);

            if (vehicleState.isFlying()) {
                Mission mission = new Mission();

                for (MissionPoint mp : points) {
                    if (mp instanceof WayPoint) {
                        Waypoint waypoint = new Waypoint();

                        waypoint.setCoordinate(new LatLongAlt(mp.getLat(),mp.getLon(),mp.getAltitude()));
                        mission.addMissionItem(waypoint);

                    } else if (mp instanceof CirclePoint) {
                        Circle circle = new Circle();

                        circle.setCoordinate(new LatLongAlt(mp.getLat(),mp.getLon(),mp.getAltitude()));
                        mission.addMissionItem(circle);

                    } else if (mp instanceof LandPoint) {
                        Land land = new Land();

                        land.setCoordinate(new LatLongAlt(mp.getLat(),mp.getLon(),mp.getAltitude()));
                        mission.addMissionItem(land);

                    } else if (mp instanceof RtlPoint) {
                        Waypoint waypoint = new Waypoint();
                        ReturnToLaunch rtl = new ReturnToLaunch();

                        waypoint.setCoordinate(new LatLongAlt(mp.getLat(),mp.getLon(),mp.getAltitude()));
                        rtl.setReturnAltitude(mp.getAltitude());
                        mission.addMissionItem(waypoint);
                        mission.addMissionItem(rtl);
                    }
                }

                mDrone.setMission(mission,true);
            }
        }
    }

    private void connectUdp() {
        Bundle extraParams = new Bundle();
        extraParams.putInt(ConnectionType.EXTRA_UDP_SERVER_PORT, 14550); // Set default port to 14550

        ConnectionParameter connectionParams = new ConnectionParameter(ConnectionType.TYPE_UDP, extraParams, null);
        mDrone.connect(connectionParams);
    }

    private void connectUsb() {
        Bundle extraParams = new Bundle();
        extraParams.putInt(ConnectionType.EXTRA_USB_BAUD_RATE, 57600); // Set default baud rate to 57600
        ConnectionParameter connectionParams = new ConnectionParameter(ConnectionType.TYPE_USB, extraParams, null);
        mDrone.connect(connectionParams);
    }

    private void registerDroneListener(DroneEventListener listener) {
        mDroneListener = new DroneListener() {
            @Override
            public void onDroneConnectionFailed(ConnectionResult result) {
                Log.d(LOG_TAG,"Drone Connection Failed!");
                listener.onDroneConnectionFailed(result.getErrorMessage());
            }

            @Override
            public void onDroneEvent(String event, Bundle extras) {
                switch (event) {

                    case AttributeEvent.SIGNAL_WEAK:
                        break;

                    case AttributeEvent.WARNING_NO_GPS:
                        break;

                    case AttributeEvent.STATE_CONNECTED:
                        listener.onDroneConnected();
                        break;

                    case AttributeEvent.STATE_DISCONNECTED:
                        Log.d(LOG_TAG,"drone disconnected!");
                        listener.onDroneDisconnected();
                        break;

                    case AttributeEvent.SPEED_UPDATED:
                        Speed droneSpeed = mDrone.getAttribute(AttributeType.SPEED);

                        switch (mUnitType) {
                            case METRIC:
                                listener.onDroneAirSpeedChange(new AirSpeed(DataUnitsConverter
                                        .convertMsToKmh(droneSpeed.getAirSpeed()),mUnitType));
                                listener.onDroneVerticalSpeedChange(new VerticalSpeed(droneSpeed
                                        .getVerticalSpeed(),mUnitType));
                                break;

                            case IMPERIAL:
                                listener.onDroneAirSpeedChange(new AirSpeed(DataUnitsConverter
                                        .convertMsToMph(droneSpeed.getAirSpeed()),mUnitType));
                                listener.onDroneVerticalSpeedChange(new VerticalSpeed(DataUnitsConverter
                                        .convertMsToFps(droneSpeed.getVerticalSpeed()),mUnitType));
                                break;

                            default:
                                break;
                        }

                        break;

                    case AttributeEvent.ALTITUDE_UPDATED:
                        Altitude droneAltitude = mDrone.getAttribute(AttributeType.ALTITUDE);
                        switch (mUnitType) {
                            case METRIC:
                                listener.onDroneAltitudeChange(new DroneAltitude(droneAltitude.getAltitude()
                                        ,mUnitType));
                                break;

                            case IMPERIAL:
                                listener.onDroneAltitudeChange(new DroneAltitude(DataUnitsConverter
                                        .convertMetersToFeet(droneAltitude.getAltitude()),mUnitType));
                                break;

                            default:
                                break;
                        }
                        break;

                    case AttributeEvent.GPS_POSITION:
                        double vehicleAltitude = ((Altitude)mDrone.getAttribute(AttributeType.ALTITUDE))
                                .getAltitude();
                        Gps gps = mDrone.getAttribute(AttributeType.GPS);
                        LatLong vehiclePosition = gps.getPosition();
                        double distanceFromHome;

                        if (gps.isValid()) {
                            LatLongAlt vehicle3DPosition = new LatLongAlt(vehiclePosition.getLatitude()
                                    , vehiclePosition.getLongitude(), vehicleAltitude);
                            Home droneHome = mDrone.getAttribute(AttributeType.HOME);
                            distanceFromHome = distanceBetweenPoints(droneHome.getCoordinate(), vehicle3DPosition);
                        } else {
                            distanceFromHome = 0;
                        }

                        listener.onDroneLocationChange(gps.getPosition().getLatitude(),
                                gps.getPosition().getLongitude());

                        switch (mUnitType) {
                            case METRIC:
                                listener.onDroneDistanceFromHomeChange(new HomeDistance
                                        (distanceFromHome,mUnitType));
                                break;

                            case IMPERIAL:
                                listener.onDroneDistanceFromHomeChange(new HomeDistance(
                                        DataUnitsConverter.convertMetersToFeet(distanceFromHome),mUnitType));
                                break;

                            default:
                                break;
                        }

                        break;

                    case AttributeEvent.BATTERY_UPDATED:
                        Battery battery = mDrone.getAttribute(AttributeType.BATTERY);

                        if (battery.getBatteryRemain() < 0) {
                            battery.setBatteryRemain(0);
                        }

                        listener.onDroneBatteryChange(battery.getBatteryRemain());
                        break;

                    case AttributeEvent.STATE_VEHICLE_MODE:
                        State state = mDrone.getAttribute(AttributeType.STATE);
                        VehicleMode vehicleMode = state.getVehicleMode();
                        listener.onDroneVehicleModeChange(vehicleMode.getLabel());
                        break;

                    case AttributeEvent.STATE_ARMING:
                        State vehicleState = mDrone.getAttribute(AttributeType.STATE);
                        listener.onDroneArmingStateChange(vehicleState.isArmed());
                        break;

                    case AttributeEvent.GUIDED_POINT_UPDATED:
                        Log.d(LOG_TAG,"guided point updated");
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onDroneServiceInterrupted(String errorMsg) {
                Log.d(LOG_TAG,errorMsg);
            }
        };

        mDrone.registerDroneListener(mDroneListener);

    }

    protected double distanceBetweenPoints(LatLongAlt pointA, LatLongAlt pointB) {
        if (pointA == null || pointB == null) {
            return 0;
        }
        double dx = pointA.getLatitude() - pointB.getLatitude();
        double dy  = pointA.getLongitude() - pointB.getLongitude();
        double dz = pointA.getAltitude() - pointB.getAltitude();
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }
}
