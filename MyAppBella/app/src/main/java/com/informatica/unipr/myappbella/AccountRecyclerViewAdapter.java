package com.informatica.unipr.myappbella;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.content.ContentValues.TAG;
/*
public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private String mItem;
        private TextView mTextView;
        View rowContainer;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.mTextView = (TextView) view.findViewById(R.id.account_name);
        }

        public void setItem(String item) {
            mItem = item;
            mTextView.setText(item);
        }
        @Override
        public void onClick(View view) {
           // Log.d(TAG, "onClick sa" + getPosition() + " " + mItem);
            getAdapterPosition();

            Intent intent = new Intent(MainActivity.this, DetailActivity.class);

            intent.putExtra("name",data.getName());
            intent.putExtra("phone",data.getPhone());

            (intent);
        }


    }

    private List<AccountData> accountDataList;

    public AccountRecyclerViewAdapter(List<AccountData> maccountDataList) {
        accountDataList = maccountDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // holder.setItem(accountDataList[position]);
        AccountData item = accountDataList.get(position);

        holder.mTextView.setText(item.getName());

       // holder.rowContainer.setTag(holder);
    }

    @Override
    public int getItemCount() {
        return (accountDataList != null ? accountDataList.size() : 0);
        //return mDataset.length;
    }


}

*/


public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.AccoundDataViewHolder> {

    private List<AccountData> accountDataList;
    private SummaryListener listener;
    private LayoutInflater layoutInflater;
    private Context context;

    public AccountRecyclerViewAdapter(Context context, SummaryListener listener, List<AccountData> accountDataList) {
        this.context = context;
        this.accountDataList = accountDataList;
        this.listener = listener;

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public AccoundDataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = layoutInflater.inflate(R.layout.account_row, viewGroup, false);
        return new AccoundDataViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AccoundDataViewHolder accoundDataViewHolder, int i) {

        AccountData item = accountDataList.get(i);

        accoundDataViewHolder.nameTextView.setText(item.getName());

        accoundDataViewHolder.rowContainer.setTag(accoundDataViewHolder);
    }


    @Override
    public int getItemCount() {
        return (accountDataList != null ? accountDataList.size() : 0);
    }


    class AccoundDataViewHolder extends RecyclerView.ViewHolder {

        View rowContainer;
        TextView nameTextView;


        AccoundDataViewHolder(View view) {
            super(view);
            this.rowContainer = view;
            this.nameTextView = (TextView) view.findViewById(R.id.account_name);

            this.rowContainer.setOnClickListener(new OnAccountClickListener());
        }
    }

        private class OnAccountClickListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {

                AccoundDataViewHolder holder = (AccoundDataViewHolder) v.getTag();
                int layoutPosition = holder.getLayoutPosition();

                AccountData accountData = accountDataList.get(layoutPosition);

                listener.onClick(accountData);


            }
        }
}



