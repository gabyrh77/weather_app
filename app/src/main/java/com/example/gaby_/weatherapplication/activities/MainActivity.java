package com.example.gaby_.weatherapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.gaby_.weatherapplication.utils.WeatherParcelable;
import com.example.gaby_.weatherapplication.fragments.AboutFragment;
import com.example.gaby_.weatherapplication.R;
import com.example.gaby_.weatherapplication.fragments.DetailFragment;
import com.example.gaby_.weatherapplication.fragments.WeatherFragment;
import com.example.gaby_.weatherapplication.api.WeatherResponse;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        WeatherFragment.OnListFragmentInteractionListener{
    private String ABOUT_FRAGMENT_TAG = "ABOUT_TAG";
    private String LIST_FRAGMENT_TAG = "LIST_TAG";
    private String DETAIL_FRAGMENT_TAG = "DETAIL_TAG";
    private String SELECTED_FRAGMENT_KEY = "SEL_FRAG_KEY";
    private String mSelectedFragment;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_view);

        if(savedInstanceState!=null){
            mSelectedFragment = savedInstanceState.getString(SELECTED_FRAGMENT_KEY);
        }else{
            navigationView.getMenu().getItem(0).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_list));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SELECTED_FRAGMENT_KEY, mSelectedFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Snackbar
                    .make(mCoordinatorLayout, R.string.text_settings, Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.action_settings), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(Settings.ACTION_SETTINGS));
                            //dismiss
                        }
                    })
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_list) {
            mSelectedFragment = LIST_FRAGMENT_TAG;
        } else {
            mSelectedFragment = ABOUT_FRAGMENT_TAG;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(mSelectedFragment);
        if(fragment == null) {
            if( mSelectedFragment.equals(LIST_FRAGMENT_TAG)){
                fragment = WeatherFragment.newInstance();
            }else{
                fragment = AboutFragment.newInstance();
            }
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment, mSelectedFragment)
                .addToBackStack(mSelectedFragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(WeatherResponse item, boolean isTablet) {
        WeatherParcelable myParcelable = new WeatherParcelable(item);
        Fragment detailFragment =  DetailFragment.newInstance(myParcelable);
        if(!isTablet) {
            mSelectedFragment = DETAIL_FRAGMENT_TAG;
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_main, detailFragment, DETAIL_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }else{
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.weather_detail_container, detailFragment, DETAIL_FRAGMENT_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
