package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import org.json.JSONObject;

public class Question
{
    private final String name;
    private final String[] answers;
    private final int correctAnswer;
    private int points;
    private final Bitmap picture;
    public Question(String name, String[] answers, int correctAnswer, Bitmap picture)
    {
        this.name=name;
        this.answers=answers;
        this.correctAnswer=correctAnswer;
        this.picture=picture;
    }
    public void setPoints(int points)
    {
        this.points=points;
    }
    public int getPoints() {
        return points;
    }
}
