package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import com.example.findyourprivategrandpa.Models.Question;

public class Quiz
{
    private final Bitmap thumbnail;
    private final Question[] questions;
    public Quiz(Question[] questions, Bitmap thumbnail)
    {
        this.thumbnail=thumbnail;
        this.questions=questions;
    }
}
