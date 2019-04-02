package com.nytimes.application;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nytimes.application.api.Json;
import com.nytimes.application.api.Result;

import com.nytimes.application.data.DataManager;
import com.nytimes.application.data.entity.NewsEntity;
import com.nytimes.application.databinding.DetailsBinding;
import com.nytimes.application.eventbus.DeleteNewsEvent;
import com.nytimes.application.util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executors;

public class DetailsActivity extends AppCompatActivity {

    private Result result;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.details);

        setUpActionBar();

        ImageView ivImage = findViewById(R.id.iv_article_image);
        String article = getIntent().getStringExtra("article");
        position  = getIntent().getIntExtra("position", -1);
        result = Json.gson().fromJson(article, Result.class);
        Log.e(DetailsActivity.class.getName(), result.getTitle());
        binding.setArticle(result);

        try {
            Glide.with(getApplicationContext()).load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(ivImage);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void setUpActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                deleteNewsItem();
                finish();
                return true;

            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void deleteNewsItem() {

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                DataManager dataManager = new DataManager(getApplicationContext());
                dataManager.delete(new NewsEntity(Json.gson().toJson(result, Result.class)));
            }
        });

        EventBus.getDefault().post(new DeleteNewsEvent(position));
    }
}
