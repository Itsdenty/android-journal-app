package com.journalapp.tjohnn.journalapp.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.journalapp.tjohnn.journalapp.contracts.LoginContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.utils.AppDi;
import com.journalapp.tjohnn.journalapp.utils.Constants;

/**
 * Created by Tjohn on 6/25/18.
 */

public class AuthPresenter implements LoginContract.Presenter {


    private PreferencesHelper preferencesHelper;
    private LoginContract.View mView;

    public AuthPresenter(LoginContract.View view){
        this(view, AppDi.getPreferencesHelper());
    }

    AuthPresenter(LoginContract.View mView, PreferencesHelper preferencesHelper) {
        this.preferencesHelper = preferencesHelper;
        this.mView = mView;
    }


    @Override
    public void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            mView.doFirebaseLogin(account);
        } catch (ApiException e) {
            e.printStackTrace();
            mView.showLoginError(e);
        }
    }

    @Override
    public void handleFirebaseResult(Task<AuthResult> task, FirebaseUser user) {
        if (task.isSuccessful()) {
            saveUserAndOpenDashboard(user);
        } else {
            mView.showLoginError(task.getException());
        }
    }

    @Override
    public void saveUserAndOpenDashboard(FirebaseUser user) {
        preferencesHelper.putString(Constants.USER_EMAIL_PREF_KEY, user.getEmail());
        preferencesHelper.putString(Constants.USER_NAME_PREF_KEY, user.getDisplayName());
        preferencesHelper.putString(Constants.USER_ID_PREF_KEY, user.getUid());
        preferencesHelper.putString(Constants.USER_ID_PREF_KEY, user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "");

        mView.openDashboard(user);
    }
}
