package com.example.rk1_news_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_pick_theme).setOnClickListener(onThemeClick);
    }


    private final View.OnClickListener onThemeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        }
    };
}
