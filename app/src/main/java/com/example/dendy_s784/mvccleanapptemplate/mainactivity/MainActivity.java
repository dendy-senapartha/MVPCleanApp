package com.example.dendy_s784.mvccleanapptemplate.mainactivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.aboutactivity.AboutActivity;
import com.example.dendy_s784.mvccleanapptemplate.base.BaseActivity;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.MainActivityComponent;
import com.example.dendy_s784.mvccleanapptemplate.draganddrop.DragActivity;
import com.example.dendy_s784.mvccleanapptemplate.locationservice.ActivityRecognitionIntentService;
import com.example.dendy_s784.mvccleanapptemplate.locationservice.LocationActivity;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab_add_note)
    FloatingActionButton fab;

    String TAG = MainActivity.class.getSimpleName();

    PopupMenu popup;

    NotesFragment notesFragment;

    private MainActivityComponent component;

    ActivityRecognitionClient activityRecognitionClient;

    private PendingIntent mPendingIntent;

    BroadcastReceiver broadcastReceiver;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        //setup the navigation drawer
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        initOptionMenu();

        notesFragment = (NotesFragment) this.getSupportFragmentManager().
                findFragmentById(R.id.contentFrame);
        if (notesFragment == null) {
            //create fragment
            notesFragment = NotesFragment.newInstance();
            this.getSupportFragmentManager().beginTransaction().
                    add(R.id.contentFrame, notesFragment).commit();
        }

        // Set up floating action button
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesFragment.showAddNote();
            }
        });

        activityRecognitionClient = ActivityRecognition.getClient(getApplicationContext());
        Intent intent = new Intent(this, ActivityRecognitionIntentService.class);
        mPendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        requestActivityUpdatesButtonHandler();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "Intent Action " + intent.getAction());
                if (intent.getAction().equals(ActivityRecognitionIntentService.BROADCAST_DETECTED_ACTIVITY)) {
                    int type = intent.getIntExtra("type", -1);
                    int confidence = intent.getIntExtra("confidence", 0);
                    handleUserActivity(type, confidence);
                }
            }
        };
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.drag_and_drop_example:
                                //TODO :define the action for list_item
                                startActivity(new Intent(MainActivity.this, DragActivity.class));
                                break;
                            case R.id.logout:
                                //TODO :define the action
                                notesFragment.signOut();
                                break;
                            default:
                                break;
                        }
                        //close the navigation drawer when an item is selected
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                }
        );
    }

    @Override
    protected void configToolbar() {
        setTitle("My Notes");
        setMenuLeftButton(R.drawable.ic_menu);
        setMenuRightButton(R.drawable.ic_more);
    }

    @Override
    public void onClickLeftMenuButton(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onClickRightMenuButton(View view) {
        popup.show(); //showing popup menu
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(ActivityRecognitionIntentService.BROADCAST_DETECTED_ACTIVITY));
    }

    @Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeActivityUpdatesButtonHandler();
    }

    private void initOptionMenu() {
        //getMenuInflater().inflate(R.menu.notes_fragment_menu, getMen);
        popup = new PopupMenu(this, rightButton);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.notes_fragment_menu, popup.getMenu());
        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_location:
                        startActivity(new Intent(MainActivity.this, LocationActivity.class));
                        break;
                    case R.id.menu_about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    public void requestActivityUpdatesButtonHandler() {
        Task<Void> task = activityRecognitionClient.requestActivityUpdates(
                ActivityRecognitionIntentService.DETECTION_INTERVAL_IN_MILLISECONDS,
                mPendingIntent);

        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void result) {
                /*
                Toast.makeText(getApplicationContext(),
                        "Successfully requested activity updates",
                        Toast.LENGTH_LONG)
                        .show();*/
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                /*
                Toast.makeText(getApplicationContext(),
                        "Requesting activity updates failed to start",
                        Toast.LENGTH_SHORT)
                        .show();*/
            }
        });
    }

    public void removeActivityUpdatesButtonHandler() {
        Task<Void> task = activityRecognitionClient.removeActivityUpdates(
                mPendingIntent);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void result) {
                /*Toast.makeText(getApplicationContext(),
                        "Removed activity updates successfully!",
                        Toast.LENGTH_SHORT)
                        .show();*/
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                /*Toast.makeText(getApplicationContext(), "Failed to remove activity updates!",
                        Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    private void handleUserActivity(int type, int confidence) {
        /*
        switch (type) {
            case DetectedActivity.IN_VEHICLE: {
                Toast.makeText(this, "IN_VEHICLE", Toast.LENGTH_LONG).show();
                break;
            }
            case DetectedActivity.ON_BICYCLE: {
                Toast.makeText(this, "ON_BICYCLE", Toast.LENGTH_LONG).show();
                break;
            }
            case DetectedActivity.ON_FOOT: {
                Toast.makeText(this, "ON_FOOT", Toast.LENGTH_LONG).show();
                break;
            }
            case DetectedActivity.RUNNING: {
                Toast.makeText(this, "RUNNING", Toast.LENGTH_LONG).show();
                break;
            }
            case DetectedActivity.STILL: {
                Toast.makeText(this, "STILL", Toast.LENGTH_LONG).show();
                break;
            }
            case DetectedActivity.TILTING: {
                Toast.makeText(this, "TILTING", Toast.LENGTH_LONG).show();
                break;
            }
            case DetectedActivity.WALKING: {
                Toast.makeText(this, "WALKING", Toast.LENGTH_LONG).show();
                break;
            }
            case DetectedActivity.UNKNOWN: {
                Toast.makeText(this, "UNKNOWN", Toast.LENGTH_LONG).show();
                break;
            }
        }*/

        if (((type == DetectedActivity.IN_VEHICLE)
                || (type == DetectedActivity.ON_BICYCLE)
                || (type == DetectedActivity.ON_FOOT)
                || (type == DetectedActivity.RUNNING)
                || (type == DetectedActivity.WALKING))
                && (confidence > ActivityRecognitionIntentService.CONFIDENCE)) {
            Toast.makeText(this, "Please don't moving if you using this app.", Toast.LENGTH_LONG).show();
        }
    }
}
