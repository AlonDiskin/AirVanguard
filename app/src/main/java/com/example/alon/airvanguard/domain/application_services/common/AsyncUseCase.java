package com.example.alon.airvanguard.domain.application_services.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Application services use case interface. Represents a application
 * services layer use case, that results in the execution of an async operation
 *
 * @param <T> input parameter class for this use case task execution.
 * @param <R> result class type of this use case task execution.
 */

public interface AsyncUseCase<T,R> {

    interface Success<R> {

        void onSuccess(@Nullable R result);
    }

    interface Failure {

        void onFailure(@NonNull Throwable throwable);
    }

    /**
     * Executes the designated use case task.
     *
     * @param param use case input param data type.
     * @param success use case execution success execution callback.
     * @param failure use case execution failure callback.
     */
    void executeUseCase(@Nullable T param, @Nullable Success<R> success, @Nullable Failure failure);
}
