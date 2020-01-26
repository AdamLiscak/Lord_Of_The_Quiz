package com.example.findyourprivategrandpa.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.findyourprivategrandpa.Models.Question;
import com.example.findyourprivategrandpa.Models.Quiz;
import com.example.findyourprivategrandpa.R;
import com.example.findyourprivategrandpa.controllerinterfaces.post.ImageFetcher;

import static com.example.findyourprivategrandpa.Urls.THUMBNAIL_URL;

public class GameActivity extends AppCompatActivity {

    private Button[] buttons = new Button[4];
    private ImageView imageView;
    private TextView name;
    private Quiz currentQuiz = Quiz.getQuizzes()[Quiz.getCurrentQuiz()];
    private Question currentQuestion = currentQuiz.getQuestion();
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
        for (int i=0;i<buttons.length;i++)
        {
            buttons[i].setText(currentQuestion.getAnswers()[i]);
        }
        currentQuiz.start();
        imageView.setImageBitmap(currentQuestion.getPicture());
        name.setText(currentQuestion.getName());
      //  ImageFetcher imageFetcher=new ImageFetcher(THUMBNAIL_URL+currentQuiz.getId()+"/"+1,"");
     //   imageView.setImageBitmap(imageFetcher.getImage());

    }
}
