package com.example.findyourprivategrandpa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.findyourprivategrandpa.controllerinterfaces.get.DownloadImageTask;
import com.example.findyourprivategrandpa.controllerinterfaces.get.FileStringifier;
import com.example.findyourprivategrandpa.controllerinterfaces.HostResolver;
import com.example.findyourprivategrandpa.controllerinterfaces.post.AsyncRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostRequestBuilder;
import com.example.findyourprivategrandpa.controllerinterfaces.post.StringPoster;

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
        final  String hostUrl= new HostResolver().findHost();
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
        Log.d("penis ster create", "onCreate: "+name);


                PostRequestBuilder pb=new PostRequestBuilder();
                pb.addEntry("to","Jenny");
                pb.addEntry("from","Bacock");
                String data=pb.getRequests();
               StringPoster stringPoster =new StringPoster(hostUrl+"lord_of_the_quiz_backend/testing/index.php",data);
               stringPoster.post();
                AsyncRequest asr=new AsyncRequest(hostUrl+"lord_of_the_quiz_backend/testing/index.php",data);
        Log.d("Response Cock", "onCreate: "+asr.getResponse());

    }
}
