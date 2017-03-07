package com.example.rk1_news_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        findViewById(R.id.btn_pick_science).setOnClickListener(onThemeClick);
        findViewById(R.id.btn_pick_culture).setOnClickListener(onThemeClick);
        findViewById(R.id.btn_pick_sport).setOnClickListener(onThemeClick);
    }

    private final View.OnClickListener onThemeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        finish();
        }
    };

}
