package com.example.santa.redhare.personactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.santa.redhare.R;

/**
 * Created by santa on 16/8/5.
 */
public class PersonActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        initView(getIntent());

    }

    private void initView(Intent intent) {
        TextView name = (TextView) findViewById(R.id.person_name);
        TextView company = (TextView) findViewById(R.id.person_company);
        TextView station = (TextView) findViewById(R.id.person_station);
        PersonHeader header = (PersonHeader) findViewById(R.id.person_header);

        String namVal = intent.getStringExtra("name");
        String companyVal = intent.getStringExtra("company");
        String stationVal = intent.getStringExtra("station");
        if (null != namVal) {
            name.setText(namVal);
            header.setName(namVal);
        }
        if (null != companyVal) {
            company.setText(companyVal);
        }
        if (null != stationVal) {
            station.setText(stationVal);
        }
    }
}
