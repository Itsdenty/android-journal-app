package com.journalapp.tjohnn.journalapp.contracts;

import com.journalapp.tjohnn.journalapp.BasePresenter;
import com.journalapp.tjohnn.journalapp.BaseView;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;

/**
 * Created by Tjohn on 6/30/18.
 */

public interface JournalDetailContract {

    interface View extends BaseView{
        void populateJournalDetail(JournalEntry journalEntry);
        void journalDeleted();
    }

    interface Presenter extends BasePresenter{
        void deleteJournal(JournalEntry journalEntry);
        void loadJournalDetail(int id);
    }

}
