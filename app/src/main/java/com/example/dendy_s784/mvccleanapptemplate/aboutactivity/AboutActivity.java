package com.example.dendy_s784.mvccleanapptemplate.aboutactivity;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * Created by dendy-prtha on 20/02/2019.
 * Example of about activity
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.backButton)
    Button btnBack;

    @Override
    public int getLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void init() {

    }

    @Override
    protected void configToolbar() {

    }

    @OnClick(R.id.backButton)
    public void onClickRightMenuButton(View view) {
        //startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }
}
