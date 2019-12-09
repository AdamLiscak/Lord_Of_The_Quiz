package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuizBuilder
{
    private String name;
    private String thumbnail;
    private JSONArray questions;
    private JSONObject quiz;
    public QuizBuilder() throws Exception
    {
        JSONObject jsonObject;
        jsonObject=LocalStorage.getJSONObject("quiz");
        name=jsonObject.getString("name");
        thumbnail=jsonObject.getString("thumbnail");
        questions = jsonObject.getJSONArray("questions");
    }
    public void addThumbnail(String thumbnail)
    {

        try
        {
            this.quiz.put("thumbnail", thumbnail);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        LocalStorage.commit();
    }
    public void setName(String name)
    {
        try
        {
            this.quiz.put("name", name);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        LocalStorage.commit();
    }
    public void newQuestion(String name)
    {
        this.questions.put(new JSONObject());
    }
    public void editQuestionName(String name, int index)
    {
        try
        {
            JSONObject question = questions.getJSONObject(index);
            question.put("name",name);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        LocalStorage.commit();
    }
    public void editAnswerName(String name, int questionIndex, int answerIndex)
    {
        try
        {
            JSONObject question = this.questions.getJSONObject(questionIndex);
            JSONArray answers=question.getJSONArray("answers");
            answers.put(answerIndex,name);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        LocalStorage.commit();
    }
    public void newAnswer(String name, int questionIndex)
    {

        try
        {
            JSONObject question = this.questions.getJSONObject(questionIndex);
            JSONArray answers=question.getJSONArray("answers");
            answers.put(name);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        LocalStorage.commit();
    }
    public void editQuestionThumbnail(String thumbnail, int questionIndex)
    {

        try
        {
            JSONObject question = this.questions.getJSONObject(questionIndex);
            question.put("picture",thumbnail);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        LocalStorage.commit();
    }
    public void addCorrectAnswer(int questionIndex,int answerIndex)
    {
        try
        {
            JSONObject question = this.questions.getJSONObject(questionIndex);
            question.put("correctAnswer",answerIndex);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        LocalStorage.commit();
    }

}
