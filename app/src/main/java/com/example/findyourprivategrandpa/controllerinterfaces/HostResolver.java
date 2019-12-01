package com.example.findyourprivategrandpa.controllerinterfaces;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
public class HostResolver
{
    /*diese Klasse stellt fest, ob einer der hosts in der List hosts
    im lokalen Netzwerk ist... falls ja, gibt sie die URL des Hosts zurück.
     */
    private class AddressGetter extends Thread
    {
        private final String[] hosts={"adislav-pc","bacock","osboxes"};
        String address;
        @Override
        public void run()
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
                        this.address ="http://"+host+"/";
                        break;
                    }
                }
                catch (Exception e)
                {
                    Log.d("NoNetwork", "run: ");
                }

            }
        }
        public String getdAdress()
        {
            return this.address;
        }
    }
    public String findHost()
    {

        AddressGetter addressGetter = new AddressGetter();
        addressGetter.start();
        try {
            addressGetter.join();
        }
        catch (Exception e)
        {

        }
        return addressGetter.getdAdress();
    }

}