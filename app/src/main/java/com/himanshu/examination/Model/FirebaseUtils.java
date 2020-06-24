package com.himanshu.examination.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtils {

    public static final FirebaseDatabase firebaseDb = FirebaseDatabase.getInstance();
    public static final DatabaseReference userReference = firebaseDb.getReference().child("SignUp Members")
            .child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

}
