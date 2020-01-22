package com.example.findyourprivategrandpa.controllerinterfaces.post;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.findyourprivategrandpa.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class ImageUploader
{
    private final String uri;
    private final String url;
    private final String params;
    public ImageUploader(String uri, String url, String params)
    {
        this.uri=uri;
        this.url=url;
        this.params=params;
    }
    private class Worker extends Thread
    {
        @Override
        public void run()
        {
            uploadBitmap();
        }

        public void uploadBitmap()
        {
            Bitmap bitmap = BitmapFactory.decodeFile(uri);

            try
            {
                URL urlAddress = new URL(url + "?" + params);
                HttpURLConnection c = (HttpURLConnection) urlAddress.openConnection();
                c.setRequestProperty("Content-Type", "image/jpeg");
               // c.setDoInput(true);
                c.setRequestMethod("POST");
                c.setDoOutput(true);
                c.connect();
                OutputStream output = c.getOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, output);
                int code = c.getResponseCode();
                output.close();

                /* Scanner result = new Scanner(c.getInputStream());
                String response = result.nextLine();
                Log.e("ImageUploader", "Error uploading image: " + response);
                result.close(); */
                Log.e("ImageUploader", "uploadBitmap: sucess"+code);
            } catch (Exception e)
            {
                Log.e("ImageUploader", "Error uploading image", e);
            }

        }
    }

        public void uploadImage()
        {
            Worker worker = new Worker();
            worker.start();
            try
            {
                worker.join();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
}
