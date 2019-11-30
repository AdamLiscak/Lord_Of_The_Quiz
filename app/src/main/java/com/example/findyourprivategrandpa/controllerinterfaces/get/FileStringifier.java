package com.example.findyourprivategrandpa.controllerinterfaces.get;

import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class FileStringifier {
    private final String url;
    public FileStringifier(String url)
    {
        this.url=url;
    }
    private class FileServant extends Thread
    {
        private String myJson;
        @Override
        public void run()
        {
            try {
                InputStream is = new URL(url).openStream();
                int size = is.available();
                Scanner s = new Scanner(is).useDelimiter("\\A");
                myJson = s.hasNext() ? s.next() : "";
                //JSONObject obj = new JSONObject(myJson);
               // Log.d("grandpa.json", "stringify: "+myJson);
            }
            catch (Exception e)
            {
                Log.d("Small COCK",e.toString());
            }
        }
        public String getString()
        {
            return myJson;
        }

    }
    public String stringify()
    {
        FileServant f=new FileServant();
        f.start();
        try {
            f.join();
        }
        catch (Exception e)
        {

        }
        Log.d("cock grandpa", "stringify: "+f.getString());
        return  f.getString();
    }
    public JSONObject JSONify() throws Exception
    {
        return new JSONObject(stringify());
    }
}
