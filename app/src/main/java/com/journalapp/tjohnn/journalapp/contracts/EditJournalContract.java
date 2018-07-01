package com.journalapp.tjohnn.journalapp.contracts;

import com.journalapp.tjohnn.journalapp.BasePresenter;
import com.journalapp.tjohnn.journalapp.BaseView;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;

/**
 * Created by Tjohn on 6/30/18.
 */

public interface EditJournalContract {

    interface View extends BaseView{
        String getJournalTitle();
        String getJournalDescription();

        void setTitleError(int stringResourceId);
        void setDescriptionError(int stringResourceId);

        void closeEditFragment();

        void showSuccessMessage();

        void populateJournalDetail(JournalEntry journalEntry);
    }

    interface Presenter extends BasePresenter{
        void updateJournal(int journalId);

        void loadJournalDetail(int journalId);
    }

}
