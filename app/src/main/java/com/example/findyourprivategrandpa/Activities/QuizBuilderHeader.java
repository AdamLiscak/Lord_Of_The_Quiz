package com.example.findyourprivategrandpa.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.findyourprivategrandpa.Models.QuizBuilder;
import com.example.findyourprivategrandpa.R;
import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.ImageUploader;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostMessageBuilder;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import static com.example.findyourprivategrandpa.Urls.QUESTION_PICTURE_UPLOAD_URL;
import static com.example.findyourprivategrandpa.Urls.QUIZ_BUILDER_EXPORT_URL;
import static com.example.findyourprivategrandpa.Urls.QUIZ_THUMBNAIL_UPLOAD_URL;

public class QuizBuilderHeader extends AppCompatActivity
{
    private ImageView imageView;
    private EditText editText;
    private Button questions;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_builder_header);
        imageView = findViewById(R.id.quiz_header_image_view);
        editText = findViewById(R.id.quiz_builder_header_edit_text);
        if(LocalStorage.isNull("quiz"))
        {
            LocalStorage.changeProperty("quiz",new JSONObject());
            try
            {
                JSONObject quiz = LocalStorage.getJSONObject("quiz");
                quiz.put("questions", new JSONArray());
                quiz.put("author",LocalStorage.getProperty("username"));
                LocalStorage.commit();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                JSONObject quiz = LocalStorage.getJSONObject("quiz");
                String thumbnail = quiz.getString("thumbnail");
                Picasso.with((getApplication())).load(new File(thumbnail)).into(imageView);
                editText.setText(quiz.getString("name"));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

    }
    public void pickImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
           // String path = data.getData().getPath(); // "/mnt/sdcard/FileName.mp3"
            final Uri imageUri = data.getData();
            JSONObject quiz = LocalStorage.getJSONObject("quiz");
            try
            {
                quiz.put("thumbnail", getPath(imageUri));
                LocalStorage.commit();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                Log.d("hure", getPath(imageUri));
            }
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

    @Override
    public void onBackPressed()
    {
        try
        {
            LocalStorage.getJSONObject("quiz").put("name",editText.getText());
            LocalStorage.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onBackPressed();
    }
    public void editQuestions(View view)
    {
        try
        {
            LocalStorage.getJSONObject("quiz").put("name",editText.getText());
            LocalStorage.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, QuizBuilderActivity.class);
        startActivity(intent);
    }
    public void uploadQuestions(View view)
    {
        JSONObject quiz = LocalStorage.getJSONObject("quiz");
        try
        {
            JSONArray questions = quiz.getJSONArray("questions");
            PostMessageBuilder pm = new PostMessageBuilder();
            pm.addEntry("quiz", LocalStorage.getString("quiz"));
            pm.addEntry("author", LocalStorage.getString("author"));
            BidirectionalRequest br = new BidirectionalRequest(QUIZ_BUILDER_EXPORT_URL+"?"+pm.getValues(),"");
            // JSONObject response = new JSONObject(br.getResponse());
            //   JSONArray qIDs=response.getJSONArray("qIDs");
            int quizID = Integer.parseInt(br.getResponse());
            PostMessageBuilder pBuilder = new PostMessageBuilder();
            pBuilder.addEntry("quizID", "" + quizID);
            pBuilder.addEntry("picture", "-1");
            ImageUploader iup = new ImageUploader(quiz.getString("thumbnail"), QUESTION_PICTURE_UPLOAD_URL+"?"+pBuilder.getValues(),"");
            iup.uploadImage();
            for (int i = 0; i < questions.length(); i++)
            {
                PostMessageBuilder postBuilder = new PostMessageBuilder();
                postBuilder.addEntry("quizID", "" + quizID);
                postBuilder.addEntry("picture", "" + i);
                ImageUploader iu = new ImageUploader(((JSONObject) questions.get(i)).getString("picture"), QUESTION_PICTURE_UPLOAD_URL+"?"+postBuilder.getValues(),"");
                iu.uploadImage();
            }
            finish();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
       LocalStorage.removeString("quiz");
    }
}
