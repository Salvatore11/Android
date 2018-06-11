package it.frametech.unipr.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import it.frametech.unipr.R;
import it.frametech.unipr.adapter.AccountRecyclerViewAdapter;
import it.frametech.unipr.model.AccountData;
import it.frametech.unipr.model.SummaryListener;

public class SummaryFragment extends Fragment {

    private RecyclerView mAccountRecyclerView;
    private AccountRecyclerViewAdapter mAccountRecyclerViewAdapter;

    private SummaryListener summaryListener;

    private List<AccountData> accounts;

    public SummaryFragment() {

        accounts = new ArrayList<AccountData>() ;

        accounts.add(new AccountData("Mario Rossi","3401234567"));
        accounts.add(new AccountData("Dario Bianchi","3477654321"));
        accounts.add(new AccountData("Luca Verdi","0521776655"));
        accounts.add(new AccountData("Cesare Neri","0511234590"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summary, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        summaryListener = (SummaryListener) context;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAccountRecyclerView = view.findViewById(R.id.ticket_recycler_view);
        mAccountRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAccountRecyclerView.setHasFixedSize(true);

        mAccountRecyclerViewAdapter = new AccountRecyclerViewAdapter(getContext(), summaryListener, accounts);
        mAccountRecyclerView.setAdapter(mAccountRecyclerViewAdapter);

    }




}


