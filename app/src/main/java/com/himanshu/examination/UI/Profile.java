package com.himanshu.examination.UI;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
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
import com.rey.material.app.Dialog;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class Profile extends AppCompatActivity {
    private ImageView img;
    private Button LogOut;
    private TextView UserName, RollNo, Standard, Gender;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDb;
    private StorageReference storageReference;
    private FirebaseUser user;
    private ProgressDialog loadBar;
    private String name, rollNo, stream, standard, division, address, gender, email, image;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private String ImageUploadId;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LogOut = findViewById(R.id.logOutBtn);
        UserName = findViewById(R.id.currentUserNameTxt);
        RollNo = findViewById(R.id.currentUserRollNo);
        Standard = findViewById(R.id.textView36);
        Gender = findViewById(R.id.currentUserGender);
        img = findViewById(R.id.imageView4);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDb = mDatabase.getReference().child("SignUp Members").child("Users");
        user = firebaseAuth.getCurrentUser();

        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Photos/");

        loadBar = new ProgressDialog(this);

        loadBar.show();
        loadBar.setContentView(R.layout.progress_dialog);
        loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadBar.setCanceledOnTouchOutside(false);

        mDb.orderByChild("email").equalTo(user.getEmail()).limitToFirst(1).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        name = String.valueOf(data.child("name").getValue());
                        email = String.valueOf(data.child("email").getValue());
                        rollNo = String.valueOf(data.child("rollNo").getValue());
                        stream = String.valueOf(data.child("stream").getValue());
                        standard = String.valueOf(data.child("standard").getValue());
                        division = String.valueOf(data.child("division").getValue());
                        address = String.valueOf(data.child("address").getValue());
                        gender = String.valueOf(data.child("gender").getValue());
                        image = String.valueOf(data.child("image").getValue());
                        if (!image.equals("")) {
                            Glide.with(Profile.this).load(image).into(img);
                        }
                        if (image.equals("")) {
                            if (gender.equals("Male")) {
                                img.setImageResource(R.drawable.profilemale);
                            } else {
                                img.setImageResource(R.drawable.profilefemale);
                            }
                        }
                        UserName.setText("Name = " + name);
                        RollNo.setText("Roll No = " + rollNo);
                        Gender.setText("Gender = " + gender);
                        Standard.setText("Stream = " + stream + "\nClass  = " + standard + "\nDivision = " + division + "\nAddress = " + address);
                        loadBar.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(Profile.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Profile.this, "Network Problem..!!", Toast.LENGTH_SHORT).show();
            }
        });

        getSupportActionBar().hide();

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Profile.this);
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
                        Toast.makeText(Profile.this, "Logged Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Start.class));
                        finish();
                    }
                });
            }
        });
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void UpdateProfile(View view) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.profile_upload_dialog);
        Button ChangeProfile = dialog.findViewById(R.id.changeProfile);
        Button RemoveProfile = dialog.findViewById(R.id.removeProfile);
        dialog.show();

        ChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

            }
        });

        RemoveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDb.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("image").setValue("");
                        Toast.makeText(context, "Profile is removed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Profile.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(img);

            loadBar.setTitle("Please Wait...");
            loadBar.setMessage("Your profile is uploading..");
            loadBar.setCanceledOnTouchOutside(false);
            loadBar.show();

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
                                Toast.makeText(Profile.this, "Uploaded", Toast.LENGTH_SHORT).show();

                                Log.d("Profile", "Url: " + task.getResult());


                                FirebaseUtils.userReference.updateChildren(SignUpMember.updateImageMap(task.getResult().toString()))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Profile.this, "Success", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(Profile.this, "Failed", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                //storage.update()
                                // Getting image upload ID.
                                ImageUploadId = mDb.push().getKey();
                            } else {
                                //error
                            }
                        }
                    });
        }
    }

    public void BackButton(View view) {
        finish();
    }

    public void EditProfileTxt(View view) {
        startActivity(new Intent(getApplicationContext(), EditProfile.class));
    }
}
