package com.himanshu.examination.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.himanshu.examination.R;

public class VerbalCategory extends AppCompatActivity {

    String category, subject, action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbal_category);
        getSupportActionBar().hide();

        category = getIntent().getExtras().getString("category");
        action = getIntent().getExtras().getString("action");

    }

    public void StartVerbal(View view) {
        if (view.getId() == R.id.spot_errors) {
            subject = "Spot Errors";
        } else if (view.getId() == R.id.selecting_words) {
            subject = "Selecting Words";
        } else if (view.getId() == R.id.ordering_words) {
            subject = "Ordering Words";
        } else if (view.getId() == R.id.complete_statement) {
            subject = "Complete Statement";
        } else if (view.getId() == R.id.closer_test) {
            subject = "Closer Test";
        } else if (view.getId() == R.id.idioms_phrases) {
            subject = "Idioms & Phrases";
        } else if (view.getId() == R.id.verbal_analogies) {
            subject = "Verbal Analogies";
        } else if (view.getId() == R.id.synonyms) {
            subject = "Synonyms";
        } else if (view.getId() == R.id.spellings) {
            subject = "Spellings";
        } else if (view.getId() == R.id.sentence_correction) {
            subject = "Sentence Correction";
        } else if (view.getId() == R.id.order_sentences) {
            subject = "Order Sentences";
        } else if (view.getId() == R.id.comprehension) {
            subject = "Comprehension";
        } else if (view.getId() == R.id.change_of_voice) {
            subject = "Change of Voice";
        } else if (view.getId() == R.id.antonyms) {
            subject = "Antonyms";
        } else if (view.getId() == R.id.sentence_formation) {
            subject = "Sentence Formation";
        } else if (view.getId() == R.id.sentence_improvement) {
            subject = "Sentence Improvement";
        } else if (view.getId() == R.id.paragraph_formation) {
            subject = "Paragraph Formation";
        } else if (view.getId() == R.id.substitute) {
            subject = "Substitute";
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
