package it.frametech.unipr;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.frametech.unipr.adapter.AccountRecyclerViewAdapter;
import it.frametech.unipr.model.AccountData;
import it.frametech.unipr.model.Constants;
import it.frametech.unipr.model.SummaryListener;
import it.frametech.unipr.network.NetworkManager;

public class MainActivity extends AppCompatActivity implements SummaryListener {

    private final String TAG = "MainActivity";

    private RecyclerView mAccountRecyclerView;
    private AccountRecyclerViewAdapter mAccountRecyclerViewAdapter;
    private List<AccountData> accounts;

    @Override
    protected void onResume() {
        super.onResume();

        loadAccounts();

    }
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        accounts = new ArrayList<AccountData>() ;

        mAccountRecyclerView = findViewById(R.id.ticket_recycler_view);
        mAccountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAccountRecyclerView.setHasFixedSize(true);

        mAccountRecyclerViewAdapter = new AccountRecyclerViewAdapter(this, this, accounts);
        mAccountRecyclerView.setAdapter(mAccountRecyclerViewAdapter);

        loadAccounts();

    }

    protected void loadAccounts() {
        JSONObject params = new JSONObject();
        try {
            params.put("command", "getAccounts");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = NetworkManager.getInstance(this.getApplicationContext()).getRequestQueue();

        String url = "http://dev.frametech.it:8088/unipr/account.jsp";

        Request accountsRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String result = response.getString("result");

                    if (!result.equals("ok")) {

                        //Toast.makeText(getActivity(), "errore nel caricamento account", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    JSONArray jsonAccounts = response.getJSONArray("accounts");

                    accounts.clear();

                    for(int jj = 0 ;jj <jsonAccounts.length();jj++) {
                        JSONObject jsonAccount = jsonAccounts.getJSONObject(jj);
                        AccountData data = new AccountData(jsonAccount.getString("name"), jsonAccount.getString("phone"));
                        accounts.add(data);
                    }

                    mAccountRecyclerViewAdapter.notifyDataSetChanged();


                } catch (Exception e) {
                    e.printStackTrace();
                    //showProgress(false);
                    //Toast.makeText(this, "Error retrieving data", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                //showProgress(false);

                //Toast.makeText(getActivity(), "Connection with server error", Toast.LENGTH_SHORT).show();
            }
        });

        accountsRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(accountsRequest);

    }

    //avoid to come back to the LoginActivity
    @Override
    public void onBackPressed() {}

    public void onClick(AccountData data) {
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra("name",data.getName());
        intent.putExtra("phone",data.getPhone());

        startActivity(intent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.summary_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_account:

                Intent intent = new Intent(this,CreateAccountActivity.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
