package com.nytimes.application;

import com.nytimes.application.api.ApiManager;
import com.nytimes.application.api.Article;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ApiTest {

    private CountDownLatch countDownLatch;


    @Test
    public void testAPI() throws InterruptedException {
        countDownLatch = new CountDownLatch(1);
        ApiManager apiManager = new ApiManager();
        apiManager.getArticles("7", new ApiManager.ApiCallback<Article>() {
            @Override
            public void success(Article response) {
                if (countDownLatch != null)
                    countDownLatch.countDown();

                assertNotNull(response);
                assertEquals(response.getStatus(), "OK");
            }

            @Override
            public void failure(int responseCode) {
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }
        });

        countDownLatch.await();

    }

}
