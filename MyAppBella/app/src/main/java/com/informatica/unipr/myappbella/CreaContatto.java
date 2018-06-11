package com.informatica.unipr.myappbella;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreaContatto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_contatto);

        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        //fragment2
        FragmentNuovoContatto f2= new FragmentNuovoContatto();
        fragmentTransaction.add(R.id.fragmentcontatto, f2);
        fragmentTransaction.commit();

    }


}
