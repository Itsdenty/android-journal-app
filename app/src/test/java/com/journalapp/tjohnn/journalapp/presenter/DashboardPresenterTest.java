package com.journalapp.tjohnn.journalapp.presenter;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.journalapp.tjohnn.journalapp.contracts.DashboardContract;
import com.journalapp.tjohnn.journalapp.contracts.LoginContract;
import com.journalapp.tjohnn.journalapp.model.PreferencesHelper;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;
import com.journalapp.tjohnn.journalapp.model.repository.JournalRepository;
import com.journalapp.tjohnn.journalapp.utils.AppDi;
import com.journalapp.tjohnn.journalapp.utils.AppExecutors;
import com.journalapp.tjohnn.journalapp.utils.Constants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tjohn on 7/1/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class DashboardPresenterTest {

    @Mock
    private DashboardContract.View view;

    @Mock
    private PreferencesHelper preferencesHelper;

    @Mock
    private JournalRepository repository;


    private DashboardPresenter presenter;
    private List<JournalEntry> journalEntries;



    @Before
    public void setUp() throws Exception {
        presenter = new DashboardPresenter(view, repository, preferencesHelper);
        journalEntries = Arrays.asList(
                new JournalEntry("Title",  "Description", new Date(), "90983"),
                new JournalEntry("Title2",  "Description2", new Date(), "90983"),
                new JournalEntry("Title2",  "Description2", new Date(), "90983")
        );
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void loadAllJournals_shouldPopulateJournalList() throws Exception {

        when(repository.loadJournals(any())).thenReturn(Flowable.fromArray(journalEntries));
        presenter.loadAllJournals();
        verify(view, atLeast(1)).populateJournalList(journalEntries);
    }

    @Test
    public void loadUserData_shouldSetUserDataToView() throws Exception {
        String name = "Tosin John";
        String email = "john@gmail.com";
        when(preferencesHelper.getStringPreference(Constants.USER_NAME_PREF_KEY)).thenReturn(name);
        when(preferencesHelper.getStringPreference(Constants.USER_EMAIL_PREF_KEY)).thenReturn(email);
        presenter.loadUserData();
        verify(view).setDisplayName(name);
        verify(view).setEmail(email);
    }


}