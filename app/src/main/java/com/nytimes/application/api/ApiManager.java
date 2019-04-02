package com.nytimes.application.api;

import android.content.Context;

import com.nytimes.application.data.DataManager;
import com.nytimes.application.data.entity.NewsEntity;
import com.nytimes.application.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.nytimes.application.api.NyClient.getClient;

public class ApiManager {

    private final Retrofit client;
    private final String apiKey = "Ag3kGAyLkdSHT1hyi5dsR7DmmXiUrL6W";
    private final DataManager dataManager;
    private final Context context;

    public ApiManager(Context context) {
        client = getClient();
        this.context = context;
        dataManager = new DataManager(context);
    }


    public void getArticles(final String period, final ApiCallback<List<Result>> callback) {
        if (Utils.isNetworkConnected(context)) {
            NyInterface services = client.create(NyInterface.class);
            services.getAllArticles(period, apiKey).enqueue(new Callback<Article>() {
                @Override
                public void onResponse(Call<Article> call, Response<Article> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.success(response.body().getResults());
                        insertNewsData(response.body());
                    } else {
                        callback.failure(response.code());
                    }
                }

                @Override
                public void onFailure(Call<Article> call, Throwable t) {
                    callback.failure(500);
                }
            });
        } else {
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    List<Result> articles = new ArrayList<>();
                    for (NewsEntity newsEntity : dataManager.getNewsData()) {
                        articles.add(Json.gson().fromJson(newsEntity.getNewsData(), Result.class));
                    }
                    callback.success(articles);
                }
            });
        }
    }

    private void insertNewsData(final Article body) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                dataManager.deleteAll();
                for (Result result : body.getResults()) {
                    dataManager.insert(new NewsEntity(Json.gson().toJson(result, Result.class)));
                }
            }
        });
    }


    public interface ApiCallback<T> {
        void success(T response);

        void failure(int responseCode);
    }
}


