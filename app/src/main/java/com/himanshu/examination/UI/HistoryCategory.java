package com.himanshu.examination.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.himanshu.examination.R;

public class HistoryCategory extends AppCompatActivity {


    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_category);

        getSupportActionBar().hide();


    }

    public void SubjectExam2(View view) {
        category = "Subject Exam";
        Intent i = new Intent(getApplicationContext(), History.class);
        i.putExtra("category", category);
        startActivity(i);
    }

    public void AptitudeTest2(View view) {
        category = "Aptitude Test";
        Intent i = new Intent(getApplicationContext(), History.class);
        i.putExtra("category", category);
        startActivity(i);
    }

    public void ReasoningTest2(View view) {
        category = "Reasoning Test";
        Intent i = new Intent(getApplicationContext(), History.class);
        i.putExtra("category", category);
        startActivity(i);
    }

    public void VerbalTest2(View view) {
        category = "Verbal Test";
        Intent i = new Intent(getApplicationContext(), History.class);
        i.putExtra("category", category);
        startActivity(i);
    }
}
