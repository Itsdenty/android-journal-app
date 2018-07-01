package com.journalapp.tjohnn.journalapp.presenter;

import com.journalapp.tjohnn.journalapp.contracts.DashboardContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.model.repository.JournalRepository;
import com.journalapp.tjohnn.journalapp.utils.AppDi;
import com.journalapp.tjohnn.journalapp.utils.Constants;
import com.journalapp.tjohnn.journalapp.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tjohn on 6/29/18.
 */

public class DashboardPresenter implements DashboardContract.Presenter {


    private DashboardContract.View mView;
    private JournalRepository journalRepository;
    private PreferencesHelper preferencesHelper;
    private CompositeDisposable disposeBag;
    private List<JournalEntry> journalEntries, dataHolder;

    public DashboardPresenter(DashboardContract.View mView) {
        this(mView, AppDi.journalRepository(), AppDi.getPreferencesHelper());
    }

    DashboardPresenter(DashboardContract.View mView, JournalRepository journalRepository, PreferencesHelper preferencesHelper) {
        this.mView = mView;
        this.journalRepository = journalRepository;
        this.preferencesHelper = preferencesHelper;

        disposeBag = new CompositeDisposable();
    }

    @Override
    public void loadAllJournals() {

        Disposable disposable = journalRepository.loadJournals(preferencesHelper.getStringPreference(Constants.USER_ID_PREF_KEY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleReturnedData, this::handleError, () -> mView.showProgressBar(false));
        disposeBag.add(disposable);
    }

    private void handleError(Throwable throwable) {
    }

    private void handleReturnedData(List<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
        this.dataHolder = new ArrayList<>(journalEntries);
        mView.populateJournalList(journalEntries);
    }

    @Override
    public void loadUserData() {
        mView.setEmail(preferencesHelper.getStringPreference(Constants.USER_EMAIL_PREF_KEY));
        mView.setDisplayName(preferencesHelper.getStringPreference(Constants.USER_NAME_PREF_KEY));
        mView.loadProfilePicture(preferencesHelper.getStringPreference(Constants.PICTURE_URL_PREF_KEY));
    }

    @Override
    public void deleteJournal(JournalEntry journalEntry, int position) {
       journalRepository.deleteJournal(journalEntry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    journalEntries.remove(position);
                    dataHolder = journalEntries;
                    mView.upDateJournalList(journalEntries);
                }, error -> {

                });

    }

    public void filterByDate(Calendar filterDate) {
        if(filterDate == null){
            journalEntries = new ArrayList<>(dataHolder);
        }
        else {
            Date realDate = filterDate.getTime();
            Calendar calendarPlus = Calendar.getInstance();
            calendarPlus.setTime(filterDate.getTime());
            calendarPlus.add(Calendar.DATE, 1);
            Date realDatePlus = calendarPlus.getTime();

            journalEntries.clear();
            Utils.logD(dataHolder.size() + "");
            for (JournalEntry j:
                    dataHolder) {
                if(j.getUpdatedAt().compareTo(realDate) >= 0 && j.getUpdatedAt().compareTo(realDatePlus) < 0){
                    journalEntries.add(j);
                }
            }
        }
        mView.upDateJournalList(journalEntries);
    }

    @Override
    public void unSubscribe() {
        disposeBag.clear();
    }
}
