package com.example.alon.airvanguard.presentation.drone_control;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.domain.core.drone_control_tower.DroneControlTower.*;
import com.example.alon.airvanguard.presentation.drone_control.dialogs.DroneControlDialogs;

/**
 * Drone connection options dialog.
 */

public class ConnectDroneDialog extends DialogFragment {

    // default connection type.
    private DroneConnectionType mSelectedConnectionType = DroneConnectionType.UDP;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle("Select connection type")
                .setSingleChoiceItems(R.array.drone_connections_types, 0,
                        (dialog, which) -> {
                            String type = getResources().getStringArray(
                                    R.array.drone_connections_types)[which];

                            switch (DroneConnectionType.valueOf(type)) {
                                case UDP:
                                    mSelectedConnectionType = DroneConnectionType.UDP;
                                    break;

                                case USB:
                                    mSelectedConnectionType = DroneConnectionType.USB;
                                    break;

                                default:
                                    break;

                            }
                        })
                .setPositiveButton("OK",(dialog, which) -> ((DroneControlDialogs)getActivity())
                        .onDroneConnectionTypeSelected(mSelectedConnectionType))
                .setNegativeButton("Cancel",null)
                .show();
    }
}
