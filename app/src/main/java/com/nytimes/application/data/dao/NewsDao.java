package com.nytimes.application.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.nytimes.application.data.entity.NewsEntity;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert
    void insert(NewsEntity data);

    @Query("select * from ny_popular_news")
    List<NewsEntity> getNewsData();

    @Delete
    void delete(NewsEntity newsDao);

    @Query("DELETE FROM ny_popular_news")
    void deleteAll();

}
