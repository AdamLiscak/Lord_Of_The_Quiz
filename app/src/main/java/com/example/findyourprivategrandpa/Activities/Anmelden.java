package com.example.findyourprivategrandpa.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.findyourprivategrandpa.R;

public class Anmelden extends AppCompatActivity {

    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anmelden);

        button =findViewById(R.id.anmelden);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivitystart();
            }
        });

    }

    public void openActivitystart(){
        Intent intent = new Intent(this,Start.class);
        startActivity(intent);
    }
    public void openActivityRegister(View view){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

}
