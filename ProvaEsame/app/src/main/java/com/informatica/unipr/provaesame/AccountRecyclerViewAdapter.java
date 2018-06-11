package com.informatica.unipr.provaesame;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.AccountDataViewHolder>{

    private List<AccountData> accountDataList;
    private SummaryListener listener;
    private LayoutInflater layoutInflater;
    private Context context;

    //costruttore della classe
    public AccountRecyclerViewAdapter(Context context, SummaryListener listener, List<AccountData> accountDataList){
       this.context= context;
       this.accountDataList= accountDataList;
       this.listener = listener;

       layoutInflater= LayoutInflater.from(context);
    }

    @Override
    public AccountDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){

        //Assegno View la il fragment account_row
        View v = layoutInflater.inflate(R.layout.account_row,viewGroup,false);
        return new AccountDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AccountDataViewHolder accountDataViewHolder, int i){

        //prendo l'oggetto della lista in posizione i
        AccountData item = accountDataList.get(i);

        //passo solo il nome
        accountDataViewHolder.nameTaxtView.setText(item.getName());

        accountDataViewHolder.rowContainer.setTag(accountDataViewHolder);
    }

    @Override
    public int getItemCount(){
        return (accountDataList != null ? accountDataList.size() : 0);
    }


    class AccountDataViewHolder extends RecyclerView.ViewHolder{

        View rowContainer;
        TextView nameTaxtView;

        AccountDataViewHolder(View view){
            super(view);
            this.rowContainer= view;
            this.nameTaxtView=(TextView) view.findViewById(R.id.account_name);

            this.rowContainer.setOnClickListener(new OnAccountClicklistener());
        }
    }

    private class OnAccountClicklistener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            AccountDataViewHolder holder = (AccountDataViewHolder) v.getTag();
            int layoutPosition= holder.getLayoutPosition();

            AccountData accountData = accountDataList.get(layoutPosition);

            listener.onClick(accountData);
        }
    }

}
