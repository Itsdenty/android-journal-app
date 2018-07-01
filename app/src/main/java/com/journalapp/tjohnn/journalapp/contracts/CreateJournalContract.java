package com.journalapp.tjohnn.journalapp.contracts;

import com.journalapp.tjohnn.journalapp.BasePresenter;
import com.journalapp.tjohnn.journalapp.BaseView;

/**
 * Created by Tjohn on 6/30/18.
 */

public interface CreateJournalContract {

    interface View extends BaseView{
        String getJournalTitle();
        String getJournalDescription();

        void setTitleError(int stringResourceId);
        void setDescriptionError(int stringResourceId);

        void clearFormFields();

        void showSuccessMessage();
    }

    interface Presenter extends BasePresenter{
        void createJournal();
    }

}
