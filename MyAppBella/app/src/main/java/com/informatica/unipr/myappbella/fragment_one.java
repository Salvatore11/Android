package com.informatica.unipr.myappbella;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class fragment_one extends Fragment implements View.OnClickListener{

    //Creo la view
    private View view;
    private Button mb;  //bottone inserito nel xml
    private TextView myTXT; //textView inserito nell' xml

    //costruttore vuoto
    public fragment_one(){}

    //onCreate vuota
    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

    }

    //onCreateView
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //collego la classe al layout del fragment
        view = inflater.inflate(R.layout.fragment_one, container, false);
        //setto gli elementi nel fragment
        mb = view.findViewById(R.id.my_button);
        myTXT = view.findViewById(R.id.txtuno);
        //dico se clicco nel bottone
        mb.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        //se il click va sul bottone
        if (v == mb)
        {
            myTXT.setText("Ciao salvo sono marco");
        }

    }
}
