package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import com.example.findyourprivategrandpa.Models.Question;

public class Quiz
{
    private Bitmap thumbnail;
    private final String name;
    private Question[] questions;
    public Quiz(Bitmap thumbnail, String name)
    {
        this.thumbnail=thumbnail;
        this.name=name;
    }
    public Quiz(String name)
    {
        this.name=name;
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
