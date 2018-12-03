package com.example.dendy_s784.mvccleanapptemplate.base;

/**
 * Abstract for view and presenter behavior
 */
public interface AbstractContract {

    interface AbstractView {

        void showProgress();

        void dismissProgress();

        void onError(String errorMessage);

    }

    interface AuthenticatedAbstractView extends AbstractView {

        void onSessionExpired();

    }

    interface AbstractPresenter {

        void onDestroy();

    }
}
