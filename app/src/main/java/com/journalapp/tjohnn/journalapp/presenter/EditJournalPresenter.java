package com.journalapp.tjohnn.journalapp.presenter;

import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.contracts.EditJournalContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.model.repository.JournalRepository;
import com.journalapp.tjohnn.journalapp.utils.AppDi;
import com.journalapp.tjohnn.journalapp.utils.AppExecutors;
import com.journalapp.tjohnn.journalapp.utils.Constants;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tjohn on 6/30/18.
 */

public class EditJournalPresenter implements EditJournalContract.Presenter {


    private EditJournalContract.View mView;
    private JournalRepository journalRepository;
    private PreferencesHelper preferencesHelper;
    private CompositeDisposable disposeBag;

    public EditJournalPresenter(EditJournalContract.View mView) {
        this(mView, AppDi.journalRepository(), AppDi.getPreferencesHelper());
    }

    EditJournalPresenter(EditJournalContract.View mView, JournalRepository journalRepository,
                         PreferencesHelper preferencesHelper) {
        this.mView = mView;
        this.journalRepository = journalRepository;
        this.preferencesHelper = preferencesHelper;

        disposeBag = new CompositeDisposable();
    }


    @Override
    public void updateJournal(int journalId) {
        String title = mView.getJournalTitle();
        String description = mView.getJournalDescription();
        boolean goodTogo = true;

        if(title == null || title.isEmpty()){
            mView.setTitleError(R.string.journal_title_required);
            goodTogo = false;
        }
        if(description == null || description.isEmpty()){
            mView.setDescriptionError(R.string.journal_description_required);
            goodTogo = false;
        }
        if(!goodTogo) return;
        Date updatedAt = new Date(System.currentTimeMillis());

        String gid = preferencesHelper.getStringPreference(Constants.USER_ID_PREF_KEY);

        journalRepository.updateJournal(new JournalEntry(journalId, title, description, updatedAt, gid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mView.closeEditFragment();
                    mView.showSuccessMessage();
                }, throwable -> {

                });

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
    public void unSubscribe() {
        disposeBag.clear();
    }

}
