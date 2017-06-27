package com.example.alon.airvanguard.presentation.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.alon.airvanguard.infrastructure.app.AirVanguardApp;

import javax.inject.Inject;

/**
 * Base class for presentation views,implemented using the {@link AppCompatActivity} component.
 * This class is abstract and will hold a ref to a {@link BaseContract.Presenter} implementation,
 * invoking him at certain activity state changes.
 *
 * @param <T> implementation of {@link BaseContract.Presenter}
 */

public abstract class BaseActivity<T extends BaseContract.Presenter> extends AppCompatActivity
        implements BaseContract.View {

    @Inject
    T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init the views presenter, passing whether this activity
        // is created for the first time via the state of saved instance object.
        initPresenter(savedInstanceState == null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // notify presenter that this view is about to be visible
        mPresenter.onViewVisible();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // notify presenter that this view is now invisible
        mPresenter.onViewInvisible();
    }

    @Override
    protected void onDestroy() {
        // notify the presenter that this view is about to be destroyed
        mPresenter.onViewDestroy();
        super.onDestroy();
    }

    /**
     * Inject the presenter for this view
     */
    protected abstract void injectPresenter();

    /**
     * Get the views presenter.
     *
     * @return this views presenter.
     */
    protected T getPresenter() {
        return mPresenter;
    }

    /**
     * Initializes the presenter for this view.
     *
     * @param isFirstCreated whether this activity is created for the first time
     */
    private void initPresenter(boolean isFirstCreated) {
        // if this is the first creation of this activity,inject the
        // presenter and push it to the application presenters stack
        if (isFirstCreated) {
            injectPresenterAndPassToStack();

            // if this is a recreated activity,restore its presenter
        } else {

            if (!AirVanguardApp.isPresentersStackEmpty()) {
                restorePresenter();

                // presenter does not exist, inject a new one
                // and push it to the application presenters stack
            } else {
                injectPresenterAndPassToStack();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mPresenter.onRemovePresenter();
        AirVanguardApp.popPresenter();
    }

    @Override
    public boolean onSupportNavigateUp() {
        mPresenter.onRemovePresenter();
        AirVanguardApp.popPresenter();
        return super.onSupportNavigateUp();
    }

    /**
     * Restores the presenter for this view.
     */
    private void restorePresenter() {
        mPresenter = (T) AirVanguardApp.getPresenter();
        ((BasePresenter)mPresenter).setView(this);
    }

    /**
     * Injects the presenter for this view, and
     * add it to the application presenters stack.
     */
    private void injectPresenterAndPassToStack() {
        injectPresenter();
        AirVanguardApp.pushPresenter(mPresenter);
    }
}
