package it.frametech.unipr;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.frametech.unipr.adapter.AccountRecyclerViewAdapter;
import it.frametech.unipr.fragment.DetailFragment;
import it.frametech.unipr.fragment.SummaryFragment;
import it.frametech.unipr.model.AccountData;
import it.frametech.unipr.model.SummaryListener;

public class MainActivity extends AppCompatActivity implements SummaryListener {

    private final String TAG = "MainActivity";

    private SummaryFragment summaryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        summaryFragment = new SummaryFragment();
        fragmentTransaction.add(R.id.fragment_container, summaryFragment);
        fragmentTransaction.commit();

    }

    //avoid to come back to the LoginActivity
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, summaryFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(AccountData data) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DetailFragment detailFragment = DetailFragment.newInstance(data);
        fragmentTransaction.replace(R.id.fragment_container, detailFragment);
        fragmentTransaction.commit();

    }
}
