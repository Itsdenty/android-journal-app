package com.journalapp.tjohnn.journalapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.journalapp.tjohnn.journalapp.MainView;
import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.contracts.CreateJournalContract;
import com.journalapp.tjohnn.journalapp.presenter.CreateJournalPresenter;

/**
 * Created by Tjohn on 6/30/18.
 */

public class CreateJournalFragment extends Fragment implements CreateJournalContract.View{

    CreateJournalContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    private MainView mainView;

    EditText titleEditText;
    EditText descriptionEditText;
    Button submitButton;

    String title, description;

    public static final String TAG = "CreateJournalFragment";
    public static final String TITLE_BUNDLE_KEY = "TITLE_BUNDLE_KEY";
    public static final String DESCRIPTION_BUNDLE_KEY = "DESCRIPTION_BUNDLE_KEY";

    public static CreateJournalFragment newInstance() {
        return new CreateJournalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            title = savedInstanceState.getString(TITLE_BUNDLE_KEY);
            description = savedInstanceState.getString(DESCRIPTION_BUNDLE_KEY);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_journal, container, false);
        titleEditText = view.findViewById(R.id.et_title);
        descriptionEditText = view.findViewById(R.id.et_description);
        submitButton = view.findViewById(R.id.btn_submit);

        mPresenter = new CreateJournalPresenter(this);
        mActivity = (AppCompatActivity) getActivity();

        mainView = (MainView) mActivity;

        submitButton.setOnClickListener(view1 -> mPresenter.createJournal());
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setTitle(getString(R.string.create_new_journal));
        mainView.displayUpNavigation(true);
        if(description != null) descriptionEditText.setText(description);
        if(title != null) titleEditText.setText(title);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(TITLE_BUNDLE_KEY, getJournalTitle());
        outState.putString(DESCRIPTION_BUNDLE_KEY, getJournalDescription());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void showProgressBar(boolean b) {

    }

    @Override
    public String getJournalTitle() {
        return titleEditText.getText().toString();
    }

    @Override
    public String getJournalDescription() {
        return descriptionEditText.getText().toString();
    }

    @Override
    public void setTitleError(int stringResourceId) {
        titleEditText.setError(getString(stringResourceId));
    }

    @Override
    public void setDescriptionError(int stringResourceId) {
        descriptionEditText.setError(getString(stringResourceId));
    }

    @Override
    public void clearFormFields() {
        titleEditText.setText("");
        descriptionEditText.setText("");
    }

    @Override
    public void showSuccessMessage() {
        Snackbar.make(mActivity.findViewById(android.R.id.content), R.string.create_journal_success, Snackbar.LENGTH_LONG).show();
    }
}
