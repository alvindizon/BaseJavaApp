package com.alvindizon.basejavaapp.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alvindizon.basejavaapp.data.db.model.SampleDbRecord;

// TODO rename as needed
@Database(entities = {SampleDbRecord.class}, version = 1)
public abstract class SampleDb extends RoomDatabase {
    public abstract SampleDao sampleDao();
}
