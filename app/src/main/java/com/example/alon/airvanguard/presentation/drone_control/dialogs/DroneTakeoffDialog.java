package com.example.alon.airvanguard.presentation.drone_control.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.NumberPicker;

import com.example.alon.airvanguard.R;

/**
 * Drone take off dialog.
 */

public class DroneTakeoffDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_numberpicker,null);
        NumberPicker numberPicker = (NumberPicker) dialogView.findViewById(R.id.takeoffPicker);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(2);

        return builder.setView(dialogView)
                .setTitle("Takeoff Height")
                .setPositiveButton("OK",(dialog1, which) ->
                        ((DroneControlDialogs)getActivity()).onDroneTakeoffHeightSelected(numberPicker.getValue()))
                .setNegativeButton("CANCEL",null)
                .create();
    }
}
