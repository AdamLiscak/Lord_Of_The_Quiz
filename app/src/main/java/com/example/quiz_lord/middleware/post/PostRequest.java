package com.example.quiz_lord.middleware.post;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;


public class PostRequest
{
    /*
    Diese Klasse baut PostRequests auf. Params muss von PostMessageBuilder geliefert werden.
     */
    private String urlAddress;
    private String params;
    public PostRequest(String urlAddress, String params)
    {
        this.urlAddress=urlAddress;
        this.params = params;
    }
    private class Worker extends Thread
    {
        private String text;
        @Override
        public void run()
        {
            try {
                URL url = new URL(urlAddress);
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

            }
            catch (Exception e)
            {

            }
        }
    }
    public void post()
    {
        Worker worker= new Worker();
        worker.start();
        try {
            worker.join();
        }
        catch (Exception e)
        {

        }

    }
}