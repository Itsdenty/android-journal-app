package com.journalapp.tjohnn.journalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.journalapp.tjohnn.journalapp.utils.Utils;
import com.journalapp.tjohnn.journalapp.view.LoginFragment;

public class LoginActivity extends AppCompatActivity implements BaseView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utils.openFragment(getSupportFragmentManager(), LoginFragment.getInstance(), LoginFragment.TAG, R.id.main_content);
    }

    @Override
    public void showProgressBar(boolean b) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
