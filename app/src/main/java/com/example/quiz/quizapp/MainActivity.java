package com.example.quiz.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button trueB,falseB, nextB, backB, cheatB;
    private Question[] qs;
    private TextView qView;
    private int currentQ;
    private int score;
    private boolean canN, cheatable;
    public static final String EXTRA_ANSWER= "answer", EXTRA_QUESTION = "current q", EXTRA_SCORE = "scores";
    private Intent result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentQ =getIntent().getIntExtra(EXTRA_QUESTION,0);

        trueB = ((Button) findViewById(R.id.t));
        falseB = ((Button) findViewById(R.id.f));
        nextB = ((Button) findViewById(R.id.next));
        cheatB = ((Button) findViewById(R.id.cheat));
        backB = ((Button) findViewById(R.id.back));
        qView = (TextView) findViewById(R.id.q);
        qs = new Question[]{new Question(R.string.question1,true),new Question(R.string.question2,false),new Question(R.string.question3,true),new Question(R.string.question4,true),new Question(R.string.question5,false)};
        qView.setText(qs[currentQ].getQuestionId());
        canN=false;
        nextB.setBackgroundResource(R.color.fal_color);
        score =-0;
        cheatable =true;
        toggleCheat();

        result = new Intent();
        result.putExtra(MainActivity.EXTRA_SCORE,score);
        result.putExtra(MainActivity.EXTRA_QUESTION,currentQ);
        setResult(Result.RESULT_SCORE, result);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cheatM:
                toggleCheat();
                return true;
            case R.id.homeM:
                exit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void listeners(){
        trueB.setOnClickListener(this);
        falseB.setOnClickListener(this);
        nextB.setOnClickListener(this);
        qView.setOnClickListener(this);
        backB.setOnClickListener(this);
        cheatB.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==trueB.getId()){
            if(qs[currentQ].isAnswerTrue())
                right();
            else
                wrong();

        } else if(view.getId()==falseB.getId()){
            if(!qs[currentQ].isAnswerTrue())
                right();
            else
                wrong();
        } else if(view.getId()==nextB.getId()||view.getId()==qView.getId()){
            if(canN)
                next();
        }else if(view.getId()==backB.getId()){
                back();
        }else if(view.getId()==cheatB.getId()) {
            AlertDialog.Builder popup = new AlertDialog.Builder(this);
            popup.setMessage("Do you really want to cheat?").setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent cheat = new Intent(MainActivity.this, Cheat.class);
                    cheat.putExtra(EXTRA_ANSWER, qs[currentQ].isAnswerTrue());
                    cheat.putExtra(EXTRA_QUESTION, qs[currentQ].getQuestionId());
                    score--;
                    startActivity(cheat);
                }
            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    score++;
                    Toast.makeText(MainActivity.this, "Good job for not cheating, have a point.", Toast.LENGTH_SHORT).show();
                    dialogInterface.cancel();
                }
            });

            AlertDialog a = popup.create();

            a.show();

        }

        result.putExtra(MainActivity.EXTRA_SCORE,score);
        result.putExtra(MainActivity.EXTRA_QUESTION,currentQ);
        setResult(Result.RESULT_SCORE, result);
    }

    private void back() {
        if(currentQ>0)
            currentQ  = --currentQ % qs.length;
        else
            currentQ = qs.length-1;
        qView.setText(qs[currentQ].getQuestionId());
        canN=true;
    }

    private void wrong() {
        Toast.makeText(MainActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
        nextB.setBackgroundResource(R.color.tru_color);
        canN=true;
    }
    private void toggleCheat(){
        cheatable=!cheatable;
        if(!cheatable)
            cheatB.setVisibility(View.GONE);
        else
            cheatB.setVisibility(View.VISIBLE);
    }
    private void right() {
        if(!canN)
            score++;
        Toast.makeText(MainActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
        nextB.setBackgroundResource(R.color.tru_color);
        canN=true;
    }

    private void next() {
        if(currentQ<qs.length-1)
            currentQ++;
        else
            exit();
        qView.setText( qs[currentQ].getQuestionId());
        nextB.setBackgroundResource(R.color.fal_color);
        canN=false;
    }

    public void exit(){
        finish();
    }

}
