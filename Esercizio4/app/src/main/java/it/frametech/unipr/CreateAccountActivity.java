package it.frametech.unipr;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import it.frametech.unipr.CreateAccountBoundService.CreateAccountBinder;
import it.frametech.unipr.model.AccountData;
import it.frametech.unipr.model.Constants;
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
                Log.d("Esercizio","onEditorAction");

                return true;
            }
        });

        findViewById(R.id.create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Esercizio","onClick");

                create();

            }

        });
    }

    public void create() {
        String name  = accountTextName.getText().toString();
        String phone = accountTextPhone.getText().toString();

        Intent intent = new Intent(this,CreateAccountService.class);
        intent.putExtra("name",name);
        intent.putExtra("phone",phone);

        startService(intent);
    }

}


