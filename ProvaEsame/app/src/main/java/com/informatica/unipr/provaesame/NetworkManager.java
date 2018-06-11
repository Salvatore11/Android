package com.informatica.unipr.provaesame;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkManager {

    private static NetworkManager mInstance;
    private static Context mCxt;
    private RequestQueue mRequestQueue;


    public static synchronized NetworkManager getInstance(Context context){
        if(mInstance == null){
            mInstance= new NetworkManager(context);
        }
        return mInstance;
    }


    private NetworkManager(Context context){
        mCxt= context;
        mRequestQueue= getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mCxt.getApplicationContext());
        }
        return mRequestQueue;
    }
}
