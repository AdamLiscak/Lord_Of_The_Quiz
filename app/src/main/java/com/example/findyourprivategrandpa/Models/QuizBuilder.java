package com.example.findyourprivategrandpa.Models;

import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuizBuilder
{
    private String name;
    private Question[] questions;
    private JSONObject quiz;
    public QuizBuilder() throws Exception
    {
        JSONObject jsonObject;
        jsonObject=LocalStorage.getJSONObject("quiz");
        name=jsonObject.getString("name");
        JSONArray jsonQuestions= jsonObject.getJSONArray("questions");
        int length=jsonQuestions.length();
        questions=new Question[length];
        for(int i=0;i<length;i++)
        {
            JSONObject jsonQuestion=(JSONObject)jsonQuestions.get(i);
            Question question= new Question(jsonQuestion);
            questions[i]=question;
        }

    }
}
