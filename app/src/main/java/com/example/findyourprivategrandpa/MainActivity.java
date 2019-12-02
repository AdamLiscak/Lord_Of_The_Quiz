package com.example.findyourprivategrandpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findyourprivategrandpa.Models.Quiz;
import com.example.findyourprivategrandpa.controllerinterfaces.get.DownloadImageTask;
import com.example.findyourprivategrandpa.controllerinterfaces.get.FileStringifier;
import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostMessageBuilder;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostRequest;

import org.json.JSONObject;

import static com.example.findyourprivategrandpa.Urls.HOST_URL;

public class MainActivity extends AppCompatActivity
{
    private static String ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WifiManager wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        ip = String.format("%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff));
        setContentView(R.layout.activity_main);
        Bitmap bmp;
        ImageView imageView=findViewById(R.id.imageView);
        DownloadImageTask downloadImageTask=new DownloadImageTask(imageView);
        Log.d("host", "onCreate: "+ HOST_URL);
        downloadImageTask.execute((HOST_URL +"lord_of_the_quiz_backend/chadDaniels.jpg"));
        String json=new FileStringifier(HOST_URL +"lord_of_the_quiz_backend/grandpa.json").stringify();
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


                PostMessageBuilder pb=new PostMessageBuilder();
                pb.addEntry("to","Jenny");
                pb.addEntry("from","Bacock");
                String data=pb.getValues();
               PostRequest postRequest =new PostRequest(HOST_URL +"lord_of_the_quiz_backend/testing/index.php",data);
               postRequest.post();
                BidirectionalRequest asr=new BidirectionalRequest(HOST_URL +"lord_of_the_quiz_backend/testing/mockup/mockup.php",data);
        Log.d("Response Cock", "onCreate: "+asr.getResponse());
        try
        {
            Quiz quiz = new Quiz(0);
            quiz.start();
            Log.d("Quiz succesful", "onCreate: "+quiz.toString());
        }
        catch (Exception e)
        {
            Log.d("Quiz unsuccesful", "onCreate: "+e.toString());
        }
        Toast toast= Toast.makeText(getApplicationContext(),ip,Toast.LENGTH_SHORT);
        toast.show();

    }
    public static String getIp()
    {
        return ip;
    }
}
