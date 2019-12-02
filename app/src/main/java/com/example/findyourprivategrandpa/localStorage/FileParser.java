package com.example.findyourprivategrandpa.localStorage;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileParser
{
    public static String read(File file)
    {
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try
        {
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
        }
        catch (Exception e)
        {
            Log.d("Filepenis", "read: "+e.toString());
        }
        return new String(bytes);
    }
    public static void write(File file, String text)
    {
        try
        {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(text.getBytes());
            stream.close();
        }
        catch (Exception e)
        {
            Log.d("Filepenis", "read: "+e.toString());
        }
        }
    public static void append(File file, String text)
    {
        write(file,read(file)+text);
    }
    public static void prepend(File file, String text)
    {
        write(file,text+read(file));
    }
}
