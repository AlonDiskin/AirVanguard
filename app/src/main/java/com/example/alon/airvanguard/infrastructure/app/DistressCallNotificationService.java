package com.example.alon.airvanguard.infrastructure.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.example.alon.airvanguard.R;
import com.example.alon.airvanguard.domain.core.entity.DistressCall;
import com.example.alon.airvanguard.domain.repository.DistressCallRepository;
import com.example.alon.airvanguard.domain.repository.UserPrefsRepository;
import com.example.alon.airvanguard.infrastructure.di.component.DaggerNotificationServiceComponent;
import com.example.alon.airvanguard.infrastructure.di.module.NotificationServiceModule;
import com.example.alon.airvanguard.presentation.common.DateTimeUtil;

import javax.inject.Inject;

/**
 * This service runs in the background and listens for
 * incoming distress calls from distress calls repository.
 * When a distress call arrives, the service sends a notification
 * to the device status bar.
 */

public class DistressCallNotificationService extends Service implements DistressCallRepository.DistressCallListener {

    private static final String LOG_TAG = DistressCallNotificationService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 1;

    @Inject
    NotificationManager mNotificationManager;
    @Inject
    NotificationCompat.Builder mNotificationBuilder;
    @Inject
    DistressCallRepository mDistressCallRepository;
    @Inject
    UserPrefsRepository mUserPrefsRepository;
    @Inject
    PendingIntent mPendingIntent;

    public DistressCallNotificationService() {
        injectService();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG,"start notification service");
        // add this service as a listener to distress call repository
        mDistressCallRepository.addDistressCallListener(this);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG,"stop notification service");
        // remove this service as a listener to distress call repository
        mDistressCallRepository.removeDistressCallListener(this);
        super.onDestroy();
    }

    @Override
    public void onCallAdded(@NonNull DistressCall call) {
        sendNotification(call);
    }

    @Override
    public void onCallRemoved(@NonNull DistressCall call) {

    }

    /**
     * Send a status bar notification alerting the
     * user that a new distress call has been received.
     *
     * @param call the new incoming distress call.
     */
    private void sendNotification(DistressCall call) {
        // build a notification
        mNotificationBuilder.setContentTitle("Distress Call")
                .setContentText(call.getUser().getName())
                .setSubText(DateTimeUtil.convert(call.getTimeStamp(),
                        getString(R.string.distress_call_sending_time_format)))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setVibrate(new long[] {1000})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setTicker("AirVanguard\nnew distress call")
                .setContentIntent(mPendingIntent)
                .setAutoCancel(true);

        // send notification
        mNotificationManager.notify(NOTIFICATION_ID,mNotificationBuilder.build());

    }

    /**
     * Inject service dependencies.
     */
    private void injectService() {
        DaggerNotificationServiceComponent.builder()
                .appComponent(AirVanguardApp.getAppComponent())
                .notificationServiceModule(new NotificationServiceModule())
                .build()
                .inject(this);
    }

}
