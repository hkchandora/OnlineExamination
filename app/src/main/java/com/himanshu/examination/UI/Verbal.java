package com.himanshu.examination.UI;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.himanshu.examination.Adapter.MyVerbalListAdapter;
import com.himanshu.examination.Model.MyListData;
import com.himanshu.examination.R;

import java.util.Objects;

public class Verbal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbal);

        MyListData[] myListData = new MyListData[]{
                new MyListData("Spot Errors"),
                new MyListData("Selecting Words"),
                new MyListData("Ordering Words"),
                new MyListData("Complete Statement"),
                new MyListData("Idioms & Phrases"),
                new MyListData("Verbal Analogies"),
                new MyListData("Synonyms"),
                new MyListData("Spellings"),
                new MyListData("Sentence Correction"),
                new MyListData("Order Sentences"),
                new MyListData("Comprehension"),
                new MyListData("Change of Voice"),
                new MyListData("Antonyms"),
                new MyListData("Sentence Formation"),
                new MyListData("Sentence Improvement"),
                new MyListData("Paragraph Formation"),
                new MyListData("Substitute"),
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView3);
        MyVerbalListAdapter adapter = new MyVerbalListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Verbal Test");
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
