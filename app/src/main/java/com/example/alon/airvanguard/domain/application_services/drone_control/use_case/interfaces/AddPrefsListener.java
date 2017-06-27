package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces;

import com.example.alon.airvanguard.domain.repository.UserPrefsRepository.*;

/**
 * Created by alon on 6/27/17.
 */

public interface AddPrefsListener {

    void execute(OnPrefChangeListener listener);
}
