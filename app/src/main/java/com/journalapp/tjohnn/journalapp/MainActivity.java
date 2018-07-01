package com.journalapp.tjohnn.journalapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.utils.Utils;
import com.journalapp.tjohnn.journalapp.view.DashboardFragment;

public class MainActivity extends AppCompatActivity implements MainView{

    ActionBar toolbar;
    FragmentManager fragmentManager;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null){
            Utils.openFragment(fragmentManager, DashboardFragment.newInstance(), DashboardFragment.TAG, R.id.content_main);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            fragmentManager.popBackStackImmediate();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void displayUpNavigation(boolean display){
        if(toolbar != null){
            toolbar.setHomeButtonEnabled(display);
            toolbar.setDisplayHomeAsUpEnabled(display);
            toolbar.setDisplayShowHomeEnabled(display);
        }
    }

    @Override
    public void showProgressBar(boolean b) {

    }

}
