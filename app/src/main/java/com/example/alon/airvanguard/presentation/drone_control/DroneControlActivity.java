package com.example.alon.airvanguard.presentation.drone_control;

import android.app.NotificationManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower;
import com.example.alon.airvanguard.infrastructure.app.AirVanguardApp;
import com.example.alon.airvanguard.databinding.ActivityDroneControlBinding;
import com.example.alon.airvanguard.infrastructure.di.component.DaggerDroneControlComponent;
import com.example.alon.airvanguard.infrastructure.di.module.DroneControlModule;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.presentation.common.DateTimeUtil;
import com.example.alon.airvanguard.presentation.drone_control.dialogs.DisconnectDroneDialog;
import com.example.alon.airvanguard.presentation.drone_control.dialogs.DroneControlDialogs;
import com.example.alon.airvanguard.presentation.drone_control.dialogs.DroneTakeoffDialog;
import com.example.alon.airvanguard.presentation.drone_control.dialogs.EditDistressCallMissionDialog;
import com.example.alon.airvanguard.presentation.edit_mission.EditMissionActivity;
import com.example.alon.airvanguard.presentation.entity.DistressCallDTO;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;
import com.example.alon.airvanguard.presentation.map.BaseMapActivity;
import com.example.alon.airvanguard.presentation.settings.SettingsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Main screen for this app, act as the view in the drone control
 * feature. This activity provides the user with drone monitoring
 * and control UI.
 */
