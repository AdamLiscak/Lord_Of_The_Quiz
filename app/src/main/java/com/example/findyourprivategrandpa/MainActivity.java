package com.example.findyourprivategrandpa;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.findyourprivategrandpa.Models.Quiz;
import com.example.findyourprivategrandpa.controllerinterfaces.get.DownloadImageTask;
import com.example.findyourprivategrandpa.controllerinterfaces.get.FileStringifier;
import com.example.findyourprivategrandpa.controllerinterfaces.post.BidirectionalRequest;
import com.example.findyourprivategrandpa.controllerinterfaces.post.ImageUploader;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostMessageBuilder;
import com.example.findyourprivategrandpa.controllerinterfaces.post.PostRequest;
import com.example.findyourprivategrandpa.localStorage.FileParser;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import org.json.JSONObject;

import java.io.File;

import static com.example.findyourprivategrandpa.Urls.HOST_URL;
import static com.example.findyourprivategrandpa.Urls.MOCKUP_URL;
import static com.example.findyourprivategrandpa.Urls.QUESTION_IMAGE_URL;
import static com.example.findyourprivategrandpa.localStorage.LocalStorage.commit;

public class MainActivity extends AppCompatActivity
{
    private static String ip;
    private static File rootDataDir;
    private static File localStorage;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initiateVars();
        setContentView(R.layout.activity_main);
        Bitmap bmp;
        ImageView imageView=findViewById(R.id.imageView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, 0);
        }
        DownloadImageTask downloadImageTask=new DownloadImageTask(imageView);
        Log.d("host", "onCreate: "+ HOST_URL);
        downloadImageTask.execute(QUESTION_IMAGE_URL);
        String json=new FileStringifier(HOST_URL +"lord_of_the_quiz_backend/download/grandpa.json").stringify();
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
        //        BidirectionalRequest asr=new BidirectionalRequest(HOST_URL +"lord_of_the_quiz_backend/testing/mockup/mockup.php",data);
      //  Log.d("Response Cock", "onCreate: "+asr.getResponse());
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
    public void initiateVars()
    {
        WifiManager wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        ip = String.format("%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff));
        rootDataDir = getApplication().getFilesDir();
        localStorage = new File(rootDataDir.toString()+"/localStorage.json");
        if (!localStorage.exists()) {
            try {
                localStorage.createNewFile();
                FileParser.write(localStorage,"{}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("initiatievars", "initiateVars: "+FileParser.read(localStorage));

       LocalStorage.initiate();
     // LocalStorage.changeProperty("penis","small");
        if(LocalStorage.getConfiguration()!=null)
        {
            Log.d("cockvars", "initiateVars: " + LocalStorage.getConfiguration().toString());
        }
        else
        {
            FileParser.write(localStorage,"{}");
        }
       PostMessageBuilder pm = new PostMessageBuilder();
        pm.addEntry("user","bacock");
       ImageUploader iu= new ImageUploader("/storage/emulated/0/Download/myfile.jpg",MOCKUP_URL,pm.getValues());
       iu.uploadImage();
    }
    public static String getIp()
    {
        return ip;
    }

    public static File getLocalStorage()
    {
        return localStorage;
    }
    public static void setLocalStorage(File localStorage)
    {
        MainActivity.localStorage = localStorage;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
