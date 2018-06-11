package com.example.utente.esercizio1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by utente on 24/03/2018.
 */

public class ShowResult  extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        Intent intent = getIntent();

        String number = intent.getStringExtra("finalNumber");
        String tries = intent.getStringExtra("triesNumber");
        String gameTime = intent.getStringExtra("gameTime");

        TextView winLabel1 = findViewById(R.id.winView);

        winLabel1.setText("complimenti hai vinto!!!! \n Il numero corretto è " + number + "|n\n numero di tentativi: "+tries + "\n secondi di gioco: " + gameTime +" ");
    }
}
