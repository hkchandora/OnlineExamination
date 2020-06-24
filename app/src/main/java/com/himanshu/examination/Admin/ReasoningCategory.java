package com.himanshu.examination.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.himanshu.examination.R;

public class ReasoningCategory extends AppCompatActivity {

    String category, subject, action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reasoning_category);
        getSupportActionBar().hide();

        category = getIntent().getExtras().getString("category");
        action = getIntent().getExtras().getString("action");

    }

    public void StartReasoning(View view) {
        if(view.getId() == R.id.number_series) {
            subject = "Number Series";
        } else if(view.getId() == R.id.essential_part) {
            subject = "Essential Part";
        } else if(view.getId() == R.id.logical_problems) {
            subject = "Logical Problems";
        } else if(view.getId() == R.id.assumptions) {
            subject = "Assumptions";
        } else if(view.getId() == R.id.theme_detection) {
            subject = "Theme Detection";
        } else if(view.getId() == R.id.logical_deduction) {
            subject = "Logical Deduction";
        } else if(view.getId() == R.id.symbol_series) {
            subject = "Symbol Series";
        } else if(view.getId() == R.id.analogies) {
            subject = "Analogies";
        } else if(view.getId() == R.id.making_judgments) {
            subject = "Making Judgments";
        } else if(view.getId() == R.id.logic_games) {
            subject = "Logic Games";
        } else if(view.getId() == R.id.course_of_action) {
            subject = "Course of Action";
        } else if(view.getId() == R.id.cause_effect) {
            subject = "Cause & Effect";
        } else if(view.getId() == R.id.verbal_classification) {
            subject = "Verbal Classification";
        } else if(view.getId() == R.id.artificial_language) {
            subject = "Artificial Language";
        } else if(view.getId() == R.id.verbal_reasoning) {
            subject = "Verbal Reasoning";
        } else if(view.getId() == R.id.analysing_arguments) {
            subject = "Analysing Arguments";
        } else if(view.getId() == R.id.statement_conclusion) {
            subject = "Statement & Conclusion";
        } else if(view.getId() == R.id.statement_arguments) {
            subject = "Statement & Arguments";
        }
        switch (action) {
            case "Upload": {
                Intent i = new Intent(getApplicationContext(), UploadQuestion.class);
                i.putExtra("category", category);
                i.putExtra("subject", subject);
                startActivity(i);
                break;
            }
            case "Update": {
                Intent i = new Intent(getApplicationContext(), UpdateQuestion.class);
                i.putExtra("category", category);
                i.putExtra("subject", subject);
                startActivity(i);
                break;
            }
            case "View": {
                Intent i = new Intent(getApplicationContext(), ViewQuestion.class);
                i.putExtra("category", category);
                i.putExtra("subject", subject);
                startActivity(i);
                break;
            }
        }
    }
}
