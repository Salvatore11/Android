package com.informatica.unipr.provaesame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputPassword;

    private Button bottoneAccedi;

    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsername = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);


        bottoneAccedi = (Button) findViewById(R.id.accediButton);

        bottoneAccedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accedi();
            }
        });

        register = (TextView) findViewById(R.id.forgot_password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginActivity.this, recuperoPassword.class);
                startActivity(intent2);
            }
        });
    }

    private void accedi() {

        boolean canLogin = true;
        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();

        if (!username.equals("") && !isUsernameVerified(username)) {
            inputUsername.setError("username errata");
            canLogin=false;
        }
        else if (!password.equals("") && !isPasswordVerified(password)) {
            inputPassword.setError("password errata");
            canLogin= false;
        }

        else if (username.equals("")){
            inputUsername.setError("username obbligatoria");
            canLogin=false;
        }
        else if(password.equals("")){
            inputPassword.setError("campo obbligatorio");
            canLogin=false;
        }

        else if ( username.equals("bello") && password.equals("mio")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else
            Toast.makeText(LoginActivity.this, "minchia ma perchè non funziona?", Toast.LENGTH_SHORT).show();
    }


    public boolean isUsernameVerified(String username) {

        return true;
    }

    public boolean isPasswordVerified(String password) {

        return true;
    }

}
