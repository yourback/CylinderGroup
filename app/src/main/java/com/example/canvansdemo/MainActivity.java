package com.example.canvansdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.FitWindowsLinearLayout;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CanView cv;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cv = findViewById(R.id.cv);


        ArrayList<Integer> fall_in_sleep_data_list = new ArrayList<>();
        ArrayList<Integer> sleep_data_list = new ArrayList<>();

        fall_in_sleep_data_list.add(10);
        fall_in_sleep_data_list.add(20);
        fall_in_sleep_data_list.add(15);
        sleep_data_list.add(13);
        sleep_data_list.add(5);
        sleep_data_list.add(0);

        cv.loadData(fall_in_sleep_data_list, sleep_data_list);


    }
}
