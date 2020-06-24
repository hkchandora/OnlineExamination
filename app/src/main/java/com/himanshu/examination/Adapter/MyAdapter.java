package com.himanshu.examination.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.himanshu.examination.Model.ListData;
import com.himanshu.examination.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListData> listData;
    int QuestionCount = 0;

    public MyAdapter(List<ListData> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder viewHolder, int i) {

        QuestionCount++;
        ListData ld = listData.get(i);
        viewHolder.QuestionTxt.setText(" Question "+QuestionCount+" :\n "+ld.getQuestion());
        viewHolder.Option1Txt.setText("Option 1 : "+ld.getOption1());
        viewHolder.Option2Txt.setText("Option 2 : "+ld.getOption2());
        viewHolder.Option3Txt.setText("Option 3 : "+ld.getOption3());
        viewHolder.Option4Txt.setText("Option 4 : "+ld.getOption4());
        viewHolder.CorrectTxt.setText("Correct Answer : "+ld.getCorrect());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView QuestionTxt, Option1Txt, Option2Txt, Option3Txt, Option4Txt, CorrectTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            QuestionTxt = (TextView) itemView.findViewById(R.id.textView31);
            Option1Txt = (TextView) itemView.findViewById(R.id.textView32);
            Option2Txt = (TextView) itemView.findViewById(R.id.textView33);
            Option3Txt = (TextView) itemView.findViewById(R.id.textView37);
            Option4Txt = (TextView) itemView.findViewById(R.id.textView38);
            CorrectTxt = (TextView) itemView.findViewById(R.id.textView39);
        }
    }
}

