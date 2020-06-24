package com.himanshu.examination.Admin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.R;
import com.himanshu.examination.UI.Start;

import io.paperdb.Paper;

public class AdminMain extends AppCompatActivity {

    CardView AllUser, Subject, Aptitude, Reasoning, Verbal, Logout;
    final Context context = this;
    Dialog dialog;
    String category;
    String action;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        getSupportActionBar().hide();

        Toolbar toolbar = findViewById(R.id.toolbar2);
//        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Share Button
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "https://play.google.com/store/apps/details?id=" + getPackageName();
                String shareSub = "Online Examination App";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_category_box);

        AllUser = findViewById(R.id.cardView9);
        Subject = findViewById(R.id.cardView10);
        Aptitude = findViewById(R.id.cardView11);
        Reasoning = findViewById(R.id.cardView12);
        Verbal = findViewById(R.id.cardView13);
        Logout = findViewById(R.id.cardView14);
    }

    public void ShowUser(View view) {
        startActivity(new Intent(getApplicationContext(), RegisteredUsers.class));
    }

    public void SubjectExam(View view) {
        category = "Subject Questions";
        dialog.show();
    }

    public void AptitudeTest(View view) {
        category = "Aptitude Questions";
        dialog.show();
    }

    public void ReasoningTest(View view) {
        category = "Reasoning Questions";
        dialog.show();
    }

    public void VerbalTest(View view) {
        category = "Verbal Questions";
        dialog.show();
    }

    public void click(View view) {
        if (view.getId() == R.id.upload) {
            action = "Upload";
        } else if (view.getId() == R.id.update) {
            action = "Update";
        } else if (view.getId() == R.id.view) {
            action = "View";
        }
//        Toast.makeText(context, "Clicked\n"+category+"\n"+action, Toast.LENGTH_SHORT).show();
        dialog.dismiss();


        switch (category) {
            case "Subject Questions": {
                Intent i = new Intent(getApplicationContext(), SubjectCategory.class);
                i.putExtra("category", category);
                i.putExtra("action", action);
                startActivity(i);
                break;
            }
            case "Aptitude Questions": {
                Intent i = new Intent(getApplicationContext(), AptitudeCategory.class);
                i.putExtra("category", category);
                i.putExtra("action", action);
                startActivity(i);
                break;
            }
            case "Reasoning Questions": {
                Intent i = new Intent(getApplicationContext(), ReasoningCategory.class);
                i.putExtra("category", category);
                i.putExtra("action", action);
                startActivity(i);
                break;
            }
            case "Verbal Questions": {
                Intent i = new Intent(getApplicationContext(), VerbalCategory.class);
                i.putExtra("category", category);
                i.putExtra("action", action);
                startActivity(i);
                break;
            }
        }
    }


    public void LogOut(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.log_out_dialog);
        dialog.show();
        Button LogoutBtn = dialog.findViewById(R.id.outTxt);
        Button CancelBtn = dialog.findViewById(R.id.cancelBtn);
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(AdminMain.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Start.class));
                finish();
            }
        });
    }

    //forgetPassword
    public void ForgotAdminPassword(View view) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.forgot_admin_password);

        final EditText emailEditText = dialog.findViewById(R.id.email);
        final EditText PasswordEditText = dialog.findViewById(R.id.password);
        final EditText RePasswordEditText = dialog.findViewById(R.id.rePassword);
        Button ChangePassword = dialog.findViewById(R.id.changePassword);
        final ProgressBar progressBar = dialog.findViewById(R.id.progressBar3);
        dialog.show();

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = emailEditText.getText().toString();
                final String Password = PasswordEditText.getText().toString();
                String RePassword = RePasswordEditText.getText().toString();
                if (TextUtils.isEmpty(Email)) {
                    emailEditText.setError("Email is Required");
                } else if (TextUtils.isEmpty(Password)) {
                    PasswordEditText.setError("Password is required");
                } else if (TextUtils.isEmpty(RePassword)) {
                    RePasswordEditText.setError("Re-enter password is required");
                } else if (!RePassword.equals(Password)) {
                    RePasswordEditText.setError("Password Mismatch");
                    RePasswordEditText.setText("");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                            .child("SignUp Members").child("Admin");

                    reference.orderByChild("Email").equalTo(Email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    data.getRef().child("Password").setValue(Password);
                                    Toast.makeText(context, "Password Changed", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}