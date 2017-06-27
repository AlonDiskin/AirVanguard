package com.example.alon.airvanguard.domain.application_services.common;

import android.support.annotation.Nullable;

/**
 * Application services simple use case interface. Represents a application
 * services layer use case, that results in the execution of an sync operation
 *
 * @param <T> input parameter class for this use case task execution.
 * @param <R> result class type of this use case task execution.
 */

public interface UseCase<T,R> {

    /**
     * Execute use case task
     *
     * @param param input param for this use cases task, may bee null.
     * @return returns a use case task result, or null
     * if non is requaired.
     */
    @Nullable
    R executeUseCase(@Nullable T param);
}
