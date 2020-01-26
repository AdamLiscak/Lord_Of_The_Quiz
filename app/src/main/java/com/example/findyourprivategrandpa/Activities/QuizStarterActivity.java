package com.example.findyourprivategrandpa.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.findyourprivategrandpa.Models.Quiz;
import com.example.findyourprivategrandpa.R;

public class QuizStarterActivity extends AppCompatActivity
{
    private Quiz quiz = Quiz.getQuizzes()[Quiz.getCurrentQuiz()];
    private TextView questionNumber;
    private TextView author;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_starter);
        questionNumber= findViewById(R.id.number_questions_textView);
        author = findViewById(R.id.quiz_author_textView);
        questionNumber.setText("Der quiz hat "+quiz.getQuestions().length+" Fragen");
        author.setText(quiz.getAuthor());
        name = findViewById(R.id.textView_quiz_name);
        name.setText(quiz.getName());
    }
    public void startGame(View view)
    {
        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);
    }
}
