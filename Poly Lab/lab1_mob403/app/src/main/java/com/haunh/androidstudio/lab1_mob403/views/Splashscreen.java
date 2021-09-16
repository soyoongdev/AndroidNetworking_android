package com.haunh.androidstudio.lab1_mob403.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.haunh.androidstudio.lab1_mob403.R;

public class Splashscreen extends AppCompatActivity {
    public static int splashTimeOut = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splashscreen.this, lession1.class);
                startActivity(intent);

                fileList();
            }
        }, splashTimeOut);
    }
}