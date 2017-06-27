package com.example.alon.airvanguard.presentation.drone_control;


import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;

import java.util.List;

/**
 * Created by alon on 6/18/17.
 */

public class CustomProfileDrawerItem extends ProfileDrawerItem {

    @Override
    public void bindView(ViewHolder viewHolder, List payloads) {
        super.bindView(viewHolder, payloads);
        TextView emailTextView = (TextView) viewHolder.itemView.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_email);
        TextView nameTextView = (TextView) viewHolder.itemView.findViewById(com.mikepenz.materialdrawer.R.id.material_drawer_name);

        emailTextView.setVisibility(View.VISIBLE);
        nameTextView.setVisibility(View.VISIBLE);

        StringHolder.applyTo(this.getEmail(), emailTextView);
        StringHolder.applyTo(this.getName(), nameTextView);
    }
}
