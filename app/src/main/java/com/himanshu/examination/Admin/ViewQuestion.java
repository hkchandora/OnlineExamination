package com.himanshu.examination.Admin;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.Adapter.MyAdapter;
import com.himanshu.examination.Model.ListData;
import com.himanshu.examination.R;

import java.util.ArrayList;
import java.util.List;

public class ViewQuestion extends AppCompatActivity {

    private List<ListData> listData;
    private RecyclerView rv;
    private MyAdapter adapter;
    String category, subject;

    ProgressDialog loadBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);

        loadBar = new ProgressDialog(this);

        loadBar.show();
        loadBar.setContentView(R.layout.progress_dialog);
        loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadBar.setCanceledOnTouchOutside(false);

        category = getIntent().getExtras().getString("category");
        subject = getIntent().getExtras().getString("subject");

        TextView subjectTxt = findViewById(R.id.subjectTxt);
        subjectTxt.setText(subject+" Questions");

        rv = (RecyclerView) findViewById(R.id.questionRecyclerview);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        listData = new ArrayList<>();

        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Questions");

        reference.child(category).child(subject).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npSnapShot : dataSnapshot.getChildren()) {
                        ListData l = npSnapShot.getValue(ListData.class);
                        listData.add(l);
                    }
                    adapter = new MyAdapter(listData);
                    rv.setAdapter(adapter);
                    loadBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}