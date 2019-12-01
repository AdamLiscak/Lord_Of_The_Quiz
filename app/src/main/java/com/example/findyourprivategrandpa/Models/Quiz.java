package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import com.example.findyourprivategrandpa.Models.Question;

import org.json.JSONObject;

public class Quiz
{
    private Bitmap thumbnail;
    private final String name;
    private Question[] questions;
    private int index=0;
    private int points=0;
    private int id;
    public Quiz(Bitmap thumbnail, String name, int id)
    {
        this.thumbnail=thumbnail;
        this.name=name;
        this.id=id;
    }
    public Quiz(String name)
    {
        this.name=name;
    }
    public Question getQuestion()
    {
        return questions[index];
    }
    public void nextQuestion()
    {
        points+=questions[index].getPoints();
        index++;
    }
    public JSONObject getJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("points",points);
        }
        catch (Exception e)
        {

        }
        return jsonObject;
    }
    public void setThumbnail(Bitmap bmp)
    {
        this.thumbnail=bmp;
    }
    public void setQuestions(Question[] questions)
    {
        this.questions=questions;
    }
}
