package com.example.findyourprivategrandpa.localStorage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class ImageParser {
    private final String path;
    public ImageParser(String path)
    {
        this.path=path;
    }
    private class Worker extends Thread
    {
        Bitmap bitmap;
        @Override
        public void run() {
            File imgFile = new File(path);

            if (imgFile.exists()) {

              bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            }
        }
        public Bitmap getBitmap()
        {
            return bitmap;
        }
    }
    public  Bitmap returnImage()
    {
        Worker worker= new Worker();
        worker.start();
        try {
            worker.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return worker.getBitmap();
    }

}
