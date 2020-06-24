package com.himanshu.examination.Admin;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.Adapter.MyUserAdapter;
import com.himanshu.examination.Model.SignUpMember;
import com.himanshu.examination.R;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUsers extends AppCompatActivity {


    private List<SignUpMember> members;
    private RecyclerView recyclerView;
    private MyUserAdapter UserAdapter;

    ProgressDialog loadBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_users);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("All Users");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        loadBar = new ProgressDialog(this);
        loadBar.show();
        loadBar.setContentView(R.layout.progress_dialog);
        loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadBar.setCanceledOnTouchOutside(false);



        recyclerView = (RecyclerView) findViewById(R.id.userRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        members = new ArrayList<>();

        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("SignUp Members");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        db.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()) {
                        SignUpMember l = npsnapshot.getValue(SignUpMember.class);
                        members.add(l);
                    }
                    UserAdapter = new MyUserAdapter(members);
                    recyclerView.setAdapter(UserAdapter);
                    loadBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(RegisteredUsers.this, "No Test Data Found", Toast.LENGTH_SHORT).show();
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