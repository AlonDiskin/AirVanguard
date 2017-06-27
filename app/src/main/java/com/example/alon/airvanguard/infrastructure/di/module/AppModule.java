package com.example.alon.airvanguard.infrastructure.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alon on 4/18/17.
 */
@Module
public class AppModule {

    private Context mAppContext;

    public AppModule(@NonNull Context context) {
        mAppContext = context;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return mAppContext;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
