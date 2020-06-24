package com.himanshu.examination.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.himanshu.examination.Model.TestData;
import com.himanshu.examination.R;
import java.util.List;

public class MyTestAdapter extends RecyclerView.Adapter<MyTestAdapter.ViewHolder> {

    private List<TestData> testData;
    private int TestCount = 0;

    public MyTestAdapter(List<TestData> testData) {
        this.testData = testData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        TestCount += 1;
        TestData ld = testData.get(i);
        viewHolder.testCountTxt.setText("Test no = " + TestCount);
        viewHolder.subjectTxt.setText("Subject               :  " + ld.getSubject());
        viewHolder.marksTxt.setText("Marks                 :  " + ld.getMarks() + "/10");
        viewHolder.resultTxt.setText("Result                 :  " + ld.getResult());
        viewHolder.testTimeTxt.setText("Test Time          :  " + ld.getTestTime());
        viewHolder.dateAndTimeTxt.setText("Date And Time :  " + ld.getDateAndTime());
        viewHolder.descriptionTxt.setText("Description       :  " + ld.getDescription());

    }

    @Override
    public int getItemCount() {
        return testData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView testCountTxt, subjectTxt, marksTxt, resultTxt, testTimeTxt, dateAndTimeTxt, descriptionTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            testCountTxt = (TextView) itemView.findViewById(R.id.testCount);
            subjectTxt = (TextView) itemView.findViewById(R.id.textView40);
            marksTxt = (TextView) itemView.findViewById(R.id.textView41);
            resultTxt = (TextView) itemView.findViewById(R.id.textView42);
            testTimeTxt = (TextView) itemView.findViewById(R.id.textView43);
            dateAndTimeTxt = (TextView) itemView.findViewById(R.id.textView44);
            descriptionTxt = (TextView) itemView.findViewById(R.id.textView45);
        }
    }
}
