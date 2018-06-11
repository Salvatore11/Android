package com.informatica.unipr.provaesame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SummaryListener{

    private RecyclerView mAccountRecyclerView;
    private AccountRecyclerViewAdapter mAccountRecyclerViewAdapter;
    private List<AccountData> accounts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAccountRecyclerView= findViewById(R.id.ticket_recyclerView);
        mAccountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAccountRecyclerView.setHasFixedSize(true);

        mAccountRecyclerViewAdapter= new AccountRecyclerViewAdapter(this, this,accounts);
        mAccountRecyclerView.setAdapter(mAccountRecyclerViewAdapter);

        loads();
    }

    public void loads(){
        JSONObject params = new JSONObject();
        try{
            params.put("comand", "getAccounts");
        }catch(JSONException e){
            e.printStackTrace();
        }

        RequestQueue queue= NetworkManager.getInstance(this.getApplicationContext()).getRequestQueue();

        String url= "http://dev.frametech.it:8088/unipr/account.jsp";

        Request loadRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String result = response.getString("result");
                    if (!result.equals("ok")) {
                        return;
                    }
                    JSONArray jsonAccounts = response.getJSONArray("accounts");

                    accounts.clear();

                    for (int jj = 0; jj < jsonAccounts.length(); jj++) {
                        JSONObject jsonAccount = jsonAccounts.getJSONObject(jj);
                        AccountData data = new AccountData(jsonAccount.getString("name"), jsonAccount.getString("phone"));
                        accounts.add(data);
                    }

                    mAccountRecyclerViewAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        loadRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(loadRequest);
    }

    @Override
    public void onClick(AccountData data) {

    }
}
