package it.frametech.unipr;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateAccountService extends IntentService {

    public CreateAccountService() {
        super("CreateAccountService");
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

    @Override
    public void onHandleIntent(Intent intent) {
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");

        createAccount(name,phone);
    }

    private void createAccount(String name, String phone) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name",name);
            jsonObject.put("phone",phone);
            jsonObject.put("command","createAccount");
            jsonObject.put("sleep","15000");

            Log.d("Esercizio","onCreateAccount");

            String urlString = "http://dev.frametech.it:8088/unipr/account.jsp";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30000);
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(jsonObject.toString());

            dos.flush();
            dos.close();

            Log.d("Esercizio","before response");

            Log.d("ResponseStatus",String.valueOf(conn.getResponseCode()));

            InputStreamReader ir = new InputStreamReader(conn.getInputStream());

            BufferedReader br = new BufferedReader(ir);

            String resultLine = null;
            StringBuffer buffer = new StringBuffer();

            while ((resultLine = br.readLine()) != null ) {
                buffer.append(resultLine);
            }

            br.close();

            Log.d("Esercizio", buffer.toString());

            JSONObject jsonResponse = new JSONObject(buffer.toString());

            String result = jsonResponse.getString("result");

            if (result.equals("ok")) {
                showToast("account "+name +" created");
            } else {
                showToast("account not created");
            }

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
}
