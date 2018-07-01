package com.journalapp.tjohnn.journalapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.journalapp.tjohnn.journalapp.MainView;
import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.contracts.EditJournalContract;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.presenter.EditJournalPresenter;

/**
 * Created by Tjohn on 6/30/18.
 */

public class EditJournalFragment extends Fragment implements EditJournalContract.View{

    public static final String JOURNAL_ID_KEY = "JournalId";
    EditJournalContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    private MainView mainView;
    private int journalId;
    private FragmentManager fragmentManager;

    EditText titleEditText;
    EditText descriptionEditText;
    Button submitButton;

    public static final String TAG = "EditJournalFragmentTag";

    public static EditJournalFragment newInstance() {
        return new EditJournalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null && journalId == 0){
            throw new RuntimeException("Journal entry must be set on EditJournalFragment fragment");
        }
        else if(savedInstanceState != null){
            journalId = savedInstanceState.getInt(JOURNAL_ID_KEY);
        }

        mPresenter = new EditJournalPresenter(this);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_journal, container, false);
        titleEditText = view.findViewById(R.id.et_title);
        descriptionEditText = view.findViewById(R.id.et_description);
        submitButton = view.findViewById(R.id.btn_submit);

        mActivity = (AppCompatActivity) getActivity();
        mainView = (MainView) mActivity;

        submitButton.setOnClickListener(view1 -> mPresenter.updateJournal(journalId));
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentManager = mActivity.getSupportFragmentManager();
        mActivity.setTitle(getString(R.string.edit_journal));
        mainView.displayUpNavigation(true);
        mPresenter.loadJournalDetail(journalId);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(JOURNAL_ID_KEY, journalId);
    }

    @Override
    public void onDestroy() {
        mPresenter.unSubscribe();
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
    public void closeEditFragment() {
        fragmentManager.popBackStackImmediate();
    }


    @Override
    public void showSuccessMessage() {
        Snackbar.make(mActivity.findViewById(android.R.id.content), R.string.update_journal_success, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void populateJournalDetail(JournalEntry journalEntry) {
        titleEditText.setText(journalEntry.getTitle());
        descriptionEditText.setText(journalEntry.getDescription());
    }

    public void setJournalEntry(JournalEntry journalEntry) {
        this.journalId = journalEntry.getId();
    }

}
