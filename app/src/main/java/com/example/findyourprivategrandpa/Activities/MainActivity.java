package com.example.findyourprivategrandpa.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.findyourprivategrandpa.Models.Me;
import com.example.findyourprivategrandpa.R;
import com.example.findyourprivategrandpa.localStorage.FileParser;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

import java.io.File;

public class MainActivity extends AppCompatActivity
{
    private static String ip;
    private static File rootDataDir;
    private static File localStorage;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initiateVars();
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permissions, 0);
        }
        if(LocalStorage.isNull("username"))
        {
            Intent intent = new Intent(this, Anmelden.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    public void initiateVars()
    {
        WifiManager wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        ip = String.format("%d.%d.%d", (ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff));
        rootDataDir = getApplication().getFilesDir();
        localStorage = new File(rootDataDir.toString()+"/localStorage.json");
        if (!localStorage.exists()) {
            try {
                localStorage.createNewFile();
                FileParser.write(localStorage,"{}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("initiatievars", "initiateVars: "+FileParser.read(localStorage));

       LocalStorage.initiate();
     // LocalStorage.changeProperty("penis","small");
        if(LocalStorage.getConfiguration()!=null)
        {
            Log.d("cockvars", "initiateVars: " + LocalStorage.getConfiguration().toString());
        }
        else
        {
            FileParser.write(localStorage,"{}");
        }
    }
    public static String getIp()
    {
        return ip;
    }

    public static File getLocalStorage()
    {
        return localStorage;
    }
    public static void setLocalStorage(File localStorage)
    {
        MainActivity.localStorage = localStorage;
    }
    public void logout(View view)
    {
        Me.logout();
        Intent intent = new Intent(this,Anmelden.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
}
