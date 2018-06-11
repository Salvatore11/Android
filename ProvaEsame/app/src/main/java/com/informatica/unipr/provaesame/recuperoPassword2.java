package com.informatica.unipr.provaesame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class recuperoPassword2 extends AppCompatActivity {

    private EditText iEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupero_password2);


        iEmail=(EditText)findViewById(R.id.email);

        Intent intent=getIntent();

        String dEmail= intent.getStringExtra("email");

        iEmail.setText(dEmail);
        Toast.makeText(this,dEmail,Toast.LENGTH_SHORT).show();
    }
}
