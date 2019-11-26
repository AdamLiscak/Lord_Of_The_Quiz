package com.example.findyourprivategrandpa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.findyourprivategrandpa.ControllerInterfaces.DownloadImageTask;
import com.example.findyourprivategrandpa.ControllerInterfaces.FileStringifier;
import com.example.findyourprivategrandpa.ControllerInterfaces.HostResolver;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bitmap bmp;
        ImageView imageView=findViewById(R.id.imageView);
        DownloadImageTask downloadImageTask=new DownloadImageTask(imageView);
        String hostUrl= new HostResolver().findHost();
        Log.d("host", "onCreate: "+hostUrl);
        downloadImageTask.execute((hostUrl+"lord_of_the_quiz_backend/chadDaniels.jpg"));
        String json=new FileStringifier(hostUrl+"lord_of_the_quiz_backend/grandpa.json").stringify();
        JSONObject jsonObject=null;
        String name="";
        try
        {
            jsonObject= new JSONObject(json);
            JSONObject grandpa=  jsonObject.getJSONObject("grandpa");
            name=grandpa.getString("name");
        }
        catch (Exception e)
        {
        }
        Log.d("penis cock create", "onCreate: "+name);




    }
}
