package com.example.alon.airvanguard.domain.application_services.drone_control.use_case.impl;

import com.example.alon.airvanguard.domain.application_services.drone_control.use_case.interfaces.AddPrefsListener;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;

import javax.inject.Inject;

/**
 * Created by alon on 6/27/17.
 */

public class AddPrefsListenerImpl implements AddPrefsListener {

    private UserPrefsRepository mUserPrefsRepository;

    @Inject
    public AddPrefsListenerImpl(UserPrefsRepository userPrefsRepository) {
        mUserPrefsRepository = userPrefsRepository;
    }

    @Override
    public void execute(UserPrefsRepository.OnPrefChangeListener listener) {
        mUserPrefsRepository.addListener(listener);
    }
}
