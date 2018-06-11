package it.frametech.unipr;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import it.frametech.unipr.network.NetworkManager;

public class CreateAccountBoundService extends Service {

    public IBinder mBinder = new CreateAccountBinder();

    public class CreateAccountBinder extends Binder {
        CreateAccountBoundService getService() {
            return CreateAccountBoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void showToast(String message) {
        final String fMessage = message;
        Handler handler = new Handler(getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),fMessage,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createAccount(String name, String phone) {

        final String fName = name;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",name);
            jsonObject.put("phone",phone);
            jsonObject.put("command","createAccount");
            jsonObject.put("sleep","8000");

            RequestQueue queue = NetworkManager.getInstance(this.getApplicationContext()).getRequestQueue();
            String url = "http://dev.frametech.it:8088/unipr/account.jsp";

            // Request a string response from the provided URL.
            Request createAccountRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String result = response.getString("result");

                        if (!result.equals("ok")) {
                            showToast("Account "+ fName + " not created");
                            return;
                        }

                        showToast("Account "+ fName + " created");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    showToast("Account "+ fName + " not created");
                    error.printStackTrace();
                }
            });

            createAccountRequest.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(createAccountRequest);

        } catch(Throwable t) {}
    }



}
