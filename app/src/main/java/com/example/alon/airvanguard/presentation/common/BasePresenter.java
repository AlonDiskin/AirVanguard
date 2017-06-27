package com.example.alon.airvanguard.presentation.common;

import android.support.annotation.NonNull;

import com.example.alon.airvanguard.domain.application_services.common.Domain;


/**
 * Base class for presenters
 *
 * @param <T>
 * @param <V>
 */
public abstract class BasePresenter<T extends BaseContract.View, V extends Domain>
        implements BaseContract.Presenter {

    private T mView;
    private V mDomain;
    private boolean mViewVisible;

    public BasePresenter(@NonNull T view, @NonNull V domain) {
        mView = view;
        mDomain = domain;
    }

    @Override
    public void onViewVisible() {
        mViewVisible = true;
    }

    @Override
    public void onViewInvisible() {
        mViewVisible = false;
    }

    @Override
    public void onViewDestroy() {
        mView = null;
    }

    @Override
    public void onRemovePresenter() {

    }

    /**
     * Get presenters view.
     *
     * @return presenter view
     */
    protected T getView() {
        return mView;
    }

    public void setView(T view) {
        mView = view;
    }

    /**
     * @return presenters domain
     */
    protected V getDomain() {
        return mDomain;
    }

    /**
     *
     * Get the state of presenters view visibility
     *
     * @return true if the view of this presenter is
     * in currently visible, else returns false.
     */
    protected boolean isViewVisible() {
        return mViewVisible;
    }

    /**
     * Get state of presenters view.
     *
     * @return true if the view of this presenter exist,
     * else returns false.
     */
    protected boolean isViewExist() {
        return mView != null;
    }
}
