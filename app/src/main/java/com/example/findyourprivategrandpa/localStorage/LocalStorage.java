package com.example.findyourprivategrandpa.localStorage;

import android.util.Log;

import com.example.findyourprivategrandpa.MainActivity;

import org.json.JSONObject;

public class LocalStorage
{

    private static JSONObject config;
    public static void initiate()
    {
        try
        {
            config=new JSONObject(FileParser.read(MainActivity.getLocalStorage()));
        }
        catch (Exception e)
        {
            Log.d("ConfigShit", "getConfig: "+e.toString());
        }
    }
    public static void setConfig()
    {
        FileParser.write(MainActivity.getLocalStorage(),config.toString());
    }
    public static void changeProperty(String key, String value)
    {
        try
        {
            config.remove(key);
            config.put(key,value);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static Object getProperty(String key)
    {
        try
        {
            return config.get(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONObject getConfiguration()
    {
        return config;
    }
    @Override
    protected void finalize() throws Throwable
    {
        super.finalize();
    }
}
