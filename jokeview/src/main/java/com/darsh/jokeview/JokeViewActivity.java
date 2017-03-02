package com.darsh.jokeview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by darshan on 2/3/17.
 */

public class JokeViewActivity extends AppCompatActivity {
    public static final String JOKE_EXTRA = "joke_data";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_view);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        }
        String joke = intent.getStringExtra(JOKE_EXTRA);
        ((TextView) findViewById(R.id.text_view)).setText(joke);
    }
}
