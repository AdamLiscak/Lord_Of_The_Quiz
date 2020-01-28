package com.example.quiz_lord.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.quiz_lord.activities.adapters.QuizBuilderAdapter;
import com.example.quiz_lord.R;
import com.example.quiz_lord.localstorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuizBuilderActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private QuizBuilderAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Button addQuestion;
    private static int index=0;
    private static boolean edit=false;
    private static int length;

    public static int getIndex()
    {
        return index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_builder);
        initialize();
    }
    private void initialize()
    {
        recyclerView = (RecyclerView) findViewById(R.id.quiz_builder_list);
        recyclerView.setHasFixedSize(false);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        JSONObject quiz = LocalStorage.getJSONObject("quiz");
        try
        {
            JSONArray questions = quiz.getJSONArray("questions");
            length = questions.length();
            mAdapter = new QuizBuilderAdapter(questions,getApplication());
            recyclerView.setAdapter(mAdapter);
            addQuestion = findViewById(R.id.quiz_builder_button_add_question_quiz_builder);
        }
        catch (Exception e)
        {

        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        setContentView(R.layout.activity_quiz_builder);

    initialize();
    }

    public void addQuestion(View view)
    {
        edit=false;
        index = length;
        Intent intent = new Intent(this,DesignQuestionActivity.class);
        startActivity(intent);
    }
    public void discardQuestion(View view)
    {

    }

    public static boolean isEdit()
    {
        return edit;
    }

    public void editQuestion(View view)
    {
        edit=true;
        index = (int)view.getTag();
        Log.d("editQuestion", "editQuestion: "+index);
        Intent intent = new Intent(this,DesignQuestionActivity.class);
        startActivity(intent);
    }
}
