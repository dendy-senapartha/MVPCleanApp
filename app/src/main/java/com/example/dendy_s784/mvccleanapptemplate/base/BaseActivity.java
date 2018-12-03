package com.example.dendy_s784.mvccleanapptemplate.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.ApplicationComponent;
import com.example.dendy_s784.mvccleanapptemplate.aplication.MVPCleanAppTemplate;
import com.example.dendy_s784.mvccleanapptemplate.utils.SizeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 *abstract activity for all activity of the app
 */

public abstract class BaseActivity extends AppCompatActivity
        implements BasicMenuFacade, PresenterHandler, DisposableHandler{

    @Nullable
    @BindView(R.id.base_toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Nullable
    @BindView(R.id.iv_title_image)
    ImageView toolbarImage;

    @Nullable
    @BindView(R.id.left_button)
    TextView leftButton;

    @Nullable
    @BindView(R.id.right_button)
    protected TextView rightButton;

    @Nullable
    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;

    private Unbinder unbinder;

    private List<AbstractContract.AbstractPresenter> presenterList;

    public abstract int getLayout();

    public abstract void init();

    public void init(Bundle savedInstanceState){
        init();
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        initToolbar();
        init(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        dispose();
        disposePresenter();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void registerPresenter(AbstractContract.AbstractPresenter... presenters) {
        if (presenterList == null) {
            presenterList = new ArrayList<>();
        }
        if (presenters != null && presenters.length > 0) {
            presenterList.addAll(Arrays.asList(presenters));
        }
    }

    @Override
    public void disposePresenter() {
        if (presenterList != null) {
            for (AbstractContract.AbstractPresenter presenter : presenterList) {
                presenter.onDestroy();
            }
        }
    }

    /**
     * get {@link ApplicationComponent}
     */
    public ApplicationComponent getApplicationComponent() {
        return ((MVPCleanAppTemplate) getApplication()).getApplicationComponent();
    }

    private void initToolbar() {
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            setTitle("");
            configToolbar();
        }
    }

    protected void setTitle(String title) {
        toolbarTitle.setText(title);
    }

    protected abstract void configToolbar();

    @Optional
    @OnClick(R.id.left_button)
    public void onClickLeftMenuButton(View view) {
    }

    @Optional
    @OnClick(R.id.right_button)
    public void onClickRightMenuButton(View view) {
    }

    @Optional
    @OnClick(R.id.toolbar_title)
    public void OnClickTitle(View view) {
    }

    /**
     * set toolbar Menu LeftButton enabled
     */
    @Override
    public void setMenuLeftButtonEnabled(boolean isEnabled) {
        if (leftButton != null) {
            leftButton.setEnabled(isEnabled);
        }
    }

    /**
     * set left menu using icon
     */
    @Override
    public void setMenuLeftButton(@DrawableRes int icon) {
        if (leftButton != null && icon != 0) {
            setMarginLeft(10);
            leftButton.setBackground(getDrawableFromRes(icon));
            leftButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * set toolbar Menu LeftButton
     */
    @Override
    public void setMenuLeftButton(String leftButtonText) {
        if (leftButton != null) {
            setMarginLeft(10);
            leftButton.setText(leftButtonText);
            if (leftButtonText != null && !leftButtonText.isEmpty()) {
                leftButton.setVisibility(View.VISIBLE);
            } else {
                leftButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * set toolbar Menu RightButton
     */
    @Override
    public void setMenuRightButton(String rightButtonText) {
        if (rightButton != null) {
            setMarginRight(10);
            rightButton.setText(rightButtonText);
            if (rightButtonText != null && !rightButtonText.isEmpty()) {
                rightButton.setVisibility(View.VISIBLE);
            } else {
                rightButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void setMenuRightButton(@DrawableRes int icon) {
        if (rightButton != null && icon != 0) {
            setMarginLeft(10);
            rightButton.setBackground(getDrawableFromRes(icon));
            rightButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * set right menu using icon
     */
    @Override
    public void setMenuRightButtonEnabled(boolean isEnabled) {
        if (rightButton != null) {
            rightButton.setEnabled(isEnabled);
        }
    }

    /**
     * set margin right toolbar
     */
    private void setMarginRight(int marginRight) {
        RelativeLayout.MarginLayoutParams relativeParams = getToolbarParam();
        relativeParams.setMargins(relativeParams.leftMargin, relativeParams.topMargin,
                SizeUtils.convertToPx(marginRight), relativeParams.bottomMargin);
        rlToolbar.setLayoutParams(relativeParams);
    }

    /**
     * set margin left toolbar
     */
    private void setMarginLeft(int marginLeft) {
        RelativeLayout.MarginLayoutParams relativeParams = getToolbarParam();
        relativeParams.setMargins(SizeUtils.convertToPx(marginLeft), relativeParams.topMargin,
                relativeParams.rightMargin, relativeParams.bottomMargin);
        rlToolbar.setLayoutParams(relativeParams);
    }

    /**
     * get margin param of toolbar
     */
    private RelativeLayout.MarginLayoutParams getToolbarParam() {
        return (RelativeLayout.MarginLayoutParams) rlToolbar.getLayoutParams();
    }

    public Drawable getDrawableFromRes(@DrawableRes int drawableRes) {
        return ContextCompat.getDrawable(this, drawableRes);
    }

    protected void setToolbarImage(@DrawableRes int drawableRes) {
        toolbarTitle.setVisibility(View.GONE);
        toolbarImage.setVisibility(View.VISIBLE);
        toolbarImage.setImageDrawable(getDrawableFromRes(drawableRes));
    }
}
