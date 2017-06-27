package com.example.alon.airvanguard.presentation.edit_mission.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.alon.airvanguard.R;

/**
 * Mission Edit completion dialog. This dialog
 * shows when a user confirms that he is finished
 * with drone mission editing.
 */

public class CompleteEditMissionDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        return builder.setTitle(getString(R.string.dialog_title_edit_complete))
                .setMessage(getString(R.string.dialog_message_edit_complete))
                .setPositiveButton(getString(R.string.dialog_positive_button_title),
                        (dialog1, which) -> ((EditMissionDialogs)getActivity())
                                .onCompleteEditMissionPositiveClick())
                .setNegativeButton(getString(R.string.dialog_negative_button_title),null)
                .create();
    }
}
