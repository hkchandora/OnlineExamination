package com.himanshu.examination.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.Model.ResultStore;
import com.himanshu.examination.Model.SignUpMember;
import com.himanshu.examination.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Result extends AppCompatActivity {

    int mark, right, wrong, min, sec, point;
    String formattedDate;
    String Subject;
    TextView MarkTxt;
    private long TestCount = 0;

    EditText suggestion;

    ResultStore store;
    SignUpMember member;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().hide();

        MarkTxt = findViewById(R.id.marksTxt);
        suggestion = findViewById(R.id.description1);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        formattedDate = df.format(c.getTime());

        String Category = getIntent().getExtras().getString("category");
        Subject = getIntent().getExtras().getString("subject");
        mark = getIntent().getIntExtra("mark",0);
        point = mark*2;
        right = getIntent().getIntExtra("right",0);
        wrong = getIntent().getIntExtra("wrong",0);
        min = getIntent().getIntExtra("min",0);
        sec = getIntent().getIntExtra("sec",0);
        MarkTxt.setText("Right Answer = "+right+"\nWrong Answer = "+wrong+"\nTime = "+min+" min "+sec+" sec\nTotal Marks = "+point+" Points\nResult = "
                +String.valueOf(Math.round(mark/10.00*100*100)/100)+" %");


        store = new ResultStore();
        member = new SignUpMember();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        myRef = FirebaseDatabase.getInstance().getReference().child("Test History").child(Category).child(user.getUid());


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    TestCount = (dataSnapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void Save(View view) {

        String Suggestion = suggestion.getText().toString();

        store.setTestTime(min+":"+sec);
        store.setMarks(String.valueOf(mark));
        store.setResult(String.valueOf(Math.round(mark/10.00*100*100)/100)+" %");
        store.setDateAndTime(formattedDate);
        store.setEmail(user.getEmail());
        store.setName(member.getName());
        store.setSubject(Subject);
        store.setSuggestion(Suggestion);
        myRef.child("Test "+(TestCount+1)).setValue(store);
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();


        Intent i = new Intent(getApplicationContext(), End.class);
        i.putExtra("total", right);
        startActivity(i);
        finish();
    }

    public void Home(View view) {
//        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(i);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//
//        startActivity(new Intent(getApplicationContext(), SubjectExam.class));
//        finish();
    }
}
