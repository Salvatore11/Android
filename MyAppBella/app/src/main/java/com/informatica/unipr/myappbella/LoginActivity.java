package com.informatica.unipr.myappbella;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private EditText mUsernameView;
    private EditText mPasswordView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // don't show keyboard onCreate()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_login);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //setto le mie EditText
        mUsernameView= (EditText)findViewById(R.id.username);
        mPasswordView= (EditText)findViewById(R.id.password);

        mPasswordView.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                // hide keyboard
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                attemptLogin();
                return true;
            }
        });

        Button bottone_login= (Button)findViewById(R.id.login_button);
        bottone_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                attemptLogin();
            }
        });

    }

    private void attemptLogin(){

        boolean canLogin = true;

        mUsernameView.setError(null);
        mPasswordView.setError(null);

        final String username= mUsernameView.getText().toString();
        final String password= mPasswordView.getText().toString();

        if(!TextUtils.isEmpty(username) && !isPasswordValid(password)){
            mPasswordView.setError("errore password invalida");
        }

        if(TextUtils.isEmpty(username)){
            mUsernameView.setError("email obbligatoria");
            canLogin= false;
        }
        else if(!isEmailValid(username)){
            mUsernameView.setError("username non valida");
            canLogin= false;
        }

        if(canLogin){
            doLogin(username,password);
        }
    }

    private boolean isPasswordValid(String valore){
        return true;
    }
    private boolean isEmailValid(String valore){
        return true;
    }

    private void doLogin(String username, String password){
        if(username.equals("salvo")&& password.equals("turuzzu")){
            Intent intent= new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(LoginActivity.this, "credenziali Errate", Toast.LENGTH_SHORT).show();
        }
    }
}
