package com.journalapp.tjohnn.journalapp.presenter;

import com.journalapp.tjohnn.journalapp.contracts.JournalDetailContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.model.repository.JournalRepository;
import com.journalapp.tjohnn.journalapp.utils.AppDi;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tjohn on 6/29/18.
 */

public class JournalDetailPresenter implements JournalDetailContract.Presenter {


    private JournalDetailContract.View mView;
    private JournalRepository journalRepository;
    private PreferencesHelper preferencesHelper;
    private CompositeDisposable disposeBag;

    public JournalDetailPresenter(JournalDetailContract.View mView) {
        this(mView, AppDi.journalRepository(), AppDi.getPreferencesHelper());
    }

    JournalDetailPresenter(JournalDetailContract.View mView, JournalRepository journalRepository, PreferencesHelper preferencesHelper) {
        this.mView = mView;
        this.journalRepository = journalRepository;
        this.preferencesHelper = preferencesHelper;

        disposeBag = new CompositeDisposable();
    }


    @Override
    public void deleteJournal(JournalEntry journalEntry) {
        journalRepository.deleteJournal(journalEntry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> mView.journalDeleted(), error -> {});
    }

    @Override
    public void loadJournalDetail(int id) {

        Disposable disposable = journalRepository.getJournalById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleReturnedData, this::handleError, () -> mView.showProgressBar(false));
        disposeBag.add(disposable);
    }

    private void handleError(Throwable throwable) {

    }

    private void handleReturnedData(JournalEntry journalEntry) {
        mView.populateJournalDetail(journalEntry);
    }

    @Override
    public void unSubscribe(){
        disposeBag.clear();
    }

}
