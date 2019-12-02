package com.example.findyourprivategrandpa.controllerinterfaces;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.findyourprivategrandpa.MainActivity;

import java.net.HttpURLConnection;
import java.net.URL;
public class HostResolver
{
    /*diese Klasse stellt fest, ob einer der hosts in der List hosts
    im lokalen Netzwerk ist... falls ja, gibt sie die URL des Hosts zurück.
     */
    private class AddressGetter extends Thread
    {
        private final String[] hosts={"adislav-pc","bacosck"};
        String address;
        @Override
        public void run()
        {
            getNetwork();
        }
        public void getNetwork()
        {
            for (String host: hosts) {
                try
                {

                    URL url = new URL("http://" + host);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(100);
                    connection.setRequestMethod("HEAD");
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200)
                    {
                        this.address ="http://"+host+"/";
                        return;
                    }

                }
                catch (Exception e)
                {
                    System.out.println("penis");
                }

            }
            getNetworkByIp();
        }
        public void getNetworkByIp()
        {
            for (int i=0;i<255;i++) {
                try
                {

                    String host=MainActivity.getIp()+"."+i;
                    System.out.println(host);
                    URL url = new URL("http://" + host);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(100);
                    connection.setRequestMethod("HEAD");
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200)
                    {
                        this.address ="http://"+host+"/";
                        return;
                    }

                }
                catch (Exception e)
                {
                    System.out.println("penis");
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