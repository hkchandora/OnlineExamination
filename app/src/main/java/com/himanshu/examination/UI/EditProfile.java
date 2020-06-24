package com.himanshu.examination.UI;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.himanshu.examination.Model.FirebaseUtils;
import com.himanshu.examination.Model.SignUpMember;
import com.himanshu.examination.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView ChangeProfile;
    ImageView ProfileImage;
    EditText Name, RollNo, Address;
    Spinner Stream, Standard, Division, Gender;
    String NameTxt, RollNoTxt, AddressTxt, StreamTxt, ClassTxt, DivisionTxt, GenderTxt;
    int PICK_IMAGE_REQUEST = 1;
    Uri mImageUri;
    String ImageUploadId;
    StorageReference storageReference;
    DatabaseReference reference;
    ProgressDialog loadBar;
    FirebaseUser user;
    String name, email, gender,rollNo, address, image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().hide();


        ProfileImage = findViewById(R.id.profileImage);
        ChangeProfile = findViewById(R.id.changeProfile);
        Name = findViewById(R.id.profileName);
        Gender = findViewById(R.id.profileGender);
        RollNo = findViewById(R.id.profileRollNo);
        Address = findViewById(R.id.profileAddress);
        Stream = findViewById(R.id.profileStream);
        Standard = findViewById(R.id.profileClass);
        Division = findViewById(R.id.profileDivision);
        Stream.setOnItemSelectedListener(this);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("SignUp Members").child("Users");

        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Photos/");

        loadBar = new ProgressDialog(this);

        loadBar.show();
        loadBar.setContentView(R.layout.progress_dialog);
        loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadBar.setCanceledOnTouchOutside(false);



        reference.orderByChild("email").equalTo(user.getEmail()).limitToFirst(1).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        name = String.valueOf(data.child("name").getValue());
                         rollNo = String.valueOf(data.child("rollNo").getValue());
                         address = String.valueOf(data.child("address").getValue());
                         image = String.valueOf(data.child("image").getValue());
                        email = String.valueOf(data.child("email").getValue());
                         gender = String.valueOf(data.child("gender").getValue());
                        if (!image.equals("")) {
                            Glide.with(EditProfile.this).load(image).into(ProfileImage);
                        }
                        if (image.equals("")) {
                            if (gender.equals("Male")) {
                                ProfileImage.setImageResource(R.drawable.profilemale);
                            } else {
                                ProfileImage.setImageResource(R.drawable.profilefemale);
                            }
                        }
                        Name.setText(name);
                        RollNo.setText(rollNo);
                        Address.setText(address);
                        loadBar.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(Profile.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(EditProfile.this, "Network Problem..!!", Toast.LENGTH_SHORT).show();
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("Edit Profile");
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolBarColor)));
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void ChangeProfilePhoto(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(ProfileImage);

            loadBar.show();
            loadBar.setContentView(R.layout.progress_dialog);
            loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            loadBar.setCanceledOnTouchOutside(false);

            final StorageReference s = storageReference.child(name + "." + getFileExtension(mImageUri) + " (" + email + ")");

            UploadTask uploadTask = s.putFile(mImageUri);
            uploadTask
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return s.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                loadBar.dismiss();
                                Toast.makeText(EditProfile.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                Log.d("Profile", "Url: " + task.getResult());


                                FirebaseUtils.userReference.updateChildren(SignUpMember.updateImageMap(task.getResult().toString()))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(EditProfile.this, "Success", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(EditProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                //storage.update()
                                // Getting image upload ID.
                                ImageUploadId = reference.push().getKey();
                            } else {
                                //error
                            }
                        }
                    });
        }
    }

    public void SaveProfile(View view) {

         NameTxt = Name.getText().toString();
         GenderTxt = Gender.getSelectedItem().toString();
         RollNoTxt = RollNo.getText().toString();
         AddressTxt = Address.getText().toString();
         StreamTxt = Stream.getSelectedItem().toString();
         ClassTxt = Standard.getSelectedItem().toString();
         DivisionTxt = Division.getSelectedItem().toString();

        if (NameTxt.equals("")) {
            Name.setError("Name is required");
        } else if (GenderTxt.equals("Select Gender")) {
            Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
        } else if (RollNoTxt.equals("")) {
            RollNo.setError("Roll no is required");
        } else if (AddressTxt.equals("")) {
            Address.setError("Address is required");
        } else if (StreamTxt.equals("Choose Stream")) {
            Toast.makeText(this, "Select Stream", Toast.LENGTH_SHORT).show();
        } else if (ClassTxt.equals("Choose Class")) {
            Toast.makeText(this, "Select Class", Toast.LENGTH_SHORT).show();
        } else if (DivisionTxt.equals("Select Division")) {
            Toast.makeText(this, "Select Division", Toast.LENGTH_SHORT).show();
        } else {

            reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().child("name").setValue(NameTxt);
                    dataSnapshot.getRef().child("gender").setValue(GenderTxt);
                    dataSnapshot.getRef().child("rollNo").setValue(RollNoTxt);
                    dataSnapshot.getRef().child("address").setValue(AddressTxt);
                    dataSnapshot.getRef().child("stream").setValue(StreamTxt);
                    dataSnapshot.getRef().child("standard").setValue(ClassTxt);
                    dataSnapshot.getRef().child("division").setValue(DivisionTxt);
                    Toast.makeText(EditProfile.this, "Changes Saved Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Profile.class));
                    finish();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(EditProfile.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // TODO Auto-generated method stub
        String sp1 = String.valueOf(Stream.getSelectedItem());
        if (sp1.contentEquals("Choose Stream")) {
            //Nothing
            List<String> list = new ArrayList<String>();
            list.add("Choose Stream First");
            ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter4.notifyDataSetChanged();
            Standard.setAdapter(dataAdapter4);
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
            Standard.setAdapter(dataAdapter);
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
            Standard.setAdapter(dataAdapter2);
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
            Standard.setAdapter(dataAdapter3);
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
            Standard.setAdapter(dataAdapter4);
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

    public void BackButtonEditProfile(View view) {
        finish();
    }
}
