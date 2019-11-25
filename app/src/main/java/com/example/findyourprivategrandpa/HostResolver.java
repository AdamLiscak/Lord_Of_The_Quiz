package com.example.findyourprivategrandpa;

import java.net.HttpURLConnection;
import java.net.URL;

public class HostResolver
{
    private final String[] hosts={"adislav-pc","bacock","JuliPC"};
    public String findHost()
    {
        for (String host: hosts) {
            try
            {
                URL url = new URL("http://" + host);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();
                if (responseCode == 200)
                {
                    return "http://"+host+"/";
                }
            }
            catch (Exception e)
            {

            }
        }
        return null;
    }

}
