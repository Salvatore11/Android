package it.frametech.unipr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import it.frametech.unipr.model.AccountData;
import it.frametech.unipr.network.NetworkManager;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText accountTextName;
    private EditText accountTextPhone;

    private AccountData data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_create_account);

        accountTextName = (EditText) findViewById(R.id.newAccountTextName);
        accountTextPhone = (EditText) findViewById(R.id.newAccountTextPhone);

        accountTextPhone.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                // hide keyboard
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                create();
                return true;
            }
        });

        findViewById(R.id.create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
    }

    public void create() {

        JSONObject params = new JSONObject();
        try {
            params.put("command", "createAccount");

            String accountName = accountTextName.getText().toString();
            params.put("name", accountName);

            String accountPhone = accountTextPhone.getText().toString();
            params.put("phone", accountPhone);

        } catch(Throwable t) {}

        RequestQueue queue = NetworkManager.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = "http://dev.frametech.it:8088/unipr/account.jsp";

        // Request a string response from the provided URL.
        Request saveRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String result = response.getString("result");

                    if (!result.equals("ok")) {
                        //Toast.makeText(getActivity().getApplicationContext(), "Error to save account data", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    onBackPressed();

                } catch (Exception e) {
                    e.printStackTrace();

                    //Toast.makeText(getApplicationContext(), "Error to save account data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        });

        saveRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(saveRequest);
    }


}


