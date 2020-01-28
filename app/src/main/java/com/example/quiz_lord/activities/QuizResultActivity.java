package com.example.quiz_lord.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.quiz_lord.gamelogic.Question;
import com.example.quiz_lord.gamelogic.Quiz;
import com.example.quiz_lord.R;

public class QuizResultActivity extends AppCompatActivity
{
    private Quiz currentQuiz = Quiz.getQuizzes()[Quiz.getCurrentQuiz()];
    private Question currentQuestion = currentQuiz.getQuestion();

    private TextView allpoints;
    private TextView lastpoints;
    private Button finish;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        allpoints = findViewById(R.id.quiz_result_score);
        lastpoints = findViewById(R.id.quiz_result_last_score);
        finish = findViewById(R.id.quiz_result_quizhub_button);
        title = findViewById(R.id.quiz_result_quiz_finished_textview);
        allpoints.setText("Insgesamt hast du "+currentQuiz.getPoints()+" Punkte erreicht");
        lastpoints.setText("Bei der letzten Frage hast du "+currentQuestion.getPoints()+" Punkte erreicht");
    }
    public void finish(View view)
    {
        currentQuiz.reset();
        Intent intent = new Intent(this,QuizHubActivity.class);
        startActivity(intent);
    }
    public void scoreboard(View view)
    {
        currentQuiz.reset();
        Intent intent = new Intent(this,ScoreBoardActivity.class);
        startActivity(intent);
    }
}
