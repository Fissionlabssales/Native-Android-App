package com.nytimes.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nytimes.application.adapter.ArticlesAdapter;
import com.nytimes.application.api.ApiManager;
import com.nytimes.application.api.Article;
import com.nytimes.application.api.Json;
import com.nytimes.application.api.Result;
import com.nytimes.application.eventbus.DeleteNewsEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ArticlesAdapter.OnArticleItemClickListener {


    private RecyclerView rvNews;
    private List<Result> articleList = new ArrayList<>();
    private ArticlesAdapter articlesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNews = findViewById(R.id.rvNews);
        rvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        articlesAdapter = new ArticlesAdapter(getApplicationContext(), articleList, this);
        rvNews.setAdapter(articlesAdapter);

        EventBus.getDefault().register(this);

        renderNewsData();
    }

    private void renderNewsData() {
        ApiManager apiManager = new ApiManager(getApplicationContext());
        apiManager.getArticles("7", new ApiManager.ApiCallback<List<Result>>() {
            @Override
            public void success(final List<Result> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        articleList.clear();
                        articleList.addAll(response);
                        articlesAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void failure(int responseCode) {

            }
        });
    }

    @Subscribe
    public void deleteNews(DeleteNewsEvent deleteNewsEvent) {
        if (deleteNewsEvent.getPosition() != -1) {
            articlesAdapter.notifyDeleteItem(deleteNewsEvent);
        }
    }


    @Override
    public void onItemClick(Result article, int position) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("article", Json.gson().toJson(article, Result.class));
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
