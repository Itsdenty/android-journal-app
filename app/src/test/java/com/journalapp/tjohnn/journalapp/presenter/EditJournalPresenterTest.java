package com.journalapp.tjohnn.journalapp.presenter;

import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.contracts.CreateJournalContract;
import com.journalapp.tjohnn.journalapp.contracts.EditJournalContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.model.repository.JournalRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tjohn on 7/1/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class EditJournalPresenterTest {

    @Mock
    private EditJournalContract.View view;

    @Mock
    private PreferencesHelper preferencesHelper;

    @Mock
    private JournalRepository repository;


    private EditJournalPresenter presenter;



    @Before
    public void setUp() throws Exception {
        presenter = new EditJournalPresenter(view, repository, preferencesHelper);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }


    @Test
    public void updateJournal_shouldCloseEditorAndShowSuccess() throws Exception {
        when(view.getJournalTitle()).thenReturn("Title");
        when(view.getJournalDescription()).thenReturn("Desc");
        when(repository.updateJournal(any(JournalEntry.class))).thenReturn(Completable.complete());
        presenter.updateJournal(2);
        verify(view).closeEditFragment();
        verify(view).showSuccessMessage();
    }



}