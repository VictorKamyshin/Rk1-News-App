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

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION_GET_NEWS = "action.GET_NEWS";

    private final static String TAG = MainActivity.class.getSimpleName();

    private final static boolean backgroundUpdate = false;

    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_pick_theme).setOnClickListener(onThemeClick);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                printNews();
            }
        };
        IntentFilter intentFilter = new IntentFilter(ACTION_GET_NEWS);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, Storage.getInstance(this).loadCurrentTopic());
        printTopic();
        loadNews();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
       // ((TextView) findViewById(R.id.text_news_date)).setText(news.getDate());
        printTopic();
    }

    private void loadNews(){
        Intent intent = new Intent(MainActivity.this, NewsIntentService.class);
        startService(intent);
    }


    private final View.OnClickListener onThemeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        }
    };
}
