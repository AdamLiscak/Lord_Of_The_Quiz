package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.ImageFetcher;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostMessageBuilder;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostRequest;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.findyourprivategrandpa.Urls.EXPORT_URL;
import static com.example.findyourprivategrandpa.Urls.QUESTION_IMAGE_URL;
import static com.example.findyourprivategrandpa.Urls.QUIZ_URL;
import static com.example.findyourprivategrandpa.Urls.THUMBNAIL_URL;
import static java.lang.Math.pow;

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
    private int streak=0;
    public Quiz(Bitmap thumbnail, String name, int id, Question[] questions)
    {
        this.thumbnail=thumbnail;
        this.name=name;
        this.id=id;
        this.questions=questions;
    }
    public Quiz(int id,String name)
    {
        this.id=id;
        this.name=name;
    }
    public Quiz (int id) throws Exception
    {
        this.id=id;
        PostMessageBuilder pb= new PostMessageBuilder();
        pb.addEntry("id",""+id);
        pb.addEntry("username",LocalStorage.getString("username"));
        BidirectionalRequest br=new BidirectionalRequest(QUIZ_URL,pb.getValues());
        String stringJson=br.getResponse();
        JSONObject jsonObject;
        jsonObject=new JSONObject(stringJson);
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
        PostMessageBuilder pm= new PostMessageBuilder();
        pm.addEntry("username",LocalStorage.getString("username"));
        pb.addEntry("id",""+id);
        ImageFetcher imageFetcher=new ImageFetcher(THUMBNAIL_URL,pm.getValues());
       this.thumbnail=imageFetcher.getImage();
    }
    public void downloadQuiz() throws Exception
    {
        PostMessageBuilder pb= new PostMessageBuilder();
        pb.addEntry("id",""+id);
        pb.addEntry("username",LocalStorage.getString("username"));
        BidirectionalRequest br=new BidirectionalRequest(QUIZ_URL,pb.getValues());
        String stringJson=br.getResponse();
        JSONObject jsonObject;
        jsonObject=new JSONObject(stringJson);
        author=jsonObject.getString("author");
        JSONArray jsonQuestions= jsonObject.getJSONArray("questions");
        int length=jsonQuestions.length();
        questions=new Question[length];
        for(int i=0;i<length;i++)
        {
            JSONObject jsonQuestion=(JSONObject)jsonQuestions.get(i);
            Question question= new Question(jsonQuestion);
            questions[i]=question;
        }
        PostMessageBuilder pm= new PostMessageBuilder();
        pm.addEntry("username",LocalStorage.getString("username"));
        pm.addEntry("id",""+id);
        ImageFetcher imageFetcher=new ImageFetcher(THUMBNAIL_URL,pm.getValues());
        this.thumbnail=imageFetcher.getImage();
    }
    public Question getQuestion()
    {
        return questions[index];
    }
    public void start()
    {
        PostMessageBuilder pm= new PostMessageBuilder();
        pm.addEntry("username",LocalStorage.getString("username"));
        pm.addEntry("qID",""+questions[index].getId());
        ImageFetcher imageFetcher=new ImageFetcher(QUESTION_IMAGE_URL,pm.getValues());
        questions[index].setPicture(imageFetcher.getImage());
    }
    public int multiplyByStreak(int score)
    {
        float exponent=1.03f;
        return (int)pow(exponent,streak)*score;
    }
    public void nextQuestion()
    {
        questions[index].setPicture(null);
        index++;
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("username",LocalStorage.getString("username"));
        pm.addEntry("id",""+id);
        pm.addEntry("qID",""+questions[index].getId());
        ImageFetcher imageFetcher=new ImageFetcher(QUESTION_IMAGE_URL,pm.getValues());
        questions[index].setPicture(imageFetcher.getImage());
    }
    public void export()
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("quiz_id",""+questions[index].getId());
        pm.addEntry("username",LocalStorage.getString("username"));
        PostRequest pr= new PostRequest(EXPORT_URL,pm.getValues());
        pr.post();
    }

    public int getStreak()
    {
        return streak;
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

    public String getName()
    {
        return name;
    }

    public String getAuthor()
    {
        return author;
    }

    public Question[] getQuestions()
    {
        return questions;
    }

    public HashMap<User, Integer> getHighscores()
    {
        return highscores;
    }

    public int[] getMyScores()
    {
        return myScores;
    }

    public int getPoints()
    {
        return points;
    }

    public void setQuestions(Question[] questions)
    {
        this.questions=questions;
    }
    public boolean isCorrect(int answerID)
    {
        points+=multiplyByStreak(questions[index].getPoints());
        boolean isCorrect=questions[index].isCorrect(answerID);
        if(isCorrect)
        {
            streak++;
        }
        else
        {
            streak=0;
        }
        return isCorrect;
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
