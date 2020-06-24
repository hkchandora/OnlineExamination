package com.himanshu.examination;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.UI.Aptitude;
import com.himanshu.examination.UI.Developer;
import com.himanshu.examination.UI.HistoryCategory;
import com.himanshu.examination.UI.Profile;
import com.himanshu.examination.UI.QuestionBank;
import com.himanshu.examination.UI.Reasoning;
import com.himanshu.examination.UI.Start;
import com.himanshu.examination.UI.SubjectExam;
import com.himanshu.examination.UI.Verbal;

import io.paperdb.Paper;

import static com.himanshu.examination.R.layout.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    CardView SubjectExamCard, AptitudeCard, ReasoningCard, VerbalCard, QuestionCard, NotificationCard, HelpCard, AdCard;
    TextView UserNameTxt, EmailTxt;
    ImageView UserImage;
    ProgressDialog loadBar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mDb;
    FirebaseUser user;
    private boolean doubleBackToExitPressedOnce = false;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);


        SubjectExamCard = findViewById(R.id.cardView1);
        AptitudeCard = findViewById(R.id.cardView2);
        ReasoningCard = findViewById(R.id.cardView3);
        VerbalCard = findViewById(R.id.cardView4);
        QuestionCard = findViewById(R.id.cardView5);
        NotificationCard = findViewById(R.id.cardView6);
        HelpCard = findViewById(R.id.cardView7);
        AdCard = findViewById(R.id.cardView8);
        UserNameTxt = headerView.findViewById(R.id.userNameTxt);
        EmailTxt = headerView.findViewById(R.id.emailTxt);
        UserImage = headerView.findViewById(R.id.userImage);

        SubjectExamCard.setOnClickListener(this);
        AptitudeCard.setOnClickListener(this);
        ReasoningCard.setOnClickListener(this);
        VerbalCard.setOnClickListener(this);
        QuestionCard.setOnClickListener(this);
        NotificationCard.setOnClickListener(this);
        HelpCard.setOnClickListener(this);
        AdCard.setOnClickListener(this);


        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDb = mDatabase.getReference();
        user = firebaseAuth.getCurrentUser();


        loadBar = new ProgressDialog(this);

        loadBar.show();
        loadBar.setContentView(R.layout.progress_dialog);
        loadBar.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        loadBar.setCanceledOnTouchOutside(false);

        mDb.child("SignUp Members").child("Users").orderByChild("email").equalTo(user.getEmail()).limitToFirst(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String name = String.valueOf(data.child("name").getValue());
                        String email = String.valueOf(data.child("email").getValue());
                        String image = String.valueOf(data.child("image").getValue());
                        String gender = String.valueOf(data.child("gender").getValue());

                        UserNameTxt.setText(name);
                        EmailTxt.setText(email);
                        if (!image.equals("")) {
                            Glide.with(MainActivity.this).load(image).into(UserImage);
                        }
                        if (image.equals("")) {
                            if (gender.equals("Male")) {
                                UserImage.setImageResource(R.drawable.profilemale);
                            } else {
                                UserImage.setImageResource(R.drawable.profilefemale);
                            }
                        }
                        loadBar.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent i = new Intent(MainActivity.this, Developer.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.logOut) {
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
                    Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Start.class));
                    finish();
                }
            });
           return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.first) {
//            finish();

        } else if (id == R.id.second) {
            Intent i = new Intent(MainActivity.this, Profile.class);
            startActivity(i);

        } else if (id == R.id.third) {
            Intent i = new Intent(MainActivity.this, HistoryCategory.class);
            startActivity(i);

        } else if (id == R.id.fourth) {
            Intent i = new Intent(MainActivity.this, Developer.class);
            startActivity(i);

        } else if (id == R.id.fifth) {
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "chandorahimanshu@email.com", null));
            intent.putExtra(Intent.EXTRA_TEXT, "Hello");
            intent.putExtra(Intent.EXTRA_SUBJECT, "About Online Examination App");
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));

        } else if (id == R.id.sixth) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + this.getPackageName())));
            } catch (android.content.ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Start.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cardView1) {
            Intent i = new Intent(MainActivity.this, SubjectExam.class);
            startActivity(i);
        }
        if (v.getId() == R.id.cardView2) {
            Intent i = new Intent(MainActivity.this, Aptitude.class);
            startActivity(i);
        }
        if (v.getId() == R.id.cardView3) {
            Intent i = new Intent(MainActivity.this, Reasoning.class);
            startActivity(i);
        }
        if (v.getId() == R.id.cardView4) {
            Intent i = new Intent(MainActivity.this, Verbal.class);
            startActivity(i);
        }
        if (v.getId() == R.id.cardView5) {
            Intent i = new Intent(MainActivity.this, QuestionBank.class);
            startActivity(i);
        }
        if (v.getId() == R.id.cardView6) {
            startActivity(new Intent(getApplicationContext(), Developer.class));
        }
        if (v.getId() == R.id.cardView7) {
            startActivity(new Intent(getApplicationContext(), Developer.class));
        }
        if (v.getId() == R.id.cardView8) {
            startActivity(new Intent(getApplicationContext(), Developer.class));
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else

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