package com.alvindizon.basejavaapp.di.module;

import android.content.Context;

import androidx.room.Room;

import com.alvindizon.basejavaapp.data.db.SampleDao;
import com.alvindizon.basejavaapp.data.db.SampleDb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Provides
    @Singleton
    SampleDb provideSampleDb(Context appContext) {
        return Room.databaseBuilder(appContext, SampleDb.class, "basejavaapp")
                .build();
    }

    @Provides
    @Singleton
    SampleDao provideSampleDao(SampleDb sampleDb) {
        return sampleDb.sampleDao();
    }
}
