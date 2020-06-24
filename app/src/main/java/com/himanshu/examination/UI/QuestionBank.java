package com.himanshu.examination.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.himanshu.examination.R;

public class QuestionBank extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question_bank);

    getSupportActionBar().hide();
  }
}
