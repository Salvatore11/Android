package it.frametech.unipr;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import it.frametech.unipr.model.Constants;
import it.frametech.unipr.network.NetworkManager;

public class CreateAccountMsgService extends Service {

    class AccountHandler extends Handler {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case Constants.CREATE_ACCOUNT:
                        Bundle bundle = msg.getData();

                        String name = bundle.getString("name");
                        String phone = bundle.getString("phone");

                        createAccount(name,phone);

                        break;
                    default:
                        super.handleMessage(msg);
                }

            }
    }

    final Messenger mMessenger = new Messenger(new AccountHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
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
            String url = "http://78.134.82.178:8088/unipr/account.jsp";

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
