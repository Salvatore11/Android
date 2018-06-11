package com.informatica.unipr.esercizio2;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by utente on 05/05/2018.
 */

public class NetworkManager {

    private static NetworkManager mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx; //Interfaccia con informazioni globali su un ambiente applicativo.

    public static synchronized NetworkManager getInstance(Context context){
        if(mInstance == null){
            mInstance= new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context){
        mCtx=context;
        mRequestQueue= getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

}
