package com.informatica.unipr.myappbella;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  implements SummaryListener{

    private final String TAG = "MainActivity";

    private RecyclerView mAccountRecyclerView;
    private AccountRecyclerViewAdapter mAccountRecyclerViewAdapter;
    private List<AccountData> accounts;
    private SummaryListener summaryListener;

    private Button bottoneNuovoContatto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accounts = new ArrayList<AccountData>() ;


        accounts.add(new AccountData("Mario Rossi","3401234567"));
        accounts.add(new AccountData("Dario Bianchi","34776543"));
        accounts.add(new AccountData("Luca Verdi","0521776655"));
        accounts.add(new AccountData("Cesare Neri","0511234590"));
        accounts.add(new AccountData("Peppe Piloto","3445737443"));
        accounts.add(new AccountData("Salvatore Latino","353524"));
        accounts.add(new AccountData("Luigi Zaccone","363838432"));
        accounts.add(new AccountData("Cazzo Mene","123456756"));
        accounts.add(new AccountData("Davide Ambu","34445443443"));
        accounts.add(new AccountData("Federica Ambu","3536863524"));
        accounts.add(new AccountData("Matteo Gandolfo","363555432"));
        accounts.add(new AccountData("Minchia Frate","178796756"));

        mAccountRecyclerView = findViewById(R.id.ticket_recycler_view);
        mAccountRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAccountRecyclerView.setHasFixedSize(true);

        mAccountRecyclerViewAdapter = new AccountRecyclerViewAdapter(this,this, accounts);

        mAccountRecyclerView.setAdapter(mAccountRecyclerViewAdapter);


        bottoneNuovoContatto= (Button)findViewById(R.id.bottoneCreacontatto);

        bottoneNuovoContatto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CreaContatto.class);
                startActivity(intent);
            }
        });

        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Fragment
        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        //fragment1
        fragment_one f1= new fragment_one();
        fragmentTransaction.add(R.id.myframeLay,f1);
        fragmentTransaction.commit();


    }




    @Override
    public void onClick(AccountData data) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        intent.putExtra("name",data.getName());
        intent.putExtra("phone",data.getPhone());

        startActivity(intent);

    }


    //Menu toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_settings:
            // User chose the "Settings" item, show the app settings UI...
            return true;

        case R.id.action_favorite:
            // User chose the "Favorite" action, mark the current item
            // as a favorite...
            return true;

        default:
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            return super.onOptionsItemSelected(item);

    }
}


}