public class DroneControlActivity extends BaseMapActivity<DroneControlContract.Presenter>
        implements DroneControlContract.View, DroneControlDialogs {

    private static final String LOG_TAG = DroneControlActivity.class.getSimpleName();
    private static final int SETTINGS_REQUEST_CODE = 100;
    private static final int EDIT_MISSION_REQUEST_CODE = 200;

    private ActivityDroneControlBinding mLayoutBinding;
    private List<Marker> mDistressCallMarkers = new ArrayList<>();
    private boolean mFollowDroneMovement = false;
    private Drawer mDrawer;
    private ExpandableDrawerItem mDistressCallsDrawerItem;
    private boolean mDroneArmed = false;
    private boolean mDroneConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set content view for the activity binding layout
        mLayoutBinding = DataBindingUtil.setContentView(this, R.layout.activity_drone_control);

        // init toolbar
        setSupportActionBar(mLayoutBinding.toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // init map fragment and map callbacks.
        setMap(R.id.map);

        // init navigation drawer view.
        initDrawer();

        // ask the presenter to init telemetry panel
        getPresenter().initTelemetryView();

        // init drone modes spinner adapter and click listener
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.drone_modes, R.layout.spinner_mode_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLayoutBinding.droneTelemetry.modeSelector.setAdapter(adapter);
        mLayoutBinding.droneTelemetry.modeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mode = (String) ((ArrayAdapter<CharSequence>) parent.getAdapter()).getItem(position);

                if (mode != null) {
                    getPresenter().changeDroneMode(mode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected void injectPresenter() {
        // inject activity presenter
        DaggerDroneControlComponent.builder()
                .appComponent(AirVanguardApp.getAppComponent())
                .droneControlModule(new DroneControlModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_drone_control, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload_mission:
                uploadMission();
                return true;

            case R.id.action_clear_mission:
                clearMissionMarkers();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case EDIT_MISSION_REQUEST_CODE:
                    if (data.hasExtra(getString(R.string.key_mission_points))) {
                        drawMissionPoints((ArrayList<MissionPoint>) data
                                .getSerializableExtra(getString(R.string.key_mission_points)));
                    }
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancelAll();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        getMap().setOnInfoWindowClickListener(marker -> {
            DistressCall call = (DistressCall) marker.getTag();
            EditDistressCallMissionDialog dialog = EditDistressCallMissionDialog.newInstance(call);

            dialog.show(getSupportFragmentManager(), getString(R.string.edit_mission_dialog_tag));

        });
    }

    @Override
    public void showSettingsScreen() {
        startActivityForResult(new Intent(this, SettingsActivity.class), SETTINGS_REQUEST_CODE);
    }

    @Override
    public void showDistressCall(@NonNull DistressCall distressCall) {
        super.showDistressCall(distressCall);
        if (mDrawer != null && mDistressCallsDrawerItem != null) {
            ProfileDrawerItem distressCallItem = new CustomProfileDrawerItem()
                    .withName(distressCall.getUser().getName())
                    // replace email string with sent date time string
                    .withEmail(DateTimeUtil.convert(distressCall.getTimeStamp(),
                            getString(R.string.distress_call_sending_time_format)))
                    .withIcon(distressCall.getUser().getPhotoUrl())
                    .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                        mDrawer.closeDrawer();
                        new Thread(() -> {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(() -> {
                                if (getMap() != null) {
                                    LatLng latLng = new LatLng(distressCall.getLocation().getLat()
                                            ,distressCall.getLocation().getLon());
                                    getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                                }
                            });
                        }).start();
                        return true;
                    });

            mDistressCallsDrawerItem.getSubItems().add(distressCallItem);
            mDrawer.updateItem(mDistressCallsDrawerItem);
        }
    }

    @Override
    public void removeDistressCall(@NonNull DistressCall distressCall) {
        if (getMap() != null) {
            for (Marker marker : mDistressCallMarkers) {
                if (marker.getPosition().latitude == distressCall.getLocation().getLat() &&
                        marker.getPosition().longitude == distressCall.getLocation().getLon()) {
                    marker.remove();
                }
            }
        }
    }

    @Override
    public boolean isMapShowing() {
        return getMap() != null;
    }

    @Override
    public void notifyDroneConnected() {
        mDroneConnected = true;
        mLayoutBinding.droneControlPanel.connectivityIcon.setImageResource(R.drawable.ic_disconnect_drone_white_18dp);
        mLayoutBinding.droneControlPanel.connectivityState.setText(getString(R.string.disconnect_toggle));
        Toast.makeText(this, getString(R.string.toast_drone_connected), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notifyDroneDisconnected() {
        mDroneConnected = false;
        mLayoutBinding.droneControlPanel.connectivityIcon.setImageResource(R.drawable.ic_connect_drone_white_18dp);
        mLayoutBinding.droneControlPanel.connectivityState.setText(getString(R.string.connect_toggle));
        // remove drone marker
        removeDroneMarker();

        Toast.makeText(this, getString(R.string.toast_drone_disconnected), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDroneAirSpeed(String speed) {
        //mLayoutBinding.droneTelemetry.airSpeed.setText(String.format("%3.1f", speed) + "m/s");
        mLayoutBinding.droneTelemetry.airSpeed.setText(speed);
    }

    @Override
    public void updateDroneAltitude(String altitude) {
        mLayoutBinding.droneTelemetry.altitude.setText(altitude);
    }

    @Override
    public void updateDroneVerticalSpeed(String speed) {
        mLayoutBinding.droneTelemetry.verticalSpeed.setText(speed);
    }

    @Override
    public void updateDroneLocation(double lat, double lon) {
        if (getMap() != null) {

            // first drone location update
            if (getDroneMarker() == null) {
                setDroneMarker(lat, lon);

            } else {

                getDroneMarker().setPosition(new LatLng(lat, lon));
            }

            if (mFollowDroneMovement) {
                // move camera according to new location
                getMap().moveCamera(CameraUpdateFactory.newLatLng(getDroneMarker().getPosition()));
            }
        }
    }

    @Override
    public void updateDroneDistanceFromHome(String distance) {
        mLayoutBinding.droneTelemetry.homeDistance.setText(distance);
    }

    @Override
    public void updateDroneVehicleMode(String mode) {
        int position = ((ArrayAdapter) mLayoutBinding.droneTelemetry.modeSelector
                .getAdapter()).getPosition(mode);
        mLayoutBinding.droneTelemetry.modeSelector.setSelection(position);
    }

    @Override
    public void updateDroneArmingState(boolean isArmed) {
        if (isArmed) {
            mDroneArmed = true;
            mLayoutBinding.droneControlPanel.armStateIcon.setImageResource(R.drawable.ic_armed_lime_18dp);
            mLayoutBinding.droneControlPanel.armStateButton.setEnabled(false);
            mLayoutBinding.droneControlPanel.armedState.setText("Disarm");


        } else {
            mDroneArmed = false;
            mLayoutBinding.droneControlPanel.armedState.setText("Arm");
            mLayoutBinding.droneControlPanel.armStateIcon.setImageResource(R.drawable.ic_arm_white_18dp);
            mLayoutBinding.droneControlPanel.armStateButton.setEnabled(true);
        }
    }

    @Override
    public void showEditMissionScreen() {
        startActivityForResult(new Intent(this, EditMissionActivity.class), EDIT_MISSION_REQUEST_CODE);
    }

    @Override
    public void showDroneBatteryFull() {
        mLayoutBinding.droneTelemetry.batteryIcon.setImageResource(R.drawable.ic_battery_full_white_18dp);
        mLayoutBinding.droneTelemetry.battery.setText("100%");
    }

    @Override
    public void showDroneBattery90Range(int battery) {
        mLayoutBinding.droneTelemetry.batteryIcon.setImageResource(R.drawable.ic_battery_90_white_18dp);
        mLayoutBinding.droneTelemetry.battery.setText(battery + "%");
    }

    @Override
    public void showDroneBattery80Range(int battery) {
        mLayoutBinding.droneTelemetry.batteryIcon.setImageResource(R.drawable.ic_battery_80_white_18dp);
        mLayoutBinding.droneTelemetry.battery.setText(battery + "%");
    }

    @Override
    public void showDroneBattery60Range(int battery) {
        mLayoutBinding.droneTelemetry.batteryIcon.setImageResource(R.drawable.ic_battery_60_white_18dp);
        mLayoutBinding.droneTelemetry.battery.setText(battery + "%");
    }

    @Override
    public void showDroneBattery50Range(int battery) {
        mLayoutBinding.droneTelemetry.batteryIcon.setImageResource(R.drawable.ic_battery_50_white_18dp);
        mLayoutBinding.droneTelemetry.battery.setText(battery + "%");
    }

    @Override
    public void showDroneBattery30Range(int battery) {
        mLayoutBinding.droneTelemetry.batteryIcon.setImageResource(R.drawable.ic_battery_30_white_18dp);
        mLayoutBinding.droneTelemetry.battery.setText(battery + "%");
    }

    @Override
    public void showDroneBattery20Range(int battery) {
        mLayoutBinding.droneTelemetry.batteryIcon.setImageResource(R.drawable.ic_battery_20_white_18dp);
        mLayoutBinding.droneTelemetry.battery.setText(battery + "%");
    }

    @Override
    public void showDroneBatteryAlertRange(int battery) {
        mLayoutBinding.droneTelemetry.batteryIcon.setImageResource(R.drawable.ic_battery_alert_white_18dp);
        mLayoutBinding.droneTelemetry.battery.setText(battery + "%");
    }

    @Override
    public void showVerticalSpeedUp() {
        mLayoutBinding.droneTelemetry.verticalSpeedIcon.setImageResource(R.drawable.ic_arrow_upward_white_18dp);
    }

    @Override
    public void showVerticalSpeedDown() {
        mLayoutBinding.droneTelemetry.verticalSpeedIcon.setImageResource(R.drawable.ic_arrow_downward_white_18dp);
    }

    @Override
    public void showNoVerticalSpeed() {
        mLayoutBinding.droneTelemetry.verticalSpeedIcon.setImageResource(R.drawable.ic_vertical_speed_white_18dp);
    }

    @Override
    public void notifyDroneConnectionFailed(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void notifyDroneArmingFailed(String reason) {
        Toast.makeText(this, reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void initTelemetryMetric() {
        mLayoutBinding.droneTelemetry.airSpeed.setText(getString(R.string.init_telemetry_metric_air_speed));
        mLayoutBinding.droneTelemetry.verticalSpeed.setText(getString(R.string.init_telemetry_metric_vertical_speed));
        mLayoutBinding.droneTelemetry.altitude.setText(getString(R.string.init_telemetry_metric_altitude));
        mLayoutBinding.droneTelemetry.homeDistance.setText(getString(R.string.init_telemetry_metric_home));
        mLayoutBinding.droneTelemetry.battery.setText(getString(R.string.init_telemetry_battery));
    }

    @Override
    public void initTelemetryImperial() {
        mLayoutBinding.droneTelemetry.airSpeed.setText(getString(R.string.init_telemetry_imperial_air_speed));
        mLayoutBinding.droneTelemetry.verticalSpeed.setText(getString(R.string.init_telemetry_imperial_vertical_speed));
        mLayoutBinding.droneTelemetry.altitude.setText(getString(R.string.init_telemetry_imperial_altitude));
        mLayoutBinding.droneTelemetry.homeDistance.setText(getString(R.string.init_telemetry_imperial_home));
        mLayoutBinding.droneTelemetry.battery.setText(getString(R.string.init_telemetry_battery));
    }

    @Override
    public void initDroneControlPanel() {
        updateDroneArmingState(false);
    }

    @Override
    public void onDisconnectConfirmed() {
        getPresenter().disconnectDrone();
    }

    @Override
    public void onEditDistressCallMission(DistressCall call) {
        Intent intent = new Intent(this,EditMissionActivity.class);
        DistressCallDTO dto = new DistressCallDTO();

        dto.setLat(call.getLocation().getLat());
        dto.setLon(call.getLocation().getLon());
        dto.setTimeStamp(call.getTimeStamp());
        dto.setUserName(call.getUser().getName());
        dto.setUserPhotoUrl(call.getUser().getPhotoUrl());

        intent.putExtra(getString(R.string.key_distress_call_mission_edit),dto);
        startActivityForResult(intent,EDIT_MISSION_REQUEST_CODE);
    }

    @Override
    public void onDroneConnectionTypeSelected(DroneControlTower.DroneConnectionType type) {
        switch (type) {
            case UDP:
                getPresenter().connectDrone(DroneControlTower.DroneConnectionType.UDP);
                break;

            case USB:
                getPresenter().connectDrone(DroneControlTower.DroneConnectionType.USB);
                break;

            default:
                break;
        }
    }

    @Override
    public void onDroneTakeoffHeightSelected(int height) {
        getPresenter().droneTakeoff(height);
    }

    /**
     * Click listener that is invoked whenever
     * the arm drone button is clicked
     *
     * @param view button view,
     */
    public void onArmClick(View view) {
        //getPresenter().armDrone();
        if (!mDroneArmed) {
            getPresenter().setDroneArming(true);

        } else {
            getPresenter().setDroneArming(false);
        }
    }

    /**
     * Click listener that is invoked whenever
     * the takeoff button is clicked.
     *
     * @param view button view.
     */
    public void onTakeoffClick(View view) {
        DroneTakeoffDialog dialog = new DroneTakeoffDialog();
        dialog.show(getSupportFragmentManager(), "drone_takeoff_dialog");
    }

    /**
     * CLick listener that is invoked whenever
     * the follow button is clicked.
     *
     * @param view button view.
     */
    public void onFollowClick(View view) {
        mFollowDroneMovement = !mFollowDroneMovement;

        if (mFollowDroneMovement) {

            mLayoutBinding.droneControlPanel.followIcon.setImageResource(R.drawable.ic_follow_drone_active_white_18dp);

        } else {
            mLayoutBinding.droneControlPanel.followIcon.setImageResource(R.drawable.ic_follow_drone_white_18dp);
        }
    }

    /**
     * CLick listener that is invoked whenever
     * the connection button is clicked.
     *
     * @param view button view.
     */
    public void onConnectToggleClick(View view) {
        if (!mDroneConnected) {
            ConnectDroneDialog dialog = new ConnectDroneDialog();
            dialog.show(getSupportFragmentManager(), "connect_drone_dialog");
        } else {
            DisconnectDroneDialog dialog = new DisconnectDroneDialog();
            dialog.show(getSupportFragmentManager(), "disconnect_drone_dialog");
        }
    }

    /**
     * Build drawer items and sets listeners that
     * handle drawer items clicks.
     */
    private void initDrawer() {
        PrimaryDrawerItem editMissionDrawerItem = new PrimaryDrawerItem()
                .withName(getString(R.string.drawer_item_edit))
                .withIcon(R.drawable.ic_mode_edit_black_18dp)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    drawerItemClick(() -> getPresenter().openEditMissionScreen());
                    return true;
                });

        mDistressCallsDrawerItem = new ExpandableDrawerItem()
                .withName(getString(R.string.drawer_item_distress_calls))
                .withSubItems(new ArrayList<>(0))
                .withIcon(R.drawable.ic_place_black_18dp);

        PrimaryDrawerItem normalMapDrawerItem = new PrimaryDrawerItem()
                .withName(getString(R.string.drawer_item_map_normal))
                .withIcon(R.drawable.ic_map_black_18dp)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    drawerItemClick(() -> setMapType(MAP_NORMAL));
                    return true;
                });

        PrimaryDrawerItem terrainMapDrawerItem = new PrimaryDrawerItem()
                .withName(getString(R.string.drawer_item_map_terrain))
                .withIcon(R.drawable.ic_terrain_black_18dp)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    drawerItemClick(() -> setMapType(MAP_TERRAIN));
                    return true;
                });

        PrimaryDrawerItem satelliteMapDrawerItem = new PrimaryDrawerItem()
                .withName(getString(R.string.drawer_item_map_satellite))
                .withIcon(R.drawable.ic_satellite_black_18dp)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    drawerItemClick(() -> setMapType(MAP_SATELLITE));
                    return true;
                });

        PrimaryDrawerItem hybridMapDrawerItem = new PrimaryDrawerItem()
                .withName(getString(R.string.drawer_item_map_hybrid))
                .withIcon(R.drawable.ic_map_hybrid_white_18dp)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    drawerItemClick(() -> setMapType(MAP_HYBRID));
                    return true;
                });

        PrimaryDrawerItem settingsDrawerItem = new PrimaryDrawerItem()
                .withName(getString(R.string.drawer_item_settings))
                .withIcon(R.drawable.ic_settings_black_18dp)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    drawerItemClick(() -> getPresenter().openSettingsScreen());
                    return true;
                });

        PrimaryDrawerItem infoDrawerItem = new PrimaryDrawerItem()
                .withName(getString(R.string.drawer_item_about))
                .withIcon(R.drawable.ic_info_black_18dp);

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.nav_header)
                .withToolbar(mLayoutBinding.toolbar)
                .addDrawerItems(editMissionDrawerItem, mDistressCallsDrawerItem,
                        new SectionDrawerItem().withName(getString(R.string.drawer_item_section_maps)),
                        normalMapDrawerItem,terrainMapDrawerItem, satelliteMapDrawerItem,hybridMapDrawerItem)
                .addStickyDrawerItems(settingsDrawerItem, infoDrawerItem)
                .build();
    }

    /**
     * Draws the given {@code missionPoints} to the map
     * using markers and polylines.
     *
     * @param missionPoints a list of {@link MissionPoint}s.
     */
    private void drawMissionPoints(@NonNull ArrayList<MissionPoint> missionPoints) {
        for (MissionPoint point : missionPoints) {
            // add a new mission point marker upon map click
            addMissionPointMarker(new LatLng(point.getLat(), point.getLon()),point);
        }
    }

    /**
     * Uploads the current mission to the presenter
     */
    private void uploadMission() {
        if (!getMissionPointMarkers().isEmpty()) {
            ArrayList<MissionPoint> points = new ArrayList<>(getMissionPointMarkers().size());

            for (Marker marker : getMissionPointMarkers()) {
                points.add((MissionPoint) marker.getTag());
            }

            getPresenter().uploadMission(points);
        }
    }

    private void drawerItemClick(Runnable onClose) {
        mDrawer.closeDrawer();
        new Thread(() -> {
            try {
                Thread.sleep(getResources().getInteger(R.integer.drawer_close_anim_interval));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(onClose);

        }).start();
    }
}
