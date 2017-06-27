package com.example.alon.airvanguard.presentation.edit_mission.dialogs;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.databinding.MissionPointEditorBinding;
import com.example.alon.airvanguard.domain.core.entity.CirclePoint;
import com.example.alon.airvanguard.domain.core.entity.LandPoint;
import com.example.alon.airvanguard.domain.core.entity.MissionPoint;
import com.example.alon.airvanguard.domain.core.entity.RtlPoint;
import com.example.alon.airvanguard.domain.core.entity.WayPoint;
import com.google.android.gms.maps.model.Marker;

/**
 * Drone Mission point editing dialog.
 */

public class MissionPointEditorDialog extends DialogFragment {

    private static Marker mMarker;

    public static MissionPointEditorDialog newInstance(Marker marker) {
        mMarker = marker;
        return new MissionPointEditorDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MissionPoint missionPoint = (MissionPoint) mMarker.getTag();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        MissionPointEditorBinding binding = DataBindingUtil.inflate(getActivity().getLayoutInflater()
                ,R.layout.mission_point_editor,null,false);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.point_types, R.layout.spinner_type_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.typeSelector.setAdapter(adapter);
        binding.typeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String type = (String) ((ArrayAdapter<CharSequence>)parent.getAdapter()).getItem(position);

                if (!type.equals("Circle")) {
                    binding.circleInput.setVisibility(View.GONE);

                } else {
                    binding.circleInput.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.altitudeTextField.setText(String.valueOf(missionPoint.getAltitude()));

        if (missionPoint instanceof WayPoint) {
            binding.typeSelector.setSelection(0);

        } else if (missionPoint instanceof CirclePoint) {
            binding.typeSelector.setSelection(1);
            binding.circleInput.setVisibility(View.VISIBLE);
            binding.circleTextField.setText(String.valueOf(((CirclePoint)missionPoint).getRadius()));

        }else if (missionPoint instanceof LandPoint){
            binding.typeSelector.setSelection(2);

        } else if (missionPoint instanceof RtlPoint) {
            binding.typeSelector.setSelection(3);
        }

        builder.setView(binding.getRoot())
                .setTitle(getString(R.string.dialog_title_edit_mission_point
                        ,missionPoint.getPointPathNum()))
                .setPositiveButton(getString(R.string.dialog_positive_button_title),
                        (dialog, id) -> {
                            switch (binding.typeSelector.getSelectedItemPosition()) {
                                case 0:
                                    WayPoint wayPoint = new WayPoint();
                                    wayPoint.setAltitude(Double.parseDouble(
                                            binding.altitudeTextField.getText().toString()));
                                    wayPoint.setLat(missionPoint.getLat());
                                    wayPoint.setLon(missionPoint.getLon());
                                    wayPoint.setPointPathNum(missionPoint.getPointPathNum());
                                    mMarker.setTag(wayPoint);
                                    break;

                                case 1:
                                    CirclePoint circlePoint = new CirclePoint();
                                    circlePoint.setAltitude(Double.parseDouble(
                                            binding.altitudeTextField.getText().toString()));
                                    circlePoint.setRadius(Double.parseDouble(
                                            binding.circleTextField.getText().toString()));
                                    circlePoint.setLat(missionPoint.getLat());
                                    circlePoint.setLon(missionPoint.getLon());
                                    circlePoint.setPointPathNum(missionPoint.getPointPathNum());
                                    mMarker.setTag(circlePoint);
                                    break;

                                case 2:
                                    LandPoint landPoint = new LandPoint();

                                    landPoint.setAltitude(Double.parseDouble(
                                            binding.altitudeTextField.getText().toString()));
                                    landPoint.setLat(missionPoint.getLat());
                                    landPoint.setLon(missionPoint.getLon());
                                    landPoint.setPointPathNum(missionPoint.getPointPathNum());
                                    mMarker.setTag(landPoint);
                                    break;

                                case 3:
                                    RtlPoint rtlPoint = new RtlPoint();

                                    rtlPoint.setAltitude(Double.parseDouble(
                                            binding.altitudeTextField.getText().toString()));
                                    rtlPoint.setPointPathNum(missionPoint.getPointPathNum());
                                    rtlPoint.setLat(missionPoint.getLat());
                                    rtlPoint.setLon(missionPoint.getLon());
                                    mMarker.setTag(rtlPoint);
                                    break;

                                default:
                                    break;
                            }
                        })
                .setNegativeButton(getString(R.string.dialog_negative_button_title), null);


        return builder.create();
    }
}
