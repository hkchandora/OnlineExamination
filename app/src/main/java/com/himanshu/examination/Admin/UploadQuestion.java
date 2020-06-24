package com.himanshu.examination.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.Model.InsertQuestions;
import com.himanshu.examination.R;
import com.himanshu.examination.UI.Start;

import io.paperdb.Paper;

public class UploadQuestion extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private InsertQuestions add;
    public String category, subject;
    private TextView queNo;
    private EditText ques, op1, op2, op3, op4, correct;

    private long QuestionCount = 0;
    private long count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_question);
        //getSupportActionBar().hide();

        TextView t1 = findViewById(R.id.textView15);
        category = getIntent().getExtras().getString("category");
        subject = getIntent().getExtras().getString("subject");
        t1.setText(subject);
        setTitle(subject);

        queNo = findViewById(R.id.textView16);
        ques = findViewById(R.id.editText9);
        op1 = findViewById(R.id.editText10);
        op2 = findViewById(R.id.editText11);
        op3 = findViewById(R.id.editText12);
        op4 = findViewById(R.id.editText13);
        correct = findViewById(R.id.editText14);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Questions").child(category).child(subject);

        add = new InsertQuestions();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    QuestionCount = (dataSnapshot.getChildrenCount());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void AddQuestion(View view) {
        String question = ques.getText().toString();
        String option1 = op1.getText().toString();
        String option2 = op2.getText().toString();
        String option3 = op3.getText().toString();
        String option4 = op4.getText().toString();
        String right = correct.getText().toString();

        if (TextUtils.isEmpty(question)) {
            ques.setError("Question is required!");
        } else if (TextUtils.isEmpty(option1)) {
            op1.setError("Option 1 is Incorrect!");
        } else if (TextUtils.isEmpty(option2)) {
            op2.setError("Option 2 is required!");
        } else if (TextUtils.isEmpty(option3)) {
            op3.setError("Option 3 is required!");
        } else if (TextUtils.isEmpty(option4)) {
            op4.setError("Option 4 is required!");
        } else if (TextUtils.isEmpty(right)) {
            correct.setError("Right Option is required!");
        } else {

            add.setQuestion(question);
            add.setOption1(option1);
            add.setOption2(option2);
            add.setOption3(option3);
            add.setOption4(option4);
            add.setCorrect(right);
            databaseReference.child("Question " + (QuestionCount + 1)).setValue(add);
            Toast.makeText(UploadQuestion.this, "Question " + (QuestionCount + 1) + " Inserted Successfully", Toast.LENGTH_SHORT).show();

            ques.setText("");
            op1.setText("");
            op2.setText("");
            op3.setText("");
            op4.setText("");
            correct.setText("");

            count = QuestionCount + 1;
            queNo.setText("Question No = " + (count + 1));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();
        if(id == R.id.changeQuestion) {
            Intent i = new Intent(UploadQuestion.this, UpdateQuestion.class);
            startActivity(i);
            return true;
        }
        if(id == R.id.viewQuestion) {
            Intent i = new Intent(UploadQuestion.this, ViewQuestion.class);
            i.putExtra("Subject", subject);
            startActivity(i);
            return true;
        }
        if(id == R.id.logOutMenu) {
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadQuestion.this);
            builder.setPositiveButton("LogOut", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Paper.book().destroy();
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(UploadQuestion.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Start.class));
                    finish();

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.setMessage("Do you want to LogOut your account.")
                    .setTitle("Log Out");
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
