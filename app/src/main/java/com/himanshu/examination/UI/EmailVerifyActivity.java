package com.himanshu.examination.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.himanshu.examination.MainActivity;
import com.himanshu.examination.R;

import io.paperdb.Paper;

public class EmailVerifyActivity extends AppCompatActivity {

    ProgressDialog loadBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verify);

        getSupportActionBar().hide();

        loadBar = new ProgressDialog(this);
        loadBar.setTitle("Verifying Email");
        loadBar.setMessage("A verification mail is sent to your email. Please Click on it and verify your mail.");
        loadBar.setCanceledOnTouchOutside(false);
        loadBar.show();

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        assert user != null;
        if (user.isEmailVerified()) {
            loadBar.dismiss();
//            Toast.makeText(EmailVerifyActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {
            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(EmailVerifyActivity.this);
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Paper.book().destroy();
                                        FirebaseAuth.getInstance().signOut();
                                        //Perform Action
                                        Intent i = new Intent(EmailVerifyActivity.this, LogIn.class);
                                        startActivity(i);
                                        Toast.makeText(EmailVerifyActivity.this, "Verify email", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                                builder.setMessage("Check your email inbox and verify the email.")
                                        .setTitle("Sent Verification email");
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            } else {
                                Toast.makeText(EmailVerifyActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
