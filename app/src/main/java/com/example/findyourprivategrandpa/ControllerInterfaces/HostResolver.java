package com.example.findyourprivategrandpa.ControllerInterfaces;
import java.net.HttpURLConnection;
import java.net.URL;
public class HostResolver
{
    private class AddressGetter extends Thread
    {
        private final String[] hosts={"adislav-pc","bacock","JuliPC"};
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