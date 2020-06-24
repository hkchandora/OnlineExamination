package com.himanshu.examination.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.himanshu.examination.R;

import java.util.Objects;

import io.paperdb.Paper;

public class Start extends AppCompatActivity {

    Button logInBtn, signUpBtn;
    ProgressDialog loadBar;

//    String AdminMailKey, AdminPasswordKey, UserMailKey, UserPasswordKey;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        logInBtn = findViewById(R.id.main_loginBtn);
        signUpBtn = findViewById(R.id.main_signUpBtn);


        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        Paper.init(this);
        loadBar = new ProgressDialog(this);


        Objects.requireNonNull(getSupportActionBar()).hide();

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LogIn.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

    }
}