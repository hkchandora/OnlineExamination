package com.himanshu.examination.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.Admin.AdminMain;
import com.himanshu.examination.Prevalent.Prevalent;
import com.himanshu.examination.R;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LogIn extends AppCompatActivity {
    EditText emailTxt, passTxt;
    TextView UserLogInTxt, AdminLogInTxt, ForgotTxt;
    CheckBox RememberMeChkBox;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    String parentDbName = "Users";
    final Context context = this;

    ProgressDialog loadBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailTxt = findViewById(R.id.editText5);
        passTxt = findViewById(R.id.editTextPass);
        ForgotTxt = findViewById(R.id.textView3);
        UserLogInTxt = findViewById(R.id.userLogInTxt);
        AdminLogInTxt = findViewById(R.id.adminLogInTxt);
        RememberMeChkBox = findViewById(R.id.remember_me_chkBox);

        Paper.init(this);

        loadBar = new ProgressDialog(this);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        AdminLogInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(LogIn.this, "Now You are Admin.", Toast.LENGTH_SHORT).show();
                AdminLogInTxt.setVisibility(View.INVISIBLE);
                UserLogInTxt.setVisibility(View.VISIBLE);
                emailTxt.setHint("    Admin Email");
                //RememberMeChkBox.setVisibility(View.INVISIBLE);
                ForgotTxt.setVisibility(View.INVISIBLE);
                parentDbName = "Admin";
            }
        });

        UserLogInTxt.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Toast.makeText(LogIn.this, "Now You are User.", Toast.LENGTH_SHORT).show();
                UserLogInTxt.setVisibility(View.INVISIBLE);
                AdminLogInTxt.setVisibility(View.VISIBLE);
                emailTxt.setHint("    Email");
                //RememberMeChkBox.setVisibility(View.VISIBLE);
                ForgotTxt.setVisibility(View.VISIBLE);
                parentDbName = "Users";
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //forgetPassword
    public void ForgotPassword(View view) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.forgot_password_dialog);

        final EditText emailEditText = dialog.findViewById(R.id.email);
        Button CustomButton = dialog.findViewById(R.id.btn_back);
        Button ResetButton = dialog.findViewById(R.id.btn_reset_password);
        final ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        dialog.show();

        CustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Email is required");
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LogIn.this, "Reset link sent to your email", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                    } else {
                                        Toast.makeText(LogIn.this, "Email don't Exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    public void logIn(View view) {

        final String email = emailTxt.getText().toString().trim();
        final String password = passTxt.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailTxt.setError("Email is required!");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTxt.setError("Email is Incorrect!");
        } else if (TextUtils.isEmpty(password)) {
            passTxt.setError("Password is required!");
        } else {

            loadBar.show();
            loadBar.setContentView(R.layout.progress_dialog);
            loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            loadBar.setCanceledOnTouchOutside(false);


//            if (RememberMeChkBox.isChecked()) {
//                if (parentDbName.equals("Users")) {
//                    Paper.book().write(Prevalent.UserMailKey, email);
//                    Paper.book().write(Prevalent.UserPasswordKey, password);
//                }
//                if (parentDbName.equals("Admin")) {
//                    Paper.book().write(Prevalent.AdminMailKey, email);
//                    Paper.book().write(Prevalent.AdminPasswordKey, password);
//                }
//            }

            if (parentDbName.equals("Admin")) {

                reference.child("SignUp Members").child("Admin").orderByChild("Email").equalTo(email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                String pass = String.valueOf(data.child("Password").getValue());

                                if (pass.equals(password)) {
                                    if(RememberMeChkBox.isChecked()) {
                                        Paper.book().write(Prevalent.AdminMailKey, email);
                                        Paper.book().write(Prevalent.AdminPasswordKey, password);
                                    }
                                    Toast.makeText(LogIn.this, "Admin Login Successfully", Toast.LENGTH_SHORT).show();
                                    loadBar.dismiss();
                                    startActivity(new Intent(LogIn.this, AdminMain.class));
                                    finish();
                                } else {
                                    Toast.makeText(LogIn.this, "Failed", Toast.LENGTH_SHORT).show();
                                    loadBar.dismiss();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {

                reference.child("SignUp Members").child("Users").orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                String pass = String.valueOf(data.child("password").getValue());

                                if (pass.equals(password)) {

                                    firebaseAuth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        if(RememberMeChkBox.isChecked()) {
                                                            Paper.book().write(Prevalent.UserMailKey, email);
                                                            Paper.book().write(Prevalent.UserPasswordKey, password);
                                                        }
                                                        //Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                                                        loadBar.dismiss();
                                                        onBackPressed();
                                                        startActivity(new Intent(getApplicationContext(), EmailVerifyActivity.class));
                                                        finish();
                                                    }
                                                }
                                            });

                                } else {
                                    Toast.makeText(LogIn.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                                    loadBar.dismiss();
                                }
                            }
                        }
                        if (!dataSnapshot.exists()) {
                            Toast.makeText(context, "Account doesn't exist", Toast.LENGTH_SHORT).show();
                            loadBar.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


//                firebaseAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    if(RememberMeChkBox.isChecked()) {
//                                        Paper.book().write(Prevalent.UserMailKey, email);
//                                        Paper.book().write(Prevalent.UserPasswordKey, password);
//                                    }
//                                    //Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
//                                    loadBar.dismiss();
//                                    onBackPressed();
//                                    startActivity(new Intent(getApplicationContext(), EmailVerifyActivity.class));
//                                    finish();
//                                } else {
//                                    loadBar.dismiss();
//                                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
            }
        }
    }
}

