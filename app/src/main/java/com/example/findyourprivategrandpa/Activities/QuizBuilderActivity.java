package com.example.findyourprivategrandpa.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.findyourprivategrandpa.Activities.Adapters.QuizBuilderAdapter;
import com.example.findyourprivategrandpa.R;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuizBuilderActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private QuizBuilderAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button addQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_builder);

        recyclerView = (RecyclerView) findViewById(R.id.quiz_builder_list);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        JSONObject quiz = LocalStorage.getJSONObject("quiz");
        try
        {
            JSONArray questions = quiz.getJSONArray("questions");
            mAdapter = new QuizBuilderAdapter(questions);
            recyclerView.setAdapter(mAdapter);
            addQuestion = findViewById(R.id.quiz_builder_button_add_question_quiz_builder);
        }
        catch (Exception e)
        {

        }
    }
    public void editQuestion(View view)
    {

    }
    public void discardQuestion(View view)
    {

    }
    public void addQuestion(View view)
    {

    }
}
