package com.example.quiz_design;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
}
