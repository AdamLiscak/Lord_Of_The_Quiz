package com.example.quiz_lord.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.quiz_lord.activities.adapters.ScoreBoardAdapter;
import com.example.quiz_lord.gamelogic.Quiz;
import com.example.quiz_lord.R;

public class ScoreBoardActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ScoreBoardAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        recyclerView = (RecyclerView) findViewById(R.id.scoreboard_list_recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
            mAdapter = new ScoreBoardAdapter(Quiz.getQuizzes()[Quiz.getCurrentQuiz()]);
            recyclerView.setAdapter(mAdapter);

    }
}
