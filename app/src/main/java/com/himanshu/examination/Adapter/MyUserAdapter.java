package com.himanshu.examination.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.himanshu.examination.Model.SignUpMember;
import com.himanshu.examination.R;

import java.util.List;

public class MyUserAdapter extends RecyclerView.Adapter<MyUserAdapter.ViewHolder> {

    private List<SignUpMember> members;
    private Context context=null;

    public MyUserAdapter(List<SignUpMember> signUpMember) {
        this.members = signUpMember;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }


    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {

        final SignUpMember ld = members.get(i);
        viewHolder.Name.setText("Name  : " + ld.getName());
        viewHolder.Email.setText("Email     :  " + ld.getEmail());
        viewHolder.Gender.setText("Gender  :  " + ld.getGender());
        viewHolder.Stream.setText("Stream  :  " + ld.getStream());
        viewHolder.Class.setText("Class     :  " + ld.getStandard());
        viewHolder.Division.setText("Division :  " + ld.getDivision());
        viewHolder.RollNo.setText("Roll No   :  " + ld.getRollNo());
        viewHolder.Address.setText("Address : " + ld.getAddress());

        viewHolder.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("SignUp Members").child("Users");

                reference.orderByChild("email").equalTo(ld.getEmail()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                data.getRef().removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return members.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Usercount, Name, Email, Gender, Stream, Class, Division, RollNo, Address;
        private Button DeleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            Name = (TextView) itemView.findViewById(R.id.textView46);
            Email = (TextView) itemView.findViewById(R.id.textView47);
            Gender = (TextView) itemView.findViewById(R.id.textView48);
            Stream = (TextView) itemView.findViewById(R.id.textView50);
            Class = (TextView) itemView.findViewById(R.id.textView51);
            Division = (TextView) itemView.findViewById(R.id.textView52);
            RollNo = (TextView) itemView.findViewById(R.id.textView49);
            Address = (TextView) itemView.findViewById(R.id.textView53);
            DeleteBtn = (Button) itemView.findViewById(R.id.delete_user_btn);
        }
    }
}
