package com.example.dendy_s784.mvccleanapptemplate.base;

import android.support.annotation.DrawableRes;

/***
 * Basic Menu item facade for all activity
 */

public interface BasicMenuFacade {

    /**
     * set toolbar Menu LeftButton
     */
    void setMenuLeftButton(String leftButtonText);

    /**
     * set toolbar Menu LeftButton enabled
     */
    void setMenuLeftButtonEnabled(boolean isEnabled);

    /**
     * set toolbar Menu RightButton
     */
    void setMenuRightButton(String rightButtonText);

    /**
     * set toolbar Menu RightButton enabled
     */
    void setMenuRightButtonEnabled(boolean isEnabled);

    /**
     * set toolbar Menu LeftButton Drawable
     */
    void setMenuLeftButton(@DrawableRes int icon);

    /**
     * set toolbar Menu RightButton Drawable
     */
    void setMenuRightButton(@DrawableRes int icon);
}
