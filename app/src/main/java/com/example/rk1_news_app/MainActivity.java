package com.example.rk1_news_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ru.mail.weather.lib.News;
import ru.mail.weather.lib.Storage;
import ru.mail.weather.lib.Topics;
import ru.mail.weather.lib.Scheduler;
import ru.mail.weather.lib.NewsLoader;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_pick_theme).setOnClickListener(onThemeClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, Storage.getInstance(this).loadCurrentTopic());
        loadTopic();
    }

    private void loadTopic(){
        String topic = Storage.getInstance(this).loadCurrentTopic();
        if(topic!=null) {
            ((Button) findViewById(R.id.btn_pick_theme)).setText(topic);
        }
    }


    private final View.OnClickListener onThemeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        }
    };
}
