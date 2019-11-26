package com.example.findyourprivategrandpa;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

public class FileDownloader {
    private class FileServant extends Thread
    {
        private String myJson;
        @Override
        public void run()
        {
            try {
                InputStream is = new URL(new HostResolver().findHost()+"lord_of_the_quiz_backend/grandpa.json").openStream();
                int size = is.available();
                Log.d("Essert", "a"+size);
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                myJson = new String(buffer, "UTF-8");
                //JSONObject obj = new JSONObject(myJson);
               // Log.d("grandpa.json", "download: "+myJson);
            }
            catch (Exception e)
            {
                Log.d("Small COCK",e.toString());
            }
        }
        public String getJSONString()
        {
            return myJson;
        }

    }
    public File download()
    {
        FileServant f=new FileServant();
        f.start();
        try {
            f.join();
        }
        catch (Exception e)
        {

        }
        Log.d("cock grandpa", "download: "+f.getJSONString());
        return null;
    }
}
