package com.himanshu.examination.UI;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.himanshu.examination.Adapter.MyAptitudeListAdapter;
import com.himanshu.examination.Model.MyListData;
import com.himanshu.examination.R;

public class Aptitude extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aptitude);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Aptitude Test");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));


        MyListData[] myListData = new MyListData[]{
                new MyListData("HCL & LCM"),
                new MyListData("Trains"),
                new MyListData("Time & Work"),
                new MyListData("Profit & Loss"),
                new MyListData("Ages"),
                new MyListData("Averages"),
                new MyListData("Permutations"),
                new MyListData("Numbers"),
                new MyListData("Pipes"),
                new MyListData("Logarithms"),
                new MyListData("Probability"),
                new MyListData("Height & Distance"),
                new MyListData("Percentages"),
                new MyListData("Mixtures"),
                new MyListData("Stocks & Shares"),
                new MyListData("Banker's Discount"),
                new MyListData("Time & Distance"),
                new MyListData("Simple Interest"),
                new MyListData("Compound Interest"),
                new MyListData("Partnerships"),
                new MyListData("Calendar"),
                new MyListData("Area"),
                new MyListData("Clock"),
                new MyListData("Volume & Surface"),
                new MyListData("Ratio & Proportion"),
                new MyListData("Boats & Streams"),
                new MyListData("Races & Games"),
                new MyListData("True Discount"),
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MyAptitudeListAdapter adapter = new MyAptitudeListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}