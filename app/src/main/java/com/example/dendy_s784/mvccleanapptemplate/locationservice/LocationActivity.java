package com.example.dendy_s784.mvccleanapptemplate.locationservice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * Created by dendy-prtha on 20/02/2019.
 * example of location service
 */
public class LocationActivity extends BaseActivity {

    @BindView(R.id.buttonGetLocation)
    Button btnGetLocation;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    GPSTracker gpsTracker;

    @Override
    public int getLayout() {
        return R.layout.activity_location;
    }

    @Override
    public void init() {
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gpsTracker = new GPSTracker(LocationActivity.this);
    }

    @Override
    protected void configToolbar() {

    }

    @OnClick(R.id.buttonGetLocation)
    public void onPressedGetLocation(View view) {
        // check if GPS enabled
        if(gpsTracker.canGetLocation()){

            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

    }
}
