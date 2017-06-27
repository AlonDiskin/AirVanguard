package com.example.alon.airvanguard.infrastructure.di.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alon.airvanguard.data.repository.UserPrefsRepositoryImpl;
import com.example.alon.airvanguard.data.repository.DistressCallRepositoryImpl;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alon on 4/18/17.
 */
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    FirebaseDatabase providesFirebaseDatabase() {
        return FirebaseDatabase.getInstance();
    }

    @Provides
    @Singleton
    UserPrefsRepository providesUserRepository(SharedPreferences sharedPreferences, Context context) {
        return new UserPrefsRepositoryImpl(sharedPreferences, context);
    }

    @Provides
    @Singleton
    DistressCallRepository providesDistressCallRepository(FirebaseDatabase database) {
        return new DistressCallRepositoryImpl(database.getReference("distress_calls"));
    }
}
