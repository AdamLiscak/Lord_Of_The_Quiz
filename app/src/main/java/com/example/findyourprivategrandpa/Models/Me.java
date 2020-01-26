package com.example.findyourprivategrandpa.Models;

import android.util.Log;

import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostMessageBuilder;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.findyourprivategrandpa.Urls.DELETE_ACCOUNT_URL;
import static com.example.findyourprivategrandpa.Urls.LOGIN_URL;
import static com.example.findyourprivategrandpa.Urls.LOGOUT_URL;
import static com.example.findyourprivategrandpa.Urls.MY_QUIZES_URL;
import static com.example.findyourprivategrandpa.Urls.MY_SCORES_URL;
import static com.example.findyourprivategrandpa.Urls.REGISTER_URL;
import static com.example.findyourprivategrandpa.Urls.USER_QUIZES_URL;
public class Me
{
    public static final int NAME_ALREADY_EXISTS_EXCEPTION = 601;
    private HashMap<Quiz,Integer> myHighScores;
    private Quiz[] myQuizes;
    private QuizBuilder currentlyEdited;
    public void loadMyQuizes() throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("username",LocalStorage.getString("username"));
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
        pm.addEntry("username",LocalStorage.getString("username"));
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
    public static boolean login(String username,String password) throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("username",username);
        pm.addEntry("password",password);
        BidirectionalRequest br= new BidirectionalRequest(LOGIN_URL+"?"+pm.getValues(),"");
        String response = br.getResponse();
        Log.d("login", "login: "+response);
        Boolean authorized = Boolean.parseBoolean(response);
        if(authorized)
        {
            LocalStorage.changeProperty("username",username);
            LocalStorage.commit();
        }
        return authorized;
    }
    public static int register(String username, String password) throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("username",username);
        pm.addEntry("password",password);
        BidirectionalRequest br= new BidirectionalRequest(REGISTER_URL,pm.getValues());
        return Integer.parseInt(br.getResponse());
    }
    public static boolean logout() throws Exception
    {
       // PostMessageBuilder pm=new PostMessageBuilder();
    //    pm.addEntry("username",LocalStorage.getString("username"));
      //  BidirectionalRequest br= new BidirectionalRequest(LOGOUT_URL,pm.getValues());
     //   JSONObject jsonObject = new JSONObject(br.getResponse());
     //   Boolean authorized= jsonObject.getBoolean("status");
      //  if(authorized)
       // {
            LocalStorage.removeString("username");
            LocalStorage.commit();
      //  }
        return true;
    }
    public boolean deleteAccount() throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("username",LocalStorage.getString("username"));
        BidirectionalRequest br= new BidirectionalRequest(DELETE_ACCOUNT_URL,pm.getValues());
        JSONObject jsonObject = new JSONObject(br.getResponse());
        Boolean authorized= jsonObject.getBoolean("status");
        if(authorized)
        {
            LocalStorage.removeString("username");
            LocalStorage.commit();
        }
        return authorized;
    }
}
