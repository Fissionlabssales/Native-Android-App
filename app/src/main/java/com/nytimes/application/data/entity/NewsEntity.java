package com.nytimes.application.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ny_popular_news")
public class NewsEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "news_data")
    private String newsData;


    public NewsEntity(String newsData) {
        this.newsData = newsData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewsData() {
        return newsData;
    }

    public void setNewsData(String newsData) {
        this.newsData = newsData;
    }
}
