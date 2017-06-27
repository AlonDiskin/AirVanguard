package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import com.example.alon.airvanguard.domain.repository.UserPrefsRepository.*;

/**
 * Created by alon on 6/27/17.
 */

public interface RemovePrefsListener {

    void execute(OnPrefChangeListener listener);
}
