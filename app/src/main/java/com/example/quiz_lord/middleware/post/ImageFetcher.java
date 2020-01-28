package com.example.quiz_lord.middleware.post;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class ImageFetcher
{
    final String urlAddress;
    final String params;
    public ImageFetcher(String urlAddress, String params)
    {
        this.urlAddress=urlAddress;
        this.params=params;
    }
    private class Worker extends Thread
    {
        private Bitmap bitmap;
        @Override
        public void run() {
            Bitmap bmp = null;
            try {
                URL url= new URL(urlAddress);
                URLConnection con = url.openConnection();
                HttpURLConnection http = (HttpURLConnection) con;
                http.setRequestMethod("POST"); // PUT is another valid option
                http.setDoOutput(true);
                //  String param1 = URLEncoder.encode("to", "UTF-8") + "=" + URLEncoder.encode("root", "UTF-8");
                // String param2 = URLEncoder.encode("from", "UTF-8") + "=" + URLEncoder.encode("root", "UTF-8");
                // String params = param1 + "&" + param2;
                byte[] out = params.getBytes(StandardCharsets.UTF_8);
                int length = out.length;
                Log.d("LengthWiener", "run: "+length);
                http.setFixedLengthStreamingMode(length);
                http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                BufferedOutputStream os= new BufferedOutputStream(http.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(params);
                writer.flush();
                writer.close();
                os.close();
                http.connect();

                InputStream in = url.openStream();
                Log.d("wiener", "doInBackground: "+in.available());
                bmp = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("ErrorWiener", e.getMessage());
                e.printStackTrace();
            }
            this.bitmap=bmp;
        }
        public Bitmap getBitmap()
        {
            return bitmap;
        }
    }
    public Bitmap getImage()
    {
        Worker worker=new Worker();
        worker.start();
        try
        {
            worker.join();
        }
        catch (Exception e)
        {

        }
        return worker.getBitmap();
    }

}
