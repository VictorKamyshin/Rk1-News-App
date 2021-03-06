package com.example.rk1_news_app;

import android.app.IntentService;
import android.content.Intent;

import java.io.IOException;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.NewsLoader;
import ru.mail.weather.lib.Storage;


public class NewsIntentService extends IntentService {

    public NewsIntentService() {
        super("WeatherIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                String currentTopic = Storage.getInstance(getApplicationContext()).loadCurrentTopic();
                NewsLoader newsLoader =new NewsLoader();
                News news = newsLoader.loadNews(currentTopic);
                Storage.getInstance(getApplicationContext()).saveNews(news);
                Intent resultIntent = new Intent(MainActivity.ACTION_GET_NEWS);
                sendBroadcast(resultIntent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
