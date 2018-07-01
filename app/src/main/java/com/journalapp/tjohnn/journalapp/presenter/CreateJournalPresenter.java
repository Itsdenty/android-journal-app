package com.journalapp.tjohnn.journalapp.presenter;

import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.contracts.CreateJournalContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.model.repository.JournalRepository;
import com.journalapp.tjohnn.journalapp.utils.AppDi;
import com.journalapp.tjohnn.journalapp.utils.Constants;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tjohn on 6/30/18.
 */

public class CreateJournalPresenter implements CreateJournalContract.Presenter {


    private CreateJournalContract.View mView;
    private JournalRepository journalRepository;
    private PreferencesHelper preferencesHelper;
    private CompositeDisposable disposeBag;

    public CreateJournalPresenter(CreateJournalContract.View mView) {
        this(mView, AppDi.journalRepository(), AppDi.getPreferencesHelper());
    }

    CreateJournalPresenter(CreateJournalContract.View mView, JournalRepository journalRepository,
                           PreferencesHelper preferencesHelper) {
        this.mView = mView;
        this.journalRepository = journalRepository;
        this.preferencesHelper = preferencesHelper;

        disposeBag = new CompositeDisposable();
    }


    @Override
    public void createJournal() {
        String title = mView.getJournalTitle();
        String description = mView.getJournalDescription();
        boolean goodTogo = true;

        if(title == null || title.isEmpty()){
            mView.setTitleError(R.string.journal_title_required);
            goodTogo = false;
        }
        if(description == null || title.isEmpty()){
            mView.setDescriptionError(R.string.journal_description_required);
            goodTogo = false;
        }
        if(!goodTogo) return;
        Date createdAt = new Date(System.currentTimeMillis());
        String gid = preferencesHelper.getStringPreference(Constants.USER_ID_PREF_KEY);
        journalRepository.addJournal(new JournalEntry(title, description, createdAt, gid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mView.clearFormFields();
                    mView.showSuccessMessage();
                }, throwable -> {

                });

    }

    @Override
    public void unSubscribe() {
        disposeBag.clear();
    }
}
