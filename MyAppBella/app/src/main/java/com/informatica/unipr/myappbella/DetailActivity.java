package com.informatica.unipr.myappbella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView phoneTextView;

    private AccountData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        nameTextView =  findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");

        nameTextView.setText(name);
        phoneTextView.setText(phone);

        phoneTextView.requestFocus();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.delete_account:
               // delete();
                return true;
            case R.id.crea_account:
                modifica();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    public void modifica(){
        EditText newNome;
        String nomeCorrente = nameTextView.getText().toString();




    }





}
