package com.himanshu.examination.UI;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.himanshu.examination.R;


public class SubjectExam extends AppCompatActivity {

    TextView C, CPlus, CoreJava, HTML, Python, DBMS, DataStructure;

    String subject;
    String category = "Subject Questions";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_exam);
        getSupportActionBar().hide();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Subject Exam");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        C = findViewById(R.id.c);
        CPlus= findViewById(R.id.cPlus);
        CoreJava= findViewById(R.id.coreJava);
        HTML = findViewById(R.id.html);
        Python = findViewById(R.id.python);
        DBMS = findViewById(R.id.dbms);
        DataStructure = findViewById(R.id.dataStructure);
    }



    public void Start (View view){

        if(view.getId() == R.id.c) {
            subject = "C";
        } else if(view.getId() == R.id.cPlus) {
            subject = "C++";
        } else if(view.getId() == R.id.coreJava) {
            subject = "Core Java";
        } else if(view.getId() == R.id.html) {
            subject = "HTML";
        } else if(view.getId() == R.id.python) {
            subject = "Python";
        } else if(view.getId() == R.id.dbms) {
            subject = "DBMS";
        } else if(view.getId() == R.id.dataStructure) {
            subject = "Data Structure";
        }
       // Toast.makeText(this, subject, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getApplicationContext(), Instruction.class);
        i.putExtra("Subject", subject);
        i.putExtra("category", category);
        startActivity(i);
        finish();
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
