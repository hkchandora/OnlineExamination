package com.himanshu.examination.UI;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.Model.TestData;
import com.himanshu.examination.Adapter.MyTestAdapter;
import com.himanshu.examination.R;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {

    private List<TestData> testData;
    private RecyclerView rv;
    private MyTestAdapter adapter;

    ProgressDialog loadBar;
    TextView HistoryText;

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("History");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        category = getIntent().getExtras().getString("category");

        HistoryText = findViewById(R.id.historyTxt);
        HistoryText.setText(category+" History");

        loadBar = new ProgressDialog(this);

        loadBar.show();
        loadBar.setContentView(R.layout.progress_dialog);
        loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadBar.setCanceledOnTouchOutside(false);


        rv = (RecyclerView) findViewById(R.id.testRecyclerView);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        testData = new ArrayList<>();

        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("Test History");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(db.child(user.getUid()) == null) {
            Toast.makeText(this, "No Test History", Toast.LENGTH_SHORT).show();
            loadBar.dismiss();
        }

        db.child(category).child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        TestData l = npsnapshot.getValue(TestData.class);
                        testData.add(l);
                    }
                    adapter = new MyTestAdapter(testData);
                    rv.setAdapter(adapter);
                    loadBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(History.this, "No Test Data Found", Toast.LENGTH_SHORT).show();
                HistoryText.setText("No Test Data Found");
                loadBar.dismiss();
            }
        });
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
