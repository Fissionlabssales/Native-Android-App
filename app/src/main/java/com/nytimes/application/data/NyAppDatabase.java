package com.nytimes.application.data;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.nytimes.application.data.dao.NewsDao;
import com.nytimes.application.data.entity.NewsEntity;


@android.arch.persistence.room.Database(entities = {NewsEntity.class}, version = 1, exportSchema = false)
public abstract class NyAppDatabase extends RoomDatabase {

    private static NyAppDatabase INSTANCE;


    public static synchronized NyAppDatabase getAppDatabase(Context context, Class clazz) {



        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, NyAppDatabase.class, "ny_news")
                    .build();
        }
        return INSTANCE;
    }


    public abstract NewsDao getNewsDao();
}
