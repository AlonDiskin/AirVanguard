package com.example.alon.airvanguard.presentation.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;
import com.example.alon.airvanguard.presentation.common.BaseActivity;
import com.example.alon.airvanguard.presentation.common.DateTimeUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for app activities that make use
 * of a {@link GoogleMap}.
 */

public abstract class BaseMapActivity<T extends BaseMapContract.Presenter> extends BaseActivity<T>
        implements BaseMapContract.View, OnMapReadyCallback {

    protected static final String LOG_TAG = BaseMapActivity.class.getSimpleName();
    protected static final int MAP_NORMAL = 1;
    protected static final int MAP_SATELLITE = 2;
    protected static final int MAP_TERRAIN = 3;
    protected static final int MAP_HYBRID = 4;

    private GoogleMap mMap;
    private Marker mDroneMarker;
    private List<Marker> mMissionPointMarkers = new ArrayList<>();
    private List<Polyline> mPolylines = new ArrayList<>();

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(getPresenter().getMapType());
        // will apply only if map type is 'normal'.
//        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
//                this, R.raw.style_json));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void showDistressCall(DistressCall call) {
        if (mMap != null) {
            LatLng callPosition = new LatLng(call.getLocation().getLat(),
                    call.getLocation().getLon());

            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(callPosition)
                    .title(call.getUser().getName())
                    .snippet(DateTimeUtil.convert(call.getTimeStamp(),
                            getString(R.string.distress_call_sending_time_format))));

            marker.setTag(call);
            marker.showInfoWindow();
        }
    }

    @Override
    public void showDistressCalls(List<DistressCall> calls) {
        for (DistressCall call : calls) {
            showDistressCall(call);
        }
    }

    /**
     * Sets up the map fragment identified by
     * the given {@code mapId},loads the map view
     * and register its onReady listener.
     *
     * @param mapId map fragment layout id.
     */
    protected void setMap(int mapId) {
        // set map fragment and map callback when its ready
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(mapId);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean setMapType(int mapType) {
        if (getMap() != null) {
            getMap().setMapType(mapType);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isMapShowing() {
        return mMap != null;
    }

    protected GoogleMap getMap() {
        return mMap;
    }

    /**
     * Removes the drone marker from map, and
     * nullifies the marker instance.
     */
    protected void removeDroneMarker() {
        if (mDroneMarker != null) {
            mDroneMarker.remove();
            mDroneMarker = null;
        }
    }

    /**
     * @return the drone marker of the map, may
     * be null.
     */
    @Nullable
    protected Marker getDroneMarker() {
        return mDroneMarker;
    }

    protected List<Polyline> getPolylines() {
        return mPolylines;
    }


    /**
     * Sets a drone marker in the given {@code droneLocation} on
     * the map.
     *
     * @param lat geo lat value
     * @param lon geo lon value.
     */
    protected void setDroneMarker(double lat,double lon) {
        LatLng position = new LatLng(lat,lon);

        // place drone marker on the map
        mDroneMarker = mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(getString(R.string.drone_marker_title))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.drone)));

        // zoom on drone location at city block level
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,getResources().getInteger(R.integer.zoom_city)));
    }

    /**
     * Adds a mission point marker on the map to
     * the given {@code latLng} location.
     *
     * @param latLng mission point map location
     */
    protected Marker addMissionPointMarker(LatLng latLng, MissionPoint point) {
        Marker missionPointMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmap(
                        this,
                        R.drawable.ic_mission_point_32dp,
                        mMissionPointMarkers.size() + 1))));

        missionPointMarker.setTag(point);
        mMissionPointMarkers.add(missionPointMarker);

        // connect poly line with last point marked on the map
        if (mMissionPointMarkers.size() >= 2) {
            int pointsCount = mMissionPointMarkers.size();
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(mMissionPointMarkers.get(pointsCount - 1).getPosition(), mMissionPointMarkers
                            .get(pointsCount - 2).getPosition())
                    .width(getResources().getDimension(R.dimen.mission_path_polyline_width))
                    .color(getResources().getColor(R.color.colorMissionPolyline))
                    .visible(true)
                    .geodesic(false));

            mPolylines.add(line);
        }

        // zoom the camera on the new marker at a street level zoom
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,getResources().getInteger(R.integer.zoom_street)));
        return missionPointMarker;
    }

    /**
     * Clears the map of mission markers and polylines.
     */
    protected void clearMissionMarkers() {
        for (Marker marker : getMissionPointMarkers()) {
            marker.remove();
        }

        getMissionPointMarkers().clear();

        for (Polyline polyline : getPolylines()) {
            polyline.remove();
        }

        getPolylines().clear();

    }

    protected List<Marker> getMissionPointMarkers() {
        return mMissionPointMarkers;
    }

    public Bitmap getMarkerBitmap(Context context, @DrawableRes int drawableId, int number) {
        Bitmap icon = drawTextToBitmap(context,drawableId, String.valueOf(number));

        return icon;
    }

    private Bitmap drawTextToBitmap(Context context,int gResId, String gText) {
        Resources resources = context.getResources();
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = getBitmapFromDrawable(context,gResId);
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        if ( bitmapConfig == null ) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize((int) (15 * scale));
        paint.setShadowLayer(1f, 0f, 1f, Color.BLACK);

        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width())/2;
        int y = (bitmap.getHeight() + bounds.height())/2;
        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }

    private Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}