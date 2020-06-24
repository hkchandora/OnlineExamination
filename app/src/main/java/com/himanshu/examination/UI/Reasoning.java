package com.himanshu.examination.UI;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.himanshu.examination.Adapter.MyReasoningListAdapter;
import com.himanshu.examination.Model.MyListData;
import com.himanshu.examination.R;

import java.util.Objects;

public class Reasoning extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reasoning);

        MyListData[] myListData = new MyListData[]{
                new MyListData("Number Series"),
                new MyListData("Essential Part"),
                new MyListData("Logical Problems"),
                new MyListData("Assumptions"),
                new MyListData("Theme Detection"),
                new MyListData("Logical Deduction"),
                new MyListData("Symbol Series"),
                new MyListData("Analogies"),
                new MyListData("Making Judgments"),
                new MyListData("Logic Games"),
                new MyListData("Course of Action"),
                new MyListData("Cause & Effect"),
                new MyListData("Verbal Classification"),
                new MyListData("Artificial Language"),
                new MyListData("Verbal Reasoning"),
                new MyListData("Analysing Arguments"),
                new MyListData("Statement & Conclusion"),
                new MyListData("Statement & Arguments")
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView2);
        MyReasoningListAdapter adapter = new MyReasoningListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Reasoning Test");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
