package com.informatica.unipr.provaesame;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class recuperoPassword extends AppCompatActivity {


    Button inviaRichiestabottone;

    String mEmail;

    EditText inputDomanda;
    EditText inputRisposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupero_password);

        inviaRichiestabottone = (Button) findViewById(R.id.inviaBottone);

        inputDomanda = (EditText) findViewById(R.id.domanda);
        inputRisposta = (EditText) findViewById(R.id.risposta);


        inviaRichiestabottone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviaRichiesta();
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void inviaRichiesta() {
        try {
            JSONObject params = new JSONObject();


            String domanda = inputDomanda.getText().toString();
            params.put("domanda", domanda);

            String risposta = inputRisposta.getText().toString();
            params.put("risposta", risposta);


            RequestQueue queue = NetworkManager.getInstance(this.getApplicationContext()).getRequestQueue();

            String url = "http://ptsv2.com/t/8m9ag-1528465493/post";

            Request inviaRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String result = response.getString("result");

                        if (!result.equals("ok")) {
                            Toast.makeText(recuperoPassword.this, "cunt", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(recuperoPassword.this, "Richiesta inviata", Toast.LENGTH_SHORT).show();
                        //prende l'email
                        mEmail= response.getString("email");
                        inviaEmail();
                        Log.d("we", mEmail);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(recuperoPassword.this, "Error to save account data", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(recuperoPassword.this, "Connection with server error", Toast.LENGTH_SHORT).show();
                }
            });

            inviaRequest.setRetryPolicy(new DefaultRetryPolicy(
                    500,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(inviaRequest);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inviaEmail(){
        Log.d("aiuto", mEmail);

        Intent intent= new Intent(getApplicationContext(),recuperoPassword2.class);
        intent.putExtra("email",  mEmail);
        startActivity(intent);
    }
}
