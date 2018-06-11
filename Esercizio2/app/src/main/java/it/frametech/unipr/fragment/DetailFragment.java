package it.frametech.unipr.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.frametech.unipr.R;
import it.frametech.unipr.model.AccountData;

public class DetailFragment extends Fragment {

    private TextView nameTextView;
    private TextView phoneTextView;

    private AccountData data;

    public static DetailFragment newInstance(AccountData data) {

        Bundle args = new Bundle();
        args.putSerializable("account", data);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = (AccountData) getArguments().getSerializable("account");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTextView = view.findViewById(R.id.nameTextView);
        phoneTextView = view.findViewById(R.id.phoneTextView);

        nameTextView.setText(data.getName());
        phoneTextView.setText(data.getPhone());
    }
}


