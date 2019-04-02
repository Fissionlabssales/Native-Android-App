package com.nytimes.application.data;

import android.content.Context;

import com.nytimes.application.data.dao.NewsDao;
import com.nytimes.application.data.entity.NewsEntity;

import java.util.List;

public class DataManager implements NewsDao {
    private NyAppDatabase nyAppDatabase;

    public DataManager(Context context) {
        nyAppDatabase = NyAppDatabase.getAppDatabase(context, DataManager.class);
    }


    @Override
    public void insert(NewsEntity data) {
        nyAppDatabase.getNewsDao().insert(data);
    }

    @Override
    public List<NewsEntity> getNewsData() {
        return nyAppDatabase.getNewsDao().getNewsData();
    }

    @Override
    public void delete(NewsEntity newsData) {
        nyAppDatabase.getNewsDao().delete(newsData);
    }

    @Override
    public void deleteAll() {
        nyAppDatabase.getNewsDao().deleteAll();
    }
}