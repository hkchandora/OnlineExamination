package com.himanshu.examination.Admin;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.Model.InsertQuestions;
import com.himanshu.examination.R;

public class UpdateQuestion extends AppCompatActivity {

    private Spinner  questionSpinner;
    private EditText questionTxt, op1Txt, op2Txt, op3Txt, op4Txt, correctTxt;
    private DatabaseReference myRef;
    private InsertQuestions add;
    private String category, subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);

        questionSpinner = findViewById(R.id.spinner9);
        questionTxt = findViewById(R.id.editText15);
        op1Txt = findViewById(R.id.editText16);
        op2Txt = findViewById(R.id.editText17);
        op3Txt = findViewById(R.id.editText18);
        op4Txt = findViewById(R.id.editText19);
        correctTxt = findViewById(R.id.editText20);

        category = getIntent().getExtras().getString("category");
        subject = getIntent().getExtras().getString("subject");


        myRef = FirebaseDatabase.getInstance().getReference().child("Questions").child(category).child(subject);

        add = new InsertQuestions();
    }

    public void Update(View view) {

        final String questionNo = questionSpinner.getSelectedItem().toString();
        final String question = questionTxt.getText().toString();
        final String op1 = op1Txt.getText().toString();
        final String op2 = op2Txt.getText().toString();
        final String op3 = op3Txt.getText().toString();
        final String op4 = op4Txt.getText().toString();
        final String correct = correctTxt.getText().toString();

        if (questionNo.equals("Choose Question")) {
            Toast.makeText(this, "Please Select Question No", Toast.LENGTH_SHORT).show();
        } else if (question.equals("")) {
            questionTxt.setError("Question is required!");
        } else if (op1.equals("")) {
            op1Txt.setError("Option 1 is missing");
        } else if (op2.equals("")) {
            op2Txt.setError("Option 2 is missing");
        } else if (op3.equals("")) {
            op3Txt.setError("Option 3 is missing");
        } else if (op4.equals("")) {
            op4Txt.setError("Option 4 is missing");
        } else if (correct.equals("")) {
            correctTxt.setError("Correct Answer is missing");
        } else {

            myRef.child("Question "+questionNo).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    add.setQuestion(question);
                    add.setOption1(op1);
                    add.setOption2(op2);
                    add.setOption3(op3);
                    add.setOption4(op4);
                    add.setCorrect(correct);
                    myRef.child("Question "+questionNo).setValue(add);
                    Toast.makeText(UpdateQuestion.this, "Success", Toast.LENGTH_SHORT).show();

                    questionTxt.setText("");
                    op1Txt.setText("");
                    op2Txt.setText("");
                    op3Txt.setText("");
                    op4Txt.setText("");
                    correctTxt.setText("");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(UpdateQuestion.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
