package com.ccelllnmiit;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.r0adkll.slidr.Slidr;

public class Dev extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);
        Slidr.attach(this);


    }




}

