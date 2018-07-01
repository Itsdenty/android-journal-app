package com.journalapp.tjohnn.journalapp.presenter;

import com.journalapp.tjohnn.journalapp.contracts.JournalDetailContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.model.repository.JournalRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tjohn on 7/1/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class JournalDetailPresenterTest {

    @Mock
    private JournalDetailContract.View view;

    @Mock
    private PreferencesHelper preferencesHelper;

    @Mock
    private JournalRepository repository;


    private JournalDetailPresenter presenter;
    private List<JournalEntry> journalEntries;

    @Before
    public void setUp() throws Exception {
        presenter = new JournalDetailPresenter(view, repository, preferencesHelper);
        journalEntries = Arrays.asList(
                new JournalEntry("Title",  "Description", new Date(), "90983"),
                new JournalEntry("Title2",  "Description2", new Date(), "90983")
        );
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }


    @Test
    public void loadJournalDetail_shouldPopulateView() throws Exception {
        when(repository.getJournalById(1)).thenReturn(Flowable.fromArray(journalEntries.get(0)));
        presenter.loadJournalDetail(1);
        verify(view).populateJournalDetail(journalEntries.get(0));

    }

}