package com.example.quiz_lord.gamelogic;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quiz_lord.gamelogic.structs.ScoreStruct;
import com.example.quiz_lord.timer.Timer;
import com.example.quiz_lord.middleware.post.BidirectionalRequest;
import com.example.quiz_lord.middleware.post.ImageFetcher;
import com.example.quiz_lord.middleware.post.PostMessageBuilder;
import com.example.quiz_lord.middleware.post.PostRequest;
import com.example.quiz_lord.localstorage.LocalStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.quiz_lord.Urls.EXPORT_URL;
import static com.example.quiz_lord.Urls.HIGH_SCORES_BY_QUIZ_URL;
import static com.example.quiz_lord.Urls.MY_SCORES_BY_QUIZ_URL;
import static com.example.quiz_lord.Urls.QUIZZES_URL;
import static com.example.quiz_lord.Urls.THUMBNAIL_URL;

public class Quiz
{
    private static Quiz[] quizzes;
    private Bitmap thumbnail;
    private String name;
    private String author;
    private Question[] questions;
    private ScoreStruct[] highScores;
    QuizTimer timer = new QuizTimer(15000);
    private int[] myScores;
    private int index=0;
    private int points=0;
    private int id;
    private int streak=0;
    private float multiplier = 1f;
    private static int currentQuiz;
    public final int ERROR_CODE_OUT_OF_BOUNDS=600;

    private class QuizTimer extends Timer
    {
        public QuizTimer(long delay)
        {
            super(delay);
        }
        @Override
        public void onTimeUp()
        {
            nextQuestion();
        }
        @Override
        public void onInterrupt()
        {

        }
    }
    public Quiz(int id,String name)
    {
        this.id=id;
        this.name=name;
    }

    public static int getCurrentQuiz()
    {
        return currentQuiz;
    }
    public static void setCurrentQuiz(int index)
    {
        currentQuiz=index;
    }

    public static void pullQuizzes(int page) throws Exception
    {
        PostMessageBuilder pb= new PostMessageBuilder();
      //  pb.addEntry("username",LocalStorage.getString("username"));
        pb.addEntry("page",""+page);
        BidirectionalRequest br=new BidirectionalRequest(QUIZZES_URL+"?"+pb.getValues(),"");
        String stringJson=br.getResponse();
        //Log.d("StringJson", "pullQuizzes: "+stringJson);
        JSONArray quizzes=new JSONArray(stringJson);
        Quiz.quizzes=new Quiz[quizzes.length()];
        for (int i = 0; i < quizzes.length() ; i++)
        {
            Quiz.quizzes[i] = new Quiz((JSONObject)quizzes.get(i));
        }
    }
    public Quiz(JSONObject jsonObject)
    {
        try
        {
            author = jsonObject.getString("author");
            name = jsonObject.getString("name");
            id = jsonObject.getInt("id");
            JSONArray jsonQuestions = jsonObject.getJSONArray("questions");
            int length = jsonQuestions.length();
            questions = new Question[length];
            for (int i = 0; i < length; i++)
            {
                JSONObject jsonQuestion = (JSONObject) jsonQuestions.get(i);
                Question question = new Question(jsonQuestion);
                questions[i] = question;
            }
            ImageFetcher imageFetcher = new ImageFetcher(THUMBNAIL_URL+this.id+"/-1", "");
            this.thumbnail = imageFetcher.getImage();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void pullHighScores() throws Exception
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("id",""+id);
        Log.d("jsonScores", "pullHighScores: hello");
        BidirectionalRequest br= new BidirectionalRequest(HIGH_SCORES_BY_QUIZ_URL+"?"+pm.getValues(),"");
        String response = br.getResponse();
        if (response==null)
        {
            response="[]";
        }
        JSONArray jsonScores= new JSONArray(response);
        Log.d("jsonScores", "pullHighScores: "+jsonScores.toString());
        highScores = new ScoreStruct[jsonScores.length()];
        for (int i=0;i<jsonScores.length();i++)
        {
            JSONObject scoreTuple=jsonScores.getJSONObject(i);
            User user=new User(scoreTuple.getInt("userID"),scoreTuple.getString("name"));
            ScoreStruct scoreStruct = new ScoreStruct(user,scoreTuple.getInt("points"));
            highScores[i]=scoreStruct;
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
    public Question getQuestion()
    {
        return questions[index];
    }
    public void start()
    {
      //  PostMessageBuilder pm= new PostMessageBuilder();
        //pm.addEntry("username",LocalStorage.getString("username"));
       // pm.addEntry("qID",""+questions[index].getId());
        ImageFetcher imageFetcher=new ImageFetcher(THUMBNAIL_URL+this.id+"/"+(index+1),"");
        questions[index].setPicture(imageFetcher.getImage());
      //  timer.start();
    }

    public int getId()
    {
        return id;
    }
    public void reset()
    {
        this.points = 0;
        this.streak = 0;
        this.multiplier  = 1f;
    }

    public int multiplyByStreak(int score)
    {
        return (int)(multiplier *score);
    }
    public void nextQuestion()
    {
        questions[index].setPicture(null);
        index++;
       // PostMessageBuilder pm=new PostMessageBuilder();
      //  pm.addEntry("username",LocalStorage.getString("username"));
     //   pm.addEntry("id",""+id);
      //  pm.addEntry("qID",""+questions[index].getId());
    //    ImageFetcher imageFetcher=new ImageFetcher(QUESTION_IMAGE_URL+this.id+"/"+index,"");
    //    questions[index].setPicture(imageFetcher.getImage());
    }

    public void export()
    {
        PostMessageBuilder pm=new PostMessageBuilder();
        pm.addEntry("quizId",""+this.id);
        pm.addEntry("username",LocalStorage.getString("username"));
        pm.addEntry("points",""+this.points);
        PostRequest pr= new PostRequest(EXPORT_URL+"?"+pm.getValues(),"");
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
        timer.interrupt();
        boolean isCorrect=questions[index].isCorrect(answerID);
        questions[index].setPoints(multiplyByStreak(questions[index].getPoints()));
        points+=questions[index].getPoints();
        if(isCorrect)
        {
            streak++;
            multiplier *=1.03f;
        }
        else
        {
            streak=0;
            multiplier =1;
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
    public boolean reachedEnd()
    {
        return index+1 == questions.length;
    }
    public int getIndex()
    {
        return this.index;
    }
    public ScoreStruct[] getHighScores()
    {
        return highScores;
    }

    public static Quiz[] getQuizzes()
    {
        return quizzes;
    }
}
