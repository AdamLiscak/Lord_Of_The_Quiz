package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

public class Question
{
    private int id;
    private final String name;
    private final String[] answers;
    private final int correctAnswer;
    private final String pictureUrl;
    private int points;
    private Bitmap picture;
    public Question(String name, String[] answers, int correctAnswer, String pictureUrl)
    {
        this.name=name;
        this.answers=answers;
        this.correctAnswer=correctAnswer;
        this.pictureUrl=pictureUrl;
    }
    public Question(JSONObject jsonQuestion) throws Exception
    {
        this.id=jsonQuestion.getInt("id");
        this.name=jsonQuestion.getString("name");
        this.correctAnswer=jsonQuestion.getInt("correctAnswer");
        this.pictureUrl="cock.jpg";
        JSONArray jsonAnswers=jsonQuestion.getJSONArray("answers");
        answers=new String[4];
        for (int i=0;i<4;i++)
        {
            answers[i]=jsonAnswers.getString(i);
        }
    }
    public void setPicture(Bitmap picture)
    {
        this.picture=picture;
    }
    public String getName()
    {
        return this.name;
    }
    public String[] getAnswers()
    {
        return this.answers;
    }
    public int getId()
    {
        return this.id;
    }
    public void setPoints(int points)
    {
        this.points=points;
    }
    public int getPoints() {
        return points;
    }
}