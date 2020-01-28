package com.example.quiz_lord.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quiz_lord.activities.adapters.QuizAdapter;
import com.example.quiz_lord.gamelogic.Quiz;
import com.example.quiz_lord.R;


public class QuizHubActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private QuizAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_hub);
        recyclerView = (RecyclerView) findViewById(R.id.quiz_list);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new QuizAdapter();
        recyclerView.setAdapter(mAdapter);

    }
    public void openQuiz(View view)
    {
        Intent intent = new Intent(this,QuizStarterActivity.class);
        int position = (int)view.getTag();
        Quiz.setCurrentQuiz(position);
        startActivity(intent);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
