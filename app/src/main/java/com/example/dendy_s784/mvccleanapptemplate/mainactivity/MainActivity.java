package com.example.dendy_s784.mvccleanapptemplate.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.base.BaseActivity;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.MainActivityComponent;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab_add_note)
    FloatingActionButton fab;

    PopupMenu popup;

    NotesFragment notesFragment;

    private MainActivityComponent component;

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
        if(notesFragment == null) {
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
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.list_item:
                                //TODO :define the action for list_item
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

    private void initOptionMenu()
    {
        //getMenuInflater().inflate(R.menu.notes_fragment_menu, getMen);
        popup = new PopupMenu(this, rightButton);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.notes_fragment_menu, popup.getMenu());
        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_delete_marked:
                        //mPresenter.DeleteMarkedNotes(mMarkedNotes);
                        break;
                    case R.id.menu_refresh:
                        //mPresenter.loadNotes(true);
                        break;
                }
                return true;
            }
        });
    }
}
