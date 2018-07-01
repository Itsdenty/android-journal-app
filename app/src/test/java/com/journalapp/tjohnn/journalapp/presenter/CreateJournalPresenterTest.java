package com.journalapp.tjohnn.journalapp.presenter;

import com.journalapp.tjohnn.journalapp.R;
import com.journalapp.tjohnn.journalapp.contracts.CreateJournalContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.model.repository.JournalRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tjohn on 7/1/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateJournalPresenterTest {

    @Mock
    private CreateJournalContract.View view;

    @Mock
    private PreferencesHelper preferencesHelper;

    @Mock
    private JournalRepository repository;


    private CreateJournalPresenter presenter;



    @Before
    public void setUp() throws Exception {
        presenter = new CreateJournalPresenter(view, repository, preferencesHelper);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void createJournal_shouldShowTitleAndDescriptionError() throws Exception {
        when(view.getJournalTitle()).thenReturn(null);
        when(view.getJournalDescription()).thenReturn(null);
        presenter.createJournal();
        verify(view).setTitleError(R.string.journal_title_required);
        verify(view).setDescriptionError(R.string.journal_description_required);
    }

    @Test
    public void createJournal_shouldClearFormFieldsAndShowSuccess() throws Exception {
        when(view.getJournalTitle()).thenReturn("Title");
        when(view.getJournalDescription()).thenReturn("Desc");
        when(repository.addJournal(any(JournalEntry.class))).thenReturn(Completable.complete());
        presenter.createJournal();
        verify(view).clearFormFields();
        verify(view).showSuccessMessage();
    }

}