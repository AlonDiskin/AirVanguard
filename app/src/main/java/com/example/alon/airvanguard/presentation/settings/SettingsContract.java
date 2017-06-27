package com.example.alon.airvanguard.presentation.settings;

import com.example.alon.airvanguard.presentation.common.BaseContract;

/**
 * Created by alon on 6/27/17.
 */

public interface SettingsContract  {

    interface View extends BaseContract.View {

    }

    interface Presenter extends BaseContract.Presenter {

        void changeDataUnitTypeChange(int type);
    }
}
