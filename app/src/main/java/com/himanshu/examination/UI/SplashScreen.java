package com.himanshu.examination.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.Admin.AdminMain;
import com.himanshu.examination.MainActivity;
import com.himanshu.examination.Prevalent.Prevalent;
import com.himanshu.examination.R;

import io.paperdb.Paper;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    String AdminMailKey, AdminPasswordKey, UserMailKey, UserPasswordKey;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        progressBar = findViewById(R.id.progressBar2);
        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();
        Paper.init(this);

        //For User Auto LogIn
        UserMailKey = Paper.book().read(Prevalent.UserMailKey);
        UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        //For Admin Auto LogIn
        AdminMailKey = Paper.book().read(Prevalent.AdminMailKey);
        AdminPasswordKey = Paper.book().read(Prevalent.AdminPasswordKey);

        isOnline();

    }

    public void isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            //Toast.makeText(this, "Net is On", Toast.LENGTH_SHORT).show();

            if (UserMailKey != null && UserPasswordKey != null) {
                if (!TextUtils.isEmpty(UserMailKey) && !TextUtils.isEmpty(UserPasswordKey)) {

                    String UEmail = UserMailKey;
                    String UPassword = UserPasswordKey;

                    firebaseAuth.signInWithEmailAndPassword(UEmail, UPassword)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Toast.makeText(SplashScreen.this, "User login Successful", Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    } else {
//                                        Toast.makeText(SplashScreen.this, "Login Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
            if (AdminMailKey != null && AdminPasswordKey != null) {
                if (!TextUtils.isEmpty(AdminMailKey) && !TextUtils.isEmpty(AdminPasswordKey)) {
                    String email = AdminMailKey;
                    final String password = AdminPasswordKey;

                    reference.child("SignUp Members").child("Admin").orderByChild("Email").equalTo(email).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() > 0) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                    String pass = String.valueOf(data.child("Password").getValue());

                                    if (pass.equals(password)) {
                                        startActivity(new Intent(getApplicationContext(), AdminMain.class));
                                        finish();
                                    } else {
//                                        Toast.makeText(SplashScreen.this, "Login Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        /* Create an Intent that will start the Menu-Activity. */
                        Intent mainIntent = new Intent(SplashScreen.this, Start.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();
                    }
                }, 1500);
            }
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "You are Offline", Toast.LENGTH_SHORT).show();
            Toast.makeText(SplashScreen.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
            builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                    System.exit(0);
                }
            });
            builder.setMessage("Please check your internet connection.");
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }
    }
}
