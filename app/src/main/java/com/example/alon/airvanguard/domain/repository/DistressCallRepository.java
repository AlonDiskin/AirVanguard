package com.example.alon.airvanguard.domain.repository;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.core.entity.DistressCall;

import java.util.List;

/**
 * {@link DistressCall} repository
 */

public interface DistressCallRepository {

    interface DistressCallListener {

        void onCallAdded(@NonNull DistressCall call);

        void onCallRemoved(@NonNull DistressCall call);
    }

    interface LoadCallsSuccess {

        void onLoad(@NonNull List<DistressCall> calls);
    }

    interface LoadCallsFailure {

        void onFailure(@NonNull Throwable throwable);
    }

    void addDistressCallListener(@NonNull DistressCallListener listener);

    void removeDistressCallListener(@NonNull DistressCallListener listener);

    void getAll(@NonNull LoadCallsSuccess successCb,@NonNull LoadCallsFailure failureCb);
}
