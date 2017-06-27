package com.example.alon.airvanguard.presentation.settings;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.infrastructure.app.AirVanguardApp;
import com.example.alon.airvanguard.infrastructure.di.component.DaggerSettingsComponent;
import com.example.alon.airvanguard.infrastructure.di.module.SettingsModule;
import com.example.alon.airvanguard.presentation.common.BaseActivity;

public class SettingsActivity extends BaseActivity<SettingsContract.Presenter> implements SettingsContract.View,
        SettingsFragment.SettingsChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void injectPresenter() {
        DaggerSettingsComponent.builder()
                .appComponent(AirVanguardApp.getAppComponent())
                .settingsModule(new SettingsModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void onDataUnitTypeChange(int type) {
        getPresenter().changeDataUnitTypeChange(type);
    }
}
