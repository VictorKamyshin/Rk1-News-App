package com.example.rk1_news_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import ru.mail.weather.lib.Storage;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        findViewById(R.id.btn_pick_auto).setOnClickListener(onThemeClick);
        findViewById(R.id.btn_pick_it).setOnClickListener(onThemeClick);
        findViewById(R.id.btn_pick_health).setOnClickListener(onThemeClick);
    }

    private final View.OnClickListener onThemeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String topic = ((Button)view).getText().toString();
            Storage.getInstance(NewsActivity.this).saveCurrentTopic(topic);
            finish();
        }
    };

}
