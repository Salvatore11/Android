package com.informatica.unipr.esercizio2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

// schermata di login con username e Password

public class LoginActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputPassword;

    private SharedPreferences loginData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginData= getSharedPreferences("login", 0);
        String username= loginData.getString("username", null);
        String password= loginData.getString("password", null);

        //non mostra la tastiera onCreate()
      //  getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_login);
        setTitle(getString(R.string.app_name));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //imposto il modulo di login
        inputUsername = (EditText) findViewById(R.id.username);

        inputPassword = (EditText) findViewById(R.id.password);

        //Imposta un listener speciale da chiamare quando un'azione viene eseguita sulla vista testo.
        //Questo verrà chiamato quando viene premuto il tasto Invio o quando viene selezionata un'azione da parte dell'utente.
        inputPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
             @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                //nascondere la tastiera
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                tentativoLogin();
                return true;
            }
        });

        // setto il bottone di login
        Button bottoneLogin = (Button) findViewById(R.id.Login);
        bottoneLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                tentativoLogin();
            }
        });


        //BOTTONE CANCELLA

        Button bottoneCancella = (Button) findViewById(R.id.Cancella);
        bottoneCancella.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                inputUsername.setText("");
                inputPassword.setText("");
            }
        });
    }

        /*
        Tenta di accedere o registrare l'account specificato dal modulo di accesso.
        Se sono presenti errori di modulo (nome utente non valido, campi mancanti, ecc.), Vengono visualizzati
        gli errori e non viene effettuato alcun tentativo di accesso effettivo.
        */

    private void tentativoLogin() {

        boolean canLogin = true; // variabile che mi dice che posso fare entrare l'utente

        //Reset errori
        inputUsername.setError(null);
        inputPassword.setError(null);

        //memorizza i valori di username e password al momento del tentativo di accesso
        final String username = inputUsername.getText().toString();
        final String password = inputPassword.getText().toString();


        //Verifica la presenza di una password valida, se l'utente ne ha inserito uno.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            inputPassword.setError(getString(R.string.errore_password_invalida));
        }

        // Verifica se il campo username è stato compilato
        if (TextUtils.isEmpty(username)) {
            inputUsername.setError(getString(R.string.errore_username_obbligatoria));
            canLogin = false;
        } else if (!isEmailValid(username)) {
            inputUsername.setError(getString(R.string.errore_username_invalido));
            canLogin = false;
        }

        if ((username != null && password != null) && (canLogin)) {
            faiLogin(username, password);
        }
    }

    private boolean isPasswordValid(String valore) {
        return true;
    }

    private boolean isEmailValid(String valore) {
        return true;
    }

    //COLLEGAMENTO AL SERVER

    private void faiLogin(final String username, final String password)
    {
        try{
            JSONObject params = new JSONObject(); //Un insieme modificabile di mappature nome / valore. I nomi sono stringhe univoche e non null.
            params.put("comando", "login");
            params.put("username", username);
            params.put("password", password);

           // Mostra una barra di progresso e avvia un'attività in background a
            // esegue il tentativo di accesso dell'utente.
            // showProgress (true);
            RequestQueue queue = NetworkManager.getInstance(this.getApplicationContext()).getRequestQueue();
            String url= "http://dev.frametech.it:8088/unipr/account.jsp";

//          //Richiedi una risposta stringa dall'URL fornito.
            Request loginRequest = new JsonObjectRequest(Request.Method.POST,url,params,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String result = response.getString("result");

                        if (!result.equals("ok")) {
                            Toast.makeText(LoginActivity.this, getString(R.string.login_fallito), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String role = response.getString("role");

                        SharedPreferences.Editor editor = loginData.edit();

                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "error retriving data", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    error.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Connection with server error", Toast.LENGTH_SHORT).show();
                }
            });

            loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                    500,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ));

            queue.add(loginRequest);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
