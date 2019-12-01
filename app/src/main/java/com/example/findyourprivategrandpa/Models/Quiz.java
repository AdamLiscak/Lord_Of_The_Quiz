package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.ImageFetcher;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostMessageBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.findyourprivategrandpa.Urls.QUESTION_IMAGE_URL;

public class Quiz
{
    private Bitmap thumbnail;
    private final String name;
    private Question[] questions;
    private int index=0;
    private int points=0;
    private int id;
    public Quiz(Bitmap thumbnail, String name, int id, Question[] questions)
    {
        this.thumbnail=thumbnail;
        this.name=name;
        this.id=id;
        this.questions=questions;
    }
    public Quiz (String url, int id) throws Exception
    {
        PostMessageBuilder pb= new PostMessageBuilder();
        pb.addEntry("id",""+id);
        BidirectionalRequest br=new BidirectionalRequest(url,pb.getValues());
        String stringJson=br.getResponse();
        JSONObject jsonObject;
        jsonObject=new JSONObject(stringJson);
        id=jsonObject.getInt("id");
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
    public Quiz(String name)
    {
        this.name=name;
    }
    public Question getQuestion()
    {
        return questions[index];
    }
    public void start()
    {
        ImageFetcher imageFetcher=new ImageFetcher(QUESTION_IMAGE_URL,"cock");
        questions[index].setPicture(imageFetcher.getImage());
    }
    public void nextQuestion()
    {
        points+=questions[index].getPoints();
        questions[index].setPicture(null);
        index++;
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("id",""+questions[index].getId());
        ImageFetcher imageFetcher=new ImageFetcher(QUESTION_IMAGE_URL,pm.getValues());
        questions[index].setPicture(imageFetcher.getImage());
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

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder("name: ").append(name).append("\nid: ").append(id);
        for(Question question: questions)
        {
            sb.append("\nquestion: ").append(question.getName());
            for (String answer:question.getAnswers())
            {
                sb.append("\n   answer: ").append(answer);
            }
        }
        return sb.toString();
    }
}
