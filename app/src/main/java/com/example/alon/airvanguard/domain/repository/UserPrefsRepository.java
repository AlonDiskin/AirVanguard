package com.example.alon.airvanguard.domain.repository;



/**
 * User application preferences repository.
 */

public interface UserPrefsRepository {

    interface OnPrefChangeListener {

        void onMapTypeChange(int mapType);

        void onUnitTypeChange(int unitType);
    }

    int getMapType();

    int getUnitsType();

    void addListener(OnPrefChangeListener listener);

    void removeListener(OnPrefChangeListener listener);
}
