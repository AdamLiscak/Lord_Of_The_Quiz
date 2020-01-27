package com.example.findyourprivategrandpa.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.findyourprivategrandpa.Models.Question;
import com.example.findyourprivategrandpa.Models.Quiz;
import com.example.findyourprivategrandpa.R;

import org.w3c.dom.Text;

public class QuestionSeparatorActivity extends AppCompatActivity
{
    private Quiz currentQuiz = Quiz.getQuizzes()[Quiz.getCurrentQuiz()];
    private Question currentQuestion = currentQuiz.getQuestion();
    private TextView currentPointsView;
    private TextView allPointsView;
    private TextView streakView;
    private TextView currentQuestionView;
    private Button nextQuestion;
    private Button exit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(currentQuiz.reachedEnd())
        {
            currentQuiz.export();
            Intent intent = new Intent(this,QuizResultActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        setContentView(R.layout.activity_question_separator);
        currentPointsView = findViewById(R.id.textView_current_points);
        allPointsView = findViewById(R.id.textView_points);
        streakView = findViewById(R.id.textView_Streak);
        nextQuestion = findViewById(R.id.button_next_question);
        exit = findViewById(R.id.button_quit_quiz);
        currentQuestionView = findViewById(R.id.currentquestion);
        currentPointsView.setText("Punkte für die letzte Frage "+currentQuestion.getPoints());
        allPointsView.setText("Gesamtpunkte "+currentQuiz.getPoints());
        streakView.setText("Streak: "+currentQuiz.getStreak());
        currentQuestionView.setText((currentQuiz.getIndex()+1)+"/"+currentQuiz.getQuestions().length+" Fragen fertig");
        nextQuestion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nextQuestion(v);
            }
        });
        exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                exit(v);
            }
        });
    }
    public void nextQuestion(View view)
    {
        currentQuiz.nextQuestion();
        Intent intent = new Intent(this,GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void exit(View view)
    {
        currentQuiz.reset();
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    @Override
    public void onBackPressed()
    {
        currentQuiz.reset();
        Intent intent = new Intent(this,QuizHubActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
