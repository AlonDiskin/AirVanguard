package com.example.alon.airvanguard.presentation.edit_mission;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.alon.airvanguard.domain.application_services.edit_mission.facade.EditMissionDomain;
import com.example.alon.airvanguard.domain.core.entity.Location;
import com.example.alon.airvanguard.presentation.common.BasePresenter;

import javax.inject.Inject;

/**
 * Edit mission feature presenter.
 */

public class EditMissionPresenter extends BasePresenter<EditMissionContract.View,EditMissionDomain>
        implements EditMissionContract.Presenter {

    @Inject
    public EditMissionPresenter(@NonNull EditMissionContract.View view, @NonNull EditMissionDomain domain) {
        super(view, domain);
    }

    @Override
    public int getMapType() {
        return getDomain().getMapType();
    }

    @Nullable
    @Override
    public Location getDroneLocation() {
        return getDomain().getDroneLocation();
    }

    @Override
    public void loadAllDistressCalls() {
        getDomain().loadDistressCalls(result -> {
            if (isViewExist()) {
                getView().showDistressCalls(result);
            }
        },Throwable::printStackTrace);
    }
}
