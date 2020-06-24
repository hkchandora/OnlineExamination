package com.himanshu.examination.Questions;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.R;
import com.himanshu.examination.UI.Result;

public class Question extends AppCompatActivity {
    TextView t1, Question, title, QuestionCount;
    RadioButton op1, op2, op3, op4;
    RadioGroup rg;
    Button backBtn;
    ProgressDialog loadBar;
    int sec, min;
    FirebaseDatabase mDatabase;
    DatabaseReference mDb;
    String subject, correct;
    int i = 1;
    public int marks = 0, right = 0, wrong = 0;

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        getSupportActionBar().hide();

        loadBar = new ProgressDialog(this);

        loadBar.show();
        loadBar.setContentView(R.layout.progress_dialog);
        loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadBar.setCanceledOnTouchOutside(false);

        t1 = findViewById(R.id.textView21);
        title = findViewById(R.id.textView22);
        QuestionCount = findViewById(R.id.textView23);
        rg = findViewById(R.id.answer);
        Question = findViewById(R.id.textView14);
        op1 = findViewById(R.id.radioButton);
        op2 = findViewById(R.id.radioButton2);
        op3 = findViewById(R.id.radioButton3);
        op4 = findViewById(R.id.radioButton4);
        backBtn = findViewById(R.id.button14);

        category = getIntent().getExtras().getString("category");
        subject = getIntent().getExtras().getString("Subject");
        title.setText(subject);

        QuestionCount.setText("Question : 1/10");
        mDatabase = FirebaseDatabase.getInstance();
        mDb = mDatabase.getReference().child("Questions").child(category);


