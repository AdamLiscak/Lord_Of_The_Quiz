package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.ImageFetcher;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostMessageBuilder;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.findyourprivategrandpa.Urls.EXPORT_URL;
import static com.example.findyourprivategrandpa.Urls.QUESTION_IMAGE_URL;
import static com.example.findyourprivategrandpa.Urls.QUIZ_URL;
import static com.example.findyourprivategrandpa.Urls.THUMBNAIL_URL;

public class Quiz
{
    private Bitmap thumbnail;
    private final String name;
    private String author;
    private Question[] questions;
    private HashMap<User,Integer> highscores;
    private int[] myScores;
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
    public Quiz (int id) throws Exception
    {
        PostMessageBuilder pb= new PostMessageBuilder();
        pb.addEntry("id",""+id);
        BidirectionalRequest br=new BidirectionalRequest(QUIZ_URL,pb.getValues());
        String stringJson=br.getResponse();
        JSONObject jsonObject;
        jsonObject=new JSONObject(stringJson);
        this.id=id;
        author=jsonObject.getString("author");
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
    public void export()
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("quiz_id",""+questions[index].getId());
        PostRequest pr= new PostRequest(EXPORT_URL,pm.getValues());
    }
    public void setThumbnail()
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("quiz_id",""+questions[index].getId());
        ImageFetcher imageFetcher=new ImageFetcher(THUMBNAIL_URL,pm.getValues());
        this.thumbnail=imageFetcher.getImage();
    }
    public void removeThumbnail()
    {
        this.thumbnail=null;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }


    public void setQuestions(Question[] questions)
    {
        this.questions=questions;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder("name: ").append(name).append("\nauthor: ").append(author).append("\nid: ").append(id);
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
