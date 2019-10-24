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


        ArrayList<Float> fall_in_sleep_data_list = new ArrayList<Float>();
        ArrayList<Float> sleep_data_list = new ArrayList<Float>();

        fall_in_sleep_data_list.add(1.0f);
        fall_in_sleep_data_list.add(5.0f);
        fall_in_sleep_data_list.add(4.4f);
        sleep_data_list.add(3.3f);
        sleep_data_list.add(4.4f);
        sleep_data_list.add(5.5f);


        for (Object o : fall_in_sleep_data_list){
            Log.e(TAG, "fall_in_sleep_data_list: "+o.toString());
        }


        for (Object o : sleep_data_list){
            Log.e(TAG, "sleep_data_list: "+o.toString());
        }



//        cv.loadData();


    }
}
