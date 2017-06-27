package com.example.alon.airvanguard.presentation.map;

import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.presentation.common.BaseContract;

import java.util.List;

/**
 * MVP contract for the map feature.
 */
public interface BaseMapContract {

    interface View extends BaseContract.View {

        /**
         * Show the given {@code call} as a map marker.
         *
         * @param call a {@link DistressCall} received by the app.
         */
        void showDistressCall(DistressCall call);

        /**
         * Shows the given {@code calls} as map
         * markers.
         *
         * @param calls a list of {@link DistressCall}s.
         */
        void showDistressCalls(List<DistressCall> calls);

        /**
         * Sets the given {@code type} as the map type
         *
         * @param mapType
         * @return
         */
        boolean setMapType(int mapType);

        /**
         *
         * @return
         */
        boolean isMapShowing();
    }

    interface Presenter extends BaseContract.Presenter {

        /**
         * Retrieves the current google map type
         * as was configured to the app prefs
         * by the user.
         *
         * @return the current map type
         */
        int getMapType();

        /**
         * Loads all currently existing {@link DistressCall}.
         */
        void loadAllDistressCalls();
    }
}
