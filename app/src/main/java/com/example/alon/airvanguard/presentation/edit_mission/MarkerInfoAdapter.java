package com.example.alon.airvanguard.presentation.edit_mission;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.databinding.DistressCallMarkerInfoBinding;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * {@link GoogleMap.InfoWindowAdapter} implementation, for presenting
 * a custom info window of a map marker pointing to a distress call location.
 */

public class MarkerInfoAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater mInflater;

    public MarkerInfoAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        DistressCall distressCall = (DistressCall) marker.getTag();
        DistressCallMarkerInfoBinding binding = DataBindingUtil.inflate(mInflater,
                R.layout.distress_call_marker_info,null,false);
        binding.setCall(distressCall);
        binding.executePendingBindings();
        return binding.getRoot();
    }
}
