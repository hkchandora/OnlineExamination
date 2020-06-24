package com.himanshu.examination.Admin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.himanshu.examination.R;
import com.himanshu.examination.UI.Start;

import io.paperdb.Paper;

public class ChooseAction extends AppCompatActivity {

    final Context context = this;
    Dialog dialog;
    String category;
    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_action);
        getSupportActionBar().hide();

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_category_box);

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
        if(view.getId() == R.id.upload){
            action = "Upload";
        } else if(view.getId() == R.id.update) {
            action = "Update";
        } else if(view.getId() == R.id.view) {
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
                Toast.makeText(ChooseAction.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Start.class));
                finish();
            }
        });
    }
}
