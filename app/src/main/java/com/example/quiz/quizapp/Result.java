package com.example.quiz.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    private int score;
    private Button redo,exit;
    private int lastQ;

    public static final int RESULT_SCORE = 681;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        score = getSharedPreferences("score", 0).getInt("score",0);
        Log.e("score:",score+"");
        redo = (Button)findViewById(R.id.redo);
        exit = (Button)findViewById(R.id.exit);
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent test = new Intent(Result.this,MainActivity.class);
                startActivityForResult(test,RESULT_SCORE);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        refresh();
        lastQ = 0;
    }

    public void refresh(){
        ((TextView)findViewById(R.id.score)).setText(getString(R.string.score_is)+score);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.e("score:",(resultCode==RE)+" "+(requestCode==RESULT_SCORE));
        if(requestCode==RESULT_SCORE){
            score = data.getIntExtra(MainActivity.EXTRA_SCORE,-1);
            getSharedPreferences("score",0).edit().putInt("score", score).commit();
            lastQ = data.getIntExtra(MainActivity.EXTRA_QUESTION,0);
            Log.e("score:",score+"");
            refresh();

        }
    }
}
