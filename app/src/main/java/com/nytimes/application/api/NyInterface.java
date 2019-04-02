package com.nytimes.application.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NyInterface {

    @GET("svc/mostpopular/v2/mostviewed/all-sections/{period}.json?}")
    Call<Article> getAllArticles(@Path("period") String period, @Query(value = "api-key", encoded = true) String apiKey);
}
