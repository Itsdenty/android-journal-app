package com.journalapp.tjohnn.journalapp.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.journalapp.tjohnn.journalapp.contracts.LoginContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * Created by Tjohn on 7/1/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthPresenterTest {


    @Mock
    LoginContract.View view;

    @Mock
    PreferencesHelper preferencesHelper;

    @Mock
    Task<GoogleSignInAccount> signInAccountTask;

    AuthPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new AuthPresenter(view, preferencesHelper);
    }

    @Test
    public void handleSignInResult_shouldShowLoginError() throws Exception {
        ApiException exception = new ApiException(Status.RESULT_INTERNAL_ERROR);
        when(signInAccountTask.getResult(any())).thenThrow(exception);
        presenter.handleSignInResult(signInAccountTask);
        verify(view).showLoginError(exception);
    }

    @Test
    public void handleSignInResult_shouldDoFirebaseLogin() throws Exception {
        GoogleSignInAccount googleSignInAccount = mock(GoogleSignInAccount.class);
        when(signInAccountTask.getResult(any())).thenReturn(googleSignInAccount);
        presenter.handleSignInResult(signInAccountTask);
        verify(view).doFirebaseLogin(googleSignInAccount);
    }

    @Test
    public void handleFirebaseResult_showLoginError() throws Exception {
        Task<AuthResult> authResultTask = mock(Task.class);
        when(authResultTask.isSuccessful()).thenReturn(false);
        FirebaseUser firebaseUser = mock(FirebaseUser.class);
        presenter.handleFirebaseResult(authResultTask, firebaseUser);
        verify(view).showLoginError(any());
    }

    @Test
    public void handleFirebaseResult_shouldSaveUserAndOpenDashboard() throws Exception {
        Task<AuthResult> authResultTask = mock(Task.class);
        FirebaseUser firebaseUser = mock(FirebaseUser.class);
        String email = "email@email.com";
        String name = "name name";
        when(firebaseUser.getDisplayName()).thenReturn(name);
        when(firebaseUser.getEmail()).thenReturn(email);
        when(authResultTask.isSuccessful()).thenReturn(true);
        presenter.handleFirebaseResult(authResultTask, firebaseUser);
        verify(preferencesHelper).putString(Constants.USER_EMAIL_PREF_KEY, email);
        verify(preferencesHelper).putString(Constants.USER_NAME_PREF_KEY, name);
        verify(view).openDashboard(firebaseUser);
    }

}