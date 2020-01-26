package com.example.findyourprivategrandpa.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.findyourprivategrandpa.Models.Me;
import com.example.findyourprivategrandpa.R;
import com.example.findyourprivategrandpa.localStorage.LocalStorage;

public class RegisterActivity extends AppCompatActivity
{
    EditText password;
    EditText userName;
    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = findViewById(R.id.editText_register_name);
        password = findViewById(R.id.editText_register_password);
        status = findViewById(R.id.textView_register_status);

    }
    public void register(View view)
    {
        String user = userName.getText().toString();
        int numStatus = Me.register(user,password.getText().toString());
        if (numStatus==200)
        {
            LocalStorage.changeProperty("username",user);
            LocalStorage.commit();
            Intent intent = new Intent(this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if(numStatus==600)
        {
            status.setText("Nutzer Existiert schon, bitte einen anderen Namen wählen");
        }
        else if(numStatus==500)
        {
            status.setText("internal server error");
        }
    }
}
