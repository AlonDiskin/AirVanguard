package com.example.alon.airvanguard.presentation.drone_control.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;

/**
 * Dialog that gives the option to edit a drone mission
 * to a distress call location.
 */

public class EditDistressCallMissionDialog extends DialogFragment {

    private static DistressCall mDistressCall;

    public static EditDistressCallMissionDialog newInstance(DistressCall distressCall) {
        mDistressCall = distressCall;
        return new EditDistressCallMissionDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder.setTitle(getString(R.string.dialog_title_edit_mission))
                .setMessage(getString(R.string.dialog_message_edit_mission,
                        mDistressCall.getUser().getName()))
                .setPositiveButton(getString(R.string.dialog_positive_button_title),
                        (dialog, which) -> ((DroneControlDialogs)getActivity())
                                .onEditDistressCallMission(mDistressCall))
                .setNegativeButton(getString(R.string.dialog_negative_button_title),null)
                .create();
    }
}
