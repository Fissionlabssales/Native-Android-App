package com.nytimes.application.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {

    private static final Gson gson = new GsonBuilder()
            .enableComplexMapKeySerialization()
            .create();

    public static Gson gson() {
        return gson;
    }
}