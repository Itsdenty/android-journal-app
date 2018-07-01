package com.journalapp.tjohnn.journalapp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.journalapp.tjohnn.journalapp.MainView;
import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.contracts.JournalDetailContract;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.presenter.JournalDetailPresenter;
import com.journalapp.tjohnn.journalapp.utils.Utils;

/**
 * Created by Tjohn on 6/30/18.
 */

public class JournalDetailFragment extends Fragment implements JournalDetailContract.View {

    public static final String TAG = "JournalDetailFragmentTag";
    public static final String JOURNAL_ID_KEY = "JournalId";
    private JournalDetailContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    private MainView mainView;
    private int journalId;
    private JournalEntry journalEntry;

    private TextView titleTextView;
    private TextView dateTextView;
    private TextView descriptionTextView;


    public JournalDetailFragment(){}

    public static JournalDetailFragment newInstance() {
        return new JournalDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        if(savedInstanceState == null && journalId == 0){
            throw new RuntimeException("Journal entry must be set on JournalDetailFragment fragment");
        }
        else if(savedInstanceState != null){
            journalId = savedInstanceState.getInt(JOURNAL_ID_KEY);
        }
        mPresenter = new JournalDetailPresenter(this);
        mActivity = (AppCompatActivity) getActivity();
        mainView = (MainView) mActivity;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.detail_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.delete_journal_menu_item){
            mPresenter.deleteJournal(journalEntry);
        }
        else if(id == R.id.edit_journal_menu_item){
            EditJournalFragment editJournalFragment  = EditJournalFragment.newInstance();
            editJournalFragment.setJournalEntry(journalEntry);
            Utils.addFragment(mActivity.getSupportFragmentManager(), editJournalFragment, EditJournalFragment.TAG, R.id.content_main);
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joutnal_detail, container, false);

        titleTextView = view.findViewById(R.id.tv_title);
        dateTextView = view.findViewById(R.id.tv_date);
        descriptionTextView = view.findViewById(R.id.tv_description);

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setTitle(R.string.journal_detail);
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
    public void showProgressBar(boolean show) {
        mainView.showProgressBar(show);
    }

    @Override
    public void populateJournalDetail(JournalEntry journalEntry) {
        titleTextView.setText(journalEntry.getTitle());
        dateTextView.setText(String.format("%1$td %1$tb, %1$tY %1$tH:%1$tM", journalEntry.getUpdatedAt()));
        descriptionTextView.setText(journalEntry.getDescription());
    }

    @Override
    public void journalDeleted() {
        Snackbar.make(mActivity.findViewById(android.R.id.content), R.string.journal_deleted, Snackbar.LENGTH_LONG).show();
        mActivity.getSupportFragmentManager().popBackStackImmediate();
    }

    public void setJournalEntry(JournalEntry journalEntry) {
        this.journalEntry = journalEntry;
        this.journalId = journalEntry.getId();
    }
}
