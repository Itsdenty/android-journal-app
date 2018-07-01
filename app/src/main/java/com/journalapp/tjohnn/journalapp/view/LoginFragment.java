package com.journalapp.tjohnn.journalapp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.journalapp.tjohnn.journalapp.MainActivity;
import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.contracts.LoginContract;
import com.journalapp.tjohnn.journalapp.presenter.AuthPresenter;
import com.journalapp.tjohnn.journalapp.utils.AppDi;
import com.journalapp.tjohnn.journalapp.utils.Constants;
import com.journalapp.tjohnn.journalapp.utils.Utils;

/**
 * Created by Tjohn on 6/25/18.
 */

public class LoginFragment extends Fragment implements LoginContract.View {

    public static final String TAG = "LoginFragmentTag";
    private static final int REQUEST_CODE_SIGN_IN = 10;

    private AuthPresenter mPresenter;
    private Activity mActivity;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private Button loginButton;


    public static LoginFragment getInstance(){
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new AuthPresenter(this);
        mActivity = getActivity();
        mAuth = FirebaseAuth.getInstance();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        TextView textView = (TextView) signInButton.getChildAt(0);
        textView.setText(getString(R.string.sign_in_google));

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail().requestId().requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(mActivity, gso);
        loginButton = view.findViewById(R.id.btn_login);

        loginButton.setOnClickListener(e->{
            Toast.makeText(mActivity, getString(R.string.login_button_message), Toast.LENGTH_SHORT).show();
            // openDashboard(null);
            // AppDi.getPreferencesHelper().putString(Constants.USER_NAME_PREF_KEY, "Tosin Adeoye");
            // AppDi.getPreferencesHelper().putString(Constants.USER_EMAIL_PREF_KEY, "johnjokoo@gmail.com");
            // AppDi.getPreferencesHelper().putString(Constants.PICTURE_URL_PREF_KEY, "");
            // AppDi.getPreferencesHelper().putString(Constants.USER_ID_PREF_KEY, "4r5");
        });

        signInButton.setOnClickListener(this::signInUser);

        return view;
    }

    private void signInUser(View view) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            mPresenter.handleSignInResult(task);
        }
    }


    @Override
    public void doFirebaseLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(mActivity, e -> mPresenter.handleFirebaseResult(e, mAuth.getCurrentUser()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            mPresenter.saveUserAndOpenDashboard(currentUser);
        }
        super.onResume();
    }

    @Override
    public void showProgressBar(boolean b) {

    }

    @Override
    public void openDashboard(FirebaseUser user) {
        Utils.logD(user.getDisplayName());
        Utils.logD(user.getPhotoUrl().toString());
        startActivity(new Intent(mActivity,MainActivity.class));
    }

    @Override
    public void showLoginError(Exception e) {
        Toast.makeText(mActivity, "Login Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

}
