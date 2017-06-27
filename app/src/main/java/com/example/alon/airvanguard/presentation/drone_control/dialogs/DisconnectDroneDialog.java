package com.example.alon.airvanguard.presentation.drone_control.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Drone disconnect confirmation dialog.
 */

public class DisconnectDroneDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder.setTitle("Drone Disconnection")
                .setMessage("Continue with disconnection?")
                .setPositiveButton("OK",(dialog, which) ->
                        ((DroneControlDialogs)getActivity()).onDisconnectConfirmed())
                .setNegativeButton("Cancel",null)
                .create();

    }
}
