package com.example.findyourprivategrandpa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.findyourprivategrandpa.ControllerInterfaces.DownloadImageTask;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bmp;
        ImageView imageView=findViewById(R.id.imageView);
        DownloadImageTask downloadImageTask=new DownloadImageTask(imageView);
        downloadImageTask.execute("http://adislav-pc/lord_of_the_quiz_backend/chadDaniels.jpg");

    }
}
