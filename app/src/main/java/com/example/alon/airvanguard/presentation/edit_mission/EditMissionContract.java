package com.example.alon.airvanguard.presentation.edit_mission;

import android.support.annotation.Nullable;
import com.example.alon.airvanguard.domain.core.entity.Location;
import com.example.alon.airvanguard.presentation.map.BaseMapContract;


/**
 * Edit mission feature MVP contract.
 */

public interface EditMissionContract {

    interface View extends BaseMapContract.View {
    }

    interface Presenter extends BaseMapContract.Presenter {

        /**
         * @return the last known drone location.
         */
        @Nullable
        Location getDroneLocation();
    }
}
