package it.frametech.unipr;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import it.frametech.unipr.model.AccountData;
import it.frametech.unipr.network.NetworkManager;

public class DetailActivity  extends AppCompatActivity {

    private EditText nameTextView;
    private EditText phoneTextView;

    private AccountData data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameTextView = (EditText) findViewById(R.id.nameTextView);
        phoneTextView = (EditText) findViewById(R.id.phoneTextView);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");

        nameTextView.setText(name);
        phoneTextView.setText(phone);


        phoneTextView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.account_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete_account:
                delete();
                return true;

            case R.id.modify_account:
                save();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void save() {

        JSONObject params = new JSONObject();
        try {
            params.put("command", "saveAccount");

            String accountName = nameTextView.getText().toString();
            params.put("name", accountName);

            String accountPhone = phoneTextView.getText().toString();
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

    public void delete() {

        JSONObject params = new JSONObject();
        try {
            params.put("command", "deleteAccount");

            String accountName = nameTextView.getText().toString();
            params.put("name", accountName);

        } catch(Throwable t) {}

        RequestQueue queue = NetworkManager.getInstance(this.getApplicationContext()).getRequestQueue();
        String url = "http://dev.frametech.it:8088/unipr/account.jsp";

        // Request a string response from the provided URL.
        Request deleteRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String result = response.getString("result");

                    if (!result.equals("ok")) {

                        //Toast.makeText(getActivity().getApplicationContext(), "Error to save account data", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    onBackPressed();

                    //Toast.makeText(getActivity().getApplicationContext(), "Account modified", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    //showProgress(false);
                    //Toast.makeText(getActivity().getApplicationContext(), "Error to save account data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                //Toast.makeText(getActivity(), "Connection with server error", Toast.LENGTH_SHORT).show();
            }
        });

        deleteRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(deleteRequest);

    }
}


