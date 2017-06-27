package com.example.alon.airvanguard.infrastructure.di.module;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.alon.airvanguard.infrastructure.di.scope.ServiceScope;
import com.example.alon.airvanguard.presentation.drone_control.DroneControlActivity;

import dagger.Module;
import dagger.Provides;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by alon on 6/26/17.
 */
@Module
public class NotificationServiceModule {

    @ServiceScope
    @Provides
    NotificationManager provideNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    @ServiceScope
    @Provides
    NotificationCompat.Builder provideNotificationBuilder(Context context) {
        return new NotificationCompat.Builder(context);
    }

    @ServiceScope
    @Provides
    PendingIntent providePendingIntent(Context context) {
        Intent notificationIntent = new Intent(context, DroneControlActivity.class);
        return PendingIntent.getActivity(context, 0, notificationIntent, 0);
    }
}
