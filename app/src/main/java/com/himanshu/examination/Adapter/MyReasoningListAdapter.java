package com.himanshu.examination.Adapter;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.himanshu.examination.Model.MyListData;
import com.himanshu.examination.Questions.Question;
import com.himanshu.examination.R;

public class MyReasoningListAdapter extends RecyclerView.Adapter<MyReasoningListAdapter.ViewHolder> {
    private MyListData[] listData;

    String subject;
    String category = "Reasoning Questions";

    // RecyclerView recyclerView;
    public MyReasoningListAdapter(MyListData[] myListData) {
        this.listData = myListData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rlist_item, parent, false);
        return new ViewHolder(listItem);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyListData myListData = listData[position];
        holder.textView.setText(myListData.getDescription());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(view.getContext(), "click on item: " + myListData.getSuggestion(), Toast.LENGTH_LONG).show();
                subject = myListData.getDescription();
                Intent i = new Intent(view.getContext(), Question.class);
                i.putExtra("Subject", subject);
                i.putExtra("category", category);
                view.getContext().startActivity(i);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
