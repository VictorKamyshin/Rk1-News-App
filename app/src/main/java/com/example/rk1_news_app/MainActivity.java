package com.example.rk1_news_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Scheduler;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION_GET_NEWS = "action.GET_NEWS";

    private final static String TAG = MainActivity.class.getSimpleName();

    private static boolean backgroundUpdate = false;

    private String topic;

    private BroadcastReceiver broadcastReceiver;

    private final static long updateTime = 5_000L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_pick_theme).setOnClickListener(onThemeClick);
        findViewById(R.id.btn_start_service).setOnClickListener(backgroundUpdateTurnOn);
        findViewById(R.id.btn_stop_service).setOnClickListener(backgroundUpdateTurnOff);
        findViewById(R.id.btn_refresh_news).setOnClickListener(refreshNews);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                printNews();
            }
        };
        IntentFilter intentFilter = new IntentFilter(ACTION_GET_NEWS);
        registerReceiver(broadcastReceiver, intentFilter);
        topic = Storage.getInstance(this).loadCurrentTopic();
        loadNews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!topic.equals(Storage.getInstance(this).loadCurrentTopic())){
            printTopic();
            topic = Storage.getInstance(this).loadCurrentTopic();
            loadNews();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onStop");
        if(broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private void printTopic(){
        String topic = Storage.getInstance(this).loadCurrentTopic();
        if(topic!=null) {
            ((Button) findViewById(R.id.btn_pick_theme)).setText(topic);
        }
    }

    private void printNews(){
        News news = Storage.getInstance(this).getLastSavedNews();

        ((TextView) findViewById(R.id.text_news_title)).setText(news.getTitle());
        ((TextView) findViewById(R.id.text_news_text)).setText(news.getBody());
        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(news.getDate()));
        ((TextView) findViewById(R.id.text_news_date)).setText(dateString);
        printTopic();
    }

    private void loadNews(){
        Intent intent = new Intent(MainActivity.this, NewsIntentService.class);
        startService(intent);
    }

    private void switchScheduler(boolean newState){
        if(MainActivity.backgroundUpdate != newState) {
            MainActivity.backgroundUpdate = newState;
            Scheduler scheduler = Scheduler.getInstance();
            Intent intent = new Intent(MainActivity.this, NewsIntentService.class);

            if (newState) {
                scheduler.schedule(this, intent, updateTime);
            } else {
                scheduler.unschedule(this, intent);
            }
        }
    }


    private final View.OnClickListener onThemeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        }
    };

    private final View.OnClickListener backgroundUpdateTurnOn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switchScheduler(true);
        }
    };

    private final View.OnClickListener backgroundUpdateTurnOff = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switchScheduler(false);
        }
    };

    private final View.OnClickListener refreshNews = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, NewsIntentService.class);
            startService(intent);

        }
    };
}
