package com.alvindizon.basejavaapp.data.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alvindizon.basejavaapp.data.db.model.SampleDbRecord;

import java.util.List;

import io.reactivex.Completable;

// TODO add SQL queries as needed
@Dao
public abstract class SampleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(SampleDbRecord binRecord);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract List<Long> insert(List<SampleDbRecord> binRecordList);

    @Query("DELETE FROM sampleDb")
    public abstract Completable clearDb();

    @Query("SELECT * FROM sampleDb")
    public abstract List<SampleDbRecord> getAllRecords();

    @Query("DELETE FROM sampleDb WHERE id like :id")
    public abstract void deleteRecordById(int id);

    @Query("SELECT * FROM sampleDb WHERE name like :name")
    public abstract SampleDbRecord getRecordByName(String name);

}
