package com.journalapp.tjohnn.journalapp.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.journalapp.tjohnn.journalapp.MainView;
import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.adapters.JournalListAdapter;
import com.journalapp.tjohnn.journalapp.contracts.DashboardContract;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.presenter.DashboardPresenter;
import com.journalapp.tjohnn.journalapp.utils.DownloadPictureTask;
import com.journalapp.tjohnn.journalapp.utils.Utils;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tjohn on 6/29/18.
 */

public class DashboardFragment extends Fragment implements DashboardContract.View, JournalListAdapter.JournalItemListener {


    public static final String TAG = "DashboardFragmentTag";
    private DashboardContract.Presenter mPresenter;
    private AppCompatActivity mActivity;
    private MainView mainView;
    private FragmentManager fragmentManager;
    private String FILTER_DATE_KEY = "FILTER_DATE_KEY";
    private boolean isFilteringDate;

    RecyclerView recyclerView;
    ImageView imageView;
    TextView nameTextView;
    TextView emailTextView;
    FloatingActionButton fab;
    private JournalListAdapter journalListAdapter;
    private Calendar filterDate = Calendar.getInstance(Locale.ENGLISH);

    DatePickerDialog datePickerDialog;
    DatePickerDialog.OnDateSetListener dateListener = (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
        filterDate.set(Calendar.YEAR, year);
        filterDate.set(Calendar.MONTH, monthOfYear);
        filterDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        filterDate.set(Calendar.HOUR_OF_DAY, 0);
        filterDate.set(Calendar.MINUTE, 0);
        filterDate.set(Calendar.SECOND, 0);
        filterDate.set(Calendar.MILLISECOND, 0);
        isFilteringDate = true;
        mPresenter.filterByDate(filterDate);
    };



    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            long time = savedInstanceState.getLong(FILTER_DATE_KEY);
            if(time > 0) {
                isFilteringDate = true;
                filterDate.setTimeInMillis(time);
            }
        }
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerView = view.findViewById(R.id.rv_journals);
        imageView = view.findViewById(R.id.iv_profile_picture);
        nameTextView = view.findViewById(R.id.tv_display_name);
        emailTextView = view.findViewById(R.id.tv_email);
        fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(e -> addJournal());

        mPresenter = new DashboardPresenter(this);
        mActivity = (AppCompatActivity) getActivity();
        mainView = (MainView) mActivity;


        return  view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dashboard_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.filter_journals_option_item){
            datePickerDialog = new DatePickerDialog(mActivity, dateListener, filterDate.get(Calendar.YEAR),
                    filterDate.get(Calendar.MONTH), filterDate.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Clear Filter", (dialog, which) -> {
                // No more filtering by date
                isFilteringDate = false;
                mPresenter.filterByDate(null);
                mActivity.setTitle(getString(R.string.diary));
            });
            datePickerDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addJournal(){
        Utils.addFragment(mActivity.getSupportFragmentManager(),
                CreateJournalFragment.newInstance(), CreateJournalFragment.TAG, R.id.content_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainView.displayUpNavigation(false);
        mPresenter.loadAllJournals();
        mPresenter.loadUserData();
        fragmentManager = mActivity.getSupportFragmentManager();
        mActivity.setTitle(getString(R.string.diary));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // save filter date only if list is being filtered by date
        if(isFilteringDate) outState.putLong(FILTER_DATE_KEY, filterDate.getTimeInMillis());
        super.onSaveInstanceState(outState);
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
    public void upDateJournalList(List<JournalEntry> journalEntries){
        journalListAdapter.updateItems(journalEntries);
        if(isFilteringDate)mActivity.setTitle(getString(R.string.diary) + "(" + String.format("%1$td %1$tb %1$tY", filterDate) + ")");
    }

    @Override
    public void populateJournalList(List<JournalEntry> journalEntries) {
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setHasFixedSize(true);
        journalListAdapter = new JournalListAdapter(journalEntries, this);
        recyclerView.setAdapter(journalListAdapter);

        //handle filtering for orientation change
        if(isFilteringDate) mPresenter.filterByDate(filterDate);
    }

    @Override
    public void loadProfilePicture(String url) {
        if(url != null && !url.isEmpty()){
            DownloadPictureTask downloadPictureTask = new DownloadPictureTask(imageView);
            downloadPictureTask.execute(url);

        }
    }

    @Override
    public void setDisplayName(String displayName) {
        nameTextView.setText(displayName);
    }

    @Override
    public void setEmail(String email) {
        emailTextView.setText(email);
    }

    @Override
    public void onJournalItemClicked(JournalEntry journalEntry) {
        JournalDetailFragment detailFragment  = JournalDetailFragment.newInstance();
        detailFragment.setJournalEntry(journalEntry);
        Utils.addFragment(fragmentManager, detailFragment, JournalDetailFragment.TAG, R.id.content_main);
    }

    @Override
    public void onEditJournalClicked(JournalEntry journalEntry) {
        EditJournalFragment editJournalFragment  = EditJournalFragment.newInstance();
        editJournalFragment.setJournalEntry(journalEntry);
        Utils.addFragment(fragmentManager, editJournalFragment, EditJournalFragment.TAG, R.id.content_main);
    }

    @Override
    public void onDeleteJournalClicked(JournalEntry journalEntry, int position) {
        mPresenter.deleteJournal(journalEntry, position);
    }


}
