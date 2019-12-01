package com.example.findyourprivategrandpa.controllerinterfaces.post;

import java.net.URLEncoder;

public class PostMessageBuilder
{
    /*
    Diese Klasse baut Nachrichten im Key/Value Format, die vom Server lesbar sind.
    Diese Klasse ist nötig fpr PostRequests und AsyncRequests.
     */
    private StringBuilder values;
    public PostMessageBuilder()
    {
        values =new StringBuilder();
    }
    public void addEntry(String key, String value)
    {
        try
        {
            values.append(URLEncoder.encode(key, "UTF-8")).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
        }
        catch (Exception e)
        {

        }
    }
    public String getValues()
    {
        return this.values.deleteCharAt(values.length()-1).toString();
    }
}
