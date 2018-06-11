package it.frametech.unipr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import it.frametech.unipr.network.NetworkManager;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;

    private SharedPreferences loginData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Restore preferences
        loginData = getSharedPreferences("login", 0);
        String username = loginData.getString("username", null);
        String password = loginData.getString("password", null);

        // don't show keyboard onCreate()
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.activity_login);

        setTitle(getString(R.string.app_name));

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                // hide keyboard
                imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);

                attemptLogin();
                return true;
            }
        });

        findViewById(R.id.login_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {


        boolean canLogin = true;
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        final String username = mUsernameView.getText().toString();
        final String password = mPasswordView.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));

        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));

            canLogin = false;

        } else if (!isEmailValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));

            canLogin = false;
        }
        if ((username != null && password != null) && (canLogin)) {
            doLogin(username, password);

        }
    }

    private boolean isPasswordValid(String value) {
        return true;
    }

    private boolean isEmailValid(String value) {
        return true;
    }

    private void doLogin(final String username, final String password) {
        try {
            JSONObject params = new JSONObject();
            params.put("command", "login");
            params.put("username", username);
            params.put("password", password);

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);

            RequestQueue queue = NetworkManager.getInstance(this.getApplicationContext()).getRequestQueue();
            String url = "http://dev.frametech.it:8088/unipr/account.jsp";

            // Request a string response from the provided URL.
            Request loginRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        String result = response.getString("result");

                        if (!result.equals("ko")) {

                            Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                            //showProgress(false);
                            return;
                        }

                        //String role = response.getString("role");

                        SharedPreferences.Editor editor = loginData.edit();
                        editor.putString("username", username);
                        editor.putString("password", password);
                        editor.apply();

                        //showProgress(false);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                        //showProgress(false);
                        Toast.makeText(LoginActivity.this, "Error retrieving data", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    error.printStackTrace();

                    //showProgress(false);

                    Toast.makeText(LoginActivity.this, "Connection with server error", Toast.LENGTH_SHORT).show();
                }
            });

            loginRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            queue.add(loginRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

