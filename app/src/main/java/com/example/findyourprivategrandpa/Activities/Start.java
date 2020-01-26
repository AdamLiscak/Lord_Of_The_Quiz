package com.example.findyourprivategrandpa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findyourprivategrandpa.Models.Me;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

public class Start extends AppCompatActivity {

    EditText userName;
    EditText password;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button go = findViewById(R.id.go);
        userName = findViewById(R.id.editText_username);
        password = findViewById(R.id.editText_password);
        status = findViewById(R.id.textView_status);
    }
    public void checkPassword(View view)
    {
        final String zeroSuccess= "Login nicht erfolgreich";
        try
        {
            String user = userName.getText().toString();
            boolean success = Me.login(user,password.getText().toString());
            if(success)
            {
                LocalStorage.changeProperty("username",user);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                status.setText(zeroSuccess);
            }
        }
        catch (Exception e)
        {
            status.setText(zeroSuccess);
            e.printStackTrace();
        }
    }
}
