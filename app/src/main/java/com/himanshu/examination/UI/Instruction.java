package com.himanshu.examination.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.himanshu.examination.Questions.Question;
import com.himanshu.examination.R;

public class Instruction extends AppCompatActivity {

    String Subject;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        getSupportActionBar().hide();

        Subject = getIntent().getExtras().getString("Subject");
        category = getIntent().getExtras().getString("category");

        //Toast.makeText(this, Subject, Toast.LENGTH_SHORT).show();
    }

    public void StartExam(View view) {
        Intent i = new Intent(getApplicationContext(), Question.class);
        i.putExtra("Subject", Subject);
        i.putExtra("category", category);
        startActivity(i);
        finish();
    }
}
