package com.himanshu.examination.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.himanshu.examination.Model.SignUpMember;
import com.himanshu.examination.R;

import java.util.ArrayList;
import java.util.List;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatabaseReference myRef;
    Spinner genderSpinner, streamSpinner, classSpinner, divisionSpinner;
    EditText nameTxt, rollNoTxt, addressTxt, emailTxt, passTxt, pass2Txt;
    ProgressDialog loadBar;
    SignUpMember member;
    FirebaseAuth firebaseAuth;
    TextView RegisterTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle("Sign Up");
//        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

        RegisterTxt = findViewById(R.id.register_text);
        nameTxt = findViewById(R.id.editText7);
        rollNoTxt = findViewById(R.id.editText);
        addressTxt = findViewById(R.id.editText8);
        emailTxt = findViewById(R.id.editText2);
        passTxt = findViewById(R.id.editText3);
        pass2Txt = findViewById(R.id.editText4);
        //Spinner
        genderSpinner = findViewById(R.id.spinner);
        streamSpinner = findViewById(R.id.spinner1);
        classSpinner = findViewById(R.id.spinner2);
        divisionSpinner = findViewById(R.id.spinner3);

        RegisterTxt.setPaintFlags(RegisterTxt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loadBar = new ProgressDialog(this);

        member = new SignUpMember();
        myRef = FirebaseDatabase.getInstance().getReference().child("SignUp Members").child("Users");
        firebaseAuth = FirebaseAuth.getInstance();


        pass2Txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!pass2Txt.getText().toString().equals(passTxt.getText().toString())) {
                        pass2Txt.setError("Password and Confirm password do not match");
                        pass2Txt.setText("");
                    }
                }
            }
        });

        streamSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // TODO Auto-generated method stub
        String sp1 = String.valueOf(streamSpinner.getSelectedItem());
        if (sp1.contentEquals("Choose Stream")) {
            //Nothing
            List<String> list = new ArrayList<String>();
            list.add("Choose Stream First");
            ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter4.notifyDataSetChanged();
            classSpinner.setAdapter(dataAdapter4);
        }
        if (sp1.contentEquals("Degree Science")) {
            List<String> list = new ArrayList<String>();
            list.add("Choose Class");
            list.add("F.Y.B.Sc");
            list.add("S.Y.B.Sc");
            list.add("T.Y.B.Sc");
            list.add("F.Y.B.Sc IT Sem 1");
            list.add("F.Y.B.Sc IT Sem 2");
            list.add("S.Y.B.Sc IT Sem 3");
            list.add("S.Y.B.Sc IT Sem 4");
            list.add("T.Y.B.Sc IT Sem 5");
            list.add("T.Y.B.Sc IT Sem 6");
            list.add("M.Sc.I.Computer Sci.");
            list.add("M.Sc.II.Computer Sci.");
            list.add("M.Sc.IT Sem 1");
            list.add("M.Sc.IT Sem 2");
            list.add("M.Sc.IT Sem 3");
            list.add("M.Sc.IT Sem 4");
            list.add("F.Y.B.Sc. Aviation");
            list.add("S.Y.B.Sc. Aviation");
            list.add("T.Y.B.Sc. Aviation");
            list.add("Human Science");
            list.add("M.Sc. Inorganic Chemistry Sem (IV)");
            list.add("M.Sc. organic Chemistry Sem (IV)");
            list.add("M.Sc. Chemistry Sem (II)");
            list.add("M.Sc. Inorganic Chemistry Sem (III)");
            list.add("M.Sc. organic Chemistry Sem (III)");
            list.add("M.Sc. Inorganic Chemistry Sem (I)");
            list.add("M.Sc. organic Chemistry Sem (I)");
            list.add("CHS");
            list.add("CHS");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.notifyDataSetChanged();
            classSpinner.setAdapter(dataAdapter);
        }
        if (sp1.contentEquals("Degree Commerce")) {
            List<String> list = new ArrayList<String>();
            list.add("Choose Class");
            list.add("F.Y.B.Com");
            list.add("S.Y.B.Com");
            list.add("T.Y.B.Com");
            list.add("F.Y.B.Com AF Sem 1");
            list.add("F.Y.B.Com AF Sem 2");
            list.add("S.Y.B.Com AF Sem 3");
            list.add("S.Y.B.Com AF Sem 4");
            list.add("T.Y.B.Com AF Sem 5");
            list.add("T.Y.B.Com AF Sem 6");
            list.add("F.Y.B.Com BI Sem 1");
            list.add("F.Y.B.Com BI Sem 2");
            list.add("S.Y.B.Com BI Sem 3");
            list.add("S.Y.B.Com BI Sem 4");
            list.add("T.Y.B.Com BI Sem 5");
            list.add("T.Y.B.Com BI Sem 6");
            list.add("F.Y.B.Com BMS Sem 1");
            list.add("F.Y.B.Com BMS Sem 2");
            list.add("S.Y.B.Com BMS Sem 3");
            list.add("S.Y.B.Com BMS Sem 4");
            list.add("T.Y.B.Com BMS Sem 5");
            list.add("T.Y.B.Com BMS Sem 6");
            list.add("F.Y.B.Com FM Sem 1");
            list.add("F.Y.B.Com FM Sem 2");
            list.add("S.Y.B.Com FM Sem 3");
            list.add("S.Y.B.Com FM Sem 4");
            list.add("T.Y.B.Com FM Sem 5");
            list.add("T.Y.B.Com FM Sem 6");
            list.add("F.Y.B.Com BMM Sem 1");
            list.add("F.Y.B.Com BMM Sem 2");
            list.add("S.Y.B.Com BMM Sem 3");
            list.add("S.Y.B.Com BMM Sem 4");
            list.add("T.Y.B.Com BMM Sem 5");
            list.add("T.Y.B.Com BMM Sem 6");
            list.add("F.Y.B.Com BIM Sem 1");
            list.add("F.Y.B.Com BIM Sem 2");
            list.add("S.Y.B.Com BIM Sem 3");
            list.add("S.Y.B.Com BIM Sem 4");
            list.add("T.Y.B.Com BIM Sem 5");
            list.add("T.Y.B.Com BIM Sem 6");
            list.add("B.A FTNMP");
            list.add("SY BA. FTNMP SEM 3");
            list.add("B.A FTNMP SEM 2");
            list.add("SY BA. FTNMP SEM 4");
            list.add("M COM.(E COMMERCE) SEM II");
            list.add("M. COM(E COMMERCE) SEM IV");
            list.add("TYBA. FTNMP SEM 5");
            list.add("M COM(E COMMERCE) SEM III");
            list.add("M COM(E COMMERCE) SEM I");
            list.add("M/A");
            list.add("TY BA. FTNMP SEM 6");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter2.notifyDataSetChanged();
            classSpinner.setAdapter(dataAdapter2);
        }
        if (sp1.contentEquals("Junior Science")) {
            List<String> list = new ArrayList<String>();
            list.add("Choose Class");
            list.add("F.Y.J.C.");
            list.add("S.Y.J.C.");
            ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter3.notifyDataSetChanged();
            classSpinner.setAdapter(dataAdapter3);
        }
        if (sp1.contentEquals("Junior Commerce")) {
            List<String> list = new ArrayList<String>();
            list.add("Choose Class");
            list.add("F.Y.J.C.");
            list.add("S.Y.J.C.");
            ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter4.notifyDataSetChanged();
            classSpinner.setAdapter(dataAdapter4);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void SignUP(View view) {

        String emailVerifyTxt = emailTxt.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        String stream = streamSpinner.getSelectedItem().toString();
        String standard = classSpinner.getSelectedItem().toString();
        String division = divisionSpinner.getSelectedItem().toString();

        if (nameTxt.getText().toString().length() == 0) {
            nameTxt.setError("Name is required!");
        } else if (rollNoTxt.getText().toString().length() == 0) {
            rollNoTxt.setError("Roll no is required!");
        } else if (addressTxt.getText().toString().length() == 0) {
            addressTxt.setError("Address is required!");
        } else if (emailTxt.getText().toString().length() == 0) {
            emailTxt.setError("Email is required!");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailVerifyTxt).matches()) {
            emailTxt.setError("Email is incorrect");
        } else if (passTxt.getText().toString().length() == 0) {
            passTxt.setError("Password is required!");
        } else if (gender.equals("Select Gender")) {
            Toast.makeText(SignUp.this, "Please Choose Gender", Toast.LENGTH_SHORT).show();
        } else if (stream.equals("Choose Stream")) {
            Toast.makeText(SignUp.this, "Please Choose Stream", Toast.LENGTH_SHORT).show();
        } else if (standard.equals("Choose Class")) {
            Toast.makeText(SignUp.this, "Please Choose Class", Toast.LENGTH_SHORT).show();
        } else if (division.equals("Select Division")) {
            Toast.makeText(SignUp.this, "Please Choose Division", Toast.LENGTH_SHORT).show();
        } else if (pass2Txt.getText().toString().trim().length() < 6) {
            passTxt.setError("Password must be greater than 6 character");
        } else {

            loadBar.show();
            loadBar.setContentView(R.layout.progress_dialog);
            loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            loadBar.setCanceledOnTouchOutside(false);

            String email = emailTxt.getText().toString();
            String password = passTxt.getText().toString();

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                member.setName(nameTxt.getText().toString().trim());
                                member.setGender(genderSpinner.getSelectedItem().toString().trim());
                                member.setStream(streamSpinner.getSelectedItem().toString().trim());
                                member.setStandard(classSpinner.getSelectedItem().toString().trim());
                                member.setDivision(divisionSpinner.getSelectedItem().toString().trim());
                                member.setRollNo(rollNoTxt.getText().toString().trim());
                                member.setAddress(addressTxt.getText().toString().trim());
                                member.setEmail(emailTxt.getText().toString().trim());
                                member.setPassword(passTxt.getText().toString().trim());
                                member.setPassword2(pass2Txt.getText().toString().trim());
                                member.setImage("");
                                myRef.child(task.getResult().getUser().getUid()).setValue(member);

                                loadBar.dismiss();
                                onBackPressed();
                                //Toast.makeText(SignUp.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUp.this, "Congratulation, Your account has been created.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LogIn.class));
                                finish();

                            } else {
                                Toast.makeText(SignUp.this, "Failed SignUp", Toast.LENGTH_SHORT).show();
                                Toast.makeText(SignUp.this, "Please try again using another Email Address.", Toast.LENGTH_SHORT).show();
                                loadBar.dismiss();
                            }
                        }
                    });
        }
    }
}