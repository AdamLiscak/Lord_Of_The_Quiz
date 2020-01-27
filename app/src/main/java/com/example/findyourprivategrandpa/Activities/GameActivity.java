package com.example.findyourprivategrandpa.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.findyourprivategrandpa.Models.Question;
import com.example.findyourprivategrandpa.Models.Quiz;
import com.example.findyourprivategrandpa.R;
import com.example.findyourprivategrandpa.Timer.Timer;
import com.example.findyourprivategrandpa.controllerinterfaces.post.ImageFetcher;

import java.util.TimerTask;

import static com.example.findyourprivategrandpa.Urls.THUMBNAIL_URL;

public class GameActivity extends AppCompatActivity {

    ProgressBar pb;
    int counter = 0;


    private Button[] buttons = new Button[4];
    private ImageView imageView;
    private TextView name;
    private Quiz currentQuiz = Quiz.getQuizzes()[Quiz.getCurrentQuiz()];
    private Question currentQuestion = currentQuiz.getQuestion();
    private GameTimer timer;

    private class GameTimer extends Timer {
        private Activity activity;

        public GameTimer(long duration, Activity activity) {
            super(duration);
            this.activity = activity;
        }

        @Override
        public void onInterrupt() {
            try {
                sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(activity, QuestionSeparatorActivity.class);
            startActivity(intent);
        }

        @Override
        public void onTimeUp() {
            Intent intent = new Intent(activity, QuestionSeparatorActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        buttons[0] = findViewById(R.id.gameButton1);
        buttons[1] = findViewById(R.id.gameButton2);
        buttons[2] = findViewById(R.id.gameButton3);
        buttons[3] = findViewById(R.id.gameButton4);
        imageView = findViewById(R.id.gameActivity_imagview);
        name = findViewById(R.id.gameactivity_question_name_textView);
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(currentQuestion.getAnswers()[i]);
            buttons[i].setTag(i);
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    evaluateAnswer(v);
                }
            });
        }
        currentQuiz.start();
        imageView.setImageBitmap(currentQuestion.getPicture());
        name.setText(currentQuestion.getName());
        timer = new GameTimer(15000, this);
        timer.start();
        currentQuestion.setTStart();
        //  ImageFetcher imageFetcher=new ImageFetcher(THUMBNAIL_URL+currentQuiz.getId()+"/"+1,"");
        //   imageView.setImageBitmap(imageFetcher.getImage());

    }

    public void evaluateAnswer(View view) {
        if (currentQuiz.isCorrect((int) view.getTag())) {
            view.setBackgroundColor(Color.GREEN);
            timer.interrupt();
        } else {
            view.setBackgroundColor(Color.RED);
            timer.interrupt();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInatanceState) {
        super.onCreate(navedInstanceState);
        setContentView(R.Layout.activity_game);

        prog();
    }

    public void prog() {
        pb = (ProgressBar) findViewById(R.id.pb);

        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {

                counter++;
                pb.setProgress(counter);

                if (counter == 100)
                    t.cancel();
            }
        };

        t.schedule(tt, 0, 100);
    }
}


