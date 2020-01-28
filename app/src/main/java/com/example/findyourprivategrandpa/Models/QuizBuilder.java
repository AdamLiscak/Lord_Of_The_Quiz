package com.example.findyourprivategrandpa.Models;

import android.graphics.Bitmap;

import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.ImageUploader;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostMessageBuilder;
import com.example.findyourprivategrandpa.localStorage.ImageParser;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.findyourprivategrandpa.Urls.QUESTION_PICTURE_UPLOAD_URL;
import static com.example.findyourprivategrandpa.Urls.QUIZ_BUILDER_EXPORT_URL;
import static com.example.findyourprivategrandpa.Urls.QUIZ_THUMBNAIL_UPLOAD_URL;
import static com.example.findyourprivategrandpa.Urls.USER_QUIZES_URL;

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
    public void export() throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("quiz",LocalStorage.getString("quiz"));
        pm.addEntry("author",LocalStorage.getString("author"));
        BidirectionalRequest br= new BidirectionalRequest(QUIZ_BUILDER_EXPORT_URL,pm.getValues());
       // JSONObject response = new JSONObject(br.getResponse());
     //   JSONArray qIDs=response.getJSONArray("qIDs");
        int quizID=Integer.parseInt(br.getResponse());
        PostMessageBuilder pBuilder = new PostMessageBuilder();
        pBuilder.addEntry("quizID",""+quizID);
        pBuilder.addEntry("picture","-1");
        ImageUploader iup= new ImageUploader(quiz.getString("thumbnail"),QUESTION_PICTURE_UPLOAD_URL,pBuilder.getValues());
        iup.uploadImage();
        for (int i = 0; i < questions.length() ; i++)
        {
            PostMessageBuilder postBuilder = new PostMessageBuilder();
            postBuilder.addEntry("quizID",""+quizID);
            postBuilder.addEntry("picture",""+i);
            ImageUploader iu= new ImageUploader(((JSONObject)questions.get(i)).getString("picture"),QUESTION_PICTURE_UPLOAD_URL,postBuilder.getValues());
            iu.uploadImage();
        }
        PostMessageBuilder pb = new PostMessageBuilder();
        pb.addEntry("thumbnail",""+quizID);
        ImageUploader iu= new ImageUploader(this.thumbnail,QUIZ_THUMBNAIL_UPLOAD_URL,pb.getValues());
        iu.uploadImage();
        LocalStorage.removeString("quiz");

    }
    public JSONObject getQuestionAt(int index)
    {
        JSONObject question=null;
        if(index<questions.length())
        {
            try {
                question = questions.getJSONObject(index);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return question;
    }
    public Bitmap getThumbnail()
    {
        try {
            ImageParser ip= new ImageParser(quiz.getString("thumbnail"));
            return ip.returnImage();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public Bitmap getPictureAtQuestion(JSONObject q)
    {
        try {
            ImageParser ip= new ImageParser(q.getString("picture"));
            return ip.returnImage();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
