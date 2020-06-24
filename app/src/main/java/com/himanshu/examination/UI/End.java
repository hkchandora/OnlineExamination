package com.himanshu.examination.UI;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.himanshu.examination.R;

public class End extends AppCompatActivity {

    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        getSupportActionBar().hide();

        int marks = getIntent().getIntExtra("total", 0);

        t1 = findViewById(R.id.complete);
        t1.setText("Hurry..!!\nCompleted\n"+ marks+"/10");
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(End.this, MainActivity.class);
//               startActivity(mainIntent);
//                finish();
                onBackPressed();
            }
        }, 2*1000);
    }
}
