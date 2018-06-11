package com.informatica.unipr.myappbella;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class FragmentNuovoContatto extends Fragment implements  View.OnClickListener {

    AccountData nuovoAccount;
    List<AccountData> lista;
    View view;
    EditText inputUsername;
    EditText inputPhone;
    Button buttonCrea;


    //public FragmentNuovoContatto(){}

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //collego la classe al layout
        inflater.inflate(R.layout.fragment_nuovo_contatto,container,false);
        //setto gli elementi nel fr


        inputUsername= view.findViewById(R.id.newUsername);
        inputPhone = view.findViewById(R.id.newPhone);
        buttonCrea= view.findViewById(R.id.bottoneCrea);


        //dico se clicco nel bottone
        buttonCrea.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v){
        //se il click va sul bottone
        if (v == buttonCrea)
        {
            String name= inputUsername.getText().toString();
            String phone= inputPhone.getText().toString();
            nuovoAccount= new AccountData(name,phone);


        }

    }
}
