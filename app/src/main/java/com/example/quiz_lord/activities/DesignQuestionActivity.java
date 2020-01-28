package com.example.quiz_lord.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.quiz_lord.R;
import com.example.quiz_lord.localstorage.LocalStorage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class DesignQuestionActivity extends AppCompatActivity
{
    Button allquestions;
    Button deleteQuestion;
    ImageView imageView;
    EditText editQuestionName;
    EditText[] answers;
    private JSONObject question;
    private JSONObject quiz;
    private JSONArray jsonAnswers;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_question);
        allquestions = findViewById(R.id.design_question_tree_button);
        deleteQuestion = findViewById(R.id.design_question_delete_button);
        imageView = findViewById(R.id.edit_question_imageView);
        editQuestionName = findViewById(R.id.edit_question_name);
        EditText editAnswer1 = findViewById(R.id.edit_question_answer_1);
        EditText editAnswer2 = findViewById(R.id.edit_question_answer_2);
        EditText editAnswer3 = findViewById(R.id.edit_question_answer_3);
        EditText editAnswer4 = findViewById(R.id.edit_question_answer_4);
        answers = new EditText[4];
        answers[0] =editAnswer1;
        answers[1] = editAnswer2;
        answers[2] = editAnswer3;
        answers[3] = editAnswer4;
        allquestions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                saveValues();
                finish();
            }
        });
        for (int i = 0; i < answers.length ; i++)
        {
            answers[i].setTag(i);
            answers[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    colorize(v);
                }
            });
        }
        initialize();

    }
    public void initialize()
    {
        int length = 0;
        try
        {
            length= quiz.getString("questions").length();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (!QuizBuilderActivity.isEdit())
        {
            Log.d("question", "onCreate: "+"hello");
            quiz = LocalStorage.getJSONObject("quiz");
            try
            {
                if(quiz.isNull("questions"))
                {
                    quiz.put("questions", new JSONArray());
                }
                JSONArray questions = quiz.getJSONArray("questions");
                questions.put(new JSONObject());
                question = questions.getJSONObject(questions.length()-1);
                question.put("answers",new JSONArray());
                jsonAnswers = question.getJSONArray("answers");
                LocalStorage.commit();
            }
            catch (Exception e)
            {

            }
        }
        else
        {
            quiz = LocalStorage.getJSONObject("quiz");
            try
            {
                JSONArray questions = quiz.getJSONArray("questions");
                question = questions.getJSONObject(QuizBuilderActivity.getIndex());
                Log.d("question", "onCreate: "+question.toString());
                jsonAnswers = question.getJSONArray("answers");
                loadvalues();
            }
            catch (Exception e)
            {

            }

        }
    }
    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }
    public void colorize(View view)
    {
        view.setBackgroundColor(Color.rgb(170,255,170));
        try
        {
            question.put("correctAnswer", view.getTag());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for (EditText answer:answers)
        {
            if(view==answer)
            {

            }
            else
            {
                answer.setBackground(null);
            }
        }

    }
    public void saveValues()
    {
        try
        {
            question.put("name", editQuestionName.getText());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        for (int i = 0 ;i<answers.length;i++)
        {
            try
            {
                jsonAnswers.put(i, answers[i].getText());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        LocalStorage.commit();
    }
    public void loadvalues()
    {
        try
        {
            Picasso.with((getApplication())).load(new File(question.getString("picture"))).into(imageView);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            editQuestionName.setText(question.getString("name"));
            for (int i = 0; i < answers.length ; i++)
            {
                String answer = jsonAnswers.getString(i);
                answers[i].setText(answer);
            }
            if (!question.isNull("correctAnswer"))
            {
                answers[question.getInt("correctAnswer")].setBackgroundColor(Color.rgb(170, 255, 170));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == Activity.RESULT_OK)
        {
            // String path = data.getData().getPath(); // "/mnt/sdcard/FileName.mp3"
            final Uri imageUri = data.getData();
            Log.d("path", "onActivityResult: "+getPath(imageUri));
            try
            {
                question.put("picture", getPath(imageUri));
            }
            catch (Exception e)
            {

            }
            LocalStorage.commit();
            Picasso.with((getApplication())).load(imageUri).into(imageView);

            //   Worker worker = new Worker(imageView,data,);
            //  worker.start();
        }


      /*      else
            {
            try
            {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                Log.d("data", "onActivityResult: "+data.getData());

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            } */
        //  }

        //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
    }
    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
    public void deleteQuestion(View view)
    {
        if(QuizBuilderActivity.isEdit())
        {
            try
            {
                quiz.getJSONArray("questions").remove(QuizBuilderActivity.getIndex());
                LocalStorage.commit();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finish();
        }
        else
        {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        saveValues();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed()
    {
        saveValues();
        super.onBackPressed();
    }
}
