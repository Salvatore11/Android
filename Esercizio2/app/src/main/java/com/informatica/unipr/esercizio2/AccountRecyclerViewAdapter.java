package com.informatica.unipr.esercizio2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by utente on 05/05/2018.
 */

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.AccountDataViewHolder> {

    private List<AccountData> accountDataList;
    private SummaryListener listener;
    private LayoutInflater layoutInflater; //Crea un'istanza di un file XML di layout negli View oggetti corrispondenti .
    private Context context; // Permette l'accesso a risorse e classi specifiche dell'applicazione,

    public AccountRecyclerViewAdapter(Context context, SummaryListener listener, List<AccountData> accountDataList){
        this.accountDataList = accountDataList;
        this.listener = listener;
        this.context = context;
    }

    //Un ViewHolder descrive una vista e dei metadati relativi alla sua posizione all'interno di RecyclerView.
    //ViewGroup è una vista speciale che può contenere altre vieste
    @Override
    public AccountDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = layoutInflater.inflate(R.layout.account_row,viewGroup,false);
        return new AccountDataViewHolder(v);
    }

    @Override
    public void onBildViewHolder(AccountDataViewHolder accountDataViewHolder, int i){
        AccountData item = accountDataList.get(i);

        accountDataViewHolder.nameTextView.setText(item.getName());

        accountDataViewHolder.rowContainer.setTag(accountDataViewHolder);
    }
    @Override
    public int getItemCount(){
        return (accountDataList != null ? accountDataList.size() : 0);
    }

    //CREAO LA CLASSE ACCOUNTDATAVIEWHOLDER
    //ViewHolder è il titolare della lista che ha il compito di visualizzare un singolo oggetto con il titolare della lista

    class AccountDataViewHolder extends RecyclerView.ViewHolder{
        View rowContainer;
        TextView nameTextView;


        //costruttore con parametri della classe
        AccountDataViewHolder(View view) {
            super(view);
            this.rowContainer = view;
            this.nameTextView = (TextView) view.findViewById(R.id.Account_name);

            this.rowContainer.setOnClickListener(new OnAccountClickListener());
        }
    }


    private class OnAccountClickListener implements View.OnClickListener{
        public void onClick(View v){
            AccountDataViewHolder holder = (AccountDataViewHolder) v.getTag();
            int layoutPosition = holder.getLayoutPosition();

            AccountData accountData= accountDataList.get(layoutPosition);

            listener.onClick(accountData);
        }
    }
}