        mDb.child(subject).child("Question 1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String question = String.valueOf(dataSnapshot.child("question").getValue());
                String option1 = String.valueOf(dataSnapshot.child("option1").getValue());
                String option2 = String.valueOf(dataSnapshot.child("option2").getValue());
                String option3 = String.valueOf(dataSnapshot.child("option3").getValue());
                String option4 = String.valueOf(dataSnapshot.child("option4").getValue());
                correct = String.valueOf(dataSnapshot.child("correct").getValue());

                Question.setText(question);
                op1.setText(option1);
                op2.setText(option2);
                op3.setText(option3);
                op4.setText(option4);

                loadBar.dismiss();
                //Toast.makeText(Question.this, "Correct Answer is "+correct, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadBar.dismiss();
                Toast.makeText(Question.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


        new CountDownTimer(60000 * 10, 1000) {

            public void onTick(long millisUntilFinished) {
                long totSec = (millisUntilFinished / 1000);
                sec = (int) (totSec % 60);
                min = (int) (totSec / 60);

                t1.setText("Remain- Min: " + min + " Sec: " + sec);
            }

            public void onFinish() {
                t1.setText("done!");

                switch (category) {
                    case "Aptitude Questions":
                        category = "Aptitude Test";
                        break;
                    case "Reasoning Questions":
                        category = "Reasoning Test";
                        break;
                    case "Verbal Questions":
                        category = "Verbal Test";
                        break;
                    case "Subject Questions":
                        category = "Subject Exam";
                        break;
                }
                Intent i = new Intent(Question.this, Result.class);
                i.putExtra("mark", marks);
                i.putExtra("right", right);
                i.putExtra("wrong", wrong);
                i.putExtra("min", 9 - min);
                i.putExtra("sec", 60 - sec);
                i.putExtra("subject", subject);
                i.putExtra("category", category);
                startActivity(i);
                finish();
            }

        }.start();
    }

    //For Next Button
    public void NextQuestion(View view) {

        int selectedId = rg.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        String selected = selectedRadioButton.getText().toString();

        if (selected.matches("")) {
            Toast.makeText(com.himanshu.examination.Questions.Question.this, "Select One Option", Toast.LENGTH_SHORT).show();
        } else {
            i += 1;

            if (i >= 11) {

                if (selected.equals(correct)) {
                    //Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
                    right += 1;
                    marks += 1;
                } else {
                    //Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                    wrong += 1;
                }

                switch (category) {
                    case "Aptitude Questions":
                        category = "Aptitude Test";
                        break;
                    case "Reasoning Questions":
                        category = "Reasoning Test";
                        break;
                    case "Verbal Questions":
                        category = "Verbal Test";
                        break;
                    case "Subject Questions":
                        category = "Subject Exam";
                        break;
                }

                Intent i = new Intent(getApplicationContext(), Result.class);
                i.putExtra("mark", marks);
                i.putExtra("right", right);
                i.putExtra("wrong", wrong);
                i.putExtra("min", 9 - min);
                i.putExtra("sec", 60 - sec);
                i.putExtra("subject", subject);
                i.putExtra("category", category);
                startActivity(i);
                finish();
            } else {

                if (selected.equals(correct)) {
                    //Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
                    right += 1;
                    marks += 1;
                } else {
                    //Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                    wrong += 1;
                }

                mDb.child(subject).child("Question " + i).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String question = String.valueOf(dataSnapshot.child("question").getValue());
                        String option1 = String.valueOf(dataSnapshot.child("option1").getValue());
                        String option2 = String.valueOf(dataSnapshot.child("option2").getValue());
                        String option3 = String.valueOf(dataSnapshot.child("option3").getValue());
                        String option4 = String.valueOf(dataSnapshot.child("option4").getValue());
                        correct = String.valueOf(dataSnapshot.child("correct").getValue());

                        Question.setText(question);
                        op1.setText(option1);
                        op2.setText(option2);
                        op3.setText(option3);
                        op4.setText(option4);

                        loadBar.dismiss();

                        rg.clearCheck();
                        //Toast.makeText(Question.this, "Correct Answer is "+correct, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        loadBar.dismiss();
                        Toast.makeText(com.himanshu.examination.Questions.Question.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

                QuestionCount.setText("Question : " + i + "/10");
                backBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    //For Back Button
    public void BackQuestion(View view) {
        i -= 1;
        mDb.child(subject).child("Question " + i).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String question = String.valueOf(dataSnapshot.child("question").getValue());
                String option1 = String.valueOf(dataSnapshot.child("option1").getValue());
                String option2 = String.valueOf(dataSnapshot.child("option2").getValue());
                String option3 = String.valueOf(dataSnapshot.child("option3").getValue());
                String option4 = String.valueOf(dataSnapshot.child("option4").getValue());
                correct = String.valueOf(dataSnapshot.child("correct").getValue());

                Question.setText(question);
                op1.setText(option1);
                op2.setText(option2);
                op3.setText(option3);
                op4.setText(option4);

                loadBar.dismiss();
                //Toast.makeText(Question.this, "Correct Answer is "+correct, Toast.LENGTH_SHORT).show();
                QuestionCount.setText("Question : " + i + "/10");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadBar.dismiss();
                Toast.makeText(com.himanshu.examination.Questions.Question.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        if (i == 1) {
            backBtn.setVisibility(View.INVISIBLE);
        }
    }

    //For Exit
    public void Exit(View view) {
        Warning();
        //Toast.makeText(this, "Marks = " + marks, Toast.LENGTH_SHORT).show();
    }


    public void Warning() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                switch (category) {
                    case "Subject Questions":
                        category = "Subject Exam";
                        break;
                    case "Aptitude Questions":
                        category = "Aptitude Test";
                        break;
                    case "Reasoning Questions":
                        category = "Reasoning Test";
                        break;
                    case "Verbal Questions":
                        category = "Verbal Test";
                        break;
                }
                //Perform Action
                Intent i = new Intent(Question.this, Result.class);
                i.putExtra("mark", marks);
                i.putExtra("right", right);
                i.putExtra("wrong", wrong);
                i.putExtra("min", 9 - min);
                i.putExtra("sec", 60 - sec);
                i.putExtra("subject", subject);
                i.putExtra("category", category);
                startActivity(i);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Set other dialog properties
        builder.setMessage("Do you want to exit?")
                .setTitle("Exit");
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        Warning();
    }
}
