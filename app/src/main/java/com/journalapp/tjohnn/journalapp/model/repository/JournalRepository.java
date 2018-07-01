package com.journalapp.tjohnn.journalapp.model.repository;

import com.journalapp.tjohnn.journalapp.model.database.JournalDao;
import com.journalapp.tjohnn.journalapp.model.database.JournalEntry;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Created by Tjohn on 6/24/18.
 */

public class JournalRepository {

    private JournalDao journalDao;


    public JournalRepository(JournalDao journalDao) {
        this.journalDao = journalDao;
    }

    public Flowable<List<JournalEntry>> loadJournals(String googleId) {
        return journalDao.loadAllJournals(googleId);
    }

    public Completable addJournal(JournalEntry journalEntry) {
        return Completable.fromAction(() -> journalDao.insertJournal(journalEntry));
    }

    public Flowable<JournalEntry> getJournalById(int journalId) {
        return journalDao.getJournalById(journalId);
    }

    public Completable updateJournal(JournalEntry journalEntry) {
        return Completable.fromAction(() -> journalDao.updateJournal(journalEntry));
    }

    public Completable deleteJournal(JournalEntry journalEntry) {
        return Completable.fromAction(() -> journalDao.deleteJournal(journalEntry));
    }

}
