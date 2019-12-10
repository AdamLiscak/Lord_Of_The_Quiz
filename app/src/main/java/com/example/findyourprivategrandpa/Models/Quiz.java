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
import static com.example.findyourprivategrandpa.Urls.HIGH_SCORES_BY_QUIZ_URL;
import static com.example.findyourprivategrandpa.Urls.HIGH_SCORES_BY_USER_URL;
import static com.example.findyourprivategrandpa.Urls.MY_SCORES_BY_QUIZ_URL;
import static com.example.findyourprivategrandpa.Urls.QUESTION_IMAGE_URL;
import static com.example.findyourprivategrandpa.Urls.QUIZZES_URL;
import static com.example.findyourprivategrandpa.Urls.QUIZ_URL;
import static com.example.findyourprivategrandpa.Urls.THUMBNAIL_URL;
import static java.lang.Math.pow;

public class Quiz
{
    private static Quiz[] quizzes;
    private Bitmap thumbnail;
    private final String name;
    private String author;
    private Question[] questions;
    private HashMap<User,Integer> highScores;
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
    public static void loadQuizzes(int page) throws Exception
    {
        PostMessageBuilder pb= new PostMessageBuilder();
        pb.addEntry("username",LocalStorage.getString("username"));
        pb.addEntry("page",""+page);
        BidirectionalRequest br=new BidirectionalRequest(QUIZZES_URL,pb.getValues());
        String stringJson=br.getResponse();
        JSONObject jsonObject;
        jsonObject=new JSONObject(stringJson);
        JSONArray quizzes=jsonObject.getJSONArray("quizzes");
        Quiz.quizzes=new Quiz[quizzes.length()];
        for (int i = 0; i < quizzes.length() ; i++)
        {
            Quiz quiz= new Quiz(jsonObject.getInt("id"),jsonObject.getString("name"));
            JSONArray jsonQuestions = jsonObject.getJSONArray("questions");
            int length = jsonQuestions.length();
            quiz.questions = new Question[length];
            for (int j = 0; j < length; j++)
            {
                JSONObject jsonQuestion = (JSONObject) jsonQuestions.get(i);
                Question question = new Question(jsonQuestion);
                quiz.questions[i] = question;
            }
            PostMessageBuilder pm = new PostMessageBuilder();
            pm.addEntry("username", LocalStorage.getString("username"));
            pb.addEntry("id", "" + quiz.id);
            ImageFetcher imageFetcher = new ImageFetcher(THUMBNAIL_URL, pm.getValues());
            quiz.thumbnail = imageFetcher.getImage();
            Quiz.quizzes[i]=quiz;
        }
    }
    public void pullHighScores() throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("id",""+id);
        BidirectionalRequest br= new BidirectionalRequest(HIGH_SCORES_BY_QUIZ_URL,pm.getValues());
        JSONObject jsonObject = new JSONObject(br.getResponse());
        JSONArray jsonScores= jsonObject.getJSONArray("scores");
        for (int i=0;i<jsonScores.length();i++)
        {
            JSONObject scoreTuple=jsonScores.getJSONObject(i);
            User user=new User(scoreTuple.getInt("id"),scoreTuple.getString("name"));
            highScores.put(user,scoreTuple.getInt("score"));
        }
    }
    public void pullMyScores() throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("id",""+id);
        BidirectionalRequest br= new BidirectionalRequest(MY_SCORES_BY_QUIZ_URL,pm.getValues());
        JSONObject jsonObject = new JSONObject(br.getResponse());
        JSONArray jsonScores= jsonObject.getJSONArray("scores");
        int length=jsonScores.length();
        myScores= new int[length];
        for (int i=0;i<length;i++)
        {
            myScores[i]=(int)jsonScores.get(i);
        }
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
        boolean isCorrect=questions[index].isCorrect(answerID);
        points+=multiplyByStreak(questions[index].getPoints());
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

    public HashMap<User, Integer> getHighScores()
    {
        return highScores;
    }

    public static Quiz[] getQuizzes()
    {
        return quizzes;
    }
}
