package com.example.alon.airvanguard.presentation.common;

/**
 * Base presentation contract between a view and
 * a presenter within the presentation layer.
 */

public interface BaseContract {

    /**
     * Base presentation view
     */
    interface View {

    }

    /**
     * Base presentation presenter
     */
    interface Presenter {

        /**
         * Called when this presenters view
         * is about to be visible.
         */
        void onViewVisible();

        /**
         * Called when this presenters view
         * is about to be invisible.
         */
        void onViewInvisible();

        /**
         * Called when this presenters view
         * is about to be destroyed.
         */
        void onViewDestroy();

        /**
         * Called when this presenter is about to be removed
         * from presenters stack.
         */
        void onRemovePresenter();
    }
}
