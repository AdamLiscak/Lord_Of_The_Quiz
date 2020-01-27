package com.example.findyourprivategrandpa.controllerinterfaces;
import android.util.Log;

import com.example.findyourprivategrandpa.Activities.MainActivity;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.findyourprivategrandpa.localStorage.LocalStorage.commit;

public class HostResolver
{
    /*diese Klasse stellt fest, ob einer der hosts in der List hosts
    im lokalen Netzwerk ist... falls ja, gibt sie die URL des Hosts zurück.
     */
    private class AddressGetter extends Thread
    {
        private final String[] hosts={"adislav-pc","bacock","178.191.76.222"};
        String address;
        @Override
        public void run()
        {
            getNetwork();
        }
        private void getNetwork()
        {
            for (String host: hosts) {
                try
                {

                    URL url = new URL("http://" + host);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(500);
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
            getCachedNetwork();
        }
        private void getCachedNetwork()
        {
            String host=null;
            try
            {
                host = (String)LocalStorage.getProperty("ip");
            }
            catch (Exception e)
            {
                Log.d("Network", "getCachedNetwork: Network Caching failed "+e.toString());
            }
            try
            {
                System.out.println(host);
                URL url = new URL("http://" + host);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(500);
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();
                if (responseCode == 200)
                {
                    this.address = "http://" + host + "/";
                    return;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            getNetworkByIp();
        }
        private void getNetworkByIp()
        {
            for (int i=0;i<255;i++) {
                try
                {

                    String host=MainActivity.getIp()+ "."+i;
                    System.out.println(host);
                    URL url = new URL("http://" + host);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(500);
                    connection.setRequestMethod("HEAD");
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200)
                    {
                        LocalStorage.changeProperty("ip",MainActivity.getIp()+"."+i);
                        commit();
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