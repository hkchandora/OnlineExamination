package com.himanshu.examination.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.himanshu.examination.R;

public class AptitudeCategory extends AppCompatActivity {

    String subject, category, action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aptitude_category);
        getSupportActionBar().hide();

        category = getIntent().getExtras().getString("category");
        action = getIntent().getExtras().getString("action");


    }

    public void StartAptitude(View view) {
        if (view.getId() == R.id.hcl_lcm) {
            subject = "HCL & LCM";
        } else if (view.getId() == R.id.trains) {
            subject = "Trains";
        } else if (view.getId() == R.id.time_work) {
            subject = "Time & Work";
        } else if (view.getId() == R.id.profit_loss) {
            subject = "Profit & Loss";
        } else if (view.getId() == R.id.ages) {
            subject = "Ages";
        } else if (view.getId() == R.id.averages) {
            subject = "Averages";
        } else if (view.getId() == R.id.permutations) {
            subject = "Permutations";
        } else if (view.getId() == R.id.numbers) {
            subject = "Numbers";
        } else if (view.getId() == R.id.pipes) {
            subject = "Pipes";
        } else if (view.getId() == R.id.logarithms) {
            subject = "Logarithms";
        } else if (view.getId() == R.id.probability) {
            subject = "Probability";
        } else if (view.getId() == R.id.height_distance) {
            subject = "Height & Distance";
        } else if (view.getId() == R.id.percentages) {
            subject = "Percentages";
        } else if (view.getId() == R.id.mixtures) {
            subject = "Mixtures";
        } else if (view.getId() == R.id.stocks_shares) {
            subject = "Stocks & Shares";
        } else if (view.getId() == R.id.banker) {
            subject = "Banker's Discount";
        } else if (view.getId() == R.id.time_distance) {
            subject = "Time & Distance";
        } else if (view.getId() == R.id.simple_interest) {
            subject = "Simple Interest";
        } else if (view.getId() == R.id.compound_interest) {
            subject = "Compound Interest";
        } else if (view.getId() == R.id.partnerships) {
            subject = "Partnerships";
        } else if (view.getId() == R.id.calendar) {
            subject = "Calendar";
        } else if (view.getId() == R.id.area) {
            subject = "Area";
        } else if (view.getId() == R.id.clock) {
            subject = "Clock";
        } else if (view.getId() == R.id.volume_surface) {
            subject = "Volume & Surface";
        } else if (view.getId() == R.id.ratio_proportion) {
            subject = "Ratio & Proportion";
        } else if (view.getId() == R.id.boats_streams) {
            subject = "Boats & Streams";
        } else if (view.getId() == R.id.races_games) {
            subject = "Races & Games";
        } else if (view.getId() == R.id.true_discount) {
            subject = "True Discount";
        }

        switch (action) {
            case "Upload": {
                Intent i = new Intent(getApplicationContext(), UploadQuestion.class);
                i.putExtra("category", category);
                i.putExtra("subject", subject);
                startActivity(i);
                break;
            }
            case "Update": {
                Intent i = new Intent(getApplicationContext(), UpdateQuestion.class);
                i.putExtra("category", category);
                i.putExtra("subject", subject);
                startActivity(i);
                break;
            }
            case "View": {
                Intent i = new Intent(getApplicationContext(), ViewQuestion.class);
                i.putExtra("category", category);
                i.putExtra("subject", subject);
                startActivity(i);
                break;
            }
        }

    }
}

