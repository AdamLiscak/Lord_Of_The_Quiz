package com.example.findyourprivategrandpa.localStorage;

import android.util.Log;

import com.example.findyourprivategrandpa.Activities.MainActivity;

import org.json.JSONArray;
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
    public static String getString(String key)
    {
        try
        {
            return config.getString(key);
        }
        catch (Exception e)
        {
        }
        return null;
    }
    public static void commit()
    {
        FileParser.write(MainActivity.getLocalStorage(),config.toString());
    }
    public static void changeProperty(String key, Object value)
    {
        try
        {
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
    public static JSONObject getJSONObject(String key)
    {
        try
        {
            return config.getJSONObject(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONObject removeJSON(String key)
    {
        try
        {
            JSONObject jsonObject=config.getJSONObject(key);
            config.remove(key);
            return jsonObject;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static void remove(String key)
    {
        config.remove("username");
    }
    public static String removeString(String key)
    {
        try
        {
            String jsonObject=config.getString(key);
            config.remove(key);
            return jsonObject;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static JSONArray getJSONArray(String key)
    {
        try
        {
            return config.getJSONArray(key);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public static boolean isNull(String property)
    {
        return config.isNull(property);
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
