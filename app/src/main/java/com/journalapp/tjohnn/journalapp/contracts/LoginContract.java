package com.journalapp.tjohnn.journalapp.contracts;

import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.journalapp.tjohnn.journalapp.BaseView;

/**
 * Created by Tjohn on 6/25/18.
 */

public interface LoginContract {

    interface View extends BaseView{


        void openDashboard(FirebaseUser account);

        void doFirebaseLogin(GoogleSignInAccount account);

        void showLoginError(Exception e);
    }

    interface  Presenter{
        void handleSignInResult(Task<GoogleSignInAccount> task);

        void handleFirebaseResult(Task<AuthResult> task, FirebaseUser user);

        void saveUserAndOpenDashboard(FirebaseUser user);
    }

}
