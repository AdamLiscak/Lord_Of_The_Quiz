package com.example.findyourprivategrandpa.controllerinterfaces.post;

import java.net.URLEncoder;

public class PostRequestBuilder
{
    private StringBuilder requests;
    public PostRequestBuilder()
    {
        requests=new StringBuilder();
    }
    public void addEntry(String key, String value)
    {
        try
        {
            requests.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
        }
        catch (Exception e)
        {

        }
    }
    public String getRequests()
    {
        return this.requests.deleteCharAt(requests.length()-1).toString();
    }
}
