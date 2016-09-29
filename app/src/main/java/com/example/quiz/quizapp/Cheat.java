package com.example.quiz.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Cheat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        Intent t = getIntent();
        boolean answer = t.getBooleanExtra(MainActivity.EXTRA_ANSWER,true);
        int q = t.getIntExtra(MainActivity.EXTRA_QUESTION,-1);
        TextView text = (TextView) findViewById(R.id.answer);
        text.setText(getString(q)+": "+answer);
    }
    public void noClick(View view){
        finish();
    }
}
