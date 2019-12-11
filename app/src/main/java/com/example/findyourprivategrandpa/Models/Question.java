package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.ImageFetcher;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

public class Question
{
    private int id;
    private final String name;
    private final String[] answers;
    private final int correctAnswer;
    private long tStart;
    private int points;
    private Bitmap picture;

    public void  setTStart()
    {
        tStart=System.currentTimeMillis();
    }
    private long timeElapsed()
    {
        return tStart-System.currentTimeMillis();
    }
    boolean isCorrect(int answerID)
    {
        boolean isCorrect=answerID==correctAnswer;
        if (isCorrect)
        {
            points=1000-(int)timeElapsed()/15;
        }
        return isCorrect;
    }
    public int getCorrectAnswer()
    {
        return correctAnswer;
    }
    public Bitmap getPicture()
    {
        return picture;
    }
    public Question(JSONObject jsonQuestion) throws Exception
    {
        this.id=jsonQuestion.getInt("id");
        this.name=jsonQuestion.getString("name");
        this.correctAnswer=jsonQuestion.getInt("correctAnswer");
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
    public int isCorrect() {
        return points;
    }

    public int getPoints() {
        return points;
    }
}