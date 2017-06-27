package com.example.alon.airvanguard.presentation.edit_mission;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.databinding.ActivityEditMissionBinding;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.core.entity.Location;
import com.example.alon.airvanguard.domain.core.entity.User;
import com.example.alon.airvanguard.domain.core.entity.WayPoint;
import com.example.alon.airvanguard.infrastructure.app.AirVanguardApp;
import com.example.alon.airvanguard.infrastructure.di.component.DaggerEditMissionComponent;
import com.example.alon.airvanguard.infrastructure.di.module.EditMissionModule;
import com.example.alon.airvanguard.presentation.edit_mission.dialogs.CompleteEditMissionDialog;
import com.example.alon.airvanguard.presentation.edit_mission.dialogs.EditMissionDialogs;
import com.example.alon.airvanguard.presentation.edit_mission.dialogs.MissionPointEditorDialog;
import com.example.alon.airvanguard.presentation.entity.DistressCallDTO;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;
import com.example.alon.airvanguard.presentation.map.BaseMapActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

/**
 * This activity act as the view in the edit mission feature,
 * providing the user with drone mission editing UI.
 */
public class EditMissionActivity extends BaseMapActivity<EditMissionContract.Presenter>
        implements EditMissionContract.View, EditMissionDialogs {

    private static final String LOG_TAG = EditMissionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditMissionBinding mLayoutBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_edit_mission);

        // set toolbar
        setSupportActionBar(mLayoutBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // set map fragment and map callback when its ready
        setMap(R.id.map);

        // set fab click listener
        mLayoutBinding.fab.setOnClickListener(view -> {
            CompleteEditMissionDialog dialog = new CompleteEditMissionDialog();
            dialog.show(getSupportFragmentManager(),getString(R.string.edit_dialog_tag));
        });
    }

    @Override
    protected void injectPresenter() {
        // init the presenter for this activity
        DaggerEditMissionComponent.builder()
                .appComponent(AirVanguardApp.getAppComponent())
                .editMissionModule(new EditMissionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_edit_mission, menu);
        SubMenu mapTypes = menu.findItem(R.id.action_map_type).getSubMenu();

        // set current map type as checked in submenu
        switch (getPresenter().getMapType()) {
            case MAP_NORMAL:
                mapTypes.findItem(R.id.action_map_normal).setChecked(true);
                break;

            case MAP_SATELLITE:
                mapTypes.findItem(R.id.action_map_satellite).setChecked(true);
                break;

            case MAP_TERRAIN:
                mapTypes.findItem(R.id.action_map_terrain).setChecked(true);
                break;

            case MAP_HYBRID:
                mapTypes.findItem(R.id.action_map_hybrid).setChecked(true);
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mark_location:
                if (getMap() != null && getMap().getMyLocation() != null) {
                    mapClick(new LatLng(getMap().getMyLocation().getLatitude(),
                            getMap().getMyLocation().getLongitude()));
                }
                return true;

            case R.id.action_undo:
                undo();
                return true;

            case R.id.action_load_calls:
                getPresenter().loadAllDistressCalls();
                return true;

            case R.id.action_map_normal:
                if (setMapType(MAP_NORMAL)) {
                    item.setChecked(true);
                }
                return true;

            case R.id.action_map_satellite:
                if (setMapType(MAP_SATELLITE)) {
                    item.setChecked(true);
                }
                return true;

            case R.id.action_map_terrain:
                if (setMapType(MAP_TERRAIN)) {
                    item.setChecked(true);
                }
                return true;

            case R.id.action_map_hybrid:
                if (setMapType(MAP_HYBRID)) {
                    item.setChecked(true);
                }
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        getMap().setOnMapClickListener(this::mapClick);

        // put drone marker on the map if drone location
        // is available
        Location droneLocation = getPresenter().getDroneLocation();

        if (droneLocation != null) {
            setDroneMarker(droneLocation.getLat(),droneLocation.getLon());
        }

        if (getIntent().hasExtra(getString(R.string.key_distress_call_mission_edit))) {

            // activity was started with a distress call editing intent
            DistressCallDTO dto = (DistressCallDTO) getIntent()
                    .getSerializableExtra(getString(R.string.key_distress_call_mission_edit));
            DistressCall call = new DistressCall();

            call.setTimeStamp(dto.getTimeStamp());
            call.setLocation(new Location(dto.getLat(),dto.getLon()));
            call.setUser(new User(dto.getUserName(),dto.getUserPhotoUrl()));

            // add the call to the map
            showDistressCall(call);
            //MapUtil.addDistressCallMarker(mMap,call).showInfoWindow();

            // zoom on the distress call location at a city level
            getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dto.getLat()
                    ,dto.getLon()),getResources().getInteger(R.integer.zoom_city)));
        }

        getMap().setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            MissionPointEditorDialog dialog = MissionPointEditorDialog.newInstance(marker);
            dialog.show(getSupportFragmentManager(),getString(R.string.point_editor_dialog_tag));
            return true;
        });
    }

    @Override
    public void onCompleteEditMissionPositiveClick() {
        if (!getMissionPointMarkers().isEmpty()) {
            Intent intent = new Intent();
            ArrayList<MissionPoint> missionPoints = new ArrayList<>(getMissionPointMarkers().size());

            for (Marker marker : getMissionPointMarkers()) {
                missionPoints.add((MissionPoint) marker.getTag());
            }

            intent.putExtra(getString(R.string.key_mission_points),missionPoints);
            setResult(RESULT_OK,intent);

        } else {
            setResult(RESULT_CANCELED);
        }

        onBackPressed();
    }

    /**
     * Responds to map clicks by adding mission point marker
     * in the clicked location.
     *
     * @param latLng click geo location.
     */
    private void mapClick(LatLng latLng) {
        // set default mission point as marker tag
        MissionPoint defaultPoint = new WayPoint(getMissionPointMarkers().size() + 1,latLng.latitude,latLng.longitude,2);
        // add mission point marker
        addMissionPointMarker(latLng,defaultPoint);
    }

    /**
     * Undo the last editing operation. Will
     * delete the last added mission marker and its
     * polyline.
     */
    private void undo() {
        int lastIndex;
        if (!getMissionPointMarkers().isEmpty()) {
            lastIndex= getMissionPointMarkers().size() - 1;
            getMissionPointMarkers().get(lastIndex).remove();
            getMissionPointMarkers().remove(lastIndex);
        }

        if (!getPolylines().isEmpty()) {
            lastIndex = getPolylines().size() - 1;
            getPolylines().get(lastIndex).remove();
            getPolylines().remove(lastIndex);
        }
    }
}
