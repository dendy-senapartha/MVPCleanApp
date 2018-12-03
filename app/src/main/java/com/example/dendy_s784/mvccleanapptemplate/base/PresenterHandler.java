package com.example.dendy_s784.mvccleanapptemplate.base;

/**
 * Basic handler for presenter
 */

public interface PresenterHandler {

    void registerPresenter(AbstractContract.AbstractPresenter... presenters);

    void disposePresenter();
}
