package com.journalapp.tjohnn.journalapp.contracts;

import com.journalapp.tjohnn.journalapp.BasePresenter;
import com.journalapp.tjohnn.journalapp.BaseView;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Tjohn on 6/29/18.
 */

public interface DashboardContract {



    interface View extends BaseView {
        void populateJournalList(List<JournalEntry> journalEntries);
        void loadProfilePicture(String url);
        void setDisplayName(String displayName);
        void setEmail(String email);
        void upDateJournalList(List<JournalEntry> journalEntries);
    }

    interface  Presenter extends BasePresenter{
        void loadAllJournals();

        void loadUserData();

        void deleteJournal(JournalEntry journalEntry, int position);

        void filterByDate(Calendar filterDate);
    }
}
