package com.example.findyourprivategrandpa.Models;

import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostMessageBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.findyourprivategrandpa.Urls.MY_QUIZES_URL;
import static com.example.findyourprivategrandpa.Urls.MY_SCORES_URL;
import static com.example.findyourprivategrandpa.Urls.USER_QUIZES_URL;

public class Me
{
    private HashMap<Quiz,Integer> myHighScores;
    private Quiz[] myQuizes;
    private QuizBuilder currentlyEdited;
    public void loadMyQuizes() throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        BidirectionalRequest br= new BidirectionalRequest(MY_QUIZES_URL,pm.getValues());
        JSONObject jsonObject = new JSONObject(br.getResponse());
        JSONArray jsonScores= jsonObject.getJSONArray("quizes");
        int size=jsonScores.length();
        myQuizes=new Quiz[size];
        for (int i=0;i<jsonScores.length();i++)
        {
            JSONObject scoreTuple=jsonScores.getJSONObject(i);
            Quiz quiz=new Quiz(scoreTuple.getInt("id"),scoreTuple.getString("name"));
            myQuizes[i]=quiz;
        }
    }
    public void loadCurrentlyEdited()
    {
        try
        {
            this.currentlyEdited = new QuizBuilder();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void loadMyHighScores() throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();

        BidirectionalRequest br= new BidirectionalRequest(MY_SCORES_URL,pm.getValues());
        JSONObject jsonObject = new JSONObject(br.getResponse());
        JSONArray jsonScores= jsonObject.getJSONArray("scores");
        int size=jsonScores.length();
        myQuizes=new Quiz[size];
        for (int i=0;i<jsonScores.length();i++)
        {
            JSONObject scoreTuple=jsonScores.getJSONObject(i);
            Quiz quiz=new Quiz(scoreTuple.getInt("id"),scoreTuple.getString("name"));
            myHighScores.put(quiz,scoreTuple.getInt("quiz"));
        }
    }
}
