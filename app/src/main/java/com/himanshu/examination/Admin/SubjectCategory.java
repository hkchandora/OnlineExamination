package com.himanshu.examination.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.himanshu.examination.R;

public class SubjectCategory extends AppCompatActivity {

    String category, subject, action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_category);
        getSupportActionBar().hide();

        category = getIntent().getExtras().getString("category");
        action = getIntent().getExtras().getString("action");
    }

    public void Start(View view) {
        if(view.getId() == R.id.c2) {
            subject = "C";
        } else if(view.getId() == R.id.cPlus2) {
            subject = "C++";
        } else if(view.getId() == R.id.coreJava2) {
            subject = "Core Java";
        } else if(view.getId() == R.id.html2) {
            subject = "HTML";
        } else if(view.getId() == R.id.python2) {
            subject = "Python";
        } else if(view.getId() == R.id.dbms2) {
            subject = "DBMS";
        } else {
            subject = "Data Structure";
        }


        if(action.equals("Upload")) {
            Intent i = new Intent(getApplicationContext(), UploadQuestion.class);
            i.putExtra("category", category);
            i.putExtra("subject", subject);
            startActivity(i);
        }
        if(action.equals("Update")) {
            Intent i = new Intent(getApplicationContext(), UpdateQuestion.class);
            i.putExtra("category", category);
            i.putExtra("subject", subject);
            startActivity(i);
        }
        if (action.equals("View")) {
            Intent i = new Intent(getApplicationContext(), ViewQuestion.class);
            i.putExtra("category", category);
            i.putExtra("subject", subject);
            startActivity(i);
        }
    }
}


