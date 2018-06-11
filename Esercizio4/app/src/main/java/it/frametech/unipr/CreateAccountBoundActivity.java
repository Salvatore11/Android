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
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import it.frametech.unipr.model.AccountData;
import it.frametech.unipr.model.Constants;

public class CreateAccountBoundActivity extends AppCompatActivity {

    private EditText accountTextName;
    private EditText accountTextPhone;

    private AccountData data;

    private CreateAccountBoundService mBoundService = null;
    private boolean mBound = false;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CreateAccountBoundService.CreateAccountBinder binder = (CreateAccountBoundService.CreateAccountBinder) service;
            mBoundService = binder.getService();
            Log.d("Esercizio","serviceBound");
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
            mBound = true;
        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,CreateAccountBoundService.class);
        Log.d("Esercizio","bindService");
        bindService(intent, serviceConnection,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(serviceConnection);
        Log.d("Esercizio","unbindService");
        mBound = false;
    }
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

                createOnBoundService();

            }

        });
    }

    public void createOnBoundService() {
        String name  = accountTextName.getText().toString();
        String phone = accountTextPhone.getText().toString();
        Log.d("Esercizio","mBoundService.createAccount");
        mBoundService.createAccount(name,phone);
    }

}


