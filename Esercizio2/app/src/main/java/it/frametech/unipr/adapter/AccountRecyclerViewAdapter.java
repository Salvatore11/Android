package it.frametech.unipr.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import it.frametech.unipr.R;

import it.frametech.unipr.model.AccountData;
import it.frametech.unipr.model.SummaryListener;

public class AccountRecyclerViewAdapter extends RecyclerView.Adapter<AccountRecyclerViewAdapter.AccoundDataViewHolder> {

    private List<AccountData> accountDataList;
    private SummaryListener listener;
    private LayoutInflater layoutInflater;
    private Context context;

    public AccountRecyclerViewAdapter(Context context, SummaryListener listener, List<AccountData> accountDataList) {
        this.accountDataList = accountDataList;
        this.listener = listener;
        this.context = context;
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